package com.almanara.homeschool.controller.activities;

/**
 * Created by manar_000 on 6/21/2017.
 */

public class Home {
    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    int image ;
    String name ;

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    String slogan ;

    public int getBackGround() {
        return backGround;
    }

    public void setBackGround(int backGround) {
        this.backGround = backGround;
    }

    int backGround ;
}
