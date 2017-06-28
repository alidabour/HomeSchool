package com.almanara.homeschool.student.course.lesson.topic.template;

/**
 * Created by Ali on 6/27/2017.
 */

import android.media.MediaPlayer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.almanara.ali.homeschool.R;

import java.util.Timer;

/**
 * A placeholder fragment containing a simple view.
 */

public class AnimationFragment extends Fragment {

    ImageView letter;
    ImageView word;
    Timer myTimer;
    String layout;
    String[] parms;


    private final String HOLD = " ,HO##LD,";

    public AnimationFragment() {
    }

    public static AnimationFragment newInstance(String layout) {
        AnimationFragment fragment = new AnimationFragment();
        Bundle args = new Bundle();
        args.putString("layout", layout);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            layout = getArguments().getString("layout");
            if (layout != null) {
                parms = layout.split("(?<=" + HOLD + ")");
                for (int i = 0; i < parms.length; i++) {
                    parms[i] = parms[i].replaceAll(HOLD, " ");
                }
            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.animtation_fragment_topic, container, false);

//        clockwise(view);
//        zoom(view);
        final MediaPlayer mediaPlayer = MediaPlayer.create(getActivity(), R.raw.catname);
        final MediaPlayer mediaPlayerword = MediaPlayer.create(getActivity(), R.raw.catname);
        letter = (ImageView) view.findViewById(R.id.letter);
        word = (ImageView) view.findViewById(R.id.word);
        letter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                clockwise(v);
                mediaPlayer.start();
                v.animate().alpha(0).setDuration(2000).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        v.setVisibility(View.GONE);
                        word.setVisibility(View.VISIBLE);
                    }
                });
            }
        });

        word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayerword.start();
                moveword(v);
            }
        });

        return view;
    }

    public void clockwise(View view) {
        ImageView image = (ImageView) view.findViewById(R.id.letter);
        Animation animation = AnimationUtils.loadAnimation(getActivity(),
                R.anim.myanimation);
        image.startAnimation(animation);
    }

    public void zoom(View view) {
        ImageView image = (ImageView) view.findViewById(R.id.word);
        Animation animation1 = AnimationUtils.loadAnimation(getActivity(),
                R.anim.clockwise);
        image.startAnimation(animation1);
    }

    public void fade(View view) {
        ImageView image = (ImageView) view.findViewById(R.id.word);
        Animation animation1 =
                AnimationUtils.loadAnimation(getActivity(),
                        R.anim.fade);
        image.startAnimation(animation1);
    }

    public void blink(View view) {
        ImageView image = (ImageView) view.findViewById(R.id.word);
        Animation animation1 =
                AnimationUtils.loadAnimation(getActivity(),
                        R.anim.blink);
        image.startAnimation(animation1);
    }

    public void move(View view) {
        ImageView image = (ImageView) view.findViewById(R.id.letter);
        Animation animation1 =
                AnimationUtils.loadAnimation(getActivity(), R.anim.move);
        image.startAnimation(animation1);
    }

    public void moveword(View view) {
        ImageView image = (ImageView) view.findViewById(R.id.word);
        Animation animation1 =
                AnimationUtils.loadAnimation(getActivity(), R.anim.move);
        image.startAnimation(animation1);
    }

    public void slide(View view) {
        ImageView image = (ImageView) view.findViewById(R.id.letter);
        Animation animation1 =
                AnimationUtils.loadAnimation(getActivity(), R.anim.slide);
        image.startAnimation(animation1);
    }
}

