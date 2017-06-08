package com.example.ali.homeschool.childEnrolledCourses;


import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.example.ali.homeschool.InstructorLessons.LessonModel;
import com.example.ali.homeschool.InstructorLessons.LessonModel2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hossam on 01/03/17.
 */

public class Courses2 implements Parcelable {

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