package com.example.ali.homeschool.data.firebase;

import com.google.firebase.database.Exclude;

/**
 * Created by hossam on 01/03/17.
 */

public class Users {

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



    public String getParentid() {
        return parent_id;
    }

    public void setParentid(String parentid) {
        this.parent_id = parentid;
    }

    public Users(String name, String address, String age, String parentid) {
        this.name = name;
        this.address = address;
        this.age = age;
        this.parent_id = parentid;
    }
    public Users(){

    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String photo ;
    public String name;
    public String address;
    public String age;
    public String parent_id;

}
