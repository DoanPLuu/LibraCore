package com.libracoreteam.libracore.gui.panel;

import com.libracoreteam.libracore.bus.DocGiaBUS;
import com.libracoreteam.libracore.model.DocGia;
import com.libracoreteam.libracore.gui.dialog.ThanhVienDialog;
import org.kordamp.ikonli.swing.FontIcon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ThanhVienPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private DocGiaBUS docGiaBUS;
    private List<DocGia> listDocGia;
    private List<DocGia> currentList; // List dùng để hiển thị (hỗ trợ tìm kiếm)
    private JTextField txtSearch;

    public ThanhVienPanel() {
        docGiaBUS = new DocGiaBUS();
        initComponents();
        loadData();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);

        // ==========================================
        // TOP-BAR: CHUẨN HÓA GIAO DIỆN TÌM KIẾM & CÔNG CỤ
        // ==========================================
        JPanel pnlTop = new JPanel(new BorderLayout());
        pnlTop.setBackground(new Color(255, 153, 153)); // Nền đỏ nhạt đồng bộ hệ thống

        JPanel pnlCongCu = new JPanel(new BorderLayout());
        pnlCongCu.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // --- CỤM BÊN TRÁI: Tìm kiếm & Làm mới ---
        JPanel pnlTimKiem = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        
        txtSearch = new JTextField();
        txtSearch.setPreferredSize(new Dimension(200, 40));
        txtSearch.putClientProperty("JTextField.placeholderText", "Tìm kiếm độc giả...");
        // Bắt sự kiện gõ phím để tìm kiếm Live Search
        txtSearch.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { actionTimKiem(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { actionTimKiem(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { actionTimKiem(); }
        });

        JButton btnSearch = new JButton();
        btnSearch.setPreferredSize(new Dimension(40, 40));
        btnSearch.setIcon(FontIcon.of(FontAwesomeSolid.SEARCH, 16, new Color(100, 100, 100)));
        btnSearch.addActionListener(e -> actionTimKiem());

        JButton btnRefresh = new JButton();
        btnRefresh.setPreferredSize(new Dimension(40, 40));
        btnRefresh.setIcon(FontIcon.of(FontAwesomeSolid.SYNC_ALT, 16, new Color(100, 100, 100)));
        btnRefresh.addActionListener(e -> {
            txtSearch.setText("");
            loadData();
        });

        pnlTimKiem.add(txtSearch);
        pnlTimKiem.add(btnSearch);
        pnlTimKiem.add(btnRefresh);

        // --- CỤM BÊN PHẢI: Xuất, Thêm, Sửa, Xóa ---
        JPanel pnlNutBam = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        
        Dimension btnSize = new Dimension(90, 40);
        int iconSize = 16;

        JButton btnXuat = new JButton("Xuất");
        btnXuat.setPreferredSize(btnSize);
        btnXuat.setIcon(FontIcon.of(FontAwesomeSolid.FILE_EXPORT, iconSize, new Color(100, 100, 100)));
        btnXuat.addActionListener(e -> actionXuatExcel());

        JButton btnThem = new JButton("Thêm");
        btnThem.setPreferredSize(btnSize);
        btnThem.setIcon(FontIcon.of(FontAwesomeSolid.PLUS_CIRCLE, iconSize, new Color(21, 110, 71)));
        btnThem.addActionListener(e -> openDialog(null));

        JButton btnSua = new JButton("Sửa");
        btnSua.setPreferredSize(btnSize);
        btnSua.setIcon(FontIcon.of(FontAwesomeSolid.EDIT, iconSize, new Color(13, 110, 253)));
        btnSua.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) openDialog(currentList.get(row));
            else JOptionPane.showMessageDialog(this, "Vui lòng chọn độc giả cần sửa!");
        });

        JButton btnXoa = new JButton("Xóa");
        btnXoa.setPreferredSize(btnSize);
        btnXoa.setIcon(FontIcon.of(FontAwesomeSolid.TRASH, iconSize, new Color(220, 53, 69)));
        btnXoa.addActionListener(e -> deleteDocGia());

        pnlNutBam.add(btnXuat);
        pnlNutBam.add(btnThem);
        pnlNutBam.add(btnSua);
        pnlNutBam.add(btnXoa);

        pnlCongCu.add(pnlTimKiem, BorderLayout.WEST);
        pnlCongCu.add(pnlNutBam, BorderLayout.EAST);
        
        pnlTop.add(pnlCongCu, BorderLayout.CENTER);
        add(pnlTop, BorderLayout.NORTH);

        // ==========================================
        // KHU VỰC BẢNG (TABLE)
        // ==========================================
        JPanel pnlBoard = new JPanel(new BorderLayout());
        pnlBoard.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        pnlBoard.setBackground(Color.WHITE);

        // Tui sửa lại mảng Cột để khớp với số lượng dữ liệu sếp đổ vào (6 cột)
        String[] cols = {"Mã ĐG", "Họ Tên", "Ngày Sinh", "SĐT", "Email", "Địa Chỉ"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(tableModel);
        table.setRowHeight(34);
        table.getTableHeader().setReorderingAllowed(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setSelectionBackground(new Color(220, 220, 220));

        pnlBoard.add(new JScrollPane(table), BorderLayout.CENTER);
        add(pnlBoard, BorderLayout.CENTER);
    }

    // ==========================================
    // LOGIC XỬ LÝ
    // ==========================================
    public void loadData() {
        listDocGia = docGiaBUS.getAllDocGia();
        currentList = new ArrayList<>(listDocGia);
        renderTable(currentList);
    }

    private void renderTable(List<DocGia> list) {
        tableModel.setRowCount(0);
        for (DocGia dg : list) {
            tableModel.addRow(new Object[]{
                    dg.getIdDocGia(), 
                    dg.getTenDocGia(), 
                    dg.getNgaySinh(),
                    dg.getSdt(), 
                    dg.getEmail(), 
                    dg.getDiaChi()
            });
        }
    }

    private void actionTimKiem() {
        String keyword = txtSearch.getText().toLowerCase().trim();
        if (keyword.isEmpty()) {
            currentList = new ArrayList<>(listDocGia);
        } else {
            // Lọc trực tiếp trên danh sách đã tải về cho mượt (Live Search)
            currentList = listDocGia.stream()
                .filter(dg -> String.valueOf(dg.getIdDocGia()).contains(keyword) || 
                              (dg.getTenDocGia() != null && dg.getTenDocGia().toLowerCase().contains(keyword)) ||
                              (dg.getSdt() != null && dg.getSdt().contains(keyword)))
                .collect(Collectors.toList());
        }
        renderTable(currentList);
    }

    private void openDialog(DocGia dg) {
        ThanhVienDialog dialog = new ThanhVienDialog((JFrame) SwingUtilities.getWindowAncestor(this), dg);
        dialog.setVisible(true);
        if (dialog.isSuccess()) { 
            loadData();
        }
    }

    private void deleteDocGia() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần xóa!");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Xóa độc giả này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String msg = docGiaBUS.delete(currentList.get(row).getIdDocGia());
            JOptionPane.showMessageDialog(this, msg);
            loadData();
        }
    }

    private void actionXuatExcel() {
        if (currentList == null || currentList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Danh sách trống, không có gì để xuất.");
            return;
        }

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Xuất danh sách Độc Giả ra Excel");
        chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Excel (*.xlsx)", "xlsx"));
        chooser.setAcceptAllFileFilterUsed(true);

        String defaultName = "DocGia_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".xlsx";
        chooser.setSelectedFile(new File(defaultName));

        int result = chooser.showSaveDialog(this);
        if (result != JFileChooser.APPROVE_OPTION) return;

        File file = chooser.getSelectedFile();
        if (file == null) return;

        String path = file.getAbsolutePath();
        if (!path.toLowerCase().endsWith(".xlsx")) {
            file = new File(path + ".xlsx");
        }

        try (org.apache.poi.ss.usermodel.Workbook wb = new org.apache.poi.xssf.usermodel.XSSFWorkbook()) {
            org.apache.poi.ss.usermodel.Sheet sheet = wb.createSheet("DocGia");
            
            org.apache.poi.ss.usermodel.CellStyle headerStyle = wb.createCellStyle();
            org.apache.poi.ss.usermodel.Font headerFont = wb.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            org.apache.poi.ss.usermodel.Row header = sheet.createRow(0);
            String[] headers = {"Mã ĐG", "Họ Tên", "Ngày Sinh", "SĐT", "Email", "Địa Chỉ"};
            for (int c = 0; c < headers.length; c++) {
                org.apache.poi.ss.usermodel.Cell cell = header.createCell(c);
                cell.setCellValue(headers[c]);
                cell.setCellStyle(headerStyle);
            }

            int r = 1;
            for (DocGia dg : currentList) {
                org.apache.poi.ss.usermodel.Row row = sheet.createRow(r++);
                row.createCell(0).setCellValue(dg.getIdDocGia());
                row.createCell(1).setCellValue(dg.getTenDocGia() != null ? dg.getTenDocGia() : "");
                row.createCell(2).setCellValue(dg.getNgaySinh() != null ? dg.getNgaySinh().toString() : "");
                row.createCell(3).setCellValue(dg.getSdt() != null ? dg.getSdt() : "");
                row.createCell(4).setCellValue(dg.getEmail() != null ? dg.getEmail() : "");
                row.createCell(5).setCellValue(dg.getDiaChi() != null ? dg.getDiaChi() : "");
            }

            for (int c = 0; c < headers.length; c++) {
                sheet.autoSizeColumn(c);
            }

            try (FileOutputStream fos = new FileOutputStream(file)) {
                wb.write(fos);
            }
            JOptionPane.showMessageDialog(this, "Xuất Excel thành công:\n" + file.getAbsolutePath());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Xuất Excel thất bại: " + ex.getMessage());
        }
    }
}