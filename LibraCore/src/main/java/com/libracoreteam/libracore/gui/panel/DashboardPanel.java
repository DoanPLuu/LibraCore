package com.libracoreteam.libracore.gui.panel;

import javax.swing.*;
import java.awt.*;

/**
 * Dashboard panel - Trang chủ
 * @author luuis
 */
public class DashboardPanel extends JPanel {
    
    public DashboardPanel() {
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Header
        JLabel lblTitle = new JLabel("DASHBOARD", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitle.setForeground(new Color(21, 110, 71));
        
        add(lblTitle, BorderLayout.NORTH);
        
        // Content (sẽ thêm sau)
        JLabel lblContent = new JLabel("Trang chủ - Tổng quan hệ thống", SwingConstants.CENTER);
        lblContent.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lblContent.setForeground(new Color(150, 150, 150));
        
        add(lblContent, BorderLayout.CENTER);
    }
}

