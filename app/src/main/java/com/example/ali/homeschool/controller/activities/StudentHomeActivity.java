package com.example.ali.homeschool.controller.activities;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.example.ali.homeschool.R;
import com.example.ali.homeschool.adapter.SampleCoursesToolbarAdapter;
import com.example.ali.homeschool.controller.fragments.MyCoursesFragment;
import com.example.ali.homeschool.controller.fragments.StudentFeaturedCoursesFragment;

/*
    We enter this class from the Student image button as it has more than one fragment
    it contains the navigation bar and we use the fragments and replace them
    to get the desired fragment
 */
public class StudentHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public int type;
    ViewPager mViewPager;
    CollapsingToolbarLayout collapsingToolbarLayout;
    AppBarLayout appBarLayout;
    SampleCoursesToolbarAdapter imageCollapsingToolBarAdapter;
    Intent intent;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        setting the Content View
        setting the toolbar and collapsing toolbar
        setting the drawer layout
        setting the appbar layout
        setting the navigation view for menu item click listener
         */
        setContentView(R.layout.courseslayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mViewPager = (ViewPager) findViewById(R.id.viewPage_collapsing_toolbar);
        imageCollapsingToolBarAdapter = new SampleCoursesToolbarAdapter(this);
        mViewPager.setAdapter(imageCollapsingToolBarAdapter);
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
//                Log.v("Test", "Pager Touch");
                return false;
            }
        });

        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mViewPager.getParent().requestDisallowInterceptTouchEvent(true);
            }
        });

    }

    /*
     this is used to close the drawer (Navigation Bar)
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    //
    //    @Override
    //    public boolean onOptionsItemSelected(MenuItem item) {
    //        // Handle action bar item clicks here. The action bar will
    //        // automatically handle clicks on the Home/Up button, so long
    //        // as you specify a parent activity in AndroidManifest.xml.
    //        int id = item.getItemId();
    //
    //        //noinspection SimplifiableIfStatement
    //        if (id == R.id.action_settings) {
    //            return true;
    //        }
    //
    //        return super.onOptionsItemSelected(item);
    //    }


    // Here we choose which part in the navigation menu is being selected
    // and depending on that we use the if else to see which fragment should be used

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.coursesNav) {
//            Log.v("Test", "Test");
            //appBarLayout.removeView(cllapsingToolbarLayout);
            appBarLayout.setExpanded(false);
            appBarLayout.addOnOffsetChangedListener(new   AppBarLayout.OnOffsetChangedListener() {
                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                    appBarLayout.setExpanded(false,false);
                }
            });
            mViewPager.removeAllViews();
            Fragment newFragment = new MyCoursesFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack if needed
            transaction.replace(R.id.fragment1, newFragment);
            // transaction.addToBackStack(null);
            transaction.commit();

            // Commit the transaction
        } else if (id == R.id.featuredCoursesNaV) {
//            Log.v("Test", "Test");
            //appBarLayout.removeView(cllapsingToolbarLayout);
            appBarLayout.setExpanded(true);
            mViewPager.setAdapter(imageCollapsingToolBarAdapter);

            // mViewPager.removeAllViews();
            Fragment newFragment1 = new StudentFeaturedCoursesFragment();
            newFragment1.setArguments(bundle);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack if needed
            transaction.replace(R.id.fragment1, newFragment1);
            //  transaction.addToBackStack(null);
            transaction.commit();

            // Commit the transaction
        }  else if (id == R.id.settings) {

        } else if (id == R.id.signout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
