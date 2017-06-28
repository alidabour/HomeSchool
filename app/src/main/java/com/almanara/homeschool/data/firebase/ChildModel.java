package com.almanara.homeschool.data.firebase;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ali on 4/21/2017.
 */

public class ChildModel implements Parcelable {
    public String id;
    public String photo;
    public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.photo);
    }

    public ChildModel() {
    }

    public ChildModel(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.photo = in.readString();
    }

    public static final Parcelable.Creator<ChildModel> CREATOR = new Parcelable.Creator<ChildModel>() {
        @Override
        public ChildModel createFromParcel(Parcel source) {
            return new ChildModel(source);
        }

        @Override
        public ChildModel[] newArray(int size) {
            return new ChildModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
