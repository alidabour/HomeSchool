package com.almanara.ali.homeschool.student.course.lesson.topic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.almanara.ali.homeschool.Constants;
import com.almanara.ali.homeschool.instructor.topic.TopicModel;
import com.almanara.ali.homeschool.R;
import com.almanara.ali.homeschool.student.course.lesson.topic.template.AnimationFragment;
import com.almanara.ali.homeschool.student.course.lesson.topic.template.MultiImageQuestionFragment;
import com.almanara.ali.homeschool.childProgress.ProgressModel;
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
    Context context;
    ViewPager pager;
    ArrayList<String> layouts;
    DatabaseReference db;
    String course_id, lesson_id;
    ArrayList<TopicModel> TopicModelList;
    ValueEventListener listener;
    FirebaseUser user;
    FirebaseAuth firebaseAuth;
    ImageView imageView2;
    List<Fragment> fragmentList;
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
        imageView2 = (ImageView) findViewById(R.id.masha);
        Intent iin = getIntent();
        Bundle b = iin.getExtras();
        TopicModelList = new ArrayList<TopicModel>();
        if (b != null) {
            course_id = b.getString("courseId");
            lesson_id = b.getString("lessonid");
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.v("Pager0000", "OnStart");
        if (pager.getChildCount() < 1) {
            listener = new ValueEventListener() {
                @Override
                public void onCancelled(DatabaseError databaseError) {}
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    TopicModelList = new ArrayList<TopicModel>();
                    Log.v("Pager0000", "OnDataChanged");
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        Log.v("Pager----",
                                "OnStart onDataChange Child:-> " + dataSnapshot1.toString());
                        TopicModel topicModel = dataSnapshot1.getValue(TopicModel.class);
                        TopicModelList.add(topicModel);
                    }
                    layouts = new ArrayList<String>();
                    fragmentList = new ArrayList<>();
                    for (TopicModel modelEntry : TopicModelList) {
                        if (modelEntry.getTopicType().equals("normal")) {
                            fragmentList.add(LessonFragment.newInstance(modelEntry.getLayout()));
                        } else if (modelEntry.getTopicType().equals("multiImageQue")) {
                            fragmentList.add(NestedFrag.newInstance("12"));
                            fragmentList.add(MultiImageQuestionFragment
                                    .newInstance(modelEntry.getLayout()));
                        }else if(modelEntry.getTopicType().equals("animation")){
                            fragmentList.add(AnimationFragment.newInstance(modelEntry.getLayout()));
                        }
                    }
                    pager.setAdapter(
                            new LessonPagerAdapter(getSupportFragmentManager(), fragmentList));
                pager.setPageTransformer(true, new CubeOutTransformer()); //set the animation
                }
            };
            db.child("courses").
                    child(course_id).
                    child("lessons").
                    child(lesson_id).
                    child("topics").
                    addValueEventListener(listener);

        }

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
        imageView2.setVisibility(View.VISIBLE);
        final GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imageView2);
        Glide.with(this).load(R.raw.source).into(imageViewTarget);
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.yay);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                imageView2.setVisibility(View.GONE);
                pager.setCurrentItem((pager.getCurrentItem() + 1));
            }
        });
        if (requestCode == Constants.SPEECH) {
            if (resultCode == Constants.CORRECTANSWER) {
                Toast.makeText(this, " احسنت \n" + data.getData().toString(), Toast.LENGTH_LONG)
                        .show();
            } else if (resultCode == Constants.WRONGANSWER) {
                db.child("users").child(user.getUid()).child("enrolledcourses")
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot d : dataSnapshot.getChildren()) {
                                    for (DataSnapshot d1 : d.getChildren())
                                        for (DataSnapshot d2 : d1.getChildren()) {
                                            ProgressModel progressModel = d2
                                                    .getValue(ProgressModel.class);
                                            if (progressModel.getTopicProgressId()
                                                    .equals(TopicModelList
                                                            .get(pager.getCurrentItem()).getId())) {
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
                Toast.makeText(this, " حاول مرة أخري \n" + data.getData().toString(),
                        Toast.LENGTH_LONG).show();

            }
        }
        if (resultCode == Constants.CORRECTANSWER) {
            if (requestCode == Constants.SIMPLE) {
//                pager.setCurrentItem((pager.getCurrentItem() + 1) % TopicModelList.size());
                Toast.makeText(context, "Result Correct", Toast.LENGTH_SHORT).show();
            }
        }
        if (resultCode == Constants.WRONGANSWER) {
            if (requestCode == Constants.SIMPLE) {
//                pager.setCurrentItem((pager.getCurrentItem() + 1) % TopicModelList.size());
                Toast.makeText(context, "Result Wrong", Toast.LENGTH_SHORT).show();
            }
        }
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Constants.SPEECH) {
//                pager.setCurrentItem((pager.getCurrentItem() + 1) % TopicModelList.size());
            }
        }
        //-----------------------------------------------------------------------------------------------------
        Log.v("LessonFragment", "Activity Result " + requestCode + " , " + resultCode);
        //-----------------------------------------------------------------------------------------------------
        if (resultCode == Constants.CORRECTANSWER) {
            if (requestCode == Constants.Text_Detection) {
//                pager.setCurrentItem((pager.getCurrentItem() + 1) % TopicModelList.size());
                Log.v("t3alayabni", "etnyl t3ala enta eltani");
                Toast.makeText(context, "Result Correct", Toast.LENGTH_SHORT).show();
            }
        }
        if (resultCode == Constants.WRONGANSWER) {
            if (requestCode == Constants.Text_Detection) {
//                pager.setCurrentItem((pager.getCurrentItem() + 1) % TopicModelList.size());
                Toast.makeText(context, "Result Incorrect", Toast.LENGTH_SHORT).show();
            }
        }
   