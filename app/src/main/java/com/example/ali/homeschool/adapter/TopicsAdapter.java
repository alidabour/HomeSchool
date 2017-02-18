package com.example.ali.homeschool.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ali.homeschool.R;

import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Ali on 2/7/2017.
 */

public class TopicsAdapter extends RecyclerView.Adapter<TopicsAdapter.TopicViewHolder> {
    List<String> names;
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
        String name = names.get(position);
        holder.topicName.setText(name);
        holder.topicNum.setText(String.valueOf(position));
    }

    @Override
    public int getItemCount() {
        return names.size();
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
