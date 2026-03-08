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
        setBackground(new Color(245, 245, 250)); 
        setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setOpaque(false);
        pnlHeader.setBorder(new EmptyBorder(0, 0, 20, 0));
        
        JLabel lblTitle = new JLabel("DASHBOARD TỔNG QUAN");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(new Color(21, 110, 71));
        
        JLabel lblSub = new JLabel("Cập nhật số liệu thời gian thực từ hệ thống LibraCore");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSub.setForeground(new Color(100, 100, 100));
        
        JPanel pnlTitle = new JPanel(new GridLayout(2, 1));
        pnlTitle.setOpaque(false);
        pnlTitle.add(lblTitle);
        pnlTitle.add(lblSub);
        pnlHeader.add(pnlTitle, BorderLayout.WEST);
        
        JButton btnRefresh = new JButton("Làm mới dữ liệu");
        btnRefresh.setIcon(FontIcon.of(FontAwesomeSolid.SYNC_ALT, 16, Color.WHITE));
        btnRefresh.setBackground(new Color(21, 110, 71));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRefresh.setFocusPainted(false);
        btnRefresh.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRefresh.setPreferredSize(new Dimension(160, 40));
        btnRefresh.addActionListener(e -> loadData()); 
        
        JPanel pnlHeaderRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlHeaderRight.setOpaque(false);
        pnlHeaderRight.add(btnRefresh);
        pnlHeader.add(pnlHeaderRight, BorderLayout.EAST);
        
        add(pnlHeader, BorderLayout.NORTH);
        
        JPanel pnlCards = new JPanel(new MigLayout("wrap 4, insets 0, gap 20", "[grow, fill][grow, fill][grow, fill][grow, fill]", "[160!]"));
        pnlCards.setOpaque(false);
        
        lblTongSachVal = createValueLabel();
        lblTongDocGiaVal = createValueLabel();
        lblPhieuMuonVal = createValueLabel();
        lblDoanhThuVal = createValueLabel();
        

        pnlCards.add(createCard("Tổng Đầu Sách", lblTongSachVal, FontAwesomeSolid.BOOK, new Color(41, 128, 185))); 
        pnlCards.add(createCard("Độc Giả Hoạt Động", lblTongDocGiaVal, FontAwesomeSolid.USER_GRADUATE, new Color(39, 174, 96))); 
        pnlCards.add(createCard("Phiếu Đang Mượn", lblPhieuMuonVal, FontAwesomeSolid.CLIPBOARD_LIST, new Color(243, 156, 18))); 
        pnlCards.add(createCard("Doanh Thu Phạt", lblDoanhThuVal, FontAwesomeSolid.MONEY_BILL_WAVE, new Color(192, 57, 43)));
        
        JPanel pnlCenter = new JPanel(new BorderLayout());
        pnlCenter.setOpaque(false);
        pnlCenter.add(pnlCards, BorderLayout.NORTH);
        
        JLabel lblChartPlaceholder = new JLabel("Khu vực hiển thị biểu đồ/bảng dữ liệu (Cập nhật sau)", SwingConstants.CENTER);
        lblChartPlaceholder.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        lblChartPlaceholder.setForeground(new Color(180, 180, 180));
        pnlCenter.add(lblChartPlaceholder, BorderLayout.CENTER);

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
        lblTitle.setForeground(new Color(255, 255, 255, 220)); 
        JLabel lblIcon = new JLabel();
        lblIcon.setIcon(FontIcon.of(iconType, 50, new Color(255, 255, 255, 100))); 
        card.add(lblTitle, "wrap");         
        card.add(lblValue, "growy, bottom"); 
        card.add(lblIcon, "dock east");      
        return card;
    }
}