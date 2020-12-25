package com.chuyende.hotelbookingappofhotel.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.chuyende.hotelbookingappofhotel.R;

public class DanhSachTatCaPhongFragment extends Fragment {
    TextView tieuDe;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tat_ca_phong, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Thay doi tieu de
        tieuDe = getView().findViewById(R.id.tvTieuDe);
        tieuDe.setText("Danh sách tất cả phòng");

        Bundle bundle = getActivity().getIntent().getExtras();
        String s = bundle.getString("taiKhoan");
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }
}