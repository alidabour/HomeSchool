package com.example.ali.homeschool.controller.activities;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import com.example.ali.homeschool.R;
import com.example.ali.homeschool.adapter.TopicsAdapter;
import com.example.ali.homeschool.adapter.TopicsFirebaseAdapter;
import com.example.ali.homeschool.data.DataProvider;
import com.example.ali.homeschool.data.Entry.CourseColumns;
import com.example.ali.homeschool.data.Entry.LessonColumns;
import com.example.ali.homeschool.data.firebase.Courses;
import com.example.ali.homeschool.data.firebase.Lessons;
import com.example.ali.homeschool.data.firebase.Topics;
import com.example.ali.homeschool.data.firebase.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
This is the class which i use to get the description of the course from the click listener
and supposely later on i would use the data base to fetch this data
 */
public class CourseDescriptionActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>  {
    Toolbar toolbar;
    ListView topicsListView;
    RecyclerView topicsRecyclerView;
    TopicsAdapter topicsAdapter;
   // TableLayout topicsTable;
   // Intent intent;
    Button enroll;
    TopicsFirebaseAdapter topicsFirebaseAdapter;
    private static final int CURSOR_LOADER_ID_DES = 1;
    private static final int CURSOR_LOADER_ID_TOPIC = 2;
    ImageView courseImage;
    TextView courseTeacher;
    TextView courseName;
    RatingBar courseRating;
    TextView courseRatingText;
    TextView courseDescription;
    private DatabaseReference databaseReference;
    private List<String> lessons;
    private List<Topics> topics;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;

