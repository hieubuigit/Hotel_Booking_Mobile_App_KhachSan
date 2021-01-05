package com.chuyende.hotelbookingappofhotel.adapters;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.chuyende.hotelbookingappofhotel.firebase_models.DBDanhSachDat;
import com.chuyende.hotelbookingappofhotel.firebase_models.DBManHinhDangNhap;
import com.chuyende.hotelbookingappofhotel.interfaces.DanhSachThanhToanCallBack;
import com.chuyende.hotelbookingappofhotel.interfaces.DataCallBack;
import com.chuyende.hotelbookingappofhotel.R;
import com.chuyende.hotelbookingappofhotel.data_models.ThongTinThanhToan;
import com.chuyende.hotelbookingappofhotel.firebase_models.DBDanhSachThanhToan;

import java.util.ArrayList;
import java.util.List;

public class DanhSachThanhToanAdapter extends RecyclerView.Adapter<DanhSachThanhToanAdapter.SwipeAdapterVH> implements Filterable {
    private List<ThongTinThanhToan> listThongTinThanhToan = new ArrayList<>();
    private List<ThongTinThanhToan> getListThongTinThanhToan = new ArrayList<>();
    private List<String> listTen = new ArrayList<>();
    private List<ThongTinThanhToan> resultData = new ArrayList<>();
    private Context context;
    private SelectedItem selectedItem;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    private DBDanhSachThanhToan dbDanhSachThanhToan = new DBDanhSachThanhToan();
    private DBDanhSachDat dbDanhSachDat = new DBDanhSachDat();
    private DBManHinhDangNhap dbManHinhDangNhap = new DBManHinhDangNhap();
    public static String TRANGTHAIHOANTTATTHANHTOAN = "true";
    public static String TAG = "DanhSachThanhToanAdpater";

    public DanhSachThanhToanAdapter(List<ThongTinThanhToan> listThongTinThanhToan, Context context, SelectedItem selectedItem) {
        this.listThongTinThanhToan = listThongTinThanhToan;
        this.getListThongTinThanhToan = listThongTinThanhToan;
        this.context = context;
        this.selectedItem = selectedItem;
    }

    @NonNull
    @Override
    public DanhSachThanhToanAdapter.SwipeAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_swipe_item, parent, false);
        return new SwipeAdapterVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DanhSachThanhToanAdapter.SwipeAdapterVH holder, int position) {
        viewBinderHelper.setOpenOnlyOne(true);
        viewBinderHelper.bind(holder.swipeRevealLayout, String.valueOf(listThongTinThanhToan.get(position).getMaThanhToan()));
        viewBinderHelper.closeLayout(String.valueOf(listThongTinThanhToan.get(position).getMaThanhToan()));
        holder.blindData(listThongTinThanhToan.get(position));
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();

                if(constraint == null | constraint.length() == 0) {
                    filterResults.count = getListThongTinThanhToan.size();
                    filterResults.values = getListThongTinThanhToan;
                } else {
                    String searchFilter = constraint.toString().toLowerCase();
                    List<String> saveTenNguoiDung = new ArrayList<>();

                    //So sanh ten nguoi dung lay tu recyclerview voi ki tu duoc nhap vao searchview
                    for (int i = 0; i < listTen.size(); i++) {
                        if (listTen.get(i).toLowerCase().contains(searchFilter)){
                            saveTenNguoiDung.add(listTen.get(i));
                        }
                    }

                    //Lay ra danh sach cac thongTinThanhToan theo ki tu duoc chuyen vao
                    try {
                        dbManHinhDangNhap.getTenTaiKhoanKhachSan(new DataCallBack() {
                            @Override
                            public void dataCallBack(String info) {
                                dbDanhSachThanhToan.thongTinThanhToanFilter(info, saveTenNguoiDung, new DanhSachThanhToanCallBack() {
                                    @Override
                                    public void danhSachThanhToanCallBack(ArrayList<ThongTinThanhToan> thanhToanList) {
                                        resultData.clear();
                                        for (ThongTinThanhToan thongTinThanhToan : thanhToanList) {
                                            resultData.add(thongTinThanhToan);
                                        }
                                    }
                                });
                            }
                        });
                    }catch (Exception e) {
                        Log.d(TAG, "Lỗi: " + e);
                    }
                    filterResults.count = resultData.size();
                    filterResults.values = resultData;
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listThongTinThanhToan = (List<ThongTinThanhToan>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }

    public interface SelectedItem{
        void selectedItem(ThongTinThanhToan thongTinThanhToan);
    }

    @Override
    public int getItemCount() {
        return listThongTinThanhToan.size();
    }

    public class SwipeAdapterVH extends RecyclerView.ViewHolder {
        private TextView tvTenPhong;
        private TextView tvTenNguoiDat;
        private TextView tvNgayThanhToan;
        private Button btnHoanTien;
        private SwipeRevealLayout swipeRevealLayout;

        public SwipeAdapterVH(@NonNull View itemView) {
            super(itemView);
            tvTenPhong = itemView.findViewById(R.id.tvTenPhong);
            tvTenNguoiDat = itemView.findViewById(R.id.tvTenNguoiDat);
            tvNgayThanhToan = itemView.findViewById(R.id.tvNgayDat);
            btnHoanTien = itemView.findViewById(R.id.btnThanhToan);
            swipeRevealLayout = itemView.findViewById(R.id.SwipeLayout);

            btnHoanTien.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ThongTinThanhToan thongTinThanhToan = listThongTinThanhToan.get(getAdapterPosition());
                    thongTinThanhToan.setTrangThaiHoanTatThanhToan(TRANGTHAIHOANTTATTHANHTOAN);
                    thongTinThanhToan.setSoTienThanhToanTruoc(thongTinThanhToan.getTongThanhToan());
                    try {
                        dbDanhSachThanhToan.thanhToanDu(thongTinThanhToan);
                    }catch (Exception e) {
                        Log.d(TAG, "Lỗi: " + e);
                    }

                    //set Toast message
                    View view = LayoutInflater.from(context).inflate(R.layout.custom_toast_message_success,
                            (ViewGroup)itemView.findViewById(R.id.ToastMessage_layout));
                    TextView textView = view.findViewById(R.id.tvTextToast);
                    textView.setText("Thanh toán thành công");
                    final Toast toast = new Toast(context);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view);
                    toast.show();
                }
            });

            tvTenNguoiDat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedItem.selectedItem(listThongTinThanhToan.get(getAdapterPosition()));
                }
            });

            tvTenPhong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedItem.selectedItem(listThongTinThanhToan.get(getAdapterPosition()));
                }
            });

            tvNgayThanhToan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedItem.selectedItem(listThongTinThanhToan.get(getAdapterPosition()));
                }
            });
        }

        void blindData(ThongTinThanhToan thongTinThanhToan) {
            try {
                dbDanhSachDat.getTenPhong(thongTinThanhToan.getMaPhong(), new DataCallBack() {
                    @Override
                    public void dataCallBack(String info) {
                        tvTenPhong.setText(info);
                    }
                });
            }catch (Exception e) {
                Log.d(TAG, "Lỗi: " + e);
            }

            try {
                dbDanhSachDat.getTenNguoiDung(thongTinThanhToan.getMaNguoiDung(), new DataCallBack() {
                    @Override
                    public void dataCallBack(String info) {
                        tvTenNguoiDat.setText(info);
                        listTen.add(info);
                    }
                });
            }catch (Exception e) {
                Log.d(TAG, "Lỗi: " + e);
            }

            tvNgayThanhToan.setText(thongTinThanhToan.getNgayThanhToan());
        }
    }
}
