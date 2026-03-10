
package com.libracoreteam.libracore.util.checklist;


import javax.swing.*;
import java.awt.*;

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
        setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
        setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
        setEnabled(list.isEnabled());
        setFont(list.getFont());
        setOpaque(true);
        return this;
    }
}
