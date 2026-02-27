package com.libracoreteam.libracore.gui.dialog;

import com.libracoreteam.libracore.bus.PhieuPhatBUS;
import com.libracoreteam.libracore.model.ChiTietPhieuPhat;
import com.libracoreteam.libracore.model.PhieuPhat;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ChiTietPhieuPhatDialog extends JDialog {

  public ChiTietPhieuPhatDialog(Frame parent, PhieuPhat phieuPhat) {
    super(parent, "Chi tiết phiếu phạt #" + phieuPhat.getIdPhieuPhat(), true);
    initUI(phieuPhat);
    pack();
    setMinimumSize(new Dimension(620, 380));
    setLocationRelativeTo(parent);
  }

  private void initUI(PhieuPhat pp) {
    JTextField tfNgay = new JTextField(pp.getNgayLap() != null ? pp.getNgayLap().toString() : "");
    JTextField tfTong = new JTextField(
        pp.getTienPhatPhaiNop() != null ? pp.getTienPhatPhaiNop().toPlainString() + " đ" : "");
    JTextField tfLyDo = new JTextField(pp.getLyDoPhat());
    JTextField tfTrangThai = new JTextField(pp.getTrangThai());
    tfNgay.setEditable(false);
    tfTong.setEditable(false);
    tfLyDo.setEditable(false);
    tfTrangThai.setEditable(false);

    JPanel infoPanel = new JPanel(new GridLayout(4, 2, 6, 4));
    infoPanel.setBorder(BorderFactory.createTitledBorder("Thông tin phiếu phạt"));
    infoPanel.add(new JLabel("Ngày lập:"));
    infoPanel.add(tfNgay);
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
          ct.getMucPhat() != null ? ct.getMucPhat().getLoaiPhat() : "",
          ct.getSoNgayTreHan(),
          ct.getTienPhatTra() != null ? ct.getTienPhatTra().toPlainString() : "0"
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
