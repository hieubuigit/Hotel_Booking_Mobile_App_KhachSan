package com.chuyende.hotelbookingappofhotel.firebase_models;

import android.util.Log;

import androidx.annotation.Nullable;

import com.chuyende.hotelbookingappofhotel.data_models.TienNghi;
import com.chuyende.hotelbookingappofhotel.interfaces.ChonTienNghiCallback;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class TienNghiDatabase {
    private FirebaseFirestore db;

    public static final String COLLECTION_TIEN_NGHI = "TienNghi";
    public static final String KEY_MA_TIEN_NGHI = "maTienNghi";
    public static final String KEY_TIEN_NGHI = "tienNghi";
    public static final String KEY_ICON_TIEN_NGHI = "iconTienNghi";

    public TienNghiDatabase() {
        db = FirebaseFirestore.getInstance();
    }

    public void readAllDataTienNghi(String maKhachSan, ChonTienNghiCallback chonTienNghiCallback) {
        try {
            db.collection(COLLECTION_TIEN_NGHI).addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (error != null) {
                        Log.d("TN=>", "Listen TienNghi is failed! Error: " + error.getMessage());
                    }

                    if (value != null) {
                        ArrayList<TienNghi> dsTienNghi = new ArrayList<TienNghi>();
                        TienNghi tienNghi;
                        for (QueryDocumentSnapshot doc : value) {
                            tienNghi = new TienNghi(doc.getString(KEY_MA_TIEN_NGHI), doc.getString(KEY_ICON_TIEN_NGHI), doc.getString(KEY_TIEN_NGHI),
                                    doc.getString(PhongDatabase.FIELD_MA_KHACH_SAN) ,false);

                            if (tienNghi.getMaKhachSan().contains(maKhachSan)) {
                                dsTienNghi.add(tienNghi);
                            }

                            // Test database
                            Log.d("TN=>", "Ma tien nghi: " + tienNghi.getMaTienNghi()
                                    + " -- Tien nghi: " + tienNghi.getTienNghi()
                                    + " -- Uri icon tien nghi" + tienNghi.getIconTienNghi()
                                    + " -- Ma khach san: " + tienNghi.getMaKhachSan());
                        }
                        chonTienNghiCallback.onDataCallbackChonTienNghi(dsTienNghi);
                    }
                }
            });
        } catch (Exception e) {
            Log.d("TND=>", e.getMessage());
        }
    }

    public void removeATienNghi() {
        // Code here
    }

    public void addANewTienNghi() {
        // Code here
    }

}
