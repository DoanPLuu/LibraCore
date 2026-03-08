/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.libracoreteam.libracore.gui.panel;

import com.libracoreteam.libracore.bus.NhanVienBUS;
import com.libracoreteam.libracore.gui.dialog.SuaNhanVienDialog;
import com.libracoreteam.libracore.gui.dialog.ThemNhanVienDialog;
import com.libracoreteam.libracore.model.NhanVien;
import com.libracoreteam.libracore.util.ImageHelper;

import java.awt.Color;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class NhanVienPanel extends javax.swing.JPanel {
 
  private DefaultTableModel tableModel;
  private javax.swing.JLabel jLabelAnh;
  
  private final NhanVienBUS nhanVienBUS = new NhanVienBUS();
  
  private List<NhanVien> fullListCache = new ArrayList<>();
  
  private final java.time.format.DateTimeFormatter dateFormatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
  
  /**
   * Creates new form NhanVienPanel
   */
  public NhanVienPanel() {
    initComponents();
    initAnhPanel();
    customComponents();
    initTable();
    loadDataToTable();
    setupRealTimeSearch();
  }
  
  private void initAnhPanel() {
        jLabelAnh = new javax.swing.JLabel();
        jLabelAnh.setPreferredSize(new java.awt.Dimension(150, 180));
        jLabelAnh.setMinimumSize(new java.awt.Dimension(150, 180));
        jLabelAnh.setMaximumSize(new java.awt.Dimension(150, 180));
        jLabelAnh.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.GRAY));
        jLabelAnh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelAnh.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelAnh.setText("Ảnh");
        jLabelAnh.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);

        javax.swing.JPanel anhWrapper = new javax.swing.JPanel();
        anhWrapper.setLayout(new javax.swing.BoxLayout(anhWrapper, javax.swing.BoxLayout.Y_AXIS));
        anhWrapper.setBorder(javax.swing.BorderFactory.createEmptyBorder(6, 0, 6, 0));
        anhWrapper.add(jLabelAnh);

        jPanelFields.remove(filler1);
        jPanelFields.add(anhWrapper);
        jPanelFields.add(filler1);
        jPanelFields.revalidate();
    }

    private void customComponents() {
        int iconSize = 16;
        jButtonThem.setIcon(FontIcon.of(FontAwesomeSolid.PLUS_CIRCLE, iconSize, new Color(21, 110, 71)));
        jButtonSua.setIcon(FontIcon.of(FontAwesomeSolid.EDIT, iconSize, new Color(13, 110, 253)));
        jButtonXoa.setIcon(FontIcon.of(FontAwesomeSolid.TRASH, iconSize, new Color(220, 53, 69)));
        jButtonTimKiem.setIcon(FontIcon.of(FontAwesomeSolid.SEARCH, iconSize, new Color(100, 100, 100)));
        jButtonLamMoi.setIcon(FontIcon.of(FontAwesomeSolid.SYNC_ALT, iconSize, new Color(100, 100, 100)));
        jTextFieldTimKiem.putClientProperty("JTextField.placeholderText", "Tìm kiếm...");
    }

    private void initTable() {
        tableModel = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Mã NV", "Họ Tên", "Ngày Sinh", "SĐT", "Email"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jTable1.setModel(tableModel);
        jTable1.setRowHeight(30);
        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTable1.getTableHeader().setReorderingAllowed(false);
    }

    private void loadDataToTable() {
        fullListCache = nhanVienBUS.getActive(); // Nạp đầy kho RAM
        jTextFieldTimKiem.setText("");
        performSearch(); // Tự động hiển thị toàn bộ do ô tìm kiếm đang trống
    }

    private void performSearch() {
        String keyword = jTextFieldTimKiem.getText().trim().toLowerCase();
        tableModel.setRowCount(0); // Xóa sạch bảng cũ
        
        if (keyword.isEmpty() || "tìm kiếm...".equals(keyword)) {
            // Nếu không gõ gì -> In toàn bộ kho ra bảng
            for (NhanVien nv : fullListCache) {
                addRowToTable(nv);
            }
        } else {
            // Biến cục bộ chỉ tồn tại tạm thời lúc đang tìm kiếm
            List<NhanVien> danhSachUuTien = new ArrayList<>();
            List<NhanVien> danhSachLienQuan = new ArrayList<>();
            
            for (NhanVien nv : fullListCache) {
                boolean matchIdExact = String.valueOf(nv.getIdNhanVien()).equals(keyword);
                boolean matchIdPartial = String.valueOf(nv.getIdNhanVien()).contains(keyword);
                boolean matchTen = nv.getTenNhanVien() != null && nv.getTenNhanVien().toLowerCase().contains(keyword);
                boolean matchSdt = nv.getSdt() != null && nv.getSdt().contains(keyword);
                boolean matchEmail = nv.getEmail() != null && nv.getEmail().toLowerCase().contains(keyword);
                
                String ngaySinhStr = (nv.getNgaySinh() != null) ? nv.getNgaySinh().format(dateFormatter) : "";
                boolean matchNgaySinh = ngaySinhStr.contains(keyword);
                
                if (matchIdExact) {
                    danhSachUuTien.add(nv); // Gõ đúng ID -> Lên top 1
                } else if (matchIdPartial || matchTen || matchSdt || matchEmail || matchNgaySinh) {
                    danhSachLienQuan.add(nv); // Chỉ chứa từ khóa -> Xếp sau
                }
            }
            
            // Đổ thẳng 2 danh sách vừa lọc lên bảng
            for (NhanVien nv : danhSachUuTien) addRowToTable(nv);
            for (NhanVien nv : danhSachLienQuan) addRowToTable(nv);
        }
        clearFields();
    }
    
    private void setupRealTimeSearch() {
        jTextFieldTimKiem.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
        @Override public void insertUpdate(javax.swing.event.DocumentEvent e) { performSearch(); }
        @Override public void removeUpdate(javax.swing.event.DocumentEvent e) { performSearch(); }
        @Override public void changedUpdate(javax.swing.event.DocumentEvent e) { performSearch(); }
        });
    }
    
    private void addRowToTable(NhanVien nv) {
        String ngaySinhStr = (nv.getNgaySinh() != null) ? nv.getNgaySinh().format(dateFormatter) : "";
        tableModel.addRow(new Object[]{
            nv.getIdNhanVien(),
            nv.getTenNhanVien(),
            ngaySinhStr, 
            nv.getSdt(),
            nv.getEmail()
        });
    }
    
    
    private void clearFields() {
        jTextFieldMaNhanVien.setText("");
        jTextFieldTenNhanVien.setText("");
        jTextFieldNgaySinh.setText("");
        jTextAreaDiaChi.setText("");
        jTextFieldSDT.setText("");
        jTextFieldEmail.setText("");
        if(jLabelAnh != null) {
            jLabelAnh.setIcon(null);
            jLabelAnh.setText("Ảnh");
        }
        jTable1.clearSelection();
    }

  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelRight = new javax.swing.JPanel();
        jPanelTop = new javax.swing.JPanel();
        jLabelTitle = new javax.swing.JLabel();
        jPanelBottom = new javax.swing.JPanel();
        jPanelFields = new javax.swing.JPanel();
        jPanelMaNhanVien = new javax.swing.JPanel();
        jLabelMaNhanVien = new javax.swing.JLabel();
        jTextFieldMaNhanVien = new javax.swing.JTextField();
        jPanelTenNhanVien = new javax.swing.JPanel();
        jLabelTenNhanVien = new javax.swing.JLabel();
        jTextFieldTenNhanVien = new javax.swing.JTextField();
        jPanelNgaySinh = new javax.swing.JPanel();
        jLabelTenNgaySinh = new javax.swing.JLabel();
        jTextFieldNgaySinh = new javax.swing.JTextField();
        jPanelDiaChi = new javax.swing.JPanel();
        jLabelDiaChi = new javax.swing.JLabel();
        jScrollPaneDiaChi = new javax.swing.JScrollPane();
        jTextAreaDiaChi = new javax.swing.JTextArea();
        jPanelSDT = new javax.swing.JPanel();
        jLabelSDT = new javax.swing.JLabel();
        jTextFieldSDT = new javax.swing.JTextField();
        jPanelEmail = new javax.swing.JPanel();
        jLabelEmail = new javax.swing.JLabel();
        jTextFieldEmail = new javax.swing.JTextField();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
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

        setLayout(new java.awt.BorderLayout());

        jPanelRight.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 0, 50, 20));
        jPanelRight.setMinimumSize(new java.awt.Dimension(300, 306));
        jPanelRight.setPreferredSize(new java.awt.Dimension(306, 306));
        jPanelRight.setLayout(new java.awt.BorderLayout());

        jLabelTitle.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabelTitle.setText("THÔNG TIN NHÂN VIÊN");
        jPanelTop.add(jLabelTitle);

        jPanelRight.add(jPanelTop, java.awt.BorderLayout.PAGE_START);

        jPanelBottom.setLayout(new javax.swing.BoxLayout(jPanelBottom, javax.swing.BoxLayout.Y_AXIS));

        jPanelFields.setLayout(new javax.swing.BoxLayout(jPanelFields, javax.swing.BoxLayout.Y_AXIS));

        jPanelMaNhanVien.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        jPanelMaNhanVien.setLayout(new java.awt.GridLayout(0, 1));

        jLabelMaNhanVien.setText("Mã Nhân Viên");
        jPanelMaNhanVien.add(jLabelMaNhanVien);

        jTextFieldMaNhanVien.setEditable(false);
        jTextFieldMaNhanVien.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldMaNhanVien.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jPanelMaNhanVien.add(jTextFieldMaNhanVien);

        jPanelFields.add(jPanelMaNhanVien);

        jPanelTenNhanVien.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        jPanelTenNhanVien.setLayout(new java.awt.GridLayout(0, 1));

        jLabelTenNhanVien.setText("Họ và Tên");
        jPanelTenNhanVien.add(jLabelTenNhanVien);

        jTextFieldTenNhanVien.setEditable(false);
        jTextFieldTenNhanVien.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldTenNhanVien.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jPanelTenNhanVien.add(jTextFieldTenNhanVien);

        jPanelFields.add(jPanelTenNhanVien);

        jPanelNgaySinh.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        jPanelNgaySinh.setLayout(new java.awt.GridLayout(0, 1));

        jLabelTenNgaySinh.setText("Ngày Sinh");
        jPanelNgaySinh.add(jLabelTenNgaySinh);

        jTextFieldNgaySinh.setEditable(false);
        jTextFieldNgaySinh.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldNgaySinh.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jPanelNgaySinh.add(jTextFieldNgaySinh);

        jPanelFields.add(jPanelNgaySinh);

        jPanelDiaChi.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        jPanelDiaChi.setMaximumSize(new java.awt.Dimension(2147483647, 200));
        jPanelDiaChi.setPreferredSize(new java.awt.Dimension(234, 100));
        jPanelDiaChi.setLayout(new java.awt.BorderLayout());

        jLabelDiaChi.setText("Địa Chỉ");
        jPanelDiaChi.add(jLabelDiaChi, java.awt.BorderLayout.NORTH);

        jTextAreaDiaChi.setEditable(false);
        jTextAreaDiaChi.setBackground(new java.awt.Color(255, 255, 255));
        jTextAreaDiaChi.setColumns(20);
        jTextAreaDiaChi.setLineWrap(true);
        jTextAreaDiaChi.setRows(3);
        jTextAreaDiaChi.setWrapStyleWord(true);
        jTextAreaDiaChi.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jScrollPaneDiaChi.setViewportView(jTextAreaDiaChi);

        jPanelDiaChi.add(jScrollPaneDiaChi, java.awt.BorderLayout.CENTER);

        jPanelFields.add(jPanelDiaChi);

        jPanelSDT.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        jPanelSDT.setLayout(new java.awt.GridLayout(0, 1));

        jLabelSDT.setText("SDT");
        jPanelSDT.add(jLabelSDT);

        jTextFieldSDT.setEditable(false);
        jTextFieldSDT.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldSDT.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jPanelSDT.add(jTextFieldSDT);

        jPanelFields.add(jPanelSDT);

        jPanelEmail.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        jPanelEmail.setLayout(new java.awt.GridLayout(0, 1));

        jLabelEmail.setText("Email");
        jPanelEmail.add(jLabelEmail);

        jTextFieldEmail.setEditable(false);
        jTextFieldEmail.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldEmail.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jPanelEmail.add(jTextFieldEmail);

        jPanelFields.add(jPanelEmail);
        jPanelFields.add(filler1);

        jPanelBottom.add(jPanelFields);

        jPanelRight.add(jPanelBottom, java.awt.BorderLayout.CENTER);

        add(jPanelRight, java.awt.BorderLayout.EAST);

        jPanelLeft.setPreferredSize(new java.awt.Dimension(600, 60));
        jPanelLeft.setLayout(new java.awt.BorderLayout());

        jPanelLeftTop.setMinimumSize(new java.awt.Dimension(551, 43));
        jPanelLeftTop.setLayout(new java.awt.BorderLayout());

        jPanelCongCu.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 0, 20));
        jPanelCongCu.setLayout(new java.awt.BorderLayout());

        jTextFieldTimKiem.setPreferredSize(new java.awt.Dimension(150, 40));
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
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã Nhân Viên", "Họ Và Tên", "Ngày Sinh", "Địa Chỉ", "SDT", "Email"
            }
        ));
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable1.setShowGrid(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setHeaderValue("Mã Nhân Viên");
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setHeaderValue("Họ Và Tên");
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setHeaderValue("Ngày Sinh");
            jTable1.getColumnModel().getColumn(3).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setHeaderValue("Địa Chỉ");
            jTable1.getColumnModel().getColumn(4).setHeaderValue("SDT");
            jTable1.getColumnModel().getColumn(5).setHeaderValue("Email");
        }

        jPanelBoard.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanelLeft.add(jPanelBoard, java.awt.BorderLayout.CENTER);

        add(jPanelLeft, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonThemActionPerformed
        ThemNhanVienDialog dialog = new ThemNhanVienDialog(
            (java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this), true);
        dialog.setVisible(true);

        if (dialog.isSaved()) {
            loadDataToTable();
            clearFields();
        }
    }//GEN-LAST:event_jButtonThemActionPerformed

    private void jButtonSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSuaActionPerformed
        int selectedRow = jTable1.getSelectedRow();

        if (selectedRow < 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần sửa!", "Cảnh báo",
            javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Lấy ID nhân viên từ bảng
        int idNhanVien = (int) tableModel.getValueAt(selectedRow, 0);

        // Lấy thông tin chi tiết từ BUS
        NhanVien nv = nhanVienBUS.getById(idNhanVien);

        if (nv != null) {
            SuaNhanVienDialog dialog = new SuaNhanVienDialog(
            (java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this), true, nv);
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
            javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần xóa!", "Cảnh báo",
            javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Lấy ID nhân viên từ bảng
        int idNhanVien = (int) tableModel.getValueAt(selectedRow, 0);

        // Xác nhận trước khi xóa
        int confirm = javax.swing.JOptionPane.showConfirmDialog(
        this,"Bạn có chắc chắn muốn xóa nhân viên này?", "Xác nhận xóa", javax.swing.JOptionPane.YES_NO_OPTION);

        if (confirm == javax.swing.JOptionPane.YES_OPTION) {
            if (nhanVienBUS.delete(idNhanVien)) {
                javax.swing.JOptionPane.showMessageDialog(this, "Xóa nhân viên thành công!");
                loadDataToTable();
                clearFields();
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "Lỗi khi xóa nhân viên!", "Lỗi",
                javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButtonXoaActionPerformed

    private void jButtonLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLamMoiActionPerformed
        loadDataToTable(); // Tải lại dữ liệu mới nhất
        jTextFieldTimKiem.setText(""); //Reset thanh tìm kiếm
    }//GEN-LAST:event_jButtonLamMoiActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int selectedRow = jTable1.getSelectedRow();

        // Kiểm tra xem dòng được click có hợp lệ không
        if (selectedRow >= 0) {
            
            // 1. Lấy ID nhân viên từ cột đầu tiên (cột 0) của bảng
            int idNhanVien = (int) jTable1.getValueAt(selectedRow, 0);

            // 2. Tìm nhân viên có mã ID này trong kho RAM (fullListCache)
            NhanVien nv = null;
            for (NhanVien item : fullListCache) {
                if (item.getIdNhanVien() == idNhanVien) {
                    nv = item;
                    break; // Tìm thấy rồi thì dừng vòng lặp cho nhẹ máy
                }
            }

            // 3. Đẩy dữ liệu lên form nếu tìm thấy
            if (nv != null) {
                // --- Đẩy dữ liệu VĂN BẢN ---
                jTextFieldMaNhanVien.setText(String.valueOf(nv.getIdNhanVien()));
                jTextFieldTenNhanVien.setText(nv.getTenNhanVien() != null ? nv.getTenNhanVien() : "");
                jTextFieldNgaySinh.setText(nv.getNgaySinh() != null ? nv.getNgaySinh().format(dateFormatter) : "");
                jTextAreaDiaChi.setText(nv.getDiaChi() != null ? nv.getDiaChi() : "");
                jTextFieldSDT.setText(nv.getSdt() != null ? nv.getSdt() : "");
                jTextFieldEmail.setText(nv.getEmail() != null ? nv.getEmail() : "");

                // --- Đẩy dữ liệu HÌNH ẢNH (1 luồng cơ bản) ---
                String anhPath = nv.getAnhNhanVien();
        
                if (anhPath == null || anhPath.trim().isEmpty()) {
                     // Nếu Database không lưu đường dẫn ảnh -> Xóa trắng khung ảnh
                    jLabelAnh.setIcon(null);
                    jLabelAnh.setText("Ảnh");
                } else {
                    // Tạm dừng một chút để đọc và cắt ảnh từ ổ cứng
                    javax.swing.ImageIcon icon = ImageHelper.loadImage(anhPath, 150, 180);
            
                    // Đọc xong thì dán ảnh lên giao diện
                    if (icon != null) {
                        jLabelAnh.setIcon(icon);
                        jLabelAnh.setText("");
                    } else {
                        jLabelAnh.setIcon(null);
                        jLabelAnh.setText("Lỗi ảnh"); // File ảnh trên máy tính bị xóa mất
                    }
                }
            }
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButtonTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTimKiemActionPerformed
        performSearch();
    }//GEN-LAST:event_jButtonTimKiemActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler filler1;
    private javax.swing.JButton jButtonLamMoi;
    private javax.swing.JButton jButtonSua;
    private javax.swing.JButton jButtonThem;
    private javax.swing.JButton jButtonTimKiem;
    private javax.swing.JButton jButtonXoa;
    private javax.swing.JLabel jLabelDiaChi;
    private javax.swing.JLabel jLabelEmail;
    private javax.swing.JLabel jLabelMaNhanVien;
    private javax.swing.JLabel jLabelSDT;
    private javax.swing.JLabel jLabelTenNgaySinh;
    private javax.swing.JLabel jLabelTenNhanVien;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JPanel jPanelBoard;
    private javax.swing.JPanel jPanelBottom;
    private javax.swing.JPanel jPanelCongCu;
    private javax.swing.JPanel jPanelDiaChi;
    private javax.swing.JPanel jPanelEmail;
    private javax.swing.JPanel jPanelFields;
    private javax.swing.JPanel jPanelLeft;
    private javax.swing.JPanel jPanelLeftTop;
    private javax.swing.JPanel jPanelMaNhanVien;
    private javax.swing.JPanel jPanelNgaySinh;
    private javax.swing.JPanel jPanelNutDieuKhien;
    private javax.swing.JPanel jPanelRight;
    private javax.swing.JPanel jPanelSDT;
    private javax.swing.JPanel jPanelTenNhanVien;
    private javax.swing.JPanel jPanelTimKiem;
    private javax.swing.JPanel jPanelTop;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPaneDiaChi;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextAreaDiaChi;
    private javax.swing.JTextField jTextFieldEmail;
    private javax.swing.JTextField jTextFieldMaNhanVien;
    private javax.swing.JTextField jTextFieldNgaySinh;
    private javax.swing.JTextField jTextFieldSDT;
    private javax.swing.JTextField jTextFieldTenNhanVien;
    private javax.swing.JTextField jTextFieldTimKiem;
    // End of variables declaration//GEN-END:variables
}
