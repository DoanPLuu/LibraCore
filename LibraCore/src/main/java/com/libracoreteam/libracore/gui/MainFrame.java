/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.libracoreteam.libracore.gui;

import com.libracoreteam.libracore.gui.panel.MenuPanel;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author luuis
 */
public class MainFrame extends javax.swing.JFrame {
    private MenuPanel menuPanel;
    private JPanel contentPanel;
    
    public MainFrame() {
        initComponents();
    }
    
    private void initComponents() {
        setTitle("LibraCore - Quản lý thư viện");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Panel trái - Menu
        menuPanel = new MenuPanel(this);
        add(menuPanel, BorderLayout.WEST);
        
        // Panel phải - Content (tạm thời)
        contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(new BorderLayout());
        
        JLabel lblContent = new JLabel("Content Area - Sẽ thay thế sau", SwingConstants.CENTER);
        lblContent.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        contentPanel.add(lblContent, BorderLayout.CENTER);
        
        add(contentPanel, BorderLayout.CENTER);
        
        setMinimumSize(new Dimension(1200, 700));
        setLocationRelativeTo(null);
    }
    
    public void showScreen(String screenName) {
        System.out.println("Chuyển sang màn hình: " + screenName);
        // Sẽ implement sau
    }
}
