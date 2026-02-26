package com.libracoreteam.libracore.gui.dialog;

import com.libracoreteam.libracore.dao.SachDAO;
import com.libracoreteam.libracore.model.Sach;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class TimSachDialog extends JDialog {

    private final SachDAO sachDAO = new SachDAO();
    private JTable tblResults;
    private DefaultTableModel tableModel;
    private Sach selectedSach = null;
    private JTextField txtSearch;

    public TimSachDialog(JDialog parent, JTextField sourceTextField) {
        super(parent, false); // Không block màn hình dưới hoàn toàn, chỉ là popup
        setUndecorated(true);
        getRootPane().setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        this.txtSearch = sourceTextField;

        initComponents();

        pack();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        String[] cols = { "ID", "Tên sách", "Nhà xuất bản" }; // Thể loại hoặc NXB tùy ý
        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblResults = new JTable(tableModel);
        tblResults.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblResults.setRowHeight(25);

        // Hide ID column
        tblResults.getColumnModel().getColumn(0).setMinWidth(0);
        tblResults.getColumnModel().getColumn(0).setMaxWidth(0);
        tblResults.getColumnModel().getColumn(0).setWidth(0);

        // Events
        tblResults.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    selectRowAndClose();
                }
            }
        });

        tblResults.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    selectRowAndClose();
                    e.consume(); // Prevent default enter behavior
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    selectedSach = null;
                    setVisible(false);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(tblResults);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void searchAndShow(String keyword) {
        tableModel.setRowCount(0);
        List<Sach> results = sachDAO.searchActive(keyword);

        if (results.isEmpty()) {
            setVisible(false);
            return;
        }

        for (Sach s : results) {
            // NXB column will be empty for now since we don't have NXBDAO loaded inside
            // Sach logic easily here,
            // but we can just show the id or skip it. Let's just show ID for demo.
            tableModel.addRow(
                    new Object[] { s, s.getTenSach(), "NXB ID: " + (s.getIdNXB() != null ? s.getIdNXB() : "N/A") });
        }

        if (!isVisible()) {
            Point p = txtSearch.getLocationOnScreen();
            setBounds(p.x, p.y + txtSearch.getHeight(), txtSearch.getWidth(), 250);
            setVisible(true);
        }

        // If results exist, select the first one automatically
        if (tblResults.getRowCount() > 0) {
            tblResults.setRowSelectionInterval(0, 0);
        }
    }

    private void selectRowAndClose() {
        int row = tblResults.getSelectedRow();
        if (row >= 0) {
            selectedSach = (Sach) tableModel.getValueAt(row, 0); // We stored the whole object in Col 0
        }
        setVisible(false);
    }

    public Sach getSelectedSach() {
        return selectedSach;
    }
}
