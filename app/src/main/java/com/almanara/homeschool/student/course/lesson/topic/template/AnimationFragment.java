package com.almanara.homeschool.student.course.lesson.topic.template;

/**
 * Created by Ali on 6/27/2017.
 */

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.almanara.ali.homeschool.R;
import com.almanara.homeschool.student.course.lesson.topic.ClassActivity;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Random;

/**
 * A placeholder fragment containing a simple view.
 */

public class AnimationFragment extends Fragment {

    ImageView letter;
    ImageView word;
    String layout;
    String[] parms;
    ArrayList<Integer> random;
    private final String HOLD = " ,HO##LD,";
    RelativeLayout relativelayout ;
    int page ;
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
        random = new ArrayList<Integer>();
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
        Random r = new Random();
        final int letterIndex = r.nextInt(6 - 0) + 0;
        final int workIndex = r.nextInt(6 - 0) + 0;
        final MediaPlayer mediaPlayer = MediaPlayer.create(getActivity(), Uri.parse(parms[2]));
        final MediaPlayer mediaPlayerword = MediaPlayer.create(getActivity(), Uri.parse(parms[3]));
        relativelayout = (RelativeLayout) view.findViewById(R.id.animation_layout);

        if (getActivity() instanceof ClassActivity) {
            page = ((ClassActivity) getActivity()).getCurrentPage();
            Log.v("Animation page " ,page+"" );
        }
        if(page % 2 == 0 ){

            relativelayout.setBackground(getActivity().getResources().getDrawable(R.drawable.characteres_1));
        } else {
            relativelayout.setBackground(getActivity().getResources().getDrawable(R.drawable.characteres_2));
        }

        letter = (ImageView) view.findViewById(R.id.letter);
        word = (ImageView) view.findViewById(R.id.word);
        Glide.with(getActivity()).load(parms[0]).into(letter);
        Glide.with(getActivity()).load(parms[1]).into(word);
        letter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                int id = R.id.letter;
                switch (letterIndex) {
                    case 0:
                        clockwise(v, id);
                        break;
                    case 1:
                        zoom(v, id);
                        break;
                    case 2:
                        fade(v, id);
                        break;
                    case 3:
                        blink(v, id);
                        break;
                    case 4:
                        move(v, id);
                        break;
                    case 5:
                        slide(v, id);
                        break;
                }

                if(getActivity() instanceof  ClassActivity){
                    ((ClassActivity)getActivity()).pauseSound(true);
                }
                mediaPlayer.start();
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        if(getActivity() instanceof  ClassActivity){
                            ((ClassActivity)getActivity()).pauseSound(false);
                        }
                    }
                });
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
                int id = R.id.word;
                switch (workIndex) {
                    case 0:
                        clockwise(v, id);
                        break;
                    case 1:
                        zoom(v, id);
                        break;
                    case 2:
                        fade(v, id);
                        break;
                    case 3:
                        blink(v, id);
                        break;
                    case 4:
                        move(v, id);
                        break;
                    case 5:
                        slide(v, id);
                        break;
                }


                if(getActivity() instanceof  ClassActivity){
                    ((ClassActivity)getActivity()).pauseSound(true);
                }
                mediaPlayerword.start();
                mediaPlayerword.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        if(getActivity() instanceof  ClassActivity){
                            ((ClassActivity)getActivity()).swipPager(true);
                            ((ClassActivity)getActivity()).pauseSound(false);
                        }
                    }
                });

            }
        });

        return view;
    }

    public void clockwise(View view, int id) {
        ImageView image = (ImageView) view.findViewById(id);
        Animation animation = AnimationUtils.loadAnimation(getActivity(),
                R.anim.myanimation);
        image.startAnimation(animation);
    }

    public void zoom(View view, int id) {
        ImageView image = (ImageView) view.findViewById(id);
        Animation animation1 = AnimationUtils.loadAnimation(getActivity(),
                R.anim.clockwise);
        image.startAnimation(animation1);
    }

    public void fade(View view, int id) {
        ImageView image = (ImageView) view.findViewById(id);
        Animation animation1 =
                AnimationUtils.loadAnimation(getActivity(),
                        R.anim.fade);
        image.startAnimation(animation1);
    }

    public void blink(View view, int id) {
        ImageView image = (ImageView) view.findViewById(id);
        Animation animation1 =
                AnimationUtils.loadAnimation(getActivity(),
                        R.anim.blink);
        image.startAnimation(animation1);
    }

    public void move(View view, int id) {
        ImageView image = (ImageView) view.findViewById(id);
        Animation animation1 =
                AnimationUtils.loadAnimation(getActivity(), R.anim.move);
        image.startAnimation(animation1);
    }

    public void slide(View view, int id) {
        ImageView image = (ImageView) view.findViewById(id);
        Animation animation1 =
                AnimationUtils.loadAnimation(getActivity(), R.anim.slide);
        image.startAnimation(animation1);
    }
}

