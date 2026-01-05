package com.libracoreteam.libracore.gui.panel;

import javax.swing.*;
import java.awt.*;

/**
 * Book Management Panel - Quản lý sách
 * Tạm thời là placeholder, sẽ implement SplitPanel ở bước sau
 * @author luuis
 */
public class BookManagementPanel extends JPanel {
    
    public BookManagementPanel() {
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Header
        JLabel lblTitle = new JLabel("QUẢN LÝ SÁCH", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitle.setForeground(new Color(21, 110, 71));
        
        add(lblTitle, BorderLayout.NORTH);
        
        // Content (sẽ thêm SplitPanel ở bước sau)
        JLabel lblContent = new JLabel("Master-Detail với SplitPanel sẽ được implement ở bước sau", SwingConstants.CENTER);
        lblContent.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblContent.setForeground(new Color(150, 150, 150));
        
        add(lblContent, BorderLayout.CENTER);
    }
}

