/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.libracoreteam.libracore.gui.panel;

/**
 *
 * @author Sang
 */

import com.libracoreteam.libracore.bus.ThongKeTienPhatBUS;
import java.awt.Color;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ThongKeTienPhatPanel extends javax.swing.JPanel {

    private final ThongKeTienPhatBUS bus = new ThongKeTienPhatBUS();
    
    // Biến cho Biểu đồ
    private org.jfree.chart.JFreeChart chart;
    private org.jfree.chart.ChartPanel chartPanel;
    private org.jfree.data.category.DefaultCategoryDataset dataset;

    public ThongKeTienPhatPanel() {
        initComponents();
        setupUI(); 
        runThongKe(); 
    }

    private void setupUI() {
        // 1. Ẩn ComboBox loại thống kê đi theo yêu cầu
        jLabel3.setVisible(false);
        jComboBoxchucnang.setVisible(false);

        // 2. Cài đặt Bảng (JTable)
        jTableTienPhat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {"Mã phiếu phạt", "Ngày lập", "Lý do phạt", "Trạng thái", "Tổng tiền phạt"}
        ) {
            boolean[] canEdit = new boolean [] {false, false, false, false, false};
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableTienPhat.setRowHeight(30);

        // 3. Cài đặt ngày mặc định (Từ 7 ngày trước đến hôm nay)
        LocalDate today = LocalDate.now();
        LocalDate weekAgo = today.minusDays(6);
        jDateChooser1.setDate(java.sql.Date.valueOf(weekAgo));
        jDateChooser2.setDate(java.sql.Date.valueOf(today));
        jDateChooser1.setDateFormatString("dd/MM/yyyy");
        jDateChooser2.setDateFormatString("dd/MM/yyyy");

        // 4. Khởi tạo Biểu đồ (Sẽ tự động có chú thích Legend màu sắc)
        dataset = new org.jfree.data.category.DefaultCategoryDataset();
        chart = org.jfree.chart.ChartFactory.createBarChart(
                "Biểu đồ thống kê tiền phạt", "Ngày", "Số tiền (VNĐ)", dataset,
                org.jfree.chart.plot.PlotOrientation.VERTICAL, true, true, false);

        chart.setBackgroundPaint(Color.WHITE);
        org.jfree.chart.plot.CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(new Color(200, 200, 200));

        chartPanel = new org.jfree.chart.ChartPanel(chart);
        chartPanel.setMouseWheelEnabled(true);

        jPanelCenter.setLayout(new java.awt.BorderLayout());
        jPanelCenter.add(chartPanel, java.awt.BorderLayout.CENTER);
    }

    // ================== LOGIC THỐNG KÊ ==================
    
    private void runThongKe() {
        java.util.Date d1 = jDateChooser1.getDate();
        java.util.Date d2 = jDateChooser2.getDate();
        
        if (d1 == null || d2 == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn đủ Từ ngày và Đến ngày.");
            return;
        }
        
        LocalDate from = d1.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        LocalDate to = d2.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        
        if (from.isAfter(to)) {
            JOptionPane.showMessageDialog(this, "Từ ngày không được sau Đến ngày.");
            return;
        }

        try {
            // Lấy toàn bộ phiếu phạt trong khoảng thời gian
            List<Object[]> rows = bus.getThongKeTienPhat(from, to);
            
            String title = String.format("Thống kê từ %s đến %s", 
                    from.format(DateTimeFormatter.ofPattern("dd/MM")), 
                    to.format(DateTimeFormatter.ofPattern("dd/MM")));
            
            updateChart(rows, title);
            updateTable(rows);
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi thống kê: " + ex.getMessage());
        }
    }

    private void updateChart(List<Object[]> rows, String title) {
        dataset.clear();

        // Map để lưu trữ 3 thông số cho mỗi ngày
        Map<String, Double> mapDaThu = new LinkedHashMap<>();
        Map<String, Double> mapChuaThu = new LinkedHashMap<>();
        Map<String, Double> mapTong = new LinkedHashMap<>();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Lọc và cộng dồn dữ liệu
        for (Object[] row : rows) {
            java.sql.Date sqlDate = (java.sql.Date) row[1];
            String dateStr = sqlDate != null ? sqlDate.toLocalDate().format(fmt) : "N/A";
            String trangThai = (String) row[3];
            double tien = (Double) row[4];

            // Khởi tạo ngày trong map nếu chưa có
            mapDaThu.putIfAbsent(dateStr, 0.0);
            mapChuaThu.putIfAbsent(dateStr, 0.0);
            mapTong.putIfAbsent(dateStr, 0.0);

            // Cộng tiền dựa theo trạng thái
            if ("DaThu".equals(trangThai)) {
                mapDaThu.put(dateStr, mapDaThu.get(dateStr) + tien);
                mapTong.put(dateStr, mapTong.get(dateStr) + tien);
            } else if ("ChuaThu".equals(trangThai)) {
                mapChuaThu.put(dateStr, mapChuaThu.get(dateStr) + tien);
                mapTong.put(dateStr, mapTong.get(dateStr) + tien);
            }
            // (Đã hủy thì bỏ qua không cộng vào biểu đồ)
        }

        // Đổ dữ liệu vào biểu đồ (Gộp 3 cột vào cùng 1 ngày)
        for (String dateStr : mapTong.keySet()) {
            dataset.addValue(mapDaThu.get(dateStr), "Đã thu", dateStr);
            dataset.addValue(mapChuaThu.get(dateStr), "Chưa thu", dateStr);
            dataset.addValue(mapTong.get(dateStr), "Tổng tiền phạt", dateStr);
        }

        chart.setTitle(title);
        
        // Cài đặt màu sắc cho 3 cột
        org.jfree.chart.plot.CategoryPlot plot = chart.getCategoryPlot();
        org.jfree.chart.renderer.category.BarRenderer renderer = (org.jfree.chart.renderer.category.BarRenderer) plot.getRenderer();
        renderer.setMaximumBarWidth(0.04); 
        renderer.setItemMargin(0.05);
        
        renderer.setSeriesPaint(0, new Color(0x4CAF50)); // Cột 1: Xanh lá (Đã thu)
        renderer.setSeriesPaint(1, new Color(0xFF9800)); // Cột 2: Cam (Chưa thu)
        renderer.setSeriesPaint(2, new Color(0x2196F3)); // Cột 3: Xanh dương (Tổng tiền)

        chartPanel.repaint();
    }

    private void updateTable(List<Object[]> rows) {
        DefaultTableModel model = (DefaultTableModel) jTableTienPhat.getModel();
        model.setRowCount(0);
        
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        java.text.DecimalFormat df = new java.text.DecimalFormat("#,###");

        for (Object[] row : rows) {
            java.sql.Date sqlDate = (java.sql.Date) row[1];
            String dateStr = sqlDate != null ? sqlDate.toLocalDate().format(fmt) : "";
            String tienStr = df.format((Double) row[4]) + " đ";

            // Hiển thị trạng thái thân thiện hơn
            String ttStr = (String) row[3];
            if ("DaThu".equals(ttStr)) ttStr = "Đã thu";
            else if ("ChuaThu".equals(ttStr)) ttStr = "Chưa thu";
            else if ("DaHuy".equals(ttStr)) ttStr = "Đã hủy";

            model.addRow(new Object[]{
                row[0],      // Mã PP
                dateStr,     // Ngày lập
                row[2],      // Lý do
                ttStr,       // Trạng thái
                tienStr      // Tổng tiền
            });
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelTop = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        jComboBoxchucnang = new javax.swing.JComboBox<>();
        jButtonTK = new javax.swing.JButton();
        jPanelBottom = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableTienPhat = new javax.swing.JTable();
        jPanelCenter = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        jPanelTop.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setText("Từ ngày:");
        jPanelTop.add(jLabel1);
        jPanelTop.add(jDateChooser1);

        jLabel2.setText("Đến ngày:");
        jPanelTop.add(jLabel2);
        jPanelTop.add(jDateChooser2);

        jLabel3.setText("Loại thống kê:");
        jPanelTop.add(jLabel3);

        jComboBoxchucnang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBoxchucnang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxchucnangActionPerformed(evt);
            }
        });
        jPanelTop.add(jComboBoxchucnang);

        jButtonTK.setText("Thống kê");
        jButtonTK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonTKActionPerformed(evt);
            }
        });
        jPanelTop.add(jButtonTK);

        add(jPanelTop, java.awt.BorderLayout.PAGE_START);

        jPanelBottom.setLayout(new java.awt.BorderLayout());

        jTableTienPhat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTableTienPhat);

        jPanelBottom.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        add(jPanelBottom, java.awt.BorderLayout.PAGE_END);

        jPanelCenter.setLayout(new java.awt.BorderLayout());
        add(jPanelCenter, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBoxchucnangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxchucnangActionPerformed
    }

    private void jButtonTKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTKActionPerformed
        runThongKe();
    }


    private javax.swing.JButton jButtonTK;
    private javax.swing.JComboBox<String> jComboBoxchucnang;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanelBottom;
    private javax.swing.JPanel jPanelCenter;
    private javax.swing.JPanel jPanelTop;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableTienPhat;
}
