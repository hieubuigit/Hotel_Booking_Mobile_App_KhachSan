package com.chuyende.hotelbookingappofhotel.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.chuyende.hotelbookingappofhotel.R;
import com.google.type.DateTime;

import java.util.Date;

public class TimeKhuyenMaiDialog extends DialogFragment {
    private TextView tvTieuDe;
    private DatePicker dpNgayBatDau, dpNgayKetThuc;
    private Button btnThoi, btnThemKhuyenMai;
    private String beginDateOfPromotion;
    private String endDateDateOfPromotion;

    public TimeKhuyenMaiDialog() {
    }

    public String getBeginDateOfPromotion() {
        return beginDateOfPromotion;
    }

    public void setBeginDateOfPromotion(String beginDateOfPromotion) {
        this.beginDateOfPromotion = beginDateOfPromotion;
    }

    public String getEndDateDateOfPromotion() {
        return endDateDateOfPromotion;
    }

    public void setEndDateDateOfPromotion(String endDateDateOfPromotion) {
        this.endDateDateOfPromotion = endDateDateOfPromotion;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View viewDialog = inflater.inflate(R.layout.custom_dialog_khuyen_mai, null);

        // Get all view from layout
        tvTieuDe = viewDialog.findViewById(R.id.tvTieuDe);
        dpNgayBatDau = viewDialog.findViewById(R.id.dpNgayBatDau);
        dpNgayKetThuc = viewDialog.findViewById(R.id.dpNgayKetThuc);
        btnThoi = viewDialog.findViewById(R.id.btnThoi);
        btnThemKhuyenMai = viewDialog.findViewById(R.id.btnThemKhuyenMai);

        tvTieuDe.setText(R.string.title_dialog_them_ngay_khuyen_mai);

        //dpNgayBatDau.updateDate(2021, 1 - 1, 5);

        btnThoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TKM=>", "Thoi khuyen mai button khuyen mai is tapped!");
                dismiss();
            }
        });

        btnThemKhuyenMai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TKM=>", "Them khuyen mai button khuyen mai is tapped!");

                // Get date ngay bat dau khuyen mai
                int dayBegin = dpNgayBatDau.getDayOfMonth();
                int monthBegin = dpNgayBatDau.getMonth() + 1;
                int yearBegin = dpNgayBatDau.getYear();
                String ngayBatDau = dayBegin + "/" + monthBegin + "/" + yearBegin;

                // Get date ngay ket thuc khuyen mai
                int dayEnd = dpNgayKetThuc.getDayOfMonth();
                int monthEnd = dpNgayKetThuc.getMonth() + 1;
                int yearEnd = dpNgayKetThuc.getYear();
                String ngayKetThuc = dayEnd + "/" + monthEnd + "/" + yearEnd;

                Log.i("DATE=>", "Ngay bat dau: " + ngayBatDau);
                Log.i("DATE=>", "Ngay ket thuc: " + ngayKetThuc);

                setBeginDateOfPromotion(ngayBatDau);
                setEndDateDateOfPromotion(ngayKetThuc);

                dismiss();
            }
        });

        builder.setView(viewDialog);

        return builder.create();
    }
}
