package com.digruttola.sistematurnos.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.digruttola.sistematurnos.Adapter.RecyclerViewTurnosAdapter;
import com.digruttola.sistematurnos.Prosecer.Turno;
import com.digruttola.sistematurnos.R;
import com.digruttola.sistematurnos.Server.ServerFireBase;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btFecha , btHora , btGuardar, btNext;
    private EditText findFecha,findHora,findNombre;
    private RecyclerView recyclerView;

    private ServerFireBase serverFireBase = new ServerFireBase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btFecha = findViewById(R.id.bt_Fecha);
        btHora = findViewById(R.id.bt_Hora);
        btGuardar = findViewById(R.id.bt_guardar);
        btNext = findViewById(R.id.bt_next);
        findFecha = findViewById(R.id.edt_fecha);
        findHora = findViewById(R.id.edt_hora);
        findNombre = findViewById(R.id.edit_nombre);
        recyclerView = findViewById(R.id.recyclerView_turnos);

        findFecha.setEnabled(false);
        findHora.setEnabled(false);

        btFecha.setOnClickListener(this);
        btHora.setOnClickListener(this);
        btGuardar.setOnClickListener(this);
        btNext.setOnClickListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        serverFireBase.readDocumentFireBase(recyclerView);
    }

    @Override
    public void onClick(View v) {
        if(v == btFecha){
            final Calendar c = Calendar.getInstance();
            int dia = c.get(Calendar.DAY_OF_MONTH);
            int mes = c.get(Calendar.MONTH);
            int anio = c.get(Calendar.YEAR);

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
            int hora = c.get(Calendar.HOUR_OF_DAY);
            int minutos = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    if(hourOfDay > 12){ //PM
                        if(minute > 0 && minute <= 9) findHora.setText(hourOfDay+":0"+minute);
                        else findHora.setText(hourOfDay+":"+minute+" PM");
                    }else{
                        findHora.setText(hourOfDay+":"+minute+" AM");
                    }
                }
            },hora,minutos,true);
            timePickerDialog.show();
        }

        if(v == btGuardar){
            String nombre = findNombre.getText().toString();
            String fecha = findFecha.getText().toString();
            String hora = findHora.getText().toString();

            if(!nombre.equals("") && !fecha.equals("") && !hora.equals("")){
                serverFireBase.addFireBase(new Turno(nombre,fecha,hora));
                findHora.setText("");
                findFecha.setText("");
                findNombre.setText("");
            }
        }

        if(v == btNext){
            Intent i = new Intent(MainActivity.this, Activity_view_allturnos.class);
            startActivity(i);
        }
    }
}