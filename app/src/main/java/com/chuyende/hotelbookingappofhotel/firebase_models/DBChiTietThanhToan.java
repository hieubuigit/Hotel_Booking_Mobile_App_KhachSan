package com.chuyende.hotelbookingappofhotel.firebase_models;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chuyende.hotelbookingappofhotel.interfaces.DanhSachThanhToanCallBack;
import com.chuyende.hotelbookingappofhotel.interfaces.DataCallBack;
import com.chuyende.hotelbookingappofhotel.interfaces.ThongTinNguoiDungCallBack;
import com.chuyende.hotelbookingappofhotel.interfaces.ThongTinPhongCallBack;
import com.chuyende.hotelbookingappofhotel.data_models.NguoiDung;
import com.chuyende.hotelbookingappofhotel.data_models.Phong;
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

    //Lay thong tin thanh toan phong tu bang DaThanhToan theo maThanhToan
    public void getDataDaThanhToan(String maThanhToan, DanhSachThanhToanCallBack danhSachThanhToanCallBack) {
        db.collection("DaThanhToan").whereEqualTo("maThanhToan", maThanhToan).addSnapshotListener(new EventListener<QuerySnapshot>() {
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

    //Lay thong tin phong tu bang phong theo ThongTinThanhToan.maPhong
    public void getDataPhong(String maPhong, ThongTinPhongCallBack thongTinPhongCallBack) {
        db.collection("Phong").whereEqualTo("maPhong", maPhong)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.w(TAG, error);
                            return;
                        }
                        if (value != null) {
                            ArrayList<Phong> phongList = new ArrayList<>();
                            for (DocumentSnapshot doc : value) {
                                phongList.add(doc.toObject(Phong.class));
                            }
                            thongTinPhongCallBack.thongTinPhongCallBack(phongList);
                        } else {
                            Log.d(TAG, "Data phong null");
                        }
                    }
                });
    }

    //Lay thong tin nguoi dung tu bang NguoiDung theo ThongTinThanhToan.maNguoiDung
    public void getDataNguoiDung(String maNguoiDung, ThongTinNguoiDungCallBack thongTinNguoiDungCallBack, DataCallBack dataCallBack) {
        db.collection("NguoiDung").whereEqualTo("maNguoiDung", maNguoiDung)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.w(TAG, error);
                            return;
                        }
                        if (value != null) {
                            ArrayList<NguoiDung> nguoiDungList = new ArrayList<>();
                            for (DocumentSnapshot doc : value) {
                                nguoiDungList.add(doc.toObject(NguoiDung.class));
                            }
                            thongTinNguoiDungCallBack.thongTinNguoiDungCallBack(nguoiDungList);

                            //Lay ra email tu bang TaiKhoanNguoiDung theo tenTaiKhoan trong bang NguoiDung
                            db.collection("TaiKhoanNguoiDung").whereEqualTo("tenTaiKhoan", nguoiDungList.get(0).getTenTaiKhoan())
                                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                            if (error != null) {
                                                Log.w(TAG, error);
                                                return;
                                            }
                                            if (value != null) {
                                                ArrayList<String> emailList = new ArrayList<>();
                                                for (DocumentSnapshot doc : value) {
                                                    emailList.add(doc.getString("email"));
                                                }
                                                dataCallBack.dataCallBack(emailList.get(0));
                                            } else {
                                                Log.d(TAG, "Data TaiKhoanNguoiDung null");
                                            }
                                        }
                                    });
                        } else {
                            Log.d(TAG, "Data nguoi dung null");
                        }
                    }
                });
    }

    //Them thong tin thanh toan vao bang DaHuy
    public void addThongTinThanhToanInTableDaHuy(ThongTinHuy thongTinHuy) {
        db.collection("DaHuy").document(thongTinHuy.getMaHuy())
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

    //Huy thong tin thanh toan sau khi them thong tin thanh toan vao bang DaHuy
    public void deleteThongTinThanhToan(String maThanhToan){
        db.collection("DaThanhToan").document(maThanhToan).delete()
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
