package com.almanara.homeschool.instructor.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.almanara.homeschool.data.firebase.CourseCreated;
import com.almanara.homeschool.instructor.lesson.InstructorLessonsActivity;
import com.almanara.ali.homeschool.R;
import com.almanara.homeschool.UserModelHelper.FileUploadHelper;
import com.almanara.homeschool.UserModelHelper.UploadFile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InstructorFragment extends Fragment {

    View view;
    String photoUrl = "";
    String coursName;
    String description;
    String subject;
    List<CourseCreated> coursesList;
    RecyclerView coursesRV;
    DatabaseReference db;
    FirebaseUser user;
    CourseCreatedAdapter courseCreatedAdapter;
    InstructorCoursesCardAdapter instructorCoursesCardAdapter;
    private static final int PICK_IMAGE_REQUEST = 235;
    UploadFile uploadFile;
    private Uri filePath;
    CourseCreated globalCourseCreated;
    ProgressBar progressBar;
    ValueEventListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.instructor_fragment, container, false);
        // addCourse = (Button) view.findViewById(R.id.addCourse);
        progressBar = (ProgressBar) view.findViewById(R.id.instructor_progressbas);

        coursesList = new ArrayList<>();
        coursesRV = (RecyclerView) view.findViewById(R.id.courses);
        coursesRV.setHasFixedSize(true);
        coursesRV.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab2);

        Log.v("Enta Get Hna101", "T3alaly");
        FirebaseAuth auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        Log.v("Test", "User Email" + user.getDisplayName());
//        Log.v("Test","User Email"+ user.getProviderId());
        Log.v("Test", "User Email" + user.getUid());
        db = FirebaseDatabase.getInstance().getReference();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.addCourse);
                LayoutInflater li = LayoutInflater.from(getActivity());
                LinearLayout someLayout;
                someLayout = (LinearLayout) li.inflate(R.layout.dialog_add_course, null);

                // Set up the input
                final EditText input = (EditText) someLayout.findViewById(R.id.courseName);
                final EditText descriptions = (EditText) someLayout.findViewById(R.id.descriptionS);
                final EditText subjects = (EditText) someLayout.findViewById(R.id.subjectS);

                TextView gallery = (TextView) someLayout.findViewById(R.id.choosefromGallery);
                gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        openImageActivity();
                    }
                });

