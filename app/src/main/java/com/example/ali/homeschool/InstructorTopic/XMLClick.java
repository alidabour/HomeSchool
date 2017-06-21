package com.example.ali.homeschool.InstructorTopic;

import android.view.View;

import edu.sfsu.cs.orange.ocr.Answer;

/**
 * Created by Ali on 6/8/2017.
 */

public interface XMLClick extends XMLClickParent{
    void playSound(String url);
    void openActivity(String activity, Answer answer);
    void onImageClick(View imageView);
    void onMultQuestionClicked(boolean isCorrect);
}
