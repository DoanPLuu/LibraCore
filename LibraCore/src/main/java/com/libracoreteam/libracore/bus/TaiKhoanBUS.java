package com.libracoreteam.libracore.bus;

import com.libracoreteam.libracore.dao.TaiKhoanDAO;
import com.libracoreteam.libracore.util.UserSession;
import java.util.Map;
import java.util.Arrays;

public class TaiKhoanBUS {

    private final TaiKhoanDAO taiKhoanDAO;

    public TaiKhoanBUS() {
        this.taiKhoanDAO = new TaiKhoanDAO();
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