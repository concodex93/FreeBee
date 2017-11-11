package com.example.conorbyrne.freebee;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.UUID;

public class UploadActivity extends AppCompatActivity {

    // Firebase vars
    private DatabaseReference mItemDatabase;
    private FirebaseUser currentUser;

    // UI vars
    private TextInputLayout mUploadName;
    private TextInputLayout mUploadDesc;
    private Button mUploadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        // Get user id
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = currentUser.getUid();

        // Create unique ID for item
        String uniqueID = UUID.randomUUID().toString();

        // Firebase Database - get Item node
        mItemDatabase = FirebaseDatabase.getInstance().getReference("users").child(uid).child("item" + uniqueID);

        // UI vars
        mUploadName = (TextInputLayout) findViewById(R.id.upload_til_name);
        mUploadDesc = (TextInputLayout) findViewById(R.id.upload_til_desc);
        mUploadButton = (Button) findViewById(R.id.upload_btn);

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
}
