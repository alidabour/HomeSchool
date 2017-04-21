package com.example.ali.homeschool.controller.fragments;


import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;

import com.example.ali.homeschool.adapter.EnrolledCoursesAdapter;
import com.example.ali.homeschool.controller.activities.EnrolledCourseActivity;
import com.example.ali.homeschool.data.CategoryInformation;
import com.example.ali.homeschool.R;
import com.example.ali.homeschool.adapter.CategoryAdapter;
import com.example.ali.homeschool.data.DataProvider;
import com.example.ali.homeschool.data.Entry.CourseColumns;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;



/**
 * A simple {@link Fragment} subclass.
 */
public class MyCoursesFragment extends Fragment implements LoaderCallbacks<Cursor> {
    EnrolledCoursesAdapter enrolledCoursesAdapter;
    private static final int CURSOR_LOADER_ID = 1;
    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference db;
    public MyCoursesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        db = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        container.removeAllViews();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_courses_fragmetnt, container, false);
        getLoaderManager().initLoader(0, null, (LoaderManager.LoaderCallbacks<Cursor>) this);
        enrolledCoursesAdapter = new EnrolledCoursesAdapter();
        RecyclerView enrolledCourses = (RecyclerView)view.findViewById(R.id.enrolledCourses);
        enrolledCourses.setHasFixedSize(true);
        LinearLayoutManager categoryLayoutManger = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        enrolledCourses.setLayoutManager(categoryLayoutManger);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        db.child("users").child(user.getUid()).child("EnrolledCourses")
                .addValueEventListener(
                        new ValueEventListener() {
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot s : dataSnapshot.getChildren()) {
                                    Log.v("Fire","Outside "+s.getValue());
                                    db.child("courses").child(s.getValue(String.class)).addValueEventListener(
                                            new ValueEventListener() {
                                                @Override
                                                public void onDataChange(
                                                        DataSnapshot inside) {
                                                    Log.v("Fire", "Inside " + inside
                                                            .toString());
                                                }

                                                @Override
                                                public void onCancelled(
                                                        DatabaseError databaseError) {

                                                }
                                            });
                                }

                                //                db.child("users").orderByChild("email").equalTo("a@go.com").addValueEventListener(
//                        new ValueEventListener() {
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                for(DataSnapshot d : dataSnapshot.getChildren()) {
//                                    Log.v("Test", "Data" + d.toString());
//                                    Log.v("Test", "Key" +d.getKey());
//                                    String childKey = d.getKey();
//                                    String key =db.child("users").child(d.getKey()).child("childs").push().getKey();
//                                    db.child("users").child(user.getUid()).child("childs").child(key).setValue("WW");
//                                }
//                            }
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//                            }
//                        });
                            }
                        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(CURSOR_LOADER_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(getActivity(), DataProvider.Course.CONTENT_URI,
                new String[]{CourseColumns._ID, CourseColumns.GLOBAL_ID,CourseColumns.COURSE_NAME}, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        Log.v("Test","Cursor Count : "+cursor.getCount());
        enrolledCoursesAdapter.swapCursor(cursor);
//        if (cursor.moveToFirst()) {
//            Log.v("Test", "Cursor Global id:" + cursor.getString(cursor.getColumnIndex(CourseColumns._ID)));
//        } else {
//            Log.v("Test", "Cursor");
//        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        enrolledCoursesAdapter.swapCursor(null);
    }

}
