package com.chuyende.hotelbookingappofhotel.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chuyende.hotelbookingappofhotel.interfaces.DanhSachHuyCallBack;
import com.chuyende.hotelbookingappofhotel.R;
import com.chuyende.hotelbookingappofhotel.adapters.DanhSachHuyAdapter;
import com.chuyende.hotelbookingappofhotel.data_models.ThongTinHuy;
import com.chuyende.hotelbookingappofhotel.firebase_models.DBDanhSachHuy;
import static com.chuyende.hotelbookingappofhotel.activities.MainFragment.TEN_TKKS;
import static com.chuyende.hotelbookingappofhotel.activities.MainFragment.TTKKS;

import java.util.ArrayList;

public class DanhSachHuyFragment extends Fragment implements DanhSachHuyAdapter.SelectedItem{
    TextView tieuDe;
    RecyclerView rvDanhSachHuy;
    SearchView svTimKiem;
    Button btnDaHoanTien, btnChuaHoanTien;

    private static final String TAG ="DanhSachHuyFragment";
    public static final String MAHUY ="maHuy";
    public static String TRANGTHAIHOANTIEN = "true";

    DBDanhSachHuy dbDanhSachHuy = new DBDanhSachHuy();
    private DanhSachHuyAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_danh_sach_huy, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setControl();
        setEvent();
    }

    private void setEvent() {
        //Thay doi tieu de
        tieuDe.setText("Danh sách các khách hàng đã hủy");

        //Lay du lieu dc chuyen tu man hinh chi tiet huy
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            String tenTKKS = bundle.getString(TTKKS);
            TEN_TKKS = tenTKKS;
        }

        //Hien thi danh sach thong tin huy len recyclerview
        try {
            dbDanhSachHuy.readAllDataHuy(TEN_TKKS, new DanhSachHuyCallBack() {
                @Override
                public void danhSachHuyCallBack(ArrayList<ThongTinHuy> huyList) {
                    adapter = new DanhSachHuyAdapter(huyList, DanhSachHuyFragment.this::selectedItem);
                    rvDanhSachHuy.setHasFixedSize(true);
                    rvDanhSachHuy.setLayoutManager(new LinearLayoutManager(getContext()));
                    rvDanhSachHuy.setAdapter(adapter);

                    svTimKiem.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            return false;
                        }
                        @Override
                        public boolean onQueryTextChange(String newText) {
                            adapter.getFilter().filter(newText);
                            return false;
                        }
                    });
                }
            });
        }catch (Exception e) {
            Log.d(TAG, "Lỗi " + e);
        }

        //Hien thi danh sach thong tin huy chua hoan tien len recyclerview
        try {
            btnChuaHoanTien.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dbDanhSachHuy.readAllDataHuyChuaHoanTien(TEN_TKKS, new DanhSachHuyCallBack() {
                        @Override
                        public void danhSachHuyCallBack(ArrayList<ThongTinHuy> huyList) {
                            adapter = new DanhSachHuyAdapter(huyList, DanhSachHuyFragment.this::selectedItem);
                            rvDanhSachHuy.setHasFixedSize(true);
                            rvDanhSachHuy.setLayoutManager(new LinearLayoutManager(getContext()));
                            rvDanhSachHuy.setAdapter(adapter);

                            svTimKiem.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                @Override
                                public boolean onQueryTextSubmit(String query) {
                                    return false;
                                }
                                @Override
                                public boolean onQueryTextChange(String newText) {
                                    adapter.getFilter().filter(newText);
                                    return false;
                                }
                            });
                        }
                    });
                }
            });
        }catch (Exception e) {
            Log.d(TAG, "Lỗi " + e);
        }

        //Hien thi danh sach thong tin huy da hoan tien len recyclerview
        try {
            btnDaHoanTien.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dbDanhSachHuy.readAllDataHuyDaHoanTien(TEN_TKKS, new DanhSachHuyCallBack() {
                        @Override
                        public void danhSachHuyCallBack(ArrayList<ThongTinHuy> huyList) {
                            adapter = new DanhSachHuyAdapter(huyList, DanhSachHuyFragment.this::selectedItem);
                            rvDanhSachHuy.setHasFixedSize(true);
                            rvDanhSachHuy.setLayoutManager(new LinearLayoutManager(getContext()));
                            rvDanhSachHuy.setAdapter(adapter);

                            svTimKiem.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                @Override
                                public boolean onQueryTextSubmit(String query) {
                                    return false;
                                }
                                @Override
                                public boolean onQueryTextChange(String newText) {
                                    adapter.getFilter().filter(newText);
                                    return false;
                                }
                            });
                        }
                    });
                }
            });
        }catch (Exception e) {
            Log.d(TAG, "Lỗi " + e);
        }
    }

    private void setControl() {
        tieuDe = getView().findViewById(R.id.tvTieuDe);
        rvDanhSachHuy = getView().findViewById(R.id.rvDanhSachHuy);
        svTimKiem = getView().findViewById(R.id.svTimKiem);
        btnChuaHoanTien = getView().findViewById(R.id.btnChuaHoanTien);
        btnDaHoanTien = getView().findViewById(R.id.btnDaHoanTien);
    }

    @Override
    public void selectedItem(ThongTinHuy thongTinHuy) {
        Intent intent = new Intent(getActivity().getApplicationContext(), ManHinhChiTietHuy.class);
        Bundle bundle = new Bundle();
        bundle.putString(MAHUY, thongTinHuy.getMaHuy());
        intent.putExtras(bundle);
        startActivity(intent);
    }
}