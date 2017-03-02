package com.example.ali.homeschool.controller.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.example.ali.homeschool.R;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {
TextView logoname;
    public FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        mAuth =FirebaseAuth.getInstance();
//        logoname = (TextView) findViewById(R.id.logoName);
//        logoname.setTypeface(Typeface.createFromAsset(getAssets(),"Amiri-Bold.ttf"));
        //ImageView imageView = (ImageView) findViewById(R.id.imageView);
        Button Signup = (Button) findViewById(R.id.Signin);

        Button Signin = (Button) findViewById(R.id.Register);

        Button Guest = (Button) findViewById(R.id.Guest);



        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getBaseContext() , Sign_In.class));

            }
        });

        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getBaseContext() , Register.class));

            }
        });
        Guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Guest.class);
                intent.putExtra("type",0);

                startActivity(intent);

            }
        });
    }
}
