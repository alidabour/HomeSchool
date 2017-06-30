package com.almanara.homeschool.student.course;

import android.animation.Animator;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.bumptech.glide.Glide;
import com.almanara.homeschool.data.firebase.CourseCreated;
import com.almanara.ali.homeschool.R;
import com.almanara.homeschool.student.course.lesson.LessonActivity;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Ali on 6/23/2017.
 */

public class StudentCoursesAdapter extends RecyclerView.Adapter<StudentCoursesAdapter.CourseViewHolder> {
    Context context;
    List<CourseCreated> courses;
    View viewRoot;
    List<Integer> colors = new ArrayList<>();
    public void setViewRoot(View viewRoot) {
        this.viewRoot = viewRoot;
    }

    public StudentCoursesAdapter(Context context, List<CourseCreated> courses) {
        this.context = context;
        this.courses = courses;
        colors.add(Color.parseColor("#003366"));
        colors.add(Color.parseColor("#a3b03d"));
        colors.add(Color.parseColor("#fdbcb4"));
        colors.add(Color.parseColor("#34b233"));
        colors.add(Color.parseColor("#bf4147"));
        colors.add(Color.parseColor("#f6883d"));

    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_item, parent, false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position) {
        if(courses.get(position)!= null){
            CourseCreated courseCreated = courses.get(position);
            Glide.with(context).load(courseCreated.getPhoto_url()).into(holder.courseImage);
        }
        holder.courseImage.setBorderColor(colors.get(position));


    }

    @Override
    public int getItemCount() {
//        return 6;
        return courses.size();
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircleImageView courseImage;

        public CourseViewHolder(View itemView) {
            super(itemView);
            courseImage = (CircleImageView) itemView.findViewById(R.id.course_image);
//            courseImage.setBorderColor(Color.parseColor("#ff00ff"));
            courseImage.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            final MediaPlayer mediaPlayer = MediaPlayer.create(context,R.raw.onpresss);
            mediaPlayer.start();
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.course_image);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
//                    context.startActivity(new Intent(context, ClassActivity.class));
                    int cx = (viewRoot.getLeft() + viewRoot.getRight()) / 2;
                    int cy = (viewRoot.getTop() + viewRoot.getBottom()) / 2;
                    int finalRadius = Math.max(viewRoot.getWidth(), viewRoot.getHeight());

                    Animator anim = ViewAnimationUtils
                            .createCircularReveal(viewRoot, cx, cy, 0, finalRadius);
                    final int color = 0xFFFF0000;
                    final Drawable drawable = new ColorDrawable(color);
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                        viewRoot.setForeground(drawable);
//                        anim.addListener(new AnimatorListenerAdapter() {
//                            @Override
//                            public void onAnimationEnd(Animator animation) {
//                                int p = getAdapterPosition();
//                                CourseCreated courseCreated = courses.get(p);
//                                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity)context);
//                                Intent intent = new Intent(context,
//                                        LessonActivity.class);
//                                intent.putExtra("course", courseCreated);
//                                context.startActivity(intent, options.toBundle());
//                            }
//                        });
//                        anim.start();
//                    }else{
                        int p = getAdapterPosition();
                        CourseCreated courseCreated = courses.get(p);
                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity)context);
                        Intent intent = new Intent(context,
                                LessonActivity.class);
                        intent.putExtra("course", courseCreated);
                        context.startActivity(intent, options.toBundle());
//                    ((Activity) context).finish();
//                    }
//                    viewRoot.setBackgroundColor(Color.parseColor("#ff0000"));

                    //

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            v.startAnimation(animation);
        }
    }
}
