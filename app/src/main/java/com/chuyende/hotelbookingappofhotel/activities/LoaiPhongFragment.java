package com.chuyende.hotelbookingappofhotel.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chuyende.hotelbookingappofhotel.R;
import com.chuyende.hotelbookingappofhotel.adapters.DanhSachLoaiPhongAdapter;
import com.chuyende.hotelbookingappofhotel.data_models.LoaiPhong;
import com.chuyende.hotelbookingappofhotel.dialogs.ThemLoaiPhongDialog;
import com.chuyende.hotelbookingappofhotel.firebase_models.LoaiPhongDatabase;
import com.chuyende.hotelbookingappofhotel.interfaces.LoaiPhongCallback;

import java.util.ArrayList;
import java.util.List;

public class LoaiPhongFragment extends Fragment {
    TextView tvIconThemLoaiPhong, tvThemLoaiPhong;

    DanhSachLoaiPhongAdapter danhSachLoaiPhongAdapter;
    RecyclerView rcvLoaiPhong;
    RecyclerView.LayoutManager layoutManager;

    public static boolean loaiPhongFragmentIsRunning = false;

    LoaiPhongDatabase loaiPhongDatabase = new LoaiPhongDatabase();

    @Override
    public void onStart() {
        super.onStart();
        loaiPhongFragmentIsRunning = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_loai_phong, container, false);

        // Find all views from layout
        tvIconThemLoaiPhong = v.findViewById(R.id.tvIconThemLoaiPhong);
        tvThemLoaiPhong = v.findViewById(R.id.tvThemLoaiPhong);
        rcvLoaiPhong = v.findViewById(R.id.rcvLoaiPhong);

        tvIconThemLoaiPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("LPF=>", "Icon them loai phong is tapped!");
                showThemLoaiPhongDialog();
            }
        });

        tvThemLoaiPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("LPF=>", "Text them loai phong is tapped!");
                showThemLoaiPhongDialog();
            }
        });

        loaiPhongDatabase.readAllDataLoaiPhong(new LoaiPhongCallback() {
            @Override
            public void onDataCallbackLoaiPhong(List<LoaiPhong> listLoaiPhongs) {
                danhSachLoaiPhongAdapter = new DanhSachLoaiPhongAdapter((ArrayList<LoaiPhong>) listLoaiPhongs, getContext());
                rcvLoaiPhong.setAdapter(danhSachLoaiPhongAdapter);
                danhSachLoaiPhongAdapter.notifyDataSetChanged();

                layoutManager = new LinearLayoutManager(getContext());
                rcvLoaiPhong.setLayoutManager(layoutManager);
            }
        });

        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
        loaiPhongFragmentIsRunning = false;
    }

    public void showThemLoaiPhongDialog() {
        DialogFragment themLoaiPhongDialog = new ThemLoaiPhongDialog();
        themLoaiPhongDialog.show(getChildFragmentManager(), "THEM_LOAI_PHONG");
    }
}