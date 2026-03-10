package com.libracoreteam.libracore.gui.dialog;

import com.libracoreteam.libracore.bus.SachBUS;
import com.libracoreteam.libracore.model.NXB;
import com.libracoreteam.libracore.model.TacGia;
import com.libracoreteam.libracore.model.TheLoai;
import com.libracoreteam.libracore.model.Sach;
import com.libracoreteam.libracore.util.checklist.CheckListItem;
import com.libracoreteam.libracore.util.checklist.CheckListUtils;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

public class ThemSachDialog extends JDialog {

    private final SachBUS sachBUS = new SachBUS();
    private boolean saved = false;
    private int createdId = -1;

    private JTextField txtTenSach;
    private JTextField txtNamXB;
    private JTextField txtSoTrang;

    private JTextArea txtMoTa;

    private JComboBox<IdNameItem> cboNXB;

    private DefaultListModel<CheckListItem> tacGiaModel;
    private DefaultListModel<CheckListItem> theLoaiModel;

    private JList<CheckListItem> lstTacGia;
    private JList<CheckListItem> lstTheLoai;

    private JButton jButtonLuu;
    private JButton jButtonHuy;

    public ThemSachDialog(Frame parent, boolean modal) {
        super(parent, "Thêm sách", modal);
        initComponents();
    }

    private void initComponents() {

        JPanel formPanel = new JPanel(
                new MigLayout(
                        "wrap 2, insets 15, gapx 10, gapy 8",
                        "[right][grow, fill]",
                        "[]"));

        int iconSize = 16;

        txtTenSach = new JTextField(25);
        formPanel.add(new JLabel("Tên sách:"));
        formPanel.add(txtTenSach);

        tacGiaModel = new DefaultListModel<>();
        loadTacGiaToModel();

        lstTacGia = CheckListUtils.createCheckList(tacGiaModel);
        lstTacGia.setVisibleRowCount(3);

        JScrollPane spTacGia = new JScrollPane(lstTacGia);

        formPanel.add(new JLabel("Tác giả:"));
        formPanel.add(spTacGia, "hmin 80");

        theLoaiModel = new DefaultListModel<>();
        loadTheLoaiToModel();

        lstTheLoai = CheckListUtils.createCheckList(theLoaiModel);
        lstTheLoai.setVisibleRowCount(3);

        JScrollPane spTheLoai = new JScrollPane(lstTheLoai);

        formPanel.add(new JLabel("Thể loại:"));
        formPanel.add(spTheLoai, "hmin 80");

        cboNXB = new JComboBox<>();
        loadNXBToCombo();

        formPanel.add(new JLabel("Nhà xuất bản:"));
        formPanel.add(cboNXB);

        txtNamXB = new JTextField(10);
        formPanel.add(new JLabel("Năm xuất bản:"));
        formPanel.add(txtNamXB);

        txtSoTrang = new JTextField(10);
        formPanel.add(new JLabel("Số trang:"));
        formPanel.add(txtSoTrang);

        txtMoTa = new JTextArea(4, 25);
        txtMoTa.setLineWrap(true);
        txtMoTa.setWrapStyleWord(true);

        JScrollPane spMoTa = new JScrollPane(
                txtMoTa,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        formPanel.add(new JLabel("Mô tả:"));
        formPanel.add(spMoTa, "hmin 100");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        jButtonLuu = new JButton("Xác nhận");
        jButtonHuy = new JButton("Hủy");

        buttonPanel.add(jButtonLuu);
        buttonPanel.add(jButtonHuy);
        jButtonLuu.setIcon(FontIcon.of(FontAwesomeSolid.CHECK_CIRCLE, iconSize, new Color(0, 100, 0)));
        jButtonHuy.setIcon(FontIcon.of(FontAwesomeSolid.TIMES_CIRCLE, iconSize, new Color(100, 0, 0)));

        jButtonLuu.addActionListener(e -> onSave());
        jButtonHuy.addActionListener(e -> dispose());

        setLayout(new BorderLayout());
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(getParent());
        setResizable(false);
    }

    private void onSave() {
        IdNameItem selectedNXB = (IdNameItem) cboNXB.getSelectedItem();
        Integer idNXB = selectedNXB != null ? selectedNXB.id : null;

        List<Integer> tacGiaIds = toIdList(CheckListUtils.getSelectedItems(tacGiaModel));
        List<Integer> theLoaiIds = toIdList(CheckListUtils.getSelectedItems(theLoaiModel));

        try {
            Sach created = sachBUS.create(
                    txtTenSach.getText(),
                    idNXB,
                    txtNamXB.getText(),
                    txtSoTrang.getText(),
                    txtMoTa.getText(),
                    tacGiaIds,
                    theLoaiIds);

            saved = true;
            createdId = created.getIdSach();

            JOptionPane.showMessageDialog(this, "Thêm sách thành công. Mã sách: " + createdId);
            dispose();

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi hệ thống: " + ex.getMessage());
        }
    }

    public boolean isSaved() {
        return saved;
    }

    public int getCreatedId() {
        return createdId;
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

    private static List<Integer> toIdList(List<CheckListItem> items) {
        List<Integer> ids = new ArrayList<>();
        if (items == null)
            return ids;
        for (CheckListItem it : items) {
            if (it != null && it.getId() > 0)
                ids.add(it.getId());
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
