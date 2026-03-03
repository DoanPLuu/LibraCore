package com.libracoreteam.libracore.gui.panel;

import com.libracoreteam.libracore.bus.ThongKeBUS;
import com.toedter.calendar.JDateChooser;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
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

/**
 * Panel thống kê sách theo ngày.
 * Layout hoàn toàn bằng code tay, không dùng NetBeans Form Editor.
 */
public class ThongKeSachPanel extends JPanel {

    // ─── Loại thống kê ──────────────────────────────────────────────────────
    private static final String[] LOAI_THONG_KE = {
            "Tổng số sách",
            "Sách đang mượn",
            "Sách nhập kho",
            "Sách hỏng / mất"
    };

    // Màu tương ứng từng loại (chart bar color)
    private static final Color[] BAR_COLORS = {
            new Color(0x2196F3), // xanh dương — Tổng số sách
            new Color(0x4CAF50), // xanh lá — Đang mượn
            new Color(0xFF9800), // cam — Nhập kho
            new Color(0xF44336), // đỏ — Hỏng/mất
    };

    // ─── Components ─────────────────────────────────────────────────────────
    private final JDateChooser dateFrom;
    private final JDateChooser dateTo;
    private final JComboBox<String> comboLoai;
    private final JButton btnThongKe;

    private JFreeChart chart;
    private ChartPanel chartPanel;
    private DefaultCategoryDataset dataset;

    private final DefaultTableModel tblModel;
    private final JTable table;

    private final ThongKeBUS bus = new ThongKeBUS();

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FMT_SHORT = DateTimeFormatter.ofPattern("dd/MM");

    // ─── Constructor ─────────────────────────────────────────────────────────
    public ThongKeSachPanel() {
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

        comboLoai = new JComboBox<>(LOAI_THONG_KE);
        comboLoai.setPreferredSize(new Dimension(150, 32));

        btnThongKe = new JButton("Thống kê");
        btnThongKe.setPreferredSize(new Dimension(100, 32));
        btnThongKe.setBackground(new Color(0x1565C0));
        btnThongKe.setForeground(Color.WHITE);
        btnThongKe.setFocusPainted(false);
        btnThongKe.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        toolbar.add(new JLabel("Từ ngày"));
        toolbar.add(dateFrom);
        toolbar.add(new JLabel("Đến ngày"));
        toolbar.add(dateTo);
        toolbar.add(comboLoai);
        toolbar.add(btnThongKe);

        add(toolbar, BorderLayout.NORTH);

        // ── Chart ─────────────────────────────────────────────────────────────
        dataset = new DefaultCategoryDataset();
        chart = ChartFactory.createBarChart(
                "Thống kê",
                null,
                "Số lượng",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);
        styleChart(chart, 0);
        chartPanel = new ChartPanel(chart);
        chartPanel.setMouseWheelEnabled(true);

        // ── Table ─────────────────────────────────────────────────────────────
        tblModel = new DefaultTableModel(
                new Object[] { "Tên sách", "Thể loại", "Số lượng", "Ngày" }, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        table = new JTable(tblModel);
        table.setRowHeight(28);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(true);
        table.setFillsViewportHeight(true);

        // set col widths
        table.getColumnModel().getColumn(0).setPreferredWidth(250);
        table.getColumnModel().getColumn(1).setPreferredWidth(180);
        table.getColumnModel().getColumn(2).setPreferredWidth(80);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);

        JScrollPane tableScroll = new JScrollPane(table);

        // ── SplitPane chart + table ───────────────────────────────────────────
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, chartPanel, tableScroll);
        splitPane.setResizeWeight(0.60);
        splitPane.setDividerSize(6);
        splitPane.setBorder(null);

        add(splitPane, BorderLayout.CENTER);

        // ── Events ───────────────────────────────────────────────────────────
        btnThongKe.addActionListener(e -> runThongKe());

