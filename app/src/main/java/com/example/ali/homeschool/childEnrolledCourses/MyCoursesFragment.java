package com.example.ali.homeschool.childEnrolledCourses;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ali.homeschool.InstructorHome.CourseCreated;
import com.example.ali.homeschool.R;
import com.example.ali.homeschool.childClass.ClassActivity;
import com.example.ali.homeschool.childProgress.EnrolledCourseModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyCoursesFragment extends Fragment {
    EnrolledCoursesAdapter1 enrolledCoursesAdapter;
    RecyclerView enrolledRecyclerView;
    private static final int CURSOR_LOADER_ID = 1;
    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference db;
    List<Courses2> enrolledCoursesList;
    List<CourseCreated> coursesNames;

    public MyCoursesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        db = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if(container!=null){
            container.removeAllViews();
        }
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_courses_fragmetnt, container, false);


        enrolledRecyclerView = (RecyclerView) view.findViewById(R.id.enrolledCourses);
        enrolledRecyclerView.setHasFixedSize(true);
        LinearLayoutManager categoryLayoutManger = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        enrolledRecyclerView.setLayoutManager(categoryLayoutManger);
        Log.v("Test","MyCoursesFragment");
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.v("REBE","User Email :" + user.getEmail());
        Log.v("REBE","User Uid :" + user.getUid());

        db.child("users").child(user.getUid()).child("EnrolledCourses")
                .addValueEventListener(new ValueEventListener() {
//                    enrolledCourses = new ArrayList;

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }

                    @Override
                    public void onDataChange(final DataSnapshot dataSnapshot) {
                        enrolledCoursesList = new ArrayList<Courses2>();
                        coursesNames = new ArrayList<CourseCreated>();
                        for (DataSnapshot s : dataSnapshot.getChildren()) {
                            Log.v("REBE", "Inside " + s);
                            EnrolledCourseModel c = s.getValue(EnrolledCourseModel.class);
                            Log.v("REBE", "Outside " + c);

                            db.child("courses").child(c.getCourse_id())
                                    .addValueEventListener(new ValueEventListener() {
                                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                        @Override
                                        public void onDataChange(DataSnapshot inside) {
                                            Log.v("Fire", "Inside " + inside.toString());
                                            Courses courses = new Courses();
                                            CourseCreated courseCreated = new CourseCreated();
                                                for (DataSnapshot x : inside.getChildren()){
                                                    Log.v("Test","Inside Childs "+x.toString() );
                                                    if(Objects.equals(x.getKey(), "course_id")){
                                                        Log.v("Test","course id " +x.getValue());
//                                                        courses.setCourse_id(x.getValue().toString());
                                                        courseCreated.setCourse_id(x.getValue().toString());
                                                    }else if(Objects.equals(x.getKey(), "name")){
                                                        Log.v("Test","Des " +x.getValue());
                                                        courseCreated.setName(x.getValue().toString());
//                                                        courses.setDescription(x.getValue().toString());
                                                    }

                                                }
//                                                Courses2 course2=null ;
//                                                course2= inside.getValue(Courses2.class);
    //                                            Log.e("onDataChange: ", course + "");
//                                                enrolledCoursesList.add(course2);
                                            coursesNames.add(courseCreated);
                                                Log.v("Test", "Enrolled Size Updated:" + enrolledCoursesList.size());
                                                EnrolledCoursesAdapter1 enrolledCoursesAdapter1 = new EnrolledCoursesAdapter1(
                                                        coursesNames,
                                                        new EnrolledCoursesAdapter1.OnClickHandler() {
                                                            @Override
                                                            public void onClick(CourseCreated test) {
                                                                Intent intent = new Intent(getActivity(),
                                                                        ClassActivity.class);
                                                                intent.putExtra("course",test);
//                                                                Log.v("Test","Course Lesson : " + test.getLessons().size());
//                                                                Set<Map.Entry<String, LessonModel>> entry =test.getLessons().entrySet();
//                                                                Log.v("Test","Lessons entry.size());" +entry.size());
                                                                startActivity(intent);
                                                            }
                                                        });
                                                Log.v("Test", "Enrolled Size New:" + enrolledCoursesList.size());
                                            enrolledRecyclerView.setAdapter(enrolledCoursesAdapter1);
                                        }
                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                        }
                                    });
                        }
                        // [END_EXCLUDE]
                    }

                });
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}