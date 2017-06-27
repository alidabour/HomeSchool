package com.almanara.homeschool.childProgress;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.almanara.ali.homeschool.R;

import java.util.List;

/**
 * Created by Ali on 4/21/2017.
 */

public class ChildProgressAdapter1 extends RecyclerView.Adapter<ChildProgressAdapter1.ChildProgressViewHolder> {
    List<EnrolledCourseModel> enrolledCourseModels;
    OnClickHandler onClickHandler;
    double Progress;

    public double getProgress() {
        return Progress;
    }

    public void setProgress(double progress) {
        Progress = progress;
    }

    public interface OnClickHandler {
        void onClick(EnrolledCourseModel test);
    }

    public ChildProgressAdapter1(
            List<EnrolledCourseModel> enrolledCourseModels,
            OnClickHandler onClickHandler) {
        this.enrolledCourseModels = enrolledCourseModels;
        this.onClickHandler = onClickHandler;
    }

    @Override
    public ChildProgressViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext()).inflate(R.layout.child_progress_item, parent, false);
        return new ChildProgressViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ChildProgressViewHolder holder,
                                 int position) {
        EnrolledCourseModel enrolledCourseModel = enrolledCourseModels.get(position);
        holder.courseName.setText(enrolledCourseModel.getName());
        holder.progressBar.setProgress(Integer.parseInt(enrolledCourseModel.getProgressaftercalc()));
    }

    @Override
    public int getItemCount() {
        return enrolledCourseModels.size();
    }

    public class ChildProgressViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView courseName;
        ProgressBar progressBar;

        public ChildProgressViewHolder(View itemView) {
            super(itemView);
            courseName = (TextView) itemView.findViewById(R.id.courseName);
            progressBar = (ProgressBar) itemView.findViewById(R.id.courseProgress);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}