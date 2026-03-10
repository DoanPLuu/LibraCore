
package com.libracoreteam.libracore.util.checklist;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class CheckListUtils {
    public static JList<CheckListItem> createCheckList(
            DefaultListModel<CheckListItem> model) {

        JList<CheckListItem> list = new JList<>(model);
        list.setCellRenderer(new CheckListRenderer());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                list.requestFocusInWindow();

                int index = list.locationToIndex(e.getPoint());
                if (index < 0) return;

                Rectangle bounds = list.getCellBounds(index, index);
                if (bounds == null || !bounds.contains(e.getPoint())) return; 

                toggleItem(list, model, index);
            }
        });

        InputMap im = list.getInputMap(JComponent.WHEN_FOCUSED);
        ActionMap am = list.getActionMap();
        im.put(KeyStroke.getKeyStroke("SPACE"), "toggle-check");
        am.put("toggle-check", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = list.getSelectedIndex();
                if (index >= 0) {
                    toggleItem(list, model, index);
                }
            }
        });

        return list;
    }

    private static void toggleItem(JList<CheckListItem> list, DefaultListModel<CheckListItem> model, int index) {
        list.setSelectedIndex(index);

        CheckListItem item = model.getElementAt(index);
        item.setSelected(!item.isSelected());

        Rectangle bounds = list.getCellBounds(index, index);
        if (bounds != null) {
            list.repaint(bounds);
        } else {
            list.repaint();
        }
    }

    public static List<CheckListItem> getSelectedItems(
            DefaultListModel<CheckListItem> model) {

        List<CheckListItem> result = new ArrayList<>();
        for (int i = 0; i < model.size(); i++) {
            CheckListItem item = model.get(i);
            if (item.isSelected()) {
                result.add(item);
            }
        }
        return result;
    }
}
