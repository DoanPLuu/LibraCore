package com.libracoreteam.libracore.gui.panel;

import com.libracoreteam.libracore.bus.PhieuPhatBUS;
import com.libracoreteam.libracore.gui.dialog.ChiTietPhieuPhatDialog;
import com.libracoreteam.libracore.model.PhieuPhat;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PhatPanel extends JPanel {

  private final PhieuPhatBUS bus = new PhieuPhatBUS();

  private final DefaultTableModel tableModel = new DefaultTableModel(
      new String[] { "Mã PP", "Ngày lập", "Tổng tiền phạt", "Lý do", "Trạng thái", "Thanh toán" }, 0) {
    @Override
    public boolean isCellEditable(int r, int c) {
      return c == 5;
    }
  };

  private final JTable table = new JTable(tableModel);
  private final JTextField tfSearch = new JTextField(15);
  private final JComboBox<String> cbFilter = new JComboBox<>(new String[] { "Tất cả", "ChuaThu", "DaThu", "DaHuy" });
  private List<PhieuPhat> currentData;

  public PhatPanel() {
    setLayout(new BorderLayout(6, 6));
    setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    add(buildTopBar(), BorderLayout.NORTH);
    add(buildTable(), BorderLayout.CENTER);
    loadData();
  }

  private JPanel buildTopBar() {
    JButton btnHuy = new JButton("Hủy phiếu phạt");
    JButton btnChiTiet = new JButton("Chi tiết");
    JButton btnLamMoi = new JButton("Làm mới");
    JButton btnTim = new JButton("Tìm");

    btnHuy.addActionListener(e -> onHuy());
    btnChiTiet.addActionListener(e -> onChiTiet());
    btnLamMoi.addActionListener(e -> {
      tfSearch.setText("");
      cbFilter.setSelectedIndex(0);
      loadData();
    });
    btnTim.addActionListener(e -> loadData());
    cbFilter.addActionListener(e -> loadData());
    tfSearch.addActionListener(e -> loadData());

    JPanel bar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
    bar.add(btnHuy);
    bar.add(btnChiTiet);
    bar.add(btnLamMoi);
    bar.add(new JSeparator(SwingConstants.VERTICAL));
    bar.add(new JLabel("Tìm:"));
    bar.add(tfSearch);
    bar.add(btnTim);
    bar.add(new JLabel("Lọc:"));
    bar.add(cbFilter);
    return bar;
  }

  private JScrollPane buildTable() {
    table.setRowHeight(28);
    table.getColumnModel().getColumn(0).setMaxWidth(60);
    table.getColumnModel().getColumn(4).setMaxWidth(90);
    table.getColumnModel().getColumn(5).setMaxWidth(110);

    table.getColumn("Thanh toán").setCellRenderer(new ThanhToanRenderer());
    table.getColumn("Thanh toán").setCellEditor(new ThanhToanEditor(new JCheckBox(), this));
    return new JScrollPane(table);
  }

  public void loadData() {
    String kw = tfSearch.getText().trim();
    String filter = "Tất cả".equals(cbFilter.getSelectedItem()) ? null : (String) cbFilter.getSelectedItem();
    currentData = bus.search(kw.isEmpty() ? null : kw, filter);
    tableModel.setRowCount(0);
    for (PhieuPhat pp : currentData) {
      tableModel.addRow(new Object[] {
          pp.getIdPhieuPhat(),
          pp.getNgayLap(),
          pp.getTienPhatPhaiNop() != null ? pp.getTienPhatPhaiNop().toPlainString() + " đ" : "0",
          pp.getLyDoPhat(),
          pp.getTrangThai(),
          pp.getTrangThai()
      });
    }
  }

  public void onThanhToan(int viewRow) {
    PhieuPhat pp = currentData.get(viewRow);
    int confirm = JOptionPane.showConfirmDialog(this,
        "Xác nhận thu tiền phạt phiếu #" + pp.getIdPhieuPhat() + "\nSố tiền: " + pp.getTienPhatPhaiNop() + " đ",
        "Xác nhận thanh toán", JOptionPane.YES_NO_OPTION);
    if (confirm != JOptionPane.YES_OPTION)
      return;
    try {
      bus.thanhToan(pp.getIdPhieuPhat());
      loadData();
    } catch (Exception ex) {
      JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
  }

  private void onHuy() {
    int row = table.getSelectedRow();
    if (row < 0) {
      JOptionPane.showMessageDialog(this, "Chọn phiếu phạt cần hủy");
      return;
    }
    PhieuPhat pp = currentData.get(row);
    int confirm = JOptionPane.showConfirmDialog(this, "Hủy phiếu phạt #" + pp.getIdPhieuPhat() + "?", "Xác nhận",
        JOptionPane.YES_NO_OPTION);
    if (confirm != JOptionPane.YES_OPTION)
      return;
    try {
      bus.huy(pp.getIdPhieuPhat());
      loadData();
    } catch (Exception ex) {
      JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
  }

  private void onChiTiet() {
    int row = table.getSelectedRow();
    if (row < 0) {
      JOptionPane.showMessageDialog(this, "Chọn phiếu phạt cần xem");
      return;
    }
    PhieuPhat pp = currentData.get(row);
    new ChiTietPhieuPhatDialog(getParentFrame(), pp).setVisible(true);
  }

  private Frame getParentFrame() {
    return (Frame) SwingUtilities.getWindowAncestor(this);
  }

  private class ThanhToanRenderer implements javax.swing.table.TableCellRenderer {
    private final JButton btn = new JButton("Thanh toán");
    private final JLabel lblXong = new JLabel("✓ Đã xong", SwingConstants.CENTER);
    private final JLabel lblHuy = new JLabel("Đã hủy", SwingConstants.CENTER);

    @Override
    public Component getTableCellRendererComponent(JTable t, Object v, boolean sel, boolean focus, int r, int c) {
      if ("ChuaThu".equals(v))
        return btn;
      if ("DaHuy".equals(v))
        return lblHuy;
      return lblXong;
    }
  }

  private class ThanhToanEditor extends DefaultCellEditor {
    private final PhatPanel panel;
    private int editRow;

    ThanhToanEditor(JCheckBox cb, PhatPanel panel) {
      super(cb);
      this.panel = panel;
      setClickCountToStart(1);
    }

    @Override
    public Component getTableCellEditorComponent(JTable t, Object value, boolean isSelected, int row, int col) {
      this.editRow = row;
      if (!"ChuaThu".equals(value)) {
        fireEditingStopped();
        return new JLabel("DaHuy".equals(value) ? "Đã hủy" : "✓ Đã xong", SwingConstants.CENTER);
      }
      JButton btn = new JButton("Thanh toán");
      btn.addActionListener(e -> {
        fireEditingStopped();
        panel.onThanhToan(editRow);
      });
      return btn;
    }

    @Override
    public Object getCellEditorValue() {
      return "";
    }
  }
}
