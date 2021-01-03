package com.chuyende.hotelbookingappofhotel.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.chuyende.hotelbookingappofhotel.firebase_models.DBChiTietDat;
import com.chuyende.hotelbookingappofhotel.interfaces.DanhSachHuyCallBack;
import com.chuyende.hotelbookingappofhotel.interfaces.DataCallBack;
import com.chuyende.hotelbookingappofhotel.interfaces.ThongTinNguoiDungCallBack;
import com.chuyende.hotelbookingappofhotel.interfaces.ThongTinPhongCallBack;
import com.chuyende.hotelbookingappofhotel.R;
import com.chuyende.hotelbookingappofhotel.data_models.NguoiDung;
import com.chuyende.hotelbookingappofhotel.data_models.Phong;
import com.chuyende.hotelbookingappofhotel.data_models.ThongTinHuy;
import com.chuyende.hotelbookingappofhotel.firebase_models.DBChiTietHuy;
import static com.chuyende.hotelbookingappofhotel.activities.DanhSachHuyFragment.MAHUY;

import java.util.ArrayList;
import java.util.List;

public class ManHinhChiTietHuy extends AppCompatActivity {
    TextView tieuDe, tvTenNguoiDat, tvGioiTinh, tvEmail, tvNgayDen, tvNgayDi, tvNgaySinh,
            tvQuocTich, tvTenPhong, tvSoNguoi, tvDiaDiem, tvGiaThue, tvDaThanhToan;
    Button btnHoanTien;

