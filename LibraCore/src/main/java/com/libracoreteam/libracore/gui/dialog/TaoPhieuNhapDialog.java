package com.libracoreteam.libracore.gui.dialog;

import com.libracoreteam.libracore.bus.PhieuNhapBUS;
import com.libracoreteam.libracore.dao.NCCDAO;
import com.libracoreteam.libracore.dao.SachDAO;
import com.libracoreteam.libracore.model.ChiTietPhieuNhap;
import com.libracoreteam.libracore.model.NCC;
import com.libracoreteam.libracore.model.PhieuNhap;
import com.libracoreteam.libracore.model.Sach;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultCellEditor;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import net.miginfocom.swing.MigLayout;

public class TaoPhieuNhapDialog extends JDialog {

    private static final int MOCK_NHAN_VIEN_ID = 1;

    private final PhieuNhapBUS phieuNhapBUS = new PhieuNhapBUS();
    private final SachDAO sachDAO = new SachDAO();
    private final NCCDAO nccDAO = new NCCDAO();

    private boolean saved = false;
    private boolean isUpdatingTotals = false;

    private JPanel formPanel;
    private JTextField txtNhanVien;
    private JComboBox<NCC> cbxNCC;
    private JTextField txtNgayNhap;
    private JTextField txtTongSoLuong;
    private JTextField txtTongTien;

    private JPanel tablePanel;
    private JTextField txtTimKiem;
    private JButton btnTimKiem;
    private JComboBox<Sach> cbxKetQuaTimKiem;
    private JButton btnThemSach;
    private JTable tblChiTiet;
    private DefaultTableModel tableModel;
    private JButton btnLuuPhieu;

    public TaoPhieuNhapDialog(JFrame parent, boolean modal) {
        super(parent, modal);
        setTitle("Tạo Phiếu Nhập Mới");
        setSize(1000, 700);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initComponents();
        setupLayout();
        bindEvents();
    }

