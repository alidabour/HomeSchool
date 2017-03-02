package com.example.ali.homeschool.data.firebase;

/**
 * Created by hossam on 01/03/17.
 */

public class Topics {
    public String name;
    public String number;
    public String lesson_id;
    public String json_layout;

    public Topics(String name, String number, String lesson_id,String json_layout) {
        this.name = name;
        this.number = number;
        this.lesson_id = lesson_id;
        this.json_layout = json_layout;
    }
    public Topics(){};
    public void setNumber(String number) {
        this.number = number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setJson_layout(String json_layout) {
        this.json_layout = json_layout;
    }

    public void setLesson_id(String lesson_id) {
        this.lesson_id = lesson_id;
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public String getJson_layout() {
        return json_layout;
    }

    public String getLesson_id() {
        return lesson_id;
    }
}
