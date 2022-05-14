package com.digruttola.sistematurnos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
/*
* Vista del Dentista
* Agregar , Modificar , eliminar y leer Turnos
*
*
* */

    Spinner dia,mes,anio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dia = findViewById(R.id.sp_dia);
        mes = findViewById(R.id.sp_mes);
        anio = findViewById(R.id.sp_anio);

    }
}