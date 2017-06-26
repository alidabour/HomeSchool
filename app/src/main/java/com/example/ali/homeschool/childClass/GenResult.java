package com.example.ali.homeschool.childClass;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.ali.homeschool.R;

public class GenResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gen_result);
    }

    public void GoBack(View view) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("Hi"));
        setResult(Activity.RESULT_OK,intent);
        finish();
    }
}
