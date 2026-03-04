package com.libracoreteam.libracore.gui.panel;

import com.libracoreteam.libracore.bus.TacGiaBUS;
import com.libracoreteam.libracore.gui.dialog.ThemTacGiaDialog;
import com.libracoreteam.libracore.gui.dialog.SuaTacGiaDialog;
import com.libracoreteam.libracore.model.TacGia;
import org.kordamp.ikonli.swing.FontIcon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TacGiaPanel extends javax.swing.JPanel {

    private final TacGiaBUS bus = new TacGiaBUS();
    private DefaultTableModel tblModel;
    private List<TacGia> currentList = new ArrayList<>();
    private TacGia currentSelected = null;

    public TacGiaPanel() {
        initComponents();
        InnitButton();
        initTable();
        bindEvents();
        loadData();
    }

    private void initTable() {
        tblModel = new DefaultTableModel(new Object[]{"Mã TG", "Tên tác giả", "Ngày sinh", "Nơi sinh", "SĐT"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jTableSach.setModel(tblModel);
        jTableSach.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTableSach.setRowHeight(30);
    }

    public void loadData() {
        try {
            currentList = bus.getAll();
            tblModel.setRowCount(0);
            for (TacGia tg : currentList) {
                tblModel.addRow(new Object[]{
                    tg.getIdTacGia(), 
                    tg.getTenTacGia(), 
                    tg.getNgaySinh() != null ? tg.getNgaySinh() : "", 
                    tg.getNoiSinh() != null ? tg.getNoiSinh() : "", 
                    tg.getSdt() != null ? tg.getSdt() : "",
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu tác giả!");
        }
    }
    
    // Động cơ Live Search
    private void thucHienTimKiem() {
        String keyword = jTextFieldTimKiem.getText();
        if (keyword != null) keyword = keyword.trim();

        // Nếu ô tìm kiếm rỗng hoặc là chữ mặc định
        if (keyword == null || keyword.isEmpty() || "Tìm kiếm...".equalsIgnoreCase(keyword)) {
            loadData();
            return;
        }

        try {
            // Gọi BUS để tìm kiếm
            currentList = bus.search(keyword);
            
            // Xóa dữ liệu cũ và đổ dữ liệu mới lên bảng
            tblModel.setRowCount(0);
            for (TacGia tg : currentList) {
                tblModel.addRow(new Object[]{
                    tg.getIdTacGia(), 
                    tg.getTenTacGia(), 
                    tg.getNgaySinh() != null ? tg.getNgaySinh() : "", 
                    tg.getNoiSinh() != null ? tg.getNoiSinh() : "", 
                    tg.getSdt() != null ? tg.getSdt() : "",
                });
            }
            
            // Làm sạch các ô điền thông tin bên phải sau khi tìm
            jTableSach.clearSelection();
            jTextFieldMaTacGia.setText("");
            jTextFieldTenTacGia.setText("");
            jTextFieldNgaySinhTacGia.setText("");
            jTextFieldNoiSinhTacGia.setText("");
            jTextFieldSDTTacGia.setText("");
            currentSelected = null;
            
        } catch (Exception ex) {
            System.out.println("Lỗi tìm kiếm: " + ex.getMessage());
        }
    }

    private void bindEvents() {
        // Sự kiện click bảng
        jTableSach.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && jTableSach.getSelectedRow() != -1) {
                currentSelected = currentList.get(jTableSach.getSelectedRow());
                jTextFieldMaTacGia.setText(String.valueOf(currentSelected.getIdTacGia()));
                jTextFieldTenTacGia.setText(currentSelected.getTenTacGia());
                jTextFieldNgaySinhTacGia.setText(currentSelected.getNgaySinh() != null ? currentSelected.getNgaySinh().toString() : "");
                jTextFieldNoiSinhTacGia.setText(currentSelected.getNoiSinh() != null ? currentSelected.getNoiSinh() : "");
                jTextFieldSDTTacGia.setText(currentSelected.getSdt() != null ? currentSelected.getSdt() : "");
            }
        });
        
        // Cảm biến Live Search
        jTextFieldTimKiem.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { thucHienTimKiem(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { thucHienTimKiem(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { thucHienTimKiem(); }
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
        jButtonSua = new javax.swing.JButton(); // Khai báo nút Sửa
        jButtonXoa = new javax.swing.JButton(); // Khai báo nút Xóa
        jPanelBoard = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableSach = new javax.swing.JTable();
        jPanelRight = new javax.swing.JPanel();
        jPanelTop = new javax.swing.JPanel();
        jLabelTitle = new javax.swing.JLabel();
        jPanelBottom = new javax.swing.JPanel();
        jPanelFields = new javax.swing.JPanel();
        jPanelMa = new javax.swing.JPanel();
        jLabelMaTacGia = new javax.swing.JLabel();
        jTextFieldMaTacGia = new javax.swing.JTextField();
        jPanelTen = new javax.swing.JPanel();
        jLabelTenTacGia = new javax.swing.JLabel();
        jTextFieldTenTacGia = new javax.swing.JTextField();
        jPanelNgaySinh = new javax.swing.JPanel();
        jLabelNgaySinh = new javax.swing.JLabel();
        jTextFieldNgaySinhTacGia = new javax.swing.JTextField();
        jPanelNoiSinh = new javax.swing.JPanel();
        jLabelNoiSinh = new javax.swing.JLabel();
        jTextFieldNoiSinhTacGia = new javax.swing.JTextField();
        jPanelTrangThai = new javax.swing.JPanel();
        jLabelSDTTacGia = new javax.swing.JLabel();
        jTextFieldSDTTacGia = new javax.swing.JTextField();

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

        // Gắn 4 nút: Xuất - Thêm - Sửa - Xóa
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
        jLabelTitle.setText("THÔNG TIN TÁC GIẢ");
        jPanelTop.add(jLabelTitle);

        jPanelRight.add(jPanelTop, java.awt.BorderLayout.PAGE_START);

        jPanelBottom.setLayout(new javax.swing.BoxLayout(jPanelBottom, javax.swing.BoxLayout.Y_AXIS));

        jPanelFields.setLayout(new javax.swing.BoxLayout(jPanelFields, javax.swing.BoxLayout.Y_AXIS));

        jPanelMa.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        jPanelMa.setLayout(new java.awt.GridLayout(0, 1));

        jLabelMaTacGia.setText("Mã tác giả:");
        jPanelMa.add(jLabelMaTacGia);

        jTextFieldMaTacGia.setEditable(false);
        jTextFieldMaTacGia.setFocusable(false);
        jPanelMa.add(jTextFieldMaTacGia);

        jPanelFields.add(jPanelMa);

        jPanelTen.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        jPanelTen.setLayout(new java.awt.GridLayout(0, 1));

        jLabelTenTacGia.setText("Tên tác giả:");
        jPanelTen.add(jLabelTenTacGia);

        jTextFieldTenTacGia.setEditable(false);
        jTextFieldTenTacGia.setFocusable(false);
        jPanelTen.add(jTextFieldTenTacGia);

        jPanelFields.add(jPanelTen);

        jPanelNgaySinh.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        jPanelNgaySinh.setLayout(new java.awt.GridLayout(0, 1));

        jLabelNgaySinh.setText("Ngày sinh tác giả:");
        jPanelNgaySinh.add(jLabelNgaySinh);

        jTextFieldNgaySinhTacGia.setEditable(false);
        jTextFieldNgaySinhTacGia.setFocusable(false);
        jPanelNgaySinh.add(jTextFieldNgaySinhTacGia);

        jPanelFields.add(jPanelNgaySinh);

        jPanelNoiSinh.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        jPanelNoiSinh.setLayout(new java.awt.GridLayout(0, 1));

        jLabelNoiSinh.setText("Nơi sinh tác giả:");
        jPanelNoiSinh.add(jLabelNoiSinh);

        jTextFieldNoiSinhTacGia.setEditable(false);
        jTextFieldNoiSinhTacGia.setFocusable(false);
        jPanelNoiSinh.add(jTextFieldNoiSinhTacGia);

        jPanelFields.add(jPanelNoiSinh);

        jPanelTrangThai.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        jPanelTrangThai.setLayout(new java.awt.GridLayout(0, 1));

        jLabelSDTTacGia.setText("SDT tác giả:");
        jPanelTrangThai.add(jLabelSDTTacGia);

        jTextFieldSDTTacGia.setEditable(false);
        jTextFieldSDTTacGia.setFocusable(false);
        jPanelTrangThai.add(jTextFieldSDTTacGia);

        jPanelFields.add(jPanelTrangThai);

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
            jButtonSua.setIcon(FontIcon.of(FontAwesomeSolid.EDIT, iconSize, new Color(13, 110, 253))); 
            jButtonXoa.setIcon(FontIcon.of(FontAwesomeSolid.TRASH, iconSize, new Color(220, 53, 69))); // Icon nút Xóa
    }
    
    private void jButtonThemActionPerformed(java.awt.event.ActionEvent evt) {
        javax.swing.JFrame parentFrame = (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);
        ThemTacGiaDialog dialog = new ThemTacGiaDialog(parentFrame, true);
        dialog.setVisible(true);
        loadData(); 
    }
    
    // Sự kiện Nút Sửa
    private void jButtonSuaActionPerformed(java.awt.event.ActionEvent evt) {
        if (currentSelected == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một tác giả trên bảng để sửa!");
            return;
        }
        javax.swing.JFrame parentFrame = (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);
        SuaTacGiaDialog dialog = new SuaTacGiaDialog(parentFrame, true, currentSelected);
        dialog.setVisible(true);
        loadData(); 
    }

    // Sự kiện Nút Xóa
    private void jButtonXoaActionPerformed(java.awt.event.ActionEvent evt) {
        if (currentSelected == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một tác giả trên bảng để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc muốn xoá (ngừng hoạt động) tác giả \"" + currentSelected.getTenTacGia() + "\" không?",
                "Xác nhận xoá",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (choice == JOptionPane.YES_OPTION) {
            try {
                boolean ok = bus.softDelete(currentSelected.getIdTacGia());
                
                if (!ok) {
                    JOptionPane.showMessageDialog(this, "Xoá thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                JOptionPane.showMessageDialog(this, "Đã xoá (ngừng hoạt động) tác giả thành công.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                
                // Tải lại bảng
                loadData(); 
                
                // Clear form
                jTableSach.clearSelection();
                jTextFieldMaTacGia.setText("");
                jTextFieldTenTacGia.setText("");
                jTextFieldNgaySinhTacGia.setText("");
                jTextFieldNoiSinhTacGia.setText("");
                jTextFieldSDTTacGia.setText("");
                currentSelected = null;
                
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi hệ thống: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void jButtonXuatActionPerformed(java.awt.event.ActionEvent evt) {}
    private void jButtonTimKiemActionPerformed(java.awt.event.ActionEvent evt) {
        thucHienTimKiem();
    }
    
    private void jButtonLamMoiActionPerformed(java.awt.event.ActionEvent evt) {
// Trả ô tìm kiếm về mặc định
        jTextFieldTimKiem.setText("Tìm kiếm...");
        
        // Tải lại toàn bộ dữ liệu
        loadData();
        
        // Làm sạch form bên phải
        jTableSach.clearSelection();
        jTextFieldMaTacGia.setText("");
        jTextFieldTenTacGia.setText("");
        jTextFieldNgaySinhTacGia.setText("");
        jTextFieldNoiSinhTacGia.setText("");
        jTextFieldSDTTacGia.setText("");
        currentSelected = null;
    }

    private javax.swing.JButton jButtonXoa;
    private javax.swing.JButton jButtonSua;
    private javax.swing.JButton jButtonLamMoi;
    private javax.swing.JButton jButtonThem;
    private javax.swing.JButton jButtonTimKiem;
    private javax.swing.JButton jButtonXuat;
    private javax.swing.JLabel jLabelMaTacGia;
    private javax.swing.JLabel jLabelNgaySinh;
    private javax.swing.JLabel jLabelNoiSinh;
    private javax.swing.JLabel jLabelSDTTacGia;
    private javax.swing.JLabel jLabelTenTacGia;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanelBoard;
    private javax.swing.JPanel jPanelBottom;
    private javax.swing.JPanel jPanelCongCu;
    private javax.swing.JPanel jPanelFields;
    private javax.swing.JPanel jPanelLeft;
    private javax.swing.JPanel jPanelLeftTop;
    private javax.swing.JPanel jPanelMa;
    private javax.swing.JPanel jPanelNgaySinh;
    private javax.swing.JPanel jPanelNoiSinh;
    private javax.swing.JPanel jPanelNutThem;
    private javax.swing.JPanel jPanelRight;
    private javax.swing.JPanel jPanelTen;
    private javax.swing.JPanel jPanelTimKiem;
    private javax.swing.JPanel jPanelTop;
    private javax.swing.JPanel jPanelTrangThai;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableSach;
    private javax.swing.JTextField jTextFieldMaTacGia;
    private javax.swing.JTextField jTextFieldNgaySinhTacGia;
    private javax.swing.JTextField jTextFieldNoiSinhTacGia;
    private javax.swing.JTextField jTextFieldSDTTacGia;
    private javax.swing.JTextField jTextFieldTenTacGia;
    private javax.swing.JTextField jTextFieldTimKiem;
}