package com.example.ali.homeschool.data.Entry;

import android.provider.BaseColumns;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * Created by Dabour on 10/27/2016.
 */

public class TopicColumns implements BaseColumns {
    @DataType(DataType.Type.INTEGER) @PrimaryKey @AutoIncrement
    public static final String _ID = BaseColumns._ID;
    @DataType(DataType.Type.INTEGER)
    public static final String TOPIC_ID = "topic_id";
    @DataType(DataType.Type.TEXT)
    public static final String TOPIC_NAME = "topic_name";
    @DataType(DataType.Type.INTEGER)
    public static final String TOPIC_NUMBER = "topic_number";
    @DataType(DataType.Type.TEXT)
    public static final String TOPIC_LAYOUT = "topic_layout";
}
