package com.chuyende.hotelbookingappofhotel.firebase_models;

import android.util.Log;

import androidx.annotation.Nullable;

import com.chuyende.hotelbookingappofhotel.Interface.DanhSachDatCallBack;
import com.chuyende.hotelbookingappofhotel.Interface.DataCallBack;
import com.chuyende.hotelbookingappofhotel.Interface.ThongTinNguoiDungCallBack;
import com.chuyende.hotelbookingappofhotel.Interface.ThongTinPhongCallBack;
import com.chuyende.hotelbookingappofhotel.data_models.NguoiDung;
import com.chuyende.hotelbookingappofhotel.data_models.Phong;
import com.chuyende.hotelbookingappofhotel.data_models.ThongTinDat;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DBChiTietDat {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static String TAG = "DBChiTietDat";

    public void getDataDaDat(String maDat, DanhSachDatCallBack danhSachDatCallBack) {
        //Lay thong tin dat phong tu bang DaDat theo maDat
        db.collection("DaDat").whereEqualTo("maDat", maDat).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null) {
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
                    Log.d(TAG, "Data chi tiet dat null");
                }
            }
        });
    }

    public void getDataPhong(String maPhong, ThongTinPhongCallBack thongTinPhongCallBack) {
        //Lay thong tin phong tu bang phong theo ThongTinDat.maPhong
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

    public void getDataNguoiDung(String maNguoiDung, ThongTinNguoiDungCallBack thongTinNguoiDungCallBack, DataCallBack dataCallBack) {
        //Lay thong tin nguoi dung tu bang NguoiDung theo ThongTinDat.maNguoiDung
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
}
