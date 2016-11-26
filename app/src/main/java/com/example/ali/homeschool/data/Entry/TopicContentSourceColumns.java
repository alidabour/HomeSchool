package com.example.ali.homeschool.data.Entry;

import android.provider.BaseColumns;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * Created by Dabour on 11/26/2016.
 */

public class TopicContentSourceColumns implements BaseColumns {
    @DataType(DataType.Type.INTEGER) @PrimaryKey @AutoIncrement
    public static final String _ID = BaseColumns._ID;

}
