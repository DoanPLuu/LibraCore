
package com.libracoreteam.libracore.gui.panel;

import com.libracoreteam.libracore.bus.PhieuNhapBUS;
import com.libracoreteam.libracore.gui.dialog.ChiTietPhieuNhapDialog;
import com.libracoreteam.libracore.gui.dialog.TaoPhieuNhapDialog;
import com.libracoreteam.libracore.model.ChiTietPhieuNhap;
import com.libracoreteam.libracore.model.PhieuNhap;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;


public class NhapSachPanel extends javax.swing.JPanel {

    private final PhieuNhapBUS phieuNhapBUS = new PhieuNhapBUS();
    private DefaultTableModel tblModel;
    private List<PhieuNhap> currentList = new ArrayList<PhieuNhap>();
    private boolean showingCancelled = false;

    public NhapSachPanel() {
        initComponents();
        InnitButton();
        initTable();
        loadData();
    }
    
    

  
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
        jButtonDSHuy = new javax.swing.JButton();
        jPanelNutThem = new javax.swing.JPanel();
        jButtonXuat = new javax.swing.JButton();
        jButtonThem = new javax.swing.JButton();
        jButtonChiTiet = new javax.swing.JButton();
        jButtonHuyPhieu = new javax.swing.JButton();
        jPanelBoard = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableSach = new javax.swing.JTable();

        jMenuItem1.setText("jMenuItem1");

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

        jButtonDSHuy.setText("Danh sách huỷ");
        jButtonDSHuy.setPreferredSize(new java.awt.Dimension(140, 40));
        jButtonDSHuy.addActionListener(this::jButtonDSHuyActionPerformed);
        jPanelTimKiem.add(jButtonDSHuy);

        jPanelCongCu.add(jPanelTimKiem, java.awt.BorderLayout.WEST);

        jButtonXuat.setText("Xuất");
        jButtonXuat.setPreferredSize(new java.awt.Dimension(90, 40));
        jButtonXuat.addActionListener(this::jButtonXuatActionPerformed);
        jPanelNutThem.add(jButtonXuat);

        jButtonThem.setText("Thêm");
        jButtonThem.setPreferredSize(new java.awt.Dimension(90, 40));
        jButtonThem.addActionListener(this::jButtonThemActionPerformed);
        jPanelNutThem.add(jButtonThem);

        jButtonChiTiet.setText("Chi tiết");
        jButtonChiTiet.setPreferredSize(new java.awt.Dimension(100, 40));
        jButtonChiTiet.addActionListener(this::jButtonChiTietActionPerformed);
        jPanelNutThem.add(jButtonChiTiet);

