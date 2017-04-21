package com.example.ali.homeschool.InstructorLessons;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.ali.homeschool.InstructorTopic.TopicModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ali on 4/21/2017.
 */

public class LessonModel implements Parcelable {
    String id;
    String name;
    Map<String,TopicModel> topics;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, TopicModel> getTopics() {
        return topics;
    }

    public void setTopics(
            Map<String, TopicModel> topics) {
        this.topics = topics;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
//        dest.writeInt(this.topics.size());
//        for (Map.Entry<String, TopicModel> entry : this.topics.entrySet()) {
//            dest.writeString(entry.getKey());
//            dest.writeParcelable(entry.getValue(), flags);
//        }
    }

    public LessonModel() {
    }

    protected LessonModel(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        int topicsSize = in.readInt();
        this.topics = new HashMap<String, TopicModel>(topicsSize);
        for (int i = 0; i < topicsSize; i++) {
            String key = in.readString();
            TopicModel value = in.readParcelable(TopicModel.class.getClassLoader());
            this.topics.put(key, value);
        }
    }

    public static final Parcelable.Creator<LessonModel> CREATOR = new Parcelable.Creator<LessonModel>() {
        @Override
        public LessonModel createFromParcel(Parcel source) {
            return new LessonModel(source);
        }

        @Override
        public LessonModel[] newArray(int size) {
            return new LessonModel[size];
        }
    };

    @Override
    public String toString() {
        return "LessonModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", topics=" + topics +
                '}';
    }
}
