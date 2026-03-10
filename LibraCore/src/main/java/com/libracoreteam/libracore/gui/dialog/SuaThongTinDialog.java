package com.libracoreteam.libracore.gui.dialog;

import com.libracoreteam.libracore.model.NhanVien;
import com.libracoreteam.libracore.bus.NhanVienBUS;
import net.miginfocom.swing.MigLayout;
import org.kordamp.ikonli.swing.FontIcon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import javax.swing.*;
import java.awt.*;

public class SuaThongTinDialog extends JDialog {
    
    private JTextField jTextFieldSDT;
    private JTextField jTextFieldDiaChi;
    private JTextField jTextFieldEmail;
    private JButton jButtonOK;
    private JButton jButtonCancel;
    private boolean isConfirmed = false;
    private NhanVien employee;

    public SuaThongTinDialog(Frame parent, NhanVien employee) {
        super(parent, "Sửa thông tin cá nhân", true);
        this.employee = employee;
        initComponents();
        setLocationRelativeTo(parent);
        setResizable(false);
    }

    private void initComponents() {
        JPanel formPanel = new JPanel(
                new MigLayout(
                        "wrap 2, insets 15, gapx 10, gapy 8",
                        "[right][grow, fill]",
                        "[]"
                )
        );

        jTextFieldSDT = new JTextField(25);
        jTextFieldSDT.setText(employee.getSdt() != null ? employee.getSdt() : "");
        formPanel.add(new JLabel("Số điện thoại:"));
        formPanel.add(jTextFieldSDT);

        jTextFieldDiaChi = new JTextField(25);
        jTextFieldDiaChi.setText(employee.getDiaChi() != null ? employee.getDiaChi() : "");
        formPanel.add(new JLabel("Địa chỉ:"));
        formPanel.add(jTextFieldDiaChi);

        jTextFieldEmail = new JTextField(25);
        jTextFieldEmail.setText(employee.getEmail() != null ? employee.getEmail() : "");
        formPanel.add(new JLabel("Email:"));
        formPanel.add(jTextFieldEmail);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));

        jButtonOK = new JButton("Xác nhận");
        jButtonCancel = new JButton("Hủy");

        int iconSize = 16;
        jButtonOK.setIcon(FontIcon.of(FontAwesomeSolid.CHECK_CIRCLE, iconSize, new Color(40, 167, 69)));
        jButtonCancel.setIcon(FontIcon.of(FontAwesomeSolid.TIMES_CIRCLE, iconSize, new Color(220, 53, 69)));

        buttonPanel.add(jButtonOK);
        buttonPanel.add(jButtonCancel);

        jButtonOK.addActionListener(e -> handleOK());
        jButtonCancel.addActionListener(e -> handleCancel());

        setLayout(new BorderLayout());
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
    }

    private void handleOK() {
        isConfirmed = true;
        dispose();
    }

    private void handleCancel() {
        isConfirmed = false;
        dispose();
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public String getSDT() {
        return jTextFieldSDT.getText().trim();
    }

    public String getDiaChi() {
        return jTextFieldDiaChi.getText().trim();
    }

    public String getEmail() {
        return jTextFieldEmail.getText().trim();
    }
}
