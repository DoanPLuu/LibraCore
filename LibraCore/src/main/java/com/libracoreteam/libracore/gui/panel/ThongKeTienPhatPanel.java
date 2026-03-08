package com.libracoreteam.libracore.gui.panel;

import com.libracoreteam.libracore.bus.ThongKeTienPhatBUS;
import com.toedter.calendar.JDateChooser;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
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

public class ThongKeTienPhatPanel extends JPanel {

    private final JDateChooser dateFrom;
    private final JDateChooser dateTo;
    private final JButton btnThongKe;

    private JFreeChart chart;
    private ChartPanel chartPanel;
    private DefaultCategoryDataset dataset;

    private final DefaultTableModel tblModel;
    private final JTable table;
    private final ThongKeTienPhatBUS bus = new ThongKeTienPhatBUS();
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public ThongKeTienPhatPanel() {
        setLayout(new BorderLayout(0, 0));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

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

        dataset = new DefaultCategoryDataset();
        chart = ChartFactory.createBarChart(
                "Biểu đồ Thống kê Tiền phạt", null, "Số tiền (VNĐ)", dataset,
                PlotOrientation.VERTICAL, true, true, false);
        
        styleChart(chart);
        chartPanel = new ChartPanel(chart);
        chartPanel.setMouseWheelEnabled(true);

        tblModel = new DefaultTableModel(
                new Object[]{"Mã Phiếu phạt", "Ngày lập", "Lý do phạt", "Trạng thái", "Tổng tiền phạt"}, 0) {
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
            List<Object[]> rows = bus.getThongKeTienPhat(from, to);
            String title = String.format("Thống kê từ %s đến %s", from.format(FMT), to.format(FMT));
            
            updateChart(rows, title);
            updateTable(rows);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi thống kê: " + ex.getMessage());
        }
    }

    private void updateChart(List<Object[]> rows, String title) {
        dataset.clear();

        double tongDaThu = 0.0;
        double tongChuaThu = 0.0;

        for (Object[] row : rows) {
            String trangThai = (String) row[3];
            double tien = 0.0;
            
            if (row[4] != null) {
                tien = Double.parseDouble(row[4].toString()); 
            }

            if ("DaThu".equals(trangThai)) {
                tongDaThu += tien;
            } else if ("ChuaThu".equals(trangThai)) {
                tongChuaThu += tien;
            }
        }

        String label = "Tổng cộng";
        dataset.addValue(tongDaThu, "Đã thu", label);
        dataset.addValue(tongChuaThu, "Chưa thu", label);
        dataset.addValue(tongDaThu + tongChuaThu, "Tổng tiền phạt", label);

        chart.setTitle(title);
        chartPanel.repaint();
    }

    private void updateTable(List<Object[]> rows) {
        tblModel.setRowCount(0);
        java.text.DecimalFormat df = new java.text.DecimalFormat("#,###");

        for (Object[] row : rows) {
            String dateStr = row[1] != null ? ((java.sql.Date) row[1]).toLocalDate().format(FMT) : "";
            
            double tien = 0.0;
            if (row[4] != null) {
                tien = Double.parseDouble(row[4].toString());
            }
            String tienStr = df.format(tien) + " đ";

            String ttStr = (String) row[3];
            if ("DaThu".equals(ttStr)) ttStr = "Đã thu";
            else if ("ChuaThu".equals(ttStr)) ttStr = "Chưa thu";
            else if ("DaHuy".equals(ttStr)) ttStr = "Đã hủy";

            tblModel.addRow(new Object[]{ "PP" + row[0], dateStr, row[2], ttStr, tienStr });
        }
    }

    private void styleChart(JFreeChart c) {
        c.setBackgroundPaint(Color.WHITE);
        CategoryPlot plot = c.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(new Color(0xE0E0E0));

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setMaximumBarWidth(0.20); 
        renderer.setItemMargin(0.05); 
        
        renderer.setSeriesPaint(0, new Color(0x4CAF50));
        renderer.setSeriesPaint(1, new Color(0xFF9800)); 
        renderer.setSeriesPaint(2, new Color(0x2196F3)); 

        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setTickLabelsVisible(true);
        domainAxis.setTickLabelFont(new Font("Segoe UI", Font.BOLD, 14));
    }
}