package com.almanara.homeschool.login;

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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.almanara.homeschool.instructor.InstructorActivity;
import com.almanara.homeschool.parent.ParentActivity;
import com.almanara.ali.homeschool.R;
import com.almanara.homeschool.controller.activities.Home;
import com.almanara.homeschool.controller.activities.StudentHomeActivity;

import java.util.ArrayList;

/**
 * Created by Ali on 6/20/2017.
 */

public class SignInAsAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    ArrayList<Home> mResources;

    public SignInAsAdapter(Context context, ArrayList<Home> mResources) {
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
        return view == ((FrameLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inTargetDensity = DisplayMetrics.DENSITY_DEFAULT;
        Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(),
                mResources.get(position).getImage(), o);
        int w = bmp.getWidth();
        int h = bmp.getHeight();
         Log.v("Size " , w + " " +h) ;
        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        Glide.with(mContext).load(mResources.get(position).getImage()).override(w,h).into(imageView);
        TextView textView = (TextView) itemView.findViewById(R.id.textView);
        textView.setText(mResources.get(position).getName());
        container.addView(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 0) {

                    Intent intent =new Intent(mContext, StudentHomeActivity.class);
                    intent.putExtra("FLAG_ACTIVITY_NEW_TASK" , true );
                    mContext.startActivity(intent);

                } else if (position == 1) {

                    Intent intent =new Intent(mContext, InstructorActivity.class);
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
        container.removeView((FrameLayout) object);
    }
}
