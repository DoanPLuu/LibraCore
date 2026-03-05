package com.libracoreteam.libracore.gui.dialog;

import net.miginfocom.swing.MigLayout;
import org.kordamp.ikonli.swing.FontIcon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import javax.swing.*;
import java.awt.*;

public class DoiMatKhauDialog extends JDialog {
    
    private JPasswordField jPasswordFieldOld;
    private JPasswordField jPasswordFieldNew;
    private JPasswordField jPasswordFieldConfirm;
    private JButton jButtonOK;
    private JButton jButtonCancel;
    private boolean isConfirmed = false;

    public DoiMatKhauDialog(Frame parent) {
        super(parent, "Đổi mật khẩu", true);
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

        // Mật khẩu hiện tại
        jPasswordFieldOld = new JPasswordField(25);
        formPanel.add(new JLabel("Mật khẩu hiện tại:"));
        formPanel.add(jPasswordFieldOld);

        // Mật khẩu mới
        jPasswordFieldNew = new JPasswordField(25);
        formPanel.add(new JLabel("Mật khẩu mới:"));
        formPanel.add(jPasswordFieldNew);

        // Xác nhận mật khẩu
        jPasswordFieldConfirm = new JPasswordField(25);
        formPanel.add(new JLabel("Xác nhận mật khẩu:"));
        formPanel.add(jPasswordFieldConfirm);

        // Button Panel
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

        // Layout tổng
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

    public String getOldPassword() {
        return new String(jPasswordFieldOld.getPassword());
    }

    public String getNewPassword() {
        return new String(jPasswordFieldNew.getPassword());
    }

    public String getConfirmPassword() {
        return new String(jPasswordFieldConfirm.getPassword());
    }
}
