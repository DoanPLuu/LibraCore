package com.libracoreteam.libracore.gui.dialog;

import com.libracoreteam.libracore.bus.NXBBUS;
import com.libracoreteam.libracore.model.NXB;
import net.miginfocom.swing.MigLayout;
import org.kordamp.ikonli.swing.FontIcon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

import javax.swing.*;
import java.awt.*;

public class ThemNXBDialog extends JDialog {
    private JTextField txtTenNXB;
    private JTextField txtDiaChi;
    private JTextField txtSDT;
    
    private JButton btnLuu;
    private JButton btnHuy;

    private final NXBBUS nxbBUS = new NXBBUS();
    private boolean saved = false;
    private int createdId = -1;

    public ThemNXBDialog(Frame parent, boolean modal) {
        super(parent, "Thêm nhà xuất bản", modal);
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
        try {
            NXB created = nxbBUS.create(
                    txtTenNXB.getText(),
                    txtDiaChi.getText(),
                    txtSDT.getText()
            );

            saved = true;
            createdId = created != null ? created.getIdNXB() : -1;

            JOptionPane.showMessageDialog(this, "Thêm NXB thành công.");
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

    public int getCreatedId() {
        return createdId;
    }
}

