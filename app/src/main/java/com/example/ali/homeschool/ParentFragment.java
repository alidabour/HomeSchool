package com.example.ali.homeschool;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.ali.homeschool.adabter.CategoryAdapter;

import java.util.List;

//
///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link ParentFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link ParentFragment#newInstance} factory method to
// * create an instance of this fragment.
// */

public class ParentFragment extends Fragment {
    View view;

    public ParentFragment() {
        // Required empty public constructor
    }
    List<CategoryInformation> children;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_parent, container, false);
        CategoryInformation categoryInformation= new CategoryInformation("Child 1",R.drawable.earlymath);
        children.add(categoryInformation);
        categoryInformation = new  CategoryInformation("Child 2",R.drawable.earlymath);
        children.add(categoryInformation);
        categoryInformation = new  CategoryInformation("Child 3",R.drawable.earlymath);
        children.add(categoryInformation);
        categoryInformation = new  CategoryInformation("Child 4 ",R.drawable.earlymath);
        RecyclerView parentRecycleView = (RecyclerView)view.findViewById(R.id.category_recyclerView);
        parentRecycleView.setHasFixedSize(true);
//        LinearLayoutManager categoryLayoutManger = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
//        categoryLayoutManger.setOrientation(LinearLayoutManager.HORIZONTAL);
        parentRecycleView.setLayoutManager(new GridLayoutManager(getActivity(),4));
        CategoryAdapter categoryAdapter = new CategoryAdapter(children);
        parentRecycleView.setAdapter(categoryAdapter);
        return view;
    }
}
