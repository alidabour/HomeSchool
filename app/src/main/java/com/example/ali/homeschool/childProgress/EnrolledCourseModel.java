package com.example.ali.homeschool.childProgress;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ali on 4/22/2017.
 */

public class EnrolledCourseModel implements Parcelable {
    String course_id;
    String name;
    String enrolledcoursemodelid;
    Map<String,ProgressModel> progress;
    public String getEnrolledcoursemodelid() {
        return enrolledcoursemodelid;
    }

    public void setEnrolledcoursemodelid(String enrolledcoursemodelid) {
        this.enrolledcoursemodelid = enrolledcoursemodelid;
    }


    public EnrolledCourseModel() {
    }




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

    public Map<String, ProgressModel> getProgress() {
        return progress;
    }

    public void setProgress(Map<String, ProgressModel> progress) {
        this.progress = progress;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.course_id);
        dest.writeString(this.name);
        dest.writeString(this.enrolledcoursemodelid);
        dest.writeInt(this.progress.size());
        for (Map.Entry<String, ProgressModel> entry : this.progress.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeParcelable(entry.getValue(), flags);
        }
    }

    protected EnrolledCourseModel(Parcel in) {
        this.course_id = in.readString();
        this.name = in.readString();
        this.enrolledcoursemodelid = in.readString();
        int progressSize = in.readInt();
        this.progress = new HashMap<String, ProgressModel>(progressSize);
        for (int i = 0; i < progressSize; i++) {
            String key = in.readString();
            ProgressModel value = in.readParcelable(ProgressModel.class.getClassLoader());
            this.progress.put(key, value);
        }
    }

    public static final Creator<EnrolledCourseModel> CREATOR = new Creator<EnrolledCourseModel>() {
        @Override
        public EnrolledCourseModel createFromParcel(Parcel source) {
            return new EnrolledCourseModel(source);
        }

        @Override
        public EnrolledCourseModel[] newArray(int size) {
            return new EnrolledCourseModel[size];
        }
    };
}