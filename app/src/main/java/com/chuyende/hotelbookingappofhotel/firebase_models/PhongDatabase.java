package com.chuyende.hotelbookingappofhotel.firebase_models;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chuyende.hotelbookingappofhotel.data_models.Phong;
import com.chuyende.hotelbookingappofhotel.interfaces.PhongCallback;
import com.chuyende.hotelbookingappofhotel.interfaces.SuccessNotificationCallback;
import com.chuyende.hotelbookingappofhotel.interfaces.URIDownloadAvatarCallback;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PhongDatabase {
    public static final String COLLECTION_PHONG = "Phong";

    public static final String PATH_PHONG = "/media/phong/";
    public static final String PATH_ANH_DAI_DIEN = "anhDaiDien/";
    public static final String PATH_BO_SUU_TAP = "boSuuTap/";
    public static final String PATH_CAC_TIEN_NGHI = "cacTienNghi/";
    public static final String KEY_METADATA_AVATAR_ANH_DAI_DIEN = "Anh dai dien";
    public static final String KEY_METADATA_ANH_BO_SUU_TAP = "Anh bo suu tap cua phong ";

    public static final String FIELD_ANH_DAI_DIEN = "anhDaiDien";
    public static final String FIELD_BO_SUU_TAP_ANH = "boSuuTapAnh";
    public static final String FIELD_DIA_CHI_PHONG = "diaChiPhong";
    public static final String FIELD_GIA_THUE = "giaThue";
    public static final String FIELD_KINH_DO = "kinhDo";
    public static final String FIELD_VI_DO = "viDo";
    public static final String FIELD_MA_KHACH_SAN = "maKhachSan";
    public static final String FIELD_MA_LOAI_PHONG = "maLoaiPhong";
    public static final String FIELD_MA_PHONG = "maPhong";
    public static final String FIELD_MA_TIEN_NGHI = "maTienNghi";
    public static final String FIELD_MA_TINH_THANH_PHO = "maTinhThanhPho";
    public static final String FIELD_MA_TRANG_THAI_PHONG = "maTrangThaiPhong";
    public static final String FIELD_MO_TA_PHONG = "moTaPhong";
    public static final String FIELD_PHAN_TRAM_GIAM_GIA = "phanTramGiamGia";
    public static final String FIELD_RATING_PHONG = "ratingPhong";
    public static final String FIELD_SO_KHACH = "soKhach";
    public static final String FIELD_SO_LUOT_DAT = "soLuotDat";
    public static final String FIELD_SO_LUOT_HUY = "soLuotHuy";
    public static final String FIELD_TEN_PHONG = "tenPhong";

    private FirebaseFirestore db;
    private FirebaseStorage storage;

    public PhongDatabase() {
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
    }

    public String getPathCacTienNghi(String maPhong) {
        return PATH_PHONG + maPhong + '/' + PATH_CAC_TIEN_NGHI;
    }

    /*
     * addAvatarOfTheRoom(): the function add avatar to Firebase storage
     * removeAvatarOfTheRoom(): the function delete avatar of Room from Firebase storage
     * readAvatarOfRoom(): the function read avatar of the room from Firebase storage
     * */
    public void addAvatarOfTheRoom(ImageView imageView, String maPhong, URIDownloadAvatarCallback uriDownloadAvatarCallback) {
        Bitmap avatar = Bitmap.createBitmap(
                imageView.getWidth(),
                imageView.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas avatarCanvas = new Canvas(avatar);
        imageView.draw(avatarCanvas);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        avatar.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        byte[] data = outputStream.toByteArray();

        String pathAvatar = PATH_PHONG + maPhong + '/' + PATH_ANH_DAI_DIEN + UUID.randomUUID() + ".png";
        StorageReference storeRef = storage.getReference(pathAvatar);

        StorageMetadata metadata = new StorageMetadata.Builder()
                .setContentType("image/png")
                .setCustomMetadata("caption", KEY_METADATA_AVATAR_ANH_DAI_DIEN)
                .build();

        UploadTask uploadTask = storeRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("FB=>", e.getMessage());
            }
        }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                Log.d("P=>", "Add avartar of " + maPhong + " uploaded successfully!");
            }
        });

        // Get link download after upload a image anh dai dien
        Task<Uri> getDownloadUriTask = uploadTask.continueWithTask(
                new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        return storeRef.getDownloadUrl();
                    }
                }
        );

        Log.d("P=>", "Before get URI");
        getDownloadUriTask.addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                Uri uriImage = task.getResult();
                uriDownloadAvatarCallback.onCallbackUriDownload(uriImage.toString());
                Log.d("URI=>", uriImage.toString());
            }
        });

        Log.d("P=>", "After get URI");
    }

    public void removeAvatarOfTheRoom() {
        // Code here
    }

    public String readAvatarOfRoom() {
        String uriDownload = "";
        // Code here
        return uriDownload;
    }

    /*
     * saveImagesOneByOneToStorage(): the function save image one by one from list to Firebase Storage.
     * addPhotoGalleryOfRoom(): the function upload all photo galley to Firebase Storage.
     * removeAPhotoOfGallery(): the function remove a photo of gallery from Firebase Storage.
     * readPhotoGalleyOfRoom(): the function read all photo galley of Room
     *  */
    public void saveImagesOneByOneToStorage(Bitmap bitmap, String maPhong) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        String path = PATH_PHONG + maPhong + "/" + PATH_BO_SUU_TAP + UUID.randomUUID() + ".png";
        StorageReference storeRef = storage.getReference(path);

        StorageMetadata storeMeta = new StorageMetadata.Builder()
                .setContentType("image/png")
                .setCustomMetadata("caption", KEY_METADATA_ANH_BO_SUU_TAP + maPhong)
                .build();

        UploadTask uploadTask = storeRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("P=>", e.getMessage());
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d("P=>", "Upload a image to firebase is successfully!");
            }
        });
    }

    public String addPhotoGalleryOfRoom(ArrayList<Bitmap> listImageViews, String maPhong) {
        String pathUploadGalley = PATH_PHONG + maPhong + "/" + PATH_BO_SUU_TAP;
        for (Bitmap bitmap : listImageViews) {
            saveImagesOneByOneToStorage(bitmap, maPhong);
        }
        return pathUploadGalley;
    }

    public void removeAPhotoOfGallery() {
        // Code here
    }

    public void readPhotoGalleyOfRoom(String maPhong, PhongCallback phongCallback) {
        // Code here
    }

    /*
     * addANewRoom(): the function to add a new room to Firestore
     * updateARoom(): the function update information of Room to Firestore
     * removeARoom(): the function remove a Room from Firestore
     * getAllRoomOfHotel(): the function get all Rooms of the Hotel
     * */
    public void addANewRoom(Phong aPhong, SuccessNotificationCallback successNotificationCallback) {
        db.collection(COLLECTION_PHONG).document(aPhong.getMaPhong()).set(aPhong, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("P=>", "A Phong add successfully!");
                        successNotificationCallback.onCallbackSuccessNotification(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("P=>", "Add A Phong is failed! - Error: " + e.getMessage());
                        successNotificationCallback.onCallbackSuccessNotification(false);
                    }
                });
    }

    public void updateARoom(String maPhong, Phong aPhong) {
        // Code here
    }

    public void removeARoom(String maPhong) {
        // Code here
    }

    public void readAllDataRoomOfHotel(String maKhachSan, PhongCallback phongCallback) {
        db.collection(COLLECTION_PHONG).whereEqualTo(FIELD_MA_KHACH_SAN, maKhachSan).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.d("P=>", error.getMessage() + "");
                }
                if (value != null) {
                    List<Phong> dsPhongs = new ArrayList<Phong>();
                    Phong aPhong;
                    for (QueryDocumentSnapshot doc : value) {
                        aPhong = new Phong();
                        aPhong.setMaPhong(doc.getString(FIELD_MA_PHONG));
                        aPhong.setTenPhong(doc.getString(FIELD_TEN_PHONG));
                        aPhong.setMaTrangThaiPhong(doc.getString(FIELD_MA_TRANG_THAI_PHONG));
                        aPhong.setGiaThue((Double) doc.get(FIELD_GIA_THUE));
                        aPhong.setMaLoaiPhong(doc.getString(FIELD_MA_LOAI_PHONG));
                        aPhong.setSoKhach(Math.toIntExact((Long) doc.get(FIELD_SO_KHACH)));
                        aPhong.setMaTienNghi(doc.getString(FIELD_MA_TIEN_NGHI));
                        aPhong.setMoTaPhong(doc.getString(FIELD_MO_TA_PHONG));
                        aPhong.setRatingPhong((Double) doc.get(FIELD_RATING_PHONG));
                        aPhong.setMaTinhThanhPho(doc.getString(FIELD_MA_TINH_THANH_PHO));
                        aPhong.setDiaChiPhong(doc.getString(FIELD_DIA_CHI_PHONG));
                        aPhong.setKinhDo((Double) doc.get(FIELD_KINH_DO));
                        aPhong.setViDo((Double) doc.get(FIELD_VI_DO));
                        aPhong.setPhanTramGiamGia(Math.toIntExact((Long) doc.get(FIELD_PHAN_TRAM_GIAM_GIA)));
                        aPhong.setAnhDaiDien(doc.getString(FIELD_ANH_DAI_DIEN));
                        aPhong.setBoSuuTapAnh(doc.getString(FIELD_BO_SUU_TAP_ANH));
                        aPhong.setMaKhachSan(doc.getString(FIELD_MA_KHACH_SAN));
                        aPhong.setSoLuotDat(Math.toIntExact((Long) doc.get(FIELD_SO_LUOT_DAT)));
                        aPhong.setSoLuotHuy(Math.toIntExact((Long) doc.get(FIELD_SO_LUOT_HUY)));

                        dsPhongs.add(aPhong);
                    }
                    phongCallback.onDataCallbackPhong(dsPhongs);
                } else {
                    Log.d("P=>", "Data Phong is null!");
                }
            }
        });
    }

    public void readAllDataRoomHasTrangThaiPhong(String maKhachSan, String maTrangThaiPhong, PhongCallback phongCallback) {
        db.collection(COLLECTION_PHONG).whereEqualTo(FIELD_MA_KHACH_SAN, maKhachSan)
                .whereEqualTo(FIELD_MA_TRANG_THAI_PHONG, maTrangThaiPhong)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.d("P=>", error.getMessage() + "");
                        }
                        if (value != null) {
                            List<Phong> dsPhongCoMaTrangThai = new ArrayList<Phong>();
                            Phong aPhong;
                            for (QueryDocumentSnapshot doc : value) {
                                aPhong = new Phong();
                                aPhong.setMaPhong(doc.getString(FIELD_MA_PHONG));
                                aPhong.setTenPhong(doc.getString(FIELD_TEN_PHONG));
                                aPhong.setMaTrangThaiPhong(doc.getString(FIELD_MA_TRANG_THAI_PHONG));
                                aPhong.setGiaThue(Double.parseDouble(doc.getString(FIELD_GIA_THUE)));
                                aPhong.setMaLoaiPhong(doc.getString(FIELD_MA_LOAI_PHONG));
                                aPhong.setSoKhach(Integer.parseInt(doc.getString(FIELD_SO_KHACH)));
                                aPhong.setMaTienNghi(doc.getString(FIELD_MA_TIEN_NGHI));
                                aPhong.setMoTaPhong(doc.getString(FIELD_MO_TA_PHONG));
                                aPhong.setRatingPhong(Double.parseDouble(doc.getString(FIELD_RATING_PHONG)));
                                aPhong.setMaTinhThanhPho(doc.getString(FIELD_MA_TINH_THANH_PHO));
                                aPhong.setDiaChiPhong(doc.getString(FIELD_DIA_CHI_PHONG));
                                aPhong.setKinhDo(Double.parseDouble(doc.getString(FIELD_KINH_DO)));
                                aPhong.setViDo(Double.parseDouble(doc.getString(FIELD_VI_DO)));
                                aPhong.setPhanTramGiamGia(Integer.parseInt(doc.getString(FIELD_PHAN_TRAM_GIAM_GIA)));
                                aPhong.setAnhDaiDien(doc.getString(FIELD_ANH_DAI_DIEN));
                                aPhong.setBoSuuTapAnh(doc.getString(FIELD_BO_SUU_TAP_ANH));
                                aPhong.setMaKhachSan(doc.getString(FIELD_MA_KHACH_SAN));
                                aPhong.setSoLuotDat(Integer.parseInt(doc.getString(FIELD_SO_LUOT_DAT)));
                                aPhong.setSoLuotHuy(Integer.parseInt(doc.getString(FIELD_SO_LUOT_HUY)));

                                dsPhongCoMaTrangThai.add(aPhong);
                            }
                            phongCallback.onDataCallbackPhong(dsPhongCoMaTrangThai);
                        } else {
                            Log.d("P=>", "Data Phong is null!");
                        }
                    }
                });
    }

    public void readAllDataRoomHasLoaiPhong(String maKhachSan, String maLoaiPhong, PhongCallback phongCallback) {
        db.collection(COLLECTION_PHONG).whereEqualTo(FIELD_MA_KHACH_SAN, maKhachSan)
                .whereEqualTo(FIELD_MA_LOAI_PHONG, maLoaiPhong)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.d("P=>", error.getMessage() + "");
                        }
                        if (value != null) {
                            List<Phong> dsPhongCoMaLoaiPhong = new ArrayList<Phong>();
                            Phong aPhong;
                            for (QueryDocumentSnapshot doc : value) {
                                aPhong = new Phong();
                                aPhong.setMaPhong(doc.getString(FIELD_MA_PHONG));
                                aPhong.setTenPhong(doc.getString(FIELD_TEN_PHONG));
                                aPhong.setMaTrangThaiPhong(doc.getString(FIELD_MA_TRANG_THAI_PHONG));
                                aPhong.setGiaThue(Double.parseDouble(doc.getString(FIELD_GIA_THUE)));
                                aPhong.setMaLoaiPhong(doc.getString(FIELD_MA_LOAI_PHONG));
                                aPhong.setSoKhach(Integer.parseInt(doc.getString(FIELD_SO_KHACH)));
                                aPhong.setMaTienNghi(doc.getString(FIELD_MA_TIEN_NGHI));
                                aPhong.setMoTaPhong(doc.getString(FIELD_MO_TA_PHONG));
                                aPhong.setRatingPhong(Double.parseDouble(doc.getString(FIELD_RATING_PHONG)));
                                aPhong.setMaTinhThanhPho(doc.getString(FIELD_MA_TINH_THANH_PHO));
                                aPhong.setDiaChiPhong(doc.getString(FIELD_DIA_CHI_PHONG));
                                aPhong.setKinhDo(Double.parseDouble(doc.getString(FIELD_KINH_DO)));
                                aPhong.setViDo(Double.parseDouble(doc.getString(FIELD_VI_DO)));
                                aPhong.setPhanTramGiamGia(Integer.parseInt(doc.getString(FIELD_PHAN_TRAM_GIAM_GIA)));
                                aPhong.setAnhDaiDien(doc.getString(FIELD_ANH_DAI_DIEN));
                                aPhong.setBoSuuTapAnh(doc.getString(FIELD_BO_SUU_TAP_ANH));
                                aPhong.setMaKhachSan(doc.getString(FIELD_MA_KHACH_SAN));
                                aPhong.setSoLuotDat(Integer.parseInt(doc.getString(FIELD_SO_LUOT_DAT)));
                                aPhong.setSoLuotHuy(Integer.parseInt(doc.getString(FIELD_SO_LUOT_HUY)));

                                dsPhongCoMaLoaiPhong.add(aPhong);
                            }
                            phongCallback.onDataCallbackPhong(dsPhongCoMaLoaiPhong);
                        } else {
                            Log.d("P=>", "Data Phong is null!");
                        }
                    }
                });
    }

    public void readAllDataRoomHasLoaiPhongAndTrangThaiPhong(String maKhachSan, String maLoaiPhong, String maTrangThaiPhong, PhongCallback phongCallback) {
        db.collection(COLLECTION_PHONG).whereEqualTo(FIELD_MA_KHACH_SAN, maKhachSan)
                .whereEqualTo(FIELD_MA_LOAI_PHONG, maLoaiPhong)
                .whereEqualTo(FIELD_MA_TRANG_THAI_PHONG, maTrangThaiPhong)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.d("P=>", error.getMessage() + "");
                        }
                        if (value != null) {
                            List<Phong> dsPhongCoMaLoaiPhongVaMaTrangThai = new ArrayList<Phong>();
                            Phong aPhong;
                            for (QueryDocumentSnapshot doc : value) {
                                aPhong = new Phong();
                                aPhong.setMaPhong(doc.getString(FIELD_MA_PHONG));
                                aPhong.setTenPhong(doc.getString(FIELD_TEN_PHONG));
                                aPhong.setMaTrangThaiPhong(doc.getString(FIELD_MA_TRANG_THAI_PHONG));
                                aPhong.setGiaThue(Double.parseDouble(doc.getString(FIELD_GIA_THUE)));
                                aPhong.setMaLoaiPhong(doc.getString(FIELD_MA_LOAI_PHONG));
                                aPhong.setSoKhach(Integer.parseInt(doc.getString(FIELD_SO_KHACH)));
                                aPhong.setMaTienNghi(doc.getString(FIELD_MA_TIEN_NGHI));
                                aPhong.setMoTaPhong(doc.getString(FIELD_MO_TA_PHONG));
                                aPhong.setRatingPhong(Double.parseDouble(doc.getString(FIELD_RATING_PHONG)));
                                aPhong.setMaTinhThanhPho(doc.getString(FIELD_MA_TINH_THANH_PHO));
                                aPhong.setDiaChiPhong(doc.getString(FIELD_DIA_CHI_PHONG));
                                aPhong.setKinhDo(Double.parseDouble(doc.getString(FIELD_KINH_DO)));
                                aPhong.setViDo(Double.parseDouble(doc.getString(FIELD_VI_DO)));
                                aPhong.setPhanTramGiamGia(Integer.parseInt(doc.getString(FIELD_PHAN_TRAM_GIAM_GIA)));
                                aPhong.setAnhDaiDien(doc.getString(FIELD_ANH_DAI_DIEN));
                                aPhong.setBoSuuTapAnh(doc.getString(FIELD_BO_SUU_TAP_ANH));
                                aPhong.setMaKhachSan(doc.getString(FIELD_MA_KHACH_SAN));
                                aPhong.setSoLuotDat(Integer.parseInt(doc.getString(FIELD_SO_LUOT_DAT)));
                                aPhong.setSoLuotHuy(Integer.parseInt(doc.getString(FIELD_SO_LUOT_HUY)));

                                dsPhongCoMaLoaiPhongVaMaTrangThai.add(aPhong);
                            }
                            phongCallback.onDataCallbackPhong(dsPhongCoMaLoaiPhongVaMaTrangThai);
                        } else {
                            Log.d("P=>", "Data Phong is null!");
                        }
                    }
                });
    }
}
