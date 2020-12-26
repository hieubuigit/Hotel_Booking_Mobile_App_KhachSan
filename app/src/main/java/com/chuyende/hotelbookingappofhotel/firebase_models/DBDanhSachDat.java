package com.chuyende.hotelbookingappofhotel.firebase_models;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chuyende.hotelbookingappofhotel.interfaces.DanhSachDatCallBack;
import com.chuyende.hotelbookingappofhotel.interfaces.DataCallBack;
import com.chuyende.hotelbookingappofhotel.data_models.ThongTinDat;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DBDanhSachDat {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static String TAG = "DBDanhSachDat";

    public void getTenPhong(String maPhong, DataCallBack dataCallBack) {
        db.collection("Phong").whereEqualTo("maPhong", maPhong).get().
                addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            List<String> listTenPhong = new ArrayList<>();
                            for (DocumentSnapshot document : task.getResult()) {
                                listTenPhong.add(document.getString("tenPhong"));
                            }
                            dataCallBack.dataCallBack(listTenPhong.get(0));
                        } else {
                            Log.d(TAG, "Error" + task.getException());
                        }
                    }
                });
    }

    public void getTenNguoiDung(String maNguoiDung, DataCallBack dataCallBack) {
        db.collection("NguoiDung").whereEqualTo("maNguoiDung", maNguoiDung).get().
                addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            List<String> listTenNguoiDung = new ArrayList<>();
                            for (DocumentSnapshot document : task.getResult()) {
                                listTenNguoiDung.add(document.getString("tenNguoiDung"));
                            }
                            dataCallBack.dataCallBack(listTenNguoiDung.get(0));
                        } else {
                            Log.d(TAG, "Error" + task.getException());
                        }
                    }
                });
    }

    public void hienThiThongTinDat(String taiKhoanKhachSan, DanhSachDatCallBack danhSachDatCallBack) {
        //Lay ma khach san tu ten tai khoan cua khach san
        db.collection("KhachSan").whereEqualTo("tenTaiKhoanKhachSan", taiKhoanKhachSan)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    List<String> listMaKhachSan = new ArrayList<>();
                    for(DocumentSnapshot document : task.getResult()) {
                        listMaKhachSan.add(document.getString("maKhachSan"));
                    }

                    db.collection("DaDat").addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            if (error != null) {
                                Log.w(TAG, "Listen failed.", error);
                                return;
                            }

                            if (value != null) {
                                ArrayList<String> listMaPhong = new ArrayList<>();
                                for (DocumentSnapshot doc : value) {
                                    listMaPhong.add(doc.getString("maPhong"));
                                }

                                //Lay ra tat ca ma phong cua khach san dua vao ma khach san
                                for (int i = 0; i < listMaPhong.size(); i++) {
                                    db.collection("Phong").whereEqualTo("maPhong", listMaPhong.get(i))
                                            .whereEqualTo("maKhachSan", listMaKhachSan.get(0)).get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if(task.isSuccessful()) {
                                                        ArrayList<String> listMaPhong = new ArrayList<>();
                                                        for (DocumentSnapshot doc : task.getResult()) {
                                                            listMaPhong.add(doc.getString("maPhong"));
                                                        }

                                                        //Lay tat ca thong tin dat tu ma phong
                                                        for (int i = 0; i < listMaPhong.size(); i++)
                                                            db.collection("DaDat").whereEqualTo("maPhong", listMaPhong.get(i))
                                                                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                                                        @Override
                                                                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                                                            if (error != null) {
                                                                                Log.w(TAG, "Listen failed.", error);
                                                                                return;
                                                                            }
                                                                            if (value != null) {
                                                                                ArrayList<ThongTinDat> listThongTinDat = new ArrayList<>();
                                                                                for (DocumentSnapshot doc : value) {
                                                                                    listThongTinDat.add(doc.toObject(ThongTinDat.class));
                                                                                }
                                                                                danhSachDatCallBack.danhSachDatCallBack(listThongTinDat);
                                                                            } else {
                                                                                Log.d(TAG, "Current data: null");
                                                                            }
                                                                        }
                                                                    });
                                                    }
                                                }
                                            });
                                }
                            } else {
                                Log.d(TAG, "Current data: null");
                            }
                        }
                    });
                } else {
                    Log.d(TAG, "Error " + task.getException());
                }
            }
        });
    }

    public void thongTinDatFilter(List<String> tenNguoiDungList, DanhSachDatCallBack danhSachDatCallBack) {
        for (String tenNguoiDung : tenNguoiDungList) {
            db.collection("NguoiDung").whereEqualTo("tenNguoiDung", tenNguoiDung).addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if(error != null) {
                        Log.w(TAG, error);
                        return;
                    }

                    if (value != null) {
                        ArrayList<String> listMaNguoiDung = new ArrayList<>();
                        for (DocumentSnapshot doc : value) {
                            listMaNguoiDung.add(doc.getString("maNguoiDung"));
                        }

                        //Lay thong tin dat theo ma nguoi dung
                        for (String maNguoiDung : listMaNguoiDung) {
                            db.collection("DaDat").whereEqualTo("maNguoiDung", maNguoiDung)
                                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                            if (error != null) {
                                                Log.w(TAG, error);
                                                return;
                                            }

                                            if (value != null) {
                                                ArrayList<ThongTinDat> thongTinDatListFilter = new ArrayList<>();
                                                for (DocumentSnapshot doc : value) {
                                                    thongTinDatListFilter.add(doc.toObject(ThongTinDat.class));
                                                }
                                                danhSachDatCallBack.danhSachDatCallBack(thongTinDatListFilter);
                                            } else {
                                                Log.d(TAG, "Data thongTinDatFilter.getThongTinDat null");
                                            }
                                        }
                                    });
                        }
                    } else {
                        Log.d(TAG, "Data thongTinDatFilter.getMaNguoiDung null");
                    }
                }
            });
        }
    }
}
