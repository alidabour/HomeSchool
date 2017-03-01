package com.example.ali.homeschool.data.Entry;

import android.provider.BaseColumns;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * Created by Ali on 3/1/2017.
 */

public class ChildProgressColumns implements BaseColumns {
    @DataType(DataType.Type.INTEGER) @PrimaryKey @AutoIncrement
    public static final String _ID = BaseColumns._ID;
    @DataType(DataType.Type.INTEGER)
    public static final String CHILD_ID = "child_id";
    @DataType(DataType.Type.TEXT)
    public static final String COURSE_NAME = "course_name";
    @DataType(DataType.Type.INTEGER)
    public static final String COURSE_PROGRESS = "course_progress";

}
