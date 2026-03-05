package com.libracoreteam.libracore.gui.dialog;

import net.miginfocom.swing.MigLayout;
import org.kordamp.ikonli.swing.FontIcon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

import javax.swing.*;
import java.awt.*;

public class SuaTaiKhoanDialog extends JDialog {
    // ===== Fields =====
    private JTextField txtIdTaiKhoan;
    private JTextField txtTenDangNhap;
    private JPasswordField txtMatKhau;
    private JPasswordField txtMatKhauLai;
    private JComboBox<String> cmbVaiTro;
    
    private JButton btnLuu;
    private JButton btnHuy;
    
    private boolean saved = false;
    private com.libracoreteam.libracore.model.TaiKhoan taiKhoan;

    public SuaTaiKhoanDialog(Frame parent, boolean modal, com.libracoreteam.libracore.model.TaiKhoan taiKhoan) {
        super(parent, "Sửa thông tin tài khoản", modal);
        this.taiKhoan = taiKhoan;
        initComponents();
        loadData();
    }

    private void initComponents() {
        JPanel formPanel = new JPanel(
                new MigLayout(
                        "wrap 2, insets 15, gapx 10, gapy 8",
                        "[right][grow, fill]",
                        "[]"
                )
        );

        // ===== ID tài khoản (không cho chỉnh sửa) =====
        txtIdTaiKhoan = new JTextField(25);
        txtIdTaiKhoan.setEditable(false);
        formPanel.add(new JLabel("ID Tài Khoản:"));
        formPanel.add(txtIdTaiKhoan);

        // ===== Tên đăng nhập =====
        txtTenDangNhap = new JTextField(25);
        formPanel.add(new JLabel("Tên Đăng Nhập:"));
        formPanel.add(txtTenDangNhap);

        // ===== Mật khẩu =====
        txtMatKhau = new JPasswordField(25);
        formPanel.add(new JLabel("Mật Khẩu:"));
        formPanel.add(txtMatKhau);

        // ===== Nhập lại mật khẩu =====
        txtMatKhauLai = new JPasswordField(25);
        formPanel.add(new JLabel("Nhập Lại Mật Khẩu:"));
        formPanel.add(txtMatKhauLai);

        // ===== Vai trò =====
        cmbVaiTro = new JComboBox<>(new String[]{"Admin", "Nhân Viên", "Quản Lý"});
        formPanel.add(new JLabel("Vai Trò:"));
        formPanel.add(cmbVaiTro);

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

    private void loadData() {
        if (taiKhoan != null) {
            txtIdTaiKhoan.setText(String.valueOf(taiKhoan.getIdTaiKhoan()));
            txtTenDangNhap.setText(taiKhoan.getTaiKhoan() != null ? taiKhoan.getTaiKhoan() : "");
            txtMatKhau.setText(taiKhoan.getMatKhau() != null ? taiKhoan.getMatKhau() : "");
            txtMatKhauLai.setText(taiKhoan.getMatKhau() != null ? taiKhoan.getMatKhau() : "");
            cmbVaiTro.setSelectedIndex(taiKhoan.getIdVaiTro() - 1);
        }
    }

    private void onSave() {
        String tenDangNhap = txtTenDangNhap.getText().trim();
        String matKhau = new String(txtMatKhau.getPassword());
        String matKhauLai = new String(txtMatKhauLai.getPassword());
        int vaiTro = cmbVaiTro.getSelectedIndex() + 1;

        // 1. Validate dữ liệu
        if (tenDangNhap.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên đăng nhập không được để trống!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (tenDangNhap.length() < 3) {
            JOptionPane.showMessageDialog(this, "Tên đăng nhập phải có ít nhất 3 ký tự!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (matKhau.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mật khẩu không được để trống!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (matKhau.length() < 6) {
            JOptionPane.showMessageDialog(this, "Mật khẩu phải có ít nhất 6 ký tự!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!matKhau.equals(matKhauLai)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu nhập lại không khớp!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 2. Cập nhật DTO
        taiKhoan.setIdVaiTro(vaiTro);
        taiKhoan.setTaiKhoan(tenDangNhap);
        taiKhoan.setMatKhau(matKhau);

        // 3. Gọi BUS để lưu
        com.libracoreteam.libracore.bus.TaiKhoanBUS taiKhoanBUS = new com.libracoreteam.libracore.bus.TaiKhoanBUS();
        if (taiKhoanBUS.update(taiKhoan)) {
            JOptionPane.showMessageDialog(this, "Cập nhật tài khoản thành công!");
            saved = true;
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật tài khoản!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isSaved() {
        return saved;
    }
}
