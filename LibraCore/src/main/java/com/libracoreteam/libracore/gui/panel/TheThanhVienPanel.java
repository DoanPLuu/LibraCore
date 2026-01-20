package com.libracoreteam.libracore.gui.panel;

import com.libracoreteam.libracore.bus.TheThanhVienBUS;
import com.libracoreteam.libracore.model.TheThanhVien;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TheThanhVienPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private TheThanhVienBUS theThanhVienBUS;
    private List<TheThanhVien> listThe;

    // Các component nhập liệu
    private JTextField txtIdThe;
    private JTextField txtTenDocGia;
    private JTextField txtNgayHetHan;
    private JTextField txtTrangThai;

    // Biến lưu thẻ đang chọn
    private TheThanhVien selectedThe = null;

    public TheThanhVienPanel() {
        theThanhVienBUS = new TheThanhVienBUS();
        initComponents();
        loadData();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);

        // 1. Tiêu đề
        JLabel lblTitle = new JLabel("QUẢN LÝ THẺ THÀNH VIÊN", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(0, 102, 51));
        add(lblTitle, BorderLayout.NORTH);

        // 2. Bảng danh sách (CENTER)
        String[] columns = {"Mã thẻ", "Mã ĐG", "Tên Độc Giả", "Ngày cấp", "Ngày hết hạn", "Trạng thái"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho sửa trực tiếp trên bảng
            }
        };
        table = new JTable(tableModel);
        table.setRowHeight(30);

        // Sự kiện click vào bảng
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                showDetail(table.getSelectedRow());
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // 3. Panel thao tác bên phải (EAST)
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        rightPanel.setPreferredSize(new Dimension(300, 0));

        // Form hiển thị thông tin
        rightPanel.add(new JLabel("Mã thẻ:"));
        txtIdThe = new JTextField();
        txtIdThe.setEditable(false);
        rightPanel.add(txtIdThe);
        rightPanel.add(Box.createVerticalStrut(10));

        rightPanel.add(new JLabel("Tên độc giả:"));
        txtTenDocGia = new JTextField();
        txtTenDocGia.setEditable(false);
        rightPanel.add(txtTenDocGia);
        rightPanel.add(Box.createVerticalStrut(10));

        rightPanel.add(new JLabel("Ngày hết hạn:"));
        txtNgayHetHan = new JTextField();
        txtNgayHetHan.setEditable(false);
        rightPanel.add(txtNgayHetHan);
        rightPanel.add(Box.createVerticalStrut(10));

        rightPanel.add(new JLabel("Trạng thái:"));
        txtTrangThai = new JTextField();
        txtTrangThai.setEditable(false);
        rightPanel.add(txtTrangThai);
        rightPanel.add(Box.createVerticalStrut(20));

        // Các nút chức năng
        JButton btnGiaHan = new JButton("Gia hạn 1 năm");
        btnGiaHan.setBackground(new Color(0, 153, 76));
        btnGiaHan.setForeground(Color.WHITE);
        btnGiaHan.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnGiaHan.addActionListener(e -> actionGiaHan());
        rightPanel.add(btnGiaHan);
        rightPanel.add(Box.createVerticalStrut(10));

        JButton btnKhoa = new JButton("Khóa / Mở khóa thẻ");
        btnKhoa.setBackground(new Color(204, 0, 0));
        btnKhoa.setForeground(Color.WHITE);
        btnKhoa.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnKhoa.addActionListener(e -> actionKhoaThe());
        rightPanel.add(btnKhoa);
        rightPanel.add(Box.createVerticalStrut(10));

        JButton btnLamMoi = new JButton("Làm mới danh sách");
        btnLamMoi.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnLamMoi.addActionListener(e -> loadData());
        rightPanel.add(btnLamMoi);

        add(rightPanel, BorderLayout.EAST);
    }

    // Load dữ liệu lên bảng
    private void loadData() {
        listThe = theThanhVienBUS.getAll();
        tableModel.setRowCount(0);
        for (TheThanhVien t : listThe) {
            String tenDG = theThanhVienBUS.getTenDocGia(t.getIdDocGia());
            tableModel.addRow(new Object[]{
                    t.getIdTheThanhVien(),
                    t.getIdDocGia(),
                    tenDG,
                    t.getNgayCap(),
                    t.getNgayHetHan(),
                    t.getTrangThai()
            });
        }
    }

    // Hiển thị chi tiết khi click vào bảng
    private void showDetail(int row) {
        selectedThe = listThe.get(row);
        txtIdThe.setText(String.valueOf(selectedThe.getIdTheThanhVien()));
        txtTenDocGia.setText(theThanhVienBUS.getTenDocGia(selectedThe.getIdDocGia()));
        txtNgayHetHan.setText(selectedThe.getNgayHetHan().toString());
        txtTrangThai.setText(selectedThe.getTrangThai());
    }

    // Chức năng gia hạn
    private void actionGiaHan() {
        if (selectedThe == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn thẻ cần gia hạn!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Gia hạn thẻ cho độc giả này thêm 1 năm?", "Xác nhận", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (theThanhVienBUS.giaHanThe(selectedThe, 1)) {
                JOptionPane.showMessageDialog(this, "Gia hạn thành công!");
                loadData();
                showDetail(table.getSelectedRow()); // Reload lại form
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi khi gia hạn!");
            }
        }
    }

    // Chức năng khóa/mở khóa
    private void actionKhoaThe() {
        if (selectedThe == null) return;

        String action = selectedThe.getTrangThai().equals("BiKhoa") ? "MỞ KHÓA" : "KHÓA";
        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn " + action + " thẻ này?", "Xác nhận", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (theThanhVienBUS.toggleLockCard(selectedThe)) {
                JOptionPane.showMessageDialog(this, "Đã cập nhật trạng thái thẻ!");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi cập nhật!");
            }
        }
    }
}