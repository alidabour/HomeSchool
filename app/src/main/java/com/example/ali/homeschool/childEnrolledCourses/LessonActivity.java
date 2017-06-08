package com.example.ali.homeschool.childEnrolledCourses;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.ali.homeschool.InstructorHome.CourseCreated;
import com.example.ali.homeschool.InstructorLessons.LessonAdapter;
import com.example.ali.homeschool.InstructorLessons.LessonModel;
import com.example.ali.homeschool.InstructorTopic.InstructorTopicActivity;
import com.example.ali.homeschool.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class LessonActivity extends AppCompatActivity {

    List<CourseCreated> enrolledLessonList;
    RecyclerView enrolledRecyclerView;
    Toolbar toolbar ;
    CourseCreated course;
    ValueEventListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);
        toolbar = (Toolbar) findViewById(R.id.toolbar1);

        enrolledRecyclerView = (RecyclerView) findViewById(R.id.enrolledCourses);
        enrolledRecyclerView.setHasFixedSize(true);
        LinearLayoutManager categoryLayoutManger = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false);
        enrolledRecyclerView.setLayoutManager(categoryLayoutManger);
        Log.v("Test","myLessonActivity");

        Intent intent = getIntent();
        course = intent.getParcelableExtra("course");



    }

    @Override
    protected void onStart() {
        super.onStart();
        listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

//                LessonAdapter lessonAdapter = new LessonAdapter(course.ge,
//                        new LessonAdapter.OnClickHandler() {
//                            @Override
//                            public void onClick(LessonModel test) {
//                                Intent intent = new Intent(getApplicationContext(), InstructorTopicActivity.class);
//                                intent.putExtra("lesson",test.getId());
//                                intent.putExtra("courseID",CourseCreated.getCourse_id());
//                                startActivity(intent);
//                            }
//                        });
             //   enrolledRecyclerView.setAdapter(lessonAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        };
    }
}
