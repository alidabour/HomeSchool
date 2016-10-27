package com.example.ali.homeschool;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
        imageView = new ImageView(linearLayout.getContext());
        imageView.setImageDrawable(linearLayout.getResources().getDrawable(R.drawable.ic_launcher));
        //Width = MP , Height = WC
        imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        linearLayout.addView(imageView);
        //Set View after finish construction the layout
        setContentView(linearLayout);
    }
}
