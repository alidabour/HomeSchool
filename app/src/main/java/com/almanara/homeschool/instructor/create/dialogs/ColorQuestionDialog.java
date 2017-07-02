package com.almanara.homeschool.instructor.create.dialogs;

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
import android.widget.Toast;

import com.almanara.homeschool.Answer;
import com.almanara.homeschool.instructor.create.OnLayoutReadyInterface;
import com.almanara.ali.homeschool.R;
import com.almanara.homeschool.Constants;
import com.almanara.homeschool.instructor.create.OnQuestionLayoutReady;
import com.almanara.homeschool.instructor.create.ProgressImage;


import static com.almanara.homeschool.Constants.mButton;

/**
 * Created by Ali on 6/18/2017.
 */

public class ColorQuestionDialog {
    //    String questionTitle;
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

    public ColorQuestionDialog(Activity activity,
                               OnQuestionLayoutReady onQuestionLayoutReady) {
        this.activity = activity;
        this.onQuestionLayoutReady = onQuestionLayoutReady;
    }


    public void openColorQuestionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//        builder.setTitle("Title");
        LayoutInflater li = LayoutInflater.from(activity);
        LinearLayout someLayout = (LinearLayout) li.inflate(R.layout.color_question_dialog, null);
        final EditText questionET = (EditText) someLayout.findViewById(R.id.question);
//        questionET.setText(questionTitle);
//        Constants.textViewProperties(someLayout, activity, this);
//        Spinner colorSpinner = (Spinner) someLayout.findViewById(R.id.colors);
//        ArrayAdapter<CharSequence> actionColors = ArrayAdapter.createFromResource(activity,
//                R.array.color_array, android.R.layout.simple_spinner_item);
//        actionColors.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
//        colorSpinner.setAdapter(actionColors);
//        final String[] selection = {" "};
//        colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int position,
//                                       long l) {
//                selection[0] = (String) adapterView.getItemAtPosition(position);
//                Log.v("ITA", "Selected :" + selection[0]);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
        builder.setView(someLayout);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String questionText = questionET.getText().toString();
//                if (!isEditing) {
//                    onLayoutReadyInterface.setLayout(
//                            Constants.mButton(++id, questionText, "ColorActivity", new Answer(selection[0]),
//                                    Constants.PUT_SOUND_LINK_HERE));
//                    progressImage.setImageOrSound(true,true);
//                }
//                else {
//                    onEditLayoutReady.setLayoutAt(
//                            Constants.mButton(++id, questionText, "ColorActivity", new Answer(selection[0]),
//                            Constants.PUT_SOUND_LINK_HERE),index);
//                }
                if (!questionET.getText().toString().trim().isEmpty()) {
                    String layout = "";
                    layout += questionET.getText().toString().trim();
                    layout += HOLD;
                    progressImage.setImageOrSound(true, true);
                    onQuestionLayoutReady.onLayoutReady(layout);
//                    progressImage.setImageOrSound(true,true);
                    dialogInterface.cancel();
                } else {
                    Toast.makeText(activity, R.string.enter_word, Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.show();
    }

//    public void setQuestionTitle(String questionTitle) {
//        this.questionTitle = questionTitle;
//    }

}
