package com.example.ali.homeschool.adabter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.ali.homeschool.controller.fragments.LessonFragment;

import java.util.ArrayList;

/**
 * Created by Dabour on 11/26/2016.
 */

public class LessonPagerAdapter extends FragmentStatePagerAdapter {
    ArrayList<String> layouts ;
    public LessonPagerAdapter(FragmentManager fm, ArrayList<String> layouts) {
        super(fm);
        this.layouts=layouts;
    }

    @Override
    public Fragment getItem(int position) {
        return new LessonFragment().newInstance(layouts.get(position),"Test");
    }

    @Override
    public int getCount() {
        return layouts.size();
    }
}
