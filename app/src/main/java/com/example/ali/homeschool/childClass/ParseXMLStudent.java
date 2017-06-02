package com.example.ali.homeschool.childClass;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.ali.homeschool.Constants;
import com.example.ali.homeschool.exercises.simple.SimpleExercises;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Dabour on 11/20/2016.
 */

public class ParseXMLStudent {
    private ButtonClicked buttonClicked;
    private static final String ns = null;
    private static Context context;
    private float scale;
    ImageClickedStudent loadImage;

    public View parse(InputStream in, Context context,
                      ImageClickedStudent loadImage,ButtonClicked buttonClicked) throws XmlPullParserException, IOException {
        Log.v("Parser", "InputStream : ");
        this.loadImage = loadImage;
        try {
            this.buttonClicked = buttonClicked;
            this.context = context;
            scale = getScale(context);
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();

            return readLayout(parser);
        } finally {
            in.close();
        }
    }

    private View readLayout(XmlPullParser parser) throws XmlPullParserException, IOException {
        Log.v("Parser", "XML ---> " + parser.getText());
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
            Log.v("Parse", "Child :" + name);
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
        final int id = Integer.parseInt(parser.getAttributeValue(ns, "android:id"));
        float weight = Float.parseFloat(parser.getAttributeValue(ns, "android:layout_weight"));
        final String audioURL = parser.getAttributeValue(ns, "homeSchool:audioLink");
        final String activity = parser.getAttributeValue(ns,"homeSchool:activity");
        button.setId(id);
        button.setOnClickListener(new View.OnClickListener() {

            MediaPlayer mp;

            //            final MediaPlayer sp = MediaPlayer.create(context, R.raw.catname);
            @Override
            public void onClick(View view) {
                if(activity!=null){
                    Log.v("Parse","Activity :" + activity);
                    if(activity.equals("SimpleExercises")){
                        buttonClicked.onButtonClicked("SimpleExercises");
                        Intent intent = new Intent(context, SimpleExercises.class);
                        if (context instanceof Activity) {
                            ((Activity) context).startActivityForResult(intent, Constants.SIMPLE);
                        } else {
                            Log.e("Parse","mContext should be an instanceof Activity.");
                        }
                    }
                }
//                try {
//                    final MediaPlayer mediaPlayer =new MediaPlayer();
////                    final MediaPlayer sp = MediaPlayer.create(context, R.raw.catname);
//                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//                    mediaPlayer.setDataSource(audioURL);
//                    mediaPlayer.prepare();
//                    mediaPlayer.start();
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }


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
        String height = parser.getAttributeValue(ns, "android:layout_height");
        String width = parser.getAttributeValue(ns, "android:layout_width");
        textView.setLayoutParams(getLayoutParams(height, width, weight, scale));
        textView.setTextSize(Float.parseFloat(parser.getAttributeValue(ns,"android:textSize")));

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

    private float getScale(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    private int intToPixels(int dps, final float scale) {
        int pixels = (int) (dps * scale + 0.5f);
        return pixels;
    }

    private TableLayout.LayoutParams getLayoutParams(String height, String width, float weight,
                                             final float scale) {
        //Log.v("Parse","h :"+height+" WI : "+width+" WE :"+weight+"SC :"+scale);
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


//    private static final String ns = null;
//    private static Context context;
//    private  float scale;
//    public View parse(InputStream in,Context context) throws XmlPullParserException, IOException {
//        try {
//            this.context=context;
//            scale = getScale(context);
//            XmlPullParser parser = Xml.newPullParser();
//            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
//            parser.setInput(in, null);
//            parser.nextTag();
//            return readLayout(parser);
//        } finally {
//            in.close();
//        }
//    }
//    private View readLayout(XmlPullParser parser) throws XmlPullParserException, IOException {
//            int id= Integer.parseInt(parser.getAttributeValue(ns,"android:id"));
//            float weight = Float.parseFloat(parser.getAttributeValue(ns,"android:layout_weight"));
//            Log.v("Parse","Weight : " +weight);
//            LinearLayout linearLayout=new LinearLayout(context);
//            linearLayout.setId(id);
//            String height = parser.getAttributeValue(ns,"android:layout_height");
//            String width = parser.getAttributeValue(ns,"android:layout_width");
//            String orientation=parser.getAttributeValue(ns,"android:orientation");
//            linearLayout.setLayoutParams(getLayoutParams(height,width,weight,scale));
//            if(orientation.equals("vertical")){ linearLayout.setOrientation(LinearLayout.VERTICAL);}
//            else { linearLayout.setOrientation(LinearLayout.HORIZONTAL);}
//            parser.require(XmlPullParser.START_TAG, ns, "LinearLayout");
//            while (parser.next() != XmlPullParser.END_TAG) {
//                if (parser.getEventType() != XmlPullParser.START_TAG) {
//                    continue;
//                }
//                String name = parser.getName();
//                Log.v("Parse","Child :"+name);
//                if (name.equals("ImageView")) {
//                    linearLayout.addView(readImageView(parser));
//                }
//                else if(name.equals("TextView")) {
//                    linearLayout.addView(readTextView(parser));
//                }else if(name.equals("Button")){
//                    linearLayout.addView(readButton(parser));
//                }else if (name.equals("LinearLayout")){
//                    linearLayout.addView(readLayout(parser));
//                } else {
//                    skip(parser);
//                }
//            }
//            return linearLayout;
//        }
//    private Button readButton (XmlPullParser parser)throws XmlPullParserException,IOException{
//        final Button button=new Button(context);
//        int id= Integer.parseInt(parser.getAttributeValue(ns,"android:id"));
//        float weight = Float.parseFloat(parser.getAttributeValue(ns,"android:layout_weight"));
//        button.setId(id);
//        button.setOnClickListener(new View.OnClickListener() {
//             MediaPlayer mp ;
//            final MediaPlayer sp = MediaPlayer.create(context, R.raw.catname);
//            @Override
//            public void onClick(View view) {
//                Log.v("Parse","Button id:"+button.getId());
//                int id =button.getId();
//                switch (id){
//                    case 1:
//                        mp = MediaPlayer.create(context,R.raw.catname);
//                        mp.start();
//                        break;
//                    case 12:
//                        mp = MediaPlayer.create(context,R.raw.catssound);
//                        mp.start();
//                        break;
//                    case 2:
//                        mp = MediaPlayer.create(context,R.raw.dogname);
//                        mp.start();
//                        break;
//                    case 21:
//                        mp = MediaPlayer.create(context,R.raw.dogsound);
//                        mp.start();
//                        break;
//
//                }
//
//            }
//        });
//        String height=parser.getAttributeValue(ns,"android:layout_height");
//        String width = parser.getAttributeValue(ns,"android:layout_width");
//        button.setLayoutParams(getLayoutParams(height,width,weight,scale));
//        button.setText(parser.getAttributeValue(ns,"android:text"));
//        parser.require(XmlPullParser.START_TAG, ns,"Button");
//        while (parser.next() != XmlPullParser.END_TAG){
//            if(parser.getEventType() != XmlPullParser.START_TAG){
//                continue;
//            }
//            skip(parser);
//        }
//        return button;
//    }
//    private ImageView readImageView(XmlPullParser parser)throws XmlPullParserException,IOException{
//        parser.require(XmlPullParser.START_TAG, ns,"ImageView");
//        int id= Integer.parseInt(parser.getAttributeValue(ns,"android:id"));
//        float weight = Float.parseFloat(parser.getAttributeValue(ns,"android:layout_weight"));
//        ImageView imageView = new ImageView(context);
//        imageView.setId(id);
//        //int imageDrawableId = context.getResources().getDrawable(R.drawable.ic_launcher);
//        //imageView.setImageDrawable(parser.getAttributeValue(ns,"android:src"));
//        if (id==1){
//            imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.catimage));
//
//        }else {
//            imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.dogimage));
//        }
//        String height=parser.getAttributeValue(ns,"android:layout_height");
//        String width = parser.getAttributeValue(ns,"android:layout_width");
//        imageView.setLayoutParams(getLayoutParams(height,width,weight,scale));
//        while (parser.next() != XmlPullParser.END_TAG){
//            if(parser.getEventType() != XmlPullParser.START_TAG){
//                continue;
//            }
//            skip(parser);
//        }
//        return imageView;
//    }
//    private TextView readTextView(XmlPullParser parser)throws XmlPullParserException,IOException{
//        parser.require(XmlPullParser.START_TAG, ns,"TextView");
//        int id= Integer.parseInt(parser.getAttributeValue(ns,"android:id"));
//        float weight = Float.parseFloat(parser.getAttributeValue(ns,"android:layout_weight"));
//        TextView textView = new TextView(context);
//        textView.setId(id);
//        textView.setText(parser.getAttributeValue(ns,"android:text"));
//        String height=parser.getAttributeValue(ns,"android:layout_height");
//        String width = parser.getAttributeValue(ns,"android:layout_width");
//        textView.setLayoutParams(getLayoutParams(height,width,weight,scale));
//
//        while (parser.next() != XmlPullParser.END_TAG){
//            if(parser.getEventType() != XmlPullParser.START_TAG){
//                continue;
//            }
//            skip(parser);
//        }
//
//        return textView;
//    }
//
//    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
//        if (parser.getEventType() != XmlPullParser.START_TAG) {
//            throw new IllegalStateException();
//        }
//        int depth = 1;
//        while (depth != 0) {
//            switch (parser.next()) {
//                case XmlPullParser.END_TAG:
//                    depth--;
//                    break;
//                case XmlPullParser.START_TAG:
//                    depth++;
//                    break;
//            }
//        }
//    }
//    float getScale(Context context){
//        return context.getResources().getDisplayMetrics().density;
//    }
//    int intToPixels (int dps,final float scale){
//        int pixels = (int) (dps * scale + 0.5f);
//        return pixels;
//    }
//    TableLayout.LayoutParams getLayoutParams(String height, String width,float weight,final float scale){
//        //Log.v("Parse","h :"+height+" WI : "+width+" WE :"+weight+"SC :"+scale);
//        if (height.equals("match_parent")){
//            if (width.equals("match_parent")){
//                return new TableLayout.LayoutParams( TableLayout.LayoutParams.MATCH_PARENT,  TableLayout.LayoutParams.MATCH_PARENT,weight);
//            }else if(width.equals("wrap_content")){
//               return new  TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,  TableLayout.LayoutParams.MATCH_PARENT,weight);
//            }else {
//
//                return new  TableLayout.LayoutParams(intToPixels(Integer.parseInt(width),scale), TableLayout.LayoutParams.MATCH_PARENT ,weight);
//            }
//        }
//        else if (height.equals("wrap_content")){
//            if (width.equals("match_parent")){
//               return  new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT,weight);
//            }else if(width.equals("wrap_content")){
//                return new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT,weight);
//            }else {
//                return new TableLayout.LayoutParams(intToPixels(Integer.parseInt(width),scale),LinearLayout.LayoutParams.WRAP_CONTENT,weight);
//            }
//        }
//        else {
//            return new TableLayout.LayoutParams(intToPixels(Integer.parseInt(width),scale),intToPixels(Integer.parseInt(height),scale),weight);
//        }
//
//            //return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
//    }
//    public static class ViewX{
//        String id;
//        String layout_height;
//        String layout_width;
//
//        public String getType() {
//            return type;
//        }
//
//        public void setType(String type) {
//            this.type = type;
//        }
//
//        String type;
//        private ViewX(String id){
//            this.id=id;
//        }
//
//        public String getId() {
//            return id;
//        }
//
//        public void setId(String id) {
//            this.id = id;
//        }
//
//        public String getLayout_width() {
//            return layout_width;
//        }
//
//        public void setLayout_width(String layout_width) {
//            this.layout_width = layout_width;
//        }
//
//        public String getLayout_height() {
//            return layout_height;
//        }
//
//        public void setLayout_height(String layout_height) {
//            this.layout_height = layout_height;
//        }
//    }
//    public static class ImageViewX extends ViewX{
//        String src;
//        private ImageViewX(String id){
//            super(id);
//        }
//
//        public String getSrc() {
//            return src;
//        }
//
//        public void setSrc(String src) {
//            this.src = src;
//        }
//    }
//    public static class TextViewX extends ViewX{
//        String text;
//        private TextViewX(String id){
//            super(id);
//        }
//
//        public String getText() {
//            return text;
//        }
//
//        public void setText(String text) {
//            this.text = text;
//        }
//    }
//    public static class ButtonX extends TextViewX{
//        String onClick;
//        private ButtonX(String id) {
//            super(id);
//        }
//
//        public String getOnClick() {
//            return onClick;
//        }
//
//        public void setOnClick(String onClick) {
//            this.onClick = onClick;
//        }
//    }
//    public static class ViewGroupX extends ViewX{
//
//        private ViewGroupX(String id) {
//            super(id);
//        }
//    }
//    public static class LinearLayoutX extends ViewGroupX{
//        ArrayList<ViewX> children = new ArrayList<>();
//
//        String orientation;
//        private LinearLayoutX(String id) {
//            super(id);
//        }
//        private void addView(ViewX viewX){
//            children.add(viewX);
//        }
//        public ArrayList<ViewX> getChildren() {
//            return children;
//        }
//
//        public String getOrientation() {
//            return orientation;
//        }
//
//        public void setOrientation(String orientation) {
//            this.orientation = orientation;
//        }
//    }


