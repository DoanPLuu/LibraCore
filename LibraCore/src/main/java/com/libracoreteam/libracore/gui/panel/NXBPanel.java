
package com.libracoreteam.libracore.gui.panel;

import com.libracoreteam.libracore.bus.NXBBUS;
import com.libracoreteam.libracore.gui.dialog.ThemNXBDialog;
import com.libracoreteam.libracore.gui.dialog.SuaNXBDialog;
import com.libracoreteam.libracore.model.NXB;
import java.awt.*;
import org.kordamp.ikonli.swing.FontIcon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.Box;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class NXBPanel extends javax.swing.JPanel {

    private final NXBBUS nxbBUS = new NXBBUS();

    private DefaultTableModel tblModel;
    private List<NXB> currentList = new ArrayList<>();
    private NXB currentSelected = null;
    private boolean editMode = false;
    private boolean isLoadingSelection = false;

   
    public NXBPanel() {
        initComponents();
        InnitButton();
        initTable();
        bindEvents();
        loadActiveToTable();
        setEditMode(false);
    }
    
    

  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jPanelLeft = new javax.swing.JPanel();
        jPanelLeftTop = new javax.swing.JPanel();
        jPanelCongCu = new javax.swing.JPanel();
        jPanelTimKiem = new javax.swing.JPanel();
        jTextFieldTimKiem = new javax.swing.JTextField();
        jButtonTimKiem = new javax.swing.JButton();
        jButtonLamMoi = new javax.swing.JButton();
        jPanelNutThem = new javax.swing.JPanel();
        jButtonXuat = new javax.swing.JButton();
        jButtonThem = new javax.swing.JButton();
        jButtonSua = new javax.swing.JButton();
        jButtonXoa = new javax.swing.JButton();
        jPanelBoard = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableSach = new javax.swing.JTable();
        jPanelRight = new javax.swing.JPanel();
        jPanelTop = new javax.swing.JPanel();
        jLabelTitle = new javax.swing.JLabel();
        jPanelBottom = new javax.swing.JPanel();
        jPanelFields = new javax.swing.JPanel();
        jPanelMa = new javax.swing.JPanel();
        jLabelMaNXB = new javax.swing.JLabel();
        jTextFieldMaNXB = new javax.swing.JTextField();
        jPanelTen = new javax.swing.JPanel();
        jLabelTenNXB = new javax.swing.JLabel();
        jTextFieldTenNXB = new javax.swing.JTextField();
        jPanelDiaChi = new javax.swing.JPanel();
        jLabelDiaChiNXB = new javax.swing.JLabel();
        jTextFieldDiaChiNXB = new javax.swing.JTextField();
        jPanelSDT = new javax.swing.JPanel();
        jLabelSDT = new javax.swing.JLabel();
        jTextFieldSDT = new javax.swing.JTextField();
        jPanelTrangThai = new javax.swing.JPanel();
        jPanelButton = new javax.swing.JPanel();
        jButtonXacNhan = new javax.swing.JButton();
        jButtonHuy = new javax.swing.JButton();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
        filler5 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));

        jMenuItem1.setText("jMenuItem1");

        setLayout(new java.awt.BorderLayout());

        jPanelLeft.setLayout(new java.awt.BorderLayout());

        jPanelLeftTop.setBackground(new java.awt.Color(255, 153, 153));
        jPanelLeftTop.setLayout(new java.awt.BorderLayout());

        jPanelCongCu.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 0, 20));
        jPanelCongCu.setLayout(new java.awt.BorderLayout());

        jTextFieldTimKiem.setText("Tìm kiếm...");
        jTextFieldTimKiem.setPreferredSize(new java.awt.Dimension(150, 40));
        jTextFieldTimKiem.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextFieldTimKiemFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldTimKiemFocusLost(evt);
            }
        });
        jPanelTimKiem.add(jTextFieldTimKiem);

        jButtonTimKiem.setPreferredSize(new java.awt.Dimension(40, 40));
        jButtonTimKiem.addActionListener(this::jButtonTimKiemActionPerformed);
        jPanelTimKiem.add(jButtonTimKiem);

        jButtonLamMoi.setPreferredSize(new java.awt.Dimension(40, 40));
        jButtonLamMoi.addActionListener(this::jButtonLamMoiActionPerformed);
        jPanelTimKiem.add(jButtonLamMoi);

        jPanelCongCu.add(jPanelTimKiem, java.awt.BorderLayout.WEST);

        jButtonXuat.setText("Xuất");
        jButtonXuat.setPreferredSize(new java.awt.Dimension(90, 40));
        jButtonXuat.addActionListener(this::jButtonXuatActionPerformed);
        jPanelNutThem.add(jButtonXuat);

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

        jTableSach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableSach.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTableSach.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTableSach.getTableHeader().setResizingAllowed(false);
        jTableSach.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTableSach);

        jPanelBoard.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanelLeft.add(jPanelBoard, java.awt.BorderLayout.CENTER);

        add(jPanelLeft, java.awt.BorderLayout.CENTER);

        jPanelRight.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 0, 50, 20));
        jPanelRight.setMinimumSize(new java.awt.Dimension(300, 306));
        jPanelRight.setPreferredSize(new java.awt.Dimension(306, 306));
        jPanelRight.setLayout(new java.awt.BorderLayout());

        jLabelTitle.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabelTitle.setText("THÔNG TIN NHÀ XUẤT BẢN");
        jPanelTop.add(jLabelTitle);

        jPanelRight.add(jPanelTop, java.awt.BorderLayout.PAGE_START);

        jPanelBottom.setLayout(new javax.swing.BoxLayout(jPanelBottom, javax.swing.BoxLayout.Y_AXIS));

        jPanelFields.setLayout(new javax.swing.BoxLayout(jPanelFields, javax.swing.BoxLayout.Y_AXIS));

        jPanelMa.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        jPanelMa.setLayout(new java.awt.GridLayout(0, 1));

        jLabelMaNXB.setText("Mã NXB:");
        jPanelMa.add(jLabelMaNXB);

        jTextFieldMaNXB.setEditable(false);
        jTextFieldMaNXB.setFocusable(false);
        jTextFieldMaNXB.addActionListener(this::jTextFieldMaNXBActionPerformed);
        jPanelMa.add(jTextFieldMaNXB);

        jPanelFields.add(jPanelMa);

        jPanelTen.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        jPanelTen.setLayout(new java.awt.GridLayout(0, 1));

        jLabelTenNXB.setText("Tên NXB:");
        jPanelTen.add(jLabelTenNXB);

        jTextFieldTenNXB.setEditable(false);
        jTextFieldTenNXB.setFocusable(false);
        jPanelTen.add(jTextFieldTenNXB);

        jPanelFields.add(jPanelTen);

        jPanelDiaChi.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        jPanelDiaChi.setLayout(new java.awt.GridLayout(0, 1));

        jLabelDiaChiNXB.setText("Địa chỉ NXB:");
        jPanelDiaChi.add(jLabelDiaChiNXB);

        jTextFieldDiaChiNXB.setEditable(false);
        jTextFieldDiaChiNXB.setFocusable(false);
        jTextFieldDiaChiNXB.addActionListener(this::jTextFieldDiaChiNXBActionPerformed);
        jPanelDiaChi.add(jTextFieldDiaChiNXB);

        jPanelFields.add(jPanelDiaChi);

        jPanelSDT.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        jPanelSDT.setLayout(new java.awt.GridLayout(0, 1));

        jLabelSDT.setText("Số điện thoại NXB:");
        jPanelSDT.add(jLabelSDT);

        jTextFieldSDT.setEditable(false);
        jTextFieldSDT.setFocusable(false);
        jTextFieldSDT.addActionListener(this::jTextFieldSDTActionPerformed);
        jPanelSDT.add(jTextFieldSDT);

        jPanelFields.add(jPanelSDT);

        jPanelTrangThai.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        jPanelTrangThai.setLayout(new java.awt.GridLayout(0, 1));
        jPanelFields.add(jPanelTrangThai);

        jPanelButton.setMinimumSize(new java.awt.Dimension(250, 60));
        jPanelButton.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));

        jButtonXacNhan.setText("Xác nhận");
        jButtonXacNhan.setPreferredSize(new java.awt.Dimension(110, 40));
        jButtonXacNhan.addActionListener(this::jButtonXacNhanActionPerformed);
        jPanelButton.add(jButtonXacNhan);

        jButtonHuy.setText("Huỷ");
        jButtonHuy.setPreferredSize(new java.awt.Dimension(110, 40));
        jPanelButton.add(jButtonHuy);

        jPanelFields.add(jPanelButton);
        jPanelFields.add(filler2);
        jPanelFields.add(filler3);
        jPanelFields.add(filler4);
        jPanelFields.add(filler5);

        jPanelBottom.add(jPanelFields);

        jPanelRight.add(jPanelBottom, java.awt.BorderLayout.CENTER);

        add(jPanelRight, java.awt.BorderLayout.EAST);
    }// </editor-fold>//GEN-END:initComponents

    private void InnitButton() {
            int iconSize = 16;

            jButtonThem.setIcon(FontIcon.of(FontAwesomeSolid.PLUS_CIRCLE, iconSize, new Color(21, 110, 71)));
            jButtonXuat.setIcon(FontIcon.of(FontAwesomeSolid.FILE_EXPORT, iconSize, new Color(100, 100, 100)));
            jButtonTimKiem.setIcon(FontIcon.of(FontAwesomeSolid.SEARCH, iconSize, new Color(100, 100, 100)));
            jButtonLamMoi.setIcon(FontIcon.of(FontAwesomeSolid.SYNC_ALT, iconSize, new Color(100, 100, 100)));
            jButtonXacNhan.setIcon(FontIcon.of(FontAwesomeSolid.CHECK_CIRCLE, iconSize, new Color(0, 100, 0)));
            jButtonHuy.setIcon(FontIcon.of(FontAwesomeSolid.TIMES_CIRCLE, iconSize, new Color(100, 0, 0)));
            jPanelButton.add(Box.createRigidArea(new Dimension(0, 40)));

            jButtonSua.setIcon(FontIcon.of(FontAwesomeSolid.EDIT, iconSize, new Color(13, 110, 253)));
            jButtonXoa.setIcon(FontIcon.of(FontAwesomeSolid.TRASH, iconSize, new Color(220, 53, 69)));

    }

    private void initTable() {
        tblModel = new DefaultTableModel(
                new Object[]{"Mã", "Tên NXB", "Địa chỉ", "SĐT"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        jTableSach.setModel(tblModel);
        jTableSach.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTableSach.getTableHeader().setReorderingAllowed(false);

        jTableSach.setSelectionBackground(new Color(220, 220, 220)); 
        jTableSach.setSelectionForeground(Color.BLACK);              
        jTableSach.setRowHeight(30);
    }

    private void bindEvents() {
        jTableSach.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (e.getValueIsAdjusting()) return;
            if (isLoadingSelection) return;
            int row = jTableSach.getSelectedRow();
            if (row < 0 || row >= currentList.size()) return;

            currentSelected = currentList.get(row);
            fillDetail(currentSelected);
            setEditMode(false);
        });

        jButtonHuy.addActionListener(evt -> {
            if (editMode && currentSelected != null) {
                fillDetail(currentSelected); 
                setEditMode(false);
            } else {
                jTableSach.clearSelection();
                clearDetail();
                currentSelected = null;
                setEditMode(false);
            }
        });
    }

    public void loadActiveToTable() {
        try {
            currentList = nxbBUS.getActive();
            renderTable(currentList);
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, "Không tải được dữ liệu NXB: " + ex.getMessage());
        }
    }

    private void renderTable(List<NXB> list) {
        if (tblModel == null) return;
        tblModel.setRowCount(0);
        for (NXB n : list) {
            tblModel.addRow(new Object[]{
                    n.getIdNXB(),
                    n.getTenNXB(),
                    n.getDiaChi(),
                    n.getSdt()
            });
        }
    }

    private void fillDetail(NXB n) {
        if (n == null) return;
        jTextFieldMaNXB.setText(String.valueOf(n.getIdNXB()));
        jTextFieldTenNXB.setText(n.getTenNXB());
        jTextFieldDiaChiNXB.setText(n.getDiaChi());
        jTextFieldSDT.setText(n.getSdt());
    }

    private void clearDetail() {
        jTextFieldMaNXB.setText("");
        jTextFieldTenNXB.setText("");
        jTextFieldDiaChiNXB.setText("");
        jTextFieldSDT.setText("");
    }

    private void setEditMode(boolean on) {
        editMode = on;

        jButtonXacNhan.setVisible(on);
        jButtonHuy.setVisible(on);

        jTableSach.setEnabled(!on);
        jTableSach.setRowSelectionAllowed(!on);

        jTextFieldTenNXB.setEditable(on);
        jTextFieldTenNXB.setFocusable(on);

        jTextFieldDiaChiNXB.setEditable(on);
        jTextFieldDiaChiNXB.setFocusable(on);

        jTextFieldSDT.setEditable(on);
        jTextFieldSDT.setFocusable(on);

        jTextFieldMaNXB.setEditable(false);
        jTextFieldMaNXB.setFocusable(false);

        jButtonXacNhan.setText("Lưu");
    }

    private void selectRowById(int id) {
        if (currentList == null || currentList.isEmpty()) return;
        for (int i = 0; i < currentList.size(); i++) {
            if (currentList.get(i).getIdNXB() == id) {
                isLoadingSelection = true;
                try {
                    jTableSach.setRowSelectionInterval(i, i);
                } finally {
                    isLoadingSelection = false;
                }
                return;
            }
        }
    }

    private void deleteByRow(int viewRow) {
        if (viewRow < 0 || viewRow >= currentList.size()) return;
        NXB n = currentList.get(viewRow);

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc muốn xoá (ngừng hoạt động) NXB \"" + n.getTenNXB() + "\" không?",
                "Xác nhận xoá",
                JOptionPane.YES_NO_OPTION
        );
        if (choice != JOptionPane.YES_OPTION) return;

        try {
            boolean ok = nxbBUS.softDelete(n.getIdNXB());
            if (!ok) {
                JOptionPane.showMessageDialog(this, "Xoá thất bại.");
                return;
            }
            JOptionPane.showMessageDialog(this, "Đã xoá (ngừng hoạt động).");
            loadActiveToTable();
            jTableSach.clearSelection();
            clearDetail();
            currentSelected = null;
            setEditMode(false);
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi hệ thống: " + ex.getMessage());
        }
    }

    
    private void jButtonThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonThemActionPerformed
        javax.swing.JFrame parentFrame =
                (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);
        ThemNXBDialog dialog = new ThemNXBDialog(parentFrame, true);
        dialog.setVisible(true);
        loadActiveToTable();
        setEditMode(false);
    }//GEN-LAST:event_jButtonThemActionPerformed

    private void jTextFieldMaNXBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldMaNXBActionPerformed
    }//GEN-LAST:event_jTextFieldMaNXBActionPerformed

    private void jTextFieldDiaChiNXBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldDiaChiNXBActionPerformed
    }//GEN-LAST:event_jTextFieldDiaChiNXBActionPerformed

    private void jTextFieldSDTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldSDTActionPerformed
    }//GEN-LAST:event_jTextFieldSDTActionPerformed

    private void jButtonXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonXuatActionPerformed
        if (editMode) {
            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Bạn đang ở chế độ sửa. Vẫn muốn xuất danh sách không?",
                    "Xác nhận xuất",
                    JOptionPane.YES_NO_OPTION
            );
            if (choice != JOptionPane.YES_OPTION) return;
        }

        List<NXB> listToExport = (currentList != null) ? currentList : new ArrayList<>();
        if (listToExport.isEmpty()) {
            try {
                listToExport = nxbBUS.getActive();
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(this, "Không tải được dữ liệu để xuất: " + ex.getMessage());
                return;
            }
        }

        if (listToExport.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Danh sách trống, không có gì để xuất.");
            return;
        }

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Xuất danh sách NXB ra Excel");
        chooser.setFileFilter(new FileNameExtensionFilter("Excel (*.xlsx)", "xlsx"));
        chooser.setAcceptAllFileFilterUsed(true);

        String defaultName = "NXB_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".xlsx";
        chooser.setSelectedFile(new File(defaultName));

        int result = chooser.showSaveDialog(this);
        if (result != JFileChooser.APPROVE_OPTION) return;

        File file = chooser.getSelectedFile();
        if (file == null) return;

        String path = file.getAbsolutePath();
        if (!path.toLowerCase().endsWith(".xlsx")) {
            file = new File(path + ".xlsx");
        }

        try {
            exportNXBToExcel(file, listToExport);
            JOptionPane.showMessageDialog(this, "Xuất Excel thành công:\n" + file.getAbsolutePath());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Xuất Excel thất bại: " + ex.getMessage());
        }
    }//GEN-LAST:event_jButtonXuatActionPerformed

    private void exportNXBToExcel(File file, List<NXB> list) throws IOException {
        try (Workbook wb = new XSSFWorkbook()) {
            Sheet sheet = wb.createSheet("NXB");

            CellStyle headerStyle = wb.createCellStyle();
            Font headerFont = wb.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            int r = 0;
            Row header = sheet.createRow(r++);
            String[] headers = {"Mã NXB", "Tên NXB", "Địa chỉ", "Số điện thoại"};
            for (int c = 0; c < headers.length; c++) {
                Cell cell = header.createCell(c);
                cell.setCellValue(headers[c]);
                cell.setCellStyle(headerStyle);
            }

            for (NXB n : list) {
                Row row = sheet.createRow(r++);
                row.createCell(0).setCellValue(n.getIdNXB());
                row.createCell(1).setCellValue(nullSafe(n.getTenNXB()));
                row.createCell(2).setCellValue(nullSafe(n.getDiaChi()));
                row.createCell(3).setCellValue(nullSafe(n.getSdt()));
            }

            for (int c = 0; c < headers.length; c++) {
                sheet.autoSizeColumn(c);
            }

            try (FileOutputStream fos = new FileOutputStream(file)) {
                wb.write(fos);
            }
        }
    }

    private String nullSafe(String s) {
        return s == null ? "" : s;
    }

    private void jButtonTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTimKiemActionPerformed
        String keyword = jTextFieldTimKiem.getText();
        if (keyword != null) keyword = keyword.trim();

        if (keyword == null || keyword.isEmpty() || "Tìm kiếm...".equalsIgnoreCase(keyword)) {
            loadActiveToTable();
        } else {
            try {
                currentList = nxbBUS.searchActive(keyword);
                renderTable(currentList);
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(this, "Tìm kiếm thất bại: " + ex.getMessage());
            }
        }

        jTableSach.clearSelection();
        clearDetail();
        currentSelected = null;
        setEditMode(false);
    }//GEN-LAST:event_jButtonTimKiemActionPerformed

    private void jButtonLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLamMoiActionPerformed
        jTextFieldTimKiem.setText("");
        loadActiveToTable();
        jTableSach.clearSelection();
        clearDetail();
        currentSelected = null;
        setEditMode(false);
    }//GEN-LAST:event_jButtonLamMoiActionPerformed

    private void jButtonXacNhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonXacNhanActionPerformed
        if (currentSelected == null) return;
        if (!editMode) return;

        try {
            boolean ok = nxbBUS.update(
                    currentSelected.getIdNXB(),
                    jTextFieldTenNXB.getText(),
                    jTextFieldDiaChiNXB.getText(),
                    jTextFieldSDT.getText(),
                    currentSelected.isHoatDong() 
            );

            if (!ok) {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại.");
                return;
            }

            JOptionPane.showMessageDialog(this, "Cập nhật thành công.");
            int id = currentSelected.getIdNXB();
            loadActiveToTable();
            setEditMode(false);
            selectRowById(id);

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi hệ thống: " + ex.getMessage());
        }
    }//GEN-LAST:event_jButtonXacNhanActionPerformed

    private void jButtonSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSuaActionPerformed
                int row = jTableSach.getSelectedRow();
        if (row < 0 || row >= currentList.size()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một NXB để sửa.");
            return;
        }

        currentSelected = currentList.get(row);
        javax.swing.JFrame parentFrame =
                (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);

        SuaNXBDialog dialog = new SuaNXBDialog(parentFrame, true, currentSelected);
        dialog.setVisible(true);

        if (dialog.isSaved()) {
            int id = currentSelected.getIdNXB();
            loadActiveToTable();
            selectRowById(id);
        }
    }//GEN-LAST:event_jButtonSuaActionPerformed

    private void jButtonXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonXoaActionPerformed
               int row = jTableSach.getSelectedRow();
        if (row < 0 || row >= currentList.size()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một NXB để xoá.");
            return;
        }
        deleteByRow(row);
    }//GEN-LAST:event_jButtonXoaActionPerformed

    private void jTextFieldTimKiemFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldTimKiemFocusGained
                if (jTextFieldTimKiem.getText().equals("Tìm kiếm...")) { 
        jTextFieldTimKiem.setText("");
        jTextFieldTimKiem.setForeground(new java.awt.Color(0, 0, 0)); 
    }
    }//GEN-LAST:event_jTextFieldTimKiemFocusGained

    private void jTextFieldTimKiemFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldTimKiemFocusLost
                if (jTextFieldTimKiem.getText().trim().isEmpty()) {
        jTextFieldTimKiem.setForeground(new java.awt.Color(153, 153, 153)); 
        jTextFieldTimKiem.setText("Tìm kiếm..."); 
    }
    }//GEN-LAST:event_jTextFieldTimKiemFocusLost
                                              



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.Box.Filler filler5;
    private javax.swing.JButton jButtonHuy;
    private javax.swing.JButton jButtonLamMoi;
    private javax.swing.JButton jButtonSua;
    private javax.swing.JButton jButtonThem;
    private javax.swing.JButton jButtonTimKiem;
    private javax.swing.JButton jButtonXacNhan;
    private javax.swing.JButton jButtonXoa;
    private javax.swing.JButton jButtonXuat;
    private javax.swing.JLabel jLabelDiaChiNXB;
    private javax.swing.JLabel jLabelMaNXB;
    private javax.swing.JLabel jLabelSDT;
    private javax.swing.JLabel jLabelTenNXB;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanelBoard;
    private javax.swing.JPanel jPanelBottom;
    private javax.swing.JPanel jPanelButton;
    private javax.swing.JPanel jPanelCongCu;
    private javax.swing.JPanel jPanelDiaChi;
    private javax.swing.JPanel jPanelFields;
    private javax.swing.JPanel jPanelLeft;
    private javax.swing.JPanel jPanelLeftTop;
    private javax.swing.JPanel jPanelMa;
    private javax.swing.JPanel jPanelNutThem;
    private javax.swing.JPanel jPanelRight;
    private javax.swing.JPanel jPanelSDT;
    private javax.swing.JPanel jPanelTen;
    private javax.swing.JPanel jPanelTimKiem;
    private javax.swing.JPanel jPanelTop;
    private javax.swing.JPanel jPanelTrangThai;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableSach;
    private javax.swing.JTextField jTextFieldDiaChiNXB;
    private javax.swing.JTextField jTextFieldMaNXB;
    private javax.swing.JTextField jTextFieldSDT;
    private javax.swing.JTextField jTextFieldTenNXB;
    private javax.swing.JTextField jTextFieldTimKiem;
    // End of variables declaration//GEN-END:variables
}
