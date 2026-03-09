package com.libracoreteam.libracore.gui.panel;

import com.libracoreteam.libracore.bus.ThongKeBUS;
import net.miginfocom.swing.MigLayout;
import org.kordamp.ikonli.swing.FontIcon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


public class DashboardPanel extends JPanel {
    
    private ThongKeBUS thongKeBUS;
    
    // Các nhãn hiển thị số liệu thực tế
    private JLabel lblTongSachVal;
    private JLabel lblTongDocGiaVal;
    private JLabel lblPhieuMuonVal;
    private JLabel lblDoanhThuVal;

    public DashboardPanel() {
        thongKeBUS = new ThongKeBUS();
        initComponents();
        loadData();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 250)); // Màu nền xám nhạt hiện đại
        setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // ========== HEADER ==========
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setOpaque(false);
        pnlHeader.setBorder(new EmptyBorder(0, 0, 20, 0));
        
        JLabel lblTitle = new JLabel("DASHBOARD TỔNG QUAN");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(new Color(21, 110, 71)); // Màu xanh chủ đạo của hệ thống
        JPanel pnlTitle = new JPanel(new GridLayout(2, 1));
        pnlTitle.setOpaque(false);
        pnlTitle.add(lblTitle);
        pnlHeader.add(pnlTitle, BorderLayout.WEST);
        
        // Nút Refresh (Làm mới số liệu)
        JButton btnRefresh = new JButton("Làm mới dữ liệu");
        btnRefresh.setIcon(FontIcon.of(FontAwesomeSolid.SYNC_ALT, 16, Color.WHITE));
        btnRefresh.setBackground(new Color(21, 110, 71));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRefresh.setFocusPainted(false);
        btnRefresh.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRefresh.setPreferredSize(new Dimension(160, 40));
        btnRefresh.addActionListener(e -> loadData()); // Click vào là gọi lại DB
        
        JPanel pnlHeaderRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlHeaderRight.setOpaque(false);
        pnlHeaderRight.add(btnRefresh);
        pnlHeader.add(pnlHeaderRight, BorderLayout.EAST);
        
        add(pnlHeader, BorderLayout.NORTH);
        
        // ========== CARDS (THẺ THỐNG KÊ) ==========
        // Dùng MigLayout chia 4 cột đều nhau, gap 20px, chiều cao card cố định 160px
        JPanel pnlCards = new JPanel(new MigLayout("wrap 4, insets 0, gap 20", "[grow, fill][grow, fill][grow, fill][grow, fill]", "[160!]"));
        pnlCards.setOpaque(false);
        
        // Khởi tạo các Label chứa dữ liệu
        lblTongSachVal = createValueLabel();
        lblTongDocGiaVal = createValueLabel();
        lblPhieuMuonVal = createValueLabel();
        lblDoanhThuVal = createValueLabel();
        

        // Thêm 4 thẻ với 4 màu khác nhau cho sinh động
        pnlCards.add(createCard("Tổng Đầu Sách", lblTongSachVal, FontAwesomeSolid.BOOK, new Color(41, 128, 185))); // Xanh dương
        pnlCards.add(createCard("Độc Giả Hoạt Động", lblTongDocGiaVal, FontAwesomeSolid.USER_GRADUATE, new Color(39, 174, 96))); // Xanh lá
        pnlCards.add(createCard("Phiếu Đang Mượn", lblPhieuMuonVal, FontAwesomeSolid.CLIPBOARD_LIST, new Color(243, 156, 18))); // Cam
        pnlCards.add(createCard("Doanh Thu Phạt", lblDoanhThuVal, FontAwesomeSolid.MONEY_BILL_WAVE, new Color(192, 57, 43))); // Đỏ
        
        // Thêm phần Cards lên Top của vùng Center
        JPanel pnlCenter = new JPanel(new BorderLayout());
        pnlCenter.setOpaque(false);
        pnlCenter.add(pnlCards, BorderLayout.NORTH);
        JLabel lblIntro = new JLabel("LIBRACORE - PHẦN MỀM QUẢN LÝ THƯ VIỆN", SwingConstants.CENTER);
        lblIntro.setFont(new Font("Segoe UI", Font.BOLD, 40));
        lblIntro.setForeground(new Color(41, 128, 185));
        pnlCenter.add(lblIntro, BorderLayout.CENTER);
        add(pnlCenter, BorderLayout.CENTER);
    }
    
    public void loadData() {
        try {
            lblTongSachVal.setText(String.valueOf(thongKeBUS.getTongSoSach()));
            lblTongDocGiaVal.setText(String.valueOf(thongKeBUS.getTongDocGia()));
            lblPhieuMuonVal.setText(String.valueOf(thongKeBUS.getSoPhieuDangMuon()));
            lblDoanhThuVal.setText(thongKeBUS.getTongDoanhThuPhatFormatted());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi kết nối CSDL khi tải dữ liệu thống kê!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JLabel createValueLabel() {
        JLabel lbl = new JLabel("0");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 40));
        lbl.setForeground(Color.WHITE);
        return lbl;
    }

    private JPanel createCard(String title, JLabel lblValue, FontAwesomeSolid iconType, Color bgColor) {
        JPanel card = new JPanel(new MigLayout("insets 20", "[grow][]", "[][grow]"));
        card.setBackground(bgColor);
        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(new Color(255, 255, 255, 220)); // Trắng mờ
        JLabel lblIcon = new JLabel();
        lblIcon.setIcon(FontIcon.of(iconType, 50, new Color(255, 255, 255, 100))); // Trắng mờ 100
        card.add(lblTitle, "wrap");           // Tiêu đề ở trên, wrap xuống dòng
        card.add(lblValue, "growy, bottom");  // Số liệu chìm xuống dưới
        card.add(lblIcon, "dock east");       // Icon neo vào mép phải
        return card;
    }
}