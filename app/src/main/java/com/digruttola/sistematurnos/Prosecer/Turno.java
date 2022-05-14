package com.digruttola.sistematurnos.Prosecer;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class Turno implements getUI {

    private String fecha;
    private String hora;

    public Date formatFechaHora(){
        try {
            Date date = new SimpleDateFormat("dd/MM/yyyyhh:mm").parse(fecha + hora);
            return date;
        }catch (Exception e){
            Log.w("TURNO",e.getMessage());
            return null;
        }
    }

    public String getDate(){
        return formatFechaHora().toString();
    }

    @Override
    public void getFechaUI(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public void getHoraUI(String hora) {
        this.hora = hora;
    }
}
