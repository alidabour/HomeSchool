package com.almanara.homeschool.instructor.create.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.almanara.homeschool.Answer;
import com.almanara.homeschool.Constants;
import com.almanara.homeschool.instructor.create.OnLayoutReadyInterface;
import com.almanara.ali.homeschool.R;
import com.almanara.homeschool.instructor.create.OnQuestionLayoutReady;
import com.almanara.homeschool.instructor.create.ProgressImage;


import static com.almanara.homeschool.Constants.mButton;

/**
 * Created by Ali on 6/18/2017.
 */

public class SpeechDialog {
    Activity activity;
    ProgressImage progressImage;

    private OnQuestionLayoutReady onQuestionLayoutReady;
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    String courseId;
    private final String HOLD = " ,HO##LD,";

    public void openSpeechDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(
                activity);
        builder.setTitle(R.string.question);
        LayoutInflater li = LayoutInflater.from(activity);
        final LinearLayout linearLayout = (LinearLayout) li
                .inflate(R.layout.speech_question_dialog, null);
        final EditText word = (EditText) linearLayout.findViewById(R.id.word);
        if(!word.getText().toString().trim().isEmpty()){

        }
//        Constants.textViewProperties(linearLayout, activity, this);
//        Constants.setColorButton(linearLayout, textColor);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                Log.v("TESTAPP", "onClick :" + textAppearance);

//                onLayoutReadyInterface.setLayout(
//                        Constants.mTextView(++id, "قول كلمة :", Constants.textColor, textAppearance));
//                String textview = Constants.mTextView(++id, word.getText().toString(), Constants.uniqueTextColor,
//                        textAppearance);
//                onLayoutReadyInterface.setLayout(textview);
//                Answer answer = new Answer();
//                answer.setAnswer(word.getText().toString());
                /*
                *               Hi please do not
                *                       translate word "" Speech ""
                *               Thanks,
                *               Ali.
                *
                 */
//                onLayoutReadyInterface.setLayout(Constants
//                        .mButton(++id, activity.getString(R.string.start), "Speech", answer, Constants.PUT_SOUND_LINK_HERE));
                progressImage.setImageOrSound(true,true);
                dialogInterface.cancel();
            }
        });
        builder.setView(linearLayout);
        builder.show();
    }

}
