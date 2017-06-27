package com.example.ali.homeschool.childEnrolledCourses;


import android.os.Parcel;
import android.os.Parcelable;

import com.example.ali.homeschool.instructor.lesson.LessonModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hossam on 01/03/17.
 */

public class Courses implements Parcelable {

    public String name;
    public String rate;
    public String subjectS;
    public String teacher;
    public String teacher_id;
    public String privacy;
    public String descriptionS;
    public String course_id;
    Map<String, LessonModel> lessons;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.course_id);
        dest.writeString(this.descriptionS);
        dest.writeInt(this.lessons.size());
        dest.writeString(this.name);
        dest.writeString(this.privacy);
        dest.writeString(this.rate);
        dest.writeString(this.subjectS);
        dest.writeString(this.teacher);
        dest.writeString(this.teacher_id);
        for (Map.Entry<String, LessonModel> entry : this.lessons.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeParcelable(entry.getValue(), flags);
        }
    }

    public Courses() {
    }

    protected Courses(Parcel in) {
        this.name = in.readString();
        this.rate = in.readString();
        this.subjectS = in.readString();
        this.teacher = in.readString();
        this.teacher_id = in.readString();
        this.privacy = in.readString();
        this.descriptionS = in.readString();
        this.course_id = in.readString();
        int lessonsSize = in.readInt();
        this.lessons = new HashMap<String, LessonModel>(lessonsSize);
        for (int i = 0; i < lessonsSize; i++) {
            String key = in.readString();
            LessonModel value = in.readParcelable(LessonModel.class.getClassLoader());
            this.lessons.put(key, value);
        }
    }

    public static final Creator<Courses> CREATOR = new Creator<Courses>() {
        @Override
        public Courses createFromParcel(Parcel source) {
            return new Courses(source);
        }

        @Override
        public Courses[] newArray(int size) {
            return new Courses[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getSubjectS() {
        return subjectS;
    }

    public void setSubjectS(String subjectS) {
        this.subjectS = subjectS;
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

    public String getDescriptionS() {
        return descriptionS;
    }

    public void setDescriptionS(String descriptionS) {
        this.descriptionS = descriptionS;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public Map<String, LessonModel> getLessons() {
        return lessons;
    }

    public void setLessons(Map<String, LessonModel> lessons) {
        this.lessons = lessons;
    }
}