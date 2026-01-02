/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.libracoreteam.libracore.model;

/**
 *
 * @author luuis
 */
public class TaiKhoan {
    private int idTaiKhoan;
    private int idVaiTro;
    private String taiKhoan;
    private String matKhau;
    
    // Quan hệ (optional)
    private VaiTro vaiTro;
    
    public TaiKhoan() {
    }
    
    public TaiKhoan(int idTaiKhoan, int idVaiTro, String taiKhoan, String matKhau) {
        this.idTaiKhoan = idTaiKhoan;
        this.idVaiTro = idVaiTro;
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
    }
    
    public TaiKhoan(int idVaiTro, String taiKhoan, String matKhau) {
        this.idVaiTro = idVaiTro;
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
    }
    
    // Getters
    public int getIdTaiKhoan() {
        return idTaiKhoan;
    }
    
    public int getIdVaiTro() {
        return idVaiTro;
    }
    
    public String getTaiKhoan() {
        return taiKhoan;
    }
    
    public String getMatKhau() {
        return matKhau;
    }
    
    public VaiTro getVaiTro() {
        return vaiTro;
    }
    
    // Setters
    public void setIdTaiKhoan(int idTaiKhoan) {
        this.idTaiKhoan = idTaiKhoan;
    }
    
    public void setIdVaiTro(int idVaiTro) {
        this.idVaiTro = idVaiTro;
    }
    
    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }
    
    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }
    
    public void setVaiTro(VaiTro vaiTro) {
        this.vaiTro = vaiTro;
    }
    
    @Override
    public String toString() {
        // Không in password vì lý do bảo mật
        return "TaiKhoan{" +
                "idTaiKhoan=" + idTaiKhoan +
                ", taiKhoan='" + taiKhoan + '\'' +
                '}';
    }
}
