package com.example.ali.homeschool.controller.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ali.homeschool.R;
import com.example.ali.homeschool.data.InternetConnectionChecker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by lenovo on 30/11/2016.
 */

public class Sign_In extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "EmailPassword";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    //  private TextView Detail;
    private TextView textViewSignup ;
    private ProgressDialog progressDialog ;

    private EditText Email;
    private EditText Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);
        // Detail = (TextView) findViewById(R.id.Detail);

        Email = (EditText) findViewById(R.id.Email);
        Password = (EditText) findViewById(R.id.Password);
        textViewSignup = (TextView) findViewById(R.id.textViewSignUp);
        progressDialog = new ProgressDialog(this);

        textViewSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getBaseContext() , Register.class));

            }
        });
        mAuth = FirebaseAuth.getInstance();
        // Buttons


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    {
                        Intent intent = new Intent(getApplicationContext(), SignInAs.class);
                        startActivity(intent);
                    }
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
        findViewById(R.id.SigninButton).setOnClickListener(this);
        Log.e(TAG, "" + mAuth);

    }


    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }


        //   showProgressDialog();

        // [START sign_in_with_email]

        progressDialog.setMessage("Signin user ...");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(Email.getText().toString().trim(),Password.getText().toString().trim())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        InternetConnectionChecker internetConnectionChecker = new InternetConnectionChecker(getApplicationContext());
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                        if(task.isSuccessful())
                        {
                            Intent intent = new Intent(getApplicationContext(), SignInAs.class);
                            startActivity(intent);
                        }
                        else {
                            if (internetConnectionChecker.isInternetOn()) {
                                Log.w(TAG, "signInWithEmail:failed", task.getException());
                                Toast.makeText(Sign_In.this, "Wrong Username Or Password",
                                        Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Log.w(TAG, "signInWithEmail:failed", task.getException());
                                Toast.makeText(Sign_In.this, "Internet Connection Not Available",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.

                        // hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    private boolean validateForm() {
        boolean valid = true;

        String email = Email.getText().toString();
        if (TextUtils.isEmpty(email)) {
            Email.setError("Required.");
            valid = false;
        } else {
            Email.setError(null);
        }

        String password = Password.getText().toString();
        if (TextUtils.isEmpty(password)) {
            Password.setError("Required.");
            valid = false;
        } else {
            Password.setError(null);
        }

        return valid;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        // if (i == R.id.email_create_account_button) {
        //     createAccount(Email.getText().toString(), Password.getText().toString());
        //  } else
        if (i == R.id.SigninButton) {
            String s =Email.getText().toString().trim();
            String ss =Password.getText().toString().trim();
            signIn(s, ss);
//        } else if (i == R.id.sign_out_button) {
//            signOut();
//        }
        }

    }

}
