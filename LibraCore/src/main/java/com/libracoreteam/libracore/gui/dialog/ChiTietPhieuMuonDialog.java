package com.libracoreteam.libracore.gui.dialog;

import com.libracoreteam.libracore.bus.PhieuMuonBUS;
import com.libracoreteam.libracore.model.ChiTietPhieuMuon;
import com.libracoreteam.libracore.model.PhieuMuon;
import com.libracoreteam.libracore.util.PdfExportUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ChiTietPhieuMuonDialog extends JDialog {

  private Frame parentFrame;

  public ChiTietPhieuMuonDialog(Frame parent, PhieuMuon phieuMuon) {
    super(parent, "Chi tiết phiếu mượn #" + phieuMuon.getIdPhieuMuon(), true);
    this.parentFrame = parent;
    initUI(phieuMuon);
    pack();
    setMinimumSize(new Dimension(640, 420));
    setLocationRelativeTo(parent);
  }

  private void initUI(PhieuMuon pm) {
    String tenDocGia = pm.getTheThanhVien() != null && pm.getTheThanhVien().getDocGia() != null
        ? pm.getTheThanhVien().getDocGia().getTenDocGia()
        : "";
    String tenNV = pm.getNhanVien() != null ? pm.getNhanVien().getTenNhanVien() : "";

    JTextField tfDocGia = makeField(tenDocGia);
    JTextField tfNhanVien = makeField(tenNV);
    JTextField tfNgayMuon = makeField(pm.getNgayMuon() != null ? pm.getNgayMuon().toString() : "");
    JTextField tfHenTra = makeField(pm.getNgayHenTra() != null ? pm.getNgayHenTra().toString() : "");
    JTextField tfTrangThai = makeField(toViTrangThai(pm.getTrangThai()));

    int rows = "DaHuy".equals(pm.getTrangThai()) ? 6 : 5;
    JPanel infoPanel = new JPanel(new GridLayout(rows, 2, 6, 4));
    infoPanel.setBorder(BorderFactory.createTitledBorder("Thông tin"));
    infoPanel.add(new JLabel("Độc giả:"));
    infoPanel.add(tfDocGia);
    infoPanel.add(new JLabel("Nhân viên lập:"));
    infoPanel.add(tfNhanVien);
    infoPanel.add(new JLabel("Ngày mượn:"));
    infoPanel.add(tfNgayMuon);
    infoPanel.add(new JLabel("Hạn trả:"));
    infoPanel.add(tfHenTra);
    infoPanel.add(new JLabel("Trạng thái:"));
    infoPanel.add(tfTrangThai);

    if ("DaHuy".equals(pm.getTrangThai())) {
      infoPanel.add(new JLabel("Lý do hủy:"));
      infoPanel.add(makeField(pm.getLyDoHuy() != null ? pm.getLyDoHuy() : ""));
    }

    DefaultTableModel tableModel = new DefaultTableModel(
        new String[] { "Mã cuốn", "Tên sách", "Tình trạng", "Ngày trả" }, 0) {
      @Override
      public boolean isCellEditable(int r, int c) {
        return false;
      }
    };

    List<ChiTietPhieuMuon> chiTiet = new PhieuMuonBUS().getChiTiet(pm.getIdPhieuMuon());
    for (ChiTietPhieuMuon ct : chiTiet) {
      tableModel.addRow(new Object[] {
          ct.getCuonSach() != null ? ct.getCuonSach().getMaCuonSach() : "",
          ct.getCuonSach() != null && ct.getCuonSach().getSach() != null ? ct.getCuonSach().getSach().getTenSach() : "",
          toViTinhTrang(ct.getTinhTrangTra()),
          ct.getNgayTra() != null ? ct.getNgayTra().toString() : "Chưa trả"
      });
    }

    JTable table = new JTable(tableModel);
    table.getColumnModel().getColumn(0).setPreferredWidth(80);
    table.getColumnModel().getColumn(1).setPreferredWidth(250);
    table.getColumnModel().getColumn(2).setPreferredWidth(100);
    table.getColumnModel().getColumn(3).setPreferredWidth(100);
    table.setDefaultEditor(Object.class, null);
    table.setRowHeight(25);

    JButton btnXuatPDF = new JButton("Xuất PDF");
    btnXuatPDF.setBackground(new Color(0xC6, 0x28, 0x28));
    btnXuatPDF.setForeground(Color.WHITE);
    btnXuatPDF.setFocusPainted(false);
    btnXuatPDF.addActionListener(e -> {
      String title = "Chứng Nhận Trả Sách - Phiếu #" + pm.getIdPhieuMuon();
      String subtitle = "Độc giả: " + tenDocGia
          + "  |  Nhân viên: " + tenNV
          + "  |  Ngày mượn: " + pm.getNgayMuon()
          + "  |  Hạn trả: " + pm.getNgayHenTra()
          + "  |  Trạng thái: " + toViTrangThai(pm.getTrangThai());
      String[] cols = { "Mã cuốn", "Tên sách", "Tình trạng", "Ngày trả" };
      PdfExportUtil.export(parentFrame, title, subtitle, cols,
          PdfExportUtil.fromTableModel(tableModel), "PhieuMuon_" + pm.getIdPhieuMuon() + ".pdf");
    });

    JButton btnDong = new JButton("Đóng");
    btnDong.addActionListener(e -> dispose());

    setLayout(new BorderLayout(6, 6));
    add(infoPanel, BorderLayout.NORTH);
    add(new JScrollPane(table), BorderLayout.CENTER);
    JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    btnPanel.add(btnXuatPDF);
    btnPanel.add(btnDong);
    add(btnPanel, BorderLayout.SOUTH);
    getRootPane().setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
  }

  private JTextField makeField(String text) {
    JTextField tf = new JTextField(text, 25);
    tf.setEditable(false);
    tf.setBackground(new Color(245, 245, 245));
    return tf;
  }

  private String toViTrangThai(String tt) {
    if (tt == null)
      return "";
    return switch (tt) {
      case "DangMuon" -> "Đang mượn";
      case "DaTra" -> "Đã trả";
      case "DaHuy" -> "Đã hủy";
      case "QuaHan" -> "Quá hạn";
      default -> tt;
    };
  }

  private String toViTinhTrang(String tt) {
    if (tt == null)
      return "Chưa trả";
    return switch (tt) {
      case "DaTra" -> "Đã trả";
      case "TreHan" -> "Trả trễ hẹn";
      case "Hong" -> "Hư/Mất";
      case "ChuaTra" -> "Chưa trả";
      default -> tt;
    };
  }
}
