package com.libracoreteam.libracore.bus;

import com.libracoreteam.libracore.dao.MucPhatDAO;
import com.libracoreteam.libracore.model.MucPhat;
import java.util.List;

public class MucPhatBUS {
  private final MucPhatDAO dao = new MucPhatDAO();

  public List<MucPhat> getAll() {
    return dao.getAll();
  }

  public List<MucPhat> getFiltered(String loaiPhat) {
    return dao.getFiltered(loaiPhat);
  }

  public MucPhat getPerDayActive() {
    return dao.getPerDayActive();
  }

  public List<MucPhat> getAllFixedActive() {
    return dao.getAllFixedActive();
  }

  public void insert(MucPhat mp) {
    if ("PerDay".equals(mp.getLoaiPhat())) {
      MucPhat existing = dao.getPerDayActive();
      if (existing != null) {
        throw new RuntimeException(
            "Đã tồn tại mức phạt theo ngày đang hoạt động: \"" + existing.getTenMucPhat() + "\".\n"
                + "Vui lòng vô hiệu hóa mức phạt cũ trước khi thêm mới.");
      }
    }
    boolean ok = dao.insert(mp);
    if (!ok) {
      throw new RuntimeException("Lỗi khi thêm mức phạt mới!");
    }
  }

  public void update(MucPhat mp) {
    if ("PerDay".equals(mp.getLoaiPhat())) {
      MucPhat existing = dao.getPerDayActive();
      if (existing != null && existing.getIdMucPhat() != mp.getIdMucPhat()) {
        throw new RuntimeException(
            "Đã tồn tại mức phạt theo ngày đang hoạt động: \"" + existing.getTenMucPhat() + "\".\n"
                + "Vui lòng vô hiệu hóa mức phạt cũ trước khi cập nhật loại này.");
      }
    }
    boolean ok = dao.update(mp);
    if (!ok) {
      throw new RuntimeException("Lỗi khi cập nhật mức phạt!");
    }
  }

  public void xoa(int id) {
    boolean ok = dao.setHoatDong(id, false);
    if (!ok)
      throw new RuntimeException("Lỗi khi ẩn mức phạt!");
  }

  public void setHoatDong(int id, boolean hoatDong) {
    boolean ok = dao.setHoatDong(id, hoatDong);
    if (!ok)
      throw new RuntimeException("Lỗi khi cập nhật mức phạt!");
  }

  public List<MucPhat> getFilteredInactive(String loaiPhat) {
    return dao.getFilteredInactive(loaiPhat);
  }
}
