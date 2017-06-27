package com.example.ali.homeschool.register;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ali.homeschool.CircleTransform;
import com.example.ali.homeschool.R;
import com.example.ali.homeschool.UserModelHelper.FileUploadHelper;
import com.example.ali.homeschool.UserModelHelper.UploadFile;
import com.example.ali.homeschool.data.InternetConnectionChecker;
import com.example.ali.homeschool.data.firebase.UserModel;
import com.example.ali.homeschool.login.SignInAs;
import com.example.ali.homeschool.login.Sign_In;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.UUID;

/**
 * Created by lenovo on 30/11/2016.
 */

public class Register extends AppCompatActivity implements FileUploadHelper {
    FirebaseAuth mAuth;
    private EditText userName;
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
    String photoString = "";
    UploadFile uploadFile;
    private TextView textViewSignin ;
    private ProgressDialog progressDialog ;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        userName = (EditText) findViewById(R.id.userName);
        email = (EditText) findViewById(R.id.EmailRegister);
        password = (EditText) findViewById(R.id.PasswordRegister);
        repeated_password = (EditText) findViewById(R.id.Repeated_PasswordRegister);
        photoImageView = (ImageView) findViewById(R.id.PhotoimageView);
        Button RegisterButton = (Button) findViewById(R.id.RegisterButton);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        progressDialog = new ProgressDialog(this);
        textViewSignin = (TextView) findViewById(R.id.textViewSignin);
        textViewSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext() , Sign_In.class));

            }
        });

        photoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageActivity();
            }
        });

        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registering(email.getText().toString().trim(), password.getText().toString().trim(), repeated_password.getText().toString().trim());
            }
        });

        if (user != null) {
            // User is signed in
            {
                Log.v("uid",user.getUid());
                Intent intent = new Intent(getApplicationContext(), SignInAs.class);
                startActivity(intent);
            }
        } else {
            // User is signed out
        }
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
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK)
        {
                Log.v("Instructor",
                        "Req : " + requestCode + " Res :" + resultCode + " Intent : " + data
                                .getData().toString());
                filePath = data.getData();
                String storagePath = "images/usersPhoto/"+  UUID.randomUUID();
                uploadFile = new UploadFile(filePath , Register.this , this ,storagePath );
            }
        }



    private void registering(String Email, String Password, String Repeated_Password) {
        if (!validateForm()) {
            return;
        }
        progressDialog.setMessage("Registering user ...");
        progressDialog.show();

        if (Password.equals(Repeated_Password)) {
            mAuth.createUserWithEmailAndPassword(Email, Password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            InternetConnectionChecker internetConnectionChecker = new InternetConnectionChecker(getApplicationContext());
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("success", "");
                                DatabaseReference myRef = databaseReference;
                                if(photoString.isEmpty()){
                                    photoString = " https://firebasestorage.googleapis.com/v0/b/dealgamed-f2066.appspot.com/o/images%2FusersPhoto%2Fprofile.png?alt=media&token=b832ae10-6cda-45f5-9681-4ff355736da4";
                                }
                                newUser = new UserModel();
                                newUser.setEmail(email.getText().toString().trim());
                                newUser.setName(String.valueOf(userName.getText()));
                                newUser.setPhoto(photoString);
                                user = mAuth.getCurrentUser();

                                myRef.child("users").child(user.getUid()).setValue(newUser);
                                String Uid = myRef.child("users").child(user.getUid()).getKey();
                                myRef.child("users").child(user.getUid()).child("uid").setValue(Uid);
                                finish();
                                startActivity(new Intent(getBaseContext(), SignInAs.class));
                            } else {
                                if (internetConnectionChecker.isInternetOn()) {
                                    Toast.makeText(Register.this, "Email is Either not Created or Doesn't Exist",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Register.this, "Internet Connection Not Available",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
        } else {
            Toast.makeText(this, "Passwords didn't match", Toast.LENGTH_SHORT).show();
            repeated_password.setError("Doesn't Match.");
        }
    }

    private boolean validateForm() {
        boolean valid = true;

        String UserName = userName.getText().toString();
        if (TextUtils.isEmpty(UserName)) {
            userName.setError("Required");
            valid = false;
        } else {
            repeated_password.setError(null);
        }
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
        Glide.with(getApplicationContext()).load(photoString).transform(new CircleTransform(getApplicationContext())).into(photoImageView);
    }
}


