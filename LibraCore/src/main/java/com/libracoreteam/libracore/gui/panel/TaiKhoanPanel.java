/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.libracoreteam.libracore.gui.panel;

import com.formdev.flatlaf.FlatClientProperties;
import java.awt.Color;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.table.DefaultTableModel;
import com.libracoreteam.libracore.bus.TaiKhoanBUS;
import com.libracoreteam.libracore.bus.VaiTroBUS;
import com.libracoreteam.libracore.model.TaiKhoan;
import com.libracoreteam.libracore.model.VaiTro;
import com.libracoreteam.libracore.gui.dialog.SuaTaiKhoanDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ASUS
 */
public class TaiKhoanPanel extends javax.swing.JPanel {
    
    private DefaultTableModel tableModel;
    private final TaiKhoanBUS taiKhoanBUS = new TaiKhoanBUS();
    private Map<Integer, String> cacheVaiTroNames;
    
    // Cache trên RAM để tìm kiếm và lấy dữ liệu siêu tốc
    private List<TaiKhoan> fullListCache = new ArrayList<>();
    private List<TaiKhoan> currentList = new ArrayList<>();

    /**
     * Creates new form TaiKhoanPanel
     */
    public TaiKhoanPanel() {
        initComponents();
        customComponets();
        initTable();
        bindEvents();
        loadDataToTable();
        
    }
    
    private void customComponets() {
        int iconSize = 16;
        jButtonThem.setIcon(FontIcon.of(FontAwesomeSolid.PLUS_CIRCLE, iconSize, new Color(21, 110, 71)));
        jButtonTimKiem.setIcon(FontIcon.of(FontAwesomeSolid.SEARCH, iconSize, new Color(100, 100, 100)));
        jButtonLamMoi.setIcon(FontIcon.of(FontAwesomeSolid.SYNC_ALT, iconSize, new Color(100, 100, 100)));
        jButtonSua.setIcon(FontIcon.of(FontAwesomeSolid.EDIT, iconSize, new Color(13, 110, 253)));
        jButtonXoa.setIcon(FontIcon.of(FontAwesomeSolid.TRASH, iconSize, new Color(220, 53, 69)));
        jTextFieldTimKiem.putClientProperty("JTextField.placeholderText", "Tìm kiếm");
        jPasswordField1.putClientProperty(FlatClientProperties.STYLE, "showRevealButton: true");
    }
    
    private void initTable() {
        tableModel = new DefaultTableModel(
            new Object [][] {},
            new String [] {"Mã Tài Khoản", "Tên Đăng Nhập", "Vai Trò"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jTable1.setModel(tableModel);
        jTable1.setRowHeight(30);   
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable1.getTableHeader().setReorderingAllowed(false);
    }
    
    private void bindEvents() {
        
        jTextFieldTimKiem.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override public void insertUpdate(javax.swing.event.DocumentEvent e) { performSearch(); }
            @Override public void removeUpdate(javax.swing.event.DocumentEvent e) { performSearch(); }
            @Override public void changedUpdate(javax.swing.event.DocumentEvent e) { performSearch(); }
        });
    }
   
    // =================================== LOGIC DỮ LIỆU ===================================
    private void renderTable(List<TaiKhoan> list) {
        tableModel.setRowCount(0);
        for (TaiKhoan tk : list) {
            Object[] row = {
                tk.getIdTaiKhoan(),
                tk.getTaiKhoan(),
                getVaiTroName(tk.getIdVaiTro())
            };
            tableModel.addRow(row);
        }
    }

    private void loadDataToTable() {
        fullListCache = taiKhoanBUS.getAll();
        currentList = new ArrayList<>(fullListCache);
        renderTable(currentList);
        clearFields();
    }

