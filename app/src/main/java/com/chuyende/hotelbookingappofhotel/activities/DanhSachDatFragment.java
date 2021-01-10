package com.chuyende.hotelbookingappofhotel.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chuyende.hotelbookingappofhotel.R;
import com.chuyende.hotelbookingappofhotel.adapters.DanhSachDatAdapter;
import com.chuyende.hotelbookingappofhotel.data_models.ThongTinDat;
import com.chuyende.hotelbookingappofhotel.firebase_models.DBDanhSachDat;
import com.chuyende.hotelbookingappofhotel.interfaces.DanhSachDatCallBack;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static com.chuyende.hotelbookingappofhotel.activities.MainFragment.MADD;
import static com.chuyende.hotelbookingappofhotel.activities.MainFragment.TEN_TKKS;
import static com.chuyende.hotelbookingappofhotel.activities.MainFragment.TRANGTHAIXOAD;
import static com.chuyende.hotelbookingappofhotel.activities.MainFragment.TTKKS;
import static com.chuyende.hotelbookingappofhotel.activities.ManHinhChiTietDat.XOAHET;
import static com.chuyende.hotelbookingappofhotel.activities.ManHinhChiTietDat.XOAMOT;

public class DanhSachDatFragment extends Fragment implements DanhSachDatAdapter.SelectedItem {
    TextView tieuDe;
    RecyclerView rvDanhSachDat;
    SearchView svTimKiem;
    public static String TAG = "DanhSachDatFragment";
    public static String MADAT = "maDat";
    DBDanhSachDat dbDanhSachDat = new DBDanhSachDat();
    private DanhSachDatAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_danh_sach_dat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setControl();
        setEvent();
    }

    private void setEvent() {
        //Thay doi tieu de
        tieuDe.setText("Danh sách các khách hàng đã đặt phòng");

        //Lay du lieu dc chuyen tu man hinh chi tiet thanh toan
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            String tenTKKS = bundle.getString(TTKKS);
            String mad = bundle.getString(MADD);
            String xoa = bundle.getString(TRANGTHAIXOAD);
            TEN_TKKS = tenTKKS;
            if (xoa.equals(XOAMOT)) {
                try {
                    dbDanhSachDat.deleteOneThongTinDat(mad);
                } catch (Exception e) {
                    Log.d(TAG, "Lỗi " + e);
                }
            }
            if (xoa.equals(XOAHET)) {
                try {
                    dbDanhSachDat.deleteThongTinDat(TEN_TKKS, mad);
                } catch (Exception e) {
                    Log.d(TAG, "Lỗi " + e);
                }
            }
        }

        //Xoa nhung thong tin dat qua han cho thanh toan
        try {
            dbDanhSachDat.readAllDataDat(TEN_TKKS, new DanhSachDatCallBack() {
                @Override
                public void danhSachDatCallBack(ArrayList<ThongTinDat> list) {
                    for (ThongTinDat thongTinDat : list) {
                        if (compareDateTime(thongTinDat.getNgayDatPhong()) == true) {
                            dbDanhSachDat.deleteOneThongTinDat(thongTinDat.getMaDat());
                        }
                    }
                }
            });
        } catch (Exception e) {
            Log.d(TAG, "Lỗi " + e);
        }

        //Hien thi danh sach thong tin dat len recyclerview
        try {
            dbDanhSachDat.readAllDataDat(TEN_TKKS, new DanhSachDatCallBack() {
                @Override
                public void danhSachDatCallBack(ArrayList<ThongTinDat> list) {
                    adapter = new DanhSachDatAdapter(list, DanhSachDatFragment.this::selectedItem);
                    rvDanhSachDat.setHasFixedSize(true);
                    rvDanhSachDat.setLayoutManager(new LinearLayoutManager(getContext()));
                    rvDanhSachDat.setAdapter(adapter);

                    svTimKiem.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {
                            adapter.getFilter().filter(newText);
                            return false;
                        }
                    });
                }
            });
        } catch (Exception e) {
            Log.d(TAG, "Lỗi " + e);
        }
    }

    private void setControl() {
        tieuDe = getView().findViewById(R.id.tvTieuDe);
        rvDanhSachDat = getView().findViewById(R.id.rvDanhSachDat);
        svTimKiem = getView().findViewById(R.id.svTimKiem);
    }

    @Override
    public void selectedItem(ThongTinDat thongTinDat) {
        Intent intent = new Intent(getActivity().getApplicationContext(), ManHinhChiTietDat.class);
        Bundle bundle = new Bundle();
        bundle.putString(MADAT, thongTinDat.getMaDat());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    //Lay thoi gian hien tai tu he thong
    private String getDateTime() {
        String dateTime = "";
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss-dd/MM/yyyy");
        dateTime = dateFormat.format(calendar.getTime());
        return dateTime;
    }

    //So sanh thoi gian hien tai voi thoi gian han cho thanh toan
    private boolean compareDateTime(String time) {
        String presentTime = getDateTime();
        String newTime = setDateOfDatPhong(time);

        int gio, phut, giay, ngay, thang, nam, pgio, pphut, pgiay, pngay, pthang, pnam;
        gio = Integer.parseInt(newTime.substring(0, 2));
        phut = Integer.parseInt(newTime.substring(3, 5));
        giay = Integer.parseInt(newTime.substring(6, 8));
        ngay = Integer.parseInt(newTime.substring(9, 11));
        thang = Integer.parseInt(newTime.substring(12, 14));
        nam = Integer.parseInt(newTime.substring(15, 19));
        pgio = Integer.parseInt(presentTime.substring(0, 2));
        pphut = Integer.parseInt(presentTime.substring(3, 5));
        pgiay = Integer.parseInt(presentTime.substring(6, 8));
        pngay = Integer.parseInt(presentTime.substring(9, 11));
        pthang = Integer.parseInt(presentTime.substring(12, 14));
        pnam = Integer.parseInt(presentTime.substring(15, 19));

        if (nam < pnam) {
            return true;
        } else if (nam == pnam) {
            if (thang < pthang) {
                return true;
            } else if (thang == pthang) {
                if (ngay < pngay) {
                    return true;
                } else if (ngay == pngay) {
                    if (gio < pgio) {
                        return true;
                    } else if (gio == pgio) {
                        if (phut < pphut) {
                            return true;
                        } else if (phut == pphut) {
                            if (giay < pgiay) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
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
}