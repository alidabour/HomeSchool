package com.almanara.homeschool.student.course.lesson;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.almanara.homeschool.data.firebase.LessonModel;
import com.bumptech.glide.Glide;
import com.almanara.homeschool.data.firebase.CourseCreated;
import com.almanara.ali.homeschool.R;
import com.almanara.homeschool.student.course.lesson.topic.ClassActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LessonActivity extends AppCompatActivity {

    List<CourseCreated> enrolledLessonList;
    RecyclerView enrolledRecyclerView;
    Toolbar toolbar;
    CourseCreated course;
    ValueEventListener listener;
    List<LessonModel> lessonModelList;
    DatabaseReference db;
    ValueEventListener queryListener;
    GridLayoutManager gridLayoutManager;
    RelativeLayout viewRoot;
    RelativeLayout line;
    int cx;
    int cy;
    int finalRadius;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        setContentView(R.layout.activity_lesson);
        viewRoot = (RelativeLayout) findViewById(R.id.viewRoot);
        toolbar = (Toolbar) findViewById(R.id.toolbar1);

        line = (RelativeLayout) findViewById(R.id.line);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)
                line.getLayoutParams();
        params.setMargins(-1 * line.getWidth(), 0, 0, 0);
        line.setLayoutParams(params);

        enrolledRecyclerView = (RecyclerView) findViewById(R.id.lessonsRV2);
        enrolledRecyclerView.setHasFixedSize(true);
//        LinearLayoutManager categoryLayoutManger = new LinearLayoutManager(getApplicationContext(),
//                LinearLayoutManager.VERTICAL, false);
        gridLayoutManager = new GridLayoutManager(this, 2);
        enrolledRecyclerView.setLayoutManager(gridLayoutManager);

        Log.e("Test", "myLessonActivity");
        db = FirebaseDatabase.getInstance().getReference();
        lessonModelList = new ArrayList<LessonModel>();

        Intent intent = getIntent();
        course = intent.getParcelableExtra("course");
        ((TextView) findViewById(R.id.course_name)).setText(course.getName());
        Glide.with(getApplicationContext()).load(course.getPhoto_url())
                .override(25,25).dontTransform().into((ImageView)findViewById(R.id.course_image));
        Explode enterTransition = new Explode();
        enterTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
        getWindow().setEnterTransition(enterTransition);
        Log.e("courseinLessonActivity", course.toString());
        getWindow().setAllowEnterTransitionOverlap(false);
        cx = (viewRoot.getLeft() + viewRoot.getRight()) / 2;
        cy = (viewRoot.getTop() + viewRoot.getBottom()) / 2;
        finalRadius = Math.max(viewRoot.getWidth(), viewRoot.getHeight());

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            startAnimation();
//        }


    }
//
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    void startAnimation() {
//        Animator anim = ViewAnimationUtils
//                .createCircularReveal(viewRoot, cx, cy, 0, finalRadius);
//        final int color = 0x0000FFFF;
//        final Drawable drawable = new ColorDrawable(color);
//        viewRoot.setForeground(drawable);
////                    viewRoot.setBackgroundColor(Color.parseColor("#ff0000"));
//        anim.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//            }
//        });
//        anim.start();
//
//    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            startAnimation();
//        }
        return super.onCreateView(name, context, attrs);
    }

    @Override
    protected void onPause() {
        if (listener != null)
            db.removeEventListener(listener);
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        Animation animation = new TranslateAnimation(0, 200,0, 0);
//        animation.setDuration(3000);
//        animation.setFillAfter(true);
//        line.startAnimation(animation);
        ObjectAnimator animX = ObjectAnimator.ofFloat(line,
                View.TRANSLATION_X, -1 * line.getWidth(), 0);
        animX.setDuration(3000);
        animX.start();
        listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.v("NNLessonNN", "Datasnapshot " + dataSnapshot);
                lessonModelList = new ArrayList<LessonModel>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    LessonModel lessonModel = dataSnapshot1.getValue(LessonModel.class);
//                    Log.v("Lesson ", lessonModel.getName());
                    lessonModelList.add(lessonModel);
                }

                final StudentLessonAdapter studentLessonAdapter = new StudentLessonAdapter(
                        lessonModelList,
                        new StudentLessonAdapter.OnClickHandler() {
                            @Override
                            public void onClick(LessonModel test) {
                                Intent intent = new Intent(getApplicationContext(),
                                        ClassActivity.class);
                                intent.putExtra("courseId", course.getCourse_id());
                                intent.putExtra("lessonid", test.getId());

                                startActivity(intent);
                            }
                        }, LessonActivity.this, course.getCourse_id());
                studentLessonAdapter.setCourseId(course.getCourse_id());
                enrolledRecyclerView.setAdapter(studentLessonAdapter);
                gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        Log.v("getSpanSize", " positon" + position);
                        Log.v("getSpanSize", " isOneRow" + studentLessonAdapter.isOneRow(position));

                        return studentLessonAdapter.isOneRow(position) ? 2 : 1;
                    }
                });


                enrolledRecyclerView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {


                        return false;
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        };
        Log.v("Lesson", "Course id : " + course.getCourse_id());
        db.child("courses").child(course.getCourse_id()).child("lessons")
                .addValueEventListener(listener);

    }
}
