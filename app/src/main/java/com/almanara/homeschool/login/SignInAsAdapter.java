package com.almanara.homeschool.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.almanara.homeschool.controller.activities.Utility;
import com.almanara.homeschool.student.StudentHomeActivityNew;
import com.almanara.homeschool.student.course.lesson.topic.ClassActivity;
import com.bumptech.glide.Glide;
import com.almanara.homeschool.instructor.InstructorActivity;
import com.almanara.homeschool.parent.ParentActivity;
import com.almanara.ali.homeschool.R;
import com.almanara.homeschool.controller.activities.Home;
import com.almanara.homeschool.controller.activities.StudentHomeActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static android.R.drawable.title_bar;

/**
 * Created by Ali on 6/20/2017.
 */

public class SignInAsAdapter extends PagerAdapter {
    Activity activity;
    //    Context mContext;
    LayoutInflater mLayoutInflater;
    ArrayList<Home> mResources;

    public SignInAsAdapter(Activity activity, ArrayList<Home> mResources) {
//        mContext = context;
        this.activity = activity;
        this.mResources = mResources;
//        Log.v("SignInAsAdapter","MRes : 1:" + mResources.get(0).getName() +" 2 :" +mResources.get(1).getName()+ " 3  :" +mResources.get(2).getName() );
        mLayoutInflater = (LayoutInflater) activity
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
        Bitmap bmp = BitmapFactory.decodeResource(activity.getResources(),
                mResources.get(position).getImage(), o);
        int w = bmp.getWidth();
        int h = bmp.getHeight();
        Log.v("Size ", w + " " + h);
        Log.v("Size ", "mRes :" + mResources.get(position).getName());

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        Glide.with(activity).load(mResources.get(position).getImage()).override(w, h)
                .into(imageView);
        TextView textView = (TextView) itemView.findViewById(R.id.textView);
        textView.setText(mResources.get(position).getName());
        ImageView layout = (ImageView) itemView.findViewById(R.id.framePager);
        Glide.with(activity).load(mResources.get(position).getBackGround()).into(layout);
        TextView slogan = (TextView) itemView.findViewById(R.id.slogan);
        slogan.setText(mResources.get(position).getSlogan());
        TextView description = (TextView) itemView.findViewById(R.id.description);
        description.setText(mResources.get(position).getDescription());
        container.addView(itemView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mResources.get(position).getName()
                        .equals(activity.getString(R.string.Parents))) {

                    Intent intent = new Intent(activity, ParentActivity.class);
                    intent.putExtra("FLAG_ACTIVITY_NEW_TASK", true);
                    activity.startActivity(intent);
                    Utility.setTheme(activity, 1);
                    // ((SignInAs)mContext).recreateActivity();


                } else if (mResources.get(position).getName()
                        .equals(activity.getString(R.string.Instructor))) {


                    Intent intent = new Intent(activity, InstructorActivity.class);
                    intent.putExtra("FLAG_ACTIVITY_NEW_TASK", true);
                    activity.startActivity(intent);
                    Utility.setTheme(activity, 2);
                    // ((SignInAs)mContext).recreateActivity();

                } else if (mResources.get(position).getName()
                        .equals(activity.getString(R.string.Student))) {

                    Intent intent = new Intent(activity, StudentHomeActivityNew.class);
                    intent.putExtra("FLAG_ACTIVITY_NEW_TASK", true);
                    activity.startActivity(intent);
                    activity.finish();


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
