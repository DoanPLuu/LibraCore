package com.libracoreteam.libracore.gui.dialog;

import com.libracoreteam.libracore.bus.PhieuMuonBUS;
import com.libracoreteam.libracore.model.ChiTietPhieuMuon;
import com.libracoreteam.libracore.model.PhieuMuon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ChiTietPhieuMuonDialog extends JDialog {

  public ChiTietPhieuMuonDialog(Frame parent, PhieuMuon phieuMuon) {
    super(parent, "Chi tiết phiếu mượn #" + phieuMuon.getIdPhieuMuon(), true);
    initUI(phieuMuon);
    pack();
    setMinimumSize(new Dimension(550, 350));
    setLocationRelativeTo(parent);
  }

  private void initUI(PhieuMuon pm) {
    JTextField tfDocGia = new JTextField(
        pm.getTheThanhVien() != null ? pm.getTheThanhVien().getDocGia().getTenDocGia() : "");
    JTextField tfNgayMuon = new JTextField(pm.getNgayMuon() != null ? pm.getNgayMuon().toString() : "");
    JTextField tfHenTra = new JTextField(pm.getNgayHenTra() != null ? pm.getNgayHenTra().toString() : "");
    JTextField tfTrangThai = new JTextField(pm.getTrangThai());
    tfDocGia.setEditable(false);
    tfNgayMuon.setEditable(false);
    tfHenTra.setEditable(false);
    tfTrangThai.setEditable(false);

    JPanel infoPanel = new JPanel(new GridLayout(4, 2, 6, 4));
    infoPanel.setBorder(BorderFactory.createTitledBorder("Thông tin"));
    infoPanel.add(new JLabel("Đọc giả:"));
    infoPanel.add(tfDocGia);
    infoPanel.add(new JLabel("Ngày mượn:"));
    infoPanel.add(tfNgayMuon);
    infoPanel.add(new JLabel("Hạn trả:"));
    infoPanel.add(tfHenTra);
    infoPanel.add(new JLabel("Trạng thái:"));
    infoPanel.add(tfTrangThai);

    DefaultTableModel tableModel = new DefaultTableModel(
        new String[] { "Mã cuốn", "Tên sách", "Tình trạng trả", "Ngày trả" }, 0) {
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
          ct.getTinhTrangTra(),
          ct.getNgayTra() != null ? ct.getNgayTra().toString() : "Chưa trả"
      });
    }

    JButton btnDong = new JButton("Đóng");
    btnDong.addActionListener(e -> dispose());

    setLayout(new BorderLayout(6, 6));
    add(infoPanel, BorderLayout.NORTH);
    add(new JScrollPane(new JTable(tableModel)), BorderLayout.CENTER);
    JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    btnPanel.add(btnDong);
    add(btnPanel, BorderLayout.SOUTH);
    getRootPane().setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
  }
}
