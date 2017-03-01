package com.example.ali.homeschool.controller.fragments;

import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.example.ali.homeschool.R;
import com.example.ali.homeschool.adapter.ChildProgressAdapter;
import com.example.ali.homeschool.adapter.CustomListviewAdapter;
import com.example.ali.homeschool.data.DataProvider;
import com.example.ali.homeschool.data.Entry.ChildProgressColumns;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class ChildCoursesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int CURSOR_LOADER_ID = 5;
    ListView lv;
    Context context;
    RecyclerView recyclerView;
    ChildProgressAdapter childProgressAdapter;
    public ChildCoursesFragment() {
    }
    View view;
    ArrayList prgmName;
    public static String [] prgmNameList={"Let Us C","c++","JAVA","Jsp","Microsoft .Net","Android","PHP","Jquery","JavaScript"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getLoaderManager().initLoader(0, null, (LoaderManager.LoaderCallbacks<Cursor>) this);
        view =inflater.inflate(R.layout.fragment_child_courses, container, false);
        context = getContext();
//        lv=(ListView) view.findViewById(R.id.listView);
//        lv.setAdapter(new CustomListviewAdapter(context , prgmNameList));

        recyclerView = (RecyclerView) view.findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        childProgressAdapter = new ChildProgressAdapter();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(CURSOR_LOADER_ID, null, this);
    }

    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), DataProvider.ChildProgress.CONTENT_URI,
                new String[]{ChildProgressColumns.COURSE_NAME,ChildProgressColumns.COURSE_PROGRESS}
        ,null,null,null);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        Log.v("Test","Child Progress count :"+data.getCount());
        childProgressAdapter.swapCursor(data);
        recyclerView.setAdapter(childProgressAdapter);

    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {
        childProgressAdapter.swapCursor(null);
    }

}
