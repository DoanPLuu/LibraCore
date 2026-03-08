package com.libracoreteam.libracore.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.libracoreteam.libracore.gui.panel.CuonSachPanel;
import com.libracoreteam.libracore.gui.panel.DashboardPanel;
import com.libracoreteam.libracore.gui.panel.MenuPanel;
import com.libracoreteam.libracore.gui.panel.MucPhatPanel;
import com.libracoreteam.libracore.gui.panel.MuonTraSachPanel;
import com.libracoreteam.libracore.gui.panel.NCCPanel;
import com.libracoreteam.libracore.gui.panel.NXBPanel;
import com.libracoreteam.libracore.gui.panel.NhanVienPanel;
import com.libracoreteam.libracore.gui.panel.NhapSachPanel;
import com.libracoreteam.libracore.gui.panel.PhatPanel;
import com.libracoreteam.libracore.gui.panel.SachPanel;
import com.libracoreteam.libracore.gui.panel.TacGiaPanel;
import com.libracoreteam.libracore.gui.panel.TaiKhoanCaNhanPanel;
import com.libracoreteam.libracore.gui.panel.TaiKhoanPanel;
import com.libracoreteam.libracore.gui.panel.ThanhVienPanel;
import com.libracoreteam.libracore.gui.panel.TheLoaiPanel;
import com.libracoreteam.libracore.gui.panel.TheThanhVienPanel;
import com.libracoreteam.libracore.gui.panel.ThongKeSachPanel;
import com.libracoreteam.libracore.gui.panel.ThongKeMuonTraPanel; 
import com.libracoreteam.libracore.gui.panel.ThongKeTienPhatPanel;
import com.libracoreteam.libracore.gui.panel.VaiTroPanel;

public class MainFrame extends javax.swing.JFrame {
    private MenuPanel menuPanel;
    private JPanel contentPanel;
    private CardLayout cardLayout;

    private DashboardPanel dashboardPanel;
    private SachPanel sachPanel;
    private NXBPanel nxbPanel;
    private CuonSachPanel cuonSachPanel;
    private TacGiaPanel tacGiaPanel;
    private TheLoaiPanel theLoaiPanel;
    private ThanhVienPanel thanhVienPanel;
    private TheThanhVienPanel theThanhVienPanel;
    private NhapSachPanel nhapSachPanel;
    private NCCPanel nccPanel;

    private NhanVienPanel nhanVienPanel;
    private TaiKhoanPanel taiKhoanPanel;
    private VaiTroPanel vaiTroPanel;
    private TaiKhoanCaNhanPanel taiKhoanCaNhanPanel;

    private MuonTraSachPanel muonTraSachPanel;
    private PhatPanel phatPanel;
    private MucPhatPanel mucPhatPanel;
    private ThongKeSachPanel thongKeSachPanel;
    private ThongKeMuonTraPanel thongKeMuonTraPanel;
    private ThongKeTienPhatPanel thongKeTienPhatPanel;

    public MainFrame() {
        initComponents();
    }

    private void initComponents() {
        setTitle("LibraCore - Quản lý thư viện");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        menuPanel = new MenuPanel(this);
        add(menuPanel, BorderLayout.WEST);

        contentPanel = new JPanel();
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);
        contentPanel.setBackground(Color.WHITE);

        createContentPanels();
        cardLayout.show(contentPanel, "DASHBOARD");

        add(contentPanel, BorderLayout.CENTER);

        setMinimumSize(new Dimension(1400, 800));
        setSize(1600, 900);
        setLocationRelativeTo(null);
    }

    private void createContentPanels() {
        dashboardPanel = new DashboardPanel();
        contentPanel.add(dashboardPanel, "DASHBOARD");

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

        contentPanel.add(createPlaceholderPanel("Quản lý thành viên"), "MEMBER");

        thanhVienPanel = new ThanhVienPanel();
        contentPanel.add(thanhVienPanel, "MEMBER_1");

        theThanhVienPanel = new TheThanhVienPanel();
        contentPanel.add(theThanhVienPanel, "MEMBER_2");

        contentPanel.add(createPlaceholderPanel("Quản lý mượn - trả"), "BORROW");

        muonTraSachPanel = new MuonTraSachPanel();
        contentPanel.add(muonTraSachPanel, "BORROW_1");

        contentPanel.add(createPlaceholderPanel("Quản lý phạt - trả phạt"), "FINE");

        phatPanel = new PhatPanel();
        contentPanel.add(phatPanel, "FINE_1");

        mucPhatPanel = new MucPhatPanel();
        contentPanel.add(mucPhatPanel, "FINE_2");

        contentPanel.add(createPlaceholderPanel("Quản lý nhập sách"), "IMPORT");

        nhapSachPanel = new NhapSachPanel();
        contentPanel.add(nhapSachPanel, "IMPORT_1");

        nccPanel = new NCCPanel();
        contentPanel.add(nccPanel, "IMPORT_2");

        contentPanel.add(createPlaceholderPanel("Quản lý người dùng"), "USER");

        nhanVienPanel = new NhanVienPanel();
        contentPanel.add(nhanVienPanel, "USER_1");

        taiKhoanPanel = new TaiKhoanPanel();
        contentPanel.add(taiKhoanPanel, "USER_2");

        vaiTroPanel = new VaiTroPanel();
        contentPanel.add(vaiTroPanel, "USER_3");

        taiKhoanCaNhanPanel = new TaiKhoanCaNhanPanel();
        contentPanel.add(taiKhoanCaNhanPanel, "ACCOUNT");

        contentPanel.add(createPlaceholderPanel("Thống kê báo cáo"), "REPORT");

        thongKeSachPanel = new ThongKeSachPanel();
        contentPanel.add(thongKeSachPanel, "REPORT_1");

        thongKeMuonTraPanel = new ThongKeMuonTraPanel();
        contentPanel.add(thongKeMuonTraPanel, "REPORT_2");

        thongKeTienPhatPanel = new ThongKeTienPhatPanel();
        contentPanel.add(thongKeTienPhatPanel, "REPORT_3");
    }

    private JPanel createPlaceholderPanel(String title) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel(title, SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(100, 100, 100));

        panel.add(lblTitle, BorderLayout.CENTER);
        return panel;
    }

    public void showScreen(String screenName) {
        try {
            cardLayout.show(contentPanel, screenName);

            switch (screenName) {
                case "DASHBOARD":
                    if (dashboardPanel != null)
                        dashboardPanel.loadData();
                    break;
                case "BOOK_1":
                    if (sachPanel != null)
                        sachPanel.loadActiveToTable();
                    break;
                case "BOOK_2":
                    if (cuonSachPanel != null)
                        cuonSachPanel.loadData();
                    break;
                case "BOOK_3":
                    break;
                case "BOOK_4":
                    if (nxbPanel != null)
                        nxbPanel.loadActiveToTable();
                    break;
                case "MEMBER_1":
                    if (thanhVienPanel != null)
                        thanhVienPanel.loadData();
                    break;
                case "MEMBER_2":
                    if (theThanhVienPanel != null)
                        theThanhVienPanel.loadData();
                    break;
                case "BORROW_1":
                    if (muonTraSachPanel != null)
                        muonTraSachPanel.loadData();
                    break;
                case "FINE_1":
                    if (phatPanel != null)
                        phatPanel.loadData();
                    break;
                case "IMPORT_1":
                    if (nhapSachPanel != null)
                        nhapSachPanel.loadData();
                    break;
            }
        } catch (Exception e) {
            System.err.println("✗ Không tìm thấy màn hình: " + screenName);
            cardLayout.show(contentPanel, "DASHBOARD");
        }
    }
}