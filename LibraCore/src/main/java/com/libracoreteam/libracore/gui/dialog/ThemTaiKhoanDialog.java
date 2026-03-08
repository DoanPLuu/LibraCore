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

        cmbNhanVien = new JComboBox<>();
        formPanel.add(new JLabel("Chọn nhân viên:"));
        formPanel.add(cmbNhanVien);

        txtTenDangNhap = new JTextField(25);
        formPanel.add(new JLabel("Tên Đăng Nhập:"));
        formPanel.add(txtTenDangNhap);

        txtMatKhau = new JPasswordField(25);
        formPanel.add(new JLabel("Mật Khẩu:"));
        formPanel.add(txtMatKhau);

        txtMatKhauLai = new JPasswordField(25);
        formPanel.add(new JLabel("Nhập Lại Mật Khẩu:"));
        formPanel.add(txtMatKhauLai);

        cmbVaiTro = new JComboBox<>();
        formPanel.add(new JLabel("Vai Trò:"));
        formPanel.add(cmbVaiTro);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnLuu = new JButton("Xác nhận");
        btnHuy = new JButton("Hủy");
        
        int iconSize = 16;
        btnLuu.setIcon(FontIcon.of(FontAwesomeSolid.CHECK_CIRCLE, iconSize, new Color(40, 167, 69)));
        btnHuy.setIcon(FontIcon.of(FontAwesomeSolid.TIMES_CIRCLE, iconSize, new Color(220, 53, 69)));

        buttonPanel.add(btnLuu);
        buttonPanel.add(btnHuy);

        btnLuu.addActionListener(e -> onSave());
        btnHuy.addActionListener(e -> dispose());

        setLayout(new BorderLayout());
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(getParent());
        setResizable(false);
    }

    private void onSave() {
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
        int vaiTro = selectedRole.getIdVaiTro(); 

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

        com.libracoreteam.libracore.model.TaiKhoan tk = new com.libracoreteam.libracore.model.TaiKhoan(
                vaiTro, tenDangNhap, matKhau
        );

        com.libracoreteam.libracore.bus.TaiKhoanBUS taiKhoanBUS = new com.libracoreteam.libracore.bus.TaiKhoanBUS();
        if (taiKhoanBUS.add(tk)) {
            int idTaiKhoan = tk.getIdTaiKhoan();
            
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

    private void loadNhanVienChuaCoTaiKhoan() {
        cmbNhanVien.removeAllItems();
        NhanVienBUS nhanVienBUS = new NhanVienBUS();
        List<NhanVien> dsNhanVien = nhanVienBUS.getActive();
        
        List<NhanVien> result = new java.util.ArrayList<>();
        for (NhanVien nv : dsNhanVien) {
            if (nv.getIdTaiKhoan() == null) {
                result.add(nv);
            }
        }
        
        Collections.sort(result, (nv1, nv2) -> Integer.compare(nv1.getIdNhanVien(), nv2.getIdNhanVien()));
        
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
