package com.example.ali.homeschool.childEnrolledCourses;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ali.homeschool.InstructorHome.CourseCreated;
import com.example.ali.homeschool.R;
import com.example.ali.homeschool.childEnrolledCourses.Courses;

import java.util.List;

/**
 * Created by Ali on 4/21/2017.
 */

public class EnrolledCoursesAdapter1 extends RecyclerView.Adapter<EnrolledCoursesAdapter1.LessonViewHolder>{
    List<CourseCreated> courses;
    public EnrolledCoursesAdapter1.OnClickHandler onClickHandler;
    public interface OnClickHandler {
        void onClick(CourseCreated test);
    }
    public EnrolledCoursesAdapter1(List<CourseCreated> courses, EnrolledCoursesAdapter1.OnClickHandler onClickHandler) {
        this.courses = courses;
        this.onClickHandler = onClickHandler;
    }

    @Override
    public LessonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.enrolled_course_item,parent,false);
        return new LessonViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LessonViewHolder holder, int position) {
        CourseCreated categoryInformations1 = courses.get(position);
        holder.courseName.setText(categoryInformations1.getName());
        holder.courseImage.setImageResource(R.drawable.earlymath);
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public class LessonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView courseName;
        private ImageView courseImage;
        public LessonViewHolder(View itemView) {
            super(itemView);
            courseImage = (ImageView) itemView.findViewById(R.id.category_imageView);
            courseName = (TextView) itemView.findViewById(R.id.category_textView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int b = getAdapterPosition();
            onClickHandler.onClick(courses.get(b));
        }
    }
}
