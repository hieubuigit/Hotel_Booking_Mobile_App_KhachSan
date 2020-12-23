package com.chuyende.hotelbookingappofhotel.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chuyende.hotelbookingappofhotel.interfaces.DanhSachThanhToanCallBack;
import com.chuyende.hotelbookingappofhotel.interfaces.DataCallBack;
import com.chuyende.hotelbookingappofhotel.R;
import com.chuyende.hotelbookingappofhotel.data_models.ThongTinThanhToan;
import com.chuyende.hotelbookingappofhotel.firebase_models.DBDanhSachThanhToan;

import java.util.ArrayList;
import java.util.List;

public class DanhSachThanhToanAdapter extends RecyclerView.Adapter<DanhSachThanhToanAdapter.DanhSachThanhToanAdapterVH> implements Filterable {
    private List<ThongTinThanhToan> listThongTinThanhToan;
    private List<ThongTinThanhToan> getlistThongTinThanhToanFilter;
    private Context context;
    private SelectedItem selectedItem;

    DBDanhSachThanhToan dbDanhSachThanhToan = new DBDanhSachThanhToan();
    private List<String> listTen = new ArrayList<>();
    private List<ThongTinThanhToan> resultData = new ArrayList<>();

    public DanhSachThanhToanAdapter(List<ThongTinThanhToan> listThongTinThanhToan, SelectedItem selectedItem) {
        this.listThongTinThanhToan = listThongTinThanhToan;
        this.getlistThongTinThanhToanFilter = listThongTinThanhToan;
        this.selectedItem = selectedItem;
    }

    @NonNull
    @Override
    public DanhSachThanhToanAdapter.DanhSachThanhToanAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new DanhSachThanhToanAdapter.DanhSachThanhToanAdapterVH(LayoutInflater.from(context).inflate(R.layout.custom_listview, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DanhSachThanhToanAdapter.DanhSachThanhToanAdapterVH holder, int position) {
        ThongTinThanhToan thongTinThanhToan = listThongTinThanhToan.get(position);

        dbDanhSachThanhToan.getTenPhong(thongTinThanhToan.getMaPhong(), new DataCallBack() {
            @Override
            public void dataCallBack(String info) {
                holder.tvTenPhong.setText(info);
            }
        });

        dbDanhSachThanhToan.getTenNguoiDung(thongTinThanhToan.getMaNguoiDung(), new DataCallBack() {
            @Override
            public void dataCallBack(String info) {
                holder.tvTenNguoiDat.setText(info);
                listTen.add(info);
            }
        });

        holder.tvNgayThanhToan.setText(thongTinThanhToan.getNgayThanhToan());
    }

    @Override
    public int getItemCount() {
        return listThongTinThanhToan.size();
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();

                if (constraint == null | constraint.length() == 0) {
                    filterResults.count = getlistThongTinThanhToanFilter.size();
                    filterResults.values = getlistThongTinThanhToanFilter;
                } else {
                    String searchFilter = constraint.toString().toLowerCase();
                    List<String> saveTenNguoiDung = new ArrayList<>();

                    //So sanh ten nguoi dung lay tu recyclerview voi ki tu duoc nhap vao searchview
                    for (int i = 0; i < listTen.size(); i++) {
                        if (listTen.get(i).toLowerCase().contains(searchFilter)) {
                            saveTenNguoiDung.add(listTen.get(i));
                        }
                    }

                    //Lay ra danh sach cac thongTinThanhToan theo ki tu duoc chuyen vao
                    dbDanhSachThanhToan.thongTinThanhToanFilter(saveTenNguoiDung, new DanhSachThanhToanCallBack() {
                        @Override
                        public void danhSachThanhToanCallBack(ArrayList<ThongTinThanhToan> thanhToanList) {
                            resultData.clear();
                            for (ThongTinThanhToan thongTinThanhToan : thanhToanList) {
                                resultData.add(thongTinThanhToan);
                            }
                        }
                    });
                    filterResults.count = resultData.size();
                    filterResults.values = resultData;
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listThongTinThanhToan = (List<ThongTinThanhToan>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }

    public interface SelectedItem {
        void selectedItem(ThongTinThanhToan thongTinThanhToan);
    }

    public class DanhSachThanhToanAdapterVH extends RecyclerView.ViewHolder {
        private TextView tvTenPhong;
        private TextView tvTenNguoiDat;
        private TextView tvNgayThanhToan;

        public DanhSachThanhToanAdapterVH(@NonNull View itemView) {
            super(itemView);
            tvTenPhong = itemView.findViewById(R.id.tvTenPhong);
            tvTenNguoiDat = itemView.findViewById(R.id.tvTenNguoiDat);
            tvNgayThanhToan = itemView.findViewById(R.id.tvNgayDat);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedItem.selectedItem(listThongTinThanhToan.get(getAdapterPosition()));
                }
            });
        }
    }
}
