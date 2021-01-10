package com.chuyende.hotelbookingappofhotel.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chuyende.hotelbookingappofhotel.R;
import com.chuyende.hotelbookingappofhotel.adapters.DanhSachThanhToanAdapter;
import com.chuyende.hotelbookingappofhotel.data_models.Phong;
import com.chuyende.hotelbookingappofhotel.data_models.ThongTinThanhToan;
import com.chuyende.hotelbookingappofhotel.firebase_models.DBChiTietDat;
import com.chuyende.hotelbookingappofhotel.firebase_models.DBDanhSachThanhToan;
import com.chuyende.hotelbookingappofhotel.interfaces.DanhSachThanhToanCallBack;
import com.chuyende.hotelbookingappofhotel.interfaces.ThongTinPhongCallBack;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.chuyende.hotelbookingappofhotel.activities.MainFragment.MADTT;
import static com.chuyende.hotelbookingappofhotel.activities.MainFragment.TEN_TKKS;
import static com.chuyende.hotelbookingappofhotel.activities.MainFragment.TTKKS;

public class DanhSachThanhToanFragment extends Fragment implements DanhSachThanhToanAdapter.SelectedItem {
    TextView tieuDe;
    RecyclerView rvDanhSachThanhToan;
    SearchView svTimKiem;
    Button btnThanhToanTruoc, btnThanhToanDu;

    private static final String TAG = "DanhSachThanhToanFragment";
    public static final String MATHANHTOAN = "maThanhToan";
    public static String TRANGTHAIPHONG = "Đang trống";

