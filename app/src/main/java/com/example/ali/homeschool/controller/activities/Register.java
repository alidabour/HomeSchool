package com.example.ali.homeschool.controller.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ali.homeschool.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by lenovo on 30/11/2016.
 */
public class Register extends AppCompatActivity{
    private FirebaseAuth mAuth;
    private EditText email;
    private EditText password;
    private EditText repeated_password;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        email =(EditText) findViewById(R.id.EmailRegister) ;
        password =(EditText) findViewById(R.id.PasswordRegister) ;
        repeated_password =(EditText) findViewById(R.id.Repeated_PasswordRegister) ;
        Button RegisterButton = (Button) findViewById(R.id.RegisterButton);
        mAuth = FirebaseAuth.getInstance();
        RegisterButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                registering(email.getText().toString().trim(),password.getText().toString().trim(),repeated_password.getText().toString().trim());
            }
        });


    }
    private void registering(String Email,String Password , String Repeated_Password){
        if (!validateForm()) {
            return;
        }
        if(Password.equals(Repeated_Password)) {
            mAuth.createUserWithEmailAndPassword(Email, Password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("success", "");
                                finish();
                                startActivity(new Intent(getBaseContext(), SignInAs.class));
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("failed", "");
                                Toast.makeText(Register.this, "Email Either Created or Doesn't exist at all",
                                        Toast.LENGTH_SHORT).show();
                            }

                            // ...
                        }
                    });
        }
        else {
            Toast.makeText(this, "Passwords didn't match", Toast.LENGTH_SHORT).show();
            repeated_password.setError("Doesn't Match.");
        }


    }
    private boolean validateForm() {
        boolean valid = true;

        String emailString = email.getText().toString();
        if (TextUtils.isEmpty(emailString)) {
            email.setError("Required.");
            valid = false;
        } else {
            email.setError(null);
        }

        String passwordString = password.getText().toString();
        if (TextUtils.isEmpty(passwordString)) {
            password.setError("Required.");
            valid = false;
        } else {
            password.setError(null);
        }
        String repeatedPasswordString = repeated_password.getText().toString();
        if (TextUtils.isEmpty(repeatedPasswordString)) {
            repeated_password.setError("Doesn't Match");
            valid = false;
        } else {
            repeated_password.setError(null);
        }
        return valid;
    }


}
