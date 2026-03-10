
package com.libracoreteam.libracore.gui.panel;

import com.libracoreteam.libracore.gui.MainFrame;
import com.libracoreteam.libracore.gui.LoginFrame;
import com.libracoreteam.libracore.util.UserSession;
import com.libracoreteam.libracore.bus.VaiTroBUS;
import com.libracoreteam.libracore.model.VaiTro;
import net.miginfocom.swing.MigLayout;
import org.kordamp.ikonli.swing.FontIcon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
/**
 *
 * @author luuis
 */
public class MenuPanel extends JPanel {
    private MainFrame mainFrame;
    private MigLayout layout;
    private boolean isCollapsed = false; 
    private MenuItem selectedMenuItem = null;
    
    private Map<Integer, MenuSubMenuInfo> openSubMenus = new HashMap<>();
    
    private boolean[] allowedMenus;
    private boolean isAdminRole = false;
    

    private static class MenuSubMenuInfo {
        MenuItem menuItem;
        JPanel subMenuPanel;
        
        MenuSubMenuInfo(MenuItem menuItem, JPanel subMenuPanel) {
            this.menuItem = menuItem;
            this.subMenuPanel = subMenuPanel;
        }
    }
    
    private String[][] menuItems = {
        {"Dashboard"},
        {"Quản lý sách", "Sách", "Cuốn sách", "Tác giả", "Nhà xuất bản", "Thể loại"},
        {"Quản lý thành viên", "Thành viên", "Thẻ thành viên"},
        {"Quản lý mượn - trả", "Mượn - trả sách"},
        {"Quản lý phạt - trả phạt", "Phạt", "Mức phạt"},
        {"Quản lý nhập sách", "Nhập sách", "Nhà cung cấp"},
        {"Quản lý người dùng", "Nhân viên", "Tài khoản", "Vai trò"},
        {"Thống kê báo cáo", "Thống kê sách", "Thống kê mượn trả sách", "Thống kê tiền phạt"}
    };
    
