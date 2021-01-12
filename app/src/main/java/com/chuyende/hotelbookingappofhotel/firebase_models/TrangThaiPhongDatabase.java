package com.chuyende.hotelbookingappofhotel.firebase_models;

import android.util.Log;

import androidx.annotation.Nullable;

import com.chuyende.hotelbookingappofhotel.data_models.TrangThaiPhong;
import com.chuyende.hotelbookingappofhotel.interfaces.TrangThaiPhongCallback;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class TrangThaiPhongDatabase {
    private List<TrangThaiPhong> listTrangThaiPhong;
    private FirebaseFirestore db;

    public static final String COLLECTION_TRANG_THAI_PHONG = "TrangThaiPhong";
    public static final String FIELD_MA_TRANG_THAI_PHONG = "maTrangThaiPhong";
    public static final String FIELD_TRANG_THAI_PHONG = "trangThaiPhong";

    public TrangThaiPhongDatabase() {
        listTrangThaiPhong = new ArrayList<TrangThaiPhong>();
        db = FirebaseFirestore.getInstance();
    }

    public List<TrangThaiPhong> getListTrangThaiPhong() {
        return listTrangThaiPhong;
    }

    public void setListTrangThaiPhong(ArrayList<TrangThaiPhong> listTrangThaiPhong) {
        this.listTrangThaiPhong = listTrangThaiPhong;
    }

    public void readAllDataTrangThaiPhong(TrangThaiPhongCallback trangThaiPhongCallBack) {
        try {
            db.collection(COLLECTION_TRANG_THAI_PHONG).addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    int sizeData = value.size();
                    if (error != null) {
                        Log.d("TTP=>", error.getMessage() + "");
                        return;
                    }

                    List<TrangThaiPhong> dsTrangThaiPhong = new ArrayList<TrangThaiPhong>();
                    TrangThaiPhong trangThaiPhong;
                    for (QueryDocumentSnapshot doc : value) {
                        trangThaiPhong = new TrangThaiPhong(doc.getString(FIELD_MA_TRANG_THAI_PHONG), doc.getString(FIELD_TRANG_THAI_PHONG));
                        dsTrangThaiPhong.add(trangThaiPhong);

                        /*if (dsTrangThaiPhong.size() == sizeData) {
                            trangThaiPhongCallBack.onDataCallbackTrangThaiPhong(dsTrangThaiPhong);
                        }*/
                    }
                    trangThaiPhongCallBack.onDataCallbackTrangThaiPhong(dsTrangThaiPhong);
                }
            });
        } catch (Exception e) {
            Log.d("ERR=>", "Read data trang thai phong is failed! Error: " + e.getMessage());
        }

    }

    public void removeATrangThaiPhong() {
    }

    public void addNewATrangThaiPhong() {
    }
}
