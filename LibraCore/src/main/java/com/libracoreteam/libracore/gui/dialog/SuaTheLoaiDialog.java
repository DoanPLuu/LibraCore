
package com.libracoreteam.libracore.gui.dialog;


import com.libracoreteam.libracore.bus.TheLoaiBUS;
import com.libracoreteam.libracore.model.TheLoai;
import javax.swing.*;
import java.awt.*;
import org.kordamp.ikonli.swing.FontIcon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

public class SuaTheLoaiDialog extends JDialog {
    private JTextField txtTen;
    private JButton btnLuu, btnHuy;
    private TheLoaiBUS bus = new TheLoaiBUS();
    private TheLoai currentTL;
    private boolean isSaved = false;

    public SuaTheLoaiDialog(Frame parent, boolean modal, TheLoai tl) {
        super(parent, "Sửa thể loại", modal);
        this.currentTL = tl;
        setLayout(new BorderLayout(10, 10));
        
        JPanel pnlCenter = new JPanel(new GridLayout(2, 2, 10, 10));
        pnlCenter.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        pnlCenter.add(new JLabel("Mã thể loại:"));
        JTextField txtMa = new JTextField(String.valueOf(tl.getIdTheLoai()));
        txtMa.setEditable(false);
        pnlCenter.add(txtMa);
        
        pnlCenter.add(new JLabel("Tên thể loại:"));
        txtTen = new JTextField(tl.getTenTheLoai());
        pnlCenter.add(txtTen);
        
        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnLuu = new JButton("Cập nhật");
        btnLuu.setIcon(FontIcon.of(FontAwesomeSolid.SAVE, 16, Color.BLUE));
        btnHuy = new JButton("Hủy");
        btnHuy.setIcon(FontIcon.of(FontAwesomeSolid.TIMES_CIRCLE, 16, Color.RED));
        
        btnLuu.addActionListener(e -> {
            currentTL.setTenTheLoai(txtTen.getText().trim());
            if(bus.update(currentTL)) {
                isSaved = true;
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                dispose();
            }
        });
        
        btnHuy.addActionListener(e -> dispose());
        pnlBottom.add(btnLuu);
        pnlBottom.add(btnHuy);
        
        add(pnlCenter, BorderLayout.CENTER);
        add(pnlBottom, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(parent);
    }

    public boolean isSavedSuccess() { return isSaved; }
}