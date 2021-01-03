package com.chuyende.hotelbookingappofhotel.data_models;

public class TenTaiKhoanKS {
    String maTenTKKS, tenTKKS;

    public TenTaiKhoanKS() {
    }

    public TenTaiKhoanKS(String maTenTKKS, String tenTKKS) {
        this.maTenTKKS = maTenTKKS;
        this.tenTKKS = tenTKKS;
    }

    public String getMaTenTKKS() {
        return maTenTKKS;
    }

    public void setMaTenTKKS(String maTenTKKS) {
        this.maTenTKKS = maTenTKKS;
    }

    public String getTenTKKS() {
        return tenTKKS;
    }

    public void setTenTKKS(String tenTKKS) {
        this.tenTKKS = tenTKKS;
    }
}
