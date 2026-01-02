/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.libracoreteam.libracore.model;

/**
 *
 * @author luuis
 */
public class NCC {
    private int idNCC;
    private String tenNCC;
    
    public NCC() {
    }
    
    public NCC(int idNCC, String tenNCC) {
        this.idNCC = idNCC;
        this.tenNCC = tenNCC;
    }
    
    public NCC(String tenNCC) {
        this.tenNCC = tenNCC;
    }
    
    // Getters
    public int getIdNCC() {
        return idNCC;
    }
    
    public String getTenNCC() {
        return tenNCC;
    }
    
    // Setters
    public void setIdNCC(int idNCC) {
        this.idNCC = idNCC;
    }
    
    public void setTenNCC(String tenNCC) {
        this.tenNCC = tenNCC;
    }
    
    @Override
    public String toString() {
        return tenNCC;
    }
}
