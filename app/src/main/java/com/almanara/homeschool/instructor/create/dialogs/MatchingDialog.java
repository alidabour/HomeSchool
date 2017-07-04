package com.almanara.homeschool.instructor.create.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.almanara.ali.homeschool.R;
import com.almanara.homeschool.UserModelHelper.FileUploadHelper;
import com.almanara.homeschool.UserModelHelper.UploadFile;
import com.almanara.homeschool.instructor.create.OnQuestionLayoutReady;
import com.bumptech.glide.Glide;

import java.util.UUID;

/**
 * Created by Ali on 6/29/2017.
 */

public class MatchingDialog implements View.OnClickListener {
    Activity activity;

    final public int PICK_IMAGE_MATCHING = 239;
    int postion = 0;
    String[] uris = new String[4];
    private OnQuestionLayoutReady onQuestionLayoutReady;

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    String courseId;
    private final String HOLD = " ,HO##LD,";

    public MatchingDialog(Activity activity,
                          OnQuestionLayoutReady onQuestionLayoutReady) {
        this.activity = activity;
        this.onQuestionLayoutReady = onQuestionLayoutReady;
    }

    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;

    public void openMatchingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle((Html.fromHtml("<font color='#400684'>"+builder.getContext().getResources().getString(R.string.animation)+"</font>")));
        LayoutInflater li = LayoutInflater.from(activity);
        final RelativeLayout relativeLT = (RelativeLayout) li
                .inflate(R.layout.matching_dialog, null);

        imageView1 = (ImageView) relativeLT.findViewById(R.id.image1);
        imageView2 = (ImageView) relativeLT.findViewById(R.id.image2);
        imageView3 = (ImageView) relativeLT.findViewById(R.id.image3);
        imageView4 = (ImageView) relativeLT.findViewById(R.id.image4);
        imageView1.setOnClickListener(this);
        imageView2.setOnClickListener(this);
        imageView3.setOnClickListener(this);
        imageView4.setOnClickListener(this);
        builder.setView(relativeLT);
        builder.setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (checkifEmpty(uris)) {
                    return;
                } else {

                }
            }
        });
        final AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkifEmpty(uris)) {
                            Toast.makeText(activity, R.string.cancel_or_comple_data,
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            String layout = "";
                            layout += uris[0];
                            layout += HOLD;
                            layout += uris[1];
                            layout += HOLD;
                            layout += uris[2];
                            layout += HOLD;
                            layout += uris[3];
                            layout += HOLD;
                            onQuestionLayoutReady.onLayoutReady(layout);
                            dialog.dismiss();
                        }

                    }
                });

    }

    private boolean checkifEmpty(String... links) {
        for (String x : links) {
            if (x == null) {
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
                PICK_IMAGE_MATCHING
        );
    }

    public void setFilePath(Uri filePath) {
        String storagePath = "images/" + courseId + "/" + UUID.randomUUID();
        new UploadFile(filePath, activity, new FileUploadHelper() {
            @Override
            public void fileUploaded(String url) {
                uris[postion] = url;
                switch (postion) {
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
            }
        }, storagePath);
    }

}
