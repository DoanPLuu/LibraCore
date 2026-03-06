package com.libracoreteam.libracore.gui.dialog;

import com.libracoreteam.libracore.bus.PhieuPhatBUS;
import com.libracoreteam.libracore.model.ChiTietPhieuPhat;
import com.libracoreteam.libracore.model.PhieuPhat;
import com.libracoreteam.libracore.util.PdfExportUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ChiTietPhieuPhatDialog extends JDialog {

  private Frame parentFrame;

  public ChiTietPhieuPhatDialog(Frame parent, PhieuPhat phieuPhat) {
    super(parent, "Chi tiết phiếu phạt #" + phieuPhat.getIdPhieuPhat(), true);
    this.parentFrame = parent;
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
    infoPanel.add(new JLabel("Độc giả:"));
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

    List<ChiTietPhieuPhat> chiTiet = new PhieuPhatBUS().getChiTiet(pp.getIdPhieuPhat());
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

    JTable table = new JTable(tableModel);
    table.getColumnModel().getColumn(0).setPreferredWidth(180);
    table.getColumnModel().getColumn(1).setPreferredWidth(100);
    table.getColumnModel().getColumn(2).setPreferredWidth(120);
    table.getColumnModel().getColumn(3).setPreferredWidth(100);
    table.getColumnModel().getColumn(4).setPreferredWidth(100);
    table.getColumnModel().getColumn(5).setPreferredWidth(120);
    table.setDefaultEditor(Object.class, null);
    table.setRowHeight(25);

    JButton btnXuatPDF = new JButton("Xuất PDF");
    btnXuatPDF.setBackground(new Color(0xC6, 0x28, 0x28));
    btnXuatPDF.setForeground(Color.WHITE);
    btnXuatPDF.setFocusPainted(false);
    btnXuatPDF.addActionListener(e -> {
      String title = "Phiếu Phạt #" + pp.getIdPhieuPhat();
      String subtitle = "Đọc giả: " + (pp.getTenDocGia() != null ? pp.getTenDocGia() : "")
          + "  |  Ngày: " + pp.getNgayLap()
          + "  |  Tổng: " + (pp.getTienPhatPhaiNop() != null ? pp.getTienPhatPhaiNop().toPlainString() : "0") + " đ"
          + "  |  Trạng thái: " + toViTrangThai(pp.getTrangThai());
      String[] cols = { "Tên sách", "Mã cuốn", "Mức phạt", "Loại", "Số ngày trễ", "Tiền phạt (đ)" };
      PdfExportUtil.export(parentFrame, title, subtitle, cols,
          PdfExportUtil.fromTableModel(tableModel), "PhieuPhat_" + pp.getIdPhieuPhat() + ".pdf");
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
}
