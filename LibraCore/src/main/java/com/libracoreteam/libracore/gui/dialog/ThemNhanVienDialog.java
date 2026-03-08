package com.libracoreteam.libracore.gui.dialog;

import com.libracoreteam.libracore.util.ImageHelper;
import net.miginfocom.swing.MigLayout;
import org.kordamp.ikonli.swing.FontIcon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class ThemNhanVienDialog extends JDialog {

  private JTextField txtTenNhanVien;
  private JSpinner spnNgaySinh;
  private JTextArea txtDiaChi;
  private JTextField txtSDT;
  private JTextField txtEmail;
  private JLabel lblAnh;
  private JButton btnChonAnh;
  private JButton btnLuu;
  private JButton btnHuy;

  private File selectedImageFile;
  private boolean saved = false;

  public ThemNhanVienDialog(Frame parent, boolean modal) {
    super(parent, "Thêm nhân viên", modal);
    initComponents();
  }

  private void initComponents() {
    JPanel photoPanel = buildPhotoPanel();

    JPanel formPanel = new JPanel(
        new MigLayout("wrap 2, insets 15, gapx 10, gapy 8", "[right][grow, fill]", "[]"));

    txtTenNhanVien = new JTextField(25);
    formPanel.add(new JLabel("Họ và Tên:"));
    formPanel.add(txtTenNhanVien);

    SpinnerDateModel dateModel = new SpinnerDateModel();
    spnNgaySinh = new JSpinner(dateModel);
    JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spnNgaySinh, "dd/MM/yyyy");
    spnNgaySinh.setEditor(dateEditor);
    spnNgaySinh.setValue(new Date());
    formPanel.add(new JLabel("Ngày Sinh:"));
    formPanel.add(spnNgaySinh);

    txtDiaChi = new JTextArea(4, 25);
    txtDiaChi.setLineWrap(true);
    txtDiaChi.setWrapStyleWord(true);
    JScrollPane scrollDiaChi = new JScrollPane(txtDiaChi);
    formPanel.add(new JLabel("Địa Chỉ:"));
    formPanel.add(scrollDiaChi);

    txtSDT = new JTextField(25);
    formPanel.add(new JLabel("Số Điện Thoại:"));
    formPanel.add(txtSDT);

    txtEmail = new JTextField(25);
    formPanel.add(new JLabel("Email:"));
    formPanel.add(txtEmail);

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
    btnLuu = new JButton("Xác nhận");
    btnHuy = new JButton("Hủy");
    int iconSize = 16;
    btnLuu.setIcon(FontIcon.of(FontAwesomeSolid.CHECK_CIRCLE, iconSize, new Color(40, 167, 69)));
    btnHuy.setIcon(FontIcon.of(FontAwesomeSolid.TIMES_CIRCLE, iconSize, new Color(220, 53, 69)));
    buttonPanel.add(btnLuu);
    buttonPanel.add(btnHuy);

    btnLuu.addActionListener(e -> onSave());
    btnHuy.addActionListener(e -> dispose());

    JPanel contentPanel = new JPanel(new BorderLayout());
    contentPanel.add(photoPanel, BorderLayout.WEST);
    contentPanel.add(formPanel, BorderLayout.CENTER);

    setLayout(new BorderLayout());
    add(contentPanel, BorderLayout.CENTER);
    add(buttonPanel, BorderLayout.SOUTH);

    pack();
    setLocationRelativeTo(getParent());
    setResizable(false);
  }

  private JPanel buildPhotoPanel() {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 10));

    lblAnh = new JLabel();
    lblAnh.setPreferredSize(new Dimension(150, 180));
    lblAnh.setMinimumSize(new Dimension(150, 180));
    lblAnh.setMaximumSize(new Dimension(150, 180));
    lblAnh.setBorder(BorderFactory.createLineBorder(Color.GRAY));
    lblAnh.setHorizontalAlignment(SwingConstants.CENTER);
    lblAnh.setVerticalAlignment(SwingConstants.CENTER);
    lblAnh.setText("ẢNH");
    lblAnh.setAlignmentX(Component.CENTER_ALIGNMENT);

    btnChonAnh = new JButton("Chọn ảnh");
    btnChonAnh.setAlignmentX(Component.CENTER_ALIGNMENT);
    btnChonAnh.addActionListener(e -> chonAnh());

    panel.add(lblAnh);
    panel.add(Box.createVerticalStrut(8));
    panel.add(btnChonAnh);
    return panel;
  }

  private void chonAnh() {
    JFileChooser fc = new JFileChooser();
    fc.setFileFilter(new FileNameExtensionFilter("Ảnh (jpg, jpeg, png, gif)", "jpg", "jpeg", "png", "gif"));
    if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
      selectedImageFile = fc.getSelectedFile();
      ImageIcon icon = ImageHelper.loadImage(selectedImageFile.getAbsolutePath(), 150, 180);
      if (icon != null) {
        lblAnh.setIcon(icon);
        lblAnh.setText("");
      }
    }
  }

  private void onSave() {
    String tenNhanVien = txtTenNhanVien.getText().trim();
    String diaChi = txtDiaChi.getText().trim();
    String sdt = txtSDT.getText().trim();
    String email = txtEmail.getText().trim();

    if (tenNhanVien.isEmpty()) {
      JOptionPane.showMessageDialog(this, "Họ và tên không được để trống!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
      return;
    }

    Date selectedDate = (Date) spnNgaySinh.getValue();
    LocalDate ngaySinh = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    if (ngaySinh.isAfter(LocalDate.now())) {
      JOptionPane.showMessageDialog(this, "Ngày sinh phải trước ngày hiện tại!", "Cảnh báo",
          JOptionPane.WARNING_MESSAGE);
      return;
    }

    if (!sdt.isEmpty() && !sdt.matches("^\\d{10}$")) {
      JOptionPane.showMessageDialog(this, "Số điện thoại phải bao gồm 10 chữ số!", "Cảnh báo",
          JOptionPane.WARNING_MESSAGE);
      return;
    }

    if (!email.isEmpty() && !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
      JOptionPane.showMessageDialog(this, "Email không hợp lệ!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
      return;
    }

    com.libracoreteam.libracore.model.NhanVien nv = new com.libracoreteam.libracore.model.NhanVien(
        tenNhanVien, ngaySinh, diaChi, sdt, email);

    com.libracoreteam.libracore.bus.NhanVienBUS nhanVienBUS = new com.libracoreteam.libracore.bus.NhanVienBUS();
    if (nhanVienBUS.add(nv)) {
      if (selectedImageFile != null) {
        String path = ImageHelper.saveImage(selectedImageFile, nv.getIdNhanVien());
        if (path != null) {
          nv.setAnhNhanVien(path);
          nhanVienBUS.update(nv);
        }
      }
      JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công!");
      saved = true;
      dispose();
    } else {
      JOptionPane.showMessageDialog(this, "Lỗi khi lưu nhân viên vào cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
  }

  public boolean isSaved() {
    return saved;
  }
}
