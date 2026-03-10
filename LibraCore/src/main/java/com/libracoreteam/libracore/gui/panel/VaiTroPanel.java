
package com.libracoreteam.libracore.gui.panel;

import com.libracoreteam.libracore.bus.QuyenBUS;
import com.libracoreteam.libracore.bus.VaiTroBUS;
import com.libracoreteam.libracore.gui.dialog.SuaVaiTroDialog;
import com.libracoreteam.libracore.gui.dialog.ThemVaiTroDialog;
import com.libracoreteam.libracore.model.Quyen;
import com.libracoreteam.libracore.model.VaiTro;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

public class VaiTroPanel extends javax.swing.JPanel {

    private final VaiTroBUS vaiTroBUS = new VaiTroBUS();
    private final List<VaiTro> currentList = new ArrayList<VaiTro>();
    private DefaultTableModel tableModel;

    public VaiTroPanel() {
        initComponents();
        InnitButton();
        initTable();
        loadData();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelLeft = new javax.swing.JPanel();
        jPanelLeftTop = new javax.swing.JPanel();
        jPanelCongCu = new javax.swing.JPanel();
        jPanelTimKiem = new javax.swing.JPanel();
        jTextFieldTimKiem = new javax.swing.JTextField();
        jButtonTimKiem = new javax.swing.JButton();
        jButtonLamMoi = new javax.swing.JButton();
        jPanelNutDieuKhien = new javax.swing.JPanel();
        jButtonChiTiet = new javax.swing.JButton();
        jButtonThem = new javax.swing.JButton();
        jButtonSua = new javax.swing.JButton();
        jButtonXoa = new javax.swing.JButton();
        jPanelBoard = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        jPanelLeft.setPreferredSize(new java.awt.Dimension(600, 60));
        jPanelLeft.setLayout(new java.awt.BorderLayout());

        jPanelLeftTop.setMinimumSize(new java.awt.Dimension(551, 43));
        jPanelLeftTop.setLayout(new java.awt.BorderLayout());

        jPanelCongCu.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 0, 20));
        jPanelCongCu.setLayout(new java.awt.BorderLayout());

        jTextFieldTimKiem.setPreferredSize(new java.awt.Dimension(150, 40));
        jTextFieldTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldTimKiemKeyPressed(evt);
            }
        });
        jPanelTimKiem.add(jTextFieldTimKiem);

        jButtonTimKiem.setPreferredSize(new java.awt.Dimension(40, 40));
        jPanelTimKiem.add(jButtonTimKiem);

        jButtonLamMoi.setPreferredSize(new java.awt.Dimension(40, 40));
        jButtonLamMoi.addActionListener(this::jButtonLamMoiActionPerformed);
        jPanelTimKiem.add(jButtonLamMoi);

        jPanelCongCu.add(jPanelTimKiem, java.awt.BorderLayout.WEST);

        jButtonChiTiet.setText("Chi tiết");
        jButtonChiTiet.setPreferredSize(new java.awt.Dimension(90, 40));
        jButtonChiTiet.addActionListener(this::jButtonChiTietActionPerformed);
        jPanelNutDieuKhien.add(jButtonChiTiet);

        jButtonThem.setText("Thêm");
        jButtonThem.setPreferredSize(new java.awt.Dimension(90, 40));
        jButtonThem.addActionListener(this::jButtonThemActionPerformed);
        jPanelNutDieuKhien.add(jButtonThem);

        jButtonSua.setText("Sửa");
        jButtonSua.setPreferredSize(new java.awt.Dimension(90, 40));
        jButtonSua.addActionListener(this::jButtonSuaActionPerformed);
        jPanelNutDieuKhien.add(jButtonSua);

        jButtonXoa.setText("Xóa");
        jButtonXoa.setPreferredSize(new java.awt.Dimension(90, 40));
        jButtonXoa.addActionListener(this::jButtonXoaActionPerformed);
        jPanelNutDieuKhien.add(jButtonXoa);

        jPanelCongCu.add(jPanelNutDieuKhien, java.awt.BorderLayout.EAST);

        jPanelLeftTop.add(jPanelCongCu, java.awt.BorderLayout.CENTER);

        jPanelLeft.add(jPanelLeftTop, java.awt.BorderLayout.NORTH);

        jPanelBoard.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));
        jPanelBoard.setPreferredSize(new java.awt.Dimension(452, 402));
        jPanelBoard.setLayout(new java.awt.BorderLayout());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "ID", "Vai Trò"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jPanelBoard.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanelLeft.add(jPanelBoard, java.awt.BorderLayout.CENTER);

        add(jPanelLeft, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void InnitButton() {
        int iconSize = 16;
        jButtonThem.setIcon(FontIcon.of(FontAwesomeSolid.PLUS_CIRCLE, iconSize, new Color(21, 110, 71)));
        jButtonTimKiem.setIcon(FontIcon.of(FontAwesomeSolid.SEARCH, iconSize, new Color(100, 100, 100)));
        jButtonLamMoi.setIcon(FontIcon.of(FontAwesomeSolid.SYNC_ALT, iconSize, new Color(100, 100, 100)));
        jButtonSua.setIcon(FontIcon.of(FontAwesomeSolid.EDIT, iconSize, new Color(13, 110, 253)));
        jButtonXoa.setIcon(FontIcon.of(FontAwesomeSolid.TRASH, iconSize, new Color(220, 53, 69)));
        jTextFieldTimKiem.putClientProperty("JTextField.placeholderText", "Tìm kiếm");

        jButtonTimKiem.addActionListener(e -> thucHienTimKiem());
        jTextFieldTimKiem.addActionListener(e -> thucHienTimKiem());
    }
    
    private void initTable() {
        tableModel = (DefaultTableModel) jTable1.getModel();
        jTable1.setRowHeight(28);
    }

    public void loadData() {
        List<VaiTro> list = vaiTroBUS.getAll();
        setTableData(list);
    }

    private void setTableData(List<VaiTro> list) {
        currentList.clear();
        tableModel.setRowCount(0);
        if (list == null) {
            return;
        }
        for (VaiTro vt : list) {
            if (vt == null) continue;
            currentList.add(vt);
            tableModel.addRow(new Object[]{vt.getIdVaiTro(), vt.getTenVaiTro()});
        }
    }

    private void thucHienTimKiem() {
        String keyword = jTextFieldTimKiem.getText();
        List<VaiTro> list = vaiTroBUS.search(keyword == null ? "" : keyword.trim());
        setTableData(list);
    }

    private void lamMoi() {
        jTextFieldTimKiem.setText("");
        loadData();
    }
    
   
    private void jButtonThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonThemActionPerformed
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        ThemVaiTroDialog dialog = new ThemVaiTroDialog(parentFrame, true);
        dialog.setVisible(true);
        if (dialog.isSaved()) {
            loadData();
        }
    }//GEN-LAST:event_jButtonThemActionPerformed

    private void jButtonSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSuaActionPerformed
        int row = jTable1.getSelectedRow();
        if (row < 0 || row >= currentList.size()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một vai trò để sửa.");
            return;
        }
        VaiTro selected = currentList.get(row);
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        SuaVaiTroDialog dialog = new SuaVaiTroDialog(parentFrame, true, selected);
        dialog.setVisible(true);
        if (dialog.isSaved()) {
            loadData();
        }
    }//GEN-LAST:event_jButtonSuaActionPerformed

    private void jButtonXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonXoaActionPerformed
        int row = jTable1.getSelectedRow();
        if (row < 0 || row >= currentList.size()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một vai trò để xoá.");
            return;
        }
        VaiTro selected = currentList.get(row);
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc muốn xoá vai trò \"" + selected.getTenVaiTro() + "\"?",
                "Xác nhận xoá",
                JOptionPane.YES_NO_OPTION
        );
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            boolean ok = vaiTroBUS.delete(selected.getIdVaiTro());
            if (!ok) {
                JOptionPane.showMessageDialog(this, "Xoá vai trò thất bại.");
                return;
            }
            JOptionPane.showMessageDialog(this, "Xoá vai trò thành công.");
            loadData();
        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Không thể xoá", JOptionPane.WARNING_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.WARNING_MESSAGE);
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi hệ thống: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonXoaActionPerformed

    private void jButtonLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLamMoiActionPerformed
        lamMoi();
    }//GEN-LAST:event_jButtonLamMoiActionPerformed

    private void jButtonChiTietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonChiTietActionPerformed
        int row = jTable1.getSelectedRow();
        if (row < 0 || row >= currentList.size()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một vai trò để xem chi tiết.");
            return;
        }
        VaiTro selected = currentList.get(row);
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(selected.getIdVaiTro()).append("\n");
        sb.append("Tên vai trò: ").append(selected.getTenVaiTro());

        List<Integer> quyenIds = vaiTroBUS.getQuyenIdsByVaiTro(selected.getIdVaiTro());
        QuyenBUS quyenBUS = new QuyenBUS();
        List<Quyen> allQuyen = quyenBUS.getAll();
        Map<Integer, Quyen> quyenById = new HashMap<Integer, Quyen>();
        for (Quyen q : allQuyen) {
            if (q != null) {
                quyenById.put(q.getIdQuyen(), q);
            }
        }

        List<String> tenQuyenList = new ArrayList<String>();
        for (Integer qid : quyenIds) {
            Quyen q = quyenById.get(qid);
            if (q != null) {
                tenQuyenList.add(getQuyenDisplayName(q));
            }
        }

        sb.append("\nQuyền: ");
        if (tenQuyenList.isEmpty()) {
            sb.append("(Không có quyền nào)");
        } else {
            sb.append(String.join(", ", tenQuyenList));
        }

        JOptionPane.showMessageDialog(this, sb.toString(), "Chi tiết vai trò", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jButtonChiTietActionPerformed

    private void jTextFieldTimKiemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldTimKiemKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            thucHienTimKiem();
        }
    }//GEN-LAST:event_jTextFieldTimKiemKeyPressed

    private String getQuyenDisplayName(Quyen q) {
        if (q == null) {
            return "";
        }
        int id = q.getIdQuyen();
        switch (id) {
            case 1:
                return "Quản lý sách";
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonChiTiet;
    private javax.swing.JButton jButtonLamMoi;
    private javax.swing.JButton jButtonSua;
    private javax.swing.JButton jButtonThem;
    private javax.swing.JButton jButtonTimKiem;
    private javax.swing.JButton jButtonXoa;
    private javax.swing.JPanel jPanelBoard;
    private javax.swing.JPanel jPanelCongCu;
    private javax.swing.JPanel jPanelLeft;
    private javax.swing.JPanel jPanelLeftTop;
    private javax.swing.JPanel jPanelNutDieuKhien;
    private javax.swing.JPanel jPanelTimKiem;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextFieldTimKiem;
    // End of variables declaration//GEN-END:variables
}
