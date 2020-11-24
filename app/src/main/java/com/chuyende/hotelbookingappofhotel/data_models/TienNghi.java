package com.chuyende.hotelbookingappofhotel.data_models;

public class TienNghi {
    private String maTienNghi;
    private String iconTienNghi;
    private String tienNghi;

    public TienNghi() {
    }

    public TienNghi(String maTienNghi, String iconTienNghi, String tienNghi) {
        this.maTienNghi = maTienNghi;
        this.iconTienNghi = iconTienNghi;
        this.tienNghi = tienNghi;
    }

    public String getMaTienNghi() {
        return maTienNghi;
    }

    public void setMaTienNghi(String maTienNghi) {
        this.maTienNghi = maTienNghi;
    }

    public String getIconTienNghi() {
        return iconTienNghi;
    }

    public void setIconTienNghi(String iconTienNghi) {
        this.iconTienNghi = iconTienNghi;
    }

    public String getTienNghi() {
        return tienNghi;
    }

    public void setTienNghi(String tienNghi) {
        this.tienNghi = tienNghi;
    }

    @Override
    public String toString() {
        return "TienNghi{" +
                "maTienNghi='" + maTienNghi + '\'' +
                "\n -- iconTienNghi='" + iconTienNghi + '\'' +
                "\n -- tienNghi='" + tienNghi + '\'' +
                '}';
    }
}
