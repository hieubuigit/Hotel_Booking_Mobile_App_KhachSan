package com.chuyende.hotelbookingappofhotel.firebase_models;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.chuyende.hotelbookingappofhotel.data_models.TenTaiKhoanKS;
import com.chuyende.hotelbookingappofhotel.interfaces.DataCallBack;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DBManHinhDangNhap {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static String TAG = "ManHinhDangNhap";
    public static String TTKKS = "TTKKS";
    public static String TENTKKS = "tenTKKS";

    public void saveTenTaiKhoanKhachSan(TenTaiKhoanKS tenTaiKhoanKS){
        db.collection(TTKKS).document(tenTaiKhoanKS.getMaTenTKKS()).set(tenTaiKhoanKS)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "Save ten tai khoan khach san success");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "Save ten tai khoan khach san failure");
            }
        });
    }

    public void getTenTaiKhoanKhachSan(DataCallBack dataCallBack) {
        db.collection(TTKKS).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w(TAG, error);
                    return;
                }
                if (value != null) {
                    List<String> tenTKKS = new ArrayList<>();
                    for (DocumentSnapshot doc : value) {
                        tenTKKS.add(doc.getString(TENTKKS));
                    }
                    dataCallBack.dataCallBack(tenTKKS.get(0));
                } else {
                    Log.d(TAG, "Data TTKKS null");
                }
            }
        });
    }
}
