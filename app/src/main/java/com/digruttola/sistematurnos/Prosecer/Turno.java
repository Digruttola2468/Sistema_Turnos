package com.digruttola.sistematurnos.Prosecer;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Turno {

    private String fecha;
    private String hora;
    private String nombre;

    public Turno(String nombre,String hora,String fecha){
        this.fecha = fecha;
        this.hora = hora;
        this.nombre = nombre;
    }

    //GETTERS
    public String getFecha() {
        return fecha;
    }
    public String getHora() {
        return hora;
    }
    public String getNombre() {
        return nombre;
    }

}
