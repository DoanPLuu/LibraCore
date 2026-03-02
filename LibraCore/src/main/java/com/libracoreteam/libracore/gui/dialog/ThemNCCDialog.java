/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.libracoreteam.libracore.gui.dialog;

import com.libracoreteam.libracore.bus.NCCBUS;
import net.miginfocom.swing.MigLayout;
import org.kordamp.ikonli.swing.FontIcon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 *
 * @author Sang
 */
public class ThemNCCDialog extends JDialog{
    // ===== Fields =====
    private JTextField txtTenNCC;
    
    private JButton btnLuu;
    private JButton btnHuy;

    private final NCCBUS nccBUS = new NCCBUS();
    private boolean isAdded = false; // Cờ kiểm tra xem đã thêm thành công chưa

    // Dùng Window owner để linh hoạt gọi từ JPanel (có thể là Frame hoặc Dialog)
    public ThemNCCDialog(Window owner, boolean modal) {
        super(owner, "Thêm Nhà cung cấp", modal ? ModalityType.APPLICATION_MODAL : ModalityType.MODELESS);
        initComponents();
    }

    private void initComponents() {
        // ===== Form Panel dùng MigLayout =====
        JPanel formPanel = new JPanel(
                new MigLayout(
                        "wrap 2, insets 15, gapx 10, gapy 8", // cấu hình layout
                        "[right][grow, fill]",                // cấu hình cột (label căn phải, input kéo dãn)
                        "[]"                                  // cấu hình hàng
                )
        );

        // ===== Tên nhà cung cấp =====
        txtTenNCC = new JTextField(25);
        formPanel.add(new JLabel("Tên Nhà cung cấp (*):"));
        formPanel.add(txtTenNCC);

        // ===== Buttons =====
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnLuu = new JButton("Xác nhận");
        btnHuy = new JButton("Hủy");
        
        // Thêm icon cho buttons (Đồng bộ với ThemTacGiaDialog)
        int iconSize = 16;
        btnLuu.setIcon(FontIcon.of(FontAwesomeSolid.CHECK_CIRCLE, iconSize, new Color(40, 167, 69)));
        btnHuy.setIcon(FontIcon.of(FontAwesomeSolid.TIMES_CIRCLE, iconSize, new Color(220, 53, 69)));

        buttonPanel.add(btnLuu);
        buttonPanel.add(btnHuy);

        // Xử lý sự kiện nút bấm
        btnLuu.addActionListener(e -> onSave());
        btnHuy.addActionListener(e -> dispose());

        // ===== Layout tổng =====
        setLayout(new BorderLayout());
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack(); // Tự động co giãn kích thước cho vừa vặn
        setLocationRelativeTo(getParent()); // Hiển thị ở giữa cửa sổ cha
        setResizable(false);
    }

    private void onSave() {
        try {
            // Gọi BUS để xử lý thêm vào DB
            nccBUS.create(txtTenNCC.getText());
            
            isAdded = true; // Đánh dấu là đã thêm thành công để Panel cha biết
            JOptionPane.showMessageDialog(this, "Thêm Nhà cung cấp thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            dispose(); // Đóng form
            
        } catch (IllegalArgumentException ex) {
            // Bắt lỗi kiểm tra dữ liệu từ BUS (ví dụ: để trống, tên quá dài, trùng lặp)
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi nhập liệu", JOptionPane.WARNING_MESSAGE);
        } catch (RuntimeException ex) {
            // Bắt lỗi hệ thống (như rớt mạng, lỗi CSDL)
            JOptionPane.showMessageDialog(this, "Lỗi hệ thống: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Hàm public để Panel cha gọi xem có cần tải lại bảng dữ liệu không
    public boolean isAddedSuccess() {
        return isAdded;
    }
}
