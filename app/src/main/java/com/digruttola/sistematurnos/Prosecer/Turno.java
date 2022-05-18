package com.digruttola.sistematurnos.Prosecer;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Turno {

    private String id;
    private String fecha;
    private String hora;
    private String nombre;

    public Turno(String id,String nombre,String hora,String fecha){
        this.fecha = fecha;
        this.hora = hora;
        this.nombre = nombre;
        this.id = id;
    }
    public Turno(String nombre,String hora,String fecha){
        this.fecha = fecha;
        this.hora = hora;
        this.nombre = nombre;
    }
    @Override
    public String toString() {
        return "Turno{" +
                "ID='" + id + '\'' +
                "fecha='" + fecha + '\'' +
                ", hora='" + hora + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }

    //SETTER ID
    public void setId(String id) {
        this.id = id;
    }

    //GETTERS
    public String getId() {
        return id;
    }
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
