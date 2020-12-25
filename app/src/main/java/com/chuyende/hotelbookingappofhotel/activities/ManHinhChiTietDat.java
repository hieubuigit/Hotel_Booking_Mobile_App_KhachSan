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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.List;
import java.util.Random;

public class ManHinhChiTietDat extends AppCompatActivity {
    TextView tieuDe, tvMaNguoiDat, tvTenNguoiDat, tvGioiTinh, tvEmail, tvNgayDen, tvNgayDi, tvNgaySinh,
            tvQuocTich, tvMaPhong, tvTenPhong, tvSoNguoi, tvDiaDiem, tvGiaThue, tvTongPhi, tvHanChoThanhToan;
    EditText edtDaThanhToan;
    Button btnHuy, btnChoThue;
    Dialog dialog;

    private DBChiTietDat dbChiTietDat = new DBChiTietDat();
    public static String TAG = "ManHinhChiTietDat";
    public static String DATHANHTOAN = "DTT";
    public static String MATRANGTHAI = "TTP02";
    private ArrayList<ThongTinDat> thongTinDats = new ArrayList<>();
    private ArrayList<Phong> phongs = new ArrayList<>();
    private ArrayList<NguoiDung> nguoiDungs = new ArrayList<>();
    private List<String> email = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chi_tiet_dat);
        setControl();
        setEvent();

    }

    private void setEvent() {
        //Thay doi tieu de
        tieuDe.setText("Thông tin chi tiết khách hàng đặt");
        //Dialog huy
        dialog = new Dialog(this);

        //Lay maDat tu man hinh DanhSachDatFragment
        Bundle bundle = getIntent().getExtras();
        String maDat = bundle.getString("maDat");

        //Hien thi thong tin chi tiet dat phong, phong, nguoi dung
        dbChiTietDat.getDataDaDat(maDat, new DanhSachDatCallBack() {
            @Override
            public void danhSachDatCallBack(ArrayList<ThongTinDat> list) {
                for (ThongTinDat thongTinDat : list) {
                    thongTinDat.setMaPhong(list.get(0).getMaPhong());
                    thongTinDat.setMaNguoiDung(list.get(0).getMaNguoiDung());
                    thongTinDat.setNgayDen(list.get(0).getNgayDen());
                    thongTinDat.setNgayDi(list.get(0).getNgayDi());
                    thongTinDat.setNgayDatPhong(list.get(0).getNgayDatPhong());
                    thongTinDats.add(thongTinDat);
                }
                tvNgayDen.setText(thongTinDats.get(0).getNgayDen());
                tvNgayDi.setText(thongTinDats.get(0).getNgayDi());
                tvHanChoThanhToan.setText("12:00:00-" + thongTinDats.get(0).getNgayDatPhong());

                dbChiTietDat.getDataPhong(thongTinDats.get(0).getMaPhong(), new ThongTinPhongCallBack() {
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
                        tvMaPhong.setText(phongs.get(0).getMaPhong());
                        tvTenPhong.setText(phongs.get(0).getTenPhong());
                        tvSoNguoi.setText(phongs.get(0).getSoKhach() + "");
                        tvDiaDiem.setText(phongs.get(0).getDiaChiPhong());
                        tvGiaThue.setText(((int) phongs.get(0).getGiaThue()) + "");
                        //Tinh tong phi theo so ngay dat phong
                        int tongPhi = tinhTongPhiThanhToan(thongTinDats.get(0).getNgayDen(), thongTinDats.get(0).getNgayDi(), phongs.get(0).getGiaThue());
                        tvTongPhi.setText(tongPhi + "");
                    }
                });

                dbChiTietDat.getDataNguoiDung(thongTinDats.get(0).getMaNguoiDung(), new ThongTinNguoiDungCallBack() {
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
                        tvMaNguoiDat.setText(nguoiDungs.get(0).getMaNguoiDung());
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
            }
        });

        btnChoThue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Xoa thong tin dat
                //dbChiTietDat.deleteThongTinDat(maDat);

                //Them thong tin dat vao bang DaThanhTaon
                dbChiTietDat.getDataDaDat(maDat, new DanhSachDatCallBack() {
                    @Override
                    public void danhSachDatCallBack(ArrayList<ThongTinDat> list) {
                        for (ThongTinDat thongTinDat : list) {
                            thongTinDat.setMaPhong(list.get(0).getMaPhong());
                            thongTinDat.setMaNguoiDung(list.get(0).getMaNguoiDung());
                            thongTinDat.setNgayDen(list.get(0).getNgayDen());
                            thongTinDat.setNgayDi(list.get(0).getNgayDi());
                            thongTinDat.setNgayDatPhong(list.get(0).getNgayDatPhong());
                            thongTinDats.add(thongTinDat);
                        }

                        dbChiTietDat.getDataPhong(thongTinDats.get(0).getMaPhong(), new ThongTinPhongCallBack() {
                            @Override
                            public void thongTinPhongCallBack(List<Phong> phongList) {
                                for (Phong phong : phongList) {
                                    phong.setGiaThue(phongList.get(0).getGiaThue());
                                    phongs.add(phong);
                                }
                                //Tinh tong phi theo so ngay dat phong
                                int tongPhi = tinhTongPhiThanhToan(thongTinDats.get(0).getNgayDen(), thongTinDats.get(0).getNgayDi(), phongs.get(0).getGiaThue());
                                int soCanTienThanhToanTruoc = tongPhi * 30 / 100;
                                int soTienThanhToanTruoc = 0;
                                String daThanhToan = edtDaThanhToan.getText().toString();
                                if (!daThanhToan.equals("")) {
                                    soTienThanhToanTruoc = Integer.parseInt(daThanhToan);
                                }
                                ThongTinThanhToan thongTinThanhToan = new ThongTinThanhToan();

                                if (soTienThanhToanTruoc == 0) {
                                    Toast.makeText(getApplicationContext(), "Vui lòng nhập số tiền đã thanh toán", Toast.LENGTH_SHORT).show();
                                } else if (!daThanhToan.equals("") && soTienThanhToanTruoc < soCanTienThanhToanTruoc) {
                                    Toast.makeText(getApplicationContext(), "Số tiền cần thanh toán phải ít nhất là " + soCanTienThanhToanTruoc, Toast.LENGTH_LONG).show();
                                } else if (!daThanhToan.equals("") && tongPhi > soTienThanhToanTruoc && soTienThanhToanTruoc >= soCanTienThanhToanTruoc) {
                                    thongTinThanhToan.setMaThanhToan(DATHANHTOAN + createRandomAString());
                                    thongTinThanhToan.setMaNguoiDung(thongTinDats.get(0).getMaNguoiDung());
                                    thongTinThanhToan.setMaPhong(thongTinDats.get(0).getMaPhong());
                                    thongTinThanhToan.setMaTrangThai(MATRANGTHAI);
                                    thongTinThanhToan.setNgayDen(thongTinDats.get(0).getNgayDen());
                                    thongTinThanhToan.setNgayDi(thongTinDats.get(0).getNgayDi());
                                    thongTinThanhToan.setNgayThanhToan(thongTinDats.get(0).getNgayDatPhong());
                                    thongTinThanhToan.setTrangThaiHoanTatThanhToan("false");
                                    thongTinThanhToan.setSoTienThanhToanTruoc(soTienThanhToanTruoc);
                                    thongTinThanhToan.setTongThanhToan(tongPhi);
                                    dbChiTietDat.addChoThue(thongTinThanhToan);
                                    Toast.makeText(getApplicationContext(), "Cho thuê thành công", Toast.LENGTH_SHORT).show();
                                } else if (!daThanhToan.equals("") && soTienThanhToanTruoc == tongPhi) {
                                    thongTinThanhToan.setMaThanhToan(DATHANHTOAN + createRandomAString());
                                    thongTinThanhToan.setMaNguoiDung(thongTinDats.get(0).getMaNguoiDung());
                                    thongTinThanhToan.setMaPhong(thongTinDats.get(0).getMaPhong());
                                    thongTinThanhToan.setMaTrangThai(MATRANGTHAI);
                                    thongTinThanhToan.setNgayDen(thongTinDats.get(0).getNgayDen());
                                    thongTinThanhToan.setNgayDi(thongTinDats.get(0).getNgayDi());
                                    thongTinThanhToan.setNgayThanhToan(thongTinDats.get(0).getNgayDatPhong());
                                    thongTinThanhToan.setTrangThaiHoanTatThanhToan("true");
                                    thongTinThanhToan.setSoTienThanhToanTruoc(soTienThanhToanTruoc);
                                    thongTinThanhToan.setTongThanhToan(tongPhi);
                                    dbChiTietDat.addChoThue(thongTinThanhToan);
                                    Toast.makeText(getApplicationContext(), "Cho thuê thành công", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogHuy();
            }
        });
    }

    //Ham lay ngay tu he thong theo dinh dang HH:mm:ss-dd/MM/yyyy
    private String getDateInSystem() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss-dd/MM/yyyy");
        String currentDate = simpleDateFormat.format(calendar.getTime());
        return currentDate;
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

    //Tinh tong tien ma nguoi dung phai tra theo so ngay dat
    private int tinhTongPhiThanhToan(String ngayDen, String ngayDi, double giathue) {
        int tongPhi = 0;
        int ngDen, thDen, nDen, ngDi, thDi, nDi;

        ngDen = Integer.parseInt(ngayDen.substring(0, 2));
        thDen = Integer.parseInt(ngayDen.substring(3, 5));
        nDen = Integer.parseInt(ngayDen.substring(6, 10));
        ngDi = Integer.parseInt(ngayDi.substring(0, 2));
        thDi = Integer.parseInt(ngayDi.substring(3, 5));
        nDi = Integer.parseInt(ngayDi.substring(6, 10));

        if (nDen == nDi) {
            if (thDen == thDi) {
                tongPhi = (int) giathue * (ngDi - ngDen);
            } else if (thDen < thDi) {
                if (thDen == 1 | thDen == 3 | thDen == 5| thDen == 7| thDen == 8| thDen == 10| thDen == 12) {
                    tongPhi = (int) giathue * (ngDi + (31 - ngDen));
                } else if (thDen == 4 | thDen == 6 | thDen == 9| thDen == 11) {
                    tongPhi = (int) giathue * (ngDi + (30 - ngDen));
                } else if (thDen == 2 && nDen % 4 == 0) {
                    tongPhi = (int) giathue * (ngDi + (29 - ngDen));
                } else if (thDen == 2) {
                    tongPhi = (int) giathue * (ngDi + (28 - ngDen));
                }
            }
        } else if (nDen < nDi) {
            tongPhi = (int) giathue * (ngDi + (31 - ngDen));
        }
        return tongPhi;
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
        tvHanChoThanhToan = findViewById(R.id.tvHanChoThanToan);
        edtDaThanhToan = findViewById(R.id.edtDaThanhToan);
        btnHuy = findViewById(R.id.btnHuy);
        btnChoThue = findViewById(R.id.btnChoThue);
    }

    private void openDialogHuy() {
        dialog.setContentView(R.layout.custom_dialog_huy);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tieuDeDialog = dialog.findViewById(R.id.tvTieuDe);
        Button btnOk = dialog.findViewById(R.id.btnOk);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);

        tieuDeDialog.setText("Thông báo");

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ManHinhChiTietDat.this, "Lịch đặt của khách đã được hủy", Toast.LENGTH_SHORT).show();
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