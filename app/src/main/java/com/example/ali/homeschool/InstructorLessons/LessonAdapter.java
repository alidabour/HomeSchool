package com.example.ali.homeschool.InstructorLessons;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ali.homeschool.R;

import java.util.List;

/**
 * Created by Ali on 4/21/2017.
 */

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.LessonViewHolder>{
    List<LessonModel> lessonModelList;
    @Override
    public LessonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(LessonViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class LessonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView lessonName;
        public LessonViewHolder(View itemView) {
            super(itemView);
            lessonName = (TextView) itemView.findViewById(R.id.lessonName);
            lessonName.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
