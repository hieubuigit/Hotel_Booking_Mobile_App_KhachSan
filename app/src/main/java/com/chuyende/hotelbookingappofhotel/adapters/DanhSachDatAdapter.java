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

import com.chuyende.hotelbookingappofhotel.Interface.DanhSachDatCallBack;
import com.chuyende.hotelbookingappofhotel.Interface.DataCallBack;
import com.chuyende.hotelbookingappofhotel.R;
import com.chuyende.hotelbookingappofhotel.data_models.ThongTinDat;
import com.chuyende.hotelbookingappofhotel.firebase_models.DBDanhSachDat;

import java.util.ArrayList;
import java.util.List;

public class DanhSachDatAdapter extends RecyclerView.Adapter<DanhSachDatAdapter.DanhSachDatAdapterVH> implements Filterable {
    private List<ThongTinDat> listThongTinDat;
    private List<ThongTinDat> getlistThongTinDatFilter;
    private Context context;
    private SelectedItem selectedItem;

    DBDanhSachDat dbDanhSachDat = new DBDanhSachDat();
    private List<String> listTen = new ArrayList<>();
    private List<ThongTinDat> resultData = new ArrayList<>();

    public DanhSachDatAdapter(List<ThongTinDat> listThongTinDat, SelectedItem selectedItem) {
        this.listThongTinDat = listThongTinDat;
        this.getlistThongTinDatFilter = listThongTinDat;
        this.selectedItem = selectedItem;
    }

    @NonNull
    @Override
    public DanhSachDatAdapter.DanhSachDatAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new DanhSachDatAdapterVH(LayoutInflater.from(context).inflate(R.layout.custom_listview, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DanhSachDatAdapter.DanhSachDatAdapterVH holder, int position) {
        ThongTinDat thongTinDat = listThongTinDat.get(position);

        dbDanhSachDat.getTenPhong(thongTinDat.getMaPhong(), new DataCallBack() {
            @Override
            public void dataCallBack(String info) {
                holder.tvTenPhong.setText(info);
            }
        });

        dbDanhSachDat.getTenNguoiDung(thongTinDat.getMaNguoiDung(), new DataCallBack() {
            @Override
            public void dataCallBack(String info) {
                holder.tvTenNguoiDat.setText(info);
                listTen.add(info);
            }
        });

        holder.tvNgayDat.setText(thongTinDat.getNgayDatPhong());
    }

    @Override
    public int getItemCount() {
        return listThongTinDat.size();
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();

                if(constraint == null | constraint.length() == 0) {
                    filterResults.count = getlistThongTinDatFilter.size();
                    filterResults.values = getlistThongTinDatFilter;
                } else {
                    String searchFilter = constraint.toString().toLowerCase();
                    List<String> saveTenNguoiDung = new ArrayList<>();

                    //So sanh ten nguoi dung lay tu recyclerview voi ki tu duoc nhap vao searchview
                    for (int i = 0; i < listTen.size(); i++) {
                        if (listTen.get(i).toLowerCase().contains(searchFilter)){
                            saveTenNguoiDung.add(listTen.get(i));
                        }
                    }

                    //Lay ra danh sach cac thongTinDat theo ki tu duoc chuyen vao
                    dbDanhSachDat.thongTinDatFilter(saveTenNguoiDung, new DanhSachDatCallBack() {
                        @Override
                        public void danhSachDatCallBack(ArrayList<ThongTinDat> list) {
                            resultData.clear();
                            for (ThongTinDat thongTinDat : list) {
                                resultData.add(thongTinDat);
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
                listThongTinDat = (List<ThongTinDat>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }

    public interface SelectedItem{
        void selectedItem(ThongTinDat thongTinDat);
    }

    public class DanhSachDatAdapterVH extends RecyclerView.ViewHolder {
        TextView tvTenPhong;
        TextView tvTenNguoiDat;
        TextView tvNgayDat;

        public DanhSachDatAdapterVH(@NonNull View itemView) {
            super(itemView);
            tvTenPhong = itemView.findViewById(R.id.tvTenPhong);
            tvTenNguoiDat = itemView.findViewById(R.id.tvTenNguoiDat);
            tvNgayDat = itemView.findViewById(R.id.tvNgayDat);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedItem.selectedItem(listThongTinDat.get(getAdapterPosition()));
                }
            });
        }
    }
}
