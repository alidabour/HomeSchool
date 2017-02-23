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
import android.widget.Button;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import com.example.ali.homeschool.RecyclerTouchListener;
import com.example.ali.homeschool.controller.activities.ChildCourses;
import com.example.ali.homeschool.data.CategoryInformation;
import com.example.ali.homeschool.controller.activities.EnrolledCourse;
import com.example.ali.homeschool.R;
import com.example.ali.homeschool.adapter.CategoryAdapter;
import com.example.ali.homeschool.controller.activities.StudentHomeActivity;
import com.example.ali.homeschool.data.DataProvider;
import com.example.ali.homeschool.data.Entry.CourseColumns;


import java.util.ArrayList;
import java.util.List;



/**
 * A simple {@link Fragment} subclass.
 */
public class MyCoursesFragment extends Fragment implements LoaderCallbacks<Cursor> {
    List<CategoryInformation> categoryInformationList;
    CategoryAdapter categoryAdapter;
    private static final int CURSOR_LOADER_ID = 1;

    public MyCoursesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        container.removeAllViews();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_courses_fragmetnt, container, false);
        getLoaderManager().initLoader(0, null, (LoaderManager.LoaderCallbacks<Cursor>) this);

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
        RecyclerView enrolledCourses = (RecyclerView)view.findViewById(R.id.enrolledCourses);
        enrolledCourses.setHasFixedSize(true);
        LinearLayoutManager categoryLayoutManger = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
//        categoryLayoutManger.setOrientation(LinearLayoutManager.HORIZONTAL);
        enrolledCourses.setLayoutManager(categoryLayoutManger);
        categoryAdapter = new CategoryAdapter(categoryInformationList);
        enrolledCourses.setAdapter(categoryAdapter);

        enrolledCourses.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), enrolledCourses, new RecyclerTouchListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent (getActivity(),EnrolledCourse.class);
                intent.putExtra("courseID","2");
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
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
        categoryAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        categoryAdapter.swapCursor(null);
    }

}
