package com.libracoreteam.libracore.gui.dialog;

import com.libracoreteam.libracore.bus.PhieuMuonBUS;
import com.libracoreteam.libracore.model.CuonSach;
import com.libracoreteam.libracore.util.UserSession;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ThemPhieuMuonDialog extends JDialog {

  private static final int MOCK_ID_THE_THANH_VIEN = 0;
  private static final String MOCK_TEN_DOC_GIA = "Nguyễn Văn A (giả lập)";

  private final PhieuMuonBUS phieuMuonBUS = new PhieuMuonBUS();

  private final JSpinner spNgayMuon = new JSpinner(new SpinnerDateModel());
  private final JSpinner spNgayHenTra = new JSpinner(new SpinnerDateModel());
  private final DefaultTableModel tableModel = new DefaultTableModel(
      new String[] { "Chọn", "Mã cuốn", "Tên sách" }, 0) {
    @Override
    public Class<?> getColumnClass(int col) {
      return col == 0 ? Boolean.class : String.class;
    }

    @Override
    public boolean isCellEditable(int row, int col) {
      return col == 0;
    }
  };
  private final JTable table = new JTable(tableModel);
  private final List<CuonSach> dsCuonSach = new ArrayList<>();

  public ThemPhieuMuonDialog(Frame parent) {
    super(parent, "Thêm phiếu mượn", true);
    initUI();
    loadCuonSach();
    pack();
    setLocationRelativeTo(parent);
  }

  private void initUI() {
    JSpinner.DateEditor editorMuon = new JSpinner.DateEditor(spNgayMuon, "dd/MM/yyyy");
    JSpinner.DateEditor editorHen = new JSpinner.DateEditor(spNgayHenTra, "dd/MM/yyyy");
    spNgayMuon.setEditor(editorMuon);
    spNgayHenTra.setEditor(editorHen);
    spNgayMuon.setValue(new Date());
    spNgayHenTra.setValue(Date.from(LocalDate.now().plusDays(14).atStartOfDay(ZoneId.systemDefault()).toInstant()));

    JPanel topPanel = new JPanel(new GridBagLayout());
    topPanel.setBorder(BorderFactory.createTitledBorder("Thông tin mượn"));
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(4, 6, 4, 6);
    gbc.anchor = GridBagConstraints.WEST;

    addRow(topPanel, gbc, 0, "Nhân viên:", new JLabel(UserSession.getInstance().getTenNhanVien()));
    addRow(topPanel, gbc, 1, "Đọc giả:", new JLabel(MOCK_TEN_DOC_GIA));
    addRow(topPanel, gbc, 2, "Ngày mượn:", spNgayMuon);
    addRow(topPanel, gbc, 3, "Ngày hẹn trả:", spNgayHenTra);

    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setPreferredSize(new Dimension(450, 200));
    JPanel sachPanel = new JPanel(new BorderLayout());
    sachPanel.setBorder(BorderFactory.createTitledBorder("Chọn cuốn sách (sẵn sàng, tình trạng tốt)"));
    sachPanel.add(scrollPane, BorderLayout.CENTER);

    JButton btnThem = new JButton("Thêm phiếu mượn");
    JButton btnHuy = new JButton("Hủy");
    btnThem.addActionListener(e -> onThem());
    btnHuy.addActionListener(e -> dispose());

    JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    btnPanel.add(btnThem);
    btnPanel.add(btnHuy);

    setLayout(new BorderLayout(8, 8));
    add(topPanel, BorderLayout.NORTH);
    add(sachPanel, BorderLayout.CENTER);
    add(btnPanel, BorderLayout.SOUTH);
    getRootPane().setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
  }

  private void addRow(JPanel panel, GridBagConstraints gbc, int row, String label, JComponent comp) {
    gbc.gridx = 0;
    gbc.gridy = row;
    panel.add(new JLabel(label), gbc);
    gbc.gridx = 1;
    panel.add(comp, gbc);
  }

  private void loadCuonSach() {
    dsCuonSach.clear();
    tableModel.setRowCount(0);
    List<CuonSach> list = phieuMuonBUS.getCuonSachRanh();
    dsCuonSach.addAll(list);
    for (CuonSach cs : list) {
      tableModel.addRow(new Object[] { false, cs.getMaCuonSach(),
          cs.getSach() != null ? cs.getSach().getTenSach() : "" });
    }
  }

  private void onThem() {
    List<Integer> selectedIds = new ArrayList<>();
    for (int i = 0; i < tableModel.getRowCount(); i++) {
      if (Boolean.TRUE.equals(tableModel.getValueAt(i, 0))) {
        selectedIds.add(dsCuonSach.get(i).getIdCuonSach());
      }
    }
    try {
      LocalDate ngayMuon = ((Date) spNgayMuon.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
      LocalDate ngayHenTra = ((Date) spNgayHenTra.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
      phieuMuonBUS.addPhieuMuon(
          UserSession.getInstance().getIdTaiKhoan(),
          MOCK_ID_THE_THANH_VIEN,
          ngayMuon, ngayHenTra, selectedIds);
      JOptionPane.showMessageDialog(this, "Tạo phiếu mượn thành công!");
      dispose();
    } catch (Exception ex) {
      JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
  }
}
