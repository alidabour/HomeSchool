package com.example.ali.homeschool.InstructorTopic.CreationHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.ali.homeschool.InstructorTopic.InstructorTopicCreationActivity;
import com.example.ali.homeschool.R;

import edu.sfsu.cs.orange.ocr.Answer;

import static com.example.ali.homeschool.Constants.PUT_SOUND_LINK_HERE;
import static com.example.ali.homeschool.Constants.mButton;
import static com.example.ali.homeschool.Constants.mTextView;
import static com.example.ali.homeschool.Constants.setColorButton;
import static com.example.ali.homeschool.Constants.textViewProperties;

/**
 * Created by Ali on 6/18/2017.
 */

public class SpeechDialog extends MainTextDialog {

    public SpeechDialog(Integer id, Activity activity,
                        OnLayoutReadyInterface onLayoutReadyInterface) {
        super(id, activity, onLayoutReadyInterface);
    }
    public void openSpeechDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(
                activity);
        builder.setTitle("Questions :");
        LayoutInflater li = LayoutInflater.from(activity);
        final LinearLayout linearLayout = (LinearLayout) li
                .inflate(R.layout.speech_question_dialog, null);
        final EditText word = (EditText) linearLayout.findViewById(R.id.word);
        textViewProperties(linearLayout, activity, this);
        setColorButton(linearLayout, textColor);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.v("TESTAPP", "onClick :" + textAppearance);

                onLayoutReadyInterface.setLayout(mTextView(++id, "قول كلمة :", textColor, textAppearance));
                String textview = mTextView(++id, word.getText().toString(), textColor,
                        textAppearance);
                onLayoutReadyInterface.setLayout(textview);
                Answer answer = new Answer();
                answer.setAnswer(word.getText().toString());
                onLayoutReadyInterface.setLayout(mButton(++id, "Start", "Speech", answer, PUT_SOUND_LINK_HERE));
                dialogInterface.cancel();
            }
        });
        builder.setView(linearLayout);
        builder.show();
    }

}
