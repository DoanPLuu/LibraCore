package com.libracoreteam.libracore.gui.dialog;

import com.libracoreteam.libracore.bus.SachBUS;
import com.libracoreteam.libracore.model.NXB;
import com.libracoreteam.libracore.model.Sach;
import com.libracoreteam.libracore.model.TacGia;
import com.libracoreteam.libracore.model.TheLoai;
import com.libracoreteam.libracore.util.checklist.CheckListItem;
import com.libracoreteam.libracore.util.checklist.CheckListUtils;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Dialog sửa sách (mock để test luồng UI).
 * - Dùng MigLayout 11.4.2
 * - Có checklist Tác giả + Thể loại (multi-select)
 * - Pre-fill dữ liệu giả lập để test "sửa"
 */
public class SuaSachDialog extends JDialog {

    private final SachBUS sachBUS = new SachBUS();
    private final int editingIdSach;
    private boolean saved = false;

    // ===== Field cơ bản =====
    private JTextField txtTenSach;
    private JTextField txtNamXB;
    private JTextField txtSoTrang;
    private JTextField txtGiaSach;

    private JTextArea txtMoTa;

    private JComboBox<IdNameItem> cboNXB;

    // ===== Checkbox list =====
    private DefaultListModel<CheckListItem> tacGiaModel;
    private DefaultListModel<CheckListItem> theLoaiModel;

    private JList<CheckListItem> lstTacGia;
    private JList<CheckListItem> lstTheLoai;

    private JButton btnXacNhan;
    private JButton btnHuy;

    public SuaSachDialog(Frame parent, boolean modal) {
        this(parent, modal, -1);
    }

    public SuaSachDialog(Frame parent, boolean modal, int idSach) {
        super(parent, "Sửa sách", modal);
        this.editingIdSach = idSach;
        initComponents();
        loadListsFromDb();

        if (editingIdSach > 0) {
            loadDataForEdit(editingIdSach);
        } else {
            // vẫn giữ mock để test UI nếu chưa có luồng chọn sách
            loadMockDataForEdit();
        }
    }

