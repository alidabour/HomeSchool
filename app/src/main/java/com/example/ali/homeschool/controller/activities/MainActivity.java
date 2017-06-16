package com.example.ali.homeschool.controller.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ali.homeschool.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.ali.homeschool.R.id.Signin;


public class MainActivity extends AppCompatActivity {
TextView logoname;
    public FirebaseAuth mAuth;
    public FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);mAuth = FirebaseAuth.getInstance();
        // Buttons
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            // User is signed in
            {
                Intent intent = new Intent(getApplicationContext(), SignInAs.class);
                startActivity(intent);
            }
            Log.d("onAuthChang:signed_in:","");
        } else {
            // User is signed out
            Log.d("onAuthChang:signed_out","");
        }
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
                    Log.d("onAuthChang:signed_in:","");
                } else {
                    // User is signed out
                    Log.d("onAuthChang:signed_out","");
                }
                // ...
            }
        };
//        logoname = (TextView) findViewById(R.id.logoName);
//        logoname.setTypeface(Typeface.createFromAsset(getAssets(),"Amiri-Bold.ttf"));
        //ImageView imageView = (ImageView) findViewById(R.id.imageView);
        Button  signin = (Button) findViewById(Signin);

        Button register= (Button) findViewById(R.id.Register);

        Button guest = (Button) findViewById(R.id.Guest);



        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getBaseContext() , Sign_In.class));

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getBaseContext() , Register.class));

            }
        });
        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Guest.class);
                intent.putExtra("type",0);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
