package com.libracoreteam.libracore.gui.dialog;

import net.miginfocom.swing.MigLayout;
import org.kordamp.ikonli.swing.FontIcon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

import javax.swing.*;
import java.awt.*;

public class ThemTheLoaiDialog extends JDialog {
    private JTextField txtTenTheLoai;
    
    private JButton btnLuu;
    private JButton btnHuy;

    public ThemTheLoaiDialog(Frame parent, boolean modal) {
        super(parent, "Thêm thể loại", modal);
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

        txtTenTheLoai = new JTextField(25);
        formPanel.add(new JLabel("Tên thể loại:"));
        formPanel.add(txtTenTheLoai);

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
        String tenTheLoai = txtTenTheLoai.getText().trim();

        if (tenTheLoai.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên thể loại không được để trống!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        com.libracoreteam.libracore.model.TheLoai tl = new com.libracoreteam.libracore.model.TheLoai(tenTheLoai);

        com.libracoreteam.libracore.dao.TheLoaiDAO theLoaiDAO = new com.libracoreteam.libracore.dao.TheLoaiDAO();
        if (theLoaiDAO.insert(tl)) {
            JOptionPane.showMessageDialog(this, "Thêm thể loại thành công!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu thể loại vào cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}

