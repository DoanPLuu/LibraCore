package com.libracoreteam.libracore.gui.dialog;

import com.libracoreteam.libracore.bus.NXBBUS;
import com.libracoreteam.libracore.model.NXB;
import net.miginfocom.swing.MigLayout;
import org.kordamp.ikonli.swing.FontIcon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;

public class SuaNXBDialog extends JDialog {

    private final NXBBUS nxbBUS = new NXBBUS();
    private final NXB original;

    private JTextField txtTenNXB;
    private JTextField txtDiaChi;
    private JTextField txtSDT;

    private JButton btnLuu;
    private JButton btnHuy;

    private boolean saved = false;

    public SuaNXBDialog(Frame parent, boolean modal, NXB nxb) {
        super(parent, "Sửa nhà xuất bản", modal);
        if (nxb == null) {
            throw new IllegalArgumentException("NXB không hợp lệ để sửa.");
        }
        this.original = nxb;
        initComponents();
        fillForm();
    }

    private void initComponents() {
        JPanel formPanel = new JPanel(
                new MigLayout(
                        "wrap 2, insets 15, gapx 10, gapy 8",
                        "[right][grow, fill]",
                        "[]"
                )
        );

        txtTenNXB = new JTextField(25);
        formPanel.add(new JLabel("Tên NXB:"));
        formPanel.add(txtTenNXB);

        txtDiaChi = new JTextField(25);
        formPanel.add(new JLabel("Địa chỉ:"));
        formPanel.add(txtDiaChi);

        txtSDT = new JTextField(15);
        formPanel.add(new JLabel("Số điện thoại:"));
        formPanel.add(txtSDT);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnLuu = new JButton("Lưu");
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

    private void fillForm() {
        txtTenNXB.setText(original.getTenNXB() != null ? original.getTenNXB() : "");
        txtDiaChi.setText(original.getDiaChi() != null ? original.getDiaChi() : "");
        txtSDT.setText(original.getSdt() != null ? original.getSdt() : "");
    }

    private void onSave() {
        try {
            boolean ok = nxbBUS.update(
                    original.getIdNXB(),
                    txtTenNXB.getText(),
                    txtDiaChi.getText(),
                    txtSDT.getText(),
                    original.isHoatDong()
            );

            if (!ok) {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại.");
                return;
            }

            saved = true;
            JOptionPane.showMessageDialog(this, "Cập nhật thành công.");
            dispose();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi hệ thống: " + ex.getMessage());
        }
    }

    public boolean isSaved() {
        return saved;
    }
}

