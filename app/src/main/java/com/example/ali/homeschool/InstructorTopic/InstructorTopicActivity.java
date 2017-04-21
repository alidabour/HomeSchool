package com.example.ali.homeschool.InstructorTopic;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ali.homeschool.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class InstructorTopicActivity extends AppCompatActivity  implements ImageClicked{
    int id=0;
    DatabaseReference databaseReference;
    ArrayList<String> midLayouts;
    TextView submitTV;
    String m_Text = "";
    String audioLink = "";
    TextView image;
    TextView sound;
    ImageView ims;
    LinearLayout act_main;
    LinearLayout mainView;
    String start = "<LinearLayout " +
            "android:orientation=\"vertical\" " +
            "android:layout_weight=\"0\" " +
            "android:id=\"2000\" " +
            "android:layout_width=\"match_parent\" " +
            "android:layout_height=\"match_parent\">";
    String mid = "";
    String end = "</LinearLayout>";
    String layout = "<LinearLayout " +
            "android:orientation=\"vertical\" " +
            "android:layout_weight=\"0\" " +
            "android:id=\"2000\" " +
            "android:layout_width=\"match_parent\" " +
            "android:layout_height=\"match_parent\">" +

            "<ImageView android:layout_weight=\"5\" android:id=\"1\" android:layout_width=\"match_parent\"" +
            " android:layout_height=\"wrap_content\" />" +

            "<LinearLayout android:orientation=\"horizontal\" android:layout_weight=\"1\" android:id=\"6\" " +
            "android:layout_width=\"match_parent\" android:layout_height=\"wrap_content\">" +

            "<Button android:layout_weight=\"1\" android:id=\"12\" android:text=\"Meow\" android:layout_width=\"0\" " +
            "android:layout_height=\"match_parent\" />" +

            "<Button android:layout_weight=\"1\" android:id=\"1\" android:text=\"Cat\" android:layout_width=\"0\" " +
            "android:layout_height=\"match_parent\" />" +

            "</LinearLayout>" +
            "</LinearLayout>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_topic);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        submitTV = (TextView) findViewById(R.id.submit);
        submitTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference = databaseReference.child("courses").child("0").child("lessons").child("1").child("topics");
                String key = databaseReference.push().getKey();
                TopicModel t= new TopicModel();
                t.setId(key);
                t.setName("Name");
                t.setLayout(start  + midLayouts.toString() + end);
                databaseReference.child(key).updateChildren(t.toMap());
                finish();


            }
        });
        midLayouts = new ArrayList<>();
        act_main = (LinearLayout) findViewById(R.id.activiy_main);
        mainView = (LinearLayout) findViewById(R.id.mainLayout);
        image = (TextView) findViewById(R.id.image);
        sound = (TextView) findViewById(R.id.sound);
        ims = (ImageView) findViewById(R.id.ims);
//        Picasso.with(getApplicationContext())
//                .load(R.drawable.catimage)
//                .into(ims);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(InstructorTopicActivity.this);
                builder.setTitle("Title");

                // Set up the input
                final EditText input = new EditText(InstructorTopicActivity.this);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(
                        InputType.TYPE_CLASS_TEXT );
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
                        mid = "<ImageView android:layout_weight=\"5\" android:id=\"" + id
                                + "\" android:layout_width=\"match_parent\"" +
                                " android:layout_height=\"wrap_content\" homeSchool:src=\""+m_Text+"\" />";
                        midLayouts.add(id,mid);
                        id++;
                        LinearLayout linearLayout = parse(start + midLayouts.toString() + end);
                        mainView.removeAllViews();
                        mainView.addView(linearLayout);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });
        sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(InstructorTopicActivity.this);
                builder.setTitle("Sound Button");
                LayoutInflater li = LayoutInflater.from(InstructorTopicActivity.this);
                LinearLayout someLayout;
                someLayout = (LinearLayout)li.inflate(R.layout.dialog_button, null);

                // Set up the input
//                final EditText input = new EditText(MainActivity.this);
//                final EditText audioIn = new EditText(MainActivity.this);
                final EditText input = (EditText) someLayout.findViewById(R.id.text);
                final EditText audioIn = (EditText) someLayout.findViewById(R.id.audio);

//                input.setHint("Text");
//                audioIn.setHint("Audio Link");
//                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
//                input.setInputType(
//                        InputType.TYPE_CLASS_TEXT );
//                audioIn.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(someLayout);


                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
                        audioLink = audioIn.getText().toString();
                        mid =  "<Button android:layout_weight=\"1\" android:id=\""+ id
                                +"\" android:text=\""+m_Text+"\" android:layout_width=\"0\" " +
                                "android:layout_height=\"match_parent\"" +
                                "homeSchool:audioLink=\""+audioLink+"\" />";
                        midLayouts.add(id,mid);

                        id++;
                        LinearLayout linearLayout = parse(start + midLayouts.toString() + end);
                        mainView.removeAllViews();
                        mainView.addView(linearLayout);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });
    }

    LinearLayout parse(String layout) {
        InputStream stream = null;
        stream = new ByteArrayInputStream(layout.getBytes(Charset.forName("UTF-8")));
        ParseXML parseXML = new ParseXML();
        ParseXML.LinearLayoutX viewX = null;
        LinearLayout mainLayout = null;

        try {
            mainLayout = (LinearLayout) parseXML.parse(stream, getApplicationContext(),
                    this);

            Log.v("Test", "pass");
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mainLayout;
    }

    @Override
    public void onClick(View v) {
        Log.v("Test","COUNT BF " + mainView.getChildCount());
        Log.v("Test","id " + v.getId());
//        v.setVisibility(View.INVISIBLE);
        mainView.removeView(v);
        int mainId = v.getId();
        Log.v("Test", "Main ID :" + v.getId());
        LinearLayout v0 = (LinearLayout) mainView.getChildAt(0);
        Log.v("Test","V0 id "+ v0.getId());
        v0.removeView(v);
//        LinearLayout view =(LinearLayout) mainView.findViewById(2000);
//        view.removeView(v);
//        mainView.removeView((View) view.getParent());
//        mainView.removeViewInLayout(view);

        act_main.removeView(v);
//        mainView.removeViewAt(v.getInd);
        mid = "";
        mainView.invalidate();
        Log.v("Test","COUNT AF " + mainView.getChildCount());
//        midLayouts.get(v.getId());
        midLayouts.remove(v.getId());
        --id;
        Log.v("Test","mid Layouts "+midLayouts);
    }
}
