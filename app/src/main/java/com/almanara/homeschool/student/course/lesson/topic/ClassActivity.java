package com.almanara.homeschool.student.course.lesson.topic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.almanara.ali.homeschool.R;
import com.almanara.homeschool.Constants;
import com.almanara.homeschool.data.firebase.ProgressModel;
import com.almanara.homeschool.data.firebase.TopicModel;
import com.almanara.homeschool.student.course.lesson.topic.template.AnimationFragment;
import com.almanara.homeschool.student.course.lesson.topic.template.MatchingFragment;
import com.almanara.homeschool.student.course.lesson.topic.template.MultiImageQuestionFragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
    This class is for advanced level later on as it is designed to get the images like the cat image and the voice
    and that is why it has not been deleted yet
 */
public class ClassActivity extends AppCompatActivity {
    //RelativeLayout relativeLayout;
    final String RES_PREFIX = "android.resource://com.almanara.homeschool/";

    Context context;
    ViewPager pager;
    ArrayList<String> layouts;
    DatabaseReference db;
    String course_id, lesson_id;
    ArrayList<TopicModel> TopicModelList;
    ValueEventListener listener;
    ValueEventListener listener2;
    FirebaseUser user;
    FirebaseAuth firebaseAuth;
    ImageView imageView2;
    List<Fragment> fragmentList;
    MediaPlayer mediaPlayer = null;

    RoundCornerProgressBar progress1;
    int counter = 0;
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


        progress1 = (RoundCornerProgressBar) findViewById(R.id.progress_1);
        progress1.setProgressColor(Color.parseColor("#08aac7"));
        progress1.setProgressBackgroundColor(Color.parseColor("#efeeee"));
        progress1.setPadding(16);
        progress1.setMax(100);
        progress1.setProgress(0);


        pager = (ViewPager) findViewById(R.id.viewPager);
        imageView2 = (ImageView) findViewById(R.id.masha);
        Intent iin = getIntent();
        Bundle b = iin.getExtras();

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                progress1.setProgress((100*(position))/TopicModelList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        TopicModelList = new ArrayList<TopicModel>();
        if (b != null) {
            course_id = b.getString("courseId");
            lesson_id = b.getString("lessonid");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.backgroundsound);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        Log.v("Pager0000", "OnStart");
        if (pager.getChildCount() < 1) {
            listener = new ValueEventListener() {
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }

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
//                            fragmentList.add(NestedFrag.newInstance("12"));
                            fragmentList.add(MultiImageQuestionFragment
                                    .newInstance(modelEntry.getLayout()));
                        } else if (modelEntry.getTopicType().equals("animation")) {
                            fragmentList.add(AnimationFragment.newInstance(modelEntry.getLayout()));
                        } else if (modelEntry.getTopicType().equals("matching")) {
                            fragmentList.add(MatchingFragment.newInstance(modelEntry.getLayout()));
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
        if (mediaPlayer != null) {
            mediaPlayer.reset();
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
//        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.yay);
        try {
            mediaPlayer.setDataSource(getApplicationContext(), Uri.parse(RES_PREFIX + R.raw.yay));
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                imageView2.setVisibility(View.GONE);
                swipPager();
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
    }

    public void onAnswer(boolean isCorrect) {
        if (isCorrect) {
            imageView2.setVisibility(View.VISIBLE);
            final GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imageView2);
            Glide.with(this).load(R.raw.source).into(imageViewTarget);
//            if(mediaPlayer != null){
//                mediaPlayer.reset();
//            }
//            try {
//                mediaPlayer.setDataSource(getApplicationContext(),Uri.parse(RES_PREFIX + R.raw.yay));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            if(mediaPlayer.isPlaying()){
//                Log.v("ClassActivity","Playing");
//                mediaPlayer.pause();
//                mediaPlayer.start();
//            }else {
//                Log.v("ClassActivity","not Playing");
//                mediaPlayer.start();
//            }
            int mpPosition = 0;
            if (mediaPlayer != null) {
                mpPosition = mediaPlayer.getCurrentPosition();
                mediaPlayer.pause();
            }
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.yay);
            mediaPlayer.start();
            final int finalMpPosition = mpPosition;
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.backgroundsound);
                    mediaPlayer.seekTo(finalMpPosition);
                    mediaPlayer.start();
                    imageView2.setVisibility(View.GONE);

                    db.child("users").child(user.getUid()).child("enrolledcourses").addValueEventListener(listener2);
                    swipPager();
                }
            });


            listener2 = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        for (DataSnapshot d1 : d.getChildren())
                            for (DataSnapshot d2 : d1.getChildren()) {
                                ProgressModel progressModel = d2
                                        .getValue(ProgressModel.class);
                                if (progressModel.getTopicProgressId()
                                        .equals(TopicModelList
                                                .get(pager.getCurrentItem() - 2).getId())) {
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
            };
        }

    }

    public void swipPager() {
        progress1.setProgress((100*(pager.getCurrentItem()))/TopicModelList.size());
        pager.setCurrentItem(pager.getCurrentItem() + 1);

    }

}
