package com.example.ali.homeschool.adabter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ali.homeschool.R;

import java.util.List;

/**
 * Created by Ali on 1/23/2017.
 * it's use in the view baser
 */

public class SampleCoursesToolbarAdapter extends PagerAdapter {
    Context mContext;
    private int[] sliderImagesId = new int[]{
            R.drawable.earlymath,R.drawable.ic_launcher,R.drawable.earlymath
    };
    public SampleCoursesToolbarAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return sliderImagesId.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==((ImageView)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView mImageView  = new ImageView(mContext);
        mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        mImageView.setImageResource(sliderImagesId[position]);
        ((ViewPager) container).addView(mImageView,0);
        return mImageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }
}
