package com.example.ali.homeschool.InstructorTopic.CreationHelper;

import android.app.Activity;
import android.net.Uri;

/**
 * Created by Ali on 6/18/2017.
 */

public abstract class MainDialog {
    Integer id;

    Activity activity;
    OnLayoutReadyInterface onLayoutReadyInterface;
    public MainDialog(
            OnLayoutReadyInterface onLayoutReadyInterface) {
        this.onLayoutReadyInterface = onLayoutReadyInterface;
    }

    public MainDialog(Integer id, Activity activity,
                      OnLayoutReadyInterface onLayoutReadyInterface) {
        this.id = id;
        this.activity = activity;
        this.onLayoutReadyInterface = onLayoutReadyInterface;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

}
