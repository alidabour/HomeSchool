package com.example.ali.homeschool.InstructorTopic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.util.Log;
import android.util.Xml;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.ali.homeschool.Answer;
import com.example.ali.homeschool.Constants;
import com.example.ali.homeschool.exercises.speech.Speech;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * Created by Dabour on 11/20/2016.
 */

public class ParseXMLInstructor {
    private static final String ns = null;
    private float scale;
    XMLClick xmlClick;
    XMLEditClick xmlEditClick;
    Activity activity;
    String layout;

    public ParseXMLInstructor(Activity activity) {
        this.activity = activity;
    }

    public View parse(String layout) throws XmlPullParserException, IOException {
        InputStream in = null;
        this.layout = layout;
        try {
            in = new ByteArrayInputStream(layout.getBytes(Charset.forName("UTF-8")));
            scale = getScale(activity);
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            Log.v("Parse", "parser getName :" + parser.getName());
            if (parser.getName().equals("RelativeLayout")) {
                Log.v("Parse", "parser getName Relative:" + parser.getName());
                return readRelativeLayout(parser);
            } else if (parser.getName().equals("LinearLayout")) {
                Log.v("Parse", "parser getName Linear:" + parser.getName());
                return readLinearLayout(parser);
            } else if (parser.getName().equals("TextView")) {
                return readTextView(parser);
            } else if (parser.getName().equals("ImageView")) {
                Log.v("Test", "parser getName :" + parser.getName());
                return readImageView(parser);
            } else if (parser.getName().equals("RadioGroup")) {
                return readRadioGroup(parser);
            } else if (parser.getName().equals("Button")) {
                return readButton(parser);
            } else {
                return null;
            }
        } finally {
            in.close();
        }
    }

    public void setXmlEditClick(XMLEditClick xmlEditClick) {
        this.xmlEditClick = xmlEditClick;
    }

    public void setXmlClick(XMLClick xmlClick) {
        this.xmlClick = xmlClick;
    }

