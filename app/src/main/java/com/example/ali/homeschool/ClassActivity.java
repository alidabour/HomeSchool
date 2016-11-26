package com.example.ali.homeschool;

import android.content.Context;
import android.media.Image;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.simonvt.schematic.annotation.TableEndpoint;

import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class ClassActivity extends AppCompatActivity {
    //RelativeLayout relativeLayout;
    View linearLayout;
    ImageView imageView;
    Context context;
    ViewPager pager;
    ArrayList<String> layouts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layouts=new ArrayList<String>();
        //relativeLayout = (RelativeLayout) findViewById(R.id.activity_class);
        context=getApplicationContext();
        //InputStream stream = null;
        String layout = "<LinearLayout android:orientation=\"vertical\" android:layout_weight=\"0\" android:id=\"6\" android:layout_width=\"match_parent\" android:layout_height=\"match_parent\"><ImageView android:layout_weight=\"5\" android:id=\"1\" android:layout_width=\"match_parent\" android:layout_height=\"wrap_content\" /><LinearLayout android:orientation=\"horizontal\" android:layout_weight=\"1\" android:id=\"6\" android:layout_width=\"match_parent\" android:layout_height=\"wrap_content\"><Button android:layout_weight=\"1\" android:id=\"12\" android:text=\"Meow\" android:layout_width=\"0\" android:layout_height=\"match_parent\" /><Button android:layout_weight=\"1\" android:id=\"1\" android:text=\"Cat\" android:layout_width=\"0\" android:layout_height=\"match_parent\" /></LinearLayout></LinearLayout>";
        layouts.add(layout);
        String layoutt = "<LinearLayout android:orientation=\"vertical\" android:layout_weight=\"0\" android:id=\"6\" android:layout_width=\"match_parent\" android:layout_height=\"match_parent\"><ImageView android:layout_weight=\"5\" android:id=\"6\" android:layout_width=\"match_parent\" android:layout_height=\"wrap_content\" /><LinearLayout android:orientation=\"horizontal\" android:layout_weight=\"1\" android:id=\"6\" android:layout_width=\"match_parent\" android:layout_height=\"wrap_content\"><Button android:layout_weight=\"1\" android:id=\"21\" android:text=\"Bark\" android:layout_width=\"0\" android:layout_height=\"match_parent\" /><Button android:layout_weight=\"1\" android:id=\"2\" android:text=\"Dog\" android:layout_width=\"0\" android:layout_height=\"match_parent\" /></LinearLayout></LinearLayout>";
        layouts.add(layoutt);

        setContentView(R.layout.activity_class);
        pager = (ViewPager) findViewById(R.id.viewPager);
        pager.setAdapter(new TopicPagerAdapter(getSupportFragmentManager(),layouts));
//        String layout="<LinearLayout \n" +
//                "android:orientation=\"vertical\"\n" +
//                "    android:id=\"3\"\n" +
//                "    android:layout_width=\"match_parent\"\n" +
//                "    android:layout_height=\"match_parent\"\n" +
//                "<ImageView\n" +
//                "    android:layout_weight=\"5\"\n" +
//                "    android:layout_width=\"match_parent\"\n" +
//                "    android:layout_height=\"0dp\" />\n" +
//                "    <LinearLayout\n" +
//                "        android:orientation=\"horizontal\"\n" +
//                "        android:layout_weight=\"1\"\n" +
//                "        android:layout_width=\"match_parent\"\n" +
//                "        android:layout_height=\"0dp\">\n" +
//                "        <Button\n" +
//                "            android:text=\"Sound\"\n" +
//                "            android:onClick=\"sound\"\n" +
//                "            android:id=\"1\"\n" +
//                "            android:layout_weight=\"1\"\n" +
//                "            android:layout_width=\"0dp\"\n" +
//                "            android:layout_height=\"match_parent\" />\n" +
//                "        <Button\n" +
//                "            android:onClick=\"name\"\n" +
//                "            android:id=\"2\"\n" +
//                "            android:text=\"Name\"\n" +
//                "            android:layout_weight=\"1\"\n" +
//                "            android:layout_width=\"0dp\"\n" +
//                "            android:layout_height=\"match_parent\" />\n" +
//                "    </LinearLayout>\n" +
//                "\n" +
//                "</LinearLayout>";
//        stream = new ByteArrayInputStream(layout.getBytes(Charset.forName("UTF-8")));
//        ParseXML parseXML = new ParseXML();
//        ParseXML.LinearLayoutX viewX =null;
//        try {
//            linearLayout=parseXML.parse(stream,context);
//
//        } catch (XmlPullParserException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        //Log.v("ClassActivity","Linear Layout : "+linearLayout.getId());
//        linearLayout = new LinearLayout(this);
//        if(viewX.getOrientation().equals("vertical")){
//            linearLayout.setOrientation(LinearLayout.VERTICAL);
//        }
//        else {
//            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
//        }
//        //Width = MP , Height = MP
//        if(viewX.getLayout_height().equals("match_parent")){
//            if (viewX.getLayout_width().equals("match_parent")){
//
//            }
//        }
//        LinearLayout.LayoutParams lp =
//                new LinearLayout.LayoutParams(-1,LinearLayout.LayoutParams.MATCH_PARENT);
        //imageView = new ImageView(linearLayout.getContext());
        //imageView.setImageDrawable(linearLayout.getResources().getDrawable(R.drawable.ic_launcher));
        //Width = MP , Height = WC
        //imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        //String layout[] ={"Image","Text","Image"};
        //String layout[] ={"Image","Text","LinearLayout"};
//        for (int i=0; i<layout.length; i++){
//            switch(layout[i]){
//                case "Image":
//                    linearLayout.addView(CreateImageView());
//                    break;
//                case "Text":
//                    linearLayout.addView(CreateTextView());
//                    break;
//                case "LinearLayout":
//                    linearLayout.addView(CreateLinearLayout());
//                    break;
//            }
//        }

        //Set View after finish construction the layout
    }
    View CreateImageView(){
        ImageView imageView = new ImageView(linearLayout.getContext());
        imageView.setImageDrawable(linearLayout.getResources().getDrawable(R.drawable.ic_launcher));
        imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        return imageView;
    }
    View CreateTextView(){
        TextView textView = new TextView(linearLayout.getContext());
        textView.setText("Android");
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        return textView;
    }
    View CreateLinearLayout(){
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout layout1 = new LinearLayout(linearLayout.getContext());
        layout1.setLayoutParams(lp);
        layout1.setOrientation(LinearLayout.HORIZONTAL);
        String layout[] ={"Image","Image","Text"};
        Log.v("Test","TEST1");
        for (int i=0; i<layout.length; i++) {
            switch (layout[i]) {
                case "Image":
                    Log.v("Test","TEST3");
                    layout1.addView(CreateImageView());
                    break;
                case "Text":
                    Log.v("Test","TEST2");
                    layout1.addView(CreateTextView());
                    break;
            }
        }
        return layout1;
    }

}
