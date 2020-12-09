package com.chuyende.hotelbookingappofhotel.interfaces;

import com.chuyende.hotelbookingappofhotel.data_models.Phong;

import java.util.List;

public interface PhongCallback {
    public void onDataCallbackPhong(List<Phong> listPhongs);
}
