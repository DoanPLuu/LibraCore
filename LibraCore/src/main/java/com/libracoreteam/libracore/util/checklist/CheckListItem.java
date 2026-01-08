/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.libracoreteam.libracore.util.checklist;

/**
 *
 * @author luuis
 */
public class CheckListItem {
    private final int id;
    private final String label;
    private boolean selected;

    /**
     * Dùng constructor này khi item map với DB (id_TacGia, id_TheLoai,...).
     */
    public CheckListItem(int id, String label) {
        this.id = id;
        this.label = label;
    }

    /**
     * Constructor tiện cho mock/demo (id = -1).
     * Cái này để test giao diện trước
     */
    public CheckListItem(String label) {
        this(-1, label);
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CheckListItem)) return false;
        CheckListItem that = (CheckListItem) o;

        // Nếu có id hợp lệ thì so theo id (phù hợp DB)
        if (this.id >= 0 && that.id >= 0) {
            return this.id == that.id;
        }
        // Fallback: so theo label (làm để test hay demo)
        return this.label != null ? this.label.equals(that.label) : that.label == null;
    }

    @Override
    public int hashCode() {
        if (id >= 0) return Integer.hashCode(id);
        return label != null ? label.hashCode() : 0;
    }
}
