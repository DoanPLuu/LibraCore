package com.libracoreteam.libracore.gui.dialog;

import com.libracoreteam.libracore.bus.PhieuMuonBUS;
import com.libracoreteam.libracore.model.ChiTietPhieuMuon;
import com.libracoreteam.libracore.model.PhieuMuon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.List;

public class ChiTietPhieuMuonDialog extends JDialog {

  public ChiTietPhieuMuonDialog(Frame parent, PhieuMuon phieuMuon) {
    super(parent, "Chi tiết phiếu mượn #" + phieuMuon.getIdPhieuMuon(), true);
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

    JPanel infoPanel = new JPanel(new GridLayout(5, 2, 6, 4));
    infoPanel.setBorder(BorderFactory.createTitledBorder("Thông tin"));
    infoPanel.add(new JLabel("Đọc giả:"));
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
      JTextField tfLyDo = makeField(pm.getLyDoHuy() != null ? pm.getLyDoHuy() : "");
      infoPanel.setLayout(new GridLayout(6, 2, 6, 4));
      infoPanel.add(new JLabel("Lý do hủy:"));
      infoPanel.add(tfLyDo);
    }

    DefaultTableModel tableModel = new DefaultTableModel(
        new String[] { "Mã cuốn", "Tên sách", "Tình trạng", "Ngày trả" }, 0) {
      @Override
      public boolean isCellEditable(int r, int c) {
        return false;
      }
    };

    PhieuMuonBUS bus = new PhieuMuonBUS();
    List<ChiTietPhieuMuon> chiTiet = bus.getChiTiet(pm.getIdPhieuMuon());
    for (ChiTietPhieuMuon ct : chiTiet) {
      tableModel.addRow(new Object[] {
          ct.getCuonSach() != null ? ct.getCuonSach().getMaCuonSach() : "",
          ct.getCuonSach() != null && ct.getCuonSach().getSach() != null ? ct.getCuonSach().getSach().getTenSach() : "",
          toViTinhTrang(ct.getTinhTrangTra()),
          ct.getNgayTra() != null ? ct.getNgayTra().toString() : "Chưa trả"
      });
    }

    JButton btnXuatPDF = new JButton("Xuất PDF");
    btnXuatPDF.addActionListener((ActionEvent e) -> xuatPDF(pm, chiTiet));
    JButton btnDong = new JButton("Đóng");
    btnDong.addActionListener(e -> dispose());

    setLayout(new BorderLayout(6, 6));
    add(infoPanel, BorderLayout.NORTH);
    add(new JScrollPane(new JTable(tableModel)), BorderLayout.CENTER);
    JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    btnPanel.add(btnXuatPDF);
    btnPanel.add(btnDong);
    add(btnPanel, BorderLayout.SOUTH);
    getRootPane().setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
  }

  private JTextField makeField(String text) {
    JTextField tf = new JTextField(text);
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

  private void xuatPDF(PhieuMuon pm, List<ChiTietPhieuMuon> chiTiet) {
    JFileChooser fc = new JFileChooser();
    fc.setSelectedFile(new File("PhieuMuon_" + pm.getIdPhieuMuon() + ".txt"));
    if (fc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION)
      return;
    try (PrintWriter pw = new PrintWriter(new FileWriter(fc.getSelectedFile()))) {
      pw.println("=== CHỨNG NHẬN TRẢ SÁCH ===");
      pw.println("Mã phiếu: #" + pm.getIdPhieuMuon());
      pw.println("Đọc giả: " + (pm.getTheThanhVien() != null && pm.getTheThanhVien().getDocGia() != null
          ? pm.getTheThanhVien().getDocGia().getTenDocGia()
          : ""));
      pw.println("Nhân viên lập: " + (pm.getNhanVien() != null ? pm.getNhanVien().getTenNhanVien() : ""));
      pw.println("Ngày mượn: " + pm.getNgayMuon());
      pw.println("Hạn trả: " + pm.getNgayHenTra());
      pw.println("Trạng thái: " + toViTrangThai(pm.getTrangThai()));
      pw.println("----------------------------");
      pw.println("Danh sách sách:");
      for (ChiTietPhieuMuon ct : chiTiet) {
        pw.printf("  - %s | %s | %s | Ngày trả: %s%n",
            ct.getCuonSach() != null ? ct.getCuonSach().getMaCuonSach() : "",
            ct.getCuonSach() != null && ct.getCuonSach().getSach() != null ? ct.getCuonSach().getSach().getTenSach()
                : "",
            toViTinhTrang(ct.getTinhTrangTra()),
            ct.getNgayTra() != null ? ct.getNgayTra().toString() : "Chưa trả");
      }
      pw.println("=== HẾT ===");
      JOptionPane.showMessageDialog(this, "Xuất thành công: " + fc.getSelectedFile().getAbsolutePath());
    } catch (IOException ex) {
      JOptionPane.showMessageDialog(this, "Lỗi xuất file: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
  }
}
