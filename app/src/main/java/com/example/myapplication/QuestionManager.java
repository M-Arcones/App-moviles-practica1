package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

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
                    seekBar.setProgress(Integer.parseInt(((TextView)findViewById(R.id.TxtskbValorSeleccionado)).getText().toString())-Integer.parseInt(((TextView)findViewById(R.id.TxtSeekbarMinValue)).getText().toString()));
                    int valoue=Integer.parseInt(((TextView)findViewById(R.id.TxtskbValorSeleccionado)).getText().toString())-Integer.parseInt(((TextView)findViewById(R.id.TxtSeekbarMinValue)).getText().toString());
                    ((TextView) findViewById(R.id.TxtDebug)).setText(""+valoue);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        RadioButton RbtnRespImagen1 = (RadioButton) findViewById(R.id.RbtnRespImagen1);
        RadioButton RbtnRespImagen2 = (RadioButton) findViewById(R.id.RbtnRespImagen2);
        RadioButton RbtnRespImagen3 = (RadioButton) findViewById(R.id.RbtnRespImagen3);
        RadioButton RbtnRespImagen4 = (RadioButton) findViewById(R.id.RbtnRespImagen4);

        RbtnRespImagen1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((RadioButton) findViewById(R.id.RbtnRespImagen1)).setChecked(true);
                ((RadioButton) findViewById(R.id.RbtnRespImagen2)).setChecked(false);
                ((RadioButton) findViewById(R.id.RbtnRespImagen3)).setChecked(false);
                ((RadioButton) findViewById(R.id.RbtnRespImagen4)).setChecked(false);
            }
        });

        RbtnRespImagen2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((RadioButton) findViewById(R.id.RbtnRespImagen1)).setChecked(false);
                ((RadioButton) findViewById(R.id.RbtnRespImagen2)).setChecked(true);
                ((RadioButton) findViewById(R.id.RbtnRespImagen3)).setChecked(false);
                ((RadioButton) findViewById(R.id.RbtnRespImagen4)).setChecked(false);
            }
        });

        RbtnRespImagen3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((RadioButton) findViewById(R.id.RbtnRespImagen1)).setChecked(false);
                ((RadioButton) findViewById(R.id.RbtnRespImagen2)).setChecked(false);
                ((RadioButton) findViewById(R.id.RbtnRespImagen3)).setChecked(true);
                ((RadioButton) findViewById(R.id.RbtnRespImagen4)).setChecked(false);
            }
        });

        RbtnRespImagen4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((RadioButton) findViewById(R.id.RbtnRespImagen1)).setChecked(false);
                ((RadioButton) findViewById(R.id.RbtnRespImagen2)).setChecked(false);
                ((RadioButton) findViewById(R.id.RbtnRespImagen3)).setChecked(false);
                ((RadioButton) findViewById(R.id.RbtnRespImagen4)).setChecked(true);
            }
        });



    }

    public void onClick(View v) {
        if (Estado_validar==1){
            ((TextView) findViewById(R.id.BtnValidar_SigPregunta)).setText("Validar Respuesta");
            Estado_validar=0;
            mostarPregunta();
            deshabilitarCambiosLayout((LinearLayout) findViewById(R.id.LayoutMultipleRespuesta),true);
            deshabilitarCambiosLayout((LinearLayout) findViewById(R.id.LayoutRespuestaButtonImagen),true);
            deshabilitarCambiosLayout((LinearLayout) findViewById(R.id.LayoutSwitch),true);
            deshabilitarCambiosRadioGroup((RadioGroup) findViewById(R.id.Rgbtn_button),true);
            TipoPreguntaActual=Preguntas.get(0)[0];
        }
        else{
            if(Estado_validar==0) {
                deshabilitarCambiosLayout((LinearLayout) findViewById(R.id.LayoutMultipleRespuesta),false);
                deshabilitarCambiosRadioGroup((RadioGroup) findViewById(R.id.Rgbtn_button),false);
                deshabilitarCambiosLayout((LinearLayout) findViewById(R.id.LayoutSwitch),false);

                int valorResuesta=suma_Fallo;
                switch (TipoPreguntaActual){
                    case "Button":
                    case "Imagen":
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
                        //calcular puntos
                        if(Integer.parseInt(((TextView) findViewById(R.id.TxtskbValorSeleccionado)).getText().toString())==Integer.parseInt((Preguntas.get(0)[Preguntas.get(0).length-1]))){
                            valorResuesta=suma_Acierto;
                        }
                        //Corregir
                        if(valorResuesta==suma_Fallo){
                            /*Pintar Error*/
                            ((SeekBar)  findViewById(R.id.Skb_BarraRespuestas)).setProgress(Integer.parseInt((Preguntas.get(0)[Preguntas.get(0).length-1]))-Integer.parseInt((Preguntas.get(0)[2])));
                            int resultadoa=Integer.parseInt((Preguntas.get(0)[Preguntas.get(0).length-1]))-Integer.parseInt((Preguntas.get(0)[2]));
                            ((TextView)findViewById(R.id.TxtskbValorSeleccionado)).setText("" +Preguntas.get(0)[Preguntas.get(0).length-1]);
                        }else{

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
                    case "Switch":
                        if (((Switch)findViewById(R.id.Switch)).isChecked()==Boolean.parseBoolean(Preguntas.get(0)[Preguntas.get(0).length-1])){
                            valorResuesta=suma_Acierto;
                        }
                    break;
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

    @SuppressLint("WrongViewCast")
    public void mostarPregunta(){
        int n_respuestas=4;
        ((TextView) findViewById(R.id.TxtPregunta)).setText(Preguntas.get(0)[1]);
        List<Integer> PosicionesDisponiblesRespuesta = new ArrayList<Integer>();
        Random rnd = new Random();
        rnd.setSeed(6);//cambiar a la hora
        findViewById(R.id.LayoutRespuestaButton).setVisibility(View.GONE);
        findViewById(R.id.LayoutRespuestaSkb).setVisibility(View.GONE);
        findViewById(R.id.LayoutMultipleRespuesta).setVisibility(View.GONE);
        findViewById(R.id.LayoutSwitch).setVisibility(View.GONE);
        findViewById(R.id.Layout_ImagenPregunta).setVisibility(View.GONE);
        findViewById(R.id.LayoutRespuestaButtonImagen).setVisibility(View.GONE);

        switch (Preguntas.get(0)[0]){
            case "Button":
                findViewById(R.id.LayoutRespuestaButton).setVisibility(View.VISIBLE);
                for(int i=0;i<n_respuestas;i++){
                    PosicionesDisponiblesRespuesta.add(i+2);
                }
                for(int i=0;i<n_respuestas;i++){
                    int randomNum = rnd.nextInt((PosicionesDisponiblesRespuesta.size()));
                    switch (i){
                        case 0:
                            ((RadioButton) findViewById(R.id.RbtnResp1)).setChecked(false);
                            ((TextView) findViewById(R.id.RbtnResp1)).setText(Preguntas.get(0)[PosicionesDisponiblesRespuesta.get(randomNum)]);
                            break;
                        case 1:
                            ((RadioButton) findViewById(R.id.RbtnResp2)).setChecked(false);
                            ((TextView) findViewById(R.id.RbtnResp2)).setText(Preguntas.get(0)[PosicionesDisponiblesRespuesta.get(randomNum)]);
                            break;
                        case 2:
                            ((RadioButton) findViewById(R.id.RbtnResp3)).setChecked(false);
                            ((TextView) findViewById(R.id.RbtnResp3)).setText(Preguntas.get(0)[PosicionesDisponiblesRespuesta.get(randomNum)]);
                            break;
                        case 3:
                            ((RadioButton) findViewById(R.id.RbtnResp4)).setChecked(false);
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
                ((TextView) findViewById(R.id.TxtskbValorSeleccionado)).setText(""+((Integer.parseInt(Preguntas.get(0)[3])-Integer.parseInt(Preguntas.get(0)[2]))/2+Integer.parseInt(Preguntas.get(0)[2])));
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
                            ((CheckBox) findViewById(R.id.ChkB_Resp1)).setChecked(false);
                            ((TextView) findViewById(R.id.ChkB_Resp1)).setText(Preguntas.get(0)[PosicionesDisponiblesRespuesta.get(randomNum)]);
                            break;
                        case 1:
                            ((CheckBox) findViewById(R.id.ChkB_Resp2)).setChecked(false);
                            ((TextView) findViewById(R.id.ChkB_Resp2)).setText(Preguntas.get(0)[PosicionesDisponiblesRespuesta.get(randomNum)]);
                            break;
                        case 2:
                            ((CheckBox) findViewById(R.id.ChkB_Resp3)).setChecked(false);
                            ((TextView) findViewById(R.id.ChkB_Resp3)).setText(Preguntas.get(0)[PosicionesDisponiblesRespuesta.get(randomNum)]);
                            break;
                        case 3:
                            ((CheckBox) findViewById(R.id.ChkB_Resp4)).setChecked(false);
                            ((TextView) findViewById(R.id.ChkB_Resp4)).setText(Preguntas.get(0)[PosicionesDisponiblesRespuesta.get(randomNum)]);
                            break;
                        case 4:
                            ((CheckBox) findViewById(R.id.ChkB_Resp5)).setChecked(false);
                            ((TextView) findViewById(R.id.ChkB_Resp5)).setText(Preguntas.get(0)[PosicionesDisponiblesRespuesta.get(randomNum)]);
                            break;
                        case 5:
                            ((CheckBox) findViewById(R.id.ChkB_Resp6)).setChecked(false);
                            ((TextView) findViewById(R.id.ChkB_Resp6)).setText(Preguntas.get(0)[PosicionesDisponiblesRespuesta.get(randomNum)]);
                            break;
                    }
                    PosicionesDisponiblesRespuesta.remove(randomNum);
                }
            break;
            case "Imagen":
                findViewById(R.id.LayoutRespuestaButton).setVisibility(View.VISIBLE);
                findViewById(R.id.Layout_ImagenPregunta).setVisibility(View.VISIBLE);
                int imagenID = getResources().getIdentifier(Preguntas.get(0)[2] , "drawable", getPackageName());
                ((ImageView)findViewById(R.id.Img_pregunta)).setImageResource(imagenID);
                for(int i=0;i<n_respuestas;i++){
                    PosicionesDisponiblesRespuesta.add(i+3);
                }
                for(int i=0;i<n_respuestas;i++){
                    int randomNum = rnd.nextInt((PosicionesDisponiblesRespuesta.size()));
                    switch (i){
                        case 0:
                            ((RadioButton) findViewById(R.id.RbtnResp1)).setChecked(false);
                            ((TextView) findViewById(R.id.RbtnResp1)).setText(Preguntas.get(0)[PosicionesDisponiblesRespuesta.get(randomNum)]);
                            break;
                        case 1:
                            ((RadioButton) findViewById(R.id.RbtnResp2)).setChecked(false);
                            ((TextView) findViewById(R.id.RbtnResp2)).setText(Preguntas.get(0)[PosicionesDisponiblesRespuesta.get(randomNum)]);
                            break;
                        case 2:
                            ((RadioButton) findViewById(R.id.RbtnResp3)).setChecked(false);
                            ((TextView) findViewById(R.id.RbtnResp3)).setText(Preguntas.get(0)[PosicionesDisponiblesRespuesta.get(randomNum)]);
                            break;
                        case 3:
                            ((RadioButton) findViewById(R.id.RbtnResp4)).setChecked(false);
                            ((TextView) findViewById(R.id.RbtnResp4)).setText(Preguntas.get(0)[PosicionesDisponiblesRespuesta.get(randomNum)]);
                            break;
                    }
                    PosicionesDisponiblesRespuesta.remove(randomNum);
                }
            break;
            case "Switch":
                findViewById(R.id.LayoutSwitch).setVisibility(View.VISIBLE);
                ((Switch)findViewById(R.id.Switch)).setChecked(true);
            break;
            case "ButtonImagen":
                findViewById(R.id.LayoutRespuestaButtonImagen).setVisibility(View.VISIBLE);
                for(int i=0;i<n_respuestas;i++){
                    PosicionesDisponiblesRespuesta.add(i+3);
                }
                for(int i=0;i<n_respuestas;i++){
                    int randomNum = rnd.nextInt((PosicionesDisponiblesRespuesta.size()));
                    switch (i){
                        case 0:
                            ((RadioButton) findViewById(R.id.RbtnResp1)).setChecked(false);
                            ((TextView) findViewById(R.id.RbtnResp1)).setText(Preguntas.get(0)[PosicionesDisponiblesRespuesta.get(randomNum)]);
                            break;
                        case 1:
                            ((RadioButton) findViewById(R.id.RbtnResp2)).setChecked(false);
                            ((TextView) findViewById(R.id.RbtnResp2)).setText(Preguntas.get(0)[PosicionesDisponiblesRespuesta.get(randomNum)]);
                            break;
                        case 2:
                            ((RadioButton) findViewById(R.id.RbtnResp3)).setChecked(false);
                            ((TextView) findViewById(R.id.RbtnResp3)).setText(Preguntas.get(0)[PosicionesDisponiblesRespuesta.get(randomNum)]);
                            break;
                        case 3:
                            ((RadioButton) findViewById(R.id.RbtnResp4)).setChecked(false);
                            ((TextView) findViewById(R.id.RbtnResp4)).setText(Preguntas.get(0)[PosicionesDisponiblesRespuesta.get(randomNum)]);
                            break;
                    }
                    PosicionesDisponiblesRespuesta.remove(randomNum);
                }
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
