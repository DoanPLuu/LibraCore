/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.libracoreteam.libracore.model;

import java.time.LocalDate;


public class TheThanhVien {
    private int idTheThanhVien;
    private int idDocGia;
    private LocalDate ngayCap;
    private LocalDate ngayHetHan;
    private String trangThai; // "HoatDong", "BiKhoa", "HetHan"
    
    private DocGia docGia;
    
    public TheThanhVien() {
    }
    
    public TheThanhVien(int idTheThanhVien, int idDocGia, LocalDate ngayCap, LocalDate ngayHetHan, String trangThai) {
        this.idTheThanhVien = idTheThanhVien;
        this.idDocGia = idDocGia;
        this.ngayCap = ngayCap;
        this.ngayHetHan = ngayHetHan;
        this.trangThai = trangThai;
    }
    
    public TheThanhVien(int idDocGia, LocalDate ngayCap, LocalDate ngayHetHan) {
        this.idDocGia = idDocGia;
        this.ngayCap = ngayCap;
        this.ngayHetHan = ngayHetHan;
        this.trangThai = "HoatDong";
    }

    public TheThanhVien(int idDocGia){
        this.idDocGia=idDocGia;
        this.ngayCap=LocalDate.now();
        this.ngayHetHan=ngayCap.plusYears(1);
        this.trangThai="HoatDong";
    }
    
    
    // Getters
    public int getIdTheThanhVien() {
        return idTheThanhVien;
    }
    
    public int getIdDocGia() {
        return idDocGia;
    }
    
    public LocalDate getNgayCap() {
        return ngayCap;
    }
    
    public LocalDate getNgayHetHan() {
        return ngayHetHan;
    }
    
    public String getTrangThai() {
        return trangThai;
    }
    
    public DocGia getDocGia() {
        return docGia;
    }
    
    // Setters
    public void setIdTheThanhVien(int idTheThanhVien) {
        this.idTheThanhVien = idTheThanhVien;
    }
    
    public void setIdDocGia(int idDocGia) {
        this.idDocGia = idDocGia;
    }
    
    public void setNgayCap(LocalDate ngayCap) {
        this.ngayCap = ngayCap;
    }
    
    public void setNgayHetHan(LocalDate ngayHetHan) {
        this.ngayHetHan = ngayHetHan;
    }
    
    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
    
    public void setDocGia(DocGia docGia) {
        this.docGia = docGia;
    }
    
    // Utility methods
    public boolean isHoatDong() {
        return "HoatDong".equals(trangThai);
    }
    
    public boolean isConHan() {
        return ngayHetHan != null && ngayHetHan.isAfter(LocalDate.now());
    }
    
    @Override
    public String toString() {
        return "TheThanhVien{" +
                "idTheThanhVien=" + idTheThanhVien +
                ", idDocGia=" + idDocGia +
                ", TrangThai='" + trangThai + '\'' +
                '}';
    }

    public void GiaHanThe(int soNam){
        if(this.ngayHetHan.isBefore(LocalDate.now())){
            this.ngayHetHan=LocalDate.now().plusYears(soNam);
        }else this.ngayHetHan=this.ngayHetHan.plusYears(soNam);
        this.trangThai="HoatDong";
    }
    
    public void GiaHanTheoThang(int soThang) {
        if(this.ngayHetHan.isBefore(LocalDate.now())){
            this.ngayHetHan = LocalDate.now().plusMonths(soThang);
        } else {
            this.ngayHetHan = this.ngayHetHan.plusMonths(soThang);
        }
        this.trangThai = "HoatDong";
    }

    public void GiaHanDenNgayCuThe(LocalDate ngayChot) {
        this.ngayHetHan = ngayChot;
        this.trangThai = "HoatDong";
    }
}
