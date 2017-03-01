package com.example.ali.homeschool.controller.activities;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ali.homeschool.R;
import com.example.ali.homeschool.adapter.TopicsAdapter;
import com.example.ali.homeschool.data.DataProvider;
import com.example.ali.homeschool.data.Entry.CourseColumns;
import com.example.ali.homeschool.data.Entry.LessonColumns;

import java.util.ArrayList;
import java.util.List;

/*
This is the class which i use to get the description of the course from the click listener
and supposely later on i would use the data base to fetch this data
 */
public class CourseDescriptionActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>  {
    Toolbar toolbar;
    ListView topicsListView;
    RecyclerView topicsRecyclerView;
    TopicsAdapter topicsAdapter;
   // TableLayout topicsTable;
    Intent intent;
    Button enroll;
    private static final int CURSOR_LOADER_ID_DES = 1;
    private static final int CURSOR_LOADER_ID_TOPIC = 2;
    ImageView courseImage;
    TextView courseTeacher;
    TextView courseName;
    RatingBar courseRating;
    TextView courseRatingText;
    TextView courseDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_course_description);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Animals Vol. 1");
        enroll = (Button) findViewById(R.id.enroll);
        courseImage = (ImageView) findViewById(R.id.imageView);
        courseTeacher  = (TextView) findViewById(R.id.textView);
        courseName = (TextView) findViewById(R.id.textView2);
        courseRating = (RatingBar) findViewById(R.id.ratingBar);
        courseRatingText = (TextView) findViewById(R.id.textView3);
        courseDescription = (TextView) findViewById(R.id.textView5);

        setSupportActionBar(toolbar);
        // this line supports the back button to go back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getLoaderManager().initLoader(CURSOR_LOADER_ID_DES, null, (LoaderManager.LoaderCallbacks<Cursor>) this);
        getLoaderManager().initLoader(CURSOR_LOADER_ID_TOPIC, null, (LoaderManager.LoaderCallbacks<Cursor>) this);

        List<String> names = new ArrayList<>();
        names.add("مقدمة");names.add("Topic 2");names.add("Topic 3");names.add("Topic 4");
        names.add("Topic 5");names.add("Topic 6");names.add("Topic 7");names.add("Topic 8");
        names.add("Topic 9");names.add("Topic 10");names.add("Topic 11");names.add("Topic 12");

        topicsRecyclerView = (RecyclerView)findViewById(R.id.listViewDes);
        topicsRecyclerView.setHasFixedSize(false);
        topicsAdapter = new TopicsAdapter(names);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        topicsRecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(topicsRecyclerView.getContext(),layoutManager.getOrientation());
        topicsRecyclerView.addItemDecoration(dividerItemDecoration);



//
//        topicsListView = (ListView) findViewById(R.id.listViewDes);
//        ArrayList<String> items = new ArrayList<String>();
//        items.add("Topic 1");
//        items.add("Topic 1");
//        items.add("Topic 1");
//        items.add("Topic 1");
//        items.add("Topic 1");
//        items.add("Topic 1");
//        items.add("Topic 1");
//        items.add("Topic 1");
//        items.add("Topic 1");
//        items.add("Topic 1");
//
//        ArrayAdapter<String> itemsAdapter =
//                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
//        topicsListView.setAdapter(itemsAdapter);
//      //  topicsTable = (TableLayout) findViewById(R.id.listViewDes);

        intent = getIntent();
        final int type = intent.getIntExtra("type",0);
        enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type == 0){
                    Toast.makeText(CourseDescriptionActivity.this, getResources().getString(R.string.youMustSignIn), Toast.LENGTH_SHORT).show();
                }
                else if (type == 1 ){
                    Toast.makeText(CourseDescriptionActivity.this, getResources().getString(R.string.enrollDone), Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        if(i == CURSOR_LOADER_ID_DES ){
            //Description
            CursorLoader loader = new CursorLoader(getApplicationContext(),DataProvider.Course.CONTENT_URI,
                    new String[]{CourseColumns._ID,CourseColumns.GLOBAL_ID,CourseColumns.COURSE_DES
                            ,CourseColumns.COURSE_IMG,CourseColumns.COURSE_NAME,CourseColumns.COURSE_RATINGS,
                    CourseColumns.COURSE_TEACHER}
            ,null,null,null) ;
            return loader;
        }else if(i == CURSOR_LOADER_ID_TOPIC){
            //Topics
            CursorLoader loader = new CursorLoader(getApplicationContext(), DataProvider.Lesson.CONTENT_URI,
                    new String[]{LessonColumns._ID,LessonColumns.LESSON_NAME,LessonColumns.LESSON_NUMBER}
            ,null,null,null);
            return loader;
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if(loader.getId()==CURSOR_LOADER_ID_DES){
            Log.v("Test","Description Count :" + cursor.getCount());
            cursor.moveToFirst();
            courseTeacher.setText(cursor.getString(cursor.getColumnIndex(CourseColumns.COURSE_TEACHER)));
            courseName.setText(cursor.getString(cursor.getColumnIndex(CourseColumns.COURSE_NAME)));
            courseRating.setProgress(cursor.getInt(cursor.getColumnIndex(CourseColumns.COURSE_RATINGS)));
//            courseRatingText.setText(cursor.getInt(cursor.getColumnIndex(CourseColumns.COURSE_RATINGS))+
//                    getString(R.string.ratings));
            courseRatingText.setText(cursor.getInt(cursor.getColumnIndex(CourseColumns.COURSE_RATINGS))
                    +" Ratings");

            courseDescription.setText(cursor.getString(cursor.getColumnIndex(CourseColumns.COURSE_DES)));

        } else if(loader.getId() == CURSOR_LOADER_ID_TOPIC){
            Log.v("Test","Topics Count :"+ cursor.getCount());
            topicsAdapter.swapCursor(cursor);
            topicsRecyclerView.setAdapter(topicsAdapter);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
