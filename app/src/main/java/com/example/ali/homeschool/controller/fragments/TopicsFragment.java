package com.example.ali.homeschool.controller.fragments;

import android.content.Intent;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ali.homeschool.R;
import com.example.ali.homeschool.RecyclerTouchListener;
import com.example.ali.homeschool.adapter.TopicsAdapter;
import com.example.ali.homeschool.closed.ClassActivitytrial____________________;
import com.example.ali.homeschool.data.DataProvider;
import com.example.ali.homeschool.data.Entry.LessonColumns;

import java.util.ArrayList;
import java.util.List;


public class TopicsFragment extends Fragment  implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int CURSOR_LOADER_ID = 2;
    String id;
    TopicsAdapter topicsAdapter;
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
        getLoaderManager().initLoader(0, null, (LoaderManager.LoaderCallbacks<Cursor>) this);

        Bundle bundle = getArguments();
        if (bundle != null){
            id = bundle.getString("courseID");
            Log.v("Test","Topic fragment id : "+id);
        }
        List<String> names = new ArrayList<>();
        names.add("مقدمة");names.add("Topic 2");names.add("Topic 3");names.add("Topic 4");
        names.add("Topic 5");names.add("Topic 6");names.add("Topic 7");names.add("Topic 8");
        names.add("Topic 9");names.add("Topic 10");names.add("Topic 11");names.add("Topic 12");
        RecyclerView topicsRecyclerView = (RecyclerView)view.findViewById(R.id.topicsRecyclerView);
        topicsRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        topicsRecyclerView.setLayoutManager(layoutManager);
        topicsAdapter = new TopicsAdapter(names);
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

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(CURSOR_LOADER_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(getActivity(), DataProvider.Lesson.CONTENT_URI,
                new String[]{LessonColumns._ID, LessonColumns.LESSON_NAME},null,null, null);
//        LessonColumns.COURSE_ID +" = ?", new String[]{id}
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

//        if (data.moveToFirst()) {
////            Log.v("Test", "Cursor Lesson data: " + data.getCount());
////            Log.v("Test", "Cursor Lesson ID:" + data.getString(data.getColumnIndex(LessonColumns.COURSE_ID)));
//        } else {
//            Log.v("Test", "Cursor");
//        }
        topicsAdapter.swapCursor(data);
    }

//    @Override
//    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
//        if (cursor.moveToFirst()) {
//            Log.v("Test", "Cursor Lesson ID:" + cursor.getString(cursor.getColumnIndex(LessonColumns.COURSE_ID)));
//        } else {
//            Log.v("Test", "Cursor");
//        }
////        to.swapCursor(cursor);
//    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
//        categoryAdapter.swapCursor(null);
    }






}
