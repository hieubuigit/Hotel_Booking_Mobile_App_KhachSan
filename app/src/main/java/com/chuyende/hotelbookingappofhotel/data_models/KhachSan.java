package com.chuyende.hotelbookingappofhotel.data_models;

public class KhachSan {
    private String maKhachSan, tenKhachSan, diaDiemKhachSan;

    public KhachSan() {
    }

    public KhachSan(String maKhachSan, String tenKhachSan, String diaDiemKhachSan) {
        this.maKhachSan = maKhachSan;
        this.tenKhachSan = tenKhachSan;
        this.diaDiemKhachSan = diaDiemKhachSan;
    }

    public String getMaKhachSan() {
        return maKhachSan;
    }

    public void setMaKhachSan(String maKhachSan) {
        this.maKhachSan = maKhachSan;
    }

    public String getTenKhachSan() {
        return tenKhachSan;
    }

    public void setTenKhachSan(String tenKhachSan) {
        this.tenKhachSan = tenKhachSan;
    }

    public String getDiaDiemKhachSan() {
        return diaDiemKhachSan;
    }

    public void setDiaDiemKhachSan(String diaDiemKhachSan) {
        this.diaDiemKhachSan = diaDiemKhachSan;
    }

    @Override
    public String toString() {
        return "KhachSan{" +
                "maKhachSan='" + maKhachSan + '\'' +
                "\n -- tenKhachSan='" + tenKhachSan + '\'' +
                "\n -- diaDiemKhachSan='" + diaDiemKhachSan + '\'' +
                '}';
    }
}
