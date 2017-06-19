package com.example.ali.homeschool.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.ali.homeschool.InstructorHome.InstructorActivity;
import com.example.ali.homeschool.ParentHome.ParentActivity;
import com.example.ali.homeschool.controller.activities.StudentHomeActivity;
import com.example.ali.homeschool.controller.fragments.ImageFragment;
import com.example.ali.homeschool.descriptionActivity.CourseDescriptionActivity;

import java.util.ArrayList;

/**
 * Created by manar_000 on 6/19/2017.
 */

public class SigninAdapter extends FragmentStatePagerAdapter {

    Context mContext ;
    ArrayList<String> random;

    public SigninAdapter(FragmentManager fm, Context mContext){
        super(fm);

        this.mContext=mContext;
        random = new ArrayList<String>();
        Log.v("signinAdapter ",random.toString());
        random.add("@drawable/instructor2");
        random.add("@drawable/parents2");
        random.add("@drawable/student2");
        Log.v("signinAdapter ",random.toString());

    }

    @Override
    public Fragment getItem(int position) {
        return  new ImageFragment().newInstance(random.get(position),"Test");
    }

    @Override
    public int getCount() {
        return 0;
    }
}
