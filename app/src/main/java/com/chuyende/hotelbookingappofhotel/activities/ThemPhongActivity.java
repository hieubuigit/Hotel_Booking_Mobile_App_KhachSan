package com.chuyende.hotelbookingappofhotel.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.chuyende.hotelbookingappofhotel.R;

public class ThemPhongActivity extends AppCompatActivity {
    EditText edtMaPhong, edtTenPhong, edtGiaThue, edtSoKhach, edtMoTaPhong, edtDiaChi, edtKinhDo, edtViDo, edtPhanTramGiamGia;
    Spinner spnTrangThaiPhong, spnLoaiPhong, spnTinhThanhPho;
    Button btnCacTienNghi, btnThemPhong;
    TextView tvAnhDaiDien, tvBoSuuTap;
    ImageView imvAnhDaiDien, imvBoSuuTap;

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
        edtMoTaPhong = findViewById(R.id.edtDiaChi);
        edtDiaChi = findViewById(R.id.edtDiaChi);
        edtKinhDo = findViewById(R.id.edtKinhDo);
        edtViDo = findViewById(R.id.edtViDo);
        edtPhanTramGiamGia = findViewById(R.id.edtViDo);
        spnTrangThaiPhong = findViewById(R.id.spnTrangThaiPhong);
        spnLoaiPhong = findViewById(R.id.spnLoaiPhong);
        spnTinhThanhPho = findViewById(R.id.spnTinhThanhPho);
        btnCacTienNghi = findViewById(R.id.btnCacTienNghi);
        btnThemPhong = findViewById(R.id.btnThemPhong);
        tvAnhDaiDien = findViewById(R.id.tvAnhDaiDien);
        tvBoSuuTap = findViewById(R.id.tvBoSuuTap);
        imvAnhDaiDien = findViewById(R.id.imvAnhDaiDien);

    }
}