package com.almanara.homeschool;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.almanara.ali.homeschool.R;
import com.almanara.homeschool.controller.activities.Home;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by Ali on 7/2/2017.
 */

public class IndicatorViewPagerAdapter extends PagerAdapter {
    Context mContext;
    LayoutInflater mLayoutInflater;
    ArrayList<Integer> mResources;
    boolean isArabic= false;
    int size;
    public IndicatorViewPagerAdapter(Context context, ArrayList<Integer> mResources,int size,boolean isArabic) {
        mContext = context;
        this.mResources = mResources;
        mLayoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.size = size;
        this.isArabic = isArabic;
    }
    @Override
    public int getCount() {
        return size ;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.v("TestPager","Position :" + position);
        View itemView = mLayoutInflater.inflate(R.layout.indicatior_item, container, false);
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inTargetDensity = DisplayMetrics.DENSITY_DEFAULT;

        Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(),
                mResources.get(0), o);
        int w = bmp.getWidth();
        int h = bmp.getHeight();
        LinearLayout layout = (LinearLayout) itemView.findViewById(R.id.line);
        if(isArabic){
            for(int i =size-1; i>-1 ; i--){

                ImageView imageView = new ImageView(mContext);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.MATCH_PARENT,1.0f));
                if(i==position){

                    Glide.with(mContext).load(mResources.get(1)).override(w,h).into(imageView);
                }else {
                    Glide.with(mContext).load(mResources.get(0)).override(w,h).into(imageView);
                }

                layout.addView(imageView);
            }
        }else {
            for(int i =0; i<size ; i++){

                ImageView imageView = new ImageView(mContext);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.MATCH_PARENT,1.0f));
                if(i==position){
                    Glide.with(mContext).load(mResources.get(1)).override(w*2,h*2).into(imageView);
                }else {
                    Glide.with(mContext).load(mResources.get(0)).override(w,h).into(imageView);
                }

                layout.addView(imageView);
            }
        }

        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        ((ViewPager) container).removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((FrameLayout) object);
    }
}