    private View readRelativeLayout(
            XmlPullParser parser) throws XmlPullParserException, IOException {
        RelativeLayout relativeLayout = new RelativeLayout(activity);
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        relativeLayout.setLayoutParams(rlp);
        relativeLayout.setPadding(8, 8, 8, 8);
        parser.require(XmlPullParser.START_TAG, ns, "RelativeLayout");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("ImageView")) {
                relativeLayout.addView(readImageView(parser));
            } else if (name.equals("RadioGroup")) {
                relativeLayout.addView(readRadioGroup(parser));
            } else if (name.equals("RadioButton")) {

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
        Log.v("Parse", "RadioGroup :" + parser.toString());
        //Log.v("Parse", "ns  :" + ns);

        int id = Integer.parseInt(parser.getAttributeValue(ns, "android:id"));
        final int answerId = Integer.parseInt(parser.getAttributeValue(ns, "homeSchool:answer"));
//        float weight = Float.parseFloat(parser.getAttributeValue(ns, "android:layout_weight"));
        RadioGroup radioGroup = new RadioGroup(activity);
        radioGroup.setOrientation(RadioGroup.VERTICAL);
        radioGroup.setId(id);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (answerId == checkedId) {
                    xmlClick.onMultQuestionClicked(true);
                } else {
                    xmlClick.onMultQuestionClicked(false);
                }
            }
        });
        parser.require(XmlPullParser.START_TAG, ns, "RadioGroup");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            Log.v("Parser.next", parser.next() + "");
            String name = parser.getName();
            Log.v("Parse", "RadioButton :" + name);
            if (name.equals("RadioButton")) {
                Log.v("Parse", "RadioButton :" + parser.toString());
                radioGroup.addView(readRadioButton(parser));
            } else {
                Log.v("Parse", "Error RadioButton :" + parser.toString());
                skip(parser);
            }
        }

        return radioGroup;
    }

    private RadioButton readRadioButton(XmlPullParser parser) {
        int id = Integer.parseInt(parser.getAttributeValue(ns, "android:id"));
        RadioButton radioButton = new RadioButton(activity);
        radioButton.setText(parser.getAttributeValue(ns, "android:text"));
        radioButton.setId(id);
        return radioButton;
    }

    private View readLinearLayout(XmlPullParser parser) throws XmlPullParserException, IOException {
        Log.v("Parse", "LinearLayout");
        int id = Integer.parseInt(parser.getAttributeValue(ns, "android:id"));
        float weight = Float.parseFloat(parser.getAttributeValue(ns, "android:layout_weight"));
        Log.v("Parse", "Weight : " + weight);
        LinearLayout linearLayout = new LinearLayout(activity);
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
            } else if (name.equals("RadioGroup")) {
                linearLayout.addView(readRadioGroup(parser));
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
        String activityString = "null";
        String answer = null;
        String audioURL = null;
        String lan = null;
        String text = null;
        final Button button = new Button(activity);
        final int id = Integer.parseInt(parser.getAttributeValue(ns, "android:id"));
        float weight = Float.parseFloat(parser.getAttributeValue(ns, "android:layout_weight"));
        if (parser.getAttributeValue(ns, "homeSchool:audioLink") != null) {
            audioURL = parser.getAttributeValue(ns, "homeSchool:audioLink");
        }
        if (parser.getAttributeValue(ns, "homeSchool:activity") != null) {
            activityString = parser.getAttributeValue(ns, "homeSchool:activity");
        }
        if (parser.getAttributeValue(ns, "homeSchool:answer") != null) {
            answer = parser.getAttributeValue(ns, "homeSchool:answer");

        }
        if (parser.getAttributeValue(ns, "homeSchool:lan") != null) {
            lan = parser.getAttributeValue(ns, "homeSchool:lan");

        }
        if (parser.getAttributeValue(ns, "android:text") != null) {
            text = parser.getAttributeValue(ns, "android:text");
            button.setText(text);
        }
        button.setId(id);
        button.setBackgroundColor(Color.parseColor("#08aac7"));
        button.setTextColor(Color.parseColor("#FFFFFF"));
        final String finalActivity = activityString;
        final String finalAnswer = answer;
        final String finalAnswer1 = answer;
        final String finalAudioURL = audioURL;
        final Answer answer1 = new Answer(answer, lan);
        Log.v("Parser", "Answer " + answer1.getAnswer());
        if(xmlClick != null){
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!finalActivity.equals("null")) {
                        if (finalActivity.equals("Speech")) {
                            Intent intent = new Intent(activity, Speech.class);
                            intent.putExtra("Answer", answer1);
                            activity.startActivityForResult(intent, Constants.SPEECH);
//                        Speech speech = new Speech(activity);
//                        speech.setWord(answer)
                        } else if (finalActivity.equals("ColorActivity")) {
                            xmlClick.openActivity("ColorActivity", answer1);
                        } else if (finalActivity.equals("TextDetection")) {
                            xmlClick.openActivity("TextDetection", answer1);
                        }
                    }
                    if (finalAudioURL != null && !finalAudioURL.equals(Constants.PUT_SOUND_LINK_HERE)) {
                        xmlClick.playSound(finalAudioURL);
                    }
                }
            });
        }
        final String finalText = text;
        if(xmlEditClick != null){
            button.setOnTouchListener(new View.OnTouchListener() {
                private GestureDetector gestureDetector = new GestureDetector(activity,
                        new GestureDetector.SimpleOnGestureListener() {
                            @Override
                            public boolean onDoubleTap(MotionEvent e) {
                                Log.d("TEST", "onDoubleTap");
                                if (finalAudioURL != null && !finalAudioURL
                                        .equals(Constants.PUT_SOUND_LINK_HERE)) {
                                    if (xmlEditClick != null) {
                                        xmlEditClick.onEditSound(id, finalAudioURL, finalText, layout);
                                    }
                                } else if (finalActivity.equals("ColorActivity")) {
                                    xmlEditClick.onEditColorQuestion(id, finalText, layout);
                                }

//                            Toast.makeText(activity, "ON Double Tap", Toast.LENGTH_SHORT).show();
                                return super.onDoubleTap(e);
                            }
                            // implement here other callback methods like onFling, onScroll as necessary
                        });

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Log.d("TEST",
                            "Raw event: " + event.getAction() + ", (" + event.getRawX() + ", " + event
                                    .getRawY() + ")");
                    gestureDetector.onTouchEvent(event);
                    return true;
                }
            });
        }
        String height = parser.getAttributeValue(ns, "android:layout_height");
        String width = parser.getAttributeValue(ns, "android:layout_width");
        button.setLayoutParams(getLayoutParams(height, width, weight, scale));
