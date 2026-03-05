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

    // Lấy tất cả tài khoản
    public List<TaiKhoan> getAll() {
        return taiKhoanDAO.getAll();
    }

    // Lấy tài khoản theo ID
    public TaiKhoan getById(int id) {
        if (id <= 0) return null;
        return taiKhoanDAO.getById(id);
    }

    // Thêm tài khoản mới
    public boolean add(TaiKhoan tk) {
        if (!validate(tk)) {
            return false;
        }
        return taiKhoanDAO.insert(tk);
    }

    // Cập nhật tài khoản
    public boolean update(TaiKhoan tk) {
        if (!validate(tk)) {
            return false;
        }
        return taiKhoanDAO.update(tk);
    }

    // Xóa tài khoản
    public boolean delete(int id) {
        if (id <= 0) return false;
        return taiKhoanDAO.delete(id);
    }

    // Tìm kiếm tài khoản
    public List<TaiKhoan> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAll();
        }
        return taiKhoanDAO.search(keyword);
    }

    /* ==================== VALIDATION ==================== */
    
    private boolean validate(TaiKhoan tk) {
        if (tk == null) return false;

        // Tên đăng nhập không được để trống
        if (tk.getTaiKhoan() == null || tk.getTaiKhoan().trim().isEmpty()) {
            System.err.println("Lỗi: Tên đăng nhập không được để trống.");
            return false;
        }

        // Tên đăng nhập phải có ít nhất 3 ký tự
        if (tk.getTaiKhoan().length() < 3) {
            System.err.println("Lỗi: Tên đăng nhập phải có ít nhất 3 ký tự.");
            return false;
        }

        // Mật khẩu không được để trống
        if (tk.getMatKhau() == null || tk.getMatKhau().trim().isEmpty()) {
            System.err.println("Lỗi: Mật khẩu không được để trống.");
            return false;
        }

        // Mật khẩu phải có ít nhất 6 ký tự
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
                return "WRONG_PASS"; // Sai tên đăng nhập
            }

            // Lấy mật khẩu từ DB và từ ô nhập liệu
            String dbPassword = (String) userInfo.get("matKhau");
            String inputPassword = new String(passwordInput);

            // TỐI GIẢN: So sánh 2 chuỗi bằng equals()
            if (inputPassword.equals(dbPassword)) {

                // Kiểm tra tài khoản có bị khóa không
                boolean isActive = (Boolean) userInfo.get("hoatDong");
                if (!isActive) return "LOCKED";

                // Đăng nhập thành công -> Lưu vào Session
                UserSession.getInstance().login(
                        (Integer) userInfo.get("idTaiKhoan"),
                        (String) userInfo.get("tenNhanVien"),
                        (String) userInfo.get("tenVaiTro")
                );
                return "SUCCESS";

            } else {
                return "WRONG_PASS"; // Sai mật khẩu
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        } finally {
            Arrays.fill(passwordInput, '0'); // Xóa RAM
        }
    }
}