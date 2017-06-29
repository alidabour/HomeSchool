package com.almanara.homeschool.instructor.create.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.almanara.homeschool.UserModelHelper.FileUploadHelper;
import com.almanara.homeschool.instructor.create.OnQuestionLayoutReady;
import com.almanara.homeschool.instructor.create.ProgressImage;
import com.bumptech.glide.Glide;
import com.almanara.ali.homeschool.R;
import com.almanara.homeschool.UserModelHelper.UploadFile;

import java.util.UUID;

/**
 * Created by Ali on 6/27/2017.
 */

public class AnimationDialog implements View.OnClickListener {
    Activity activity;

    final public int PICK_IMAGE_ANIMATION = 237;
    final public int PICK_SOUND_ANIMATION = 238;
    int postion = 0;
    String[] uris = new String[4];
    private OnQuestionLayoutReady onQuestionLayoutReady;
    ProgressImage progressImage;
    public void setProgressImage(
            ProgressImage progressImage) {
        this.progressImage = progressImage;
    }
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    String courseId;
    private final String HOLD = " ,HO##LD,";

    public AnimationDialog(Activity activity,
                           OnQuestionLayoutReady onQuestionLayoutReady) {
        this.activity = activity;
        this.onQuestionLayoutReady = onQuestionLayoutReady;
    }

    ImageView word;
    ImageView letter_image;
    ImageView word_sound;
    ImageView letter_sound;
    public void openAnimationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.animation);
        LayoutInflater li = LayoutInflater.from(activity);
        final RelativeLayout relativeLT = (RelativeLayout) li
                .inflate(R.layout.animation_dialog, null);

        word = (ImageView) relativeLT.findViewById(R.id.word);
        letter_image = (ImageView) relativeLT.findViewById(R.id.letter);
        word_sound = (ImageView) relativeLT.findViewById(R.id.sound1);
        letter_sound = (ImageView) relativeLT.findViewById(R.id.sound2);

        word.setOnClickListener(this);
        letter_image.setOnClickListener(this);
        word_sound.setOnClickListener(this);
        letter_sound.setOnClickListener(this);
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
                            progressImage.setImageOrSound(true,true);
                            Log.v("Animation " , layout);
                            onQuestionLayoutReady.onLayoutReady(layout);
                            dialog.dismiss();
                        }

                    }
                });

    }

    private boolean checkifEmpty(String... links) {
        for (String x : links) {
            if (x ==null) {
                Toast.makeText(activity,R.string.complete_data, Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return false;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.letter:
                postion = 0;
                openImageActivity();
                break;
            case R.id.word:
                postion =1;
                openImageActivity();
                break;
            case R.id.sound1:
                postion = 2;
                openSoundActivity();
                break;
            case R.id.sound2:
                postion = 3;
                openSoundActivity();
                break;
        }

    }

    private void openSoundActivity() {
        Intent intent = new Intent();
        intent.setType("audio/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(Intent.createChooser(intent, "Select Sound"),
                PICK_SOUND_ANIMATION);
    }

    private void openImageActivity() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                PICK_IMAGE_ANIMATION
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
                        Log.v("Animation ",uris[0]);
                        Glide.with(activity).load(uris[0]).into(letter_image);
                        break;
                    case 1:
                        Log.v("Animation ",uris[1]);
                        Glide.with(activity).load(uris[1]).into(word);
                        break;
                    case 2:
                    case 3:
                        break;

                }
            }
        }, storagePath);
    }

}
