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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chuyende.hotelbookingappofhotel.interfaces.DanhSachThanhToanCallBack;
import com.chuyende.hotelbookingappofhotel.R;
import com.chuyende.hotelbookingappofhotel.adapters.DanhSachThanhToanAdapter;
import com.chuyende.hotelbookingappofhotel.data_models.ThongTinThanhToan;
import com.chuyende.hotelbookingappofhotel.firebase_models.DBDanhSachThanhToan;

import java.util.ArrayList;


public class DanhSachThanhToanFragment extends Fragment implements DanhSachThanhToanAdapter.SelectedItem{
    TextView tieuDe;
    RecyclerView rvDanhSachThanhToan;
    SearchView svTimKiem;
    Button btnThanhToanTruoc, btnThanhToanDu;

    private static final String TAG ="DanhSachThanhToanFragment";
    public static String TAIKHOANKS = "taiKhoan";

    DBDanhSachThanhToan dbDanhSachThanhToan = new DBDanhSachThanhToan();
    ArrayList<ThongTinThanhToan> listThongTinThanhToan = new ArrayList<>();
    ArrayList<ThongTinThanhToan> listThongTinThanhToanTruoc = new ArrayList<>();
    ArrayList<ThongTinThanhToan> listThongTinThanhToanDu = new ArrayList<>();
    public DanhSachThanhToanAdapter swipeAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_danh_sach_thanh_toan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setControl();
        setEvent();
    }

    private void setEvent() {
        //Thay doi tieu de
        tieuDe.setText("Danh sách các khách hàng đã thanh toán phòng");

        //Lay tai khoan khach san tu man hinh dang nhap
        Bundle bundle = getActivity().getIntent().getExtras();
        String taiKhoanKS = bundle.getString(TAIKHOANKS);

        //Hien thi danh sach thong tin thanh toan len recyclerview
        dbDanhSachThanhToan.hienThiThongTinThanhToan(taiKhoanKS, new DanhSachThanhToanCallBack() {
            @Override
            public void danhSachThanhToanCallBack(ArrayList<ThongTinThanhToan> thanhToanList) {
                for(ThongTinThanhToan thongTinThanhToan : thanhToanList) {
                    listThongTinThanhToan.add(thongTinThanhToan);
                    Log.d(TAG, thongTinThanhToan.getMaPhong());
                }
                swipeAdapter = new DanhSachThanhToanAdapter(listThongTinThanhToan, getContext(), DanhSachThanhToanFragment.this::selectedItem);
                swipeAdapter.notifyDataSetChanged();
                rvDanhSachThanhToan.setHasFixedSize(true);
                rvDanhSachThanhToan.setLayoutManager(new LinearLayoutManager(getContext()));
                rvDanhSachThanhToan.setAdapter(swipeAdapter);

                svTimKiem.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        swipeAdapter.getFilter().filter(newText);
                        return false;
                    }
                });

                //Hien thi thong tin thanh toan truoc len recyclerview khi tap vao nut thanh toan truoc
                btnThanhToanTruoc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listThongTinThanhToanTruoc.clear();
                        dbDanhSachThanhToan.hienThiThongTinThanhToanTruoc(listThongTinThanhToan, new DanhSachThanhToanCallBack() {
                            @Override
                            public void danhSachThanhToanCallBack(ArrayList<ThongTinThanhToan> thanhToanList) {
                                for(ThongTinThanhToan thongTinThanhToan : thanhToanList) {
                                    listThongTinThanhToanTruoc.add(thongTinThanhToan);
                                    Log.d(TAG, thongTinThanhToan.getMaPhong());
                                }
                                swipeAdapter = new DanhSachThanhToanAdapter(listThongTinThanhToanTruoc, getContext(), DanhSachThanhToanFragment.this::selectedItem);
                                swipeAdapter.notifyDataSetChanged();
                                rvDanhSachThanhToan.setHasFixedSize(true);
                                rvDanhSachThanhToan.setLayoutManager(new LinearLayoutManager(getContext()));
                                rvDanhSachThanhToan.setAdapter(swipeAdapter);

                                svTimKiem.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                    @Override
                                    public boolean onQueryTextSubmit(String query) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onQueryTextChange(String newText) {
                                        swipeAdapter.getFilter().filter(newText);
                                        return false;
                                    }
                                });
                            }
                        });
                    }
                });

                //Hien thi thong tin thanh toan du len recyclerview khi tap vao nut thanh toan du
                btnThanhToanDu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listThongTinThanhToanDu.clear();
                        dbDanhSachThanhToan.hienThiThongTinThanhToanDu(listThongTinThanhToan, new DanhSachThanhToanCallBack() {
                            @Override
                            public void danhSachThanhToanCallBack(ArrayList<ThongTinThanhToan> thanhToanList) {
                                for(ThongTinThanhToan thongTinThanhToan : thanhToanList) {
                                    listThongTinThanhToanDu.add(thongTinThanhToan);
                                    Log.d(TAG, thongTinThanhToan.getMaPhong());
                                }
                                swipeAdapter = new DanhSachThanhToanAdapter(listThongTinThanhToanDu, getContext(), DanhSachThanhToanFragment.this::selectedItem);
                                swipeAdapter.notifyDataSetChanged();
                                rvDanhSachThanhToan.setHasFixedSize(true);
                                rvDanhSachThanhToan.setLayoutManager(new LinearLayoutManager(getContext()));
                                rvDanhSachThanhToan.setAdapter(swipeAdapter);

                                svTimKiem.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                    @Override
                                    public boolean onQueryTextSubmit(String query) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onQueryTextChange(String newText) {
                                        swipeAdapter.getFilter().filter(newText);
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
        rvDanhSachThanhToan = getView().findViewById(R.id.rvDanhSachThanhToan);
        svTimKiem = getView().findViewById(R.id.svTimKiem);
        btnThanhToanTruoc = getView().findViewById(R.id.btnThanhToanTruoc);
        btnThanhToanDu = getView().findViewById(R.id.btnThanhToanDu);
    }

    @Override
    public void selectedItem(ThongTinThanhToan thongTinThanhToan) {
        Intent intent = new Intent(getActivity().getApplicationContext(), ManHinhChiTietThanhToan.class);
        Bundle bundle = new Bundle();
        bundle.putString("maThanhToan", thongTinThanhToan.getMaThanhToan());
        intent.putExtras(bundle);
        startActivity(intent);
    }
}