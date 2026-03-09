package com.libracoreteam.libracore.gui.dialog;

import com.libracoreteam.libracore.bus.DocGiaBUS;
import com.libracoreteam.libracore.model.DocGia;
import net.miginfocom.swing.MigLayout;
import org.kordamp.ikonli.swing.FontIcon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class ThanhVienDialog extends JDialog {
    private DocGiaBUS docGiaBUS;
    private DocGia docGiaEdit; 
    private boolean isSuccess = false;

    private JTextField txtTen, txtSdt, txtEmail, txtDiaChi;
    private JSpinner spnNgaySinh; 

    public ThanhVienDialog(JFrame parent, DocGia docGia) {
        super(parent, docGia == null ? "Thêm Độc Giả" : "Sửa Độc Giả", true);
        this.docGiaEdit = docGia;
        this.docGiaBUS = new DocGiaBUS();

        initComponents();
        fillData();

        pack(); 
        setLocationRelativeTo(parent);
        setResizable(false);
    }

    private void initComponents() {
        JPanel formPanel = new JPanel(
                new MigLayout(
                        "wrap 2, insets 20, gapx 15, gapy 12",
                        "[right][grow, fill]",
                        "[]"
                )
        );

        txtTen = new JTextField(20);
        formPanel.add(new JLabel("Họ tên:"));
        formPanel.add(txtTen);

        SpinnerDateModel dateModel = new SpinnerDateModel();
        spnNgaySinh = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spnNgaySinh, "dd/MM/yyyy");
        spnNgaySinh.setEditor(dateEditor);
        formPanel.add(new JLabel("Ngày sinh:"));
        formPanel.add(spnNgaySinh);

        txtSdt = new JTextField(15);
        formPanel.add(new JLabel("Số điện thoại:"));
        formPanel.add(txtSdt);

        txtEmail = new JTextField(20);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(txtEmail);

        txtDiaChi = new JTextField(20);
        formPanel.add(new JLabel("Địa chỉ:"));
        formPanel.add(txtDiaChi);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        JButton btnLuu = new JButton("Xác nhận");
        JButton btnHuy = new JButton("Hủy");
        
        int iconSize = 16;
        btnLuu.setIcon(FontIcon.of(FontAwesomeSolid.CHECK_CIRCLE, iconSize, new Color(40, 167, 69)));
        btnHuy.setIcon(FontIcon.of(FontAwesomeSolid.TIMES_CIRCLE, iconSize, new Color(220, 53, 69)));

        btnLuu.addActionListener(e -> save());
        btnHuy.addActionListener(e -> dispose());

        buttonPanel.add(btnLuu);
        buttonPanel.add(btnHuy);

        setLayout(new BorderLayout());
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void fillData() {
        if (docGiaEdit != null) {
            txtTen.setText(docGiaEdit.getTenDocGia());
            txtSdt.setText(docGiaEdit.getSdt());
            txtEmail.setText(docGiaEdit.getEmail());
            txtDiaChi.setText(docGiaEdit.getDiaChi());

            if (docGiaEdit.getNgaySinh() != null) {
                Date date = Date.from(docGiaEdit.getNgaySinh().atStartOfDay(ZoneId.systemDefault()).toInstant());
                spnNgaySinh.setValue(date);
            }
        }
    }

    private void save() {
        try {
            String ten = txtTen.getText().trim();
            String sdt = txtSdt.getText().trim();
            String email = txtEmail.getText().trim();
            String diachi = txtDiaChi.getText().trim();

            if(ten.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Họ tên không được để trống!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Date selectedDate = (Date) spnNgaySinh.getValue();
            LocalDate ngSinh = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            String msg;
            if (docGiaEdit == null) {
                DocGia dg = new DocGia(0, ten, diachi, ngSinh, sdt, email, true);
                msg = docGiaBUS.addDocGia(dg);
            } else {
                docGiaEdit.setTenDocGia(ten);
                docGiaEdit.setNgaySinh(ngSinh);
                docGiaEdit.setSdt(sdt);
                docGiaEdit.setEmail(email);
                docGiaEdit.setDiaChi(diachi);
                msg = docGiaBUS.updateDocGia(docGiaEdit);
            }

            if (msg.contains("thành công")) {
                JOptionPane.showMessageDialog(this, msg);
                isSuccess = true;
                dispose();
            }else if(msg.contains("thất bại")){
                JOptionPane.showMessageDialog(this, msg, "Lỗi", JOptionPane.ERROR_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(this, msg, "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            }
           
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra trong quá trình lưu dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isSuccess() {
        return isSuccess;
    }
}