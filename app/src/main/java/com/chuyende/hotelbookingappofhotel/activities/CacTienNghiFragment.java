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
import com.chuyende.hotelbookingappofhotel.adapters.DanhSachTienNghiAdapter;
import com.chuyende.hotelbookingappofhotel.data_models.TienNghi;
import com.chuyende.hotelbookingappofhotel.dialogs.ThemTienNghiDialog;
import com.chuyende.hotelbookingappofhotel.firebase_models.TienNghiDatabase;
import com.chuyende.hotelbookingappofhotel.interfaces.ChonTienNghiCallback;

import java.util.ArrayList;
import java.util.List;

public class CacTienNghiFragment extends Fragment {
    TextView tvIconThemTienNghi, tvThemTienNghi;

    DanhSachTienNghiAdapter danhSachTienNghiAdapter;
    RecyclerView rcvTienNghi;
    RecyclerView.LayoutManager layoutManager;

    TienNghiDatabase tienNghiDatabase;

    public static boolean cacTienNghiFramentIsRunning = false;

    @Override
    public void onStart() {
        super.onStart();
        cacTienNghiFramentIsRunning = true;

        tienNghiDatabase.readAllDataTienNghi(new ChonTienNghiCallback() {
            @Override
            public void onDataCallbackChonTienNghi(List<TienNghi> listTienNghis) {
                Log.d("CTNF=>", listTienNghis.size() + "");

                danhSachTienNghiAdapter = new DanhSachTienNghiAdapter((ArrayList<TienNghi>) listTienNghis, getContext());
                rcvTienNghi.setAdapter(danhSachTienNghiAdapter);
                danhSachTienNghiAdapter.notifyDataSetChanged();

                layoutManager = new LinearLayoutManager(getContext());
                rcvTienNghi.setLayoutManager(layoutManager);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cac_tien_nghi, container, false);

        tienNghiDatabase = new TienNghiDatabase();

        // Get all view from layout
        tvIconThemTienNghi = v.findViewById(R.id.tvIconThemTienNghi);
        tvThemTienNghi = v.findViewById(R.id.tvThemTienNghi);
        rcvTienNghi = v.findViewById(R.id.rcvTienNghi);

        // Handle when user tapped on icon Them Phong and Them Tien Nghi
        tvIconThemTienNghi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CTNF=>", "Icon them tien nghi is tapped!");
                showDialogThemTienNghi();
            }
        });

        tvThemTienNghi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CTNF=>", "Text them tien nghi is tapped!");
                showDialogThemTienNghi();
            }
        });
        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
        cacTienNghiFramentIsRunning = false;
    }

    public void showDialogThemTienNghi() {
        DialogFragment themTNFragment = new ThemTienNghiDialog();
        themTNFragment.show(getChildFragmentManager(), "THEM_TIEN_NGHI");
    }
}