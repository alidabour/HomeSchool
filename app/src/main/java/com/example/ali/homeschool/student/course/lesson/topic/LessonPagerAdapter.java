package com.example.ali.homeschool.student.course.lesson.topic;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dabour on 11/26/2016.
 */

public class LessonPagerAdapter extends FragmentStatePagerAdapter {
    ArrayList<String> layouts ;
    List<Fragment> fragmentList;
    public LessonPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
//        this.layouts=layouts;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
