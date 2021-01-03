package com.chuyende.hotelbookingappofhotel.data_models;

public class ThongTinHuy {
    String maHuy, maNguoiDung, maPhong, maKhachSan, ngayDen, ngayDi, ngayHuy, trangThaiHoanTien;
    double soTienDaThanhToan;

    public ThongTinHuy() {
    }

    public ThongTinHuy(String maHuy, String maNguoiDung, String maPhong, String maKhachSan,
                       String ngayDen, String ngayDi, String ngayHuy, String trangThaiHoanTien,
                       double soTienDaThanhToan) {
        this.maHuy = maHuy;
        this.maNguoiDung = maNguoiDung;
        this.maPhong = maPhong;
        this.maKhachSan = maKhachSan;
        this.ngayDen = ngayDen;
        this.ngayDi = ngayDi;
        this.ngayHuy = ngayHuy;
        this.trangThaiHoanTien = trangThaiHoanTien;
        this.soTienDaThanhToan = soTienDaThanhToan;
    }

    public String getMaHuy() {
        return maHuy;
    }

    public void setMaHuy(String maHuy) {
        this.maHuy = maHuy;
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

    public String getMaKhachSan() {
        return maKhachSan;
    }

    public void setMaKhachSan(String maKhachSan) {
        this.maKhachSan = maKhachSan;
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

    public String getNgayHuy() {
        return ngayHuy;
    }

    public void setNgayHuy(String ngayHuy) {
        this.ngayHuy = ngayHuy;
    }

    public String getTrangThaiHoanTien() {
        return trangThaiHoanTien;
    }

    public void setTrangThaiHoanTien(String trangThaiHoanTien) {
        this.trangThaiHoanTien = trangThaiHoanTien;
    }

    public double getSoTienDaThanhToan() {
        return soTienDaThanhToan;
    }

    public void setSoTienDaThanhToan(double soTienDaThanhToan) {
        this.soTienDaThanhToan = soTienDaThanhToan;
    }
}