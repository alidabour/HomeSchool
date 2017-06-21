package com.example.ali.homeschool.InstructorTopic.CreationHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ali.homeschool.R;

/**
 * Created by Ali on 6/18/2017.
 */

public class MainQuestionDialog extends MainDialog {
    int textColor = -11177216;

    SpeechDialog speechDialog;
    MultiQuestionDialog multiQuestionDialog;
    ColorQuestionDialog colorQuestionDialog;
    public MainQuestionDialog(Integer id, Activity activity,
                              OnLayoutReadyInterface onLayoutReadyInterface) {
        super(id, activity, onLayoutReadyInterface);
    }

    TextDetectionDialog textDetectionDialog;
    public void setTextColor(int textColor) {
        this.textColor = textColor;
        if (textDetectionDialog != null) {
            textDetectionDialog.setTextColor(textColor);
        }
        if (speechDialog != null) {
            speechDialog.setTextColor(textColor);
        }
        if (colorQuestionDialog != null) {
            colorQuestionDialog.setTextColor(textColor);
        }
    }
    public void openMainQuestionDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(
                activity);
        builder.setTitle("Choose type :");
        LayoutInflater li = LayoutInflater.from(activity);
        LinearLayout someLayout = (LinearLayout) li.inflate(R.layout.question_list, null);
        final TextView colorQue = (TextView) someLayout.findViewById(R.id.color);
        final TextView speechQue = (TextView) someLayout.findViewById(R.id.speech);
        final TextView multiQue = (TextView) someLayout.findViewById(R.id.multipleChoices);
        final TextView textDetection = (TextView) someLayout
                .findViewById(R.id.textDetection);

        builder.setView(someLayout);
        final AlertDialog dialog = builder.create();
        dialog.show();
        colorQue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                colorQuestionDialog = new ColorQuestionDialog(id,
                        activity, onLayoutReadyInterface);
                colorQuestionDialog.openColorQuestionDialog();

            }
        });
        speechQue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                speechDialog = new SpeechDialog(id, activity,
                        onLayoutReadyInterface);
                speechDialog.openSpeechDialog();

            }
        });
        multiQue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                multiQuestionDialog = new MultiQuestionDialog(id,
                        activity, onLayoutReadyInterface);
                multiQuestionDialog.openMultiQuestionDialog();
            }
        });

        textDetection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                textDetectionDialog = new TextDetectionDialog(id,
                        activity, onLayoutReadyInterface);
                textDetectionDialog.openTextDetectionDialog();
            }
        });

    }

}
