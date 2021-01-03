package com.chuyende.hotelbookingappofhotel.firebase_models;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.chuyende.hotelbookingappofhotel.interfaces.DanhSachHuyCallBack;
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
    public static String NGUOIDUNG = "NguoiDung";
    public static String MANGUOIDUNG = "maNguoiDung";
    public static String TENNGUOIDUNG = "tenNguoiDung";
    public static String KHACHSAN = "KhachSan";
    public static String MAKHACHSAN = "maKhachSan";
    public static String TENTAIKHOANKHACHSAN = "tenTaiKhoanKhachSan";
    public static String DAHUY = "DaHuy";
    public static String TRANGTHAIHOANTIEN = "trangThaiHoanTien";
    public static String TRANGTHAIHOANTIENTHATBAI = "false";
    public static String TRANGTHAIHOANTIENTHANHCONG = "true";

    public void readAllDataHuy(String tenTaiKhoanKhachSan, DanhSachHuyCallBack danhSachHuyCallBack) {
        db.collection(KHACHSAN).whereEqualTo(TENTAIKHOANKHACHSAN, tenTaiKhoanKhachSan).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> maKhachSanList = new ArrayList<>();
                     for (DocumentSnapshot doc : task.getResult()) {
                        maKhachSanList.add(doc.getString(MAKHACHSAN));
                    }

                     db.collection(DAHUY).whereEqualTo(MAKHACHSAN, maKhachSanList.get(0))
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
                             } else {
                                 Log.d(TAG, "Data danh sach huy null");
                             }
                         }
                     });
                } else {
                    Log.d(TAG, "Error " + task.getException());
                }
            }
        });
    }

    public void readAllDataHuyChuaHoanTien(String tenTaiKhoanKhachSan, DanhSachHuyCallBack danhSachHuyCallBack) {
        db.collection(KHACHSAN).whereEqualTo(TENTAIKHOANKHACHSAN, tenTaiKhoanKhachSan).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<String> maKhachSanList = new ArrayList<>();
                            for (DocumentSnapshot doc : task.getResult()) {
                                maKhachSanList.add(doc.getString(MAKHACHSAN));
                            }

                            db.collection(DAHUY).whereEqualTo(MAKHACHSAN, maKhachSanList.get(0))
                                    .whereEqualTo(TRANGTHAIHOANTIEN, TRANGTHAIHOANTIENTHATBAI)
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
                                            } else {
                                                Log.d(TAG, "Data danh sach huy null");
                                            }
                                        }
                                    });
                        } else {
                            Log.d(TAG, "Error " + task.getException());
                        }
                    }
                });
    }

    public void readAllDataHuyDaHoanTien(String tenTaiKhoanKhachSan, DanhSachHuyCallBack danhSachHuyCallBack) {
        db.collection(KHACHSAN).whereEqualTo(TENTAIKHOANKHACHSAN, tenTaiKhoanKhachSan).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<String> maKhachSanList = new ArrayList<>();
                            for (DocumentSnapshot doc : task.getResult()) {
                                maKhachSanList.add(doc.getString(MAKHACHSAN));
                            }

                            db.collection(DAHUY).whereEqualTo(MAKHACHSAN, maKhachSanList.get(0))
                                    .whereEqualTo(TRANGTHAIHOANTIEN, TRANGTHAIHOANTIENTHANHCONG)
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
                                            } else {
                                                Log.d(TAG, "Data danh sach huy null");
                                            }
                                        }
                                    });
                        } else {
                            Log.d(TAG, "Error " + task.getException());
                        }
                    }
                });
    }

    public void thongTinHuyFilter(List<String> tenNguoiDungList, DanhSachHuyCallBack danhSachHuyCallBack) {
        for (String tenNguoiDung : tenNguoiDungList) {
            db.collection(NGUOIDUNG).whereEqualTo(TENNGUOIDUNG, tenNguoiDung).addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if(error != null) {
                        Log.w(TAG, error);
                        return;
                    }

                    if (value != null) {
                        ArrayList<String> listMaNguoiDung = new ArrayList<>();
                        for (DocumentSnapshot doc : value) {
                            listMaNguoiDung.add(doc.getString(MANGUOIDUNG));
                        }

                        //Lay thong tin huy theo ma nguoi dung
                        for (String maNguoiDung : listMaNguoiDung) {
                            db.collection(DAHUY).whereEqualTo(MANGUOIDUNG, maNguoiDung)
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
