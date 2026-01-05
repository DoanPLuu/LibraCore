/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.libracoreteam.libracore.gui.panel;

import com.libracoreteam.libracore.gui.MainFrame;
import net.miginfocom.swing.MigLayout;
import org.kordamp.ikonli.swing.FontIcon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import javax.swing.*;
import java.awt.*;
/**
 *
 * @author luuis
 */
public class MenuPanel extends JPanel {
    private MainFrame mainFrame;
    private MigLayout layout;
    private boolean isCollapsed = false;
    private MenuItem selectedMenuItem = null; // Track menu item đang được chọn
    
    // Cấu trúc menu items
    private String[][] menuItems = {
        {"Dashboard"},
        {"Quản lý sách", "Danh sách sách", "Thêm sách mới", "Thống kê sách"},
        {"Quản lý độc giả", "Danh sách độc giả", "Thêm độc giả", "Thẻ thành viên"},
        {"Mượn trả", "Phiếu mượn", "Phiếu trả", "Quá hạn"},
        {"Báo cáo", "Báo cáo mượn trả", "Báo cáo tồn kho", "Báo cáo doanh thu"},
        {"Hệ thống", "Cài đặt", "Người dùng", "Phân quyền"}
    };
    
    public MenuPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        initComponents();
    }
    
    private void initComponents() {
        layout = new MigLayout("wrap 1, fillx, gapy 0, insets 2", "[fill]");
        setLayout(layout);
        setPreferredSize(new Dimension(250, 0));
        setBackground(new Color(21, 110, 71));
        setOpaque(true);
        
        // TODO: Thêm nút collapse/expand ở đây (Phase 4)
        
        // Tạo menu items
        for (int i = 0; i < menuItems.length; i++) {
            addMenu(menuItems[i][0], i);
        }
    }
    
    private FontIcon getIcon(int index) {
        switch (index) {
            case 0: return FontIcon.of(FontAwesomeSolid.HOME, 18, Color.WHITE);
            case 1: return FontIcon.of(FontAwesomeSolid.BOOK, 18, Color.WHITE);
            case 2: return FontIcon.of(FontAwesomeSolid.USERS, 18, Color.WHITE);
            case 3: return FontIcon.of(FontAwesomeSolid.BOOK_READER, 18, Color.WHITE);
            case 4: return FontIcon.of(FontAwesomeSolid.CHART_BAR, 18, Color.WHITE);
            case 5: return FontIcon.of(FontAwesomeSolid.COG, 18, Color.WHITE);
            default: return null;
        }
    }
    
    private void addMenu(String menuName, int index) {
        int length = menuItems[index].length;
        boolean hasSubMenu = length > 1;
        
        // Tạo MenuItem thay vì JButton
        MenuItem item = new MenuItem(menuName, hasSubMenu);
        
        // Set icon
        FontIcon icon = getIcon(index);
        if (icon != null) {
            item.setIcon(icon);
        }
        
        // Action listener
        item.addActionListener(e -> {
            // Bỏ selected state của item cũ
            if (selectedMenuItem != null && selectedMenuItem != item) {
                selectedMenuItem.setSelected(false);
            }
            
            // Set selected state cho item mới
            item.setSelected(true);
            selectedMenuItem = item;
            
            if (hasSubMenu) {
                // TODO: Mở dropdown submenu (Phase 3)
                System.out.println("Click menu có submenu: " + menuName);
            } else {
                // Không có submenu, chuyển màn hình
                String screenName = mapToScreenName(index);
                mainFrame.showScreen(screenName);
            }
        });
        
        add(item);
        revalidate();
        repaint();
    }
    
    private String mapToScreenName(int index) {
        // Map menu index sang screen name
        switch (index) {
            case 0: return "DASHBOARD";
            case 1: return "BOOK";
            case 2: return "MEMBER";
            case 3: return "BORROW";
            case 4: return "REPORT";
            case 5: return "SYSTEM";
            default: return "DASHBOARD";
        }
    }
}
