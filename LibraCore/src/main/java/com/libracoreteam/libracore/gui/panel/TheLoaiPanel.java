package com.libracoreteam.libracore.gui.panel;

import com.libracoreteam.libracore.bus.TheLoaiBUS;
import com.libracoreteam.libracore.gui.dialog.ThemTheLoaiDialog;
import com.libracoreteam.libracore.gui.dialog.SuaTheLoaiDialog;
import com.libracoreteam.libracore.model.TheLoai;
import org.kordamp.ikonli.swing.FontIcon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TheLoaiPanel extends javax.swing.JPanel {

    private final TheLoaiBUS bus = new TheLoaiBUS();
    private DefaultTableModel tblModel;
    private List<TheLoai> currentList = new ArrayList<>();
    private TheLoai currentSelected = null;

    public TheLoaiPanel() {
        initComponents();
        InnitButton();
        initTable();
        bindEvents();
        loadData();
    }

    private void initTable() {
        tblModel = new DefaultTableModel(new Object[]{"Mã thể loại", "Tên thể loại", "Trạng thái"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jTableSach.setModel(tblModel);
        jTableSach.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTableSach.setRowHeight(30);
        jTableSach.getTableHeader().setReorderingAllowed(false);
    }

    public void loadData() {
        try {
            currentList = bus.getAll();
            tblModel.setRowCount(0);
            for (TheLoai tl : currentList) {
                tblModel.addRow(new Object[]{
                    tl.getIdTheLoai(), 
                    tl.getTenTheLoai(), 
                    tl.isHoatDong() ? "Hoạt động" : "Ngừng"
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu thể loại!");
        }
    }

    private void bindEvents() {
        jTableSach.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && jTableSach.getSelectedRow() != -1) {
                currentSelected = currentList.get(jTableSach.getSelectedRow());
                jTextFieldMaTheLoai.setText(String.valueOf(currentSelected.getIdTheLoai()));
                jTextFieldTenTheLoai.setText(currentSelected.getTenTheLoai());
            }
        });
    }

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
        
        // Khởi tạo nút Sửa và Xóa
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
        jLabelMaTheLoai = new javax.swing.JLabel();
        jTextFieldMaTheLoai = new javax.swing.JTextField();
        jPanelTen = new javax.swing.JPanel();
        jLabelTenTheLoai = new javax.swing.JLabel();
        jTextFieldTenTheLoai = new javax.swing.JTextField();
        jPanelButton = new javax.swing.JPanel();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
        filler5 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
        filler6 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
        filler7 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
        filler8 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
        filler9 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
        filler10 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));

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

        jPanelCongCu.add(jPanelTimKiem, java.awt.BorderLayout.WEST);

        jButtonXuat.setText("Xuất");
        jButtonXuat.setPreferredSize(new java.awt.Dimension(90, 40));
        jButtonXuat.addActionListener(this::jButtonXuatActionPerformed);
        jPanelNutThem.add(jButtonXuat);

        jButtonThem.setText("Thêm");
        jButtonThem.setPreferredSize(new java.awt.Dimension(90, 40));
        jButtonThem.addActionListener(this::jButtonThemActionPerformed);
        jPanelNutThem.add(jButtonThem);

        // Đưa nút Sửa vào
        jButtonSua.setText("Sửa");
        jButtonSua.setPreferredSize(new java.awt.Dimension(90, 40));
        jButtonSua.addActionListener(this::jButtonSuaActionPerformed);
        jPanelNutThem.add(jButtonSua);

        // Đưa nút Xóa vào
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
            new Object [][] {},
            new String [] {"Title 1", "Title 2", "Title 3", "Title 4"}
        ));
        jScrollPane1.setViewportView(jTableSach);

        jPanelBoard.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanelLeft.add(jPanelBoard, java.awt.BorderLayout.CENTER);

        add(jPanelLeft, java.awt.BorderLayout.CENTER);

        jPanelRight.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 0, 50, 20));
        jPanelRight.setMinimumSize(new java.awt.Dimension(300, 306));
        jPanelRight.setPreferredSize(new java.awt.Dimension(306, 306));
        jPanelRight.setLayout(new java.awt.BorderLayout());

        jLabelTitle.setFont(new java.awt.Font("Segoe UI", 1, 20)); 
        jLabelTitle.setText("THÔNG TIN THỂ LOẠI");
        jPanelTop.add(jLabelTitle);

        jPanelRight.add(jPanelTop, java.awt.BorderLayout.PAGE_START);

        jPanelBottom.setLayout(new javax.swing.BoxLayout(jPanelBottom, javax.swing.BoxLayout.Y_AXIS));

        jPanelFields.setLayout(new javax.swing.BoxLayout(jPanelFields, javax.swing.BoxLayout.Y_AXIS));

        jPanelMa.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        jPanelMa.setLayout(new java.awt.GridLayout(0, 1));

        jLabelMaTheLoai.setText("Mã thể loại:");
        jPanelMa.add(jLabelMaTheLoai);

        jTextFieldMaTheLoai.setEditable(false);
        jTextFieldMaTheLoai.setFocusable(false);
        jTextFieldMaTheLoai.addActionListener(this::jTextFieldMaTheLoaiActionPerformed);
        jPanelMa.add(jTextFieldMaTheLoai);

        jPanelFields.add(jPanelMa);

        jPanelTen.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        jPanelTen.setLayout(new java.awt.GridLayout(0, 1));

        jLabelTenTheLoai.setText("Tên thể loại:");
        jPanelTen.add(jLabelTenTheLoai);

        jTextFieldTenTheLoai.setEditable(false);
        jTextFieldTenTheLoai.setFocusable(false);
        jPanelTen.add(jTextFieldTenTheLoai);

        jPanelFields.add(jPanelTen);

        jPanelButton.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));

        jPanelFields.add(jPanelButton);
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
    }

    private void InnitButton() {
            int iconSize = 16;
            jButtonThem.setIcon(FontIcon.of(FontAwesomeSolid.PLUS_CIRCLE, iconSize, new Color(21, 110, 71)));
            jButtonXuat.setIcon(FontIcon.of(FontAwesomeSolid.FILE_EXPORT, iconSize, new Color(100, 100, 100)));
            jButtonTimKiem.setIcon(FontIcon.of(FontAwesomeSolid.SEARCH, iconSize, new Color(100, 100, 100)));
            jButtonLamMoi.setIcon(FontIcon.of(FontAwesomeSolid.SYNC_ALT, iconSize, new Color(100, 100, 100)));
            
            // Icon cho Sửa và Xóa
            jButtonSua.setIcon(FontIcon.of(FontAwesomeSolid.EDIT, iconSize, new Color(13, 110, 253))); 
            jButtonXoa.setIcon(FontIcon.of(FontAwesomeSolid.TRASH, iconSize, new Color(220, 53, 69))); 
    }
    
    private void jButtonThemActionPerformed(java.awt.event.ActionEvent evt) {
        javax.swing.JFrame parentFrame = (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);
        ThemTheLoaiDialog dialog = new ThemTheLoaiDialog(parentFrame, true);
        dialog.setVisible(true);
        loadData(); 
    }

    // Sự kiện Nút Sửa
    private void jButtonSuaActionPerformed(java.awt.event.ActionEvent evt) {
        if (currentSelected == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một thể loại để sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        javax.swing.JFrame parentFrame = (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);
        SuaTheLoaiDialog dialog = new SuaTheLoaiDialog(parentFrame, true, currentSelected);
        dialog.setVisible(true);
        loadData(); 
    }

    // Sự kiện Nút Xóa
    private void jButtonXoaActionPerformed(java.awt.event.ActionEvent evt) {
        if (currentSelected == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một thể loại để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc muốn xoá (ngừng hoạt động) thể loại \"" + currentSelected.getTenTheLoai() + "\" không?",
                "Xác nhận xoá",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (choice == JOptionPane.YES_OPTION) {
            try {
                boolean ok = bus.softDelete(currentSelected.getIdTheLoai());
                
                if (!ok) {
                    JOptionPane.showMessageDialog(this, "Xoá thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                JOptionPane.showMessageDialog(this, "Đã xoá (ngừng hoạt động) thể loại thành công.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                
                loadData(); 
                
                jTableSach.clearSelection();
                jTextFieldMaTheLoai.setText("");
                jTextFieldTenTheLoai.setText("");
                currentSelected = null;
                
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi hệ thống: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void jTextFieldMaTheLoaiActionPerformed(java.awt.event.ActionEvent evt) {}

    private void jButtonXuatActionPerformed(java.awt.event.ActionEvent evt) {
        List<TheLoai> listToExport = (currentList != null && !currentList.isEmpty())
                ? currentList
                : bus.getAll();
        if (listToExport == null || listToExport.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu thể loại để xuất.");
            return;
        }

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Xuất danh sách thể loại ra Excel");
        chooser.setFileFilter(new FileNameExtensionFilter("Excel (*.xlsx)", "xlsx"));
        chooser.setAcceptAllFileFilterUsed(true);

        String defaultName = "TheLoai_" + System.currentTimeMillis() + ".xlsx";
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
            exportTheLoaiToExcel(file, listToExport);
            JOptionPane.showMessageDialog(this, "Xuất Excel thành công:\n" + file.getAbsolutePath());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Xuất Excel thất bại: " + ex.getMessage());
        }
    }

    private void jButtonTimKiemActionPerformed(java.awt.event.ActionEvent evt) {}

    private void jButtonLamMoiActionPerformed(java.awt.event.ActionEvent evt) {
        loadData();
    }

    private void exportTheLoaiToExcel(File file, List<TheLoai> list) throws IOException {
        try (Workbook wb = new XSSFWorkbook()) {
            Sheet sheet = wb.createSheet("TheLoai");

            int r = 0;
            Row header = sheet.createRow(r++);
            String[] headers = { "Mã thể loại", "Tên thể loại", "Trạng thái" };
            for (int c = 0; c < headers.length; c++) {
                header.createCell(c).setCellValue(headers[c]);
            }

            for (TheLoai tl : list) {
                Row row = sheet.createRow(r++);
                row.createCell(0).setCellValue(tl.getIdTheLoai());
                row.createCell(1).setCellValue(tl.getTenTheLoai() != null ? tl.getTenTheLoai() : "");
                row.createCell(2).setCellValue(tl.isHoatDong() ? "Hoạt động" : "Ngừng");
            }

            for (int c = 0; c < headers.length; c++) {
                sheet.autoSizeColumn(c);
            }

            try (FileOutputStream fos = new FileOutputStream(file)) {
                wb.write(fos);
            }
        }
    }


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
    private javax.swing.JButton jButtonThem;
    private javax.swing.JButton jButtonSua; // Khai báo nút sửa
    private javax.swing.JButton jButtonXoa; // Khai báo nút xóa
    private javax.swing.JButton jButtonTimKiem;
    private javax.swing.JButton jButtonXuat;
    private javax.swing.JLabel jLabelMaTheLoai;
    private javax.swing.JLabel jLabelTenTheLoai;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanelBoard;
    private javax.swing.JPanel jPanelBottom;
    private javax.swing.JPanel jPanelButton;
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
    private javax.swing.JTable jTableSach;
    private javax.swing.JTextField jTextFieldMaTheLoai;
    private javax.swing.JTextField jTextFieldTenTheLoai;
    private javax.swing.JTextField jTextFieldTimKiem;
}