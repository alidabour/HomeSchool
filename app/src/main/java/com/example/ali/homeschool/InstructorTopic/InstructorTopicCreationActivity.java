package com.example.ali.homeschool.InstructorTopic;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ali.homeschool.InstructorTopic.CreationHelper.ColorQuestionDialog;
import com.example.ali.homeschool.InstructorTopic.CreationHelper.ImageDialog;
import com.example.ali.homeschool.InstructorTopic.CreationHelper.MainQuestionDialog;
import com.example.ali.homeschool.InstructorTopic.CreationHelper.MultiQuestionDialog;
import com.example.ali.homeschool.InstructorTopic.CreationHelper.OnEditLayoutReady;
import com.example.ali.homeschool.InstructorTopic.CreationHelper.OnLayoutReadyInterface;
import com.example.ali.homeschool.InstructorTopic.CreationHelper.ProgressImage;
import com.example.ali.homeschool.InstructorTopic.CreationHelper.SoundDialog;
import com.example.ali.homeschool.InstructorTopic.CreationHelper.SpeechDialog;
import com.example.ali.homeschool.InstructorTopic.CreationHelper.TextDetectionDialog;
import com.example.ali.homeschool.InstructorTopic.CreationHelper.TextViewDialog;
import com.example.ali.homeschool.InstructorTopic.helper.DoneOrderInterface;
import com.example.ali.homeschool.InstructorTopic.helper.OnStartDragListener;
import com.example.ali.homeschool.InstructorTopic.helper.SimpleItemTouchHelperCallback;
import com.example.ali.homeschool.R;
import com.example.ali.homeschool.controller.activities.Register;
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
        implements OnLayoutReadyInterface, XMLClick, ColorPickerDialogListener,
        OnStartDragListener, DoneOrderInterface, OnEditLayoutReady, XMLEditClick,ProgressImage {
    static int id = 0;
    private static final int PICK_IMAGE_REQUEST = 234;
    private static final int PICK_SOUND_REQUEST = 235;
    private MediaPlayer mediaPlayer;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    ImageView submitTV;
    ImageView image;
    ImageView question;
    ImageView sound;
    ImageView text ;
    String courseId;
    String lessonid;
    String topicid;
    String topicname;

    int textColor = -11177216;

    SpeechDialog speechDialog;
    MultiQuestionDialog multiQuestionDialog;
    ColorQuestionDialog colorQuestionDialog;
    SoundDialog soundDialog;
    ImageDialog imageDialog;
    TextViewDialog textViewDialog;
    TextDetectionDialog textDetectionDialog;
    ProgressImage progressImage;

    //    MainQuestionDialog mainQuestionDialog;
    OnLayoutReadyInterface onLayoutReadyInterface = this;

    private ItemTouchHelper mItemTouchHelper;
    ArrayList<String> layoutsList;
    RecyclerView recyclerViewOrdering;
    RecyclerListAdapter adapter;
    Toolbar toolbar;
    ParseXMLInstructor parseXMLInstructor;
    boolean isImageOrSound = false;
    boolean isQuestion = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_topic);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        parseXMLInstructor = new ParseXMLInstructor(InstructorTopicCreationActivity.this);
        parseXMLInstructor.setXmlClick(this);
        parseXMLInstructor.setXmlEditClick(this);
        layoutsList = new ArrayList<>();
        recyclerViewOrdering = (RecyclerView) findViewById(R.id.recycleView);
        recyclerViewOrdering.setHasFixedSize(true);
        recyclerViewOrdering.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new RecyclerListAdapter(parseXMLInstructor, this,
                layoutsList, this);
        recyclerViewOrdering.setAdapter(adapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerViewOrdering);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        question = (ImageView) findViewById(R.id.question);
        submitTV = (ImageView) findViewById(R.id.submit);
        image = (ImageView) findViewById(R.id.image);
        sound = (ImageView) findViewById(R.id.sound);
        text = (ImageView) findViewById(R.id.textViewInstructor);

        progressImage = this;
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("lessonid")) {
            lessonid = intent.getStringExtra("lessonid");
        }
        if (intent != null && intent.hasExtra("courseId")) {
            courseId = intent.getStringExtra("courseId");
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
//                mainQuestionDialog = new MainQuestionDialog(id,InstructorTopicCreationActivity.this,onLayoutReadyInterface);
//                mainQuestionDialog.openMainQuestionDialog();
                if(isImageOrSound){
                    Toast.makeText(InstructorTopicCreationActivity.this, "Not Allowed", Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                final AlertDialog.Builder builder = new AlertDialog.Builder(
                        InstructorTopicCreationActivity.this);
                builder.setTitle("Choose type :");
                LayoutInflater li = LayoutInflater.from(InstructorTopicCreationActivity.this);
                LinearLayout someLayout = (LinearLayout) li.inflate(R.layout.question_list, null);
                final TextView colorQue = (TextView) someLayout.findViewById(R.id.color);
                final TextView speechQue = (TextView) someLayout.findViewById(R.id.speech);
                final TextView multiQue = (TextView) someLayout.findViewById(R.id.multipleChoices);
                final TextView textDetection = (TextView) someLayout
                        .findViewById(R.id.textDetection);

                builder.setView(someLayout);
                final AlertDialog dialog = builder.create();
                dialog.show();
                colorQue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                        colorQuestionDialog = new ColorQuestionDialog(id,
                                InstructorTopicCreationActivity.this, onLayoutReadyInterface);
                        colorQuestionDialog.setProgressImage(progressImage);
                        colorQuestionDialog.openColorQuestionDialog();

                    }
                });
                speechQue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                        speechDialog = new SpeechDialog(id, InstructorTopicCreationActivity.this,
                                onLayoutReadyInterface);
                        speechDialog.setProgressImage(progressImage);
                        speechDialog.openSpeechDialog();

                    }
                });
                multiQue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                        multiQuestionDialog = new MultiQuestionDialog(id,
                                InstructorTopicCreationActivity.this, onLayoutReadyInterface);
                        multiQuestionDialog.setProgressImage(progressImage);
                        multiQuestionDialog.openMultiQuestionDialog();
                    }
                });

                textDetection.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                        textDetectionDialog = new TextDetectionDialog(id,
                                InstructorTopicCreationActivity.this, onLayoutReadyInterface);
                        textDetectionDialog.setProgressImage(progressImage);
                        textDetectionDialog.openTextDetectionDialog();
                    }
                });


            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageDialog = new ImageDialog(id, InstructorTopicCreationActivity.this,
                        onLayoutReadyInterface);
                imageDialog.setCourseId(courseId);
                imageDialog.setProgressImage(progressImage);
                imageDialog.openImageDialog();

            }
        });

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewDialog = new TextViewDialog(id, InstructorTopicCreationActivity.this, onLayoutReadyInterface);
                textViewDialog.openTextViewDialog();
            }
        });
        sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundDialog = new SoundDialog(id, InstructorTopicCreationActivity.this,
                        onLayoutReadyInterface);
                soundDialog.setCourseId(courseId);
                soundDialog.setProgressImage(progressImage);
                soundDialog.openSoundDialog();
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId() == R.id.submit) {
                    Log.v("Test", "Sign IN");
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
                return true;
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.instructor, menu);
        MenuItem item = menu.findItem(R.id.submit);
        return true;
    }


    private void addLayout(String layout) {
        adapter.clearItems();
        layoutsList.add(layout);
        adapter.setArrayItems(layoutsList);
    }

    private void addLayoutAt(String layout, int index) {
        adapter.clearItems();
        layoutsList.add(index, layout);
        adapter.setArrayItems(layoutsList);
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
        if (textDetectionDialog != null) {
            textDetectionDialog.setTextColor(color);
        }
        if (colorQuestionDialog != null) {
            colorQuestionDialog.setTextColor(color);
        }
        if (speechDialog != null) {
            speechDialog.setTextColor(color);
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
//            intent.putExtra("lan", "eng");
            startActivityForResult(intent, Text_Detection);
        }
    }

    @Override
    public void onImageClick(View imageView) {
    }

    @Override
    public void onMultQuestionClicked(boolean isCorrect) {

    }

    @Override
    public void onEditImageView(int id, String src, String layout) {
        int index = layoutsList.indexOf(layout);
        imageDialog = new ImageDialog(id, InstructorTopicCreationActivity.this,
                onLayoutReadyInterface);
        imageDialog.setCourseId(courseId);
        imageDialog.setUrl(src);
        imageDialog.setEditing(true);
        imageDialog.setIndex(index);
        imageDialog.setOnEditLayoutReady(this);
        imageDialog.setProgressImage(progressImage);
        imageDialog.openImageDialog();
    }

    @Override
    public void onEditSound(int id, String audioUrl, String audioText, String layout) {
        int index = layoutsList.indexOf(layout);
        soundDialog = new SoundDialog(id, InstructorTopicCreationActivity.this,
                onLayoutReadyInterface);
        soundDialog.setCourseId(courseId);
        soundDialog.setAudioLink(audioUrl);
        soundDialog.setSoundText(audioText);
        soundDialog.setEditing(true);
        soundDialog.setIndex(index);
        soundDialog.setProgressImage(progressImage);
        soundDialog.setOnEditLayoutReady(this);
        soundDialog.openSoundDialog();
    }

    @Override
    public void onEditColorQuestion(int id, String questionTitle, String layout) {
        int index = layoutsList.indexOf(layout);
        colorQuestionDialog = new ColorQuestionDialog(id, InstructorTopicCreationActivity.this,
                onLayoutReadyInterface);
        colorQuestionDialog.setQuestionTitle(questionTitle);
        colorQuestionDialog.setOnEditLayoutReady(this);
        colorQuestionDialog.setIndex(index);
        colorQuestionDialog.setEditing(true);
        colorQuestionDialog.openColorQuestionDialog();
    }

    @Override
    public void onEditTextView(int id, String text, String layout) {
        int index = layoutsList.indexOf(layout);
        textViewDialog = new TextViewDialog(id, InstructorTopicCreationActivity.this, onLayoutReadyInterface);
        textViewDialog.setEditing(true);
        textViewDialog.setIndex(index);
        textViewDialog.setOnEditLayoutReady(this);
        textViewDialog.setText(text);
        textViewDialog.openTextViewDialog();

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

    @Override
    public void setLayoutAt(String layout, int index) {
        layoutsList.remove(index);
        addLayoutAt(layout, index);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing Activity")
                .setMessage("Are you sure you want to close this activity?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }


    @Override
    public void setImageOrSound(boolean imageOrSound, boolean isQuestion) {
        isImageOrSound = imageOrSound;
        this.isQuestion = isQuestion;
    }
}
