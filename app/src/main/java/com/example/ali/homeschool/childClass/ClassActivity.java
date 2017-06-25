package com.example.ali.homeschool.childClass;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ali.homeschool.Constants;
import com.example.ali.homeschool.InstructorLessons.LessonModel;
import com.example.ali.homeschool.InstructorTopic.TopicModel;
import com.example.ali.homeschool.R;
import com.example.ali.homeschool.childClass.topicsfragment.MultiImageQuestionFragment;
import com.example.ali.homeschool.childProgress.ProgressModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/*
    This class is for advanced level later on as it is designed to get the images like the cat image and the voice
    and that is why it has not been deleted yet
 */
public class ClassActivity extends AppCompatActivity {
    //RelativeLayout relativeLayout;
    View linearLayout;
    ImageView imageView;
    Context context;
    ViewPager pager;
    ArrayList<String> layouts;
    DatabaseReference db;
    LessonModel lesson;
    String course_id, lesson_id;
    ArrayList<TopicModel> TopicModelList;
    ValueEventListener listener;
    TextView noTopics;
    FirebaseUser user;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        db = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        context = getApplicationContext();
        setContentView(R.layout.activity_class_trial);
        pager = (ViewPager) findViewById(R.id.viewPager);
        Intent iin = getIntent();
        Bundle b = iin.getExtras();
        TopicModelList = new ArrayList<TopicModel>();
        if (b != null) {

            Log.v("Test", "Intent Found");
            course_id = b.getString("courseId");
            lesson_id = b.getString("lessonid");
            Log.v("Test", "Intent Found" + " lesson " + lesson_id + " Course id " + course_id);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.v("Test", "Coursr id " + "OnStart bta3t topic");
//        Log.v("Test","Listener ahu "+ listener.toString());
        //  lessonModelList = new ArrayList<LessonModel>();
        listener = new ValueEventListener() {
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("TopicsonDataChang", "onDataChang");
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Log.e("TopicsonDataChang", dataSnapshot.toString());
                    TopicModel topicModel = dataSnapshot1.getValue(TopicModel.class);
                    Log.e("TopicsonDataChang", topicModel.toString());
                    TopicModelList.add(topicModel);
                }

                layouts = new ArrayList<String>();
                List<Fragment> fragmentList = new ArrayList<>();

                for (TopicModel modelEntry : TopicModelList) {
//                    layouts.add(topicModel.getLayout());
                    if (modelEntry.getTopicType().equals("normal")) {
                        fragmentList.add(LessonFragment.newInstance(modelEntry.getLayout()));
                    } else if (modelEntry.getTopicType().equals("multiImageQue")) {
                        fragmentList.add(MultiImageQuestionFragment.newInstance(modelEntry.getLayout()));
                    }

                }

//                Map<String, TopicModel> topicModelHashMap = lesson.getTopics();
//                for (Map.Entry<String, TopicModel> modelEntry : topicModelHashMap.entrySet()) {
//                    TopicModel topicModel = modelEntry.getValue();
//                    Log.e("Test", "Layouts " + topicModel.getLayout());
//                    layouts.add(topicModel.getLayout());
//                    //  }
//                }
                //  if (getSupportFragmentManager() != null){
//                fragmentList.add(LessonFragment.newInstance(layouts.get(0)));
//                fragmentList.add(new NestedFrag());
//                fragmentList.add(new NestedFrag());

                pager.setAdapter(new LessonPagerAdapter(getSupportFragmentManager(), fragmentList));

                //layouts = new ArrayList<String>();

            }
        };


        ;
        db.child("courses").

                child(course_id).

                child("lessons").

                child(lesson_id).

                child("topics").

                addValueEventListener(listener);

    }

    @Override
    protected void onPause() {
        if (listener != null) {
            db.removeEventListener(listener);
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (pager != null) {
            pager.setAdapter(null);
        }
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.SPEECH) {
            if (resultCode == Constants.CORRECTANSWER) {
                Log.v("talayabni", data.getData().toString());

                Log.e("ارنب", " Rabbit");
                MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.yay);
                mediaPlayer.start();

                new CountDownTimer(4000, 1000) {
                    public void onFinish() {
                        finish();
                    }

                    public void onTick(long millisUntilFinished) {
                    }

                }.start();

                Toast.makeText(this, " احسنت \n" + data.getData().toString(), Toast.LENGTH_LONG)
                        .show();
                Toast.makeText(this, " ya rbbbbbb \n" + data.getData().toString(),
                        Toast.LENGTH_LONG).show();

             /*   db.child("users").child(user.getUid()).child("enrolledcourses").addValueEventListener(new ValueEventListener() {
                    int count = 0;
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                            for (DataSnapshot d : dataSnapshot.getChildren()) {
                                Log.e("ehel8oldah ", d + "");

                            }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });*/


            } else if (resultCode == Constants.WRONGANSWER) {
                db.child("users").child(user.getUid()).child("enrolledcourses")
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for (DataSnapshot d : dataSnapshot.getChildren()) {
                                    for (DataSnapshot d1 : d.getChildren())
                                        for (DataSnapshot d2 : d1.getChildren()) {
                                            ProgressModel progressModel = d2.getValue(ProgressModel.class);
                                            Log.v("dddddddddddddd", progressModel.getTopicProgressId() + "Welcome");

                                            Log.v("dddddddddddddd", TopicModelList.get(pager.getCurrentItem()).getId() + "Welcome");
                                            if (progressModel.getTopicProgressId().equals(TopicModelList.get(pager.getCurrentItem()).getId())) {
                                                progressModel.setTopicProgressFlag("true");
                                                db.child("users").child(user.getUid())
                                                        .child("enrolledcourses")
                                                        .child(progressModel.getEnrolledcourseid())
                                                        .child("progress")
                                                        .child(progressModel.getProgressid())
                                                        .updateChildren(progressModel.toMap());
                                            }

                                        }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                Log.v("t3alayabni", "etnyl t3ala");
                Toast.makeText(this, " حاول مرة أخري \n" + data.getData().toString(),
                        Toast.LENGTH_LONG).show();
                Toast.makeText(this, " ya rbbbbbb \n" + data.getData().toString(),
                        Toast.LENGTH_LONG).show();

            }
        }
        Log.v("LessonFragment", "Activity Result " + requestCode + " , " + resultCode);
        if (resultCode == Constants.CORRECTANSWER) {
            if (requestCode == Constants.SIMPLE) {
                Toast.makeText(context, "Result Correct", Toast.LENGTH_SHORT).show();
            }
        }
        if (resultCode == Constants.WRONGANSWER) {
            if (requestCode == Constants.SIMPLE) {
                Toast.makeText(context, "Result Wrong", Toast.LENGTH_SHORT).show();
            }
        }
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Constants.SPEECH) {

            }
        }


        //-----------------------------------------------------------------------------------------------------


        Log.v("LessonFragment", "Activity Result " + requestCode + " , " + resultCode);

        //-----------------------------------------------------------------------------------------------------


        if (resultCode == Constants.CORRECTANSWER) {
            if (requestCode == Constants.Text_Detection) {

                Log.v("t3alayabni", "etnyl t3ala enta eltani");
                Toast.makeText(context, "Result Correct", Toast.LENGTH_SHORT).show();
            }
        }


        /*db.child("users").child(user.getUid()).child("enrolledcourses").addValueEventListener(new ValueEventListener() {
            int count = 0;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Log.e("ehel8oldahel3b3l3b ",  d.getValue() + "7amada t3ala ya baba engzz");

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
        Log.v("ezhr w ban ", resultCode + "");
        Log.v("ezhr w ban ", requestCode + "");
        if (resultCode == Constants.WRONGANSWER) {
            if (requestCode == Constants.Text_Detection) {


                Toast.makeText(context, "Result Incorrect", Toast.LENGTH_SHORT).show();
            }
            Log.v("t3ala yabni", "etnyl t3ala");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
//        layouts.add(cursor.getString(cursor.getColumnIndex(TopicColumns.TOPIC_LAYOUT)));
//        pager.setAdapter(new LessonPagerAdapter(getSupportFragmentManager(),layouts));
//        layouts.clear();

}
