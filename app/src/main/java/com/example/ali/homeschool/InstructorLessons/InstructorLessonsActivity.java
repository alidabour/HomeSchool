package com.example.ali.homeschool.InstructorLessons;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ali.homeschool.InstructorHome.CourseCreated;
import com.example.ali.homeschool.InstructorTopic.InstructorTopicActivity;
import com.example.ali.homeschool.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class InstructorLessonsActivity extends AppCompatActivity {
    RecyclerView lessonsRV;
    String m_Text = "";
    DatabaseReference db;
    CourseCreated courseCreated;
    LessonModel lessonModel;
    Toolbar toolbar;
    String courseID;
    TextView noLesson ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Intent intentt = new Intent(this, InstructorTopicCreationActivity.class);
//        intentt.putExtra("courseId","dsdssd");
//        startActivity(intentt);
//        finish();
        setContentView(R.layout.activity_instructor_lessons);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab3);
        noLesson = (TextView) findViewById(R.id.no_lesson);

        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(InstructorLessonsActivity.this);
                builder.setTitle("Title");

                // Set up the input
                final EditText input = new EditText(InstructorLessonsActivity.this);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(
                        InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
//                        Map<String,String> lesson = new HashMap<String, String>();
                        String key = db.child("courses").child(courseCreated.getCourse_id()).child("lessons").push().getKey();
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        lessonsRV.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(lessonsRV.getContext(), layoutManager.getOrientation());
        lessonsRV.addItemDecoration(dividerItemDecoration);

        db = FirebaseDatabase.getInstance().getReference();
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("course")) {
            courseCreated = intent.getParcelableExtra("course");
            Log.v("TestingTesting", "" + courseCreated.getName().toString());
            toolbar.setTitle(courseCreated.getName().toString());
            courseID = courseCreated.getCourse_id().toString();
        }

        toolbar.setTitleTextColor((ContextCompat.getColor(InstructorLessonsActivity.this,R.color.colorBack)));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        db = FirebaseDatabase.getInstance().getReference();
        Log.v("el_ID_hna" , courseID + " " );
        db.child("courses").child(courseID).child("lessons").addValueEventListener(
                new ValueEventListener() {
                    ArrayList<LessonModel> lessonModelList;
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        lessonModelList = new ArrayList<>();
                        for (DataSnapshot d : dataSnapshot.getChildren()) {
                            Log.e("dataSnapShot",d+"");
                            lessonModel = d.getValue(LessonModel.class);
                            lessonModelList.add(lessonModel);
                        }
                        InstructorLessonAdapter instructorLessonAdapter = new InstructorLessonAdapter(lessonModelList,
                                new InstructorLessonAdapter.OnClickHandler() {
                                    @Override
                                    public void onClick(LessonModel test) {
                                        // intent from current activity to Next Activity
                                        Intent intent = new Intent(InstructorLessonsActivity.this, InstructorTopicActivity.class);
                                        //Putting extras to get them in the Next Activity
                                        intent.putExtra("courseId", courseID);
                                        intent.putExtra("lessonid", test.getId());
                                        //     intent.putExtra("lesson",test);
                                        // starting the Activity
                                        startActivity(intent);
                                    }
                                },InstructorLessonsActivity.this,courseID);
                        lessonsRV.setAdapter(instructorLessonAdapter);
                        if(lessonModelList.size()<0){
                            noLesson.setVisibility(View.VISIBLE);
                        }else{
                            noLesson.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}