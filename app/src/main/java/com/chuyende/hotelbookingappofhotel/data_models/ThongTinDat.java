package com.chuyende.hotelbookingappofhotel.data_models;

public class ThongTinDat {
    String maDat, maNguoiDung, maPhong, ngayDatPhong, ngayDen, ngayDi;
    double soTienThanhToan;

    public ThongTinDat() {
    }

    public ThongTinDat(String maDat, String maNguoiDung, String maPhong, String ngayDatPhong, String ngayDen, String ngayDi, double soTienThanhToan) {
        this.maDat = maDat;
        this.maNguoiDung = maNguoiDung;
        this.maPhong = maPhong;
        this.ngayDatPhong = ngayDatPhong;
        this.ngayDen = ngayDen;
        this.ngayDi = ngayDi;
        this.soTienThanhToan = soTienThanhToan;
    }

    public String getMaDat() {
        return maDat;
    }

    public void setMaDat(String maDat) {
        this.maDat = maDat;
    }

    public String getMaNguoiDung() {
        return maNguoiDung;
    }

    public void setMaNguoiDung(String maNguoiDung) {
        this.maNguoiDung = maNguoiDung;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public String getNgayDatPhong() {
        return ngayDatPhong;
    }

    public void setNgayDatPhong(String ngayDatPhong) {
        this.ngayDatPhong = ngayDatPhong;
    }

    public String getNgayDen() {
        return ngayDen;
    }

    public void setNgayDen(String ngayDen) {
        this.ngayDen = ngayDen;
    }

    public String getNgayDi() {
        return ngayDi;
    }

    public void setNgayDi(String ngayDi) {
        this.ngayDi = ngayDi;
    }

    public double getSoTienThanhToan() {
        return soTienThanhToan;
    }

    public void setSoTienThanhToan(double soTienThanhToan) {
        this.soTienThanhToan = soTienThanhToan;
    }
}
