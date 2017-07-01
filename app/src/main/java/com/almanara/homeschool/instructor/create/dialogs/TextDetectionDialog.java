package com.almanara.homeschool.instructor.create.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

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

public class TextDetectionDialog  {
    String selectlanguageString;
    Activity activity;
    private OnQuestionLayoutReady onQuestionLayoutReady;
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
    String courseId;
    ProgressImage progressImage;
    public void setProgressImage(
            ProgressImage progressImage) {
        this.progressImage = progressImage;
    }
    private final String HOLD = " ,HO##LD,";
    public TextDetectionDialog(Activity activity,
                        OnQuestionLayoutReady onQuestionLayoutReady) {
        this.activity = activity;
        this.onQuestionLayoutReady = onQuestionLayoutReady;
    }
    public void openTextDetectionDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(
                activity);
        builder.setTitle(R.string.question);
        LayoutInflater li = LayoutInflater.from(activity);
        final LinearLayout linearLayout = (LinearLayout) li
                .inflate(R.layout.text_detection_dialog, null);
        final EditText word = (EditText) linearLayout.findViewById(R.id.word);
//        final EditText questionStart = (EditText) linearLayout.findViewById(R.id.questionStart);
//        final Spinner selectLanguage = (Spinner) linearLayout.findViewById(R.id.textLan);

        word.setTextColor(ContextCompat.getColor(activity, R.color.colorPrimary));
//        ArrayAdapter<CharSequence> textSizes = ArrayAdapter
//                .createFromResource(activity,
//                        R.array.text_lan_array, android.R.layout.simple_spinner_item);

//        selectLanguage.setAdapter(textSizes);
//        selectLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                /*
//                *                         Do not translate
//                *
//                 */
//                if (i == 0) {
//                    selectlanguageString = "eng";
//                } else if (i == 1) {
//                    selectlanguageString = "ara";
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//            }
//        });
//        Constants.textViewProperties(linearLayout, activity, this);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                onLayoutReadyInterface.setLayout(Constants
//                        .mTextView(++id, questionStart.getText().toString().trim(), Constants.textColor,
//                        textAppearance));
//                onLayoutReadyInterface.setLayout( Constants
//                        .mTextView(++id, word.getText().toString().trim(), Constants.uniqueTextColor,
//                        textAppearance));
                if(word.getText().toString().isEmpty()){
                        Toast.makeText(activity, "ادخل كلمة", Toast.LENGTH_SHORT).show();
                }else {
                    if (word.getText().toString().trim().matches("[a-zA-Z]+")) {
                        selectlanguageString = "eng";
                    } else {
                        selectlanguageString = "ara";
                    }
                    Answer answer = new Answer(word.getText().toString().trim(),selectlanguageString);
String layout = "";
                    layout += word.getText().toString().trim();
                    layout += HOLD;
                    layout += selectlanguageString;
                    layout += HOLD;
                    onQuestionLayoutReady.onLayoutReady(layout);
                    progressImage.setImageOrSound(true,true);
                    dialogInterface.cancel();
                }
//                  onLayoutReadyInterface.setLayout( Constants.mButton(++id, "Start", "TextDetection",
//                        answer,
//                        Constants.PUT_SOUND_LINK_HERE));


            }
        });
        builder.setView(linearLayout);
        builder.show();


    }
}
