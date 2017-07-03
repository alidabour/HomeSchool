package com.almanara.homeschool.controller.activities;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.almanara.ali.homeschool.R;
import com.almanara.homeschool.adapter.CourseSectionListAdapter;
import com.almanara.homeschool.adapter.SampleCoursesToolbarAdapter;
import com.almanara.homeschool.data.HeaderRVData;
import com.almanara.homeschool.data.firebase.CourseCreated;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
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
import java.util.Timer;
import java.util.TimerTask;

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

public class StudentFeaturedCourses extends AppCompatActivity implements courseInterface{
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
    String mychild="no";

    ViewPager mViewPager;
    ArrayList<CourseCreated> courseList ;

    SampleCoursesToolbarAdapter imageCollapsingToolBarAdapter;
    AppBarLayout appBarLayout;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.courses_fragment_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.Courses);
        mViewPager = (ViewPager) findViewById(R.id.viewPage_collapsing_toolbar);
        appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
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
        listener = (courseInterface) StudentFeaturedCourses.this;
        gifImage = (ImageView) findViewById(R.id.gif);
        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(gifImage);
        Glide.with(this).load(R.raw.animated).into(imageViewTarget);
        //temp.setListener(listener);
        Log.v("StudentCoursesFragment", "Test");
        Intent intent = getIntent();
        if(intent.hasExtra("userid")) {
            userId = intent.getStringExtra("userid");
            mychild= intent.getStringExtra("mychild");
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
                    if(pair.getKey() !=null && pair.getValue()!=null){
                        headerRVDatas.add(new HeaderRVData((String) pair.getKey(),
                                (List) pair.getValue()));
                        Log.v("Test", "Map_______" + pair.getKey() + " = " + pair.getValue());
                        subject.add(pair.getKey().toString());
                        it.remove(); // avoids a ConcurrentModificationException
                    }

                }
                courseSectionListAdapter = new CourseSectionListAdapter(StudentFeaturedCourses.this,
                        headerRVDatas, 1, subject);
                courseSectionListAdapter.setUserId(userId,mychild);
                courseSectionRV.setAdapter(courseSectionListAdapter);

                // [END_EXCLUDE]
            //    if(random!=null)
                listener.onDataFetched(random);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
                Toast.makeText(StudentFeaturedCourses.this, R.string.failure_to_load,
                        Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]

            }
        };
        myRef.child("courses").addValueEventListener(queryListener);
    }

    Timer timer;
    @Override
    public void onDataFetched(final ArrayList courses) {
        courseList = courses ;
        imageCollapsingToolBarAdapter = new SampleCoursesToolbarAdapter(this , courses );
        mViewPager.setAdapter(imageCollapsingToolBarAdapter);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                mViewPager.post(new Runnable(){

                    @Override
                    public void run() {
                        mViewPager.setCurrentItem((mViewPager.getCurrentItem()+1)%courses.size());
                    }
                });
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 45000, 45000);
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);

                switch(event.getAction()){
                    case MotionEvent.ACTION_MOVE:

                        Log.e("viewPager" ,mViewPager.getCurrentItem() +"");

                        return false; //This is important, if you return TRUE the action of swipe will not take place.

                }
                return false;
            }
        });


        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mViewPager.getParent().requestDisallowInterceptTouchEvent(true);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        });



    }
}


