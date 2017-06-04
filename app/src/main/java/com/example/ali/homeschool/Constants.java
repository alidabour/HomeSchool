package com.example.ali.homeschool;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.ali.homeschool.InstructorTopic.InstructorTopicActivity;
import com.example.ali.homeschool.InstructorTopic.TextAppInterface;
import com.example.ali.homeschool.exercises.Answer;
import com.jrummyapps.android.colorpicker.ColorPickerDialog;

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
    public static final int SPEECH = 300;

    //Replace all
    public final static String PUTIDHERE = "PUTIDHERE";
    public final  static String PUTACTIONTEXTHERE = "PUTACTIONTEXTHERE";
    public final static String PUTTEXTAPPEARANCEHERE = "PUTTEXTAPPEARANCEHERE";
    public final  static String PUTSIZEHERE = "PUTSIZEHERE";
    public final static String PUTACTIVITYHERE = "PUTACTIVITYHERE";
    public final static String PUTCOLOR = "PUTCOLOR";
    public final static String PUTANSWERHERE ="PUTANSWERHERE";
    //XML 'S
    public final static String radioGroupStart = "<RadioGroup android:layout_width=\"match_parent\"\n" +
            "        android:layout_height=\"wrap_content\"\n" +
            "        android:id=\""+PUTIDHERE+"\" >" ;
    public final static String radioGroupEnd = "</RadioGroup>";
    public final static String radioButton = "<RadioButton\n" +
            "            android:layout_width=\"match_parent\"\n" +
            "            android:layout_height=\"wrap_content\"\n" +
            "            android:text=\""+PUTANSWERHERE+"\"\n" +
            "            android:id=\""+PUTIDHERE+"\"/>";

    public final static String actionTextXML = "<TextView\n" +
            "        android:id=\""+PUTIDHERE+"\"\n" +
            "android:layout_weight=\"0\"" +
            "        android:text=\""+ PUTACTIONTEXTHERE+ "\"\n" +
//            "        android:textSize=\""+PUTSIZEHERE+"\"\n" +
                    " android:textColor=\""+PUTCOLOR+"\" "+
            "android:textAppearance=\""+PUTTEXTAPPEARANCEHERE+"\""+
            "        android:layout_width=\"match_parent\"\n" +
            "        android:layout_height=\"wrap_content\"/>";



    public final static String actionButtonXML = "<Button" +
            " android:layout_weight=\"0\"" +
            " android:id=\""+PUTIDHERE+"\" " +
            "android:text=\""+PUTACTIONTEXTHERE+"\" " +
            "android:layout_width=\"match_parent\" " +
            "android:layout_height=\"wrap_content\"" +
            "homeSchool:activity=\""+PUTACTIVITYHERE+"\" " +
            "homeSchool:answer=\""+PUTANSWERHERE+"\"" +
            "/>";
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


    //TextView Helper
//     <item>Button 14sp CAPS</item>
//        <item>Caption 12sp</item>
//        <item>Body1 14sp</item>
//        <item>Body2 14sp Bold</item>
//        <item>Subheading 16sp</item>
//        <item>Title 20sp</item>
//        <item>Headline 24sp</item>
//        <item>Display1 34sp</item>
//        <item>Display2 45sp</item>
//        <item>Display3 56sp</item>
//        <item>Display4 112sp</item>
    public static int[] textAppearance = {android.R.style.TextAppearance_Material_Button,
            android.R.style.TextAppearance_Material_Caption,
            android.R.style.TextAppearance_Material_Body1,
            android.R.style.TextAppearance_Material_Body2,
            android.R.style.TextAppearance_Material_Subhead,
            android.R.style.TextAppearance_Material_Title,
            android.R.style.TextAppearance_Material_Headline,
            android.R.style.TextAppearance_Material_Display1,
            android.R.style.TextAppearance_Material_Display2,
            android.R.style.TextAppearance_Material_Display3,
            android.R.style.TextAppearance_Material_Display4
    };
    public static String mTextView(int id,String text,int color,int textAppearance){
        String textView = actionTextXML.replaceAll(PUTIDHERE, String.valueOf(id));
        textView = textView.replaceAll(PUTACTIONTEXTHERE,text);
//        textView = textView.replaceAll(PUTSIZEHERE,size);
        textView = textView.replaceAll(PUTCOLOR, String.valueOf(color));
        textView = textView.replaceAll(PUTTEXTAPPEARANCEHERE, String.valueOf(textAppearance));
        return textView;
    }

    public static void textViewProperties(View view, final Activity activity, final TextAppInterface textAppInterface){
        TextAppInterface textAppInterface1 = textAppInterface;
        Button openColorPicker = (Button) view.findViewById(R.id.colorsButton);
        openColorPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorPickerDialog.Builder c = ColorPickerDialog.newBuilder().setColor(0xFF000000);
                c.show(activity);
            }
        });
        Spinner textSizeSpinner = (Spinner) view.findViewById(R.id.textSizes);
        ArrayAdapter<CharSequence> textSizes = ArrayAdapter.createFromResource(activity,
                R.array.text_size_array, android.R.layout.simple_spinner_item);
        textSizeSpinner.setAdapter(textSizes);
        textSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               textAppInterface.onSelected(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }
    public static void setColorButton(View view,int color){
        Button openColorPicker = (Button) view.findViewById(R.id.colorsButton);
        openColorPicker.setBackgroundColor(color);
    }

    //Button helper
    public static String mButton(int id, String text, String activity, Answer answer){
        String button =actionButtonXML.replaceAll(PUTIDHERE, String.valueOf(id));
        button = button.replaceAll(PUTACTIONTEXTHERE,text);
        button = button.replaceAll(PUTACTIVITYHERE,activity);
        button = button.replaceAll(PUTANSWERHERE,answer.getAnswer());
        return button;
    }



}
