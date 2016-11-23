package com.example.ali.homeschool;

import android.content.Context;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.    util.ArrayList;
import java.util.List;

/**
 * Created by Dabour on 11/20/2016.
 */

public class ParseXML {
    private static final String ns = null;
    private static Context context;
    public View parse(InputStream in,Context context) throws XmlPullParserException, IOException {
        try {
            this.context=context;
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
            List views = new ArrayList();
            String id=null;
            id=parser.getAttributeValue(ns,"android:id");

            LinearLayout linearLayout=new LinearLayout(context);
            String height = parser.getAttributeValue(ns,"android:layout:height");
            String width = parser.getAttributeValue(ns,"android:layout:width");
            String orientation=parser.getAttributeValue(ns,"android:orientation");
            if (height.equals("match_parent")){
                if (width.equals("match_parent")){
                    linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
                }else if(width.equals("wrap_content")){
                    linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
                }else {
                    linearLayout.setLayoutParams(new LinearLayout.LayoutParams(Integer.parseInt(width),LinearLayout.LayoutParams.MATCH_PARENT));
                }
            }
            else if (height.equals("wrap_content")){
                if (width.equals("match_parent")){
                    linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                }else if(width.equals("wrap_content")){
                    linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                }else {
                    linearLayout.setLayoutParams(new LinearLayout.LayoutParams(Integer.parseInt(width),LinearLayout.LayoutParams.WRAP_CONTENT));
                }
            }
            else {
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(Integer.parseInt(width),Integer.parseInt(height)));
            }
            if(orientation.equals("vertical")){

                linearLayout.setOrientation(LinearLayout.VERTICAL);
            }
            else {
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            }
            //LinearLayoutX linearLayoutX=new LinearLayoutX(id);
            //linearLayoutX.setLayout_height(parser.getAttributeValue(ns,"android:layout:height"));
            //linearLayoutX.setLayout_width(parser.getAttributeValue(ns,"android:layout:width"));
            //linearLayoutX.setOrientation(parser.getAttributeValue(ns,"android:orientation"));
            //Log.v("Parse","L id:" + parser.getAttributeValue(ns,"android:id"));
            //Log.v("Parse","L Id="+parser.getAttributeValue(0));
            //Log.v("Parse","L Get Text: "+parser.getText());
            //Log.v("Parse","L Attribute Count: "+parser.getAttributeCount());
            if(parser.getAttributeCount()>0){
                //Log.v("Parse","L GetAttributeName: " +
                     //   ""+parser.getAttributeName(0));
            }
            //Log.v("Parse","LPrefix:" +parser.getPrefix());
            //Log.v("Parse","L ColumnNumber: "+parser.getColumnNumber());
            //Log.v("Parse","LInputEncoding: " +parser.getInputEncoding());

            //Log.v("Parse","LgetAttributeValue: "+parser.getAttributeValue("android:id","android:id"));
          //  Log.v("Parse","L Value :"+parser.getAttributeValue(0));
            //Log.v("Parse","L Attribute Type: "+parser.getAttributeType(0) );
            //Log.v("Parse","L getName:"+ parser.getName());
            parser.require(XmlPullParser.START_TAG, ns, "LinearLayout");
            while (parser.next() != XmlPullParser.END_TAG) {
                //Log.v("Parse","L Start While");
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    //Log.v("Parse","L Inside if ");

                    continue;
                }
                String name = parser.getName();
                //Log.v("Parse","L Parser Read Layout getName : "+name);
                // Starts by looking for the entry tag
                Log.v("Parse","Child :"+name);
                if (name.equals("ImageView")) {
                    //Log.v("Parse","L if == imageview");
                    linearLayout.addView(readImageView(parser));

                }
                else if(name.equals("TextView")) {
                    linearLayout.addView(readTextView(parser));
                }else if(name.equals("Button")){
                    Log.v("Parse","__________Button________");
                    linearLayout.addView(readButton(parser));
                }else if (name.equals("LinearLayout")){
                    Log.v("Parse","__________LinearLayout________");
                    linearLayout.addView(readLayout(parser));
                } else {
                    skip(parser);
                }
            }
            //views.add(linearLayoutX);
            return linearLayout;
        }
    public static class ViewX{
        String id;
        String layout_height;
        String layout_width;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        String type;
        private ViewX(String id){
            this.id=id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLayout_width() {
            return layout_width;
        }

        public void setLayout_width(String layout_width) {
            this.layout_width = layout_width;
        }

        public String getLayout_height() {
            return layout_height;
        }

        public void setLayout_height(String layout_height) {
            this.layout_height = layout_height;
        }
    }
    public static class ImageViewX extends ViewX{
        String src;
        private ImageViewX(String id){
            super(id);
        }

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }
    }
    public static class TextViewX extends ViewX{
        String text;
        private TextViewX(String id){
            super(id);
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
    public static class ButtonX extends TextViewX{
        String onClick;
        private ButtonX(String id) {
            super(id);
        }

        public String getOnClick() {
            return onClick;
        }

        public void setOnClick(String onClick) {
            this.onClick = onClick;
        }
    }
    public static class ViewGroupX extends ViewX{

        private ViewGroupX(String id) {
            super(id);
        }
    }
    public static class LinearLayoutX extends ViewGroupX{
        ArrayList<ViewX> children = new ArrayList<>();

        String orientation;
        private LinearLayoutX(String id) {
            super(id);
        }
        private void addView(ViewX viewX){
            children.add(viewX);
        }
        public ArrayList<ViewX> getChildren() {
            return children;
        }

        public String getOrientation() {
            return orientation;
        }

        public void setOrientation(String orientation) {
            this.orientation = orientation;
        }
    }
    private Button readButton (XmlPullParser parser)throws XmlPullParserException,IOException{
        Button button=new Button(context);
        button.setId(Integer.parseInt(parser.getAttributeValue(ns,"android:id")));
        String height=parser.getAttributeValue(ns,"android:layout_height");
        String width = parser.getAttributeValue(ns,"android:layout_width");
        if (height.equals("match_parent")){
            if (width.equals("match_parent")){
                button.setLayoutParams(new ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }else if(width.equals("wrap_content")){
                button.setLayoutParams(new  ViewGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,  ViewGroup.LayoutParams.MATCH_PARENT));
            }else {
                button.setLayoutParams(new  ViewGroup.LayoutParams(Integer.parseInt(width), ViewGroup.LayoutParams(Integer.parseInt(height)));
            }
        }
        else if (height.equals("wrap_content")){
            if (width.equals("match_parent")){
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
            }else if(width.equals("wrap_content")){
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            }else {
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(Integer.parseInt(width),LinearLayout.LayoutParams.WRAP_CONTENT));
            }
        }
        else {
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(Integer.parseInt(width),Integer.parseInt(height)));
        }
        button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        parser.require(XmlPullParser.START_TAG, ns,"Button");
        ButtonX buttonX=new ButtonX(parser.getAttributeValue(ns,"android:id"));
        buttonX.setLayout_height(parser.getAttributeValue(ns,"android:layout_height"));
        buttonX.setLayout_width(parser.getAttributeValue(ns,"android:layout_width"));
        buttonX.setOnClick(parser.getAttributeValue(ns,"android:onClick"));
        buttonX.setType("Button");
        Log.v("Parse"," " +buttonX.getType());
        while (parser.next() != XmlPullParser.END_TAG){
            if(parser.getEventType() != XmlPullParser.START_TAG){
                continue;
            }
            skip(parser);
        }
        return buttonX;
    }
    private ImageViewX readImageView(XmlPullParser parser)throws XmlPullParserException,IOException{
        parser.require(XmlPullParser.START_TAG, ns,"ImageView");
        String id=null;
        id=parser.getAttributeValue(ns,"android:id");
        ImageViewX imageView= new ImageViewX(id);
        imageView.setLayout_width(parser.getAttributeValue(ns,"android:layout_width"));
        imageView.setLayout_height(parser.getAttributeValue(ns,"android:layout_height"));
        imageView.setSrc(parser.getAttributeValue(ns,"android:src"));
        imageView.setType("ImageView");

        //Log.v("Parse","L id:" + parser.getAttributeValue(ns,"android:id"));

        //Log.v("Parse","IM Read Image View");
        //Log.v("Parse","IM GetAttributeName: "+parser.getAttributeName(0));
        //Log.v("Parse","IM Value :"+parser.getAttributeValue(0));
        //Log.v("Parse","IM Attribute Type: "+parser.getAttributeType(0) );
        //Log.v("Parse","IM Get Text: "+parser.getText());
        //Log.v("Parse","IM Event Type: "+parser.getEventType());
        //Log.v("Parse","IM Attribute Count: "+parser.getAttributeCount());
        //Log.v("Parse","IM ColumnNumber: "+parser.getColumnNumber());
        //Log.v("Parse","IM Prefix:" +parser.getPrefix());
        //Log.v("Parse","IM getAttributeValue: "+parser.getAttributeValue("android:id","android:id"));
        //Log.v("Parse","IM InputEncoding: " +parser.getInputEncoding());
        while (parser.next() != XmlPullParser.END_TAG){
            if(parser.getEventType() != XmlPullParser.START_TAG){
                continue;
            }
            skip(parser);
        }

//        while (parser.next() !=XmlPullParser.END_TAG){
//            //Log.v("Parse","While Started");
//            if(parser.getEventType() != XmlPullParser.START_TAG){
//                //Log.v("Parse","Continue Read Image View");
//                continue;
//            }
//            String name= parser.getName();
//            //Log.v("Parse","Parse.getName :"+name);
//            if (name.equals("android:id")){
//                id=readId(parser);
//            }else {
//                skip(parser);
//            }
//        }
        //Log.v("Parse","IM Parse id: "+id);
        return imageView;
    }
    private TextViewX readTextView(XmlPullParser parser)throws XmlPullParserException,IOException{
        parser.require(XmlPullParser.START_TAG, ns,"TextView");
        String id=null;
        id=parser.getAttributeValue(ns,"android:id");
        TextViewX textView = new TextViewX(id);
        textView.setLayout_width(parser.getAttributeValue(ns,"android:layout_width"));
        textView.setLayout_height(parser.getAttributeValue(ns,"android:layout_height"));
        textView.setText(parser.getAttributeValue(ns,"android:text"));
        textView.setType("TextView");
        while (parser.next() != XmlPullParser.END_TAG){
            if(parser.getEventType() != XmlPullParser.START_TAG){
                continue;
            }
            skip(parser);
        }
        //Log.v("Parse","L id:" + parser.getAttributeValue(ns,"android:id"));

        //Log.v("Parse","T Read Text View");
        //Log.v("Parse","T GetAttributeName: " +
             //   ""+parser.getAttributeName(0));
        //Log.v("Parse","T Value :"+parser.getAttributeValue(0));
        //Log.v("Parse","T Event Type: "+parser.getEventType());
        //Log.v("Parse","T Get Text: "+parser.getText());
        //Log.v("Parse","T Attribute Count: "+parser.getAttributeCount());
        //Log.v("Parse","T ColumnNumber: "+parser.getColumnNumber());
        //Log.v("Parse","T Attribute Type: "+parser.getAttributeType(0) );
        //Log.v("Parse","T Prefix:" +parser.getPrefix());
        //Log.v("Parse","T InputEncoding: " +parser.getInputEncoding());
        //Log.v("Parse","T getAttributeValue: "+parser.getAttributeValue("android:id","android:id"));

//        while (parser.next() !=XmlPullParser.END_TAG){
//            //Log.v("Parse","T While Started");
//            if(parser.getEventType() != XmlPullParser.START_TAG){
//                //Log.v("Parse","T Continue Read Text View");
//                continue;
//            }
//            String name= parser.getName();
//            //Log.v("Parse","T Parse.getName :"+name);
//            if (name.equals("android:id")){
//                id=readId(parser);
//            }else {
//                skip(parser);
//            }
//        }
        //Log.v("Parse","T Parse id: "+id);
        return textView;
    }
//    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
//        String result = "";
//        if (parser.next() == XmlPullParser.TEXT) {
//            result = parser.getText();
//            parser.nextTag();
//        }
//        return result;
//    }
//    private String readId (XmlPullParser parser)throws IOException,XmlPullParserException{
//        parser.require(XmlPullParser.START_TAG,ns,"android:id");
//        String id=readText(parser);
//        parser.require(XmlPullParser.END_TAG,ns,"android:id");
//        return id;
//    }
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
}

