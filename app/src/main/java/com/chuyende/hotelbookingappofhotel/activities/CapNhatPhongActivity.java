package com.chuyende.hotelbookingappofhotel.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chuyende.hotelbookingappofhotel.R;
import com.chuyende.hotelbookingappofhotel.data_models.LoaiPhong;
import com.chuyende.hotelbookingappofhotel.data_models.Phong;
import com.chuyende.hotelbookingappofhotel.data_models.TinhThanhPho;
import com.chuyende.hotelbookingappofhotel.data_models.TrangThaiPhong;
import com.chuyende.hotelbookingappofhotel.dialogs.BoSuuTapDialog;
import com.chuyende.hotelbookingappofhotel.dialogs.ChonTienNghiDialog;
import com.chuyende.hotelbookingappofhotel.dialogs.ThongBaoXoaDialog;
import com.chuyende.hotelbookingappofhotel.dialogs.TimeKhuyenMaiDialog;
import com.chuyende.hotelbookingappofhotel.firebase_models.LoaiPhongDatabase;
import com.chuyende.hotelbookingappofhotel.firebase_models.PhongDatabase;
import com.chuyende.hotelbookingappofhotel.firebase_models.TienNghiDatabase;
import com.chuyende.hotelbookingappofhotel.firebase_models.TinhThanhPhoDatabase;
import com.chuyende.hotelbookingappofhotel.firebase_models.TrangThaiPhongDatabase;
import com.chuyende.hotelbookingappofhotel.interfaces.LoaiPhongCallback;
import com.chuyende.hotelbookingappofhotel.interfaces.PhongCallback;
import com.chuyende.hotelbookingappofhotel.interfaces.SuccessNotificationCallback;
import com.chuyende.hotelbookingappofhotel.interfaces.TinhThanhPhoCallback;
import com.chuyende.hotelbookingappofhotel.interfaces.TrangThaiPhongCallback;
import com.chuyende.hotelbookingappofhotel.interfaces.URIDownloadAvatarCallback;
import com.chuyende.hotelbookingappofhotel.interfaces.UriCallback;
import com.chuyende.hotelbookingappofhotel.validate.ErrorMessage;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.chuyende.hotelbookingappofhotel.activities.TatCaPhongFragment.MA_KS_LOGIN;
import static com.chuyende.hotelbookingappofhotel.activities.TatCaPhongFragment.listBitmap;
import static com.chuyende.hotelbookingappofhotel.adapters.DanhSachPhongAdapter.KEY_MA_PHONG;

public class CapNhatPhongActivity extends AppCompatActivity {
    private BottomNavigationView botNav;
    TextView tvTieuDe;
    EditText edtMaPhong, edtTenPhong, edtGiaThue, edtSoKhach, edtMoTaPhong, edtDiaChi, edtKinhDo, edtViDo, edtPhanTramGiamGia;
    Spinner spnTrangThaiPhong, spnLoaiPhong, spnTinhThanhPho;
    Button btnChonTienNghi, btnCapNhatPhong, btnXoaPhong;
    TextView tvAddAnhDaiDien, tvAddBoSuuTap, tvBoSuuTap;
    ImageView imvAnhDaiDien, imvTimeKhuyenMai;

    DialogFragment fragment = new BoSuuTapDialog();
    DialogFragment thongBaoXoaFragment = new ThongBaoXoaDialog();
    DialogFragment timeKhuyenMaiDialog;

    public static boolean capNhatPhongIsRunning = false;

    TrangThaiPhongDatabase trangThaiPhongDB;
    LoaiPhongDatabase loaiPhongDB;
    public static TienNghiDatabase tienNghiDB;
    TinhThanhPhoDatabase tinhThanhPhoDB;
    PhongDatabase phongDB;

    Intent intent;
    Bundle bundle;
    public static String maPhong = "";
    public static String cacMaTienNghi = "";
    public static List<String> maTienNghis;