    public MenuPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        initPermissions();
        initComponents();
    }
    
    private Component createSpacer() {
        JPanel spacer = new JPanel();
        spacer.setOpaque(false);
        spacer.setPreferredSize(new Dimension(0, 2));
        return spacer;
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new net.miginfocom.swing.MigLayout(
                "wrap 1, fillx, insets 8 8 8 8", "[fill]"));
        panel.setOpaque(false);
    
        MenuItem accountBtn = new MenuItem("Tài khoản", false);
        accountBtn.setIcon(FontIcon.of(FontAwesomeSolid.USER_CIRCLE, 18, Color.WHITE));
        accountBtn.setToolTipText("Thông tin cá nhân, đổi mật khẩu");
        accountBtn.setHorizontalAlignment(SwingConstants.LEFT);
    
        MenuItem logoutBtn = new MenuItem("Đăng xuất", false);
        logoutBtn.setIcon(FontIcon.of(FontAwesomeSolid.SIGN_OUT_ALT, 18, Color.WHITE));
        logoutBtn.setToolTipText("Đăng xuất khỏi hệ thống");
        logoutBtn.setHorizontalAlignment(SwingConstants.LEFT);
    

         accountBtn.addActionListener(e -> handleAccountButton());
         logoutBtn.addActionListener(e -> handleLogoutButton());
    
        panel.add(accountBtn);
        panel.add(logoutBtn, "gapy 4");
    
        return panel;
    }


    private void handleAccountButton() {
        closeAllSubMenusExcept(-1); 

        if (selectedMenuItem != null) {
            selectedMenuItem.setSelected(false);
            selectedMenuItem = null;
        }

        mainFrame.showScreen("ACCOUNT");
    }

    private void handleLogoutButton() {
        int xacNhan = JOptionPane.showConfirmDialog(
                mainFrame,
                "Bạn có chắc chắn muốn đăng xuất khỏi hệ thống không?",
                "Xác nhận đăng xuất",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (xacNhan == JOptionPane.YES_OPTION) {
            UserSession.getInstance().logout();

            Window window = SwingUtilities.getWindowAncestor(this);
            if (window != null) {
                window.dispose();
            }

            EventQueue.invokeLater(() -> {
                new LoginFrame().setVisible(true);
            });
        }
    }
    
    private void initComponents() {
        layout = new MigLayout("wrap 1, fillx, gapy 0, insets 0", "[fill]");
        setLayout(layout);
        setPreferredSize(new Dimension(200, 0));
        setBackground(new Color(21, 110, 71));
        setOpaque(true);

        setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        for (int i = 0; i < menuItems.length; i++) {
            if (!shouldShowMenu(i)) {
                continue;
            }
            addMenu(menuItems[i][0], i);

            if (i < menuItems.length - 1) {
                add(createSpacer(), "h 2!");
            }
        }
        JPanel spacer = new JPanel();
        spacer.setOpaque(false);
        add(spacer, "grow, push");
        add(createBottomPanel(), "h pref!");
    }
    
    private void initPermissions() {
        int menuCount = menuItems.length;
        allowedMenus = new boolean[menuCount];
        for (int i = 0; i < menuCount; i++) {
            allowedMenus[i] = false;
        }

        allowedMenus[0] = true;

        UserSession session = UserSession.getInstance();
        String tenVaiTro = session.getVaiTro();
        if (tenVaiTro == null || tenVaiTro.trim().isEmpty()) {
            for (int i = 0; i < menuCount; i++) {
                allowedMenus[i] = true;
            }
            isAdminRole = true;
            return;
        }

        if ("Admin".equalsIgnoreCase(tenVaiTro.trim())) {
            for (int i = 0; i < menuCount; i++) {
                allowedMenus[i] = true;
            }
            isAdminRole = true;
            return;
        }

        VaiTroBUS vaiTroBUS = new VaiTroBUS();
        java.util.List<VaiTro> dsVaiTro = vaiTroBUS.getAll();
        int idVaiTro = -1;
        if (dsVaiTro != null) {
            for (VaiTro vt : dsVaiTro) {
                if (vt != null && tenVaiTro.equalsIgnoreCase(vt.getTenVaiTro())) {
                    idVaiTro = vt.getIdVaiTro();
                    break;
                }
            }
        }

        if (idVaiTro <= 0) {
            for (int i = 0; i < menuCount; i++) {
                allowedMenus[i] = true;
            }
            isAdminRole = true;
            return;
        }

        java.util.List<Integer> quyenIds = vaiTroBUS.getQuyenIdsByVaiTro(idVaiTro);
        Set<Integer> quyenSet = new HashSet<Integer>(quyenIds);

        if (quyenSet.contains(1)) {
            allowedMenus[1] = true;
        }
        if (quyenSet.contains(3)) {
            allowedMenus[2] = true;
        }
        if (quyenSet.contains(4)) {
            allowedMenus[3] = true;
        }
        if (quyenSet.contains(5)) {
            allowedMenus[4] = true;
        }
        if (quyenSet.contains(2)) {
            allowedMenus[5] = true;
        }
        if (quyenSet.contains(6)) {
            allowedMenus[6] = true;
        }

    }

    private boolean shouldShowMenu(int index) {
        if (allowedMenus == null || index < 0 || index >= allowedMenus.length) {
            return true;
        }
        return allowedMenus[index];
    }
    
    private FontIcon getIcon(int index) {
        switch (index) {
            case 0: return FontIcon.of(FontAwesomeSolid.HOME, 18, Color.WHITE);          
            case 1: return FontIcon.of(FontAwesomeSolid.BOOK, 18, Color.WHITE);          
            case 2: return FontIcon.of(FontAwesomeSolid.USER_FRIENDS, 18, Color.WHITE);  
            case 3: return FontIcon.of(FontAwesomeSolid.BOOK_READER, 18, Color.WHITE);    
            case 4: return FontIcon.of(FontAwesomeSolid.MONEY_CHECK_ALT, 18, Color.WHITE);
            case 5: return FontIcon.of(FontAwesomeSolid.TRUCK_LOADING, 18, Color.WHITE); 
            case 6: return FontIcon.of(FontAwesomeSolid.USER_COG, 18, Color.WHITE);       
            case 7: return FontIcon.of(FontAwesomeSolid.CHART_BAR, 18, Color.WHITE);    
            default: return null;
        }
    }
    
    private String getMenuTooltip(int index) {
        switch (index) {
            case 0: return "Trang chủ - Tổng quan hệ thống";
            case 1: return "Quản lý sách - Sách, quyển sách, tác giả, NXB, thể loại";
            case 2: return "Quản lý thành viên - Thành viên và thẻ thành viên";
            case 3: return "Quản lý mượn - trả sách";
            case 4: return "Quản lý phạt và trả phạt";
            case 5: return "Quản lý nhập sách và nhà cung cấp";
            case 6: return "Quản lý nhân viên, tài khoản, vai trò";
            case 7: return "Thống kê sách, mượn trả và tiền phạt";
            default: return "";
        }
    }
    
    private String getSubMenuTooltip(int parentIndex, int subIndex) {
        switch (parentIndex) {
            case 1: 
                switch (subIndex) {
                    case 1: return "Quản lý danh mục sách";
                    case 2: return "Quản lý từng quyển sách (bản thể hiện)";
                    case 3: return "Quản lý tác giả";
                    case 4: return "Quản lý nhà xuất bản";
                    case 5: return "Quản lý thể loại sách";
                    default: return "";
                }
            case 2: 
                switch (subIndex) {
                    case 1: return "Quản lý thông tin thành viên/độc giả";
                    case 2: return "Quản lý thẻ thành viên";
                    default: return "";
                }
            case 3:
                switch (subIndex) {
                    case 1: return "Tạo và quản lý phiếu mượn - trả sách";
                    default: return "";
                }
            case 4: 
                switch (subIndex) {
                    case 1: return "Quản lý phiếu phạt";
                    case 2: return "Quản lý mức phạt";
                    default: return "";
                }
            case 5: 
                switch (subIndex) {
                    case 1: return "Quản lý phiếu nhập sách";
                    case 2: return "Quản lý nhà cung cấp";
                    default: return "";
                }
            case 6: 
                switch (subIndex) {
                    case 1: return "Quản lý thông tin nhân viên";
                    case 2: return "Quản lý tài khoản đăng nhập";
                    case 3: return "Quản lý vai trò/quyền hạn";
                    default: return "";
                }
            case 7: 
                switch (subIndex) {
                    case 1: return "Thống kê sách theo nhiều tiêu chí";
                    case 2: return "Thống kê mượn trả sách";
                    case 3: return "Thống kê tiền phạt";
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
            closeAllSubMenusExcept(index);
            
            if (selectedMenuItem != null && selectedMenuItem != item) {
                selectedMenuItem.setSelected(false);
            }
            
            item.setSelected(true);
            selectedMenuItem = item;
            
            if (hasSubMenu) {
                boolean isSubMenuOpen = isSubMenuOpen(index);
                if (isSubMenuOpen) {
                    hideMenu(item, index);
                    item.setSelected(false);
                } else {
                    addSubMenu(item, index, length, getComponentZOrder(item));
                    item.setSelected(true);
                }
            } else {
                String screenName = mapToScreenName(index);
                mainFrame.showScreen(screenName);
            }
        });
        
        add(item);
        revalidate();
        repaint();
    }
    
    private void addSubMenu(MenuItem item, int index, int length, int indexZorder) {
        JPanel subMenuPanel = new JPanel(new MigLayout("wrap 1, fillx, inset 0, gapy 0", "fill"));
        subMenuPanel.setName(index + ""); 
        subMenuPanel.setBackground(new Color(18, 99, 63));
        subMenuPanel.setOpaque(true);
        
        for (int i = 1; i < length; i++) {
            MenuItem subItem = new MenuItem(menuItems[index][i], false);
            subItem.initSubMenuStyle(); 
            final int subIndex = i;
            
            String subTooltip = getSubMenuTooltip(index, subIndex);
            if (!subTooltip.isEmpty()) {
                subItem.setToolTipText(subTooltip);
            }
            
            subItem.addActionListener(e -> {
                String screenName = mapToScreenName(index, subIndex);
                mainFrame.showScreen(screenName);
                
                clearSubMenuSelection(subMenuPanel);
                subItem.setSelected(true);
            });
            
            subMenuPanel.add(subItem);
        }
        
        add(subMenuPanel, "h 0!", indexZorder + 1);
        revalidate();
        repaint();
        
        openSubMenus.put(index, new MenuSubMenuInfo(item, subMenuPanel));
        
        MenuAnimation.showMenu(subMenuPanel, item, layout, true);
    }
    
    private void hideMenu(MenuItem item, int index) {
        MenuSubMenuInfo info = openSubMenus.get(index);
        if (info != null) {
            info.subMenuPanel.setName(null);
            MenuAnimation.showMenu(info.subMenuPanel, item, layout, false);
            openSubMenus.remove(index);
        }
    }
    
    private boolean isSubMenuOpen(int index) {
        MenuSubMenuInfo info = openSubMenus.get(index);
        return info != null && info.subMenuPanel.getHeight() > 0;
    }
    
    private void closeAllSubMenusExcept(int exceptIndex) {
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
            case 4: return "FINE";     
            case 5: return "IMPORT";   
            case 6: return "USER";     
            case 7: return "REPORT";   
            default: return "DASHBOARD";
        }
    }
    
    private String mapToScreenName(int parentIndex, int subIndex) {
        String parent = mapToScreenName(parentIndex);
        return parent + "_" + subIndex;
    }
}
