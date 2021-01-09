package com.chuyende.hotelbookingappofhotel.firebase_models;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chuyende.hotelbookingappofhotel.data_models.LoaiPhong;
import com.chuyende.hotelbookingappofhotel.interfaces.LoaiPhongCallback;
import com.chuyende.hotelbookingappofhotel.interfaces.SuccessNotificationCallback;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.List;

public class LoaiPhongDatabase {
    private FirebaseFirestore db;

    public static final String COLLECTION_LOAI_PHONG = "LoaiPhong";
    public static final String FIELD_MA_LOAI_PHONG = "maLoaiPhong";
    public static final String FIELD_LOAI_PHONG = "loaiPhong";

    public LoaiPhongDatabase() {
        db = FirebaseFirestore.getInstance();
    }

    public void readAllDataLoaiPhong(LoaiPhongCallback loaiPhongCallback) {
        try {
            db.collection(COLLECTION_LOAI_PHONG).addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    int sizeData = value.size();

                    if (error != null) {
                        Log.d("LP=>", "Listen failed! Error: " + error.getMessage());
                        return;
                    }

                    List<LoaiPhong> dsLoaiPhong = new ArrayList<LoaiPhong>();
                    LoaiPhong loaiPhong;
                    for (QueryDocumentSnapshot doc : value) {
                        loaiPhong = new LoaiPhong(doc.getString(FIELD_MA_LOAI_PHONG), doc.getString(FIELD_LOAI_PHONG));
                        dsLoaiPhong.add(loaiPhong);

                        if (dsLoaiPhong.size() == sizeData) {
                            loaiPhongCallback.onDataCallbackLoaiPhong(dsLoaiPhong);
                        }

                        // Test database
                        Log.d("LP=>", loaiPhong.getMaLoaiPhong() + " -- " + loaiPhong.getLoaiPhong());
                    }
                }
            });
        } catch (Exception e) {
            Log.d("LPD=>", "Listen loai phong from Firebase is failed! Error: " + e.getMessage());
        }
    }

    public void addNewALoaiPhong(LoaiPhong loaiPhong, SuccessNotificationCallback successNotificationCallback) {
        try {
            db.collection(COLLECTION_LOAI_PHONG).document(loaiPhong.getMaLoaiPhong()).set(loaiPhong, SetOptions.merge())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            successNotificationCallback.onCallbackSuccessNotification(true);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            successNotificationCallback.onCallbackSuccessNotification(false);
                        }
                    });
        } catch (Exception e) {
            Log.d("LPD=>", "Add new a loai phong to Firebase is failed! Error: " + e.getMessage());
        }
    }

    public void removeALoaiPhong(String maLoaiPhongToDelete, SuccessNotificationCallback successNotificationCallback) {
        try {
            db.collection(COLLECTION_LOAI_PHONG).document(maLoaiPhongToDelete).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    successNotificationCallback.onCallbackSuccessNotification(true);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    successNotificationCallback.onCallbackSuccessNotification(false);
                }
            });
        } catch (Exception e) {
            Log.d("LPD=>", "Remove a loai phong from Firebase is failed! Error: " + e.getMessage());
        }
    }

    public void updateALoaiPhong(LoaiPhong loaiPhong, SuccessNotificationCallback successNotificationCallback) {
        try {
            db.collection(COLLECTION_LOAI_PHONG).document(loaiPhong.getMaLoaiPhong()).set(loaiPhong, SetOptions.merge())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            successNotificationCallback.onCallbackSuccessNotification(true);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            successNotificationCallback.onCallbackSuccessNotification(false);
                        }
                    });
        } catch (Exception e) {
            Log.d("LPD=>", "Update a loai phong to Firebase is failed! Error: " + e.getMessage());
        }
    }
}
