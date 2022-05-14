package com.digruttola.sistematurnos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
/*
* Vista del Dentista
* Agregar , Modificar , eliminar y leer Turnos
*
*
* */

    private Button btFecha , btHora;
    private EditText findFecha,findHora;
    private int dia,mes,anio,hora,minutos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btFecha = findViewById(R.id.bt_Fecha);
        btHora = findViewById(R.id.bt_Hora);
        findFecha = findViewById(R.id.edt_fecha);
        findHora = findViewById(R.id.edt_hora);

        btFecha.setOnClickListener(this);
        btHora.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == btFecha){
            final Calendar c = Calendar.getInstance();
            dia = c.get(Calendar.DAY_OF_MONTH);
            mes = c.get(Calendar.MONTH);
            anio = c.get(Calendar.YEAR);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        findFecha.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                    }
                },anio,mes,dia);
                datePickerDialog.show();
            }
        }
        if(v == btHora){
            final Calendar c = Calendar.getInstance();
            hora = c.get(Calendar.HOUR_OF_DAY);
            minutos = c.get(Calendar.MINUTE);
            Log.d("FECHA",hora+":"+minutos);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    findHora.setText(hourOfDay+":"+minute);
                }
            },hora,minutos,true);
            timePickerDialog.show();
        }
    }
}