package com.example.ali.homeschool.InstructorTopic;

import android.view.View;

/**
 * Created by Ali on 6/8/2017.
 */

public interface XMLClick {
    void playSound(String url);
    void openActivity(String activity,String answer);
    void onImageClick(View imageView);
}