    private void performSearch() {
        String keyword = jTextFieldTimKiem.getText().trim().toLowerCase();
        
        if (keyword.isEmpty() || "tìm kiếm...".equals(keyword)) {
            currentList = new ArrayList<>(fullListCache);
        } else {
            currentList = new ArrayList<>();
            List<TaiKhoan> danhSachUuTien = new ArrayList<>();
            List<TaiKhoan> danhSachLienQuan = new ArrayList<>();
            
            for (TaiKhoan tk : fullListCache) {
                // Kiểm tra điều kiện
                boolean matchIdExact = String.valueOf(tk.getIdTaiKhoan()).equals(keyword);
                boolean matchIdPartial = String.valueOf(tk.getIdTaiKhoan()).contains(keyword);
                boolean matchTen = tk.getTaiKhoan() != null && tk.getTaiKhoan().toLowerCase().contains(keyword);
                String tenVaiTro = getVaiTroName(tk.getIdVaiTro()).toLowerCase();
                boolean matchVaiTro = tenVaiTro.contains(keyword);
                
                // Phân loại nhóm hiển thị
                if (matchIdExact) {
                    danhSachUuTien.add(tk);
                } else if (matchIdPartial || matchTen || matchVaiTro) {
                    danhSachLienQuan.add(tk);
                }
            }
            // Gộp danh sách, ưu tiên ID lên đầu
            currentList.addAll(danhSachUuTien);
            currentList.addAll(danhSachLienQuan);
        }
        renderTable(currentList);
        clearFields();
    }

    private String getVaiTroName(int idVaiTro) {
        if (cacheVaiTroNames == null) {
            cacheVaiTroNames = new HashMap<>();
            VaiTroBUS vaiTroBUS = new VaiTroBUS();
            List<VaiTro> dsVaiTro = vaiTroBUS.getAll();
            if (dsVaiTro != null) {
                for (VaiTro vt : dsVaiTro) {
                    if (vt != null) cacheVaiTroNames.put(vt.getIdVaiTro(), vt.getTenVaiTro());
                }
            }
        }
        String name = cacheVaiTroNames.get(idVaiTro);
        return name != null ? name : "Khác";
    }

