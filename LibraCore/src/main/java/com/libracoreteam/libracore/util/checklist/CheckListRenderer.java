/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.libracoreteam.libracore.util.checklist;


import javax.swing.*;
import java.awt.*;

/**
 *
 * @author luuis
 */
public class CheckListRenderer extends JCheckBox
        implements ListCellRenderer<CheckListItem> {

    @Override
    public Component getListCellRendererComponent(
            JList<? extends CheckListItem> list,
            CheckListItem value,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {

        setText(value.getLabel());
        setSelected(value.isSelected());
        // Để người dùng thấy rõ item đang được focus/selected (giảm cảm giác "click không ăn")
        setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
        setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
        setEnabled(list.isEnabled());
        setFont(list.getFont());
        setOpaque(true);
        return this;
    }
}
