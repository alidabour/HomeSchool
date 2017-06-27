package com.almanara.homeschool.data.firebase;

import android.os.Parcel;
import android.os.Parcelable;

import com.almanara.homeschool.childProgress.EnrolledCourseModel;

/**
 * Created by Ali on 4/21/2017.
 */

public class UserModel implements Parcelable {
    String uid;
    String name;
    String email;
    String photo;
    EnrolledCourseModel enrolledcourses;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uid);
        dest.writeString(this.name);
        dest.writeString(this.email);
        dest.writeString(this.photo);
        dest.writeParcelable(this.enrolledcourses, flags);
    }

    public UserModel() {
    }

    protected UserModel(Parcel in) {
        this.uid = in.readString();
        this.name = in.readString();
        this.email = in.readString();
        this.photo = in.readString();
        this.enrolledcourses = in.readParcelable(EnrolledCourseModel.class.getClassLoader());
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel source) {
            return new UserModel(source);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public EnrolledCourseModel getEnrolledcourses() {
        return enrolledcourses;
    }

    public void setEnrolledcourses(EnrolledCourseModel enrolledcourses) {
        this.enrolledcourses = enrolledcourses;
    }
}
