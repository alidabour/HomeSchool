package com.almanara.homeschool.instructor.create.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.almanara.homeschool.instructor.create.OnLayoutReadyInterface;
import com.almanara.homeschool.instructor.create.xmlinterface.TextAppInterface;
import com.almanara.ali.homeschool.R;
import com.almanara.homeschool.Constants;


import static com.almanara.homeschool.Constants.mButton;

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
        builder.setTitle(R.string.text);
        LayoutInflater li = LayoutInflater.from(activity);
        final LinearLayout linearLayout = (LinearLayout) li
                .inflate(R.layout.text_view_dialog, null);
        final EditText word = (EditText) linearLayout.findViewById(R.id.text);
        word.setText(text);
        Constants.textViewProperties(linearLayout, activity, this);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(!isEditing){

                    onLayoutReadyInterface.setLayout(
                            Constants.mTextView(++id, word.getText().toString().trim(),
                                    textColor,
                                    textAppearance));
                }else {
                    onEditLayoutReady.setLayoutAt( Constants
                            .mTextView(++id, word.getText().toString().trim(),
                            textColor,
                            textAppearance),index);
                }
                dialogInterface.cancel();
            }
        });
        builder.setView(linearLayout);
        final AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                        .setTextColor(activity.getResources().getColor(R.color.parent));
                dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                        .setTextColor(activity.getResources().getColor(R.color.parent));
            }
        });
        dialog.show();
//        builder.show();

    }

}
