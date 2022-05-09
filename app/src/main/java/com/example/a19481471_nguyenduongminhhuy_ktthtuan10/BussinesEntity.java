package com.example.a19481471_nguyenduongminhhuy_ktthtuan10;

public class BussinesEntity {
    private int id;
    private String congViec;
    private String trangThai;

    public BussinesEntity() {
    }

    public BussinesEntity(int id, String congViec, String trangThai) {
        this.id = id;
        this.congViec = congViec;
        this.trangThai = trangThai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCongViec() {
        return congViec;
    }

    public void setCongViec(String congViec) {
        this.congViec = congViec;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

}
