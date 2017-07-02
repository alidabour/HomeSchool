package com.almanara.homeschool.descriptionActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.almanara.homeschool.data.firebase.EnrolledCourseModel;
import com.almanara.homeschool.data.firebase.LessonModel;
import com.bumptech.glide.Glide;
import com.almanara.homeschool.data.firebase.CourseCreated;
import com.almanara.homeschool.data.firebase.TopicModel;
import com.almanara.ali.homeschool.R;
import com.almanara.homeschool.adapter.TopicsAdapter;
import com.almanara.homeschool.data.firebase.ProgressModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static com.almanara.ali.homeschool.R.id.textView;

/*
This is the class which i use to get the descriptionS of the course from the click listener
and supposely later on i would use the data base to fetch this data
 */
public class CourseDescriptionActivity extends AppCompatActivity {
    Toolbar toolbar;
    String mychild ="no";
    ListView topicsListView;
    RecyclerView topicsRecyclerView;
    Button enroll;
    ImageView courseImage;
    TextView courseTeacher;
    TextView courseName;
    RatingBar courseRating;
    TextView courseDescription;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private String key;
    LessonModel lessonModel;
    CourseCreated courseCreated;
    EnrolledCourseModel courseCreated1;
    boolean courseExists ;
    DatabaseReference myRef1;
    DatabaseReference myRef2;
    TopicModel topicModel;
    FloatingActionButton fab;
    NestedScrollView nestedScrollView;
    ProgressModel progresstrial;
    ValueEventListener listener1;
    ValueEventListener listener2;
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        courseExists = false;
        setContentView(R.layout.activity_course_description);
        progresstrial = new ProgressModel();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        myRef1 = databaseReference;
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        Drawable drawable = ratingBar.getProgressDrawable();
        drawable.setColorFilter(Color.parseColor("#0C75DE"),PorterDuff.Mode.SRC_ATOP);

        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
//        fab = (FloatingActionButton) findViewById(R.id.fab);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.animal_vlaue1);
        enroll = (Button) findViewById(R.id.enroll);
        courseImage = (ImageView) findViewById(R.id.imageView);
        courseTeacher = (TextView) findViewById(textView);
        courseName = (TextView) findViewById(R.id.textView2);
        courseRating = (RatingBar) findViewById(R.id.ratingBar);
        courseDescription = (TextView) findViewById(R.id.textView5);
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.courseDescriptionCoordinatorLayout);
        topicsRecyclerView = (RecyclerView) findViewById(R.id.listViewDes);
        topicsRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        Intent intent = getIntent();

        final int type = intent.getIntExtra("type", 0);
        if (intent.hasExtra("course")) {
            courseCreated = intent.getParcelableExtra("course");
            courseDescription.setText(courseCreated.getDescriptionS());
            courseName.setText(courseCreated.getName());
            courseTeacher.setText(courseCreated.getTeacher_name());
            courseRating.setRating(Float.parseFloat(courseCreated.getRate()));
            Glide.with(getApplicationContext()).load(courseCreated.getPhoto_url()).fitCenter().into(courseImage);
            key = courseCreated.getCourse_id();
            toolbar.setTitle(courseCreated.getName());
            Log.v("Test", "Child : " + courseCreated.getName());
        }
        if(intent.hasExtra("userid")){
            userid = intent.getStringExtra("userid");
            mychild=intent.getStringExtra("mychild");
            Log.e(TAG, "onCreate: _____________"+mychild );
            Log.v("ElidZaahr",userid);
        }
        else if(type != 0)
            userid = user.getUid();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        List<String> names = new ArrayList<>();

        enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type != 0) {

                    listener1 = new ValueEventListener() {
                                        boolean flag = false;
                                        int count = 0;
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            if (!courseExists)
                                                for (DataSnapshot d : dataSnapshot.getChildren()) {
                                                    courseCreated1 = d.getValue(EnrolledCourseModel.class);
                                                    if (courseCreated.getCourse_id().equals(courseCreated1.getCourse_id())) {
                                                        courseExists = true;
                                                    }
                                                }
                                                if(!courseExists) {
                                                    myRef1 = databaseReference;
                                                    final EnrolledCourseModel enrolledCourseModel = new EnrolledCourseModel();
                                                    enrolledCourseModel.setName(courseCreated.getName());
                                                    enrolledCourseModel.setCourse_id(key);
                                                    Log.v("enrolledCourseModel ", userid + " " + enrolledCourseModel.getName() + " ");
                                                    String key = myRef1.child("users").child(userid).child("enrolledcourses").push().getKey();
                                                    enrolledCourseModel.setEnrolledcoursemodelid(key);
                                                    myRef1.child("users").child(userid).child("enrolledcourses").child(key).setValue(enrolledCourseModel);
                                                    String key2 = myRef1.child("users").child(userid).child("enrolledcourses").child(key).child("progress").push().getKey();

                                                    courseExists = true;
                                                    if(mychild.equals("hello"))
                                                        Toast.makeText(CourseDescriptionActivity.this, R.string.sign_in, Toast.LENGTH_SHORT).show();
                                                    else
                                                        Toast.makeText(CourseDescriptionActivity.this, R.string.sign_in, Toast.LENGTH_SHORT).show();

                                                    databaseReference.child("courses").child(enrolledCourseModel.getCourse_id()).child("lessons").addValueEventListener(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                                            for (DataSnapshot d1 : dataSnapshot.getChildren()) {
                                                                for (DataSnapshot d2 : d1.getChildren()) {
                                                                    for (DataSnapshot d3 : d2.getChildren()) {
                                                                        TopicModel topicModel = d3.getValue(TopicModel.class);
                                                                        if(topicModel.getQuestion().equals("true")){
                                                                            myRef1 = databaseReference;
                                                                            String key2 = myRef1.child("users").child(userid).child("enrolledcourses").child(enrolledCourseModel.getEnrolledcoursemodelid()).child("progress").push().getKey();
                                                                            myRef1 = myRef1.child("users").child(userid).child("enrolledcourses").child(enrolledCourseModel.getEnrolledcoursemodelid()).child("progress");
                                                                            progresstrial.setTopicProgressFlag("false");
                                                                            progresstrial.setTopicProgressId(topicModel.getId());
                                                                            progresstrial.setProgressid(key2);
                                                                            progresstrial.setEnrolledcourseid(enrolledCourseModel.getEnrolledcoursemodelid());
                                                                            Log.v("TopicModel",topicModel.getQuestion());
                                                                            Log.v("TopicModel",topicModel.getId());
                                                                            Log.v("TopicModel",topicModel.getName());
                                                                            Log.v("TopicModel",topicModel.getLayout());
                                                                            Log.v("TopicModel",topicModel.getTopicType());
                                                                            myRef1.child(key2).updateChildren(progresstrial.toMap());
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                            }


                                                        @Override
                                                        public void onCancelled(DatabaseError databaseError) {

                                                        }
                                                    });
                                                }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    };
                    databaseReference.child("users").child(userid).child("enrolledcourses").addValueEventListener(listener1);

                } else
                    Toast.makeText(CourseDescriptionActivity.this, R.string.sign_in_correct, Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    protected void onPause(){
        if (listener1 != null)
            databaseReference.removeEventListener(listener1);
        if (listener2 != null)
            databaseReference.removeEventListener(listener2);
        super.onPause();
    }
    @Override
    public void onStart() {
        super.onStart();
        // Add value event listener to the post
        // [START post_value_event_listener]DatabaseReference myRef = databaseReference;

           listener2 =      new ValueEventListener() {
                    List<TopicModel> lessonModelList;

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.v("Testing", "Lesson " + dataSnapshot.toString());
                        lessonModelList = new ArrayList<TopicModel>();
                        for (DataSnapshot d1 : dataSnapshot.getChildren()) {
                            Log.e("datasnapshot: ", d1 +"");
                            lessonModel = d1.getValue(LessonModel.class);
                            for (DataSnapshot d2 : d1.getChildren()) {
                                for (DataSnapshot d3 : d2.getChildren()) {
                                    Log.e("d2ooool: ", d3.toString());
                                    topicModel = d3.getValue(TopicModel.class);
                                    Log.e("Topic Model: ", topicModel.getName() + "");
                                    lessonModelList.add(topicModel);
                                }
                            }
                        }
                        TopicsAdapter topicAdapter
                                = new TopicsAdapter(lessonModelList,
                                new TopicsAdapter.OnClickHandler() {
                                    @Override
                                    public void onClick(TopicModel test) {

                                    }
                                });
                        topicsRecyclerView.setAdapter(topicAdapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                };



        listener1 = new ValueEventListener() {
            int count = 0;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!courseExists)
                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        Log.e("ehel8oldah ", d + "");
                        courseCreated1 = d.getValue(EnrolledCourseModel.class);
                        Log.e("Etl3e b2a: ", courseCreated.getCourse_id());
                        Log.e("Etl3e b2a enta eltani: ", courseCreated1.getCourse_id());
                        if (courseCreated.getCourse_id().equals(courseCreated1.getCourse_id())) {
                            Log.e("Etl3e b2a: ", courseCreated.getCourse_id());
                            Log.e("Etl3e b2a enta eltani: ", courseCreated1.getCourse_id());
                            courseExists = true;
                        }
                    }
                if(courseExists) {
                   enroll.setText("Enrolled");
                    enroll.setBackgroundColor((ContextCompat.getColor(CourseDescriptionActivity.this, R.color.colorLesson)));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        if(userid!=null)
        databaseReference.child("users").child(userid).child("enrolledcourses").addValueEventListener(listener1);
        databaseReference.child("courses").child(String.valueOf(courseCreated.getCourse_id())).child("lessons").addValueEventListener(listener2);
//
////            for(Lessons x : lessonsID)
////            myRef.child("topics").orderByChild("lesson_id").equalTo(x.getLesson_id()).addValueEventListener(
//                databaseReference.child("topics").orderByChild("lesson_id").addValueEventListener(
//                    new ValueEventListener() {
//                    public static final String TAG = "EmailPassword";
//
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        topics = new ArrayList<>();
//                        // Get Post object and use the values to update the UI
//                        // [START_EXCLUDE]
//                        for (DataSnapshot x : dataSnapshot.getChildren()) {
//                            Log.v("Test", "Child : " + x.toString());
//                            Topics c = x.getValue(Topics.class);
//                            if(lessonsID.contains(c.getLesson_id())){
//                                topics.add(c);
//                                Log.v("Test","---------------Topic Name : "+c.getName());
//                            }
////                            Log.v("Test", "Child : " + topics);
//                        }
////                        HashMap<String, ArrayList<Courses>> map = new HashMap<>();
////                        for (Courses x : lessonsID) {
////                            ArrayList<Courses> c = new ArrayList<Courses>();
////                            if (map.get(x.getSubjectS()) != null) {
////                                c = map.get(x.getSubjectS());
////                                c.add(x);
////                                map.put(x.getSubjectS(), c);
////                            } else {
////                                c.add(x);
////                                map.put(x.getSubjectS(), c);
////                            }
////                        }
//                        Log.e("onDataChange: " ,"" +topics.size());
//
//                        topicsFirebaseAdapter = new TopicsFirebaseAdapter(topics);
//                        topicsRecyclerView.setAdapter(topicsFirebaseAdapter);
////                        courseSectionListAdapter = new CourseSectionListAdapter(getActivity(), headerRVDatas);
////                        courseSectionRV.setAdapter(courseSectionListAdapter);
//
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                        // Getting Post failed, log a message
//                        Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
//                        // [START_EXCLUDE]
//                        Toast.makeText(getApplicationContext(), "Failed to load post.",
//                                Toast.LENGTH_SHORT).show();
//                        // [END_EXCLUDE]
//                    }
//                });


    }



}
