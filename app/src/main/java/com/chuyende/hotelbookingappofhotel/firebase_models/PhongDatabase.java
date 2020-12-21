package com.chuyende.hotelbookingappofhotel.firebase_models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chuyende.hotelbookingappofhotel.data_models.Phong;
import com.chuyende.hotelbookingappofhotel.interfaces.PathCallback;
import com.chuyende.hotelbookingappofhotel.interfaces.PhongCallback;
import com.chuyende.hotelbookingappofhotel.interfaces.SuccessNotificationCallback;
import com.chuyende.hotelbookingappofhotel.interfaces.URIDownloadAvatarCallback;
import com.chuyende.hotelbookingappofhotel.interfaces.UriCallback;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PhongDatabase {
    public static final String COLLECTION_PHONG = "Phong";
    public static final String COLLECTION_DA_DAT = "DaDat";
    public static final String COLLECTION_DA_HUY = "DaHuy";
    public static final String COLLECTION_DA_THANH_TOAN = "DaThanhToan";
    public static final String COLLECTION_DANH_GIA = "DanhGia";

    public static final String PATH_PHONG = "/media/phong/";
    public static final String PATH_ANH_DAI_DIEN = "anhDaiDien/";
    public static final String PATH_BO_SUU_TAP = "boSuuTap/";
    public static final String BUCKET_BO_SUU_TAP = "boSuuTap";
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

    private int countFiles;

    Context context;

    public PhongDatabase() {
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
    }

    public int getCountFiles() {
        return countFiles;
    }

    public void setCountFiles(int countFiles) {
        this.countFiles = countFiles;
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
        String pathUploadGalley = PATH_PHONG + maPhong + "/" + BUCKET_BO_SUU_TAP;
        for (Bitmap bitmap : listImageViews) {
            saveImagesOneByOneToStorage(bitmap, maPhong);
        }
        return pathUploadGalley;
    }

    public void removeAllFilesInStorage(String pathBoSuuTap) {
        try {
            StorageReference listRef = storage.getReference().child(pathBoSuuTap);
            listRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
                @Override
                public void onSuccess(ListResult listResult) {
                    for (StorageReference item : listResult.getItems()) {
                        Log.d("PATH2=>", item.getPath() + "");

                        item.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("DELETE=>", "Delete all files at " + pathBoSuuTap + " is successfully!");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("DELETE=>", "Delete all files at " + pathBoSuuTap + " is failed! Error: " + e.getMessage());
                            }
                        });
                    }
                }
            });
        } catch (Exception e) {
            Log.d("ERR=>", e.getMessage());
        }
    }

    public void readPhotoGalleyOfRoom(String pathBoSuuTapAnh, UriCallback uriCallback) {
        try {
            StorageReference listRef = storage.getReference().child(pathBoSuuTapAnh);
            listRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
                @Override
                public void onSuccess(ListResult listResult) {

                    // Count files in directory
                    int countFiles = 0;

                    List<Uri> listUris = new ArrayList<Uri>();
                    for (StorageReference item : listResult.getItems()) {
                        Log.d("PATH=>", item.getPath() + "");
                        countFiles += 1;

                        item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Log.d("URI=>", uri + "");
                                listUris.add(uri);
                                uriCallback.onCallbackUri(listUris);
                            }
                        });
                    }
                    Log.d("SIZE=>", countFiles + "");
                    setCountFiles(countFiles);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("P=>", "Read Photo Gallery is failed! Error: " + e.getMessage());
                }
            });
        } catch (Exception e) {
            Log.d("ERR=>", e.getMessage() + "Read avatar is failed!");
        }
    }

    /*
     * addANewRoom(): the function to add a new room to Firestore
     * updateARoom(): the function update information of Room to Firestore
     * removeARoom(): the function remove a Room from Firestore
     * getAllRoomOfHotel(): the function get all Rooms of the Hotel
     * */
    public void addANewRoom(Phong aPhong, SuccessNotificationCallback successNotificationCallback) {
        try{
            Task task = db.collection(COLLECTION_PHONG).document(aPhong.getMaPhong()).set(aPhong, SetOptions.merge())
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
        } catch (Exception e) {
            Log.d("ERR=>", "Add a room " + aPhong.getTenPhong() + " is failed, with Error: " + e.getMessage());
        }
    }

    public void readAllDataRoomOfHotel(String maKhachSan, PhongCallback phongCallback) {
        try {
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
                            aPhong.setRatingPhong(Double.parseDouble(doc.get(FIELD_RATING_PHONG).toString()));
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
        } catch (Exception e) {
            Log.d("ERR=>", "Update a room " + maKhachSan + " is failed, with Error: " + e.getMessage());
        }
    }

    public void readRoomDataWithRoomID(String maPhong, PhongCallback phongCallback) {
        try {
            db.collection(COLLECTION_PHONG).document(maPhong).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (error != null) {
                        Log.d("P=>", "Read room data is failed! -- " + error.getMessage());
                    }
                    if (value != null && value.exists()) {
                        Phong aPhong = new Phong();
                        aPhong = value.toObject(Phong.class);

                        ArrayList<Phong> phong = new ArrayList<Phong>();
                        phong.add(0, aPhong);
                        phongCallback.onDataCallbackPhong(phong);

                        Log.d("P=>", value.getData() + "");
                    } else {
                        Log.d("P=>", "Room Data is null!");
                    }
                }
            });
        } catch (Exception e) {
            Log.d("ERR=>", "Read room wih room id is failed! Error: " + e.getMessage());
        }
    }

    public void updateARoom(Phong aPhong, SuccessNotificationCallback successNotificationCallback) {
        try {
            db.collection(COLLECTION_PHONG).document(aPhong.getMaPhong()).set(aPhong, SetOptions.merge())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("NOTIFICATION=>", "Update a room successfully! " + aPhong.getMaPhong());
                            successNotificationCallback.onCallbackSuccessNotification(true);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("NOTIFICATION=>", "Update a room failed! Error: " + e.getMessage());
                            successNotificationCallback.onCallbackSuccessNotification(false);
                        }
                    });
        } catch (Exception e) {
            Log.d("ERR=>", "Update a room " + aPhong.getTenPhong() + " is failed, with Error: " + e.getMessage());
        }
    }

    public void removeARoom(String maPhong, SuccessNotificationCallback successNotificationCallback) {
        CollectionReference daDatRef = db.collection(COLLECTION_DA_DAT);
        CollectionReference daThanhToanRef = db.collection(COLLECTION_DA_THANH_TOAN);
        CollectionReference daHuyRef = db.collection(COLLECTION_DA_HUY);
        CollectionReference danhGiaRef = db.collection(COLLECTION_DANH_GIA);
        CollectionReference phongRef = db.collection(COLLECTION_PHONG);

        try {
            Task task = phongRef.document(maPhong).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d("SUC=>", "Delete a room " + "is successfully!");
                    successNotificationCallback.onCallbackSuccessNotification(true);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("ERR=>", "Delete a room " + " is failed, with Error: " + e.getMessage());
                    successNotificationCallback.onCallbackSuccessNotification(false);
                }
            });
        } catch (Exception e) {
            Log.d("ERR=>", "Delete a room " + maPhong + " is failed, with Error: " + e.getMessage());
        }
    }
}
