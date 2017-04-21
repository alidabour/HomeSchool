package com.example.ali.homeschool.InstructorLessons;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.ali.homeschool.InstructorHome.CourseCreated;
import com.example.ali.homeschool.InstructorTopic.InstructorTopicActivity;
import com.example.ali.homeschool.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InstructorLessonsActivity extends AppCompatActivity {
    RecyclerView lessonsRV;
    DatabaseReference db;
    CourseCreated courseCreated;
    Button addTopicB;
    LessonModel lessonModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_lessons);
        addTopicB = (Button) findViewById(R.id.addTopic);
        addTopicB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InstructorTopicActivity.class);
                intent.putExtra("lesson",lessonModel);
                startActivity(intent);
            }
        });
        lessonsRV = (RecyclerView) findViewById(R.id.lessonsRV);
        db = FirebaseDatabase.getInstance().getReference();
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("course")){
            courseCreated = intent.getParcelableExtra("course");
            Log.v("Test","Course "+ courseCreated.getName());
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        db.child("courses").child(String.valueOf(courseCreated.getId())).child("lessons").addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot d : dataSnapshot.getChildren()){
                            Log.v("Test","Lesson " + d.toString());
                            lessonModel = d.getValue(LessonModel.class);
                            Log.v("Test","LESSON __ "+ lessonModel.toString());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }
}
