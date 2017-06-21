package com.example.ali.homeschool.InstructorTopic.CreationHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.ali.homeschool.Constants;
import com.example.ali.homeschool.InstructorTopic.TextAppInterface;
import com.example.ali.homeschool.R;

import edu.sfsu.cs.orange.ocr.Answer;

import static com.example.ali.homeschool.Constants.PUT_SOUND_LINK_HERE;
import static com.example.ali.homeschool.Constants.mButton;
import static com.example.ali.homeschool.Constants.mTextView;
import static com.example.ali.homeschool.Constants.textViewProperties;

/**
 * Created by Ali on 6/21/2017.
 */

public class TextViewDialog extends MainTextDialog implements TextAppInterface {
    public String text;

    public TextViewDialog(Integer id, Activity activity,
                          OnLayoutReadyInterface onLayoutReadyInterface) {
        super(id, activity, onLayoutReadyInterface);
    }

    public void setText(String text) {
        this.text = text;
    }

    public void openTextViewDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(
                activity);
        builder.setTitle("TextView :");
        LayoutInflater li = LayoutInflater.from(activity);
        final LinearLayout linearLayout = (LinearLayout) li
                .inflate(R.layout.text_view_dialog, null);
        final EditText word = (EditText) linearLayout.findViewById(R.id.text);
        word.setText(text);
        textViewProperties(linearLayout, activity, this);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(!isEditing){

                    onLayoutReadyInterface.setLayout(
                            mTextView(++id, word.getText().toString().trim(),
                                    textColor,
                                    textAppearance));
                }else {
                    onEditLayoutReady.setLayoutAt( mTextView(++id, word.getText().toString().trim(),
                            textColor,
                            textAppearance),index);
                }
                dialogInterface.cancel();
            }
        });
        builder.setView(linearLayout);
        builder.show();

    }

}
