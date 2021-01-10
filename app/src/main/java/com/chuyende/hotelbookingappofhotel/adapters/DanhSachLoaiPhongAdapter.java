package com.chuyende.hotelbookingappofhotel.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.chuyende.hotelbookingappofhotel.R;
import com.chuyende.hotelbookingappofhotel.data_models.LoaiPhong;
import com.chuyende.hotelbookingappofhotel.dialogs.CapNhatLoaiPhongDialog;
import com.chuyende.hotelbookingappofhotel.dialogs.ThongBaoXoaDialog;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

import java.util.ArrayList;

public class DanhSachLoaiPhongAdapter extends RecyclerSwipeAdapter<DanhSachLoaiPhongAdapter.LoaiPhongHolder> {
    ArrayList<LoaiPhong> listLoaiPhong = new ArrayList<LoaiPhong>();
    Context context;

    public DanhSachLoaiPhongAdapter() {
    }

    public DanhSachLoaiPhongAdapter(ArrayList<LoaiPhong> listLoaiPhong, Context context) {
        this.listLoaiPhong = listLoaiPhong;
        this.context = context;
    }

    public static class LoaiPhongHolder extends RecyclerView.ViewHolder {
        SwipeLayout swipeLayout;
        TextView tvMaLoaiPhong, tvLoaiPhong;
        Button btnXoaLoaiPhong;

        public LoaiPhongHolder(@NonNull View itemView) {
            super(itemView);
            swipeLayout = itemView.findViewById(R.id.swipeLayoutLoaiPhong);
            tvMaLoaiPhong = itemView.findViewById(R.id.tvMaLoaiPhong);
            tvLoaiPhong = itemView.findViewById(R.id.tvLoaiPhong);
            btnXoaLoaiPhong = itemView.findViewById(R.id.btnXoaLoaiPhong);
        }
    }

    @Override
    public LoaiPhongHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_recyclerview_ds_loai_phong, parent, false);
        return new LoaiPhongHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LoaiPhongHolder loaiPhongHolder, int i) {
        LoaiPhong loaiPhong = listLoaiPhong.get(i);
        loaiPhongHolder.tvMaLoaiPhong.setText(loaiPhong.getMaLoaiPhong().trim());
        loaiPhongHolder.tvLoaiPhong.setText(loaiPhong.getLoaiPhong().trim());

        // Show swipe layout
        loaiPhongHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        loaiPhongHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, loaiPhongHolder.swipeLayout.findViewById(R.id.dragBtnDelete));

        // Event handling when touch item in RecyclerView
        loaiPhongHolder.swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("LPA=>", "Item loai phong = " + i + "is tapped! ");

                DialogFragment dialogCapNhatLoaiPhong = new CapNhatLoaiPhongDialog(loaiPhong);
                dialogCapNhatLoaiPhong.show(((AppCompatActivity) context).getSupportFragmentManager(), "CAP_NHAT_LOAI_PHONG");
            }
        });

        loaiPhongHolder.btnXoaLoaiPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("LPA=>", "Xoa button loai phong at item: " + i + "is tapped! ");

                DialogFragment dialogXoaLoaiPhong = new ThongBaoXoaDialog(loaiPhong.getMaLoaiPhong());
                dialogXoaLoaiPhong.show(((AppCompatActivity) context).getSupportFragmentManager(), "XOA_LOAI_PHONG");
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
}
