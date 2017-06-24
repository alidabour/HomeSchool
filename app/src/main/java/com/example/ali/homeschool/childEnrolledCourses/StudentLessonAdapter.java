package com.example.ali.homeschool.childEnrolledCourses;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.ali.homeschool.InstructorHome.CourseCreated;
import com.example.ali.homeschool.InstructorLessons.LessonModel;
import com.example.ali.homeschool.R;
import com.example.ali.homeschool.childClass.ClassActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Ali on 4/21/2017.
 */

public class StudentLessonAdapter extends RecyclerView.Adapter<StudentLessonAdapter.LessonViewHolder> {
    private static final int ITEM_VIEW_TYPE_HEADER = 0;
    private static final int ITEM_VIEW_TYPE_ITEM = 1;
    List<LessonModel> lessonModelList;
    public StudentLessonAdapter.OnClickHandler onClickHandler;
    Activity activity;

    String courseId;

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
    public interface OnClickHandler {
        void onClick(LessonModel test);
    }
    public boolean isOneRow(int position) {
        return position%3==0;
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
//        if (viewType == ITEM_VIEW_TYPE_HEADER) {
//            return new LessonViewHolder(itemView);
//        }
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
//        return new TextViewHolder(view);


        return new LessonViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LessonViewHolder holder, int position) {
//        final LessonModel lessonModel = lessonModelList.get(position);
//        Log.v("Lesson " , lessonModel.getName());
//        holder.lessonName.setText(lessonModel.getName());
//        holder.lessonName.setTextColor(ContextCompat.getColor(activity, R.color.colorLesson));
//        holder.startBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(activity,
//                        ClassActivity.class);
//                intent.putExtra("courseId", courseId);
//                intent.putExtra("lessonid", lessonModel.getId());
//                activity.startActivity(intent);
//            }
//        });
    }


    @Override
    public int getItemCount() {
        return 20;
//        return lessonModelList.size();
    }
    @Override
    public int getItemViewType(int position) {
        return isOneRow(position) ? ITEM_VIEW_TYPE_HEADER : ITEM_VIEW_TYPE_ITEM;
    }

    public class LessonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        private TextView lessonName;
//        protected Button startBtn;
        CircleImageView circleImageView;

        public LessonViewHolder(View itemView) {
            super(itemView);
            circleImageView = (CircleImageView) itemView.findViewById(R.id.lesson_image);
            circleImageView.setOnClickListener(this);
//            lessonName = (TextView) itemView.findViewById(R.id.lessonName);
//            startBtn = (Button) itemView.findViewById(R.id.startbtn);
//            startBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            final int p = getAdapterPosition();
//            onClickHandler.onClick(lessonModelList.get(p));
            Animation animation = AnimationUtils.loadAnimation(activity, R.anim.course_image);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
//                    int p = getAdapterPosition();
//                    CourseCreated courseCreated = courses.get(p);
//                    Intent intent = new Intent(activity,
//                            LessonActivity.class);
//                    intent.putExtra("course", courseCreated);
//                    activity.startActivity(intent);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
//                    context.startActivity(new Intent(context, ClassActivity.class));
                    Intent intent = new Intent(activity,
                            ClassActivity.class);
                    if(courseId !=null) {
                        intent.putExtra("courseId", courseId);
                    }
                    intent.putExtra("lessonid", lessonModelList.get(p).getId());

                    activity.startActivity(intent);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            view.startAnimation(animation);
        }
    }
}