    private String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_course_description);

        databaseReference = FirebaseDatabase.getInstance().getReference();


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Animals Vol. 1");
        enroll = (Button) findViewById(R.id.enroll);
        courseImage = (ImageView) findViewById(R.id.imageView);
        courseTeacher  = (TextView) findViewById(R.id.textView);
        courseName = (TextView) findViewById(R.id.textView2);
        courseRating = (RatingBar) findViewById(R.id.ratingBar);
        courseRatingText = (TextView) findViewById(R.id.textView3);
        courseDescription = (TextView) findViewById(R.id.textView5);
        topicsRecyclerView = (RecyclerView) findViewById(R.id.listViewDes);
       topicsRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        Intent intent = getIntent();
        if (intent.hasExtra("course")){
            Courses course = intent.getParcelableExtra("course");
            courseDescription.setText(course.getDescription());
            courseName.setText(course.getName());
            courseTeacher.setText(course.getTeacher());
            courseRatingText.setText(course.getRate());
            key = course.getCourse_id();
            Log.v("Test", "Child : " + key);
        }

        setSupportActionBar(toolbar);
        // this line supports the back button to go back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getLoaderManager().initLoader(CURSOR_LOADER_ID_DES, null, (LoaderManager.LoaderCallbacks<Cursor>) this);
        getLoaderManager().initLoader(CURSOR_LOADER_ID_TOPIC, null, (LoaderManager.LoaderCallbacks<Cursor>) this);

        List<String> names = new ArrayList<>();

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        final int type = intent.getIntExtra("type",0);
        enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> data= new HashMap<String, Object>();
                data.put("EnrolledCourses","2");
                databaseReference.child("users").child(user.getUid()).updateChildren(data);
                Log.e("onClick: ",data+ " "   );
                if(type == 0){
                    Toast.makeText(CourseDescriptionActivity.this, getResources().getString(R.string.youMustSignIn), Toast.LENGTH_SHORT).show();
                }
                else if (type == 1 ){
                    Toast.makeText(CourseDescriptionActivity.this, getResources().getString(R.string.enrollDone), Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Add value event listener to the post
        // [START post_value_event_listener]DatabaseReference myRef = databaseReference;
        DatabaseReference myRef = databaseReference;
        myRef.child("lessons").orderByChild("course_id").equalTo(key).addValueEventListener(
                new ValueEventListener() {
                    public static final String TAG = "EmailPassword";

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        lessons = new ArrayList<>();
                        // Get Post object and use the values to update the UI
                        // [START_EXCLUDE]
                        for (DataSnapshot x : dataSnapshot.getChildren()) {
                            Log.v("Test", "Child : " + x.toString());
                            Lessons c = x.getValue(Lessons.class);
                            c.setLesson_id(x.getKey());
                            lessons.add(c.getLesson_id());
                            Log.v("Test", "Child : " + lessons);
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Getting Post failed, log a message
                        Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                        // [START_EXCLUDE]
                        Toast.makeText(getApplicationContext(), "Failed to load post.",
                                Toast.LENGTH_SHORT).show();
                        // [END_EXCLUDE]
                    }
                });

//            for(Lessons x : lessons)
//            myRef.child("topics").orderByChild("lesson_id").equalTo(x.getLesson_id()).addValueEventListener(
                myRef.child("topics").orderByChild("lesson_id").addValueEventListener(
                    new ValueEventListener() {
                    public static final String TAG = "EmailPassword";

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        topics = new ArrayList<>();
                        // Get Post object and use the values to update the UI
                        // [START_EXCLUDE]
                        for (DataSnapshot x : dataSnapshot.getChildren()) {
                            Log.v("Test", "Child : " + x.toString());
                            Topics c = x.getValue(Topics.class);
                            if(lessons.contains(c.getLesson_id())){
                                topics.add(c);
                                Log.v("Test","---------------Topic Name : "+c.getName());
                            }
//                            Log.v("Test", "Child : " + topics);
                        }
//                        HashMap<String, ArrayList<Courses>> map = new HashMap<>();
//                        for (Courses x : lessons) {
//                            ArrayList<Courses> c = new ArrayList<Courses>();
//                            if (map.get(x.getSubject()) != null) {
//                                c = map.get(x.getSubject());
//                                c.add(x);
//                                map.put(x.getSubject(), c);
//                            } else {
//                                c.add(x);
//                                map.put(x.getSubject(), c);
//                            }
//                        }
                        Log.e("onDataChange: " ,"" +topics.size());


                        topicsFirebaseAdapter = new TopicsFirebaseAdapter(topics);
                        topicsRecyclerView.setAdapter(topicsFirebaseAdapter);
//                        courseSectionListAdapter = new CourseSectionListAdapter(getActivity(), headerRVDatas);
//                        courseSectionRV.setAdapter(courseSectionListAdapter);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Getting Post failed, log a message
                        Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                        // [START_EXCLUDE]
                        Toast.makeText(getApplicationContext(), "Failed to load post.",
                                Toast.LENGTH_SHORT).show();
                        // [END_EXCLUDE]
                    }
                });






    }
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        if(i == CURSOR_LOADER_ID_DES ){
            //Description
            CursorLoader loader = new CursorLoader(getApplicationContext(),DataProvider.Course.CONTENT_URI,
                    new String[]{CourseColumns._ID,CourseColumns.GLOBAL_ID,CourseColumns.COURSE_DES
                            ,CourseColumns.COURSE_IMG,CourseColumns.COURSE_NAME,CourseColumns.COURSE_RATINGS,
                    CourseColumns.COURSE_TEACHER}
            ,null,null,null) ;
            return loader;
        }else if(i == CURSOR_LOADER_ID_TOPIC){
            //Topics
            CursorLoader loader = new CursorLoader(getApplicationContext(), DataProvider.Lesson.CONTENT_URI,
                    new String[]{LessonColumns._ID,LessonColumns.LESSON_NAME,LessonColumns.LESSON_NUMBER}
            ,null,null,null);
            return loader;
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if(loader.getId()==CURSOR_LOADER_ID_DES){
            Log.v("Test","Description Count :" + cursor.getCount());
//            cursor.moveToFirst();
//            courseTeacher.setText(cursor.getString(cursor.getColumnIndex(CourseColumns.COURSE_TEACHER)));
//            courseName.setText(cursor.getString(cursor.getColumnIndex(CourseColumns.COURSE_NAME)));
//            courseRating.setProgress(cursor.getInt(cursor.getColumnIndex(CourseColumns.COURSE_RATINGS)));
////            courseRatingText.setText(cursor.getInt(cursor.getColumnIndex(CourseColumns.COURSE_RATINGS))+
////                    getString(R.string.ratings));
//            courseRatingText.setText(cursor.getInt(cursor.getColumnIndex(CourseColumns.COURSE_RATINGS))
//                    +" Ratings");
//
//            courseDescription.setText(cursor.getString(cursor.getColumnIndex(CourseColumns.COURSE_DES)));

//        } else if(loader.getId() == CURSOR_LOADER_ID_TOPIC){
//            Log.v("Test","Topics Count :"+ cursor.getCount());
//            topicsAdapter.swapCursor(cursor);
//            topicsRecyclerView.setAdapter(topicsAdapter);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

}
