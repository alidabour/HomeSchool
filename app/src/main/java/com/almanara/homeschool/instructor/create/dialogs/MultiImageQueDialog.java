package com.almanara.homeschool.instructor.create.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.almanara.homeschool.instructor.create.OnQuestionLayoutReady;
import com.almanara.ali.homeschool.R;
import com.almanara.homeschool.UserModelHelper.FileUploadHelper;
import com.almanara.homeschool.UserModelHelper.UploadFile;

import java.util.UUID;

/**
 * Created by Ali on 6/24/2017.
 */

public class MultiImageQueDialog  implements View.OnClickListener {
    Integer id;
    Activity activity;
    final public int PICK_IMAGE_MULTI_QUE = 236;
    int postion = 0;
    String[] uris = new String[4];
    String courseId;
    private final String HOLD  = " ,HO##LD,";
    private OnQuestionLayoutReady onQuestionLayoutReady;
    ImageView imageView1=null;
    ImageView imageView2=null;
    ImageView imageView3=null;
    ImageView imageView4=null;
    LinearLayout line1;
    LinearLayout line2;
    LinearLayout line3;
    LinearLayout line4;
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public MultiImageQueDialog(Integer id, Activity activity,
                               OnQuestionLayoutReady onQuestionLayoutReady) {
        this.activity = activity;
        this.id = id;
        this.onQuestionLayoutReady = onQuestionLayoutReady;
    }

    public void openMultiImageQueDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.question);
        LayoutInflater li = LayoutInflater.from(activity);
        final LinearLayout relativeLT = (LinearLayout) li
                .inflate(R.layout.mulit_image_question, null);
        final EditText question = (EditText) relativeLT.findViewById(R.id.question);

        imageView1 = (ImageView) relativeLT.findViewById(R.id.image1);
        imageView2 = (ImageView) relativeLT.findViewById(R.id.image2);
        imageView3 = (ImageView) relativeLT.findViewById(R.id.image3);
        imageView4 = (ImageView) relativeLT.findViewById(R.id.image4);

        line1 = (LinearLayout) relativeLT.findViewById(R.id.line1);
        line2 = (LinearLayout) relativeLT.findViewById(R.id.line2);
        line3 = (LinearLayout) relativeLT.findViewById(R.id.line3);
        line4 = (LinearLayout) relativeLT.findViewById(R.id.line4);
        final RadioButton radioButton1 = (RadioButton) relativeLT.findViewById(R.id.radioButton1);
        final RadioButton radioButton2 = (RadioButton) relativeLT.findViewById(R.id.radioButton2);
        final RadioButton radioButton3 = (RadioButton) relativeLT.findViewById(R.id.radioButton3);
        final RadioButton radioButton4 = (RadioButton) relativeLT.findViewById(R.id.radioButton4);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.radioButton1:
                        radioButton1.setChecked(false);
                        radioButton2.setChecked(false);
                        radioButton3.setChecked(false);
                        radioButton4.setChecked(false);
                        radioButton1.setChecked(true);
                        lineBackgroudColor();
                        line1.setBackgroundColor(Color.parseColor("#ff0000"));
                        break;
                    case R.id.radioButton2:
                        radioButton1.setChecked(false);
                        radioButton2.setChecked(false);
                        radioButton3.setChecked(false);
                        radioButton4.setChecked(false);
                        radioButton2.setChecked(true);
                        lineBackgroudColor();
                        line2.setBackgroundColor(Color.parseColor("#ff0000"));
                        break;
                    case R.id.radioButton3:
                        radioButton1.setChecked(false);
                        radioButton2.setChecked(false);
                        radioButton3.setChecked(false);
                        radioButton4.setChecked(false);
                        radioButton3.setChecked(true);
                        lineBackgroudColor();
                        line3.setBackgroundColor(Color.parseColor("#ff0000"));
                        break;
                    case R.id.radioButton4:
                        radioButton1.setChecked(false);
                        radioButton2.setChecked(false);
                        radioButton3.setChecked(false);
                        radioButton4.setChecked(false);
                        radioButton4.setChecked(true);
                        lineBackgroudColor();
                        line4.setBackgroundColor(Color.parseColor("#ff0000"));
                        break;
                }
            }
        };

        radioButton1.setOnClickListener(onClickListener);
        radioButton2.setOnClickListener(onClickListener);
        radioButton3.setOnClickListener(onClickListener);
        radioButton4.setOnClickListener(onClickListener);

        final EditText answer1 = (EditText) relativeLT.findViewById(R.id.answer1);
        final EditText answer2 = (EditText) relativeLT.findViewById(R.id.answer2);
        final EditText answer3 = (EditText) relativeLT.findViewById(R.id.answer3);
        final EditText answer4 = (EditText) relativeLT.findViewById(R.id.answer4);

        final Button submit = (Button) relativeLT.findViewById(R.id.submit);
        imageView1.setOnClickListener(this);
        imageView2.setOnClickListener(this);
        imageView3.setOnClickListener(this);
        imageView4.setOnClickListener(this);

        builder.setView(relativeLT);
