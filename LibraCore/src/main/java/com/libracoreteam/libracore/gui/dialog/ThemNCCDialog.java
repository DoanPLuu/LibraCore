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

public class ThemNCCDialog extends JDialog{
    private JTextField txtTenNCC;
    
    private JButton btnLuu;
    private JButton btnHuy;

    private final NCCBUS nccBUS = new NCCBUS();
    private boolean isAdded = false; 

    public ThemNCCDialog(Window owner, boolean modal) {
        super(owner, "Thêm Nhà cung cấp", modal ? ModalityType.APPLICATION_MODAL : ModalityType.MODELESS);
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

        txtTenNCC = new JTextField(25);
        formPanel.add(new JLabel("Tên Nhà cung cấp (*):"));
        formPanel.add(txtTenNCC);

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
            nccBUS.create(txtTenNCC.getText());
            
            isAdded = true;
            JOptionPane.showMessageDialog(this, "Thêm Nhà cung cấp thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi nhập liệu", JOptionPane.WARNING_MESSAGE);
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi hệ thống: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isAddedSuccess() {
        return isAdded;
    }
}
