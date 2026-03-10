
package com.libracoreteam.libracore.gui.panel;

import com.libracoreteam.libracore.bus.CuonSachBUS;
import com.libracoreteam.libracore.model.CuonSach;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class CuonSachPanel extends javax.swing.JPanel {

    private final CuonSachBUS cuonSachBUS = new CuonSachBUS();
    private DefaultTableModel tblModel;
    private List<CuonSach> currentList = new ArrayList<CuonSach>();
    private CuonSach currentSelected = null;


    public CuonSachPanel() {
        initComponents();
        jPanelFields.remove(jPanelButton);
        InnitButton();
        initTable();
        bindEvents();
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
        jPanelNut = new javax.swing.JPanel();
        jButtonXuat = new javax.swing.JButton();
        jButtonHuyCuon = new javax.swing.JButton();
        jPanelBoard = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableSach = new javax.swing.JTable();
        jPanelRight = new javax.swing.JPanel();
        jPanelTop = new javax.swing.JPanel();
        jLabelTitle = new javax.swing.JLabel();
        jPanelBottom = new javax.swing.JPanel();
        jPanelFields = new javax.swing.JPanel();
        jPanelMaSach = new javax.swing.JPanel();
        jLabelMaSach = new javax.swing.JLabel();
        jTextFieldMaSach = new javax.swing.JTextField();
        jPanelMaCuonSach = new javax.swing.JPanel();
        jLabelMaCuonSach = new javax.swing.JLabel();
        jTextFieldMaCuonSach = new javax.swing.JTextField();
        jPanelTenCuonSach = new javax.swing.JPanel();
        jLabelTenCuonSach = new javax.swing.JLabel();
        jTextFieldTenCuonSach = new javax.swing.JTextField();
        jPanelTinhTrangSach = new javax.swing.JPanel();
        jLabelTinhTrang = new javax.swing.JLabel();
        jTextFieldTinhTrang = new javax.swing.JTextField();
        jPanelTrangThaiMuon = new javax.swing.JPanel();
        jLabelTrangThaiMuon = new javax.swing.JLabel();
        jTextFieldTrangThaiMuon = new javax.swing.JTextField();
        jPanelDaHuy = new javax.swing.JPanel();
        jLabelDaHuy = new javax.swing.JLabel();
        jTextFieldDaHuy = new javax.swing.JTextField();
        jPanelButton = new javax.swing.JPanel();
        jButtonXacNhan = new javax.swing.JButton();
        jButtonHuy = new javax.swing.JButton();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));

        setLayout(new java.awt.BorderLayout());

        jPanelLeft.setLayout(new java.awt.BorderLayout());

        jPanelLeftTop.setBackground(new java.awt.Color(255, 153, 153));
        jPanelLeftTop.setLayout(new java.awt.BorderLayout());

        jPanelCongCu.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 0, 20));
        jPanelCongCu.setLayout(new java.awt.BorderLayout());

        jTextFieldTimKiem.setText("Tìm kiếm...");
        jTextFieldTimKiem.setPreferredSize(new java.awt.Dimension(150, 40));
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
        jPanelNut.add(jButtonXuat);

        jButtonHuyCuon.setText("Hủy cuốn");
        jButtonHuyCuon.setPreferredSize(new java.awt.Dimension(110, 40));
        jButtonHuyCuon.addActionListener(this::jButtonHuyCuonActionPerformed);
        jPanelNut.add(jButtonHuyCuon);

        jPanelCongCu.add(jPanelNut, java.awt.BorderLayout.EAST);

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
        jLabelTitle.setText("THÔNG TIN CUỐN SÁCH");
        jPanelTop.add(jLabelTitle);

        jPanelRight.add(jPanelTop, java.awt.BorderLayout.PAGE_START);

        jPanelBottom.setLayout(new javax.swing.BoxLayout(jPanelBottom, javax.swing.BoxLayout.Y_AXIS));

        jPanelFields.setLayout(new javax.swing.BoxLayout(jPanelFields, javax.swing.BoxLayout.Y_AXIS));

        jPanelMaSach.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        jPanelMaSach.setLayout(new java.awt.GridLayout(0, 1));

        jLabelMaSach.setText("Mã sách:");
        jPanelMaSach.add(jLabelMaSach);

        jTextFieldMaSach.setEditable(false);
        jTextFieldMaSach.setFocusable(false);
        jTextFieldMaSach.addActionListener(this::jTextFieldMaSachActionPerformed);
        jPanelMaSach.add(jTextFieldMaSach);

        jPanelFields.add(jPanelMaSach);

        jPanelMaCuonSach.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        jPanelMaCuonSach.setLayout(new java.awt.GridLayout(0, 1));

        jLabelMaCuonSach.setText("Mã cuốn sách:");
        jPanelMaCuonSach.add(jLabelMaCuonSach);

        jTextFieldMaCuonSach.setEditable(false);
        jTextFieldMaCuonSach.setFocusable(false);
        jPanelMaCuonSach.add(jTextFieldMaCuonSach);

        jPanelFields.add(jPanelMaCuonSach);

        jPanelTenCuonSach.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        jPanelTenCuonSach.setLayout(new java.awt.GridLayout(0, 1));

        jLabelTenCuonSach.setText("Tên cuốn sách:");
        jPanelTenCuonSach.add(jLabelTenCuonSach);

        jTextFieldTenCuonSach.setEditable(false);
        jTextFieldTenCuonSach.setFocusable(false);
        jTextFieldTenCuonSach.addActionListener(this::jTextFieldTenCuonSachActionPerformed);
        jPanelTenCuonSach.add(jTextFieldTenCuonSach);

        jPanelFields.add(jPanelTenCuonSach);

        jPanelTinhTrangSach.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        jPanelTinhTrangSach.setLayout(new java.awt.GridLayout(0, 1));

        jLabelTinhTrang.setText("Tình trạng sách:");
        jPanelTinhTrangSach.add(jLabelTinhTrang);

        jTextFieldTinhTrang.setEditable(false);
        jTextFieldTinhTrang.setFocusable(false);
        jTextFieldTinhTrang.addActionListener(this::jTextFieldTinhTrangActionPerformed);
        jPanelTinhTrangSach.add(jTextFieldTinhTrang);

        jPanelFields.add(jPanelTinhTrangSach);

        jPanelTrangThaiMuon.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        jPanelTrangThaiMuon.setLayout(new java.awt.GridLayout(0, 1));

        jLabelTrangThaiMuon.setText("Trạng thái mượn:");
        jPanelTrangThaiMuon.add(jLabelTrangThaiMuon);

        jTextFieldTrangThaiMuon.setEditable(false);
        jTextFieldTrangThaiMuon.setFocusable(false);
        jTextFieldTrangThaiMuon.addActionListener(this::jTextFieldTrangThaiMuonActionPerformed);
        jPanelTrangThaiMuon.add(jTextFieldTrangThaiMuon);

        jPanelFields.add(jPanelTrangThaiMuon);

        jPanelDaHuy.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        jPanelDaHuy.setLayout(new java.awt.GridLayout(0, 1));

        jLabelDaHuy.setText("Đã huỷ:");
        jPanelDaHuy.add(jLabelDaHuy);

        jTextFieldDaHuy.setEditable(false);
        jTextFieldDaHuy.setFocusable(false);
        jTextFieldDaHuy.addActionListener(this::jTextFieldDaHuyActionPerformed);
        jPanelDaHuy.add(jTextFieldDaHuy);

        jPanelFields.add(jPanelDaHuy);

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

        jPanelBottom.add(jPanelFields);

        jPanelRight.add(jPanelBottom, java.awt.BorderLayout.CENTER);

        add(jPanelRight, java.awt.BorderLayout.EAST);
    }// </editor-fold>//GEN-END:initComponents
    
    private void InnitButton() {
            int iconSize = 16;

            jButtonXuat.setIcon(FontIcon.of(FontAwesomeSolid.FILE_EXPORT, iconSize, new Color(100, 100, 100)));
            jButtonTimKiem.setIcon(FontIcon.of(FontAwesomeSolid.SEARCH, iconSize, new Color(100, 100, 100)));
            jButtonLamMoi.setIcon(FontIcon.of(FontAwesomeSolid.SYNC_ALT, iconSize, new Color(100, 100, 100)));
            jTextFieldTimKiem.putClientProperty("JTextField.placeholderText", "Tìm theo mã cuốn sách / mã sách / tên sách");

            jButtonHuyCuon.setIcon(FontIcon.of(FontAwesomeSolid.TRASH, iconSize, new Color(220, 53, 69)));

    }

    private void initTable() {
        tblModel = new DefaultTableModel(
                new Object[]{"Mã cuốn sách", "Mã sách", "Tên sách", "Tình trạng", "Trạng thái mượn", "Đã huỷ"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        jTableSach.setModel(tblModel);
        jTableSach.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTableSach.getTableHeader().setReorderingAllowed(false);
        jTableSach.setRowHeight(34);
    }

    private void bindEvents() {
        jTableSach.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) {
                return;
            }
            int row = jTableSach.getSelectedRow();
            if (row < 0 || row >= currentList.size()) {
                return;
            }
            currentSelected = currentList.get(row);
            fillDetail(currentSelected);
        });
    }

    public void loadData() {
        try {
            currentList = cuonSachBUS.getAll();
            renderTable(currentList);
            jTableSach.clearSelection();
            clearDetail();
            currentSelected = null;
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, "Không tải được danh sách cuốn sách: " + ex.getMessage());
        }
    }

    private void renderTable(List<CuonSach> list) {
        tblModel.setRowCount(0);
        for (CuonSach c : list) {
            String tenSach = "";
            if (c.getSach() != null && c.getSach().getTenSach() != null) {
                tenSach = c.getSach().getTenSach();
            }
            tblModel.addRow(new Object[]{
                    valueOrEmpty(c.getMaCuonSach()),
                    c.getIdSach(),
                    tenSach,
                    valueOrEmpty(c.getTinhTrangSach()),
                    valueOrEmpty(c.getTrangThaiMuon()),
                    c.isDaHuy() ? "Có" : "Không"
            });
        }
    }

    private void fillDetail(CuonSach c) {
        if (c == null) {
            return;
        }
        jTextFieldMaSach.setText(String.valueOf(c.getIdSach()));
        jTextFieldMaCuonSach.setText(valueOrEmpty(c.getMaCuonSach()));
        if (c.getSach() != null && c.getSach().getTenSach() != null) {
            jTextFieldTenCuonSach.setText(c.getSach().getTenSach());
        } else {
            jTextFieldTenCuonSach.setText("");
        }
        jTextFieldTinhTrang.setText(valueOrEmpty(c.getTinhTrangSach()));
        jTextFieldTrangThaiMuon.setText(valueOrEmpty(c.getTrangThaiMuon()));
        jTextFieldDaHuy.setText(c.isDaHuy() ? "Có" : "Không");
    }

    private void clearDetail() {
        jTextFieldMaSach.setText("");
        jTextFieldMaCuonSach.setText("");
        jTextFieldTenCuonSach.setText("");
        jTextFieldTinhTrang.setText("");
        jTextFieldTrangThaiMuon.setText("");
        jTextFieldDaHuy.setText("");
    }

    private void cancelByRow(int row) {
        if (row < 0 || row >= currentList.size()) {
            return;
        }
        CuonSach c = currentList.get(row);
        doCancel(c);
    }

    private void doCancel(CuonSach c) {
        if (c == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một cuốn sách.");
            return;
        }

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc muốn huỷ cuốn sách " + valueOrEmpty(c.getMaCuonSach()) + " không?",
                "Xác nhận huỷ",
                JOptionPane.YES_NO_OPTION
        );
        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            cuonSachBUS.softDelete(c.getIdCuonSach(), c.getTrangThaiMuon(), c.isDaHuy());
            JOptionPane.showMessageDialog(this, "Đã huỷ cuốn sách.");
            loadData();
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private String valueOrEmpty(String s) {
        return s == null ? "" : s;
    }

    private void jTextFieldMaSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldMaSachActionPerformed
    }//GEN-LAST:event_jTextFieldMaSachActionPerformed

    private void jTextFieldTenCuonSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldTenCuonSachActionPerformed
    }//GEN-LAST:event_jTextFieldTenCuonSachActionPerformed

    private void jTextFieldTrangThaiMuonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldTrangThaiMuonActionPerformed
    }//GEN-LAST:event_jTextFieldTrangThaiMuonActionPerformed

    private void jTextFieldTinhTrangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldTinhTrangActionPerformed
    }//GEN-LAST:event_jTextFieldTinhTrangActionPerformed

    private void jTextFieldDaHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldDaHuyActionPerformed
    }//GEN-LAST:event_jTextFieldDaHuyActionPerformed

    private void jButtonXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonXuatActionPerformed
        List<CuonSach> listToExport = (currentList != null && !currentList.isEmpty())
                ? currentList
                : cuonSachBUS.getAll();

        if (listToExport == null || listToExport.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu cuốn sách để xuất.");
            return;
        }

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Xuất danh sách cuốn sách ra Excel");
        chooser.setFileFilter(new FileNameExtensionFilter("Excel (*.xlsx)", "xlsx"));
        chooser.setAcceptAllFileFilterUsed(true);

        String defaultName = "CuonSach_" + System.currentTimeMillis() + ".xlsx";
        chooser.setSelectedFile(new File(defaultName));

        int result = chooser.showSaveDialog(this);
        if (result != JFileChooser.APPROVE_OPTION)
            return;

        File file = chooser.getSelectedFile();
        if (file == null)
            return;
        String path = file.getAbsolutePath();
        if (!path.toLowerCase().endsWith(".xlsx")) {
            file = new File(path + ".xlsx");
        }

        try {
            exportCuonSachToExcel(file, listToExport);
            JOptionPane.showMessageDialog(this, "Xuất Excel thành công:\n" + file.getAbsolutePath());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Xuất Excel thất bại: " + ex.getMessage());
        }
    }//GEN-LAST:event_jButtonXuatActionPerformed

    private void jButtonTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTimKiemActionPerformed
        String keyword = jTextFieldTimKiem.getText();
        if (keyword != null) {
            keyword = keyword.trim();
        }

        if (keyword == null || keyword.isEmpty() || "Tìm kiếm...".equalsIgnoreCase(keyword)) {
            loadData();
            return;
        }

        try {
            currentList = cuonSachBUS.search(keyword);
            renderTable(currentList);
            jTableSach.clearSelection();
            clearDetail();
            currentSelected = null;
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, "Tìm kiếm thất bại: " + ex.getMessage());
        }
    }//GEN-LAST:event_jButtonTimKiemActionPerformed

    private void jButtonLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLamMoiActionPerformed
        jTextFieldTimKiem.setText("");
        loadData();
    }//GEN-LAST:event_jButtonLamMoiActionPerformed

    private void jButtonXacNhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonXacNhanActionPerformed
    }//GEN-LAST:event_jButtonXacNhanActionPerformed
    

    private void jButtonHuyCuonActionPerformed(java.awt.event.ActionEvent evt) {
        if (currentSelected == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một cuốn sách để huỷ.");
            return;
        }
        doCancel(currentSelected);
    }

    private void exportCuonSachToExcel(File file, List<CuonSach> list) throws IOException {
        try (Workbook wb = new XSSFWorkbook()) {
            Sheet sheet = wb.createSheet("CuonSach");

            int r = 0;
            Row header = sheet.createRow(r++);
            String[] headers = {
                    "Mã cuốn sách",
                    "Mã sách",
                    "Tên sách",
                    "Tình trạng",
                    "Trạng thái mượn",
                    "Đã huỷ"
            };
            for (int c = 0; c < headers.length; c++) {
                header.createCell(c).setCellValue(headers[c]);
            }

            for (CuonSach c : list) {
                Row row = sheet.createRow(r++);
                row.createCell(0).setCellValue(valueOrEmpty(c.getMaCuonSach()));
                row.createCell(1).setCellValue(c.getIdSach());
                String tenSach = "";
                if (c.getSach() != null && c.getSach().getTenSach() != null) {
                    tenSach = c.getSach().getTenSach();
                }
                row.createCell(2).setCellValue(tenSach);
                row.createCell(3).setCellValue(valueOrEmpty(c.getTinhTrangSach()));
                row.createCell(4).setCellValue(valueOrEmpty(c.getTrangThaiMuon()));
                row.createCell(5).setCellValue(c.isDaHuy() ? "Có" : "Không");
            }

            for (int c = 0; c < headers.length; c++) {
                sheet.autoSizeColumn(c);
            }

            try (FileOutputStream fos = new FileOutputStream(file)) {
                wb.write(fos);
            }
        }
    }


    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.JButton jButtonHuy;
    private javax.swing.JButton jButtonHuyCuon;
    private javax.swing.JButton jButtonLamMoi;
    private javax.swing.JButton jButtonTimKiem;
    private javax.swing.JButton jButtonXacNhan;
    private javax.swing.JButton jButtonXuat;
    private javax.swing.JLabel jLabelDaHuy;
    private javax.swing.JLabel jLabelMaCuonSach;
    private javax.swing.JLabel jLabelMaSach;
    private javax.swing.JLabel jLabelTenCuonSach;
    private javax.swing.JLabel jLabelTinhTrang;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JLabel jLabelTrangThaiMuon;
    private javax.swing.JPanel jPanelBoard;
    private javax.swing.JPanel jPanelBottom;
    private javax.swing.JPanel jPanelButton;
    private javax.swing.JPanel jPanelCongCu;
    private javax.swing.JPanel jPanelDaHuy;
    private javax.swing.JPanel jPanelFields;
    private javax.swing.JPanel jPanelLeft;
    private javax.swing.JPanel jPanelLeftTop;
    private javax.swing.JPanel jPanelMaCuonSach;
    private javax.swing.JPanel jPanelMaSach;
    private javax.swing.JPanel jPanelNut;
    private javax.swing.JPanel jPanelRight;
    private javax.swing.JPanel jPanelTenCuonSach;
    private javax.swing.JPanel jPanelTimKiem;
    private javax.swing.JPanel jPanelTinhTrangSach;
    private javax.swing.JPanel jPanelTop;
    private javax.swing.JPanel jPanelTrangThaiMuon;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableSach;
    private javax.swing.JTextField jTextFieldDaHuy;
    private javax.swing.JTextField jTextFieldMaCuonSach;
    private javax.swing.JTextField jTextFieldMaSach;
    private javax.swing.JTextField jTextFieldTenCuonSach;
    private javax.swing.JTextField jTextFieldTimKiem;
    private javax.swing.JTextField jTextFieldTinhTrang;
    private javax.swing.JTextField jTextFieldTrangThaiMuon;
}
