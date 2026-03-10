
package com.libracoreteam.libracore.model;

import java.util.List;


public class VaiTro {
    private int idVaiTro;
    private String tenVaiTro;
    
    private List<Quyen> danhSachQuyen;
    
    public VaiTro() {
    }
    
    public VaiTro(int idVaiTro, String tenVaiTro) {
        this.idVaiTro = idVaiTro;
        this.tenVaiTro = tenVaiTro;
    }
    
    public VaiTro(String tenVaiTro) {
        this.tenVaiTro = tenVaiTro;
    }
    
    public int getIdVaiTro() {
        return idVaiTro;
    }
    
    public String getTenVaiTro() {
        return tenVaiTro;
    }
    
    public List<Quyen> getDanhSachQuyen() {
        return danhSachQuyen;
    }
    
    public void setIdVaiTro(int idVaiTro) {
        this.idVaiTro = idVaiTro;
    }
    
    public void setTenVaiTro(String tenVaiTro) {
        this.tenVaiTro = tenVaiTro;
    }
    
    public void setDanhSachQuyen(List<Quyen> danhSachQuyen) {
        this.danhSachQuyen = danhSachQuyen;
    }
    
    @Override
    public String toString() {
        return tenVaiTro;
    }
}
