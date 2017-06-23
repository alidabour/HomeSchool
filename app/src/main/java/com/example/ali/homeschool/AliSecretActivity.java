package com.example.ali.homeschool;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AliSecretActivity extends AppCompatActivity {
    private File file;
    private List<String> myList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        //firebaseUser.getEmail().toString()
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ali_secret);
        myList = new ArrayList<String>();
        //01098899805
        String root_sd = Environment.getDataDirectory().toString();
        if(root_sd != null){
            Log.v("AliAli","Root Sd :"  + root_sd);
            Toast.makeText(this, "root_sd" + root_sd, Toast.LENGTH_SHORT).show();
            file = new File( root_sd ) ;
            Toast.makeText(this, "file : " + file.toString(), Toast.LENGTH_SHORT).show();
            File list[] = file.listFiles();
            if(list!=null)
            for( int i=0; i< list.length; i++)
            {
                Log.v("AliAli","Test");
                myList.add( list[i].getName() );
                Log.v("AliAli","Name:"+list[i].getName());
            }
        }
    }
}
