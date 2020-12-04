package com.chuyende.hotelbookingappofhotel.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.chuyende.hotelbookingappofhotel.R;

public class ManhinhChiTietThanhToan extends AppCompatActivity {

    TextView tieuDe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manhinh_chi_tiet_thanh_toan);

        //Thay doi tieu de
        tieuDe = findViewById(R.id.tvTieuDe);
        tieuDe.setText("Thông tin chi tiết khách hàng thanh toán");
    }
}