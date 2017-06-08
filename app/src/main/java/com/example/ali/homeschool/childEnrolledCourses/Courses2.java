package com.example.ali.homeschool.childEnrolledCourses;


import android.os.Parcel;
import android.os.Parcelable;

import com.example.ali.homeschool.InstructorLessons.LessonModel2;

import java.util.ArrayList;

/**
 * Created by hossam on 01/03/17.
 */

public class Courses2 implements Parcelable {
    public void setName(String name) {
        this.name = name;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public ArrayList<LessonModel2> getLessons() {
        return lessons;
    }

    public void setLessons(ArrayList<LessonModel2> lessons) {
        this.lessons = lessons;
    }

    public Courses2() {
    }

    public String getName() {
        return name;
    }

    public String name;
    public String rate;
    public String subject;
    public String teacher;
    public String teacher_id;
    public String privacy;
    public String description;
    public String course_id;
    public  String photo_url ;
    ArrayList<LessonModel2> lessons;


    protected Courses2(Parcel in) {
        photo_url = in.readString();
        name = in.readString();
        rate = in.readString();
        subject = in.readString();
        teacher = in.readString();
        teacher_id = in.readString();
        privacy = in.readString();
        description = in.readString();
        course_id = in.readString();
        lessons = in.createTypedArrayList(LessonModel2.CREATOR);
    }

    public static final Creator<Courses2> CREATOR = new Creator<Courses2>() {
        @Override
        public Courses2 createFromParcel(Parcel in) {
            return new Courses2(in);
        }

        @Override
        public Courses2[] newArray(int size) {
            return new Courses2[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(photo_url);
        parcel.writeString(name);
        parcel.writeString(rate);
        parcel.writeString(subject);
        parcel.writeString(teacher);
        parcel.writeString(teacher_id);
        parcel.writeString(privacy);
        parcel.writeString(description);
        parcel.writeString(course_id);
        parcel.writeTypedList(lessons);
    }
}