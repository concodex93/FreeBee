package com.example.conorbyrne.freebee;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.UUID;

public class UploadActivity extends AppCompatActivity {

    // Firebase vars
    private DatabaseReference mItemDatabase;
    private FirebaseUser currentUser;
    private StorageReference mStorageRef;

    // UI vars
    private TextInputLayout mUploadName;
    private TextInputLayout mUploadDesc;
    private Button mUploadButton;
    private Button mUploadImageButton;

    private String uniqueID;
    static final int GALLERY_PICK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        // Get user id
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = currentUser.getUid();

        // Create unique ID for item
        uniqueID = UUID.randomUUID().toString();

        mStorageRef = FirebaseStorage.getInstance().getReference();

        // Firebase Database - get Item node
        mItemDatabase = FirebaseDatabase.getInstance().getReference("users").child(uid).child("item" + uniqueID);

        // UI vars
        mUploadName = (TextInputLayout) findViewById(R.id.upload_til_name);
        mUploadDesc = (TextInputLayout) findViewById(R.id.upload_til_desc);
        mUploadImageButton = (Button) findViewById(R.id.upload_image_btn);
        mUploadButton = (Button) findViewById(R.id.upload_btn);

        mUploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                    startActivityForResult(Intent.createChooser(galleryIntent, "SELECT IMAGE") ,GALLERY_PICK);
                }
            }
        );


        // Upload item to Firebase DB. Create new item with name using user input
        mUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Get item name from user input and set in DB
                String itemName = mUploadName.getEditText().getText().toString();
                String itemDescription = mUploadDesc.getEditText().getText().toString();

                HashMap<String, String> dbHashMap = new HashMap<String, String>();
                dbHashMap.put("name", itemName);
                dbHashMap.put("description", itemDescription);

                mItemDatabase.setValue(dbHashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        // If task is successful, go to main activity
                        if (task.isSuccessful()){
                            Intent mainIntent = new Intent(UploadActivity.this, MainActivity.class);
                            startActivity(mainIntent);
                            finish();
                        } else {

                            Toast.makeText(getApplicationContext(), "Upload Error... Sorry !", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_PICK && resultCode == RESULT_OK){

            // Image data
            Uri uriImage = data.getData();
            // Storage reference
            StorageReference filepath = mStorageRef.child("uploaded_images").child("image" + uniqueID + ".jpg");

            // On task successful
            filepath.putFile(uriImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                    if(task.isSuccessful()){

                        @SuppressWarnings("VisibleForTests")
                        String downloadURL = task.getResult().getDownloadUrl().toString();

                        mItemDatabase.child("image").setValue(downloadURL).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if(task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();

                                } else {

                                    Toast.makeText(getApplicationContext(), "Cannot save image", Toast.LENGTH_LONG).show();

                                }
                            }
                        });

                    } else{
                        Toast.makeText(getApplicationContext(), "Image Uploading Error ", Toast.LENGTH_LONG).show();

                    }
                }
            });

        }
    }
}
