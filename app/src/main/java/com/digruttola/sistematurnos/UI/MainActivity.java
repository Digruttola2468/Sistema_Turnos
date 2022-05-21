package com.digruttola.sistematurnos.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.digruttola.sistematurnos.Adapter.RecyclerViewTurnosAdapter;
import com.digruttola.sistematurnos.Prosecer.Turno;
import com.digruttola.sistematurnos.R;
import com.digruttola.sistematurnos.Server.ServerFireBase;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btFecha  , btGuardar, btNext;
    private EditText findFecha,findNombre;
    private RecyclerView recyclerView;
    private Spinner sp_horarios;

    private ServerFireBase serverFireBase = new ServerFireBase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btFecha = findViewById(R.id.bt_Fecha);
        btGuardar = findViewById(R.id.bt_guardar);
        btNext = findViewById(R.id.bt_next);
        findFecha = findViewById(R.id.edt_fecha);
        findNombre = findViewById(R.id.edit_nombre);
        recyclerView = findViewById(R.id.recyclerView_turnos);
        sp_horarios = findViewById(R.id.sp_horarios_main);

        findFecha.setEnabled(false);

        btFecha.setOnClickListener(this);
        btGuardar.setOnClickListener(this);
        btNext.setOnClickListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        serverFireBase.readDocumentFireBase(recyclerView);

        serverFireBase.getRealTimeData(recyclerView);
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

                        serverFireBase.getHorarios(MainActivity.this,sp_horarios,findFecha.getText().toString());

                    }
                },anio,mes,dia);
                datePickerDialog.show();
            }
        }


        if(v == btGuardar){
            String nombre = findNombre.getText().toString();
            String fecha = findFecha.getText().toString();
            String horario;

            //Verificamos si es diferente de null
            if(sp_horarios.getSelectedItem() != null)
                horario = sp_horarios.getSelectedItem().toString();
            else
                horario = "";

            if(!nombre.equals("") && !fecha.equals("") && !horario.equals("")){
                serverFireBase.addFireBase(new Turno(nombre,horario,fecha));
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