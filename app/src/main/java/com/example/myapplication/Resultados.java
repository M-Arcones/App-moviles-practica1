package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class Resultados extends AppCompatActivity implements View.OnClickListener{

    private Button buttonPlayAgain;
    private TextView textScore;
    private Animation scaleUp,scaleDown;

    private int n_preguntas_totales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);

        int puntuacion = (int) getIntent().getSerializableExtra("puntuacion");
        n_preguntas_totales= (int) getIntent().getSerializableExtra("n_preguntas_totales");
        textScore = findViewById(R.id.Text_Puntuacion);
        textScore.setText("Puntuacion Final \n \n" + puntuacion +" pts");

        buttonPlayAgain=findViewById(R.id.Button_VolverAJugar);

        scaleUp= AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scaleDown= AnimationUtils.loadAnimation(this, R.anim.scale_down);

        buttonPlayAgain.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        buttonPlayAgain.startAnimation(scaleDown);
        buttonPlayAgain.startAnimation(scaleUp);

        Intent intent;
        intent = new Intent(this, QuestionManager.class);
        intent.putExtra("n_preguntas_totales", n_preguntas_totales);
        startActivity(intent);
    }
}