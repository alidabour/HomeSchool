package com.example.ali.homeschool.controller.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.example.ali.homeschool.InstructorHome.InstructorActivity;
import com.example.ali.homeschool.R;

/**
 * Created by lenovo on 30/11/2016.
 */
public class SignInAs extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_as);

        //ImageView imageView = (ImageView) findViewById(R.id.imageView);
        ImageButton student = (ImageButton) findViewById(R.id.Student);
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),StudentHomeActivity.class);
                intent.putExtra("type",1);

                startActivity(intent);
            }
        });
        ImageButton Parent = (ImageButton) findViewById(R.id.Parent);
        Parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ParentActivity.class);
                intent.putExtra("type",1);
                startActivity(intent);
            }
        });
        final ImageButton instructor = (ImageButton) findViewById(R.id.Instructor);
        instructor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InstructorActivity.class);
                startActivity(intent);
            }
        });


    }
}
