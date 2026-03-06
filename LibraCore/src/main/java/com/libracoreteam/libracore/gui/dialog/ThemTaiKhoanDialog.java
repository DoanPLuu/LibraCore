package com.libracoreteam.libracore.gui.dialog;

import com.libracoreteam.libracore.model.NhanVien;
import com.libracoreteam.libracore.model.VaiTro;
import com.libracoreteam.libracore.bus.VaiTroBUS;
import com.libracoreteam.libracore.bus.NhanVienBUS;
import net.miginfocom.swing.MigLayout;
import org.kordamp.ikonli.swing.FontIcon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Collections;

public class ThemTaiKhoanDialog extends JDialog {
    // ===== Fields =====
    private JComboBox<NhanVienWrapper> cmbNhanVien;
    private JTextField txtTenDangNhap;
    private JPasswordField txtMatKhau;
    private JPasswordField txtMatKhauLai;
    private JComboBox<VaiTro> cmbVaiTro;
    private List<VaiTro> dsVaiTro;
    
    private JButton btnLuu;
    private JButton btnHuy;
    
    private boolean saved = false;

    public ThemTaiKhoanDialog(Frame parent, boolean modal) {
        super(parent, "Thêm tài khoản", modal);
        initComponents();
        loadNhanVienChuaCoTaiKhoan();
        loadVaiTro();
    }

    private void initComponents() {
        JPanel formPanel = new JPanel(
                new MigLayout(
                        "wrap 2, insets 15, gapx 10, gapy 8",
                        "[right][grow, fill]",
                        "[]"
                )
        );

        // ===== Chọn nhân viên =====
        cmbNhanVien = new JComboBox<>();
        formPanel.add(new JLabel("Chọn nhân viên:"));
        formPanel.add(cmbNhanVien);

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
        cmbVaiTro = new JComboBox<>();
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

    private void onSave() {
        // Check xem user đã chọn nhân viên chưa
        NhanVienWrapper wrapper = (NhanVienWrapper) cmbNhanVien.getSelectedItem();
        if (wrapper == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên để cấp tài khoản!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        NhanVien nhanVien = wrapper.getNhanVien();

        String tenDangNhap = txtTenDangNhap.getText().trim();
        String matKhau = new String(txtMatKhau.getPassword());
        String matKhauLai = new String(txtMatKhauLai.getPassword());
        VaiTro selectedRole = (VaiTro) cmbVaiTro.getSelectedItem();
        if (selectedRole == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn vai trò!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int vaiTro = selectedRole.getIdVaiTro(); // ID vai trò thực tế

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

        // 2. Gom vào DTO
        com.libracoreteam.libracore.model.TaiKhoan tk = new com.libracoreteam.libracore.model.TaiKhoan(
                vaiTro, tenDangNhap, matKhau
        );

        // 3. Gọi BUS để lưu
        com.libracoreteam.libracore.bus.TaiKhoanBUS taiKhoanBUS = new com.libracoreteam.libracore.bus.TaiKhoanBUS();
        if (taiKhoanBUS.add(tk)) {
            // 4. Lấy ID tài khoản vừa tạo
            int idTaiKhoan = tk.getIdTaiKhoan();
            
            // 5. UPDATE bảng NhanVien: gán id_TaiKhoan cho nhân viên được chọn
            nhanVien.setIdTaiKhoan(idTaiKhoan);
            com.libracoreteam.libracore.bus.NhanVienBUS nhanVienBUS = new com.libracoreteam.libracore.bus.NhanVienBUS();
            if (nhanVienBUS.update(nhanVien)) {
                JOptionPane.showMessageDialog(this, "Thêm tài khoản thành công!");
                saved = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi khi liên kết tài khoản với nhân viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu tài khoản vào cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isSaved() {
        return saved;
    }

    // ===== Load nhân viên chưa có tài khoản =====
    private void loadNhanVienChuaCoTaiKhoan() {
        cmbNhanVien.removeAllItems();
        NhanVienBUS nhanVienBUS = new NhanVienBUS();
        List<NhanVien> dsNhanVien = nhanVienBUS.getActive();
        
        // Lọc nhân viên chưa có tài khoản
        List<NhanVien> result = new java.util.ArrayList<>();
        for (NhanVien nv : dsNhanVien) {
            if (nv.getIdTaiKhoan() == null) {
                result.add(nv);
            }
        }
        
        // Sắp xếp theo ID nhân viên tăng dần
        Collections.sort(result, (nv1, nv2) -> Integer.compare(nv1.getIdNhanVien(), nv2.getIdNhanVien()));
        
        // Thêm vào ComboBox bằng wrapper
        for (NhanVien nv : result) {
            cmbNhanVien.addItem(new NhanVienWrapper(nv));
        }
    }
    
    private void loadVaiTro() {
        VaiTroBUS vaiTroBUS = new VaiTroBUS();
        dsVaiTro = vaiTroBUS.getAll();
        cmbVaiTro.removeAllItems();
        if (dsVaiTro == null) {
            return;
        }
        for (VaiTro vt : dsVaiTro) {
            if (vt != null) {
                cmbVaiTro.addItem(vt);
            }
        }
    }
    
    // ===== Inner class: Wrapper để hiển thị định dạng "ID: ... -- Tên: ..." =====
    private static class NhanVienWrapper {
        private NhanVien nhanVien;
        
        public NhanVienWrapper(NhanVien nhanVien) {
            this.nhanVien = nhanVien;
        }
        
        public NhanVien getNhanVien() {
            return nhanVien;
        }
        
        @Override
        public String toString() {
            return "ID: " + nhanVien.getIdNhanVien() + "-Tên: " + nhanVien.getTenNhanVien();
        }
    }
}