        // Load mặc định
        runThongKe();
    }

    // ─── Core logic ──────────────────────────────────────────────────────────

    private void runThongKe() {
        LocalDate from = getDateFrom();
        LocalDate to = getDateTo();
        if (from == null || to == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn đủ Từ ngày và Đến ngày.");
            return;
        }
        if (from.isAfter(to)) {
            JOptionPane.showMessageDialog(this, "Từ ngày không được sau Đến ngày.");
            return;
        }

        int loaiIdx = comboLoai.getSelectedIndex();
        List<Object[]> rows;
        try {
            switch (loaiIdx) {
                case 0:
                    rows = bus.getTongSoSachTheoSach(from, to);
                    break;
                case 1:
                    rows = bus.getSachDangMuon(from, to);
                    break;
                case 2:
                    rows = bus.getSachNhapKho(from, to);
                    break;
                case 3:
                    rows = bus.getSachHongHoacMat(from, to);
                    break;
                default:
                    rows = bus.getTongSoSachTheoSach(from, to);
                    break;
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi thống kê: " + ex.getMessage());
            return;
        }

        String title = String.format("Thống kê từ %s đến %s",
                from.format(FMT_SHORT), to.format(FMT_SHORT));
        updateChart(rows, title, loaiIdx);
        updateTable(rows);
    }

    private void updateChart(List<Object[]> rows, String title, int colorIdx) {
        dataset.clear();
        String seriesKey = LOAI_THONG_KE[colorIdx];

        for (Object[] row : rows) {
            String tenSach = truncate((String) row[0], 25);
            int soLuong = row[2] instanceof Number ? ((Number) row[2]).intValue() : 0;
            dataset.addValue(soLuong, seriesKey, tenSach);
        }

        chart.setTitle(title);
        styleChart(chart, colorIdx);
        chartPanel.repaint();
    }

    private void updateTable(List<Object[]> rows) {
        tblModel.setRowCount(0);
        DateTimeFormatter fullFmt = DateTimeFormatter.ofPattern("d/M/yyyy");
        for (Object[] row : rows) {
            String tenSach = (String) row[0];
            String theLoai = (String) row[1];
            int soLuong = row[2] instanceof Number ? ((Number) row[2]).intValue() : 0;
            Object ngayRaw = row[3];
            String ngay = "";
            if (ngayRaw instanceof java.sql.Date) {
                ngay = ((java.sql.Date) ngayRaw).toLocalDate().format(fullFmt);
            } else if (ngayRaw instanceof java.sql.Timestamp) {
                ngay = ((java.sql.Timestamp) ngayRaw).toLocalDateTime().toLocalDate().format(fullFmt);
            }
            tblModel.addRow(new Object[] { tenSach, theLoai, soLuong, ngay });
        }
    }

    // ─── Chart styling ───────────────────────────────────────────────────────

    private void styleChart(JFreeChart c, int colorIdx) {
        c.setBackgroundPaint(Color.WHITE);
        c.setTextAntiAlias(true);
        c.getTitle().setFont(new Font("Segoe UI", Font.BOLD, 14));

        CategoryPlot plot = c.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(new Color(0xE0E0E0));
        plot.setOutlineVisible(false);

        // Renderer
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setMaximumBarWidth(0.08);
        renderer.setSeriesPaint(0, BAR_COLORS[colorIdx]);
        renderer.setShadowVisible(false);
        renderer.setDrawBarOutline(false);

        // Category axis
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setVisible(rows() > 0);
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
        domainAxis.setTickLabelFont(new Font("Segoe UI", Font.PLAIN, 10));
        domainAxis.setLabel("Tên sách");

        // Value axis
        plot.getRangeAxis().setTickLabelFont(new Font("Segoe UI", Font.PLAIN, 11));
        plot.getRangeAxis().setLabel("Số lượng");
    }

    private int rows() {
        return dataset.getColumnCount();
    }

    // ─── Helpers ─────────────────────────────────────────────────────────────

    private LocalDate getDateFrom() {
        java.util.Date d = dateFrom.getDate();
        return d == null ? null : d.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
    }

    private LocalDate getDateTo() {
        java.util.Date d = dateTo.getDate();
        return d == null ? null : d.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
    }

    private static String truncate(String s, int maxLen) {
        if (s == null)
            return "";
        return s.length() <= maxLen ? s : s.substring(0, maxLen - 1) + "…";
    }
}
