package com.example.ali.homeschool.data.firebase;

import com.google.firebase.database.Exclude;

/**
 * Created by hossam on 01/03/17.
 */

public class Users {

    public Users(String name, String address, String age, String uid) {
        this.name = name;
        this.address = address;
        this.age = age;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    public String name;
    public String address;
    public String age;
    public String uid;


}
