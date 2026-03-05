/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.libracoreteam.libracore.gui.panel;

import java.awt.Color;
import javax.swing.JOptionPane;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;
import javax.swing.event.ListSelectionEvent;

public class NCCPanel extends javax.swing.JPanel {
    private final com.libracoreteam.libracore.bus.NCCBUS nccBUS = new com.libracoreteam.libracore.bus.NCCBUS();
    private javax.swing.table.DefaultTableModel tblModel;
    private java.util.List<com.libracoreteam.libracore.model.NCC> currentList = new java.util.ArrayList<>();
    private com.libracoreteam.libracore.model.NCC currentSelected = null;
    private boolean editMode = false;
    private boolean isLoadingSelection = false;
    
    public NCCPanel() {
        initComponents();
        InnitButton();
        initTable();
        addTableListener();
        loadDataToTable();
        bindEvents();
    }
    
        private void InnitButton() {
            // Cấu hình tập trung tại đây giúp dễ bảo trì màu sắc/kích thước đồng bộ
            int iconSize = 16;

            jButtonThem.setIcon(FontIcon.of(FontAwesomeSolid.PLUS_CIRCLE, iconSize, new Color(21, 110, 71)));
            jButtonTimKiem.setIcon(FontIcon.of(FontAwesomeSolid.SEARCH, iconSize, new Color(100, 100, 100)));
            jButtonLamMoi.setIcon(FontIcon.of(FontAwesomeSolid.SYNC_ALT, iconSize, new Color(100, 100, 100)));
            jButtonXoa.setIcon(FontIcon.of(FontAwesomeSolid.TRASH, iconSize, new Color(220, 53, 69)));
            jButtonSua.setIcon(FontIcon.of(FontAwesomeSolid.EDIT, iconSize, new Color(13, 110, 253)));
        }
        private void initTable() {
        // Tạo model với 2 cột và không cho phép click đúp để sửa trực tiếp trên bảng
        tblModel = new javax.swing.table.DefaultTableModel(new Object[]{"Mã NCC", "Tên nhà cung cấp"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        jTableSach1.setModel(tblModel);
        
        // Chỉnh kích thước cột (Cột Mã nhỏ lại, cột Tên phình to ra)
        jTableSach1.getColumnModel().getColumn(0).setPreferredWidth(100);
        jTableSach1.getColumnModel().getColumn(0).setMaxWidth(150);
        jTableSach1.getColumnModel().getColumn(1).setPreferredWidth(400);

        // Tranh thủ style lại bảng cho đẹp giống SachPanel
        jTableSach1.setRowHeight(30);
        jTableSach1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    }
        private void bindEvents() {
        // 1. Sự kiện click chọn 1 dòng trên bảng NCC
        jTableSach1.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = jTableSach1.getSelectedRow();
                // Kiểm tra xem có dòng nào được chọn không và index có hợp lệ không
                if (selectedRow >= 0 && selectedRow < currentList.size()) {
                    currentSelected = currentList.get(selectedRow);
                    
                    // Hiển thị thông tin lên Form bên phải
                    jTextFieldMaNCC.setText(String.valueOf(currentSelected.getIdNCC()));
                    jTextTenNCC.setText(currentSelected.getTenNCC());
                } else {
                    currentSelected = null;
                }
            }
        });

