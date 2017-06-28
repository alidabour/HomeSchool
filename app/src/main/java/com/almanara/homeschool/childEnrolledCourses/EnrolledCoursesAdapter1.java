package com.almanara.homeschool.childEnrolledCourses;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.almanara.homeschool.data.firebase.CourseCreated;
import com.almanara.ali.homeschool.R;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by Ali on 4/21/2017.
 */

public class EnrolledCoursesAdapter1 extends RecyclerView.Adapter<EnrolledCoursesAdapter1.LessonViewHolder>{
    List<CourseCreated> courses;
    public EnrolledCoursesAdapter1.OnClickHandler onClickHandler;
    Context mContext ;
    public interface OnClickHandler {
        void onClick(CourseCreated test);
    }
    public EnrolledCoursesAdapter1(List<CourseCreated> courses, EnrolledCoursesAdapter1.OnClickHandler onClickHandler , Context mContext) {
        this.courses = courses;
        this.onClickHandler = onClickHandler;
        this.mContext = mContext;
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
        if(categoryInformations1.getPhoto_url()!=null){
            Log.v("Adapter ", categoryInformations1.getPhoto_url());
            Glide.with(mContext).load(categoryInformations1.getPhoto_url()).bitmapTransform(new RoundedCornersTransformation(mContext, 20, 0, RoundedCornersTransformation.CornerType.TOP)).into(holder.courseImage);
        }
        holder.courseName.setText(categoryInformations1.getName());

    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public class LessonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView courseName;
        private ImageView courseImage;
        CardView cardView ;
        public LessonViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            courseImage = (ImageView) itemView.findViewById(R.id.category_imageView);
            courseName = (TextView) itemView.findViewById(R.id.category_textView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int b = getAdapterPosition();
            cardView.setBackgroundColor(Color.parseColor("#04bf4f"));
            onClickHandler.onClick(courses.get(b));

        }
    }
}