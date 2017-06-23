package com.example.ali.homeschool.studenthome;

import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.ali.homeschool.R;

public class StudentHomeActivityNew extends AppCompatActivity {
    ViewPager viewPager ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_student_home);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        StudentHomeAdapter studentHomeAdapter = new StudentHomeAdapter(getSupportFragmentManager());
        viewPager.setAdapter(studentHomeAdapter);
    }
}
