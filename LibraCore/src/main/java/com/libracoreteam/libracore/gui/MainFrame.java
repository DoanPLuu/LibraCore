package com.libracoreteam.libracore.gui;

import com.libracoreteam.libracore.gui.panel.MenuPanel;
import com.libracoreteam.libracore.gui.panel.DashboardPanel;
import com.libracoreteam.libracore.gui.panel.BookManagementPanel;
import com.libracoreteam.libracore.gui.panel.TestPanel;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author luuis
 */
public class MainFrame extends javax.swing.JFrame {
    private MenuPanel menuPanel;
    private JPanel contentPanel;
    private CardLayout cardLayout;
    
    // Các panel sẽ được thêm vào CardLayout
    private DashboardPanel dashboardPanel;
    private BookManagementPanel bookPanel;
    // Các panel khác sẽ thêm sau...
    
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
        
        // Panel phải - Content với CardLayout
        contentPanel = new JPanel();
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);
        contentPanel.setBackground(Color.WHITE);
        
        // Tạo các panel placeholder
        createContentPanels();
        
        // Hiển thị panel mặc định
        cardLayout.show(contentPanel, "DASHBOARD");
        
        add(contentPanel, BorderLayout.CENTER);
        
        setMinimumSize(new Dimension(1200, 700));
        setSize(1600, 900);
        setLocationRelativeTo(null);
       
    }
    
    /**
     * Tạo các content panels và thêm vào CardLayout
     */
    private void createContentPanels() {
        // Dashboard panel
        TestPanel testPanel = new TestPanel();
        contentPanel.add(testPanel, "DASHBOARD"); // Thay thế DashboardPanel
        
        // Book management panel (tạm thời là placeholder)
        bookPanel = new BookManagementPanel();
        contentPanel.add(bookPanel, "BOOK");
        
        // Các panel khác sẽ thêm sau (Member, Borrow, Report, System)
        // Tạm thời tạo placeholder panels
        contentPanel.add(createPlaceholderPanel("Quản lý độc giả"), "MEMBER");
        contentPanel.add(createPlaceholderPanel("Mượn trả"), "BORROW");
        contentPanel.add(createPlaceholderPanel("Báo cáo"), "REPORT");
        contentPanel.add(createPlaceholderPanel("Hệ thống"), "SYSTEM");
        
        // Các submenu panels (tạm thời)
        contentPanel.add(createPlaceholderPanel("Danh sách sách"), "BOOK_1");
        contentPanel.add(createPlaceholderPanel("Thêm sách mới"), "BOOK_2");
        contentPanel.add(createPlaceholderPanel("Thống kê sách"), "BOOK_3");
        // ... các submenu khác sẽ thêm sau
    }
    
    /**
     * Tạo placeholder panel đơn giản (tạm thời)
     */
    private JPanel createPlaceholderPanel(String title) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        JLabel lblTitle = new JLabel(title, SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(100, 100, 100));
        
        panel.add(lblTitle, BorderLayout.CENTER);
        return panel;
    }
    
    /**
     * Chuyển đổi giữa các màn hình
     * Được gọi từ MenuPanel khi click menu item
     */
    public void showScreen(String screenName) {
        // Kiểm tra xem card có tồn tại không
        Component card = null;
        for (Component comp : contentPanel.getComponents()) {
            if (comp.getName() != null && comp.getName().equals(screenName)) {
                card = comp;
                break;
            }
        }
        
        // Nếu không tìm thấy, thử tìm bằng cách khác
        if (card == null) {
            try {
                cardLayout.show(contentPanel, screenName);
                System.out.println("✓ Đã chuyển sang màn hình: " + screenName);
            } catch (Exception e) {
                System.err.println("✗ Không tìm thấy màn hình: " + screenName);
                // Fallback về Dashboard
                cardLayout.show(contentPanel, "DASHBOARD");
            }
        } else {
            cardLayout.show(contentPanel, screenName);
            System.out.println("✓ Đã chuyển sang màn hình: " + screenName);
        }
    }
}
