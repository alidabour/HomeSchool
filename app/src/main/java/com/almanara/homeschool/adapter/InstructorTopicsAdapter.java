package com.almanara.homeschool.adapter;

/**
 * Created by hossam on 21/06/17.
 */

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.almanara.homeschool.instructor.create.InstructorTopicCreationActivity;
import com.almanara.homeschool.data.firebase.TopicModel;
import com.almanara.ali.homeschool.R;

import java.util.List;


public class InstructorTopicsAdapter extends RecyclerView.Adapter<InstructorTopicsAdapter.TopicViewHolder>{
    static String lessonId;
    static String courseId;
    Activity activity;
    List<TopicModel> topicModelList;
    public InstructorTopicsAdapter.OnClickHandler onClickHandler;


    public interface OnClickHandler {
        void onClick(TopicModel test);
    }
    public InstructorTopicsAdapter(List<TopicModel> topicModelList, InstructorTopicsAdapter.OnClickHandler onClickHandler,
                         Activity activity , String courseId , String lessonId) {
        this.topicModelList = topicModelList;
        this.onClickHandler = onClickHandler;
        this.activity = activity;
        this.courseId = courseId;
        this.lessonId = lessonId;
    }

    @Override
    public TopicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.instructor_lesson_item_view,parent,false);
        return new TopicViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final TopicViewHolder holder, int position) {
        final TopicModel topicModelInFocus = topicModelList.get(position);
        holder.topicName.setText(topicModelInFocus.getName());
        holder.topicName.setTextColor(ContextCompat.getColor(activity, R.color.colorLesson));
        holder.topicOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //creating a popup menu
                final PopupMenu popup = new PopupMenu(activity,holder.topicOptions);
                //inflating menu from xml resource
                popup.inflate(R.menu.instructor_course_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.Preview:{

                                startIntentFromAdapter(activity,topicModelInFocus);
                            }
                            break;
                            case R.id.EditCourse: {
                                startIntentFromAdapter(activity,topicModelInFocus);
                            }
                            break;
                        }
                        return false;
                    }
                });
                //displaying the popup
                popup.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return topicModelList.size();
    }

    public class TopicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView topicName;
        protected TextView topicOptions;

        public TopicViewHolder(View itemView) {
            super(itemView);
            topicName = (TextView) itemView.findViewById(R.id.lessonName);
            topicOptions = (TextView) itemView.findViewById(R.id.textViewOptions);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int p = getAdapterPosition();
            onClickHandler.onClick(topicModelList.get(p));
        }
    }


    public static void startIntentFromAdapter(Activity activity,TopicModel topicModel){
        Intent intent = new Intent(activity,
                InstructorTopicCreationActivity.class);
        intent.putExtra("topicPar",topicModel);
        intent.putExtra("topicid", topicModel.getId());
        intent.putExtra("topicname", topicModel.getName());
        intent.putExtra("layout", topicModel.getLayout());
        intent.putExtra("lessonid", lessonId);
        intent.putExtra("courseId", courseId);
        activity.startActivity(intent);
    }
}

