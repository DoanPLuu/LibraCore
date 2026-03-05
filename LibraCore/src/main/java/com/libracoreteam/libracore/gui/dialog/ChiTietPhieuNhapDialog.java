package com.libracoreteam.libracore.gui.dialog;

import com.libracoreteam.libracore.model.ChiTietPhieuNhap;
import com.libracoreteam.libracore.model.PhieuNhap;
import com.libracoreteam.libracore.model.Sach;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import net.miginfocom.swing.MigLayout;

public class ChiTietPhieuNhapDialog extends JDialog {

    private final PhieuNhap phieuNhap;
    private final List<ChiTietPhieuNhap> details;

    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel lblTongSoLuong;
    private JLabel lblTongTien;

    public ChiTietPhieuNhapDialog(JFrame parent, boolean modal, PhieuNhap phieuNhap, List<ChiTietPhieuNhap> details) {
        super(parent, modal);
        this.phieuNhap = phieuNhap;
        this.details = details == null ? Collections.<ChiTietPhieuNhap>emptyList() : details;

        initComponents();
        loadData();
    }

    private void initComponents() {
        setTitle("Chi tiết phiếu nhập #" + phieuNhap.getIdPhieuNhap());
        setLayout(new BorderLayout(6, 6));

        // ===== Panel thông tin giống các dialog phiếu mượn/phạt =====
        JPanel infoPanel = new JPanel(new GridLayout(4, 2, 6, 4));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Thông tin phiếu nhập"));

        String maPhieu = String.valueOf(phieuNhap.getIdPhieuNhap());
        String ngayNhap = phieuNhap.getNgayNhap() == null ? "" : phieuNhap.getNgayNhap().toString();
        String tenNcc = "";
        if (phieuNhap.getNcc() != null && phieuNhap.getNcc().getTenNCC() != null) {
            tenNcc = phieuNhap.getNcc().getTenNCC();
        }
        String trangThai = phieuNhap.getTrangThai() == null ? "" : phieuNhap.getTrangThai();

        String tenNhanVien = "";
        if (phieuNhap.getNhanVien() != null && phieuNhap.getNhanVien().getTenNhanVien() != null) {
            tenNhanVien = phieuNhap.getNhanVien().getTenNhanVien();
        } else if (phieuNhap.getIdNhanVien() > 0) {
            tenNhanVien = "Mã NV: " + phieuNhap.getIdNhanVien();
        }

        infoPanel.add(new JLabel("Mã phiếu:"));
        infoPanel.add(new JLabel(maPhieu));
        infoPanel.add(new JLabel("Ngày nhập:"));
        infoPanel.add(new JLabel(ngayNhap));
        infoPanel.add(new JLabel("Nhà cung cấp:"));
        infoPanel.add(new JLabel(tenNcc));
        infoPanel.add(new JLabel("Nhân viên xử lý:"));
        infoPanel.add(new JLabel(tenNhanVien));

        add(infoPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(
                new Object[]{"STT", "Tên sách", "Số lượng", "Đơn giá", "Thành tiền"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setRowHeight(28);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new MigLayout("insets 10 16 16 16", "[grow][right][grow]"));
        lblTongSoLuong = new JLabel("Tổng số lượng: 0");
        lblTongTien = new JLabel("Tổng tiền: 0 đ");
        bottomPanel.add(lblTongSoLuong, "split 2");
        bottomPanel.add(new JLabel("    "));
        bottomPanel.add(lblTongTien);
        add(bottomPanel, BorderLayout.SOUTH);

        // ===== Nút đóng giống các dialog khác =====
        JButton btnDong = new JButton("Đóng");
        btnDong.addActionListener(e -> dispose());
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.add(btnDong);
        add(btnPanel, BorderLayout.PAGE_END);

        getRootPane().setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        pack();
        setMinimumSize(new java.awt.Dimension(650, 420));
        setLocationRelativeTo(getParent());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void loadData() {
        int stt = 1;
        int tongSoLuong = 0;
        BigDecimal tongTien = BigDecimal.ZERO;

        for (ChiTietPhieuNhap ct : details) {
            if (ct == null) {
                continue;
            }

            Sach sach = ct.getSach();
            String tenSach = (sach != null && sach.getTenSach() != null) ? sach.getTenSach() : ("#" + ct.getIdSach());
            int soLuong = ct.getSoLuong() == null ? 0 : ct.getSoLuong();
            BigDecimal donGia = ct.getGiaTien() == null ? BigDecimal.ZERO : ct.getGiaTien();
            BigDecimal thanhTien = donGia.multiply(BigDecimal.valueOf(soLuong));

            tableModel.addRow(new Object[]{
                    stt++,
                    tenSach,
                    soLuong,
                    donGia,
                    thanhTien
            });

            tongSoLuong += soLuong;
            tongTien = tongTien.add(thanhTien);
        }

        lblTongSoLuong.setText("Tổng số lượng: " + tongSoLuong);
        lblTongTien.setText("Tổng tiền: " + tongTien.toPlainString() + " đ");
    }
}
