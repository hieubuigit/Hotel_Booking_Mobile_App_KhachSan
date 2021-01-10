package com.chuyende.hotelbookingappofhotel.data_models;

public class ThongTinThanhToan {
    String maThanhToan, maNguoiDung, maPhong, maKhachSan, maTrangThai, ngayDen, ngayDi, ngayThanhToan, trangThaiHoanTatThanhToan;
    double soTienThanhToanTruoc;
    int tongThanhToan;

    public ThongTinThanhToan() {
    }

    public ThongTinThanhToan(String maThanhToan, String maNguoiDung, String maPhong, String maKhachSan,
                             String maTrangThai, String ngayDen, String ngayDi, String ngayThanhToan,
                             String trangThaiHoanTatThanhToan, double soTienThanhToanTruoc, int tongThanhToan) {
        this.maThanhToan = maThanhToan;
        this.maNguoiDung = maNguoiDung;
        this.maPhong = maPhong;
        this.maKhachSan = maKhachSan;
        this.maTrangThai = maTrangThai;
        this.ngayDen = ngayDen;
        this.ngayDi = ngayDi;
        this.ngayThanhToan = ngayThanhToan;
        this.trangThaiHoanTatThanhToan = trangThaiHoanTatThanhToan;
        this.soTienThanhToanTruoc = soTienThanhToanTruoc;
        this.tongThanhToan = tongThanhToan;
    }

    public String getMaThanhToan() {
        return maThanhToan;
    }

    public void setMaThanhToan(String maThanhToan) {
        this.maThanhToan = maThanhToan;
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

    public String getMaTrangThai() {
        return maTrangThai;
    }

    public void setMaTrangThai(String maTrangThai) {
        this.maTrangThai = maTrangThai;
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

    public String getNgayThanhToan() {
        return ngayThanhToan;
    }

    public void setNgayThanhToan(String ngayThanhToan) {
        this.ngayThanhToan = ngayThanhToan;
    }

    public String getTrangThaiHoanTatThanhToan() {
        return trangThaiHoanTatThanhToan;
    }

    public void setTrangThaiHoanTatThanhToan(String trangThaiHoanTatThanhToan) {
        this.trangThaiHoanTatThanhToan = trangThaiHoanTatThanhToan;
    }

    public double getSoTienThanhToanTruoc() {
        return soTienThanhToanTruoc;
    }

    public void setSoTienThanhToanTruoc(double soTienThanhToanTruoc) {
        this.soTienThanhToanTruoc = soTienThanhToanTruoc;
    }

    public int getTongThanhToan() {
        return tongThanhToan;
    }

    public void setTongThanhToan(int tongThanhToan) {
        this.tongThanhToan = tongThanhToan;
    }
}
