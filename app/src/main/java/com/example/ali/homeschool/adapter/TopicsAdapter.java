package com.example.ali.homeschool.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ali.homeschool.instructor.create.InstructorTopicCreationActivity;
import com.example.ali.homeschool.instructor.topic.TopicModel;
import com.example.ali.homeschool.R;

import java.util.List;

public class TopicsAdapter extends RecyclerView.Adapter<TopicsAdapter.TopicViewHolder>{
    TopicModel topicModel;
    List<TopicModel> topicModelList;
    public TopicsAdapter.OnClickHandler onClickHandler;


    public interface OnClickHandler {
        void onClick(TopicModel test);
    }
    public TopicsAdapter(List<TopicModel> topicModelList, TopicsAdapter.OnClickHandler onClickHandler) {
        this.topicModelList = topicModelList;
        this.onClickHandler = onClickHandler;
    }

    @Override
    public TopicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.topic_list_item,parent,false);

        return new TopicViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TopicViewHolder holder, int position) {
        final TopicModel topicModel = topicModelList.get(position);
        holder.topicName.setText(topicModel.getName());
        holder.topicNum.setText(position + 1+"");

    }

    @Override
    public int getItemCount() {
        return topicModelList.size();
    }

    public class TopicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView topicName;
        protected TextView topicNum;

        public TopicViewHolder(View itemView) {
            super(itemView);
            topicName = (TextView) itemView.findViewById(R.id.topicName);
            topicNum = (TextView) itemView.findViewById(R.id.topicNum);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int p = getAdapterPosition();
            onClickHandler.onClick(topicModelList.get(p));
        }
    }


    private void startIntent(Activity activity){
        Intent intent = new Intent(activity,
                InstructorTopicCreationActivity.class);
        intent.putExtra("topicid", topicModel.getId());
        intent.putExtra("topicname", topicModel.getName());
        activity.startActivity(intent);
    }
}
