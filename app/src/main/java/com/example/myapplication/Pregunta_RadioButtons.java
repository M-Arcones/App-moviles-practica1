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

public class Pregunta_RadioButtons extends AppCompatActivity implements View.OnClickListener{
    ArrayList<String[]> Preguntas= new ArrayList<String[]>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta__radio_buttons);

        int n_preguntas_totales = (int) getIntent().getSerializableExtra("n_preguntas_totales");

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

        /**/
        mostarPregunta();
        Preguntas.remove(0);

        /*Borrar Codigo pruebas*/
        findViewById(R.id.BtnSiguientePregunta).setOnClickListener((View.OnClickListener) this);
    }

    public void onClick(View v) {
        if(Preguntas.size()>0){
            mostarPregunta();
            Preguntas.remove(0);
        }
    }

    public void mostarPregunta(){
        ((TextView) findViewById(R.id.TxtPregunta)).setText(Preguntas.get(0)[1]);
        List<Integer> PosicionesDisponiblesRespuesta = new ArrayList<Integer>();
        Random rnd = new Random();
        rnd.setSeed(6);//cambiar a la hora
        findViewById(R.id.LayoutRespuestaButton).setVisibility(View.INVISIBLE);

        switch (Preguntas.get(0)[0]){
            case "Button":
                findViewById(R.id.LayoutRespuestaButton).setVisibility(View.VISIBLE);
                int n_respuestas=4;
                for(int i=0;i<n_respuestas;i++){
                    PosicionesDisponiblesRespuesta.add(i+2);
                }
                for(int i=0;i<n_respuestas;i++){
                    int randomNum = rnd.nextInt((PosicionesDisponiblesRespuesta.size()));
                    switch (i){
                        case 0:
                            ((TextView) findViewById(R.id.RbtnResp1)).setText(Preguntas.get(0)[PosicionesDisponiblesRespuesta.get(randomNum)]);
                        break;
                        case 1:
                            ((TextView) findViewById(R.id.RbtnResp2)).setText(Preguntas.get(0)[PosicionesDisponiblesRespuesta.get(randomNum)]);
                        break;
                        case 2:
                            ((TextView) findViewById(R.id.RbtnResp3)).setText(Preguntas.get(0)[PosicionesDisponiblesRespuesta.get(randomNum)]);
                        break;
                        case 3:
                            ((TextView) findViewById(R.id.RbtnResp4)).setText(Preguntas.get(0)[PosicionesDisponiblesRespuesta.get(randomNum)]);
                        break;
                    }
                    PosicionesDisponiblesRespuesta.remove(randomNum);
                }
                break;
            case "Seekbar":
                findViewById(R.id.LayoutRespuestaButton).setVisibility(View.INVISIBLE);
            break;
            case "Iamgen":
                findViewById(R.id.LayoutRespuestaButton).setVisibility(View.INVISIBLE);
            break;

        }
    }

}