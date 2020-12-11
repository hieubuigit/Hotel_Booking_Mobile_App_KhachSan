package com.chuyende.hotelbookingappofhotel.activities;

import android.os.Bundle;
import android.util.Log;
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

import com.chuyende.hotelbookingappofhotel.Interface.MaKhachSanCallBack;
import com.chuyende.hotelbookingappofhotel.R;
import com.chuyende.hotelbookingappofhotel.adapters.DanhSachDatAdapter;
import com.chuyende.hotelbookingappofhotel.firebase_models.DBDanhSachDat;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DanhSachDatFragment extends Fragment {
    TextView tieuDe;
    RecyclerView rvDanhSachDat;
    SearchView svTimKiem;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static String TAG = "DanhSachDatFragment";
    DBDanhSachDat dbDanhSachDat = new DBDanhSachDat();
    private DanhSachDatAdapter adapter;

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
    }

    @Override
    public void onStart() {
        super.onStart();
        //Lay tai khoan khach san tu man hinh dang nhap
        Bundle bundle = getActivity().getIntent().getExtras();
        String taiKhoanKS = bundle.getString("taiKhoan");

        dbDanhSachDat.getMaPhong(taiKhoanKS, new MaKhachSanCallBack() {
            @Override
            public void maKhachSanCallBack(String maKhachSan) {
                Log.d(TAG, maKhachSan);

                adapter = new DanhSachDatAdapter(dbDanhSachDat.hienThiDuLieu(taiKhoanKS));
                adapter.updateOptions(dbDanhSachDat.hienThiDuLieu(maKhachSan));
                rvDanhSachDat.setHasFixedSize(true);
                rvDanhSachDat.setLayoutManager(new LinearLayoutManager(getContext()));
                rvDanhSachDat.setAdapter(adapter);
                adapter.startListening();
            }
        });


    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    private void setControl() {
        tieuDe = getView().findViewById(R.id.tvTieuDe);
        rvDanhSachDat = getView().findViewById(R.id.rvDanhSachDat);
        svTimKiem = getView().findViewById(R.id.svTimKiem);
    }
}