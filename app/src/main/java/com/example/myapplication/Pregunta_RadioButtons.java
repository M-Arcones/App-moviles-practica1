package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Pregunta_RadioButtons extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta__radio_buttons);

        ArrayList<String[]> Preguntas = (ArrayList<String[]>) getIntent().getSerializableExtra("Preguntas");
        int Puntuacion = (int) getIntent().getSerializableExtra("Puntuacion");
        ((TextView)findViewById(R.id.textView)).setText(""+ Puntuacion);

    }
}