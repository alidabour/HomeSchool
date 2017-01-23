package com.example.ali.homeschool;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends AppCompatActivity {
    List<CategoryInformation> categoryInformationList;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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
        toolbar= (Toolbar)findViewById(R.id.mToolbar);
        toolbar.inflateMenu(R.menu.home_menu);
        Log.v("Test","Inf :"+categoryInformation.getCategoryName());
        for (CategoryInformation x : categoryInformationList){
            Log.v("Test","Inf"+x.getCategoryName());
        }
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.signIn_action:
                        Log.v("Test","Sign IN");
                        break;
                    case R.id.search_action:
                        Log.v("Test","Search");
                        break;
                }
                return true;
            }
        });

        RecyclerView categoryRecycleView = (RecyclerView)findViewById(R.id.category_recyclerView);
        categoryRecycleView.setHasFixedSize(true);
        LinearLayoutManager categoryLayoutManger = new LinearLayoutManager(HomeActivity.this,LinearLayoutManager.HORIZONTAL,false);
//        categoryLayoutManger.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecycleView.setLayoutManager(categoryLayoutManger);
        CategoryAdapter categoryAdapter = new CategoryAdapter(categoryInformationList);
        categoryRecycleView.setAdapter(categoryAdapter);
        categoryRecycleView.addOnItemTouchListener(new RecyclerTouchListener(this, categoryRecycleView, new RecyclerTouchListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        startActivity(new Intent(getApplicationContext(),CourseDescriptionActivity.class));}

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );

        RecyclerView c2 = (RecyclerView)findViewById(R.id.c22);
        c2.setHasFixedSize(true);
        LinearLayoutManager cm2 = new LinearLayoutManager(HomeActivity.this,LinearLayoutManager.HORIZONTAL,false);
        //cm2.setOrientation(LinearLayoutManager.HORIZONTAL);
        c2.setLayoutManager(cm2);
        CategoryAdapter ca = new CategoryAdapter(categoryInformationList);
        c2.setAdapter(ca);

        RecyclerView c3= (RecyclerView)findViewById(R.id.c33);
        c3.setHasFixedSize(true);
        LinearLayoutManager cm3 = new LinearLayoutManager(HomeActivity.this,LinearLayoutManager.HORIZONTAL,false);
        //cm2.setOrientation(LinearLayoutManager.HORIZONTAL);
        c3.setLayoutManager(cm3);
        CategoryAdapter ca3 = new CategoryAdapter(categoryInformationList);
        c3.setAdapter(ca3);
        final ViewPager mViewPager = (ViewPager) findViewById(R.id.viewPage_collapsing_toolbar);
        ImageCollapsingToolBarAdapter imageCollapsingToolBarAdapter = new ImageCollapsingToolBarAdapter(this);
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
//        RecyclerView toolbarRecycleView= (RecyclerView)findViewById(R.id.recycleview_collapsing_toolbar);
//        toolbarRecycleView.setHasFixedSize(true);
//        LinearLayoutManager toolbarLM = new LinearLayoutManager(HomeActivity.this,LinearLayoutManager.HORIZONTAL,false);
//        //cm2.setOrientation(LinearLayoutManager.HORIZONTAL);
//        toolbarRecycleView.setLayoutManager(toolbarLM);
//        List<Integer> ids = new ArrayList<>();
//        ids.add(R.drawable.earlymath);
//        ids.add(R.drawable.earlymath);
//        ids.add(R.drawable.earlymath);
//        ids.add(R.drawable.earlymath);
//        ImageCollapsingToolBarAdapter toolbarAdapter = new ImageCollapsingToolBarAdapter(ids);
//        toolbarRecycleView.setAdapter(toolbarAdapter);


    }
}
