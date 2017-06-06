package com.example.ali.homeschool.childProgress;

/**
 * Created by Ali on 4/22/2017.
 */

public class EnrolledCourseModel {
    String course_id;
    String name;
    String progress;

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }
}
