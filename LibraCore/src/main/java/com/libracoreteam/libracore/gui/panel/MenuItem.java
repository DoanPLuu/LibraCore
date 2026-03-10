
package com.libracoreteam.libracore.gui.panel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

public class MenuItem extends JButton {
    private boolean isSelected = false;
    private boolean hasSubMenu = false;
    private float arrowAnimation = 0f; 
    private boolean isPressed = false; 
    
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

        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (!isSelected) {
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
                repaint();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (!isSelected) {
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
                isPressed = false; 
                repaint();
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                isPressed = true;
                repaint();
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                isPressed = false;
                repaint();
            }
        });
    }
    
    public boolean isSelected() {
        return isSelected;
    }
    
    public void setSelected(boolean selected) {
        this.isSelected = selected;
        repaint(); 
    }
    
    public boolean hasSubMenu() {
        return hasSubMenu;
    }
    
    public void setArrowAnimation(float animation) {
        this.arrowAnimation = animation;
        repaint();
    }
    
    public void initSubMenuStyle() {
        setBorder(new EmptyBorder(10, 40, 10, 15));
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        boolean isSubMenuItem = isSubMenu();
        
        Color bgColor = null;
        
        if (isSelected) {
            bgColor = new Color(43, 141, 98);
        } else if (isPressed) {
            if (isSubMenuItem) {
                bgColor = new Color(10, 60, 40); 
            } else {
                bgColor = new Color(15, 80, 50);
            }
        } else if (getModel().isRollover()) {
            bgColor = new Color(43, 141, 98);
        } else if (isSubMenuItem) {
            bgColor = new Color(18, 99, 63);
        }
        
        if (bgColor != null) {
            g2.setColor(bgColor);
            g2.fillRect(0, 0, getWidth(), getHeight());
        }
        
        g2.dispose();
        
        super.paintComponent(g);
        
        g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (hasSubMenu && !isSubMenuItem) {
        g2.setColor(getForeground());

        int size = 8;
        int x = getWidth() - size - 15;
        int y = (getHeight() - size) / 2;

        Path2D arrow = new Path2D.Double();
        arrow.moveTo(0, 0);
        arrow.lineTo(size, 0);
        arrow.lineTo(size / 2.0, size);
        arrow.closePath();

        AffineTransform at = new AffineTransform();
        at.translate(x, y);


        if (arrowAnimation >= 0.5f) {
            at.rotate(Math.toRadians(-90), size / 2.0, size / 2.0);
        }

        g2.fill(at.createTransformedShape(arrow));
    }
        
        g2.dispose();
    }
    
    private boolean isSubMenu() {
        if (getBorder() instanceof EmptyBorder border) {
            return border.getBorderInsets(this).left > 30;
        }
        return false;
    }
}
