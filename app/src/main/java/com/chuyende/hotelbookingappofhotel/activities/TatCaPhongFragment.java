package com.chuyende.hotelbookingappofhotel.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chuyende.hotelbookingappofhotel.R;
import com.chuyende.hotelbookingappofhotel.adapters.DanhSachPhongAdapter;
import com.chuyende.hotelbookingappofhotel.data_models.LoaiPhong;
import com.chuyende.hotelbookingappofhotel.data_models.Phong;
import com.chuyende.hotelbookingappofhotel.data_models.TrangThaiPhong;
import com.chuyende.hotelbookingappofhotel.firebase_models.LoaiPhongDatabase;
import com.chuyende.hotelbookingappofhotel.firebase_models.PhongDatabase;
import com.chuyende.hotelbookingappofhotel.firebase_models.TienNghiDatabase;
import com.chuyende.hotelbookingappofhotel.firebase_models.TrangThaiPhongDatabase;
import com.chuyende.hotelbookingappofhotel.interfaces.LoaiPhongCallback;
import com.chuyende.hotelbookingappofhotel.interfaces.PhongCallback;
import com.chuyende.hotelbookingappofhotel.interfaces.TrangThaiPhongCallback;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.joda.time.DateTime;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TatCaPhongFragment extends Fragment {
    public static final String MA_KS_LOGIN = "KS02";

    TextView tvTieuDe;
    Button btnQuanTriKhac, btnThemPhong;
    SearchView svTimKiem;
    Spinner spnLoaiPhong, spnTrangThai;

    public static ArrayList<Bitmap> listBitmap;

    public DanhSachPhongAdapter danhSachPhongAdapter;
    RecyclerView rcvDanhSachPhong;
    RecyclerView.LayoutManager layoutManager;

    Intent switchActivity;

    public static TrangThaiPhongDatabase trangThaiPhongDB;
    public static LoaiPhongDatabase loaiPhongDB;
    public static PhongDatabase phongDB;
    public static TienNghiDatabase tienNghiDB;

    public static String TAT_CA = "Tất cả";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentTatCaPhong = null;
        fragmentTatCaPhong = inflater.inflate(R.layout.fragment_tat_ca_phong, container, false);

        listBitmap = new ArrayList<Bitmap>();

        //Get all views from layout
        tvTieuDe = fragmentTatCaPhong.findViewById(R.id.tvTieuDe);
        btnQuanTriKhac = fragmentTatCaPhong.findViewById(R.id.btnQuanTriKhac);
        btnThemPhong = fragmentTatCaPhong.findViewById(R.id.btnThemPhong);
        svTimKiem = fragmentTatCaPhong.findViewById(R.id.svTimKiem);
        spnLoaiPhong = fragmentTatCaPhong.findViewById(R.id.spnLoaiPhong);
        spnTrangThai = fragmentTatCaPhong.findViewById(R.id.spnTrangThai);
        rcvDanhSachPhong = fragmentTatCaPhong.findViewById(R.id.rcvDanhSachPhong);

        // Set title for toolbar
        tvTieuDe.setText(R.string.titleTatCaPhong);

        trangThaiPhongDB = new TrangThaiPhongDatabase();
        loaiPhongDB = new LoaiPhongDatabase();
        phongDB = new PhongDatabase();
        tienNghiDB = new TienNghiDatabase();

        initializeFirestore();

        btnQuanTriKhac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchActivity = new Intent(v.getContext(), MainQuanTriKhac.class);
                startActivity(switchActivity);
            }
        });

        btnThemPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchActivity = new Intent(v.getContext(), ThemPhongActivity.class);
                startActivity(switchActivity);
            }
        });

        return fragmentTatCaPhong;
    }

    @Override
    public void onStart() {
        super.onStart();
        phongDB.readAllDataRoomOfHotel(MA_KS_LOGIN, new PhongCallback() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataCallbackPhong(List<Phong> listPhongs) {
                Log.d("TCPF=>", listPhongs.size() + "");

                // Check thoi han khuyen mai
                int count = 0;
                if (listPhongs != null) {
                    for (Phong aPhong : listPhongs) {
                        if (aPhong.getPhanTramGiamGia() != 0 || !aPhong.getThoiHanGiamGia().equals("")) {
                            if (!checkPromotionDate(aPhong.getThoiHanGiamGia())) {
                                Map<String, Object> fieldsUpdate = new HashMap<String, Object>();
                                fieldsUpdate.put(PhongDatabase.FIELD_PHAN_TRAM_GIAM_GIA, 0);
                                fieldsUpdate.put(PhongDatabase.FIELD_THOI_HAN_GIAM_GIA, "");
                                phongDB.getDb().collection(PhongDatabase.COLLECTION_PHONG).document(aPhong.getMaPhong())
                                        .update(fieldsUpdate)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d("TCP=>", "Update khuyen mai thanh cong!");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d("TCP=>", "Update khuyen mai that bai!");
                                            }
                                        });
                            }
                        }
                        count += 1;
                    }
                }

                // Callback database
                if (count == listPhongs.size()) {
                    phongDB.readAllDataRoomOfHotel(MA_KS_LOGIN, new PhongCallback() {
                        @Override
                        public void onDataCallbackPhong(List<Phong> listPhongs) {
                            danhSachPhongAdapter = new DanhSachPhongAdapter((ArrayList<Phong>) listPhongs, getContext());
                            rcvDanhSachPhong.setAdapter(danhSachPhongAdapter);
                            rcvDanhSachPhong.setHasFixedSize(true);
                            danhSachPhongAdapter.notifyDataSetChanged();

                            layoutManager = new LinearLayoutManager(getActivity());
                            rcvDanhSachPhong.setLayoutManager(layoutManager);

                            svTimKiem.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                @Override
                                public boolean onQueryTextSubmit(String query) {
                                    danhSachPhongAdapter.searchAndFilter(query, spnLoaiPhong.getSelectedItem().toString(), spnTrangThai.getSelectedItem().toString());
                                    return true;
                                }

                                @Override
                                public boolean onQueryTextChange(String newText) {
                                    danhSachPhongAdapter.searchAndFilter(newText, spnLoaiPhong.getSelectedItem().toString(), spnTrangThai.getSelectedItem().toString());
                                    //danhSachPhongAdapter.filter(newText);
                                    Log.d("TCPF=>", newText);
                                    return true;
                                }
                            });

                            spnLoaiPhong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    Log.d("TCPF=>", spnLoaiPhong.getItemAtPosition(position) + "");
                                    danhSachPhongAdapter.searchAndFilter(svTimKiem.getQuery().toString(), spnLoaiPhong.getSelectedItem().toString()
                                            , spnTrangThai.getSelectedItem().toString());
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                            spnTrangThai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    Log.d("TCPF=>", spnTrangThai.getItemAtPosition(position) + "");
                                    danhSachPhongAdapter.searchAndFilter(svTimKiem.getQuery().toString(), spnLoaiPhong.getSelectedItem().toString()
                                            , spnTrangThai.getSelectedItem().toString());
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        }
                    });
                }
            }
        });
    }

    public void initializeFirestore() {
        loaiPhongDB.readAllDataLoaiPhong(new LoaiPhongCallback() {
            @Override
            public void onDataCallbackLoaiPhong(List<LoaiPhong> listLoaiPhongs) {
                Log.d("TCPF=>", listLoaiPhongs.size() + "");

                ArrayList<String> listOnlyLoaiPhong = new ArrayList<String>();
                listOnlyLoaiPhong.add(0, TAT_CA);
                for (LoaiPhong item : listLoaiPhongs) {
                    listOnlyLoaiPhong.add(item.getLoaiPhong());
                }

                ArrayAdapter<String> loaiPhongAdapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, listOnlyLoaiPhong);
                spnLoaiPhong.setAdapter(loaiPhongAdapter);
                loaiPhongAdapter.notifyDataSetChanged();
            }
        });
        trangThaiPhongDB.readAllDataTrangThaiPhong(new TrangThaiPhongCallback() {
            @Override
            public void onDataCallbackTrangThaiPhong(List<TrangThaiPhong> listTrangThaiPhongs) {
                Log.d("TCPF=>", listTrangThaiPhongs.size() + "");

                List<String> listOnlyTrangThaiPhong = new ArrayList<String>();
                listOnlyTrangThaiPhong.add(0, TAT_CA);
                for (TrangThaiPhong item : listTrangThaiPhongs) {
                    listOnlyTrangThaiPhong.add(item.getTrangThaiPhong());
                }

                ArrayAdapter<String> trangThaiPhongAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, listOnlyTrangThaiPhong);
                spnTrangThai.setAdapter(trangThaiPhongAdapter);
                trangThaiPhongAdapter.notifyDataSetChanged();
            }
        });
    }

    // Check ngay hien tai co thuoc khuyen mai hay khong?
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Boolean checkPromotionDate(String thoiHanKhuyenMai) {
        boolean resultCheck = false;

        // Split("-") thoiHanKhuyenMai to get start date and end date
        if (!thoiHanKhuyenMai.equals("")) {
            String[] date = thoiHanKhuyenMai.split("-");

            int startDay, startMonth, startYear;
            int endDay, endMonth, endYear;
            int currentDay, currentMonth, currentYear;

            // Split("/") to get day, month, year of start date and end date
            if (date.length <= 2) {
                String startDate = date[0];
                String endDate = date[1];

                String[] resultStartDate = startDate.split("/");
                String[] resultEndDate = endDate.split("/");

                if (resultStartDate.length == 3 && resultEndDate.length == 3) {
                    startDay = Integer.parseInt(resultStartDate[0]);
                    startMonth = Integer.parseInt(resultStartDate[1]);
                    startYear = Integer.parseInt(resultStartDate[2]);
                    Log.i("GETD=>", "Start date: " + startDay + "/" + startMonth + "/" + startYear);

                    endDay = Integer.parseInt(resultEndDate[0]);
                    endMonth = Integer.parseInt(resultEndDate[1]);
                    endYear = Integer.parseInt(resultEndDate[2]);
                    Log.i("GETD=>", "End date: " + endDay + "/" + endMonth + "/" + endYear);

                    // Get current date
                    DateTime dt = new DateTime();
                    currentDay = dt.getDayOfMonth();
                    currentMonth = dt.getMonthOfYear();
                    currentYear = dt.getYear();
                    Log.i("GETD=>", "Current date: " + currentDay + "/" + currentMonth + "/" + currentYear);

                    // Check current day within start date and end date
                    LocalDate localStartDate = LocalDate.of(startYear, startMonth, startDay);
                    LocalDate localEndDate = LocalDate.of(endYear, endMonth, endDay);
                    LocalDate localCurrentDate = LocalDate.of(currentYear, currentMonth, currentDay);
                    // LocalDate localCurrentDate = LocalDate.of(2021, 1, 3);   // Test

                    resultCheck = (localCurrentDate.isEqual(localStartDate)) || localCurrentDate.isAfter(localStartDate) &&
                            (localCurrentDate.isBefore(localEndDate) || localCurrentDate.isEqual(localEndDate));
                }
            }
        }
        return resultCheck;
    }
}