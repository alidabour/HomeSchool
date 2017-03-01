package com.example.ali.homeschool.controller.fragments;


import android.app.Fragment;
import android.content.Intent;
import android.content.Loader;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.os.Bundle;

//import android.support.v4.app.LoaderManager;
//import android.support.v4.app.LoaderManager.LoaderCallbacks;
//import android.support.v4.content.Loader;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
//import android.support.v4.content.CursorLoader;
import android.os.RemoteException;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ali.homeschool.Utils;
import com.example.ali.homeschool.adapter.CourseSectionListAdapter;
import com.example.ali.homeschool.data.CategoryInformation;
import com.example.ali.homeschool.controller.activities.CourseDescriptionActivity;
import com.example.ali.homeschool.R;
import com.example.ali.homeschool.RecyclerTouchListener;
import com.example.ali.homeschool.adapter.CategoryAdapter;
import com.example.ali.homeschool.data.DataProvider;
import com.example.ali.homeschool.data.Entry.CourseColumns;
import com.example.ali.homeschool.data.HeaderRVData;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 * This is the inital fragment for the Student which contains the featured courses as well
 * as the navigation bar for his courses and settings
 */
public class StudentFeaturedCoursesFragment extends Fragment implements LoaderCallbacks<Cursor> {
    private static final int CURSOR_LOADER_ID = 0;
    View view;
    List<CategoryInformation> categoryInformationList;
    CategoryAdapter categoryAdapter;
    CourseSectionListAdapter courseSectionListAdapter;
    RecyclerView courseSectionRV;
    public int type;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        getLoaderManager().initLoader(0, null, this);

    }

    public StudentFeaturedCoursesFragment() {
    }

    Bundle b;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.courses_fragment_layout, container, false);
//        initLoader(CURSOR_LOADER_ID, null,g);
        getLoaderManager().initLoader(0, null, (LoaderManager.LoaderCallbacks<Cursor>) this);
        if (savedInstanceState == null) {
            try {
//                Utils.addDefaultsSubjects(getApplicationContext());
                Utils.addCoursesTest(getActivity());
                Utils.addLessonsTest(getActivity());
                Utils.addTopicsTest(getActivity());
//                Utils.addTopicsContentsTest(getApplicationContext());
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (OperationApplicationException e) {
                e.printStackTrace();
            }

        }
        courseSectionRV = (RecyclerView)view.findViewById(R.id.category_recyclerView);
        courseSectionRV.setHasFixedSize(true);
        courseSectionRV.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        List<HeaderRVData> headerRVDatas = new ArrayList<>();
        List<CategoryInformation> categoryInformations = new ArrayList<>();
        categoryInformations.add(new CategoryInformation("Test",R.drawable.earlymath));

        categoryInformations.add(new CategoryInformation("Test",R.drawable.earlymath));

        categoryInformations.add(new CategoryInformation("Test",R.drawable.earlymath));

        categoryInformations.add(new CategoryInformation("Test",R.drawable.earlymath));
        headerRVDatas.add(new HeaderRVData("Section num1",categoryInformations));
        headerRVDatas.add(new HeaderRVData("Section num2",categoryInformations));
        headerRVDatas.add(new HeaderRVData("Section num3",categoryInformations));
        courseSectionListAdapter = new CourseSectionListAdapter(getActivity(),headerRVDatas);
        courseSectionRV.setAdapter(courseSectionListAdapter);
//        getActivity().getSupportLoaderManager().initLoader(0, null, (LoaderManager.LoaderCallbacks<Cursor>)this);
        //    b = getArguments();
        //  type = b.getInt("type");

        //  Here we add in the array list to be used in the RecyclerView
//        RecyclerView categoryRecycleView = (RecyclerView) view.findViewById(R.id.category_recyclerView);
//        categoryRecycleView.setHasFixedSize(true);
//        LinearLayoutManager categoryLayoutManger = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
////        categoryLayoutManger.setOrientation(LinearLayoutManager.HORIZONTAL);
//        categoryRecycleView.setLayoutManager(categoryLayoutManger);
//        categoryAdapter = new CategoryAdapter(categoryInformationList, new CategoryAdapter.OnClickHandler() {
//            @Override
//            public void onClick(String test) {
//
//            }
//        });
//        categoryRecycleView.setAdapter(categoryAdapter);
//
//        //here when we use the addonitemtouchlistener we need to consider that the listener listens
//        //to the activity so we can't "this" as this is a fragment not an activity
//        //and that is what it desires
//        categoryRecycleView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), categoryRecycleView, new RecyclerTouchListener.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(View view, int position) {
//                        //    getActivity().startActivity(new Intent(getActivity(), CourseDescriptionActivity.class));
//                        Intent intent = new Intent(getActivity(), CourseDescriptionActivity.class);
//                        intent.putExtra("type", 1); // here i send the type one to notify that this is the student
//                        startActivity(intent);
//                    }
//
//                    @Override
//                    public void onLongItemClick(View view, int position) {
//
//                    }
//                })
//        );
//
//
//        // This is the RecyclerView of the Top Visited
//        RecyclerView c2 = (RecyclerView) view.findViewById(R.id.c22);
//        c2.setHasFixedSize(true);
//        LinearLayoutManager cm2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
//        //cm2.setOrientation(LinearLayoutManager.HORIZONTAL);
//        c2.setLayoutManager(cm2);
//        CategoryAdapter ca = new CategoryAdapter(categoryInformationList, new CategoryAdapter.OnClickHandler() {
//            @Override
//            public void onClick(String test) {
//
//            }
//        });
//        c2.setAdapter(ca);
//
//
//        // This is the RecyclerView of the New
//        RecyclerView c3 = (RecyclerView) view.findViewById(R.id.c33);
//        c3.setHasFixedSize(true);
//        LinearLayoutManager cm3 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
//        //cm2.setOrientation(LinearLayoutManager.HORIZONTAL);
//        c3.setLayoutManager(cm3);
//        CategoryAdapter ca3 = new CategoryAdapter(categoryInformationList, new CategoryAdapter.OnClickHandler() {
//            @Override
//            public void onClick(String test) {
//
//            }
//        });
//        c3.setAdapter(ca3);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(CURSOR_LOADER_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(getActivity(), DataProvider.Course.CONTENT_URI,
                new String[]{CourseColumns._ID, CourseColumns.COURSE_NAME}, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor.moveToFirst()) {
            Log.v("Test", "Cursor :" + cursor.getString(cursor.getColumnIndex(CourseColumns.COURSE_NAME)));
        } else {
            Log.v("Test", "Cursor");
        }
//        categoryAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        categoryAdapter.swapCursor(null);
    }

//    @Override
//    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
//        return new CursorLoader(getActivity(), DataProvider.Course.CONTENT_URI,
//                      new String[]{CourseColumns._ID, CourseColumns.COURSE_NAME},null,null,null);
//    }
//
//    @Override
//    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
//
//        if(data.moveToFirst()){
//            Log.v("Test","Cursor :" +data.getString(data.getColumnIndex(CourseColumns.COURSE_NAME)));
//        }else {
//            Log.v("Test","Cursor");
//        }
//    }
//
//    @Override
//    public void onLoaderReset(Loader<Cursor> loader) {
//
//    }
}
