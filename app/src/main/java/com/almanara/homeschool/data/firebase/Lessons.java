package com.almanara.homeschool.data.firebase;

/**
 * Created by hossam on 01/03/17.
 */

public class Lessons {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getLesson_id() {
        return lesson_id;
    }

    public void setLesson_id(String lesson_id) {
        this.lesson_id = lesson_id;
    }

    public String name;
    public String number;
    public String course_id;
    public String lesson_id;

    public Lessons(String name, String number, String course_id, String lesson_id) {
        this.name = name;
        this.number = number;
        this.course_id = course_id;
        this.lesson_id = lesson_id;
    }

    public Lessons() {
    }


}
