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
 */
public class Main4ActivityFragment extends Fragment {
    View view;
    List<CategoryInformation> categoryInformationList;

    public Main4ActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_main4, container, false);
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
        categoryRecycleView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), categoryRecycleView, new RecyclerTouchListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                    //    getActivity().startActivity(new Intent(getActivity(), CourseDescriptionActivity.class));
                        Intent intent = new Intent(getActivity(), CourseDescriptionActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );

        RecyclerView c2 = (RecyclerView)view.findViewById(R.id.c22);
        c2.setHasFixedSize(true);
        LinearLayoutManager cm2 = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        //cm2.setOrientation(LinearLayoutManager.HORIZONTAL);
        c2.setLayoutManager(cm2);
        CategoryAdapter ca = new CategoryAdapter(categoryInformationList);
        c2.setAdapter(ca);

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
