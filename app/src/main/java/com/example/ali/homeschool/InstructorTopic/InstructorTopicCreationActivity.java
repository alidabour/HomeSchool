package com.example.ali.homeschool.InstructorTopic;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ali.homeschool.Constants;
import com.example.ali.homeschool.R;
import com.example.ali.homeschool.UserModelHelper.FileUploadHelper;
import com.example.ali.homeschool.UserModelHelper.UploadFile;
import com.example.ali.homeschool.exercises.Answer;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.jrummyapps.android.colorpicker.ColorPickerDialog;
import com.jrummyapps.android.colorpicker.ColorPickerDialogListener;

import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.UUID;

import static com.example.ali.homeschool.Constants.PUT_SOUND_LINK_HERE;
import static com.example.ali.homeschool.Constants.mButton;
import static com.example.ali.homeschool.Constants.mTextView;
import static com.example.ali.homeschool.Constants.radioGroupEnd;
import static com.example.ali.homeschool.Constants.radioGroupStart;
import static com.example.ali.homeschool.Constants.setColorButton;
import static com.example.ali.homeschool.Constants.soundXML;
import static com.example.ali.homeschool.Constants.start;
import static com.example.ali.homeschool.Constants.textViewProperties;

public class InstructorTopicCreationActivity extends AppCompatActivity implements ImageClicked, ColorPickerDialogListener, TextAppInterface {
    int id = 0;
    private static final int PICK_IMAGE_REQUEST = 234;
    private static final int PICK_SOUND_REQUEST = 235;
    Button openColorPicker;

    UploadFile uploadFileImage;
    UploadFile uploadFileSound;
    private Uri filePath;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    ArrayList<String> midLayouts;
    TextView submitTV;
    String m_Text = "";
    String audioLink = "";
    TextView image;
    TextView question;
    String courseId;
    String lessonid;
    String topicid;
    TextView sound;
    LinearLayout act_main;
    LinearLayout mainView;
    String soundText = "PlaceHolder";
    String mid = "";
    int textColor=-11177216;
    int textAppearance = android.R.style.TextAppearance_Material_Body1;

