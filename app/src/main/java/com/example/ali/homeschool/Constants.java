package com.example.ali.homeschool;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.ali.homeschool.InstructorTopic.TextAppInterface;
import com.jrummyapps.android.colorpicker.ColorPickerDialog;

import edu.sfsu.cs.orange.ocr.Answer;

/**
 * Created by Ali on 6/2/2017.
 */

public class Constants {
    // Text Color
    public static final int textColor = -2565924;
    public static final int uniqueTextColor = -568007;
    //Result Code
    public static final int CORRECTANSWER = 10;
    public static final int WRONGANSWER = -10;

    public static String language ="eng" ;
    //Request Code
    public static final int COLOR = 100;
    public static final int SIMPLE = 200;
    public static final int SPEECH = 300;
    public static final int Color_Request = 122;
    public static final int Text_Detection = 123;

    //Replace all
    public final static String PUT_ID_HERE = "PUTIDHERE";
    public final  static String PUT_TEXT_HERE = "PUTTEXTHERE";
    public final static String PUTTEXTAPPEARANCEHERE = "PUTTEXTAPPEARANCEHERE";
    public final  static String PUTSIZEHERE = "PUTSIZEHERE";
    public final static String PUT_ACTIVITY_HERE = "PUTACTIVITYHERE";
    public final static String PUTCOLOR = "PUTCOLOR";
    public final static String PUT_ANSWER_HERE ="PUTANSWERHERE";
    public final static String PUT_LAN_HERE ="PUTLANHERE";

    public final static String ARRANGE = "ARR4444ANGE";
    //XML 'S
    public final static String radioGroupStart = "<RadioGroup android:layout_width=\"match_parent\"\n" +
            "        android:layout_height=\"wrap_content\"\n" +
            "        android:id=\""+ PUT_ID_HERE +"\" >" ;
    public final static String radioGroupEnd = "</RadioGroup>";
    public final static String radioButton = "<RadioButton\n" +
            "            android:layout_width=\"match_parent\"\n" +
            "            android:layout_height=\"wrap_content\"\n" +
            "            android:text=\""+ PUT_ANSWER_HERE +"\"\n" +
            "            android:id=\""+ PUT_ID_HERE +"\"/>";

    public final static String actionTextXML = "<TextView " +
            " android:id=\""+ PUT_ID_HERE +"\" " +
            "android:layout_weight=\"0\" " +
            " android:text=\""+ PUT_TEXT_HERE + "\" " +
//            "        android:textSize=\""+PUTSIZEHERE+"\"\n" +
                    " android:textColor=\""+PUTCOLOR+"\" "+
            " android:textAppearance=\""+PUTTEXTAPPEARANCEHERE+"\" "+
            " android:layout_width=\"match_parent\" " +
            " android:layout_height=\"wrap_content\" />";



    public final static String soundXML =
            "<Button " +
                    "android:layout_weight=\"0\"" +
                    " android:id=\"PUT_ID_HERE\" " +
                    "android:text=\"PUTSOUNDTEXTHERE\" " +
                    "android:layout_width=\"match_parent\" " +
                    "android:layout_height=\"wrap_content\"" +
                    "homeSchool:audioLink=\"PUTLINKHERE\" />";

    public final static String end = "</LinearLayout></RelativeLayout>";

    public final static String start = "<RelativeLayout " +
            "xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
            "android:id=\"2\"" +
            " android:layout_weight=\"0\" "+
            "android:layout_width=\"match_parent\"\n" +
            "android:layout_height=\"match_parent\">" +
            "<LinearLayout " +
            "android:layout_centerInParent=\"true\" " +
            "android:orientation=\"vertical\" " +
            "android:layout_weight=\"0\" " +
            "android:id=\"2000\" " +
            "android:layout_width=\"match_parent\" " +
            "android:layout_height=\"wrap_content\">"+ARRANGE;


    //TextView Helper
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
        String textView = actionTextXML.replaceAll(PUT_ID_HERE, String.valueOf(id));
        textView = textView.replaceAll(PUT_TEXT_HERE,text);
//        textView = textView.replaceAll(PUTSIZEHERE,size);
        textView = textView.replaceAll(PUTCOLOR, String.valueOf(color));
        textView = textView.replaceAll(PUTTEXTAPPEARANCEHERE, String.valueOf(textAppearance));
        textView+=ARRANGE;
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
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });



    }
    public static void setColorButton(View view,int color){
        Button openColorPicker = (Button) view.findViewById(R.id.colorsButton);
        openColorPicker.setBackgroundColor(color);
    }

    /*Button helper
    *
    *
     */
    public final static String PUT_SOUND_LINK_HERE = "PUTSOUNDLINKHERE";
    public final static String BUTTON_HOLDER = "<Button" +
            " android:layout_weight=\"0\"" +
            " android:id=\""+ PUT_ID_HERE +"\" " +
            "android:text=\""+ PUT_TEXT_HERE +"\" " +
            "android:layout_width=\"match_parent\" " +
            "android:layout_height=\"wrap_content\"" +
            "homeSchool:audioLink=\""+ PUT_SOUND_LINK_HERE +"\""+
            "homeSchool:activity=\""+ PUT_ACTIVITY_HERE +"\" " +
            "homeSchool:answer=\""+ PUT_ANSWER_HERE +"\"" +
            "homeSchool:lan=\""+PUT_LAN_HERE+"\"" +
            "/>";
    public static String mButton(int id, String text, String activity, Answer answer,String soundLink){
        soundLink = soundLink.replaceAll("&", "&amp;");
        soundLink = soundLink.replaceAll("\\?", "&#63;");
        String button = BUTTON_HOLDER.replaceAll(PUT_ID_HERE, String.valueOf(id));
        button = button.replaceAll(PUT_TEXT_HERE,text);
        button = button.replaceAll(PUT_ACTIVITY_HERE,activity);
        button = button.replaceAll(PUT_ANSWER_HERE,answer.getAnswer());
        button = button.replaceAll(PUT_SOUND_LINK_HERE,soundLink);
        button = button.replaceAll(PUT_LAN_HERE,answer.getLan());
        button+=ARRANGE;
        return button;
    }
    //Sound
    public static String mButton(int id,String soundLink, String text){
        soundLink = soundLink.replaceAll("&", "&amp;");
        soundLink = soundLink.replaceAll("\\?", "&#63;");
        String button = BUTTON_HOLDER.replaceAll(PUT_ID_HERE, String.valueOf(id));
        button = button.replaceAll(PUT_TEXT_HERE,text);
        button = button.replaceAll(PUT_SOUND_LINK_HERE,soundLink);
        button+=ARRANGE;
        return button;
    }
    /*Image Helper
    *
    *
    *
     */
    public final static String PUT_IMAGE_URL_HERE = "PUTIMAGEURLHERE";
    public final static String IMAGE_VIEW_HOLDER = "<ImageView " +
            "android:layout_weight=\"1\" " +
            "android:id=\""+ PUT_ID_HERE +"\" " +
            "android:layout_width=\"match_parent\" " +
            "android:layout_height=\"wrap_content\" " +
            "homeSchool:src=\""+ PUT_IMAGE_URL_HERE +"\" />";
    public static String mImageView(int id,String url){
        String imageView = IMAGE_VIEW_HOLDER.replace(PUT_ID_HERE,String.valueOf(id));
        imageView = imageView.replace(PUT_IMAGE_URL_HERE,url);
        imageView += ARRANGE;
        return imageView;
    }
}
