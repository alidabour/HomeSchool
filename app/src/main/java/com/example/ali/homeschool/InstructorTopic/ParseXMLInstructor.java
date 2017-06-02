package com.example.ali.homeschool.InstructorTopic;

import android.content.Context;
import android.media.AudioManager;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.ali.homeschool.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Dabour on 11/20/2016.
 */

public class ParseXMLInstructor {
    private static final String ns = null;
    private static Context context;
    private float scale;
    ImageClicked loadImage;

    public View parse(InputStream in, Context context,
                      ImageClicked loadImage) throws XmlPullParserException, IOException {
        this.loadImage = loadImage;
        try {
            this.context = context;
            scale = getScale(context);
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            Log.v("Test","parser getName :"+parser.getName());
            if(parser.getName().equals("RelativeLayout")){
                return readRelativeLayout(parser);
            }
            return readLayout(parser);
        } finally {
            in.close();
        }
    }
    private View readRelativeLayout(XmlPullParser parser) throws XmlPullParserException, IOException {
//        int id = Integer.parseInt(parser.getAttributeValue(ns, "android:id"));
//        float weight = Float.parseFloat(parser.getAttributeValue(ns, "android:layout_weight"));
//        Log.v("Parse", "Weight : " + weight);
        RelativeLayout relativeLayout = new RelativeLayout(context);
//        linearLayout.setId(id);
        String height = parser.getAttributeValue(ns, "android:layout_height");
        String width = parser.getAttributeValue(ns, "android:layout_width");
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.FILL_PARENT,
                RelativeLayout.LayoutParams.FILL_PARENT);

//        relativeLayout.setLayoutParams(getLayoutParams(height, width, scale));

//        parser.require(XmlPullParser.START_TAG, ns, "LinearLayout");
//        while (parser.next() != XmlPullParser.END_TAG) {
//            if (parser.getEventType() != XmlPullParser.START_TAG) {
//                continue;
//            }
//            String name = parser.getName();
//            if (name.equals("ImageView")) {
//                linearLayout.addView(readImageView(parser));
//            } else if (name.equals("TextView")) {
//                linearLayout.addView(readTextView(parser));
//            } else if (name.equals("Button")) {
//                linearLayout.addView(readButton(parser));
//            } else if (name.equals("LinearLayout")) {
//                linearLayout.addView(readLayout(parser));
//            } else {
//                skip(parser);
//            }
//        }
        return relativeLayout;
    }
    private View readLayout(XmlPullParser parser) throws XmlPullParserException, IOException {
        int id = Integer.parseInt(parser.getAttributeValue(ns, "android:id"));
        float weight = Float.parseFloat(parser.getAttributeValue(ns, "android:layout_weight"));
        Log.v("Parse", "Weight : " + weight);
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setId(id);
        String height = parser.getAttributeValue(ns, "android:layout_height");
        String width = parser.getAttributeValue(ns, "android:layout_width");
        String orientation = parser.getAttributeValue(ns, "android:orientation");
        linearLayout.setLayoutParams(getLayoutParams(height, width, weight, scale));
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
                linearLayout.addView(readLayout(parser));
            } else {
                skip(parser);
            }
        }
        return linearLayout;
    }

    private Button readButton(XmlPullParser parser) throws XmlPullParserException, IOException {
        final Button button = new Button(context);
        int id = Integer.parseInt(parser.getAttributeValue(ns, "android:id"));
        float weight = Float.parseFloat(parser.getAttributeValue(ns, "android:layout_weight"));
        final String audioURL = parser.getAttributeValue(ns, "homeSchool:audioLink");
        button.setId(id);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        parser.require(XmlPullParser.START_TAG, ns, "TextView");
        int id = Integer.parseInt(parser.getAttributeValue(ns, "android:id"));
        float weight = Float.parseFloat(parser.getAttributeValue(ns, "android:layout_weight"));
        TextView textView = new TextView(context);
        textView.setId(id);
        textView.setText(parser.getAttributeValue(ns, "android:text"));
        textView.setTextSize(Float.parseFloat(parser.getAttributeValue(ns,"android:textSize")));
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

