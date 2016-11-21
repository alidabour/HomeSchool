package com.example.ali.homeschool;

import android.util.Log;
import android.util.Xml;
import android.widget.ImageView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dabour on 11/20/2016.
 */

public class ParseXML {
    private static final String ns = null;
    public List parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readLayout(parser);
        } finally {
            in.close();
        }
    }
        private List readLayout(XmlPullParser parser) throws XmlPullParserException, IOException {
            List views = new ArrayList();
            Log.v("Parse","L Get Text: "+parser.getText());
            Log.v("Parse","L Attribute Count: "+parser.getAttributeCount());
            if(parser.getAttributeCount()>0){
                Log.v("Parse","L GetAttributeName: " +
                        ""+parser.getAttributeName(0));
            }
            //Log.v("Parse","LPrefix:" +parser.getPrefix());
            Log.v("Parse","L ColumnNumber: "+parser.getColumnNumber());
            //Log.v("Parse","LInputEncoding: " +parser.getInputEncoding());

            //Log.v("Parse","LgetAttributeValue: "+parser.getAttributeValue("android:id","android:id"));
          //  Log.v("Parse","L Value :"+parser.getAttributeValue(0));
            Log.v("Parse","L Attribute Type: "+parser.getAttributeType(0) );
            Log.v("Parse","L getName:"+ parser.getName());
            parser.require(XmlPullParser.START_TAG, ns, "LinearLayout");
            while (parser.next() != XmlPullParser.END_TAG) {
                Log.v("Parse","L Start While");
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    Log.v("Parse","L Inside if ");

                    continue;
                }
                String name = parser.getName();
                Log.v("Parse","L Parser Read Layout getName : "+name);
                // Starts by looking for the entry tag
                if (name.equals("ImageView")) {
                    Log.v("Parse","L if == imageview");
                    views.add(readImageView(parser));
                }
                else if(name.equals("TextView")) {
                    views.add(readTextView(parser));
                } else {
                    Log.v("Parse","L else");

                    skip(parser);
                }
            }
            return views;
        }
    public static class ViewX{
        String id;
        private ViewX(String id){
            this.id=id;
        }
    }
    public static class ImageViewX extends ViewX{

        private ImageViewX(String id){
            super(id);
        }
    }
    public static class TextViewX extends ViewX{
        private TextViewX(String id){
            super(id);
        }
    }
    private ImageViewX readImageView(XmlPullParser parser)throws XmlPullParserException,IOException{
        parser.require(XmlPullParser.START_TAG, ns,"ImageView");
        String id=null;
        id=parser.getAttributeValue(0);

        Log.v("Parse","IM Read Image View");
        Log.v("Parse","IM GetAttributeName: "+parser.getAttributeName(0));
        Log.v("Parse","IM Value :"+parser.getAttributeValue(0));
        //Log.v("Parse","IM Attribute Type: "+parser.getAttributeType(0) );
        //Log.v("Parse","IM Get Text: "+parser.getText());
        //Log.v("Parse","IM Event Type: "+parser.getEventType());
        //Log.v("Parse","IM Attribute Count: "+parser.getAttributeCount());
        //Log.v("Parse","IM ColumnNumber: "+parser.getColumnNumber());
        //Log.v("Parse","IM Prefix:" +parser.getPrefix());
        //Log.v("Parse","IM getAttributeValue: "+parser.getAttributeValue("android:id","android:id"));
        //Log.v("Parse","IM InputEncoding: " +parser.getInputEncoding());

        while (parser.next() !=XmlPullParser.END_TAG){
            Log.v("Parse","While Started");
            if(parser.getEventType() != XmlPullParser.START_TAG){
                Log.v("Parse","Continue Read Image View");
                continue;
            }
            String name= parser.getName();
            Log.v("Parse","Parse.getName :"+name);
            if (name.equals("android:id")){
                id=readId(parser);
            }else {
                skip(parser);
            }
        }
        Log.v("Parse","IM Parse id: "+id);
        return new ImageViewX(id);
    }
    private TextViewX readTextView(XmlPullParser parser)throws XmlPullParserException,IOException{
        parser.require(XmlPullParser.START_TAG, ns,"TextView");
        String id=null;
        id=parser.getAttributeValue(0);
        Log.v("Parse","T Read Text View");
        Log.v("Parse","T GetAttributeName: " +
                ""+parser.getAttributeName(0));
        Log.v("Parse","T Value :"+parser.getAttributeValue(0));
        //Log.v("Parse","T Event Type: "+parser.getEventType());
        //Log.v("Parse","T Get Text: "+parser.getText());
        //Log.v("Parse","T Attribute Count: "+parser.getAttributeCount());
        //Log.v("Parse","T ColumnNumber: "+parser.getColumnNumber());
        //Log.v("Parse","T Attribute Type: "+parser.getAttributeType(0) );
        //Log.v("Parse","T Prefix:" +parser.getPrefix());
        //Log.v("Parse","T InputEncoding: " +parser.getInputEncoding());
        //Log.v("Parse","T getAttributeValue: "+parser.getAttributeValue("android:id","android:id"));

        while (parser.next() !=XmlPullParser.END_TAG){
            Log.v("Parse","T While Started");
            if(parser.getEventType() != XmlPullParser.START_TAG){
                Log.v("Parse","T Continue Read Text View");
                continue;
            }
            String name= parser.getName();
            Log.v("Parse","T Parse.getName :"+name);
            if (name.equals("android:id")){
                id=readId(parser);
            }else {
                skip(parser);
            }
        }
        Log.v("Parse","T Parse id: "+id);
        return new TextViewX(id);
    }
    private String readId (XmlPullParser parser)throws IOException,XmlPullParserException{
        parser.require(XmlPullParser.START_TAG,ns,"android:id");
        String id=readText(parser);
        parser.require(XmlPullParser.END_TAG,ns,"android:id");
        return id;
    }
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
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
}

