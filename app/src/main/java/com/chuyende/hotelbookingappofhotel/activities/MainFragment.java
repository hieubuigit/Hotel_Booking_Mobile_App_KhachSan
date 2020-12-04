package com.chuyende.hotelbookingappofhotel.activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.chuyende.hotelbookingappofhotel.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainFragment extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fragment);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new DanhSachTatCaPhongFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;

            switch (item.getItemId()) {
                case R.id.nav_tat_ca_phong:
                    fragment = new DanhSachTatCaPhongFragment();
                    break;
                case R.id.nav_da_dat:
                    fragment = new DanhSachDatFragment();
                    break;
                case R.id.nav_da_thanh_toan:
                    fragment = new DanhSachThanhToanFragment();
                    break;
                case R.id.nav_da_huy:
                    fragment = new DanhSachHuyFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment).commit();
            return false;
        }
    };
}