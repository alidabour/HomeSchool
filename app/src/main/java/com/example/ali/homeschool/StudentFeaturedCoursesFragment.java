package com.example.ali.homeschool;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 * This is the inital fragment for the Student which contains the featured courses as well
 * as the navigation bar for his courses and settings
 */
public class StudentFeaturedCoursesFragment extends Fragment {
    View view;
    List<CategoryInformation> categoryInformationList;
    public int type;
    public StudentFeaturedCoursesFragment() {
    }
    Bundle b;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.courses_fragment_layout, container, false);
        //    b = getArguments();
        //  type = b.getInt("type");

        //  Here we add in the array list to be used in the RecyclerView
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

        RecyclerView categoryRecycleView = (RecyclerView)view.findViewById(R.id.category_recyclerView);
        categoryRecycleView.setHasFixedSize(true);
        LinearLayoutManager categoryLayoutManger = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
//        categoryLayoutManger.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecycleView.setLayoutManager(categoryLayoutManger);
        CategoryAdapter categoryAdapter = new CategoryAdapter(categoryInformationList);
        categoryRecycleView.setAdapter(categoryAdapter);
        //here when we use the addonitemtouchlistener we need to consider that the listener listens
        //to the activity so we can't "this" as this is a fragment not an activity
        //and that is what it desires
        categoryRecycleView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), categoryRecycleView, new RecyclerTouchListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                    //    getActivity().startActivity(new Intent(getActivity(), CourseDescriptionActivity.class));
                        Intent intent = new Intent(getActivity(), CourseDescriptionActivity.class);
                        intent.putExtra("type",1); // here i send the type one to notify that this is the student
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );


        // This is the RecyclerView of the Top Visited
        RecyclerView c2 = (RecyclerView)view.findViewById(R.id.c22);
        c2.setHasFixedSize(true);
        LinearLayoutManager cm2 = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        //cm2.setOrientation(LinearLayoutManager.HORIZONTAL);
        c2.setLayoutManager(cm2);
        CategoryAdapter ca = new CategoryAdapter(categoryInformationList);
        c2.setAdapter(ca);


        // This is the RecyclerView of the New
        RecyclerView c3= (RecyclerView)view.findViewById(R.id.c33);
        c3.setHasFixedSize(true);
        LinearLayoutManager cm3 = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        //cm2.setOrientation(LinearLayoutManager.HORIZONTAL);
        c3.setLayoutManager(cm3);
        CategoryAdapter ca3 = new CategoryAdapter(categoryInformationList);
        c3.setAdapter(ca3);


        return view;
    }
}
