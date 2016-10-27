package com.example.ali.homeschool.data.Entry;

import android.provider.BaseColumns;
import android.provider.SearchRecentSuggestions;

import com.example.ali.homeschool.data.DataProvider;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * Created by Dabour on 10/27/2016.
 */

public class LessonColumns implements BaseColumns {
    @DataType(DataType.Type.INTEGER)@PrimaryKey @AutoIncrement
    public static final String _ID = BaseColumns._ID;
    @DataType(DataType.Type.INTEGER)
    public static final String LESSON_ID = "lesson_id";
    @DataType(DataType.Type.INTEGER)
    public static final String COURSE_ID = "course_id";
    @DataType(DataType.Type.TEXT)
    public static final String LESSON_NAME = "lesson_name";
    @DataType(DataType.Type.INTEGER)
    public static final String LESSON_NUMBER = "lesson_number";
}
