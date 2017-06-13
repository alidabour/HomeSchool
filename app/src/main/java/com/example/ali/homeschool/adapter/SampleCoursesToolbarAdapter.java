package com.example.ali.homeschool.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.ali.homeschool.InstructorHome.CourseCreated;
import com.example.ali.homeschool.R;
import com.example.ali.homeschool.descriptionActivity.CourseDescriptionActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Ali on 1/23/2017.
 * it's use in the view baser
 */

public class SampleCoursesToolbarAdapter extends PagerAdapter {
    Context mContext;
    ArrayList<CourseCreated> random ;


    public SampleCoursesToolbarAdapter(Context mContext , ArrayList random ) {
        this.mContext = mContext;
        this.random = random ;
    }

    @Override
    public int getCount() {
        return random.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==((ImageView)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView mImageView  = new ImageView(mContext);
        mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(mContext).load(random.get(position).getPhoto_url()).into(mImageView);
        ((ViewPager) container).addView(mImageView,0);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,
                        CourseDescriptionActivity.class);
                intent.putExtra("course",random.get(position));
                intent.putExtra("FLAG_ACTIVITY_NEW_TASK" , true );
                mContext.startActivity(intent);
            }
        });
        return mImageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }
}
