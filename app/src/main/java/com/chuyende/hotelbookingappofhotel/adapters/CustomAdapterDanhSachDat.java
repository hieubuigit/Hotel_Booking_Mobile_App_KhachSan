package com.chuyende.hotelbookingappofhotel.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chuyende.hotelbookingappofhotel.R;
import com.chuyende.hotelbookingappofhotel.data_models.ThongTinDat;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CustomAdapterDanhSachDat extends ArrayAdapter {
    Context context;
    int resource;
    ArrayList<ThongTinDat> data;
    ArrayList<ThongTinDat> data_DS;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static String TAG = "tag";
    String tenPhong;
    String tenNguoiDung;

    public CustomAdapterDanhSachDat(Context context, int resource, ArrayList<ThongTinDat> data) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        this.data = data;
        this.data_DS = new ArrayList<ThongTinDat>();
        this.data_DS.addAll(data);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    private static class Holder {
        TextView tvTenPhong;
        TextView tvTenNguoiDat;
        TextView tvNgayDat;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        Holder holder = null;
        if (view == null) {
            holder = new Holder();
            view = LayoutInflater.from(context).inflate(resource, null);
            holder.tvTenPhong = view.findViewById(R.id.tvTenPhong);
            holder.tvTenNguoiDat = view.findViewById(R.id.tvTenNguoiDat);
            holder.tvNgayDat = view.findViewById(R.id.tvNgayDat);
            view.setTag(holder);
        }
        else {
            holder = (Holder) view.getTag();
        }
        final ThongTinDat thongTinDat = data.get(position);

        db.collection("Phong")
                .whereEqualTo("maPhong", thongTinDat.getMaPhong())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                tenPhong = document.getData().get("tenPhong").toString();
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        db.collection("NguoiDung")
                .whereEqualTo("tmaNguoiDung", thongTinDat.getMaNguoiDung())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                tenNguoiDung = document.getData().get("tenNguoiDung").toString();
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        holder.tvTenPhong.setText(tenPhong);
        holder.tvTenNguoiDat.setText(tenNguoiDung);
        holder.tvNgayDat.setText(thongTinDat.getNgayDatPhong());
        return view;
    }
}
