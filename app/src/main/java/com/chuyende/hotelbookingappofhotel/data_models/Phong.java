package com.chuyende.hotelbookingappofhotel.data_models;

public class Phong {
    String maPhong, tenPhong, maTrangThaiPhong, maLoaiPhong, maTienNghi, moTaPhong
            , maTinhThanhPho, diaChiPhong, anhDaiDien, boSuuTapAnh, maKhachSan;
    int soLuotDat, soLuotHuy, soKhach, phanTramGiamGia;
    double giaThue, ratingPhong, kinhDo, viDo;

    public Phong() {
    }

    public Phong(String maPhong, String tenPhong, String maTrangThaiPhong, String maLoaiPhong,
                 String maTienNghi, String moTaPhong, String maTinhThanhPho, String diaChiPhong,
                 String anhDaiDien, String boSuuTapAnh, String maKhachSan, int soLuotDat, int soLuotHuy,
                 int soKhach, int phanTramGiamGia, double giaThue, double ratingPhong, double kinhDo, double viDo) {
        this.maPhong = maPhong;
        this.tenPhong = tenPhong;
        this.maTrangThaiPhong = maTrangThaiPhong;
        this.maLoaiPhong = maLoaiPhong;
        this.maTienNghi = maTienNghi;
        this.moTaPhong = moTaPhong;
        this.maTinhThanhPho = maTinhThanhPho;
        this.diaChiPhong = diaChiPhong;
        this.anhDaiDien = anhDaiDien;
        this.boSuuTapAnh = boSuuTapAnh;
        this.maKhachSan = maKhachSan;
        this.soLuotDat = soLuotDat;
        this.soLuotHuy = soLuotHuy;
        this.soKhach = soKhach;
        this.phanTramGiamGia = phanTramGiamGia;
        this.giaThue = giaThue;
        this.ratingPhong = ratingPhong;
        this.kinhDo = kinhDo;
        this.viDo = viDo;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public String getTenPhong() {
        return tenPhong;
    }

    public void setTenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
    }

    public String getMaTrangThaiPhong() {
        return maTrangThaiPhong;
    }

    public void setMaTrangThaiPhong(String maTrangThaiPhong) {
        this.maTrangThaiPhong = maTrangThaiPhong;
    }

    public String getMaLoaiPhong() {
        return maLoaiPhong;
    }

    public void setMaLoaiPhong(String maLoaiPhong) {
        this.maLoaiPhong = maLoaiPhong;
    }

    public String getMaTienNghi() {
        return maTienNghi;
    }

    public void setMaTienNghi(String maTienNghi) {
        this.maTienNghi = maTienNghi;
    }

    public String getMoTaPhong() {
        return moTaPhong;
    }

    public void setMoTaPhong(String moTaPhong) {
        this.moTaPhong = moTaPhong;
    }

    public String getMaTinhThanhPho() {
        return maTinhThanhPho;
    }

    public void setMaTinhThanhPho(String maTinhThanhPho) {
        this.maTinhThanhPho = maTinhThanhPho;
    }

    public String getDiaChiPhong() {
        return diaChiPhong;
    }

    public void setDiaChiPhong(String diaChiPhong) {
        this.diaChiPhong = diaChiPhong;
    }

    public String getAnhDaiDien() {
        return anhDaiDien;
    }

    public void setAnhDaiDien(String anhDaiDien) {
        this.anhDaiDien = anhDaiDien;
    }

    public String getBoSuuTapAnh() {
        return boSuuTapAnh;
    }

    public void setBoSuuTapAnh(String boSuuTapAnh) {
        this.boSuuTapAnh = boSuuTapAnh;
    }

    public String getMaKhachSan() {
        return maKhachSan;
    }

    public void setMaKhachSan(String maKhachSan) {
        this.maKhachSan = maKhachSan;
    }

    public int getSoLuotDat() {
        return soLuotDat;
    }

    public void setSoLuotDat(int soLuotDat) {
        this.soLuotDat = soLuotDat;
    }

    public int getSoLuotHuy() {
        return soLuotHuy;
    }

    public void setSoLuotHuy(int soLuotHuy) {
        this.soLuotHuy = soLuotHuy;
    }

    public int getSoKhach() {
        return soKhach;
    }

    public void setSoKhach(int soKhach) {
        this.soKhach = soKhach;
    }

    public int getPhanTramGiamGia() {
        return phanTramGiamGia;
    }

    public void setPhanTramGiamGia(int phanTramGiamGia) {
        this.phanTramGiamGia = phanTramGiamGia;
    }

    public double getGiaThue() {
        return giaThue;
    }

    public void setGiaThue(double giaThue) {
        this.giaThue = giaThue;
    }

    public double getRatingPhong() {
        return ratingPhong;
    }

    public void setRatingPhong(double ratingPhong) {
        this.ratingPhong = ratingPhong;
    }

    public double getKinhDo() {
        return kinhDo;
    }

    public void setKinhDo(double kinhDo) {
        this.kinhDo = kinhDo;
    }

    public double getViDo() {
        return viDo;
    }

    public void setViDo(double viDo) {
        this.viDo = viDo;
    }
}
