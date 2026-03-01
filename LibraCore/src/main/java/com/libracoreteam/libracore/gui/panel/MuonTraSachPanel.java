package com.libracoreteam.libracore.gui.panel;

import com.libracoreteam.libracore.bus.PhieuMuonBUS;
import com.libracoreteam.libracore.gui.dialog.ChiTietPhieuMuonDialog;
import com.libracoreteam.libracore.gui.dialog.ThemPhieuMuonDialog;
import com.libracoreteam.libracore.gui.dialog.TraSachDialog;
import com.libracoreteam.libracore.model.PhieuMuon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MuonTraSachPanel extends JPanel {

  private final PhieuMuonBUS bus = new PhieuMuonBUS();

  private final DefaultTableModel tableModel = new DefaultTableModel(
      new String[] { "Mã PM", "Đọc giả", "Ngày mượn", "Hạn trả", "Trạng thái", "Số sách", "Thao tác" }, 0) {
    @Override
    public boolean isCellEditable(int r, int c) {
      return c == 6;
    }
  };

  private final JTable table = new JTable(tableModel);
  private final JTextField tfSearch = new JTextField(18);
  private final JCheckBox cbHienHuy = new JCheckBox("Hiện đã hủy");
  private List<PhieuMuon> currentData;

  public MuonTraSachPanel() {
    setLayout(new BorderLayout(6, 6));
    setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    add(buildTopBar(), BorderLayout.NORTH);
    add(buildTable(), BorderLayout.CENTER);
    loadData();
  }

  private JPanel buildTopBar() {
    JButton btnThem = new JButton("+ Thêm phiếu mượn");
    JButton btnHuy = new JButton("Hủy phiếu");
    JButton btnChiTiet = new JButton("Chi tiết");
    JButton btnLamMoi = new JButton("Làm mới");
    JButton btnTimKiem = new JButton("Tìm");

    btnThem.addActionListener(e -> {
      new ThemPhieuMuonDialog(getParentFrame()).setVisible(true);
      loadData();
    });
    btnHuy.addActionListener(e -> onHuyPhieu());
    btnChiTiet.addActionListener(e -> onChiTiet());
    btnLamMoi.addActionListener(e -> {
      tfSearch.setText("");
      cbHienHuy.setSelected(false);
      loadData();
    });
    btnTimKiem.addActionListener(e -> loadData());
    cbHienHuy.addActionListener(e -> loadData());
    tfSearch.addActionListener(e -> loadData());

    JPanel bar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
    bar.add(btnThem);
    bar.add(btnHuy);
    bar.add(btnChiTiet);
    bar.add(btnLamMoi);
    bar.add(new JSeparator(SwingConstants.VERTICAL));
    bar.add(new JLabel("Tìm:"));
    bar.add(tfSearch);
    bar.add(btnTimKiem);
    bar.add(cbHienHuy);
    return bar;
  }

  private JScrollPane buildTable() {
    table.setRowHeight(28);
    table.getColumnModel().getColumn(0).setMaxWidth(60);
    table.getColumnModel().getColumn(5).setMaxWidth(70);
    table.getColumnModel().getColumn(6).setMaxWidth(90);

    table.getColumn("Thao tác").setCellRenderer(new TraButtonRenderer());
    table.getColumn("Thao tác").setCellEditor(new TraButtonEditor(new JCheckBox(), table, this));
    return new JScrollPane(table);
  }

  public void loadData() {
    String kw = tfSearch.getText().trim();
    String filter = cbHienHuy.isSelected() ? "DaHuy" : null;
    currentData = bus.search(kw.isEmpty() ? null : kw, filter);
    tableModel.setRowCount(0);
    for (PhieuMuon pm : currentData) {
      String docGia = pm.getTheThanhVien() != null && pm.getTheThanhVien().getDocGia() != null
          ? pm.getTheThanhVien().getDocGia().getTenDocGia()
          : "";
      tableModel.addRow(new Object[] {
          pm.getIdPhieuMuon(), docGia,
          pm.getNgayMuon(), pm.getNgayHenTra(),
          pm.getTrangThai(), pm.getTongSoSachMuon(),
          pm.getTrangThai()
      });
    }
  }

  public void onTraSach(int viewRow) {
    PhieuMuon pm = currentData.get(viewRow);
    if (!"DangMuon".equals(pm.getTrangThai())) {
      JOptionPane.showMessageDialog(this, "Phiếu này không ở trạng thái đang mượn.");
      return;
    }
    new TraSachDialog(getParentFrame(), pm).setVisible(true);
    loadData();
  }

  private void onHuyPhieu() {
    int row = table.getSelectedRow();
    if (row < 0) {
      JOptionPane.showMessageDialog(this, "Chọn phiếu cần hủy");
      return;
    }
    PhieuMuon pm = currentData.get(row);
    int confirm = JOptionPane.showConfirmDialog(this, "Hủy phiếu mượn #" + pm.getIdPhieuMuon() + "?", "Xác nhận",
        JOptionPane.YES_NO_OPTION);
    if (confirm != JOptionPane.YES_OPTION)
      return;
    try {
      bus.huyPhieuMuon(pm.getIdPhieuMuon());
      loadData();
    } catch (Exception ex) {
      JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
  }

  private void onChiTiet() {
    int row = table.getSelectedRow();
    if (row < 0) {
      JOptionPane.showMessageDialog(this, "Chọn phiếu cần xem");
      return;
    }
    PhieuMuon pm = bus.getById(currentData.get(row).getIdPhieuMuon());
    new ChiTietPhieuMuonDialog(getParentFrame(), pm).setVisible(true);
  }

  private Frame getParentFrame() {
    return (Frame) SwingUtilities.getWindowAncestor(this);
  }

  private class TraButtonRenderer implements javax.swing.table.TableCellRenderer {
    private final JButton btn = new JButton("Trả sách");
    private final JLabel lbl = new JLabel("Đã xong");

    @Override
    public Component getTableCellRendererComponent(JTable t, Object v, boolean sel, boolean focus, int r, int c) {
      return "DangMuon".equals(v) ? btn : lbl;
    }
  }

  private class TraButtonEditor extends DefaultCellEditor {
    private final MuonTraSachPanel panel;
    private int editRow;

    TraButtonEditor(JCheckBox cb, JTable t, MuonTraSachPanel panel) {
      super(cb);
      this.panel = panel;
      setClickCountToStart(1);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
      this.editRow = row;
      if (!"DangMuon".equals(value)) {
        fireEditingStopped();
        return new JLabel("Đã xong");
      }
      JButton btn = new JButton("Trả sách");
      btn.addActionListener(e -> {
        fireEditingStopped();
        panel.onTraSach(editRow);
      });
      return btn;
    }

    @Override
    public Object getCellEditorValue() {
      return "";
    }
  }
}
