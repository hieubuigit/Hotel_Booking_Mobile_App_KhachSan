package com.chuyende.hotelbookingappofhotel.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.chuyende.hotelbookingappofhotel.R;

import java.io.IOException;

public class CapNhatTienNghiDialog extends DialogFragment {
    private TextView tvTieuDe;
    private EditText edtMaTienNghi;
    private EditText edtTienNghi;
    private ImageView imvIconThemTienNghi, imvTienNghi;
    private Button btnThoi, btnCapNhat;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder buildDialog = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View viewDialog = inflater.inflate(R.layout.custom_dialog_them_tien_nghi, null);

        // Get all view from layout
        tvTieuDe = viewDialog.findViewById(R.id.tvTieuDe);
        edtMaTienNghi = viewDialog.findViewById(R.id.edtMaTienNghi);
        edtTienNghi = viewDialog.findViewById(R.id.edtTienNghi);
        imvIconThemTienNghi = viewDialog.findViewById(R.id.imvIconThemTienNghi);
        imvTienNghi = viewDialog.findViewById(R.id.imvTienNghi);
        btnThoi = viewDialog.findViewById(R.id.btnThoi);
        btnCapNhat = viewDialog.findViewById(R.id.btnThem);

        tvTieuDe.setText(R.string.title_dialog_them_tien_nghi);
        btnCapNhat.setText(R.string.btn_cap_nhat);

        imvIconThemTienNghi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ADD_ICON=>", "Image icon add tien nghi is tapped!");
                pickImageFromGallery(v);
            }
        });

        btnThoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("BUTTON=>", "Thoi button is tapped!");
                dismiss();
            }
        });

        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("BUTTON=>", "Cap nhat button is tapped! Ma tien nghi: " + edtMaTienNghi.getText().toString().trim() + " -- Tien nghi: "
                        + edtTienNghi.getText().toString().trim());
                dismiss();
            }
        });

        buildDialog.setView(viewDialog);

        return buildDialog.create();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (data.getData() != null) {
                try {
                    Bitmap bitmapIconTienNghi = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), data.getData());
                    imvTienNghi.setImageBitmap(bitmapIconTienNghi);

                    Log.d("BIT=>", bitmapIconTienNghi+"");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void pickImageFromGallery(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }
}
