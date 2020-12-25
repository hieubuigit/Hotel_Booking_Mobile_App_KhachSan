package com.chuyende.hotelbookingappofhotel.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.chuyende.hotelbookingappofhotel.R;
import com.chuyende.hotelbookingappofhotel.activities.CacTienNghiFragment;
import com.chuyende.hotelbookingappofhotel.activities.MainFragment;
import com.chuyende.hotelbookingappofhotel.activities.TatCaPhongFragment;
import com.chuyende.hotelbookingappofhotel.firebase_models.PhongDatabase;
import com.chuyende.hotelbookingappofhotel.interfaces.SuccessNotificationCallback;

import static com.chuyende.hotelbookingappofhotel.activities.CacTienNghiFragment.cacTienNghiFramentIsRunning;
import static com.chuyende.hotelbookingappofhotel.activities.CapNhatPhongActivity.capNhatPhongIsRunning;
import static com.chuyende.hotelbookingappofhotel.activities.CapNhatPhongActivity.maPhong;

public class ThongBaoXoaDialog extends DialogFragment {
    TextView tvThongBao, tvTieuDe;
    Button btnThoi, btnXoa;

    Intent intent;

    PhongDatabase phongDB = new PhongDatabase();

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View viewDialog = inflater.inflate(R.layout.custom_dialog_thong_bao_xoa, null);

        tvTieuDe = viewDialog.findViewById(R.id.tvTieuDe);
        tvThongBao = viewDialog.findViewById(R.id.tvThongBao);
        btnThoi = viewDialog.findViewById(R.id.btnThoi);
        btnXoa = viewDialog.findViewById(R.id.btnXoa);

        tvTieuDe.setText(R.string.title_dialog_thong_bao);

        if (capNhatPhongIsRunning) {
            tvThongBao.setText(R.string.text_thong_bao_xoa_phong);
        }

        if (cacTienNghiFramentIsRunning) {
            tvThongBao.setText(R.string.text_thong_bao_xoa_tien_nghi);
        }

        btnThoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                Log.d("BTN=>", "Button thoi in Dialog thong bao xoa is tapped!");
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("BTN=>", "Button Xoa in Dialog thong bao xoa is tapped!");

                // Delete mot tien nghi
                if (capNhatPhongIsRunning) {
                    if (!maPhong.trim().equals("")) {
                        phongDB.removeARoom(maPhong, new SuccessNotificationCallback() {
                            @Override
                            public void onCallbackSuccessNotification(Boolean isSuccess) {
                                if (isSuccess) {
                                    Log.d("ERR=>", isSuccess.toString());
                                    intent = new Intent(getContext(), MainFragment.class);
                                    startActivity(intent);
                                }
                            }
                        });
                    }
                }

                if (cacTienNghiFramentIsRunning) {
                    // Delete a tien nghi
                    Log.d("REMOVE=>", "Xoa mot tien nghi is tapped!");
                }
            }
        });
        builder.setView(viewDialog);

        return builder.create();
    }
}
