package com.libracoreteam.libracore.gui.dialog;

import com.libracoreteam.libracore.bus.MuonTraBUS;
import com.libracoreteam.libracore.bus.TheThanhVienBUS;
import com.libracoreteam.libracore.model.PhieuMuon;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaoPhieuMuonDialog extends JDialog {
    private MuonTraBUS muonTraBUS;
    private TheThanhVienBUS theThanhVienBUS;
    private DefaultTableModel tableModel;
    private List<Integer> listIdSachMuon;

    private JTextField txtIdThe;
    private JTextField txtTenDocGia;
    private JTextField txtIdCuonSach;
    private JLabel lblTongSach;

    public TaoPhieuMuonDialog(JFrame parent) {
        super(parent, "Tạo Phiếu Mượn Sách", true);
        muonTraBUS = new MuonTraBUS();
        theThanhVienBUS = new TheThanhVienBUS();
        listIdSachMuon = new ArrayList<>();

        initComponents();
        setSize(900, 600);
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));


        JPanel pnlLeft = new JPanel();
        pnlLeft.setLayout(new BoxLayout(pnlLeft, BoxLayout.Y_AXIS));
        pnlLeft.setBorder(BorderFactory.createTitledBorder("Thông tin Thẻ & Độc giả"));
        pnlLeft.setPreferredSize(new Dimension(300, 0));
        pnlLeft.setBackground(Color.WHITE);

        pnlLeft.add(new JLabel("Mã thẻ thành viên:"));
        JPanel pnlTimThe = new JPanel(new BorderLayout(5, 0));
        pnlTimThe.setBackground(Color.WHITE);
        txtIdThe = new JTextField();
        JButton btnCheckThe = new JButton("Kiểm tra");
        btnCheckThe.addActionListener(e -> checkTheThanhVien());
        pnlTimThe.add(txtIdThe, BorderLayout.CENTER);
        pnlTimThe.add(btnCheckThe, BorderLayout.EAST);
        pnlLeft.add(pnlTimThe);
        pnlLeft.add(Box.createVerticalStrut(10));

        pnlLeft.add(new JLabel("Tên độc giả:"));
        txtTenDocGia = new JTextField();
        txtTenDocGia.setEditable(false);
        pnlLeft.add(txtTenDocGia);
        pnlLeft.add(Box.createVerticalStrut(20));

        pnlLeft.add(new JLabel("Ngày mượn: " + LocalDate.now()));
        pnlLeft.add(Box.createVerticalStrut(5));
        pnlLeft.add(new JLabel("Hẹn trả: " + LocalDate.now().plusDays(14))); // Mặc định 2 tuần

        add(pnlLeft, BorderLayout.WEST);

        // --- PANEL GIỮA: CHỌN SÁCH ---
        JPanel pnlCenter = new JPanel(new BorderLayout(5, 5));
        pnlCenter.setBorder(BorderFactory.createTitledBorder("Danh sách sách muốn mượn"));


        JPanel pnlScan = new JPanel(new BorderLayout(5, 0));
        pnlScan.add(new JLabel("Nhập mã cuốn sách (ID): "), BorderLayout.WEST);
        txtIdCuonSach = new JTextField();
        txtIdCuonSach.addActionListener(e -> themSachVaoGio()); // Enter là thêm
        JButton btnThemSach = new JButton("Thêm");
        btnThemSach.addActionListener(e -> themSachVaoGio());
        pnlScan.add(txtIdCuonSach, BorderLayout.CENTER);
        pnlScan.add(btnThemSach, BorderLayout.EAST);
        pnlCenter.add(pnlScan, BorderLayout.NORTH);

        String[] cols = {"Mã cuốn", "Tên sách", "Trạng thái"};
        tableModel = new DefaultTableModel(cols, 0);
        JTable table = new JTable(tableModel);
        pnlCenter.add(new JScrollPane(table), BorderLayout.CENTER);

        JButton btnXoaSach = new JButton("Xóa dòng chọn");
        btnXoaSach.addActionListener(e -> xoaSachKhoiGio(table.getSelectedRow()));
        pnlCenter.add(btnXoaSach, BorderLayout.SOUTH);

        add(pnlCenter, BorderLayout.CENTER);

        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        lblTongSach = new JLabel("Tổng số sách: 0  ");
        lblTongSach.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JButton btnLuu = new JButton("Lưu Phiếu Mượn");
        btnLuu.setBackground(new Color(0, 153, 76));
        btnLuu.setForeground(Color.WHITE);
        btnLuu.setPreferredSize(new Dimension(150, 40));
        btnLuu.addActionListener(e -> luuPhieuMuon());

        JButton btnHuy = new JButton("Hủy bỏ");
        btnHuy.addActionListener(e -> dispose());

        pnlBottom.add(lblTongSach);
        pnlBottom.add(btnLuu);
        pnlBottom.add(btnHuy);
        add(pnlBottom, BorderLayout.SOUTH);
    }

    private void checkTheThanhVien() {
        try {
            int idThe = Integer.parseInt(txtIdThe.getText());
            String loi = muonTraBUS.kiemTraTheKhaDung(idThe);
            if (loi != null) {
                JOptionPane.showMessageDialog(this, loi, "Lỗi thẻ", JOptionPane.WARNING_MESSAGE);
                txtTenDocGia.setText("");
                txtTenDocGia.setForeground(Color.BLACK);
            } else {
                String tenDG = muonTraBUS.getTenDocGiaByThe(idThe);
                txtTenDocGia.setText(tenDG + " (Hợp lệ)");
                txtTenDocGia.setForeground(new Color(0, 102, 51)); // Màu xanh lá cây
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Mã thẻ phải là số!");
        }
    }

    private void themSachVaoGio() {
        try {
            int idCuon = Integer.parseInt(txtIdCuonSach.getText());

            // 1. Check trùng
            if (listIdSachMuon.contains(idCuon)) {
                JOptionPane.showMessageDialog(this, "Sách này đã có trong danh sách!");
                return;
            }

            String tenSach = muonTraBUS.getTenCuonSach(idCuon);
            if (tenSach == null || tenSach.equals("Sách bận hoặc hỏng")) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy sách hoặc sách đang bận!");
                return;
            }

            listIdSachMuon.add(idCuon);
            tableModel.addRow(new Object[]{idCuon, tenSach, "Sẵn sàng"});
            lblTongSach.setText("Tổng số sách: " + listIdSachMuon.size());
            txtIdCuonSach.setText("");
            txtIdCuonSach.requestFocus();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Mã sách phải là số!");
        }
    }


    private void xoaSachKhoiGio(int row) {
        if (row != -1) {
            listIdSachMuon.remove(row);
            tableModel.removeRow(row);
            lblTongSach.setText("Tổng số sách: " + listIdSachMuon.size());
        }
    }


    private void luuPhieuMuon() {
        if (txtIdThe.getText().isEmpty() || listIdSachMuon.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập thẻ và ít nhất 1 cuốn sách!");
            return;
        }

        int idThe = Integer.parseInt(txtIdThe.getText());

        PhieuMuon pm = new PhieuMuon();
        pm.setIdTheThanhVien(idThe);
        pm.setIdNhanVien(1);
        pm.setNgayMuon(LocalDate.now());
        pm.setNgayHenTra(LocalDate.now().plusDays(14));

        boolean ketQua = muonTraBUS.muonSach(pm, listIdSachMuon);

        if (ketQua) {
            JOptionPane.showMessageDialog(this, "Tạo phiếu mượn thành công!");
            dispose(); // Đóng dialog
        } else {
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu phiếu!");
        }
    }
}