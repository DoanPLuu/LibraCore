package com.libracoreteam.libracore.gui.panel;

import com.libracoreteam.libracore.bus.MuonTraBUS;
import com.libracoreteam.libracore.model.PhieuMuon;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MuonTraSachPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private MuonTraBUS muonTraBUS;

    public MuonTraSachPanel() {
        muonTraBUS = new MuonTraBUS();
        initComponents();
        loadData();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);

        // Header
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel("QUẢN LÝ MƯỢN TRẢ", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(0, 102, 51));
        pnlHeader.add(lblTitle, BorderLayout.CENTER);

        JButton btnTaoPhieu = new JButton("+ Tạo Phiếu Mượn");
        btnTaoPhieu.setBackground(new Color(0, 153, 76));
        btnTaoPhieu.setForeground(Color.WHITE);
        btnTaoPhieu.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Tìm đoạn này trong MuonTraSachPanel.java
        btnTaoPhieu.addActionListener(e -> {
            com.libracoreteam.libracore.gui.dialog.TaoPhieuMuonDialog dialog =
                    new com.libracoreteam.libracore.gui.dialog.TaoPhieuMuonDialog(
                            (JFrame) SwingUtilities.getWindowAncestor(this)
                    );
            dialog.setVisible(true);

            loadData();
        });

        JPanel pnlBtn = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlBtn.setBackground(Color.WHITE);
        pnlBtn.add(btnTaoPhieu);
        pnlHeader.add(pnlBtn, BorderLayout.EAST);

        add(pnlHeader, BorderLayout.NORTH);

        // Table
        String[] columns = {"Mã phiếu", "Mã ĐG", "Ngày mượn", "Hẹn trả", "SL Sách", "Trạng thái"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setRowHeight(30);

        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton btnChiTiet = new JButton("Xem chi tiết / Trả sách");
        btnChiTiet.addActionListener(e -> {
            int row = table.getSelectedRow();
            if(row != -1) {
                int idPhieu = (int) table.getValueAt(row, 0);
                JOptionPane.showMessageDialog(this, "Sẽ mở chi tiết phiếu: " + idPhieu);
            }
        });
        add(btnChiTiet, BorderLayout.SOUTH);
    }

    private void loadData() {
        List<PhieuMuon> list = muonTraBUS.getAllPhieuMuon();
        tableModel.setRowCount(0);
        for (PhieuMuon pm : list) {
            tableModel.addRow(new Object[]{
                    pm.getIdPhieuMuon(),
                    pm.getIdTheThanhVien(),
                    pm.getNgayMuon(),
                    pm.getNgayHenTra(),
                    pm.getTongSoSachMuon(),
                    pm.getTrangThai()
            });
        }
    }
}