    String end = "</LinearLayout></RelativeLayout>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_topic);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        question = (TextView) findViewById(R.id.question);
        submitTV = (TextView) findViewById(R.id.submit);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("lessonid")) {
            lessonid = intent.getStringExtra("lessonid");
        }
        if (intent != null && intent.hasExtra("courseID")) {
            courseId = intent.getStringExtra("courseID");
        }
        if (intent != null && intent.hasExtra("topicid")) {
            courseId = intent.getStringExtra("topicid");
        }
        question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(
                        InstructorTopicCreationActivity.this);
                builder.setTitle("Choose type :");
                LayoutInflater li = LayoutInflater.from(InstructorTopicCreationActivity.this);
                LinearLayout someLayout = (LinearLayout) li.inflate(R.layout.question_list, null);
                final TextView colorQue = (TextView) someLayout.findViewById(R.id.color);
                final TextView speechQue = (TextView) someLayout.findViewById(R.id.speech);
                final TextView multiQue = (TextView) someLayout.findViewById(R.id.multipleChoices);

                builder.setView(someLayout);
                final AlertDialog dialog = builder.create();
                dialog.show();
                colorQue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                        openColorQuestionDialog();

                    }
                });
                speechQue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                        openSpeechQueDialog();
                    }
                });
                multiQue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                        openMultiQuestionDialog();
                    }
                });

            }
        });
        submitTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference = databaseReference.child("courses").child(courseId)
                        .child("lessons").child(lessonid).child("topics").child("topicid");
                String key = databaseReference.push().getKey();
                TopicModel t = new TopicModel();
                t.setId(key);
                t.setName("Name");
                t.setLayout(start + midLayouts.toString() + end);
                databaseReference.child(key).updateChildren(t.toMap());
                finish();
            }
        });
        midLayouts = new ArrayList<>();
        act_main = (LinearLayout) findViewById(R.id.activiy_main);
        mainView = (LinearLayout) findViewById(R.id.mainLayout);
        image = (TextView) findViewById(R.id.image);
        sound = (TextView) findViewById(R.id.sound);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(
                        InstructorTopicCreationActivity.this);
                builder.setTitle("Select image");
                LayoutInflater li = LayoutInflater.from(InstructorTopicCreationActivity.this);
                LinearLayout someLayout = (LinearLayout) li.inflate(R.layout.image_dialog, null);
                builder.setView(someLayout);
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                final AlertDialog dialog = builder.create();
                dialog.show();
                TextView gallery = (TextView) someLayout.findViewById(R.id.choosefromGallery);
                gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                        openImageActivity();
                    }
                });

                TextView urlTV = (TextView) someLayout.findViewById(R.id.imageUrl);
                urlTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                        openImageURLDialog();
                    }
                });

            }
        });
        sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final AlertDialog.Builder builder = new AlertDialog.Builder(
                        InstructorTopicCreationActivity.this);
                builder.setTitle("Select sound file");
                LayoutInflater li = LayoutInflater.from(InstructorTopicCreationActivity.this);
                LinearLayout someLayout = (LinearLayout) li.inflate(R.layout.sound_dialog, null);
                final EditText soundET = (EditText) someLayout.findViewById(R.id.soundtext);
                builder.setView(someLayout);
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                final AlertDialog dialog = builder.create();
                dialog.show();
                TextView gallery = (TextView) someLayout.findViewById(R.id.choosefromGallery);
                gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                        soundText = soundET.getText().toString();
                        openSoundActivity();
                    }
                });

                TextView urlTV = (TextView) someLayout.findViewById(R.id.imageUrl);
                urlTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                        soundText = soundET.getText().toString();
                        openSoundURLDialog();
                    }
                });


            }
        });
    }

    private void openSpeechQueDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(InstructorTopicCreationActivity.this);
        builder.setTitle("Questions :");
        LayoutInflater li = LayoutInflater.from(InstructorTopicCreationActivity.this);
        final LinearLayout linearLayout = (LinearLayout) li
                .inflate(R.layout.speech_question_dialog, null);
        final EditText word = (EditText) linearLayout.findViewById(R.id.word);
        textViewProperties(linearLayout,InstructorTopicCreationActivity.this,this);
        setColorButton(linearLayout,textColor);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.v("TESTAPP","onClick :"+ textAppearance);
                addLayout(mTextView(id,"قول كلمة :",textColor,textAppearance));
                String textview = mTextView(id, word.getText().toString(), textColor,textAppearance);
                addLayout(textview);
                Answer answer = new Answer();
                answer.setAnswer(word.getText().toString());
                addLayout(mButton(id,"Start","Speech",answer,PUT_SOUND_LINK_HERE));
                dialogInterface.cancel();
            }
        });
        builder.setView(linearLayout);
        builder.show();
    }

    private void openMultiQuestionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(InstructorTopicCreationActivity.this);
        builder.setTitle("Questions :");
        LayoutInflater li = LayoutInflater.from(InstructorTopicCreationActivity.this);
        final RelativeLayout relativeLT = (RelativeLayout) li
                .inflate(R.layout.mulit_choice_dialog, null);
        final EditText question1 = (EditText) relativeLT.findViewById(R.id.questionText);
        final LinearLayout linearLayout = (LinearLayout) relativeLT.findViewById(R.id.questions);
        final EditText answer1 = (EditText) linearLayout.findViewById(R.id.answer1ET);
        FloatingActionButton addQuestion = (FloatingActionButton) relativeLT
                .findViewById(R.id.addQuestion);
        addQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(InstructorTopicCreationActivity.this);
                LinearLayout question = (LinearLayout) li.inflate(R.layout.question_layout, null);
                linearLayout.addView(question);
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                LinearLayout la = (LinearLayout) linearLayout.getChildAt(0);
                TextInputLayout te = (TextInputLayout) la.getChildAt(0);
                FrameLayout et = (FrameLayout) te.getChildAt(0);
                android.support.v7.widget.AppCompatEditText om = (AppCompatEditText) et
                        .getChildAt(0);
                ArrayList<String> radioLayout = new ArrayList<String>();
