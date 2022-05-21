package com.digruttola.sistematurnos.Server;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.ContentView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.digruttola.sistematurnos.Adapter.RecyclerViewTurnosAdapter;
import com.digruttola.sistematurnos.Prosecer.Turno;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

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

                        turnos.add(new Turno(document.getId(),nombre,hora,fechas));
                    }
                    RecyclerViewTurnosAdapter adapter = new RecyclerViewTurnosAdapter(turnos);
                    recyclerView.setAdapter(adapter);
                }else Log.w("","Error getting documents." , task.getException());
            }
        });
    }

    /**<b>Descripcion:</b> <p>Agregar un nuevo documento al servidor FireBase</p>
     * @param turno Obtenemos una clase turno para agregar el documento*/
    public void addFireBase(Turno turno){

        Map<String,String> agregarFireBase = new HashMap<>();
        agregarFireBase.put("Nombre",turno.getNombre());
        agregarFireBase.put("Fecha",turno.getFecha());
        agregarFireBase.put("Hora",turno.getHora());

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

    /**
     * <b>Description: </b>Actualizar el nombre , fecha y hora del documento
     * @param turno  obtener las propiedades de la clase Turno
     * @param content obtener el activity para mostrar un Toast*/
    public void actualizarDocumento(Context content, Turno turno){
        Map<String,String> actualizar = new HashMap<>();

        actualizar.put("Nombre",turno.getNombre());
        actualizar.put("Fecha",turno.getFecha());
        actualizar.put("Hora",turno.getHora());

        db.collection("Turnos").document(turno.getId())
                .set(actualizar,SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(content,"Actualizado Correctamente",Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                    }
                });

    }

    /**
     * <b>Description: </b>Eliminar un documento de Firebase
     * @param id obtener el id del documento para eliminar
     * */
    public void deleteDocument(Context context,String id){
        db.collection("Turnos").document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context,"Eliminado Correctamente",Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                    }
                });

    }

    public void getRealTimeData(RecyclerView recyclerView){
        db.collection("Turnos").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    Log.e("ERROR",error.getMessage());
                    return;
                }
                for (DocumentChange documents : value.getDocumentChanges()) {
                    switch (documents.getType()){
                        case ADDED:
                            String nombre = (String) documents.getDocument().getData().get("Nombre");
                            String fechas = (String) documents.getDocument().getData().get("Fecha");
                            String hora = (String) documents.getDocument().getData().get("Hora");

                            RecyclerViewTurnosAdapter adapter = new RecyclerViewTurnosAdapter(new Turno(documents.getDocument().getId(),nombre,hora,fechas));
                            recyclerView.setAdapter(adapter);
                            break;
                        case MODIFIED:
                        case REMOVED:
                            readDocumentFireBase(recyclerView);
                            break;
                    }
                }

            }
        });
    }

    /***/
    public void LeerFechas(int dayOfMonth,int mounth,int year){
        //TODO
    }

    /*Depende de la fecha seleccionada , vamos a obtener los horarios No seleccionados*/
    public void getHorarios(Context context,Spinner spinner, String fecha){
        db.collection("Turnos").whereEqualTo("Fecha",fecha).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    Log.e("ERROR",error.getMessage());
                    return;
                }

                //Horarios por defectos
                ArrayList<String> horarios = new ArrayList<>();
                horarios.add("12:00");horarios.add("12:30");
                horarios.add("13:00");horarios.add("13:30");
                horarios.add("14:00");horarios.add("14:30");
                horarios.add("15:00");horarios.add("15:30");

                for (QueryDocumentSnapshot document : value) {
                    String hora = (String) document.getData().get("Hora");
                    for (int i = 0; i < horarios.size(); i++) {
                        if(hora.equals(horarios.get(i))){
                            horarios.remove(i);
                        }
                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,horarios);
                spinner.setAdapter(adapter);
            }
        });
    }

}
