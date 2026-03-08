
package com.libracoreteam.libracore.bus;

import com.libracoreteam.libracore.dao.NhanVienDAO;
import com.libracoreteam.libracore.model.NhanVien;

import java.util.List;


public class NhanVienBUS {
  private final NhanVienDAO nhanVienDAO;

  public NhanVienBUS() {
    this.nhanVienDAO = new NhanVienDAO();
  }

  public List<NhanVien> getActive() {
    return nhanVienDAO.getActive();
  }

  public List<NhanVien> getAll() {
    return nhanVienDAO.getAll();
  }

  public NhanVien getById(int id) {
    if (id <= 0)
      return null;
    return nhanVienDAO.getById(id);
  }

  public NhanVien getByIdTaiKhoan(int idTaiKhoan) {
    if (idTaiKhoan <= 0)
      return null;
    return nhanVienDAO.getByIdTaiKhoan(idTaiKhoan);
  }

  public boolean add(NhanVien nv) {
    if (!validate(nv)) {
      return false;
    }
    return nhanVienDAO.insert(nv);
  }

  public boolean update(NhanVien nv) {
    if (!validate(nv)) {
      return false;
    }
    return nhanVienDAO.update(nv);
  }

  public boolean delete(int idNhanVien) {
    if (idNhanVien <= 0)
      return false;
    return nhanVienDAO.softDelete(idNhanVien);
  }

  public List<NhanVien> searchActive(String keyword) {
    if (keyword == null || keyword.trim().isEmpty()) {
      return getActive();
    }
    return nhanVienDAO.searchActive(keyword);
  }


  private boolean validate(NhanVien nv) {
    if (nv == null)
      return false;
    if (nv.getTenNhanVien() == null || nv.getTenNhanVien().trim().isEmpty())
      return false;
    return true;
  }
}
