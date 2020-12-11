package com.chuyende.hotelbookingappofhotel.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.chuyende.hotelbookingappofhotel.R;
import com.chuyende.hotelbookingappofhotel.data_models.ThongTinDat;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class DanhSachDatAdapter extends FirestoreRecyclerAdapter<ThongTinDat, DanhSachDatAdapter.DanhSachDatHolder> {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static String TAG = "TAG";
    public static String TENPHONG_KEY = "tenPhong";
    public static String MAPHONG_KEY = "maPhong";
    public static String TENND_KEY = "tenNguoiDung";
    public static String MAND_KEY = "maNguoiDung";

    OnItemClickListener listener;

    public DanhSachDatAdapter(@NonNull FirestoreRecyclerOptions<ThongTinDat> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull DanhSachDatAdapter.DanhSachDatHolder holder, int position, @NonNull ThongTinDat model) {
        db.collection("Phong").whereEqualTo(MAPHONG_KEY, model.getMaPhong()).get().
                addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        holder.tvTenPhong.setText(document.getData().get(TENPHONG_KEY).toString());
                    }
                } else {
                    Log.d(TAG, "Error" + task.getException());
                }
            }
        });
        db.collection("NguoiDung").whereEqualTo(MAND_KEY, model.getMaNguoiDung())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        holder.tvTenNguoiDat.setText(document.getData().get(TENND_KEY).toString());
                    }
                } else {
                    Log.d(TAG, "Error" + task.getException());
                }
            }
        });
        holder.tvNgayDat.setText(model.getNgayDatPhong());
    }

    @NonNull
    @Override
    public DanhSachDatAdapter.DanhSachDatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_listview, parent, false);
        return new DanhSachDatHolder(view);
    }

    public class DanhSachDatHolder extends RecyclerView.ViewHolder {
        TextView tvTenPhong;
        TextView tvTenNguoiDat;
        TextView tvNgayDat;

        public DanhSachDatHolder(@NonNull View itemView) {
            super(itemView);
            tvTenPhong = itemView.findViewById(R.id.tvTenPhong);
            tvTenNguoiDat = itemView.findViewById(R.id.tvTenNguoiDat);
            tvNgayDat = itemView.findViewById(R.id.tvNgayDat);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot snapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
