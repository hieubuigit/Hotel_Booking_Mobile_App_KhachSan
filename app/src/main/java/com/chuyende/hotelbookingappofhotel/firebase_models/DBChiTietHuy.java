package com.chuyende.hotelbookingappofhotel.firebase_models;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.chuyende.hotelbookingappofhotel.interfaces.DanhSachHuyCallBack;
import com.chuyende.hotelbookingappofhotel.data_models.ThongTinHuy;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DBChiTietHuy {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static String TAG = "DBChiTietHuy";
    public static String DAHUY = "DaHuy";
    public static String MAHUY = "maHuy";

    //Lay thong tin huy tu bang DaHuy theo maHuy
    public void getDataDaHuy(String maHuy, DanhSachHuyCallBack danhSachHuyCallBack) {
        db.collection(DAHUY).whereEqualTo(MAHUY, maHuy).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w(TAG, error);
                    return;
                }
                if (value != null) {
                    ArrayList<ThongTinHuy> thongTinHuyList = new ArrayList<>();
                    for (DocumentSnapshot doc : value) {
                        thongTinHuyList.add(doc.toObject(ThongTinHuy.class));
                    }
                    danhSachHuyCallBack.danhSachHuyCallBack(thongTinHuyList);
                } else {
                    Log.d(TAG, "Data chi tiet huy null");
                }
            }
        });
    }

    //Sua trangThaiHoanTien trong bang daHuy
    public void updateThongTinHuy(ThongTinHuy thongTinHuy) {
        db.collection(DAHUY).document(thongTinHuy.getMaHuy())
                .set(thongTinHuy).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "update thong tin thanh toan success");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "update thong tin thanh toan failure " + e);
            }
        });
    }
}
