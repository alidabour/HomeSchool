package com.example.ali.homeschool.controller.fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ali.homeschool.adapter.ChildrenAdapter;
import com.example.ali.homeschool.data.CategoryInformation;
import com.example.ali.homeschool.controller.activities.ChildCourses;
import com.example.ali.homeschool.R;
import com.example.ali.homeschool.RecyclerTouchListener;
import com.example.ali.homeschool.adapter.CategoryAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class ParentActivityFragment extends Fragment {
    View view;
    public ParentActivityFragment() {
    }
    List<CategoryInformation> children;
    private RecyclerView.LayoutManager mLayoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_parent2, container, false);
        CategoryInformation categoryInformation= new CategoryInformation("Child 1",R.drawable.photoid);
        children = new ArrayList<CategoryInformation>();
        children.add(categoryInformation);
        categoryInformation = new  CategoryInformation("Child 2",R.drawable.photoid);
        children.add(categoryInformation);
        categoryInformation = new  CategoryInformation("Child 3",R.drawable.photoid);
        children.add(categoryInformation);
        categoryInformation = new  CategoryInformation("Child 4",R.drawable.photoid);
        children.add(categoryInformation);
        categoryInformation = new  CategoryInformation("Child 5",R.drawable.photoid);
        children.add(categoryInformation);
        categoryInformation = new  CategoryInformation("Child 6",R.drawable.photoid);
        children.add(categoryInformation);
        categoryInformation = new  CategoryInformation("Child 7",R.drawable.photoid);
        children.add(categoryInformation);
        categoryInformation = new  CategoryInformation("Child 8",R.drawable.photoid);
        children.add(categoryInformation);
        categoryInformation = new  CategoryInformation("Child 9",R.drawable.photoid);
        children.add(categoryInformation);
        categoryInformation = new  CategoryInformation("Child 10",R.drawable.photoid);
        children.add(categoryInformation);
        categoryInformation = new  CategoryInformation("Child xx",R.drawable.photoid);
        children.add(categoryInformation);

        RecyclerView parentRecycleView = (RecyclerView)view.findViewById(R.id.category_recyclerView);
        parentRecycleView.setHasFixedSize(true);
 //       LinearLayoutManager categoryLayoutManger = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
//        categoryLayoutManger.setOrientation(LinearLayoutManager.HORIZONTAL);
        parentRecycleView.setLayoutManager(new GridLayoutManager(getActivity(),2));
    //    mLayoutManager = new LinearLayoutManager(getActivity());
     //   parentRecycleView.setLayoutManager(mLayoutManager);
        ChildrenAdapter childrenAdapter = new ChildrenAdapter(children);
        parentRecycleView.setAdapter(childrenAdapter);
        parentRecycleView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), parentRecycleView, new RecyclerTouchListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                    Intent intent = new Intent (getActivity(),ChildCourses.class);
                    startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
        return view;
    }
}
