package com.example.ali.homeschool.data;

/**
 * Created by ali on 09/10/16.
 */

import android.graphics.Shader;
import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

@ContentProvider(authority =DataProvider.AUTHORITY,database = SchoolDatabase.class)
public class DataProvider {
    public static final String AUTHORITY = "com.example.ali.homeschool.data.DataProvider";
    static final Uri BASE_CONTENT_URI = Uri.parse("content://"+AUTHORITY);
    interface Path {
        //String CATEGORY = "category";
        String SUBJECT = "subjectS";
        String COURSE = "course";
        String LESSON = "lesson";
        String TOPIC = "topic";
        String TOPIC_CONTENT = "topic_content";
        String CHILD = "child";
        String CHILD_PROGRESS = "child_progress";
    }
    private static Uri buildUri(String... paths){
        Uri.Builder builder= BASE_CONTENT_URI.buildUpon();
        for(String path:paths){
            builder.appendPath(path);
        }
        return builder.build();
    }

//    @TableEndpoint(table = SchoolDatabase.CATEGORY)
//    public static class Category{
//        @ContentUri(
//                path = Path.CATEGORY,
//                type = "vnd.android.cursor.dir/category"
//        )
//        public static final Uri CONTENT_URI = buildUri(Path.CATEGORY);
//        @InexactContentUri(
//                name = "CATEGORY_ID",
//                path = Path.CATEGORY+"/*",
//                type = "vnd.android.cursor.dir/category",
//                whereColumn = CategoryColumns.CATEGORY_NAME,
//                pathSegment = 1
//        )
//        public static Uri withName (String name){
//            return buildUri(Path.CATEGORY,name);
//        }
//    }
    @TableEndpoint(table = SchoolDatabase.SUBJECT)
    public static class Subject {
        @ContentUri(
                path = Path.SUBJECT,
                type = "vnd.android.cursor.dir/category"
        )
        public static final Uri CONTENT_URI = buildUri(Path.SUBJECT);
    }
    @TableEndpoint(table = SchoolDatabase.COURSE)
    public static class Course {
        @ContentUri(path = Path.COURSE, type = "vnd.android.cursor.dir/course")
        public static final Uri CONTENT_URI = buildUri(Path.COURSE);
    }
    @TableEndpoint(table = SchoolDatabase.LESSON)
    public static class Lesson {
        @ContentUri( path = Path.LESSON , type = "vnd.android.cursor.dir/lesson")
        public static final Uri CONTENT_URI = buildUri(Path.LESSON);
    }
    @TableEndpoint(table = SchoolDatabase.TOPIC)
    public static class Topic {
        @ContentUri( path = Path.TOPIC , type = "vnd.android.cursor.dir/topic")
        public static final Uri CONTENT_URI = buildUri(Path.TOPIC);
    }
    @TableEndpoint(table = SchoolDatabase.TOPIC_CONTENT)
    public static class TopicContent {
        @ContentUri( path = Path.TOPIC_CONTENT , type = "vnd.android.cursor.dir/topic_content")
        public static final Uri CONTENT_URI = buildUri(Path.TOPIC_CONTENT);
    }
    @TableEndpoint(table = SchoolDatabase.CHILD)
    public static class Child {
        @ContentUri(path = Path.CHILD, type = "vnd.android.cursor.dir/child")
        public static final Uri CONTENT_URI = buildUri(Path.CHILD);
    }
    @TableEndpoint(table = SchoolDatabase.CHILD_PROGRESS)
    public static class ChildProgress {
        @ContentUri(path = Path.CHILD_PROGRESS, type = "vnd.android.cursor.dir/child_progress")
        public static final Uri CONTENT_URI = buildUri(Path.CHILD_PROGRESS);
    }
}
