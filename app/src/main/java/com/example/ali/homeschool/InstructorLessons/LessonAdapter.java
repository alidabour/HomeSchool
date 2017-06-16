package com.example.ali.homeschool.InstructorLessons;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ali.homeschool.InstructorHome.CourseCreated;
import com.example.ali.homeschool.InstructorHome.CourseCreatedAdapter;
import com.example.ali.homeschool.R;

import java.util.List;

/**
 * Created by Ali on 4/21/2017.
 */

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.LessonViewHolder>{
    List<LessonModel> lessonModelList;
    public LessonAdapter.OnClickHandler onClickHandler;

    public interface OnClickHandler {
        void onClick(LessonModel test);
    }
    public LessonAdapter(List<LessonModel> lessonModelList,LessonAdapter.OnClickHandler onClickHandler ) {
        this.lessonModelList = lessonModelList;
        this.onClickHandler = onClickHandler;
    }

    @Override
    public LessonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.topic_list_item,parent,false);
        return new LessonViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LessonViewHolder holder, int position) {
        LessonModel lessonModel = lessonModelList.get(position);
        holder.lessonName.setText(lessonModel.getName());
        holder.lessonNum.setText(position + 1 +" ");
    }

    @Override
    public int getItemCount() {
        return lessonModelList.size();
    }

    public class LessonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView lessonName;
        protected TextView lessonNum;

        public LessonViewHolder(View itemView) {
            super(itemView);
            lessonName = (TextView) itemView.findViewById(R.id.topicName);
            lessonNum = (TextView) itemView.findViewById(R.id.topicNum);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int p = getAdapterPosition();
            onClickHandler.onClick(lessonModelList.get(p));
        }
    }
}
