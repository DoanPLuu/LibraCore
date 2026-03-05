/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.libracoreteam.libracore.model;

/**
 *
 * @author Sang
 */
public class ThongKePhieuPhat {
    private double tongTienPhat;
    private int soLuongPhieuPhat;
    private double mucPhatTrungBinh;

    public ThongKePhieuPhat() {
    }

    public ThongKePhieuPhat(double tongTienPhat, int soLuongPhieuPhat) {
        this.tongTienPhat = tongTienPhat;
        this.soLuongPhieuPhat = soLuongPhieuPhat;
        // Tính trung bình ngay khi khởi tạo
        if (soLuongPhieuPhat > 0) {
            this.mucPhatTrungBinh = tongTienPhat / soLuongPhieuPhat;
        } else {
            this.mucPhatTrungBinh = 0;
        }
    }

    // Getters
    public double getTongTienPhat() { return tongTienPhat; }
    public int getSoLuongPhieuPhat() { return soLuongPhieuPhat; }
    public double getMucPhatTrungBinh() { return mucPhatTrungBinh; }

    // Setters (nếu cần)
    public void setTongTienPhat(double tongTienPhat) { this.tongTienPhat = tongTienPhat; }
    public void setSoLuongPhieuPhat(int soLuongPhieuPhat) { this.soLuongPhieuPhat = soLuongPhieuPhat; }
    public void setMucPhatTrungBinh(double mucPhatTrungBinh) { this.mucPhatTrungBinh = mucPhatTrungBinh; }
}
