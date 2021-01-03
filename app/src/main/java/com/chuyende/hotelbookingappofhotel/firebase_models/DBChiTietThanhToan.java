package com.chuyende.hotelbookingappofhotel.firebase_models;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.chuyende.hotelbookingappofhotel.interfaces.DanhSachThanhToanCallBack;
import com.chuyende.hotelbookingappofhotel.data_models.ThongTinHuy;
import com.chuyende.hotelbookingappofhotel.data_models.ThongTinThanhToan;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DBChiTietThanhToan {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static String TAG = "DBChiTietThanhToan";
    public static String DATHANHTOAN = "DaThanhToan";
    public static String MATHANHTOAN = "maThanhToan";
    public static String DAHUY = "DaHuy";

    //Lay thong tin thanh toan phong tu bang DaThanhToan theo maThanhToan
    public void getDataDaThanhToan(String maThanhToan, DanhSachThanhToanCallBack danhSachThanhToanCallBack) {
        db.collection(DATHANHTOAN).whereEqualTo(MATHANHTOAN, maThanhToan).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null) {
                    Log.w(TAG, error);
                    return;
                }
                if (value != null) {
                    ArrayList<ThongTinThanhToan> thongTinThanhToanList = new ArrayList<>();
                    for (DocumentSnapshot doc : value) {
                        thongTinThanhToanList.add(doc.toObject(ThongTinThanhToan.class));
                    }
                    danhSachThanhToanCallBack.danhSachThanhToanCallBack(thongTinThanhToanList);
                } else {
                    Log.d(TAG, "Data chi tiet thanh toan null");
                }
            }
        });
    }

    //Them thong tin thanh toan vao bang DaHuy
    public void addThongTinThanhToanInTableDaHuy(ThongTinHuy thongTinHuy) {
        db.collection(DAHUY).document(thongTinHuy.getMaHuy())
                .set(thongTinHuy).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "add thong tin thanh toan success");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "add thong tin thanh toan failure " + e);
            }
        });
    }
}
