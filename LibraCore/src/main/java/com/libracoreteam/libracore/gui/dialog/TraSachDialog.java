package com.libracoreteam.libracore.gui.dialog;

import com.libracoreteam.libracore.bus.PhieuMuonBUS;
import com.libracoreteam.libracore.dao.MucPhatDAO;
import com.libracoreteam.libracore.model.ChiTietPhieuMuon;
import com.libracoreteam.libracore.model.MucPhat;
import com.libracoreteam.libracore.model.PhieuMuon;
import com.libracoreteam.libracore.util.UserSession;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.List;

public class TraSachDialog extends JDialog {

  private static final int COL_CHON = 0;
  private static final int COL_MA = 1;
  private static final int COL_TINH_TRANG = 3;
  private static final int COL_MUC_PHAT = 4;
  private static final int COL_PHAT_TRE = 5;
  private static final int COL_TONG_PHAT = 6;

  private final PhieuMuonBUS phieuMuonBUS;
  private final PhieuMuon phieuMuon;
  private final List<MucPhat> dsMucPhatFixed;
  private final MucPhat mucPhatPerDay;
  private final List<ChiTietPhieuMuon> dsChiTiet;

  private DefaultTableModel tableModel;
  private JTable table;

  public TraSachDialog(Frame parent, PhieuMuon phieuMuon) {
    super(parent, "Trả sách - Phiếu #" + phieuMuon.getIdPhieuMuon(), true);
    this.phieuMuon = phieuMuon;
    this.phieuMuonBUS = new PhieuMuonBUS();
    MucPhatDAO mucPhatDAO = new MucPhatDAO();
    this.dsMucPhatFixed = mucPhatDAO.getAllFixedActive();
    this.mucPhatPerDay = mucPhatDAO.getPerDayActive();
    this.dsChiTiet = phieuMuonBUS.getChiTiet(phieuMuon.getIdPhieuMuon());
    initUI();
    pack();
    setMinimumSize(new Dimension(750, 420));
    setLocationRelativeTo(parent);
  }

