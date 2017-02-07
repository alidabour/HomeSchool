package com.example.ali.homeschool;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/*
This is the class which i use to get the description of the course from the click listener
and supposely later on i would use the data base to fetch this data
 */
public class CourseDescriptionActivity extends AppCompatActivity {
    Toolbar toolbar;
    ListView topicsListView;
    Intent intent;
    Button enroll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_description);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Animals Vol. 1");
        enroll = (Button) findViewById(R.id.enroll);
        setSupportActionBar(toolbar);
        // this line supports the back button to go back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        topicsListView = (ListView) findViewById(R.id.listViewDes);
        ArrayList<String> items = new ArrayList<String>();
        items.add("Topic 1");
        items.add("Topic 1");
        items.add("Topic 1");
        items.add("Topic 1");
        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        topicsListView.setAdapter(itemsAdapter);
        intent = getIntent();
        final int type = intent.getIntExtra("type",0);
        enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type == 0){
                    Toast.makeText(CourseDescriptionActivity.this, getResources().getString(R.string.youMustSignIn), Toast.LENGTH_SHORT).show();
                }
                else if (type == 1 ){
                    Toast.makeText(CourseDescriptionActivity.this, getResources().getString(R.string.enrollDone), Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}