    DBDanhSachThanhToan dbDanhSachThanhToan = new DBDanhSachThanhToan();
    DBChiTietDat dbChiTietDat = new DBChiTietDat();
    public DanhSachThanhToanAdapter swipeAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_danh_sach_thanh_toan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setControl();
        setEvent();
    }

    private void setEvent() {
        //Thay doi tieu de
        tieuDe.setText("Danh sách các khách hàng đã thanh toán phòng");

        //Lay du lieu dc chuyen tu man hinh chi tiet thanh toan
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            String tenTKKS = bundle.getString(TTKKS);
            String matt = bundle.getString(MADTT);
            TEN_TKKS = tenTKKS;
            try {
                dbDanhSachThanhToan.deleteThongTinThanhToan(matt);
            } catch (Exception e) {
                Log.d(TAG, "Lỗi " + e);
            }
        }

        try {
            dbDanhSachThanhToan.readAllDataThanhToan(TEN_TKKS, new DanhSachThanhToanCallBack() {
                @Override
                public void danhSachThanhToanCallBack(ArrayList<ThongTinThanhToan> thanhToanList) {
                    for (ThongTinThanhToan thongTinThanhToan : thanhToanList) {
                        if (compareDateTime(thongTinThanhToan.getNgayDi()) == true) {
                            dbDanhSachThanhToan.deleteThongTinThanhToan(thongTinThanhToan.getMaThanhToan());
                            dbChiTietDat.getDataPhong(thongTinThanhToan.getMaPhong(), new ThongTinPhongCallBack() {
                                @Override
                                public void thongTinPhongCallBack(List<Phong> phongList) {
                                    Phong phong = phongList.get(0);
                                    phong.setMaTrangThaiPhong(TRANGTHAIPHONG);
                                    dbChiTietDat.setTrangThaiPhong(phong);
                                }
                            });
                        }
                    }
                }
            });
        } catch (Exception e) {
            Log.d(TAG, "Lỗi " + e);
        }

        //Hien thi danh sach thong tin thanh toan len recyclerview
        try {
            dbDanhSachThanhToan.readAllDataThanhToan(TEN_TKKS, new DanhSachThanhToanCallBack() {
                @Override
                public void danhSachThanhToanCallBack(ArrayList<ThongTinThanhToan> thanhToanList) {
                    swipeAdapter = new DanhSachThanhToanAdapter(thanhToanList, getContext(), DanhSachThanhToanFragment.this::selectedItem);
                    rvDanhSachThanhToan.setHasFixedSize(true);
                    rvDanhSachThanhToan.setLayoutManager(new LinearLayoutManager(getContext()));
                    rvDanhSachThanhToan.setAdapter(swipeAdapter);

                    svTimKiem.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {
                            swipeAdapter.getFilter().filter(newText);
                            return false;
                        }
                    });
                }
            });
        } catch (Exception e) {
            Log.d(TAG, "Lỗi " + e);
        }

        //Hien thi thong tin thanh toan truoc len recyclerview khi tap vao nut thanh toan truoc
        try {
            btnThanhToanTruoc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dbDanhSachThanhToan.readAllDataThanhToanTruoc(TEN_TKKS, new DanhSachThanhToanCallBack() {
                        @Override
                        public void danhSachThanhToanCallBack(ArrayList<ThongTinThanhToan> thanhToanList) {
                            swipeAdapter = new DanhSachThanhToanAdapter(thanhToanList, getContext(), DanhSachThanhToanFragment.this::selectedItem);
                            rvDanhSachThanhToan.setHasFixedSize(true);
                            rvDanhSachThanhToan.setLayoutManager(new LinearLayoutManager(getContext()));
                            rvDanhSachThanhToan.setAdapter(swipeAdapter);

                            svTimKiem.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                @Override
                                public boolean onQueryTextSubmit(String query) {
                                    return false;
                                }

                                @Override
                                public boolean onQueryTextChange(String newText) {
                                    swipeAdapter.getFilter().filter(newText);
                                    return false;
                                }
                            });
                        }
                    });
                }
            });
        } catch (Exception e) {
            Log.d(TAG, "Lỗi " + e);
        }

        //Hien thi thong tin thanh toan du len recyclerview khi tap vao nut thanh toan du
        try {
            btnThanhToanDu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dbDanhSachThanhToan.readAllDataThanhToanDu(TEN_TKKS, new DanhSachThanhToanCallBack() {
                        @Override
                        public void danhSachThanhToanCallBack(ArrayList<ThongTinThanhToan> thanhToanList) {
                            swipeAdapter = new DanhSachThanhToanAdapter(thanhToanList, getContext(), DanhSachThanhToanFragment.this::selectedItem);
                            rvDanhSachThanhToan.setHasFixedSize(true);
                            rvDanhSachThanhToan.setLayoutManager(new LinearLayoutManager(getContext()));
                            rvDanhSachThanhToan.setAdapter(swipeAdapter);

                            svTimKiem.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                @Override
                                public boolean onQueryTextSubmit(String query) {
                                    return false;
                                }

                                @Override
                                public boolean onQueryTextChange(String newText) {
                                    swipeAdapter.getFilter().filter(newText);
                                    return false;
                                }
                            });
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
        rvDanhSachThanhToan = getView().findViewById(R.id.rvDanhSachThanhToan);
        svTimKiem = getView().findViewById(R.id.svTimKiem);
        btnThanhToanTruoc = getView().findViewById(R.id.btnThanhToanTruoc);
        btnThanhToanDu = getView().findViewById(R.id.btnThanhToanDu);
    }

    @Override
    public void selectedItem(ThongTinThanhToan thongTinThanhToan) {
        Intent intent = new Intent(getActivity().getApplicationContext(), ManHinhChiTietThanhToan.class);
        Bundle bundle = new Bundle();
        bundle.putString(MATHANHTOAN, thongTinThanhToan.getMaThanhToan());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    //Lay thoi gian hien tai tu he thong
    private String getDateTime() {
        String dateTime = "";
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateTime = dateFormat.format(calendar.getTime());
        return dateTime;
    }

    //So sanh thoi gian hien tai voi thoi gian di
    private boolean compareDateTime(String time) {
        String presentTime = getDateTime();

        int ngay, thang, nam, pngay, pthang, pnam;
        ngay = Integer.parseInt(time.substring(0, 2));
        thang = Integer.parseInt(time.substring(3, 5));
        nam = Integer.parseInt(time.substring(6, 10));
        pngay = Integer.parseInt(presentTime.substring(0, 2));
        pthang = Integer.parseInt(presentTime.substring(3, 5));
        pnam = Integer.parseInt(presentTime.substring(6, 10));

        if (nam < pnam)
            return true;
        else if (nam == pnam) {
            if (thang < pthang)
                return true;
            else if (thang == pthang) {
                if (ngay < pngay || ngay == pngay)
                    return true;
            }
        }
        return false;
    }
}