package com.example.ali.homeschool.controller.activities;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ali.homeschool.InstructorHome.CourseCreated;
import com.example.ali.homeschool.R;
import com.example.ali.homeschool.adapter.CourseSectionListAdapter;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guest_activity);

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
//        Log.v("StudentCoursesFragment","Test");
//        categoryInformationList = new ArrayList<CategoryInformation>();
//        CategoryInformation categoryInformation= new CategoryInformation("Arabic",R.drawable.java);
//        categoryInformationList.add(categoryInformation);
//        categoryInformation = new  CategoryInformation("English",R.drawable.java);
//        categoryInformationList.add(categoryInformation);
//        categoryInformation = new  CategoryInformation("Math",R.drawable.java);
//        categoryInformationList.add(categoryInformation);
//        categoryInformation = new  CategoryInformation("Math",R.drawable.java);
//        categoryInformationList.add(categoryInformation);
//        categoryInformation = new  CategoryInformation("Math",R.drawable.java);
//        categoryInformationList.add(categoryInformation);
//        categoryInformation = new  CategoryInformation("Math",R.drawable.java);
//        categoryInformationList.add(categoryInformation);
//        categoryInformation = new  CategoryInformation("Math",R.drawable.java);
//        categoryInformationList.add(categoryInformation);
//        categoryInformation = new  CategoryInformation("Math",R.drawable.java);
//        categoryInformationList.add(categoryInformation);
//        toolbar= (Toolbar)findViewById(R.id.mToolbar);
//        toolbar.inflateMenu(R.menu.home_menu);
//        Log.v("Test","Inf :"+categoryInformation.getCategoryName());
//        for (CategoryInformation x : categoryInformationList){
//            Log.v("Test","Inf"+x.getCategoryName());
//        }
//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                switch (item.getItemId()){
//                    case R.id.signIn_action:
//                        Log.v("Test","Sign IN");
//                        break;
//                    case R.id.search_action:
//                        Log.v("Test","Search");
//                        break;
//                }
//                return true;
//            }
//        });
//
//        RecyclerView categoryRecycleView = (RecyclerView)findViewById(R.id.category_recyclerView);
//        categoryRecycleView.setHasFixedSize(true);
//        LinearLayoutManager categoryLayoutManger = new LinearLayoutManager(Guest.this,LinearLayoutManager.HORIZONTAL,false);
////        categoryLayoutManger.setOrientation(LinearLayoutManager.HORIZONTAL);
//        categoryRecycleView.setLayoutManager(categoryLayoutManger);
//
//        RecyclerView c3= (RecyclerView)findViewById(R.id.c33);
//        c3.setHasFixedSize(true);
//        LinearLayoutManager cm3 = new LinearLayoutManager(Guest.this,LinearLayoutManager.HORIZONTAL,false);
//        //cm2.setOrientation(LinearLayoutManager.HORIZONTAL);
//        c3.setLayoutManager(cm3);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.signUp_action:
                        Log.v("Test","Sign IN");
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

        return true;
    }



    @Override
    protected void onStart() {
        super.onStart();

        DatabaseReference myRef = databaseReference;
        myRef.child("courses").addValueEventListener(
                new ValueEventListener() {
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
                        while (it.hasNext()) {
                            Map.Entry pair = (Map.Entry) it.next();
                            headerRVDatas.add(new HeaderRVData((String) pair.getKey(),
                                    (List) pair.getValue()));
                            Log.v("Test", "Map_______" + pair.getKey() + " = " + pair.getValue());
                            it.remove(); // avoids a ConcurrentModificationException
                        }
                        courseSectionListAdapter = new CourseSectionListAdapter(getApplicationContext(),
                                headerRVDatas,0);
                        courseSectionRV.setAdapter(courseSectionListAdapter);
                        // [END_EXCLUDE]
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
    }

