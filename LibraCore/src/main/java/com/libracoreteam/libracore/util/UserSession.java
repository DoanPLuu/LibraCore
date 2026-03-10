package com.libracoreteam.libracore.util;

public class UserSession {
    private static UserSession instance;

    private int idTaiKhoan;
    private String tenNhanVien;
    private String vaiTro;

    private UserSession() {}

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public void login(int idTaiKhoan, String tenNhanVien, String vaiTro) {
        this.idTaiKhoan = idTaiKhoan;
        this.tenNhanVien = tenNhanVien;
        this.vaiTro = vaiTro;
    }

    public void logout() {
        this.idTaiKhoan = 0;
        this.tenNhanVien = null;
        this.vaiTro = null;
    }

    public int getIdTaiKhoan() { return idTaiKhoan; }
    public String getTenNhanVien() { return tenNhanVien; }
    public String getVaiTro() { return vaiTro; }
}