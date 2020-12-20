package com.chuyende.hotelbookingappofhotel.firebase_models;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chuyende.hotelbookingappofhotel.Interface.DanhSachHuyCallBack;
import com.chuyende.hotelbookingappofhotel.Interface.DanhSachThanhToanCallBack;
import com.chuyende.hotelbookingappofhotel.Interface.DataCallBack;
import com.chuyende.hotelbookingappofhotel.Interface.ThongTinNguoiDungCallBack;
import com.chuyende.hotelbookingappofhotel.Interface.ThongTinPhongCallBack;
import com.chuyende.hotelbookingappofhotel.activities.DanhSachHuyFragment;
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

public class DBChiTietHuy {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static String TAG = "DBChiTietHuy";

    //Lay thong tin huy tu bang DaHuy theo maHuy
    public void getDataDaHuy(String maHuy, DanhSachHuyCallBack danhSachHuyCallBack) {
        db.collection("DaHuy").whereEqualTo("maHuy", maHuy).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null) {
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

    //Lay thong tin phong tu bang phong theo ThongTinHuy.maPhong
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

    //Lay thong tin nguoi dung tu bang NguoiDung theo ThongTinHuy.maNguoiDung
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

    //Sua trangThaiHoanTien trong bang daHuy
    public void updateThongTinHuy(ThongTinHuy thongTinHuy) {
        db.collection("DaHuy").document(thongTinHuy.getMaHuy())
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
