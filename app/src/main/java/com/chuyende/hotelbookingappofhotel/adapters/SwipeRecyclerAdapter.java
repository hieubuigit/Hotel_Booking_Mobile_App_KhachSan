package com.chuyende.hotelbookingappofhotel.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chuyende.hotelbookingappofhotel.Interface.DataCallBack;
import com.chuyende.hotelbookingappofhotel.R;
import com.chuyende.hotelbookingappofhotel.data_models.ThongTinThanhToan;
import com.chuyende.hotelbookingappofhotel.firebase_models.DBDanhSachThanhToan;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

import java.util.List;

public class SwipeRecyclerAdapter extends RecyclerSwipeAdapter<SwipeRecyclerAdapter.SwipeRecyclerAdapterVH> {
    private List<ThongTinThanhToan> listThongTinThanhToan;
    private Context context;

    DBDanhSachThanhToan dbDanhSachThanhToan = new DBDanhSachThanhToan();

    public SwipeRecyclerAdapter(List<ThongTinThanhToan> listThongTinThanhToan, Context context) {
        this.listThongTinThanhToan = listThongTinThanhToan;
        this.context = context;
    }

    @Override
    public SwipeRecyclerAdapterVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_swipe_item_recyclerview, parent, false);
        return new SwipeRecyclerAdapterVH(view);
    }

    @Override
    public void onBindViewHolder(SwipeRecyclerAdapterVH holder, int i) {
        ThongTinThanhToan thongTinThanhToan = listThongTinThanhToan.get(i);

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
            }
        });

        holder.tvNgayThanhToan.setText(thongTinThanhToan.getNgayThanhToan());

        // Show swipe layout
        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);

        // Drag a item from right to left
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, holder.swipeLayout.findViewById(R.id.bottom_pull));

        // Action when swiping
        holder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {
            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onClose(SwipeLayout layout) {

            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

            }
        });

        holder.btnHoanTien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Adap", listThongTinThanhToan.get(i).getMaThanhToan());
                notifyDataSetChanged();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Adap", listThongTinThanhToan.get(i).getMaThanhToan());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listThongTinThanhToan.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.SwipeThanhToan;
    }

    public class SwipeRecyclerAdapterVH extends RecyclerView.ViewHolder {
        private TextView tvTenPhong;
        private TextView tvTenNguoiDat;
        private TextView tvNgayThanhToan;
        private Button btnHoanTien;
        private SwipeLayout swipeLayout;

        public SwipeRecyclerAdapterVH(@NonNull View itemView) {
            super(itemView);
            tvTenPhong = itemView.findViewById(R.id.tvTenPhong);
            tvTenNguoiDat = itemView.findViewById(R.id.tvTenNguoiDat);
            tvNgayThanhToan = itemView.findViewById(R.id.tvNgayDat);
            btnHoanTien = itemView.findViewById(R.id.btnHoanTatThanhToan);
            swipeLayout = itemView.findViewById(R.id.SwipeThanhToan);
        }
    }
}
