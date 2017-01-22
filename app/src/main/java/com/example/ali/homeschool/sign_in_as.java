package com.example.ali.homeschool;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;

/**
 * Created by lenovo on 30/11/2016.
 */
public class sign_in_as extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_as);

        //ImageView imageView = (ImageView) findViewById(R.id.imageView);
        ImageButton Student = (ImageButton) findViewById(R.id.Student);
        ImageButton Parent = (ImageButton) findViewById(R.id.Parent);
        ImageButton Instructor = (ImageButton) findViewById(R.id.Instructor);


    }
}
