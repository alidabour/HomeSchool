package com.example.ali.homeschool.childProgress;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hossam on 16/06/17.
 */

public class ProgressModel implements Parcelable {
    String topicProgressFlag;
    String topicProgressId;

    public String getTopicProgressFlag() {
        return topicProgressFlag;
    }

    public void setTopicProgressFlag(String topicProgressFlag) {
        this.topicProgressFlag = topicProgressFlag;
    }

    public String getTopicProgressId() {
        return topicProgressId;
    }

    public void setTopicProgressId(String topicProgressId) {
        this.topicProgressId = topicProgressId;
    }

    public Map<String,Object> toMap(){
        HashMap<String,Object> topic = new HashMap<>();
        topic.put("id",topicProgressFlag);
        topic.put("name",topicProgressId);
        return topic;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.topicProgressFlag);
        dest.writeString(this.topicProgressId);
    }

    public ProgressModel() {
    }

    protected ProgressModel(Parcel in) {
        this.topicProgressFlag = in.readString();
        this.topicProgressId = in.readString();
    }

    public static final Parcelable.Creator<ProgressModel> CREATOR = new Parcelable.Creator<ProgressModel>() {
        @Override
        public ProgressModel createFromParcel(Parcel source) {
            return new ProgressModel(source);
        }

        @Override
        public ProgressModel[] newArray(int size) {
            return new ProgressModel[size];
        }
    };
}