  private void initUI() {
    JTextField tfDocGia = new JTextField(phieuMuon.getTheThanhVien() != null
        ? phieuMuon.getTheThanhVien().getDocGia().getTenDocGia()
        : "");
    JTextField tfNgayMuon = new JTextField(phieuMuon.getNgayMuon() != null ? phieuMuon.getNgayMuon().toString() : "");
    JTextField tfHenTra = new JTextField(phieuMuon.getNgayHenTra() != null ? phieuMuon.getNgayHenTra().toString() : "");
    tfDocGia.setEditable(false);
    tfNgayMuon.setEditable(false);
    tfHenTra.setEditable(false);

    JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 4));
    infoPanel.setBorder(BorderFactory.createTitledBorder("Thông tin phiếu mượn"));
    infoPanel.add(new JLabel("Đọc giả:"));
    infoPanel.add(tfDocGia);
    infoPanel.add(new JLabel("Ngày mượn:"));
    infoPanel.add(tfNgayMuon);
    infoPanel.add(new JLabel("Hạn trả:"));
    infoPanel.add(tfHenTra);

    tableModel = buildTableModel();
    table = new JTable(tableModel);
    setupTableColumns();
    loadDuLieu();

    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setPreferredSize(new Dimension(700, 250));

    JButton btnXacNhan = new JButton("Xác nhận trả");
    JButton btnDong = new JButton("Đóng");
    btnXacNhan.addActionListener(e -> onXacNhan());
    btnDong.addActionListener(e -> dispose());

    JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    btnPanel.add(btnXacNhan);
    btnPanel.add(btnDong);

    setLayout(new BorderLayout(6, 6));
    add(infoPanel, BorderLayout.NORTH);
    add(scrollPane, BorderLayout.CENTER);
    add(btnPanel, BorderLayout.SOUTH);
    getRootPane().setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
  }

  private DefaultTableModel buildTableModel() {
    return new DefaultTableModel(
        new String[] { "Trả", "Mã cuốn", "Tên sách", "Tình trạng trả", "Mức phạt cố định", "Phạt trễ (VNĐ)",
            "Tổng phạt (VNĐ)" },
        0) {
      @Override
      public Class<?> getColumnClass(int col) {
        return col == COL_CHON ? Boolean.class : String.class;
      }

      @Override
      public boolean isCellEditable(int row, int col) {
        if (col == COL_MUC_PHAT) {
          String tt = (String) getValueAt(row, COL_TINH_TRANG);
          return !"Tot".equals(tt);
        }
        return col == COL_CHON || col == COL_TINH_TRANG;
      }

      @Override
      public void setValueAt(Object value, int row, int col) {
        super.setValueAt(value, row, col);
        if (col == COL_TINH_TRANG) {
          if ("Tot".equals(value))
            super.setValueAt(null, row, COL_MUC_PHAT);
          recalculate(row);
        }
        if (col == COL_MUC_PHAT) {
          recalculate(row);
        }
        fireTableRowsUpdated(row, row);
      }
    };
  }

  private void setupTableColumns() {
    String[] tinhTrangOptions = { "Tot", "Hong", "Mat" };
    JComboBox<String> cbTinhTrang = new JComboBox<>(tinhTrangOptions);
    table.getColumnModel().getColumn(COL_TINH_TRANG).setCellEditor(new DefaultCellEditor(cbTinhTrang));

    String[] mucPhatItems = buildMucPhatItems();
    JComboBox<String> cbMucPhat = new JComboBox<>(mucPhatItems);
    table.getColumnModel().getColumn(COL_MUC_PHAT).setCellEditor(new DefaultCellEditor(cbMucPhat));

    table.getColumnModel().getColumn(COL_CHON).setMaxWidth(50);
    table.getColumnModel().getColumn(COL_MA).setMaxWidth(100);
    table.getRowHeight(24);
  }

  private String[] buildMucPhatItems() {
    String[] items = new String[dsMucPhatFixed.size() + 1];
    items[0] = "";
    for (int i = 0; i < dsMucPhatFixed.size(); i++) {
      MucPhat mp = dsMucPhatFixed.get(i);
      items[i + 1] = mp.getTenMucPhat() + " (" + mp.getSoTienPhat().toPlainString() + " đ)";
    }
    return items;
  }

  private void loadDuLieu() {
    tableModel.setRowCount(0);
    LocalDate ngayHenTra = phieuMuon.getNgayHenTra();
    LocalDate homNay = LocalDate.now();
    for (ChiTietPhieuMuon ct : dsChiTiet) {
      if (!"ChuaTra".equals(ct.getTinhTrangTra()))
        continue;
      long soNgayTre = ngayHenTra != null ? Math.max(0, ChronoUnit.DAYS.between(ngayHenTra, homNay)) : 0;
      BigDecimal phatTre = soNgayTre > 0 && mucPhatPerDay != null
          ? mucPhatPerDay.getSoTienPhat().multiply(BigDecimal.valueOf(soNgayTre))
          : BigDecimal.ZERO;
      tableModel.addRow(new Object[] {
          false,
          ct.getCuonSach() != null ? ct.getCuonSach().getMaCuonSach() : "",
          ct.getCuonSach() != null && ct.getCuonSach().getSach() != null ? ct.getCuonSach().getSach().getTenSach() : "",
          "Tot",
          null,
          phatTre.toPlainString(),
          phatTre.toPlainString()
      });
    }
  }

  private void recalculate(int row) {
    String tt = (String) tableModel.getValueAt(row, COL_TINH_TRANG);
    BigDecimal phatTre;
    try {
      phatTre = new BigDecimal((String) tableModel.getValueAt(row, COL_PHAT_TRE));
    } catch (Exception e) {
      phatTre = BigDecimal.ZERO;
    }
    BigDecimal phatFixed = BigDecimal.ZERO;
    if (!"Tot".equals(tt)) {
      String mucPhatStr = (String) tableModel.getValueAt(row, COL_MUC_PHAT);
      if (mucPhatStr != null && !mucPhatStr.isEmpty()) {
        int idx = findMucPhatIndex(mucPhatStr);
        if (idx >= 0)
          phatFixed = dsMucPhatFixed.get(idx).getSoTienPhat();
      }
    }
    tableModel.setValueAt(phatTre.add(phatFixed).toPlainString(), row, COL_TONG_PHAT);
  }

  private int findMucPhatIndex(String displayStr) {
    for (int i = 0; i < dsMucPhatFixed.size(); i++) {
      MucPhat mp = dsMucPhatFixed.get(i);
      if (displayStr.startsWith(mp.getTenMucPhat()))
        return i;
    }
    return -1;
  }

  private void onXacNhan() {
    if (table.isEditing())
      table.getCellEditor().stopCellEditing();

    List<PhieuMuonBUS.TraSachItem> items = new ArrayList<>();
    List<ChiTietPhieuMuon> chuaTra = dsChiTiet.stream()
        .filter(ct -> "ChuaTra".equals(ct.getTinhTrangTra())).toList();

    for (int i = 0; i < tableModel.getRowCount(); i++) {
      if (!Boolean.TRUE.equals(tableModel.getValueAt(i, COL_CHON)))
        continue;
      ChiTietPhieuMuon ct = chuaTra.get(i);
      String tinhTrang = (String) tableModel.getValueAt(i, COL_TINH_TRANG);
      Integer idMucPhatFixed = null;
      if (!"Tot".equals(tinhTrang)) {
        String mucPhatStr = (String) tableModel.getValueAt(i, COL_MUC_PHAT);
        if (mucPhatStr == null || mucPhatStr.isEmpty()) {
          JOptionPane.showMessageDialog(this, "Sách hư/mất phải chọn mức phạt cố định!", "Lỗi",
              JOptionPane.ERROR_MESSAGE);
          return;
        }
        int idx = findMucPhatIndex(mucPhatStr);
        if (idx >= 0)
          idMucPhatFixed = dsMucPhatFixed.get(idx).getIdMucPhat();
      }
      items
          .add(new PhieuMuonBUS.TraSachItem(ct.getIdChiTietPhieuMuon(), ct.getIdCuonSach(), tinhTrang, idMucPhatFixed));
    }

    if (items.isEmpty()) {
      JOptionPane.showMessageDialog(this, "Chưa chọn sách để trả!", "Lỗi", JOptionPane.ERROR_MESSAGE);
      return;
    }

    try {
      phieuMuonBUS.traSachBulk(phieuMuon.getIdPhieuMuon(), items, UserSession.getInstance().getIdTaiKhoan());
      JOptionPane.showMessageDialog(this, "Trả sách thành công!");
      dispose();
    } catch (Exception ex) {
      JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
  }
}
