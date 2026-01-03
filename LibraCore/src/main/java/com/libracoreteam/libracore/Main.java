/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.libracoreteam.libracore;

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
        
        // 2. Test database connection
        testDatabaseConnection();
        
        // 3. Launch application
        java.awt.EventQueue.invokeLater(() -> {
            // new LoginFrame().setVisible(true);
            System.out.println("Application started!");
        });
    }
    
    private static void setupLookAndFeel() {
        try {
            // Chọn 1 trong các theme sau:
            
            // Option 1: FlatLaf Light (sáng, đẹp)
            FlatLightLaf.setup();
            
            // Option 2: FlatLaf Dark (tối, đẹp)
            // FlatDarkLaf.setup();
            
            // Option 3: Arc Dark (IntelliJ theme)
            // FlatArcDarkIJTheme.setup();
            
            // Option 4: Các theme khác
            // UIManager.setLookAndFeel(new FlatDarculaLaf());
            
            System.out.println("FlatLaf theme đã áp dụng!");
            
        } catch (Exception e) {
            System.err.println("Không thể setup FlatLaf!");
            e.printStackTrace();
        }
    }
    
    /**
     * Test database connection
     */
    private static void testDatabaseConnection() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.println("Database connection OK!");
        } catch (Exception e) {
            System.err.println("Database connection FAILED!");
            System.err.println("Kiểm tra:");
            System.err.println("  1. Laragon đã chạy chưa?");
            System.err.println("  2. Database 'LibraCore' đã tạo chưa?");
            System.err.println("  3. URL, username, password đúng chưa?");
            e.printStackTrace();
        }
    }
}
