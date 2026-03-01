package com.libracoreteam.libracore.gui.dialog;

import net.miginfocom.swing.MigLayout;
import org.kordamp.ikonli.swing.FontIcon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class ThemTacGiaDialog extends JDialog {
    // ===== Fields =====
    private JTextField txtTenTacGia;
    private JSpinner spnNgaySinh;
    private JTextField txtNoiSinh;
    private JTextField txtSDT;
    
    private JButton btnLuu;
    private JButton btnHuy;

    public ThemTacGiaDialog(Frame parent, boolean modal) {
        super(parent, "Thêm tác giả", modal);
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

        // ===== Tên tác giả =====
        txtTenTacGia = new JTextField(25);
        formPanel.add(new JLabel("Tên tác giả:"));
        formPanel.add(txtTenTacGia);

        // ===== Ngày sinh =====
        SpinnerDateModel dateModel = new SpinnerDateModel();
        spnNgaySinh = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spnNgaySinh, "dd/MM/yyyy");
        spnNgaySinh.setEditor(dateEditor);
        spnNgaySinh.setValue(new Date()); // Mặc định là ngày hiện tại
        
        formPanel.add(new JLabel("Ngày sinh:"));
        formPanel.add(spnNgaySinh);

        // ===== Nơi sinh =====
        txtNoiSinh = new JTextField(25);
        formPanel.add(new JLabel("Nơi sinh:"));
        formPanel.add(txtNoiSinh);

        // ===== Số điện thoại =====
        txtSDT = new JTextField(15);
        formPanel.add(new JLabel("Số điện thoại:"));
        formPanel.add(txtSDT);

        // ===== Buttons =====
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnLuu = new JButton("Xác nhận");
        btnHuy = new JButton("Hủy");
        
        // Thêm icon cho buttons
        int iconSize = 16;
        btnLuu.setIcon(FontIcon.of(FontAwesomeSolid.CHECK_CIRCLE, iconSize, new Color(40, 167, 69)));
        btnHuy.setIcon(FontIcon.of(FontAwesomeSolid.TIMES_CIRCLE, iconSize, new Color(220, 53, 69)));

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
        // 1. Lấy và Validate dữ liệu
        String tenTacGia = txtTenTacGia.getText().trim();
        String noiSinh = txtNoiSinh.getText().trim();
        String sdt = txtSDT.getText().trim();

        if (tenTacGia.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên tác giả không được để trống!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Chuyển Date sang LocalDate
        java.util.Date selectedDate = (java.util.Date) spnNgaySinh.getValue();
        java.time.LocalDate ngaySinh = selectedDate.toInstant()
                .atZone(java.time.ZoneId.systemDefault())
                .toLocalDate();
        
        // 2. Gom vào DTO
        com.libracoreteam.libracore.model.TacGia tacGia = new com.libracoreteam.libracore.model.TacGia(tenTacGia, ngaySinh, noiSinh, sdt);
        
        // 3. Gọi DAO để lưu
        com.libracoreteam.libracore.dao.TacGiaDAO tacGiaDAO = new com.libracoreteam.libracore.dao.TacGiaDAO();
        if (tacGiaDAO.insert(tacGia)) {
            JOptionPane.showMessageDialog(this, "Thêm tác giả thành công!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu tác giả vào cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}

