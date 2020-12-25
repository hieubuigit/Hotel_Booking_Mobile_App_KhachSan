package com.chuyende.hotelbookingappofhotel.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.chuyende.hotelbookingappofhotel.Interface.DataCallBack;
import com.chuyende.hotelbookingappofhotel.R;
import com.chuyende.hotelbookingappofhotel.data_models.ThongTinThanhToan;
import com.chuyende.hotelbookingappofhotel.firebase_models.DBDanhSachThanhToan;

import java.util.List;

public class SwipeAdapter extends RecyclerView.Adapter<SwipeAdapter.SwipeAdapterVH> {
    private List<ThongTinThanhToan> listThongTinThanhToan;
    private Context context;
    private SelectedItem selectedItem;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    DBDanhSachThanhToan dbDanhSachThanhToan = new DBDanhSachThanhToan();

    public SwipeAdapter(List<ThongTinThanhToan> listThongTinThanhToan, Context context, SelectedItem selectedItem) {
        this.listThongTinThanhToan = listThongTinThanhToan;
        this.context = context;
        this.selectedItem = selectedItem;
    }

    @NonNull
    @Override
    public SwipeAdapter.SwipeAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_swipe_item, parent, false);
        return new SwipeAdapterVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SwipeAdapter.SwipeAdapterVH holder, int position) {
        viewBinderHelper.setOpenOnlyOne(true);
        viewBinderHelper.bind(holder.swipeRevealLayout, String.valueOf(listThongTinThanhToan.get(position).getMaThanhToan()));
        viewBinderHelper.closeLayout(String.valueOf(listThongTinThanhToan.get(position).getMaThanhToan()));
        holder.blindData(listThongTinThanhToan.get(position));
    }

    public interface SelectedItem{
        void selectedItem(ThongTinThanhToan thongTinThanhToan);
    }

    @Override
    public int getItemCount() {
        return listThongTinThanhToan.size();
    }

    public class SwipeAdapterVH extends RecyclerView.ViewHolder {
        private TextView tvTenPhong;
        private TextView tvTenNguoiDat;
        private TextView tvNgayThanhToan;
        private Button btnHoanTien;
        private SwipeRevealLayout swipeRevealLayout;

        public SwipeAdapterVH(@NonNull View itemView) {
            super(itemView);
            tvTenPhong = itemView.findViewById(R.id.tvTenPhong);
            tvTenNguoiDat = itemView.findViewById(R.id.tvTenNguoiDat);
            tvNgayThanhToan = itemView.findViewById(R.id.tvNgayDat);
            btnHoanTien = itemView.findViewById(R.id.btnThanhToan);
            swipeRevealLayout = itemView.findViewById(R.id.SwipeLayout);

            btnHoanTien.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, listThongTinThanhToan.get(getAdapterPosition()).getMaThanhToan(), Toast.LENGTH_LONG).show();
                }
            });

            tvTenNguoiDat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedItem.selectedItem(listThongTinThanhToan.get(getAdapterPosition()));
                }
            });
        }

        void blindData(ThongTinThanhToan thongTinThanhToan) {
            dbDanhSachThanhToan.getTenPhong(thongTinThanhToan.getMaPhong(), new DataCallBack() {
                @Override
                public void dataCallBack(String info) {
                    tvTenPhong.setText(info);
                }
            });

            dbDanhSachThanhToan.getTenNguoiDung(thongTinThanhToan.getMaNguoiDung(), new DataCallBack() {
                @Override
                public void dataCallBack(String info) {
                    tvTenNguoiDat.setText(info);
                }
            });

            tvNgayThanhToan.setText(thongTinThanhToan.getNgayThanhToan());
        }
    }
}
