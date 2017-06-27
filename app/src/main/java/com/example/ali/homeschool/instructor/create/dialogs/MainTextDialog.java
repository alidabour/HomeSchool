package com.example.ali.homeschool.instructor.create.dialogs;

import android.app.Activity;

import com.example.ali.homeschool.Constants;
import com.example.ali.homeschool.instructor.create.OnLayoutReadyInterface;
import com.example.ali.homeschool.instructor.create.xmlinterface.TextAppInterface;

/**
 * Created by Ali on 6/18/2017.
 */

public class MainTextDialog extends MainDialog implements TextAppInterface {
    int textColor = -11177216;
    int textAppearance = android.R.style.TextAppearance_Material_Body1;

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }
    public MainTextDialog(Integer id, Activity activity,
                          OnLayoutReadyInterface onLayoutReadyInterface) {
        super(id, activity, onLayoutReadyInterface);
    }

    @Override
    public void onSelected(int i) {
        textAppearance = Constants.textAppearance[i];

    }
}
