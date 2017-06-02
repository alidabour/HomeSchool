package com.example.ali.homeschool.childClass;

import android.content.Context;
import android.content.Intent;
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
    ValueEventListener listener;
    Context context;
    ViewPager pager;
    ArrayList<String> layouts;
    DatabaseReference db;
    CourseCreated course;
    List<LessonModel> lessonModelList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseDatabase.getInstance().getReference();
        context=getApplicationContext();
        setContentView(R.layout.activity_class_trial);
        pager = (ViewPager) findViewById(R.id.viewPager);
        Intent intent = getIntent();
        if (intent!= null && intent.hasExtra("course")){
            Log.v("Test","Intent Found");
             course = intent.getParcelableExtra("course");
            Log.v("Test","Intent Found" +  " Course "+course.getName());

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v("Test","Coursr id "+ course.getId());
        layouts=new ArrayList<String>();
        lessonModelList = new ArrayList<LessonModel>();
        listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.v("Test","ONdaatachange");
                Log.v("Test","Datasnapshot "+dataSnapshot.toString() );
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    Log.v("Test","Data Lesson" + dataSnapshot1.toString());
                    LessonModel lessonModel = dataSnapshot1.getValue(LessonModel.class);
                    lessonModelList.add(lessonModel);
                    Log.v("Test","Topics "+lessonModel.getTopics().toString());
                    Log.v("Test","Topics "+lessonModel.getTopics().size());

                }
                for (LessonModel lessonModel:lessonModelList){
                    Map<String,TopicModel> topicModelHashMap =lessonModel.getTopics();
                    for(Map.Entry<String,TopicModel> modelEntry : topicModelHashMap.entrySet()){
                        TopicModel topicModel = modelEntry.getValue();
                        Log.v("Test","Layouts " +topicModel.getLayout() );
                        layouts.add(topicModel.getLayout());
                    }
                }
                if (getSupportFragmentManager() != null){
                    pager.setAdapter(new LessonPagerAdapter(getSupportFragmentManager(),layouts));

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        db.child("courses").child(course.getId()).child("lessons").addValueEventListener(listener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        db.removeEventListener(listener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.v("LessonFragment", "Activity Result " +requestCode + " , "+ resultCode );
        super.onActivityResult(requestCode, resultCode, data);
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
