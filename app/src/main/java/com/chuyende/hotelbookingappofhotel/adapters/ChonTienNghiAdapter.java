package com.chuyende.hotelbookingappofhotel.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chuyende.hotelbookingappofhotel.R;
import com.chuyende.hotelbookingappofhotel.data_models.TienNghi;

import java.util.ArrayList;

import static com.chuyende.hotelbookingappofhotel.activities.CapNhatPhongActivity.maTienNghis;

public class ChonTienNghiAdapter extends RecyclerView.Adapter<ChonTienNghiAdapter.ChonTienNghiHolder> {
    ArrayList<TienNghi> listTienNghi = new ArrayList<TienNghi>();
    Context context;

    public ChonTienNghiAdapter(ArrayList<TienNghi> listTienNghi, Context context) {
        this.listTienNghi = listTienNghi;
        this.context = context;
    }

    public static class ChonTienNghiHolder extends RecyclerView.ViewHolder {
        CheckBox ckbChonTienNghi;
        TextView tvTienNghi;

        public TextView getTvTienNghi() {
            return tvTienNghi;
        }

        public void setTvTienNghi(TextView tvTienNghi) {
            this.tvTienNghi = tvTienNghi;
        }

        public CheckBox getCkbChonTienNghi() {
            return ckbChonTienNghi;
        }

        public void setCkbChonTienNghi(CheckBox ckbChonTienNghi) {
            this.ckbChonTienNghi = ckbChonTienNghi;
        }

        public ChonTienNghiHolder(@NonNull View itemView) {
            super(itemView);
            ckbChonTienNghi = itemView.findViewById(R.id.ckbChonTienNghi);
            tvTienNghi = itemView.findViewById(R.id.tvTienNghi);
        }
    }

    @NonNull
    @Override
    public ChonTienNghiHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_recyclerview_chon_tien_nghi, parent, false);
        return new ChonTienNghiHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChonTienNghiHolder holder, int position) {
        TienNghi tienNghi = listTienNghi.get(position);
        holder.tvTienNghi.setText(tienNghi.getTienNghi());
        //holder.ckbChonTienNghi.setChecked(tienNghi.getCheckTN());

        if (maTienNghis != null) {
            for (String aMaTienNghi : maTienNghis) {
                if (aMaTienNghi.equals(tienNghi.getMaTienNghi())) {
                    holder.ckbChonTienNghi.setChecked(true);
                }
            }
        } else {
            holder.ckbChonTienNghi.setChecked(tienNghi.getCheckTN());
        }

        holder.ckbChonTienNghi.setTag(position);
        holder.ckbChonTienNghi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CTN=>", "Checkbox position " + position + " is tapped!");

                int pos = (int) holder.ckbChonTienNghi.getTag();
                if (!holder.ckbChonTienNghi.isChecked()) {
                    if (!listTienNghi.get(pos).getCheckTN()) {
                        listTienNghi.get(pos).setCheckTN(true);
                    } else {
                        listTienNghi.get(pos).setCheckTN(false);
                    }
                } else {
                    if (listTienNghi.get(pos).getCheckTN()) {
                        listTienNghi.get(pos).setCheckTN(false);
                    } else {
                        listTienNghi.get(pos).setCheckTN(true);
                    }
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Item Tien nghi " + position + " is tapped!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listTienNghi.size();
    }
}
