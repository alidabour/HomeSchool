package com.example.ali.homeschool.controller.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.ali.homeschool.InstructorHome.InstructorActivity;
import com.example.ali.homeschool.ParentHome.ParentActivity;
import com.example.ali.homeschool.R;
import com.example.ali.homeschool.childClass.LessonFragment;
import com.example.ali.homeschool.controller.activities.StudentHomeActivity;

/**
 * Created by manar_000 on 6/19/2017.
 */

public class ImageFragment extends android.support.v4.app.Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String image ;
    String mParam2 ;
    ImageView mImageView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {

            image = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            Log.v("signinAdapter ",image);

        }
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        Log.v("signinAdapterImage ",image);

        mImageView = new ImageView(getActivity());
       // inflater.inflate(R.layout.sign_in_as,mImageView);
        mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(getActivity()).load(image).into(mImageView);

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return mImageView ;
    }

    public static ImageFragment newInstance (String image ,String param2){
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1,image);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;

    }
}
