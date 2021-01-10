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
import com.chuyende.hotelbookingappofhotel.activities.ThemPhongActivity;
import com.chuyende.hotelbookingappofhotel.data_models.Phong;
import com.chuyende.hotelbookingappofhotel.data_models.TienNghi;
import com.chuyende.hotelbookingappofhotel.firebase_models.PhongDatabase;
import com.chuyende.hotelbookingappofhotel.firebase_models.TienNghiDatabase;
import com.chuyende.hotelbookingappofhotel.interfaces.SuccessNotificationCallback;
import com.chuyende.hotelbookingappofhotel.interfaces.URIDownloadAvatarCallback;
import com.chuyende.hotelbookingappofhotel.validate.ErrorMessage;

import java.io.IOException;

import static com.chuyende.hotelbookingappofhotel.activities.TatCaPhongFragment.MA_KS_LOGIN;
import static com.chuyende.hotelbookingappofhotel.activities.ThemPhongActivity.createRandomAString;

public class ThemTienNghiDialog extends DialogFragment {
    private TextView tvTieuDe;
    private EditText edtMaTienNghi;
    private EditText edtTienNghi;
    private ImageView imvIconThemTienNghi, imvTienNghi;
    private Button btnThoi, btnThem;

    TienNghiDatabase tienNghiDatabase = new TienNghiDatabase();

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
        btnThem = viewDialog.findViewById(R.id.btnThem);

        tvTieuDe.setText(R.string.title_dialog_them_tien_nghi);
        edtMaTienNghi.setText(createRandomAString());
        edtMaTienNghi.setFocusable(false);

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

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtTienNghi.getText().toString().trim().equals("")) {
                    String maTienNghi = edtMaTienNghi.getText().toString().trim();
                    String tienNghi = edtTienNghi.getText().toString().trim();

                    tienNghiDatabase.addIconTienNghi(imvTienNghi, maTienNghi, new URIDownloadAvatarCallback() {
                        @Override
                        public void onCallbackUriDownload(String uri) {
                            TienNghi theTienNghi = new TienNghi(maTienNghi, uri, tienNghi);

                            tienNghiDatabase.addANewTienNghi(theTienNghi, new SuccessNotificationCallback() {
                                @Override
                                public void onCallbackSuccessNotification(Boolean isSuccess) {
                                    if (isSuccess) {
                                        Log.d("TTND=>", "Add new tien nghi is successfully!");
                                    }
                                }
                            });
                        }
                    });

                    // Test data form UI
                    Log.d("BUTTON=>", "Them button is tapped! Ma tien nghi: " + edtMaTienNghi
                            + " -- Tien nghi: " + tienNghi);

                } else {
                    if (edtTienNghi.getText().toString().trim().equals("")) {
                        edtTienNghi.setError(ErrorMessage.ERROR_TIEN_NGHI);
                    }
                }

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

                    Log.d("BIT=>", bitmapIconTienNghi + "");
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

