package com.chuyende.hotelbookingappofhotel.firebase_models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chuyende.hotelbookingappofhotel.data_models.TienNghi;
import com.chuyende.hotelbookingappofhotel.interfaces.ChonTienNghiCallback;
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
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.UUID;

public class TienNghiDatabase {
    private FirebaseFirestore db;
    private FirebaseStorage storage;
    public static final String BUCKET_TIEN_NGHI = "tienNghi";
    public static final String PATH_TIEN_NGHI = "/media/tienNghi/";

    public static final String COLLECTION_TIEN_NGHI = "TienNghi";
    public static final String KEY_MA_TIEN_NGHI = "maTienNghi";
    public static final String KEY_TIEN_NGHI = "tienNghi";
    public static final String KEY_ICON_TIEN_NGHI = "iconTienNghi";

    public TienNghiDatabase() {
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
    }

    public void addIconTienNghi(ImageView iconTienNghi, String maTienNghi, URIDownloadAvatarCallback uriDownloadAvatarCallback) {
        Bitmap iconTienNghiBit = Bitmap.createBitmap(
                iconTienNghi.getWidth(),
                iconTienNghi.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas iconCanvas = new Canvas(iconTienNghiBit);
        iconTienNghi.draw(iconCanvas);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        iconTienNghiBit.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        byte[] data = outputStream.toByteArray();

        // Add path for image to Firebase Storage
        String pathIconTienNghi = PATH_TIEN_NGHI + maTienNghi + "/" + UUID.randomUUID() + ".png";
        StorageReference storeRef = storage.getReference(pathIconTienNghi);

        StorageMetadata metadata = new StorageMetadata.Builder()
                .setContentType("image/png")
                .setCustomMetadata("Description", "Icon tien nghi")
                .build();

        UploadTask uploadTask = storeRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("ICON=>", "Upload icon is failed! Error: " + e.getMessage());
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d("ICON=>", "Upload icon is successfully! ");
            }
        });

        // Get uri download icon tien nghi after upload icon to Firebase storage
        Task<Uri> getDownloadUriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return storeRef.getDownloadUrl();
            }
        });

        getDownloadUriTask.addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                Uri uriImage = task.getResult();
                uriDownloadAvatarCallback.onCallbackUriDownload(uriImage + "");

                Log.d("TND=>", uriImage + "");
            }
        });
    }

    public void readAllDataTienNghi(ChonTienNghiCallback chonTienNghiCallback) {
        try {
            db.collection(COLLECTION_TIEN_NGHI).addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (error != null) {
                        Log.d("TN=>", "Listen TienNghi is failed! Error: " + error.getMessage());
                    }

                    if (value != null) {
                        ArrayList<TienNghi> dsTienNghi = new ArrayList<TienNghi>();
                        TienNghi tienNghi;
                        for (QueryDocumentSnapshot doc : value) {
                            tienNghi = new TienNghi(doc.getString(KEY_MA_TIEN_NGHI), doc.getString(KEY_ICON_TIEN_NGHI), doc.getString(KEY_TIEN_NGHI));

                            /*if (tienNghi.getMaKhachSan().contains(maKhachSan)) {
                                dsTienNghi.add(tienNghi);
                            }*/

                            dsTienNghi.add(tienNghi);
                            // Test database
                            Log.d("TN=>", "Ma tien nghi: " + tienNghi.getMaTienNghi()
                                    + " -- Tien nghi: " + tienNghi.getTienNghi()
                                    + " -- Uri icon tien nghi" + tienNghi.getIconTienNghi());
                        }
                        chonTienNghiCallback.onDataCallbackChonTienNghi(dsTienNghi);
                    }
                }
            });
        } catch (Exception e) {
            Log.d("TND=>", e.getMessage());
        }
    }

    public void removeIconsTienNghi(String maTienNghi, SuccessNotificationCallback successNotificationCallback) {
        try {
            StorageReference iconStoreRef = storage.getReference().child(PATH_TIEN_NGHI + maTienNghi);
            iconStoreRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
                @Override
                public void onSuccess(ListResult listResult) {
                    for (StorageReference item : listResult.getItems()) {
                        Log.d("ICON=>", item.getPath());

                        item.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("DELETE-ICON=>", "Delete file " + item.getPath() + " is successfully!");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("DELETE-ICON=>", "Delete file " + item.getPath() + " is failed! Error: " + e.getMessage());
                            }
                        });
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("ICON=>", "Listener icon tien nghi is failed! Error: " + e.getMessage());
                }
            });
        } catch (Exception e) {
            Log.d("TND=>", e.getMessage());
        }
    }


    public void removeATienNghi(String maTienNghi, SuccessNotificationCallback successNotificationCallback) {
        try {
            db.collection(COLLECTION_TIEN_NGHI).document(maTienNghi).delete()
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("TND=>", "Remove a Tien nghi " + maTienNghi + " is failed! Error: " + e.getMessage());
                            successNotificationCallback.onCallbackSuccessNotification(false);
                        }
                    })
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("TND=>", "Remove a Tien nghi " + maTienNghi + " is successfully!");
                            successNotificationCallback.onCallbackSuccessNotification(true);
                        }
                    });
        } catch (Exception e) {
            Log.d("TND=>", "Remove a tien nghi is error! Error: " + e.getMessage());
        }
    }

    public void addANewTienNghi(TienNghi tienNghi, SuccessNotificationCallback successNotificationCallback) {
        try {
            db.collection(COLLECTION_TIEN_NGHI).document(tienNghi.getMaTienNghi()).set(tienNghi, SetOptions.merge())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("TND=>", "Add new Tien nghi " + tienNghi.getMaTienNghi() + " is successfully!");
                            successNotificationCallback.onCallbackSuccessNotification(true);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("TND=>", "Add new Tien nghi " + tienNghi.getMaTienNghi() + " is failed! Error: " + e.getMessage());
                    successNotificationCallback.onCallbackSuccessNotification(false);
                }
            });
        } catch (Exception e) {
            Log.d("TND=>", "Add new Tien nghi is failed! Error: " + e.getMessage());
        }
    }

    public void updateATienNghi(TienNghi tienNghi, SuccessNotificationCallback successNotificationCallback) {
        try {
            db.collection(COLLECTION_TIEN_NGHI).document(tienNghi.getMaTienNghi()).set(tienNghi, SetOptions.merge())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("TND=>", "Update Tien nghi is successfully!");
                            successNotificationCallback.onCallbackSuccessNotification(true);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("TND=>", "Update Tien nghi is failed! Error: " + e.getMessage());
                            successNotificationCallback.onCallbackSuccessNotification(false);
                        }
                    });
        } catch (Exception e) {
            Log.d("TND=>", "Update Tien nghi is failed! Error: " + e.getMessage());
        }
    }
}
