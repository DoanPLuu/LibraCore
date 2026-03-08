package com.libracoreteam.libracore.bus;

import com.libracoreteam.libracore.dao.TaiKhoanDAO;
import com.libracoreteam.libracore.model.TaiKhoan;
import com.libracoreteam.libracore.util.UserSession;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

public class TaiKhoanBUS {

    private final TaiKhoanDAO taiKhoanDAO;

    public TaiKhoanBUS() {
        this.taiKhoanDAO = new TaiKhoanDAO();
    }

    public List<TaiKhoan> getAll() {
        return taiKhoanDAO.getAll();
    }

    public TaiKhoan getById(int id) {
        if (id <= 0) return null;
        return taiKhoanDAO.getById(id);
    }

    public boolean add(TaiKhoan tk) {
        if (!validate(tk)) {
            return false;
        }
        return taiKhoanDAO.insert(tk);
    }

    public boolean update(TaiKhoan tk) {
        if (!validate(tk)) {
            return false;
        }
        return taiKhoanDAO.update(tk);
    }

    public boolean delete(int id) {
        if (id <= 0) return false;
        return taiKhoanDAO.delete(id);
    }

    public List<TaiKhoan> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAll();
        }
        return taiKhoanDAO.search(keyword);
    }

    
    private boolean validate(TaiKhoan tk) {
        if (tk == null) return false;

        if (tk.getTaiKhoan() == null || tk.getTaiKhoan().trim().isEmpty()) {
            System.err.println("Lỗi: Tên đăng nhập không được để trống.");
            return false;
        }

        if (tk.getTaiKhoan().length() < 3) {
            System.err.println("Lỗi: Tên đăng nhập phải có ít nhất 3 ký tự.");
            return false;
        }

        if (tk.getMatKhau() == null || tk.getMatKhau().trim().isEmpty()) {
            System.err.println("Lỗi: Mật khẩu không được để trống.");
            return false;
        }

        if (tk.getMatKhau().length() < 6) {
            System.err.println("Lỗi: Mật khẩu phải có ít nhất 6 ký tự.");
            return false;
        }

        return true;
    }

    public String checkLogin(String username, char[] passwordInput) {
        if (username.isEmpty() || passwordInput.length == 0) return "ERROR";

        try {
            Map<String, Object> userInfo = taiKhoanDAO.getLoginInfo(username);

            if (userInfo == null) {
                return "WRONG_PASS"; 
            }

            String dbPassword = (String) userInfo.get("matKhau");
            String inputPassword = new String(passwordInput);

            if (inputPassword.equals(dbPassword)) {

                boolean isActive = (Boolean) userInfo.get("hoatDong");
                if (!isActive) return "LOCKED";

                UserSession.getInstance().login(
                        (Integer) userInfo.get("idTaiKhoan"),
                        (String) userInfo.get("tenNhanVien"),
                        (String) userInfo.get("tenVaiTro")
                );
                return "SUCCESS";

            } else {
                return "WRONG_PASS"; 
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        } finally {
            Arrays.fill(passwordInput, '0');
        }
    }
}