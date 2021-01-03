package com.chuyende.hotelbookingappofhotel.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.chuyende.hotelbookingappofhotel.interfaces.DanhSachThanhToanCallBack;
import com.chuyende.hotelbookingappofhotel.interfaces.DataCallBack;
import com.chuyende.hotelbookingappofhotel.interfaces.ThongTinNguoiDungCallBack;
import com.chuyende.hotelbookingappofhotel.interfaces.ThongTinPhongCallBack;
import com.chuyende.hotelbookingappofhotel.R;
import com.chuyende.hotelbookingappofhotel.data_models.NguoiDung;
import com.chuyende.hotelbookingappofhotel.data_models.Phong;
import com.chuyende.hotelbookingappofhotel.data_models.ThongTinHuy;
import com.chuyende.hotelbookingappofhotel.data_models.ThongTinThanhToan;
import com.chuyende.hotelbookingappofhotel.firebase_models.DBChiTietThanhToan;
import static com.chuyende.hotelbookingappofhotel.activities.DanhSachThanhToanFragment.MATHANHTOAN;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ManHinhChiTietThanhToan extends AppCompatActivity {
    TextView tieuDe, tvTenNguoiDat, tvGioiTinh, tvEmail, tvNgayDen, tvNgayDi, tvNgaySinh,
            tvQuocTich, tvTenPhong, tvSoNguoi, tvDiaDiem, tvGiaThue, tvTongPhi, tvDaThanhToan;
    Button btnHuy;
    Dialog dialog;
    public static String TAG = "ManHinhChiTietThanhToan";
    public static String DAHUY = "DH";
    public static String TRANGTHAIHOANTIEN = "false";
    public static String TRANGTHAIPHONG = "Đang trống";
    public static String MHTHANHTOAN = "MHThanhToan";
    public static String MATT = "MaTT";
    private DBChiTietThanhToan dbChiTietThanhToan = new DBChiTietThanhToan();
    private DBChiTietDat dbChiTietDat = new DBChiTietDat();
    private ArrayList<ThongTinThanhToan> thongTinThanhToans = new ArrayList<>();
    private ArrayList<Phong> phongs = new ArrayList<>();
    private ArrayList<NguoiDung> nguoiDungs = new ArrayList<>();
    private List<String> email = new ArrayList<>();

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
        String maThanhToan = bundle.getString(MATHANHTOAN);

        //Hien thi thong tin chi tiet thanh toan , phong, nguoi dung
        try {
            dbChiTietThanhToan.getDataDaThanhToan(maThanhToan, new DanhSachThanhToanCallBack() {
                @Override
                public void danhSachThanhToanCallBack(ArrayList<ThongTinThanhToan> thanhToanList) {
                    for (ThongTinThanhToan thongTinThanhToan : thanhToanList) {
                        thongTinThanhToan.setMaPhong(thanhToanList.get(0).getMaPhong());
                        thongTinThanhToan.setMaNguoiDung(thanhToanList.get(0).getMaNguoiDung());
                        thongTinThanhToan.setNgayDen(thanhToanList.get(0).getNgayDen());
                        thongTinThanhToan.setNgayDi(thanhToanList.get(0).getNgayDi());
                        thongTinThanhToan.setTongThanhToan(thanhToanList.get(0).getTongThanhToan());
                        thongTinThanhToan.setSoTienThanhToanTruoc(thanhToanList.get(0).getSoTienThanhToanTruoc());
                        thongTinThanhToans.add(thongTinThanhToan);
                    }
                    tvNgayDen.setText(thongTinThanhToans.get(0).getNgayDen());
                    tvNgayDi.setText(thongTinThanhToans.get(0).getNgayDi());
                    tvTongPhi.setText(thongTinThanhToans.get(0).getTongThanhToan() + "");
                    tvDaThanhToan.setText((int) thongTinThanhToans.get(0).getSoTienThanhToanTruoc() + "");

                    try {
                        dbChiTietDat.getDataPhong(thongTinThanhToans.get(0).getMaPhong(), new ThongTinPhongCallBack() {
                            @Override
                            public void thongTinPhongCallBack(List<Phong> phongList) {
                                for (Phong phong : phongList) {
                                    phong.setMaPhong(phongList.get(0).getMaPhong());
                                    phong.setTenPhong(phongList.get(0).getTenPhong());
                                    phong.setSoKhach(phongList.get(0).getSoKhach());
                                    phong.setDiaChiPhong(phongList.get(0).getDiaChiPhong());
                                    phong.setGiaThue(phongList.get(0).getGiaThue());
                                    phongs.add(phong);
                                }
                                tvTenPhong.setText(phongs.get(0).getTenPhong());
                                tvSoNguoi.setText(phongs.get(0).getSoKhach() + "");
                                tvDiaDiem.setText(phongs.get(0).getDiaChiPhong());
                                tvGiaThue.setText((int) phongs.get(0).getGiaThue() + "");
                            }
                        });
                    }catch (Exception e) {
                        Log.d(TAG, "Lỗi: " + e);
                    }

                    try {
                        dbChiTietDat.getDataNguoiDung(thongTinThanhToans.get(0).getMaNguoiDung(), new ThongTinNguoiDungCallBack() {
                            @Override
                            public void thongTinNguoiDungCallBack(List<NguoiDung> nguoiDungList) {
                                for (NguoiDung nguoiDung : nguoiDungList) {
                                    nguoiDung.setMaNguoiDung(nguoiDungList.get(0).getMaNguoiDung());
                                    nguoiDung.setTenNguoiDung(nguoiDungList.get(0).getTenNguoiDung());
                                    nguoiDung.setGioiTinh(nguoiDungList.get(0).getGioiTinh());
                                    nguoiDung.setNgaySinh(nguoiDungList.get(0).getNgaySinh());
                                    nguoiDung.setQuocTich(nguoiDungList.get(0).getQuocTich());
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

        //Xoa thong tin thanh toan va them thong tin thanh toan vao bang DaHuy
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
                try {
                    dbChiTietThanhToan.getDataDaThanhToan(maThanhToan, new DanhSachThanhToanCallBack() {
                        @Override
                        public void danhSachThanhToanCallBack(ArrayList<ThongTinThanhToan> thanhToanList) {
                            for (ThongTinThanhToan thongTinThanhToan : thanhToanList){
                                thongTinThanhToan.setMaPhong(thanhToanList.get(0).getMaPhong());
                                thongTinThanhToan.setMaNguoiDung(thanhToanList.get(0).getMaNguoiDung());
                                thongTinThanhToan.setMaKhachSan(thanhToanList.get(0).getMaKhachSan());
                                thongTinThanhToan.setNgayDen(thanhToanList.get(0).getNgayDen());
                                thongTinThanhToan.setNgayDi(thanhToanList.get(0).getNgayDi());
                                thongTinThanhToan.setNgayThanhToan(thanhToanList.get(0).getNgayThanhToan());
                                thongTinThanhToan.setSoTienThanhToanTruoc(thanhToanList.get(0).getSoTienThanhToanTruoc());
                                thongTinThanhToans.add(thongTinThanhToan);
                            }
                            ThongTinHuy thongTinHuy = new ThongTinHuy();
                            thongTinHuy.setMaHuy(DAHUY + createRandomAString());
                            thongTinHuy.setMaPhong(thongTinThanhToans.get(0).getMaPhong());
                            thongTinHuy.setMaNguoiDung(thongTinThanhToans.get(0).getMaNguoiDung());
                            thongTinHuy.setMaKhachSan(thongTinThanhToans.get(0).getMaKhachSan());
                            thongTinHuy.setNgayDen(thongTinThanhToans.get(0).getNgayDen());
                            thongTinHuy.setNgayDi(thongTinThanhToans.get(0).getNgayDi());
                            thongTinHuy.setNgayHuy(thongTinThanhToans.get(0).getNgayThanhToan());
                            thongTinHuy.setSoTienDaThanhToan(thongTinThanhToans.get(0).getSoTienThanhToanTruoc());
                            thongTinHuy.setTrangThaiHoanTien(TRANGTHAIHOANTIEN);

                            dbChiTietDat.getDataPhong(thongTinThanhToans.get(0).getMaPhong(), new ThongTinPhongCallBack() {
                                @Override
                                public void thongTinPhongCallBack(List<Phong> phongList) {
                                    Phong phong = phongList.get(0);
                                    phong.setMaTrangThaiPhong(TRANGTHAIPHONG);
                                    openDialogThanhCong(thongTinHuy, phong, maThanhToan);
                                }
                            });
                        }
                    });
                }catch (Exception e) {
                    Log.d(TAG, "Lỗi: " + e);
                }
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

    private void openDialogThanhCong(ThongTinHuy thongTinHuy, Phong phong, String ma) {
        dialog.setContentView(R.layout.custom_dialog_thanh_cong);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tieuDeDialog = dialog.findViewById(R.id.tvTieuDe);
        TextView thongBaoDialog = dialog.findViewById(R.id.tvThongBao);
        Button btnOk = dialog.findViewById(R.id.btnOk);

        tieuDeDialog.setText("Thông báo");
        thongBaoDialog.setText("Hủy thành công");

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Them thong tin thanh toan vao danh sach huy
                dbChiTietThanhToan.addThongTinThanhToanInTableDaHuy(thongTinHuy);
                //Thay doi trang thai phong
                dbChiTietDat.setTrangThaiPhong(phong);
                //Chuyen man hinh va du lieu sang man hinh danh sach thanh toan
                moveScreen(ma);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void moveScreen(String ma) {
        Intent intent = new Intent(ManHinhChiTietThanhToan.this, MainFragment.class);
        Bundle bundle1 = new Bundle();
        bundle1.putString(MHTHANHTOAN, MHTHANHTOAN);
        bundle1.putString(MATT, ma);
        intent.putExtras(bundle1);
        startActivity(intent);
    }
}