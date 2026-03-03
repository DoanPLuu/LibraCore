package com.libracoreteam.libracore.gui.dialog;

import com.libracoreteam.libracore.bus.DocGiaBUS;
import com.libracoreteam.libracore.model.DocGia;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class ThanhVienDialog extends JDialog {
    private DocGiaBUS docGiaBUS;
    private DocGia docGiaEdit; // Nếu null -> Thêm mới, Khác null -> Sửa
    private boolean isSuccess = false;

    // Components
    private JTextField txtTen, txtNgaySinh, txtSdt, txtEmail, txtDiaChi;
    private JComboBox<String> cboGioiTinh;

    public ThanhVienDialog(JFrame parent, DocGia docGia) {
        super(parent, docGia == null ? "Thêm Độc Giả" : "Sửa Độc Giả", true);
        this.docGiaEdit = docGia;
        this.docGiaBUS = new DocGiaBUS();

        initComponents();
        fillData(); // Nếu là sửa thì điền dữ liệu vào ô

        setSize(400, 450);
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        // Form nhập liệu
        JPanel pnlCenter = new JPanel(new GridLayout(6, 2, 10, 10));
        pnlCenter.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        pnlCenter.add(new JLabel("Họ tên:"));
        txtTen = new JTextField();
        pnlCenter.add(txtTen);

        pnlCenter.add(new JLabel("Ngày sinh (yyyy-MM-dd):"));
        txtNgaySinh = new JTextField(LocalDate.now().toString()); // Placeholder
        pnlCenter.add(txtNgaySinh);

        pnlCenter.add(new JLabel("Giới tính:"));
        cboGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ", "Khác"});
        pnlCenter.add(cboGioiTinh);

        pnlCenter.add(new JLabel("Số điện thoại:"));
        txtSdt = new JTextField();
        pnlCenter.add(txtSdt);

        pnlCenter.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        pnlCenter.add(txtEmail);

        pnlCenter.add(new JLabel("Địa chỉ:"));
        txtDiaChi = new JTextField();
        pnlCenter.add(txtDiaChi);

        add(pnlCenter, BorderLayout.CENTER);

        // Nút bấm
        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnLuu = new JButton("Lưu");
        JButton btnHuy = new JButton("Hủy");

        btnLuu.setBackground(new Color(0, 153, 76));
        btnLuu.setForeground(Color.WHITE);

        btnLuu.addActionListener(e -> save());
        btnHuy.addActionListener(e -> dispose());

        pnlBottom.add(btnLuu);
        pnlBottom.add(btnHuy);
        add(pnlBottom, BorderLayout.SOUTH);
    }

    private void fillData() {
        if (docGiaEdit != null) {
            txtTen.setText(docGiaEdit.getTenDocGia());
            txtNgaySinh.setText(docGiaEdit.getNgaySinh().toString());
            txtSdt.setText(docGiaEdit.getSdt());
            txtEmail.setText(docGiaEdit.getEmail());
            txtDiaChi.setText(docGiaEdit.getDiaChi());
        }
    }

    private void save() {
        try {
            // Lấy dữ liệu từ form
            String ten = txtTen.getText();
            LocalDate ngSinh = LocalDate.parse(txtNgaySinh.getText());
            String sdt = txtSdt.getText();
            String email = txtEmail.getText();
            String diachi = txtDiaChi.getText();

            String msg;
            if (docGiaEdit == null) {
                // Thêm mới
                DocGia dg = new DocGia(0,ten,diachi,ngSinh,sdt,email,true);
                msg = docGiaBUS.addDocGia(dg);
            } else {
                // Cập nhật
                docGiaEdit.setTenDocGia(ten);
                docGiaEdit.setNgaySinh(ngSinh);
                docGiaEdit.setSdt(sdt);
                docGiaEdit.setEmail(email);
                docGiaEdit.setDiaChi(diachi);
                msg = docGiaBUS.updateDocGia(docGiaEdit);
            }

            JOptionPane.showMessageDialog(this, msg);
            if (msg.contains("thành công")) {
                isSuccess = true;
                dispose();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi định dạng ngày sinh hoặc dữ liệu!");
        }
    }

    public boolean isSuccess() {
        return isSuccess;
    }
}