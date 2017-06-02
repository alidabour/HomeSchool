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
import com.example.ali.homeschool.UserModelHelper.ImageUploadHelper;
import com.example.ali.homeschool.UserModelHelper.UploadImage;
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

public class Register extends AppCompatActivity implements ImageUploadHelper {
    FirebaseAuth mAuth;
    private EditText email;
    private EditText password;
    private EditText repeated_password;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    private static final int PICK_IMAGE_REQUEST = 234;
    private Uri filePath;
    FirebaseUser user;
    UserModel newUser;
    static Context context;
    ImageView photoImageView;
    String photoString;
    UploadImage uploadImage ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        email = (EditText) findViewById(R.id.EmailRegister);
        password = (EditText) findViewById(R.id.PasswordRegister);
        repeated_password = (EditText) findViewById(R.id.Repeated_PasswordRegister);
        photoImageView = (ImageView) findViewById(R.id.PhotoimageView);
        Button RegisterButton = (Button) findViewById(R.id.RegisterButton);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        Button uploadPhotoButton = (Button) findViewById(R.id.UploadButton);

        uploadPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openImageActivity();

            }
        });


        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                registering(email.getText().toString().trim(), password.getText().toString().trim(), repeated_password.getText().toString().trim());
            }
        });
    }


    private void openImageActivity() {
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
                String storagePath = "images/usersPhoto/"+  UUID.randomUUID();
                uploadImage = new UploadImage(filePath , Register.this , this ,storagePath );
            }
        }

    }

    private void registering(String Email, String Password, String Repeated_Password) {
        if (!validateForm()) {
            return;
        }
        if (Password.equals(Repeated_Password)) {
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
        } else {
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


    @Override
    public void fileUploaded(String url) {

        photoString = url ;
        Glide.with(getApplicationContext()).load(url).into(photoImageView);

    }
}


