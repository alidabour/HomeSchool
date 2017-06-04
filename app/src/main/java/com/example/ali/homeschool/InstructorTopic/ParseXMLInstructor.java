package com.example.ali.homeschool.InstructorTopic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.ali.homeschool.Constants;
import com.example.ali.homeschool.exercises.speech.Speech;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Dabour on 11/20/2016.
 */

public class ParseXMLInstructor {
    private static final String ns = null;
    private static Context context;
    private float scale;
    ImageClicked loadImage;
    Activity activity;

    public View parse(final Activity activity,InputStream in, Context context,
                      ImageClicked loadImage) throws XmlPullParserException, IOException {
        this.loadImage = loadImage;
        try {
            this.activity = activity;
            this.context = context;
            scale = getScale(context);
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            Log.v("Parse", "parser getName :" + parser.getName());
            if (parser.getName().equals("RelativeLayout")) {
                Log.v("Parse", "parser getName :" + parser.getName());
                return readRelativeLayout(parser);
            } else {
                Log.v("Parse", "parser getName :" + parser.getName());
                return readLinearLayout(parser);
            }
        } finally {
            in.close();
        }
    }

    private View readRelativeLayout(
            XmlPullParser parser) throws XmlPullParserException, IOException {
        Log.v("Parse", "readRelativeLayout");
//        int id = Integer.parseInt(parser.getAttributeValue(ns, "android:id"));
//        float weight = Float.parseFloat(parser.getAttributeValue(ns, "android:layout_weight"));
//        Log.v("Parse", "Weight : " + weight);
        Log.v("Parse", "parser getName :" + parser.getName());
        RelativeLayout relativeLayout = new RelativeLayout(context);
//        linearLayout.setId(id);
        String height = parser.getAttributeValue(ns, "android:layout_height");
        String width = parser.getAttributeValue(ns, "android:layout_width");
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        relativeLayout.setLayoutParams(rlp);

//        relativeLayout.setLayoutParams(getLayoutParams(height, width, scale));

        parser.require(XmlPullParser.START_TAG, ns, "RelativeLayout");
        while (parser.next() != XmlPullParser.END_TAG) {
//            Log.v("Parse","While --> parser getName :"+parser.getName());
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("ImageView")) {
                relativeLayout.addView(readImageView(parser));
            } else if (name.equals("RadioGroup")) {
                Log.v("Parse", "RadioGroup");
                relativeLayout.addView(readRadioGroup(parser));
            } else if (name.equals("RadioButton")) {
                Log.v("Parse", "->RadioButton");
            } else if (name.equals("TextView")) {
                relativeLayout.addView(readTextView(parser));
            } else if (name.equals("Button")) {
                relativeLayout.addView(readButton(parser));
            } else if (name.equals("LinearLayout")) {
                relativeLayout.addView(readLinearLayout(parser));
            } else {
                skip(parser);
            }
        }
        return relativeLayout;
    }

    private View readRadioGroup(XmlPullParser parser) throws IOException, XmlPullParserException {
        int id = Integer.parseInt(parser.getAttributeValue(ns, "android:id"));
//        float weight = Float.parseFloat(parser.getAttributeValue(ns, "android:layout_weight"));
        RadioGroup radioGroup = new RadioGroup(context);
        radioGroup.setOrientation(RadioGroup.VERTICAL);
        parser.require(XmlPullParser.START_TAG, ns, "RadioButton");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("RadioButton")) {
                Log.v("Parse", "RadioButton");
                radioGroup.addView(readRadioButton(parser));
            } else {
                skip(parser);
            }
        }

        return radioGroup;
    }

    private View readRadioButton(XmlPullParser parser) {
        int id = Integer.parseInt(parser.getAttributeValue(ns, "android:id"));
        RadioButton radioButton = new RadioButton(context);
        radioButton.setText(parser.getAttributeValue(ns, "android:text"));
        radioButton.setId(id);
        return radioButton;
    }

    private View readLinearLayout(XmlPullParser parser) throws XmlPullParserException, IOException {
        Log.v("Parse", "LinearLayout");
        int id = Integer.parseInt(parser.getAttributeValue(ns, "android:id"));
        float weight = Float.parseFloat(parser.getAttributeValue(ns, "android:layout_weight"));
        Log.v("Parse", "Weight : " + weight);
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setId(id);
        String height = parser.getAttributeValue(ns, "android:layout_height");
        String width = parser.getAttributeValue(ns, "android:layout_width");
        String orientation = parser.getAttributeValue(ns, "android:orientation");
        String centerInParent = parser.getAttributeValue(ns, "android:layout_centerInParent");
        linearLayout.setLayoutParams(getLayoutParams(height, width, weight, scale, centerInParent));
        if (orientation.equals("vertical")) {
            linearLayout.setOrientation(LinearLayout.VERTICAL);
        } else {
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        }
        parser.require(XmlPullParser.START_TAG, ns, "LinearLayout");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("ImageView")) {
                linearLayout.addView(readImageView(parser));
            } else if (name.equals("TextView")) {
                linearLayout.addView(readTextView(parser));
            } else if (name.equals("Button")) {
                linearLayout.addView(readButton(parser));
            } else if (name.equals("LinearLayout")) {
                linearLayout.addView(readLinearLayout(parser));
            } else {
                skip(parser);
            }
        }
        return linearLayout;
    }

    private Button readButton(XmlPullParser parser) throws XmlPullParserException, IOException {
        Log.v("Parse", "readButton");
        String activityString="null";
        String answer = null;
        final Button button = new Button(context);
        int id = Integer.parseInt(parser.getAttributeValue(ns, "android:id"));
        float weight = Float.parseFloat(parser.getAttributeValue(ns, "android:layout_weight"));
        if (parser.getAttributeValue(ns, "homeSchool:audioLink")!= null) {
            final String audioURL = parser.getAttributeValue(ns, "homeSchool:audioLink");
        }
        if (parser.getAttributeValue(ns, "homeSchool:activity")!=null) {
            activityString = parser.getAttributeValue(ns, "homeSchool:activity");
        }
        if(parser.getAttributeValue(ns,"homeSchool:answer")!=null){
            answer = parser.getAttributeValue(ns,"homeSchool:answer");

        }
        button.setId(id);
        final String finalActivity = activityString;
        final String finalAnswer = answer;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!finalActivity.equals("null")){
                    if(finalActivity.equals("Speech")){
                        Intent intent = new Intent(context, Speech.class);
                        intent.putExtra("Answer", finalAnswer);
                        activity.startActivityForResult(intent, Constants.SPEECH);
//                        Speech speech = new Speech(activity);
//                        speech.setWord(answer)
                    }
                }
            }
        });
        String height = parser.getAttributeValue(ns, "android:layout_height");
        String width = parser.getAttributeValue(ns, "android:layout_width");
        button.setLayoutParams(getLayoutParams(height, width, weight, scale));
        button.setText(parser.getAttributeValue(ns, "android:text"));
        parser.require(XmlPullParser.START_TAG, ns, "Button");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            skip(parser);
        }
        return button;
    }

    private ImageView readImageView(
            XmlPullParser parser) throws XmlPullParserException, IOException {
        Log.v("Parse", "readImageView");
        parser.require(XmlPullParser.START_TAG, ns, "ImageView");
        int id = Integer.parseInt(parser.getAttributeValue(ns, "android:id"));
        float weight = Float.parseFloat(parser.getAttributeValue(ns, "android:layout_weight"));
        final ImageView imageView = new ImageView(context);
        imageView.setId(id);
        Log.v("Test", "IMG");
        String height = parser.getAttributeValue(ns, "android:layout_height");
        String width = parser.getAttributeValue(ns, "android:layout_width");
        String src = parser.getAttributeValue(ns, "homeSchool:src");
        Glide.with(context).load(src).listener(
                new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model,
                                               Target<GlideDrawable> target,
                                               boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model,
                                                   Target<GlideDrawable> target,
                                                   boolean isFromMemoryCache,
                                                   boolean isFirstResource) {
                        return false;
                    }
                }).into(imageView);
        imageView.setLayoutParams(getLayoutParams(height, width, weight, scale));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadImage.onClick(imageView);
            }
        });
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            skip(parser);
        }
        return imageView;
    }

    private TextView readTextView(XmlPullParser parser) throws XmlPullParserException, IOException {
        Log.v("Parse", "readTextView");
        parser.require(XmlPullParser.START_TAG, ns, "TextView");
        int id = Integer.parseInt(parser.getAttributeValue(ns, "android:id"));
        float weight = Float.parseFloat(parser.getAttributeValue(ns, "android:layout_weight"));
        TextView textView = new TextView(context);
        textView.setId(id);
        textView.setText(parser.getAttributeValue(ns, "android:text"));
        if (parser.getAttributeValue(ns, "android:textSize") != null) {
            textView.setTextSize(
                    Float.parseFloat(parser.getAttributeValue(ns, "android:textSize")));
        }
        String color = parser.getAttributeValue(ns, "android:textColor");
        if(parser.getAttributeValue(ns, "android:textAppearance")!=null){
            textView.setTextAppearance(context,
                    getTextAppearance(parser.getAttributeValue(ns, "android:textAppearance")));
        }
        textView.setTextColor(Integer.parseInt(color));
        String height = parser.getAttributeValue(ns, "android:layout_height");
        String width = parser.getAttributeValue(ns, "android:layout_width");
        textView.setLayoutParams(getLayoutParams(height, width, weight, scale));
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            skip(parser);
        }
        return textView;
    }

    int getTextAppearance(String textAppearance) {
        return android.R.style.TextAppearance_Material_Display4;
    }

    private int selectColor(String color) {
        int c = Color.parseColor("#ffcc00");
        return c;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

    float getScale(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    int intToPixels(int dps, final float scale) {
        int pixels = (int) (dps * scale + 0.5f);
        return pixels;
    }

    RelativeLayout.LayoutParams getLayoutParams(String height, String width, float weight,
                                                final float scale, String centerInParent) {
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        return lp;
//        if (height.equals("match_parent")) {
//            if (width.equals("match_parent")) {
//                return new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
//                        TableLayout.LayoutParams.MATCH_PARENT, weight);
//            } else if (width.equals("wrap_content")) {
//                return new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
//                        TableLayout.LayoutParams.MATCH_PARENT, weight);
//            } else {
//
//                return new TableLayout.LayoutParams(intToPixels(Integer.parseInt(width), scale),
//                        TableLayout.LayoutParams.MATCH_PARENT, weight);
//            }
//        } else if (height.equals("wrap_content")) {
//            if (width.equals("match_parent")) {
//                return new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
//                        TableLayout.LayoutParams.WRAP_CONTENT, weight);
//            } else if (width.equals("wrap_content")) {
//                return new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
//                        TableLayout.LayoutParams.WRAP_CONTENT, weight);
//            } else {
//                return new TableLayout.LayoutParams(intToPixels(Integer.parseInt(width), scale),
//                        LinearLayout.LayoutParams.WRAP_CONTENT, weight);
//            }
//        } else {
//            return new TableLayout.LayoutParams(intToPixels(Integer.parseInt(width), scale),
//                    intToPixels(Integer.parseInt(height), scale), weight);
//        }
    }

    TableLayout.LayoutParams getLayoutParams(String height, String width, float weight,
                                             final float scale) {
        if (height.equals("match_parent")) {
            if (width.equals("match_parent")) {
                return new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.MATCH_PARENT, weight);
            } else if (width.equals("wrap_content")) {
                return new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
                        TableLayout.LayoutParams.MATCH_PARENT, weight);
            } else {

                return new TableLayout.LayoutParams(intToPixels(Integer.parseInt(width), scale),
                        TableLayout.LayoutParams.MATCH_PARENT, weight);
            }
        } else if (height.equals("wrap_content")) {
            if (width.equals("match_parent")) {
                return new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT, weight);
            } else if (width.equals("wrap_content")) {
                return new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
                        TableLayout.LayoutParams.WRAP_CONTENT, weight);
            } else {
                return new TableLayout.LayoutParams(intToPixels(Integer.parseInt(width), scale),
                        LinearLayout.LayoutParams.WRAP_CONTENT, weight);
            }
        } else {
            return new TableLayout.LayoutParams(intToPixels(Integer.parseInt(width), scale),
                    intToPixels(Integer.parseInt(height), scale), weight);
        }
    }
}

