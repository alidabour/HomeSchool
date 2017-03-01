package com.example.ali.homeschool.data;

/**
 * Created by ali on 09/10/16.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.ali.homeschool.data.Entry.ChildColumns;
import com.example.ali.homeschool.data.Entry.ChildProgressColumns;
import com.example.ali.homeschool.data.Entry.CourseColumns;
import com.example.ali.homeschool.data.Entry.LessonColumns;
import com.example.ali.homeschool.data.Entry.SubjectColumns;
import com.example.ali.homeschool.data.Entry.TopicColumns;
import com.example.ali.homeschool.data.Entry.TopicContentColumns;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.OnCreate;
import net.simonvt.schematic.annotation.Table;

@Database(version = SchoolDatabase.VERSION)
public class SchoolDatabase {
    private SchoolDatabase(){}
    static final int VERSION = 1;
    @Table(SubjectColumns.class) public static final String SUBJECT = "subject";
    @Table(CourseColumns.class) public static final String COURSE = "course";
    @Table(LessonColumns.class) public static final String LESSON = "lesson";
    @Table(TopicColumns.class) public  static final String TOPIC = "topic";
    @Table(TopicContentColumns.class) public static final String TOPIC_CONTENT = "topic_content";
    @Table(ChildColumns.class) public static final String CHILD = "child";
    @Table(ChildProgressColumns.class) public static final String CHILD_PROGRESS = "child_progress";
    @OnCreate public static void onCreate(Context context, SQLiteDatabase db){

    }
}
