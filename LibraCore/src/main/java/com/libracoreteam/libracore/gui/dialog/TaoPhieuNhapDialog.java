package com.libracoreteam.libracore.gui.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import net.miginfocom.swing.MigLayout;

public class TaoPhieuNhapDialog extends JDialog {

    // --- Components ---
    // Top Panel (Form)
    private JPanel formPanel;
    private JTextField txtNhanVien;
    private JComboBox<String> cbxNCC; // Update type later to NCC
    private JTextField txtNgayNhap;
    private JTextField txtTongSoLuong;
    private JTextField txtTongTien;

    // Bottom Panel (Table & Search)
    private JPanel tablePanel;
    private JTextField txtTimKiem;
    private JButton btnThemSach;
    private JTable tblChiTiet;
    private DefaultTableModel tableModel;
    private JButton btnLuuPhieu;

    public TaoPhieuNhapDialog(JFrame parent, boolean modal) {
        super(parent, modal);
        setTitle("Tạo Phiếu Nhập Mới");
        setSize(1000, 700);
        setLocationRelativeTo(parent);
        
        initComponents();
        setupLayout();
    }

    private void initComponents() {
        // --- Form Components ---
        txtNhanVien = new JTextField(20);
        txtNhanVien.setEditable(false);
        txtNhanVien.setText("Admin"); // Demo text, update later with logged in user

        cbxNCC = new JComboBox<>();
        cbxNCC.addItem("Loading NCC..."); // Demo, load from DB later

        txtNgayNhap = new JTextField(20);
        txtNgayNhap.setEditable(false);
        txtNgayNhap.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        txtTongSoLuong = new JTextField("0", 20);
        txtTongSoLuong.setEditable(false);

        txtTongTien = new JTextField("0 đ", 20);
        txtTongTien.setEditable(false);
        txtTongTien.setFont(txtTongTien.getFont().deriveFont(Font.BOLD, 14f));
        txtTongTien.setForeground(new Color(255, 51, 51));

        // --- Table Components ---
        txtTimKiem = new JTextField(30);
        txtTimKiem.putClientProperty("JTextField.placeholderText", "Nhập tên sách để tìm kiếm...");
        
        btnThemSach = new JButton("Thêm vào phiếu");

        String[] cols = {"STT", "Mã đầu sách", "Tên sách", "Số lượng", "Đơn giá nhập", "Thành tiền", "Hành động"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Cho phép sửa cột 1 (Mã), 3 (Số lượng), 4 (Đơn giá), 6 (Hành động/Xóa)
                return column == 1 || column == 3 || column == 4 || column == 6; 
            }
        };
        tblChiTiet = new JTable(tableModel);
        tblChiTiet.setRowHeight(30);
        
        btnLuuPhieu = new JButton("Lưu Phiếu Nhập");
        btnLuuPhieu.setFont(btnLuuPhieu.getFont().deriveFont(Font.BOLD, 14f));
        btnLuuPhieu.setBackground(new Color(40, 167, 69));
        btnLuuPhieu.setForeground(Color.WHITE);
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));

        // --- Top Panel (MigLayout) ---
        formPanel = new JPanel(new MigLayout("wrap 4, insets 20", "[right][grow][right][grow]"));
        
        JLabel lblTitle = new JLabel("THÔNG TIN PHIẾU NHẬP");
        lblTitle.setFont(lblTitle.getFont().deriveFont(Font.BOLD, 18f));
        formPanel.add(lblTitle, "span 4, center, wrap, gapbottom 10");

        formPanel.add(new JLabel("Nhân viên lập:"));
        formPanel.add(txtNhanVien, "growx");
        formPanel.add(new JLabel("Tổng số lượng sách:"));
        formPanel.add(txtTongSoLuong, "growx");

        formPanel.add(new JLabel("Nhà cung cấp:"));
        formPanel.add(cbxNCC, "growx");
        formPanel.add(new JLabel("Tổng tiền:"));
        formPanel.add(txtTongTien, "growx");

        formPanel.add(new JLabel("Ngày nhập:"));
        formPanel.add(txtNgayNhap, "growx");

        add(formPanel, BorderLayout.NORTH);

        // --- Bottom Panel (MigLayout) ---
        tablePanel = new JPanel(new MigLayout("insets 20, fill", "[grow][]", "[][grow][]"));
        
        // Search line
        tablePanel.add(txtTimKiem, "split 2, growx");
        tablePanel.add(btnThemSach, "wrap");
        
        // Table
        tablePanel.add(new JScrollPane(tblChiTiet), "span 2, grow, wrap, gaptop 10, gapbottom 10");
        
        // Save line
        tablePanel.add(btnLuuPhieu, "span 2, right, width 150!, height 40!");

        add(tablePanel, BorderLayout.CENTER);
    }
}
