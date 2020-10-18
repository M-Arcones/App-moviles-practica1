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

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.Btn_jugar).setOnClickListener(this);
}

    public void onClick(View v) {
        ArrayList<String[]> Preguntas = new ArrayList<String[]>();

        //int n_preg=Integer.parseInt(getResources().getString(this.getResources().getIdentifier("num_preguntas", "string", this.getPackageName())));
        int n_preguntas_totales=3;
        Intent intent;
        intent = new Intent(this,Pregunta_RadioButtons.class);
        intent.putExtra("n_preguntas_totales", n_preguntas_totales);
        startActivity(intent);
    }
}