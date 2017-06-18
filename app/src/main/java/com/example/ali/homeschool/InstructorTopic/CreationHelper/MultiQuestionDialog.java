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
import static com.example.ali.homeschool.Constants.radioButton;
import static com.example.ali.homeschool.Constants.radioGroupEnd;
import static com.example.ali.homeschool.Constants.radioGroupStart;

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
        final EditText answer1 = (EditText) linearLayout.findViewById(R.id.answer1ET);
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
                LinearLayout la = (LinearLayout) linearLayout.getChildAt(0);
                TextInputLayout te = (TextInputLayout) la.getChildAt(0);
                FrameLayout et = (FrameLayout) te.getChildAt(0);
                android.support.v7.widget.AppCompatEditText om = (AppCompatEditText) et
                        .getChildAt(0);
                ArrayList<String> radioLayout = new ArrayList<String>();
//                String quET = actionTextXML.replaceAll(PU, String.valueOf(id));
//                quET = quET.replaceAll(PUTSIZEHERE, String.valueOf(30));
//                quET = quET.replaceAll(PUTACTIONTEXTHERE, question1.getText().toString());
//                quET = quET.replaceAll(PUTCOLOR, "123");
//                radioLayout.add(quET);
                radioLayout.add(radioGroupStart.replace(PUT_ID_HERE, String.valueOf(++id)));
//                addRadio(radioGroupStart.replace(PUT_ID_HERE,String.valueOf(++id)));

                for (int count = 0; count < linearLayout.getChildCount(); count++) {
                    LinearLayout lat = (LinearLayout) linearLayout.getChildAt(count);
                    TextInputLayout tet = (TextInputLayout) lat.getChildAt(0);
                    CheckBox checkbox = (CheckBox) lat.getChildAt(1);
                    Log.v("MultiQue", "Check Box Checked:" + checkbox.isChecked());
                    FrameLayout ett = (FrameLayout) tet.getChildAt(0);
                    android.support.v7.widget.AppCompatEditText omm = (AppCompatEditText) ett
                            .getChildAt(0);
                    Log.v("MultiQue", "Child 0 Edit text :" + omm.getText());
                    String r1 = radioButton
                            .replaceAll(PUT_ID_HERE, String.valueOf(++radioButtonId));
                    r1 = r1.replaceAll(PUT_ANSWER_HERE, omm.getText().toString());
                    Log.v("MultiQue", "radio " + r1);
                    radioLayout.add(r1);
                    // addLayout(r1);
                }
                radioLayout.add(radioGroupEnd);
//                addLayout(radioGroupEnd);
                //          addRadio(radioLayout.toString());
//                for(String x:radioLayout){
//                    Log.v("Parser","ITA Radio Layout :" + x);
//                }
                onLayoutReadyInterface.setLayout(radioLayout.toString());
            }
        });
        builder.setView(relativeLT);
        builder.show();
    }

}
