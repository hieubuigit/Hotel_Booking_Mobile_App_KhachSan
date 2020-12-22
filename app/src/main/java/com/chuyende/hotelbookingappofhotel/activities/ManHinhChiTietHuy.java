package com.chuyende.hotelbookingappofhotel.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.chuyende.hotelbookingappofhotel.Interface.DanhSachHuyCallBack;
import com.chuyende.hotelbookingappofhotel.Interface.DanhSachThanhToanCallBack;
import com.chuyende.hotelbookingappofhotel.Interface.DataCallBack;
import com.chuyende.hotelbookingappofhotel.Interface.ThongTinNguoiDungCallBack;
import com.chuyende.hotelbookingappofhotel.Interface.ThongTinPhongCallBack;
import com.chuyende.hotelbookingappofhotel.R;
import com.chuyende.hotelbookingappofhotel.data_models.NguoiDung;
import com.chuyende.hotelbookingappofhotel.data_models.Phong;
import com.chuyende.hotelbookingappofhotel.data_models.ThongTinHuy;
import com.chuyende.hotelbookingappofhotel.data_models.ThongTinThanhToan;
import com.chuyende.hotelbookingappofhotel.firebase_models.DBChiTietHuy;
import com.chuyende.hotelbookingappofhotel.firebase_models.DBChiTietThanhToan;

import java.util.ArrayList;
import java.util.List;

public class ManHinhChiTietHuy extends AppCompatActivity {
    TextView tieuDe, tvMaNguoiDat, tvTenNguoiDat, tvGioiTinh, tvEmail, tvNgayDen, tvNgayDi, tvNgaySinh,
            tvQuocTich, tvMaPhong, tvTenPhong, tvSoNguoi, tvDiaDiem, tvGiaThue, tvDaThanhToan;
    Button btnHoanTien;

    private DBChiTietHuy dbChiTietHuy = new DBChiTietHuy();
    public static String TAG = "ManHinhChiTietHuy";

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
        String maHuy = bundle.getString("maHuy");

        //Hien thi thong tin chi tiet huy, phong, nguoi dung
        dbChiTietHuy.getDataDaHuy(maHuy, new DanhSachHuyCallBack() {
            @Override
            public void danhSachHuyCallBack(ArrayList<ThongTinHuy> huyList) {
                tvNgayDen.setText(huyList.get(0).getNgayDen());
                tvNgayDi.setText(huyList.get(0).getNgayDi());
                tvDaThanhToan.setText((int) huyList.get(0).getSoTienDaThanhToan() + "");

                dbChiTietHuy.getDataPhong(huyList.get(0).getMaPhong(), new ThongTinPhongCallBack() {
                    @Override
                    public void thongTinPhongCallBack(List<Phong> phongList) {
                        tvMaPhong.setText(phongList.get(0).getMaPhong());
                        tvTenPhong.setText(phongList.get(0).getTenPhong());
                        tvSoNguoi.setText(phongList.get(0).getSoKhach() + "");
                        tvDiaDiem.setText(phongList.get(0).getDiaChiPhong());
                        tvGiaThue.setText(((int) phongList.get(0).getGiaThue()) + "");
                    }
                });

                dbChiTietHuy.getDataNguoiDung(huyList.get(0).getMaNguoiDung(), new ThongTinNguoiDungCallBack() {
                    @Override
                    public void thongTinNguoiDungCallBack(List<NguoiDung> nguoiDungList) {
                        tvMaNguoiDat.setText(nguoiDungList.get(0).getMaNguoiDung());
                        tvTenNguoiDat.setText(nguoiDungList.get(0).getTenNguoiDung());
                        tvGioiTinh.setText(nguoiDungList.get(0).getGioiTinh());
                        tvNgaySinh.setText(nguoiDungList.get(0).getNgaySinh());
                        tvQuocTich.setText(nguoiDungList.get(0).getQuocTich());
                    }
                }, new DataCallBack() {
                    @Override
                    public void dataCallBack(String info) {
                        tvEmail.setText(info);
                    }
                });
            }
        });

        btnHoanTien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbChiTietHuy.getDataDaHuy(maHuy, new DanhSachHuyCallBack() {
                    @Override
                    public void danhSachHuyCallBack(ArrayList<ThongTinHuy> huyList) {
                        ThongTinHuy thongTinHuy = new ThongTinHuy();
                        thongTinHuy.setMaHuy(maHuy);
                        thongTinHuy.setMaPhong(huyList.get(0).getMaPhong());
                        thongTinHuy.setMaNguoiDung(huyList.get(0).getMaNguoiDung());
                        thongTinHuy.setNgayDen(huyList.get(0).getNgayDen());
                        thongTinHuy.setNgayDi(huyList.get(0).getNgayDi());
                        thongTinHuy.setNgayHuy(huyList.get(0).getNgayHuy());
                        thongTinHuy.setSoTienDaThanhToan(huyList.get(0).getSoTienDaThanhToan());
                        thongTinHuy.setTrangThaiHoanTien("true");
                        dbChiTietHuy.updateThongTinHuy(thongTinHuy);
                    }
                });
                Toast.makeText(getApplicationContext(), "Hoàn tiền thành công", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setControl() {
        tieuDe = findViewById(R.id.tvTieuDe);
        tvMaNguoiDat = findViewById(R.id.tvMaNguoiDat);
        tvTenNguoiDat = findViewById(R.id.tvTenNguoiDat);
        tvGioiTinh = findViewById(R.id.tvGioiTinh);
        tvEmail = findViewById(R.id.tvEmail);
        tvNgayDen = findViewById(R.id.tvNgayDen);
        tvNgayDi = findViewById(R.id.tvNgayDi);
        tvNgaySinh = findViewById(R.id.tvNgaySinh);
        tvQuocTich = findViewById(R.id.tvQuocTich);
        tvMaPhong = findViewById(R.id.tvMaPhong);
        tvTenPhong = findViewById(R.id.tvTenPhong);
        tvSoNguoi = findViewById(R.id.tvSoNguoi);
        tvDiaDiem = findViewById(R.id.tvDiaDiem);
        tvGiaThue = findViewById(R.id.tvGiaThue);
        tvDaThanhToan = findViewById(R.id.tvDaThanhToan);
        btnHoanTien = findViewById(R.id.btnHoanTien);
    }
}