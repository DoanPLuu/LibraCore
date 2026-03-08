package com.libracoreteam.libracore.gui;

import com.libracoreteam.libracore.gui.panel.LoginPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class LoginFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainContainerPanel;
    private Image backgroundImage;

    public static final String LOGIN_CARD = "CARD_LOGIN";

    public LoginFrame() {
        loadBackgroundImage();
        initUI();
    }

    private void loadBackgroundImage() {
        try {
            URL imageUrl = getClass().getResource("/images/LoginBackground.jpg");
            if (imageUrl != null) {
                backgroundImage = ImageIO.read(imageUrl);
            }
        } catch (IOException e) {
            System.err.println("Lỗi: Không thể tải ảnh nền.");
        }
    }

    private void initUI() {
        setTitle("LibraCore - Hệ thống quản lý thư viện");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel backgroundPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

                if (backgroundImage != null) {
                    g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                } else {
                    g2d.setColor(new Color(30, 30, 30));
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };

        cardLayout = new CardLayout();
        mainContainerPanel = new JPanel(cardLayout);
        mainContainerPanel.setOpaque(false);

        mainContainerPanel.add(new LoginPanel(this), LOGIN_CARD);

        backgroundPanel.add(mainContainerPanel, BorderLayout.CENTER);
        setContentPane(backgroundPanel);

        cardLayout.show(mainContainerPanel, LOGIN_CARD);
    }

    public void onLoginSuccess() {
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
        this.dispose();
    }
}