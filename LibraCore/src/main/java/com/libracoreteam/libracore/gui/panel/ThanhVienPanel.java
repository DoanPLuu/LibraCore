package com.libracoreteam.libracore.gui.panel;

import com.libracoreteam.libracore.bus.DocGiaBUS;
import com.libracoreteam.libracore.model.DocGia;
import com.libracoreteam.libracore.gui.dialog.ThanhVienDialog;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ThanhVienPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private DocGiaBUS docGiaBUS;
    private List<DocGia> listDocGia;

    public ThanhVienPanel() {
        docGiaBUS = new DocGiaBUS();
        initComponents();
        loadData();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);

        // Header (Tiêu đề + Nút Thêm/Sửa/Xóa)
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel("QUẢN LÝ ĐỘC GIẢ", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(0, 102, 51));
        pnlHeader.add(lblTitle, BorderLayout.NORTH);

        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlButtons.setBackground(Color.WHITE);

        JButton btnThem = new JButton("Thêm mới");
        btnThem.addActionListener(e -> openDialog(null));

        JButton btnSua = new JButton("Sửa");
        btnSua.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) openDialog(listDocGia.get(row));
            else JOptionPane.showMessageDialog(this, "Chọn độc giả cần sửa!");
        });

        JButton btnXoa = new JButton("Xóa");
        btnXoa.setBackground(Color.RED);
        btnXoa.setForeground(Color.WHITE);
        btnXoa.addActionListener(e -> deleteDocGia());

        pnlButtons.add(btnThem);
        pnlButtons.add(btnSua);
        pnlButtons.add(btnXoa);
        pnlHeader.add(pnlButtons, BorderLayout.SOUTH);

        add(pnlHeader, BorderLayout.NORTH);

        // Table
        String[] cols = {"ID", "Họ Tên", "Ngày Sinh", "Giới Tính", "SĐT", "Email", "Địa Chỉ"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(tableModel);
        table.setRowHeight(30);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void loadData() {
        listDocGia = docGiaBUS.getAllDocGia();
        tableModel.setRowCount(0);
        for (DocGia dg : listDocGia) {
            tableModel.addRow(new Object[]{
                    dg.getIdDocGia(), dg.getTenDocGia(), dg.getNgaySinh(),
                    dg.getSdt(), dg.getEmail(), dg.getDiaChi()
            });
        }
    }

    private void openDialog(DocGia dg) {
        ThanhVienDialog dialog = new ThanhVienDialog((JFrame) SwingUtilities.getWindowAncestor(this), dg);
        dialog.setVisible(true);
        if (dialog.isSuccess()) { // Nếu lưu thành công thì load lại bảng
            loadData();
        }
    }

    private void deleteDocGia() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần xóa!");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Xóa độc giả này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String msg = docGiaBUS.delete(listDocGia.get(row).getIdDocGia());
            JOptionPane.showMessageDialog(this, msg);
            loadData();
        }
    }
}