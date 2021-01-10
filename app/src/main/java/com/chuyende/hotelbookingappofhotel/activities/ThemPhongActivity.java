package com.chuyende.hotelbookingappofhotel.activities;

import android.content.Intent;
import android.graphics.Bitmap;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.chuyende.hotelbookingappofhotel.R;
import com.chuyende.hotelbookingappofhotel.data_models.LoaiPhong;
import com.chuyende.hotelbookingappofhotel.data_models.Phong;
import com.chuyende.hotelbookingappofhotel.data_models.TinhThanhPho;
import com.chuyende.hotelbookingappofhotel.data_models.TrangThaiPhong;
import com.chuyende.hotelbookingappofhotel.dialogs.BoSuuTapDialog;
import com.chuyende.hotelbookingappofhotel.dialogs.ChonTienNghiDialog;
import com.chuyende.hotelbookingappofhotel.dialogs.TimeKhuyenMaiDialog;
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
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.chuyende.hotelbookingappofhotel.activities.TatCaPhongFragment.MA_KS_LOGIN;
import static com.chuyende.hotelbookingappofhotel.activities.TatCaPhongFragment.listBitmap;

public class ThemPhongActivity extends AppCompatActivity {

    TextView tvTieuDe;
    EditText edtMaPhong, edtTenPhong, edtGiaThue, edtSoKhach, edtMoTaPhong, edtDiaChi, edtKinhDo, edtViDo, edtPhanTramGiamGia;
    Spinner spnTrangThaiPhong, spnLoaiPhong, spnTinhThanhPho;
    Button btnChonTienNghi, btnThemPhongMoi;
    TextView tvAddAnhDaiDien, tvAddBoSuuTap, tvBoSuuTap;
    ImageView imvAnhDaiDien, imvTimeKhuyenMai;

    TrangThaiPhongDatabase trangThaiPhongDB;
    LoaiPhongDatabase loaiPhongDB;
    public static TienNghiDatabase tienNghiDB;
    TinhThanhPhoDatabase tinhThanhPhoDB;
    PhongDatabase phongDB;

    public static final String GOOGLE_API_KEY = "AIzaSyARQN_EK3jLhSFXtNBrg-1vD2XCsjk6T-M";
    public static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 3;

    DialogFragment timeKhuyenMaiDialog = new TimeKhuyenMaiDialog();

