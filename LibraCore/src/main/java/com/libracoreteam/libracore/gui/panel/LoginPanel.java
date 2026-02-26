package com.libracoreteam.libracore.gui.panel;

import com.formdev.flatlaf.FlatClientProperties;
import com.libracoreteam.libracore.bus.TaiKhoanBUS;
import com.libracoreteam.libracore.gui.LoginFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginPanel extends JPanel {


    private final LoginFrame parentFrame;
    private final Color CARD_COLOR = new Color(21, 110, 71);
    private final Font FONT_BOLD = new Font("Segoe UI", Font.BOLD, 14);
    private final Font FONT_PLAIN = new Font("Segoe UI", Font.PLAIN, 14);


    public LoginPanel(LoginFrame parentFrame) {
        this.parentFrame = parentFrame;
        initUI();
    }

    private void initUI() {
        setOpaque(false);
        setLayout(new GridLayout(1, 2));


        // BÊN TRÁI: TEXT CHÀO MỪNG

        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setOpaque(false);
        GridBagConstraints gbcLeft = new GridBagConstraints();
        gbcLeft.gridx = 0;
        gbcLeft.gridy = 0;
        gbcLeft.anchor = GridBagConstraints.WEST;
        gbcLeft.insets = new Insets(0, 50, 0, 0);

        JLabel lblWelcome = new JLabel("<html>Chào mừng đến với<br>phần mềm quản lý thư viện</html>");
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 30));
        lblWelcome.setForeground(Color.WHITE);
        leftPanel.add(lblWelcome, gbcLeft);

        gbcLeft.gridy = 1;
        gbcLeft.insets = new Insets(20, 50, 0, 0);
        JLabel lblSub = new JLabel("Vui lòng đăng nhập để tiếp tục...");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lblSub.setForeground(new Color(220, 220, 220));
        leftPanel.add(lblSub, gbcLeft);

        add(leftPanel);


        // BÊN PHẢI: FORM ĐĂNG NHẬP.

        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setOpaque(false);

        JPanel loginCard = createCardPanel();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JLabel lblHeader = new JLabel("Đăng nhập");
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 30));
        lblHeader.setForeground(Color.WHITE);
        lblHeader.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 0; gbc.insets = new Insets(0, 0, 30, 0);
        loginCard.add(lblHeader, gbc);

        JLabel lblUser = new JLabel("Tên đăng nhập");
        lblUser.setForeground(Color.WHITE); lblUser.setFont(FONT_BOLD);
        gbc.gridy = 1; gbc.insets = new Insets(0, 0, 5, 0);
        loginCard.add(lblUser, gbc);

        JTextField txtUser = new JTextField(20);
        styleTextField(txtUser);
        gbc.gridy = 2; gbc.insets = new Insets(0, 0, 15, 0);
        loginCard.add(txtUser, gbc);

        JLabel lblPass = new JLabel("Mật khẩu");
        lblPass.setForeground(Color.WHITE); lblPass.setFont(FONT_BOLD);
        gbc.gridy = 3; gbc.insets = new Insets(0, 0, 5, 0);
        loginCard.add(lblPass, gbc);

        JPasswordField txtPass = new JPasswordField(20);
        styleTextField(txtPass);
        txtPass.putClientProperty(FlatClientProperties.STYLE,
                "arc: 15; borderWidth: 0; focusWidth: 0; margin: 0,10,0,10; showRevealButton: true");
        gbc.gridy = 4; gbc.insets = new Insets(0, 0, 30, 0);
        loginCard.add(txtPass, gbc);

        JButton btnLogin = new JButton("Đăng nhập  »");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnLogin.setBackground(Color.WHITE);
        btnLogin.setForeground(CARD_COLOR);
        btnLogin.setPreferredSize(new Dimension(0, 45));
        btnLogin.putClientProperty(FlatClientProperties.STYLE, "arc: 15; borderWidth: 0; focusWidth: 0");
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridy = 5; gbc.insets = new Insets(0, 0, 10, 0);

        btnLogin.addActionListener(e -> {
            String user = txtUser.getText().trim();
            char[] passChars = txtPass.getPassword();

            if (user.isEmpty() || passChars.length == 0) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng nhập đầy đủ Username và Password!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Gọi thẳng xuống BUS để xử lý
            TaiKhoanBUS bus = new TaiKhoanBUS();
            String loginResult = bus.checkLogin(user, passChars);

            switch (loginResult) {
                case "SUCCESS":
                    parentFrame.onLoginSuccess();
                    break;
                case "WRONG_PASS":
                    JOptionPane.showMessageDialog(this,
                            "Sai tài khoản hoặc mật khẩu!", "Lỗi đăng nhập", JOptionPane.ERROR_MESSAGE);
                    txtPass.setText(""); // Xóa ô mật khẩu để nhập lại
                    txtPass.requestFocus();
                    break;
                case "LOCKED":
                    JOptionPane.showMessageDialog(this,
                            "Tài khoản của bạn đã bị khóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    break;
                case "ERROR":
                    JOptionPane.showMessageDialog(this,
                            "Lỗi hệ thống hoặc mất kết nối DB!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        });

        loginCard.add(btnLogin, gbc);
        rightPanel.add(loginCard);
        add(rightPanel);
    }

    private JPanel createCardPanel() {
        JPanel panel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(CARD_COLOR);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.dispose();
            }
        };
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(35, 40, 35, 40));
        return panel;
    }

    private void styleTextField(JTextField txt) {
        txt.setFont(FONT_PLAIN);
        txt.setBackground(new Color(255, 255, 255, 40));
        txt.setForeground(Color.WHITE);
        txt.setCaretColor(Color.WHITE);
        txt.setPreferredSize(new Dimension(280, 40));
        if (!(txt instanceof JPasswordField)) {
            txt.putClientProperty(FlatClientProperties.STYLE,
                    "arc: 15; borderWidth: 0; focusWidth: 0; margin: 0,10,0,10");
        }
    }
}