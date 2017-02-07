package com.example.ali.homeschool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

/**
 * Created by P on 2/6/2017.
 */

public class Parentactivity extends AppCompatActivity{


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parent);

        Intent intent = getIntent();


        GridView gridview = (GridView) findViewById(R.id.ParentGridView);
        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(Parentactivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();

            }
        });
    }

}
