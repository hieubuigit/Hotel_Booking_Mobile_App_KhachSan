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

import com.chuyende.hotelbookingappofhotel.interfaces.DanhSachHuyCallBack;
import com.chuyende.hotelbookingappofhotel.interfaces.DataCallBack;
import com.chuyende.hotelbookingappofhotel.R;
import com.chuyende.hotelbookingappofhotel.data_models.ThongTinHuy;
import com.chuyende.hotelbookingappofhotel.firebase_models.DBDanhSachHuy;

import java.util.ArrayList;
import java.util.List;

public class DanhSachHuyAdapter extends RecyclerView.Adapter<DanhSachHuyAdapter.DanhSachHuyAdapterVH> implements Filterable {
    private List<ThongTinHuy> listThongTinHuy;
    private List<ThongTinHuy> getlistThongTinHuyFilter;
    private Context context;
    private SelectedItem selectedItem;

    DBDanhSachHuy dbDanhSachHuy = new DBDanhSachHuy();
    private List<String> listTen = new ArrayList<>();
    private List<ThongTinHuy> resultData = new ArrayList<>();

    public DanhSachHuyAdapter(List<ThongTinHuy> listThongTinHuy, SelectedItem selectedItem) {
        this.listThongTinHuy = listThongTinHuy;
        this.getlistThongTinHuyFilter = listThongTinHuy;
        this.selectedItem = selectedItem;
    }

    @NonNull
    @Override
    public DanhSachHuyAdapter.DanhSachHuyAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new DanhSachHuyAdapter.DanhSachHuyAdapterVH(LayoutInflater.from(context).inflate(R.layout.custom_listview, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DanhSachHuyAdapter.DanhSachHuyAdapterVH holder, int position) {
        ThongTinHuy thongTinHuy = listThongTinHuy.get(position);

        dbDanhSachHuy.getTenPhong(thongTinHuy.getMaPhong(), new DataCallBack() {
            @Override
            public void dataCallBack(String info) {
                holder.tvTenPhong.setText(info);
            }
        });

        dbDanhSachHuy.getTenNguoiDung(thongTinHuy.getMaNguoiDung(), new DataCallBack() {
            @Override
            public void dataCallBack(String info) {
                holder.tvTenNguoiDat.setText(info);
                listTen.add(info);
            }
        });

        holder.tvNgayHuy.setText(thongTinHuy.getNgayHuy());
    }

    @Override
    public int getItemCount() {
        return listThongTinHuy.size();
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();

                if(constraint == null | constraint.length() == 0) {
                    filterResults.count = getlistThongTinHuyFilter.size();
                    filterResults.values = getlistThongTinHuyFilter;
                } else {
                    String searchFilter = constraint.toString().toLowerCase();
                    List<String> saveTenNguoiDung = new ArrayList<>();

                    //So sanh ten nguoi dung lay tu recyclerview voi ki tu duoc nhap vao searchview
                    for (int i = 0; i < listTen.size(); i++) {
                        if (listTen.get(i).toLowerCase().contains(searchFilter)){
                            saveTenNguoiDung.add(listTen.get(i));
                        }
                    }

                    //Lay ra danh sach cac thongTinHuy theo ki tu duoc chuyen vao
                    dbDanhSachHuy.thongTinHuyFilter(saveTenNguoiDung, new DanhSachHuyCallBack() {
                        @Override
                        public void danhSachHuyCallBack(ArrayList<ThongTinHuy> huyList) {
                            resultData.clear();
                            for (ThongTinHuy thongTinHuy : huyList) {
                                resultData.add(thongTinHuy);
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
                listThongTinHuy = (List<ThongTinHuy>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }

    public interface SelectedItem{
        void selectedItem(ThongTinHuy thongTinHuy);
    }

    public class DanhSachHuyAdapterVH extends RecyclerView.ViewHolder {
        TextView tvTenPhong;
        TextView tvTenNguoiDat;
        TextView tvNgayHuy;

        public DanhSachHuyAdapterVH(@NonNull View itemView) {
            super(itemView);
            tvTenPhong = itemView.findViewById(R.id.tvTenPhong);
            tvTenNguoiDat = itemView.findViewById(R.id.tvTenNguoiDat);
            tvNgayHuy = itemView.findViewById(R.id.tvNgayDat);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedItem.selectedItem(listThongTinHuy.get(getAdapterPosition()));
                }
            });
        }
    }
}
