package com.digruttola.sistematurnos.UI;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digruttola.sistematurnos.R;
import com.digruttola.sistematurnos.Server.ServerFireBase;

public class Activity_view_allturnos extends AppCompatActivity {

    private CalendarView calendarView;
    private ServerFireBase serverFireBase = new ServerFireBase();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_allturnos);

        calendarView = findViewById(R.id.calendarView);
        recyclerView = findViewById(R.id.recyclerView_allTurnos);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                serverFireBase.LeerFechas(recyclerView,dayOfMonth,(month)+1,year);
            }
        });

    }
}
