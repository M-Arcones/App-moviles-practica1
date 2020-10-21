package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuestionManager extends AppCompatActivity implements View.OnClickListener{
    ArrayList<String[]> Preguntas= new ArrayList<String[]>();
    SeekBar Skb_BarraRespuesta;
    int valorMinimo=0;
    int Estado_validar=0;
    int suma_Acierto=100;
    int suma_Fallo=-50;
    int Puntuacion=0;
    String TipoPreguntaActual;
    String Respuesta;

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
        TipoPreguntaActual=Preguntas.get(0)[0];

        findViewById(R.id.BtnValidar_SigPregunta).setOnClickListener((View.OnClickListener) this);

        /*Definir seekbar*/
        Skb_BarraRespuesta=(SeekBar)  findViewById(R.id.Skb_BarraRespuestas);
        Skb_BarraRespuesta.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(Estado_validar==0){
                    int calculo=Skb_BarraRespuesta.getProgress()+valorMinimo;
                    ((TextView) findViewById(R.id.TxtskbValorSeleccionado)).setText(""+calculo);
                }else
                {
                    //seekBar.setProgress(Integer.parseInt(Preguntas.get(0)[Preguntas.get(0).length-1]));
                    seekBar.setProgress(5);
                }
                ((TextView) findViewById(R.id.TxtDebug)).setText(""+Estado_validar);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

        });

    }

    public void onClick(View v) {
        if (Estado_validar==1){
                ((TextView) findViewById(R.id.BtnValidar_SigPregunta)).setText("Validar Respuesta");
                mostarPregunta();
                deshabilitarCambiosLayout((LinearLayout) findViewById(R.id.LayoutMultipleRespuesta),true);
                deshabilitarCambiosRadioGroup((RadioGroup) findViewById(R.id.Rgbtn_button),true);
                //((SeekBar) findViewById(R.id.Skb_BarraRespuestas)).setEnabled(false);
                TipoPreguntaActual=Preguntas.get(0)[0];
                Estado_validar=0;
        }
        else{
            if(Estado_validar==0) {
                deshabilitarCambiosLayout((LinearLayout) findViewById(R.id.LayoutMultipleRespuesta),false);
                deshabilitarCambiosRadioGroup((RadioGroup) findViewById(R.id.Rgbtn_button),false);
                //((SeekBar) findViewById(R.id.Skb_BarraRespuestas)).setEnabled(false);

                int valorResuesta=suma_Fallo;
                switch (TipoPreguntaActual){
                    case "Button":
                        RadioButton ArrayRespRadioButton[]= { ((RadioButton)findViewById(R.id.RbtnResp1)),
                                ((RadioButton)findViewById(R.id.RbtnResp2)),
                                ((RadioButton)findViewById(R.id.RbtnResp3)),
                                ((RadioButton)findViewById(R.id.RbtnResp4))};
                        for (int i=0;i<4;i++){
                            if(ArrayRespRadioButton[i].isChecked()){
                                if(ArrayRespRadioButton[i].getText().equals(Preguntas.get(0)[Preguntas.get(0).length-1])){
                                    valorResuesta=suma_Acierto;
                                }
                                i=4;
                            }
                        }
                    break;
                    case "Seekbar":
                        if(Integer.parseInt(((TextView) findViewById(R.id.TxtskbValorSeleccionado)).getText().toString())==Integer.parseInt((Preguntas.get(0)[Preguntas.get(0).length-1]))){
                            valorResuesta=suma_Acierto;
                        }
                    break;
                    case "Multiple":
                        CheckBox ArrayRespCheckBox[]={((CheckBox)findViewById(R.id.ChkB_Resp1)),
                                                        ((CheckBox)findViewById(R.id.ChkB_Resp2)),
                                                        ((CheckBox)findViewById(R.id.ChkB_Resp3)),
                                                        ((CheckBox)findViewById(R.id.ChkB_Resp4)),
                                                        ((CheckBox)findViewById(R.id.ChkB_Resp5)),
                                                        ((CheckBox)findViewById(R.id.ChkB_Resp6))};
                        int N_respuestas=countChar(Preguntas.get(0)[Preguntas.get(0).length-1],'|');
                        int cont_respCorrrectas=0;
                        for (int i=0;i<6;i++){
                            if(ArrayRespCheckBox[i].isChecked()){
                                if(Preguntas.get(0)[Preguntas.get(0).length-1].contains(ArrayRespCheckBox[i].getText())){
                                    cont_respCorrrectas+=1;
                                }else {
                                    cont_respCorrrectas=0;
                                    i=6;
                                }
                            }
                        }
                        if(cont_respCorrrectas==N_respuestas){
                            valorResuesta=suma_Acierto;
                        }
                }
                Puntuacion=Math.max(Puntuacion+valorResuesta,0);
                ((TextView) findViewById(R.id.TxtPuntuacion)).setText("Puntuación: "+Puntuacion);

                Preguntas.remove(0);
                if(Preguntas.size()>0){
                    ((TextView) findViewById(R.id.BtnValidar_SigPregunta)).setText("Siguiente Pregunta");
                    Estado_validar = 1;
                }else{
                    ((TextView) findViewById(R.id.BtnValidar_SigPregunta)).setText("Finalizar");
                    Estado_validar=2;
                }
            }else{
                /*Actividad resultados*/
            }
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

    public int countChar(String str, char c)
    {
        int count = 0;
        for(int i=0; i < str.length(); i++)
        {    if(str.charAt(i) == c)
            count++;
        }
        return count+1;
    }

    public void deshabilitarCambiosLayout(LinearLayout layout,boolean habilitar){
        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);
            child.setClickable(habilitar);
        }
    }
    public void deshabilitarCambiosRadioGroup(RadioGroup RadGroup,boolean habilitar){
        for (int i = 0; i < RadGroup.getChildCount(); i++) {
            View child = RadGroup.getChildAt(i);
            child.setClickable(habilitar);
        }
    }
}