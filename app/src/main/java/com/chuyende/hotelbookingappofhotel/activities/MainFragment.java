package com.chuyende.hotelbookingappofhotel.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.chuyende.hotelbookingappofhotel.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.chuyende.hotelbookingappofhotel.activities.ManHinhDangNhap.KEY_MA_KS;

public class MainFragment extends AppCompatActivity {

    private BottomNavigationView botNav;

    public static String TEN_TKKS = "";
    Intent intent;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_fragment_layout);

        intent = getIntent();
        bundle = intent.getExtras();

        TEN_TKKS = bundle.getString(KEY_MA_KS);
        Log.d("TTKS=>", TEN_TKKS);

        botNav = findViewById(R.id.botNav);
        botNav.setOnNavigationItemSelectedListener(selectedItem);

        // Setting TatCaPhong Fragment is main fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new TatCaPhongFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener selectedItem = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment fragment = null;
                    switch (item.getItemId()) {
                        case R.id.nav_tat_ca_phong:
                            fragment = new TatCaPhongFragment();
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

                    // Begin Transaction
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                    return true;
                }
            };
}