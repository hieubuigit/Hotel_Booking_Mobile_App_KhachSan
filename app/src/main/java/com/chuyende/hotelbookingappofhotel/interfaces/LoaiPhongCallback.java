package com.chuyende.hotelbookingappofhotel.interfaces;

import com.chuyende.hotelbookingappofhotel.data_models.LoaiPhong;

import java.util.List;

public interface LoaiPhongCallback {
    public void onDataCallbackLoaiPhong(List<LoaiPhong> listLoaiPhongs);
}
