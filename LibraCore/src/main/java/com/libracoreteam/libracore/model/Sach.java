
package com.libracoreteam.libracore.model;

import java.util.List;


public class Sach {
    private int idSach;
    private Integer idNXB;
    private Integer namXuatBan;
    private String tenSach;
    private String moTa;
    private Integer soTrang;
    private boolean hoatDong;

    private List<TacGia> danhSachTacGia;
    private List<TheLoai> danhSachTheLoai;
    private NXB nhaXuatBan;

    public Sach() {
    }

    public Sach(int idSach, Integer idNXB, Integer namXuatBan, String tenSach, String moTa, Integer soTrang,
            boolean hoatDong) {
        this.idSach = idSach;
        this.idNXB = idNXB;
        this.namXuatBan = namXuatBan;
        this.tenSach = tenSach;
        this.moTa = moTa;
        this.soTrang = soTrang;
        this.hoatDong = hoatDong;
    }

    public Sach(Integer idNXB, Integer namXuatBan, String tenSach, String moTa, Integer soTrang) {
        this.idNXB = idNXB;
        this.namXuatBan = namXuatBan;
        this.tenSach = tenSach;
        this.moTa = moTa;
        this.soTrang = soTrang;
        this.hoatDong = true;
    }

    public int getIdSach() {
        return idSach;
    }

    public Integer getIdNXB() {
        return idNXB;
    }

    public Integer getNamXuatBan() {
        return namXuatBan;
    }

    public String getTenSach() {
        return tenSach;
    }

    public String getMoTa() {
        return moTa;
    }

    public Integer getSoTrang() {
        return soTrang;
    }

    public List<TacGia> getDanhSachTacGia() {
        return danhSachTacGia;
    }

    public List<TheLoai> getDanhSachTheLoai() {
        return danhSachTheLoai;
    }

    public NXB getNhaXuatBan() {
        return nhaXuatBan;
    }

    public void setIdSach(int idSach) {
        this.idSach = idSach;
    }

    public void setIdNXB(Integer idNXB) {
        this.idNXB = idNXB;
    }

    public void setNamXuatBan(Integer namXuatBan) {
        this.namXuatBan = namXuatBan;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public void setSoTrang(Integer soTrang) {
        this.soTrang = soTrang;
    }
    public void setDanhSachTacGia(List<TacGia> danhSachTacGia) {
        this.danhSachTacGia = danhSachTacGia;
    }
    public void setDanhSachTheLoai(List<TheLoai> danhSachTheLoai) {
        this.danhSachTheLoai = danhSachTheLoai;
    }
    public void setNhaXuatBan(NXB nhaXuatBan) {
        this.nhaXuatBan = nhaXuatBan;
    }
    public boolean isHoatDong() {
        return hoatDong;
    }

    public void setHoatDong(boolean hoatDong) {
        this.hoatDong = hoatDong;
    }

    @Override
    public String toString() {
        return tenSach;
    }
}
