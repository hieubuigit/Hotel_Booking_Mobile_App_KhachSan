package com.chuyende.hotelbookingappofhotel.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.chuyende.hotelbookingappofhotel.R;

public class ThemPhongActivity extends AppCompatActivity {
    EditText edtMaPhong, edtTenPhong, edtGiaThue, edtSoKhach, edtMoTaPhong, edtDiaChi, edtKinhDo, edtViDo, edtPhanTramGiamGia;
    Spinner spnTrangThaiPhong, spnLoaiPhong, spnTinhThanhPho;
    Button btnChonTienNghi, btnThemPhongMoi;
    TextView tvAddAnhDaiDien, tvAddBoSuuTap, tvBoSuuTap;
    ImageView imvAnhDaiDien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.them_phong_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.title_toolbar_them_phong);

        // Get all views from layout
        edtMaPhong = findViewById(R.id.edtMaPhong);
        edtTenPhong = findViewById(R.id.edtTenPhong);
        edtGiaThue = findViewById(R.id.edtGiaThue);
        edtSoKhach = findViewById(R.id.edtSoKhach);
        edtMoTaPhong = findViewById(R.id.edtMoTaPhong);
        edtDiaChi = findViewById(R.id.edtDiaChi);
        edtKinhDo = findViewById(R.id.edtKinhDo);
        edtViDo = findViewById(R.id.edtViDo);
        edtPhanTramGiamGia = findViewById(R.id.edtPhanTramGiamGia);
        spnTrangThaiPhong = findViewById(R.id.spnTrangThaiPhong);
        spnLoaiPhong = findViewById(R.id.spnLoaiPhong);
        spnTinhThanhPho = findViewById(R.id.spnTinhThanhPho);
        btnChonTienNghi = findViewById(R.id.btnChonTienNghi);
        btnThemPhongMoi = findViewById(R.id.btnThemPhongMoi);
        tvAddAnhDaiDien = findViewById(R.id.tvAddAnhDaiDien);
        tvAddBoSuuTap = findViewById(R.id.tvAddBoSuuTap);
        tvBoSuuTap = findViewById(R.id.tvBoSuuTap);
        imvAnhDaiDien = findViewById(R.id.imvAnhDaiDien);

        tvAddAnhDaiDien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ThemPhongActivity.this, "Icon anh dai dien is tapped!", Toast.LENGTH_SHORT).show();
            }
        });

        tvAddBoSuuTap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ThemPhongActivity.this, "Icon Add bo suu tap is tapped!", Toast.LENGTH_SHORT).show();
            }
        });

        tvBoSuuTap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ThemPhongActivity.this, "Bo suu tap is tapped!", Toast.LENGTH_SHORT).show();
            }
        });

        btnChonTienNghi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ThemPhongActivity.this, "Cac tien nghi button is tapped!", Toast.LENGTH_SHORT).show();
            }
        });

        btnThemPhongMoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ThemPhongActivity.this, "Them phong moi button is tapped!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}