package com.libracoreteam.libracore.gui.dialog;

import com.libracoreteam.libracore.model.ChiTietPhieuNhap;
import com.libracoreteam.libracore.model.PhieuNhap;
import com.libracoreteam.libracore.model.Sach;

import java.awt.BorderLayout;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
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

        setTitle("Chi tiết phiếu nhập");
        setSize(900, 600);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initComponents();
        loadData();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        JPanel headerPanel = new JPanel(new MigLayout("wrap 4, insets 16", "[right][grow][right][grow]"));
        headerPanel.add(new JLabel("Mã phiếu:"));
        headerPanel.add(new JLabel(String.valueOf(phieuNhap.getIdPhieuNhap())));
        headerPanel.add(new JLabel("Ngày nhập:"));
        headerPanel.add(new JLabel(phieuNhap.getNgayNhap() == null ? "" : String.valueOf(phieuNhap.getNgayNhap())));

        headerPanel.add(new JLabel("Nhà cung cấp:"));
        String tenNcc = "";
        if (phieuNhap.getNcc() != null && phieuNhap.getNcc().getTenNCC() != null) {
            tenNcc = phieuNhap.getNcc().getTenNCC();
        }
        headerPanel.add(new JLabel(tenNcc));
        headerPanel.add(new JLabel("Trạng thái:"));
        headerPanel.add(new JLabel(phieuNhap.getTrangThai() == null ? "" : phieuNhap.getTrangThai()));
        add(headerPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(
                new Object[]{"STT", "Mã đầu sách", "Tên sách", "Số lượng", "Đơn giá", "Thành tiền"},
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
                    ct.getMaDauSach() == null ? "" : ct.getMaDauSach(),
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
