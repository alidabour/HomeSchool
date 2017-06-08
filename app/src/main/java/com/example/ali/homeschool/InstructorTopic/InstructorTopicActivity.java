package com.example.ali.homeschool.InstructorTopic;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.ali.homeschool.InstructorHome.CourseCreated;
import com.example.ali.homeschool.InstructorLessons.LessonModel;
import com.example.ali.homeschool.R;
import com.example.ali.homeschool.adapter.TopicsAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class InstructorTopicActivity extends AppCompatActivity {
    RecyclerView lessonsRV;
    String m_Text = "";
    DatabaseReference db;
    CourseCreated courseCreated;
    TopicModel topicModel;
    Toolbar toolbar;
    String courseId;
    LessonModel lessonModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_topic2);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab3);


        //Getting intent and checking if it's null
        Intent intent = getIntent();
        if(intent!=null)
            Log.v("intent Extra :  ", ":------------" + intent.getStringExtra("Hello"));






        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(InstructorTopicActivity.this);
                builder.setTitle("Title");

                // Set up the input
                final EditText input = new EditText(InstructorTopicActivity.this);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(
                        InputType.TYPE_CLASS_TEXT );
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
//                        Map<String,String> lesson = new HashMap<String, String>();
                        String key =db.child("courses").child(courseCreated.getCourse_id()).child("lessons").push().getKey();
//                        lesson.put("id",key);
//                        lesson.put("name",m_Text);
                        db.child("courses").child(courseCreated.getCourse_id()).child("lessons").child(key).child("id").setValue(key);
                        db.child("courses").child(courseCreated.getCourse_id()).child("lessons").child(key).child("name").setValue(m_Text);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });
//        addTopicB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), InstructorTopicActivity.class);
//                intent.putExtra("lesson",lessonModel);
//                startActivity(intent);
//            }
//        });
        lessonsRV = (RecyclerView) findViewById(R.id.lessonsRV);
        lessonsRV.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        lessonsRV.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(lessonsRV.getContext(),layoutManager.getOrientation());
        lessonsRV.addItemDecoration(dividerItemDecoration);

        db = FirebaseDatabase.getInstance().getReference();

     //   Log.v("Testytesty10001 ", ":------------" + intent.getParcelableExtra("lesson"));




        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        db.child("courses").child(courseId).child("lessons").child(String.valueOf(lessonModel.getId())).addValueEventListener(
                new ValueEventListener() {
                    List<TopicModel> lessonModelList;
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        lessonModelList = new ArrayList<TopicModel>();
                        for (DataSnapshot d : dataSnapshot.getChildren()){
                            Log.v("Testytesty101","Lesson " + d.toString());
                            topicModel = d.getValue(TopicModel.class);
                            lessonModelList.add(topicModel);
                            Log.v("Test","LESSON __ "+ topicModel.toString());
                        }
                        TopicsAdapter lessonAdapter = new TopicsAdapter(lessonModelList,
                                new TopicsAdapter.OnClickHandler() {
                                    @Override
                                    public void onClick(TopicModel test) {
                                        Intent intent = new Intent(getApplicationContext(), InstructorTopicCreationActivity.class);
                                        intent.putExtra("lesson",test.getId());
                                        intent.putExtra("courseID",courseCreated.getCourse_id());
                                        startActivity(intent);
                                    }
                                });
                        lessonsRV.setAdapter(lessonAdapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}
