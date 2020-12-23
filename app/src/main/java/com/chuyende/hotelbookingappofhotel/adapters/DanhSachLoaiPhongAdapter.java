package com.chuyende.hotelbookingappofhotel.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chuyende.hotelbookingappofhotel.R;
import com.chuyende.hotelbookingappofhotel.data_models.LoaiPhong;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

import java.util.ArrayList;

public class DanhSachLoaiPhongAdapter extends RecyclerSwipeAdapter<DanhSachLoaiPhongAdapter.LoaiPhongHolder> {
    ArrayList<LoaiPhong> listLoaiPhong = new ArrayList<LoaiPhong>();
    Context context;
    Intent intent;

    public DanhSachLoaiPhongAdapter() {
    }

    public DanhSachLoaiPhongAdapter(ArrayList<LoaiPhong> listLoaiPhong, Context context) {
        this.listLoaiPhong = listLoaiPhong;
        this.context = context;
    }

    @Override
    public LoaiPhongHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_recyclerview_ds_cac_tien_nghi, parent, false);
        return new LoaiPhongHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LoaiPhongHolder loaiPhongHolder, int i) {
        LoaiPhong loaiPhong = listLoaiPhong.get(i);
        loaiPhongHolder.tvMaLoaiPhong.setText(loaiPhong.getMaLoaiPhong());
        loaiPhongHolder.tvLoaiPhong.setText(loaiPhong.getLoaiPhong());

        loaiPhongHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        loaiPhongHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, loaiPhongHolder.swipeLayout.findViewById(R.id.dragLeft));

        // Event handling when touch item in RecyclerView
        loaiPhongHolder.swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Item " + i, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listLoaiPhong.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipeLayoutLoaiPhong;
    }

    public static class LoaiPhongHolder extends RecyclerView.ViewHolder {
        TextView tvMaLoaiPhong, tvLoaiPhong, tvXoaLoaiPhong;
        SwipeLayout swipeLayout;

        public TextView getTvMaLoaiPhong() {
            return tvMaLoaiPhong;
        }

        public void setTvMaLoaiPhong(TextView tvMaLoaiPhong) {
            this.tvMaLoaiPhong = tvMaLoaiPhong;
        }

        public TextView getTvLoaiPhong() {
            return tvLoaiPhong;
        }

        public void setTvLoaiPhong(TextView tvLoaiPhong) {
            this.tvLoaiPhong = tvLoaiPhong;
        }

        public TextView getTvXoaLoaiPhong() {
            return tvXoaLoaiPhong;
        }

        public void setTvXoaLoaiPhong(TextView tvXoaLoaiPhong) {
            this.tvXoaLoaiPhong = tvXoaLoaiPhong;
        }

        public SwipeLayout getSwipeLayout() {
            return swipeLayout;
        }

        public void setSwipeLayout(SwipeLayout swipeLayout) {
            this.swipeLayout = swipeLayout;
        }

        public LoaiPhongHolder(@NonNull View itemView) {
            super(itemView);
            swipeLayout = itemView.findViewById(R.id.swipeLayout);
            tvMaLoaiPhong = itemView.findViewById(R.id.tvMaLoaiPhong);
            tvLoaiPhong = itemView.findViewById(R.id.tvLoaiPhong);
            tvXoaLoaiPhong = itemView.findViewById(R.id.tvXoaLoaiPhong);
        }
    }
}