//                String quET = actionTextXML.replaceAll(PU, String.valueOf(id));
//                quET = quET.replaceAll(PUTSIZEHERE, String.valueOf(30));
//                quET = quET.replaceAll(PUTACTIONTEXTHERE, question1.getText().toString());
//                quET = quET.replaceAll(PUTCOLOR, "123");
//                radioLayout.add(quET);
                radioLayout.add(radioGroupStart);

                for (int count = 0; count < linearLayout.getChildCount(); count++) {
                    LinearLayout lat = (LinearLayout) linearLayout.getChildAt(count);
                    TextInputLayout tet = (TextInputLayout) lat.getChildAt(0);
                    CheckBox checkbox = (CheckBox) lat.getChildAt(1);
                    Log.v("MultiQue", "Check Box Checked:" + checkbox.isChecked());
                    FrameLayout ett = (FrameLayout) tet.getChildAt(0);
                    android.support.v7.widget.AppCompatEditText omm = (AppCompatEditText) ett
                            .getChildAt(0);
                    Log.v("MultiQue", "Child 0 Edit text :" + omm.getText());
//                    String r1 = radioButton.replaceAll(PUTIDHERE, String.valueOf(id));
//                    r1 = r1.replaceAll(PUTANSWERHERE, omm.getText().toString());
//                    radioLayout.add(r1);

                }

                radioLayout.add(radioGroupEnd);
                addLayout(radioLayout.toString());
            }
        });
        builder.setView(relativeLT);
        builder.show();
    }

    public RelativeLayout parse(String layout) {
        InputStream stream = null;
        stream = new ByteArrayInputStream(layout.getBytes(Charset.forName("UTF-8")));
        ParseXMLInstructor parseXMLInstructor = new ParseXMLInstructor();
        RelativeLayout mainLayout = null;

        try {
            mainLayout = (RelativeLayout) parseXMLInstructor
                    .parse(this,stream, getApplicationContext(), this);
            Log.v("ITA", "pass");
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            Log.v("ITA", "XML ERROR :" + e);
        } catch (IOException e) {
            Log.v("ITA", "XML IO ERROR :" + e);
        }
        return mainLayout;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK)
        {
            Log.v("Instructor",
                    "Req : " + requestCode + " Res :" + resultCode + " Intent : " + data
                            .getData().toString());
            filePath = data.getData();
            String storagePath = "images/" + courseId + "/" + UUID.randomUUID();
            uploadFileImage = new UploadFile(filePath, this, new FileUploadHelper() {
                @Override
                public void fileUploaded(String url) {
                    mid = "<ImageView android:layout_weight=\"1\" android:id=\"" + id
                            + "\" android:layout_width=\"match_parent\"" +
                            " android:layout_height=\"wrap_content\" homeSchool:src=\"" + url + "\" />";
                    midLayouts.add(id, mid);
                    id++;
                    RelativeLayout linearLayout = parse(start + midLayouts.toString() + end);
                    mainView.removeAllViews();
                    mainView.addView(linearLayout);
                    //and displaying a success toast
                    Toast.makeText(getApplicationContext(), "File Uploaded",
                            Toast.LENGTH_LONG).show();
                }
            }, storagePath);
        }
        if (requestCode == PICK_SOUND_REQUEST) {
            Log.v("Instructor",
                    "Req : " + requestCode + " Res :" + resultCode + " Intent : " + data
                            .getData().toString());
            filePath = data.getData();
            String storagePath = "sounds/" + courseId + "/" + UUID.randomUUID();
            uploadFileSound = new UploadFile(filePath, this, new FileUploadHelper() {
                @Override
                public void fileUploaded(String url) {
                    addSoundLayout(url, soundXML);
                    //and displaying a success toast
                    Toast.makeText(getApplicationContext(), "File Uploaded",
                            Toast.LENGTH_LONG).show();
                }
            }, storagePath);
        }
    }



    private void openColorQuestionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(InstructorTopicCreationActivity.this);
