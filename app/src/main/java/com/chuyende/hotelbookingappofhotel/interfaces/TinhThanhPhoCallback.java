package com.chuyende.hotelbookingappofhotel.interfaces;

import com.chuyende.hotelbookingappofhotel.data_models.TinhThanhPho;

import java.util.List;

public interface TinhThanhPhoCallback {
    public void onDataCallbackTinhThanhPho(List<TinhThanhPho> listTinhThanhPhos);
}
