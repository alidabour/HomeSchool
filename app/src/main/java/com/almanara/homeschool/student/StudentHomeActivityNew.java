package com.almanara.homeschool.student;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.almanara.ali.homeschool.R;
import com.almanara.homeschool.login.Sign_In;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StudentHomeActivityNew extends AppCompatActivity {
    ViewPager viewPager ;
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_student_home);
        if( FirebaseAuth.getInstance().getCurrentUser() != null){
            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        }else {
            Intent intent = new Intent(this,Sign_In.class);
            intent.putExtra("userType","student");
            startActivity(intent);
            finish();
        }
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        StudentHomeAdapter studentHomeAdapter = new StudentHomeAdapter(getSupportFragmentManager());
        viewPager.setAdapter(studentHomeAdapter);
    }
}