    private void initComponents() {
        JPanel formPanel = new JPanel(
                new MigLayout(
                        "wrap 2, insets 15, gapx 10, gapy 8",
                        "[right][grow, fill]",
                        "[]"
                )
        );

        // ===== Tên sách =====
        txtTenSach = new JTextField(25);
        formPanel.add(new JLabel("Tên sách:"));
        formPanel.add(txtTenSach);

        // ===== Tác giả (checkbox list) =====
        tacGiaModel = new DefaultListModel<>();

        lstTacGia = CheckListUtils.createCheckList(tacGiaModel);
        lstTacGia.setVisibleRowCount(4);
        JScrollPane spTacGia = new JScrollPane(lstTacGia);

        formPanel.add(new JLabel("Tác giả:"), "top");
        formPanel.add(spTacGia, "hmin 90");

        // ===== Thể loại (checkbox list) =====
        theLoaiModel = new DefaultListModel<>();

        lstTheLoai = CheckListUtils.createCheckList(theLoaiModel);
        lstTheLoai.setVisibleRowCount(4);
        JScrollPane spTheLoai = new JScrollPane(lstTheLoai);

        formPanel.add(new JLabel("Thể loại:"), "top");
        formPanel.add(spTheLoai, "hmin 90");

        // ===== Nhà xuất bản =====
        cboNXB = new JComboBox<>();

        formPanel.add(new JLabel("Nhà xuất bản:"));
        formPanel.add(cboNXB);

        // ===== Năm xuất bản =====
        txtNamXB = new JTextField(10);
        formPanel.add(new JLabel("Năm xuất bản:"));
        formPanel.add(txtNamXB);

        // ===== Số trang =====
        txtSoTrang = new JTextField(10);
        formPanel.add(new JLabel("Số trang:"));
        formPanel.add(txtSoTrang);

        // ===== Mô tả =====
        txtMoTa = new JTextArea(4, 25);
        txtMoTa.setLineWrap(true);
        txtMoTa.setWrapStyleWord(true);

        JScrollPane spMoTa = new JScrollPane(
                txtMoTa,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        formPanel.add(new JLabel("Mô tả:"), "top");
        formPanel.add(spMoTa, "hmin 100");

        // ===== Giá sách =====
        txtGiaSach = new JTextField(10);
        formPanel.add(new JLabel("Giá sách:"));
        formPanel.add(txtGiaSach);

        // ===== Buttons =====
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnXacNhan = new JButton("Xác nhận");
        btnHuy = new JButton("Hủy");

        buttonPanel.add(btnXacNhan);
        buttonPanel.add(btnHuy);

        btnXacNhan.addActionListener(e -> onSave());
        btnHuy.addActionListener(e -> dispose());

        setLayout(new BorderLayout());
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(getParent());
        setResizable(false);
    }

    /**
     * Mock dữ liệu để giả lập trạng thái "sửa".
     * Có thể thay bằng load từ DAO theo id_Sach sau này.
     */
    private void loadMockDataForEdit() {
        // Mock field cơ bản
        txtTenSach.setText("Harry Potter và Hòn đá Phù thủy");
        selectComboByName(cboNXB, "NXB Trẻ");
        txtNamXB.setText("2000");
        txtSoTrang.setText("320");
        txtGiaSach.setText("89000");
        txtMoTa.setText("Mock dữ liệu để test sửa sách.\nCó checklist tác giả và thể loại.");

        // Mock: chọn sẵn tác giả + thể loại
        // (VD: Rowling + Giả tưởng + Thiếu nhi)
        setSelectedById(tacGiaModel, 2, true);
        setSelectedById(theLoaiModel, 2, true);
        setSelectedById(theLoaiModel, 3, true);

        // repaint list để thấy tick ngay
        lstTacGia.repaint();
        lstTheLoai.repaint();
    }

    private static void setSelectedById(DefaultListModel<CheckListItem> model, int id, boolean selected) {
        for (int i = 0; i < model.size(); i++) {
            CheckListItem item = model.get(i);
            if (item.getId() == id) {
                item.setSelected(selected);
                return;
            }
        }
    }

    private void onSave() {
        if (editingIdSach <= 0) {
            JOptionPane.showMessageDialog(this, "Chưa có ID sách để cập nhật (hãy mở dialog bằng id_Sach).");
            return;
        }

        IdNameItem selectedNXB = (IdNameItem) cboNXB.getSelectedItem();
        Integer idNXB = selectedNXB != null ? selectedNXB.id : null;

        List<Integer> tacGiaIds = toIdList(CheckListUtils.getSelectedItems(tacGiaModel));
        List<Integer> theLoaiIds = toIdList(CheckListUtils.getSelectedItems(theLoaiModel));

        try {
            boolean ok = sachBUS.update(
                    editingIdSach,
                    txtTenSach.getText(),
                    idNXB,
                    txtNamXB.getText(),
                    txtSoTrang.getText(),
                    txtMoTa.getText(),
                    true,
                    tacGiaIds,
                    theLoaiIds
            );

            if (!ok) {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại.");
                return;
            }

            saved = true;
            JOptionPane.showMessageDialog(this, "Cập nhật sách thành công. Mã sách: " + editingIdSach);
            dispose();

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi hệ thống: " + ex.getMessage());
        }
    }

    public boolean isSaved() {
        return saved;
    }

    private void loadListsFromDb() {
        loadTacGiaToModel();
        loadTheLoaiToModel();
        loadNXBToCombo();
    }

    private void loadDataForEdit(int idSach) {
        try {
            Sach s = sachBUS.getById(idSach);
            if (s == null) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy sách có mã: " + idSach);
                return;
            }

            txtTenSach.setText(s.getTenSach());
            txtNamXB.setText(s.getNamXuatBan() != null ? String.valueOf(s.getNamXuatBan()) : "");
            txtSoTrang.setText(s.getSoTrang() != null ? String.valueOf(s.getSoTrang()) : "");
            txtMoTa.setText(s.getMoTa() != null ? s.getMoTa() : "");

            // chọn NXB theo id
            if (s.getIdNXB() != null) {
                selectComboById(cboNXB, s.getIdNXB());
            }

            // tick tác giả / thể loại theo bảng nối
            List<Integer> tgIds = sachBUS.getTacGiaIdsBySach(idSach);
            List<Integer> tlIds = sachBUS.getTheLoaiIdsBySach(idSach);

            for (Integer id : tgIds) {
                if (id != null) setSelectedById(tacGiaModel, id, true);
            }
            for (Integer id : tlIds) {
                if (id != null) setSelectedById(theLoaiModel, id, true);
            }

            lstTacGia.repaint();
            lstTheLoai.repaint();

        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, "Không tải được dữ liệu sách: " + ex.getMessage());
        }
    }

    private void loadTacGiaToModel() {
        tacGiaModel.clear();
        try {
            List<TacGia> list = sachBUS.getTacGiaActive();
            for (TacGia tg : list) {
                tacGiaModel.addElement(new CheckListItem(tg.getIdTacGia(), tg.getTenTacGia()));
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, "Không tải được danh sách tác giả: " + ex.getMessage());
        }
    }

    private void loadTheLoaiToModel() {
        theLoaiModel.clear();
        try {
            List<TheLoai> list = sachBUS.getTheLoaiActive();
            for (TheLoai tl : list) {
                theLoaiModel.addElement(new CheckListItem(tl.getIdTheLoai(), tl.getTenTheLoai()));
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, "Không tải được danh sách thể loại: " + ex.getMessage());
        }
    }

    private void loadNXBToCombo() {
        cboNXB.removeAllItems();
        try {
            List<NXB> list = sachBUS.getNXBActive();
            for (NXB nxb : list) {
                cboNXB.addItem(new IdNameItem(nxb.getIdNXB(), nxb.getTenNXB()));
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, "Không tải được danh sách NXB: " + ex.getMessage());
        }
    }

    private static void selectComboByName(JComboBox<IdNameItem> combo, String name) {
        if (combo == null || name == null) return;
        for (int i = 0; i < combo.getItemCount(); i++) {
            IdNameItem it = combo.getItemAt(i);
            if (it != null && name.equalsIgnoreCase(it.name)) {
                combo.setSelectedIndex(i);
                return;
            }
        }
    }

    private static void selectComboById(JComboBox<IdNameItem> combo, int id) {
        if (combo == null) return;
        for (int i = 0; i < combo.getItemCount(); i++) {
            IdNameItem it = combo.getItemAt(i);
            if (it != null && it.id == id) {
                combo.setSelectedIndex(i);
                return;
            }
        }
    }

    private static List<Integer> toIdList(List<CheckListItem> items) {
        List<Integer> ids = new ArrayList<>();
        if (items == null) return ids;
        for (CheckListItem it : items) {
            if (it != null && it.getId() > 0) ids.add(it.getId());
        }
        return ids;
    }

    private static final class IdNameItem {
        private final int id;
        private final String name;

        private IdNameItem(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}


