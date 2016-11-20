package com.example.ali.homeschool;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.simonvt.schematic.annotation.TableEndpoint;

public class ClassActivity extends AppCompatActivity {
    //RelativeLayout relativeLayout;
    LinearLayout linearLayout;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //relativeLayout = (RelativeLayout) findViewById(R.id.activity_class);
        linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        //Width = MP , Height = MP
        LinearLayout.LayoutParams lp =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        //imageView = new ImageView(linearLayout.getContext());
        //imageView.setImageDrawable(linearLayout.getResources().getDrawable(R.drawable.ic_launcher));
        //Width = MP , Height = WC
        //imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        //String layout[] ={"Image","Text","Image"};
        String layout[] ={"Image","Text","LinearLayout"};
        for (int i=0; i<layout.length; i++){
            switch(layout[i]){
                case "Image":
                    linearLayout.addView(CreateImageView());
                    break;
                case "Text":
                    linearLayout.addView(CreateTextView());
                    break;
                case "LinearLayout":
                    linearLayout.addView(CreateLinearLayout());
                    break;
            }
        }

        //Set View after finish construction the layout
        setContentView(linearLayout);
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
