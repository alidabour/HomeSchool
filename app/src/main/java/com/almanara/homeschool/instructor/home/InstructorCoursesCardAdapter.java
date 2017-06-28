package com.almanara.homeschool.instructor.home;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.almanara.homeschool.data.firebase.CourseCreated;
import com.bumptech.glide.Glide;
import com.almanara.homeschool.instructor.lesson.InstructorLessonsActivity;
import com.almanara.ali.homeschool.R;

import java.util.List;

/**
 * Created by hossam on 08/06/17.
 */

public class InstructorCoursesCardAdapter extends RecyclerView.Adapter<InstructorCoursesCardAdapter.CourseViewHolder>{

        List<CourseCreated> courseCreatedList;
        public OnClickHandler onClickHandler;
        Context context ;

        public interface OnClickHandler {
            void onClick(CourseCreated test);
        }

        public InstructorCoursesCardAdapter(List<CourseCreated> courseCreatedList, OnClickHandler onClickHandler , Context context) {
            this.courseCreatedList = courseCreatedList;
            this.onClickHandler = onClickHandler;
            this.context = context ;
        }

        @Override
        public CourseViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.instructor_course_card_item,parent,false);
            return new CourseViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final CourseViewHolder holder, int position) {
            final CourseCreated courseCreated = courseCreatedList.get(position);
            holder.buttonViewOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //creating a popup menu
                    final PopupMenu popup = new PopupMenu(context,holder.buttonViewOption);
                    //inflating menu from xml resource
                    popup.inflate(R.menu.instructor_course_menu);
                    //adding click listener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.Preview:{
                                    Intent intent = new Intent(context,
                                            Classroom.class);
                                    intent.putExtra("courseid", courseCreated.getCourse_id());
                                    context.startActivity(intent);
                            }
                                    break;
                                case R.id.EditCourse: {
                                    Intent intent = new Intent(context,
                                            InstructorLessonsActivity.class);
                                    intent.putExtra("course", courseCreated);
                                    context.startActivity(intent);
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

            holder.courseName.setText(courseCreated.getName());
            // Log.e("Photo: ",courseCreated.getPhoto_url() );
            Glide.with(context).load(courseCreated.getPhoto_url()).fitCenter().into(holder.courseImage);

        }

        @Override
        public int getItemCount() {
            return courseCreatedList.size();
        }

        public class CourseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            private TextView courseName;
            private ImageView courseImage;
            private TextView buttonViewOption;
            public CourseViewHolder(View itemView) {
                super(itemView);
                courseName = (TextView) itemView.findViewById(R.id.courseName);
                courseImage = (ImageView) itemView.findViewById(R.id.courseImage);
                buttonViewOption = (TextView) itemView.findViewById(R.id.textViewOptions);
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


