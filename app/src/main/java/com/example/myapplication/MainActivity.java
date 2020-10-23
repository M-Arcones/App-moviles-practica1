package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonPlay;
    private Animation scaleUp, scaleDown;
    private ArrayList<Pregunta> preguntas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonPlay=findViewById(R.id.Btn_jugar);

        scaleUp= AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scaleDown= AnimationUtils.loadAnimation(this, R.anim.scale_down);

        buttonPlay.setOnClickListener(this);
    }

    public void onClick(View v) {
        buttonPlay.startAnimation(scaleDown);
        buttonPlay.startAnimation(scaleUp);

        Intent intent;
        intent = new Intent(this, QuestionManager.class);
        startActivity(intent);
    }
}