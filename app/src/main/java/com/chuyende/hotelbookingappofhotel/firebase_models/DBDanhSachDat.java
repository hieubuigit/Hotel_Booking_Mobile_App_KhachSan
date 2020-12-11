package com.chuyende.hotelbookingappofhotel.firebase_models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.chuyende.hotelbookingappofhotel.Interface.MaKhachSanCallBack;
import com.chuyende.hotelbookingappofhotel.data_models.ThongTinDat;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DBDanhSachDat {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference reference = db.collection("DaDat");

    public static String TAG = "DBDanhSachDat";

    public FirestoreRecyclerOptions<ThongTinDat> hienThiDuLieu(String maKhachSan){
        Query query = reference.orderBy("maPhong").startAt(maKhachSan).endAt(maKhachSan+"\uf8ff");
        FirestoreRecyclerOptions<ThongTinDat> options = new FirestoreRecyclerOptions.Builder<ThongTinDat>()
                .setQuery(query, ThongTinDat.class).build();
        return options;
    }

    public void getMaPhong(String maKhachSan, MaKhachSanCallBack maKhachSanCallBack) {
        db.collection("KhachSan").whereEqualTo("tenTaiKhoanKhachSan", maKhachSan)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    List<String> listMaKhachSan = new ArrayList<>();
                    for(DocumentSnapshot document : task.getResult()) {
                        listMaKhachSan.add(document.getString("maKhachSan"));
                        Log.d(TAG, listMaKhachSan.get(0));
                    }
                    maKhachSanCallBack.maKhachSanCallBack(listMaKhachSan.get(0));
                } else {
                    Log.d(TAG, "Error " + task.getException());
                }
            }
        });
    }
}
