package com.libracoreteam.libracore.gui.dialog;

import com.libracoreteam.libracore.bus.QuyenBUS;
import com.libracoreteam.libracore.bus.VaiTroBUS;
import com.libracoreteam.libracore.model.Quyen;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

public class ThemVaiTroDialog extends JDialog {

    private final VaiTroBUS vaiTroBUS = new VaiTroBUS();
    private final QuyenBUS quyenBUS = new QuyenBUS();

    private JTextField txtTenVaiTro;
    private JPanel quyenPanel;

    private JButton btnLuu;
    private JButton btnHuy;

    private final List<JCheckBox> quyenCheckboxes = new ArrayList<JCheckBox>();
    private boolean saved = false;

    public ThemVaiTroDialog(Frame parent, boolean modal) {
        super(parent, "Thêm vai trò", modal);
        initComponents();
        loadQuyen();
    }

    private void initComponents() {
        JPanel formPanel = new JPanel(
                new MigLayout(
                        "wrap 2, insets 15, gapx 10, gapy 8",
                        "[right][grow, fill]",
                        "[]"
                )
        );

        txtTenVaiTro = new JTextField(25);
        formPanel.add(new JLabel("Tên vai trò:"));
        formPanel.add(txtTenVaiTro);

        quyenPanel = new JPanel(new MigLayout("wrap 2, insets 5, gapx 10, gapy 4"));
        quyenPanel.setBorder(BorderFactory.createTitledBorder("Quyền truy cập"));

        JScrollPane scroll = new JScrollPane(quyenPanel);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setPreferredSize(new Dimension(420, 220));

        JPanel centerPanel = new JPanel(new BorderLayout(0, 8));
        centerPanel.add(formPanel, BorderLayout.NORTH);
        centerPanel.add(scroll, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnLuu = new JButton("Lưu");
        btnHuy = new JButton("Hủy");

        int iconSize = 16;
        btnLuu.setIcon(FontIcon.of(FontAwesomeSolid.CHECK_CIRCLE, iconSize, new Color(40, 167, 69)));
        btnHuy.setIcon(FontIcon.of(FontAwesomeSolid.TIMES_CIRCLE, iconSize, new Color(220, 53, 69)));

        buttonPanel.add(btnLuu);
        buttonPanel.add(btnHuy);

        btnLuu.addActionListener(e -> onSave());
        btnHuy.addActionListener(e -> dispose());

        setLayout(new BorderLayout(0, 8));
        add(centerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        if (getWidth() < 520 || getHeight() < 360) {
            setSize(520, 360);
        }
        setLocationRelativeTo(getParent());
        setResizable(false);
    }

    private void loadQuyen() {
        quyenCheckboxes.clear();
        quyenPanel.removeAll();

        List<Quyen> dsQuyen = quyenBUS.getAll();
        for (Quyen q : dsQuyen) {
            String label = getQuyenDisplayName(q);
            JCheckBox cb = new JCheckBox(label);
            cb.setToolTipText(q.getTenQuyen());
            cb.putClientProperty("idQuyen", q.getIdQuyen());
            quyenCheckboxes.add(cb);
            quyenPanel.add(cb);
        }
        quyenPanel.revalidate();
        quyenPanel.repaint();
    }

    private String getQuyenDisplayName(Quyen q) {
        if (q == null) {
            return "";
        }
        int id = q.getIdQuyen();
        switch (id) {
            case 1:
                return "Quản lý sách";
//            case 2:
//                return "Quản lý cuốn sách";
            case 2:
                return "Quản lý nhập sách";
            case 3:
                return "Quản lý độc giả & thẻ";
            case 4:
                return "Quản lý mượn - trả";
            case 5:
                return "Quản lý phiếu phạt";
            case 6:
                return "Quản lý nhân viên";
            default:
                return q.getTenQuyen() != null ? q.getTenQuyen() : "";
        }
    }

    private void onSave() {
        String ten = txtTenVaiTro.getText() == null ? "" : txtTenVaiTro.getText().trim();
        if (ten.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên vai trò không được để trống.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<Integer> quyenIds = new ArrayList<Integer>();
        for (JCheckBox cb : quyenCheckboxes) {
            if (cb.isSelected()) {
                Object v = cb.getClientProperty("idQuyen");
                if (v instanceof Integer) {
                    quyenIds.add((Integer) v);
                }
            }
        }

        try {
            boolean ok = vaiTroBUS.create(ten, quyenIds);
            if (!ok) {
                JOptionPane.showMessageDialog(this, "Thêm vai trò thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            saved = true;
            JOptionPane.showMessageDialog(this, "Thêm vai trò thành công.");
            dispose();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.WARNING_MESSAGE);
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi hệ thống: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isSaved() {
        return saved;
    }
}

