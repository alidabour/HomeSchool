package com.example.ali.homeschool;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.OperationApplicationException;
import android.os.RemoteException;

import com.example.ali.homeschool.data.DataProvider;
import com.example.ali.homeschool.data.Entry.ChildColumns;
import com.example.ali.homeschool.data.Entry.ChildProgressColumns;
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
    public static ArrayList courseToContentValue (String courseName,int id,String courseTeacher,String courseDes,int courseRating){
        ArrayList<ContentProviderOperation> batchOperations = new ArrayList<>();
        ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(
                DataProvider.Course.CONTENT_URI
        );
        builder.withValue(CourseColumns.COURSE_NAME,courseName);
        builder.withValue(CourseColumns.GLOBAL_ID,id);
        builder.withValue(CourseColumns.COURSE_TEACHER,courseTeacher);
        builder.withValue(CourseColumns.COURSE_DES,courseDes);
        builder.withValue(CourseColumns.COURSE_RATINGS,courseRating);


        batchOperations.add(builder.build());
        return batchOperations;
    }
    public static void addCoursesTest(Context context) throws RemoteException, OperationApplicationException {
        String des ="Start to Learn about animals Learn about animals Learn about animals Learn about animals Learn about animalsLearn about animals Learn about animals Learn about animals Learn about animals";
                context.getContentResolver().applyBatch(DataProvider.AUTHORITY,
                courseToContentValue("FirstCourse",1,"Ahmed Ali",des,88));
        context.getContentResolver().applyBatch(DataProvider.AUTHORITY,courseToContentValue("SecondCourse",2,"Omar Ali",des,28));
        context.getContentResolver().applyBatch(DataProvider.AUTHORITY,courseToContentValue("ThirdCourse",3,"Alaa Omar",des,882));
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
    public static ArrayList topicToContentValue (String topicName,String topicContent){
        ArrayList<ContentProviderOperation> batchOperations = new ArrayList<>();
        ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(
                DataProvider.Topic.CONTENT_URI
        );
        builder.withValue(TopicColumns.TOPIC_NAME,topicName);
        builder.withValue(TopicColumns.TOPIC_LAYOUT,topicContent);

        batchOperations.add(builder.build());
        return batchOperations;
    }
    public static void addTopicsTest(Context context) throws RemoteException, OperationApplicationException {
        context.getContentResolver().applyBatch(DataProvider.AUTHORITY,
                topicToContentValue("Dog","<LinearLayout android:orientation=\"vertical\" android:layout_weight=\"0\" android:id=\"6\" android:layout_width=\"match_parent\" android:layout_height=\"match_parent\"><ImageView android:layout_weight=\"5\" android:id=\"1\" android:layout_width=\"match_parent\" android:layout_height=\"wrap_content\" /><LinearLayout android:orientation=\"horizontal\" android:layout_weight=\"1\" android:id=\"6\" android:layout_width=\"match_parent\" android:layout_height=\"wrap_content\"><Button android:layout_weight=\"1\" android:id=\"12\" android:text=\"Meow\" android:layout_width=\"0\" android:layout_height=\"match_parent\" /><Button android:layout_weight=\"1\" android:id=\"1\" android:text=\"Cat\" android:layout_width=\"0\" android:layout_height=\"match_parent\" /></LinearLayout></LinearLayout>"
        ));
        context.getContentResolver().applyBatch(DataProvider.AUTHORITY,topicToContentValue("Cat","<LinearLayout android:orientation=\"vertical\" android:layout_weight=\"0\" android:id=\"6\" android:layout_width=\"match_parent\" android:layout_height=\"match_parent\"><ImageView android:layout_weight=\"5\" android:id=\"6\" android:layout_width=\"match_parent\" android:layout_height=\"wrap_content\" /><LinearLayout android:orientation=\"horizontal\" android:layout_weight=\"1\" android:id=\"6\" android:layout_width=\"match_parent\" android:layout_height=\"wrap_content\"><Button android:layout_weight=\"1\" android:id=\"21\" android:text=\"Bark\" android:layout_width=\"0\" android:layout_height=\"match_parent\" /><Button android:layout_weight=\"1\" android:id=\"2\" android:text=\"Dog\" android:layout_width=\"0\" android:layout_height=\"match_parent\" /></LinearLayout></LinearLayout>"));
//        context.getContentResolver().applyBatch(DataProvider.AUTHORITY,topicToContentValue("ThirdTopic"));
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
    public static ArrayList childToContentValue (int id,String name){
        ArrayList<ContentProviderOperation> batchOperations = new ArrayList<>();
        ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(
                DataProvider.Child.CONTENT_URI
        );
        builder.withValue(ChildColumns.CHILD_ID,id);
        builder.withValue(ChildColumns.CHILD_NAME,name);
        batchOperations.add(builder.build());
        return batchOperations;
    }
    public static void addChildTest(Context context) throws RemoteException, OperationApplicationException {
        context.getContentResolver().applyBatch(DataProvider.AUTHORITY,childToContentValue(1,"Ali"));
        context.getContentResolver().applyBatch(DataProvider.AUTHORITY,childToContentValue(2,"Ahmed"));
        context.getContentResolver().applyBatch(DataProvider.AUTHORITY,childToContentValue(3,"Omar"));

    }
    public static ArrayList childProgressToContentValue (int id,int progress,String name){
        ArrayList<ContentProviderOperation> batchOperations = new ArrayList<>();
        ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(
                DataProvider.ChildProgress.CONTENT_URI
        );
        builder.withValue(ChildProgressColumns.CHILD_ID,id);
        builder.withValue(ChildProgressColumns.COURSE_PROGRESS,progress);
        builder.withValue(ChildProgressColumns.COURSE_NAME,name);
        batchOperations.add(builder.build());
        return batchOperations;
    }
    public static void addChildProgressTest(Context context) throws RemoteException, OperationApplicationException {
        context.getContentResolver().applyBatch(DataProvider.AUTHORITY,childProgressToContentValue(1,90,"C++"));
        context.getContentResolver().applyBatch(DataProvider.AUTHORITY,childProgressToContentValue(2,30,"Java"));
        context.getContentResolver().applyBatch(DataProvider.AUTHORITY,childProgressToContentValue(3,50,"Machine Learning"));

    }

}
