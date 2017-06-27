package com.example.ali.homeschool.InstructorTopic.CreationHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.ali.homeschool.Answer;
import com.example.ali.homeschool.InstructorTopic.InstructorTopicCreationActivity;
import com.example.ali.homeschool.R;


import static com.example.ali.homeschool.Constants.PUT_SOUND_LINK_HERE;
import static com.example.ali.homeschool.Constants.mButton;
import static com.example.ali.homeschool.Constants.textViewProperties;

/**
 * Created by Ali on 6/18/2017.
 */

public class ColorQuestionDialog extends MainTextDialog {
    String questionTitle;

    public ColorQuestionDialog(Integer id, Activity activity,
                               OnLayoutReadyInterface onLayoutReadyInterface) {
        super(id, activity, onLayoutReadyInterface);
    }

    public void openColorQuestionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//        builder.setTitle("Title");
        LayoutInflater li = LayoutInflater.from(activity);
        LinearLayout someLayout = (LinearLayout) li.inflate(R.layout.color_question_dialog, null);
        final EditText questionET = (EditText) someLayout.findViewById(R.id.question);
        questionET.setText(questionTitle);
        textViewProperties(someLayout, activity, this);
        Spinner colorSpinner = (Spinner) someLayout.findViewById(R.id.colors);
        ArrayAdapter<CharSequence> actionColors = ArrayAdapter.createFromResource(activity,
                R.array.color_array, android.R.layout.simple_spinner_item);
        actionColors.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        colorSpinner.setAdapter(actionColors);
        final String[] selection = {" "};
        colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position,
                                       long l) {
                selection[0] = (String) adapterView.getItemAtPosition(position);
                Log.v("ITA", "Selected :" + selection[0]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        builder.setView(someLayout);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String questionText = questionET.getText().toString();
                if (!isEditing) {
                    onLayoutReadyInterface.setLayout(
                            mButton(++id, questionText, "ColorActivity", new Answer(selection[0]),
                                    PUT_SOUND_LINK_HERE));
                    progressImage.setImageOrSound(true,true);
                }
                else {
                    onEditLayoutReady.setLayoutAt(mButton(++id, questionText, "ColorActivity", new Answer(selection[0]),
                            PUT_SOUND_LINK_HERE),index);
                }
                dialogInterface.cancel();
            }
        });
        builder.show();
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

}