        jButtonHuyPhieu.setText("Hủy phiếu");
        jButtonHuyPhieu.setPreferredSize(new java.awt.Dimension(110, 40));
        jButtonHuyPhieu.addActionListener(this::jButtonHuyPhieuActionPerformed);
        jPanelNutThem.add(jButtonHuyPhieu);

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
    }// </editor-fold>//GEN-END:initComponents

    private void InnitButton() {
            int iconSize = 16;

            jButtonThem.setIcon(FontIcon.of(FontAwesomeSolid.PLUS_CIRCLE, iconSize, new Color(21, 110, 71)));
            jButtonXuat.setIcon(FontIcon.of(FontAwesomeSolid.FILE_EXPORT, iconSize, new Color(100, 100, 100)));
            jButtonTimKiem.setIcon(FontIcon.of(FontAwesomeSolid.SEARCH, iconSize, new Color(100, 100, 100)));
            jButtonLamMoi.setIcon(FontIcon.of(FontAwesomeSolid.SYNC_ALT, iconSize, new Color(100, 100, 100)));
            jButtonDSHuy.setIcon(FontIcon.of(FontAwesomeSolid.ALIGN_JUSTIFY, iconSize, new Color(100, 100, 100)));
            jTextFieldTimKiem.putClientProperty("JTextField.placeholderText", "Tìm theo mã phiếu hoặc nhà cung cấp");
            jButtonChiTiet.setIcon(FontIcon.of(FontAwesomeSolid.INFO_CIRCLE, iconSize, new Color(13, 110, 253)));
            jButtonHuyPhieu.setIcon(FontIcon.of(FontAwesomeSolid.TRASH, iconSize, new Color(220, 53, 69)));
    }

    private void initTable() {
        tblModel = new DefaultTableModel(
                new Object[]{"Mã phiếu", "Ngày nhập", "Nhà cung cấp", "Số lượng sách", "Trạng thái"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        jTableSach.setModel(tblModel);
        jTableSach.getTableHeader().setReorderingAllowed(false);
        jTableSach.setRowHeight(34);
    }

    public void loadData() {
        try {
            if (showingCancelled) {
                currentList = phieuNhapBUS.getDaHuy();
            } else {
                currentList = phieuNhapBUS.getActive();
            }
            renderTable(currentList);
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, "Không tải được danh sách phiếu nhập: " + ex.getMessage());
        }
    }

    private void renderTable(List<PhieuNhap> list) {
        tblModel.setRowCount(0);
        for (PhieuNhap p : list) {
            String nccText = "";
            if (p.getNcc() != null && p.getNcc().getTenNCC() != null) {
                nccText = p.getNcc().getTenNCC();
            }

            tblModel.addRow(new Object[]{
                    p.getIdPhieuNhap(),
                    p.getNgayNhap() == null ? "" : p.getNgayNhap(),
                    nccText,
                    p.getSoLuongSach() == null ? 0 : p.getSoLuongSach(),
                    p.getTrangThai()
            });
        }
    }

    private void showDetailByRow(int row) {
        if (row < 0 || row >= currentList.size()) {
            return;
        }
        PhieuNhap phieuNhap = currentList.get(row);
        try {
            List<ChiTietPhieuNhap> details = phieuNhapBUS.getDetailsByPhieuNhap(phieuNhap.getIdPhieuNhap());
            javax.swing.JFrame parentFrame =
                    (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);
            ChiTietPhieuNhapDialog dialog = new ChiTietPhieuNhapDialog(parentFrame, true, phieuNhap, details);
            dialog.setVisible(true);
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, "Không tải được chi tiết phiếu nhập: " + ex.getMessage());
        }
    }

    private void cancelByRow(int row) {
        if (row < 0 || row >= currentList.size()) {
            return;
        }
        PhieuNhap phieuNhap = currentList.get(row);
        if (phieuNhap.isDaHuy()) {
            JOptionPane.showMessageDialog(this, "Phiếu nhập này đã ở trạng thái huỷ.");
            return;
        }

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc muốn huỷ phiếu nhập #" + phieuNhap.getIdPhieuNhap() + " không?",
                "Xác nhận huỷ",
                JOptionPane.YES_NO_OPTION
        );
        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            phieuNhapBUS.cancel(phieuNhap.getIdPhieuNhap());
            JOptionPane.showMessageDialog(this, "Đã huỷ phiếu nhập.");
            loadData();
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, "Huỷ phiếu nhập thất bại: " + ex.getMessage());
        }
    }
    
    private void jButtonThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonThemActionPerformed
        javax.swing.JFrame parentFrame =
                (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);

        TaoPhieuNhapDialog dialog = new TaoPhieuNhapDialog(parentFrame, true);

        dialog.setVisible(true);

        if (dialog.isSaved()) {
            showingCancelled = false;
            loadData();
        }
    }//GEN-LAST:event_jButtonThemActionPerformed

    private void jButtonXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonXuatActionPerformed
        List<PhieuNhap> listToExport;
        if (currentList != null && !currentList.isEmpty()) {
            listToExport = currentList;
        } else {
            try {
                listToExport = showingCancelled
                        ? phieuNhapBUS.getDaHuy()
                        : phieuNhapBUS.getActive();
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(this, "Không tải được danh sách phiếu nhập để xuất: " + ex.getMessage());
                return;
            }
        }
        if (listToExport == null || listToExport.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu phiếu nhập để xuất.");
            return;
        }
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Xuất danh sách phiếu nhập ra Excel");
        chooser.setFileFilter(new FileNameExtensionFilter("Excel (*.xlsx)", "xlsx"));
        chooser.setAcceptAllFileFilterUsed(true);
        String defaultName = "PhieuNhap_" + System.currentTimeMillis() + ".xlsx";
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
            exportPhieuNhapToExcel(file, listToExport);
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
            currentList = phieuNhapBUS.search(keyword, showingCancelled);
            renderTable(currentList);
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, "Tìm kiếm thất bại: " + ex.getMessage());
        }
    }//GEN-LAST:event_jButtonTimKiemActionPerformed

    private void jButtonLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLamMoiActionPerformed
        jTextFieldTimKiem.setText("");
        showingCancelled = false;
        loadData();
    }//GEN-LAST:event_jButtonLamMoiActionPerformed

    private void jButtonDSHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDSHuyActionPerformed
        showingCancelled = true;
        loadData();
    }//GEN-LAST:event_jButtonDSHuyActionPerformed
    
    private void jButtonChiTietActionPerformed(java.awt.event.ActionEvent evt) {
        int row = jTableSach.getSelectedRow();
        if (row < 0 || row >= currentList.size()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu nhập để xem chi tiết.");
            return;
        }
        showDetailByRow(row);
    }

    private void jButtonHuyPhieuActionPerformed(java.awt.event.ActionEvent evt) {
        int row = jTableSach.getSelectedRow();
        if (row < 0 || row >= currentList.size()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu nhập để huỷ.");
            return;
        }
        cancelByRow(row);
    }
    
    private void exportPhieuNhapToExcel(File file, List<PhieuNhap> list) throws IOException {
        try (Workbook wb = new XSSFWorkbook()) {
            Sheet sheet = wb.createSheet("PhieuNhap");

            int r = 0;
            Row header = sheet.createRow(r++);
            String[] headers = {
                    "Mã phiếu",
                    "Ngày nhập",
                    "Nhà cung cấp",
                    "Số lượng sách",
                    "Trạng thái"
            };
            for (int c = 0; c < headers.length; c++) {
                header.createCell(c).setCellValue(headers[c]);
            }

            for (PhieuNhap p : list) {
                Row row = sheet.createRow(r++);

                row.createCell(0).setCellValue(p.getIdPhieuNhap());

                row.createCell(1).setCellValue(
                        p.getNgayNhap() != null ? p.getNgayNhap().toString() : ""
                );

                String nccText = "";
                if (p.getNcc() != null && p.getNcc().getTenNCC() != null) {
                    nccText = p.getNcc().getTenNCC();
                }
                row.createCell(2).setCellValue(nccText);

                row.createCell(3).setCellValue(
                        p.getSoLuongSach() != null ? p.getSoLuongSach() : 0
                );

                row.createCell(4).setCellValue(
                        p.getTrangThai() != null ? p.getTrangThai() : ""
                );
            }

            for (int c = 0; c < headers.length; c++) {
                sheet.autoSizeColumn(c);
            }

            try (FileOutputStream fos = new FileOutputStream(file)) {
                wb.write(fos);
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonChiTiet;
    private javax.swing.JButton jButtonDSHuy;
    private javax.swing.JButton jButtonHuyPhieu;
    private javax.swing.JButton jButtonLamMoi;
    private javax.swing.JButton jButtonThem;
    private javax.swing.JButton jButtonTimKiem;
    private javax.swing.JButton jButtonXuat;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanelBoard;
    private javax.swing.JPanel jPanelCongCu;
    private javax.swing.JPanel jPanelLeft;
    private javax.swing.JPanel jPanelLeftTop;
    private javax.swing.JPanel jPanelNutThem;
    private javax.swing.JPanel jPanelTimKiem;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableSach;
    private javax.swing.JTextField jTextFieldTimKiem;
    // End of variables declaration//GEN-END:variables
}
