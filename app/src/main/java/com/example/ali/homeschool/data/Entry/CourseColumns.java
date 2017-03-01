package com.example.ali.homeschool.data.Entry;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * Created by ali on 09/10/16.
 */

public class CourseColumns {
    @DataType(DataType.Type.INTEGER) @PrimaryKey @AutoIncrement
    public static final String _ID = "_id";
    @DataType(DataType.Type.INTEGER)
    public static final String GLOBAL_ID = "global_id";
    @DataType(DataType.Type.TEXT)
    public static final String COURSE_NAME = "course_name";
    @DataType(DataType.Type.TEXT)
    public static final String COURSE_TEACHER = "course_teacher";
    @DataType(DataType.Type.INTEGER)
    public static final String SUBJECT_ID = "subject_id";
    @DataType(DataType.Type.TEXT)
    public static final String COURSE_IMG = "course_img";
    @DataType(DataType.Type.TEXT)
    public static final String COURSE_DES = "course_des";
    @DataType(DataType.Type.INTEGER)
    public static final String COURSE_RATINGS = "course_ratings";


}
