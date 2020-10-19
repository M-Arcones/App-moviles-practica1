package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Pregunta_RadioButtons extends AppCompatActivity implements View.OnClickListener{
    ArrayList<String[]> Preguntas= new ArrayList<String[]>();
    SeekBar Skb_BarraRespuesta;
    int valorMinimo=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta__radio_buttons);

        int n_preguntas_totales = (int) getIntent().getSerializableExtra("n_preguntas_totales");
        ((TextView) findViewById(R.id.TxtPreguntasContestadas)).setText(""+n_preguntas_totales);

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
        Skb_BarraRespuesta=(SeekBar)  findViewById(R.id.Skb_BarraRespuestas);
        Skb_BarraRespuesta.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int calculo=Skb_BarraRespuesta.getProgress()+valorMinimo;
                ((TextView) findViewById(R.id.TxtskbValorSeleccionado)).setText(""+calculo);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        }
        );
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
        findViewById(R.id.LayoutRespuestaButton).setVisibility(View.GONE);
        findViewById(R.id.LayoutRespuestaSkb).setVisibility(View.GONE);
        findViewById(R.id.LayoutMultipleRespuesta).setVisibility(View.GONE);

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
                findViewById(R.id.LayoutRespuestaSkb).setVisibility(View.VISIBLE);
                ((TextView) findViewById(R.id.TxtSeekbarMinValue)).setText(Preguntas.get(0)[2]);
                ((TextView) findViewById(R.id.TxtSeekbarMaxValue)).setText(Preguntas.get(0)[3]);
                ((SeekBar)findViewById(R.id.Skb_BarraRespuestas)).setProgress((Integer.parseInt(Preguntas.get(0)[3])-Integer.parseInt(Preguntas.get(0)[2]))/2);
                ((SeekBar)findViewById(R.id.Skb_BarraRespuestas)).setMax(Integer.parseInt(Preguntas.get(0)[3])-Integer.parseInt(Preguntas.get(0)[2]));
                ((TextView) findViewById(R.id.TxtskbValorSeleccionado)).setText(""+(Integer.parseInt(Preguntas.get(0)[3])-Integer.parseInt(Preguntas.get(0)[2]))/2);
                valorMinimo=Integer.parseInt(Preguntas.get(0)[2]);
            break;
            case "Multiple":
                findViewById(R.id.LayoutMultipleRespuesta).setVisibility(View.VISIBLE);
                int n_respuestas_multi=6;
                for(int i=0;i<n_respuestas_multi;i++){
                    PosicionesDisponiblesRespuesta.add(i+2);
                }
                for(int i=0;i<n_respuestas_multi;i++) {
                    int randomNum = rnd.nextInt((PosicionesDisponiblesRespuesta.size()));
                    switch (i) {
                        case 0:
                            ((TextView) findViewById(R.id.ChkB_Resp1)).setText(Preguntas.get(0)[PosicionesDisponiblesRespuesta.get(randomNum)]);
                            break;
                        case 1:
                            ((TextView) findViewById(R.id.ChkB_Resp2)).setText(Preguntas.get(0)[PosicionesDisponiblesRespuesta.get(randomNum)]);
                            break;
                        case 2:
                            ((TextView) findViewById(R.id.ChkB_Resp3)).setText(Preguntas.get(0)[PosicionesDisponiblesRespuesta.get(randomNum)]);
                            break;
                        case 3:
                            ((TextView) findViewById(R.id.ChkB_Resp4)).setText(Preguntas.get(0)[PosicionesDisponiblesRespuesta.get(randomNum)]);
                            break;
                        case 4:
                            ((TextView) findViewById(R.id.ChkB_Resp5)).setText(Preguntas.get(0)[PosicionesDisponiblesRespuesta.get(randomNum)]);
                            break;
                        case 5:
                            ((TextView) findViewById(R.id.ChkB_Resp6)).setText(Preguntas.get(0)[PosicionesDisponiblesRespuesta.get(randomNum)]);
                            break;
                    }
                    PosicionesDisponiblesRespuesta.remove(randomNum);
                }
            break;
            case "Iamgen":
                findViewById(R.id.LayoutRespuestaButton).setVisibility(View.INVISIBLE);
            break;

        }
    }
}


/*




     <string-array name="pregunta3">
        <item>Multiple</item>
        <item>Pregunta 3</item>
        <item>Resp1</item>
        <item>Resp2</item>
        <item>Resp3</item>
        <item>Resp4</item>
        <item>Resp5</item>
        <item>Resp6</item>
        <item>Resp2|Resp5</item>
    </string-array>

 */