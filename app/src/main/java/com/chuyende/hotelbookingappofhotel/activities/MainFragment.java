package com.chuyende.hotelbookingappofhotel.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.chuyende.hotelbookingappofhotel.R;
import com.chuyende.hotelbookingappofhotel.firebase_models.DBManHinhDangNhap;
import com.chuyende.hotelbookingappofhotel.interfaces.DataCallBack;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import static com.chuyende.hotelbookingappofhotel.activities.ManHinhChiTietDat.MHDAT;
import static com.chuyende.hotelbookingappofhotel.activities.ManHinhDangNhap.KEY_MAKS;
import static com.chuyende.hotelbookingappofhotel.activities.ManHinhChiTietThanhToan.MHTHANHTOAN;
import static com.chuyende.hotelbookingappofhotel.activities.ManHinhChiTietThanhToan.MATT;
import static com.chuyende.hotelbookingappofhotel.activities.ManHinhChiTietDat.MAD;
import static com.chuyende.hotelbookingappofhotel.activities.ManHinhChiTietDat.TRANGTHAIXOA;
import static com.chuyende.hotelbookingappofhotel.activities.ManHinhChiTietHuy.MHHUY;

public class MainFragment extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    public static String TEN_TKKS = "";
    public static String TAG = "MainFragment";
    public static String TTKKS = "TTKKS";
    public static String MADTT = "maDTT";
    public static String MADD = "maDD";
    public static String TRANGTHAIXOAD = "TrangThaiXoaDat";
    DBManHinhDangNhap dbManHinhDangNhap = new DBManHinhDangNhap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fragment);

        Bundle bundleTKKS = getIntent().getExtras();
        TEN_TKKS = bundleTKKS.getString(KEY_MAKS);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new DanhSachTatCaPhongFragment()).commit();

        //Move from ManHinhChiTietThanhToan to ManHinhDanhSachThanhToan
        Bundle bundleThanhToan = getIntent().getExtras();
        String mhtt = bundleThanhToan.getString(MHTHANHTOAN);
        String matt = bundleThanhToan.getString(MATT);
        if (mhtt != null && mhtt.equals(MHTHANHTOAN)) {
            try {
                dbManHinhDangNhap.getTenTaiKhoanKhachSan(new DataCallBack() {
                    @Override
                    public void dataCallBack(String info) {
                        Bundle bundleThanhToan = new Bundle();
                        bundleThanhToan.putString(TTKKS, info);
                        bundleThanhToan.putString(MADTT, matt);
                        DanhSachThanhToanFragment fragment = new DanhSachThanhToanFragment();
                        fragment.setArguments(bundleThanhToan);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment).commit();
                    }
                });
            }catch (Exception e) {
                Log.d(TAG, "Lỗi " + e);
            }
        }

        //Move from ManHinhChiTietDat to ManHinhDanhSachDat
        Bundle bundleDat = getIntent().getExtras();
        String mhd = bundleDat.getString(MHDAT);
        String xoa = bundleDat.getString(TRANGTHAIXOA);
        String mad = bundleDat.getString(MAD);
        if (mhd != null && mhd.equals(MHDAT)) {
            try {
                dbManHinhDangNhap.getTenTaiKhoanKhachSan(new DataCallBack() {
                    @Override
                    public void dataCallBack(String info) {
                        Bundle bundleDat = new Bundle();
                        bundleDat.putString(TTKKS, info);
                        bundleDat.putString(MADD, mad);
                        bundleDat.putString(TRANGTHAIXOAD, xoa);
                        DanhSachDatFragment datFragment = new DanhSachDatFragment();
                        datFragment.setArguments(bundleDat);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, datFragment).commit();
                    }
                });
            }catch (Exception e) {
                Log.d(TAG, "Lỗi " + e);
            }
        }

        //Move from ManHinhChiTietHuy to ManHinhDanhSachHuy
        Bundle bundleHuy = getIntent().getExtras();
        String mhh = bundleThanhToan.getString(MHHUY);
        if (mhh != null && mhh.equals(MHHUY)) {
            try {
                dbManHinhDangNhap.getTenTaiKhoanKhachSan(new DataCallBack() {
                    @Override
                    public void dataCallBack(String info) {
                        Bundle bundleHuy = new Bundle();
                        bundleHuy.putString(TTKKS, info);
                        DanhSachHuyFragment huyFragment = new DanhSachHuyFragment();
                        huyFragment.setArguments(bundleHuy);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, huyFragment).commit();
                    }
                });
            }catch (Exception e) {
                Log.d(TAG, "Lỗi " + e);
            }
        }

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