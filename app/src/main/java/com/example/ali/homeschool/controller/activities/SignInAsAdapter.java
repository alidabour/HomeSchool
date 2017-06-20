package com.example.ali.homeschool.controller.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.ali.homeschool.InstructorHome.InstructorActivity;
import com.example.ali.homeschool.ParentHome.ParentActivity;
import com.example.ali.homeschool.R;

import java.util.ArrayList;

/**
 * Created by Ali on 6/20/2017.
 */

public class SignInAsAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    ArrayList<Integer> mResources;

    public SignInAsAdapter(Context context, ArrayList<Integer> mResources) {
        mContext = context;
        this.mResources = mResources;
        mLayoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mResources.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inTargetDensity = DisplayMetrics.DENSITY_DEFAULT;
        Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(),
                mResources.get(position), o);
        int w = bmp.getWidth();
        int h = bmp.getHeight();
         Log.v("Size " , w + " " +h) ;
        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
       // imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(mContext).load(mResources.get(position)).override(w,h).into(imageView);
        //imageView.setImageResource(mResources.get(position));

      // Log.v("Size " , imageView.getHeight() + " " +imageView.getWidth()) ;
        container.addView(itemView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 0) {

                    Intent intent =new Intent(mContext, InstructorActivity.class);
                    intent.putExtra("FLAG_ACTIVITY_NEW_TASK" , true );
                    mContext.startActivity(intent);

                } else if (position == 1) {

                    Intent intent =new Intent(mContext, StudentHomeActivity.class);
                    intent.putExtra("FLAG_ACTIVITY_NEW_TASK" , true );
                    mContext.startActivity(intent);

                } else if (position == 2) {


                    Intent intent =new Intent(mContext, ParentActivity.class);
                    intent.putExtra("FLAG_ACTIVITY_NEW_TASK" , true );
                    mContext.startActivity(intent);

                }
            }
        });
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}
