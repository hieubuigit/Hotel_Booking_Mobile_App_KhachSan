package com.chuyende.hotelbookingappofhotel.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.chuyende.hotelbookingappofhotel.R;

import java.util.ArrayList;

public class DanhSachHuyFragment extends Fragment {
    TextView tieuDe;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_danh_sach_huy, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Thay doi tieu de
        tieuDe = getView().findViewById(R.id.tvTieuDe);
        tieuDe.setText("Danh sách các khách hàng đã hủy");

    }
}