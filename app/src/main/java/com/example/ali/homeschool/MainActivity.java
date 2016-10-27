package com.example.ali.homeschool;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.icu.util.Currency;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.ali.homeschool.data.Entry.CourseColumns;
import com.example.ali.homeschool.data.Entry.LessonColumns;
import com.example.ali.homeschool.data.Entry.TopicColumns;
import com.example.ali.homeschool.data.Entry.TopicContentColumns;
import com.example.ali.homeschool.data.DataProvider;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    private static final int CURSOR_LOADER_ID = 0;
    Button startTopic ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState==null){
            try {
                Utils.addDefaultsSubjects(getApplicationContext());
                Utils.addCoursesTest(getApplicationContext());
                Utils.addLessonsTest(getApplicationContext());
                Utils.addTopicsTest(getApplicationContext());
                Utils.addTopicsContentsTest(getApplicationContext());
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (OperationApplicationException e) {
                e.printStackTrace();
            }

        }

        startTopic = (Button) findViewById(R.id.startTopic);
        getLoaderManager().initLoader(CURSOR_LOADER_ID, null, this);
        startTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ClassActivity.class);
                startActivity(intent);
            }
        });
       // RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
      //  listView=(ListView) findViewById(R.id.list_view);
        //cursorAdapter=new CategoryCursorAdapter(this, null, new CategoryCursorAdapter.CategoryOnClickHander() {
        //    @Override
          //  public void onClick(String test, CategoryCursorAdapter.ViewHolder vh) {
            //    Log.v("Test",test);
            //}
   //     });
     //   recyclerView.setAdapter(cursorAdapter);
        //listView.setAdapter(cursorAdapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//        });
    }
    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(CURSOR_LOADER_ID, null, this);
    }
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        //Subject Test
        //CursorLoader cursorLoader= new CursorLoader(this, DataProvider.Subject.CONTENT_URI,
        //        new String[]{SubjectColumns._ID,SubjectColumns.SUBJECT_NAME},null,null,null);
        //Course Test
        //CursorLoader cursorLoader = new CursorLoader(this,DataProvider.Course.CONTENT_URI,
        //      new String[]{CourseColumns._ID,CourseColumns.COURSE_NAME},null,null,null);
        //Lesson Test
        //CursorLoader cursorLoader = new CursorLoader(this,DataProvider.Lesson.CONTENT_URI,
        //        new String[]{LessonColumns._ID,LessonColumns.LESSON_NAME},null,null,null);
        //Topic Test
        //CursorLoader cursorLoader = new CursorLoader(this,DataProvider.Topic.CONTENT_URI,
        //        new String[]{TopicColumns._ID,TopicColumns.TOPIC_NAME},null,null,null);
        //TopicContent Test
        CursorLoader cursorLoader = new CursorLoader(this,DataProvider.TopicContent.CONTENT_URI,
                new String[]{TopicContentColumns._ID,TopicContentColumns.CONTENT},null,null,null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
       // cursorAdapter.swapCursor(data);
        data.moveToFirst();
        //Subject Test
        //String test = data.getString(data.getColumnIndex(SubjectColumns.SUBJECT_NAME));
        //Course Test
        //String test = data.getString(data.getColumnIndex(CourseColumns.COURSE_NAME));
        //Lesson Test
        //String test = data.getString(data.getColumnIndex(LessonColumns.LESSON_NAME));
        //Topic Test
        //String test = data.getString(data.getColumnIndex(TopicColumns.TOPIC_NAME));
        //TopicContent Test
        byte[] test = data.getBlob(data.getColumnIndex(TopicContentColumns.CONTENT));
        Log.v("Test"," "+test[0]);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        //cursorAdapter.swapCursor(null);
    }
}
