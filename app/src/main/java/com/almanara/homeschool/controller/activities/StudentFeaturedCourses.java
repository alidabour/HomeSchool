package com.almanara.homeschool.controller.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.almanara.homeschool.data.HeaderRVData;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.almanara.homeschool.data.firebase.CourseCreated;
import com.almanara.ali.homeschool.R;
import com.almanara.homeschool.adapter.CourseSectionListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

//import android.support.v4.app.LoaderManager;
//import android.support.v4.app.LoaderManager.LoaderCallbacks;
//import android.support.v4.content.Loader;
//import android.support.v4.content.CursorLoader;

/**
 * A placeholder fragment containing a simple view.
 * This is the inital fragment for the Student which contains the featured courses as well
 * as the navigation bar for his courses and settings
 */

public class StudentFeaturedCourses extends AppCompatActivity {
    View view;
    CourseSectionListAdapter courseSectionListAdapter;
    RecyclerView courseSectionRV;
    private DatabaseReference databaseReference;
    private List<CourseCreated> users;
    private List<HeaderRVData> headerRVDatas;
    public int type;
    ArrayList<String> subject = new ArrayList<>();
    private courseInterface listener;
    ArrayList<CourseCreated> random = new ArrayList<>();
    ProgressBar progressBar;
    ValueEventListener queryListener;
    DatabaseReference myRef ;
    ImageView gifImage;
    String userId="Hello";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.courses_fragment_layout);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        progressBar = (ProgressBar) findViewById(R.id.indeterminateBar);
        courseSectionRV = (RecyclerView) findViewById(R.id.category_recyclerView);
        courseSectionRV.setHasFixedSize(true);
        courseSectionRV.setLayoutManager(
                new LinearLayoutManager(StudentFeaturedCourses.this, LinearLayoutManager.VERTICAL, false));
        headerRVDatas = new ArrayList<>();
      /*  if (container != null) {
            container.removeAllViews();
        }*/
//        listener = (courseInterface) StudentFeaturedCourses.this;
        gifImage = (ImageView) findViewById(R.id.gif);
        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(gifImage);
        Glide.with(this).load(R.raw.animated).into(imageViewTarget);
        //temp.setListener(listener);
        Log.v("StudentCoursesFragment", "Test");
        Intent intent = getIntent();
        if(intent.hasExtra("userid")) {
            userId = intent.getStringExtra("userid");
            Log.v("userid",userId);
        }

        Log.v("userid",userId);

    }



    @Override
    public void onPause() {
        if(queryListener != null)
            databaseReference.removeEventListener( queryListener);
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Add value event listener to the post
        // [START post_value_event_listener]DatabaseReference myRef = databaseReference;

        progressBar.setVisibility(View.VISIBLE);
        myRef = databaseReference;
        queryListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                users = new ArrayList<>();
                random = new ArrayList<>();
                // Get Post object and use the values to update the UI
                // [START_EXCLUDE]
                for (DataSnapshot x : dataSnapshot.getChildren()) {
                    //Log.v("Test", "Child : " + x.toString());

                    CourseCreated c = x.getValue(CourseCreated.class);
                    String key = x.getKey();
                    c.setCourse_id(key);
                    users.add(c);
                    Log.v("Test", "Child : " + c.getDescriptionS());
                }
                HashMap<String, ArrayList<CourseCreated>> map = new HashMap<>();
                for (CourseCreated x : users) {
                    // subjectS.add(x.getSubjectS());
                    random.add(x);
                    ArrayList<CourseCreated> c = new ArrayList<CourseCreated>();
                    if (map.get(x.getSubjectS()) != null) {
                        c = map.get(x.getSubjectS());
                        c.add(x);
                        map.put(x.getSubjectS(), c);
                    } else {
                        c.add(x);
                        map.put(x.getSubjectS(), c);
                    }
                }
                Log.v("Test", "Map :" + map.toString());
                Iterator it = map.entrySet().iterator();
                headerRVDatas = new ArrayList<HeaderRVData>();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    headerRVDatas.add(new HeaderRVData((String) pair.getKey(),
                            (List) pair.getValue()));
                    Log.v("Test", "Map_______" + pair.getKey() + " = " + pair.getValue());
                    subject.add(pair.getKey().toString());
                    it.remove(); // avoids a ConcurrentModificationException
                }
                courseSectionListAdapter = new CourseSectionListAdapter(StudentFeaturedCourses.this,
                        headerRVDatas, 1, subject);
                courseSectionListAdapter.setUserId(userId);
                courseSectionRV.setAdapter(courseSectionListAdapter);

                // [END_EXCLUDE]
//                listener.onDataFetched(random);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
                Toast.makeText(StudentFeaturedCourses.this, "Failed to load post.",
                        Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]

            }
        };
        myRef.child("courses").addValueEventListener(queryListener);
    }

}

