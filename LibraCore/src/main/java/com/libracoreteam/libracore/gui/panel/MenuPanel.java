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
import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author luuis
 */
public class MenuPanel extends JPanel {
    private MainFrame mainFrame;
    private MigLayout layout;
    private boolean isCollapsed = false;
    private MenuItem selectedMenuItem = null;
    
    // Map để track menu đang mở: key = menu index, value = {menuItem, subMenuPanel}
    private Map<Integer, MenuSubMenuInfo> openSubMenus = new HashMap<>();
    
    /**
     * Inner class để lưu thông tin menu đang mở
     */
    private static class MenuSubMenuInfo {
        MenuItem menuItem;
        JPanel subMenuPanel;
        
        MenuSubMenuInfo(MenuItem menuItem, JPanel subMenuPanel) {
            this.menuItem = menuItem;
            this.subMenuPanel = subMenuPanel;
        }
    }
    
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
    
    private Component createSpacer() {
        JPanel spacer = new JPanel();
        spacer.setOpaque(false);
        spacer.setPreferredSize(new Dimension(0, 2));
        return spacer;
    }
    
    private void initComponents() {
        layout = new MigLayout("wrap 1, fillx, gapy 0, insets 0", "[fill]");
        setLayout(layout);
        setPreferredSize(new Dimension(200, 0));
        setBackground(new Color(21, 110, 71));
        setOpaque(true);

        // Thêm padding cho toàn bộ panel
        setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        // Tạo menu items
        for (int i = 0; i < menuItems.length; i++) {
            addMenu(menuItems[i][0], i);

            // Thêm spacing nhỏ sau mỗi menu item (trừ item cuối)
            if (i < menuItems.length - 1) {
                add(createSpacer(), "h 2!");
            }
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
    
    private String getMenuTooltip(int index) {
        switch (index) {
            case 0: return "Trang chủ - Tổng quan hệ thống";
            case 1: return "Quản lý sách - Thêm, sửa, xóa, tìm kiếm sách";
            case 2: return "Quản lý độc giả - Thông tin độc giả và thẻ thành viên";
            case 3: return "Mượn trả - Quản lý phiếu mượn và trả sách";
            case 4: return "Báo cáo - Thống kê và báo cáo hệ thống";
            case 5: return "Hệ thống - Cài đặt và quản lý người dùng";
            default: return "";
        }
    }
    
    private String getSubMenuTooltip(int parentIndex, int subIndex) {
        String parent = menuItems[parentIndex][0];
        String sub = menuItems[parentIndex][subIndex];

        switch (parentIndex) {
            case 1: // Quản lý sách
                switch (subIndex) {
                    case 1: return "Xem danh sách tất cả sách trong thư viện";
                    case 2: return "Thêm sách mới vào hệ thống";
                    case 3: return "Thống kê số lượng sách, sách mượn nhiều nhất";
                    default: return "";
                }
            case 2: // Quản lý độc giả
                switch (subIndex) {
                    case 1: return "Xem danh sách tất cả độc giả";
                    case 2: return "Đăng ký độc giả mới";
                    case 3: return "Quản lý thẻ thành viên";
                    default: return "";
                }
            case 3: // Mượn trả
                switch (subIndex) {
                    case 1: return "Tạo và quản lý phiếu mượn sách";
                    case 2: return "Xử lý phiếu trả sách";
                    case 3: return "Danh sách sách quá hạn";
                    default: return "";
                }
            case 4: // Báo cáo
                switch (subIndex) {
                    case 1: return "Báo cáo chi tiết về mượn trả";
                    case 2: return "Báo cáo tồn kho sách";
                    case 3: return "Báo cáo doanh thu";
                    default: return "";
                }
            case 5: // Hệ thống
                switch (subIndex) {
                    case 1: return "Cài đặt hệ thống";
                    case 2: return "Quản lý người dùng";
                    case 3: return "Phân quyền người dùng";
                    default: return "";
                }
            default: return "";
        }
    }
    
    private void addMenu(String menuName, int index) {
        int length = menuItems[index].length;
        boolean hasSubMenu = length > 1;
        
        MenuItem item = new MenuItem(menuName, hasSubMenu);
        
        FontIcon icon = getIcon(index);
        if (icon != null) {
            item.setIcon(icon);
        }
        
        String tooltip = getMenuTooltip(index);
        if (!tooltip.isEmpty()) {
            item.setToolTipText(tooltip);
        }
        
        item.addActionListener(e -> {
            // AUTO-CLOSE: Đóng tất cả submenu đang mở trước (trừ menu hiện tại nếu đang mở)
            closeAllSubMenusExcept(index);
            
            // Bỏ selected state của item cũ
            if (selectedMenuItem != null && selectedMenuItem != item) {
                selectedMenuItem.setSelected(false);
            }
            
            item.setSelected(true);
            selectedMenuItem = item;
            
            if (hasSubMenu) {
                // Có submenu - toggle mở/đóng
                boolean isSubMenuOpen = isSubMenuOpen(index);
                if (isSubMenuOpen) {
                    // Đang mở → đóng lại
                    hideMenu(item, index);
                    item.setSelected(false);
                } else {
                    // Đang đóng → mở ra
                    addSubMenu(item, index, length, getComponentZOrder(item));
                    item.setSelected(true);
                }
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
    
    //đoạn này là submenu với animation trượt trượt, coi cho kỹ
    private void addSubMenu(MenuItem item, int index, int length, int indexZorder) {
        // Tạo panel chứa submenu items
        JPanel subMenuPanel = new JPanel(new MigLayout("wrap 1, fillx, inset 0, gapy 0", "fill"));
        subMenuPanel.setName(index + ""); // Đặt tên để tìm lại sau
        subMenuPanel.setBackground(new Color(18, 99, 63));
        subMenuPanel.setOpaque(true);
        
        // Tạo các submenu items
        for (int i = 1; i < length; i++) {
            MenuItem subItem = new MenuItem(menuItems[index][i], false);
            subItem.initSubMenuStyle(); // Style cho submenu
            final int subIndex = i;
            
            String subTooltip = getSubMenuTooltip(index, subIndex);
            if (!subTooltip.isEmpty()) {
                subItem.setToolTipText(subTooltip);
            }
            
            subItem.addActionListener(e -> {
                // Map submenu sang screen name
                String screenName = mapToScreenName(index, subIndex);
                mainFrame.showScreen(screenName);
                
                // Highlight submenu item khi click
                clearSubMenuSelection(subMenuPanel);
                subItem.setSelected(true);
            });
            
            subMenuPanel.add(subItem);
        }
        
        // Add vào MenuPanel với height = 0 ban đầu
        add(subMenuPanel, "h 0!", indexZorder + 1);
        revalidate();
        repaint();
        
        // Lưu vào Map để track menu đang mở
        openSubMenus.put(index, new MenuSubMenuInfo(item, subMenuPanel));
        
        // Animate mở submenu
        MenuAnimation.showMenu(subMenuPanel, item, layout, true);
    }
    
    /**
     * Ẩn submenu với animation
     */
    private void hideMenu(MenuItem item, int index) {
        MenuSubMenuInfo info = openSubMenus.get(index);
        if (info != null) {
            info.subMenuPanel.setName(null);
            MenuAnimation.showMenu(info.subMenuPanel, item, layout, false);
            openSubMenus.remove(index); // Xóa khỏi Map
        }
    }
    
    /**
     * Kiểm tra xem submenu có đang mở không
     */
    private boolean isSubMenuOpen(int index) {
        MenuSubMenuInfo info = openSubMenus.get(index);
        return info != null && info.subMenuPanel.getHeight() > 0;
    }
    
    /**
     * Đóng tất cả submenu đang mở (trừ menu được chỉ định)
     * Cách tối ưu: dùng Map để track, không cần loop qua tất cả components
     */
    private void closeAllSubMenusExcept(int exceptIndex) {
        // Tạo copy của keys để tránh ConcurrentModificationException
        Integer[] keys = openSubMenus.keySet().toArray(new Integer[0]);
        
        for (Integer index : keys) {
            if (index != exceptIndex) {
                MenuSubMenuInfo info = openSubMenus.get(index);
                if (info != null) {
                    hideMenu(info.menuItem, index);
                    info.menuItem.setSelected(false);
                }
            }
        }
    }
    
    /**
     * Clear selection của tất cả submenu items trong panel
     */
    private void clearSubMenuSelection(JPanel subMenuPanel) {
        for (Component com : subMenuPanel.getComponents()) {
            if (com instanceof MenuItem) {
                ((MenuItem) com).setSelected(false);
            }
        }
    }
    
    private String mapToScreenName(int index) {
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
    
    /**
     * Map submenu sang screen name
     */
    private String mapToScreenName(int parentIndex, int subIndex) {
        // Ví dụ: "BOOK_LIST", "BOOK_ADD", "MEMBER_LIST", etc.
        String parent = mapToScreenName(parentIndex);
        return parent + "_" + subIndex;
    }
}