//        builder.setTitle("Title");
        LayoutInflater li = LayoutInflater.from(InstructorTopicCreationActivity.this);
        LinearLayout someLayout = (LinearLayout) li.inflate(R.layout.color_question_dialog, null);
        final EditText questionET = (EditText) someLayout.findViewById(R.id.question);
//        Spinner textColorSpinner = (Spinner) someLayout.findViewById(R.id.textColors);
        Spinner colorSpinner = (Spinner) someLayout.findViewById(R.id.colors);
        Spinner textSizeSpinner = (Spinner) someLayout.findViewById(R.id.textSizes);
        openColorPicker = (Button) someLayout.findViewById(R.id.colorsButton);
        openColorPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorPickerDialog.Builder c = ColorPickerDialog.newBuilder().setColor(0xFF000000);
                c.show(InstructorTopicCreationActivity.this);
            }
        });
        ArrayAdapter<CharSequence> actionColors = ArrayAdapter.createFromResource(this,
                R.array.color_array, android.R.layout.simple_spinner_item);
//        ArrayAdapter<CharSequence> textColors = ArrayAdapter.createFromResource(this,
//                R.array.color_array, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> textSizes = ArrayAdapter.createFromResource(this,
                R.array.text_size_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        actionColors.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        textColors.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        textSizes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        colorSpinner.setAdapter(actionColors);
//        textColorSpinner.setAdapter(textColors);
        textSizeSpinner.setAdapter(textSizes);
        final String[] selection = {" "};
        colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position,
                                       long l) {
                selection[0] = (String) adapterView.getItemAtPosition(position);
                Log.v("ITA", "Selected :" + selection[0]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
//        textColorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Log.v("ITA", "Selected :" + (String) adapterView.getItemAtPosition(i));
//
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
        textSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.v("ITA", "Selected :" + (String) adapterView.getItemAtPosition(i));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        builder.setView(someLayout);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String questionText = questionET.getText().toString();
//                String questionTV = actionTextXML.replaceAll(PUTACTIONTEXTHERE, questionText);
//                questionTV = questionTV.replaceAll(PUTCOLOR, String.valueOf(textColor));
//                questionTV = questionTV.replaceAll(PUTIDHERE, String.valueOf(id));
//                questionTV = questionTV.replaceAll(PUTSIZEHERE, "40");
//                addLayout(questionTV);
//                String actionButton = .replaceAll(PUT_ID_HERE, String.valueOf(id));
//                actionButton = actionButton.replaceAll(PUT_SOUND_LINK_HERE, "SimpleExercises");
//                actionButton = actionButton.replaceAll(PUT_TEXT_HERE, "Start");
//                addLayout(actionButton);
                dialogInterface.cancel();
            }
        });
        builder.show();
    }

    private void openSoundURLDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(InstructorTopicCreationActivity.this);
        builder.setTitle("Sound Button");
        LayoutInflater li = LayoutInflater.from(InstructorTopicCreationActivity.this);
        LinearLayout someLayout;
        someLayout = (LinearLayout) li.inflate(R.layout.dialog_button, null);

        // Set up the input
//                final EditText input = new EditText(MainActivity.this);
//                final EditText audioIn = new EditText(MainActivity.this);
        final EditText input = (EditText) someLayout.findViewById(R.id.text);
        final EditText audioIn = (EditText) someLayout.findViewById(R.id.audio);

