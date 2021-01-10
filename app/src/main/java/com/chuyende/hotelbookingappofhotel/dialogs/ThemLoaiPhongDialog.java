package com.chuyende.hotelbookingappofhotel.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.chuyende.hotelbookingappofhotel.R;
import com.chuyende.hotelbookingappofhotel.activities.ThemPhongActivity;
import com.chuyende.hotelbookingappofhotel.data_models.LoaiPhong;
import com.chuyende.hotelbookingappofhotel.firebase_models.LoaiPhongDatabase;
import com.chuyende.hotelbookingappofhotel.interfaces.SuccessNotificationCallback;
import com.chuyende.hotelbookingappofhotel.validate.ErrorMessage;

public class ThemLoaiPhongDialog extends DialogFragment {
    private TextView tvTieuDe;
    private EditText edtMaLoaiPhong, edtLoaiPhong;
    private Button btnThoi, btnThem;

    LoaiPhongDatabase loaiPhongDatabase = new LoaiPhongDatabase();
    LoaiPhong loaiPhong;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder buildDialogThemLoaiPhong = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View viewDialog = inflater.inflate(R.layout.custom_dialog_them_loai_phong, null);

        // Get all view from layout
        tvTieuDe = viewDialog.findViewById(R.id.tvTieuDe);
        edtMaLoaiPhong = viewDialog.findViewById(R.id.edtMaLoaiPhong);
        edtLoaiPhong = viewDialog.findViewById(R.id.edtLoaiPhong);
        btnThoi = viewDialog.findViewById(R.id.btnThoi);
        btnThem = viewDialog.findViewById(R.id.btnThem);

        tvTieuDe.setText(R.string.title_dialog_them_loai_phong);
        edtMaLoaiPhong.setText(ThemPhongActivity.createRandomAString());
        edtMaLoaiPhong.setFocusable(false);

        btnThoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TLP=>", "Thoi button in dialog them loai phong is tapped! ");
                dismiss();
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtLoaiPhong.getText().toString().trim().equals("")) {
                    Log.d("TLP=>", "Them button in dialog them loai phong is tapped! Ma loai phong: "
                            + edtMaLoaiPhong.getText().toString().trim()
                            + " -- Loai phong: " + edtLoaiPhong.getText().toString().trim());

                    String maLoaiPhong = edtMaLoaiPhong.getText().toString().trim();
                    String loaiPhong = edtLoaiPhong.getText().toString().trim();

                    loaiPhongDatabase.addNewALoaiPhong(new LoaiPhong(maLoaiPhong, loaiPhong), new SuccessNotificationCallback() {
                        @Override
                        public void onCallbackSuccessNotification(Boolean isSuccess) {
                            if (isSuccess) {
                                dismiss();
                            }
                        }
                    });
                } else {
                    edtLoaiPhong.setError(ErrorMessage.ERROR_LOAI_PHONG);
                }
            }
        });

        buildDialogThemLoaiPhong.setView(viewDialog);
        return buildDialogThemLoaiPhong.create();
    }
}
