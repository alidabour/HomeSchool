package com.almanara.homeschool.student.course.lesson.topic.template;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.almanara.ali.homeschool.R;
import com.almanara.homeschool.RotateTransformation;
import com.almanara.homeschool.student.course.lesson.topic.ClassActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.almanara.ali.homeschool.R.id.part3;

public class MatchingFragment extends Fragment {
    int countToResult = 0;
    Drawable drawable;
    boolean flage;
    ArrayList<ImageView> matchImageList = new ArrayList<>();
    ArrayList<ImageView> blackMatchList = new ArrayList<>();
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
                    Log.v("Matching", "Parms :" + parms[i]);
                    parms[i] = parms[i].replaceAll(HOLD, " ");
                    object.add(parms[i]);
                }
            }
            Log.v("Matching", "Object size :" + object.size());

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
        //   LinearLayout part3 = (LinearLayout) mainView.findViewById(R.id.part3);


        LinearLayout part1 = (LinearLayout) mainView.findViewById(R.id.part1);
        LinearLayout part2 = (LinearLayout) mainView.findViewById(R.id.part2);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,1.0f);
        lp.setMargins(25, 50, 25, 50);


        for (int i = 0; i < object.size(); i++) {

            ImageView matchImageView = new ImageView(getActivity());

          matchImageView.setAdjustViewBounds(true);
            matchImageView.setLayoutParams(
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));

            final ImageView blackImageView = new ImageView(getActivity());
            Glide.with(getActivity()).load(object.get(i))
                    .transform(new RotateTransformation(getActivity(),5.0f))
                    .into(matchImageView);
            matchImageView.setLayoutParams(
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
//            Glide.with(getActivity()).load(object.get(i)).into(blackImageView);
            Glide.with(getActivity())
                    .load(object.get(i))
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>(100, 100) {
                        @Override
                        public void onResourceReady(Bitmap resource,
                                                    GlideAnimation glideAnimation) {
                            resource = toGrayscale(resource);
                            blackImageView.setImageBitmap(resource); // Possibly runOnUiThread()
                            blackImageView.setLayoutParams(
                                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                            ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                        }
                    });
//            blackImageView.setAdjustViewBounds(true);
//            blackImageView.setImageURI(object.get(i));
//            matchImageView.setImageURI(object.get(i));

            drawable = matchImageView.getDrawable();
//            Bitmap mBitmap = ((BitmapDrawable) drawable).getBitmap();
//            Bitmap newBitMap = toGrayscale(mBitmap);
//            blackImageView.setImageBitmap(newBitMap);
            blackImageView.setId(i);
            matchImageView.setId(i);

            blackImageView.setLayoutParams(
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));


            //if (i % 2 == 0)
          part1.addView(matchImageView, lp);
            //else part3.addView(matchImageView, lp);
            part2.addView(blackImageView, lp);

            matchImageList.add(matchImageView);
            blackMatchList.add(blackImageView);

        }


        for (int i = 0; i < matchImageList.size(); i++) {
//            matchImageList.get(i).setOnClickListener(onClickListener);
            matchImageList.get(i).setOnLongClickListener(onClickListener);
            blackMatchList.get(i).setOnDragListener(onDragListener);


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
            final View view = (View) event.getLocalState();

            switch (dragEbvent) {
                case DragEvent.ACTION_DRAG_STARTED:
                    flage = false;
                    matchImageList.get(view.getId()).setVisibility(View.INVISIBLE);
                    break;

                case DragEvent.ACTION_DRAG_ENDED:
                    if (flage) {
//                        blackMatchList.get(view.getId()).setImageURI(object.get(view.getId()));
                        Glide.with(getActivity()).load(object.get(view.getId())).into(
                                blackMatchList.get(view.getId()));
                        matchImageList.get(view.getId()).setVisibility(View.INVISIBLE);
                    } else {
                        matchImageList.get(view.getId()).setVisibility(View.VISIBLE);

                    }
                    break;

                case DragEvent.ACTION_DROP:

                    matchImageList.get(view.getId()).setVisibility(View.INVISIBLE);
                    if (v.getId() == view.getId()) {
                        flage = true;
                        countToResult++;
//                        blackMatchList.get(view.getId()).setImageURI(object.get(view.getId()));
                        Glide.with(getActivity()).load(object.get(view.getId())).into(
                                blackMatchList.get(view.getId()));
                        matchImageList.get(view.getId()).setVisibility(View.INVISIBLE);
                    } else flage = false;
                    if (countToResult == matchImageList.size()) {
//                        Intent intent =new Intent(RealTime.this,Result.class);
//                        startActivity(intent);
                        if(getActivity() instanceof ClassActivity){
                            ((ClassActivity)getActivity()).onAnswer(true);

                        }
                    }
                    break;

                default:

                    break;
            }
            return true;
        }
    };


    public Bitmap toGrayscale(Bitmap bmpOriginal) {
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
