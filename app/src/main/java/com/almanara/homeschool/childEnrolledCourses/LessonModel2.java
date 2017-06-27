package com.almanara.homeschool.childEnrolledCourses;

import android.os.Parcel;
import android.os.Parcelable;

import com.almanara.homeschool.instructor.topic.TopicModel;

import java.util.ArrayList;

/**
 * Created by Ali on 4/21/2017.
 */

public class LessonModel2 implements Parcelable {
    String id;
    String name;
    ArrayList<TopicModel> topics;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeTypedList(this.topics);
    }

    public LessonModel2() {
    }

    protected LessonModel2(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.topics = in.createTypedArrayList(TopicModel.CREATOR);
    }

    public static final Creator<LessonModel2> CREATOR = new Creator<LessonModel2>() {
        @Override
        public LessonModel2 createFromParcel(Parcel source) {
            return new LessonModel2(source);
        }

        @Override
        public LessonModel2[] newArray(int size) {
            return new LessonModel2[size];
        }
    };
}
