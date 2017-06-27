package com.example.ali.homeschool.adapter;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ali.homeschool.R;
import com.example.ali.homeschool.data.firebase.Topics;

import java.util.List;

/**
 * Created by Ali on 2/7/2017.
 */

public class TopicsFirebaseAdapter extends RecyclerView.Adapter<TopicsFirebaseAdapter.TopicViewHolder> {
    List<Topics> topics;
    private Cursor mCursor;

    public TopicsFirebaseAdapter(List<Topics> topics){
        this.topics =topics;
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
//        mCursor.moveToPosition(position);
//        Log.v("Test","Count : "+mCursor.getCount());
//
//        String name = mCursor.getString(mCursor.getColumnIndex(LessonColumns.LESSON_NAME));
//        holder.topicName.setText(name);
//        holder.topicNum.setText(String.valueOf(mCursor.getInt(mCursor.getColumnIndex(LessonColumns.LESSON_NUMBER))));
            Topics topic = topics.get(position);
        holder.topicName.setText(topic.getName());
        holder.topicNum.setText(topic.getNumber());
    }

    @Override
    public int getItemCount() {
        return topics.size();
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
