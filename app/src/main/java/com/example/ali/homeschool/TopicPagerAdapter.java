package com.example.ali.homeschool;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.ali.homeschool.TopicFragment;

import java.util.ArrayList;

/**
 * Created by Dabour on 11/26/2016.
 */

public class TopicPagerAdapter extends FragmentStatePagerAdapter {
    ArrayList<String> layouts ;
    public TopicPagerAdapter(FragmentManager fm,ArrayList<String> layouts) {
        super(fm);
        this.layouts=layouts;
    }

    @Override
    public Fragment getItem(int position) {
        return new TopicFragment().newInstance(layouts.get(position),"Test");
    }

    @Override
    public int getCount() {
        return layouts.size();
    }
}
