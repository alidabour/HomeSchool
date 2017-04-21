package com.example.ali.homeschool.InstructorHome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.ali.homeschool.InstructorLessons.InstructorLessonsActivity;
import com.example.ali.homeschool.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class InstructorActivity extends AppCompatActivity {
    RecyclerView coursesRV;
    DatabaseReference db;
    FirebaseUser user;
    List<CourseCreated> coursesList;
    CourseCreatedAdapter courseCreatedAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor);
        coursesList = new ArrayList<>();
        coursesRV = (RecyclerView) findViewById(R.id.courses);
        coursesRV.setHasFixedSize(true);
        coursesRV.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        FirebaseAuth auth = FirebaseAuth.getInstance();
         user = auth.getCurrentUser();
        Log.v("Test","User Email"+ user.getDisplayName());
//        Log.v("Test","User Email"+ user.getProviderId());
        Log.v("Test","User Email"+ user.getUid());
        db = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    protected void onStart() {
        super.onStart();
        db.child("users").child(user.getUid()).child("CreatedCourse").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.v("Test","OnData");
                for (DataSnapshot x:dataSnapshot.getChildren()){
                    CourseCreated courseCreated = x.getValue(CourseCreated.class);
                    coursesList.add(courseCreated);
                    Log.v("Test","Courses Model "+ courseCreated.getName());
                    Log.v("Test","Courses "+ x.toString());
                }
                courseCreatedAdapter = new CourseCreatedAdapter(coursesList,
                        new CourseCreatedAdapter.OnClickHandler() {
                            @Override
                            public void onClick(CourseCreated test) {
                                Log.v("Test","Open Activity");
                                Intent intent = new Intent(getApplicationContext(),
                                        InstructorLessonsActivity.class);
                                intent.putExtra("course",test);
                                startActivity(intent);
                            }
                        });
                coursesRV.setAdapter(courseCreatedAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
