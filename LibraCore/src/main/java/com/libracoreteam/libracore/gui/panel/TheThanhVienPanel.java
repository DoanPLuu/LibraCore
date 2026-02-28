package com.libracoreteam.libracore.gui.panel;

import com.libracoreteam.libracore.bus.TheThanhVienBUS;
import com.libracoreteam.libracore.model.TheThanhVien;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class TheThanhVienPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private TheThanhVienBUS theThanhVienBUS;
    private List<TheThanhVien> listThe;

    // Các component nhập liệu
    private JTextField txtIdThe;
    private JTextField txtTenDocGia;
    private JTextField txtNgayHetHan;
    private JTextField txtTrangThai;

    // Biến lưu thẻ đang chọn
    private TheThanhVien selectedThe = null;

    public TheThanhVienPanel() {
        theThanhVienBUS = new TheThanhVienBUS();
        initComponents();
        loadData();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);

        // 1. Tiêu đề
        JLabel lblTitle = new JLabel("QUẢN LÝ THẺ THÀNH VIÊN", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(0, 102, 51));
        add(lblTitle, BorderLayout.NORTH);

        // 2. Bảng danh sách (CENTER)
        String[] columns = {"Mã thẻ", "Mã ĐG", "Tên Độc Giả", "Ngày cấp", "Ngày hết hạn", "Trạng thái"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho sửa trực tiếp trên bảng
            }
        };
        table = new JTable(tableModel);
        table.setRowHeight(30);

        // Sự kiện click vào bảng
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                showDetail(table.getSelectedRow());
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // 3. Panel thao tác bên phải (EAST)
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        rightPanel.setPreferredSize(new Dimension(300, 0));

        // Form hiển thị thông tin
        rightPanel.add(new JLabel("Mã thẻ:"));
        txtIdThe = new JTextField();
        txtIdThe.setEditable(false);
        rightPanel.add(txtIdThe);
        rightPanel.add(Box.createVerticalStrut(10));

        rightPanel.add(new JLabel("Tên độc giả:"));
        txtTenDocGia = new JTextField();
        txtTenDocGia.setEditable(false);
        rightPanel.add(txtTenDocGia);
        rightPanel.add(Box.createVerticalStrut(10));

        rightPanel.add(new JLabel("Ngày hết hạn:"));
        txtNgayHetHan = new JTextField();
        txtNgayHetHan.setEditable(false);
        rightPanel.add(txtNgayHetHan);
        rightPanel.add(Box.createVerticalStrut(10));

        rightPanel.add(new JLabel("Trạng thái:"));
        txtTrangThai = new JTextField();
        txtTrangThai.setEditable(false);
        rightPanel.add(txtTrangThai);
        rightPanel.add(Box.createVerticalStrut(20));

        // ==========================================
        // KHU VỰC CÁC NÚT CHỨC NĂNG (ĐÃ BỔ SUNG)
        // ==========================================
        
        JButton btnGiaHan = new JButton("Gia hạn 1 năm");
        btnGiaHan.setBackground(new Color(0, 153, 76));
        btnGiaHan.setForeground(Color.WHITE);
        btnGiaHan.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnGiaHan.addActionListener(e -> actionGiaHan());
        rightPanel.add(btnGiaHan);
        rightPanel.add(Box.createVerticalStrut(10));

        JButton btnGiaHanThang = new JButton("Gia hạn theo tháng");
        btnGiaHanThang.setBackground(new Color(0, 153, 76));
        btnGiaHanThang.setForeground(Color.WHITE);
        btnGiaHanThang.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnGiaHanThang.addActionListener(e -> actionGiaHanThang());
        rightPanel.add(btnGiaHanThang);
        rightPanel.add(Box.createVerticalStrut(10));

        JButton btnGiaHanNgay = new JButton("Chỉnh sửa hạn cụ thể");
        btnGiaHanNgay.setBackground(new Color(0, 153, 76));
        btnGiaHanNgay.setForeground(Color.WHITE);
        btnGiaHanNgay.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnGiaHanNgay.addActionListener(e -> actionGiaHanNgay());
        rightPanel.add(btnGiaHanNgay);
        rightPanel.add(Box.createVerticalStrut(10));

        JButton btnKhoa = new JButton("Khóa / Mở khóa thẻ");
        btnKhoa.setBackground(new Color(204, 0, 0));
        btnKhoa.setForeground(Color.WHITE);
        btnKhoa.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnKhoa.addActionListener(e -> actionKhoaThe());
        rightPanel.add(btnKhoa);
        rightPanel.add(Box.createVerticalStrut(10));

        JButton btnLamMoi = new JButton("Làm mới danh sách");
        btnLamMoi.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnLamMoi.addActionListener(e -> loadData());
        rightPanel.add(btnLamMoi);

        add(rightPanel, BorderLayout.EAST);
    }

    // Load dữ liệu lên bảng
    private void loadData() {
        listThe = theThanhVienBUS.getAll();
        tableModel.setRowCount(0);
        for (TheThanhVien t : listThe) {
            String tenDG = theThanhVienBUS.getTenDocGia(t.getIdDocGia());
            tableModel.addRow(new Object[]{
                    t.getIdTheThanhVien(),
                    t.getIdDocGia(),
                    tenDG,
                    t.getNgayCap(),
                    t.getNgayHetHan(),
                    t.getTrangThai()
            });
        }
    }

    // Hiển thị chi tiết khi click vào bảng
    private void showDetail(int row) {
        selectedThe = listThe.get(row);
        txtIdThe.setText(String.valueOf(selectedThe.getIdTheThanhVien()));
        txtTenDocGia.setText(theThanhVienBUS.getTenDocGia(selectedThe.getIdDocGia()));
        txtNgayHetHan.setText(selectedThe.getNgayHetHan().toString());
        txtTrangThai.setText(selectedThe.getTrangThai());
    }

    // ==========================================
    // LOGIC CÁC NÚT BẤM
    // ==========================================

    private void actionGiaHan() {
        if (selectedThe == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn thẻ cần gia hạn!");
            return;
        }
        
        if ("BiKhoa".equals(selectedThe.getTrangThai())) {
            JOptionPane.showMessageDialog(this, 
                "TỪ CHỐI GIA HẠN!\nThẻ này đang bị khóa. Bạn phải mở khóa thẻ trước rồi mới được gia hạn.", 
                "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return; 
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Gia hạn thẻ cho độc giả này thêm 1 năm?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (theThanhVienBUS.giaHanThe(selectedThe, 1)) {
                JOptionPane.showMessageDialog(this, "Gia hạn thành công!");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi gia hạn thẻ!");
            }
        }
    }

    private void actionGiaHanThang() {
        if (selectedThe == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn thẻ cần gia hạn!");
            return;
        }
        
        if ("BiKhoa".equals(selectedThe.getTrangThai())) {
            JOptionPane.showMessageDialog(this, 
                "TỪ CHỐI GIA HẠN!\nThẻ này đang bị khóa. Bạn phải mở khóa thẻ trước rồi mới được gia hạn.", 
                "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return; 
        }

        String input = JOptionPane.showInputDialog(this, 
                "Nhập số tháng muốn gia hạn thêm:", "Gia hạn theo tháng", JOptionPane.QUESTION_MESSAGE);

        if (input != null && !input.trim().isEmpty()) {
            try {
                int soThang = Integer.parseInt(input.trim());
                if (soThang <= 0) {
                    JOptionPane.showMessageDialog(this, "Số tháng phải lớn hơn 0!");
                    return;
                }
                
                
                
                if (theThanhVienBUS.giaHanTheoThang(selectedThe, soThang)) {
                    JOptionPane.showMessageDialog(this, "Gia hạn thêm " + soThang + " tháng thành công!");
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(this, "Lỗi khi gia hạn thẻ!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Vui lòng chỉ nhập số (ví dụ: 6)!");
            }
        }
    }

    private void actionGiaHanNgay() {
        if (selectedThe == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn thẻ cần chỉnh sửa!");
            return;
        }
        
        if ("BiKhoa".equals(selectedThe.getTrangThai())) {
            JOptionPane.showMessageDialog(this, 
                "TỪ CHỐI GIA HẠN!\nThẻ này đang bị khóa. Bạn phải mở khóa thẻ trước rồi mới được gia hạn.", 
                "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return; 
        }

        // Tạo một cái Lịch (JDateChooser)
        com.toedter.calendar.JDateChooser dateChooser = new com.toedter.calendar.JDateChooser();
        dateChooser.setDateFormatString("dd/MM/yyyy"); // Format chuẩn Việt Nam
        // Chặn không cho chọn ngày trong quá khứ (Tùy chọn, cho xịn)
        dateChooser.setMinSelectableDate(new java.util.Date()); 

        // Nhét cái lịch vào một cái Panel nhỏ
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Vui lòng chọn ngày hết hạn mới:"));
        panel.add(dateChooser);

        // Hiển thị Panel đó lên bằng JOptionPane
        int result = JOptionPane.showConfirmDialog(this, panel, "Chỉnh sửa hạn cụ thể", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            java.util.Date selectedDate = dateChooser.getDate();
            
            if (selectedDate != null) {
                // Ép kiểu từ Date (của Lịch) sang LocalDate (của hệ thống)
                LocalDate newDate = selectedDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
                
                // CODE CHUẨN 3 LỚP: Bồi bàn gọi Bếp trưởng (BUS), KHÔNG tự sửa DTO
                if (theThanhVienBUS.giaHanDenNgayCuThe(selectedThe, newDate)) {
                    JOptionPane.showMessageDialog(this, "Cập nhật ngày hết hạn thành công!");
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(this, "Lỗi khi gia hạn thẻ!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Bạn chưa chọn ngày nào cả!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void actionKhoaThe() {
        if (selectedThe == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn thẻ cần thao tác!");
            return;
        }

        String action = selectedThe.getTrangThai().equals("BiKhoa") ? "MỞ KHÓA" : "KHÓA";
        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn " + action + " thẻ này?", "Xác nhận", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            
            // Gọi Bếp trưởng ra hỏi chuyện
            int ketQua = theThanhVienBUS.toggleLockCard(selectedThe);

            // Bồi bàn dựa vào câu trả lời để báo cho khách
            if (ketQua == 1) {
                JOptionPane.showMessageDialog(this, "Đã cập nhật trạng thái thẻ thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                loadData(); // Load lại bảng
                
            } else if (ketQua == -1) {
                // CHÍNH LÀ CÁI THÔNG BÁO ÔNG ĐANG TÌM ĐÂY:
                JOptionPane.showMessageDialog(this, 
                    "TỪ CHỐI MỞ KHÓA!\nHồ sơ độc giả của thẻ này đã bị xóa khỏi hệ thống.\nKhông thể cấp lại quyền sử dụng cho thẻ vô chủ.", 
                    "Cảnh báo bảo mật", JOptionPane.ERROR_MESSAGE);
                    
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi cập nhật CSDL!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}