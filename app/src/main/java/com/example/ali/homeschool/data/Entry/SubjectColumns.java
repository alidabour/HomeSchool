package com.example.ali.homeschool.data.Entry;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * Created by Dabour on 10/23/2016.
 */

public class SubjectColumns {
    @DataType(DataType.Type.INTEGER) @PrimaryKey @AutoIncrement
    public static final String _ID = "_id";
    @DataType(DataType.Type.TEXT) @NotNull
    public static final String SUBJECT_NAME = "_subject_name";
    @DataType(DataType.Type.TEXT) //@NotNull
    public static final String SUBJECT_PARENT = "_subject_parent";
}
