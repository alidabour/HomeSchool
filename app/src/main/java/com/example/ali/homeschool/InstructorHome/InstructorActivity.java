package com.example.ali.homeschool.InstructorHome;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.ali.homeschool.InstructorLessons.InstructorLessonsActivity;
import com.example.ali.homeschool.InstructorTopic.InstructorTopicActivity;
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
    Button addCourse;
    String coursName;
    String descriptionS;
    String subjectS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor);
        addCourse  = (Button) findViewById(R.id.addCourse);
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
        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(InstructorActivity.this);
                builder.setTitle("Add Course");
                LayoutInflater li = LayoutInflater.from(InstructorActivity.this);
                LinearLayout someLayout;
                someLayout = (LinearLayout)li.inflate(R.layout.dialog_add_course, null);

                // Set up the input
//                final EditText input = new EditText(MainActivity.this);
//                final EditText audioIn = new EditText(MainActivity.this);
                final EditText input = (EditText) someLayout.findViewById(R.id.courseName);
                final EditText description = (EditText) someLayout.findViewById(R.id.description);
                final EditText subject = (EditText) someLayout.findViewById(R.id.subject);

//                input.setHint("Text");
//                audioIn.setHint("Audio Link");
//                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
//                input.setInputType(
//                        InputType.TYPE_CLASS_TEXT );
//                audioIn.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(someLayout);


                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        coursName = input.getText().toString();
                        subjectS = subject.getText().toString();
                        descriptionS = description.getText().toString();
                        String key = db.child("users").child(user.getUid()).child("CreatedCourse").push().getKey();
                        db.child("users").child(user.getUid()).child("CreatedCourse").child(key).child("id").setValue(key);
                        db.child("users").child(user.getUid()).child("CreatedCourse").child(key).child("name").setValue(coursName);

                        db.child("courses").child(key).child("description").setValue(description);
                        db.child("courses").child(key).child("name").setValue(coursName);
                        db.child("courses").child(key).child("subjectS").setValue(subjectS);
                        db.child("courses").child(key).child("description").setValue(description);
                        db.child("courses").child(key).child("rate").setValue("5.0");
                        db.child("courses").child(key).child("teacher").setValue(user.getDisplayName());
                        db.child("courses").child(key).child("teacher_id").setValue(user.getUid());

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
    }

    @Override
    protected void onStart() {
        super.onStart();
        db.child("users").child(user.getUid()).child("CreatedCourse").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.v("Test","OnData");
                coursesList = new ArrayList<CourseCreated>();
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
