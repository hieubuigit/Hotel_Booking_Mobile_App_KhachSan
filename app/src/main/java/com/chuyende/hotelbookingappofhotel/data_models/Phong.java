package com.chuyende.hotelbookingappofhotel.data_models;

public class Phong {
    private String maPhong;
    private String tenPhong;
    private String maTrangThaiPhong;
    private Double giaThue;
    private String maLoaiPhong;
    private int soKhach;
    private String maTienNghi;
    private String moTaPhong;
    private double ratingPhong;
    private String maTinhThanhPho;
    private String diaChiPhong;
    private Double kinhDo;
    private Double viDo;
    private int phanTramGiamGia;
    private String anhDaiDien;
    private String boSuuTapAnh;
    private String maKhachSan;
    private int soLuotDat;
    private int soLuotHuy;

    public Phong() {
    }

    public Phong(String maPhong, String tenPhong, String maTrangThaiPhong, Double giaThue, String maLoaiPhong, int soKhach, String maTienNghi,
                 String moTaPhong, String maTinhThanhPho, String diaChiPhong, Double kinhDo, Double viDo, int phanTramGiamGia, String anhDaiDien,
                 String boSuuTapAnh, String maKhachSan) {
        this.maPhong = maPhong;
        this.tenPhong = tenPhong;
        this.maTrangThaiPhong = maTrangThaiPhong;
        this.giaThue = giaThue;
        this.maLoaiPhong = maLoaiPhong;
        this.soKhach = soKhach;
        this.maTienNghi = maTienNghi;
        this.moTaPhong = moTaPhong;
        this.maTinhThanhPho = maTinhThanhPho;
        this.diaChiPhong = diaChiPhong;
        this.kinhDo = kinhDo;
        this.viDo = viDo;
        this.phanTramGiamGia = phanTramGiamGia;
        this.anhDaiDien = anhDaiDien;
        this.boSuuTapAnh = boSuuTapAnh;
        this.maKhachSan = maKhachSan;
    }

    public Phong(String maPhong, String tenPhong, String maTrangThaiPhong, Double giaThue, String maLoaiPhong, int soKhach, String maTienNghi,
                 String moTaPhong, double ratingPhong, String maTinhThanhPho, String diaChiPhong, Double kinhDo, Double viDo, int phanTramGiamGia,
                 String anhDaiDien, String boSuuTapAnh, String maKhachSan, int soLuotDat, int soLuotHuy) {
        this.maPhong = maPhong;
        this.tenPhong = tenPhong;
        this.maTrangThaiPhong = maTrangThaiPhong;
        this.giaThue = giaThue;
        this.maLoaiPhong = maLoaiPhong;
        this.soKhach = soKhach;
        this.maTienNghi = maTienNghi;
        this.moTaPhong = moTaPhong;
        this.ratingPhong = ratingPhong;
        this.maTinhThanhPho = maTinhThanhPho;
        this.diaChiPhong = diaChiPhong;
        this.kinhDo = kinhDo;
        this.viDo = viDo;
        this.phanTramGiamGia = phanTramGiamGia;
        this.anhDaiDien = anhDaiDien;
        this.boSuuTapAnh = boSuuTapAnh;
        this.maKhachSan = maKhachSan;
        this.soLuotDat = soLuotDat;
        this.soLuotHuy = soLuotHuy;
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

    public Double getGiaThue() {
        return giaThue;
    }

    public void setGiaThue(Double giaThue) {
        this.giaThue = giaThue;
    }

    public String getMaLoaiPhong() {
        return maLoaiPhong;
    }

    public void setMaLoaiPhong(String maLoaiPhong) {
        this.maLoaiPhong = maLoaiPhong;
    }

    public int getSoKhach() {
        return soKhach;
    }

    public void setSoKhach(int soKhach) {
        this.soKhach = soKhach;
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

    public double getRatingPhong() {
        return ratingPhong;
    }

    public void setRatingPhong(double ratingPhong) {
        this.ratingPhong = ratingPhong;
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

    public Double getKinhDo() {
        return kinhDo;
    }

    public void setKinhDo(Double kinhDo) {
        this.kinhDo = kinhDo;
    }

    public Double getViDo() {
        return viDo;
    }

    public void setViDo(Double viDo) {
        this.viDo = viDo;
    }

    public int getPhanTramGiamGia() {
        return phanTramGiamGia;
    }

    public void setPhanTramGiamGia(int phanTramGiamGia) {
        this.phanTramGiamGia = phanTramGiamGia;
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

    @Override
    public String toString() {
        return "Phong{" +
                "maPhong='" + maPhong + '\'' +
                " -- tenPhong='" + tenPhong + '\'' +
                " -- maTrangThaiPhong='" + maTrangThaiPhong + '\'' +
                " -- giaThue=" + giaThue +
                " -- maLoaiPhong='" + maLoaiPhong + '\'' +
                " -- soKhach='" + soKhach + '\'' +
                " -- maTienNghi='" + maTienNghi + '\'' +
                " -- moTaPhong='" + moTaPhong + '\'' +
                " -- ratingPhong=" + ratingPhong +
                " -- maTinhThanhPho='" + maTinhThanhPho + '\'' +
                " -- diaChiPhong='" + diaChiPhong + '\'' +
                " -- kinhDo=" + kinhDo +
                " -- viDo=" + viDo +
                " -- phanTramGiamGia=" + phanTramGiamGia +
                " -- anhDaiDien='" + anhDaiDien + '\'' +
                " -- boSuuTapAnh='" + boSuuTapAnh + '\'' +
                " -- maKhachSan='" + maKhachSan + '\'' +
                " -- soLuotDat=" + soLuotDat +
                " -- soLuotHuy=" + soLuotHuy +
                '}';
    }
}