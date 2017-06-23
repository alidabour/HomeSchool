package com.example.ali.homeschool;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AliSecretActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        //firebaseUser.getEmail().toString()
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ali_secret);
    }
}
