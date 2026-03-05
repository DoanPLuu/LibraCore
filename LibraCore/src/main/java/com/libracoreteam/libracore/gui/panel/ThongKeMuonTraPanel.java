package com.libracoreteam.libracore.gui.panel;

import com.libracoreteam.libracore.bus.ThongKeMuonTraBUS;
import com.toedter.calendar.JDateChooser;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ThongKeMuonTraPanel extends JPanel {

    private final JDateChooser dateFrom;
    private final JDateChooser dateTo;
    private final JButton btnThongKe;

    private JFreeChart chart;
    private ChartPanel chartPanel;
    private DefaultCategoryDataset dataset;

    private final DefaultTableModel tblModel;
    private final JTable table;

    private final ThongKeMuonTraBUS bus = new ThongKeMuonTraBUS();
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public ThongKeMuonTraPanel() {
        setLayout(new BorderLayout(0, 0));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        // ── Toolbar ──────────────────────────────────────────────────────────
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        toolbar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0xDDDDDD)));

        LocalDate today = LocalDate.now();
        LocalDate weekAgo = today.minusDays(6);

        dateFrom = new JDateChooser();
        dateFrom.setDate(Date.valueOf(weekAgo));
        dateFrom.setPreferredSize(new Dimension(120, 32));
        dateFrom.setDateFormatString("dd/MM/yyyy");

        dateTo = new JDateChooser();
        dateTo.setDate(Date.valueOf(today));
        dateTo.setPreferredSize(new Dimension(120, 32));
        dateTo.setDateFormatString("dd/MM/yyyy");

        btnThongKe = new JButton("Thống kê");
        btnThongKe.setPreferredSize(new Dimension(100, 32));
        btnThongKe.setBackground(new Color(0x1565C0));
        btnThongKe.setForeground(Color.WHITE);
        btnThongKe.setFocusPainted(false);
        btnThongKe.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        toolbar.add(new JLabel("Từ ngày:"));
        toolbar.add(dateFrom);
        toolbar.add(new JLabel("Đến ngày:"));
        toolbar.add(dateTo);
        toolbar.add(btnThongKe);

        add(toolbar, BorderLayout.NORTH);

        // ── Chart ─────────────────────────────────────────────────────────────
        dataset = new DefaultCategoryDataset();
        chart = ChartFactory.createBarChart(
                "Thống kê Số Sách Mượn & Trả", null, "Số lượng sách", dataset,
                PlotOrientation.VERTICAL, true, true, false);
        
        styleChart(chart);
        chartPanel = new ChartPanel(chart);
        chartPanel.setMouseWheelEnabled(true);

        // ── Table ─────────────────────────────────────────────────────────────
        tblModel = new DefaultTableModel(
                new Object[]{"Mã Phiếu", "Tên độc giả", "Số lượng sách", "Ngày mượn", "Ngày trả", "Trạng thái"}, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(tblModel);
        table.setRowHeight(28);
        table.getTableHeader().setReorderingAllowed(false);
        table.setFillsViewportHeight(true);

        JScrollPane tableScroll = new JScrollPane(table);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, chartPanel, tableScroll);
        splitPane.setResizeWeight(0.60);
        splitPane.setDividerSize(6);
        splitPane.setBorder(null);

        add(splitPane, BorderLayout.CENTER);

        // ── Events ───────────────────────────────────────────────────────────
        btnThongKe.addActionListener(e -> runThongKe());
        runThongKe();
    }

    private void runThongKe() {
        java.util.Date dFrom = dateFrom.getDate();
        java.util.Date dTo = dateTo.getDate();
        if (dFrom == null || dTo == null) return;

        LocalDate from = dFrom.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        LocalDate to = dTo.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        
        if (from.isAfter(to)) {
            JOptionPane.showMessageDialog(this, "Từ ngày không được sau Đến ngày.");
            return;
        }

        try {
            List<Object[]> rows = bus.getThongKeMuonTra(from, to);
            String title = String.format("Thống kê từ %s đến %s", from.format(FMT), to.format(FMT));
            
            updateChart(rows, from, to, title);
            updateTable(rows);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi thống kê: " + ex.getMessage());
        }
    }

    private void updateChart(List<Object[]> rows, LocalDate from, LocalDate to, String title) {
        dataset.clear();
        
        // Chỉ dùng 2 biến tổng thay vì chia theo từng ngày
        int tongDangMuon = 0;
        int tongDaTra = 0;

        for (Object[] row : rows) {
            java.sql.Date sqlNgayMuon = (java.sql.Date) row[2];
            java.sql.Date sqlNgayTra = (java.sql.Date) row[3];
            String trangThai = (String) row[4];
            int soLuongSach = (Integer) row[5]; 

            // 1. Chỉ sách Đang Mượn / Quá Hạn -> Quét theo Ngày Mượn
            if (("DangMuon".equals(trangThai) || "QuaHen".equals(trangThai)) && sqlNgayMuon != null) {
                LocalDate d = sqlNgayMuon.toLocalDate();
                if (!d.isBefore(from) && !d.isAfter(to)) {
                    tongDangMuon += soLuongSach; // Cộng dồn vào biến tổng
                }
            }
            
            // 2. Chỉ sách Đã Trả -> Quét theo Ngày Trả
            if ("DaTra".equals(trangThai) && sqlNgayTra != null) {
                LocalDate d = sqlNgayTra.toLocalDate();
                if (!d.isBefore(from) && !d.isAfter(to)) {
                    tongDaTra += soLuongSach; // Cộng dồn vào biến tổng
                }
            }
        }

        // Đưa 2 cột tổng lên biểu đồ, gộp chung vào 1 nhóm "Tổng cộng"
        String label = "Tổng cộng";
        dataset.addValue(tongDangMuon, "Số sách đang mượn", label);
        dataset.addValue(tongDaTra, "Số sách đã trả", label);
        
        chart.setTitle(title);
        chartPanel.repaint();
    }

    private void updateTable(List<Object[]> rows) {
        tblModel.setRowCount(0);

        for (Object[] row : rows) {
            String ngayMuon = row[2] != null ? ((java.sql.Date) row[2]).toLocalDate().format(FMT) : "";
            String ngayTra = row[3] != null ? ((java.sql.Date) row[3]).toLocalDate().format(FMT) : "Chưa trả";
            
            String ttStr = (String) row[4];
            if ("DangMuon".equals(ttStr)) ttStr = "Đang mượn";
            else if ("DaTra".equals(ttStr)) ttStr = "Đã trả";
            else if ("QuaHen".equals(ttStr)) ttStr = "Quá hạn"; 
            else if ("DaHuy".equals(ttStr)) ttStr = "Đã hủy";
            
            int soLuongSach = (Integer) row[5];

            tblModel.addRow(new Object[]{ "PM" + row[0], row[1], soLuongSach, ngayMuon, ngayTra, ttStr });
        }
    }

    private void styleChart(JFreeChart c) {
        c.setBackgroundPaint(Color.WHITE);
        CategoryPlot plot = c.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(new Color(0xE0E0E0));

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        
        // ĐÃ CHỈNH SỬA: Tăng độ rộng cột lên (0.2) để cột to hơn vì bây giờ chỉ có 2 cột
        renderer.setMaximumBarWidth(0.20); 
        renderer.setItemMargin(0.05); 
        
        renderer.setSeriesPaint(0, new Color(0x2196F3)); // Xanh dương cho Đang mượn
        renderer.setSeriesPaint(1, new Color(0x4CAF50)); // Xanh lá cho Đã trả

        CategoryAxis domainAxis = plot.getDomainAxis();
        // ĐÃ CHỈNH SỬA: Hiển thị lại chữ ở trục X (Chữ "Tổng cộng")
        domainAxis.setTickLabelsVisible(true);
        domainAxis.setTickLabelFont(new Font("Segoe UI", Font.BOLD, 14));

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits()); 
    }
}