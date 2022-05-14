package com.digruttola.sistematurnos.Prosecer;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Turno implements getUI {

    private String fecha;
    private String hora;

    public Date formatFechaHora(){
        try {
            Date date = new SimpleDateFormat("dd/MM/yyyyhh:mm").parse(fecha + hora);
            return date;
        }catch (ParseException e){
            Log.w("TURNO",e.getMessage());
            return null;
        }
    }

    public String getDate(){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(formatFechaHora());
        return calendar.get(calendar.DAY_OF_MONTH) + "/" +
                (calendar.get(calendar.MONTH)+1) + "/" +
                calendar.get(calendar.YEAR) + "   " +
                calendar.get(calendar.HOUR_OF_DAY) + " : " + calendar.get(calendar.MINUTE);
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
