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

import java.util.ArrayList;

public class DanhSachHuyFragment extends Fragment implements DanhSachHuyAdapter.SelectedItem{
    TextView tieuDe;
    RecyclerView rvDanhSachHuy;
    SearchView svTimKiem;
    Button btnDaHoanTien, btnChuaHoanTien;

    private static final String TAG ="DanhSachHuyFragment";
    public static String TAIKHOANKS = "taiKhoan";

    DBDanhSachHuy dbDanhSachHuy = new DBDanhSachHuy();
    private DanhSachHuyAdapter adapter;
    ArrayList<ThongTinHuy> listThongTinHuy = new ArrayList<>();
    ArrayList<ThongTinHuy> listThongTinHuyChuaHoanTien = new ArrayList<>();
    ArrayList<ThongTinHuy> listThongTinHuyDaHoanTien = new ArrayList<>();

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

        //Lay tai khoan khach san tu man hinh dang nhap
        Bundle bundle = getActivity().getIntent().getExtras();
        String taiKhoanKS = bundle.getString(TAIKHOANKS);

        //Hien thi danh sach thong tin huy len recyclerview
        dbDanhSachHuy.hienThiThongTinHuy(taiKhoanKS, new DanhSachHuyCallBack() {
            @Override
            public void danhSachHuyCallBack(ArrayList<ThongTinHuy> huyList) {
                for(ThongTinHuy thongTinHuy : huyList) {
                    listThongTinHuy.add(thongTinHuy);
                    Log.d(TAG, thongTinHuy.getMaPhong());
                }
                adapter = new DanhSachHuyAdapter(listThongTinHuy, DanhSachHuyFragment.this::selectedItem);
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

                //Hien thi thong tin huy chua hoan tien len recyclerview khi tap vao nut chua hoan tien
                btnChuaHoanTien.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listThongTinHuyChuaHoanTien.clear();
                        dbDanhSachHuy.hienThiThongTinHuyChuaHoanTien(listThongTinHuy, new DanhSachHuyCallBack() {
                            @Override
                            public void danhSachHuyCallBack(ArrayList<ThongTinHuy> huyList) {
                                for(ThongTinHuy thongTinHuy : huyList) {
                                    listThongTinHuyChuaHoanTien.add(thongTinHuy);
                                    Log.d(TAG, thongTinHuy.getMaPhong());
                                }
                                adapter = new DanhSachHuyAdapter(listThongTinHuyChuaHoanTien, DanhSachHuyFragment.this::selectedItem);
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

                //Hien thi thong tin huy da hoan tien len recyclerview khi tap vao nut da hoan tien
                btnDaHoanTien.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listThongTinHuyDaHoanTien.clear();
                        dbDanhSachHuy.hienThiThongTinHuyDaHoanTien(listThongTinHuy, new DanhSachHuyCallBack() {
                            @Override
                            public void danhSachHuyCallBack(ArrayList<ThongTinHuy> huyList) {
                                for(ThongTinHuy thongTinHuy : huyList) {
                                    listThongTinHuyDaHoanTien.add(thongTinHuy);
                                    Log.d(TAG, thongTinHuy.getMaPhong());
                                }
                                adapter = new DanhSachHuyAdapter(listThongTinHuyDaHoanTien, DanhSachHuyFragment.this::selectedItem);
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
            }
        });
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
        bundle.putString("maHuy", thongTinHuy.getMaHuy());
        intent.putExtras(bundle);
        startActivity(intent);
    }
}