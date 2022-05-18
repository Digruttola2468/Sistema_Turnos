package com.digruttola.sistematurnos.Server;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digruttola.sistematurnos.Adapter.RecyclerViewTurnosAdapter;
import com.digruttola.sistematurnos.Prosecer.Turno;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServerFireBase {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final String TAG = "ServerFireBase";


    /**<b>Descripcion:</b> <p>Leer todos los turnos de FireBase y mostrarlos en el RecyclerView</p>
     * @param recyclerView obtenemos el recyclerView para mostrar la lista de turnos*/
    public void readDocumentFireBase(RecyclerView recyclerView){
        db.collection("Turnos").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    ArrayList<Turno> turnos = new ArrayList<>();
                    for(QueryDocumentSnapshot document : task.getResult()){
                        String nombre = (String) document.getData().get("Nombre");
                        String fechas = (String) document.getData().get("Fecha");
                        String hora = (String) document.getData().get("Hora");

                        turnos.add(new Turno(nombre,hora,fechas));
                    }
                    RecyclerViewTurnosAdapter adapter = new RecyclerViewTurnosAdapter(turnos);
                    recyclerView.setAdapter(adapter);
                }else Log.w("","Error getting documents." , task.getException());
            }
        });
    }

    /**<b>Descripcion:</b> <p>Agregar un nuevo documento al servidor FireBase</p>
     * @param hora hora seleccionada
     * @param fecha fecha seleccionada
     * @param nombre nombre del paciente*/
    public void addFireBase(String nombre,String fecha,String hora){

        Map<String,String> agregarFireBase = new HashMap<>();
        agregarFireBase.put("Nombre",nombre);
        agregarFireBase.put("Fecha",fecha);
        agregarFireBase.put("Hora",hora);

        db.collection("Turnos").document()
                .set(agregarFireBase)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });

    }

    /***/
    public void LeerFechas(int dayOfMonth,int mounth,int year){
        //TODO
    }

}
