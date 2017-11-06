package com.example.conorbyrne.freebee;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UploadActivity extends AppCompatActivity {

    // Firebase vars
    private DatabaseReference mItemDatabase;

    // UI vars
    private TextInputLayout mUploadText;
    private Button mUploadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        // Firebase Database
        mItemDatabase = FirebaseDatabase.getInstance().getReference().child("Item");

        // UI vars
        mUploadText = (TextInputLayout) findViewById(R.id.upload_til);
        mUploadButton = (Button) findViewById(R.id.upload_btn);

        // Upload item to Firebase DB. Create new item with name using user input
        mUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String itemUpload = mUploadText.getEditText().getText().toString();
                mItemDatabase.child("name").setValue(itemUpload).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()){
                            Intent mainIntent = new Intent(UploadActivity.this, MainActivity.class);
                            startActivity(mainIntent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Upload Error... Sorry!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

}
