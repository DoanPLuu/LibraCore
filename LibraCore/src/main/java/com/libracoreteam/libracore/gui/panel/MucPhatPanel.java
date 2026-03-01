package com.libracoreteam.libracore.gui.panel;

import com.libracoreteam.libracore.bus.MucPhatBUS;
import com.libracoreteam.libracore.model.MucPhat;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

public class MucPhatPanel extends JPanel {

  private final MucPhatBUS bus = new MucPhatBUS();
  private final int iconSize = 16;

  private final DefaultTableModel tableModel = new DefaultTableModel(
      new String[] { "Mã", "Tên mức phạt", "Loại", "Số tiền (đ)", "Mô tả" }, 0) {
    @Override
    public boolean isCellEditable(int r, int c) {
      return false;
    }
  };

  private final JTable table = new JTable(tableModel);
  private List<MucPhat> currentData;

  public MucPhatPanel() {
    setLayout(new BorderLayout(6, 10));
    setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
    add(buildTopBar(), BorderLayout.NORTH);
    add(new JScrollPane(table), BorderLayout.CENTER);
    loadData();
  }

  private JPanel buildTopBar() {
    JButton btnThem = new JButton("Thêm");
    btnThem.setIcon(FontIcon.of(FontAwesomeSolid.PLUS_CIRCLE, iconSize, new Color(21, 110, 71)));
    btnThem.setPreferredSize(new Dimension(90, 40));

    JButton btnSua = new JButton("Sửa");
    btnSua.setIcon(FontIcon.of(FontAwesomeSolid.EDIT, iconSize, new Color(250, 100, 10)));
    btnSua.setPreferredSize(new Dimension(90, 40));

    JButton btnLamMoi = new JButton("");
    btnLamMoi.setIcon(FontIcon.of(FontAwesomeSolid.SYNC_ALT, iconSize, new Color(100, 100, 100)));
    btnLamMoi.setPreferredSize(new Dimension(40, 40));

    btnThem.addActionListener(e -> showForm(null));
    btnSua.addActionListener(e -> {
      int row = table.getSelectedRow();
      if (row < 0) {
        JOptionPane.showMessageDialog(this, "Chọn mức phạt cần sửa");
        return;
      }
      showForm(currentData.get(row));
    });
    btnLamMoi.addActionListener(e -> loadData());

    JPanel bar = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 4));
    bar.add(btnThem);
    bar.add(Box.createHorizontalStrut(8));
    bar.add(btnSua);
    bar.add(Box.createHorizontalStrut(8));
    bar.add(btnLamMoi);
    return bar;
  }

  public void loadData() {
    currentData = bus.getAll();
    tableModel.setRowCount(0);
    for (MucPhat mp : currentData) {
      tableModel.addRow(new Object[] {
          mp.getIdMucPhat(), mp.getTenMucPhat(), mp.getLoaiPhat(),
          mp.getSoTienPhat() != null ? mp.getSoTienPhat().toPlainString() : "0",
          mp.getMoTa()
      });
    }
  }

  private void showForm(MucPhat existing) {
    JTextField tfTen = new JTextField(existing != null ? existing.getTenMucPhat() : "", 20);
    JComboBox<String> cbLoai = new JComboBox<>(new String[] { "PerDay", "Fixed" });
    if (existing != null)
      cbLoai.setSelectedItem(existing.getLoaiPhat());
    JTextField tfSoTien = new JTextField(existing != null ? existing.getSoTienPhat().toPlainString() : "0", 12);
    JTextField tfMoTa = new JTextField(existing != null ? existing.getMoTa() : "", 20);

    JPanel panel = new JPanel(new GridLayout(4, 2, 6, 6));
    panel.add(new JLabel("Tên mức phạt:"));
    panel.add(tfTen);
    panel.add(new JLabel("Loại:"));
    panel.add(cbLoai);
    panel.add(new JLabel("Số tiền (VNĐ):"));
    panel.add(tfSoTien);
    panel.add(new JLabel("Mô tả:"));
    panel.add(tfMoTa);

    String title = existing != null ? "Sửa mức phạt" : "Thêm mức phạt";
    int result = JOptionPane.showConfirmDialog(this, panel, title, JOptionPane.OK_CANCEL_OPTION);
    if (result != JOptionPane.OK_OPTION)
      return;

    try {
      String ten = tfTen.getText().trim();
      if (ten.isEmpty())
        throw new RuntimeException("Tên mức phạt không được trống");
      BigDecimal soTien = new BigDecimal(tfSoTien.getText().trim());

      MucPhat mp = existing != null ? existing : new MucPhat();
      mp.setTenMucPhat(ten);
      mp.setLoaiPhat((String) cbLoai.getSelectedItem());
      mp.setSoTienPhat(soTien);
      mp.setMoTa(tfMoTa.getText().trim());

      if (existing != null)
        bus.update(mp);
      else
        bus.insert(mp);
      loadData();
    } catch (NumberFormatException ex) {
      JOptionPane.showMessageDialog(this, "Số tiền không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
    } catch (Exception ex) {
      JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
  }
}
