package com.chuyende.hotelbookingappofhotel.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.chuyende.hotelbookingappofhotel.R;

public class CacTienNghiFragment extends Fragment {
    TextView tvIconThemTienNghi, tvThemTienNghi;
    RecyclerView rcvTienNghi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cac_tien_nghi, container, false);

        // Get all view from layout
        tvIconThemTienNghi = v.findViewById(R.id.tvIconThemTienNghi);
        tvThemTienNghi = v.findViewById(R.id.tvThemTienNghi);
        rcvTienNghi = v.findViewById(R.id.rcvTienNghi);

        // Handle when user tapped on icon Them Phong and Them Tien Nghi
        tvIconThemTienNghi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Icon Cac tien nghi is tapped!", Toast.LENGTH_SHORT).show();
            }
        });

        tvThemTienNghi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Cac tien nghi is tapped!", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

}