    private void initComponents() {
        txtNhanVien = new JTextField(20);
        txtNhanVien.setEditable(false);
        txtNhanVien.setText("Nhân viên #" + MOCK_NHAN_VIEN_ID);

        cbxNCC = new JComboBox<>();
        loadNCC();

        txtNgayNhap = new JTextField(20);
        txtNgayNhap.setEditable(false);
        txtNgayNhap.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        txtTongSoLuong = new JTextField("0", 20);
        txtTongSoLuong.setEditable(false);

        txtTongTien = new JTextField("0 đ", 20);
        txtTongTien.setEditable(false);
        txtTongTien.setFont(txtTongTien.getFont().deriveFont(Font.BOLD, 14f));
        txtTongTien.setForeground(new Color(255, 51, 51));

        txtTimKiem = new JTextField(20);
        txtTimKiem.putClientProperty("JTextField.placeholderText", "Nhập tên sách...");

        btnTimKiem = new JButton("Tìm");
        cbxKetQuaTimKiem = new JComboBox<Sach>();
        cbxKetQuaTimKiem.setPreferredSize(new Dimension(250, 30));

        btnTimKiem.addActionListener(e -> doSearch());

        txtTimKiem.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    doSearch();
                }
            }
        });

        btnThemSach = new JButton("Thêm vào phiếu");
        btnThemSach.addActionListener(e -> addSelectedSachToTable());

        String[] cols = {"STT", "Mã đầu sách", "Tên sách", "Số lượng", "Đơn giá nhập", "Thành tiền", "Hành động"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1 || column == 3 || column == 4 || column == 6;
            }
        };
        tblChiTiet = new JTable(tableModel);
        tblChiTiet.setRowHeight(30);
        tblChiTiet.getColumnModel().getColumn(6).setCellRenderer(new DeleteButtonRenderer());
        tblChiTiet.getColumnModel().getColumn(6).setCellEditor(new DeleteButtonEditor());
        tblChiTiet.getColumnModel().getColumn(6).setPreferredWidth(85);
        tblChiTiet.getColumnModel().getColumn(6).setMaxWidth(100);

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        tblChiTiet.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
        tblChiTiet.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
        tblChiTiet.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);

        btnLuuPhieu = new JButton("Lưu Phiếu Nhập");
        btnLuuPhieu.setFont(btnLuuPhieu.getFont().deriveFont(Font.BOLD, 14f));
        btnLuuPhieu.setBackground(new Color(40, 167, 69));
        btnLuuPhieu.setForeground(Color.WHITE);
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));

        formPanel = new JPanel(new MigLayout("wrap 4, insets 20", "[right][grow][right][grow]"));

        JLabel lblTitle = new JLabel("THÔNG TIN PHIẾU NHẬP");
        lblTitle.setFont(lblTitle.getFont().deriveFont(Font.BOLD, 18f));
        formPanel.add(lblTitle, "span 4, center, wrap, gapbottom 10");

        formPanel.add(new JLabel("Nhân viên lập:"));
        formPanel.add(txtNhanVien, "growx");
        formPanel.add(new JLabel("Tổng số lượng sách:"));
        formPanel.add(txtTongSoLuong, "growx");

        formPanel.add(new JLabel("Nhà cung cấp:"));
        formPanel.add(cbxNCC, "growx");
        formPanel.add(new JLabel("Tổng tiền:"));
        formPanel.add(txtTongTien, "growx");

        formPanel.add(new JLabel("Ngày nhập:"));
        formPanel.add(txtNgayNhap, "growx");

        add(formPanel, BorderLayout.NORTH);

        tablePanel = new JPanel(new MigLayout("insets 20, fill", "[][][grow][]", "[][grow][]"));

        tablePanel.add(txtTimKiem, "split 2");
        tablePanel.add(btnTimKiem);
        tablePanel.add(cbxKetQuaTimKiem, "growx");
        tablePanel.add(btnThemSach, "wrap");

        tablePanel.add(new JScrollPane(tblChiTiet), "span 4, grow, wrap, gaptop 10, gapbottom 10");

        tablePanel.add(btnLuuPhieu, "span 4, right, width 150!, height 40!");

        add(tablePanel, BorderLayout.CENTER);
    }

    private void bindEvents() {
        tableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (isUpdatingTotals) {
                    return;
                }
                int col = e.getColumn();
                if (col == 3 || col == 4 || col == TableModelEvent.ALL_COLUMNS || e.getType() == TableModelEvent.DELETE) {
                    updateTongs();
                }
            }
        });

        btnLuuPhieu.addActionListener(e -> savePhieuNhap());
    }

    private void loadNCC() {
        cbxNCC.removeAllItems();
        List<NCC> nccList = nccDAO.getAll();
        for (NCC ncc : nccList) {
            cbxNCC.addItem(ncc);
        }
    }

    private void doSearch() {
        String kw = txtTimKiem.getText().trim();
        if (kw.isEmpty()) {
            return;
        }
        cbxKetQuaTimKiem.removeAllItems();
        List<Sach> results = sachDAO.searchActive(kw);

        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy sách nào!");
            return;
        }
        for (Sach s : results) {
            cbxKetQuaTimKiem.addItem(s);
        }
    }

    private void addSelectedSachToTable() {
        Sach s = (Sach) cbxKetQuaTimKiem.getSelectedItem();
        if (s == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một đầu sách từ danh sách kết quả!");
            return;
        }

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            Sach rowSach = (Sach) tableModel.getValueAt(i, 2);
            if (rowSach != null && rowSach.getIdSach() == s.getIdSach()) {
                JOptionPane.showMessageDialog(this, "Sách này đã có trong phiếu nhập!");
                return;
            }
        }

        int stt = tableModel.getRowCount() + 1;

        Object[] rowData = {
                stt,
                "S-" + s.getIdSach(),
                s,
                1,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                "Xóa"
        };
        tableModel.addRow(rowData);

        txtTimKiem.setText("");
        cbxKetQuaTimKiem.removeAllItems();
        txtTimKiem.requestFocus();

        updateTongs();
    }

    private void updateTongs() {
        isUpdatingTotals = true;
        try {
            int totalQty = 0;
            BigDecimal totalAmt = BigDecimal.ZERO;

            for (int i = 0; i < tableModel.getRowCount(); i++) {
                tableModel.setValueAt(i + 1, i, 0);

                int qty = 0;
                BigDecimal price = BigDecimal.ZERO;

                try {
                    qty = parsePositiveIntOrZero(tableModel.getValueAt(i, 3));
                    price = parseBigDecimalOrZero(tableModel.getValueAt(i, 4));
                } catch (Exception ignored) {
                }

                BigDecimal rowTotal = price.multiply(BigDecimal.valueOf(qty));
                tableModel.setValueAt(rowTotal, i, 5);

                totalQty += qty;
                totalAmt = totalAmt.add(rowTotal);
            }

            txtTongSoLuong.setText(String.valueOf(totalQty));
            txtTongTien.setText(totalAmt.toPlainString() + " đ");
        } finally {
            isUpdatingTotals = false;
        }
    }

    private void removeRow(int row) {
        if (row < 0 || row >= tableModel.getRowCount()) {
            return;
        }
        tableModel.removeRow(row);
        updateTongs();
    }

    private void savePhieuNhap() {
        if (tblChiTiet.isEditing()) {
            tblChiTiet.getCellEditor().stopCellEditing();
        }
        if (tableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Phiếu nhập chưa có dòng chi tiết nào.");
            return;
        }

        try {
            List<ChiTietPhieuNhap> details = new ArrayList<ChiTietPhieuNhap>();
            int tongSoLuong = 0;

            for (int i = 0; i < tableModel.getRowCount(); i++) {
                int rowNum = i + 1;
                Sach sach = (Sach) tableModel.getValueAt(i, 2);
                if (sach == null || sach.getIdSach() <= 0) {
                    throw new IllegalArgumentException("Dòng " + rowNum + ": đầu sách không hợp lệ");
                }

                String maDauSach = valueAsString(tableModel.getValueAt(i, 1));
                if (maDauSach.isEmpty()) {
                    maDauSach = "S-" + sach.getIdSach();
                }

                int soLuong = parsePositiveInt(tableModel.getValueAt(i, 3), "Dòng " + rowNum + ": số lượng phải > 0");
                BigDecimal donGia = parseNonNegativeBigDecimal(tableModel.getValueAt(i, 4),
                        "Dòng " + rowNum + ": đơn giá phải >= 0");

                ChiTietPhieuNhap ct = new ChiTietPhieuNhap();
                ct.setIdSach(sach.getIdSach());
                ct.setMaDauSach(maDauSach);
                ct.setSoLuong(soLuong);
                ct.setGiaTien(donGia);
                details.add(ct);

                tongSoLuong += soLuong;
            }

            NCC ncc = (NCC) cbxNCC.getSelectedItem();
            PhieuNhap phieuNhap = new PhieuNhap();
            phieuNhap.setIdNCC(ncc == null ? null : ncc.getIdNCC());
            phieuNhap.setNgayNhap(LocalDate.now());
            phieuNhap.setIdNhanVien(MOCK_NHAN_VIEN_ID);
            phieuNhap.setSoLuongSach(tongSoLuong);
            phieuNhap.setTrangThai("DaNhap");

            phieuNhapBUS.create(phieuNhap, details);
            saved = true;

            JOptionPane.showMessageDialog(this, "Lưu phiếu nhập thành công.");
            dispose();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Dữ liệu chưa hợp lệ", JOptionPane.WARNING_MESSAGE);
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, "Không thể lưu phiếu nhập: " + ex.getMessage());
        }
    }

    public boolean isSaved() {
        return saved;
    }

    private static String valueAsString(Object value) {
        if (value == null) {
            return "";
        }
        return String.valueOf(value).trim();
    }

    private static int parsePositiveInt(Object value, String errorMessage) {
        int n = parsePositiveIntOrZero(value);
        if (n <= 0) {
            throw new IllegalArgumentException(errorMessage);
        }
        return n;
    }

    private static int parsePositiveIntOrZero(Object value) {
        String text = valueAsString(value);
        if (text.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(text);
    }

    private static BigDecimal parseNonNegativeBigDecimal(Object value, String errorMessage) {
        BigDecimal n = parseBigDecimalOrZero(value);
        if (n.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(errorMessage);
        }
        return n;
    }

    private static BigDecimal parseBigDecimalOrZero(Object value) {
        String text = valueAsString(value);
        if (text.isEmpty()) {
            return BigDecimal.ZERO;
        }
        // Cho phép nhập kiểu "10,000" để tránh lỗi format thường gặp.
        text = text.replace(",", "");
        return new BigDecimal(text);
    }

    private class DeleteButtonRenderer extends JButton implements TableCellRenderer {
        DeleteButtonRenderer() {
            setOpaque(true);
            setText("Xóa");
            setForeground(Color.WHITE);
            setBackground(new Color(220, 53, 69));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            return this;
        }
    }

    private class DeleteButtonEditor extends DefaultCellEditor {
        private final JButton button;
        private int row = -1;

        DeleteButtonEditor() {
            super(new JTextField());
            button = new JButton("Xóa");
            button.setForeground(Color.WHITE);
            button.setBackground(new Color(220, 53, 69));
            button.addActionListener((ActionEvent e) -> {
                fireEditingStopped();
                removeRow(row);
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.row = row;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return "Xóa";
        }
    }
}
