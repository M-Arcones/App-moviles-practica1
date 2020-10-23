package com.example.myapplication;

import java.util.ArrayList;

public class Pregunta {
    String tipo, pregunta, solucion, explicacion;
    ArrayList<String> respuestas = new ArrayList<>();
    ArrayList<String> imagenes = new ArrayList<>();
    int min, max;

    public Pregunta(String tipo){
        this.tipo=tipo;
    }
}
