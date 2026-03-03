package com.libracoreteam.libracore.gui.dialog;

import com.libracoreteam.libracore.dao.TacGiaDAO;
import com.libracoreteam.libracore.model.TacGia;
import net.miginfocom.swing.MigLayout;
import org.kordamp.ikonli.swing.FontIcon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class SuaTacGiaDialog extends JDialog {
    // ===== Fields =====
    private JTextField txtTenTacGia;
    private JSpinner spnNgaySinh;
    private JTextField txtNoiSinh;
    private JTextField txtSDT;
    private JCheckBox chkHoatDong; // Form sửa phải có thêm cái này
    
    private JButton btnLuu;
    private JButton btnHuy;

    private TacGia currentTacGia;
    private boolean isSaved = false;

    public SuaTacGiaDialog(Frame parent, boolean modal, TacGia tacGia) {
        super(parent, "Sửa thông tin tác giả", modal);
        this.currentTacGia = tacGia;
        initComponents();
        loadDataToForm(); // Đổ dữ liệu cũ lên form
    }

    private void initComponents() {
        JPanel formPanel = new JPanel(
                new MigLayout(
                        "wrap 2, insets 15, gapx 10, gapy 8",
                        "[right][grow, fill]",
                        "[]"
                )
        );

        // ===== Tên tác giả =====
        txtTenTacGia = new JTextField(25);
        formPanel.add(new JLabel("Tên tác giả:"));
        formPanel.add(txtTenTacGia);

        // ===== Ngày sinh =====
        SpinnerDateModel dateModel = new SpinnerDateModel();
        spnNgaySinh = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spnNgaySinh, "dd/MM/yyyy");
        spnNgaySinh.setEditor(dateEditor);
        
        formPanel.add(new JLabel("Ngày sinh:"));
        formPanel.add(spnNgaySinh);

        // ===== Nơi sinh =====
        txtNoiSinh = new JTextField(25);
        formPanel.add(new JLabel("Nơi sinh:"));
        formPanel.add(txtNoiSinh);

        // ===== Số điện thoại =====
        txtSDT = new JTextField(15);
        formPanel.add(new JLabel("Số điện thoại:"));
        formPanel.add(txtSDT);

        // ===== Trạng thái hoạt động =====
        chkHoatDong = new JCheckBox("Còn hoạt động");
        formPanel.add(new JLabel("Trạng thái:"));
        formPanel.add(chkHoatDong);

        // ===== Buttons =====
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnLuu = new JButton("Cập nhật");
        btnHuy = new JButton("Hủy");
        
        int iconSize = 16;
        btnLuu.setIcon(FontIcon.of(FontAwesomeSolid.SAVE, iconSize, new Color(13, 110, 253)));
        btnHuy.setIcon(FontIcon.of(FontAwesomeSolid.TIMES_CIRCLE, iconSize, new Color(220, 53, 69)));

        buttonPanel.add(btnLuu);
        buttonPanel.add(btnHuy);

        btnLuu.addActionListener(e -> onSave());
        btnHuy.addActionListener(e -> dispose());

        setLayout(new BorderLayout());
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(getParent());
        setResizable(false);
    }

    // Hàm này hút dữ liệu từ object truyền vào đắp lên giao diện
    private void loadDataToForm() {
        if (currentTacGia == null) return;
        
        txtTenTacGia.setText(currentTacGia.getTenTacGia());
        txtNoiSinh.setText(currentTacGia.getNoiSinh() != null ? currentTacGia.getNoiSinh() : "");
        txtSDT.setText(currentTacGia.getSdt() != null ? currentTacGia.getSdt() : "");
        chkHoatDong.setSelected(currentTacGia.isHoatDong());

        if (currentTacGia.getNgaySinh() != null) {
            Date date = Date.from(currentTacGia.getNgaySinh().atStartOfDay(ZoneId.systemDefault()).toInstant());
            spnNgaySinh.setValue(date);
        }
    }

    private void onSave() {
        String tenTacGia = txtTenTacGia.getText().trim();
        if (tenTacGia.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên tác giả không được để trống!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Cập nhật lại Object hiện tại
        currentTacGia.setTenTacGia(tenTacGia);
        currentTacGia.setNoiSinh(txtNoiSinh.getText().trim());
        currentTacGia.setSdt(txtSDT.getText().trim());
        currentTacGia.setHoatDong(chkHoatDong.isSelected());

        Date selectedDate = (Date) spnNgaySinh.getValue();
        LocalDate ngaySinh = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        currentTacGia.setNgaySinh(ngaySinh);
        
        // Gọi DAO để UPDATE
        TacGiaDAO tacGiaDAO = new TacGiaDAO();
        if (tacGiaDAO.update(currentTacGia)) {
            isSaved = true;
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật vào cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isSaved() {
        return isSaved;
    }
}