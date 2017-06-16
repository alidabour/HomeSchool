package com.example.ali.homeschool.InstructorTopic;

import android.view.View;

import edu.sfsu.cs.orange.ocr.Answer;

/**
 * Created by Ali on 6/8/2017.
 */

public interface XMLClick {
    void playSound(String url);
    void openActivity(String activity, Answer answer);
    void onImageClick(View imageView);
}
