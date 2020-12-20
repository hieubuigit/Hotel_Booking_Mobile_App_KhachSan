package com.chuyende.hotelbookingappofhotel.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.chuyende.hotelbookingappofhotel.Interface.DanhSachDatCallBack;
import com.chuyende.hotelbookingappofhotel.Interface.DanhSachThanhToanCallBack;
import com.chuyende.hotelbookingappofhotel.Interface.DataCallBack;
import com.chuyende.hotelbookingappofhotel.Interface.ThongTinNguoiDungCallBack;
import com.chuyende.hotelbookingappofhotel.Interface.ThongTinPhongCallBack;
import com.chuyende.hotelbookingappofhotel.R;
import com.chuyende.hotelbookingappofhotel.data_models.NguoiDung;
import com.chuyende.hotelbookingappofhotel.data_models.Phong;
import com.chuyende.hotelbookingappofhotel.data_models.ThongTinDat;
import com.chuyende.hotelbookingappofhotel.data_models.ThongTinHuy;
import com.chuyende.hotelbookingappofhotel.data_models.ThongTinThanhToan;
import com.chuyende.hotelbookingappofhotel.firebase_models.DBChiTietDat;
import com.chuyende.hotelbookingappofhotel.firebase_models.DBChiTietThanhToan;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class ManHinhChiTietThanhToan extends AppCompatActivity {

    TextView tieuDe, tvMaNguoiDat, tvTenNguoiDat, tvGioiTinh, tvEmail, tvNgayDen, tvNgayDi, tvNgaySinh,
            tvQuocTich, tvMaPhong, tvTenPhong, tvSoNguoi, tvDiaDiem, tvGiaThue, tvTongPhi, tvDaThanhToan;
    Button btnHuy;
    Dialog dialog;

    private DBChiTietThanhToan dbChiTietThanhToan = new DBChiTietThanhToan();
    public static String TAG = "ManHinhChiTietThanhToan";
    public static String DAHUY = "DH";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chi_tiet_thanh_toan);

        setControl();
        setEvent();
    }

    private void setEvent() {
        //Thay doi tieu de
        tieuDe.setText("Thông tin chi tiết khách hàng thanh toán");
        //Dialog huy
        dialog = new Dialog(this);

        //Lay maThanhToan tu man hinh DanhSachThanhToanFragment
        Bundle bundle = getIntent().getExtras();
        String maThanhToan = bundle.getString("maThanhToan");

        //Hien thi thong tin chi tiet thanh toan , phong, nguoi dung
        dbChiTietThanhToan.getDataDaThanhToan(maThanhToan, new DanhSachThanhToanCallBack() {
            @Override
            public void danhSachThanhToanCallBack(ArrayList<ThongTinThanhToan> thanhToanList) {
                tvNgayDen.setText(thanhToanList.get(0).getNgayDen());
                tvNgayDi.setText(thanhToanList.get(0).getNgayDi());
                tvTongPhi.setText(thanhToanList.get(0).getTongThanhToan() + "");
                tvDaThanhToan.setText((int) thanhToanList.get(0).getSoTienThanhToanTruoc() + "");

                dbChiTietThanhToan.getDataPhong(thanhToanList.get(0).getMaPhong(), new ThongTinPhongCallBack() {
                    @Override
                    public void thongTinPhongCallBack(List<Phong> phongList) {
                        tvMaPhong.setText(phongList.get(0).getMaPhong());
                        tvTenPhong.setText(phongList.get(0).getTenPhong());
                        tvSoNguoi.setText(phongList.get(0).getSoKhach() + "");
                        tvDiaDiem.setText(phongList.get(0).getDiaChiPhong());
                        tvGiaThue.setText(((int) phongList.get(0).getGiaThue()) + "");
                    }
                });

                dbChiTietThanhToan.getDataNguoiDung(thanhToanList.get(0).getMaNguoiDung(), new ThongTinNguoiDungCallBack() {
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

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogHuy(maThanhToan);
            }
        });
    }

    //Ham tao chuoi random 20 ki tu
    public String createRandomAString() {
        String candidateChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        Random random = new Random();
        StringBuilder rndString = new StringBuilder();
        while (rndString.length() < 20) {
            int index = (int) (random.nextFloat() * candidateChars.length());
            rndString.append(candidateChars.charAt(index));
        }
        return rndString.toString();
    }

    //Ham lay ngay tu he thong
    private String getDateInSystem() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = simpleDateFormat.format(calendar.getTime());
        return currentDate;
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
        tvTongPhi = findViewById(R.id.tvTongPhi);
        tvDaThanhToan = findViewById(R.id.tvDaThanhToan);
        btnHuy = findViewById(R.id.btnHuy);
    }

    private void openDialogHuy(String maThanhToan) {
        dialog.setContentView(R.layout.custom_dialog_huy);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tieuDeDialog = dialog.findViewById(R.id.tvTieuDe);
        Button btnOk = dialog.findViewById(R.id.btnOk);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);

        tieuDeDialog.setText("Thông báo");

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Them thong tin thanh toan vao bang DaHuy
                dbChiTietThanhToan.getDataDaThanhToan(maThanhToan, new DanhSachThanhToanCallBack() {
                    @Override
                    public void danhSachThanhToanCallBack(ArrayList<ThongTinThanhToan> thanhToanList) {
                        ThongTinHuy thongTinHuy = new ThongTinHuy();
                        thongTinHuy.setMaHuy(DAHUY + createRandomAString());
                        thongTinHuy.setMaPhong(thanhToanList.get(0).getMaPhong());
                        thongTinHuy.setMaNguoiDung(thanhToanList.get(0).getMaNguoiDung());
                        thongTinHuy.setNgayDen(thanhToanList.get(0).getNgayDen());
                        thongTinHuy.setNgayDi(thanhToanList.get(0).getNgayDi());
                        thongTinHuy.setNgayHuy(thanhToanList.get(0).getNgayThanhToan());
                        thongTinHuy.setSoTienDaThanhToan(thanhToanList.get(0).getSoTienThanhToanTruoc());
                        thongTinHuy.setTrangThaiHoanTien("false");
                        dbChiTietThanhToan.addThongTinThanhToanInTableDaHuy(thongTinHuy);
                    }
                });
                //Xoa thong tin thanh toan
                //dbChiTietThanhToan.deleteThongTinThanhToan(maThanhToan);
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}