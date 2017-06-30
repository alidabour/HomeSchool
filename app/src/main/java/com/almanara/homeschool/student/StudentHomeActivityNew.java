package com.almanara.homeschool.student;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.almanara.ali.homeschool.R;
import com.almanara.homeschool.login.Sign_In;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StudentHomeActivityNew extends AppCompatActivity {
    ViewPager viewPager;
    FirebaseUser firebaseUser;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_student_home);
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        } else {
            Intent intent = new Intent(this, Sign_In.class);
            intent.putExtra("userType", "student");
            startActivity(intent);
            finish();
        }
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        StudentHomeAdapter studentHomeAdapter = new StudentHomeAdapter(getSupportFragmentManager());
        viewPager.setAdapter(studentHomeAdapter);



    }

    @Override
    protected void onStart() {
        super.onStart();
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.backgroundsound2);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();



        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }

        });

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
            }
        },2000);

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }
}
