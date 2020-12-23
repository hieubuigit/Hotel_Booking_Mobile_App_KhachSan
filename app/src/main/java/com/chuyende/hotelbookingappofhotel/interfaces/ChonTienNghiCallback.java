package com.chuyende.hotelbookingappofhotel.interfaces;

import com.chuyende.hotelbookingappofhotel.data_models.TienNghi;

import java.util.List;

public interface ChonTienNghiCallback {
    public void onDataCallbackChonTienNghi(List<TienNghi> listTienNghis);
}
