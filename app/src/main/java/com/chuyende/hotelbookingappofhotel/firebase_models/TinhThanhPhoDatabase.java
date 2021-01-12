package com.chuyende.hotelbookingappofhotel.firebase_models;

import android.util.Log;

import androidx.annotation.Nullable;

import com.chuyende.hotelbookingappofhotel.data_models.TinhThanhPho;
import com.chuyende.hotelbookingappofhotel.interfaces.TinhThanhPhoCallback;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class TinhThanhPhoDatabase {
    FirebaseFirestore db;

    public static final String COLLECTION_TINH_THANH_PHO = "TinhThanhPho";
    public static final String KEY_MA_TINH_THANH_PHO = "maTinhThanhPho";
    public static final String KEY_TINH_THANH_PHO = "tinhThanhPho";

    public TinhThanhPhoDatabase() {
        db = FirebaseFirestore.getInstance();
    }

    public void readAllDataTinhThanhPho(TinhThanhPhoCallback tinhThanhPhoCallback) {
        try {
            db.collection(COLLECTION_TINH_THANH_PHO).addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    int sizeData = value.size();
                    if (error != null) {
                        Log.d("TTP=>", "Listen failed! Error: " + error.getMessage());
                        return;
                    }

                    ArrayList<TinhThanhPho> dsTinhThanhPho = new ArrayList<TinhThanhPho>();
                    TinhThanhPho tinhThanhPho;
                    for (QueryDocumentSnapshot doc : value) {
                        tinhThanhPho = new TinhThanhPho(doc.getString(KEY_MA_TINH_THANH_PHO), doc.getString(KEY_TINH_THANH_PHO));
                        dsTinhThanhPho.add(tinhThanhPho);

                        // Test database
                        Log.d("TTP=>", tinhThanhPho.getMaTinhThanhPho() + " -- " + tinhThanhPho.getTinhThanhPho());
                    }
                    tinhThanhPhoCallback.onDataCallbackTinhThanhPho(dsTinhThanhPho);
                }
            });
        } catch (Exception e) {
            Log.d("ERR=>", "Listen data tinh thanh pho is failed! Error: " + e.getMessage());
        }
    }

}
