package com.example.ali.homeschool.controller.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ali.homeschool.R;
import com.example.ali.homeschool.data.firebase.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.UUID;

/**
 * Created by lenovo on 30/11/2016.
 */

public class Register extends AppCompatActivity{
    FirebaseAuth mAuth;
    private EditText email;
    private EditText password;
    private EditText repeated_password;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    private static final int PICK_IMAGE_REQUEST = 234;
    private Uri filePath;
    FirebaseUser user;
    UserModel newUser ;
    static Context context;
    ImageView photoImageView;
    String photoString;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        email =(EditText) findViewById(R.id.EmailRegister) ;
        password =(EditText) findViewById(R.id.PasswordRegister) ;
        repeated_password =(EditText) findViewById(R.id.Repeated_PasswordRegister) ;
        photoImageView = (ImageView) findViewById (R.id.PhotoimageView);
        Button RegisterButton = (Button) findViewById(R.id.RegisterButton);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        Button uploadPhotoButton = (Button) findViewById(R.id.UploadButton);

        uploadPhotoButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                openImageActivity();

            }
        });


        RegisterButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                registering(email.getText().toString().trim(),password.getText().toString().trim(),repeated_password.getText().toString().trim());
            }
        });
    }


    private void openImageActivity(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_IMAGE_REQUEST) {
                Log.v("Instructor",
                        "Req : " + requestCode + " Res :" + resultCode + " Intent : " + data
                                .getData().toString());
                filePath = data.getData();
                uploadFile();
            }
        }

    }
    private void registering(String Email,String Password , String Repeated_Password){
        if (!validateForm()) {
            return;
        }
        if(Password.equals(Repeated_Password)) {
            mAuth.createUserWithEmailAndPassword(Email, Password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("success", "");
                                DatabaseReference myRef = databaseReference;
                                newUser = new UserModel();
                                newUser.setEmail(email.getText().toString().trim());
                                newUser.setUid(user.getUid());
                                newUser.setName("b3den xD ");
                                newUser.setPhoto(photoString);
                                myRef.child("users").child(user.getUid()).setValue(newUser);
                                finish();
                                startActivity(new Intent(getBaseContext(), SignInAs.class));
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("failed", "");
                                Toast.makeText(Register.this, "Email Either Created or Doesn't exist at all",
                                        Toast.LENGTH_SHORT).show();
                            }

                            // ...
                        }
                    });
        }
        else {
            Toast.makeText(this, "Passwords didn't match", Toast.LENGTH_SHORT).show();
            repeated_password.setError("Doesn't Match.");
        }


    }
    private boolean validateForm() {
        boolean valid = true;

        String emailString = email.getText().toString();
        if (TextUtils.isEmpty(emailString)) {
            email.setError("Required.");
            valid = false;
        } else {
            email.setError(null);
        }

        String passwordString = password.getText().toString();
        if (TextUtils.isEmpty(passwordString)) {
            password.setError("Required.");
            valid = false;
        } else {
            password.setError(null);
        }
        String repeatedPasswordString = repeated_password.getText().toString();
        if (TextUtils.isEmpty(repeatedPasswordString)) {
            repeated_password.setError("Doesn't Match");
            valid = false;
        } else {
            repeated_password.setError(null);
        }
        return valid;
    }


    private void uploadFile() {
        //if there is a file to upload
        if (filePath != null) {
            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();
            Log.v("ITA", "Type: " + getMimeType(getApplicationContext(), filePath));
            Log.v("ITA", "FileName: " + getFileName(getApplicationContext(), filePath));

            StorageReference riversRef = storageReference.
                    child("images/" + "courses" + "/" + UUID.randomUUID() + getFileName(
                            getApplicationContext(), filePath));
            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //if the upload is successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();
                            @SuppressWarnings("VisibleForTests") String link = taskSnapshot
                                    .getDownloadUrl().toString();
                            link = link.replaceAll("&", "&amp;");
////                            link = link.replaceAll("&#63;", "?");
//                            mid = "<ImageView android:layout_weight=\"1\" android:id=\"" +id
//                                    + "\" android:layout_width=\"match_parent\"" +
//                                    " android:layout_height=\"wrap_content\" homeSchool:src=\"" + link + "\" />";
//                            midLayouts.add(id, mid);
//                            id++;
//                            LinearLayout linearLayout = parse(start +midLayouts.toString() + end);
//                            mainView.removeAllViews();
//                            mainView.addView(linearLayout);
                            //and displaying a success toast
                            photoString = link;
                            Glide.with(getApplicationContext()).load(photoString).into(photoImageView);
                            Toast.makeText(getApplicationContext(), "File Uploaded",
                                    Toast.LENGTH_LONG).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //if the upload is not successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            //and displaying error message
                            Toast.makeText(getApplicationContext(), exception.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //calculating progress percentage
                            @SuppressWarnings("VisibleForTests") double progress = (100.0 * taskSnapshot
                                    .getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                            //displaying percentage in progress dialog
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
        }
        //if there is not any file
        else {
            //you can display an error toast
        }
    }
        public static String getMimeType(Context context, Uri uri) {
            String extension;

            //Check uri format to avoid null
            if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
                //If scheme is a content
                final MimeTypeMap mime = MimeTypeMap.getSingleton();
                extension = mime.getExtensionFromMimeType(context.getContentResolver().getType(uri));
            } else {
                //If scheme is a File
                //This will replace white spaces with %20 and also other special characters. This will avoid returning null values on file name with spaces and special characters.
                extension = MimeTypeMap
                        .getFileExtensionFromUrl(Uri.fromFile(new File(uri.getPath())).toString());

            }

            return extension;
        }

        public String getFileName(Context context, Uri uri) {
            String result = null;
            if (uri.getScheme().equals("content")) {
                Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
                try {
                    if (cursor != null && cursor.moveToFirst()) {
                        result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                }
            }
            if (result == null) {
                result = uri.getPath();
                int cut = result.lastIndexOf('/');
                if (cut != -1) {
                    result = result.substring(cut + 1);
                }
            }
            return result;
        }
    }


