package com.libracoreteam.libracore.gui.dialog;

import com.libracoreteam.libracore.util.checklist.CheckListItem;
import com.libracoreteam.libracore.util.checklist.CheckListUtils;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

/**
 * Dialog sửa sách (mock để test luồng UI).
 * - Dùng MigLayout 11.4.2
 * - Có checklist Tác giả + Thể loại (multi-select)
 * - Pre-fill dữ liệu giả lập để test "sửa"
 */
public class SuaSachDialog extends JDialog {

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

    private JButton btnXacNhan;
    private JButton btnHuy;

    public SuaSachDialog(Frame parent, boolean modal) {
        super(parent, "Sửa sách", modal);
        initComponents();
        loadMockDataForEdit();
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
        // TODO: thay id mock bằng id_TacGia lấy từ DB
        tacGiaModel.addElement(new CheckListItem(1, "Nguyễn Nhật Ánh"));
        tacGiaModel.addElement(new CheckListItem(2, "J.K. Rowling"));
        tacGiaModel.addElement(new CheckListItem(3, "Haruki Murakami"));

        lstTacGia = CheckListUtils.createCheckList(tacGiaModel);
        lstTacGia.setVisibleRowCount(4);
        JScrollPane spTacGia = new JScrollPane(lstTacGia);

        formPanel.add(new JLabel("Tác giả:"), "top");
        formPanel.add(spTacGia, "hmin 90");

        // ===== Thể loại (checkbox list) =====
        theLoaiModel = new DefaultListModel<>();
        // TODO: thay id mock bằng id_TheLoai lấy từ DB
        theLoaiModel.addElement(new CheckListItem(1, "Văn học"));
        theLoaiModel.addElement(new CheckListItem(2, "Thiếu nhi"));
        theLoaiModel.addElement(new CheckListItem(3, "Giả tưởng"));
        theLoaiModel.addElement(new CheckListItem(4, "Khoa học"));

        lstTheLoai = CheckListUtils.createCheckList(theLoaiModel);
        lstTheLoai.setVisibleRowCount(4);
        JScrollPane spTheLoai = new JScrollPane(lstTheLoai);

        formPanel.add(new JLabel("Thể loại:"), "top");
        formPanel.add(spTheLoai, "hmin 90");

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

        formPanel.add(new JLabel("Mô tả:"), "top");
        formPanel.add(spMoTa, "hmin 100");

        // ===== Giá sách =====
        txtGiaSach = new JTextField(10);
        formPanel.add(new JLabel("Giá sách:"));
        formPanel.add(txtGiaSach);

        // ===== Buttons =====
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnXacNhan = new JButton("Xác nhận");
        btnHuy = new JButton("Hủy");

        buttonPanel.add(btnXacNhan);
        buttonPanel.add(btnHuy);

        btnXacNhan.addActionListener(e -> onSave());
        btnHuy.addActionListener(e -> dispose());

        setLayout(new BorderLayout());
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(getParent());
        setResizable(false);
    }

    /**
     * Mock dữ liệu để giả lập trạng thái "sửa".
     * Có thể thay bằng load từ DAO theo id_Sach sau này.
     */
    private void loadMockDataForEdit() {
        // Mock field cơ bản
        txtTenSach.setText("Harry Potter và Hòn đá Phù thủy");
        cboNXB.setSelectedItem("NXB Trẻ");
        txtNamXB.setText("2000");
        txtSoTrang.setText("320");
        txtGiaSach.setText("89000");
        txtMoTa.setText("Mock dữ liệu để test sửa sách.\nCó checklist tác giả và thể loại.");

        // Mock: chọn sẵn tác giả + thể loại
        // (VD: Rowling + Giả tưởng + Thiếu nhi)
        setSelectedById(tacGiaModel, 2, true);
        setSelectedById(theLoaiModel, 2, true);
        setSelectedById(theLoaiModel, 3, true);

        // repaint list để thấy tick ngay
        lstTacGia.repaint();
        lstTheLoai.repaint();
    }

    private static void setSelectedById(DefaultListModel<CheckListItem> model, int id, boolean selected) {
        for (int i = 0; i < model.size(); i++) {
            CheckListItem item = model.get(i);
            if (item.getId() == id) {
                item.setSelected(selected);
                return;
            }
        }
    }

    private void onSave() {
        // TODO: Validate + gọi Service/DAO để update sách + bảng nối
        System.out.println("[SuaSachDialog] Save mock:");
        System.out.println("Tên sách: " + txtTenSach.getText());
        System.out.println("NXB: " + cboNXB.getSelectedItem());
        System.out.println("Năm XB: " + txtNamXB.getText());
        System.out.println("Số trang: " + txtSoTrang.getText());
        System.out.println("Giá: " + txtGiaSach.getText());
        System.out.println("Mô tả: " + txtMoTa.getText());

        System.out.println("Tác giả đã chọn:");
        for (CheckListItem item : CheckListUtils.getSelectedItems(tacGiaModel)) {
            System.out.println(" - (" + item.getId() + ") " + item.getLabel());
        }

        System.out.println("Thể loại đã chọn:");
        for (CheckListItem item : CheckListUtils.getSelectedItems(theLoaiModel)) {
            System.out.println(" - (" + item.getId() + ") " + item.getLabel());
        }

        dispose();
    }
}


