package com.chuyende.hotelbookingappofhotel.firebase_models;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chuyende.hotelbookingappofhotel.interfaces.DanhSachThanhToanCallBack;
import com.chuyende.hotelbookingappofhotel.data_models.ThongTinThanhToan;
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

public class DBDanhSachThanhToan {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static String TAG = "DBDanhSachThanhToan";
    public static String NGUOIDUNG = "NguoiDung";
    public static String MANGUOIDUNG = "maNguoiDung";
    public static String TENNGUOIDUNG = "tenNguoiDung";
    public static String KHACHSAN = "KhachSan";
    public static String MAKHACHSAN = "maKhachSan";
    public static String TENTAIKHOANKHACHSAN = "tenTaiKhoanKhachSan";
    public static String DATHANHTOAN = "DaThanhToan";
    public static String TRANGTHAIHOANTATTHANHTOAN = "trangThaiHoanTatThanhToan";
    public static String TRANGTHAIHOANTATTHANHTOANTHATBAI = "false";
    public static String TRANGTHAIHOANTATTHANHTOANTHANHCONG = "true";

    public void readAllDataThanhToan(String taiKhoanKhachSan, DanhSachThanhToanCallBack danhSachThanhToanCallBack) {
        db.collection(KHACHSAN).whereEqualTo(TENTAIKHOANKHACHSAN, taiKhoanKhachSan).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> maKhachSanList = new ArrayList<>();
                    for (DocumentSnapshot doc : task.getResult()) {
                        maKhachSanList.add(doc.getString(MAKHACHSAN));
                    }
                    db.collection(DATHANHTOAN).whereEqualTo(MAKHACHSAN, maKhachSanList.get(0))
                            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            if (error != null) {
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
                                Log.d(TAG, "Data danh sach thanh toan null");
                            }
                        }
                    });
                } else {
                    Log.d(TAG, "Error " + task.getException());
                }
            }
        });
    }

    public void readAllDataThanhToanTruoc(String taiKhoanKhachSan, DanhSachThanhToanCallBack danhSachThanhToanCallBack) {
        db.collection(KHACHSAN).whereEqualTo(TENTAIKHOANKHACHSAN, taiKhoanKhachSan).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<String> maKhachSanList = new ArrayList<>();
                            for (DocumentSnapshot doc : task.getResult()) {
                                maKhachSanList.add(doc.getString(MAKHACHSAN));
                            }
                            db.collection(DATHANHTOAN).whereEqualTo(MAKHACHSAN, maKhachSanList.get(0))
                                    .whereEqualTo(TRANGTHAIHOANTATTHANHTOAN, TRANGTHAIHOANTATTHANHTOANTHATBAI)
                                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                            if (error != null) {
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
                                                Log.d(TAG, "Data danh sach thanh toan truoc null");
                                            }
                                        }
                                    });
                        } else {
                            Log.d(TAG, "Error " + task.getException());
                        }
                    }
                });
    }

    public void readAllDataThanhToanDu(String taiKhoanKhachSan, DanhSachThanhToanCallBack danhSachThanhToanCallBack) {
        db.collection(KHACHSAN).whereEqualTo(TENTAIKHOANKHACHSAN, taiKhoanKhachSan).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<String> maKhachSanList = new ArrayList<>();
                            for (DocumentSnapshot doc : task.getResult()) {
                                maKhachSanList.add(doc.getString(MAKHACHSAN));
                            }
                            db.collection(DATHANHTOAN).whereEqualTo(MAKHACHSAN, maKhachSanList.get(0))
                                    .whereEqualTo(TRANGTHAIHOANTATTHANHTOAN, TRANGTHAIHOANTATTHANHTOANTHANHCONG)
                                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                            if (error != null) {
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
                                                Log.d(TAG, "Data danh sach thanh toan du null");
                                            }
                                        }
                                    });
                        } else {
                            Log.d(TAG, "Error " + task.getException());
                        }
                    }
                });
    }

    public void thongTinThanhToanFilter(List<String> tenNguoiDungList, DanhSachThanhToanCallBack danhSachThanhToanCallBack) {
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

                        //Lay thong tin thanh toan theo ma nguoi dung
                        for (String maNguoiDung : listMaNguoiDung) {
                            db.collection(DATHANHTOAN).whereEqualTo(MANGUOIDUNG, maNguoiDung)
                                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                            if (error != null) {
                                                Log.w(TAG, error);
                                                return;
                                            }

                                            if (value != null) {
                                                ArrayList<ThongTinThanhToan> thongTinThanhToanFilter = new ArrayList<>();
                                                for (DocumentSnapshot doc : value) {
                                                    thongTinThanhToanFilter.add(doc.toObject(ThongTinThanhToan.class));
                                                }
                                                danhSachThanhToanCallBack.danhSachThanhToanCallBack(thongTinThanhToanFilter);
                                            } else {
                                                Log.d(TAG, "Data ThongTinDat null");
                                            }
                                        }
                                    });
                        }
                    } else {
                        Log.d(TAG, "Data MaNguoiDung null");
                    }
                }
            });
        }
    }

    public void thanhToanDu(ThongTinThanhToan thongTinThanhToan) {
        db.collection(DATHANHTOAN).document(thongTinThanhToan.getMaThanhToan())
                .set(thongTinThanhToan).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "Thanh toán hoàn tất");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "Thanh toán thất bại " + e);
            }
        });
    }

    public void deleteThongTinThanhToan(String maThanhToan){
        db.collection(DATHANHTOAN).document(maThanhToan).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Delete thong tin thanh toan success");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "Delete thong tin thanh toan false " + e);
            }
        });
    }
}
