package com.libracoreteam.libracore.gui.dialog;

import com.libracoreteam.libracore.util.checklist.CheckListItem;
import com.libracoreteam.libracore.util.checklist.CheckListUtils;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;


public class ThemSachDialog extends JDialog {
 // ===== Field cơ bản =====
    private JTextField txtTenSach;
    private JTextField txtNamXB;
    private JTextField txtSoTrang;
    private JTextField txtGiaSach;

    private JTextArea txtMoTa;

    private JComboBox<String> cboNXB;

    // ===== Checkbox list =====
    private DefaultListModel<CheckListItem> tacGiaModel;
    private DefaultListModel<CheckListItem> theLoaiModel;

    private JList<CheckListItem> lstTacGia;
    private JList<CheckListItem> lstTheLoai;

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

        // ===== Tác giả (checkbox list) =====
        tacGiaModel = new DefaultListModel<>();
        // TODO: thay id mock này bằng id_TacGia lấy từ DB
        tacGiaModel.addElement(new CheckListItem(1, "Nguyễn Nhật Ánh"));
        tacGiaModel.addElement(new CheckListItem(2, "J.K. Rowling"));
        tacGiaModel.addElement(new CheckListItem(3, "Haruki Murakami"));

        lstTacGia = CheckListUtils.createCheckList(tacGiaModel);
        lstTacGia.setVisibleRowCount(3);

        JScrollPane spTacGia = new JScrollPane(lstTacGia);

        formPanel.add(new JLabel("Tác giả:"));
        formPanel.add(spTacGia, "hmin 80");

        // ===== Thể loại (checkbox list) =====
        theLoaiModel = new DefaultListModel<>();
        // TODO: thay id mock này bằng id_TheLoai lấy từ DB
        theLoaiModel.addElement(new CheckListItem(1, "Văn học"));
        theLoaiModel.addElement(new CheckListItem(2, "Thiếu nhi"));
        theLoaiModel.addElement(new CheckListItem(3, "Giả tưởng"));
        theLoaiModel.addElement(new CheckListItem(4, "Khoa học"));

        lstTheLoai = CheckListUtils.createCheckList(theLoaiModel);
        lstTheLoai.setVisibleRowCount(3);

        JScrollPane spTheLoai = new JScrollPane(lstTheLoai);

        formPanel.add(new JLabel("Thể loại:"));
        formPanel.add(spTheLoai, "hmin 80");

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

        // ===== Mô tả =====
        txtMoTa = new JTextArea(4, 25);
        txtMoTa.setLineWrap(true);
        txtMoTa.setWrapStyleWord(true);

        JScrollPane spMoTa = new JScrollPane(
                txtMoTa,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        formPanel.add(new JLabel("Mô tả:"));
        formPanel.add(spMoTa, "hmin 100");

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

        System.out.println("Tác giả đã chọn:");
        for (CheckListItem item : CheckListUtils.getSelectedItems(tacGiaModel)) {
            System.out.println(" - (" + item.getId() + ") " + item.getLabel());
            // TODO: insert Sach_TacGia với item.getId()
        }

        System.out.println("Thể loại đã chọn:");
        for (CheckListItem item : CheckListUtils.getSelectedItems(theLoaiModel)) {
            System.out.println(" - (" + item.getId() + ") " + item.getLabel());
            // TODO: insert Sach_TheLoai với item.getId()
        }

        dispose();
    }
}
