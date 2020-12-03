package com.chuyende.hotelbookingappofhotel.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chuyende.hotelbookingappofhotel.R;
import com.chuyende.hotelbookingappofhotel.data_models.TienNghi;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.daimajia.swipe.SwipeLayout;

import java.util.ArrayList;

public class DanhSachTienNghiAdapter extends RecyclerSwipeAdapter<DanhSachTienNghiAdapter.TienNghiHolder> {
    ArrayList<TienNghi> listTienNghi;
    private Context context;
    private Intent intent;

    @Override
    public TienNghiHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_recyclerview_ds_cac_tien_nghi, parent, false);
        return new TienNghiHolder(v);
    }

    @Override
    public void onBindViewHolder(TienNghiHolder tienNghiHolder, int i) {
        TienNghi tienNghi = listTienNghi.get(i);
        tienNghiHolder.imvIconTienNghi.setImageResource(R.drawable.ic_image);
        tienNghiHolder.tvMaTienNghi.setText(tienNghi.getMaTienNghi());
        tienNghiHolder.tvTienNghi.setText(tienNghi.getTienNghi());

        tienNghiHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        tienNghiHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, tienNghiHolder.swipeLayout.findViewById(R.id.dragLeft));

        // Event handling when touch item in RecyclerView
        tienNghiHolder.swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Item " + i , Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listTienNghi.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipeLayout;
    }

    // Create Tien Nghi holder
    public static class TienNghiHolder extends RecyclerView.ViewHolder {
        SwipeLayout swipeLayout;
        private ImageView imvIconTienNghi;
        private TextView tvMaTienNghi, tvTienNghi;
        private TextView tvXoaTienNghi;

        public TienNghiHolder(@NonNull View itemView) {
            super(itemView);
            swipeLayout = itemView.findViewById(R.id.swipeLayout);
            imvIconTienNghi = itemView.findViewById(R.id.imvIconTienNghi);
            tvMaTienNghi = itemView.findViewById(R.id.tvMaTienNghi);
            tvTienNghi = itemView.findViewById(R.id.tvTienNghi);
            tvXoaTienNghi = itemView.findViewById(R.id.tvXoaTienNghi);
        }
    }
}