        // 2. Cảm biến Live Search: Tự động chạy khi có thay đổi chữ trong ô Textfield
        jTextFieldTimKiem.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { thucHienTimKiem(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { thucHienTimKiem(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { thucHienTimKiem(); }
        });
    }

    // 2. Hàm lấy dữ liệu từ DB đổ lên bảng
    public void loadDataToTable() {
        tblModel.setRowCount(0); // Xóa trắng dữ liệu cũ
        try {
            // ĐÃ SỬA TẠI ĐÂY: Phải gán dữ liệu vào biến currentList của class
            currentList = nccBUS.getAll(); 
            
            for (com.libracoreteam.libracore.model.NCC ncc : currentList) {
                tblModel.addRow(new Object[]{
                    ncc.getIdNCC(),
                    ncc.getTenNCC()
                });
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu: " + e.getMessage(), "Lỗi", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }
    // Động cơ Live Search cho NCC
    private void thucHienTimKiem() {
        String keyword = jTextFieldTimKiem.getText();
        if (keyword != null) {
            keyword = keyword.trim();
        }

        // Nếu ô tìm kiếm rỗng hoặc đang là chữ placeholder "Tìm kiếm..."
        if (keyword == null || keyword.isEmpty() || "Tìm kiếm...".equalsIgnoreCase(keyword)) {
            loadDataToTable(); // Load lại toàn bộ dữ liệu
            
            // Xóa trắng form thông tin
            jTableSach1.clearSelection();
            jTextFieldMaNCC.setText("");
            jTextTenNCC.setText("");
            currentSelected = null;
            return;
        }

        try {
            // Gọi BUS để tìm kiếm
            currentList = nccBUS.search(keyword);
            
            // Xóa dữ liệu cũ trên bảng và đổ dữ liệu mới vào
            tblModel.setRowCount(0);
            for (com.libracoreteam.libracore.model.NCC ncc : currentList) {
                tblModel.addRow(new Object[]{
                    ncc.getIdNCC(),
                    ncc.getTenNCC()
                });
            }
            
            // Xóa trắng các ô textfield bên phải sau khi bảng thay đổi
            jTableSach1.clearSelection();
            jTextFieldMaNCC.setText("");
            jTextTenNCC.setText("");
            currentSelected = null;
            
        } catch (Exception ex) {
            System.out.println("Lỗi tìm kiếm Live Search: " + ex.getMessage());
        }
    }

    // 3. Hàm bắt sự kiện khi click vào một dòng trong bảng
        private void addTableListener() {
        jTableSach1.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = jTableSach1.getSelectedRow();
                // Kiểm tra xem có dòng nào được chọn không và index có hợp lệ không
                if (selectedRow >= 0 && selectedRow < currentList.size()) {
                    // Cập nhật biến currentSelected giống như bên TacGia
                    currentSelected = currentList.get(selectedRow);
                    
                    // Hiển thị thông tin lên Form bên phải
                    jTextFieldMaNCC.setText(String.valueOf(currentSelected.getIdNCC()));
                    jTextTenNCC.setText(currentSelected.getTenNCC());
                } else {
                    currentSelected = null;
                }
            }
        });
    }

  
        
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelLeft = new javax.swing.JPanel();
        jPanelLeftTop = new javax.swing.JPanel();
        jPanelCongCu = new javax.swing.JPanel();
        jPanelTimKiem = new javax.swing.JPanel();
        jTextFieldTimKiem = new javax.swing.JTextField();
        jButtonTimKiem = new javax.swing.JButton();
        jButtonLamMoi = new javax.swing.JButton();
        jPanelNutThem = new javax.swing.JPanel();
        jButtonThem = new javax.swing.JButton();
        jButtonSua = new javax.swing.JButton();
        jButtonXoa = new javax.swing.JButton();
        jPanelBoard = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableSach1 = new javax.swing.JTable();
        jPanelRight = new javax.swing.JPanel();
        jPanelTop = new javax.swing.JPanel();
        jLabelTitle = new javax.swing.JLabel();
        jPanelBottom = new javax.swing.JPanel();
        jPanelFields = new javax.swing.JPanel();
        jPanelMa = new javax.swing.JPanel();
        jLabelMaNCC = new javax.swing.JLabel();
        jTextFieldMaNCC = new javax.swing.JTextField();
        jPanelTen = new javax.swing.JPanel();
        jLabelTenNCC = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextTenNCC = new javax.swing.JTextArea();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
        filler5 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
        filler6 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
        filler7 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
        filler8 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
        filler9 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
        filler10 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));

        setLayout(new java.awt.BorderLayout());

        jPanelLeft.setLayout(new java.awt.BorderLayout());

        jPanelLeftTop.setBackground(new java.awt.Color(255, 153, 153));
        jPanelLeftTop.setLayout(new java.awt.BorderLayout());

        jPanelCongCu.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 0, 20));
        jPanelCongCu.setLayout(new java.awt.BorderLayout());

        jTextFieldTimKiem.setText("Tìm kiếm...");
        jTextFieldTimKiem.setPreferredSize(new java.awt.Dimension(150, 40));
        jTextFieldTimKiem.addActionListener(this::jTextFieldTimKiemActionPerformed);
        jPanelTimKiem.add(jTextFieldTimKiem);

        jButtonTimKiem.setPreferredSize(new java.awt.Dimension(40, 40));
        jButtonTimKiem.addActionListener(this::jButtonTimKiemActionPerformed);
        jPanelTimKiem.add(jButtonTimKiem);

        jButtonLamMoi.setPreferredSize(new java.awt.Dimension(40, 40));
        jButtonLamMoi.addActionListener(this::jButtonLamMoiActionPerformed);
        jPanelTimKiem.add(jButtonLamMoi);

        jPanelCongCu.add(jPanelTimKiem, java.awt.BorderLayout.WEST);

        jButtonThem.setText("Thêm");
        jButtonThem.setPreferredSize(new java.awt.Dimension(90, 40));
        jButtonThem.addActionListener(this::jButtonThemActionPerformed);
        jPanelNutThem.add(jButtonThem);

        jButtonSua.setText("Sửa");
        jButtonSua.setPreferredSize(new java.awt.Dimension(90, 40));
        jButtonSua.addActionListener(this::jButtonSuaActionPerformed);
        jPanelNutThem.add(jButtonSua);

        jButtonXoa.setText("Xóa");
        jButtonXoa.setPreferredSize(new java.awt.Dimension(90, 40));
        jButtonXoa.addActionListener(this::jButtonXoaActionPerformed);
        jPanelNutThem.add(jButtonXoa);

        jPanelCongCu.add(jPanelNutThem, java.awt.BorderLayout.EAST);

        jPanelLeftTop.add(jPanelCongCu, java.awt.BorderLayout.CENTER);

        jPanelLeft.add(jPanelLeftTop, java.awt.BorderLayout.NORTH);

        jPanelBoard.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));
        jPanelBoard.setLayout(new java.awt.BorderLayout());

        jTableSach1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Mã nhà cung cấp", "Tên nhà cung cấp"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTableSach1);

        jPanelBoard.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanelLeft.add(jPanelBoard, java.awt.BorderLayout.CENTER);

        add(jPanelLeft, java.awt.BorderLayout.CENTER);

        jPanelRight.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 0, 50, 20));
        jPanelRight.setMinimumSize(new java.awt.Dimension(300, 306));
        jPanelRight.setPreferredSize(new java.awt.Dimension(306, 306));
        jPanelRight.setLayout(new java.awt.BorderLayout());

        jLabelTitle.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabelTitle.setText("THÔNG TIN NHÀ CUNG CẤP");
        jPanelTop.add(jLabelTitle);

        jPanelRight.add(jPanelTop, java.awt.BorderLayout.PAGE_START);

        jPanelBottom.setLayout(new javax.swing.BoxLayout(jPanelBottom, javax.swing.BoxLayout.Y_AXIS));

        jPanelFields.setLayout(new javax.swing.BoxLayout(jPanelFields, javax.swing.BoxLayout.Y_AXIS));

        jPanelMa.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        jPanelMa.setLayout(new java.awt.GridLayout(0, 1));

        jLabelMaNCC.setText("Mã nhà cung cấp:");
        jPanelMa.add(jLabelMaNCC);

        jTextFieldMaNCC.setEditable(false);
        jTextFieldMaNCC.setFocusable(false);
        jTextFieldMaNCC.addActionListener(this::jTextFieldMaNCCActionPerformed);
        jPanelMa.add(jTextFieldMaNCC);

        jPanelFields.add(jPanelMa);

        jPanelTen.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        jPanelTen.setPreferredSize(new java.awt.Dimension(234, 105));
        jPanelTen.setLayout(new java.awt.GridLayout(0, 1));

        jLabelTenNCC.setText("Tên nhà cung cấp:");
        jLabelTenNCC.setPreferredSize(new java.awt.Dimension(95, 16));
        jPanelTen.add(jLabelTenNCC);

        jTextTenNCC.setEditable(false);
        jTextTenNCC.setColumns(20);
        jTextTenNCC.setRows(5);
        jTextTenNCC.setPreferredSize(new java.awt.Dimension(200, 60));
        jScrollPane2.setViewportView(jTextTenNCC);

        jPanelTen.add(jScrollPane2);

        jPanelFields.add(jPanelTen);
        jPanelFields.add(filler2);
        jPanelFields.add(filler3);
        jPanelFields.add(filler4);
        jPanelFields.add(filler5);
        jPanelFields.add(filler6);
        jPanelFields.add(filler7);
        jPanelFields.add(filler8);
        jPanelFields.add(filler9);
        jPanelFields.add(filler10);

        jPanelBottom.add(jPanelFields);

        jPanelRight.add(jPanelBottom, java.awt.BorderLayout.CENTER);

        add(jPanelRight, java.awt.BorderLayout.EAST);
    }// </editor-fold>//GEN-END:initComponents
    
    
    private void jButtonTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTimKiemActionPerformed
        thucHienTimKiem();
    }//GEN-LAST:event_jButtonTimKiemActionPerformed

    private void jButtonLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLamMoiActionPerformed
        jTextFieldTimKiem.setText("Tìm kiếm...");
        
        // Load lại toàn bộ dữ liệu từ DB
        loadDataToTable();
        
        // Bỏ chọn bảng và làm sạch form thông tin
        jTableSach1.clearSelection();
        jTextFieldMaNCC.setText("");
        jTextTenNCC.setText("");
        currentSelected = null;
    }//GEN-LAST:event_jButtonLamMoiActionPerformed

    private void jButtonThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonThemActionPerformed
        java.awt.Window owner = javax.swing.SwingUtilities.getWindowAncestor(this);
    
    // 2. Mở hộp thoại Thêm NCC (Truyền thêm tham số true để khóa màn hình nền)
    com.libracoreteam.libracore.gui.dialog.ThemNCCDialog dialog = new com.libracoreteam.libracore.gui.dialog.ThemNCCDialog(owner, true);
    dialog.setVisible(true); 
    
    // 3. Sau khi hộp thoại đóng lại, kiểm tra xem đã thêm thành công chưa
    System.out.println("Dialog Thêm nhà cung cắp đã đóng, tiến hành reload lại bảng...");
    }//GEN-LAST:event_jButtonThemActionPerformed

    private void jTextFieldMaNCCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldMaNCCActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldMaNCCActionPerformed

    private void jButtonXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonXoaActionPerformed
                // TODO add your handling code here:                                        
        if (currentSelected == null) {
            javax.swing.JOptionPane.showMessageDialog(this, 
                    "Vui lòng chọn một nhà cung cấp trên bảng để xóa!", 
                    "Thông báo", 
                    javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 2. Hiện hộp thoại xác nhận xóa
        int choice = javax.swing.JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn xóa (ngừng hoạt động) nhà cung cấp:\n\"" + currentSelected.getTenNCC() + "\" không?",
                "Xác nhận xóa",
                javax.swing.JOptionPane.YES_NO_OPTION,
                javax.swing.JOptionPane.QUESTION_MESSAGE
        );

        // 3. Nếu chọn YES thì tiến hành gọi BUS để xóa
        if (choice == javax.swing.JOptionPane.YES_OPTION) {
            try {
                boolean isDeleted = nccBUS.softDelete(currentSelected.getIdNCC());

                if (isDeleted) {
                    javax.swing.JOptionPane.showMessageDialog(this, "Đã xóa nhà cung cấp thành công!");
                    
                    // Tải lại bảng, xóa trắng form
                    loadDataToTable();
                    jTextFieldMaNCC.setText("");
                    jTextTenNCC.setText("");
                    currentSelected = null;
                    jTableSach1.clearSelection();
                } else {
                    javax.swing.JOptionPane.showMessageDialog(this, "Xóa thất bại. Vui lòng thử lại!", "Lỗi", javax.swing.JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                javax.swing.JOptionPane.showMessageDialog(this, "Lỗi hệ thống: " + ex.getMessage(), "Lỗi", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButtonXoaActionPerformed

    private void jButtonSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSuaActionPerformed
        // TODO add your handling code here:
        if (currentSelected == null) {
            javax.swing.JOptionPane.showMessageDialog(this, 
                    "Vui lòng chọn một nhà cung cấp trên bảng để sửa!", 
                    "Thông báo", 
                    javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 2. Lấy Frame cha và mở hộp thoại Sửa
        java.awt.Window owner = javax.swing.SwingUtilities.getWindowAncestor(this);
        com.libracoreteam.libracore.gui.dialog.SuaNCCDialog dialog = 
                new com.libracoreteam.libracore.gui.dialog.SuaNCCDialog((java.awt.Frame) owner, true, currentSelected);
        
        // Hiển thị hộp thoại Sửa
        dialog.setVisible(true); 

        // 3. Sau khi tắt hộp thoại, tải lại dữ liệu và xóa trắng form
        loadDataToTable();
        jTextFieldMaNCC.setText("");
        jTextTenNCC.setText("");
        currentSelected = null;
        jTableSach1.clearSelection();
    }//GEN-LAST:event_jButtonSuaActionPerformed

    private void jTextFieldTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldTimKiemActionPerformed
        // TODO add your handling code here:
        thucHienTimKiem();
    }//GEN-LAST:event_jTextFieldTimKiemActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler filler10;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.Box.Filler filler5;
    private javax.swing.Box.Filler filler6;
    private javax.swing.Box.Filler filler7;
    private javax.swing.Box.Filler filler8;
    private javax.swing.Box.Filler filler9;
    private javax.swing.JButton jButtonLamMoi;
    private javax.swing.JButton jButtonSua;
    private javax.swing.JButton jButtonThem;
    private javax.swing.JButton jButtonTimKiem;
    private javax.swing.JButton jButtonXoa;
    private javax.swing.JLabel jLabelMaNCC;
    private javax.swing.JLabel jLabelTenNCC;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JPanel jPanelBoard;
    private javax.swing.JPanel jPanelBottom;
    private javax.swing.JPanel jPanelCongCu;
    private javax.swing.JPanel jPanelFields;
    private javax.swing.JPanel jPanelLeft;
    private javax.swing.JPanel jPanelLeftTop;
    private javax.swing.JPanel jPanelMa;
    private javax.swing.JPanel jPanelNutThem;
    private javax.swing.JPanel jPanelRight;
    private javax.swing.JPanel jPanelTen;
    private javax.swing.JPanel jPanelTimKiem;
    private javax.swing.JPanel jPanelTop;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableSach1;
    private javax.swing.JTextField jTextFieldMaNCC;
    private javax.swing.JTextField jTextFieldTimKiem;
    private javax.swing.JTextArea jTextTenNCC;
    // End of variables declaration//GEN-END:variables
}