//                input.setHint("Text");
//                audioIn.setHint("Audio Link");
//                Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
//                input.setInputType(
//                        InputType.TYPE_CLASS_TEXT );
//                audioIn.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(someLayout);

                // Set up the buttons
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        coursName = input.getText().toString();
                        subject = subjects.getText().toString();
                        description = descriptions.getText().toString();

                        if (photoUrl.isEmpty()) {
                            photoUrl = "https://firebasestorage.googleapis.com/v0/b/dealgamed-f2066.appspot.com/o/images%2Fcourses%2Fphoto_default.png?alt=media&token=a338378b-eb7d-4d65-88ea-a4266fd0c1d5";
                        }
                        String key = db.child("users").child(user.getUid()).child("CreatedCourse")
                                .push().getKey();
                        db.child("users").child(user.getUid()).child("CreatedCourse").child(key)
                                .child("course_id").setValue(key);
                        db.child("users").child(user.getUid()).child("CreatedCourse").child(key)
                                .child("descriptionS").setValue(description);
                        db.child("users").child(user.getUid()).child("CreatedCourse").child(key)
                                .child("name").setValue(coursName);
                        db.child("users").child(user.getUid()).child("CreatedCourse").child(key)
                                .child("photo_url").setValue(photoUrl);
                        db.child("users").child(user.getUid()).child("CreatedCourse").child(key)
                                .child("rate").setValue("5.0");
                        db.child("users").child(user.getUid()).child("CreatedCourse").child(key)
                                .child("subjectS").setValue(subject);
                        db.child("users").child(user.getUid()).child("CreatedCourse").child(key)
                                .child("teacher_id").setValue(user.getUid());
                        db.child("users").child(user.getUid()).child("CreatedCourse").child(key)
                                .child("teacher_name").setValue("Mohamed");

                        db.child("courses").child(key).child("course_id").setValue(key);
                        db.child("courses").child(key).child("descriptionS").setValue(description);
                        db.child("courses").child(key).child("name").setValue(coursName);
                        db.child("courses").child(key).child("photo_url").setValue(photoUrl);
                        db.child("courses").child(key).child("rate").setValue("5.0");
                        db.child("courses").child(key).child("subjectS").setValue(subject);
                        db.child("courses").child(key).child("teacher_id").setValue(user.getUid());
                        db.child("courses").child(key).child("teacher_name").setValue("Mohamed");

                        String lessonKey = db.child("courses").child(key)
                                .child("lessons").push().getKey();
                        db.child("courses").child(key).child("lessons")
                                .child(lessonKey).child("id").setValue(lessonKey);
                        db.child("courses").child(key).child("lessons")
                                .child(lessonKey).child("name").setValue("الدرس الاول");
                        db.child("courses").child(key).child("lessons")
                                .child(lessonKey).child("photo_url").setValue(
                                "https://firebasestorage.googleapis.com/v0/b/dealgamed-f2066.appspot.com/o/images%2Fcourses%2Fphoto_default.png?alt=media&token=a338378b-eb7d-4d65-88ea-a4266fd0c1d5");
                        String topicKey = db.child("courses").child(key)
                                .child("lessons").child(lessonKey).child("topics").push().getKey();
                        db.child("courses").child(key)
                                .child("lessons").child(lessonKey).child("topics").child(topicKey)
                                .child("id").setValue(topicKey);
                        db.child("courses").child(key)
                                .child("lessons").child(lessonKey).child("topics").child(topicKey)
                                .child("layout").setValue(
                                "<RelativeLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\nandroid:id=\"2\" android:layout_weight=\"0\" android:layout_width=\"match_parent\"\nandroid:layout_height=\"match_parent\"><LinearLayout android:layout_centerInParent=\"true\" android:orientation=\"vertical\" android:layout_weight=\"0\" android:id=\"2000\" android:layout_width=\"match_parent\" android:layout_height=\"wrap_content\">ARR4444ANGE <TextView  android:id=\"1\" android:layout_weight=\"0\"  android:text=\"اهلا بيكم\"  android:textColor=\"-11177216\"  android:textAppearance=\"16974324\"  android:layout_width=\"match_parent\"  android:layout_height=\"wrap_content\" />ARR4444ANGE</LinearLayout></RelativeLayout>");
                        db.child("courses").child(key)
                                .child("lessons").child(lessonKey).child("topics").child(topicKey)
                                .child("name").setValue("الحصة الاولى");
                        db.child("courses").child(key)
                                .child("lessons").child(lessonKey).child("topics").child(topicKey)
                                .child("question").setValue("false");
                        db.child("courses").child(key)
                                .child("lessons").child(lessonKey).child("topics").child(topicKey)
                                .child("topicType").setValue("normal");
                        //   view.findViewById(R.id.no_course).setVisibility(View.INVISIBLE);
                      /*  db.child("courses").child(key).child("name").setValue(coursName);
                        db.child("courses").child(key).child("subjectS").setValue(subjectS);
                        db.child("courses").child(key).child("descriptionS").setValue(descriptionS);
                        db.child("courses").child(key).child("rate").setValue("5.0");
                        db.child("courses").child(key).child("teacher").setValue(user.getDisplayName());
                        db.child("courses").child(key).child("teacher_id").setValue(user.getUid());
                        db.child("courses").child(key).child("photo_url").setValue(user.getUid());*/

                    }
                });

                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });
        return view;
    }

    private void openImageActivity() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK)
            Log.e("InstructorPhoto",
                    "Req : " + requestCode + " Res :" + resultCode + " Intent : " + data
                            .getData().toString());

        if (data != null)
            filePath = data.getData();

        String storagePath = "images/coursesPhoto/" + UUID.randomUUID();
        uploadFile = new UploadFile(filePath, getActivity(), new FileUploadHelper() {
            @Override
            public void fileUploaded(String url) {
                photoUrl = url;
            }
        }, storagePath);
    }

    @Override
    public void onStart() {
        super.onStart();
        progressBar.setVisibility(View.VISIBLE);
        listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.v("Test", "OnData");
                coursesList = new ArrayList<CourseCreated>();
                for (DataSnapshot x : dataSnapshot.getChildren()) {
                    CourseCreated courseCreated = x.getValue(CourseCreated.class);
                    coursesList.add(courseCreated);
                    Log.v("Test", "Courses Model " + courseCreated.getName());
                    Log.v("Test", "Courses " + x.toString());
                }
                instructorCoursesCardAdapter = new InstructorCoursesCardAdapter(coursesList,
                        new InstructorCoursesCardAdapter.OnClickHandler() {
                            @Override
                            public void onClick(CourseCreated test) {
                                Log.v("Test", "Open Activity");
                                Intent intent = new Intent(getContext(),
                                        InstructorLessonsActivity.class);
                                intent.putExtra("course", test);
                                startActivity(intent);
                            }
                        }, getContext());

                if (coursesList.size() <= 0) {
                    Log.v("mfesh Course", "toz fek");
                    view.findViewById(R.id.no_course).setVisibility(View.VISIBLE);
                } else {
                    view.findViewById(R.id.no_course).setVisibility(View.GONE);
                }
                coursesRV.setAdapter(instructorCoursesCardAdapter);
                progressBar.setVisibility(View.INVISIBLE);

//                courseCreatedAdapter = new CourseCreatedAdapter(coursesList,
//                        new CourseCreatedAdapter.OnClickHandler() {
//                            @Override
//                            public void onClick(CourseCreated test) {
//                                Log.v("Test", "Open Activity");
//                                Intent intent = new Intent(getContext(),
//                                        InstructorLessonsActivity.class);
//                                intent.putExtra("course", test);
//                                startActivity(intent);
//                            }
//                        },getContext());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        db.child("users").child(user.getUid()).child("CreatedCourse")
                .addValueEventListener(listener);
    }

    @Override
    public void onPause() {
        if (listener != null)
            db.removeEventListener(listener);
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
