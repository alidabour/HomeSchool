package com.example.ali.homeschool.InstructorTopic;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;


public class TopicModel implements Parcelable {
    String id;
    String name;
    String layout;
    String question = "false";
    String topicType = "normal";

    public Map<String, Object> toMap() {
        HashMap<String, Object> topic = new HashMap<>();
        topic.put("id", id);
        topic.put("name", name);
        topic.put("layout", layout);
        topic.put("question", question);
        topic.put("topicType", topicType);
        return topic;
    }

    public String getTopicType() {
        return topicType;
    }

    public void setTopicType(String topicType) {
        this.topicType = topicType;
    }

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

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.layout);
        dest.writeString(this.question);
        dest.writeString(this.topicType);
    }

    public TopicModel() {
    }

    protected TopicModel(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.layout = in.readString();
        this.question = in.readString();
        this.topicType = in.readString();
    }

    public static final Creator<TopicModel> CREATOR = new Creator<TopicModel>() {
        @Override
        public TopicModel createFromParcel(Parcel source) {
            return new TopicModel(source);
        }

        @Override
        public TopicModel[] newArray(int size) {
            return new TopicModel[size];
        }
    };
}
