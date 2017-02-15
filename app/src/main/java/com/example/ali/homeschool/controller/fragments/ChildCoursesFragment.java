package com.example.ali.homeschool.controller.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.ali.homeschool.R;
import com.example.ali.homeschool.adapter.CustomListviewAdapter;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class ChildCoursesFragment extends Fragment {
    ListView lv;
    Context context;
    public ChildCoursesFragment() {
    }
    View view;
    ArrayList prgmName;
    public static String [] prgmNameList={"Let Us C","c++","JAVA","Jsp","Microsoft .Net","Android","PHP","Jquery","JavaScript"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view =inflater.inflate(R.layout.fragment_child_courses, container, false);
        context = getContext();

        lv=(ListView) view.findViewById(R.id.listView);
        lv.setAdapter(new CustomListviewAdapter(context , prgmNameList));

        return view;
    }
}
