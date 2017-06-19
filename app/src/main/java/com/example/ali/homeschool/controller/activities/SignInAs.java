package com.example.ali.homeschool.controller.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.ali.homeschool.InstructorHome.InstructorActivity;
import com.example.ali.homeschool.ParentHome.ParentActivity;
import com.example.ali.homeschool.R;
import com.example.ali.homeschool.adapter.SigninAdapter;

/**
 * Created by lenovo on 30/11/2016.
 */
public class SignInAs extends AppCompatActivity {

    ViewPager pager ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_as);
        //ImageView imageView = (ImageView) findViewById(R.id.imageView);
        pager = (ViewPager) findViewById(R.id.viewPager) ;
        Log.v("pager ",pager.toString());
        pager.setAdapter(new SigninAdapter(getSupportFragmentManager() , this));

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityCompat.finishAffinity(SignInAs.this);
      /*  Intent intent = new Intent(SignInAs.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.left_to_right,R.anim.right_to_left);*/
    }
}
