package com.chuyende.hotelbookingappofhotel.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

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
import com.chuyende.hotelbookingappofhotel.firebase_models.TrangThaiPhongDatabase;
import com.chuyende.hotelbookingappofhotel.interfaces.LoaiPhongCallback;
import com.chuyende.hotelbookingappofhotel.interfaces.PhongCallback;
import com.chuyende.hotelbookingappofhotel.interfaces.TrangThaiPhongCallback;

import java.util.ArrayList;
import java.util.List;

public class TatCaPhongFragment extends Fragment {
    Button btnQuanTriKhac, btnThemPhong;
    EditText edtTimKiem;
    Spinner spnLoaiPhong, spnTrangThai;

    public DanhSachPhongAdapter danhSachPhongAdapter;
    RecyclerView rcvDanhSachPhong;
    RecyclerView.LayoutManager layoutManager;

    Intent switchActivity;

    TrangThaiPhongDatabase trangThaiPhongDB;
    LoaiPhongDatabase loaiPhongDB;
    PhongDatabase phongDB;

    @Override
    public void onStart() {
        super.onStart();

        loaiPhongDB.readAllDataLoaiPhong(new LoaiPhongCallback() {
            @Override
            public void onDataCallbackLoaiPhong(List<LoaiPhong> listLoaiPhongs) {
                Log.d("TCPF=>", listLoaiPhongs.size()+"");

                ArrayList<String> listOnlyLoaiPhong = new ArrayList<String>();
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
                Log.d("TCPF=>", listTrangThaiPhongs.size()+"");

                List<String> listOnlyTrangThaiPhong = new ArrayList<String>();
                for (TrangThaiPhong item : listTrangThaiPhongs) {
                    listOnlyTrangThaiPhong.add(item.getTrangThaiPhong());
                }

                ArrayAdapter<String> trangThaiPhongAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, listOnlyTrangThaiPhong);
                spnTrangThai.setAdapter(trangThaiPhongAdapter);
                trangThaiPhongAdapter.notifyDataSetChanged();
            }
        });

        phongDB.readAllDataRoomOfHotel(ThemPhongActivity.MA_KS_LOGIN, new PhongCallback() {
            @Override
            public void onDataCallbackPhong(List<Phong> listPhongs) {
                Log.d("TCPF=>", listPhongs.size()+"");

                danhSachPhongAdapter = new DanhSachPhongAdapter((ArrayList<Phong>) listPhongs, getContext());
                rcvDanhSachPhong.setAdapter(danhSachPhongAdapter);
                rcvDanhSachPhong.setHasFixedSize(true);
                danhSachPhongAdapter.notifyDataSetChanged();

                layoutManager = new LinearLayoutManager(getActivity());
                rcvDanhSachPhong.setLayoutManager(layoutManager);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentTatCaPhong = null;
        fragmentTatCaPhong = inflater.inflate(R.layout.fragment_tat_ca_phong, container, false);

        //Get all views from layout
        btnQuanTriKhac = fragmentTatCaPhong.findViewById(R.id.btnQuanTriKhac);
        btnThemPhong = fragmentTatCaPhong.findViewById(R.id.btnThemPhong);
        edtTimKiem = fragmentTatCaPhong.findViewById(R.id.edtTimKiem);
        spnLoaiPhong = fragmentTatCaPhong.findViewById(R.id.spnLoaiPhong);
        spnTrangThai = fragmentTatCaPhong.findViewById(R.id.spnTrangThai);
        rcvDanhSachPhong = fragmentTatCaPhong.findViewById(R.id.rcvDanhSachPhong);

        trangThaiPhongDB = new TrangThaiPhongDatabase();
        loaiPhongDB = new LoaiPhongDatabase();
        phongDB = new PhongDatabase();

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