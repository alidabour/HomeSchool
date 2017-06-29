package com.almanara.homeschool.student.course.lesson.topic.template;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.almanara.ali.homeschool.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MatchingFragment extends Fragment {
    int countToResult=0;
    Drawable drawable;
    boolean flage;
    ArrayList<ImageView> imageViews = new ArrayList<>();
    ArrayList<ImageView> imageViews_black = new ArrayList<>();
    ArrayList<String> object;
    private final String HOLD = " ,HO##LD,";

    private String layout;


    public MatchingFragment() {
        // Required empty public constructor
    }

    public static MatchingFragment newInstance(String layout) {
        MatchingFragment fragment = new MatchingFragment();
        Bundle args = new Bundle();
        args.putString("layout", layout);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            layout = getArguments().getString("layout");
            object = new ArrayList<>();
            if (layout != null) {
                String[] parms = layout.split("(?<=" + HOLD + ")");
                for (int i = 0; i < parms.length; i++) {
                    Log.v("Matching","Parms :" + parms[i]);
                    parms[i] = parms[i].replaceAll(HOLD, " ");
                    object.add(parms[i]);
                }
            }
            Log.v("Matching","Object size :" + object.size());

//            object = (ArrayList<Uri>) args.getSerializable("ARRAYLIST");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainView = inflater.inflate(R.layout.fragment_matching, container, false);
//        View backgroundimage = (View) mainView.findViewById(R.id.matching);
//        backgroundimage.getBackground().setAlpha(90);

//        Intent intent = getIntent();
//        Bundle args = intent.getBundleExtra("BUNDLE");

//        Log.e(object.size() + "", "onCreate: ");

        LinearLayout part1 = (LinearLayout) mainView.findViewById(R.id.part1);
        LinearLayout part2 = (LinearLayout) mainView.findViewById(R.id.part2);
        LinearLayout part3 = (LinearLayout) mainView.findViewById(R.id.part3);


        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(200, 150);

        lp.setMargins(25, 50, 25, 50);
        for (int i = 0; i < object.size(); i++) {


            ImageView imageView = new ImageView(getActivity());
            ImageView imageView1 = new ImageView(getActivity());
            Glide.with(getActivity()).load(object.get(i)).into(imageView);
            Glide.with(getActivity()).load(object.get(i)).into(imageView1);

//            imageView1.setImageURI(object.get(i));
//            imageView.setImageURI(object.get(i));

            drawable = imageView.getDrawable();
//            Bitmap mBitmap = ((BitmapDrawable) drawable).getBitmap();
//            Bitmap newBitMap = toGrayscale(mBitmap);
//            imageView1.setImageBitmap(newBitMap);
            imageView1.setId(i);
            imageView.setId(i);

            imageView1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT,1.0f));
            imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT,1.0f));

            if (i % 2 == 0) part1.addView(imageView, lp);
            else part3.addView(imageView, lp);

            part2.addView(imageView1, lp);

            imageViews.add(imageView);
            imageViews_black.add(imageView1);

        }


        for (int i = 0; i < imageViews.size(); i++) {
//            imageViews.get(i).setOnClickListener(onClickListener);
            imageViews.get(i).setOnLongClickListener(onClickListener);
            imageViews_black.get(i).setOnDragListener(onDragListener);


        }
        return mainView;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
//    View.OnClickListener onClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            ClipData data = ClipData.newPlainText("", "");
//            View.DragShadowBuilder dragShadowBuilder = new View.DragShadowBuilder(v);
//            v.startDrag(data, dragShadowBuilder, v, 0);
//        }
//    };

    View.OnLongClickListener onClickListener = new View.OnLongClickListener() {

        @Override
        public boolean onLongClick(View v) {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder dragShadowBuilder = new View.DragShadowBuilder(v);
            v.startDrag(data, dragShadowBuilder, v, 0);
            return true;
        }
    };
    View.OnDragListener onDragListener = new View.OnDragListener() {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            int dragEbvent = event.getAction();
            final View view =(View) event.getLocalState();
            switch (dragEbvent) {
                case DragEvent.ACTION_DRAG_STARTED:
                    flage=false;
                    imageViews.get(view.getId()).setVisibility(View.INVISIBLE);
                    break;

                case DragEvent.ACTION_DRAG_ENDED:
                    if(flage){
//                        imageViews_black.get(view.getId()).setImageURI(object.get(view.getId()));
                        Glide.with(getActivity()).load(object.get(view.getId())).into(imageViews_black.get(view.getId()));
                        imageViews.get(view.getId()).setVisibility(View.INVISIBLE);
                    }
                    else {
                        imageViews.get(view.getId()).setVisibility(View.VISIBLE);

                    }
                    break;

                case DragEvent.ACTION_DROP:

                    imageViews.get(view.getId()).setVisibility(View.INVISIBLE);
                    if(v.getId()==view.getId()){
                        flage=true;
                        countToResult++;
//                        imageViews_black.get(view.getId()).setImageURI(object.get(view.getId()));
                        Glide.with(getActivity()).load(object.get(view.getId())).into(imageViews_black.get(view.getId()));
                        imageViews.get(view.getId()).setVisibility(View.INVISIBLE);
                    }
                    else flage=false;
                    if(countToResult==imageViews.size()) {
//                        Intent intent =new Intent(RealTime.this,Result.class);
//                        startActivity(intent);
                    }
                    break;

                default:

                    break;
            }
            return true;
        }
    };



    public Bitmap toGrayscale(Bitmap bmpOriginal)
    {
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();
        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(colorMatrix);
        paint.setColorFilter(f);
        canvas.drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGrayscale;
    }


}
