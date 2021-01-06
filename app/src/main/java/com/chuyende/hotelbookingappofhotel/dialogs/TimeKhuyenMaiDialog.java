package com.chuyende.hotelbookingappofhotel.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.chuyende.hotelbookingappofhotel.R;
import com.chuyende.hotelbookingappofhotel.validate.ErrorMessage;

import java.time.LocalDate;

public class TimeKhuyenMaiDialog extends DialogFragment {
    private TextView tvTieuDe;
    private DatePicker dpNgayBatDau, dpNgayKetThuc;
    private Button btnThoi, btnThemKhuyenMai;
    private String beginDateOfPromotion;
    private String endDateDateOfPromotion;

    public TimeKhuyenMaiDialog() {
    }

    // Getter and Setter
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
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                Log.d("TKM=>", "Them khuyen mai button khuyen mai is tapped!");

                // Get date ngay bat dau khuyen mai
                int dayBegin = dpNgayBatDau.getDayOfMonth();
                int monthBegin = dpNgayBatDau.getMonth() + 1;
                int yearBegin = dpNgayBatDau.getYear();
                LocalDate localBeginDate = LocalDate.of(yearBegin, monthBegin, dayBegin);

                // Get date ngay ket thuc khuyen mai
                int dayEnd = dpNgayKetThuc.getDayOfMonth();
                int monthEnd = dpNgayKetThuc.getMonth() + 1;
                int yearEnd = dpNgayKetThuc.getYear();
                LocalDate localEndDate = LocalDate.of(yearEnd, monthEnd, dayEnd);

                // Test data
                String ngayBatDau = dayBegin + "/" + monthBegin + "/" + yearBegin;
                String ngayKetThuc = dayEnd + "/" + monthEnd + "/" + yearEnd;
                Log.i("DATE=>", "Ngay bat dau: " + ngayBatDau);
                Log.i("DATE=>", "Ngay ket thuc: " + ngayKetThuc);

                setBeginDateOfPromotion(ngayBatDau);
                setEndDateDateOfPromotion(ngayKetThuc);

                if (!checkBeginDateAndEndDate(localBeginDate, localEndDate).equals("")) {
                    dismiss();
                } else {
                    Toast.makeText(getContext(), ErrorMessage.ERROR_NGAY_THANG_NAM, Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setView(viewDialog);
        return builder.create();
    }

    // Check ngay bat dau va ngay ket thuc hop le
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String checkBeginDateAndEndDate(LocalDate startDate, LocalDate endDate) {
        String result = "";
        String validResults = startDate.getDayOfMonth() + "/" + startDate.getMonthValue() + "/" + startDate.getYear()
                + "-" + endDate.getDayOfMonth() + "/" + endDate.getMonthValue() + "/" + endDate.getYear();
        if (startDate.isBefore(endDate)) {
            result = validResults;
            Log.d("DATE=>", "Ngay hop le: " + result);
        }
        return result;
    }
}