//        button.setText(parser.getAttributeValue(ns, "android:text"));
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
            final XmlPullParser parser) throws XmlPullParserException, IOException {
        Log.v("Parse", "readImageView");
        Log.v("Test", "readImageView----Ordering");

        parser.require(XmlPullParser.START_TAG, ns, "ImageView");
        final int id = Integer.parseInt(parser.getAttributeValue(ns, "android:id"));
        float weight = Float.parseFloat(parser.getAttributeValue(ns, "android:layout_weight"));
        final ImageView imageView = new ImageView(activity);
        imageView.setId(id);
        Log.v("Test", "IMG");
        String height = parser.getAttributeValue(ns, "android:layout_height");
        String width = parser.getAttributeValue(ns, "android:layout_width");
        final String src = parser.getAttributeValue(ns, "homeSchool:src");
        Glide.with(activity).load(src).listener(
                new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model,
                                               Target<GlideDrawable> target,
                                               boolean isFirstResource) {
                        Log.v("Test", "Parser OnException : +" + e.getMessage());
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model,
                                                   Target<GlideDrawable> target,
                                                   boolean isFromMemoryCache,
                                                   boolean isFirstResource) {
                        Log.v("Test", "Parser Model : +" + model);
                        return false;
                    }
                }).into(imageView);
        imageView.setLayoutParams(getLayoutParams(height, width, weight, scale));
        imageView.setOnTouchListener(new View.OnTouchListener() {
            private GestureDetector gestureDetector = new GestureDetector(activity,
                    new GestureDetector.SimpleOnGestureListener() {
                        @Override
                        public boolean onDoubleTap(MotionEvent e) {
                            Log.d("TEST", "onDoubleTap");
                            if (xmlEditClick != null) {
                                xmlEditClick.onEditImageView(id, src, layout);
                            }
                            Toast.makeText(activity, "ON Double Tap", Toast.LENGTH_SHORT).show();
                            return super.onDoubleTap(e);
                        }
                        // implement here other callback methods like onFling, onScroll as necessary
                    });

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("TEST",
                        "Raw event: " + event.getAction() + ", (" + event.getRawX() + ", " + event
                                .getRawY() + ")");
                gestureDetector.onTouchEvent(event);
                return true;
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
        final int id = Integer.parseInt(parser.getAttributeValue(ns, "android:id"));
        float weight = Float.parseFloat(parser.getAttributeValue(ns, "android:layout_weight"));
        TextView textView = new TextView(activity);
        textView.setId(id);
        final String text = parser.getAttributeValue(ns, "android:text");
        textView.setText(text);
        if (parser.getAttributeValue(ns, "android:textSize") != null) {
            textView.setTextSize(
                    Float.parseFloat(parser.getAttributeValue(ns, "android:textSize")));
        }
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        String color = parser.getAttributeValue(ns, "android:textColor");
        if (parser.getAttributeValue(ns, "android:textAppearance") != null) {
            textView.setTextAppearance(activity,
                    Integer.parseInt(parser.getAttributeValue(ns, "android:textAppearance")));

        }
        textView.setTextColor(Integer.parseInt(color));
        String height = parser.getAttributeValue(ns, "android:layout_height");
        String width = parser.getAttributeValue(ns, "android:layout_width");
        textView.setLayoutParams(getLayoutParams(height, width, weight, scale));
        if(xmlEditClick != null){
            textView.setOnTouchListener(new View.OnTouchListener() {
                private GestureDetector gestureDetector = new GestureDetector(activity,
                        new GestureDetector.SimpleOnGestureListener() {
                            @Override
                            public boolean onDoubleTap(MotionEvent e) {
                                xmlEditClick.onEditTextView(id,text,layout);
                                return super.onDoubleTap(e);
                            }
                            // implement here other callback methods like onFling, onScroll as necessary
                        });

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    gestureDetector.onTouchEvent(event);
                    return true;
                }
            });
        }
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

    float getScale(Activity activity) {
        return activity.getResources().getDisplayMetrics().density;
    }

    int intToPixels(int dps, final float scale) {
        int pixels = (int) (dps * scale + 0.5f);
        return pixels;
    }

    RelativeLayout.LayoutParams getLayoutParams(String height, String width, float weight,
                                                final float scale, String centerInParent) {
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
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