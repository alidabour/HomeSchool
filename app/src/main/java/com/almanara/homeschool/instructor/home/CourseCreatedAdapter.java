package com.almanara.homeschool.instructor.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.almanara.ali.homeschool.R;

import java.util.List;

/**
 * Created by Ali on 4/20/2017.
 */

public class CourseCreatedAdapter extends RecyclerView.Adapter<CourseCreatedAdapter.CourseViewHolder>{

    List<CourseCreated> courseCreatedList;
    public OnClickHandler onClickHandler;
    Context context ;

    public interface OnClickHandler {
        void onClick(CourseCreated test);
    }

    public CourseCreatedAdapter(List<CourseCreated> courseCreatedList, OnClickHandler onClickHandler , Context context) {
        this.courseCreatedList = courseCreatedList;
        this.onClickHandler = onClickHandler;
        this.context = context ;
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
       // Log.e("Photo: ",courseCreated.getPhoto_url() );
        Glide.with(context).load(courseCreated.getPhoto_url()).into(holder.courseImage);

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
