
package com.libracoreteam.libracore.model;


public class Quyen {
    private int idQuyen;
    private String tenQuyen;
    
    public Quyen() {
    }
    
    public Quyen(int idQuyen, String tenQuyen) {
        this.idQuyen = idQuyen;
        this.tenQuyen = tenQuyen;
    }
    
    public Quyen(String tenQuyen) {
        this.tenQuyen = tenQuyen;
    }
    
    public int getIdQuyen() {
        return idQuyen;
    }
    
    public String getTenQuyen() {
        return tenQuyen;
    }
    
    public void setIdQuyen(int idQuyen) {
        this.idQuyen = idQuyen;
    }
    
    public void setTenQuyen(String tenQuyen) {
        this.tenQuyen = tenQuyen;
    }
    
    @Override
    public String toString() {
        return tenQuyen;
    }
}
