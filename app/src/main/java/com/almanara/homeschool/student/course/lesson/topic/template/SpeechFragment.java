package com.almanara.homeschool.student.course.lesson.topic.template;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.almanara.ali.homeschool.R;
import com.almanara.homeschool.student.course.lesson.topic.ClassActivity;

import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;


import java.util.ArrayList;

/**
 * Created by Ali on 7/1/2017.
 */

public class SpeechFragment extends Fragment implements RecognitionListener {
    String layout;
    private final String HOLD = " ,HO##LD,";
    String[] parms;
    private TextView returnedText;
    private ImageView toggleButton;
    private ProgressBar progressBar;
    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;
    private TextView textSpeech;
    private String LOG_TAG = "VoiceRecognitionActivity";

    public SpeechFragment() {
    }

    public static SpeechFragment newInstance(String layout) {
        SpeechFragment fragment = new SpeechFragment();
        Bundle args = new Bundle();
        args.putString("layout", layout);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            layout = getArguments().getString("layout");
            if (layout != null) {
                parms = layout.split("(?<=" + HOLD + ")");
                for (int i = 0; i < parms.length; i++) {
                    parms[i] = parms[i].replaceAll(HOLD, " ");
                }
            }

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.speech_fragment, container, false);
        returnedText = (TextView) view.findViewById(R.id.textView1);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar1);
        toggleButton = (ImageView) view.findViewById(R.id.toggleButton1);
        textSpeech = (TextView) view.findViewById(R.id.text2);
        textSpeech.setText(parms[0]);
        progressBar.setVisibility(View.INVISIBLE);
        speech = SpeechRecognizer.createSpeechRecognizer(getActivity());
        speech.setRecognitionListener(this);
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        //  recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,"en");
        if (parms[0].matches("[a-zA-Z]+")) {
            recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
        } else {

            recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ar-EG");
        }

        recognizerIntent
                .putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getActivity().getPackageName());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);


        final String[] isChecked = {"true"};
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isChecked[0].equals("true")) {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setIndeterminate(true);
                    speech.startListening(recognizerIntent);
                    Log.v("speech","true");
                    isChecked[0] = "false";
                } else {
                    progressBar.setIndeterminate(false);
                    progressBar.setVisibility(View.INVISIBLE);
                    speech.stopListening();
                    Log.v("speech","false");
                    isChecked[0] = "true";

                }

            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (speech != null) {
            speech.destroy();
        }

    }

    @Override
    public void onBeginningOfSpeech() {
        progressBar.setIndeterminate(false);
        progressBar.setMax(10);
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
    }

    @Override
    public void onEndOfSpeech() {
        progressBar.setIndeterminate(true);
        toggleButton.setEnabled(false);
    }

    @Override
    public void onError(int errorCode) {
        String errorMessage = getErrorText(errorCode);
        returnedText.setText(errorMessage);
        toggleButton.setEnabled(false);
    }

    @Override
    public void onEvent(int arg0, Bundle arg1) {
    }

    @Override
    public void onPartialResults(Bundle arg0) {
    }

    @Override
    public void onReadyForSpeech(Bundle arg0) {
    }

    @Override
    public void onResults(Bundle results) {
        ArrayList<String> matches = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String text = "";
        for (String result : matches)
            text += result + "\n";
        if (matches.contains(parms[0])) {
            if (getActivity() instanceof ClassActivity) {
                ((ClassActivity) getActivity()).onAnswer(true);
            }
        } else {
            if (getActivity() instanceof ClassActivity) {
                ((ClassActivity) getActivity()).onAnswer(false);
            }
        }
        returnedText.setText(text);
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        progressBar.setProgress((int) rmsdB);
    }

    public String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = getString(R.string.audio_recording_error);
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = getString(R.string.client_error);
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = getString(R.string.permision);
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = getString(R.string.network_error);
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = getString(R.string.network_timeout);
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = getString(R.string.no_match);
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = getString(R.string.rconize_serivce);
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = getString(R.string.error_form_server);
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = getString(R.string.no_speech_input);
                break;
            default:
                message = getString(R.string.try_again);
                break;
        }
        return message;
    }
}
