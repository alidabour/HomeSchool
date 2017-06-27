package com.example.ali.homeschool.instructor.create.xmlinterface;

import android.view.View;

import com.example.ali.homeschool.Answer;


/**
 * Created by Ali on 6/8/2017.
 */

public interface XMLClick extends XMLClickParent{
    void playSound(String url);
    void openActivity(String activity, Answer answer);
    void onImageClick(View imageView);
    void onMultQuestionClicked(boolean isCorrect);
}
