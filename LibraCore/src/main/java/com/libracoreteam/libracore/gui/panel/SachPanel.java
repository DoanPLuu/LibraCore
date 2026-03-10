
package com.libracoreteam.libracore.gui.panel;

import com.libracoreteam.libracore.bus.SachBUS;
import com.libracoreteam.libracore.gui.dialog.ThemSachDialog;
import com.libracoreteam.libracore.gui.dialog.SuaSachDialog;
import com.libracoreteam.libracore.model.NXB;
import com.libracoreteam.libracore.model.Sach;
import com.libracoreteam.libracore.model.TacGia;
import com.libracoreteam.libracore.model.TheLoai;
import org.kordamp.ikonli.swing.FontIcon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SachPanel extends javax.swing.JPanel {

    private final SachBUS sachBUS = new SachBUS();

    private DefaultTableModel tblModel;
    private List<Sach> currentList = new ArrayList<>();
    private Sach currentSelected = null;
    private boolean isLoadingSelection = false;

    private Map<Integer, String> nxbNameById = new HashMap<>();
    private Map<Integer, String> tacGiaNameById = new HashMap<>();
    private Map<Integer, String> theLoaiNameById = new HashMap<>();

    public SachPanel() {
        initComponents();
        InnitButton();
        initTable();
        bindEvents();
        loadActiveToTable();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelLeft = new javax.swing.JPanel();
        jPanelLeftTop = new javax.swing.JPanel();
        jPanelCongCu = new javax.swing.JPanel();
        jPanelTimKiem = new javax.swing.JPanel();
        jComboBoxTimKiem = new javax.swing.JComboBox<>();
        jTextFieldTimKiem = new javax.swing.JTextField();
        jButtonTimKiem = new javax.swing.JButton();
        jButtonLamMoi = new javax.swing.JButton();
        jPanelNutThem = new javax.swing.JPanel();
        jButtonXuat = new javax.swing.JButton();
        jButtonNhap = new javax.swing.JButton();
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
        jPanelMaSach = new javax.swing.JPanel();
        jLabelMaSach = new javax.swing.JLabel();
        jTextFieldMaSach = new javax.swing.JTextField();
        jPanelTenSach = new javax.swing.JPanel();
        jLabelTenSach = new javax.swing.JLabel();
        jTextFieldTenSach = new javax.swing.JTextField();
        jPanelTacGia = new javax.swing.JPanel();
        jLabelTacGia = new javax.swing.JLabel();
        jScrollPaneTacGia = new javax.swing.JScrollPane();
        jTextAreaTacGia = new javax.swing.JTextArea();
        jPanelTheLoai = new javax.swing.JPanel();
        jLabelTheLoai = new javax.swing.JLabel();
        jScrollPaneTheLoai = new javax.swing.JScrollPane();
        jTextAreaTheLoai = new javax.swing.JTextArea();
        jPanelNhaXuatBan = new javax.swing.JPanel();
        jLabelNXB = new javax.swing.JLabel();
        jTextFieldNXB = new javax.swing.JTextField();
        jPanelSoTrang = new javax.swing.JPanel();
        jLabelSoTrang = new javax.swing.JLabel();
        jTextFieldSoTrang = new javax.swing.JTextField();
        jPanelNamXuatBan = new javax.swing.JPanel();
        jLabelNamXuatBan = new javax.swing.JLabel();
        jTextFieldNamXuatBan = new javax.swing.JTextField();
        jPanelMoTa = new javax.swing.JPanel();
        jLabelMoTa = new javax.swing.JLabel();
        jScrollPaneMoTa = new javax.swing.JScrollPane();
        jTextAreaMoTa = new javax.swing.JTextArea();
        jPanelGiaSach = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        jPanelLeft.setLayout(new java.awt.BorderLayout());

        jPanelLeftTop.setBackground(new java.awt.Color(255, 153, 153));
        jPanelLeftTop.setLayout(new java.awt.BorderLayout());

        jPanelCongCu.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 0, 20));
        jPanelCongCu.setLayout(new java.awt.BorderLayout());

        jComboBoxTimKiem.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Theo tên sách", "Theo tên tác giả" }));
        jComboBoxTimKiem.setPreferredSize(new java.awt.Dimension(110, 40));
        jPanelTimKiem.add(jComboBoxTimKiem);

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

        jButtonXuat.setText("Xuất");
        jButtonXuat.setPreferredSize(new java.awt.Dimension(90, 40));
        jButtonXuat.addActionListener(this::jButtonXuatActionPerformed);
        jPanelNutThem.add(jButtonXuat);

        jButtonNhap.setText("Nhập");
        jButtonNhap.setPreferredSize(new java.awt.Dimension(90, 40));
        jButtonNhap.addActionListener(this::jButtonNhapActionPerformed);
        jPanelNutThem.add(jButtonNhap);

        jButtonThem.setText("Thêm");
        jButtonThem.setPreferredSize(new java.awt.Dimension(90, 40));
        jButtonThem.addActionListener(this::jButtonThemActionPerformed);
        jPanelNutThem.add(jButtonThem);

        jButtonSua.setText("Sửa");
        jButtonSua.setPreferredSize(new java.awt.Dimension(90, 40));
        jButtonSua.addActionListener(this::jButtonSuaActionPerformed);
        jPanelNutThem.add(jButtonSua);

        jButtonXoa.setText("Xoá");
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
        jLabelTitle.setText("THÔNG TIN SÁCH");
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

        jPanelTenSach.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        jPanelTenSach.setLayout(new java.awt.GridLayout(0, 1));

        jLabelTenSach.setText("Tên sách:");
        jPanelTenSach.add(jLabelTenSach);

        jTextFieldTenSach.setEditable(false);
        jTextFieldTenSach.setFocusable(false);
        jPanelTenSach.add(jTextFieldTenSach);

        jPanelFields.add(jPanelTenSach);

        jPanelTacGia.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        jPanelTacGia.setMaximumSize(new java.awt.Dimension(2147483647, 200));
        jPanelTacGia.setLayout(new java.awt.BorderLayout());

        jLabelTacGia.setText("Tác giả");
        jPanelTacGia.add(jLabelTacGia, java.awt.BorderLayout.NORTH);

        jTextAreaTacGia.setEditable(false);
        jTextAreaTacGia.setColumns(20);
        jTextAreaTacGia.setLineWrap(true);
        jTextAreaTacGia.setRows(3);
        jTextAreaTacGia.setWrapStyleWord(true);
        jTextAreaTacGia.setFocusable(false);
        jScrollPaneTacGia.setViewportView(jTextAreaTacGia);

        jPanelTacGia.add(jScrollPaneTacGia, java.awt.BorderLayout.CENTER);

        jPanelFields.add(jPanelTacGia);

        jPanelTheLoai.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        jPanelTheLoai.setMaximumSize(new java.awt.Dimension(2147483647, 200));
        jPanelTheLoai.setLayout(new java.awt.BorderLayout());

        jLabelTheLoai.setText("Thể loại");
        jPanelTheLoai.add(jLabelTheLoai, java.awt.BorderLayout.NORTH);

        jTextAreaTheLoai.setEditable(false);
        jTextAreaTheLoai.setColumns(20);
        jTextAreaTheLoai.setLineWrap(true);
        jTextAreaTheLoai.setRows(4);
        jTextAreaTheLoai.setWrapStyleWord(true);
        jTextAreaTheLoai.setFocusable(false);
        jTextAreaTheLoai.setMaximumSize(new java.awt.Dimension(2147483647, 200));
        jScrollPaneTheLoai.setViewportView(jTextAreaTheLoai);

        jPanelTheLoai.add(jScrollPaneTheLoai, java.awt.BorderLayout.CENTER);

        jPanelFields.add(jPanelTheLoai);

        jPanelNhaXuatBan.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        jPanelNhaXuatBan.setLayout(new java.awt.GridLayout(0, 1));

        jLabelNXB.setText("Nhà xuất bản:");
        jPanelNhaXuatBan.add(jLabelNXB);

        jTextFieldNXB.setEditable(false);
        jTextFieldNXB.setFocusable(false);
        jTextFieldNXB.addActionListener(this::jTextFieldNXBActionPerformed);
        jPanelNhaXuatBan.add(jTextFieldNXB);

        jPanelFields.add(jPanelNhaXuatBan);

        jPanelSoTrang.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        jPanelSoTrang.setLayout(new java.awt.GridLayout(0, 1));

        jLabelSoTrang.setText("Số trang:");
        jPanelSoTrang.add(jLabelSoTrang);

        jTextFieldSoTrang.setEditable(false);
        jTextFieldSoTrang.setFocusable(false);
        jTextFieldSoTrang.addActionListener(this::jTextFieldSoTrangActionPerformed);
        jPanelSoTrang.add(jTextFieldSoTrang);

        jPanelFields.add(jPanelSoTrang);

        jPanelNamXuatBan.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        jPanelNamXuatBan.setLayout(new java.awt.GridLayout(0, 1));

        jLabelNamXuatBan.setText("Năm xuất bản:");
        jPanelNamXuatBan.add(jLabelNamXuatBan);

        jTextFieldNamXuatBan.setEditable(false);
        jTextFieldNamXuatBan.setFocusable(false);
        jTextFieldNamXuatBan.addActionListener(this::jTextFieldNamXuatBanActionPerformed);
        jPanelNamXuatBan.add(jTextFieldNamXuatBan);

        jPanelFields.add(jPanelNamXuatBan);

        jPanelMoTa.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        jPanelMoTa.setMaximumSize(new java.awt.Dimension(2147483647, 200));
        jPanelMoTa.setLayout(new java.awt.BorderLayout());

        jLabelMoTa.setText("Mô tả:");
        jPanelMoTa.add(jLabelMoTa, java.awt.BorderLayout.NORTH);

        jTextAreaMoTa.setEditable(false);
        jTextAreaMoTa.setColumns(20);
        jTextAreaMoTa.setLineWrap(true);
        jTextAreaMoTa.setRows(3);
        jTextAreaMoTa.setWrapStyleWord(true);
        jTextAreaMoTa.setFocusable(false);
        jTextAreaMoTa.setMaximumSize(new java.awt.Dimension(2147483647, 200));
        jScrollPaneMoTa.setViewportView(jTextAreaMoTa);

        jPanelMoTa.add(jScrollPaneMoTa, java.awt.BorderLayout.CENTER);

        jPanelFields.add(jPanelMoTa);

        jPanelGiaSach.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        jPanelGiaSach.setLayout(new java.awt.GridLayout(0, 1));
        jPanelFields.add(jPanelGiaSach);

        jPanelBottom.add(jPanelFields);

        jPanelRight.add(jPanelBottom, java.awt.BorderLayout.CENTER);

        add(jPanelRight, java.awt.BorderLayout.EAST);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSuaActionPerformed
        int row = jTableSach.getSelectedRow();
        if (row < 0 || row >= currentList.size()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một sách để sửa.");
            return;
        }
        startEditByRow(row);
    }//GEN-LAST:event_jButtonSuaActionPerformed

    private void jButtonXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonXoaActionPerformed
        int row = jTableSach.getSelectedRow();
        if (row < 0 || row >= currentList.size()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một sách để xoá.");
            return;
        }
        deleteByRow(row);
    }//GEN-LAST:event_jButtonXoaActionPerformed

    private void InnitButton() {
        int iconSize = 16;

        jButtonThem.setIcon(FontIcon.of(FontAwesomeSolid.PLUS_CIRCLE, iconSize, new Color(21, 110, 71)));
        jButtonXuat.setIcon(FontIcon.of(FontAwesomeSolid.FILE_EXPORT, iconSize, new Color(100, 100, 100)));
        jButtonNhap.setIcon(FontIcon.of(FontAwesomeSolid.FILE_IMPORT, iconSize, new Color(21, 110, 71)));
        jButtonTimKiem.setIcon(FontIcon.of(FontAwesomeSolid.SEARCH, iconSize, new Color(100, 100, 100)));
        jButtonLamMoi.setIcon(FontIcon.of(FontAwesomeSolid.SYNC_ALT, iconSize, new Color(100, 100, 100)));
        jTextFieldTimKiem.putClientProperty(
                "JTextField.placeholderText",
                "Tìm kiếm");

        jButtonSua.setIcon(FontIcon.of(FontAwesomeSolid.EDIT, iconSize, new Color(13, 110, 253)));
        jButtonXoa.setIcon(FontIcon.of(FontAwesomeSolid.TRASH, iconSize, new Color(220, 53, 69)));

    }

    private void initTable() {
        tblModel = new DefaultTableModel(
                new Object[] { "Mã", "Tên sách", "NXB", "Năm XB", "Số trang" },
                0) {
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
        jTableSach.setRowHeight(34);
    }

    private void bindEvents() {
        jTableSach.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (e.getValueIsAdjusting())
                return;
            if (isLoadingSelection)
                return;
            int row = jTableSach.getSelectedRow();
            if (row < 0 || row >= currentList.size())
                return;

            currentSelected = currentList.get(row);
            fillDetail(currentSelected);
        });
        
        jTextFieldTimKiem.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { thucHienTimKiem(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { thucHienTimKiem(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { thucHienTimKiem(); }
        });
    }

    public void loadActiveToTable() {
        try {
            currentList = sachBUS.getActive();
            rebuildCaches();
            renderTable(currentList);

            jTableSach.clearSelection();
            clearDetail();
            currentSelected = null;
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, "Không tải được dữ liệu sách: " + ex.getMessage());
        }
    }

    private void rebuildCaches() {
        nxbNameById.clear();
        tacGiaNameById.clear();
        theLoaiNameById.clear();

        try {
            for (NXB nxb : sachBUS.getNXBActive()) {
                nxbNameById.put(nxb.getIdNXB(), nxb.getTenNXB());
            }
        } catch (RuntimeException ignore) {
        }

        try {
            for (TacGia tg : sachBUS.getTacGiaActive()) {
                tacGiaNameById.put(tg.getIdTacGia(), tg.getTenTacGia());
            }
        } catch (RuntimeException ignore) {
        }

        try {
            for (TheLoai tl : sachBUS.getTheLoaiActive()) {
                theLoaiNameById.put(tl.getIdTheLoai(), tl.getTenTheLoai());
            }
        } catch (RuntimeException ignore) {
        }
    }

    private void renderTable(List<Sach> list) {
        if (tblModel == null)
            return;
        tblModel.setRowCount(0);

        for (Sach s : list) {
            String nxbText = "";
            if (s.getIdNXB() != null) {
                String name = nxbNameById.get(s.getIdNXB());
                nxbText = (name != null) ? name : ("#" + s.getIdNXB());
            }

            tblModel.addRow(new Object[] {
                    s.getIdSach(),
                    s.getTenSach(),
                    nxbText,
                    s.getNamXuatBan() != null ? s.getNamXuatBan() : "",
                    s.getSoTrang() != null ? s.getSoTrang() : ""
            });
        }
    }

    private void fillDetail(Sach s) {
        if (s == null)
            return;

        jTextFieldMaSach.setText(String.valueOf(s.getIdSach()));
        jTextFieldTenSach.setText(nullSafe(s.getTenSach()));
        jTextFieldNamXuatBan.setText(s.getNamXuatBan() != null ? String.valueOf(s.getNamXuatBan()) : "");
        jTextFieldSoTrang.setText(s.getSoTrang() != null ? String.valueOf(s.getSoTrang()) : "");
        jTextAreaMoTa.setText(nullSafe(s.getMoTa()));

        if (s.getIdNXB() != null) {
            String name = nxbNameById.get(s.getIdNXB());
            jTextFieldNXB.setText(name != null ? name : ("#" + s.getIdNXB()));
        } else {
            jTextFieldNXB.setText("");
        }

        try {
            List<Integer> tgIds = sachBUS.getTacGiaIdsBySach(s.getIdSach());
            jTextAreaTacGia.setText(joinNamesByIds(tgIds, tacGiaNameById));
        } catch (RuntimeException ex) {
            jTextAreaTacGia.setText("");
        }

        try {
            List<Integer> tlIds = sachBUS.getTheLoaiIdsBySach(s.getIdSach());
            jTextAreaTheLoai.setText(joinNamesByIds(tlIds, theLoaiNameById));
        } catch (RuntimeException ex) {
            jTextAreaTheLoai.setText("");
        }
    }

    private void clearDetail() {
        jTextFieldMaSach.setText("");
        jTextFieldTenSach.setText("");
        jTextAreaTacGia.setText("");
        jTextAreaTheLoai.setText("");
        jTextFieldNXB.setText("");
        jTextFieldSoTrang.setText("");
        jTextFieldNamXuatBan.setText("");
        jTextAreaMoTa.setText("");
    }

    private String joinNamesByIds(List<Integer> ids, Map<Integer, String> map) {
        if (ids == null || ids.isEmpty())
            return "";
        StringBuilder sb = new StringBuilder();
        for (Integer id : ids) {
            if (id == null)
                continue;
            String name = map.get(id);
            if (name == null)
                name = "#" + id;
            if (sb.length() > 0)
                sb.append("\n");
            sb.append(name);
        }
        return sb.toString();
    }

    private String nullSafe(String s) {
        return s == null ? "" : s;
    }

    private void selectRowById(int idSach) {
        if (currentList == null || currentList.isEmpty())
            return;
        for (int i = 0; i < currentList.size(); i++) {
            if (currentList.get(i).getIdSach() == idSach) {
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

    private void startEditByRow(int viewRow) {
        if (viewRow < 0 || viewRow >= currentList.size())
            return;
        Sach s = currentList.get(viewRow);

        javax.swing.JFrame parentFrame = (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);

        SuaSachDialog dialog = new SuaSachDialog(parentFrame, true, s.getIdSach());
        dialog.setVisible(true);

        if (dialog.isSaved()) {
            int id = s.getIdSach();
            loadActiveToTable();
            selectRowById(id);
        }
    }

    private void deleteByRow(int viewRow) {
        if (viewRow < 0 || viewRow >= currentList.size())
            return;
        Sach s = currentList.get(viewRow);

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc muốn xoá (ngừng hoạt động) sách \"" + nullSafe(s.getTenSach()) + "\" không?",
                "Xác nhận xoá",
                JOptionPane.YES_NO_OPTION);
        if (choice != JOptionPane.YES_OPTION)
            return;

        try {
            boolean ok = sachBUS.softDelete(s.getIdSach());
            if (!ok) {
                JOptionPane.showMessageDialog(this, "Xoá thất bại.");
                return;
            }
            JOptionPane.showMessageDialog(this, "Đã xoá (ngừng hoạt động).");
            loadActiveToTable();
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi hệ thống: " + ex.getMessage());
        }
    }

    private void jButtonThemActionPerformed(java.awt.event.ActionEvent evt) {
        javax.swing.JFrame parentFrame = (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);

        ThemSachDialog dialog = new ThemSachDialog(parentFrame, true);

        dialog.setVisible(true);

        if (dialog.isSaved()) {
            loadActiveToTable();
        }
    }

    private void jTextFieldMaSachActionPerformed(java.awt.event.ActionEvent evt) {
    }

    private void jTextFieldNXBActionPerformed(java.awt.event.ActionEvent evt) {
    }

    private void jTextFieldNamXuatBanActionPerformed(java.awt.event.ActionEvent evt) {
    }

    private void jTextFieldSoTrangActionPerformed(java.awt.event.ActionEvent evt) {
    }

    private void jTextFieldGiaSachActionPerformed(java.awt.event.ActionEvent evt) {
    }

    private void jButtonXuatActionPerformed(java.awt.event.ActionEvent evt) {
        List<Sach> listToExport = (currentList != null && !currentList.isEmpty())
                ? currentList
                : sachBUS.getActive();
        if (listToExport == null || listToExport.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu sách để xuất.");
            return;
        }

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Xuất danh sách sách ra Excel");
        chooser.setFileFilter(new FileNameExtensionFilter("Excel (*.xlsx)", "xlsx"));
        chooser.setAcceptAllFileFilterUsed(true);

        String defaultName = "Sach_" + System.currentTimeMillis() + ".xlsx";
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
            exportSachToExcel(file, listToExport);
            JOptionPane.showMessageDialog(this, "Xuất Excel thành công:\n" + file.getAbsolutePath());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Xuất Excel thất bại: " + ex.getMessage());
        }
    }

    private void jButtonNhapActionPerformed(java.awt.event.ActionEvent evt) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Nhập sách từ Excel");
        chooser.setFileFilter(new FileNameExtensionFilter("Excel (*.xlsx)", "xlsx"));
        chooser.setAcceptAllFileFilterUsed(true);

        int result = chooser.showOpenDialog(this);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File file = chooser.getSelectedFile();
        if (file == null || !file.exists()) {
            JOptionPane.showMessageDialog(this, "File không tồn tại.");
            return;
        }

        try {
            importSachFromExcel(file);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi đọc file Excel: " + ex.getMessage());
        }
    }

    private void jButtonTimKiemActionPerformed(java.awt.event.ActionEvent evt) {
        thucHienTimKiem();
    }

    private void jButtonLamMoiActionPerformed(java.awt.event.ActionEvent evt) {
        jTextFieldTimKiem.setText("");
        loadActiveToTable();
    }

    private void jTextFieldTimKiemActionPerformed(java.awt.event.ActionEvent evt) {
    }
    
    private void thucHienTimKiem() {
        String keyword = jTextFieldTimKiem.getText();
        if (keyword != null) keyword = keyword.trim();

        if (keyword == null || keyword.isEmpty() || "Tìm kiếm...".equalsIgnoreCase(keyword)) {
            loadActiveToTable();
            return;
        }

        try {
            if (jComboBoxTimKiem.getSelectedIndex() == 0) {
                currentList = sachBUS.searchActive(keyword);
            } else {
                currentList = sachBUS.searchByTacGia(keyword); 
            }
            
            renderTable(currentList);
            jTableSach.clearSelection();
            clearDetail();
            currentSelected = null;
        } catch (RuntimeException ex) {
            System.out.println("Lỗi tìm kiếm: " + ex.getMessage());
        }
    }

    private void exportSachToExcel(File file, List<Sach> list) throws IOException {
        rebuildCaches();

        try (Workbook wb = new XSSFWorkbook()) {
            Sheet sheet = wb.createSheet("Sach");

            Row header = sheet.createRow(0);
            String[] headers = {
                    "Mã sách",
                    "Tên sách",
                    "Nhà xuất bản",
                    "Năm XB",
                    "Số trang",
                    "Mô tả",
                    "Tác giả",
                    "Thể loại"
            };
            for (int c = 0; c < headers.length; c++) {
                Cell cell = header.createCell(c);
                cell.setCellValue(headers[c]);
            }

            int r = 1;
            for (Sach s : list) {
                Row row = sheet.createRow(r++);
                row.createCell(0).setCellValue(s.getIdSach());
                row.createCell(1).setCellValue(nullSafe(s.getTenSach()));

                String nxbText = "";
                if (s.getIdNXB() != null) {
                    String name = nxbNameById.get(s.getIdNXB());
                    nxbText = (name != null) ? name : ("#" + s.getIdNXB());
                }
                row.createCell(2).setCellValue(nxbText);

                if (s.getNamXuatBan() != null) {
                    row.createCell(3).setCellValue(s.getNamXuatBan());
                }
                if (s.getSoTrang() != null) {
                    row.createCell(4).setCellValue(s.getSoTrang());
                }
                row.createCell(5).setCellValue(nullSafe(s.getMoTa()));

                String tacGiaText = "";
                try {
                    List<Integer> tgIds = sachBUS.getTacGiaIdsBySach(s.getIdSach());
                    tacGiaText = joinNamesByIds(tgIds, tacGiaNameById).replace("\n", ", ");
                } catch (RuntimeException ignore) {
                }
                row.createCell(6).setCellValue(tacGiaText);

                String theLoaiText = "";
                try {
                    List<Integer> tlIds = sachBUS.getTheLoaiIdsBySach(s.getIdSach());
                    theLoaiText = joinNamesByIds(tlIds, theLoaiNameById).replace("\n", ", ");
                } catch (RuntimeException ignore) {
                }
                row.createCell(7).setCellValue(theLoaiText);
            }

            for (int c = 0; c < headers.length; c++) {
                sheet.autoSizeColumn(c);
            }

            try (FileOutputStream fos = new FileOutputStream(file)) {
                wb.write(fos);
            }
        }
    }

    private void importSachFromExcel(File file) throws IOException {
        rebuildCaches();

        Map<String, Integer> nxbIdByName = new LinkedHashMap<>();
        for (Map.Entry<Integer, String> e : nxbNameById.entrySet()) {
            if (e.getValue() != null) {
                nxbIdByName.put(e.getValue().trim().toLowerCase(), e.getKey());
            }
        }

        Map<String, Integer> tacGiaIdByName = new LinkedHashMap<>();
        try {
            for (TacGia tg : sachBUS.getTacGiaActive()) {
                if (tg.getTenTacGia() != null) {
                    tacGiaIdByName.put(tg.getTenTacGia().trim().toLowerCase(), tg.getIdTacGia());
                }
            }
        } catch (RuntimeException ignore) {
        }

        Map<String, Integer> theLoaiIdByName = new LinkedHashMap<>();
        try {
            for (TheLoai tl : sachBUS.getTheLoaiActive()) {
                if (tl.getTenTheLoai() != null) {
                    theLoaiIdByName.put(tl.getTenTheLoai().trim().toLowerCase(), tl.getIdTheLoai());
                }
            }
        } catch (RuntimeException ignore) {
        }

        int success = 0;
        int skipped = 0;

        try (FileInputStream fis = new FileInputStream(file);
             Workbook wb = new XSSFWorkbook(fis)) {
            Sheet sheet = wb.getSheetAt(0);
            if (sheet == null) {
                JOptionPane.showMessageDialog(this, "File Excel không có sheet dữ liệu.");
                return;
            }

            int lastRow = sheet.getLastRowNum();
            for (int r = 1; r <= lastRow; r++) {
                Row row = sheet.getRow(r);
                if (row == null) {
                    continue;
                }

                String tenSach = getCellString(row.getCell(1));
                if (tenSach == null || tenSach.trim().isEmpty()) {
                    skipped++;
                    continue;
                }

                String nxbName = getCellString(row.getCell(2));
                Integer idNXB = null;
                if (nxbName != null && !nxbName.trim().isEmpty()) {
                    Integer mapped = nxbIdByName.get(nxbName.trim().toLowerCase());
                    if (mapped != null) {
                        idNXB = mapped;
                    }
                }

                String namXBText = getCellString(row.getCell(3));
                String soTrangText = getCellString(row.getCell(4));
                String moTa = getCellString(row.getCell(5));

                String tacGiaText = getCellString(row.getCell(6));
                List<Integer> tacGiaIds = new ArrayList<>();
                if (tacGiaText != null && !tacGiaText.trim().isEmpty()) {
                    String[] parts = tacGiaText.split(",");
                    for (String p : parts) {
                        String name = p.trim();
                        if (name.isEmpty())
                            continue;
                        Integer id = tacGiaIdByName.get(name.toLowerCase());
                        if (id != null && !tacGiaIds.contains(id)) {
                            tacGiaIds.add(id);
                        }
                    }
                }

                String theLoaiText = getCellString(row.getCell(7));
                List<Integer> theLoaiIds = new ArrayList<>();
                if (theLoaiText != null && !theLoaiText.trim().isEmpty()) {
                    String[] parts = theLoaiText.split(",");
                    for (String p : parts) {
                        String name = p.trim();
                        if (name.isEmpty())
                            continue;
                        Integer id = theLoaiIdByName.get(name.toLowerCase());
                        if (id != null && !theLoaiIds.contains(id)) {
                            theLoaiIds.add(id);
                        }
                    }
                }

                try {
                    sachBUS.create(tenSach, idNXB, namXBText, soTrangText, moTa, tacGiaIds, theLoaiIds);
                    success++;
                } catch (IllegalArgumentException ex) {
                    skipped++;
                } catch (RuntimeException ex) {
                    skipped++;
                }
            }
        }

        loadActiveToTable();

        JOptionPane.showMessageDialog(this,
                "Nhập sách từ Excel hoàn tất.\n" +
                        "Thêm mới thành công: " + success + "\n" +
                        "Bỏ qua / lỗi: " + skipped);
    }

    private String getCellString(Cell cell) {
        if (cell == null) {
            return null;
        }
        CellType type = cell.getCellType();
        if (type == CellType.STRING) {
            return cell.getStringCellValue();
        } else if (type == CellType.NUMERIC) {
            double v = cell.getNumericCellValue();
            long lv = (long) v;
            if (Math.abs(v - lv) < 1e-6) {
                return String.valueOf(lv);
            }
            return String.valueOf(v);
        } else if (type == CellType.BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (type == CellType.FORMULA) {
            try {
                return cell.getStringCellValue();
            } catch (IllegalStateException e) {
                try {
                    return String.valueOf(cell.getNumericCellValue());
                } catch (IllegalStateException ex) {
                    return null;
                }
            }
        }
        return null;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonLamMoi;
    private javax.swing.JButton jButtonNhap;
    private javax.swing.JButton jButtonSua;
    private javax.swing.JButton jButtonThem;
    private javax.swing.JButton jButtonTimKiem;
    private javax.swing.JButton jButtonXoa;
    private javax.swing.JButton jButtonXuat;
    private javax.swing.JComboBox<String> jComboBoxTimKiem;
    private javax.swing.JLabel jLabelMaSach;
    private javax.swing.JLabel jLabelMoTa;
    private javax.swing.JLabel jLabelNXB;
    private javax.swing.JLabel jLabelNamXuatBan;
    private javax.swing.JLabel jLabelSoTrang;
    private javax.swing.JLabel jLabelTacGia;
    private javax.swing.JLabel jLabelTenSach;
    private javax.swing.JLabel jLabelTheLoai;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JPanel jPanelBoard;
    private javax.swing.JPanel jPanelBottom;
    private javax.swing.JPanel jPanelCongCu;
    private javax.swing.JPanel jPanelFields;
    private javax.swing.JPanel jPanelGiaSach;
    private javax.swing.JPanel jPanelLeft;
    private javax.swing.JPanel jPanelLeftTop;
    private javax.swing.JPanel jPanelMaSach;
    private javax.swing.JPanel jPanelMoTa;
    private javax.swing.JPanel jPanelNamXuatBan;
    private javax.swing.JPanel jPanelNhaXuatBan;
    private javax.swing.JPanel jPanelNutThem;
    private javax.swing.JPanel jPanelRight;
    private javax.swing.JPanel jPanelSoTrang;
    private javax.swing.JPanel jPanelTacGia;
    private javax.swing.JPanel jPanelTenSach;
    private javax.swing.JPanel jPanelTheLoai;
    private javax.swing.JPanel jPanelTimKiem;
    private javax.swing.JPanel jPanelTop;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPaneMoTa;
    private javax.swing.JScrollPane jScrollPaneTacGia;
    private javax.swing.JScrollPane jScrollPaneTheLoai;
    private javax.swing.JTable jTableSach;
    private javax.swing.JTextArea jTextAreaMoTa;
    private javax.swing.JTextArea jTextAreaTacGia;
    private javax.swing.JTextArea jTextAreaTheLoai;
    private javax.swing.JTextField jTextFieldMaSach;
    private javax.swing.JTextField jTextFieldNXB;
    private javax.swing.JTextField jTextFieldNamXuatBan;
    private javax.swing.JTextField jTextFieldSoTrang;
    private javax.swing.JTextField jTextFieldTenSach;
    private javax.swing.JTextField jTextFieldTimKiem;
    // End of variables declaration//GEN-END:variables
}
