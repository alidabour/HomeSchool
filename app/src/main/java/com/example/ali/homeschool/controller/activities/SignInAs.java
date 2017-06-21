package com.example.ali.homeschool.controller.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.ali.homeschool.R;

import java.util.ArrayList;

/**
 * Created by lenovo on 30/11/2016.
 */
public class SignInAs extends AppCompatActivity {

    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_as);
        //ImageView imageView = (ImageView) findViewById(R.id.imageView);
        pager = (ViewPager) findViewById(R.id.viewPager);
        Log.v("pager ", pager.toString());
        ArrayList<Home> res = new ArrayList<>();
        Home home = new Home() ;
        home.setImage(R.drawable.instructor_icon);
        home.setName(getString(R.string.Instructor));
        res.add(home);
        home = new Home();
        home.setImage(R.drawable.student_icon);
        home.setName(getString(R.string.Student));
        res.add(home);
        home = new Home();
        home.setImage(R.drawable.parents_icon);
        home.setName(getString(R.string.Parents));
        res.add(home);
        pager.setAdapter(new SignInAsAdapter(this, res));
        ImageView next = (ImageView) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(getItem(+1), true); //getItem(-1) for previous

            }
        });
        ImageView previous = (ImageView) findViewById(R.id.previous);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(getItem(-1), true); //getItem(-1) for previous
            }
        });
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inTargetDensity = DisplayMetrics.DENSITY_DEFAULT;
        Bitmap bmp = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                R.drawable.arrow_2, o);
        int w = bmp.getWidth();
        int h = bmp.getHeight();
        Bitmap bmp2 = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                R.drawable.arrow_1, o);
        int w2 = bmp2.getWidth();
        int h2 = bmp2.getHeight();
        Glide.with(getApplicationContext()).load(R.drawable.arrow_2).override(w,h).into(next);
        Glide.with(getApplicationContext()).load(R.drawable.arrow_1).override(w2,h2).into(previous);

    }

    private int getItem(int i) {
        return pager.getCurrentItem() + i;
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