    private DBChiTietHuy dbChiTietHuy = new DBChiTietHuy();
    private DBChiTietDat dbChiTietDat = new DBChiTietDat();
    private ArrayList<ThongTinHuy> thongTinHuys = new ArrayList<>();
    private ArrayList<Phong> phongs = new ArrayList<>();
    private ArrayList<NguoiDung> nguoiDungs = new ArrayList<>();
    private ArrayList<String> email = new ArrayList<>();
    public static String TAG = "ManHinhChiTietHuy";
    public static String TRANGTHAIHOANTIEN = "true";
    public static String MHHUY = "MHHuy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chi_tiet_huy);
        setControl();
        setEvent();
    }

    private void setEvent() {
        //Thay doi tieu de
        tieuDe.setText("Thong tin chi tiết khách hàng hủy");

        //Lay maHuy tu man hinh DanhSachHuyFragment
        Bundle bundle = getIntent().getExtras();
        String maHuy = bundle.getString(MAHUY);

        //Hien thi thong tin chi tiet huy, phong, nguoi dung
        try {
            dbChiTietHuy.getDataDaHuy(maHuy, new DanhSachHuyCallBack() {
                @Override
                public void danhSachHuyCallBack(ArrayList<ThongTinHuy> huyList) {
                    for (ThongTinHuy thongTinHuy : huyList) {
                        thongTinHuy = huyList.get(0);
                        thongTinHuys.add(thongTinHuy);
                    }
                    tvNgayDen.setText(thongTinHuys.get(0).getNgayDen());
                    tvNgayDi.setText(thongTinHuys.get(0).getNgayDi());
                    tvDaThanhToan.setText((int) thongTinHuys.get(0).getSoTienDaThanhToan() + "");

                    try {
                        dbChiTietDat.getDataPhong(huyList.get(0).getMaPhong(), new ThongTinPhongCallBack() {
                            @Override
                            public void thongTinPhongCallBack(List<Phong> phongList) {
                                for (Phong phong : phongList) {
                                    phong = phongList.get(0);
                                    phongs.add(phong);
                                }
                                tvTenPhong.setText(phongs.get(0).getTenPhong());
                                tvSoNguoi.setText(phongs.get(0).getSoKhach() + "");
                                tvDiaDiem.setText(phongs.get(0).getDiaChiPhong());
                                tvGiaThue.setText(((int) phongs.get(0).getGiaThue()) + "");
                            }
                        });
                    }catch (Exception e) {
                        Log.d(TAG, "Lỗi: " + e);
                    }

                    try {
                        dbChiTietDat.getDataNguoiDung(huyList.get(0).getMaNguoiDung(), new ThongTinNguoiDungCallBack() {
                            @Override
                            public void thongTinNguoiDungCallBack(List<NguoiDung> nguoiDungList) {
                                for (NguoiDung nguoiDung : nguoiDungList) {
                                    nguoiDung = nguoiDungList.get(0);
                                    nguoiDungs.add(nguoiDung);
                                }
                                tvTenNguoiDat.setText(nguoiDungs.get(0).getTenNguoiDung());
                                tvGioiTinh.setText(nguoiDungs.get(0).getGioiTinh());
                                tvNgaySinh.setText(nguoiDungs.get(0).getNgaySinh());
                                tvQuocTich.setText(nguoiDungs.get(0).getQuocTich());
                            }
                        }, new DataCallBack() {
                            @Override
                            public void dataCallBack(String info) {
                                email.add(info);
                                tvEmail.setText(email.get(0));
                            }
                        });
                    }catch (Exception e) {
                        Log.d(TAG, "Lỗi: " + e);
                    }
                }
            });
        }catch (Exception e) {
            Log.d(TAG, "Lỗi: " + e);
        }

        //Thay doi trang thai hoan tien khi tap vao nut hoan tien
        btnHoanTien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    dbChiTietHuy.getDataDaHuy(maHuy, new DanhSachHuyCallBack() {
                        @Override
                        public void danhSachHuyCallBack(ArrayList<ThongTinHuy> huyList) {
                            ThongTinHuy thongTinHuy = new ThongTinHuy();
                            thongTinHuy.setMaHuy(maHuy);
                            thongTinHuy.setMaPhong(huyList.get(0).getMaPhong());
                            thongTinHuy.setMaNguoiDung(huyList.get(0).getMaNguoiDung());
                            thongTinHuy.setMaKhachSan(huyList.get(0).getMaKhachSan());
                            thongTinHuy.setNgayDen(huyList.get(0).getNgayDen());
                            thongTinHuy.setNgayDi(huyList.get(0).getNgayDi());
                            thongTinHuy.setNgayHuy(huyList.get(0).getNgayHuy());
                            thongTinHuy.setSoTienDaThanhToan(huyList.get(0).getSoTienDaThanhToan());
                            thongTinHuy.setTrangThaiHoanTien(TRANGTHAIHOANTIEN);
                            dbChiTietHuy.updateThongTinHuy(thongTinHuy);
                            //Chuyen ma hinh sang man hinh danh sach huy
                            moveScreen();
                        }
                    });
                }catch (Exception e) {
                    Log.d(TAG, "Lỗi: " + e);
                }
                setToastMessageSuccess("Hoàn tiền thành công");
            }
        });
    }

    private void setControl() {
        tieuDe = findViewById(R.id.tvTieuDe);
        tvTenNguoiDat = findViewById(R.id.tvTenNguoiDat);
        tvGioiTinh = findViewById(R.id.tvGioiTinh);
        tvEmail = findViewById(R.id.tvEmail);
        tvNgayDen = findViewById(R.id.tvNgayDen);
        tvNgayDi = findViewById(R.id.tvNgayDi);
        tvNgaySinh = findViewById(R.id.tvNgaySinh);
        tvQuocTich = findViewById(R.id.tvQuocTich);
        tvTenPhong = findViewById(R.id.tvTenPhong);
        tvSoNguoi = findViewById(R.id.tvSoNguoi);
        tvDiaDiem = findViewById(R.id.tvDiaDiem);
        tvGiaThue = findViewById(R.id.tvGiaThue);
        tvDaThanhToan = findViewById(R.id.tvDaThanhToan);
        btnHoanTien = findViewById(R.id.btnHoanTien);
    }

    private void setToastMessageSuccess(String text) {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_toast_message_success, (ViewGroup)findViewById(R.id.ToastMessage_layout));
        TextView textView = view.findViewById(R.id.tvTextToast);
        textView.setText(text);

        final Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }

    private void moveScreen() {
        Intent intent = new Intent(ManHinhChiTietHuy.this, MainFragment.class);
        Bundle bundle1 = new Bundle();
        bundle1.putString(MHHUY, MHHUY);
        intent.putExtras(bundle1);
        startActivity(intent);
    }
}