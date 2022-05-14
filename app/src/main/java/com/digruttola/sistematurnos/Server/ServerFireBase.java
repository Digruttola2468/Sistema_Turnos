package com.digruttola.sistematurnos.Server;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class ServerFireBase {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private final String TAG = "ServerFireBase";

    //Agregar - Modificar - Eliminar - Leer

    public void read(){
        db.collection("Turnos").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        Log.d("", document.getId() + " => " + document.getData());
                    }
                }else Log.w("","Error getting documents." , task.getException());
            }
        });
    }

    public void add(String nombre,String fecha,String hora){

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


}
