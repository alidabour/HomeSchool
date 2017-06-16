package com.example.ali.homeschool.exercises.color;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.EditText;

import com.example.ali.homeschool.R;
import edu.sfsu.cs.orange.ocr.Answer;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class ColorActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {

    static {
        //  System.loadLibrary("native-lib");
        if (OpenCVLoader.initDebug()) {
            Log.d("OpenCv", "eek3wjkd");

        }
    }
    /*
    hue range
    green 45 75
    yellow 90 100
    red 121 149
    blue 0 30
    purple 150 180
    orange 100 120
       */
    int i=100;
    int[] iLowH  =  {45,90,121,0,150,100};
    int[] iHighH =  {75,100,149,30,180,120};
    int iLowS = 50;
    int iHighS = 255;
    int iLowV = 40;
    int iHighV = 255;

    String[] colorName={"Green","yellow","Red","Blue","purple","orange"};

    Mat imgHSV, imgThresholded;

    Scalar sc1, sc2;

    JavaCameraView javaCameraView;
    EditText editText;

    BaseLoaderCallback baseLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {

            if (status == baseLoaderCallback.SUCCESS) {
                javaCameraView.enableView();
            } else
                super.onManagerConnected(status);
        }


    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        editText= (EditText) findViewById(R.id.text);
        javaCameraView = (JavaCameraView) findViewById(R.id.Camera_view);
        javaCameraView.setCameraIndex(0);
        javaCameraView.setCvCameraViewListener(this);
        javaCameraView.setVisibility(SurfaceView.VISIBLE);
        javaCameraView.enableView();

        Intent intent= getIntent();
        if(intent != null){
            Answer answer = intent.getParcelableExtra("Answer");
            detectcolor(answer.getAnswer());

        }
//        Bundle b = iin.getExtras();

//        if(b!=null)
//        {
//            String j =(String) b.get("name");
//            detectcolor(j);
//
//        }




    }

    @Override
    protected void onPause() {
        super.onPause();

        if (javaCameraView != null) {
            javaCameraView.disableView();

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (OpenCVLoader.initDebug()) {
            baseLoaderCallback.onManagerConnected(baseLoaderCallback.SUCCESS);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (javaCameraView != null) {
            javaCameraView.disableView();
        }
    }

    @Override
    public void onCameraViewStarted(int width, int height) {

        imgHSV = new Mat(width, height, CvType.CV_16UC4);
        imgThresholded = new Mat(width, height, CvType.CV_16UC4);

    }

    @Override
    public void onCameraViewStopped() {
        imgHSV.release();
        imgThresholded.release();
    }
    int c=0;
    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {

        Mat img = inputFrame.rgba();
        if(i!=100) {

            Mat kernelDilate;
            Imgproc.cvtColor(img, imgHSV, Imgproc.COLOR_BGR2HSV);
            Core.inRange(imgHSV, new Scalar(iLowH[i], iLowS, iLowV), new Scalar(iHighH[i], iHighS, iHighV), imgThresholded); //Threshold the image
            Imgproc.threshold(imgThresholded, imgThresholded, 100, 255, Imgproc.THRESH_BINARY);
            kernelDilate = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(6, 6));
            Imgproc.dilate(imgThresholded, imgThresholded, kernelDilate);

            if (Core.countNonZero(imgThresholded) > 60000){

                Core.putText(img, "Detected " + colorName[i], new Point(75, 150), Core.FONT_HERSHEY_DUPLEX, 1.0, new Scalar(209, 150, 98));
//                Toast.makeText(getApplicationContext(),"Reeed",Toast.LENGTH_LONG).show();
//                finish();

            }
            else Core.putText(img, "find " + colorName[i], new Point(150, 150), Core.FONT_HERSHEY_DUPLEX, 1.0, new Scalar(209, 150, 98));

        }

        else  Core.putText(img, "Hello", new Point(60, 75), Core.FONT_HERSHEY_DUPLEX, 1.0, new Scalar(207, 150, 98));



        return img;
    }

    void detectcolor(String s){

        switch (s){
            case "green" :
                i=0;
                break;
            case "yellow" :
                i=1;
                break;
            case "Red" :
                i=2;
                break;
            case "blue" :
                i=3;
                break;

            case "purple" :
                i=4;
                break;
            case "orange" :
                i=5;
                break;
            default:
                i=100;
        }
        if(i!=100) {
            sc1 = new Scalar(iLowH[i], iLowS, iLowV);
            sc2 = new Scalar(iHighH[i], iHighS, iHighV);
        }
    }
}

