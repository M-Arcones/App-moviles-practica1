package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonPlay;
    private Animation scaleUp, scaleDown;

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
        ArrayList<String[]> Preguntas = new ArrayList<String[]>();
        buttonPlay.startAnimation(scaleDown);
        buttonPlay.startAnimation(scaleUp);

        //int n_preg=Integer.parseInt(getResources().getString(this.getResources().getIdentifier("num_preguntas", "string", this.getPackageName())));
        int n_preguntas_totales=Integer.parseInt(getResources().getString((this.getResources().getIdentifier("num_preguntas", "string", this.getPackageName()))));
        Intent intent;
        intent = new Intent(this, QuestionManager.class);
        intent.putExtra("n_preguntas_totales", n_preguntas_totales);
        startActivity(intent);
    }
}