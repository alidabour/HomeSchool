package com.example.ali.homeschool.closed;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.ali.homeschool.adapter.CategoryAdapter;
import com.example.ali.homeschool.data.CategoryInformation;
import com.example.ali.homeschool.controller.activities.CourseDescriptionActivity;
import com.example.ali.homeschool.R;
import com.example.ali.homeschool.RecyclerTouchListener;
import com.example.ali.homeschool.adapter.SampleCoursesToolbarAdapter;

import java.util.ArrayList;
import java.util.List;

public class Main3Activitytrial______________ extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    List<CategoryInformation> categoryInformationList;
    Intent intent;
    FrameLayout frame ;
   // private static final int ID = 121;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3trial);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        frame = new FrameLayout(this);
        frame.setId(R.id.main_content);
        intent=getIntent();
        intent.putExtra("type",1);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final ViewPager mViewPager = (ViewPager) findViewById(R.id.viewPage_collapsing_toolbar);
        SampleCoursesToolbarAdapter imageCollapsingToolBarAdapter = new SampleCoursesToolbarAdapter(this);
        mViewPager.setAdapter(imageCollapsingToolBarAdapter);
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                Log.v("Test","Pager Touch");
                return false;
            }
        });

        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mViewPager.getParent().requestDisallowInterceptTouchEvent(true);
            }
        });

        categoryInformationList = new ArrayList<CategoryInformation>();
        CategoryInformation categoryInformation= new CategoryInformation("Arabic",R.drawable.earlymath);
        categoryInformationList.add(categoryInformation);
        categoryInformation = new  CategoryInformation("English",R.drawable.earlymath);
        categoryInformationList.add(categoryInformation);
        categoryInformation = new  CategoryInformation("Math",R.drawable.earlymath);
        categoryInformationList.add(categoryInformation);
        categoryInformation = new  CategoryInformation("Math",R.drawable.earlymath);
        categoryInformationList.add(categoryInformation);
        categoryInformation = new  CategoryInformation("Math",R.drawable.earlymath);
        categoryInformationList.add(categoryInformation);
        categoryInformation = new  CategoryInformation("Math",R.drawable.earlymath);
        categoryInformationList.add(categoryInformation);
        categoryInformation = new  CategoryInformation("Math",R.drawable.earlymath);
        categoryInformationList.add(categoryInformation);
        categoryInformation = new  CategoryInformation("Math",R.drawable.earlymath);
        categoryInformationList.add(categoryInformation);

        RecyclerView categoryRecycleView = (RecyclerView)findViewById(R.id.category_recyclerView);
        categoryRecycleView.setHasFixedSize(true);
        LinearLayoutManager categoryLayoutManger = new LinearLayoutManager(Main3Activitytrial______________.this,LinearLayoutManager.HORIZONTAL,false);
//        categoryLayoutManger.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecycleView.setLayoutManager(categoryLayoutManger);
        CategoryAdapter categoryAdapter = new CategoryAdapter(categoryInformationList, new CategoryAdapter.OnClickHandler() {
            @Override
            public void onClick(String test) {

            }
        });
        categoryRecycleView.setAdapter(categoryAdapter);
        categoryRecycleView.addOnItemTouchListener(new RecyclerTouchListener(this, categoryRecycleView, new RecyclerTouchListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getApplicationContext(),CourseDescriptionActivity.class);
                        intent.putExtra("type",1);

                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );

        RecyclerView c2 = (RecyclerView)findViewById(R.id.c22);
        c2.setHasFixedSize(true);
        LinearLayoutManager cm2 = new LinearLayoutManager(Main3Activitytrial______________.this,LinearLayoutManager.HORIZONTAL,false);
        //cm2.setOrientation(LinearLayoutManager.HORIZONTAL);
        c2.setLayoutManager(cm2);
        CategoryAdapter ca = new CategoryAdapter(categoryInformationList, new CategoryAdapter.OnClickHandler() {
            @Override
            public void onClick(String test) {

            }
        });
        c2.setAdapter(ca);

        RecyclerView c3= (RecyclerView)findViewById(R.id.c33);
        c3.setHasFixedSize(true);
        LinearLayoutManager cm3 = new LinearLayoutManager(Main3Activitytrial______________.this,LinearLayoutManager.HORIZONTAL,false);
        //cm2.setOrientation(LinearLayoutManager.HORIZONTAL);
        c3.setLayoutManager(cm3);
        CategoryAdapter ca3 = new CategoryAdapter(categoryInformationList, new CategoryAdapter.OnClickHandler() {
            @Override
            public void onClick(String test) {

            }
        });
        c3.setAdapter(ca3);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main3, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.coursesNav) {

        } else if (id == R.id.featuredCoursesNaV) {

        } else if (id == R.id.settings) {

        } else if (id == R.id.signout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
