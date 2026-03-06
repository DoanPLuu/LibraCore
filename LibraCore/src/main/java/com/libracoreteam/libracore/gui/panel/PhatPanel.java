package com.libracoreteam.libracore.gui.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;

import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

import com.libracoreteam.libracore.bus.PhieuPhatBUS;
import com.libracoreteam.libracore.gui.dialog.ChiTietPhieuPhatDialog;
import com.libracoreteam.libracore.model.PhieuPhat;

public class PhatPanel extends JPanel {

  private final PhieuPhatBUS bus = new PhieuPhatBUS();
  private final int iconSize = 16;

  private final DefaultTableModel tableModel = new DefaultTableModel(
      new String[] { "Mã PP", "Độc giả", "Ngày lập", "Tổng tiền phạt", "Lý do", "Trạng thái", "Thanh toán" }, 0) {
    @Override
    public boolean isCellEditable(int r, int c) {
      return c == 6;
    }
  };

  private final JTable table = new JTable(tableModel);
  private final JTextField tfSearch = new JTextField(18);
  private final JComboBox<String> cbFilter = new JComboBox<>(new String[] { "Tất cả", "Chưa thu", "Đã thu", "Đã hủy" });
  private List<PhieuPhat> currentData;

  public PhatPanel() {
    setLayout(new BorderLayout(6, 10));
    setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
    add(buildTopBar(), BorderLayout.NORTH);
    add(buildTable(), BorderLayout.CENTER);
    loadData();
  }

  private JPanel buildTopBar() {
    JButton btnHuy = new JButton("Hủy");
    btnHuy.setIcon(FontIcon.of(FontAwesomeSolid.TRASH, iconSize, new Color(220, 53, 69)));
    btnHuy.setPreferredSize(new Dimension(90, 40));
    btnHuy.addActionListener(e -> onHuy());

    JButton btnChiTiet = new JButton("Chi tiết");
    btnChiTiet.setIcon(FontIcon.of(FontAwesomeSolid.INFO_CIRCLE, iconSize, new Color(0, 179, 255)));
    btnChiTiet.setPreferredSize(new Dimension(110, 40));
    btnChiTiet.addActionListener(e -> onChiTiet());

    JButton btnLamMoi = new JButton();
    btnLamMoi.setIcon(FontIcon.of(FontAwesomeSolid.SYNC_ALT, iconSize, new Color(100, 100, 100)));
    btnLamMoi.setPreferredSize(new Dimension(40, 40));
    btnLamMoi.addActionListener(e -> {
      tfSearch.setText("");
      cbFilter.setSelectedIndex(0);
      loadData();
    });

    JButton btnTim = new JButton();
    btnTim.setPreferredSize(new Dimension(40, 40));
    btnTim.setIcon(FontIcon.of(FontAwesomeSolid.SEARCH, iconSize, new Color(100, 100, 100)));
    btnTim.addActionListener(e -> loadData());

    cbFilter.setPreferredSize(new Dimension(130, 40));
    cbFilter.addActionListener(e -> loadData());

    tfSearch.setPreferredSize(new Dimension(150, 40));
    tfSearch.putClientProperty("JTextField.placeholderText", "Tìm kiếm");
    tfSearch.addActionListener(e -> loadData());

    JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 10));
    leftPanel.add(tfSearch);
    leftPanel.add(btnTim);
    leftPanel.add(cbFilter);
    leftPanel.add(btnLamMoi);

    JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 10));
    rightPanel.add(btnChiTiet);
    rightPanel.add(btnHuy);

    JPanel topBar = new JPanel(new BorderLayout(0, 10));
    topBar.add(leftPanel, BorderLayout.WEST);
    topBar.add(rightPanel, BorderLayout.EAST);
    return topBar;
  }

  private JScrollPane buildTable() {
    table.setRowHeight(28);
    table.getColumnModel().getColumn(0).setMaxWidth(60);
    table.getColumnModel().getColumn(5).setMaxWidth(100);
    table.getColumnModel().getColumn(6).setPreferredWidth(130);
    table.getColumnModel().getColumn(6).setMinWidth(120);

    table.getColumn("Thanh toán").setCellRenderer(new ThanhToanRenderer());
    table.getColumn("Thanh toán").setCellEditor(new ThanhToanEditor(new JCheckBox(), this));
    return new JScrollPane(table);
  }

  private String mapFilterToStatus(String selected) {
    return switch (selected) {
      case "Chưa thu" -> "ChuaThu";
      case "Đã thu" -> "DaThu";
      case "Đã hủy" -> "DaHuy";
      default -> null;
    };
  }

  private static String toViTrangThai(String tt) {
    if (tt == null)
      return "";
    return switch (tt) {
      case "ChuaThu" -> "Chưa thu";
      case "DaThu" -> "Đã thu";
      case "DaHuy" -> "Đã hủy";
      default -> tt;
    };
  }

  public void loadData() {
    String kw = tfSearch.getText().trim();
    String filterItem = (String) cbFilter.getSelectedItem();
    String trangThai = mapFilterToStatus(filterItem);

    currentData = bus.searchWithFilter(kw.isEmpty() ? null : kw, trangThai);

    if ("Tất cả".equals(filterItem)) {
      currentData = currentData.stream()
          .filter(pp -> !"DaHuy".equals(pp.getTrangThai()))
          .toList();
    }

    tableModel.setRowCount(0);
    for (PhieuPhat pp : currentData) {
      tableModel.addRow(new Object[] {
          pp.getIdPhieuPhat(),
          pp.getTenDocGia() != null ? pp.getTenDocGia() : "",
          pp.getNgayLap(),
          pp.getTienPhatPhaiNop() != null ? pp.getTienPhatPhaiNop().toPlainString() + " đ" : "0",
          pp.getLyDoPhat(),
          toViTrangThai(pp.getTrangThai()),
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
    int confirm = JOptionPane.showConfirmDialog(this,
        "Hủy phiếu phạt #" + pp.getIdPhieuPhat() + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
    if (confirm != JOptionPane.YES_OPTION)
      return;
    int confirm2 = JOptionPane.showConfirmDialog(this,
        "Bạn chắc chắn muốn hủy phiếu này không?", "Xác nhận lần 2", JOptionPane.YES_NO_OPTION,
        JOptionPane.WARNING_MESSAGE);
    if (confirm2 != JOptionPane.YES_OPTION)
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
    private final JButton btn = new JButton("Thu phạt");
    private final JLabel lblXong = new JLabel("✓ Đã thu", SwingConstants.CENTER);
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
    private Object currentValue;

    ThanhToanEditor(JCheckBox cb, PhatPanel panel) {
      super(cb);
      this.panel = panel;
      setClickCountToStart(1);
    }

    @Override
    public Component getTableCellEditorComponent(JTable t, Object value, boolean isSelected, int row, int col) {
      this.editRow = row;
      this.currentValue = value;
      if (!"ChuaThu".equals(value)) {
        fireEditingStopped();
        return new JLabel("DaHuy".equals(value) ? "Đã hủy" : "✓ Đã thu", SwingConstants.CENTER);
      }
      JButton btn = new JButton("Thu phạt");
      btn.addActionListener(e -> {
        fireEditingStopped();
        panel.onThanhToan(editRow);
      });
      return btn;
    }

    @Override
    public Object getCellEditorValue() {
      return currentValue;
    }
  }
}
