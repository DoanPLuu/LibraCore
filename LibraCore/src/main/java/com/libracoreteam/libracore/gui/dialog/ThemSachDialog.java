package com.libracoreteam.libracore.gui.dialog;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class ThemSachDialog extends JDialog {

    // ===== Fields =====
    private JTextField txtTenSach;
    private JTextField txtNamXB;
    private JTextField txtSoTrang;
    private JTextField txtGiaSach;

    private JTextArea txtMoTa;

    private JComboBox<String> cboNXB;

    private JList<String> lstTacGia;
    private JList<String> lstTheLoai;

    private JButton btnLuu;
    private JButton btnHuy;

    public ThemSachDialog(Frame parent, boolean modal) {
        super(parent, "Thêm sách", modal);
        initComponents();
    }

    private void initComponents() {

        JPanel formPanel = new JPanel(
                new MigLayout(
                        "wrap 2, insets 15, gapx 10, gapy 8",
                        "[right][grow, fill]",
                        "[]"
                )
        );

        // ===== Tên sách =====
        txtTenSach = new JTextField(25);
        formPanel.add(new JLabel("Tên sách:"));
        formPanel.add(txtTenSach);

        // ===== Tác giả (nhiều) =====
        lstTacGia = new JList<>(new DefaultListModel<>());
        lstTacGia.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        lstTacGia.setVisibleRowCount(3);

        // mock data
        DefaultListModel<String> tacGiaModel = (DefaultListModel<String>) lstTacGia.getModel();
        tacGiaModel.addElement("Nguyễn Nhật Ánh");
        tacGiaModel.addElement("J.K. Rowling");
        tacGiaModel.addElement("Haruki Murakami");

        JScrollPane spTacGia = new JScrollPane(lstTacGia);

        formPanel.add(new JLabel("Tác giả:"));
        formPanel.add(spTacGia, "hmin 70");

        // ===== Thể loại (nhiều) =====
        lstTheLoai = new JList<>(new DefaultListModel<>());
        lstTheLoai.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        lstTheLoai.setVisibleRowCount(3);

        DefaultListModel<String> theLoaiModel = (DefaultListModel<String>) lstTheLoai.getModel();
        theLoaiModel.addElement("Văn học");
        theLoaiModel.addElement("Thiếu nhi");
        theLoaiModel.addElement("Khoa học");
        theLoaiModel.addElement("Giả tưởng");

        JScrollPane spTheLoai = new JScrollPane(lstTheLoai);

        formPanel.add(new JLabel("Thể loại:"));
        formPanel.add(spTheLoai, "hmin 70");

        // ===== Nhà xuất bản =====
        cboNXB = new JComboBox<>();
        cboNXB.addItem("NXB Kim Đồng");
        cboNXB.addItem("NXB Trẻ");
        cboNXB.addItem("NXB Giáo Dục");

        formPanel.add(new JLabel("Nhà xuất bản:"));
        formPanel.add(cboNXB);

        // ===== Năm xuất bản =====
        txtNamXB = new JTextField(10);
        formPanel.add(new JLabel("Năm xuất bản:"));
        formPanel.add(txtNamXB);

        // ===== Số trang =====
        txtSoTrang = new JTextField(10);
        formPanel.add(new JLabel("Số trang:"));
        formPanel.add(txtSoTrang);

        // ===== Mô tả (cao + scroll) =====
        txtMoTa = new JTextArea(4, 25);
        txtMoTa.setLineWrap(true);
        txtMoTa.setWrapStyleWord(true);

        JScrollPane spMoTa = new JScrollPane(
                txtMoTa,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        formPanel.add(new JLabel("Mô tả:"));
        formPanel.add(spMoTa, "hmin 90");

        // ===== Giá sách =====
        txtGiaSach = new JTextField(10);
        formPanel.add(new JLabel("Giá sách:"));
        formPanel.add(txtGiaSach);

        // ===== Buttons =====
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnLuu = new JButton("Xác nhận");
        btnHuy = new JButton("Hủy");

        buttonPanel.add(btnLuu);
        buttonPanel.add(btnHuy);

        btnLuu.addActionListener(e -> onSave());
        btnHuy.addActionListener(e -> dispose());

        // ===== Layout tổng =====
        setLayout(new BorderLayout());
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(getParent());
        setResizable(false);
    }

    private void onSave() {
        System.out.println("Tên sách: " + txtTenSach.getText());
        System.out.println("NXB: " + cboNXB.getSelectedItem());
        System.out.println("Năm XB: " + txtNamXB.getText());
        System.out.println("Số trang: " + txtSoTrang.getText());
        System.out.println("Giá: " + txtGiaSach.getText());
        System.out.println("Mô tả: " + txtMoTa.getText());

        System.out.println("Tác giả:");
        for (String tg : lstTacGia.getSelectedValuesList()) {
            System.out.println(" - " + tg);
        }

        System.out.println("Thể loại:");
        for (String tl : lstTheLoai.getSelectedValuesList()) {
            System.out.println(" - " + tl);
        }

        dispose();
    }
}
