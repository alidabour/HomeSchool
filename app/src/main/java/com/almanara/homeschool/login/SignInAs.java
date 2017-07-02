package com.almanara.homeschool.login;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.almanara.ali.homeschool.R;
import com.almanara.homeschool.IndicatorViewPagerAdapter;
import com.almanara.homeschool.controller.activities.Home;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by lenovo on 30/11/2016.
 */
public class SignInAs extends AppCompatActivity {

    ViewPager pager;


    ViewPager pagerIndicator;
    int [] colors = new int []{R.color.instructor , R.color.parent , R.color.splash};
    ArrayList<Integer> indicators = new ArrayList<>();
    boolean isArabic= false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sign_in_as);
        if(!Locale.getDefault().getLanguage().equals("en")){
            isArabic = true;
        }
        //ImageView imageView = (ImageView) findViewById(R.id.imageView);
        pager = (ViewPager) findViewById(R.id.viewPager);


        pagerIndicator = (ViewPager) findViewById(R.id.circlePager);
        Log.v("pager ", pager.toString());
        ArrayList<Home> res = new ArrayList<>();

        if(isArabic){
            Home home = new Home();
            home.setImage(R.drawable.parents_icon);
            home.setName(getString(R.string.Parents));
            home.setSlogan(getString(R.string.Efficent));
            home.setBackGround(R.drawable.parent_background);
            res.add(home);
            home = new Home();
            home.setImage(R.drawable.instructor_icon);
            home.setName(getString(R.string.Instructor));
            home.setSlogan(getString(R.string.Smart));
            home.setBackGround(R.drawable.instructor_background);
            res.add(home);
            home = new Home();
            home.setImage(R.drawable.student_icon);
            home.setName(getString(R.string.Student));
            home.setSlogan(getString(R.string.Easy));
            home.setBackGround(R.drawable.student_background);
            res.add(home);
        }else {
            Home home = new Home();
            home.setImage(R.drawable.student_icon);
            home.setName(getString(R.string.Student));
            home.setSlogan(getString(R.string.Easy));
            home.setBackGround(R.drawable.student_background);
            res.add(home);
            home = new Home();
            home.setImage(R.drawable.parents_icon);
            home.setName(getString(R.string.Parents));
            home.setSlogan(getString(R.string.Efficent));
            home.setBackGround(R.drawable.parent_background);
            res.add(home);
            home = new Home();
            home.setImage(R.drawable.instructor_icon);
            home.setName(getString(R.string.Instructor));
            home.setSlogan(getString(R.string.Smart));
            home.setBackGround(R.drawable.instructor_background);
            res.add(home);
        }

        SignInAsAdapter signInAsAdapter = new SignInAsAdapter(this, res);
        pager.setAdapter(signInAsAdapter);

        indicators.add(R.drawable.empty);
        indicators.add(R.drawable.selected);
        IndicatorViewPagerAdapter indicatorViewPagerAdapter = new IndicatorViewPagerAdapter(getApplicationContext(),indicators,signInAsAdapter.getCount(),isArabic);
        pagerIndicator.setAdapter(indicatorViewPagerAdapter);
        if(isArabic){
            pager.setCurrentItem(signInAsAdapter.getCount() - 1);
            pagerIndicator.setCurrentItem(indicatorViewPagerAdapter.getCount() - 1);
        }
//        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
//        indicator.setViewPager(pager);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {Window window = getWindow();
                    window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    window.setStatusBarColor(getResources().getColor(colors[position]));
                }
            }

            @Override
            public void onPageSelected(int position) {
                pagerIndicator.setCurrentItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        pagerIndicator.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                pager.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
//        final ImageButton previous = (ImageButton) findViewById(R.id.previous);
//        final ImageButton next = (ImageButton) findViewById(R.id.next);
//        next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                previous.setEnabled(true);
////                previous.setSelected(true);
////                int current =pager.getCurrentItem();
////                if(current == pager.getChildCount()){
////                    next.setEnabled(false);
////                    next.setSelected(false);
////                }else {
////                    next.setEnabled(true);
////                    next.setSelected(true);
////
////                }
//                pager.setCurrentItem(getItem(+1), true); //getItem(-1) for previous
//
//            }
//        });
//        previous.setEnabled(false);
//        previous.setVisibility(View.GONE);
//        previous.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                next.setEnabled(true);
////                next.setSelected(true);
////                int current =pager.getCurrentItem();
////                if(current == 0){
////                    previous.setEnabled(false);
////                    previous.setSelected(false);
////                }else {
////                    previous.setEnabled(true);
////                    previous.setSelected(true);
////                }
//                pager.setCurrentItem(getItem(-1), true); //getItem(-1) for previous
//            }
//        });
//        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset,
//                                       int positionOffsetPixels) {
////                Toast.makeText(SignInAs.this, position +"/"+positionOffset, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onPageSelected(int position) {
////                Toast.makeText(SignInAs.this, "Count: "+pager.getChildCount(), Toast.LENGTH_SHORT).show();
//                if (position == 2) {
//                    next.setEnabled(false);
//                    next.setVisibility(View.GONE);
//                } else {
//                    next.setEnabled(true);
//                    next.setVisibility(View.VISIBLE);
//
//                }
//                if (position == 0) {
//                    previous.setEnabled(false);
//                    previous.setVisibility(View.GONE);
//                } else {
//                    previous.setEnabled(true);
//                    previous.setVisibility(View.VISIBLE);
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//        BitmapFactory.Options o = new BitmapFactory.Options();
//        o.inTargetDensity = DisplayMetrics.DENSITY_DEFAULT;
//        Bitmap bmp = BitmapFactory.decodeResource(getApplicationContext().getResources(),
//                R.drawable.arrow_2, o);
//        int w = bmp.getWidth();
//        int h = bmp.getHeight();
//        Bitmap bmp2 = BitmapFactory.decodeResource(getApplicationContext().getResources(),
//                R.drawable.arrow_1, o);
//        int w2 = bmp2.getWidth();
//        int h2 = bmp2.getHeight();
//        Glide.with(getApplicationContext()).load(R.drawable.arrow_2).override(w,h).into(next);
//        Glide.with(getApplicationContext()).load(R.drawable.arrow_1).override(w2,h2).into(previous);

    }

    private int getItem(int i) {
        return pager.getCurrentItem() + i;
    }

    @Override
    public void onBackPressed() {
        ActivityCompat.finishAffinity(SignInAs.this);
        finish();
//        super.onBackPressed();
    }
    //
//    @Override
//    public void onBackPressed() {
////        new AlertDialog.Builder(this)
////                .setIcon(android.R.drawable.ic_dialog_alert)
////                .setTitle("Closing Activity")
////                .setMessage("Are you sure you want to close this activity?")
////                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
////                    @Override
////                    public void onClick(DialogInterface dialog, int which) {
////                        ActivityCompat.finishAffinity(SignInAs.this);
////                        finish();
////                    }
////
////                })
////                .setNegativeButton("No", null)
////                .show();
//      /*  Intent intent = new Intent(SignInAs.this, MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//        overridePendingTransition(R.anim.left_to_right,R.anim.right_to_left);*/
//    }
    public void recreateActivity() {
        Intent intent = getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
}
