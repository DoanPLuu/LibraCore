package com.libracoreteam.libracore.gui.dialog;

import com.libracoreteam.libracore.bus.PhieuPhatBUS;
import com.libracoreteam.libracore.model.ChiTietPhieuPhat;
import com.libracoreteam.libracore.model.PhieuPhat;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ChiTietPhieuPhatDialog extends JDialog {

  public ChiTietPhieuPhatDialog(Frame parent, PhieuPhat phieuPhat) {
    super(parent, "Chi tiết phiếu phạt #" + phieuPhat.getIdPhieuPhat(), true);
    initUI(phieuPhat);
    pack();
    setMinimumSize(new Dimension(680, 420));
    setLocationRelativeTo(parent);
  }

  private void initUI(PhieuPhat pp) {
    JTextField tfNgay = makeField(pp.getNgayLap() != null ? pp.getNgayLap().toString() : "");
    JTextField tfDocGia = makeField(pp.getTenDocGia() != null ? pp.getTenDocGia() : "");
    JTextField tfTong = makeField(
        pp.getTienPhatPhaiNop() != null ? pp.getTienPhatPhaiNop().toPlainString() + " đ" : "");
    JTextField tfLyDo = makeField(pp.getLyDoPhat());
    JTextField tfTrangThai = makeField(toViTrangThai(pp.getTrangThai()));

    JPanel infoPanel = new JPanel(new GridLayout(5, 2, 6, 4));
    infoPanel.setBorder(BorderFactory.createTitledBorder("Thông tin phiếu phạt"));
    infoPanel.add(new JLabel("Ngày lập:"));
    infoPanel.add(tfNgay);
    infoPanel.add(new JLabel("Đọc giả:"));
    infoPanel.add(tfDocGia);
    infoPanel.add(new JLabel("Tổng tiền phạt:"));
    infoPanel.add(tfTong);
    infoPanel.add(new JLabel("Lý do:"));
    infoPanel.add(tfLyDo);
    infoPanel.add(new JLabel("Trạng thái:"));
    infoPanel.add(tfTrangThai);

    DefaultTableModel tableModel = new DefaultTableModel(
        new String[] { "Tên sách", "Mã cuốn", "Mức phạt", "Loại", "Số ngày trễ", "Tiền phạt (đ)" }, 0) {
      @Override
      public boolean isCellEditable(int r, int c) {
        return false;
      }
    };

    PhieuPhatBUS bus = new PhieuPhatBUS();
    List<ChiTietPhieuPhat> chiTiet = bus.getChiTiet(pp.getIdPhieuPhat());
    for (ChiTietPhieuPhat ct : chiTiet) {
      String tenSach = ct.getChiTietPhieuMuon() != null && ct.getChiTietPhieuMuon().getCuonSach() != null
          && ct.getChiTietPhieuMuon().getCuonSach().getSach() != null
              ? ct.getChiTietPhieuMuon().getCuonSach().getSach().getTenSach()
              : "";
      String maCuon = ct.getChiTietPhieuMuon() != null && ct.getChiTietPhieuMuon().getCuonSach() != null
          ? ct.getChiTietPhieuMuon().getCuonSach().getMaCuonSach()
          : "";
      tableModel.addRow(new Object[] {
          tenSach, maCuon,
          ct.getMucPhat() != null ? ct.getMucPhat().getTenMucPhat() : "",
          ct.getMucPhat() != null ? toViLoaiPhat(ct.getMucPhat().getLoaiPhat()) : "",
          ct.getSoNgayTreHan(),
          ct.getTienPhatTra() != null ? ct.getTienPhatTra().toPlainString() : "0"
      });
    }

    JButton btnXuatPDF = new JButton("Xuất PDF");
    btnXuatPDF.addActionListener((ActionEvent e) -> xuatPDF(pp, chiTiet));
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
      case "ChuaThu" -> "Chưa thu";
      case "DaThu" -> "Đã thu";
      case "DaHuy" -> "Đã hủy";
      default -> tt;
    };
  }

  private String toViLoaiPhat(String loai) {
    if (loai == null)
      return "";
    return switch (loai) {
      case "PerDay" -> "Theo ngày";
      case "Fixed" -> "Cố định";
      default -> loai;
    };
  }

  private void xuatPDF(PhieuPhat pp, List<ChiTietPhieuPhat> chiTiet) {
    JFileChooser fc = new JFileChooser();
    fc.setSelectedFile(new File("PhieuPhat_" + pp.getIdPhieuPhat() + ".txt"));
    if (fc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION)
      return;
    try (PrintWriter pw = new PrintWriter(new FileWriter(fc.getSelectedFile()))) {
      pw.println("=== CHỨNG NHẬN THANH TOÁN PHẠT ===");
      pw.println("Mã phiếu phạt: #" + pp.getIdPhieuPhat());
      pw.println("Đọc giả: " + (pp.getTenDocGia() != null ? pp.getTenDocGia() : ""));
      pw.println("Ngày lập: " + pp.getNgayLap());
      pw.println(
          "Tổng tiền: " + (pp.getTienPhatPhaiNop() != null ? pp.getTienPhatPhaiNop().toPlainString() + " đ" : "0 đ"));
      pw.println("Lý do: " + pp.getLyDoPhat());
      pw.println("Trạng thái: " + toViTrangThai(pp.getTrangThai()));
      pw.println("-----------------------------------");
      pw.println("Chi tiết phạt:");
      for (ChiTietPhieuPhat ct : chiTiet) {
        String tenSach = ct.getChiTietPhieuMuon() != null && ct.getChiTietPhieuMuon().getCuonSach() != null
            && ct.getChiTietPhieuMuon().getCuonSach().getSach() != null
                ? ct.getChiTietPhieuMuon().getCuonSach().getSach().getTenSach()
                : "";
        pw.printf("  - %s | Mức: %s | Loại: %s | Ngày trễ: %d | Tiền: %s đ%n",
            tenSach,
            ct.getMucPhat() != null ? ct.getMucPhat().getTenMucPhat() : "",
            ct.getMucPhat() != null ? toViLoaiPhat(ct.getMucPhat().getLoaiPhat()) : "",
            ct.getSoNgayTreHan() != null ? ct.getSoNgayTreHan() : 0,
            ct.getTienPhatTra() != null ? ct.getTienPhatTra().toPlainString() : "0");
      }
      pw.println("=== HẾT ===");
      JOptionPane.showMessageDialog(this, "Xuất thành công: " + fc.getSelectedFile().getAbsolutePath());
    } catch (IOException ex) {
      JOptionPane.showMessageDialog(this, "Lỗi xuất file: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
  }
}
