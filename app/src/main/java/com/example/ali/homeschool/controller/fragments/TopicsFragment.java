package com.example.ali.homeschool.controller.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ali.homeschool.R;
import com.example.ali.homeschool.RecyclerTouchListener;
import com.example.ali.homeschool.adapter.TopicsAdapter;
import com.example.ali.homeschool.closed.ClassActivitytrial____________________;

import java.util.ArrayList;
import java.util.List;


public class TopicsFragment extends Fragment {

    public TopicsFragment() {
        // Required empty public constructor
    }
    public static TopicsFragment newInstance() {
        TopicsFragment fragment = new TopicsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_topics, container, false);

        List<String> names = new ArrayList<>();
        names.add("مقدمة");names.add("Topic 2");names.add("Topic 3");names.add("Topic 4");
        names.add("Topic 5");names.add("Topic 6");names.add("Topic 7");names.add("Topic 8");
        names.add("Topic 9");names.add("Topic 10");names.add("Topic 11");names.add("Topic 12");
        RecyclerView topicsRecyclerView = (RecyclerView)view.findViewById(R.id.topicsRecyclerView);
        topicsRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        topicsRecyclerView.setLayoutManager(layoutManager);
        TopicsAdapter topicsAdapter = new TopicsAdapter(names);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(topicsRecyclerView.getContext(),layoutManager.getOrientation());
        topicsRecyclerView.addItemDecoration(dividerItemDecoration);
        topicsRecyclerView.setAdapter(topicsAdapter);
        topicsRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),topicsRecyclerView, new RecyclerTouchListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), ClassActivitytrial____________________.class);
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
        return view;
    }







}
