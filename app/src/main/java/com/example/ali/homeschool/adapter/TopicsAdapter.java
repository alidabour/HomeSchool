package com.example.ali.homeschool.adapter;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ali.homeschool.R;
import com.example.ali.homeschool.data.Entry.LessonColumns;

import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Ali on 2/7/2017.
 */

public class TopicsAdapter extends RecyclerView.Adapter<TopicsAdapter.TopicViewHolder> {
    List<String> names;
    private Cursor mCursor;

    public TopicsAdapter(List<String> names){
        this.names=names;
    }
    @Override
    public TopicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.topic_list_item,parent,false);
        return new TopicViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TopicViewHolder holder, int position) {
        Log.v("Test","Position :" + position);
        mCursor.moveToPosition(position);
        Log.v("Test","Count : "+mCursor.getCount());

        String name = mCursor.getString(mCursor.getColumnIndex(LessonColumns.LESSON_NAME));
        holder.topicName.setText(name);
        holder.topicNum.setText(String.valueOf(mCursor.getInt(mCursor.getColumnIndex(LessonColumns.LESSON_NUMBER))));
    }
    public Cursor swapCursor(Cursor c) {
        // check if this cursor is the same as the previous cursor (mCursor)
        if (mCursor == c) {
            return null; // bc nothing has changed
        }
        Cursor temp = mCursor;
        this.mCursor = c; // new cursor value assigned

        //check if this is a valid cursor, then update the cursor
        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }
    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }

    public static class TopicViewHolder extends RecyclerView.ViewHolder {
        protected TextView topicName;
        protected TextView topicNum;
        public TopicViewHolder(View itemView) {
            super(itemView);
            topicName = (TextView) itemView.findViewById(R.id.topicName);
            topicNum = (TextView) itemView.findViewById(R.id.topicNum);
        }
    }
}
