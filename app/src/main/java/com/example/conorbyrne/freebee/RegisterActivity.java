package com.example.conorbyrne.freebee;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.wang.avi.AVLoadingIndicatorView;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class RegisterActivity extends AppCompatActivity {

    // This Activity is for users to create new accounts with FreeBee

    // UI vars
    private TextInputLayout mDisplayName;
    private TextInputLayout mEmail;
    private TextInputLayout mPassword;
    private Button mRegiterBtn;

    // Toolbar
    private Toolbar mToolbar;

    // Firebase vars
    private FirebaseAuth mAuth;

    //Progress dialog
    private AVLoadingIndicatorView avi;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        mToolbar = (Toolbar) findViewById(R.id.toolbar_register);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Create Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        avi = (AVLoadingIndicatorView) findViewById(R.id.avi_reg);

        mDisplayName = (TextInputLayout) findViewById(R.id.register_display_name_til);
        mEmail = (TextInputLayout) findViewById(R.id.register_email_til);
        mPassword = (TextInputLayout) findViewById(R.id.register_password_til);
        mRegiterBtn = (Button) findViewById(R.id.register_create_reg_btn);

        // Pop up hints
        mDisplayName.setHint("Display Name");
        mEmail.setHint("Email");
        mPassword.setHint("Password");

        mRegiterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startAnim();
                hideKeyboard();

                // Get text from text input fields
                String displayName = mDisplayName.getEditText().getText().toString();
                String email = mEmail.getEditText().getText().toString();
                String password = mPassword.getEditText().getText().toString();

                if (!TextUtils.isEmpty(displayName) || !TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)) {
                    registerUser(displayName, email, password);
                }

            }
        });
    }

    private void registerUser(String displayName, String email, String password){

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (task.isSuccessful()) {
                            // Go to main page if task is successful
                            Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
                            // Clears all previous tasks after registration
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(mainIntent);
                            finish();
                        } else{
                            Toast.makeText(RegisterActivity.this, "Account Registration Error",
                                    Toast.LENGTH_SHORT).show();

                        }

                        stopAnim();
                    }
                });
    }

    // Hides android keyboard
    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    //Progress bar methods
    private void startAnim(){
        avi.setVisibility(View.VISIBLE);
        avi.smoothToShow();
    }

    private void stopAnim(){
        avi.setVisibility(View.GONE);

    }


}
