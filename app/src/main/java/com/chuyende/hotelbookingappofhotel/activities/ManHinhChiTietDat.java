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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.chuyende.hotelbookingappofhotel.interfaces.DanhSachDatCallBack;
import com.chuyende.hotelbookingappofhotel.interfaces.DataCallBack;
import com.chuyende.hotelbookingappofhotel.interfaces.ThongTinNguoiDungCallBack;
import com.chuyende.hotelbookingappofhotel.interfaces.ThongTinPhongCallBack;
import com.chuyende.hotelbookingappofhotel.R;
import com.chuyende.hotelbookingappofhotel.data_models.NguoiDung;
import com.chuyende.hotelbookingappofhotel.data_models.Phong;
import com.chuyende.hotelbookingappofhotel.data_models.ThongTinDat;
import com.chuyende.hotelbookingappofhotel.data_models.ThongTinThanhToan;
import com.chuyende.hotelbookingappofhotel.firebase_models.DBChiTietDat;
import com.chuyende.hotelbookingappofhotel.validate.CheckTextInput;
import static com.chuyende.hotelbookingappofhotel.activities.DanhSachDatFragment.MADAT;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ManHinhChiTietDat extends AppCompatActivity {
    TextView tieuDe, tvTenNguoiDat, tvGioiTinh, tvEmail, tvNgayDen, tvNgayDi, tvNgaySinh,
            tvQuocTich, tvTenPhong, tvSoNguoi, tvDiaDiem, tvGiaThue, tvTongPhi, tvHanChoThanhToan;
    EditText edtDaThanhToan;
    Button btnHuy, btnChoThue;
    Dialog dialog;

    public static String TAG = "ManHinhChiTietDat";
    public static String DATHANHTOAN = "DTT";
    public static String MATRANGTHAI = "TTP02";
    public static String TRANGTHAIPHONG = "Đang thuê";
    public static String TRANGTHAIHOANTATTHANHTOANTHATBAI = "false";
    public static String TRANGTHAIHOANTATTHANHTOANTHANHCONG = "true";
    public static String MAD = "MaD";
    public static String TRANGTHAIXOA = "TrangThaiXoa";
    public static String XOAMOT = "XoaMot";
    public static String XOAHET = "XoaHet";
    public static String MHDAT = "MHDat";

    CheckTextInput checkTextInput = new CheckTextInput(ManHinhChiTietDat.this);
    private DBChiTietDat dbChiTietDat = new DBChiTietDat();
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
        String maDat = bundle.getString(MADAT);

        //Hien thi thong tin chi tiet dat phong, phong, nguoi dung
        try {
            dbChiTietDat.getDataDaDat(maDat, new DanhSachDatCallBack() {
                @Override
                public void danhSachDatCallBack(ArrayList<ThongTinDat> list) {
                    for (ThongTinDat thongTinDat : list) {
                        thongTinDat.setMaPhong(list.get(0).getMaPhong());
                        thongTinDat.setMaNguoiDung(list.get(0).getMaNguoiDung());
                        thongTinDat.setNgayDen(list.get(0).getNgayDen());
                        thongTinDat.setNgayDi(list.get(0).getNgayDi());
                        thongTinDat.setNgayDatPhong(list.get(0).getNgayDatPhong());
                        thongTinDat.setTongThanhToan(list.get(0).getTongThanhToan());
                        thongTinDats.add(thongTinDat);
                    }
                    tvNgayDen.setText(thongTinDats.get(0).getNgayDen());
                    tvNgayDi.setText(thongTinDats.get(0).getNgayDi());
                    tvHanChoThanhToan.setText(setDateOfDatPhong(thongTinDats.get(0).getNgayDatPhong()));
                    tvTongPhi.setText(thongTinDats.get(0).getTongThanhToan() + "");

                    try {
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

        btnChoThue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Them thong tin dat vao bang DaThanhToan
                try {
                    dbChiTietDat.getDataDaDat(maDat, new DanhSachDatCallBack() {
                        @Override
                        public void danhSachDatCallBack(ArrayList<ThongTinDat> list) {
                            for (ThongTinDat thongTinDat : list) {
                                thongTinDat.setMaPhong(list.get(0).getMaPhong());
                                thongTinDat.setMaNguoiDung(list.get(0).getMaNguoiDung());
                                thongTinDat.setMaKhachSan(list.get(0).getMaKhachSan());
                                thongTinDat.setNgayDen(list.get(0).getNgayDen());
                                thongTinDat.setNgayDi(list.get(0).getNgayDi());
                                thongTinDat.setNgayDatPhong(list.get(0).getNgayDatPhong());
                                thongTinDat.setTongThanhToan(list.get(0).getTongThanhToan());
                                thongTinDats.add(thongTinDat);
                            }

                            dbChiTietDat.getDataPhong(thongTinDats.get(0).getMaPhong(), new ThongTinPhongCallBack() {
                                @Override
                                public void thongTinPhongCallBack(List<Phong> phongList) {
                                    //Thay doi trang thai phong
                                    Phong phong = phongList.get(0);
                                    phong.setMaTrangThaiPhong(TRANGTHAIPHONG);

                                    int tongPhi = thongTinDats.get(0).getTongThanhToan();
                                    int soCanTienThanhToanTruoc = tongPhi * 30 / 100;
                                    int soTienThanhToanTruoc = 0;
                                    String daThanhToan = edtDaThanhToan.getText().toString();
                                    if (!daThanhToan.equals("")) {
                                        soTienThanhToanTruoc = Integer.parseInt(daThanhToan);
                                    }
                                    ThongTinThanhToan thongTinThanhToan = new ThongTinThanhToan();

                                    if (soTienThanhToanTruoc == 0) {
                                        checkTextInput.checkEmpty(edtDaThanhToan, "Vui lòng nhập số tiền đã thanh toán");
                                    } else if (!daThanhToan.equals("") && soTienThanhToanTruoc < soCanTienThanhToanTruoc) {
                                        setToastMessageFailure("Số tiền cần thanh toán phải ít nhất là " + soCanTienThanhToanTruoc + " VND");
                                    } else if (!daThanhToan.equals("") && tongPhi > soTienThanhToanTruoc && soTienThanhToanTruoc >= soCanTienThanhToanTruoc) {
                                        thongTinThanhToan.setMaThanhToan(DATHANHTOAN + createRandomAString());
                                        thongTinThanhToan.setMaNguoiDung(thongTinDats.get(0).getMaNguoiDung());
                                        thongTinThanhToan.setMaPhong(thongTinDats.get(0).getMaPhong());
                                        thongTinThanhToan.setMaKhachSan(thongTinDats.get(0).getMaKhachSan());
                                        thongTinThanhToan.setMaTrangThai(MATRANGTHAI);
                                        thongTinThanhToan.setNgayDen(thongTinDats.get(0).getNgayDen());
                                        thongTinThanhToan.setNgayDi(thongTinDats.get(0).getNgayDi());
                                        thongTinThanhToan.setNgayThanhToan(thongTinDats.get(0).getNgayDatPhong().substring(9, 19));
                                        thongTinThanhToan.setTrangThaiHoanTatThanhToan(TRANGTHAIHOANTATTHANHTOANTHATBAI);
                                        thongTinThanhToan.setSoTienThanhToanTruoc(soTienThanhToanTruoc);
                                        thongTinThanhToan.setTongThanhToan(tongPhi);
                                        openDialogThanhCong(thongTinThanhToan, phong, thongTinDats.get(0).getMaPhong(), XOAHET);
                                    } else if (!daThanhToan.equals("") && soTienThanhToanTruoc == tongPhi) {
                                        thongTinThanhToan.setMaThanhToan(DATHANHTOAN + createRandomAString());
                                        thongTinThanhToan.setMaNguoiDung(thongTinDats.get(0).getMaNguoiDung());
                                        thongTinThanhToan.setMaPhong(thongTinDats.get(0).getMaPhong());
                                        thongTinThanhToan.setMaKhachSan(thongTinDats.get(0).getMaKhachSan());
                                        thongTinThanhToan.setMaTrangThai(MATRANGTHAI);
                                        thongTinThanhToan.setNgayDen(thongTinDats.get(0).getNgayDen());
                                        thongTinThanhToan.setNgayDi(thongTinDats.get(0).getNgayDi());
                                        thongTinThanhToan.setNgayThanhToan(thongTinDats.get(0).getNgayDatPhong().substring(9, 19));
                                        thongTinThanhToan.setTrangThaiHoanTatThanhToan(TRANGTHAIHOANTATTHANHTOANTHANHCONG);
                                        thongTinThanhToan.setSoTienThanhToanTruoc(soTienThanhToanTruoc);
                                        thongTinThanhToan.setTongThanhToan(tongPhi);
                                        openDialogThanhCong(thongTinThanhToan, phong, thongTinDats.get(0).getMaPhong(), XOAHET);
                                    }
                                }
                            });
                        }
                    });
                }catch (Exception e) {
                    Log.d(TAG, "Lỗi: " + e);
                }
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogHuy(maDat);
            }
        });
    }

    //Cai dat han cho thanh toan
    private String setDateOfDatPhong(String date) {
        String newDate = "";

        int gio, phut, giay, ngay, thang, nam;
        gio = Integer.parseInt(date.substring(0, 2));
        phut = Integer.parseInt(date.substring(3, 5));
        giay = Integer.parseInt(date.substring(6, 8));
        ngay = Integer.parseInt(date.substring(9, 11));
        thang = Integer.parseInt(date.substring(12, 14));
        nam = Integer.parseInt(date.substring(15, 19));

        if (gio < 12) {
            gio = gio + 12;
            newDate = formatDateTime(gio, phut, giay, ngay, thang, nam);
        } else if (gio >= 12) {
            if (nam % 4 == 0 && thang == 2 && ngay < 29) {
                gio = gio - 12;
                ngay = ngay + 1;
                newDate = formatDateTime(gio, phut, giay, ngay, thang, nam);
            } else if (nam % 4 == 0 && thang == 2 && ngay == 29) {
                gio = gio - 12;
                ngay = 1;
                thang = thang + 1;
                newDate = formatDateTime(gio, phut, giay, ngay, thang, nam);
            } else if (thang == 2 && ngay < 28) {
                gio = gio - 12;
                ngay = ngay + 1;
                newDate = formatDateTime(gio, phut, giay, ngay, thang, nam);
            } else if (thang == 2 && ngay == 28) {
                gio = gio - 12;
                ngay = 1;
                thang = thang + 1;
                newDate = formatDateTime(gio, phut, giay, ngay, thang, nam);
            } else if (thang == 4 || thang == 6 || thang == 9 || thang == 11 && ngay < 30) {
                gio = gio - 12;
                ngay = ngay + 1;
                newDate = formatDateTime(gio, phut, giay, ngay, thang, nam);
            } else if (thang == 4 || thang == 6 || thang == 9 || thang == 11 && ngay == 30) {
                gio = gio - 12;
                ngay = 1;
                thang = thang + 1;
                newDate = formatDateTime(gio, phut, giay, ngay, thang, nam);
            } else if (thang == 1 || thang == 3 || thang == 5 || thang == 7 || thang == 8 || thang == 10 || thang == 12 && ngay < 31) {
                gio = gio - 12;
                ngay = ngay + 1;
                newDate = formatDateTime(gio, phut, giay, ngay, thang, nam);
            } else if (thang == 1 || thang == 3 || thang == 5 || thang == 7 || thang == 8 || thang == 10 && ngay == 31) {
                gio = gio - 12;
                ngay = 1;
                thang = thang + 1;
                newDate = formatDateTime(gio, phut, giay, ngay, thang, nam);
            } else if (thang == 12 && ngay == 31) {
                gio = gio - 12;
                ngay = 1;
                thang = 1;
                nam = nam + 1;
                newDate = formatDateTime(gio, phut, giay, ngay, thang, nam);
            }
        }

        return newDate;
    }

    //Dinh dang lai thoi gian HH:mm:ss-dd/MM/yyyy khi lay du lieu
    private String formatDateTime(int gio, int phut, int giay, int ngay, int thang, int nam) {
        String newTime = "", sGio = "" + gio, sPhut = "" + phut, sGiay = "" + giay,
                sNgay = "" + ngay, sThang = "" + thang, sNam = "" + nam;
        if (gio < 10) sGio = "0" + gio;
        if (phut < 10) sPhut = "0" + phut;
        if (giay < 10) sGiay = "0" + giay;
        if (ngay < 10) sNgay = "0" + ngay;
        if (thang < 10) sThang = "0" + thang;
        newTime = sGio + ":" + sPhut + ":" + sGiay + "-" + sNgay + "/" + sThang + "/" + sNam;
        return newTime;
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
        tvHanChoThanhToan = findViewById(R.id.tvHanChoThanToan);
        edtDaThanhToan = findViewById(R.id.edtDaThanhToan);
        btnHuy = findViewById(R.id.btnHuy);
        btnChoThue = findViewById(R.id.btnChoThue);
    }

    private void openDialogHuy(String maDat) {
        dialog.setContentView(R.layout.custom_dialog_huy);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView tieuDeDialog = dialog.findViewById(R.id.tvTieuDe);
        Button btnOk = dialog.findViewById(R.id.btnOk);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        tieuDeDialog.setText("Thông báo");

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Chuyen man hinh va du lieu sang man hinh danh sach dat
                moveScreen(maDat, XOAMOT);

                setToastMessageSuccess("Lịch đặt của khách đã được hủy");
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

    private void openDialogThanhCong(ThongTinThanhToan thongTinThanhToan, Phong phong, String maDat, String trangThaiXoa) {
        dialog.setContentView(R.layout.custom_dialog_thanh_cong);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView tieuDeDialog = dialog.findViewById(R.id.tvTieuDe);
        Button btnOk = dialog.findViewById(R.id.btnOk);
        tieuDeDialog.setText("Thông báo");

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //Them thong tin dat vao danh sach thanh toan
                    dbChiTietDat.addChoThue(thongTinThanhToan);
                    //Thay doi trang thai phong
                    dbChiTietDat.setTrangThaiPhong(phong);
                    //Chuyen man hinh va du lieu sang man hinh danh sach dat
                    moveScreen(maDat, trangThaiXoa);
                }catch (Exception e) {
                    Log.d(TAG, "Lỗi " + e);
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void moveScreen(String ma, String trangThaiXoa) {
        Intent intent = new Intent(ManHinhChiTietDat.this, MainFragment.class);
        Bundle bundle1 = new Bundle();
        bundle1.putString(MHDAT, MHDAT);
        bundle1.putString(MAD, ma);
        bundle1.putString(TRANGTHAIXOA, trangThaiXoa);
        intent.putExtras(bundle1);
        startActivity(intent);
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

    private void setToastMessageFailure(String text) {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_toast_message_failure, (ViewGroup)findViewById(R.id.ToastMessage_layout));
        TextView textView = view.findViewById(R.id.tvTextToast);
        textView.setText(text);

        final Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }
}