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

    private JTextField txtIdThe;
    private JTextField txtTenDocGia;
    private JTextField txtNgayHetHan;
    private JTextField txtTrangThai;

    private TheThanhVien selectedThe = null;

    public TheThanhVienPanel() {
        theThanhVienBUS = new TheThanhVienBUS();
        initComponents();
        loadData();
    }

    private void initComponents() {
        setLayout(new BorderLayout()); 
        setBackground(new Color(240, 240, 240));

        JPanel jPanelLeft = new JPanel(new BorderLayout());

        JPanel jPanelLeftTop = new JPanel(new BorderLayout());
        jPanelLeftTop.setBackground(new Color(255, 153, 153));

        JPanel jPanelCongCu = new JPanel(new BorderLayout());
        jPanelCongCu.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20));

        JPanel jPanelTimKiem = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        
        JTextField txtSearch = new JTextField();
        txtSearch.setPreferredSize(new Dimension(150, 40));
        txtSearch.putClientProperty("JTextField.placeholderText", "Tìm kiếm...");
        
        JButton btnSearch = new JButton();
        btnSearch.setPreferredSize(new Dimension(40, 40));
        btnSearch.setIcon(org.kordamp.ikonli.swing.FontIcon.of(org.kordamp.ikonli.fontawesome5.FontAwesomeSolid.SEARCH, 16, new Color(100, 100, 100)));
        
        JButton btnRefresh = new JButton();
        btnRefresh.setPreferredSize(new Dimension(40, 40));
        btnRefresh.setIcon(org.kordamp.ikonli.swing.FontIcon.of(org.kordamp.ikonli.fontawesome5.FontAwesomeSolid.SYNC_ALT, 16, new Color(100, 100, 100)));
        btnRefresh.addActionListener(e -> loadData());

        jPanelTimKiem.add(txtSearch);
        jPanelTimKiem.add(btnSearch);
        jPanelTimKiem.add(btnRefresh);

        jPanelCongCu.add(jPanelTimKiem, BorderLayout.WEST);
        jPanelLeftTop.add(jPanelCongCu, BorderLayout.CENTER);
        jPanelLeft.add(jPanelLeftTop, BorderLayout.NORTH);

        JPanel jPanelBoard = new JPanel(new BorderLayout());
        jPanelBoard.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        String[] columns = {"Mã thẻ", "Mã ĐG", "Tên Độc Giả", "Ngày cấp", "Ngày hết hạn", "Trạng thái"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        table.setRowHeight(34);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setReorderingAllowed(false);
        table.setSelectionBackground(new Color(220, 220, 220)); 
        table.setSelectionForeground(Color.BLACK);

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                showDetail(table.getSelectedRow());
            }
        });

        jPanelBoard.add(new JScrollPane(table), BorderLayout.CENTER);
        jPanelLeft.add(jPanelBoard, BorderLayout.CENTER);
        
        add(jPanelLeft, BorderLayout.CENTER);

        JPanel jPanelRight = new JPanel(new BorderLayout());
        jPanelRight.setBorder(BorderFactory.createEmptyBorder(10, 0, 50, 20));
        jPanelRight.setPreferredSize(new Dimension(306, 306)); 

        JPanel jPanelTop = new JPanel();
        JLabel jLabelTitle = new JLabel("THÔNG TIN THẺ");
        jLabelTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        jPanelTop.add(jLabelTitle);
        jPanelRight.add(jPanelTop, BorderLayout.PAGE_START);

        JPanel jPanelBottom = new JPanel();
        jPanelBottom.setLayout(new BoxLayout(jPanelBottom, BoxLayout.Y_AXIS));

        JPanel jPanelFields = new JPanel();
        jPanelFields.setLayout(new BoxLayout(jPanelFields, BoxLayout.Y_AXIS));

        txtIdThe = new JTextField(); 
        txtTenDocGia = new JTextField(); 
        txtNgayHetHan = new JTextField(); 
        txtTrangThai = new JTextField(); 

        jPanelFields.add(createFieldPanel("Mã thẻ:", txtIdThe));
        jPanelFields.add(createFieldPanel("Tên độc giả:", txtTenDocGia));
        jPanelFields.add(createFieldPanel("Ngày hết hạn:", txtNgayHetHan));
        jPanelFields.add(createFieldPanel("Trạng thái:", txtTrangThai));

        JPanel pnlButtons = new JPanel(new GridLayout(0, 1, 0, 10));
        pnlButtons.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        Dimension btnSize = new Dimension(0, 30); 

        JButton btnGiaHan = new JButton("Gia hạn 1 năm");
        btnGiaHan.setBackground(new Color(21, 110, 71)); btnGiaHan.setForeground(Color.WHITE);
        btnGiaHan.setPreferredSize(btnSize); btnGiaHan.addActionListener(e -> actionGiaHan());

        JButton btnGiaHanThang = new JButton("Gia hạn theo tháng");
        btnGiaHanThang.setBackground(new Color(21, 110, 71)); btnGiaHanThang.setForeground(Color.WHITE);
        btnGiaHanThang.setPreferredSize(btnSize); btnGiaHanThang.addActionListener(e -> actionGiaHanThang());

        JButton btnGiaHanNgay = new JButton("Chỉnh sửa hạn cụ thể");
        btnGiaHanNgay.setBackground(new Color(21, 110, 71)); btnGiaHanNgay.setForeground(Color.WHITE);
        btnGiaHanNgay.setPreferredSize(btnSize); btnGiaHanNgay.addActionListener(e -> actionGiaHanNgay());

        JButton btnKhoa = new JButton("Khóa / Mở khóa thẻ");
        btnKhoa.setBackground(new Color(220, 53, 69)); btnKhoa.setForeground(Color.WHITE);
        btnKhoa.setPreferredSize(btnSize); btnKhoa.addActionListener(e -> actionKhoaThe());

        pnlButtons.add(btnGiaHan);
        pnlButtons.add(btnGiaHanThang);
        pnlButtons.add(btnGiaHanNgay);
        pnlButtons.add(btnKhoa);
        
        jPanelFields.add(pnlButtons);
        jPanelBottom.add(jPanelFields);
        jPanelRight.add(jPanelBottom, BorderLayout.CENTER);

        add(jPanelRight, BorderLayout.EAST);
    }

    private JPanel createFieldPanel(String labelText, JTextField textField) {
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.setBorder(BorderFactory.createEmptyBorder(3, 0, 3, 0)); 
        
        JLabel label = new JLabel(labelText);
        textField.setEditable(false);
        textField.setFocusable(false);
        
        panel.add(label);
        panel.add(textField);
        return panel;
    }

    public void loadData() {
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

    private void showDetail(int row) {
        selectedThe = listThe.get(row);
        txtIdThe.setText(String.valueOf(selectedThe.getIdTheThanhVien()));
        txtTenDocGia.setText(theThanhVienBUS.getTenDocGia(selectedThe.getIdDocGia()));
        txtNgayHetHan.setText(selectedThe.getNgayHetHan().toString());
        txtTrangThai.setText(selectedThe.getTrangThai());
    }

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

        com.toedter.calendar.JDateChooser dateChooser = new com.toedter.calendar.JDateChooser();
        dateChooser.setDateFormatString("dd/MM/yyyy"); 
        dateChooser.setMinSelectableDate(new java.util.Date()); 

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Vui lòng chọn ngày hết hạn mới:"));
        panel.add(dateChooser);

        int result = JOptionPane.showConfirmDialog(this, panel, "Chỉnh sửa hạn cụ thể", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            java.util.Date selectedDate = dateChooser.getDate();
            
            if (selectedDate != null) {
                LocalDate newDate = selectedDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
                
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
            
            int ketQua = theThanhVienBUS.toggleLockCard(selectedThe);

            if (ketQua == 1) {
                JOptionPane.showMessageDialog(this, "Đã cập nhật trạng thái thẻ thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                loadData(); 
                
            } else if (ketQua == -1) {
                JOptionPane.showMessageDialog(this, 
                    "TỪ CHỐI MỞ KHÓA!\nHồ sơ độc giả của thẻ này đã bị xóa khỏi hệ thống.\nKhông thể cấp lại quyền sử dụng cho thẻ vô chủ.", 
                    "Cảnh báo bảo mật", JOptionPane.ERROR_MESSAGE);
                    
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi cập nhật CSDL!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}