//
        builder.setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(checkifAnswerEmpty(answer1,answer2,answer3,answer4,question)){
                    return;
                }else {

                }
            }
        });
        final AlertDialog dialog = builder.create();

//        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//        lp.copyFrom(dialog.getWindow().getAttributes());
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
//        dialog.getWindow().setAttributes(lp);
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            if(!checkifAnswerEmpty(answer1,answer2,answer3,answer4,question)) {
//                                Toast.makeText(activity, "Hiiiiiiii", Toast.LENGTH_SHORT).show();
                                String layout = "";
                                layout += question.getText().toString();
                                layout += HOLD;
                                layout += uris[0];
                                layout += HOLD;
                                layout += answer1.getText().toString();
                                layout += HOLD;
                                layout += radioButton1.isChecked();
                                layout += HOLD;
                                layout += uris[1];
                                layout += HOLD;
                                layout += answer2.getText().toString();
                                layout += HOLD;
                                layout += radioButton2.isChecked();
                                layout += HOLD;
                                layout += uris[2];
                                layout += HOLD;
                                layout += answer3.getText().toString();
                                layout += HOLD;
                                layout += radioButton3.isChecked();
                                layout += HOLD;
                                layout += uris[3];
                                layout += HOLD;
                                layout += answer4.getText().toString();
                                layout += HOLD;
                                layout += radioButton4.isChecked();
                                layout += HOLD;
                                onQuestionLayoutReady.onLayoutReady(layout);
                                Log.v("ImageQue", "Layout " + layout);
                                dialog.dismiss();
                            }else {
                                Toast.makeText(activity, R.string.cancel_or_comple_data, Toast.LENGTH_SHORT).show();
                            }
                    }
                });
    }

    private boolean checkifAnswerEmpty(EditText... editText) {
        for(EditText x:editText){
            if(x.getText().toString().isEmpty()){
                Toast.makeText(activity, R.string.complete_data, Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image1:
                postion = 0;
                openImageActivity();
                break;
            case R.id.image2:
                postion = 1;
                openImageActivity();
                break;
            case R.id.image3:
                postion = 2;
                openImageActivity();
                break;
            case R.id.image4:
                postion = 3;
                openImageActivity();
                break;
        }
    }

    private void openImageActivity() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                PICK_IMAGE_MULTI_QUE
        );
    }

    public void setFilePath(Uri filePath) {
        String storagePath = "images/" + courseId + "/" + UUID.randomUUID();
        new UploadFile(filePath, activity, new FileUploadHelper() {
            @Override
            public void fileUploaded(String url) {
                uris[postion] = url;
                switch (postion){
                    case 0:
                        Glide.with(activity).load(uris[0]).into(imageView1);
                        break;
                    case 1:
                        Glide.with(activity).load(uris[1]).into(imageView2);
                        break;
                    case 2:
                        Glide.with(activity).load(uris[2]).into(imageView3);
                        break;
                    case 3:
                        Glide.with(activity).load(uris[3]).into(imageView4);
                        break;
                }
                //and displaying a success toast
            }
        }, storagePath);
    }
     void lineBackgroudColor() {
         line1.setBackgroundColor(Color.parseColor("#ffffff"));
         line2.setBackgroundColor(Color.parseColor("#ffffff"));
         line3.setBackgroundColor(Color.parseColor("#ffffff"));
         line4.setBackgroundColor(Color.parseColor("#ffffff"));

     }
}
