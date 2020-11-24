package com.chuyende.hotelbookingappofhotel.data_models;

public class TrangThaiPhong {
    private String maTrangThai;
    private String trangThaiPhong;

    public TrangThaiPhong() {
    }

    public TrangThaiPhong(String maTrangThai, String trangThaiPhong) {
        this.maTrangThai = maTrangThai;
        this.trangThaiPhong = trangThaiPhong;
    }

    public String getMaTrangThai() {
        return maTrangThai;
    }

    public void setMaTrangThai(String maTrangThai) {
        this.maTrangThai = maTrangThai;
    }

    public String getTrangThaiPhong() {
        return trangThaiPhong;
    }

    public void setTrangThaiPhong(String trangThaiPhong) {
        this.trangThaiPhong = trangThaiPhong;
    }

    @Override
    public String toString() {
        return "TrangThaiPhong{" +
                "maTrangThai='" + maTrangThai + '\'' +
                "\n -- trangThaiPhong='" + trangThaiPhong + '\'' +
                '}';
    }
}
