package com.example.ali.homeschool.childClass.topicsfragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ali.homeschool.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MultiImageQuestionFragment extends Fragment implements View.OnClickListener {


    public MultiImageQuestionFragment() {
        // Required empty public constructor
    }

    public static MultiImageQuestionFragment newInstance(String layout) {
        MultiImageQuestionFragment fragment = new MultiImageQuestionFragment();
        Bundle args = new Bundle();
        args.putString("layout", layout);
        fragment.setArguments(args);
        return fragment;
    }

    private final String HOLD = " ,HO##LD,";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            layout = getArguments().getString("layout");
            if(layout !=null){
                parms = layout.split("(?<=" + HOLD + ")");
                for (int i = 0; i < parms.length; i++) {
                    parms[i] = parms[i].replaceAll(HOLD, " ");
                }
                questionHeadText = parms[0];
                answer1text = parms[2];
                answer2text = parms[5];
                answer3text = parms[8];
                answer4text = parms[11];
                url1 = parms[1];
                url2 = parms[4];
                url3 = parms[7];
                url4 = parms[10];
                isCorrect1 = parms[3].trim().equals("true");
                isCorrect2 = parms[6].trim().equals("true");
                isCorrect3 = parms[9].trim().equals("true");
                isCorrect4 = parms[12].trim().equals("true");


                Log.v("MultiImageQuestion", "Layout :" + layout);

            }

        }
    }

    //Data
    String layout;
    String[] parms;
    String questionHeadText;
    String answer1text;
    String answer2text;
    String answer3text;
    String answer4text;
    String url1;
    String url2;
    String url3;
    String url4;
    boolean isCorrect1 = false;
    boolean isCorrect2 = false;
    boolean isCorrect3 = false;
    boolean isCorrect4 = false;
    //UI
    TextView questionHead;
    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    RadioButton radioButton1;
    RadioButton radioButton2;
    RadioButton radioButton3;
    RadioButton radioButton4;
    LinearLayout line1;
    LinearLayout line2;
    LinearLayout line3;
    LinearLayout line4;
    Button submit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_multi_image_question, container, false);
        questionHead = (TextView) view.findViewById(R.id.question);
        questionHead.setText(questionHeadText);
        imageView1 = (ImageView) view.findViewById(R.id.image1);
        imageView2 = (ImageView) view.findViewById(R.id.image2);
        imageView3 = (ImageView) view.findViewById(R.id.image3);
        imageView4 = (ImageView) view.findViewById(R.id.image4);
        Glide.with(getActivity()).load(url1).into(imageView1);
        Glide.with(getActivity()).load(url2).into(imageView2);
        Glide.with(getActivity()).load(url3).into(imageView3);
        Glide.with(getActivity()).load(url4).into(imageView4);

        radioButton1 = (RadioButton) view.findViewById(R.id.radioButton1);
        radioButton2 = (RadioButton) view.findViewById(R.id.radioButton2);
        radioButton3 = (RadioButton) view.findViewById(R.id.radioButton3);
        radioButton4 = (RadioButton) view.findViewById(R.id.radioButton4);
        radioButton1.setText(answer1text);
        radioButton2.setText(answer2text);
        radioButton3.setText(answer3text);
        radioButton4.setText(answer4text);
        radioButton1.setOnClickListener(this);
        radioButton2.setOnClickListener(this);
        radioButton3.setOnClickListener(this);
        radioButton4.setOnClickListener(this);
        line1 = (LinearLayout) view.findViewById(R.id.line1);
        line2 = (LinearLayout) view.findViewById(R.id.line2);
        line3 = (LinearLayout) view.findViewById(R.id.line3);
        line4 = (LinearLayout) view.findViewById(R.id.line4);
        submit = (Button) view.findViewById(R.id.submit);
        submit.setText("Check");
        submit.setEnabled(false);
        submit.setOnClickListener(this);
        return view;
    }

    public void clear() {
        radioButton1.setChecked(false);
        radioButton2.setChecked(false);
        radioButton3.setChecked(false);
        radioButton4.setChecked(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.radioButton1:
                clear();
                radioButton1.setChecked(true);
                submit.setEnabled(true);
                submit.setBackgroundColor(Color.parseColor("#ff0000"));
                lineBackgroudColor();
                line1.setBackgroundColor(Color.parseColor("#ff0000"));
                break;
            case R.id.radioButton2:
                clear();
                radioButton2.setChecked(true);
                submit.setEnabled(true);
                submit.setBackgroundColor(Color.parseColor("#ff0000"));
                lineBackgroudColor();
                line2.setBackgroundColor(Color.parseColor("#ff0000"));

                break;
            case R.id.radioButton3:
                clear();
                radioButton3.setChecked(true);
                submit.setEnabled(true);
                submit.setBackgroundColor(Color.parseColor("#ff0000"));
                lineBackgroudColor();
                line3.setBackgroundColor(Color.parseColor("#ff0000"));

                break;
            case R.id.radioButton4:
                clear();
                radioButton4.setChecked(true);
                submit.setEnabled(true);
                submit.setBackgroundColor(Color.parseColor("#ff0000"));
                lineBackgroudColor();
                line4.setBackgroundColor(Color.parseColor("#ff0000"));
                break;
            case R.id.submit:
                Log.v("MultiImageQuestion", "1 : " + isCorrect1 + " " + radioButton1.isChecked());
                Log.v("MultiImageQuestion", "2 : " + isCorrect2 + " " + radioButton2.isChecked());
                Log.v("MultiImageQuestion", "3 : " + isCorrect3 + " " + radioButton3.isChecked());
                Log.v("MultiImageQuestion", "4 : " + isCorrect4 + " " + radioButton4.isChecked());

                if (radioButton1.isChecked() && isCorrect1) {
                    Toast.makeText(getActivity(), "Correct", Toast.LENGTH_LONG).show();
                    Log.v("MultiImageQuestion", "Correct----------");
                }
                if (radioButton2.isChecked() && isCorrect2) {
                    Log.v("MultiImageQuestion", "Correct----------");
                    Toast.makeText(getActivity(), "Correct", Toast.LENGTH_LONG).show();
                }
                if (radioButton3.isChecked() && isCorrect3) {
                    Toast.makeText(getActivity(), "Correct", Toast.LENGTH_LONG).show();
                }
                if (radioButton4.isChecked() && isCorrect4) {
                    Toast.makeText(getActivity(), "Correct", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;

        }
    }

    void lineBackgroudColor() {
        line1.setBackgroundColor(Color.parseColor("#ffffff"));
        line2.setBackgroundColor(Color.parseColor("#ffffff"));
        line3.setBackgroundColor(Color.parseColor("#ffffff"));
        line4.setBackgroundColor(Color.parseColor("#ffffff"));

    }


}
