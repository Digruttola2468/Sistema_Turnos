package com.digruttola.sistematurnos.Adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digruttola.sistematurnos.Prosecer.Turno;
import com.digruttola.sistematurnos.R;
import com.digruttola.sistematurnos.UI.Activity_recyclerview_DetailItem;

import java.util.ArrayList;

public class RecyclerViewTurnosAdapter extends RecyclerView.Adapter<RecyclerViewTurnosAdapter.ViewHolder> {

    private static ArrayList<Turno> pacientes = new ArrayList<>();

    public RecyclerViewTurnosAdapter(Turno turno){
        pacientes.add(turno);
    }

    public RecyclerViewTurnosAdapter(ArrayList<Turno> pacientes){
        this.pacientes = pacientes;
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_turnos,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.id = pacientes.get(position).getId();
        holder.asignarDatoNombre(pacientes.get(position).getNombre());
        holder.asignarDatoFecha(pacientes.get(position).getFecha());
        holder.asignarDatoHora(pacientes.get(position).getHora());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), Activity_recyclerview_DetailItem.class);
                intent.putExtra("itemID",holder.id);
                intent.putExtra("itemNombre",holder.txtNombre.getText());
                intent.putExtra("itemHora",holder.txtHorario.getText());
                intent.putExtra("itemFecha",holder.txtFecha.getText());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pacientes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtNombre,txtFecha,txtHorario;
        String id;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtFecha = itemView.findViewById(R.id.txt_Fecha);
            txtHorario = itemView.findViewById(R.id.txt_Horario);
            txtNombre = itemView.findViewById(R.id.txt_Nombre);
        }

        public void asignarDatoNombre(String nombre) { txtNombre.setText(nombre); }
        public void asignarDatoFecha(String fecha){ txtFecha.setText(fecha); }
        public void asignarDatoHora(String hora) { txtHorario.setText(hora); }

    }
}
