package com.example.ali.homeschool.InstructorHome;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ali.homeschool.CircleTransform;
import com.example.ali.homeschool.R;
import com.example.ali.homeschool.UserModelHelper.UploadFile;
import com.example.ali.homeschool.UserModelHelper.UserModelFirebase;
import com.example.ali.homeschool.UserModelHelper.UserModelFirebaseClass;
import com.example.ali.homeschool.controller.activities.MainActivity;
import com.example.ali.homeschool.data.firebase.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InstructorActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    AppBarLayout appBarLayout;
    FirebaseAuth mAuth;
    FirebaseUser user;
    UserModel userModel;
    ImageView userPhotoId;
    TextView UserName;
    DatabaseReference databaseReference;
    ViewPager mViewPager;
    UploadFile uploadFile;
    private Uri filePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instructor_drawer_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout2);

        //setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout2);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view2);
        navigationView.setNavigationItemSelectedListener(this);

        userPhotoId=(ImageView) navigationView.getHeaderView(0).findViewById(R.id.photoid);
        UserName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.navigationTextName);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();

        UserModelFirebaseClass userModelFirebaseClass = new UserModelFirebaseClass(new UserModelFirebase() {
            @Override
            public void dataRetrieved(UserModel userModel) {

                Glide.with(getApplicationContext()).load(userModel.getPhoto()).transform(new CircleTransform(getApplicationContext())).into(userPhotoId);
                UserName.setText(userModel.getName());
            }
        });

    }


    //    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

            // Commit the transaction
         if (id == R.id.Support) {

             Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
             myWebLink.setData(Uri.parse("https://goo.gl/forms/QAK8BHZmvjSYk1fA3"));
             startActivity(myWebLink);

         } else if (id == R.id.signout) {
            mAuth.getInstance().signOut();
            signOut101();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout2);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    void signOut101() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("finish", true);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
        startActivity(intent);
        finish();
    }
}
