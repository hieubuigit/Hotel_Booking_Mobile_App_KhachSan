package com.chuyende.hotelbookingappofhotel.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chuyende.hotelbookingappofhotel.R;
import com.chuyende.hotelbookingappofhotel.activities.CapNhatPhongActivity;
import com.chuyende.hotelbookingappofhotel.data_models.Phong;

import java.util.ArrayList;

import static com.chuyende.hotelbookingappofhotel.activities.TatCaPhongFragment.TAT_CA;

public class DanhSachPhongAdapter extends RecyclerView.Adapter<DanhSachPhongAdapter.PhongViewHolder> {
    ArrayList<Phong> listPhongs = new ArrayList<Phong>();
    ArrayList<Phong> listPhongsCopy = new ArrayList<Phong>();
    private Context context;

    private Intent switchActivity;
    public static Bundle containerData;
    public static final String KEY_MA_PHONG = "maPhong";

    public DanhSachPhongAdapter(ArrayList<Phong> listPhongs, Context context) {
        this.listPhongs = listPhongs;
        this.context = context;
        this.listPhongsCopy.addAll(listPhongs);
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
        Phong phong = listPhongs.get(position);
        holder.tvMaPhong.setText(phong.getMaPhong());
        holder.tvTenPhong.setText(phong.getTenPhong());
        holder.tvTrangThaiPhong.setText(phong.getMaTrangThaiPhong());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DSPA=>", "item " + position + " is tapped! -- " + phong.getMaPhong());

                containerData = new Bundle();
                containerData.putString(KEY_MA_PHONG, phong.getMaPhong());
                switchActivity = new Intent(v.getContext(), CapNhatPhongActivity.class);
                switchActivity.putExtras(containerData);
                context.startActivity(switchActivity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPhongs.size();
    }

    // Filter and Search Phongs
    public void searchAndFilter(String query, String itemLoaiPhongSelected, String itemTrangThaiPhongSelected) {
        listPhongs.clear();
        if (query.isEmpty() && itemLoaiPhongSelected.equals(TAT_CA) && itemTrangThaiPhongSelected.equals(TAT_CA)) {
            listPhongs.addAll(listPhongsCopy);
        } else {
            if (!query.isEmpty()) {
                if (!itemLoaiPhongSelected.equals(TAT_CA) && itemTrangThaiPhongSelected.equals(TAT_CA)) {
                    // Search + Spinner LoaiPhong
                    query = query.toLowerCase();
                    for (Phong aPhong : listPhongsCopy) {
                        if (aPhong.getTenPhong().toLowerCase().contains(query) && aPhong.getMaLoaiPhong().trim().equals(itemLoaiPhongSelected.trim())) {
                            listPhongs.add(aPhong);
                        }
                    }
                } else if (itemLoaiPhongSelected.equals(TAT_CA) && !itemTrangThaiPhongSelected.equals(TAT_CA)) {
                    // Search + Spinner Trang thai phong
                    query = query.toLowerCase();
                    for (Phong aPhong : listPhongsCopy) {
                        if (aPhong.getTenPhong().toLowerCase().contains(query) && aPhong.getMaTrangThaiPhong().trim().equals(itemTrangThaiPhongSelected.trim())) {
                            listPhongs.add(aPhong);
                        }
                    }
                } else if (!itemLoaiPhongSelected.equals(TAT_CA) && !itemTrangThaiPhongSelected.equals(TAT_CA)) {
                    // Search + Spinner Trang thai phong + Spinner LoaiPhong
                    query = query.toLowerCase();
                    for (Phong aPhong : listPhongsCopy) {
                        if (aPhong.getTenPhong().toLowerCase().contains(query)
                                && aPhong.getMaTrangThaiPhong().trim().equals(itemTrangThaiPhongSelected.trim())
                                && aPhong.getMaLoaiPhong().trim().equals(itemLoaiPhongSelected.trim())) {
                            listPhongs.add(aPhong);
                        }
                    }
                } else {
                    // Search
                    query = query.toLowerCase();
                    for (Phong aPhong : listPhongsCopy) {
                        if (aPhong.getTenPhong().toLowerCase().contains(query)) {
                            listPhongs.add(aPhong);
                        }
                    }
                }
            } else {
                if (!itemLoaiPhongSelected.equals(TAT_CA) && itemTrangThaiPhongSelected.equals(TAT_CA)) {
                    // Spinner Loai Phong changed
                    for (Phong aPhong : listPhongsCopy) {
                        if (aPhong.getMaLoaiPhong().equals(itemLoaiPhongSelected.trim())) {
                            listPhongs.add(aPhong);
                        }
                    }
                } else if (itemLoaiPhongSelected.equals(TAT_CA) && !itemTrangThaiPhongSelected.equals(TAT_CA)) {
                    // Spinner Trang Thai Phong changed
                    for (Phong aPhong : listPhongsCopy) {
                        if (aPhong.getMaTrangThaiPhong().equals(itemTrangThaiPhongSelected.trim())) {
                            listPhongs.add(aPhong);
                        }
                    }
                } else {
                    // 2 spinner changed
                    for (Phong aPhong : listPhongsCopy) {
                        if (aPhong.getMaTrangThaiPhong().equals(itemTrangThaiPhongSelected.trim()) && aPhong.getMaLoaiPhong().equals(itemLoaiPhongSelected.trim())) {
                            listPhongs.add(aPhong);
                        }
                    }
                }
            }
        }
        notifyDataSetChanged();
    }
}
