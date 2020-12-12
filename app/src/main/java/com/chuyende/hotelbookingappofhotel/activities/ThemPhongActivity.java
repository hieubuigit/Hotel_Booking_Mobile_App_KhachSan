package com.chuyende.hotelbookingappofhotel.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import com.chuyende.hotelbookingappofhotel.R;
import com.chuyende.hotelbookingappofhotel.data_models.LoaiPhong;
import com.chuyende.hotelbookingappofhotel.data_models.Phong;
import com.chuyende.hotelbookingappofhotel.data_models.TienNghi;
import com.chuyende.hotelbookingappofhotel.data_models.TinhThanhPho;
import com.chuyende.hotelbookingappofhotel.data_models.TrangThaiPhong;
import com.chuyende.hotelbookingappofhotel.dialogs.BoSuuTapDialog;
import com.chuyende.hotelbookingappofhotel.dialogs.ChonTienNghiDialog;
import com.chuyende.hotelbookingappofhotel.firebase_models.LoaiPhongDatabase;
import com.chuyende.hotelbookingappofhotel.firebase_models.PhongDatabase;
import com.chuyende.hotelbookingappofhotel.firebase_models.TienNghiDatabase;
import com.chuyende.hotelbookingappofhotel.firebase_models.TinhThanhPhoDatabase;
import com.chuyende.hotelbookingappofhotel.firebase_models.TrangThaiPhongDatabase;
import com.chuyende.hotelbookingappofhotel.interfaces.LoaiPhongCallback;
import com.chuyende.hotelbookingappofhotel.interfaces.SuccessNotificationCallback;
import com.chuyende.hotelbookingappofhotel.interfaces.TinhThanhPhoCallback;
import com.chuyende.hotelbookingappofhotel.interfaces.TrangThaiPhongCallback;
import com.chuyende.hotelbookingappofhotel.interfaces.URIDownloadAvatarCallback;
import com.chuyende.hotelbookingappofhotel.validate.ErrorMessage;
import com.google.protobuf.DoubleValue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ThemPhongActivity extends AppCompatActivity {
    public static final String MA_KS_LOGIN = "KS02";

    EditText edtMaPhong, edtTenPhong, edtGiaThue, edtSoKhach, edtMoTaPhong, edtDiaChi, edtKinhDo, edtViDo, edtPhanTramGiamGia;
    Spinner spnTrangThaiPhong, spnLoaiPhong, spnTinhThanhPho;
    Button btnChonTienNghi, btnThemPhongMoi;
    TextView tvAddAnhDaiDien, tvAddBoSuuTap, tvBoSuuTap;
    ImageView imvAnhDaiDien;

    public static ArrayList<Bitmap> listBitmap;

    TrangThaiPhongDatabase trangThaiPhongDB;
    LoaiPhongDatabase loaiPhongDB;
    public static TienNghiDatabase tienNghiDB;
    TinhThanhPhoDatabase tinhThanhPhoDB;
    PhongDatabase phongDB;

    @Override
    protected void onStart() {
        super.onStart();

        trangThaiPhongDB.readAllDataTrangThaiPhong(new TrangThaiPhongCallback() {
            @Override
            public void onDataCallbackTrangThaiPhong(List<TrangThaiPhong> listTrangThaiPhongs) {
                //Log.d("TPM=>", "Size trang thai phong = " + listTrangThaiPhongs.size());

                ArrayList<String> listOnlyTrangThaiPhong = new ArrayList<String>();
                for (TrangThaiPhong item : listTrangThaiPhongs) {
                    listOnlyTrangThaiPhong.add(item.getTrangThaiPhong());
                }

                ArrayAdapter<String> trangThaiPhongAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1
                        , listOnlyTrangThaiPhong);
                spnTrangThaiPhong.setAdapter(trangThaiPhongAdapter);
                trangThaiPhongAdapter.notifyDataSetChanged();
            }
        });

        loaiPhongDB.readAllDataLoaiPhong(new LoaiPhongCallback() {
            @Override
            public void onDataCallbackLoaiPhong(List<LoaiPhong> listLoaiPhongs) {
                Log.d("TPM=>", "Size loai phong = " + listLoaiPhongs.size());

                ArrayList<String> listOnlyLoaiPhongs = new ArrayList<String>();
                for (LoaiPhong item : listLoaiPhongs) {
                    listOnlyLoaiPhongs.add(item.getLoaiPhong());
                }

                ArrayAdapter<String> loaiPhongAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1
                        , listOnlyLoaiPhongs);
                spnLoaiPhong.setAdapter(loaiPhongAdapter);
                loaiPhongAdapter.notifyDataSetChanged();
            }
        });

        tinhThanhPhoDB.readAllDataTinhThanhPho(new TinhThanhPhoCallback() {
            @Override
            public void onDataCallbackTinhThanhPho(List<TinhThanhPho> listTinhThanhPhos) {
                Log.d("TPM=>", "Size loai phong = " + listTinhThanhPhos.size());

                ArrayList<String> listOnlyTinhThanhPho = new ArrayList<String>();
                for (TinhThanhPho item : listTinhThanhPhos) {
                    listOnlyTinhThanhPho.add(item.getTinhThanhPho());
                }

                ArrayAdapter<String> listTinhThanhPhoAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1
                        , listOnlyTinhThanhPho);
                spnTinhThanhPho.setAdapter(listTinhThanhPhoAdapter);
                listTinhThanhPhoAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.them_phong_layout);

        listBitmap = new ArrayList<Bitmap>();

        trangThaiPhongDB = new TrangThaiPhongDatabase();
        loaiPhongDB = new LoaiPhongDatabase();
        tienNghiDB = new TienNghiDatabase();
        tinhThanhPhoDB = new TinhThanhPhoDatabase();
        phongDB = new PhongDatabase();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.title_toolbar_them_phong);

        // Get all views from layout
        edtMaPhong = findViewById(R.id.edtMaPhong);
        edtTenPhong = findViewById(R.id.edtTenPhong);
        edtGiaThue = findViewById(R.id.edtGiaThue);
        edtSoKhach = findViewById(R.id.edtSoKhach);
        edtMoTaPhong = findViewById(R.id.edtMoTaPhong);
        edtDiaChi = findViewById(R.id.edtDiaChi);
        edtKinhDo = findViewById(R.id.edtKinhDo);
        edtViDo = findViewById(R.id.edtViDo);
        edtPhanTramGiamGia = findViewById(R.id.edtPhanTramGiamGia);
        spnTrangThaiPhong = findViewById(R.id.spnTrangThaiPhong);
        spnLoaiPhong = findViewById(R.id.spnLoaiPhong);
        spnTinhThanhPho = findViewById(R.id.spnTinhThanhPho);
        btnChonTienNghi = findViewById(R.id.btnChonTienNghi);
        btnThemPhongMoi = findViewById(R.id.btnThemPhongMoi);
        tvAddAnhDaiDien = findViewById(R.id.tvAddAnhDaiDien);
        tvAddBoSuuTap = findViewById(R.id.tvAddBoSuuTap);
        tvBoSuuTap = findViewById(R.id.tvBoSuuTap);
        imvAnhDaiDien = findViewById(R.id.imvAnhDaiDien);

        tvAddAnhDaiDien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(ThemPhongActivity.this, "Icon anh dai dien is tapped!", Toast.LENGTH_SHORT).show();
                pickImageFromGallery(v);
            }
        });

        tvAddBoSuuTap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(ThemPhongActivity.this, "Icon Add bo suu tap is tapped!", Toast.LENGTH_SHORT).show();
                pickMultiImagesFromGallery(v);
            }
        });

        tvBoSuuTap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(ThemPhongActivity.this, "Bo suu tap is tapped!", Toast.LENGTH_SHORT).show();
                showBoSuuTapDialog();
            }
        });

        btnChonTienNghi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChonTienNghiDialog(); // Show dialog Chon Tien Nghi
                //Toast.makeText(ThemPhongActivity.this, "Cac tien nghi button is tapped!", Toast.LENGTH_SHORT).show();
            }
        });

        String maPhong = MA_KS_LOGIN + createRandomAString();
        edtMaPhong.setText(maPhong);
        edtMaPhong.setFocusable(false);
        btnThemPhongMoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(ThemPhongActivity.this, "Them phong moi button is tapped!", Toast.LENGTH_SHORT).show();

                if (!edtTenPhong.getText().toString().trim().equals("")
                        || !edtGiaThue.getText().toString().trim().equals("")
                        || !edtSoKhach.getText().toString().trim().equals("")
                        || !edtMoTaPhong.getText().toString().trim().equals("")
                        || !edtDiaChi.getText().toString().trim().equals("")
                        || !edtKinhDo.getText().toString().trim().equals("")
                        || !edtViDo.getText().toString().trim().equals("")
                        || !edtPhanTramGiamGia.getText().toString().trim().equals("")) {

                    phongDB.addAvatarOfTheRoom(imvAnhDaiDien, maPhong, new URIDownloadAvatarCallback() {
                        @Override
                        public void onCallbackUriDownload(String uri) {
                            String tenPhong = edtTenPhong.getText().toString().trim();
                            String trangThaiPhong = spnTrangThaiPhong.getSelectedItem().toString().trim();
                            Double giaThue = Double.parseDouble(edtGiaThue.getText().toString().trim());
                            String maLoaiPhong = spnLoaiPhong.getSelectedItem().toString().trim();
                            int soKhach = Integer.parseInt(edtSoKhach.getText().toString().trim());
                            String maTienNghi = ChonTienNghiDialog.cacMaTienNghi;
                            String moTaPhong = edtMoTaPhong.getText().toString().trim();
                            String diaChiPhong = edtDiaChi.getText().toString().trim();
                            String tinhThanhPho = spnTinhThanhPho.getSelectedItem().toString().trim();
                            Double kinhDo = Double.parseDouble(edtKinhDo.getText().toString().trim());
                            Double viDo = Double.parseDouble(edtViDo.getText().toString().trim());
                            int phanTramGiamGia = Integer.parseInt(edtPhanTramGiamGia.getText().toString().trim());
                            String anhDaiDien = uri;
                            String boSuuTap = phongDB.addPhotoGalleryOfRoom(listBitmap, maPhong);
                            String maKhachSan = MA_KS_LOGIN;
                            Phong phong = new Phong(maPhong, tenPhong, trangThaiPhong, giaThue, maLoaiPhong, soKhach, maTienNghi, moTaPhong, tinhThanhPho
                                    , diaChiPhong, kinhDo, viDo, phanTramGiamGia, anhDaiDien, boSuuTap, maKhachSan);
                            //Log.d("RS=>", phong.toString());

                            phongDB.addANewRoom(phong, new SuccessNotificationCallback() {
                                @Override
                                public void onCallbackSuccessNotification(Boolean isSuccess) {
                                    if (isSuccess) {
                                        edtTenPhong.setText("");
                                        spnTrangThaiPhong.setSelection(0);
                                        edtGiaThue.setText("");
                                        spnLoaiPhong.setSelection(0);
                                        edtSoKhach.setText("");
                                        ChonTienNghiDialog.cacMaTienNghi = "";
                                        edtMoTaPhong.setText("");
                                        edtMaPhong.setText("");
                                        edtDiaChi.setText("");
                                        spnTinhThanhPho.setSelection(0);
                                        edtKinhDo.setText("");
                                        edtViDo.setText("");
                                        edtPhanTramGiamGia.setText("");
                                        imvAnhDaiDien.setImageResource(android.R.color.transparent);
                                        listBitmap = null;
                                    } else {
                                        Log.d("P=>", "Add a room is failed!");
                                    }
                                }
                            });
                        }
                    });
                } else {
                    if (edtTenPhong.getText().toString().trim().equals("")) {
                        edtTenPhong.setError(ErrorMessage.ERROR_TEN_PHONG_EMPTY);
                    } else if (edtGiaThue.getText().toString().trim().equals("")) {
                        edtGiaThue.setError(ErrorMessage.ERROR_GIA_THUE_EMPTY);
                    } else if (edtSoKhach.getText().toString().trim().equals("")) {
                        edtSoKhach.setError(ErrorMessage.ERROR_SO_KHACH_EMPTY);
                    } else if (edtMoTaPhong.getText().toString().trim().equals("")) {
                        edtMoTaPhong.setError(ErrorMessage.ERROR_MO_TA_PHONG_EMPTY);
                    } else if (edtDiaChi.getText().toString().trim().equals("")) {
                        edtDiaChi.setError(ErrorMessage.ERROR_DIA_CHI_PHONG_EMPTY);
                    } else if (edtKinhDo.getText().toString().trim().equals("")) {
                        edtKinhDo.setError(ErrorMessage.ERROR_KINH_DO_EMPTY);
                    } else if (edtViDo.getText().toString().trim().equals("")) {
                        edtViDo.setError(ErrorMessage.ERROR_VI_DO_EMPTY);
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            if (data.getClipData() != null) {
                int count = data.getClipData().getItemCount();
                for (int i = 0; i < count; i++) {
                    Uri uriImage = data.getClipData().getItemAt(i).getUri();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uriImage);
                        listBitmap.add(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (data.getData() != null) {
                try {
                    Uri uriAImage = data.getData();
                    Bitmap image = BitmapFactory.decodeStream(getContentResolver().openInputStream(uriAImage));
                    imvAnhDaiDien.setImageBitmap(image);
                } catch (Exception e) {
                    Log.d("ERR=>", e.getMessage());
                }
            }

            // Test database on list images
            for (Bitmap bitmap : listBitmap) {
                Log.d("=>", bitmap.toString());
            }
        }
    }

    public void pickMultiImagesFromGallery(View v) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent, "Images: "), 1);
    }

    public void pickImageFromGallery(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    public void showChonTienNghiDialog() {
        DialogFragment fragment = new ChonTienNghiDialog();
        fragment.show(getSupportFragmentManager(), "ChonTienNghi");
    }

    public void showBoSuuTapDialog() {
        DialogFragment fragment = new BoSuuTapDialog();
        fragment.show(getSupportFragmentManager(), "BoSuuTap");
    }

    public String createRandomAString() {
        String candidateChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        Random random = new Random();
        StringBuilder rndString = new StringBuilder();
        while (rndString.length() < 20) {
            int index = (int) (random.nextFloat() * candidateChars.length());
            rndString.append(candidateChars.charAt(index));
        }
        return rndString.toString();
    }

    public void formatCurrencyToVietnamDong() {
        // Code here
    }
}