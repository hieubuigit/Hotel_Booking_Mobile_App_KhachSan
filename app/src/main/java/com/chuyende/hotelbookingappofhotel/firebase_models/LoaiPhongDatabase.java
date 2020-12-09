package com.chuyende.hotelbookingappofhotel.firebase_models;

import android.util.Log;

import androidx.annotation.Nullable;

import com.chuyende.hotelbookingappofhotel.activities.ThemPhongActivity;
import com.chuyende.hotelbookingappofhotel.data_models.LoaiPhong;
import com.chuyende.hotelbookingappofhotel.data_models.Phong;
import com.chuyende.hotelbookingappofhotel.interfaces.LoaiPhongCallback;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class LoaiPhongDatabase {
    private List<LoaiPhong> listLoaiPhongs;
    private FirebaseFirestore db;

    public static final String COLLECTION_LOAI_PHONG = "LoaiPhong";
    public static final String KEY_MA_LOAI_PHONG = "maLoaiPhong";
    public static final String KEY_LOAI_PHONG = "loaiPhong";

    public LoaiPhongDatabase() {
        listLoaiPhongs = new ArrayList<LoaiPhong>();
        db = FirebaseFirestore.getInstance();
    }

    public List<LoaiPhong> getListLoaiPhongs() {
        return listLoaiPhongs;
    }

    public void setListLoaiPhongs(List<LoaiPhong> listLoaiPhongs) {
        this.listLoaiPhongs = listLoaiPhongs;
    }

    public FirebaseFirestore getDb() {
        return db;
    }

    public void setDb(FirebaseFirestore db) {
        this.db = db;
    }

    public void readAllDataLoaiPhong(LoaiPhongCallback loaiPhongCallback) {
        db.collection(COLLECTION_LOAI_PHONG).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.d("LP=>", "Listen failed! Error: " + error.getMessage());
                }
                if (value != null) {
                    List<LoaiPhong> dsLoaiPhong = new ArrayList<LoaiPhong>();
                    LoaiPhong loaiPhong;
                    for (QueryDocumentSnapshot doc : value) {
                        loaiPhong = new LoaiPhong(doc.getString(KEY_MA_LOAI_PHONG), doc.getString(KEY_LOAI_PHONG));
                        dsLoaiPhong.add(loaiPhong);

                        // Test database
                        Log.d("LP=>", loaiPhong.getMaLoaiPhong() + " -- " + loaiPhong.getLoaiPhong());
                    }
                    loaiPhongCallback.onDataCallbackLoaiPhong(dsLoaiPhong);
                } else {
                    Log.d("LP=>", "Data is null!");
                }
            }
        });
    }

    public void removeALoaiPhong() {

    }

    public void addNewALoaiPhong() {

    }
}
