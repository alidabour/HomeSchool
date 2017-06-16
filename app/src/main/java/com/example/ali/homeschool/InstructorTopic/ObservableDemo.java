package com.example.ali.homeschool.InstructorTopic;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by hossam on 16/06/17.
 */

public class ObservableDemo implements Observer {
    public String name;

    public ObservableDemo(String name) {
        this.name = name;
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}