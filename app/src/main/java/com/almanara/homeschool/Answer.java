package com.almanara.homeschool;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ali on 6/4/2017.
 */

public class Answer implements Parcelable {
    String answer="null";
    String lan="null";
    public Answer(){}

    public Answer(String answer, String lan) {
        this.answer = answer;
        this.lan = lan;
    }

    public Answer(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getLan() {
        return lan;
    }

    public void setLan(String lan) {
        this.lan = lan;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.answer);
        dest.writeString(this.lan);
    }

    protected Answer(Parcel in) {
        this.answer = in.readString();
        this.lan = in.readString();
    }

    public static final Creator<Answer> CREATOR = new Creator<Answer>() {
        @Override
        public Answer createFromParcel(Parcel source) {
            return new Answer(source);
        }

        @Override
        public Answer[] newArray(int size) {
            return new Answer[size];
        }
    };
}
