package com.example.ali.homeschool.InstructorTopic;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ali.homeschool.InstructorTopic.CreationHelper.ImageDialog;
import com.example.ali.homeschool.InstructorTopic.CreationHelper.MainQuestionDialog;
import com.example.ali.homeschool.InstructorTopic.CreationHelper.OnLayoutReadyInterface;
import com.example.ali.homeschool.InstructorTopic.CreationHelper.SoundDialog;
import com.example.ali.homeschool.InstructorTopic.helper.DoneOrderInterface;
import com.example.ali.homeschool.InstructorTopic.helper.OnStartDragListener;
import com.example.ali.homeschool.InstructorTopic.helper.SimpleItemTouchHelperCallback;
import com.example.ali.homeschool.R;
import com.example.ali.homeschool.exercises.color.ColorActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.jrummyapps.android.colorpicker.ColorPickerDialogListener;

import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import edu.sfsu.cs.orange.ocr.Answer;

import static com.example.ali.homeschool.Constants.*;


public class InstructorTopicCreationActivity extends AppCompatActivity
        implements OnLayoutReadyInterface, XMLClick, ColorPickerDialogListener, OnStartDragListener, DoneOrderInterface {
    static int id = 0;
    private static final int PICK_IMAGE_REQUEST = 234;
    private static final int PICK_SOUND_REQUEST = 235;
    private MediaPlayer mediaPlayer;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    TextView submitTV;
    TextView image;
    TextView question;
    String courseId;
    String lessonid;
    String topicid;
    String topicname;
    TextView sound;

    SoundDialog soundDialog;
    ImageDialog imageDialog;
    MainQuestionDialog mainQuestionDialog;
    OnLayoutReadyInterface onLayoutReadyInterface = this;

    private ItemTouchHelper mItemTouchHelper;
    ArrayList<String> layoutsList;
    RecyclerView recyclerViewOrdering;
    RecyclerListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_topic);
        layoutsList = new ArrayList<>();
        recyclerViewOrdering = (RecyclerView) findViewById(R.id.recycleView);
        recyclerViewOrdering.setHasFixedSize(true);
        recyclerViewOrdering.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new RecyclerListAdapter(getApplicationContext(), this,
                layoutsList, this, this);
        recyclerViewOrdering.setAdapter(adapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerViewOrdering);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        question = (TextView) findViewById(R.id.question);
        submitTV = (TextView) findViewById(R.id.submit);
        image = (TextView) findViewById(R.id.image);
        sound = (TextView) findViewById(R.id.sound);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("lessonid")) {
            lessonid = intent.getStringExtra("lessonid");
        }
        if (intent != null && intent.hasExtra("courseID")) {
            courseId = intent.getStringExtra("courseID");
        }
        if (intent != null && intent.hasExtra("topicid")) {
            topicid = intent.getStringExtra("topicid");
        }
        if (intent != null && intent.hasExtra("topicname")) {
            topicname = intent.getStringExtra("topicname");
        }
        if (intent != null && intent.hasExtra("layout")) {
            String layout = intent.getStringExtra("layout");
            layout = layout.replaceAll(start, " ");
            layout = layout.replaceAll(end, " ");
            String[] lays = layout.split("(?<=" + ARRANGE + ")");
            for (String x : lays) {
                if (!x.trim().isEmpty()) {
                    addLayout(x);
                }
            }
        }

        question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainQuestionDialog = new MainQuestionDialog(id,InstructorTopicCreationActivity.this,onLayoutReadyInterface);
                mainQuestionDialog.openMainQuestionDialog();

            }
        });
        submitTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference = databaseReference.child("courses").child(courseId)
                        .child("lessons").child(lessonid).child("topics").child(topicid);
                TopicModel t = new TopicModel();
                String layouts = " ";
                for (String layout : layoutsList) {
                    layouts += layout;
                }
                t.setLayout(start + layouts + end);
                t.setName(topicname);
                t.setId(topicid);
                databaseReference.updateChildren(t.toMap());
                finish();
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageDialog = new ImageDialog(id, InstructorTopicCreationActivity.this,
                        onLayoutReadyInterface);
                imageDialog.setCourseId(courseId);
                imageDialog.openImageDialog();

            }
        });
        sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundDialog = new SoundDialog(id, InstructorTopicCreationActivity.this,
                        onLayoutReadyInterface);
                soundDialog.setCourseId(courseId);
                soundDialog.openSoundDialog();
            }
        });
    }

    private void addLayout(String layout) {
        adapter.clearItems();
        layoutsList.add(layout);
        adapter.setArrayItems(layoutsList);
        adapter = new RecyclerListAdapter(this, this, layoutsList, this, this);
        recyclerViewOrdering.setAdapter(adapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerViewOrdering);
    }

    public RelativeLayout parse(String layout) {
        InputStream stream = new ByteArrayInputStream(layout.getBytes(Charset.forName("UTF-8")));
        ParseXMLInstructor parseXMLInstructor = new ParseXMLInstructor();
        RelativeLayout mainLayout = null;

        try {
            mainLayout = (RelativeLayout) parseXMLInstructor
                    .parse(this, stream, getApplicationContext(), this);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
        }
        return mainLayout;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            imageDialog.setFilePath(data.getData());
        }
        if (requestCode == PICK_SOUND_REQUEST && resultCode == Activity.RESULT_OK) {
            soundDialog.setFilePath(data.getData());
        }
    }

    @Override
    public void onColorSelected(int dialogId, @ColorInt int color) {
       if(mainQuestionDialog !=null){
           mainQuestionDialog.setTextColor(color);
       }
    }

    @Override
    public void onDialogDismissed(int dialogId) {
    }

    @Override
    public void playSound(String url) {
        try {
            playAudio(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void openActivity(String activity, Answer answer) {
        if (activity.equals("ColorActivity")) {
            Intent intent = new Intent(this, ColorActivity.class);
            intent.putExtra("Answer", answer);
            startActivityForResult(intent, Color_Request);
        } else if (activity.equals("TextDetection")) {
            Intent intent = new Intent(this, edu.sfsu.cs.orange.ocr.CaptureActivity.class);
            intent.putExtra("Answer", answer);
            intent.putExtra("lan", "eng");
            startActivityForResult(intent, Text_Detection);
        }
    }

    @Override
    public void onImageClick(View imageView) {
    }

    private void playAudio(String url) throws Exception {
        killMediaPlayer();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(url);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.prepare();
        mediaPlayer.start();
    }

    private void killMediaPlayer() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onReorder(List<String> layouts) {
        layoutsList.clear();
        layoutsList.addAll(layouts);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void setLayout(String layout) {
        addLayout(layout);
    }
}
