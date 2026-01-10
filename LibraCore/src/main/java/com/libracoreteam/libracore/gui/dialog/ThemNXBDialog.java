package com.libracoreteam.libracore.gui.dialog;

import net.miginfocom.swing.MigLayout;
import org.kordamp.ikonli.swing.FontIcon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

import javax.swing.*;
import java.awt.*;

public class ThemNXBDialog extends JDialog {
    // ===== Fields =====
    private JTextField txtTenNXB;
    private JTextField txtDiaChi;
    private JTextField txtSDT;
    
    private JButton btnLuu;
    private JButton btnHuy;

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

        // ===== Tên NXB =====
        txtTenNXB = new JTextField(25);
        formPanel.add(new JLabel("Tên NXB:"));
        formPanel.add(txtTenNXB);

        // ===== Địa chỉ =====
        txtDiaChi = new JTextField(25);
        formPanel.add(new JLabel("Địa chỉ:"));
        formPanel.add(txtDiaChi);

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
        System.out.println("=== Thêm nhà xuất bản ===");
        System.out.println("Tên NXB: " + txtTenNXB.getText());
        System.out.println("Địa chỉ: " + txtDiaChi.getText());
        System.out.println("Số điện thoại: " + txtSDT.getText());
        
        // TODO: Validate dữ liệu trước khi lưu
        // TODO: Gọi BUS/Service để insert vào DB
        
        dispose();
    }
}

