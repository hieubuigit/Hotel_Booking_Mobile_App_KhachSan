package com.chuyende.hotelbookingappofhotel.firebase_models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.chuyende.hotelbookingappofhotel.data_models.Phong;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

public class PhongDatabase {
    public static final String COLLECTION_PHONG = "Phong";
    private FirebaseFirestore db;

    public PhongDatabase() {
        db = FirebaseFirestore.getInstance();
    }

    // Function add a new Phong to Firebase
    public void themPhongMoi(Phong aPhong) {
        // Add a Phong without random id document
        db.collection(COLLECTION_PHONG).document().set(aPhong, SetOptions.merge())
        .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("DB=>", "A Phong add successfully!");
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("DB=>", "Add A Phong is failed! - Error: " + e.getMessage());
            }
        });
    }

    // Function update a Phong
    public void capNhatMotPhong(String maPhong, Phong aPhong) {

    }

    public void xoaMotPhong(String maPhong) {

    }
}
