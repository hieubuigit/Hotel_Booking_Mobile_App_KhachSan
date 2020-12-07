package com.chuyende.hotelbookingappofhotel.firebase_models;

import android.util.Log;

import androidx.annotation.Nullable;

import com.chuyende.hotelbookingappofhotel.data_models.TrangThaiPhong;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class TrangThaiPhongDatabase {
    private ArrayList<TrangThaiPhong> listTrangThaiPhong;
    private FirebaseFirestore db;

    public static final String COLLECTION_TRANG_THAI_PHONG = "TrangThaiPhong";
    public static final String KEY_MA_TRANG_THAI_PHONG = "maTrangThaiPhong";
    public static final String KEY_TRANG_THAI_PHONG = "trangThaiPhong";

    public TrangThaiPhongDatabase() {
        listTrangThaiPhong = new ArrayList<TrangThaiPhong>();
        db = FirebaseFirestore.getInstance();
    }

    public ArrayList<TrangThaiPhong> getListTrangThaiPhong() {
        return listTrangThaiPhong;
    }

    public void setListTrangThaiPhong(ArrayList<TrangThaiPhong> listTrangThaiPhong) {
        this.listTrangThaiPhong = listTrangThaiPhong;
    }

    public ArrayList<TrangThaiPhong> getAllTrangThaiPhongFromFirestore() {
        ArrayList<TrangThaiPhong> dsTrangThaiPhong = new ArrayList<TrangThaiPhong>();
        db.collection(COLLECTION_TRANG_THAI_PHONG).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.d("TTP=>", error.getMessage());
                }
                if (value != null) {
                    TrangThaiPhong trangThaiPhong = new TrangThaiPhong();
                    for (QueryDocumentSnapshot doc : value) {
                        trangThaiPhong.setMaTrangThai(doc.getString(KEY_MA_TRANG_THAI_PHONG));
                        trangThaiPhong.setTrangThaiPhong(doc.getString(KEY_TRANG_THAI_PHONG));
                        dsTrangThaiPhong.add(trangThaiPhong);
                    }
                }
            }
        });
        return dsTrangThaiPhong;
    }

    public void removeATrangThaiPhong() {
    }

    public void addNewATrangThaiPhong() {
    }
}
