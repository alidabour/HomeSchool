package com.example.ali.homeschool.parent.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.ali.homeschool.R;
import com.example.ali.homeschool.adapter.ChildrenAdapter;
import com.example.ali.homeschool.childProgress.ChildCourses;
import com.example.ali.homeschool.controller.activities.StudentFeaturedCourses;
import com.example.ali.homeschool.data.CategoryInformation;
import com.example.ali.homeschool.data.firebase.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
     */
    public class ParentActivityFragment extends Fragment{
    ChildrenAdapter childrenAdapter;
    View view;
    Button addChild;
    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference db;
    String emailText;
    ChildsAdapter childAdapter;
    RecyclerView parentRecycleView;
    ProgressBar progressBar ;
    ValueEventListener listener;
    ValueEventListener queryListener;

    public ParentActivityFragment() {
    }

    List<CategoryInformation> children;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_parent2, container, false);
        progressBar=(ProgressBar)view.findViewById(R.id.parent_progressbar);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Enter Email");
                // Set up the input
                final EditText input = new EditText(getActivity());
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                input.setHint("Email");
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        emailText = input.getText().toString();


                        listener=  new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for (DataSnapshot d : dataSnapshot.getChildren()) {
                                            Users userHS = new Users();
                                            for (DataSnapshot x : dataSnapshot.getChildren()) {
                                                Log.v("Test", "X ______" + x.toString());
                                                userHS = x.getValue(Users.class);
                                                Log.v("Test", "USer :" + userHS);
                                                Log.v("Test", "User" + userHS.getPhoto());
                                            }

                                            Log.v("Test", "Data" + d.toString());
                                            Log.v("Test", "Key" + d.getKey());
                                            String childKey = d.getKey();
                                            Log.v("Test", "userid" + childKey);
                                            String key = db.child("users").child(user.getUid())
                                                    .child("childs").push().getKey();
                                            db.child("users").child(user.getUid()).child("childs")
                                                    .child(childKey).child("id")
                                                    .setValue(childKey);
                                            db.child("users").child(user.getUid()).child("childs")
                                                    .child(childKey).child("name")
                                                    .setValue(userHS.getName());
                                            db.child("users").child(user.getUid()).child("childs")
                                                    .child(childKey).child("photo")
                                                    .setValue(userHS.getPhoto());

                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                    }
                                };
                        db.child("users").orderByChild("email").equalTo(emailText).addValueEventListener(listener);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();


//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
        db = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        parentRecycleView = (RecyclerView) view
                .findViewById(R.id.category_recyclerView);

        return view;
    }
    @Override
    public void onPause(){
        if (listener != null)
            db.removeEventListener(listener);
        if (queryListener!= null)
            db.removeEventListener(queryListener);
        super.onPause();
    }
    @Override
    public void onStart() {
        super.onStart();
        progressBar.setVisibility(View.VISIBLE);
        queryListener=    new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.v("Test","OnDataChanged");
                        final List<ChildModel> childModels = new ArrayList<ChildModel>();
                        for(DataSnapshot ds:dataSnapshot.getChildren()){
                            childModels.add(ds.getValue(ChildModel.class));
                            Log.v("Test","NAme " +ds.getValue(ChildModel.class).toString());
                            Log.v("Test","NAme " +ds.getValue(ChildModel.class).getPhoto());
                        }
                        childAdapter = new ChildsAdapter(childModels,getActivity() ,
                                new ChildsAdapter.OnClickHandler() {
                                    @Override
                                    public void onClick(final ChildModel test) {
                                        Log.v("Test",test.toString());

                                        CharSequence colors[] = new CharSequence[] {"Enroll My Child into Course", "View This Child's Progress"};

                                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                        builder.setTitle("Choose an Option");
                                        builder.setItems(colors, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int i) {
                                                if(i==0){
                                                    Intent intent = new Intent(getContext(),StudentFeaturedCourses.class);
                                                    Log.v("Test","Child id"+test.getId());

                                                    intent.putExtra("childModel",test);
                                                    intent.putExtra("userid",test.getId());
                                                    startActivity(intent);
                                                }
                                                else {
                                                    Intent intent = new Intent(getContext(), ChildCourses.class);
                                                    Log.v("Test","Child id"+test.getId());

                                                    intent.putExtra("childModel",test);
                                                    intent.putExtra("userid",test.getId());
                                                    startActivity(intent);
                                                }


                                            }
                                        });
                                        builder.show();


                                    }
                                });
                        parentRecycleView.setHasFixedSize(true);
                        parentRecycleView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                        parentRecycleView.setAdapter(childAdapter);
                        progressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                };
        db.child("users").child(user.getUid()).child("childs").addValueEventListener(queryListener);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
