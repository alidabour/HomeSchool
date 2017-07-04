package com.almanara.homeschool.instructor.create.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.almanara.homeschool.instructor.create.OnLayoutReadyInterface;
import com.almanara.ali.homeschool.R;
import com.almanara.homeschool.UserModelHelper.FileUploadHelper;
import com.almanara.homeschool.UserModelHelper.UploadFile;
import com.almanara.homeschool.Constants;

import java.util.UUID;

/**
 * Created by Ali on 6/18/2017.
 */

public class ImageDialog extends MainDialog {
    private static final int PICK_IMAGE_REQUEST = 234;


    String courseId;
    String url;

    public ImageDialog(
            OnLayoutReadyInterface onLayoutReadyInterface) {
        super(onLayoutReadyInterface);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ImageDialog(Integer id, Activity activity,
                       OnLayoutReadyInterface onLayoutReadyInterface){
        super(id,activity,onLayoutReadyInterface);
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public void openImageDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(
                activity);
        builder.setTitle(R.string.choose_image);
        LayoutInflater li = LayoutInflater.from(activity);
        LinearLayout someLayout = (LinearLayout) li.inflate(R.layout.image_dialog, null);
        builder.setView(someLayout);
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
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
//        dialog.show();
        TextView gallery = (TextView) someLayout.findViewById(R.id.choosefromGallery);
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                openImageActivity();
            }
        });

        TextView urlTV = (TextView) someLayout.findViewById(R.id.imageUrl);
        urlTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                openImageURLDialog();
            }
        });
    }

    private void openImageActivity() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                PICK_IMAGE_REQUEST);
    }

    private void openImageURLDialog() {
        final EditText input = new EditText(activity);
        input.setHint(R.string.enter_url);
        input.setInputType(
                InputType.TYPE_CLASS_TEXT);
        input.setText(url);
        final AlertDialog.Builder urlBuilder = new AlertDialog.Builder(
                activity);
        urlBuilder.setTitle(R.string.choose_image);
        urlBuilder.setView(input);
        urlBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                url = input.getText().toString();
                String layout = Constants.mImageView(++id, url);
                if(!isEditing){
                    onLayoutReadyInterface.setLayout(layout);
                    progressImage.setImageOrSound(true,false);
                }else {
                    onEditLayoutReady.setLayoutAt(layout,index);
                }
            }
        });
        urlBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog dialog = urlBuilder.create();
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
//        urlBuilder.show();
    }

    public void setFilePath(Uri filePath) {
        String storagePath = "images/" + courseId + "/" + UUID.randomUUID();
        new UploadFile(filePath, activity, new FileUploadHelper() {
            @Override
            public void fileUploaded(String url) {
                String layout = Constants.mImageView(++id, url);
                if(!isEditing){
                    onLayoutReadyInterface.setLayout(layout);
                    progressImage.setImageOrSound(true,false);
                }else {
                    onEditLayoutReady.setLayoutAt(layout,index);
                }
                //and displaying a success toast
//                Toast.makeText(activity, R.string.file_uploaded,
//                        Toast.LENGTH_SHORT).show();
            }
        }, storagePath);
    }
}
