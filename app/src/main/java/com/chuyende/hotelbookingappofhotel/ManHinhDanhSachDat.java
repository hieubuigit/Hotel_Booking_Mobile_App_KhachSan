package com.chuyende.hotelbookingappofhotel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ManHinhDanhSachDat extends AppCompatActivity {

    TextView tieuDe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_danh_sach_dat);

        //Thay doi tieu de
        tieuDe = findViewById(R.id.tvTieuDe);
        tieuDe.setText("Danh sách các khách hàng đã đặt phòng");
    }
}