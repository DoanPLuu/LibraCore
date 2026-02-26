package com.libracoreteam.libracore.gui;

import com.libracoreteam.libracore.gui.panel.*;

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
    private SachPanel sachPanel;
    private NXBPanel nxbPanel;
    // TODO: Khai báo các panel khác khi tạo
    private CuonSachPanel cuonSachPanel;
    private TacGiaPanel tacGiaPanel;
    private TheLoaiPanel theLoaiPanel;
    private ThanhVienPanel thanhVienPanel;
    private TheThanhVienPanel theThanhVienPanel;
    private MuonTraSachPanel muonTraSachPanel;
    // private PhatPanel phatPanel;
    // private MucPhatPanel mucPhatPanel;
    private NhapSachPanel nhapSachPanel;

    private NCCPanel nccPanel;
    // private NguoiDungPanel nguoiDungPanel;
    // private TaiKhoanPanel taiKhoanPanel;
    // private VaiTroPanel vaiTroPanel;
    // private ThongKeSachPanel thongKeSachPanel;
    // private ThongKeMuonTraPanel thongKeMuonTraPanel;
    // private ThongKeTienPhatPanel thongKeTienPhatPanel;

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

        setMinimumSize(new Dimension(1200, 800));
        setSize(1600, 900);
        setLocationRelativeTo(null);
    }

    /**
     * Tạo các content panels và thêm vào CardLayout
     */
    private void createContentPanels() {
        // ========== DASHBOARD ==========
        dashboardPanel = new DashboardPanel();
        contentPanel.add(dashboardPanel, "DASHBOARD");

        // ========== QUẢN LÝ SÁCH ==========
        // BOOK: Parent menu (có thể không cần dùng, nhưng thêm để đảm bảo)
        contentPanel.add(createPlaceholderPanel("Quản lý sách"), "BOOK");

        sachPanel = new SachPanel();
        contentPanel.add(sachPanel, "BOOK_1");

        cuonSachPanel = new CuonSachPanel();
        contentPanel.add(cuonSachPanel, "BOOK_2");

        tacGiaPanel = new TacGiaPanel();
        contentPanel.add(tacGiaPanel, "BOOK_3");

        nxbPanel = new NXBPanel();
        contentPanel.add(nxbPanel, "BOOK_4");

        theLoaiPanel = new TheLoaiPanel();
        contentPanel.add(theLoaiPanel, "BOOK_5");

        // ========== QUẢN LÝ THÀNH VIÊN ==========
        // MEMBER: Parent menu
        contentPanel.add(createPlaceholderPanel("Quản lý thành viên"), "MEMBER");

        // MEMBER_1: Thành viên
        thanhVienPanel = new ThanhVienPanel();
        contentPanel.add(thanhVienPanel, "MEMBER_1");

        // MEMBER_2: Thẻ thành viên
        theThanhVienPanel = new TheThanhVienPanel();
        contentPanel.add(theThanhVienPanel, "MEMBER_2");

        // ========== QUẢN LÝ MƯỢN - TRẢ ==========
        // BORROW: Parent menu
        contentPanel.add(createPlaceholderPanel("Quản lý mượn - trả"), "BORROW");

        // BORROW_1: Mượn - trả sách
        muonTraSachPanel = new MuonTraSachPanel();
        contentPanel.add(muonTraSachPanel, "BORROW_1");

        // ========== QUẢN LÝ PHẠT - TRẢ PHẠT ==========
        // FINE: Parent menu
        contentPanel.add(createPlaceholderPanel("Quản lý phạt - trả phạt"), "FINE");

        // FINE_1: Phạt
        // phatPanel = new PhatPanel();
        // contentPanel.add(phatPanel, "FINE_1");
        contentPanel.add(createPlaceholderPanel("Phạt"), "FINE_1");

        // FINE_2: Mức phạt
        // mucPhatPanel = new MucPhatPanel();
        // contentPanel.add(mucPhatPanel, "FINE_2");
        contentPanel.add(createPlaceholderPanel("Mức phạt"), "FINE_2");

        // ========== QUẢN LÝ NHẬP SÁCH ==========
        // IMPORT: Parent menu
        contentPanel.add(createPlaceholderPanel("Quản lý nhập sách"), "IMPORT");
        nhapSachPanel = new NhapSachPanel();
        contentPanel.add(nhapSachPanel, "IMPORT_1");

        // IMPORT_2: Nhà cung cấp
        nccPanel = new NCCPanel();
        contentPanel.add(nccPanel, "IMPORT_2");

        // ========== QUẢN LÝ NGƯỜI DÙNG ==========
        // USER: Parent menu
        contentPanel.add(createPlaceholderPanel("Quản lý người dùng"), "USER");

        // USER_1: Người dùng
        // nguoiDungPanel = new NguoiDungPanel();
        // contentPanel.add(nguoiDungPanel, "USER_1");
        contentPanel.add(createPlaceholderPanel("Người dùng"), "USER_1");

        // USER_2: Tài khoản
        // taiKhoanPanel = new TaiKhoanPanel();
        // contentPanel.add(taiKhoanPanel, "USER_2");
        contentPanel.add(createPlaceholderPanel("Tài khoản"), "USER_2");

        // USER_3: Vai trò
        // vaiTroPanel = new VaiTroPanel();
        // contentPanel.add(vaiTroPanel, "USER_3");
        contentPanel.add(createPlaceholderPanel("Vai trò"), "USER_3");

        // ========== THỐNG KÊ BÁO CÁO ==========
        // REPORT: Parent menu
        contentPanel.add(createPlaceholderPanel("Thống kê báo cáo"), "REPORT");

        // REPORT_1: Thống kê sách
        // thongKeSachPanel = new ThongKeSachPanel();
        // contentPanel.add(thongKeSachPanel, "REPORT_1");
        contentPanel.add(createPlaceholderPanel("Thống kê sách"), "REPORT_1");

        // REPORT_2: Thống kê mượn trả sách
        // thongKeMuonTraPanel = new ThongKeMuonTraPanel();
        // contentPanel.add(thongKeMuonTraPanel, "REPORT_2");
        contentPanel.add(createPlaceholderPanel("Thống kê mượn trả sách"), "REPORT_2");

        // REPORT_3: Thống kê tiền phạt
        // thongKeTienPhatPanel = new ThongKeTienPhatPanel();
        // contentPanel.add(thongKeTienPhatPanel, "REPORT_3");
        contentPanel.add(createPlaceholderPanel("Thống kê tiền phạt"), "REPORT_3");
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
        try {
            cardLayout.show(contentPanel, screenName);
            System.out.println("✓ Đã chuyển sang màn hình: " + screenName);
        } catch (Exception e) {
            System.err.println("✗ Không tìm thấy màn hình: " + screenName);
            // Fallback về Dashboard
            cardLayout.show(contentPanel, "DASHBOARD");
        }
    }
}