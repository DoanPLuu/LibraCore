/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.libracoreteam.libracore.gui.panel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.Path2D;

/**
 *
 * @author luuis
 */
public class MenuItem extends JButton {
    private boolean isSelected = false;
    private boolean hasSubMenu = false;
    private float arrowAnimation = 0f; // Dùng cho Phase 3 (dropdown animation)
    
    public MenuItem(String text, boolean hasSubMenu) {
        super(text);
        this.hasSubMenu = hasSubMenu;
        initStyle();
    }
    
    private void initStyle() {
        setContentAreaFilled(false);
        setForeground(new Color(230, 230, 230));
        setHorizontalAlignment(SwingConstants.LEFT);
        setBorder(new EmptyBorder(12, 15, 12, 15));
        setIconTextGap(12);
        setFocusPainted(false);
        setBorderPainted(false);
        
        // Hover effect
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (!isSelected) {
                    setBackground(new Color(43, 141, 98));
                    setOpaque(true);
                }
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (!isSelected) {
                    setBackground(null);
                    setOpaque(false);
                }
            }
        });
    }
    
    public boolean isSelected() {
        return isSelected;
    }
    
    public void setSelected(boolean selected) {
        this.isSelected = selected;
        if (selected) {
            setBackground(new Color(43, 141, 98));
            setOpaque(true);
        } else {
            setBackground(null);
            setOpaque(false);
        }
        repaint();
    }
    
    public boolean hasSubMenu() {
        return hasSubMenu;
    }
    
    public void setArrowAnimation(float animation) {
        this.arrowAnimation = animation;
        repaint();
    }
    
    /**
     * Dùng cho submenu items (Phase 3)
     */
    public void initSubMenuStyle() {
        setBorder(new EmptyBorder(10, 40, 10, 15));
        setBackground(new Color(18, 99, 63));
        setOpaque(true);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Vẽ background nếu selected hoặc hover
        if (isSelected || isOpaque()) {
            g2.setColor(getBackground());
            g2.fillRect(0, 0, getWidth(), getHeight());
        }
        
        // Vẽ arrow nếu có submenu (chưa animate, sẽ làm ở Phase 3)
        if (hasSubMenu && !isSubMenu()) {
            g2.setColor(getForeground());
            int arrowWidth = 8;
            int arrowHeight = 4;
            int x = getWidth() - arrowWidth - 15;
            int y = (getHeight() - arrowHeight) / 2;
            
            Path2D arrow = new Path2D.Double();
            arrow.moveTo(x, y);
            arrow.lineTo(x + arrowWidth / 2, y + arrowHeight);
            arrow.lineTo(x + arrowWidth, y);
            g2.fill(arrow);
        }
        
        g2.dispose();
        super.paintComponent(g);
    }
    
    private boolean isSubMenu() {
        // Kiểm tra xem có phải submenu item không (dựa vào border)
        if (getBorder() instanceof EmptyBorder border) {
            return border.getBorderInsets(this).left > 30;
        }
        return false;
    }
}
