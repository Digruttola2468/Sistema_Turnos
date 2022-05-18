package com.digruttola.sistematurnos.UI;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.digruttola.sistematurnos.R;
import com.digruttola.sistematurnos.Server.ServerFireBase;

import java.util.Calendar;

public class Activity_recyclerview_DetailItem extends AppCompatActivity {

    private EditText editNombre,editFecha,editHora;
    private Button btGuardarCambios,btDelete,btHora,btFecha;

    private String id,nombre,fecha,hora;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview_detailitem);

        //Sincronizamos con los widgets del Layout
        serializacionConLosWidgetLayout();

        //Obtenemos los datos
        obtenerDatosRecyclerViewTurnosAdapter();

        //Con los datos guardamos en los EditText para cambiarlos
        editNombre.setText(nombre);
        editFecha.setText(fecha);
        editHora.setText(hora);

        //Desactivamos para que No puedan cambia la fecha y hora manual
        editHora.setEnabled(false);
        editFecha.setEnabled(false);

        btGuardarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Obtenemos los campos para luego actualizarlo
                String nombre = editNombre.getText().toString();
                String fecha = editFecha.getText().toString();
                String hora = editHora.getText().toString();
                new ServerFireBase().actualizarDocumento(Activity_recyclerview_DetailItem.this,id,nombre,fecha,hora);

            }
        });

        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ServerFireBase().deleteDocument(id);
            }
        });

        btFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int dia = c.get(Calendar.DAY_OF_MONTH);
                int mes = c.get(Calendar.MONTH);
                int anio = c.get(Calendar.YEAR);

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(Activity_recyclerview_DetailItem.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            editFecha.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                        }
                    },anio,mes,dia);
                    datePickerDialog.show();
                }
            }
        });

        btHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int hora = c.get(Calendar.HOUR_OF_DAY);
                int minutos = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(Activity_recyclerview_DetailItem.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if(hourOfDay > 12){ //PM
                            if(minute > 0 && minute <= 9) editHora.setText(hourOfDay+":0"+minute);
                            else editHora.setText(hourOfDay+":"+minute+" PM");
                        }else{
                            editHora.setText(hourOfDay+":"+minute+" AM");
                        }
                    }
                },hora,minutos,true);
                timePickerDialog.show();
            }
        });
    }

    private void obtenerDatosRecyclerViewTurnosAdapter(){
        id = getIntent().getExtras().getString("itemID");
        nombre = getIntent().getExtras().getString("itemNombre");
        fecha = getIntent().getExtras().getString("itemFecha");
        hora = getIntent().getExtras().getString("itemHora");
    }
    private void serializacionConLosWidgetLayout(){
        editNombre = findViewById(R.id.edit_nombre_detailItem);
        editHora = findViewById(R.id.edit_horario_detailitem);
        editFecha = findViewById(R.id.edit_fecha_detailItem);
        btGuardarCambios = findViewById(R.id.bt_guardarCambio_detailitem);
        btDelete = findViewById(R.id.bt_delete_detailitem);
        btHora = findViewById(R.id.bt_hora_detailItem);
        btFecha = findViewById(R.id.bt_fecha_detailItem);
    }
}
