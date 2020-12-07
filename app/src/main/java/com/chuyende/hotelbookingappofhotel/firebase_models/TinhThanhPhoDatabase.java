package com.chuyende.hotelbookingappofhotel.firebase_models;

import android.text.style.AlignmentSpan;
import android.util.Log;

import androidx.annotation.Nullable;

import com.chuyende.hotelbookingappofhotel.data_models.TinhThanhPho;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class TinhThanhPhoDatabase {
    FirebaseFirestore db;

    public static final String COLLECTION_TINH_THANH_PHO = "TinhThanhPho";
    public static final String KEY_MA_TINH_THANH_PHO = "maTinhThanhPho";
    public static final String KEY_TINH_THANH_PHO = "tinhThanhPho";

    public TinhThanhPhoDatabase() {
        db = FirebaseFirestore.getInstance();
    }

    public List<TinhThanhPho> getAllTinhThanhPhoFromFirebase() {
        ArrayList<TinhThanhPho> dsTinhThanhPho = new ArrayList<TinhThanhPho>();
        db.collection(COLLECTION_TINH_THANH_PHO).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.d("TTP=>", "Listen failed! Error: " + error.getMessage());
                }
                if (value != null) {
                    TinhThanhPho tinhThanhPho;
                    for (QueryDocumentSnapshot doc : value) {
                        tinhThanhPho = new TinhThanhPho();
                        tinhThanhPho.setMaTinhThanhPho(doc.getString(KEY_MA_TINH_THANH_PHO));
                        tinhThanhPho.setTinhThanhPho(doc.getString(KEY_TINH_THANH_PHO));
                        dsTinhThanhPho.add(tinhThanhPho);
                    }
                }
            }
        });
        return dsTinhThanhPho;
    }

    public void removeATinhThanhPho() {

    }

    public void addANewTinhThanhPho() {

    }

}
