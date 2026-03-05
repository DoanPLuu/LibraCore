package com.libracoreteam.libracore.gui.dialog;

import net.miginfocom.swing.MigLayout;
import org.kordamp.ikonli.swing.FontIcon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ThemNhanVienDialog extends JDialog {
    // ===== Fields =====
    private JTextField txtTenNhanVien;
    private JTextField txtNgaySinh;
    private JTextArea txtDiaChi;
    private JTextField txtSDT;
    private JTextField txtEmail;
    
    private JButton btnLuu;
    private JButton btnHuy;
    
    private boolean saved = false;

    public ThemNhanVienDialog(Frame parent, boolean modal) {
        super(parent, "Thêm nhân viên", modal);
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

        // ===== Tên nhân viên =====
        txtTenNhanVien = new JTextField(25);
        formPanel.add(new JLabel("Họ và Tên:"));
        formPanel.add(txtTenNhanVien);

        // ===== Ngày sinh =====
        txtNgaySinh = new JTextField(25);
        formPanel.add(new JLabel("Ngày Sinh (dd/MM/yyyy):"));
        formPanel.add(txtNgaySinh);

        // ===== Địa chỉ =====
        txtDiaChi = new JTextArea(4, 25);
        txtDiaChi.setLineWrap(true);
        txtDiaChi.setWrapStyleWord(true);
        JScrollPane scrollDiaChi = new JScrollPane(txtDiaChi);
        formPanel.add(new JLabel("Địa Chỉ:"));
        formPanel.add(scrollDiaChi);

        // ===== Số điện thoại =====
        txtSDT = new JTextField(25);
        formPanel.add(new JLabel("Số Điện Thoại:"));
        formPanel.add(txtSDT);

        // ===== Email =====
        txtEmail = new JTextField(25);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(txtEmail);

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
        String tenNhanVien = txtTenNhanVien.getText().trim();
        String ngaySinhStr = txtNgaySinh.getText().trim();
        String diaChi = txtDiaChi.getText().trim();
        String sdt = txtSDT.getText().trim();
        String email = txtEmail.getText().trim();

        // 1. Validate dữ liệu
        if (tenNhanVien.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Họ và tên không được để trống!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validate ngày sinh
        LocalDate ngaySinh = null;
        if (!ngaySinhStr.isEmpty()) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                ngaySinh = LocalDate.parse(ngaySinhStr, formatter);
                if (ngaySinh.isAfter(LocalDate.now())) {
                    JOptionPane.showMessageDialog(this, "Ngày sinh phải trước ngày hiện tại!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(this, "Định dạng ngày sinh không hợp lệ! Vui lòng nhập dd/MM/yyyy", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        // Validate số điện thoại (nếu có)
        if (!sdt.isEmpty() && !sdt.matches("^\\d{10}$")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại phải bao gồm 10 chữ số!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validate email (nếu có)
        if (!email.isEmpty() && !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            JOptionPane.showMessageDialog(this, "Email không hợp lệ!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 2. Gom vào DTO
        com.libracoreteam.libracore.model.NhanVien nv = new com.libracoreteam.libracore.model.NhanVien(
                tenNhanVien, ngaySinh, diaChi, sdt, email
        );

        // 3. Gọi BUS để lưu
        com.libracoreteam.libracore.bus.NhanVienBUS nhanVienBUS = new com.libracoreteam.libracore.bus.NhanVienBUS();
        if (nhanVienBUS.add(nv)) {
            JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công!");
            saved = true;
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu nhân viên vào cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isSaved() {
        return saved;
    }
}
