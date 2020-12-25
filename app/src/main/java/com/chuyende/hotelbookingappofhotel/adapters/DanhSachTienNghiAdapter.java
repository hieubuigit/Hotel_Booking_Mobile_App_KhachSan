package com.chuyende.hotelbookingappofhotel.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.chuyende.hotelbookingappofhotel.R;
import com.chuyende.hotelbookingappofhotel.data_models.TienNghi;
import com.chuyende.hotelbookingappofhotel.dialogs.CapNhatTienNghiDialog;
import com.chuyende.hotelbookingappofhotel.dialogs.ThemTienNghiDialog;
import com.chuyende.hotelbookingappofhotel.dialogs.ThongBaoXoaDialog;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

import java.util.ArrayList;

public class DanhSachTienNghiAdapter extends RecyclerSwipeAdapter<DanhSachTienNghiAdapter.TienNghiHolder> {
    ArrayList<TienNghi> listTienNghi;
    private Context context;
    private Intent intent;

    public DanhSachTienNghiAdapter(ArrayList<TienNghi> listTienNghi, Context context) {
        this.listTienNghi = listTienNghi;
        this.context = context;
    }

    // Create Tien Nghi holder
    public static class TienNghiHolder extends RecyclerView.ViewHolder {
        SwipeLayout swipeLayout;
        private ImageView imvIconTienNghi;
        private TextView tvMaTienNghi, tvTienNghi;
        private Button btnXoaTienNghi;

        public TienNghiHolder(@NonNull View itemView) {
            super(itemView);
            swipeLayout = itemView.findViewById(R.id.swipeLayout);
            imvIconTienNghi = itemView.findViewById(R.id.imvIconTienNghi);
            tvMaTienNghi = itemView.findViewById(R.id.tvMaTienNghi);
            tvTienNghi = itemView.findViewById(R.id.tvTienNghi);
            btnXoaTienNghi = itemView.findViewById(R.id.btnXoaTienNghi);
        }
    }

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

        // Show swipe layout
        tienNghiHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        tienNghiHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, tienNghiHolder.swipeLayout.findViewById(R.id.dragBtnDelete));

        tienNghiHolder.swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DSTNA=>", "Item " + i + " is tapped!");

                // Show dialog Edit tien nghi
                showDialogEditTienNghi();
            }
        });

        tienNghiHolder.btnXoaTienNghi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DSTNA=>", "Detele tien nghi at item " + i + " is tapped!");

                // Show dialog thong bao delete a tien nghi
                showDialogThongBaoXoa();
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

    public void showDialogThongBaoXoa() {
        DialogFragment thongBaoXoaFragment = new ThongBaoXoaDialog();
        thongBaoXoaFragment.show(((AppCompatActivity) context).getSupportFragmentManager(), "THONG_BAO_XOA");
    }

    public void showDialogEditTienNghi() {
        DialogFragment editTienNghiDialog = new CapNhatTienNghiDialog();
        editTienNghiDialog.show(((AppCompatActivity) context).getSupportFragmentManager(), "EDIT_TIEN_NGHI");
    }
}
