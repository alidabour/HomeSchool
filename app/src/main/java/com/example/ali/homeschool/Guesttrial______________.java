package com.example.ali.homeschool;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.support.design.widget.CollapsingToolbarLayout;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by lenovo on 30/11/2016.
 */
public class Guesttrial______________ extends AppCompatActivity{
    String [] x = new String [10];


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guesttrial);
     //   final Toolbar toolbar = (Toolbar) findViewById(R.id.MyToolbar);
     //   setSupportActionBar(toolbar);
     //   getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        CollapsingToolbarLayout collapsingToolbarLayout=(CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
       // collapsingToolbarLayout.setTitle("StudentHomeActivity");
        ListView L1 = (ListView) findViewById(R.id.listViewguest);
        for(int i=0;i<10;i++){
            x[i]="Hello";
        }
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.list_item,x);
        L1.setAdapter(adapter);

    }

}
