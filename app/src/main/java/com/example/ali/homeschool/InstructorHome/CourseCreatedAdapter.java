package com.example.ali.homeschool.InstructorHome;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ali.homeschool.R;
import com.example.ali.homeschool.adapter.CategoryAdapter;
import com.example.ali.homeschool.data.DataProvider;
import com.example.ali.homeschool.data.firebase.Courses;

import java.util.List;

/**
 * Created by Ali on 4/20/2017.
 */

public class CourseCreatedAdapter extends RecyclerView.Adapter<CourseCreatedAdapter.CourseViewHolder>{

    List<CourseCreated> courseCreatedList;
    public OnClickHandler onClickHandler;

    public interface OnClickHandler {
        void onClick(CourseCreated test);
    }

    public CourseCreatedAdapter(List<CourseCreated> courseCreatedList,OnClickHandler onClickHandler) {
        this.courseCreatedList = courseCreatedList;
        this.onClickHandler = onClickHandler;
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_created_list_item,parent,false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position) {
        CourseCreated courseCreated = courseCreatedList.get(position);
        holder.courseName.setText(courseCreated.getName());
    }

    @Override
    public int getItemCount() {
        return courseCreatedList.size();
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView courseName;
        private ImageView courseImage;

        public CourseViewHolder(View itemView) {
            super(itemView);
            courseName = (TextView) itemView.findViewById(R.id.courseName);
            courseImage = (ImageView) itemView.findViewById(R.id.courseImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.v("Test","Click Course");
            int p = getAdapterPosition();
            onClickHandler.onClick(courseCreatedList.get(p));
        }
    }
}
