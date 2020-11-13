package com.chuyende.hotelbookingappofhotel.activities;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.chuyende.hotelbookingappofhotel.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainFragment extends AppCompatActivity {

    private BottomNavigationView botNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_fragment_layout);

        // Setting custom action bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
                            getSupportActionBar().setTitle(R.string.titleTatCaPhong);
                            break;
                        case R.id.nav_da_dat:
                            fragment = new DaDatFragment();
                            getSupportActionBar().setTitle(R.string.titleDaDat);
                            break;
                        case R.id.nav_da_thanh_toan:
                            fragment = new DaThanhToanFragment();
                            getSupportActionBar().setTitle(R.string.titleDaThanhToan);
                            break;
                        case R.id.nav_da_huy:
                            fragment = new DaHuyFragment();
                            getSupportActionBar().setTitle(R.string.titleDaHuy);
                            break;
                    }

                    // Begin Transaction
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                    return true;
                }
            };
}