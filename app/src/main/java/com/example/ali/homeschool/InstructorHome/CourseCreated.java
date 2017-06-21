package com.example.ali.homeschool.InstructorHome;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ali on 4/20/2017.
 */

public class CourseCreated implements Parcelable {

    String course_id;
    String descriptionS;
    String name;
    String photo_url;
    String rate;
    String subjectS;
    String teacher_id;
    String teacher_name;

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getDescriptionS() {
        return descriptionS;
    }

    public void setDescriptionS(String descriptionS) {
        this.descriptionS = descriptionS;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
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

    public String getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.course_id);
        dest.writeString(this.descriptionS);
        dest.writeString(this.name);
        dest.writeString(this.photo_url);
        dest.writeString(this.rate);
        dest.writeString(this.subjectS);
        dest.writeString(this.teacher_id);
        dest.writeString(this.teacher_name);
    }

    public CourseCreated() {
    }

    protected CourseCreated(Parcel in) {
        this.course_id = in.readString();
        this.descriptionS = in.readString();
        this.name = in.readString();
        this.photo_url = in.readString();
        this.rate = in.readString();
        this.subjectS = in.readString();
        this.teacher_id = in.readString();
        this.teacher_name = in.readString();
    }

    public static final Parcelable.Creator<CourseCreated> CREATOR = new Parcelable.Creator<CourseCreated>() {
        @Override
        public CourseCreated createFromParcel(Parcel source) {
            return new CourseCreated(source);
        }

        @Override
        public CourseCreated[] newArray(int size) {
            return new CourseCreated[size];
        }
    };
}
