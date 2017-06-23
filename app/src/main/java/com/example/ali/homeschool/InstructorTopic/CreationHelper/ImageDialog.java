package com.example.ali.homeschool.InstructorTopic.CreationHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ali.homeschool.InstructorTopic.InstructorTopicCreationActivity;
import com.example.ali.homeschool.R;
import com.example.ali.homeschool.UserModelHelper.FileUploadHelper;
import com.example.ali.homeschool.UserModelHelper.UploadFile;

import java.util.UUID;

import static com.example.ali.homeschool.Constants.mImageView;

/**
 * Created by Ali on 6/18/2017.
 */

public class ImageDialog extends MainDialog {
    private static final int PICK_IMAGE_REQUEST = 234;

    private ProgressImage progressImage;

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
    public void setProgressImage(
            ProgressImage progressImage) {
        this.progressImage = progressImage;
    }
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public void openImageDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(
                activity);
        builder.setTitle("Select image");
        LayoutInflater li = LayoutInflater.from(activity);
        LinearLayout someLayout = (LinearLayout) li.inflate(R.layout.image_dialog, null);
        builder.setView(someLayout);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog dialog = builder.create();
        dialog.show();
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
        input.setInputType(
                InputType.TYPE_CLASS_TEXT);
        input.setText(url);
        final AlertDialog.Builder urlBuilder = new AlertDialog.Builder(
                activity);
        urlBuilder.setTitle("Title");
        urlBuilder.setView(input);
        urlBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                url = input.getText().toString();
                String layout = mImageView(++id, url);
                if(!isEditing){
                    onLayoutReadyInterface.setLayout(layout);
                    progressImage.setImageOrSound(true);
                }else {
                    onEditLayoutReady.setLayoutAt(layout,index);
                }
            }
        });
        urlBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        urlBuilder.show();
    }

    public void setFilePath(Uri filePath) {
        String storagePath = "images/" + courseId + "/" + UUID.randomUUID();
        new UploadFile(filePath, activity, new FileUploadHelper() {
            @Override
            public void fileUploaded(String url) {
                String layout = mImageView(++id, url);
                if(!isEditing){
                    onLayoutReadyInterface.setLayout(layout);
                    progressImage.setImageOrSound(true);
                }else {
                    onEditLayoutReady.setLayoutAt(layout,index);
                }
                //and displaying a success toast
                Toast.makeText(activity, "File Uploaded",
                        Toast.LENGTH_LONG).show();
            }
        }, storagePath);
    }
}
