/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.libracoreteam.libracore.gui.dialog;

import com.libracoreteam.libracore.bus.NCCBUS;
import com.libracoreteam.libracore.model.NCC;
import net.miginfocom.swing.MigLayout;
import org.kordamp.ikonli.swing.FontIcon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

import javax.swing.*;
import java.awt.*;
/**
 *
 * @author Sang
 */
public class SuaNCCDialog extends JDialog{
    private JTextArea txtTenNCC;
    private JButton btnLuu;
    private JButton btnHuy;

    private final NCCBUS nccBUS = new NCCBUS();
    private boolean isSaved = false;
    private NCC currentNCC; // Chứa dữ liệu của NCC đang được sửa

    // Constructor nhận vào đối tượng NCC cần sửa
    public SuaNCCDialog(Frame parent, boolean modal, NCC ncc) {
        super(parent, "Sửa thông tin Nhà cung cấp", modal);
        this.currentNCC = ncc;
        initComponents();
        loadOldData(); // Đổ dữ liệu cũ lên giao diện
    }

    private void initComponents() {
        // Sử dụng MigLayout cho Form
        JPanel formPanel = new JPanel(new MigLayout("wrap 2, insets 15, gapx 10, gapy 15", "[right][grow, fill]", "[]"));

        // ===== 1. Mã NCC (Khóa, không cho sửa) =====
        JTextField txtMaNCC = new JTextField(String.valueOf(currentNCC.getIdNCC()));
        txtMaNCC.setEditable(false);
        txtMaNCC.setEnabled(false);
        txtMaNCC.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        formPanel.add(new JLabel("Mã nhà cung cấp:"));
        formPanel.add(txtMaNCC);

        // ===== 2. Tên NCC (Cho phép sửa nhiều dòng) =====
        txtTenNCC = new JTextArea(3, 25);
        txtTenNCC.setLineWrap(true);
        txtTenNCC.setWrapStyleWord(true);
        JScrollPane spTenNCC = new JScrollPane(txtTenNCC);
        
        formPanel.add(new JLabel("Tên nhà cung cấp:"), "top");
        formPanel.add(spTenNCC);

        // ===== Buttons =====
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnLuu = new JButton("Cập nhật");
        btnHuy = new JButton("Hủy");

        // Thêm Icon
        int iconSize = 16;
        btnLuu.setIcon(FontIcon.of(FontAwesomeSolid.SAVE, iconSize, new Color(13, 110, 253)));
        btnHuy.setIcon(FontIcon.of(FontAwesomeSolid.TIMES_CIRCLE, iconSize, new Color(220, 53, 69)));

        buttonPanel.add(btnLuu);
        buttonPanel.add(btnHuy);

        // Bắt sự kiện
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

    private void loadOldData() {
        if (currentNCC != null) {
            txtTenNCC.setText(currentNCC.getTenNCC());
        }
    }

    private void onSave() {
        try {
            String newName = txtTenNCC.getText().trim();
            if (newName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tên nhà cung cấp không được để trống!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Cập nhật tên mới vào object
            currentNCC.setTenNCC(newName);
            
            // Gọi BUS để lưu vào Database
            boolean ok = nccBUS.update(currentNCC); 

            if (ok) {
                isSaved = true;
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                dispose(); // Đóng form
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi hệ thống: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Hàm public để bên Panel check xem có lưu thành công không để reset bảng
    public boolean isSavedSuccess() {
        return isSaved;
    }
}
