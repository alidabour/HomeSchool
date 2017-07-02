package com.almanara.homeschool.instructor.lesson;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.almanara.ali.homeschool.R;
import com.almanara.homeschool.UserModelHelper.FileUploadHelper;
import com.almanara.homeschool.UserModelHelper.UploadFile;
import com.almanara.homeschool.data.firebase.CourseCreated;
import com.almanara.homeschool.data.firebase.LessonModel;
import com.almanara.homeschool.instructor.topic.InstructorTopicActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

public class InstructorLessonsActivity extends AppCompatActivity {
    RecyclerView lessonsRV;
    String m_Text = "";
    DatabaseReference db;
    CourseCreated courseCreated;
    LessonModel lessonModel;
    Toolbar toolbar;
    String courseID;
    TextView noLesson;
    ArrayList<LessonModel> lessonModelList;
    ValueEventListener listener;
    final int PICK_IMAGE_REQUEST = 234;
    String photo_url = "";
    ItemTouchHelper.SimpleCallback simpleItemTouchCallback;
    InstructorLessonAdapter instructorLessonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Intent intentt = new Intent(this, InstructorTopicCreationActivity.class);
//        intentt.putExtra("courseId","dsdssd");
//        startActivity(intentt);
//        finish();
        setContentView(R.layout.activity_instructor_lessons);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab3);
        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        InstructorLessonsActivity.this);
                builder.setTitle(R.string.lesson_title);
                LayoutInflater li = LayoutInflater.from(InstructorLessonsActivity.this);
                LinearLayout someLayout = (LinearLayout) li.inflate(R.layout.lesson_dialog, null);
                TextView gallery = (TextView) someLayout.findViewById(R.id.choosefromGallery);
                // Set up the input
                final EditText input = (EditText) someLayout.findViewById(R.id.lessonName);
//                final EditText input = (EditText)someLayout.findViewById(R.id.lesson_image);

                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(
                        InputType.TYPE_CLASS_TEXT);
                builder.setView(someLayout);

                // Set up the buttons
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
//                        Map<String,String> lesson = new HashMap<String, String>();
                        String key = db.child("courses").child(courseCreated.getCourse_id())
                                .child("lessons").push().getKey();
                        if (photo_url.isEmpty()) {
                            photo_url = "https://firebasestorage.googleapis.com/v0/b/dealgamed-f2066.appspot.com/o/images%2Fcourses%2Fphoto_default.png?alt=media&token=a338378b-eb7d-4d65-88ea-a4266fd0c1d5";
                        }
//                        lesson.put("id",key);
//                        lesson.put("name",m_Text);
                        db.child("courses").child(courseCreated.getCourse_id()).child("lessons")
                                .child(key).child("id").setValue(key);
                        db.child("courses").child(courseCreated.getCourse_id()).child("lessons")
                                .child(key).child("name").setValue(m_Text);
                        db.child("courses").child(courseCreated.getCourse_id()).child("lessons")
                                .child(key).child("photo_url").setValue(photo_url);
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                final AlertDialog dialog = builder.create();
                dialog.show();
                gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openImageActivity();
                    }
                });
//                builder.show();
            }
        });
//        addTopicB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), InstructorTopicActivity.class);
//                intent.putExtra("lesson",lessonModel);
//                startActivity(intent);
//            }
//        });
        lessonsRV = (RecyclerView) findViewById(R.id.lessonsRV);
        lessonsRV.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false);
        lessonsRV.setLayoutManager(layoutManager);
/*
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                lessonsRV.getContext(), layoutManager.getOrientation());
        lessonsRV.addItemDecoration(dividerItemDecoration);*/

        db = FirebaseDatabase.getInstance().getReference();
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("course")) {
            courseCreated = intent.getParcelableExtra("course");
            if (courseCreated != null) {
                if (courseCreated.getName() != null) {
                    toolbar.setTitle(courseCreated.getName().toString());
                }
                if (courseCreated.getCourse_id() != null) {
                    courseID = courseCreated.getCourse_id().toString();
                }
            }
//            Log.v("TestingTesting", "" + courseCreated.getName().toString());
        }

        toolbar.setTitleTextColor(
                (ContextCompat.getColor(InstructorLessonsActivity.this, R.color.colorBack)));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN | ItemTouchHelper.UP) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                Toast.makeText(getApplicationContext(),R.string.on_move, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                final String lessonID = lessonModelList.get(viewHolder.getAdapterPosition()).getId();
                instructorLessonAdapter.notifyItemRemoved(
                        viewHolder.getLayoutPosition());
                db.child("courses").child(courseID).child("lessons").child(lessonID).removeValue();
                Toast.makeText(getApplicationContext(), R.string.on_swiped, Toast.LENGTH_SHORT).show();

            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(lessonsRV);
    }

    private void openImageActivity() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onPause() {
        if (listener != null)
            db.removeEventListener(listener);
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            Uri filePath = data.getData();
            String storagePath = "images/coursesPhoto/" + courseID + UUID.randomUUID();

            new UploadFile(filePath, InstructorLessonsActivity.this, new FileUploadHelper() {
                @Override
                public void fileUploaded(String url) {
                    photo_url = url;
                }
            }, storagePath);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        db = FirebaseDatabase.getInstance().getReference();
        Log.v("el_ID_hna", courseID + " ");

        listener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lessonModelList = new ArrayList<>();
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Log.e("dataSnapShot", d + "");
                    lessonModel = d.getValue(LessonModel.class);
                    lessonModelList.add(lessonModel);
                }
                instructorLessonAdapter = new InstructorLessonAdapter(
                        lessonModelList,
                        new InstructorLessonAdapter.OnClickHandler() {
                            @Override
                            public void onClick(LessonModel test) {
                                // intent from current activity to Next Activity
                                Intent intent = new Intent(InstructorLessonsActivity.this,
                                        InstructorTopicActivity.class);
                                //Putting extras to get them in the Next Activity
                                intent.putExtra("courseId", courseID);
                                intent.putExtra("lessonid", test.getId());
                                //     intent.putExtra("lesson",test);
                                // starting the Activity
                                startActivity(intent);
                            }
                        }, InstructorLessonsActivity.this, courseID);


                if (lessonModelList.size() <= 0) {
                    Log.v("mfesh Course", "toz fek");
                    findViewById(R.id.no_lesson).setVisibility(View.VISIBLE);
                } else {
                    findViewById(R.id.no_lesson).setVisibility(View.GONE);
                }
                lessonsRV.setAdapter(instructorLessonAdapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        db.child("courses").child(courseID).child("lessons").addValueEventListener(listener);
    }
}