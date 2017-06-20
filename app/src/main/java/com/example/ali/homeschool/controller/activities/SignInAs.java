package com.example.ali.homeschool.controller.activities;

import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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
        ArrayList<Integer> res = new ArrayList<>();
        res.add(R.drawable.instructor2);
        res.add(R.drawable.student2);
        res.add(R.drawable.parents2);
        pager.setAdapter(new SignInAsAdapter(this, res));
        Button next = (Button) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(getItem(+1), true); //getItem(-1) for previous

            }
        });
        Button previous = (Button) findViewById(R.id.previous);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(getItem(-1), true); //getItem(-1) for previous
            }
        });
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
