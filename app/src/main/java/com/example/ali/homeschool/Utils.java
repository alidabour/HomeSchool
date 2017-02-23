package com.example.ali.homeschool;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.OperationApplicationException;
import android.os.RemoteException;

import com.example.ali.homeschool.data.DataProvider;
import com.example.ali.homeschool.data.Entry.CourseColumns;
import com.example.ali.homeschool.data.Entry.LessonColumns;
import com.example.ali.homeschool.data.Entry.SubjectColumns;
import com.example.ali.homeschool.data.Entry.TopicColumns;
import com.example.ali.homeschool.data.Entry.TopicContentColumns;

import java.util.ArrayList;

/**
 * Created by ali on 09/10/16.
 * dummy data for now
 */

public class Utils {
    public static ArrayList subjectToContentVals(String subj){
        //Test
        int test;
        int test2;
        ArrayList<ContentProviderOperation> batchOperations = new ArrayList<>();
        ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(
                DataProvider.Subject.CONTENT_URI);
             builder.withValue(SubjectColumns.SUBJECT_NAME,subj);
             batchOperations.add(builder.build());

        return batchOperations;
    }
    public static void addDefaultsSubjects(Context context) throws RemoteException, OperationApplicationException {
        context.getContentResolver().applyBatch(DataProvider.AUTHORITY,Utils.subjectToContentVals("Science"));
        context.getContentResolver().applyBatch(DataProvider.AUTHORITY,Utils.subjectToContentVals("Arabic"));
        context.getContentResolver().applyBatch(DataProvider.AUTHORITY,Utils.subjectToContentVals("Math"));

    }
    public static ArrayList courseToContentValue (String course,int id){
        ArrayList<ContentProviderOperation> batchOperations = new ArrayList<>();
        ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(
                DataProvider.Course.CONTENT_URI
        );
        builder.withValue(CourseColumns.COURSE_NAME,course);
        builder.withValue(CourseColumns.GLOBAL_ID,id);
        batchOperations.add(builder.build());
        return batchOperations;
    }
    public static void addCoursesTest(Context context) throws RemoteException, OperationApplicationException {
        context.getContentResolver().applyBatch(DataProvider.AUTHORITY,courseToContentValue("FirstCourse",1));
        context.getContentResolver().applyBatch(DataProvider.AUTHORITY,courseToContentValue("SecondCourse",2));
        context.getContentResolver().applyBatch(DataProvider.AUTHORITY,courseToContentValue("ThirdCourse",3));
    }
    public static ArrayList lessonToContentValue (String lesson,int id){
        ArrayList<ContentProviderOperation> batchOperations = new ArrayList<>();
        ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(
                DataProvider.Lesson.CONTENT_URI
        );
        builder.withValue(LessonColumns.LESSON_NAME,lesson);
        builder.withValue(LessonColumns.COURSE_ID,id);
        batchOperations.add(builder.build());
        return batchOperations;
    }
    public static void addLessonsTest(Context context) throws RemoteException, OperationApplicationException {
        context.getContentResolver().applyBatch(DataProvider.AUTHORITY,lessonToContentValue("FirstLesson",1));
        context.getContentResolver().applyBatch(DataProvider.AUTHORITY,lessonToContentValue("SecondLesson",1));
        context.getContentResolver().applyBatch(DataProvider.AUTHORITY,lessonToContentValue("ThirdLesson",1));

        context.getContentResolver().applyBatch(DataProvider.AUTHORITY,lessonToContentValue("FirstLesson",2));
        context.getContentResolver().applyBatch(DataProvider.AUTHORITY,lessonToContentValue("SecondLesson",2));
        context.getContentResolver().applyBatch(DataProvider.AUTHORITY,lessonToContentValue("ThirdLesson",2));


        context.getContentResolver().applyBatch(DataProvider.AUTHORITY,lessonToContentValue("FirstLesson",3));
        context.getContentResolver().applyBatch(DataProvider.AUTHORITY,lessonToContentValue("SecondLesson",3));
        context.getContentResolver().applyBatch(DataProvider.AUTHORITY,lessonToContentValue("ThirdLesson",3));
    }
    public static ArrayList topicToContentValue (String topic){
        ArrayList<ContentProviderOperation> batchOperations = new ArrayList<>();
        ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(
                DataProvider.Topic.CONTENT_URI
        );
        builder.withValue(TopicColumns.TOPIC_NAME,topic);
        batchOperations.add(builder.build());
        return batchOperations;
    }
    public static void addTopicsTest(Context context) throws RemoteException, OperationApplicationException {
        context.getContentResolver().applyBatch(DataProvider.AUTHORITY,topicToContentValue("FirstTopic"));
        context.getContentResolver().applyBatch(DataProvider.AUTHORITY,topicToContentValue("SecondTopic"));
        context.getContentResolver().applyBatch(DataProvider.AUTHORITY,topicToContentValue("ThirdTopic"));
    }
    public static ArrayList topicContentToContentValue (byte[] topicContent){
        ArrayList<ContentProviderOperation> batchOperations = new ArrayList<>();
        ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(
                DataProvider.TopicContent.CONTENT_URI
        );
        builder.withValue(TopicContentColumns.CONTENT,topicContent);
        batchOperations.add(builder.build());
        return batchOperations;
    }
    public static void addTopicsContentsTest(Context context) throws RemoteException, OperationApplicationException {
        context.getContentResolver().applyBatch(DataProvider.AUTHORITY,topicContentToContentValue(new byte[] {100}));
        context.getContentResolver().applyBatch(DataProvider.AUTHORITY,topicContentToContentValue(new byte[] {102}));
        context.getContentResolver().applyBatch(DataProvider.AUTHORITY,topicContentToContentValue(new byte[] {104}));
    }

}
