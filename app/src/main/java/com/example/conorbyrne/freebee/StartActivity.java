package com.example.conorbyrne.freebee;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    // This activity is used for users who are either not logged in
    // or who do not have an account yet.

    // UI vars
    Button mAccountBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // Account Button Defn
        mAccountBtn = (Button) findViewById(R.id.start_account_btn);
        mAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Sent user to Register Activity
                Intent register_intent = new Intent(StartActivity.this, RegisterActivity.class);
                startActivity(register_intent);
            }
        });


    }
}
