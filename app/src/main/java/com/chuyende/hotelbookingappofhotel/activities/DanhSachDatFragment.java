package com.chuyende.hotelbookingappofhotel.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chuyende.hotelbookingappofhotel.Interface.DanhSachDatCallBack;
import com.chuyende.hotelbookingappofhotel.Interface.ListDataCallBack;
import com.chuyende.hotelbookingappofhotel.R;
import com.chuyende.hotelbookingappofhotel.adapters.DanhSachDatAdapter;
import com.chuyende.hotelbookingappofhotel.data_models.ThongTinDat;
import com.chuyende.hotelbookingappofhotel.firebase_models.DBDanhSachDat;

import java.util.ArrayList;
import java.util.List;

public class DanhSachDatFragment extends Fragment implements DanhSachDatAdapter.SelectedItem{
    TextView tieuDe;
    RecyclerView rvDanhSachDat;
    SearchView svTimKiem;

    public static String TAG = "DanhSachDatFragment";
    DBDanhSachDat dbDanhSachDat = new DBDanhSachDat();
    private DanhSachDatAdapter adapter;
    ArrayList<ThongTinDat> listThongTinDat = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_danh_sach_dat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setControl();
        setEvent();
    }

    private void setEvent() {
        //Thay doi tieu de
        tieuDe.setText("Danh sách các khách hàng đã đặt phòng");

        //Lay tai khoan khach san tu man hinh dang nhap
        Bundle bundle = getActivity().getIntent().getExtras();
        String taiKhoanKS = bundle.getString("taiKhoan");

        //Hien thi danh sach thong tin dat len recyclerview
        dbDanhSachDat.hienThiThongTinDat(taiKhoanKS, new DanhSachDatCallBack() {
            @Override
            public void danhSachDatCallBack(ArrayList<ThongTinDat> list) {
                for(ThongTinDat thongTinDat : list) {
                    listThongTinDat.add(thongTinDat);
                }
                adapter = new DanhSachDatAdapter(listThongTinDat, DanhSachDatFragment.this::selectedItem);
                rvDanhSachDat.setHasFixedSize(true);
                rvDanhSachDat.setLayoutManager(new LinearLayoutManager(getContext()));
                rvDanhSachDat.setAdapter(adapter);

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

    private void setControl() {
        tieuDe = getView().findViewById(R.id.tvTieuDe);
        rvDanhSachDat = getView().findViewById(R.id.rvDanhSachDat);
        svTimKiem = getView().findViewById(R.id.svTimKiem);
    }

    @Override
    public void selectedItem(ThongTinDat thongTinDat) {
        Intent intent = new Intent(getActivity().getApplicationContext(), ManHinhChiTietDat.class);
        Bundle bundle = new Bundle();
        bundle.putString("maDat", thongTinDat.getMaDat());
        intent.putExtras(bundle);
        startActivity(intent);
    }
}