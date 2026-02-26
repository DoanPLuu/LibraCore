package com.libracoreteam.libracore.util;

/**
 * Lớp dùng để lưu trữ thông tin của người dùng hiện tại đang đăng nhập.
 * Sử dụng Singleton Pattern để đảm bảo chỉ có 1 phiên làm việc duy nhất.
 */
public class UserSession {
    private static UserSession instance;

    private int idTaiKhoan;
    private String tenNhanVien;
    private String vaiTro;

    // Constructor private để không cho phép tạo bằng từ khóa 'new' bên ngoài
    private UserSession() {}

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    // Hàm nạp dữ liệu khi đăng nhập thành công
    public void login(int idTaiKhoan, String tenNhanVien, String vaiTro) {
        this.idTaiKhoan = idTaiKhoan;
        this.tenNhanVien = tenNhanVien;
        this.vaiTro = vaiTro;
    }

    // Hàm xóa dữ liệu khi đăng xuất
    public void logout() {
        this.idTaiKhoan = 0;
        this.tenNhanVien = null;
        this.vaiTro = null;
    }

    // Các hàm Getters để lấy thông tin ra sử dụng ở các màn hình khác
    public int getIdTaiKhoan() { return idTaiKhoan; }
    public String getTenNhanVien() { return tenNhanVien; }
    public String getVaiTro() { return vaiTro; }
}