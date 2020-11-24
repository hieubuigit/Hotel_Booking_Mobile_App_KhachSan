package com.chuyende.hotelbookingappofhotel.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.QuickContactBadge;

import com.chuyende.hotelbookingappofhotel.R;

public class MainQuanTriKhac extends AppCompatActivity {
    public Fragment fragment = null;
    private Button btnCacTienNghi;
    private Button btnLoaiPhong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_quan_tri_khac_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.toolbar_title_quan_tri_khac);

        // Get all views from layout
        btnCacTienNghi = findViewById(R.id.btnCacTienNghi);
        btnLoaiPhong = findViewById(R.id.btnLoaiPhong);

        // Setting default fragment layout
        getSupportFragmentManager().beginTransaction().replace(R.id.fragContainer, new CacTienNghiFragment()).commit();

        // Event Fill fragment when touch on button Cac tien nghi
        btnCacTienNghi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new CacTienNghiFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragContainer, new CacTienNghiFragment()).commit();
                getSupportActionBar().setTitle(R.string.text_in_btn_cac_tien_nghi);
            }
        });

        // Event Fill fragment when touch on button Loai phong
        btnLoaiPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new LoaiPhongFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragContainer, new LoaiPhongFragment()).commit();
                getSupportActionBar().setTitle(R.string.text_in_btn_loai_phong);
            }
        });
    }
}