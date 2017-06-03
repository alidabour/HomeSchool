package com.example.ali.homeschool.controller.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ali.homeschool.R;
import com.example.ali.homeschool.adapter.CourseSectionListAdapter;
import com.example.ali.homeschool.data.HeaderRVData;
import com.example.ali.homeschool.data.firebase.Courses;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//import android.support.v4.app.LoaderManager;
//import android.support.v4.app.LoaderManager.LoaderCallbacks;
//import android.support.v4.content.Loader;
//import android.support.v4.content.CursorLoader;

/**
 * A placeholder fragment containing a simple view.
 * This is the inital fragment for the Student which contains the featured courses as well
 * as the navigation bar for his courses and settings
 */
public class StudentFeaturedCoursesFragment extends Fragment {
    View view;
    CourseSectionListAdapter courseSectionListAdapter;
    RecyclerView courseSectionRV;
    private DatabaseReference databaseReference;
    private List<Courses> users;
    private List<HeaderRVData> headerRVDatas;
    public int type;

    public StudentFeaturedCoursesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.courses_fragment_layout, container, false);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        courseSectionRV = (RecyclerView) view.findViewById(R.id.category_recyclerView);
        courseSectionRV.setHasFixedSize(true);
        courseSectionRV.setLayoutManager(
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        headerRVDatas = new ArrayList<>();
        if(container !=null){
            container.removeAllViews();
        }
        Log.v("StudentCoursesFragment","Test");
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Add value event listener to the post
        // [START post_value_event_listener]DatabaseReference myRef = databaseReference;
        DatabaseReference myRef = databaseReference;
        myRef.child("courses").addValueEventListener(
                new ValueEventListener() {
                    public static final String TAG = "EmailPassword";

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        users = new ArrayList<>();
                        // Get Post object and use the values to update the UI
                        // [START_EXCLUDE]
                        for (DataSnapshot x : dataSnapshot.getChildren()) {
                            Log.v("Test", "Child : " + x.toString());
                            Courses c = x.getValue(Courses.class);
                            String key = x.getKey();
                            c.setCourse_id(key);
                            users.add(c);
                            Log.v("Test", "Child : " + users);
                        }
                        HashMap<String, ArrayList<Courses>> map = new HashMap<>();
                        for (Courses x : users) {
                            ArrayList<Courses> c = new ArrayList<Courses>();
                            if (map.get(x.getSubject()) != null) {
                                c = map.get(x.getSubject());
                                c.add(x);
                                map.put(x.getSubject(), c);
                            } else {
                                c.add(x);
                                map.put(x.getSubject(), c);
                            }
                        }
                        Log.v("Test", "Map :" + map.toString());
                        Iterator it = map.entrySet().iterator();
                        while (it.hasNext()) {
                            Map.Entry pair = (Map.Entry) it.next();
                            headerRVDatas.add(new HeaderRVData((String) pair.getKey(),
                                    (List) pair.getValue()));
                            Log.v("Test", "Map_______" + pair.getKey() + " = " + pair.getValue());
                            it.remove(); // avoids a ConcurrentModificationException
                        }
                        courseSectionListAdapter = new CourseSectionListAdapter(getActivity(),
                                headerRVDatas,1);
                        courseSectionRV.setAdapter(courseSectionListAdapter);
                        // [END_EXCLUDE]
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Getting Post failed, log a message
                        Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                        // [START_EXCLUDE]
                        Toast.makeText(getActivity(), "Failed to load post.",
                                Toast.LENGTH_SHORT).show();
                        // [END_EXCLUDE]
                    }
                });


    }
}
