package com.example.ali.homeschool.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ali.homeschool.InstructorLessons.LessonModel;
import com.example.ali.homeschool.R;
import com.example.ali.homeschool.childClass.ClassActivity;

import java.util.List;

/**
 * Created by Ali on 4/21/2017.
 */

public class StudentLessonAdapter extends RecyclerView.Adapter<StudentLessonAdapter.LessonViewHolder> {
    List<LessonModel> lessonModelList;
    public StudentLessonAdapter.OnClickHandler onClickHandler;
    Activity activity;
    String courseId;

    public interface OnClickHandler {
        void onClick(LessonModel test);
    }

    public StudentLessonAdapter(List<LessonModel> lessonModelList,
                                StudentLessonAdapter.OnClickHandler onClickHandler,
                                Activity activity,
                                String courseId) {
        this.lessonModelList = lessonModelList;
        this.onClickHandler = onClickHandler;
        this.activity = activity;
        this.courseId = courseId;
    }

    @Override
    public LessonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_lesson_item_view, parent, false);
        return new LessonViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LessonViewHolder holder, int position) {
        final LessonModel lessonModel = lessonModelList.get(position);
        holder.lessonName.setText(lessonModel.getName());
        holder.lessonName.setTextColor(ContextCompat.getColor(activity, R.color.colorLesson));
        holder.startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity,
                        ClassActivity.class);
                intent.putExtra("courseId", courseId);
                intent.putExtra("lessonid", lessonModel.getId());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lessonModelList.size();
    }

    public class LessonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView lessonName;
        protected Button startBtn;

        public LessonViewHolder(View itemView) {
            super(itemView);
            lessonName = (TextView) itemView.findViewById(R.id.lessonName);
            startBtn = (Button) itemView.findViewById(R.id.startbtn);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int p = getAdapterPosition();
            onClickHandler.onClick(lessonModelList.get(p));
        }
    }
}
