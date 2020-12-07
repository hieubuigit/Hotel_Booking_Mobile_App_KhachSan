package com.chuyende.hotelbookingappofhotel.firebase_models;

import android.util.Log;

import androidx.annotation.Nullable;

import com.chuyende.hotelbookingappofhotel.data_models.TienNghi;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class TienNghiDatabase {
    private List<TienNghi> listTienNghis;
    private FirebaseFirestore db;

    public static final String COLLECTION_TIEN_NGHI = "TienNghi";
    public static final String KEY_MA_TIEN_NGHI = "maTienNghi";
    public static final String KEY_TIEN_NGHI = "tienNghi";
    public static final String KEY_ICON_TIEN_NGHI = "iconTienNghi";

    public TienNghiDatabase() {
        listTienNghis = new ArrayList<TienNghi>();
        db = FirebaseFirestore.getInstance();
    }

    public List<TienNghi> getListTienNghis() {
        return listTienNghis;
    }

    public void setListTienNghis(List<TienNghi> listTienNghis) {
        this.listTienNghis = listTienNghis;
    }

    public FirebaseFirestore getDb() {
        return db;
    }

    public void setDb(FirebaseFirestore db) {
        this.db = db;
    }

    public List<TienNghi> getAllTienNghiFromFirestore() {
        ArrayList<TienNghi> dsTienNghi = new ArrayList<TienNghi>();
        db.collection(COLLECTION_TIEN_NGHI).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.d("TN=>", "Listen failed! Error: " + error.getMessage());
                }
                if (value != null) {
                    for (QueryDocumentSnapshot doc : value) {
                        TienNghi tienNghi = new TienNghi();
                        tienNghi.setMaTienNghi(doc.getString(KEY_MA_TIEN_NGHI));
                        tienNghi.setTienNghi(doc.getString(KEY_TIEN_NGHI));
                        tienNghi.setIconTienNghi(doc.getString(KEY_ICON_TIEN_NGHI));
                        dsTienNghi.add(tienNghi);
                    }
                }
            }
        });
        return dsTienNghi;
    }

    public void removeATienNghi() {

    }

    public void addANewTienNghi() {

    }

}
