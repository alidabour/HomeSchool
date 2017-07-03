package com.almanara.homeschool.instructor.topic;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.almanara.homeschool.controller.activities.BaseActivity;
import com.almanara.homeschool.data.firebase.TopicModel;
import com.almanara.homeschool.data.firebase.CourseCreated;
import com.almanara.homeschool.data.firebase.LessonModel;
import com.almanara.ali.homeschool.R;
import com.almanara.homeschool.adapter.InstructorTopicsAdapter;
import com.almanara.homeschool.instructor.create.InstructorTopicCreationActivity;
import com.almanara.homeschool.instructor.lesson.InstructorLessonsActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class InstructorTopicActivity extends BaseActivity {
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
    String topicid = " ";
    ValueEventListener listener;
    TextView noTopic;
    ItemTouchHelper.SimpleCallback simpleItemTouchCallback;
    InstructorTopicsAdapter lessonAdapter;
    List<TopicModel> lessonModelList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_topic2);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab3);

        noTopic = (TextView) findViewById(R.id.no_topic);
        //Getting intent and checking if it's null
        // Bundle bundle= intent.getBundleExtra("BUNDLE");
        //    lessonModel = bundle.getParcelable("lesson");
        if (getIntent().hasExtra("courseId")) {
            courseId = getIntent().getStringExtra("courseId");
            Log.v("intentExtra :  ", ":------------123" + courseId);
        }
        if (getIntent().hasExtra("lessonid")) {
            lessonid = getIntent().getStringExtra("lessonid");
            Log.v("intentExtra :  ", ":------------54321" + lessonid);
        }


        db = FirebaseDatabase.getInstance().getReference();


        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(InstructorTopicActivity.this);
                builder.setTitle(R.string.anwan_7sa);

                // Set up the input
                final EditText input = new EditText(InstructorTopicActivity.this);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(
                        InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
//                        Map<String,String> lesson = new HashMap<String, String>();
                        /*String key = db.child("courses").child(courseId).child("lessons").child(lessonid).child("topics").push().getKey();
//                        lesson.put("id",key);
//                        lesson.put("name",m_Text);
                        db.child("courses").child(courseId).child("lessons").child(lessonid).child("topics").child(key).child("id").setValue(key);
                        db.child("courses").child(courseId).child("lessons").child(lessonid).child("topics").child(key).child("name").setValue(m_Text);
                        db.child("courses").child(courseId).child("lessons").child(lessonid).child("topics").child(key).child("layout").setValue("");


                        String topicid = key;
                        */

                        Intent intent = new Intent(getApplicationContext(),
                                InstructorTopicCreationActivity.class);

                        intent.putExtra("topicname", m_Text);
//                        intent.putExtra("topicid", topicid);
                        intent.putExtra("lessonid", lessonid);
                        intent.putExtra("courseId", courseId);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false);
        lessonsRV.setLayoutManager(layoutManager);

        //   Log.v("Testytesty10001 ", ":------------" + intent.getParcelableExtra("lesson"));


/*

        toolbar.setTitle(lessonModel.getName().toString());
        toolbar.setTitleTextColor(ContextCompat.getColor(InstructorTopicActivity.this,R.color.colorBack));*/
        toolbar.setTitleTextColor(
                ContextCompat.getColor(InstructorTopicActivity.this, R.color.colorBack));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN | ItemTouchHelper.UP) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                Toast.makeText(getApplicationContext(), R.string.on_move, Toast.LENGTH_SHORT)
                        .show();
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(
                        InstructorTopicActivity.this);
                builder.setTitle(R.string.delete_alert);
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        lessonAdapter.notifyDataSetChanged();
                    }
                });
                builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String topicId = lessonModelList.get(viewHolder.getAdapterPosition())
                                .getId();
                        lessonAdapter.notifyItemRemoved(
                                viewHolder.getLayoutPosition());
                        db.child("courses").child(courseId).child("lessons").child(lessonid)
                                .child("topics").child(topicId).removeValue();

                    }
                });
                builder.show();
//              Toast.makeText(getApplicationContext(), R.string.on_swiped, Toast.LENGTH_SHORT).show();

            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(lessonsRV);
    }

    @Override
    protected void onStart() {
        super.onStart();
        listener = new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lessonModelList = new ArrayList<>();
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    topicModel = d.getValue(TopicModel.class);
                    if (!(topicModel.getLayout() == null))
                        lessonModelList.add(topicModel);
                    if (!topicModel.getId().equals(" "))
                        topicid = topicModel.getId();
                    Log.v("Test", "LESSON __ " + topicModel.toString());
                }
                lessonAdapter = new InstructorTopicsAdapter(lessonModelList,
                        new InstructorTopicsAdapter.OnClickHandler() {
                            @Override
                            public void onClick(TopicModel test) {
                                InstructorTopicsAdapter
                                        .startIntentFromAdapter(InstructorTopicActivity.this, test);
                            }
                        }, InstructorTopicActivity.this, courseId, lessonid);
                lessonsRV.setAdapter(lessonAdapter);
                if (lessonModelList.size() < 0) {
                    noTopic.setVisibility(View.VISIBLE);
                } else {
                    noTopic.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        db.child("courses").child(courseId).child("lessons").child(lessonid).child("topics")
                .addValueEventListener(listener);

    }

    @Override
    protected void onPause() {
        if (listener != null)
            db.removeEventListener(listener);
        super.onPause();
    }

}
