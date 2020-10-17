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
    ArrayList<String[]> Preguntas = new ArrayList<String[]>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.Btn_jugar).setOnClickListener(this);

        int n_preg=Integer.parseInt(getResources().getString(this.getResources().getIdentifier("num_preguntas", "string", this.getPackageName())));

        int n_preguntas_totales=3;
        Random rnd = new Random();
        rnd.setSeed(6);//cambiar a la hora

        List<Integer> PosicionesDisponibles = new ArrayList<Integer>();
        for(int i=0;i<n_preguntas_totales;i++){
            PosicionesDisponibles.add(i);
        }

        //int randomNum = rnd.nextInt(99999);
        //((TextView)findViewById(R.id.txtVistaPrueba)).setText(""+randomNum);

        for(int i=0;i<n_preg;i++){
            int randomNum = rnd.nextInt((PosicionesDisponibles.size()));
            String nombre_preg="pregunta"+PosicionesDisponibles.get(randomNum);
            PosicionesDisponibles.remove(randomNum);
            //int idPreg = getResources().getStringArray(this.getResources().getIdentifier("pregunta0", "array", this.getPackageName()));
            //((TextView)findViewById(R.id.txtVistaPrueba)).setText(n_preg);
            Preguntas.add(getResources().getStringArray(this.getResources().getIdentifier(nombre_preg, "array", this.getPackageName())));
        }
        ((TextView)findViewById(R.id.txtVistaPrueba)).setText(Preguntas.get(0)[0]);
        ((TextView)findViewById(R.id.txtVistaPrueba2)).setText(Preguntas.get(1)[0]);
        ((TextView)findViewById(R.id.txtVistaPrueba3)).setText(Preguntas.get(2)[0]);
}

    public void onClick(View v) {

        Intent intent = new Intent(this,Pregunta_RadioButtons.class);
        intent.putExtra("Preguntas", Preguntas);
        startActivity(intent);
    }
}