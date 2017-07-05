//package com.almanara.homeschool;
//
//import android.app.Application;
//
//import com.squareup.leakcanary.LeakCanary;
//
///**
// * Created by Ali on 7/3/2017.
// */
//
//public class Almanara extends Application {
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(this);
//        // Normal app init code...
//    }
//}
