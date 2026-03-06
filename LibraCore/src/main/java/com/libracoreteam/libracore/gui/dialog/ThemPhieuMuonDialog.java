package com.libracoreteam.libracore.gui.dialog;

import com.libracoreteam.libracore.bus.PhieuMuonBUS;
import com.libracoreteam.libracore.bus.TheThanhVienBUS;
import com.libracoreteam.libracore.model.CuonSach;
import com.libracoreteam.libracore.model.TheThanhVien;
import com.libracoreteam.libracore.util.UserSession;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ThemPhieuMuonDialog extends JDialog {

  private final PhieuMuonBUS phieuMuonBUS = new PhieuMuonBUS();
  // Dùng TheThanhVienBUS để check thẻ chuẩn kiến trúc
  private final TheThanhVienBUS theThanhVienBUS = new TheThanhVienBUS();

  private final JSpinner spNgayMuon = new JSpinner(new SpinnerDateModel());
  private final JSpinner spNgayHenTra = new JSpinner(new SpinnerDateModel());

  // --- UI MỚI: Dùng ComboBox thay cho TextField ---
  private JComboBox<CardItem> cbxIdThe;
  private JTextField txtTenDocGia;
  private int validIdThe = -1;
  private List<CardItem> allCardItems = new ArrayList<>(); // Chứa toàn bộ thẻ để lọc

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
    loadAllCardsToMemory();
    initUI();
    loadCuonSach();
    setMinimumSize(new Dimension(560, 480));
    pack();
    setLocationRelativeTo(parent);
  }

  // Class nội bộ đại diện cho 1 dòng trong Dropdown
  private class CardItem {
    int idThe;
    String tenDG;
    String trangThai;

    public CardItem(int idThe, String tenDG, String trangThai) {
      this.idThe = idThe;
      this.tenDG = tenDG;
      this.trangThai = trangThai;
    }

    // Cực kỳ quan trọng: Override toString để khi chọn, ô Text chỉ hiện Số ID
    @Override
    public String toString() {
      return String.valueOf(idThe);
    }
  }

  // Tải toàn bộ thẻ từ Database lên để làm tính năng Lọc
  private void loadAllCardsToMemory() {
    List<TheThanhVien> list = theThanhVienBUS.getAll();
    for (TheThanhVien t : list) {
      String ten = theThanhVienBUS.getTenDocGia(t.getIdDocGia());
      allCardItems.add(new CardItem(t.getIdTheThanhVien(), ten, t.getTrangThai()));
    }
  }

  private void initUI() {
    JSpinner.DateEditor editorMuon = new JSpinner.DateEditor(spNgayMuon, "dd/MM/yyyy");
    JSpinner.DateEditor editorHen = new JSpinner.DateEditor(spNgayHenTra, "dd/MM/yyyy");
    spNgayMuon.setEditor(editorMuon);
    spNgayHenTra.setEditor(editorHen);
    spNgayMuon.setValue(new Date());
    spNgayHenTra.setValue(Date.from(LocalDate.now().plusDays(14).atStartOfDay(ZoneId.systemDefault()).toInstant()));
    spNgayMuon.setEnabled(false);

    JPanel topPanel = new JPanel(new GridBagLayout());
    topPanel.setBorder(BorderFactory.createTitledBorder("Thông tin mượn"));
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(4, 6, 4, 6);
    gbc.anchor = GridBagConstraints.WEST;

    addRow(topPanel, gbc, 0, "Nhân viên:", new JLabel(UserSession.getInstance().getTenNhanVien()));

    // ==========================================================
    // KHU VỰC CHECK THẺ SIÊU CẤP VIP PRO (COMBOBOX AUTO-COMPLETE)
    // ==========================================================
    gbc.gridx = 0;
    gbc.gridy = 1;
    topPanel.add(new JLabel("Mã Thẻ TV:"), gbc);

    JPanel pnlCheckThe = new JPanel(new BorderLayout(5, 0));

    // Tạo ComboBox có thể gõ chữ
    cbxIdThe = new JComboBox<>();
    cbxIdThe.setEditable(true);
    cbxIdThe.setPreferredSize(new Dimension(150, 25));

    // Bộ Render màu sắc Xanh/Đỏ cho Dropdown
    cbxIdThe.setRenderer(new DefaultListCellRenderer() {
      @Override
      public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
          boolean cellHasFocus) {
        Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof CardItem) {
          CardItem item = (CardItem) value;
          String textToDisplay = item.idThe + " - " + item.tenDG;

          if ("BiKhoa".equals(item.trangThai)) {
            c.setForeground(Color.RED);
            setText(textToDisplay + " (Bị khóa)");
          } else if ("HetHan".equals(item.trangThai)) {
            c.setForeground(Color.ORANGE);
            setText(textToDisplay + " (Hết hạn)");
          } else {
            c.setForeground(new Color(0, 153, 76)); // Màu xanh lá
            setText(textToDisplay + " (Hoạt động)");
          }
        }
        return c;
      }
    });

    // Bắt sự kiện gõ phím để Lọc dữ liệu (Không tự động show Popup)
    JTextField editor = (JTextField) cbxIdThe.getEditor().getEditorComponent();
    editor.setBackground(new Color(230, 245, 255));
    editor.setForeground(new Color(0, 80, 160));
    editor.addKeyListener(new KeyAdapter() {
      @Override
      public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        // Bỏ qua các phím điều hướng để tránh lỗi
        if (code == KeyEvent.VK_UP || code == KeyEvent.VK_DOWN || code == KeyEvent.VK_LEFT || code == KeyEvent.VK_RIGHT
            || code == KeyEvent.VK_ENTER) {
          return;
        }

        String input = editor.getText();
        cbxIdThe.removeAllItems();
        for (CardItem item : allCardItems) {
          if (String.valueOf(item.idThe).contains(input)) {
            cbxIdThe.addItem(item);
          }
        }
        editor.setText(input); // Trả lại text vừa gõ
      }
    });

    // Nạp toàn bộ dữ liệu lần đầu
    for (CardItem item : allCardItems)
      cbxIdThe.addItem(item);
    editor.setText(""); // Xóa text mặc định

    JButton btnCheck = new JButton("Kiểm tra");
    btnCheck.addActionListener(e -> checkTheThanhVien());

    pnlCheckThe.add(cbxIdThe, BorderLayout.CENTER);
    pnlCheckThe.add(btnCheck, BorderLayout.EAST);

    gbc.gridx = 1;
    topPanel.add(pnlCheckThe, gbc);

    // Ô Tên độc giả bị xám
    txtTenDocGia = new JTextField(18);
    txtTenDocGia.setEditable(false);
    txtTenDocGia.setBackground(new Color(240, 240, 240));
    addRow(topPanel, gbc, 2, "Tên đọc giả:", txtTenDocGia);
    // ==========================================================

    addRow(topPanel, gbc, 3, "Ngày mượn:", spNgayMuon);
    addRow(topPanel, gbc, 4, "Ngày hẹn trả:", spNgayHenTra);

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

  private void checkTheThanhVien() {
    JTextField editor = (JTextField) cbxIdThe.getEditor().getEditorComponent();
    String inputId = editor.getText().trim();

    if (inputId.isEmpty()) {
      JOptionPane.showMessageDialog(this, "Vui lòng nhập hoặc chọn Mã Thẻ!");
      return;
    }

    try {
      int idThe = Integer.parseInt(inputId);
      String loi = theThanhVienBUS.kiemTraTheKhaDung(idThe);
      if (loi != null) {
        JOptionPane.showMessageDialog(this, loi, "Lỗi thẻ", JOptionPane.WARNING_MESSAGE);
        txtTenDocGia.setText("");
        validIdThe = -1;
      } else {
        String tenDG = theThanhVienBUS.getTenDocGiaByThe(idThe);
        txtTenDocGia.setText(tenDG);
        txtTenDocGia.setForeground(new Color(0, 102, 51));
        validIdThe = idThe; // Ghi nhận thẻ hợp lệ
      }
    } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(this, "Mã thẻ phải là số nguyên!");
    }
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
    if (validIdThe == -1) {
      JOptionPane.showMessageDialog(this, "Vui lòng nhập và bấm 'Kiểm tra' để xác nhận thẻ hợp lệ!", "Cảnh báo",
          JOptionPane.WARNING_MESSAGE);
      return;
    }

    List<Integer> selectedIds = new ArrayList<>();
    for (int i = 0; i < tableModel.getRowCount(); i++) {
      if (Boolean.TRUE.equals(tableModel.getValueAt(i, 0))) {
        selectedIds.add(dsCuonSach.get(i).getIdCuonSach());
      }
    }

    if (selectedIds.isEmpty()) {
      JOptionPane.showMessageDialog(this, "Bạn chưa tích chọn cuốn sách nào!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
      return;
    }

    try {
      LocalDate ngayMuon = ((Date) spNgayMuon.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
      LocalDate ngayHenTra = ((Date) spNgayHenTra.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
      phieuMuonBUS.addPhieuMuon(
          UserSession.getInstance().getIdTaiKhoan(),
          validIdThe, // DÙNG ID THẺ THẬT THAY VÌ GIẢ LẬP
          ngayMuon, ngayHenTra, selectedIds);

      JOptionPane.showMessageDialog(this, "Tạo phiếu mượn thành công!");
      dispose();
    } catch (Exception ex) {
      JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
  }
}