package com.chuyende.hotelbookingappofhotel.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.chuyende.hotelbookingappofhotel.R;

import java.util.ArrayList;

public class TatCaPhongFragment extends Fragment {
    Button btnQuanTriKhac, btnThemPhong;
    EditText edtTimKiem;
    Spinner spnLoaiPhong, spnTrangThai;
    RecyclerView rcvDanhSachPhong;

    Intent switchActivity;

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

        // Fill fake data for 2 spinner loai phong and trang thai phong
        ArrayList<String> myArr = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            myArr.add("Phong so " + i);
        }

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, myArr);
        spnLoaiPhong.setAdapter(myAdapter);
        spnTrangThai.setAdapter(myAdapter);

        // Handle when user tapped on Quan tri khac
        btnQuanTriKhac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchActivity = new Intent(v.getContext(), MainQuanTriKhac.class);
                startActivity(switchActivity);
            }
        });

        // Handle when user tapped on Them Phong
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