    String pathBoSuuTap;
    String thoiHanGiamGia;
    Boolean isRemovedAllFiles = false;
    Boolean isRemovedAllAvatar = false;
    double ratingPhong = 0.0;
    int soLuotDat = 0;
    int soLuotHuy = 0;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cap_nhat_phong_layout);

        Log.d("LIFE=>", "onCreate() is running!");

        // Get intent from DanhSachPhongActivity
        intent = getIntent();
        bundle = intent.getExtras();
        maPhong = bundle.getString(KEY_MA_PHONG);
        Log.d("CNPA=>", maPhong);

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
        edtPhanTramGiamGia = findViewById(R.id.edtKhuyenMai);
        spnTrangThaiPhong = findViewById(R.id.spnTrangThaiPhong);
        spnLoaiPhong = findViewById(R.id.spnLoaiPhong);
        spnTinhThanhPho = findViewById(R.id.spnTinhThanhPho);
        btnChonTienNghi = findViewById(R.id.btnChonTienNghi);
        btnCapNhatPhong = findViewById(R.id.btnCapNhat);
        btnXoaPhong = findViewById(R.id.btnXoaPhong);
        tvAddAnhDaiDien = findViewById(R.id.tvAddAnhDaiDien);
        tvAddBoSuuTap = findViewById(R.id.tvAddBoSuuTap);
        tvBoSuuTap = findViewById(R.id.tvBoSuuTap);
        imvAnhDaiDien = findViewById(R.id.imvAnhDaiDien);
        imvTimeKhuyenMai = findViewById(R.id.imvTimeKhuyenMai);

        tvTieuDe.setText(R.string.title_toolbar_cap_nhat_phong);
        edtMaPhong.setText(maPhong);
        edtMaPhong.setFocusable(false);

        initializeFirestore();

        btnChonTienNghi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CNPA=>", "Chon Tien Nghi button is tapped!");
                showChonTienNghiDialog();
            }
        });

        imvTimeKhuyenMai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("KM=>", "Icon time khuyen mai is tapped!");

                // Update time Khuyen mai
                if (!thoiHanGiamGia.equals("")) {
                    List<LocalDate> localDatesGiamGia = splitTimeGiamGia(thoiHanGiamGia);
                    if (localDatesGiamGia.size() == 2) {
                        timeKhuyenMaiDialog = new TimeKhuyenMaiDialog(localDatesGiamGia.get(0), localDatesGiamGia.get(1));
                    }
                } else {
                    timeKhuyenMaiDialog = new TimeKhuyenMaiDialog();
                }
                timeKhuyenMaiDialog.show(getSupportFragmentManager(), "TIME_KHUYEN_MAI");
            }
        });

        tvAddAnhDaiDien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CNPA=>", "Avatar is tapped!");
                pickImageFromGallery(v);
            }
        });

        tvAddBoSuuTap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CNPA=>", "Add Bo Suu Tap is tapped!");
                pickMultiImagesFromGallery(v);
            }
        });

        tvBoSuuTap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CNPA=>", "Dialog Bo Suu Tap is tapped!");
                Log.d("PATH= =>", pathBoSuuTap);
                showBoSuuTapDialog();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("LIFE=>", "onStart() is running!");
        capNhatPhongIsRunning = true;

        btnXoaPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CNPA=>", "Xoa Phong button is tapped!");
                thongBaoXoaFragment.show(getSupportFragmentManager(), "ThongBaoXoa");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("LIFE=>", "onResume() is running!");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("LIFE=>", "onActivityResult() is running!");

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    if (data.getData() != null) {
                        try {
                            Bitmap avatarBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                            imvAnhDaiDien.setImageBitmap(avatarBitmap);

                            Log.d("BIT=>", "Update avatar: " + avatarBitmap.toString());
                        } catch (Exception e) {
                            Log.d("ERR=>", "Error: " + e.getMessage());
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
    }

    public void initializeFirestore() {
        phongDB.readRoomDataWithRoomID(maPhong, new PhongCallback() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataCallbackPhong(List<Phong> listPhongs) {
                Phong aPhong = listPhongs.get(0);

                edtTenPhong.setText(aPhong.getTenPhong());
                edtGiaThue.setText(String.valueOf(aPhong.getGiaThue()));
                edtSoKhach.setText(String.valueOf(aPhong.getSoKhach()));
                edtMoTaPhong.setText(aPhong.getMoTaPhong());
                edtDiaChi.setText(aPhong.getDiaChiPhong());
                edtKinhDo.setText(String.valueOf(aPhong.getKinhDo()));
                edtViDo.setText(String.valueOf(aPhong.getViDo()));
                edtPhanTramGiamGia.setText(String.valueOf(aPhong.getPhanTramGiamGia()));
                ratingPhong = aPhong.getRatingPhong();
                soLuotDat = aPhong.getSoLuotDat();
                soLuotHuy = aPhong.getSoLuotHuy();

                String uriAvatar = aPhong.getAnhDaiDien();
                Glide.with(getApplicationContext()).load(uriAvatar).into(imvAnhDaiDien);

                String trangThaiPhong = aPhong.getMaTrangThaiPhong();
                String loaiPhong = aPhong.getMaLoaiPhong();
                String tinhThanhPho = aPhong.getMaTinhThanhPho();
                thoiHanGiamGia = aPhong.getThoiHanGiamGia();
                cacMaTienNghi = aPhong.getMaTienNghi();
                maTienNghis = splitMaTienNghis(cacMaTienNghi);

                pathBoSuuTap = aPhong.getBoSuuTapAnh();

                // Set thoiHanGiamGia when user no change data inDialog chon tien nghi
                TimeKhuyenMaiDialog.thoiHanGiamGia = thoiHanGiamGia;
                Log.d("PATH=>", pathBoSuuTap);

                if (!trangThaiPhong.equals("")) {
                    trangThaiPhongDB.readAllDataTrangThaiPhong(new TrangThaiPhongCallback() {
                        @Override
                        public void onDataCallbackTrangThaiPhong(List<TrangThaiPhong> listTrangThaiPhongs) {
                            List<String> listOnlyTrangThaiPhong = new ArrayList<String>();
                            for (TrangThaiPhong item : listTrangThaiPhongs) {
                                listOnlyTrangThaiPhong.add(item.getTrangThaiPhong());
                            }

                            ArrayAdapter<String> trangThaiPhongAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1
                                    , listOnlyTrangThaiPhong);
                            spnTrangThaiPhong.setAdapter(trangThaiPhongAdapter);
                            trangThaiPhongAdapter.notifyDataSetChanged();

                            int itemPosition = trangThaiPhongAdapter.getPosition(trangThaiPhong);
                            spnTrangThaiPhong.setSelection(itemPosition);
                            Log.d("CNPA=>", "trangThaiPhong item " + itemPosition);
                        }
                    });
                }

                if (!loaiPhong.equals("")) {
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

                            int itemPosition = loaiPhongAdapter.getPosition(loaiPhong);
                            spnLoaiPhong.setSelection(itemPosition);
                            Log.d("CNPA=>", "loaiPhong item " + itemPosition);
                        }
                    });
                }

                if (!tinhThanhPho.equals("")) {
                    tinhThanhPhoDB.readAllDataTinhThanhPho(new TinhThanhPhoCallback() {
                        @Override
                        public void onDataCallbackTinhThanhPho(List<TinhThanhPho> listTinhThanhPhos) {
                            Log.d("TPM=>", "Size loai phong = " + listTinhThanhPhos.size());

                            ArrayList<String> listOnlyTinhThanhPho = new ArrayList<String>();
                            for (TinhThanhPho item : listTinhThanhPhos) {
                                listOnlyTinhThanhPho.add(item.getTinhThanhPho());
                            }

                            ArrayAdapter<String> tinhThanhPhoAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1
                                    , listOnlyTinhThanhPho);
                            spnTinhThanhPho.setAdapter(tinhThanhPhoAdapter);
                            tinhThanhPhoAdapter.notifyDataSetChanged();

                            int itemPosition = tinhThanhPhoAdapter.getPosition(tinhThanhPho);
                            spnTinhThanhPho.setSelection(itemPosition);
                            Log.d("CNPA=>", "tinhThanhPho item " + itemPosition);
                        }
                    });
                }

                // Load bo suu tap of room
                if (!pathBoSuuTap.equals("")) {
                    phongDB.readPhotoGalleyOfRoom(pathBoSuuTap, new UriCallback() {
                        @Override
                        public void onCallbackUri(List<Uri> listUris) {
                            if (listUris != null) {
                                Log.d("LENGTH=>", listUris.size() + "");

                                // Convert uri to bitmap and add its to List<Bitmap>
                                listBitmap.clear();
                                for (Uri uri : listUris) {
                                    Glide.with(getApplicationContext())
                                            .asBitmap()
                                            .load(uri)
                                            .into(new CustomTarget<Bitmap>() {
                                                @Override
                                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                                    Log.d("BIT=>", "Loading bitmap: " + resource);
                                                    listBitmap.add(resource);
                                                }

                                                @Override
                                                public void onLoadCleared(@Nullable Drawable placeholder) {

                                                }
                                            });
                                }
                            } else {
                                Log.d("ERR=>", "listUris size = " + listUris.size() + ". Data is null! ");
                            }
                        }
                    });
                }
            }
        });
        btnCapNhatPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CNPA=>", "Cap Nhat Phong button is tapped!");

                // Delete all files in Directory before remove the room from Firebase Storage
                phongDB.removeAllFilesInStorage(pathBoSuuTap, new SuccessNotificationCallback() {
                    @Override
                    public void onCallbackSuccessNotification(Boolean isSuccess) {
                        isRemovedAllFiles = true;
                    }
                });

                // Delete all avatar in anhDaiDien/ in Firebase Storage
                phongDB.removeAllAvatarInStorage(maPhong, new SuccessNotificationCallback() {
                    @Override
                    public void onCallbackSuccessNotification(Boolean isSuccess) {
                        isRemovedAllAvatar = true;
                    }
                });

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
                            String thoiHanGiamGia = "";
                            if (TimeKhuyenMaiDialog.thoiHanGiamGia.equals("") || TimeKhuyenMaiDialog.thoiHanGiamGia == null) {
                                thoiHanGiamGia = cacMaTienNghi;
                            } else {
                                thoiHanGiamGia = TimeKhuyenMaiDialog.thoiHanGiamGia;
                            }
                            String anhDaiDien = uri;
                            String boSuuTap = "";
                            if (isRemovedAllFiles) {
                                boSuuTap = phongDB.addPhotoGalleryOfRoom(listBitmap, maPhong);
                                //isRemovedAllFiles = false;
                            } else {
                                boSuuTap = pathBoSuuTap;
                            }
                            String maKhachSan = MA_KS_LOGIN;
                            Phong phong = new Phong(maPhong, tenPhong, trangThaiPhong, giaThue, maLoaiPhong, soKhach, maTienNghi,
                                    moTaPhong, ratingPhong, tinhThanhPho, diaChiPhong, kinhDo, viDo, phanTramGiamGia,
                                    thoiHanGiamGia, anhDaiDien, boSuuTap, maKhachSan, soLuotDat, soLuotHuy);

                            Log.d("CNPA ==>", phong.toString());

                            phongDB.updateARoom(phong, new SuccessNotificationCallback() {
                                @Override
                                public void onCallbackSuccessNotification(Boolean isSuccess) {
                                    if (isSuccess) {
                                        Toast.makeText(CapNhatPhongActivity.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();

                                        // Switch to TatCaPhongFragment Screens after update successfully
                                        /*intent = new Intent(getApplicationContext(), MainFragment.class);
                                        startActivity(intent);*/
                                    } else {
                                        Toast.makeText(CapNhatPhongActivity.this, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
                                        Log.d("P=>", "Update a room is failed!");
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
    protected void onPause() {
        super.onPause();
        capNhatPhongIsRunning = false;

        Log.d("LIFE=>", "onPause() is running!");
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("LIFE=>", "onDestroy() is running!");

        // Remove all bo suu tap when destroy activity
        listBitmap.clear();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<LocalDate> splitTimeGiamGia(String thoiHanGiamGia) {
        List<LocalDate> listDateGiamGia = new ArrayList<LocalDate>();

        if (!thoiHanGiamGia.equals("")) {
            String[] timeGiamGia = thoiHanGiamGia.split("-");

            if (timeGiamGia.length == 2) {
                String[] startGiamGia = timeGiamGia[0].split("/");
                String[] endGiamGia = timeGiamGia[1].split("/");

                int dayStartGiamGia, monthStartGiamGia, yearStartGiamGia;
                int dayEndGiamGia, monthEndGiamGia, yearEndGiamGia;

                if (startGiamGia.length == 3 && endGiamGia.length == 3) {
                    dayStartGiamGia = Integer.parseInt(startGiamGia[0]);
                    monthStartGiamGia = Integer.parseInt(startGiamGia[1]);
                    yearStartGiamGia = Integer.parseInt(startGiamGia[2]);
                    LocalDate localStartDate = LocalDate.of(yearStartGiamGia, monthStartGiamGia, dayStartGiamGia);

                    dayEndGiamGia = Integer.parseInt(endGiamGia[0]);
                    monthEndGiamGia = Integer.parseInt(endGiamGia[1]);
                    yearEndGiamGia = Integer.parseInt(endGiamGia[2]);
                    LocalDate localEndDate = LocalDate.of(yearEndGiamGia, monthEndGiamGia, dayEndGiamGia);

                    listDateGiamGia.add(localStartDate);
                    listDateGiamGia.add(localEndDate);
                }
            }
        }
        return listDateGiamGia;
    }

    public List<String> splitMaTienNghis(String maCacTienNghi) {
        List<String> listMaTienNghi = new ArrayList<String>();
        listMaTienNghi = Arrays.asList(maCacTienNghi.split(","));

        // Test Data
        for (String item : listMaTienNghi) {
            Log.d("CNPA=>", "Ma tien nghi -- " + item);
        }

        return listMaTienNghi;
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
        fragment.show(getSupportFragmentManager(), "UpdateTienNghi");
    }

    public void showBoSuuTapDialog() {
        //DialogFragment fragment = new BoSuuTapDialog();
        fragment.show(getSupportFragmentManager(), "BoSuuTap");
    }

    // Test lifecycle
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("LIFE=>", "onRestart() is running!");
    }
}