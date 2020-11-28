package com.chuyende.hotelbookingappofhotel.activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chuyende.hotelbookingappofhotel.R;

import org.w3c.dom.Text;

import java.util.List;

public class LoaiPhongFragment extends Fragment {
    TextView tvIconThemLoaiPhong, tvThemLoaiPhong;
    RecyclerView rcvLoaiPhong;

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
                Toast.makeText(getContext(), "Icon loai phong is tapped!", Toast.LENGTH_SHORT).show();
            }
        });

        tvThemLoaiPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Loai phong is tapped!", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

}