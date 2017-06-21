package com.example.ali.homeschool.InstructorTopic.CreationHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.ali.homeschool.InstructorTopic.InstructorTopicCreationActivity;
import com.example.ali.homeschool.R;

import java.util.ArrayList;

import static com.example.ali.homeschool.Constants.PUT_ANSWER_HERE;
import static com.example.ali.homeschool.Constants.PUT_ID_HERE;
import static com.example.ali.homeschool.Constants.mTextView;
import static com.example.ali.homeschool.Constants.radioButton;
import static com.example.ali.homeschool.Constants.radioGroupEnd;
import static com.example.ali.homeschool.Constants.radioGroupStart;
import static com.example.ali.homeschool.Constants.textAppearance;

/**
 * Created by Ali on 6/18/2017.
 */

public class MultiQuestionDialog  extends  MainDialog{
    int radioButtonId = 0;

    public MultiQuestionDialog(Integer id, Activity activity,
                               OnLayoutReadyInterface onLayoutReadyInterface) {
        super(id, activity, onLayoutReadyInterface);
    }

    public void openMultiQuestionDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Questions :");
        LayoutInflater li = LayoutInflater.from(activity);
        final RelativeLayout relativeLT = (RelativeLayout) li
                .inflate(R.layout.mulit_choice_dialog, null);
        final EditText question1 = (EditText) relativeLT.findViewById(R.id.questionText);
        final LinearLayout linearLayout = (LinearLayout) relativeLT.findViewById(R.id.questions);
        FloatingActionButton addQuestion = (FloatingActionButton) relativeLT
                .findViewById(R.id.addQuestion);
        addQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(activity);
                LinearLayout question = (LinearLayout) li.inflate(R.layout.question_layout, null);
                linearLayout.addView(question);
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ArrayList<String> radioLayout = new ArrayList<String>();
               String radioStart = radioGroupStart.replace(PUT_ID_HERE, String.valueOf(++id));

                for (int count = 0; count < linearLayout.getChildCount(); count++) {
                    LinearLayout lat = (LinearLayout) linearLayout.getChildAt(count);
                    TextInputLayout tet = (TextInputLayout) lat.getChildAt(0);
                    CheckBox checkbox = (CheckBox) lat.getChildAt(1);
                    Log.v("MultiQue", "Check Box Checked:" + checkbox.isChecked());

                    FrameLayout ett = (FrameLayout) tet.getChildAt(0);
                    android.support.v7.widget.AppCompatEditText omm = (AppCompatEditText) ett
                                .getChildAt(0);
                    String r1 = radioButton
                            .replaceAll(PUT_ID_HERE, String.valueOf(++id));
                    r1 = r1.replaceAll(PUT_ANSWER_HERE, omm.getText().toString());
                    radioLayout.add(r1);
                    if(checkbox.isChecked()){
                        radioStart = radioStart.replace(PUT_ANSWER_HERE,String.valueOf(id));
                    }
                }
                radioLayout.add(radioGroupEnd);
                String groupLayout= "";
                for(String x:radioLayout){
                    groupLayout += x;
                }
                onLayoutReadyInterface.setLayout(mTextView(++id,question1.getText().toString(),-11177216,textAppearance[2]));
                onLayoutReadyInterface.setLayout(radioStart +groupLayout);
            }
        });
        builder.setView(relativeLT);
        builder.show();
    }

}
