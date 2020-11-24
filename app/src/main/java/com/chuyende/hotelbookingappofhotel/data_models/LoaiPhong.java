package com.chuyende.hotelbookingappofhotel.data_models;

public class LoaiPhong {
    private String maLoaiPhong;
    private String loaiPhong;

    public LoaiPhong() {
    }

    public LoaiPhong(String maLoaiPhong, String loaiPhong) {
        this.maLoaiPhong = maLoaiPhong;
        this.loaiPhong = loaiPhong;
    }

    public String getMaLoaiPhong() {
        return maLoaiPhong;
    }

    public void setMaLoaiPhong(String maLoaiPhong) {
        this.maLoaiPhong = maLoaiPhong;
    }

    public String getLoaiPhong() {
        return loaiPhong;
    }

    public void setLoaiPhong(String loaiPhong) {
        this.loaiPhong = loaiPhong;
    }

    @Override
    public String toString() {
        return "LoaiPhong{" +
                "maLoaiPhong='" + maLoaiPhong + '\'' +
                "\n -- loaiPhong='" + loaiPhong + '\'' +
                '}';
    }
}
