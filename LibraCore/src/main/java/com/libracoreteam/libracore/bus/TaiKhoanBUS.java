package com.libracoreteam.libracore.bus;

import com.libracoreteam.libracore.dao.TaiKhoanDAO;
import com.libracoreteam.libracore.dao.VaiTroDAO;
import com.libracoreteam.libracore.model.TaiKhoan;
import com.libracoreteam.libracore.model.VaiTro;
import com.libracoreteam.libracore.util.UserSession;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaiKhoanBUS {

    private final TaiKhoanDAO taiKhoanDAO;
    private final VaiTroDAO vaiTroDAO;

    public TaiKhoanBUS() {
        this.taiKhoanDAO = new TaiKhoanDAO();
        this.vaiTroDAO = new VaiTroDAO();
    }

    public List<TaiKhoan> getAll() {
        return taiKhoanDAO.getAll();
    }

    public TaiKhoan getById(int id) {
        if (id <= 0) throw new IllegalArgumentException("ID tài khoản không hợp lệ.");
        return taiKhoanDAO.getById(id);
    }

    public boolean add(TaiKhoan tk) {
        validate(tk);
        return taiKhoanDAO.insert(tk);
    }

    public boolean update(TaiKhoan tk) {
        if (tk == null || tk.getIdTaiKhoan() <= 0)
            throw new IllegalArgumentException("Tài khoản không hợp lệ.");
        validate(tk);
        return taiKhoanDAO.update(tk);
    }

    public boolean delete(int id) {
        if (id <= 0) throw new IllegalArgumentException("ID tài khoản không hợp lệ.");
        return taiKhoanDAO.delete(id);
    }

    public List<TaiKhoan> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) return getAll();
        return taiKhoanDAO.search(keyword);
    }

    public Map<Integer, String> getRoleMap() {
        Map<Integer, String> roleMap = new HashMap<>();
        List<VaiTro> dsVaiTro = vaiTroDAO.getAll();
        if (dsVaiTro == null) return roleMap;
        for (VaiTro vt : dsVaiTro) {
            if (vt != null) roleMap.put(vt.getIdVaiTro(), vt.getTenVaiTro());
        }
        return roleMap;
    }

    private void validate(TaiKhoan tk) {
        if (tk == null)
            throw new IllegalArgumentException("Tài khoản không được null.");
        if (tk.getTaiKhoan() == null || tk.getTaiKhoan().trim().isEmpty())
            throw new IllegalArgumentException("Tên đăng nhập không được để trống.");
        if (tk.getTaiKhoan().trim().length() < 3)
            throw new IllegalArgumentException("Tên đăng nhập phải có ít nhất 3 ký tự.");
        if (tk.getMatKhau() == null || tk.getMatKhau().trim().isEmpty())
            throw new IllegalArgumentException("Mật khẩu không được để trống.");
        if (tk.getMatKhau().length() < 6)
            throw new IllegalArgumentException("Mật khẩu phải có ít nhất 6 ký tự.");
    }

    public String checkLogin(String username, char[] passwordInput) {
        if (username == null || username.isEmpty() || passwordInput.length == 0) return "ERROR";

        try {
            Map<String, Object> userInfo = taiKhoanDAO.getLoginInfo(username);
            if (userInfo == null) return "WRONG_PASS";

            String dbPassword = (String) userInfo.get("matKhau");
            String inputPassword = new String(passwordInput);

            if (!inputPassword.equals(dbPassword)) return "WRONG_PASS";

            boolean isActive = (Boolean) userInfo.get("hoatDong");
            if (!isActive) return "LOCKED";

            UserSession.getInstance().login(
                (Integer) userInfo.get("idTaiKhoan"),
                (String) userInfo.get("tenNhanVien"),
                (String) userInfo.get("tenVaiTro")
            );
            return "SUCCESS";

        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        } finally {
            Arrays.fill(passwordInput, '0');
        }
    }
}