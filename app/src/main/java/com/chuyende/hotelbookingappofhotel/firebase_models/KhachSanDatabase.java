package com.chuyende.hotelbookingappofhotel.firebase_models;

import com.google.firebase.firestore.FirebaseFirestore;

public class KhachSanDatabase {
    public static final String COLLECTION_KHACH_SAN = "KhachSan";

    private FirebaseFirestore db;

    public KhachSanDatabase(FirebaseFirestore db) {
        db = FirebaseFirestore.getInstance();
    }
}

