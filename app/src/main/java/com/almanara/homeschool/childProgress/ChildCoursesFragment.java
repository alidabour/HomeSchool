package com.almanara.homeschool.childProgress;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.almanara.homeschool.data.firebase.EnrolledCourseModel;
import com.almanara.homeschool.data.firebase.ProgressModel;
import com.almanara.homeschool.data.firebase.ChildModel;
import com.almanara.ali.homeschool.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class ChildCoursesFragment extends Fragment {
    ListView lv;
    Context context;
    RecyclerView recyclerView;
    ChildProgressAdapter childProgressAdapter;
    DatabaseReference db;
    ChildModel c;
    List<EnrolledCourseModel> enrolledCourseModel;
    ValueEventListener listener;
    int counter = 1;
    int progress = 1;

    public ChildCoursesFragment() {
    }

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_child_courses, container, false);
        context = getContext();
        db = FirebaseDatabase.getInstance().getReference();

        counter = 0;
        progress = 0;
        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra("childModel")) {
            Log.v("Test", "Intent found");
            c = intent.getParcelableExtra("childModel");
            Log.v("Test", "Name" + c.getName());
        }
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        childProgressAdapter = new ChildProgressAdapter();
        return view;
    }

    @Override
    public void onPause() {
        if (listener != null)
            db.removeEventListener(listener);
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();

        listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                enrolledCourseModel = new ArrayList<EnrolledCourseModel>();
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    EnrolledCourseModel e = d.getValue(EnrolledCourseModel.class);
                    for (DataSnapshot d1 : d.getChildren()) {
                        for (DataSnapshot d2 : d1.getChildren()) {
                            ProgressModel progressModel = d2.getValue(ProgressModel.class);
                            if (progressModel.getTopicProgressFlag().equals("true")) {
                                progress++;
                            }

                            counter++;
                        }
                    }
                    if(counter!=0)
                    e.setProgressaftercalc(""+progress*100/counter);
                    else
                        e.setProgressaftercalc(""+0);
                    enrolledCourseModel.add(e);
                }
                ChildProgressAdapter1 childProgressAdapter1 = new ChildProgressAdapter1(
                        enrolledCourseModel, new ChildProgressAdapter1.OnClickHandler() {
                    @Override
                    public void onClick(EnrolledCourseModel test) {

                    }
                });
                recyclerView.setAdapter(childProgressAdapter1);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        try {
            db.child("users").child(c.getId()).child("enrolledcourses").addValueEventListener(listener);
        }catch (NullPointerException e){
            Toast.makeText(context, "ID Null", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
