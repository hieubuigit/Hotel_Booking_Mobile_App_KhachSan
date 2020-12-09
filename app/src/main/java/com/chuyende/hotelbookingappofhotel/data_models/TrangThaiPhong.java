package com.chuyende.hotelbookingappofhotel.data_models;

public class TrangThaiPhong {
    private String maTrangThaiPhong;
    private String trangThaiPhong;

    public TrangThaiPhong() {
    }

    public TrangThaiPhong(String maTrangThaiPhong, String trangThaiPhong) {
        this.maTrangThaiPhong = maTrangThaiPhong;
        this.trangThaiPhong = trangThaiPhong;
    }

    public String getMaTrangThaiPhong() {
        return maTrangThaiPhong;
    }

    public void setMaTrangThaiPhong(String maTrangThaiPhong) {
        this.maTrangThaiPhong = maTrangThaiPhong;
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
                "maTrangThaiPhong='" + maTrangThaiPhong + '\'' +
                "\n -- trangThaiPhong='" + trangThaiPhong + '\'' +
                '}';
    }
}
