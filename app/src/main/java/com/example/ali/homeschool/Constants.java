package com.example.ali.homeschool;

/**
 * Created by Ali on 6/2/2017.
 */

public class Constants {
    //Result Code
    public static final int CORRECTANSWER = 10;
    public static final int WRONGANSWER = -10;

    //Request Code
    public static final int COLOR = 100;
    public static final int SIMPLE = 200;

    //Replace all
    public final static String PUTIDHERE = "PUTIDHERE";
    public final  static String PUTACTIONTEXTHERE = "PUTACTIONTEXTHERE";
    public final  static String PUTSIZEHERE = "PUTSIZEHERE";
    public final static String PUTACTIVITYHERE = "PUTACTIVITYHERE";
    public final static String PUTCOLOR = "PUTCOLOR";
    //XML S
    public final static String actionTextXML = "<TextView\n" +
            "        android:id=\"PUTIDHERE\"\n" +
            "android:layout_weight=\"0\"" +
            "        android:text=\"PUTACTIONTEXTHERE\"\n" +
            "        android:textSize=\"PUTSIZEHERE\"\n" +
                    "android:textColor=\""+PUTCOLOR+"\" "+
            "        android:layout_width=\"match_parent\"\n" +
            "        android:layout_height=\"wrap_content\"/>";



    public final static String actionButtonXML = "<Button android:layout_weight=\"0\" android:id=\"PUTIDHERE\" android:text=\"PUTACTIONTEXTHERE\" android:layout_width=\"match_parent\" " +
            "android:layout_height=\"wrap_content\"" +
            "homeSchool:activity=\"PUTACTIVITYHERE\" />";
    public final static String soundXML =
            "<Button android:layout_weight=\"0\" android:id=\"PUTIDHERE\" android:text=\"PUTSOUNDTEXTHERE\" android:layout_width=\"match_parent\" " +
                    "android:layout_height=\"wrap_content\"" +
                    "homeSchool:audioLink=\"PUTLINKHERE\" />";

    public final static String end = "</LinearLayout></RelativeLayout>";

    public final static String start = "<RelativeLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" + "android:id=\"2\" android:layout_weight=\"0\" "+
            "    android:layout_width=\"match_parent\"\n" +
            "    android:layout_height=\"match_parent\">" +
            "<LinearLayout " + "android:layout_centerInParent=\"true\" " +
            "android:orientation=\"vertical\" " +
            "android:layout_weight=\"0\" " +
            "android:id=\"2000\" " +
            "android:layout_width=\"wrap_content\" " +
            "android:layout_height=\"wrap_content\">";

}
