/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.libracoreteam.libracore;

import com.libracoreteam.libracore.gui.MainFrame;
import com.formdev.flatlaf.FlatLightLaf;
import com.libracoreteam.libracore.util.DBConnection;
import java.sql.Connection;

/**
 *
 * @author luuis
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World 1212313123!");
        
        setupLookAndFeel();
        
        // 3. Launch application
        java.awt.EventQueue.invokeLater(() -> {
            new MainFrame().setVisible(true);  
            System.out.println("Application started!");
        });
    }
    
    private static void setupLookAndFeel() {
        try {
            FlatLightLaf.setup();
            System.out.println("FlatLaf theme đã áp dụng!");
            
        } catch (Exception e) {
            System.err.println("Không thể setup FlatLaf!");
        }
    }
}
