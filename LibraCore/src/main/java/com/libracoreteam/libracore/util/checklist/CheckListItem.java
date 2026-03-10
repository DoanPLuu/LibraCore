
package com.libracoreteam.libracore.util.checklist;

public class CheckListItem {
    private final int id;
    private final String label;
    private boolean selected;

    public CheckListItem(int id, String label) {
        this.id = id;
        this.label = label;
    }

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

        if (this.id >= 0 && that.id >= 0) {
            return this.id == that.id;
        }
        return this.label != null ? this.label.equals(that.label) : that.label == null;
    }

    @Override
    public int hashCode() {
        if (id >= 0) return Integer.hashCode(id);
        return label != null ? label.hashCode() : 0;
    }
}
