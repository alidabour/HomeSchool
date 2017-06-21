package com.example.ali.homeschool.InstructorTopic;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ali.homeschool.InstructorHome.CourseCreated;
import com.example.ali.homeschool.InstructorLessons.LessonModel;
import com.example.ali.homeschool.R;
import com.example.ali.homeschool.adapter.InstructorTopicsAdapter;
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
    String lessonid;
    Intent intent;
    ValueEventListener listener;
    TextView noTopic ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_topic2);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab3);

        noTopic = (TextView) findViewById(R.id.no_topic);
        //Getting intent and checking if it's null
       // Bundle bundle= intent.getBundleExtra("BUNDLE");
        //    lessonModel = bundle.getParcelable("lesson");
        if (getIntent().hasExtra("courseId")){
            courseId = getIntent().getStringExtra("courseId");
            Log.v("intentExtra :  ", ":------------123" + courseId);
        }
        if (getIntent().hasExtra("lessonid")){
            lessonid = getIntent().getStringExtra("lessonid");
            Log.v("intentExtra :  ", ":------------54321" + lessonid);
        }


        db = FirebaseDatabase.getInstance().getReference();


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
                        String key =db.child("courses").child(courseId).child("lessons").child(lessonid).child("topics").push().getKey();
//                        lesson.put("id",key);
//                        lesson.put("name",m_Text);
                        db.child("courses").child(courseId).child("lessons").child(lessonid).child("topics").child(key).child("id").setValue(key);
                        db.child("courses").child(courseId).child("lessons").child(lessonid).child("topics").child(key).child("name").setValue(m_Text);
                        db.child("courses").child(courseId).child("lessons").child(lessonid).child("topics").child(key).child("layout").setValue("");
                        String topicid=key;
                        Intent intent = new Intent(getApplicationContext(), InstructorTopicCreationActivity.class);

                        intent.putExtra("topicname",m_Text);
                        intent.putExtra("topicid",topicid);
                        intent.putExtra("lessonid",lessonid);
                        intent.putExtra("courseId",courseId);
                        startActivity(intent);
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

     //   Log.v("Testytesty10001 ", ":------------" + intent.getParcelableExtra("lesson"));





        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        }

    @Override
    protected void onStart() {
        super.onStart();
        listener =  new ValueEventListener() {
            List<TopicModel> lessonModelList;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lessonModelList = new ArrayList<TopicModel>();
                Log.e("dataSnapShot",dataSnapshot+"");
                for (DataSnapshot d : dataSnapshot.getChildren()){
                    Log.v("Testytesty101","Lesson " + d.toString());
                    topicModel = d.getValue(TopicModel.class);
                    if(!(topicModel.getLayout()==null))
                        lessonModelList.add(topicModel);
                    Log.v("Test","LESSON __ "+ topicModel.toString());
                }
                InstructorTopicsAdapter lessonAdapter = new InstructorTopicsAdapter(lessonModelList,
                        new InstructorTopicsAdapter.OnClickHandler() {
                            @Override
                            public void onClick(TopicModel test) {
                                InstructorTopicsAdapter.startIntentFromAdapter(InstructorTopicActivity.this,test);
                            }
                        },InstructorTopicActivity.this,courseId,lessonid);
                lessonsRV.setAdapter(lessonAdapter);
                if(lessonModelList.size()<0){
                    noTopic.setVisibility(View.VISIBLE);
                }else{
                    noTopic.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        Log.v("courseId",courseId);
        db.child("courses").child(courseId).child("lessons").child(lessonid).child("topics").addValueEventListener(listener);

    }

}
