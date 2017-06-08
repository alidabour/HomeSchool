package com.example.ali.homeschool.InstructorHome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.ali.homeschool.ParentHome.ChildModel;
import com.example.ali.homeschool.ParentHome.ChildsAdapter;
import com.example.ali.homeschool.R;
import com.example.ali.homeschool.childProgress.ChildCourses;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Classroom extends AppCompatActivity {
    DatabaseReference db;
    String courseId;
    Boolean flag=false;
    ChildModel childModel;
    ChildsAdapter childAdapter;
    RecyclerView studentsRecycleView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom);
        db = FirebaseDatabase.getInstance().getReference();
        studentsRecycleView = (RecyclerView) findViewById(R.id.classRoomRecycleView);

        if (getIntent().hasExtra("courseid")){
            courseId = getIntent().getStringExtra("courseid");
            Log.e("courseid: ",courseId );
        }

    }


    @Override
    protected void onStart() {
        super.onStart();
        db.child("users").orderByValue().addValueEventListener(
                new ValueEventListener() {
                    ArrayList<ChildModel> childModelList;
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        childModelList = new ArrayList<>();
                        for (DataSnapshot d : dataSnapshot.getChildren()) {
                            for (DataSnapshot d1 : d.getChildren()) {
                                for (DataSnapshot d2 : d1.getChildren()) {
                                    for (DataSnapshot d3 : d2.getChildren()) {
                                        if(d3.getValue().equals(courseId))
                                        {
                                            flag=true;
                                            break;
                                        }
                                    }
                                }
                            }
                            if(flag)
                            {
                                flag =false;
                                Log.e("dataSnapShot",d.getValue()+"");
                                childModel = d.getValue(ChildModel.class);
                                childModelList.add(childModel);
                            }
                        }
                        childAdapter = new ChildsAdapter(childModelList,
                                new ChildsAdapter.OnClickHandler() {
                                    @Override
                                    public void onClick(ChildModel test) {
                                        Log.v("Test",test.toString());
                                        Intent intent = new Intent(Classroom.this,ChildCourses.class);
                                        intent.putExtra("childModel",test);
                                        startActivity(intent);
                                    }
                                });
                        studentsRecycleView.setHasFixedSize(true);
                        studentsRecycleView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
                        studentsRecycleView.setAdapter(childAdapter);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}