//                input.setHint("Text");
//                audioIn.setHint("Audio Link");
//                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
//                input.setInputType(
//                        InputType.TYPE_CLASS_TEXT );
//                audioIn.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(someLayout);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                m_Text = input.getText().toString();
                audioLink = audioIn.getText().toString();
                mid = "<Button android:layout_weight=\"0\" android:id=\"" + id
                        + "\" android:text=\"" + soundText + "\" android:layout_width=\"match_parent\" " +
                        "android:layout_height=\"wrap_content\"" +
                        "homeSchool:audioLink=\"" + audioLink + "\" />";
                midLayouts.add(id, mid);

                id++;
                RelativeLayout linearLayout = parse(start + midLayouts.toString() + end);


                mainView.removeAllViews();
                mainView.addView(linearLayout);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void openSoundActivity() {
        Intent intent = new Intent();
        intent.setType("audio/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Sound"),
                PICK_SOUND_REQUEST);
    }

    private void openImageURLDialog() {
        final EditText input = new EditText(InstructorTopicCreationActivity.this);
        input.setInputType(
                InputType.TYPE_CLASS_TEXT);
        final AlertDialog.Builder urlBuilder = new AlertDialog.Builder(
                InstructorTopicCreationActivity.this);
        urlBuilder.setTitle("Title");
        urlBuilder.setView(input);
        urlBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = input.getText().toString();
                mid = "<ImageView android:layout_weight=\"1\" android:id=\"" + id
                        + "\" android:layout_width=\"match_parent\"" +
                        " android:layout_height=\"wrap_content\" homeSchool:src=\"" + m_Text + "\" />";
                midLayouts.add(id, mid);
                id++;
                RelativeLayout linearLayout = parse(
                        start + midLayouts.toString() + end);
                mainView.removeAllViews();
                mainView.addView(linearLayout);
            }
        });
        urlBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        urlBuilder.show();
    }

    private void openImageActivity() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                PICK_IMAGE_REQUEST);
    }

    @Override
    public void onClick(View v) {
        Log.v("Test", "COUNT BF " + mainView.getChildCount());
        Log.v("Test", "id " + v.getId());
//        v.setVisibility(View.INVISIBLE);
        mainView.removeView(v);
        int mainId = v.getId();
        Log.v("Test", "Main ID :" + v.getId());
        LinearLayout v0 = (LinearLayout) mainView.getChildAt(0);
        Log.v("Test", "V0 id " + v0.getId());
        v0.removeView(v);
//        LinearLayout view =(LinearLayout) mainView.findViewById(2000);
//        view.removeView(v);
//        mainView.removeView((View) view.getParent());
//        mainView.removeViewInLayout(view);

        act_main.removeView(v);
//        mainView.removeViewAt(v.getInd);
        mid = "";
        mainView.invalidate();
        Log.v("Test", "COUNT AF " + mainView.getChildCount());
//        midLayouts.get(v.getId());
        midLayouts.remove(v.getId());
        --id;
        Log.v("Test", "mid Layouts " + midLayouts);
    }

    private void addSoundLayout(String link, String layout) {
        link = link.replaceAll("&", "&amp;");
        link = link.replaceAll("\\?", "&#63;");
        layout = layout.replaceAll("PUTLINKHERE", link);
        layout = layout.replaceAll("PUTIDHERE", String.valueOf(id));
        layout = layout.replaceAll("PUTSOUNDTEXTHERE", soundText);
        midLayouts.add(id, layout);
        id++;
        RelativeLayout linearLayout = parse(start + midLayouts.toString() + end);
        mainView.removeAllViews();
        mainView.addView(linearLayout);
    }

    private void addLayout(String layout) {
        midLayouts.add(id, layout);
        id++;
        RelativeLayout linearLayout = parse(start + midLayouts.toString() + end);
        mainView.removeAllViews();
        mainView.addView(linearLayout);
    }


    @Override
    public void onColorSelected(int dialogId, @ColorInt int color) {
        textColor = color;
//        openColorPicker.setBackgroundColor(color);
        Toast.makeText(InstructorTopicCreationActivity.this, "oOOColor :" + color, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onDialogDismissed(int dialogId) {
    }

    @Override
    public void onSelected(int i) {
        Log.v("TEXTAPP"," i : "+ i);
        Log.v("TEXTAPP"," textapp[i] : "+ Constants.textAppearance[i]);
        textAppearance = Constants.textAppearance[i];
    }
}
