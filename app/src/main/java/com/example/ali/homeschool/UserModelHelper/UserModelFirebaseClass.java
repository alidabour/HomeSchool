package com.example.ali.homeschool.UserModelHelper;

import com.example.ali.homeschool.data.firebase.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by hossam on 02/06/17.
 */

public class UserModelFirebaseClass {
    FirebaseAuth mAuth;
    FirebaseUser user;
    UserModel userModel;
    DatabaseReference databaseReference;
    UserModelFirebase userModelFirebase;

    public UserModelFirebaseClass(UserModelFirebase userModelFirebase) {
        this.userModelFirebase=userModelFirebase;
        dataRetrieved();
    }


    public void dataRetrieved(){
        create();


        databaseReference.child("users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userModel = dataSnapshot.getValue(UserModel.class);
                if(userModel.getPhoto()==null){
                    userModel.setPhoto("photoid");
                }
                userModelFirebase.dataRetrieved(userModel);
                //        Log.e("onDataChange: ",userModel.getPhoto());
               // UserName.setText(userModel.getName());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
 /*   public void dataRetrieved1(){


        databaseReference.child("users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userModel = dataSnapshot.getValue(UserModel.class);
                userModelFirebase.dataRetrieved(userModel);
                //        Log.e("onDataChange: ",userModel.getPhoto());
                // UserName.setText(userModel.getName());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }*/
    private void create(){
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
    }
}
