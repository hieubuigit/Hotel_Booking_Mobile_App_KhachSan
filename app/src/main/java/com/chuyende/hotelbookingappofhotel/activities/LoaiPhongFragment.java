package com.chuyende.hotelbookingappofhotel.activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.chuyende.hotelbookingappofhotel.R;

import org.w3c.dom.Text;

import java.util.List;

public class LoaiPhongFragment extends Fragment {
    TextView tvIconThemLoaiPhong, tvThemLoaiPhong;
    ListView lvLoaiPhong;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_loai_phong, container, false);

        // Find all views from layout
        tvIconThemLoaiPhong = v.findViewById(R.id.tvIconThemLoaiPhong);
        tvThemLoaiPhong = v.findViewById(R.id.tvThemLoaiPhong);
        lvLoaiPhong = v.findViewById(R.id.listLoaiPhong);

        themLoaiPhong(tvIconThemLoaiPhong);
        themLoaiPhong(tvThemLoaiPhong);

        return inflater.inflate(R.layout.fragment_loai_phong, container, false);
    }

    // Handle when user tapped on icon Them loai phong or Them loai phong
    public void themLoaiPhong(TextView tv) {
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("==>", "TextView them loai phong is tapped!");
            }
        });
    }

}