package com.chuyende.hotelbookingappofhotel.firebase_models;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chuyende.hotelbookingappofhotel.interfaces.DanhSachHuyCallBack;
import com.chuyende.hotelbookingappofhotel.interfaces.DataCallBack;
import com.chuyende.hotelbookingappofhotel.data_models.ThongTinHuy;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DBDanhSachHuy {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static String TAG = "DBDanhSachHuy";

    public void getTenPhong(String maPhong, DataCallBack dataCallBack) {
        db.collection("Phong").whereEqualTo("maPhong", maPhong).get().
                addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
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
                        if (task.isSuccessful()) {
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

    public void hienThiThongTinHuy(String taiKhoanKhachSan, DanhSachHuyCallBack danhSachHuyCallBack) {
        //Lay ma khach san tu ten tai khoan cua khach san
        db.collection("KhachSan").whereEqualTo("tenTaiKhoanKhachSan", taiKhoanKhachSan)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> listMaKhachSan = new ArrayList<>();
                    for (DocumentSnapshot document : task.getResult()) {
                        listMaKhachSan.add(document.getString("maKhachSan"));
                    }

                    db.collection("DaHuy").addSnapshotListener(new EventListener<QuerySnapshot>() {
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
                                                    if (task.isSuccessful()) {
                                                        ArrayList<String> listMaPhong = new ArrayList<>();
                                                        for (DocumentSnapshot doc : task.getResult()) {
                                                            listMaPhong.add(doc.getString("maPhong"));
                                                        }

                                                        for (String s : listMaPhong) {
                                                            Log.d(TAG, s);
                                                        }

                                                        //Lay tat ca thong tin huy tu ma phong
                                                        for (String phong : listMaPhong) {
                                                            db.collection("DaHuy").whereEqualTo("maPhong", phong)
                                                                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
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
                                                                            }
                                                                        }
                                                                    });
                                                        }
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

    public void hienThiThongTinHuyChuaHoanTien(ArrayList<ThongTinHuy> thongTinHuyArrayList, DanhSachHuyCallBack danhSachHuyCallBack) {
        for (ThongTinHuy thongTinHuy : thongTinHuyArrayList) {
            db.collection("DaHuy").whereEqualTo("maHuy", thongTinHuy.getMaHuy())
                    .whereEqualTo("trangThaiHoanTien", "false").addSnapshotListener(new EventListener<QuerySnapshot>() {
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
                        Log.d(TAG, "get data hienThiThongTinHuyChuaHoanTien false");
                    }
                }
            });
        }
    }

    public void hienThiThongTinHuyDaHoanTien(ArrayList<ThongTinHuy> thongTinHuyArrayList, DanhSachHuyCallBack danhSachHuyCallBack) {
        for (ThongTinHuy thongTinHuy : thongTinHuyArrayList) {
            db.collection("DaHuy").whereEqualTo("maHuy", thongTinHuy.getMaHuy())
                    .whereEqualTo("trangThaiHoanTien", "true").addSnapshotListener(new EventListener<QuerySnapshot>() {
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
                        Log.d(TAG, "get data hienThiThongTinHuyDaHoanTien false");
                    }
                }
            });
        }
    }

    public void thongTinHuyFilter(List<String> tenNguoiDungList, DanhSachHuyCallBack danhSachHuyCallBack) {
        for (String tenNguoiDung : tenNguoiDungList) {
            db.collection("NguoiDung").whereEqualTo("tenNguoiDung", tenNguoiDung).addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (error != null) {
                        Log.w(TAG, error);
                        return;
                    }

                    if (value != null) {
                        ArrayList<String> listMaNguoiDung = new ArrayList<>();
                        for (DocumentSnapshot doc : value) {
                            listMaNguoiDung.add(doc.getString("maNguoiDung"));
                        }

                        //Lay thong tin huy theo ma nguoi dung
                        for (String maNguoiDung : listMaNguoiDung) {
                            db.collection("DaHuy").whereEqualTo("maNguoiDung", maNguoiDung)
                                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                            if (error != null) {
                                                Log.w(TAG, error);
                                                return;
                                            }

                                            if (value != null) {
                                                ArrayList<ThongTinHuy> thongTinHuyFilter = new ArrayList<>();
                                                for (DocumentSnapshot doc : value) {
                                                    thongTinHuyFilter.add(doc.toObject(ThongTinHuy.class));
                                                }
                                                danhSachHuyCallBack.danhSachHuyCallBack(thongTinHuyFilter);
                                            } else {
                                                Log.d(TAG, "Data thongTinHuyFilter.getThongTinHuy null");
                                            }
                                        }
                                    });
                        }
                    } else {
                        Log.d(TAG, "Data thongTinHuyFilter.getMaNguoiDung null");
                    }
                }
            });
        }
    }
}
