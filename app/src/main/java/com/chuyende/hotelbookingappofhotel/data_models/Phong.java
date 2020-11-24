package com.chuyende.hotelbookingappofhotel.data_models;

import java.util.ArrayList;

public class Phong {
    private String maPhong;
    private String tenPhong;
    private String maTrangThaiPhong;
    private Double giaThue;
    private String maLoaiPhong;
    private String soKhach;
    private ArrayList<String> maTienNghi;
    private String moTaPhong;
    private double ratingPhong;
    private String maTinhThanhPho;
    private String diaChiPhong;
    private Double kinhDo;
    private Double viDo;
    private int phanTramGiamGia;
    private String anhDaiDien;
    private ArrayList<String> boSuuTapAnh;
    private int soLuotDat;
    private int soLuotHuy;

    public Phong() {
    }

    public Phong(String maPhong, String tenPhong, String maTrangThaiPhong, Double giaThue, String maLoaiPhong, String soKhach,
                 ArrayList<String> maTienNghi, String moTaPhong, double ratingPhong, String maTinhThanhPho, String diaChiPhong, Double kinhDo,
                 Double viDo, int phanTramGiamGia, String anhDaiDien, ArrayList<String> boSuuTapAnh, int soLuotDat, int soLuotHuy) {
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

    public String getSoKhach() {
        return soKhach;
    }

    public void setSoKhach(String soKhach) {
        this.soKhach = soKhach;
    }

    public ArrayList<String> getMaTienNghi() {
        return maTienNghi;
    }

    public void setMaTienNghi(ArrayList<String> maTienNghi) {
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

    public ArrayList<String> getBoSuuTapAnh() {
        return boSuuTapAnh;
    }

    public void setBoSuuTapAnh(ArrayList<String> boSuuTapAnh) {
        this.boSuuTapAnh = boSuuTapAnh;
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
                "\n -- tenPhong='" + tenPhong + '\'' +
                "\n -- maTrangThaiPhong='" + maTrangThaiPhong + '\'' +
                "\n -- giaThue=" + giaThue +
                "\n --  maLoaiPhong='" + maLoaiPhong + '\'' +
                "\n -- soKhach='" + soKhach + '\'' +
                "\n -- maTienNghi='" + maTienNghi + '\'' +
                "\n -- moTaPhong='" + moTaPhong + '\'' +
                "\n -- ratingPhong=" + ratingPhong +
                "\n -- maTinhThanhPho='" + maTinhThanhPho + '\'' +
                "\n -- diaChiPhong='" + diaChiPhong + '\'' +
                "\n -- kinhDo=" + kinhDo +
                "\n -- viDo=" + viDo +
                "\n -- phanTramGiamGia=" + phanTramGiamGia +
                "\n -- anhDaiDien='" + anhDaiDien + '\'' +
                "\n -- boSuuTapAnh=" + boSuuTapAnh +
                "\n -- soLuotDat=" + soLuotDat +
                "\n -- soLuotHuy=" + soLuotHuy +
                '}';
    }
}