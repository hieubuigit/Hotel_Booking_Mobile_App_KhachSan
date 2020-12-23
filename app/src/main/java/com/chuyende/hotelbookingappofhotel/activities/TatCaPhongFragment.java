package com.chuyende.hotelbookingappofhotel.activities;

import android.content.Intent;
import android.graphics.Bitmap;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.chuyende.hotelbookingappofhotel.activities.MainFragment.TEN_TKKS;

public class TatCaPhongFragment extends Fragment {
    public static String MA_KS_LOGIN = "";

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

    public static final String COLLECTION_KHACH_SAN = "KhachSan";
    public static final String FIELD_TEN_TAI_KHOAN_KS = "tenTaiKhoanKhachSan";
    public static final String FIELD_MA_KS = "maKhachSan";
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static String TAT_CA = "Tất cả";

    @Override
    public void onStart() {
        super.onStart();

        loaiPhongDB.readAllDataLoaiPhong(new LoaiPhongCallback() {
            @Override
            public void onDataCallbackLoaiPhong(List<LoaiPhong> listLoaiPhongs) {
                Log.d("TCPF=>", listLoaiPhongs.size() + "");

                ArrayList<String> listOnlyLoaiPhong = new ArrayList<String>();
                listOnlyLoaiPhong.add(0, TAT_CA);
                for (LoaiPhong item : listLoaiPhongs) {
                    listOnlyLoaiPhong.add(item.getLoaiPhong());
                }

                ArrayAdapter<String> loaiPhongAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, listOnlyLoaiPhong);
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

        // Get maKhachSan from Collection KhachSan
        db.collection(COLLECTION_KHACH_SAN).whereEqualTo(FIELD_TEN_TAI_KHOAN_KS, TEN_TKKS).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    try {
                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            if (TEN_TKKS.trim().equals(doc.get(FIELD_TEN_TAI_KHOAN_KS))) {
                                MA_KS_LOGIN = (String) doc.get(FIELD_MA_KS);
                                Log.d("MAKSLOGIN=>", MA_KS_LOGIN);

                                phongDB.readAllDataRoomOfHotel(MA_KS_LOGIN, new PhongCallback() {
                                    @Override
                                    public void onDataCallbackPhong(List<Phong> listPhongs) {
                                        Log.d("TCPF=>", listPhongs.size() + "");

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
                                break;
                            }
                        }
                    } catch (Exception e) {
                        Log.d("ERR=>", e.getMessage());
                    }
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentTatCaPhong = null;
        fragmentTatCaPhong = inflater.inflate(R.layout.fragment_tat_ca_phong, container, false);

        listBitmap = new ArrayList<Bitmap>();

        Log.d("TENTKND=>", TEN_TKKS);

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
}