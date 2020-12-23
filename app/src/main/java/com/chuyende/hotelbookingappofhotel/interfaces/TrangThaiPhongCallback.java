package com.chuyende.hotelbookingappofhotel.interfaces;

import com.chuyende.hotelbookingappofhotel.data_models.TrangThaiPhong;

import java.util.List;

public interface TrangThaiPhongCallback {
    public void onDataCallbackTrangThaiPhong(List<TrangThaiPhong> listTrangThaiPhongs);
}
