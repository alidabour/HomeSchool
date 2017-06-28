package com.almanara.homeschool.data.firebase;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hossam on 16/06/17.
 */

public class ProgressModel implements Parcelable {
    public String topicProgressFlag;
    public String topicProgressId;
    public String enrolledcourseid;
    public String progressid;


    public String getProgressid() {
        return progressid;
    }

    public void setProgressid(String progressid) {
        this.progressid = progressid;
    }

    public String getEnrolledcourseid() {
        return enrolledcourseid;
    }

    public void setEnrolledcourseid(String enrolledcourseid) {
        this.enrolledcourseid = enrolledcourseid;
    }

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
        topic.put("enrolledcourseid",enrolledcourseid);
        topic.put("topicProgressFlag",topicProgressFlag);
        topic.put("topicProgressId",topicProgressId);
        topic.put("progressid",progressid);
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
        dest.writeString(this.enrolledcourseid);
        dest.writeString(this.progressid);
    }

    public ProgressModel() {
    }

    protected ProgressModel(Parcel in) {
        this.topicProgressFlag = in.readString();
        this.topicProgressId = in.readString();
        this.enrolledcourseid = in.readString();
        this.progressid = in.readString();
    }

    public static final Creator<ProgressModel> CREATOR = new Creator<ProgressModel>() {
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
