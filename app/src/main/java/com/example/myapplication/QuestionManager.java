package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import java.util.Calendar;
import java.util.Date;
import java.time.ZonedDateTime;

public class QuestionManager extends AppCompatActivity implements View.OnClickListener, Marcador.OnFragmentInteractionListener, Question.OnFragmentInteractionListener, BotonQuestion.OnFragmentInteractionListener{
    ArrayList<String[]> Preguntas= new ArrayList<String[]>();
    SeekBar Skb_BarraRespuesta;
    int valorMinimo=0;
    int Estado_validar=0;
    int suma_Acierto=100;
    int suma_Fallo=-50;
    int Puntuacion=0;
    int n_preguntas_totales;
    String TipoPreguntaActual;
    String Respuesta;
    int n_pregunta=1;
    Animation scaleUp, scaleDown;
    Button btn_validar;
    Date date;
    Random rnd;

    ArrayList<Pregunta> todasPreguntas = new ArrayList<>();
    ArrayList<Pregunta> preguntas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_manager);

        scaleUp= AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scaleDown= AnimationUtils.loadAnimation(this, R.anim.scale_down);
        btn_validar=findViewById(R.id.BtnValidar_SigPregunta);

        date = new Date();
        rnd = new Random();
        rnd.setSeed(date.getTime());//cambiar a la hora

        parseXML();

        n_preguntas_totales = todasPreguntas.size();
        ((TextView)findViewById(R.id.TxtPreguntasContestadas)).setText(n_pregunta+"/"+n_preguntas_totales);



        /*Crea una lista de las posiciones disponibles para ordenar de manera aletoria las preguntas*/
        List<Integer> PosicionesDisponibles = new ArrayList<Integer>();
        for(int i=0;i<n_preguntas_totales;i++){
            PosicionesDisponibles.add(i);
        }

        /*Añade en la lista de preguntas las preguntas leidas de string.xml cogidas de manera aleatoria por su "name" */
        for(int i=0;i<n_preguntas_totales;i++){
            int randomNum = rnd.nextInt((PosicionesDisponibles.size()));
            preguntas.add(todasPreguntas.get(PosicionesDisponibles.get(randomNum)));
            PosicionesDisponibles.remove(randomNum);
        }

        /**/
        mostarPregunta();
        //TipoPreguntaActual=preguntas.get(0).tipo;
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
        btn_validar.startAnimation(scaleDown);
        btn_validar.startAnimation(scaleUp);
        int valorResuesta=suma_Fallo;
        if (Estado_validar==1){
            ((TextView) findViewById(R.id.BtnValidar_SigPregunta)).setText("Validar Respuesta");
            Estado_validar=0;
            mostarPregunta();
            deshabilitarCambiosLayout((LinearLayout) findViewById(R.id.LayoutMultipleRespuesta),true);
            deshabilitarCambiosLayout((LinearLayout) findViewById(R.id.LayoutRespuestaButtonImagen),true);
            deshabilitarCambiosLayout((LinearLayout) findViewById(R.id.LayoutSwitch),true);
            deshabilitarCambiosRadioGroup((RadioGroup) findViewById(R.id.Rgbtn_button),true);
            TipoPreguntaActual=preguntas.get(0).tipo;
            n_pregunta++;
            ((TextView)findViewById(R.id.TxtPreguntasContestadas)).setText(n_pregunta+"/"+n_preguntas_totales);
        }
        else{
            if(Estado_validar==0) {
                deshabilitarCambiosLayout((LinearLayout) findViewById(R.id.LayoutMultipleRespuesta),false);
                deshabilitarCambiosLayout((LinearLayout) findViewById(R.id.LayoutRespuestaButtonImagen),true);
                deshabilitarCambiosRadioGroup((RadioGroup) findViewById(R.id.Rgbtn_button),false);
                deshabilitarCambiosLayout((LinearLayout) findViewById(R.id.LayoutSwitch),false);


                switch (preguntas.get(0).tipo){
                    case "Button":
                    case "Imagen":
                        RadioButton ArrayRespRadioButton[]= { ((RadioButton)findViewById(R.id.RbtnResp1)),
                                ((RadioButton)findViewById(R.id.RbtnResp2)),
                                ((RadioButton)findViewById(R.id.RbtnResp3)),
                                ((RadioButton)findViewById(R.id.RbtnResp4))};
                        for (int i=0;i<4;i++){
                            if(ArrayRespRadioButton[i].isChecked()){
                                if(ArrayRespRadioButton[i].getText().equals(preguntas.get(0).solucion)){
                                    valorResuesta=suma_Acierto;
                                }
                                i=4;
                            }
                        }
                    break;
                    case "Seekbar":
                        //calcular puntos
                        if(Integer.parseInt(((TextView) findViewById(R.id.TxtskbValorSeleccionado)).getText().toString())==Integer.parseInt((preguntas.get(0).solucion))){
                            valorResuesta=suma_Acierto;
                        }
                        //Corregir
                        /*if(valorResuesta==suma_Fallo){
                            ((SeekBar)  findViewById(R.id.Skb_BarraRespuestas)).setProgress(Integer.parseInt((Preguntas.get(0)[Preguntas.get(0).length-1]))-Integer.parseInt((Preguntas.get(0)[2])));
                            int resultadoa=Integer.parseInt((Preguntas.get(0)[Preguntas.get(0).length-1]))-Integer.parseInt((Preguntas.get(0)[2]));
                            ((TextView)findViewById(R.id.TxtskbValorSeleccionado)).setText("" +Preguntas.get(0)[Preguntas.get(0).length-1]);
                        }else{

                        }*/
                    break;
                    case "Multiple":
                        CheckBox ArrayRespCheckBox[]={((CheckBox)findViewById(R.id.ChkB_Resp1)),
                                                        ((CheckBox)findViewById(R.id.ChkB_Resp2)),
                                                        ((CheckBox)findViewById(R.id.ChkB_Resp3)),
                                                        ((CheckBox)findViewById(R.id.ChkB_Resp4)),
                                                        ((CheckBox)findViewById(R.id.ChkB_Resp5)),
                                                        ((CheckBox)findViewById(R.id.ChkB_Resp6))};
                        int N_respuestas=countChar(preguntas.get(0).solucion,'|');
                        int cont_respCorrrectas=0;
                        for (int i=0;i<preguntas.get(0).respuestas.size();i++){
                            if(ArrayRespCheckBox[i].isChecked()){
                                if(preguntas.get(0).solucion.contains(ArrayRespCheckBox[i].getText())){
                                    cont_respCorrrectas+=1;
                                }else {
                                    cont_respCorrrectas=0;
                                    i=preguntas.get(0).respuestas.size();
                                }
                            }
                        }
                        if(cont_respCorrrectas==N_respuestas){
                            valorResuesta=suma_Acierto;
                        }
                    break;
                    case "Switch":
                        if (((Switch)findViewById(R.id.Switch)).isChecked()==Boolean.parseBoolean(preguntas.get(0).solucion)){
                            valorResuesta=suma_Acierto;
                        }
                    break;
                    case "ButtonImagen":
                        RadioButton ArrayRespRadioButtonImagen[]= { ((RadioButton)findViewById(R.id.RbtnRespImagen1)),
                                                                    ((RadioButton)findViewById(R.id.RbtnRespImagen2)),
                                                                    ((RadioButton)findViewById(R.id.RbtnRespImagen3)),
                                                                    ((RadioButton)findViewById(R.id.RbtnRespImagen4))};
                        for (int i=0;i<4;i++){
                            if(ArrayRespRadioButtonImagen[i].isChecked()){
                                if(ArrayRespRadioButtonImagen[i].getText().equals(preguntas.get(0).solucion)){
                                    valorResuesta=suma_Acierto;
                                }
                                i=4;
                            }
                        }
                    break;
                }
                findViewById(R.id.Layout_AciertoFallo).setVisibility(View.VISIBLE);
                if(valorResuesta==suma_Acierto){
                    ((TextView) findViewById(R.id.Txt_RespuestaAcierto)).setText("ACIERTO");
                    ((TextView) findViewById(R.id.Txt_RespuestaAcierto)).setBackgroundResource(R.drawable.panel_acierto);
                }else{
                    ((TextView) findViewById(R.id.Txt_RespuestaAcierto)).setText("FALLO");
                    ((TextView) findViewById(R.id.Txt_RespuestaAcierto)).setBackgroundResource(R.drawable.panel_fallo);
                }
                ((TextView) findViewById(R.id.Txt_Explicacion)).setText(preguntas.get(0).explicacion);

                Puntuacion=Math.max(Puntuacion+valorResuesta,0);
                ((TextView) findViewById(R.id.TxtPuntuacion)).setText("Puntuación: "+Puntuacion);

                preguntas.remove(0);
                if(preguntas.size()>0){
                    ((TextView) findViewById(R.id.BtnValidar_SigPregunta)).setText("Siguiente Pregunta");
                    Estado_validar = 1;
                }else{
                    ((TextView) findViewById(R.id.BtnValidar_SigPregunta)).setText("Finalizar");
                    Estado_validar=2;
                }
            }else{
                Intent intent;
                intent = new Intent(this, Resultados.class);
                intent.putExtra("puntuacion", Puntuacion);
                intent.putExtra("n_preguntas_totales", n_preguntas_totales);
                startActivity(intent);
            }
        }
    }

    @SuppressLint("WrongViewCast")
    public void mostarPregunta(){

        int n_respuestas = preguntas.get(0).respuestas.size();
        int imagenID;

        ((TextView) findViewById(R.id.TxtPregunta)).setText(preguntas.get(0).pregunta);

        List<Integer> PosicionesDisponiblesRespuesta = new ArrayList<Integer>();

        findViewById(R.id.LayoutRespuestaButton).setVisibility(View.GONE);
        findViewById(R.id.LayoutRespuestaSkb).setVisibility(View.GONE);
        findViewById(R.id.LayoutMultipleRespuesta).setVisibility(View.GONE);
        findViewById(R.id.LayoutSwitch).setVisibility(View.GONE);
        findViewById(R.id.Layout_ImagenPregunta).setVisibility(View.GONE);
        findViewById(R.id.LayoutRespuestaButtonImagen).setVisibility(View.GONE);
        findViewById(R.id.Layout_AciertoFallo).setVisibility(View.GONE);
        switch (preguntas.get(0).tipo){
            case "Button":
                ((RadioGroup) findViewById(R.id.Rgbtn_button)).clearCheck();
                findViewById(R.id.LayoutRespuestaButton).setVisibility(View.VISIBLE);
                for(int i=0;i<n_respuestas;i++){
                    PosicionesDisponiblesRespuesta.add(i);
                }
                for(int i=0;i<n_respuestas;i++){
                    int randomNum = rnd.nextInt((PosicionesDisponiblesRespuesta.size()));
                    switch (i){
                        case 0:
                            ((TextView) findViewById(R.id.RbtnResp1)).setText(preguntas.get(0).respuestas.get(PosicionesDisponiblesRespuesta.get(randomNum)));
                            break;
                        case 1:
                            ((TextView) findViewById(R.id.RbtnResp2)).setText(preguntas.get(0).respuestas.get(PosicionesDisponiblesRespuesta.get(randomNum)));
                            break;
                        case 2:
                            ((TextView) findViewById(R.id.RbtnResp3)).setText(preguntas.get(0).respuestas.get(PosicionesDisponiblesRespuesta.get(randomNum)));
                            break;
                        case 3:
                            ((TextView) findViewById(R.id.RbtnResp4)).setText(preguntas.get(0).respuestas.get(PosicionesDisponiblesRespuesta.get(randomNum)));
                            break;
                    }
                    PosicionesDisponiblesRespuesta.remove(randomNum);
                }
                break;
            case "Seekbar":
                findViewById(R.id.LayoutRespuestaSkb).setVisibility(View.VISIBLE);
                ((TextView) findViewById(R.id.TxtSeekbarMinValue)).setText(""+preguntas.get(0).min);
                ((TextView) findViewById(R.id.TxtSeekbarMaxValue)).setText(""+preguntas.get(0).max);
                ((SeekBar)findViewById(R.id.Skb_BarraRespuestas)).setProgress((preguntas.get(0).max - preguntas.get(0).min)/2);
                ((SeekBar)findViewById(R.id.Skb_BarraRespuestas)).setMax(preguntas.get(0).max-preguntas.get(0).min);
                ((TextView) findViewById(R.id.TxtskbValorSeleccionado)).setText(""+((preguntas.get(0).max-preguntas.get(0).min)/2+preguntas.get(0).min));
                valorMinimo=preguntas.get(0).min;
            break;
            case "Multiple":
                findViewById(R.id.LayoutMultipleRespuesta).setVisibility(View.VISIBLE);
                for(int i=0;i<n_respuestas;i++){
                    PosicionesDisponiblesRespuesta.add(i);
                }
                for(int i=0;i<n_respuestas;i++) {
                    int randomNum = rnd.nextInt((PosicionesDisponiblesRespuesta.size()));
                    switch (i) {
                        case 0:
                            ((CheckBox) findViewById(R.id.ChkB_Resp1)).setChecked(false);
                            ((TextView) findViewById(R.id.ChkB_Resp1)).setText(preguntas.get(0).respuestas.get(PosicionesDisponiblesRespuesta.get(randomNum)));
                            break;
                        case 1:
                            ((CheckBox) findViewById(R.id.ChkB_Resp2)).setChecked(false);
                            ((TextView) findViewById(R.id.ChkB_Resp2)).setText(preguntas.get(0).respuestas.get(PosicionesDisponiblesRespuesta.get(randomNum)));
                            break;
                        case 2:
                            ((CheckBox) findViewById(R.id.ChkB_Resp3)).setChecked(false);
                            ((TextView) findViewById(R.id.ChkB_Resp3)).setText(preguntas.get(0).respuestas.get(PosicionesDisponiblesRespuesta.get(randomNum)));
                            break;
                        case 3:
                            ((CheckBox) findViewById(R.id.ChkB_Resp4)).setChecked(false);
                            ((TextView) findViewById(R.id.ChkB_Resp4)).setText(preguntas.get(0).respuestas.get(PosicionesDisponiblesRespuesta.get(randomNum)));
                            break;
                        case 4:
                            ((CheckBox) findViewById(R.id.ChkB_Resp5)).setChecked(false);
                            ((TextView) findViewById(R.id.ChkB_Resp5)).setText(preguntas.get(0).respuestas.get(PosicionesDisponiblesRespuesta.get(randomNum)));
                            break;
                        case 5:
                            ((CheckBox) findViewById(R.id.ChkB_Resp6)).setChecked(false);
                            ((TextView) findViewById(R.id.ChkB_Resp6)).setText(preguntas.get(0).respuestas.get(PosicionesDisponiblesRespuesta.get(randomNum)));
                            break;
                    }
                    PosicionesDisponiblesRespuesta.remove(randomNum);
                }
            break;
            case "Imagen":
                ((RadioGroup) findViewById(R.id.Rgbtn_button)).clearCheck();
                findViewById(R.id.LayoutRespuestaButton).setVisibility(View.VISIBLE);
                findViewById(R.id.Layout_ImagenPregunta).setVisibility(View.VISIBLE);

                imagenID = this.getResources().getIdentifier(preguntas.get(0).imagenes.get(0), "raw", this.getPackageName());
                ((ImageView)findViewById(R.id.Img_pregunta)).setImageResource(imagenID);
                for(int i=0;i<n_respuestas;i++){
                    PosicionesDisponiblesRespuesta.add(i);
                }
                for(int i=0;i<n_respuestas;i++){
                    int randomNum = rnd.nextInt((PosicionesDisponiblesRespuesta.size()));
                    switch (i){
                        case 0:
                            ((TextView) findViewById(R.id.RbtnResp1)).setText(preguntas.get(0).respuestas.get(PosicionesDisponiblesRespuesta.get(randomNum)));
                            break;
                        case 1:
                            ((TextView) findViewById(R.id.RbtnResp2)).setText(preguntas.get(0).respuestas.get(PosicionesDisponiblesRespuesta.get(randomNum)));
                            break;
                        case 2:
                            ((TextView) findViewById(R.id.RbtnResp3)).setText(preguntas.get(0).respuestas.get(PosicionesDisponiblesRespuesta.get(randomNum)));
                            break;
                        case 3:
                            ((TextView) findViewById(R.id.RbtnResp4)).setText(preguntas.get(0).respuestas.get(PosicionesDisponiblesRespuesta.get(randomNum)));
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
                for(int i=0;i<preguntas.get(0).imagenes.size();i++){
                    PosicionesDisponiblesRespuesta.add(i);
                }
                for(int i=0;i<preguntas.get(0).imagenes.size();i++){
                    int randomNum = rnd.nextInt((PosicionesDisponiblesRespuesta.size()));
                    switch (i){
                        case 0:
                            imagenID = this.getResources().getIdentifier(preguntas.get(0).imagenes.get(PosicionesDisponiblesRespuesta.get(randomNum)), "raw", this.getPackageName());
                            ((RadioButton) findViewById(R.id.RbtnRespImagen1)).setBackgroundResource(imagenID);
                            ((RadioButton) findViewById(R.id.RbtnRespImagen1)).setChecked(false);
                            ((TextView) findViewById(R.id.RbtnRespImagen1)).setText(preguntas.get(0).imagenes.get(PosicionesDisponiblesRespuesta.get(randomNum)));
                            break;
                        case 1:
                            imagenID = this.getResources().getIdentifier(preguntas.get(0).imagenes.get(PosicionesDisponiblesRespuesta.get(randomNum)), "raw", this.getPackageName());
                            ((RadioButton) findViewById(R.id.RbtnRespImagen2)).setBackgroundResource(imagenID);
                            ((RadioButton) findViewById(R.id.RbtnRespImagen2)).setChecked(false);
                            ((TextView) findViewById(R.id.RbtnRespImagen2)).setText(preguntas.get(0).imagenes.get(PosicionesDisponiblesRespuesta.get(randomNum)));
                            break;
                        case 2:
                            imagenID = this.getResources().getIdentifier(preguntas.get(0).imagenes.get(PosicionesDisponiblesRespuesta.get(randomNum)), "raw", this.getPackageName());
                            ((RadioButton) findViewById(R.id.RbtnRespImagen3)).setBackgroundResource(imagenID);
                            ((RadioButton) findViewById(R.id.RbtnRespImagen3)).setChecked(false);
                            ((TextView) findViewById(R.id.RbtnRespImagen3)).setText(preguntas.get(0).imagenes.get(PosicionesDisponiblesRespuesta.get(randomNum)));
                            break;
                        case 3:
                            imagenID = this.getResources().getIdentifier(preguntas.get(0).imagenes.get(PosicionesDisponiblesRespuesta.get(randomNum)), "raw", this.getPackageName());
                            ((RadioButton) findViewById(R.id.RbtnRespImagen4)).setBackgroundResource(imagenID);
                            ((RadioButton) findViewById(R.id.RbtnRespImagen4)).setChecked(false);
                            ((TextView) findViewById(R.id.RbtnRespImagen4)).setText(preguntas.get(0).imagenes.get(PosicionesDisponiblesRespuesta.get(randomNum)));
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


    //Coger fichero XML de preguntas de la caprte Raw
    private void parseXML(){
        XmlPullParserFactory parserFactory;
        try{
            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();
            InputStream recursoRaw = getResources().openRawResource(R.raw.preguntas);
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(recursoRaw,null);

            processParsing(parser);

        }catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
    }

    //Leer XML especifico de preguntas
    private void processParsing(XmlPullParser parser) throws XmlPullParserException, IOException {

        int eventType = parser.getEventType();
        Pregunta preguntaActual = null;

        while(eventType != XmlPullParser.END_DOCUMENT){
            String etiqueta = null;

            switch (eventType) {
                case XmlPullParser.START_TAG:
                    etiqueta = parser.getName();

                    if ("tipo".equals(etiqueta)) {
                        switch (parser.nextText()) {
                            case "Button":
                                preguntaActual = new Pregunta("Button");
                                break;
                            case "Seekbar":
                                preguntaActual = new Pregunta("Seekbar");
                                break;
                            case "Imagen":
                                preguntaActual = new Pregunta("Imagen");
                                break;
                            case "Multiple":
                                preguntaActual = new Pregunta("Multiple");
                                break;
                            case "Switch":
                                preguntaActual = new Pregunta("Switch");
                                break;
                            case "ButtonImagen":
                                preguntaActual = new Pregunta("ButtonImagen");
                                break;
                        }
                    } else if (preguntaActual != null) {
                        if ("ask".equals(etiqueta)) {
                            preguntaActual.pregunta = parser.nextText();
                        } else if ("explicacion".equals(etiqueta)) {
                            preguntaActual.explicacion = parser.nextText();
                        } else if ("solucion".equals(etiqueta)) {
                            preguntaActual.solucion = parser.nextText();
                            todasPreguntas.add(preguntaActual);
                            preguntaActual=null;
                        } else if ("respuesta".equals(etiqueta)) {
                            preguntaActual.respuestas.add(parser.nextText());
                        } else if ("min".equals(etiqueta)) {
                            preguntaActual.min = Integer.parseInt(parser.nextText());
                        } else if ("max".equals(etiqueta)) {
                            preguntaActual.max = Integer.parseInt(parser.nextText());
                        } else if("imagen".equals(etiqueta)){
                            preguntaActual.imagenes.add(parser.nextText());
                        }
                    }
                    break;
            }
            eventType = parser.next();
        }
    }
}
