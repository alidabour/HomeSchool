package com.example.ali.homeschool.controller.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ali.homeschool.R;

/**
 * Created by lenovo on 30/11/2016.
 */
public class Sign_In extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        Button RegisterButton = (Button) findViewById(R.id.SigninButton);

        RegisterButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(),"You have Signed in Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getBaseContext() , SignInAs.class));
            }
        });


    }




}
