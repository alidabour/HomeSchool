package com.example.ali.homeschool.exercises.simple;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.ali.homeschool.Constants;
import com.example.ali.homeschool.R;

public class SimpleExercises extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_exercies);
        findViewById(R.id.end).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setData(Uri.parse("Goal"));
                setResult(Constants.WRONGANSWER,intent);
                finish();
            }
        });
    }
}
