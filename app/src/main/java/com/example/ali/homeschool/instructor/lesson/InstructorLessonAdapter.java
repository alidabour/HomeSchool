package com.example.ali.homeschool.instructor.lesson;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.ali.homeschool.instructor.topic.InstructorTopicActivity;
import com.example.ali.homeschool.R;

import java.util.List;

/**
 * Created by Ali on 4/21/2017.
 */

public class InstructorLessonAdapter extends RecyclerView.Adapter<InstructorLessonAdapter.LessonViewHolder>{
    List<LessonModel> lessonModelList;
    public InstructorLessonAdapter.OnClickHandler onClickHandler;
    Activity activity;
    String courseId;
    public interface OnClickHandler {
        void onClick(LessonModel test);
    }
    public InstructorLessonAdapter(List<LessonModel> lessonModelList, InstructorLessonAdapter.OnClickHandler onClickHandler ,Activity activity,String courseId) {
        this.lessonModelList = lessonModelList;
        this.onClickHandler = onClickHandler;
        this.activity = activity;
        this.courseId = courseId;
    }

    @Override
    public LessonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.instructor_lesson_item_view,parent,false);
        return new LessonViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final LessonViewHolder holder, int position) {
        final LessonModel lessonModel = lessonModelList.get(position);
        holder.lessonName.setText(lessonModel.getName());
        holder.lessonName.setTextColor(ContextCompat.getColor(activity, R.color.colorLesson));
        holder.lessonOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //creating a popup menu
                final PopupMenu popup = new PopupMenu(activity,holder.lessonOptions);
                //inflating menu from xml resource
                popup.inflate(R.menu.instructor_course_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.Preview:{
                                Intent intent = new Intent(activity,
                                        InstructorTopicActivity.class);
                                intent.putExtra("lessonid", lessonModel.getId());
                                intent.putExtra("courseId", courseId);

                                activity.startActivity(intent);
                            }
                            break;
                            case R.id.EditCourse: {
                                Intent intent = new Intent(activity,
                                        InstructorTopicActivity.class);
                                intent.putExtra("lessonid", lessonModel.getId());
                                intent.putExtra("courseId", courseId);
                                Log.v("courseId",courseId);
                                Log.v("lessonid",lessonModel.getId());
                                activity.startActivity(intent);
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
        return lessonModelList.size();
    }

    public class LessonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView lessonName;
        protected TextView lessonOptions;

        public LessonViewHolder(View itemView) {
            super(itemView);
            lessonName = (TextView) itemView.findViewById(R.id.lessonName);
            lessonOptions = (TextView) itemView.findViewById(R.id.textViewOptions);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int p = getAdapterPosition();
            onClickHandler.onClick(lessonModelList.get(p));
        }
    }
}
