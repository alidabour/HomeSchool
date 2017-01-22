package com.example.ali.homeschool;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;


public class MainActivity extends AppCompatActivity {
 WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ImageView imageView = (ImageView) findViewById(R.id.imageView);
        Button Signup = (Button) findViewById(R.id.Signin);
       // GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(Signup);
       // Glide.with(this).load(R.drawable.hello).into(imageViewTarget);

        Button Signin = (Button) findViewById(R.id.Register);
       // GlideDrawableImageViewTarget imageViewTarget1 = new GlideDrawableImageViewTarget(Signin);
       // Glide.with(this).load(R.drawable.hello).into(imageViewTarget1);

        Button Guest = (Button) findViewById(R.id.Guest);
       // GlideDrawableImageViewTarget imageViewTarget2 = new GlideDrawableImageViewTarget(Guest);
       // Glide.with(this).load(R.drawable.hello).into(imageViewTarget2);


        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getBaseContext() , sign_up.class));

            }
        });

        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getBaseContext() , sign_in.class));

            }
        });
        Guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getBaseContext() ,HomeActivity.class));

            }
        });
    }
}