    private void clearFields() {
        jTextFieldMaTaiKhoan.setText("");
        jTextFieldTenDangNhap.setText("");
        jPasswordField1.setText("");
        jTable1.clearSelection();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
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
        jButtonThem = new javax.swing.JButton();
        jButtonSua = new javax.swing.JButton();
        jButtonXoa = new javax.swing.JButton();
        jPanelBoard = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanelRight = new javax.swing.JPanel();
        jPanelTop = new javax.swing.JPanel();
        jLabelTitle = new javax.swing.JLabel();
        jPanelBottom = new javax.swing.JPanel();
        jPanelFields = new javax.swing.JPanel();
        jPanelMaTaiKhoan = new javax.swing.JPanel();
        jLabelMaTaiKhoan = new javax.swing.JLabel();
        jTextFieldMaTaiKhoan = new javax.swing.JTextField();
        jPanelTenDangNhap = new javax.swing.JPanel();
        jLabelTenDangNhap = new javax.swing.JLabel();
        jTextFieldTenDangNhap = new javax.swing.JTextField();
        jPanelMatKhau = new javax.swing.JPanel();
        jLabelMatKhau = new javax.swing.JLabel();
        jPasswordField1 = new javax.swing.JPasswordField();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
        filler5 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
        filler6 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
        filler7 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
        filler8 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));

        setLayout(new java.awt.BorderLayout());

        jPanelLeft.setPreferredSize(new java.awt.Dimension(600, 60));
        jPanelLeft.setLayout(new java.awt.BorderLayout());

        jPanelLeftTop.setMinimumSize(new java.awt.Dimension(551, 43));
        jPanelLeftTop.setLayout(new java.awt.BorderLayout());

        jPanelCongCu.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 0, 20));
        jPanelCongCu.setLayout(new java.awt.BorderLayout());

        jTextFieldTimKiem.setPreferredSize(new java.awt.Dimension(150, 40));
        jPanelTimKiem.add(jTextFieldTimKiem);

        jButtonTimKiem.setPreferredSize(new java.awt.Dimension(40, 40));
        jPanelTimKiem.add(jButtonTimKiem);

        jButtonLamMoi.setPreferredSize(new java.awt.Dimension(40, 40));
        jButtonLamMoi.addActionListener(this::jButtonLamMoiActionPerformed);
        jPanelTimKiem.add(jButtonLamMoi);

        jPanelCongCu.add(jPanelTimKiem, java.awt.BorderLayout.WEST);

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
                "ID", "Tên Đăng Nhập"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanelBoard.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanelLeft.add(jPanelBoard, java.awt.BorderLayout.CENTER);

        add(jPanelLeft, java.awt.BorderLayout.CENTER);

        jPanelRight.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 0, 50, 20));
        jPanelRight.setMinimumSize(new java.awt.Dimension(300, 306));
        jPanelRight.setPreferredSize(new java.awt.Dimension(306, 306));
        jPanelRight.setLayout(new java.awt.BorderLayout());

        jLabelTitle.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabelTitle.setText("THÔNG TIN TÀI KHOẢN");
        jPanelTop.add(jLabelTitle);

        jPanelRight.add(jPanelTop, java.awt.BorderLayout.PAGE_START);

        jPanelBottom.setLayout(new javax.swing.BoxLayout(jPanelBottom, javax.swing.BoxLayout.Y_AXIS));

        jPanelFields.setLayout(new javax.swing.BoxLayout(jPanelFields, javax.swing.BoxLayout.Y_AXIS));

        jPanelMaTaiKhoan.setLayout(new java.awt.GridLayout(0, 1));

        jLabelMaTaiKhoan.setText("Mã Tài Khoản");
        jPanelMaTaiKhoan.add(jLabelMaTaiKhoan);

        jTextFieldMaTaiKhoan.setEditable(false);
        jTextFieldMaTaiKhoan.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldMaTaiKhoan.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jPanelMaTaiKhoan.add(jTextFieldMaTaiKhoan);

        jPanelFields.add(jPanelMaTaiKhoan);

        jPanelTenDangNhap.setLayout(new java.awt.GridLayout(0, 1));

        jLabelTenDangNhap.setText("Tên Đăng Nhập");
        jPanelTenDangNhap.add(jLabelTenDangNhap);

        jTextFieldTenDangNhap.setEditable(false);
        jTextFieldTenDangNhap.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldTenDangNhap.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jPanelTenDangNhap.add(jTextFieldTenDangNhap);

        jPanelFields.add(jPanelTenDangNhap);

        jPanelMatKhau.setLayout(new java.awt.GridLayout(0, 1));

        jLabelMatKhau.setText("Mật Khẩu");
        jPanelMatKhau.add(jLabelMatKhau);

        jPasswordField1.setEditable(false);
        jPasswordField1.setBackground(new java.awt.Color(255, 255, 255));
        jPanelMatKhau.add(jPasswordField1);

        jPanelFields.add(jPanelMatKhau);
        jPanelFields.add(filler1);
        jPanelFields.add(filler2);
        jPanelFields.add(filler3);
        jPanelFields.add(filler4);
        jPanelFields.add(filler5);
        jPanelFields.add(filler6);
        jPanelFields.add(filler7);
        jPanelFields.add(filler8);

        jPanelBottom.add(jPanelFields);

        jPanelRight.add(jPanelBottom, java.awt.BorderLayout.CENTER);

        add(jPanelRight, java.awt.BorderLayout.EAST);
    }// </editor-fold>//GEN-END:initComponents

    
    
    
    
    private void jButtonThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonThemActionPerformed
        // 1. Lấy danh sách nhân viên đang hoạt động từ BUS
        com.libracoreteam.libracore.bus.NhanVienBUS nhanVienBUS = new com.libracoreteam.libracore.bus.NhanVienBUS();
        java.util.List<com.libracoreteam.libracore.model.NhanVien> dsNhanVien = nhanVienBUS.getActive();
        
        // 2. Kiểm tra xem có ai chưa có tài khoản không (idTaiKhoan == null)
        boolean coNguoiChuaCoTK = false;
        for (com.libracoreteam.libracore.model.NhanVien nv : dsNhanVien) {
            if (nv.getIdTaiKhoan() == null) {
                coNguoiChuaCoTK = true;
                break; // Chỉ cần tìm thấy 1 người là đủ điều kiện mở form, thoát vòng lặp cho nhẹ máy
            }
        }
        
        // 3. Nếu không có ai (tất cả đều đã có tài khoản) -> Chặn lại và báo lỗi
        if (!coNguoiChuaCoTK) {
            javax.swing.JOptionPane.showMessageDialog(this, 
                "Hiện tại tất cả nhân viên đều đã được cấp tài khoản!", 
                "Thông báo", 
                javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return; // Lệnh return này sẽ kết thúc hàm ngay lập tức, KHÔNG chạy xuống code mở Dialog bên dưới
        }
        
        // 4. Nếu qua được bước trên (nghĩa là có người chưa có TK) -> Mở form Thêm bình thường
        com.libracoreteam.libracore.gui.dialog.ThemTaiKhoanDialog dialog = 
                new com.libracoreteam.libracore.gui.dialog.ThemTaiKhoanDialog(
                        (java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this), true);
        dialog.setVisible(true);
        
        // 5. Sau khi đóng form Thêm, nếu có lưu thì load lại bảng
        if (dialog.isSaved()) {
            loadDataToTable();
            clearFields();
        }
    }//GEN-LAST:event_jButtonThemActionPerformed

    private void jButtonSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSuaActionPerformed
        int selectedRow = jTable1.getSelectedRow();
        
        if (selectedRow < 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng chọn tài khoản cần sửa!", "Cảnh báo", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int idTaiKhoan = (int) tableModel.getValueAt(selectedRow, 0);
        TaiKhoan tk = taiKhoanBUS.getById(idTaiKhoan);
        
        if (tk != null) {
            SuaTaiKhoanDialog dialog = new SuaTaiKhoanDialog((java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this), true, tk);
            dialog.setVisible(true);
            
            if (dialog.isSaved()) {
                loadDataToTable();
                clearFields();
            }
        }
    }//GEN-LAST:event_jButtonSuaActionPerformed

    private void jButtonXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonXoaActionPerformed
        int selectedRow = jTable1.getSelectedRow();
        
        if (selectedRow < 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng chọn tài khoản cần xóa!", "Cảnh báo", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int idTaiKhoan = (int) tableModel.getValueAt(selectedRow, 0);
        
        int confirm = javax.swing.JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn xóa tài khoản này?",
                "Xác nhận xóa",
                javax.swing.JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == javax.swing.JOptionPane.YES_OPTION) {
            if (taiKhoanBUS.delete(idTaiKhoan)) {
                javax.swing.JOptionPane.showMessageDialog(this, "Xóa tài khoản thành công!");
                loadDataToTable();
                clearFields();
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "Lỗi khi xóa tài khoản!", "Lỗi", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButtonXoaActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
                                     
        // 1. Xác định dòng người dùng vừa click
        int selectedRow = jTable1.getSelectedRow();

        // 2. Kiểm tra tính hợp lệ của dòng (tránh lỗi click vào khoảng trống)
        if (selectedRow >= 0 && selectedRow < currentList.size()) {
        
            // 3. Lấy đối tượng TaiKhoan tương ứng từ danh sách RAM (currentList)
            TaiKhoan tk = currentList.get(selectedRow);

            if (tk != null) {
                // 4. Đổ dữ liệu vào các ô nhập liệu bên phải
                jTextFieldMaTaiKhoan.setText(String.valueOf(tk.getIdTaiKhoan()));
                jTextFieldTenDangNhap.setText(tk.getTaiKhoan() != null ? tk.getTaiKhoan() : "");
            
                
                // Lấy trực tiếp từ thuộc tính MatKhau của đối tượng tk
                String matKhauThat = (tk.getMatKhau() != null) ? tk.getMatKhau() : "";
                jPasswordField1.setText(matKhauThat);
            }
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButtonLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLamMoiActionPerformed
        loadDataToTable(); // Tải lại dữ liệu mới nhất
        jTextFieldTimKiem.setText(""); //Reset thanh tìm kiếm
    }//GEN-LAST:event_jButtonLamMoiActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.Box.Filler filler5;
    private javax.swing.Box.Filler filler6;
    private javax.swing.Box.Filler filler7;
    private javax.swing.Box.Filler filler8;
    private javax.swing.JButton jButtonLamMoi;
    private javax.swing.JButton jButtonSua;
    private javax.swing.JButton jButtonThem;
    private javax.swing.JButton jButtonTimKiem;
    private javax.swing.JButton jButtonXoa;
    private javax.swing.JLabel jLabelMaTaiKhoan;
    private javax.swing.JLabel jLabelMatKhau;
    private javax.swing.JLabel jLabelTenDangNhap;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JPanel jPanelBoard;
    private javax.swing.JPanel jPanelBottom;
    private javax.swing.JPanel jPanelCongCu;
    private javax.swing.JPanel jPanelFields;
    private javax.swing.JPanel jPanelLeft;
    private javax.swing.JPanel jPanelLeftTop;
    private javax.swing.JPanel jPanelMaTaiKhoan;
    private javax.swing.JPanel jPanelMatKhau;
    private javax.swing.JPanel jPanelNutDieuKhien;
    private javax.swing.JPanel jPanelRight;
    private javax.swing.JPanel jPanelTenDangNhap;
    private javax.swing.JPanel jPanelTimKiem;
    private javax.swing.JPanel jPanelTop;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextFieldMaTaiKhoan;
    private javax.swing.JTextField jTextFieldTenDangNhap;
    private javax.swing.JTextField jTextFieldTimKiem;
    // End of variables declaration//GEN-END:variables
}
