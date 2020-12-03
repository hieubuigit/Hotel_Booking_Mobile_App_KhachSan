package com.chuyende.hotelbookingappofhotel.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chuyende.hotelbookingappofhotel.R;
import com.chuyende.hotelbookingappofhotel.data_models.Phong;
import com.google.api.Context;

import java.util.ArrayList;

public class DanhSachPhongAdapter extends RecyclerView.Adapter<DanhSachPhongAdapter.PhongViewHolder> {
    ArrayList<Phong> listPhong = new ArrayList<Phong>();
    private Context context;
    private Intent intent;

    public DanhSachPhongAdapter(ArrayList<Phong> listPhong, Context context) {
        this.listPhong = listPhong;
        this.context = context;
    }

    public static class PhongViewHolder extends RecyclerView.ViewHolder {
        TextView tvMaPhong, tvTenPhong, tvTrangThaiPhong;

        public TextView getTvMaPhong() {
            return tvMaPhong;
        }

        public void setTvMaPhong(TextView tvMaPhong) {
            this.tvMaPhong = tvMaPhong;
        }

        public TextView getTvTenPhong() {
            return tvTenPhong;
        }

        public void setTvTenPhong(TextView tvTenPhong) {
            this.tvTenPhong = tvTenPhong;
        }

        public TextView getTvTrangThaiPhong() {
            return tvTrangThaiPhong;
        }

        public void setTvTrangThaiPhong(TextView tvTrangThaiPhong) {
            this.tvTrangThaiPhong = tvTrangThaiPhong;
        }

        // Constructor PhongViewHolder
        public PhongViewHolder(View v) {
            super(v);
            tvMaPhong = v.findViewById(R.id.tvMaPhong);
            tvTenPhong = v.findViewById(R.id.tvTenPhong);
            tvTrangThaiPhong = v.findViewById(R.id.tvTrangThaiPhong);
        }
    }

    @NonNull
    @Override
    public PhongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_recyclerview_ds_tat_ca_phong, parent, false);
        return new PhongViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PhongViewHolder holder, int position) {
        Phong phong = listPhong.get(position);
        holder.tvMaPhong.setText(phong.getMaPhong());
        holder.tvTenPhong.setText(phong.getTenPhong());
        holder.tvTrangThaiPhong.setText(phong.getMaTrangThaiPhong());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "item is tapped!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPhong.size();
    }

}