    // File Name and key to save spinner position
    public static final String FILE_NAME_TRANG_THAI_PHONG = "SpinnerTrangThaiPhong";
    public static final String FILE_NAME_LOAI_PHONG = "SpinnerLoaiPhong";
    public static final String FILE_NAME_TINH_THANH_PHO = "SpinnerTinhThanhPho";
    public static final String POS_TRANG_THAI_PHONG = "PosTrangThaiPhong";
    public static final String POS_LOAI_PHONG = "PosLoaiPhong";
    public static final String POS_TINH_THANH_PHO = "PosTinhThanhPho";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.them_phong_layout);

        // Initialize Firebase Firestore
        trangThaiPhongDB = new TrangThaiPhongDatabase();
        loaiPhongDB = new LoaiPhongDatabase();
        tienNghiDB = new TienNghiDatabase();
        tinhThanhPhoDB = new TinhThanhPhoDatabase();
        phongDB = new PhongDatabase();

        // Get all views from layout
        tvTieuDe = findViewById(R.id.tvTieuDe);
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
        imvTimeKhuyenMai = findViewById(R.id.imvTimeKhuyenMai);

        tvTieuDe.setText(R.string.title_toolbar_them_phong);

        // Initialize places
        Places.initialize(this, GOOGLE_API_KEY);

        initializeFirebaseData();

        btnChonTienNghi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChonTienNghiDialog(); // Show dialog Chon Tien Nghi
            }
        });

        /*edtDiaChi.setFocusable(false);
        edtDiaChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initialize place field list
                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);

                // Start autocomplete intent
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields).build(getApplicationContext());
                startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
            }
        });*/

        imvTimeKhuyenMai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("KM=>", "Icon time khuyen mai is tapped!");
                timeKhuyenMaiDialog.show(getSupportFragmentManager(), "TIME_KHUYEN_MAI");
            }
        });

        // Set title for toolbar
        tvTieuDe.setText(R.string.title_toolbar_them_phong);

        tvAddAnhDaiDien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImageFromGallery(v);
            }
        });

        tvAddBoSuuTap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickMultiImagesFromGallery(v);
            }
        });

        tvBoSuuTap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBoSuuTapDialog();
            }
        });

        String maPhong = MA_KS_LOGIN + createRandomAString();
        edtMaPhong.setText(maPhong);
        edtMaPhong.setFocusable(false);

        btnThemPhongMoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newMaPhong = MA_KS_LOGIN + createRandomAString();
                Log.d("CODE=>", "New Id room: " + newMaPhong);

                if (!edtTenPhong.getText().toString().trim().equals("")
                        && !edtGiaThue.getText().toString().trim().equals("")
                        && !edtSoKhach.getText().toString().trim().equals("")
                        && !edtMoTaPhong.getText().toString().trim().equals("")
                        && !edtDiaChi.getText().toString().trim().equals("")
                        && !edtKinhDo.getText().toString().trim().equals("")
                        && !edtViDo.getText().toString().trim().equals("")
                        && !edtPhanTramGiamGia.getText().toString().trim().equals("")) {

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
                            String thoiHanGiamGia = TimeKhuyenMaiDialog.thoiHanGiamGia;
                            String anhDaiDien = uri;
                            String boSuuTap = phongDB.addPhotoGalleryOfRoom(listBitmap, maPhong);
                            String maKhachSan = MA_KS_LOGIN;

                            Phong phong = new Phong(maPhong, tenPhong, trangThaiPhong, giaThue, maLoaiPhong, soKhach, maTienNghi,
                                    moTaPhong, 0.0, tinhThanhPho, diaChiPhong, kinhDo, viDo, phanTramGiamGia,
                                    thoiHanGiamGia, anhDaiDien, boSuuTap, maKhachSan, 0, 0);
                            Log.d("RS=>", phong.toString());

                            phongDB.addANewRoom(phong, new SuccessNotificationCallback() {
                                @Override
                                public void onCallbackSuccessNotification(Boolean isSuccess) {
                                    if (isSuccess) {
                                        edtMaPhong.setText(newMaPhong);
                                        edtMaPhong.setFocusable(false);
                                        edtTenPhong.setText("");
                                        spnTrangThaiPhong.setSelection(0);
                                        edtGiaThue.setText("");
                                        spnLoaiPhong.setSelection(0);
                                        edtSoKhach.setText("");
                                        ChonTienNghiDialog.cacMaTienNghi = "";
                                        edtMoTaPhong.setText("");
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
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    if (data.getData() != null) {
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                            imvAnhDaiDien.setImageBitmap(bitmap);

                            Log.d("BIT=>", "Avatar bitmap: " + bitmap);
                        } catch (Exception e) {
                            Log.d("ERR=>", e.getMessage());
                        }
                    }
                    break;

                case 2:
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
                    }
                    break;
            }
        }

        /*if (resultCode == RESULT_OK) {
            if (data.getClipData() != null && requestCode == 2) {
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
            } else if (data.getData() != null && requestCode == 1) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                    imvAnhDaiDien.setImageBitmap(bitmap);

                    Log.d("BIT=>", "Avatar bitmap: " + bitmap);
                } catch (Exception e) {
                    Log.d("ERR=>", e.getMessage());
                }
            }

            // Test database on list images
            for (Bitmap bitmap : listBitmap) {
                Log.d("=>", bitmap.toString());
            }
        }*/

        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);

                edtDiaChi.setText(place.getAddress());
                edtKinhDo.setText(place.getLatLng().longitude + "");
                edtViDo.setText(place.getLatLng().latitude + "");

                Log.i("PLACE=>", "Place: " + place.getName() + " -- ID: " + place.getId());
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i("ERR_P=>", status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // Handle here
            }
        }
    }

    // Initialize Firestore Data
    public void initializeFirebaseData() {
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

    public void pickMultiImagesFromGallery(View v) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent, "Images: "), 2);
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

    public static String createRandomAString() {
        String candidateChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        Random random = new Random();
        StringBuilder rndString = new StringBuilder();
        while (rndString.length() < 20) {
            int index = (int) (random.nextFloat() * candidateChars.length());
            rndString.append(candidateChars.charAt(index));
        }
        return rndString.toString();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}