package com.chuyende.hotelbookingappofhotel.firebase_models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.chuyende.hotelbookingappofhotel.data_models.Phong;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.joda.time.DateTime;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PhongDatabase {
    public static final String COLLECTION_PHONG = "Phong";
    public static final String COLLECTION_DA_DAT = "DaDat";
    public static final String COLLECTION_DA_HUY = "DaHuy";
    public static final String COLLECTION_DA_THANH_TOAN = "DaThanhToan";
    public static final String COLLECTION_DANH_GIA = "DanhGia";

    public static final String PATH_PHONG = "/media/phong/";
    public static final String PATH_ANH_DAI_DIEN = "anhDaiDien/";
    public static final String BUCKET_ANH_DAI_DIEN = "anhDaiDien";
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
    public static final String FIELD_THOI_HAN_GIAM_GIA = "thoiHanGiamGia";
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

    public FirebaseFirestore getDb() {
        return db;
    }

    public void setDb(FirebaseFirestore db) {
        this.db = db;
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
                Log.d("P=>", "Upload a image to storage is successfully!");
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

    public void removeAllAvatarInStorage(String maPhong, SuccessNotificationCallback successNotificationCallback) {
        try {
            StorageReference listAvatarRef = storage.getReference().child(PATH_PHONG + maPhong + "/" + BUCKET_ANH_DAI_DIEN);
            listAvatarRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
                @Override
                public void onSuccess(ListResult listResult) {
                    for (StorageReference item : listResult.getItems()) {
                        Log.d("PATH-A=>", item.getPath());

                        item.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("DELETE-A=>", "Delete all avatars of " + maPhong + " is successfully!");
                                successNotificationCallback.onCallbackSuccessNotification(true);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("DELETE-A=>", "Delete all avatars of " + maPhong + " is failed!");
                                successNotificationCallback.onCallbackSuccessNotification(false);
                            }
                        });
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("DELETE=>", "Delete avatar is failed! With Error: " + e.getMessage());
                }
            });
        } catch (Exception e) {
            Log.d("ERR=>", "Room avatar of " + " is failed, with Error: " + e.getMessage());
        }
    }

    public void removeAllFilesInStorage(String pathBoSuuTap, SuccessNotificationCallback successNotificationCallback) {
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
                                successNotificationCallback.onCallbackSuccessNotification(true);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("DELETE=>", "Delete all files at " + pathBoSuuTap + " is failed! Error: " + e.getMessage());
                                successNotificationCallback.onCallbackSuccessNotification(false);
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

                    int numberOfFile = listResult.getItems().size();

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

                                if (listUris.size() == numberOfFile) {
                                    uriCallback.onCallbackUri(listUris);
                                }

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
     * readAllRoomOfHotel(): the function get all Rooms of the Hotel
     * */
    public void addANewRoom(Phong aPhong, SuccessNotificationCallback successNotificationCallback) {
        try {
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
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    int sizeData = value.size();
                    Log.d("SIZE=>", sizeData + "");   // Test database

                    if (error != null) {
                        Log.d("P=>", error.getMessage() + "");
                        return;
                    }

                    List<Phong> dsPhongs = new ArrayList<Phong>();
                    Phong aPhong;
                    for (QueryDocumentSnapshot doc : value) {
                        String maPhong = doc.getString(FIELD_MA_PHONG);
                        String tenPhong = doc.getString(FIELD_TEN_PHONG);
                        String maTrangThaiPhong = doc.getString(FIELD_MA_TRANG_THAI_PHONG);
                        double giaThue = Double.parseDouble(doc.get(FIELD_GIA_THUE).toString());
                        String maLoaiPhong = doc.getString(FIELD_MA_LOAI_PHONG);
                        int soKhach = Integer.parseInt(doc.get(FIELD_SO_KHACH).toString());
                        String maTienNghi = doc.getString(FIELD_MA_TIEN_NGHI);
                        String moTaPhong = doc.getString(FIELD_MO_TA_PHONG);
                        double ratingPhong = Double.parseDouble(doc.get(FIELD_RATING_PHONG).toString());
                        String maTinhThanhPho = doc.getString(FIELD_MA_TINH_THANH_PHO);
                        String diaChiPhong = doc.getString(FIELD_DIA_CHI_PHONG);
                        double kinhDo = Double.parseDouble(doc.get(FIELD_KINH_DO).toString());
                        double viDo = Double.parseDouble(doc.get(FIELD_VI_DO).toString());
                        int phanTramGiamGia = Integer.parseInt(doc.get(FIELD_PHAN_TRAM_GIAM_GIA).toString());
                        String thoiHanGiamGia = doc.getString(FIELD_THOI_HAN_GIAM_GIA);
                        String anhDaiDien = doc.getString(FIELD_ANH_DAI_DIEN);
                        String boSuuTap = doc.getString(FIELD_BO_SUU_TAP_ANH);
                        String maKhachSan = doc.getString(FIELD_MA_KHACH_SAN);
                        int soLuotDat = Integer.parseInt(doc.get(FIELD_SO_LUOT_DAT).toString());
                        int soLuotHuy = Integer.parseInt(doc.get(FIELD_SO_LUOT_HUY).toString());

                        aPhong = new Phong(maPhong, tenPhong, maTrangThaiPhong, giaThue, maLoaiPhong, soKhach, maTienNghi,
                                moTaPhong, ratingPhong, maTinhThanhPho, diaChiPhong, kinhDo, viDo, phanTramGiamGia,
                                thoiHanGiamGia, anhDaiDien, boSuuTap, maKhachSan, soLuotDat, soLuotHuy);
                        dsPhongs.add(aPhong);

                        // Check con han khuyen mai hay khong?
                        if (phanTramGiamGia != 0 || !thoiHanGiamGia.equals("")) {
                            if (!checkPromotionDate(thoiHanGiamGia)) {
                                Map<String, Object> fieldsUpdate = new HashMap<String, Object>();
                                fieldsUpdate.put(FIELD_PHAN_TRAM_GIAM_GIA, 0);
                                fieldsUpdate.put(FIELD_THOI_HAN_GIAM_GIA, "");

                                db.collection(COLLECTION_PHONG).document(maPhong)
                                        .update(fieldsUpdate)
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d("ERR_UPDATE=>", "Update thoi han giam gia is failed! Error: " + e.getMessage());
                                            }
                                        })
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d("PD=>", "Updatethoi han giam gia is successfully!");

                                            }
                                        });
                            }
                        }

                        if (dsPhongs.size() == sizeData) {
                            phongCallback.onDataCallbackPhong(dsPhongs);
                        }}
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

    // Check ngay hien tai co thuoc khuyen mai hay khong?
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Boolean checkPromotionDate(String thoiHanKhuyenMai) {
        boolean resultCheck = false;

        // Split("-") thoiHanKhuyenMai to get start date and end date
        if (!thoiHanKhuyenMai.equals("")) {
            String[] date = thoiHanKhuyenMai.split("-");

            int startDay, startMonth, startYear;
            int endDay, endMonth, endYear;
            int currentDay, currentMonth, currentYear;

            // Split("/") to get day, month, year of start date and end date
            if (date.length <= 2) {
                String startDate = date[0];
                String endDate = date[1];

                String[] resultStartDate = startDate.split("/");
                String[] resultEndDate = endDate.split("/");

                if (resultStartDate.length == 3 && resultEndDate.length == 3) {
                    startDay = Integer.parseInt(resultStartDate[0]);
                    startMonth = Integer.parseInt(resultStartDate[1]);
                    startYear = Integer.parseInt(resultStartDate[2]);
                    Log.i("GETD=>", "Start date: " + startDay + "/" + startMonth + "/" + startYear);

                    endDay = Integer.parseInt(resultEndDate[0]);
                    endMonth = Integer.parseInt(resultEndDate[1]);
                    endYear = Integer.parseInt(resultEndDate[2]);
                    Log.i("GETD=>", "End date: " + endDay + "/" + endMonth + "/" + endYear);

                    // Get current date
                    DateTime dt = new DateTime();
                    currentDay = dt.getDayOfMonth();
                    currentMonth = dt.getMonthOfYear();
                    currentYear = dt.getYear();
                    Log.i("GETD=>", "Current date: " + currentDay + "/" + currentMonth + "/" + currentYear);

                    // Check current day within start date and end date
                    LocalDate localStartDate = LocalDate.of(startYear, startMonth, startDay);
                    LocalDate localEndDate = LocalDate.of(endYear, endMonth, endDay);
                    LocalDate localCurrentDate = LocalDate.of(currentYear, currentMonth, currentDay);
                    // LocalDate localCurrentDate = LocalDate.of(2021, 1, 3);   // Test

                    resultCheck = (localCurrentDate.isEqual(localStartDate)) || localCurrentDate.isAfter(localStartDate) &&
                            (localCurrentDate.isBefore(localEndDate) || localCurrentDate.isEqual(localEndDate));
                }
            }
        }
        return resultCheck;
    }
}
