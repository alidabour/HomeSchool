package com.example.ali.homeschool.childClass;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.speech.RecognizerIntent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ali.homeschool.Constants;
import com.example.ali.homeschool.InstructorHome.CourseCreated;
import com.example.ali.homeschool.InstructorLessons.LessonModel;
import com.example.ali.homeschool.InstructorTopic.TopicModel;
import com.example.ali.homeschool.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
    This class is for advanced level later on as it is designed to get the images like the cat image and the voice
    and that is why it has not been deleted yet
 */
public class ClassActivity extends AppCompatActivity  {
    //RelativeLayout relativeLayout;
    View linearLayout;
    ImageView imageView;
    Context context;
    ViewPager pager;
    ArrayList<String> layouts;
    DatabaseReference db;
    LessonModel lesson;
    String course_id , lesson_id ;
    ArrayList<TopicModel> TopicModelList;
    ValueEventListener listener ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseDatabase.getInstance().getReference();
        context=getApplicationContext();
        setContentView(R.layout.activity_class_trial);
        pager = (ViewPager) findViewById(R.id.viewPager);
        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        TopicModelList = new ArrayList<TopicModel>();
        if (b != null) {

            Log.v("Test","Intent Found");
            course_id = b.getString("courseid");
            lesson_id = b.getString("lessonid");
            Log.v("Test","Intent Found" +  " lesson "+lesson_id + " Course id " + course_id);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v("Test","Coursr id "+ "OnStart bta3t topic");
//        Log.v("Test","Listener ahu "+ listener.toString());
        //  lessonModelList = new ArrayList<LessonModel>();
        listener = new ValueEventListener() {
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

                for (TopicModel modelEntry : TopicModelList) {

                    TopicModel topicModel = modelEntry;
                    layouts.add(topicModel.getLayout());

                }

//                Map<String, TopicModel> topicModelHashMap = lesson.getTopics();
//                for (Map.Entry<String, TopicModel> modelEntry : topicModelHashMap.entrySet()) {
//                    TopicModel topicModel = modelEntry.getValue();
//                    Log.e("Test", "Layouts " + topicModel.getLayout());
//                    layouts.add(topicModel.getLayout());
//                    //  }
//                }
                //  if (getSupportFragmentManager() != null){
                pager.setAdapter(new LessonPagerAdapter(getSupportFragmentManager(), layouts));
                //layouts = new ArrayList<String>();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        db.child("courses").child(course_id).child("lessons").child(lesson_id).child("topics").addValueEventListener(listener);

    }

    @Override
    protected void onPause() {
        if(listener != null)
        db.removeEventListener(listener);
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== Constants.SPEECH){
            if(resultCode==Constants.CORRECTANSWER){

                Log.e( "ارنب", " Rabbit");
                MediaPlayer mediaPlayer= MediaPlayer.create(this,R.raw.yay);
                mediaPlayer.start();

                new CountDownTimer(4000, 1000) {
                    public void onFinish() {
                        finish();
                    }

                    public void onTick(long millisUntilFinished) {
                    }

                }.start();

                Toast.makeText(this," احسنت \n"+ data.getData().toString() ,  Toast.LENGTH_LONG).show();


            }else if (resultCode == Constants.WRONGANSWER){
                Toast.makeText(this," حاول مرة أخري \n"+ data.getData().toString() ,  Toast.LENGTH_LONG).show();

            }
        }
        Log.v("LessonFragment", "Activity Result " +requestCode + " , "+ resultCode );
        if(resultCode == Constants.CORRECTANSWER){
            if (requestCode == Constants.SIMPLE){
                Toast.makeText(context, "Result Correct", Toast.LENGTH_SHORT).show();
            }
        }
        if(resultCode == Constants.WRONGANSWER){
            if (requestCode == Constants.SIMPLE){
                Toast.makeText(context, "Result Wrong", Toast.LENGTH_SHORT).show();
            }
        }
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == Constants.SPEECH){

            }
        }
        Log.v("LessonFragment", "Activity Result " +requestCode + " , "+ resultCode );

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
//        layouts.add(cursor.getString(cursor.getColumnIndex(TopicColumns.TOPIC_LAYOUT)));
//        pager.setAdapter(new LessonPagerAdapter(getSupportFragmentManager(),layouts));
//        layouts.clear();

}
