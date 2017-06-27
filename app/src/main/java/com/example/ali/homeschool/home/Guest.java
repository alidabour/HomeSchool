package com.example.ali.homeschool.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ali.homeschool.instructor.home.CourseCreated;
import com.example.ali.homeschool.R;
import com.example.ali.homeschool.adapter.CourseSectionListAdapter;
import com.example.ali.homeschool.adapter.SampleCoursesToolbarAdapter;
import com.example.ali.homeschool.register.Register;
import com.example.ali.homeschool.data.CategoryInformation;
import com.example.ali.homeschool.data.HeaderRVData;
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

// read comments of StudentHomeActivity for ref
public class Guest extends AppCompatActivity {
    List<CategoryInformation> categoryInformationList;
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    CourseSectionListAdapter courseSectionListAdapter;
    RecyclerView courseSectionRV;
    private DatabaseReference databaseReference;
    private List<CourseCreated> users;
    private List<HeaderRVData> headerRVDatas;
    public int type;
    ViewPager mViewPager;
    SampleCoursesToolbarAdapter imageCollapsingToolBarAdapter;
    ArrayList<String> subject = new ArrayList<>();
    ArrayList<CourseCreated> random = new ArrayList<>();
    ProgressBar progressBar ;
    ValueEventListener listener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guest_activity);

        progressBar = (ProgressBar) findViewById(R.id.guest_progressbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        setSupportActionBar(toolbar);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        courseSectionRV = (RecyclerView) findViewById(R.id.category_recyclerView);
        courseSectionRV.setHasFixedSize(true);
        courseSectionRV.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        headerRVDatas = new ArrayList<>();

        mViewPager = (ViewPager) findViewById(R.id.viewPage_collapsing_toolbar);

        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                Log.v("Test", "Pager Touch");
                return false;
            }
        });

        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mViewPager.getParent().requestDisallowInterceptTouchEvent(true);
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.signUp_action:
                        Log.v("Test","Sign IN");
                        startActivity(new Intent(getBaseContext() , Register.class));

                        break;
                    case R.id.search_action:
                        Log.v("Test","Search");
                        break;
                }
                return true;            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.home_menu, menu);
        MenuItem item = menu.findItem(R.id.search_action);
        SearchView searchView = (SearchView)item.getActionView();
        Log.v("Searchview" , searchView+"");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    protected void onPause(){
        if (listener != null)
            databaseReference.removeEventListener(listener);
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();

        progressBar.setVisibility(View.VISIBLE);
        DatabaseReference myRef = databaseReference;
        listener = new ValueEventListener() {
                    public static final String TAG = "EmailPassword";

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        users = new ArrayList<>();
                        // Get Post object and use the values to update the UI
                        // [START_EXCLUDE]
                        for (DataSnapshot x : dataSnapshot.getChildren()) {
                            Log.v("Test", "Child : " + x.toString());
                            CourseCreated c = x.getValue(CourseCreated.class);
                            String key = x.getKey();
                            c.setCourse_id(key);
                            users.add(c);
                            Log.v("Test", "Child : " + users);
                        }
                        HashMap<String, ArrayList<CourseCreated>> map = new HashMap<>();
                        for (CourseCreated x : users) {
                           // subjectS.add(x.getSubjectS());
                            //Log.e("Subject", x.getSubjectS());

                            random.add(x);
                            ArrayList<CourseCreated> c = new ArrayList<CourseCreated>();
                            if (map.get(x.getSubjectS()) != null) {
                                c = map.get(x.getSubjectS());
                                c.add(x);
                                map.put(x.getSubjectS(), c);
                                Log.e("Subject1", x.getSubjectS());

                            } else {
                                c.add(x);
                                map.put(x.getSubjectS(), c);
                                Log.e("Subject2", x.getSubjectS());
                                Log.e("Name", x.getName());
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
                        imageCollapsingToolBarAdapter = new SampleCoursesToolbarAdapter(Guest.this  , random);
                        mViewPager.setAdapter(imageCollapsingToolBarAdapter);
                        courseSectionListAdapter = new CourseSectionListAdapter(getApplicationContext(),
                                headerRVDatas,0,subject);
                        courseSectionRV.setAdapter(courseSectionListAdapter);
                        // [END_EXCLUDE]
                        progressBar.setVisibility(View.INVISIBLE);

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
                };

        databaseReference.child("courses").addValueEventListener(listener);
    }
    }

