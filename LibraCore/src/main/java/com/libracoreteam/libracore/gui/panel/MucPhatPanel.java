package com.libracoreteam.libracore.gui.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

import com.libracoreteam.libracore.bus.MucPhatBUS;
import com.libracoreteam.libracore.model.MucPhat;

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

  private final JComboBox<String> cbFilter = new JComboBox<>(new String[] { "Tất cả", "Cố định", "Theo ngày" });
  private final JCheckBox cbHienAn = new JCheckBox("Hiện mức phạt ẩn");
  private final JButton btnAnHien = new JButton("Ẩn");
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
    btnThem.addActionListener(e -> showForm(null));

    JButton btnSua = new JButton("Sửa");
    btnSua.setIcon(FontIcon.of(FontAwesomeSolid.EDIT, iconSize, new Color(250, 100, 10)));
    btnSua.setPreferredSize(new Dimension(90, 40));
    btnSua.addActionListener((ActionEvent e) -> {
      int row = table.getSelectedRow();
      if (row < 0) {
        JOptionPane.showMessageDialog(this, "Chọn mức phạt cần sửa");
        return;
      }
      showForm(currentData.get(row));
    });

    JButton btnXoa = btnAnHien;
    btnXoa.setIcon(FontIcon.of(FontAwesomeSolid.EYE_SLASH, iconSize, new Color(220, 53, 69)));
    btnXoa.setPreferredSize(new Dimension(90, 40));
    btnXoa.addActionListener(e -> {
      int row = table.getSelectedRow();
      if (row < 0) {
        JOptionPane.showMessageDialog(this, "Chọn mức phạt cần ẩn");
        return;
      }
      MucPhat mp = currentData.get(row);
      boolean dangHoatDong = mp.isHoatDong();
      String msg = dangHoatDong ? "Ẩn mức phạt \"" + mp.getTenMucPhat() + "\"?"
          : "Hiện lại mức phạt \"" + mp.getTenMucPhat() + "\"?";
      int confirm = JOptionPane.showConfirmDialog(this, msg, "Xác nhận", JOptionPane.YES_NO_OPTION);
      if (confirm != JOptionPane.YES_OPTION)
        return;
      try {
        bus.setHoatDong(mp.getIdMucPhat(), !dangHoatDong);
        loadData();
      } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
      }
    });

    JButton btnLamMoi = new JButton();
    btnLamMoi.setIcon(FontIcon.of(FontAwesomeSolid.SYNC_ALT, iconSize, new Color(100, 100, 100)));
    btnLamMoi.setPreferredSize(new Dimension(40, 40));
    btnLamMoi.addActionListener(e -> {
      cbFilter.setSelectedIndex(0);
      cbHienAn.setSelected(false);
      loadData();
    });

    cbFilter.setPreferredSize(new Dimension(110, 40));
    cbFilter.addActionListener(e -> loadData());

    cbHienAn.addActionListener(e -> loadData());

    JPanel leftBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 10));
    leftBar.add(cbFilter);
    leftBar.add(btnLamMoi);
    leftBar.add(cbHienAn);

    JPanel rightBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 10));
    rightBar.add(btnThem);
    rightBar.add(btnSua);
    rightBar.add(btnXoa);

    JPanel bar = new JPanel(new BorderLayout(0, 10));
    bar.add(leftBar, BorderLayout.WEST);
    bar.add(rightBar, BorderLayout.EAST);
    return bar;
  }

  public void loadData() {
    String selected = (String) cbFilter.getSelectedItem();
    String filterLoai = switch (selected) {
      case "Cố định" -> "Fixed";
      case "Theo ngày" -> "PerDay";
      default -> null;
    };
    boolean showAn = cbHienAn.isSelected();

    if (showAn) {
      currentData = bus.getFilteredInactive(filterLoai);
      btnAnHien.setText("Hiện");
    } else {
      currentData = bus.getFiltered(filterLoai);
      btnAnHien.setText("Ẩn");
    }

    tableModel.setRowCount(0);
    for (MucPhat mp : currentData) {
      tableModel.addRow(new Object[] {
          mp.getIdMucPhat(), mp.getTenMucPhat(),
          toViLoai(mp.getLoaiPhat()),
          mp.getSoTienPhat() != null ? mp.getSoTienPhat().toPlainString() : "0",
          mp.getMoTa()
      });
    }

    JButton btnXoa = (JButton) ((JPanel) ((JPanel) getComponent(0)).getComponent(1)).getComponent(2);
    if (showAn) {
      btnXoa.setText("Hiện lại");
    } else {
      btnXoa.setText("Ẩn");
    }
  }

  private String toViLoai(String loai) {
    if (loai == null)
      return "";
    return switch (loai) {
      case "PerDay" -> "Theo ngày";
      case "Fixed" -> "Cố định";
      default -> loai;
    };
  }

  private String fromViLoai(String vi) {
    return switch (vi) {
      case "Theo ngày" -> "PerDay";
      case "Cố định" -> "Fixed";
      default -> vi;
    };
  }

  private void showForm(MucPhat existing) {
    JTextField tfTen = new JTextField(existing != null ? existing.getTenMucPhat() : "", 15);
    JComboBox<String> cbLoai = new JComboBox<>(new String[] { "Theo ngày", "Cố định" });
    if (existing != null)
      cbLoai.setSelectedItem(toViLoai(existing.getLoaiPhat()));
    JTextField tfSoTien = new JTextField(existing != null ? existing.getSoTienPhat().toPlainString() : "0", 15);
    JTextField tfMoTa = new JTextField(existing != null ? existing.getMoTa() : "", 15);

    JPanel panel = new JPanel(new java.awt.GridBagLayout());
    panel.setBorder(BorderFactory.createTitledBorder("Thông tin mức phạt"));
    java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
    gbc.insets = new java.awt.Insets(6, 10, 6, 10);
    gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gbc.anchor = java.awt.GridBagConstraints.WEST;

    gbc.gridx = 0;
    gbc.gridy = 0;
    panel.add(new JLabel("Tên:"), gbc);
    gbc.gridx = 1;
    panel.add(tfTen, gbc);

    gbc.gridx = 0;
    gbc.gridy = 1;
    panel.add(new JLabel("Loại:"), gbc);
    gbc.gridx = 1;
    panel.add(cbLoai, gbc);

    gbc.gridx = 0;
    gbc.gridy = 2;
    panel.add(new JLabel("Số tiền (đ):"), gbc);
    gbc.gridx = 1;
    panel.add(tfSoTien, gbc);

    gbc.gridx = 0;
    gbc.gridy = 3;
    panel.add(new JLabel("Mô tả:"), gbc);
    gbc.gridx = 1;
    panel.add(tfMoTa, gbc);

    String title = existing != null ? "Sửa mức phạt" : "Thêm mức phạt";
    while (true) {
      int result = JOptionPane.showConfirmDialog(this, panel, title, JOptionPane.OK_CANCEL_OPTION,
          JOptionPane.PLAIN_MESSAGE);
      if (result != JOptionPane.OK_OPTION)
        return;
      try {
        String ten = tfTen.getText().trim();
        if (ten.isEmpty())
          throw new RuntimeException("Tên mức phạt không được trống");
        BigDecimal soTien = new BigDecimal(tfSoTien.getText().trim());
        if (soTien.compareTo(BigDecimal.ZERO) <= 0)
          throw new RuntimeException("Số tiền phải > 0");
        MucPhat mp = existing != null ? existing : new MucPhat();
        mp.setTenMucPhat(ten);
        mp.setLoaiPhat(fromViLoai((String) cbLoai.getSelectedItem()));
        mp.setSoTienPhat(soTien);
        mp.setMoTa(tfMoTa.getText().trim());
        if (existing != null)
          bus.update(mp);
        else
          bus.insert(mp);
        loadData();
        return;
      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Số tiền không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
      } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
      }
    }
  }
}
