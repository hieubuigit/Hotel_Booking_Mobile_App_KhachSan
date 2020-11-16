package com.chuyende.hotelbookingappofhotel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ManHinhChiTietHuy extends AppCompatActivity {

    TextView tieuDe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chi_tiet_huy);

        //Thay doi tieu de
        tieuDe = findViewById(R.id.tvTieuDe);
        tieuDe.setText("Thong tin chi tiết khách hàng hủy");
    }
}