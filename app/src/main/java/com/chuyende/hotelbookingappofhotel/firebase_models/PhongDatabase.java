package com.chuyende.hotelbookingappofhotel.firebase_models;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.chuyende.hotelbookingappofhotel.data_models.Phong;
import com.chuyende.hotelbookingappofhotel.interfaces.PhongCallback;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
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
    public String addAvatarOfTheRoom(ImageView imageView, String maPhong) {
        final String[] uriDownload = {""};

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
        getDownloadUriTask.addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Log.d("P=>", "Add successfully avatar!");
                    Uri downloadUri = task.getResult();
                    uriDownload[0] += downloadUri.toString();
                }
            }
        });
        return uriDownload[0];
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
    public void addANewRoom(Phong aPhong) {
        // Add a Phong without random id document
        db.collection(COLLECTION_PHONG).document(aPhong.getMaPhong()).set(aPhong, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("DB=>", "A Phong add successfully!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("P=>", "Add A Phong is failed! - Error: " + e.getMessage());
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
        // Code here
    }
}
