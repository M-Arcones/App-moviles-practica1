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
        Random rnd = new Random();
        rnd.setSeed(6);//cambiar a la hora

        /*Crea una lista de las posiciones disponibles para ordenar de manera aletoria las preguntas*/
        List<Integer> PosicionesDisponibles = new ArrayList<Integer>();
        for(int i=0;i<n_preguntas_totales;i++){
            PosicionesDisponibles.add(i);
        }

        /*Añade en la lista de preguntas las preguntas leidas de string.xml cogidas de manera aleatoria por su "name" */
        for(int i=0;i<n_preguntas_totales;i++){
            int randomNum = rnd.nextInt((PosicionesDisponibles.size()));
            String nombre_preg="pregunta"+PosicionesDisponibles.get(randomNum);
            PosicionesDisponibles.remove(randomNum);
            Preguntas.add(getResources().getStringArray(this.getResources().getIdentifier(nombre_preg, "array", this.getPackageName())));
        }

        /*Pasa a la actividad la lista Preguntas y la puntuacion*/
        Intent intent;
        switch (Preguntas.get(0)[0]) {
            case "Button":
                intent = new Intent(this,Pregunta_RadioButtons.class);
                break;
            case "Seekbar":
                intent = new Intent(this,Pregunta_Seekbar.class);
                break;
            case "Imagen":
                intent = new Intent(this,Pregunta_Imagen.class);
                break;
            default:
                intent = new Intent(this,Pregunta_RadioButtons.class);
                break;
        }
        intent.putExtra("Preguntas", Preguntas);
        intent.putExtra("Puntuacion", 0);
        startActivity(intent);
    }
}