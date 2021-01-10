package com.chuyende.hotelbookingappofhotel.firebase_models;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.chuyende.hotelbookingappofhotel.interfaces.DanhSachDatCallBack;
import com.chuyende.hotelbookingappofhotel.interfaces.DataCallBack;
import com.chuyende.hotelbookingappofhotel.data_models.ThongTinDat;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
    public static String PHONG = "Phong";
    public static String MAPHONG = "maPhong";
    public static String TENPHONG = "tenPhong";
    public static String NGUOIDUNG = "NguoiDung";
    public static String MANGUOIDUNG = "maNguoiDung";
    public static String TENNGUOIDUNG = "tenNguoiDung";
    public static String KHACHSAN = "KhachSan";
    public static String MAKHACHSAN = "maKhachSan";
    public static String TENTAIKHOANKHACHSAN = "tenTaiKhoanKhachSan";
    public static String DADAT = "DaDat";
    public static String MADAT = "maDat";

    public void getTenPhong(String maPhong, DataCallBack dataCallBack) {
        db.collection(PHONG).whereEqualTo(MAPHONG, maPhong).get().
                addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<String> listTenPhong = new ArrayList<>();
                            for (DocumentSnapshot document : task.getResult()) {
                                listTenPhong.add(document.getString(TENPHONG));
                            }
                            dataCallBack.dataCallBack(listTenPhong.get(0));
                        } else {
                            Log.d(TAG, "Error" + task.getException());
                        }
                    }
                });
    }

    public void getTenNguoiDung(String maNguoiDung, DataCallBack dataCallBack) {
        db.collection(NGUOIDUNG).whereEqualTo(MANGUOIDUNG, maNguoiDung).get().
                addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<String> listTenNguoiDung = new ArrayList<>();
                            for (DocumentSnapshot document : task.getResult()) {
                                listTenNguoiDung.add(document.getString(TENNGUOIDUNG));
                            }
                            dataCallBack.dataCallBack(listTenNguoiDung.get(0));
                        } else {
                            Log.d(TAG, "Error" + task.getException());
                        }
                    }
                });
    }

    public void readAllDataDat(String tenTaiKhoanKhachSan, DanhSachDatCallBack danhSachDatCallBack) {
        db.collection(KHACHSAN).whereEqualTo(TENTAIKHOANKHACHSAN, tenTaiKhoanKhachSan).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> maKhachSanList = new ArrayList<>();
                    for (DocumentSnapshot doc : task.getResult()) {
                        maKhachSanList.add(doc.getString(MAKHACHSAN));
                    }

                    db.collection(DADAT).whereEqualTo(MAKHACHSAN, maKhachSanList.get(0))
                            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            if (error != null) {
                                Log.w(TAG, error);
                                return;
                            }
                            if (value != null) {
                                ArrayList<ThongTinDat> thongTinDatList = new ArrayList<>();
                                for (DocumentSnapshot doc : value) {
                                    thongTinDatList.add(doc.toObject(ThongTinDat.class));
                                }
                                danhSachDatCallBack.danhSachDatCallBack(thongTinDatList);
                            } else {
                                Log.d(TAG, "Data danh sach dat null");
                            }
                        }
                    });
                }else {
                    Log.d(TAG, "Error " + task.getException());
                }
            }
        });
    }

    public void thongTinDatFilter(String tenTaiKhoanKhachSan, List<String> tenNguoiDungList, DanhSachDatCallBack danhSachDatCallBack) {
        for (String tenNguoiDung : tenNguoiDungList) {
            db.collection(NGUOIDUNG).whereEqualTo(TENNGUOIDUNG, tenNguoiDung).addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (error != null) {
                        Log.w(TAG, error);
                        return;
                    }
                    if (value != null) {
                        ArrayList<String> listMaNguoiDung = new ArrayList<>();
                        for (DocumentSnapshot doc : value) {
                            listMaNguoiDung.add(doc.getString(MANGUOIDUNG));
                        }

                        db.collection(KHACHSAN).whereEqualTo(TENTAIKHOANKHACHSAN, tenTaiKhoanKhachSan).get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            List<String> maKhachSanList = new ArrayList<>();
                                            for (DocumentSnapshot doc : task.getResult()) {
                                                maKhachSanList.add(doc.getString(MAKHACHSAN));
                                            }

                                            //Lay thong tin dat theo ma nguoi dung
                                            for (String maNguoiDung : listMaNguoiDung) {
                                                db.collection(DADAT).whereEqualTo(MANGUOIDUNG, maNguoiDung)
                                                        .whereEqualTo(MAKHACHSAN, maKhachSanList.get(0))
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
                                                                    Log.d(TAG, "Data ThongTinDat null");
                                                                }
                                                            }
                                                        });
                                            }
                                        } else {
                                            Log.d(TAG, "Error " + task.getException());
                                        }
                                    }
                                });
                    } else {
                        Log.d(TAG, "Data NguoiDung null");
                    }
                }
            });
        }
    }

    //Xoa thong tin dat
    public void deleteOneThongTinDat(String maDat) {
        db.collection(DADAT).document(maDat).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "delete thong tin dat success");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "delete thong tin dat failure");
            }
        });
    }

    //Xoa tat ca thong tin dat co chung maPhong
    public void deleteThongTinDat(String tenTaiKhoanKhachSan, String maPhong) {
        db.collection(KHACHSAN).whereEqualTo(TENTAIKHOANKHACHSAN, tenTaiKhoanKhachSan).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> maKhachSanList = new ArrayList<>();
                    for (DocumentSnapshot doc : task.getResult()) {
                        maKhachSanList.add(doc.getString(MAKHACHSAN));
                    }

                    db.collection(DADAT).whereEqualTo(MAKHACHSAN, maKhachSanList.get(0)).whereEqualTo(MAPHONG, maPhong)
                            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                    if (error != null) {
                                        Log.w(TAG, error);
                                        return;
                                    }
                                    if (value != null) {
                                        List<String> maDatList = new ArrayList<>();
                                        for (DocumentSnapshot doc : value) {
                                            maDatList.add(doc.getString(MADAT));
                                        }

                                        for (String maDat : maDatList) {
                                            db.collection(DADAT).document(maDat).delete()
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Log.d(TAG, "delete thong tin dat success");
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.d(TAG, "delete thong tin dat failure " + e);
                                                }
                                            });
                                        }
                                    } else {
                                        Log.d(TAG, "Data danh sach dat null");
                                    }
                                }
                            });
                } else {
                    Log.d(TAG, "Error " + task.getException());
                }
            }
        });
    }
}
