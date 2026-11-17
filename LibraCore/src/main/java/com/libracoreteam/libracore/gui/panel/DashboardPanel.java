package com.libracoreteam.libracore.gui.panel;

import com.libracoreteam.libracore.bus.ThongKeBUS;
import com.libracoreteam.libracore.model.DashboardStatistics;
import com.libracoreteam.libracore.model.DashboardStatistics.BookRanking;
import com.libracoreteam.libracore.model.DashboardStatistics.GenreInventory;
import com.libracoreteam.libracore.model.DashboardStatistics.Granularity;
import com.libracoreteam.libracore.model.DashboardStatistics.LoanTrendPoint;
import com.libracoreteam.libracore.model.DashboardStatistics.ReaderTrendPoint;
import com.toedter.calendar.JDateChooser;
import net.miginfocom.swing.MigLayout;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class DashboardPanel extends JPanel {

    private static final Color GREEN = new Color(21, 110, 71);
    private static final Color BACKGROUND = new Color(245, 245, 250);

    private final ThongKeBUS thongKeBUS = new ThongKeBUS();
    private final JDateChooser dateFrom = new JDateChooser();
    private final JDateChooser dateTo = new JDateChooser();
    private final JComboBox<Granularity> granularitySelector = new JComboBox<>(Granularity.values());
    private final JButton refreshButton = new JButton("Làm mới dữ liệu");

    private final JLabel lblTitleCount = createValueLabel();
    private final JLabel lblActiveReaderCount = createValueLabel();
    private final JLabel lblOpenTicketCount = createValueLabel();
    private final JLabel lblCollectedFines = createValueLabel();

    private final JLabel lblBorrowedCount = createValueLabel();
    private final JLabel lblReturnedCount = createValueLabel();
    private final JLabel lblOutstandingCount = createValueLabel();
    private final JLabel lblOverdueCount = createValueLabel();

    private final JLabel lblReaderCount = createValueLabel();
    private final JLabel lblReaderActiveCount = createValueLabel();
    private final JLabel lblNewReaderCount = createValueLabel();
    private final JLabel lblReadersWithLoansCount = createValueLabel();

    private final JLabel lblCopyCount = createValueLabel();
    private final JLabel lblAvailableCopyCount = createValueLabel();
    private final JLabel lblCopyOnLoanCount = createValueLabel();
    private final JLabel lblUnavailableCopyCount = createValueLabel();

    private final JPanel loanTrendHost = createChartHost();
    private final JPanel readerTrendHost = createChartHost();
    private final JPanel genreChartHost = createChartHost();
    private final JPanel stockPieHost = createChartHost();
    private final JPanel topBooksHost = new JPanel(new BorderLayout());
    private final DefaultTableModel topBooksModel = new DefaultTableModel(
            new Object[] { "Xếp hạng", "Mã sách", "Tên sách", "Lượt mượn" }, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public DashboardPanel() {
        initComponents();
        loadData();
    }

    private void initComponents() {
        setLayout(new BorderLayout(0, 12));
        setBackground(BACKGROUND);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel header = new JPanel(new BorderLayout(12, 12));
        header.setOpaque(false);
        JLabel title = new JLabel("DASHBOARD TỔNG QUAN");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(GREEN);
        header.add(title, BorderLayout.NORTH);
        header.add(createFilterPanel(), BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);
        content.add(createSection("TỔNG QUAN", "Chỉ số hiện tại của thư viện",
                createMetricGrid(
                        new String[] { "Đầu sách đang hoạt động", "Bạn đọc hoạt động",
                                "Phiếu mượn đang mở", "Tiền phạt đã thu" },
                        new JLabel[] { lblTitleCount, lblActiveReaderCount, lblOpenTicketCount, lblCollectedFines })));
        content.add(Box.createVerticalStrut(16));
        content.add(createLoanSection());
        content.add(Box.createVerticalStrut(16));
        content.add(createTopBooksSection());
        content.add(Box.createVerticalStrut(16));
        content.add(createReaderSection());
        content.add(Box.createVerticalStrut(16));
        content.add(createInventorySection());

        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(18);
        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createFilterPanel() {
        JPanel filters = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        filters.setOpaque(false);

        LocalDate today = LocalDate.now();
        dateFrom.setDate(java.util.Date.from(today.withDayOfYear(1)
                .atStartOfDay(ZoneId.systemDefault()).toInstant()));
        dateFrom.setDateFormatString("dd/MM/yyyy");
        dateFrom.setPreferredSize(new Dimension(130, 32));
        dateTo.setDate(java.util.Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        dateTo.setDateFormatString("dd/MM/yyyy");
        dateTo.setPreferredSize(new Dimension(130, 32));
        granularitySelector.setSelectedItem(Granularity.MONTH);
        granularitySelector.setPreferredSize(new Dimension(100, 32));

        refreshButton.setIcon(FontIcon.of(FontAwesomeSolid.SYNC_ALT, 16, Color.WHITE));
        refreshButton.setBackground(GREEN);
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        refreshButton.setFocusPainted(false);
        refreshButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        refreshButton.addActionListener(event -> loadData());

        filters.add(new JLabel("Từ ngày:"));
        filters.add(dateFrom);
        filters.add(new JLabel("Đến ngày:"));
        filters.add(dateTo);
        filters.add(new JLabel("Gom theo:"));
        filters.add(granularitySelector);
        filters.add(refreshButton);
        return filters;
    }

    private JPanel createLoanSection() {
        JPanel metrics = createMetricGrid(
                new String[] { "Lượt mượn sách", "Lượt trả sách", "Bản sách đang mượn", "Bản sách quá hạn" },
                new JLabel[] { lblBorrowedCount, lblReturnedCount, lblOutstandingCount, lblOverdueCount });
        JPanel content = new JPanel(new BorderLayout(0, 10));
        content.setOpaque(false);
        content.add(metrics, BorderLayout.NORTH);
        content.add(loanTrendHost, BorderLayout.CENTER);
        return createSection("TÌNH HÌNH MƯỢN - TRẢ",
                "Lượt mượn và trả tính theo bản sách; biểu đồ áp dụng khoảng thời gian đã chọn.", content);
    }

    private JPanel createTopBooksSection() {
        topBooksHost.setOpaque(false);
        topBooksHost.add(createEmptyLabel("Đang tải thống kê..."), BorderLayout.CENTER);
        topBooksHost.setPreferredSize(new Dimension(900, 260));
        return createSection("SÁCH ĐƯỢC MƯỢN NHIỀU NHẤT",
                "Top 10 theo số lượt mượn trong khoảng thời gian đã chọn. Mã sách là ID đầu sách trong hệ thống.",
                topBooksHost);
    }

    private JPanel createReaderSection() {
        JPanel metrics = createMetricGrid(
                new String[] { "Tổng bạn đọc", "Bạn đọc hoạt động", "Bạn đọc đăng ký mới*", "Bạn đọc đang mượn" },
                new JLabel[] { lblReaderCount, lblReaderActiveCount, lblNewReaderCount, lblReadersWithLoansCount });
        JPanel charts = new JPanel(new GridLayout(1, 1, 12, 12));
        charts.setOpaque(false);
        charts.add(readerTrendHost);

        JPanel content = new JPanel(new BorderLayout(0, 10));
        content.setOpaque(false);
        content.add(metrics, BorderLayout.NORTH);
        content.add(charts, BorderLayout.CENTER);
        return createSection("THỐNG KÊ BẠN ĐỌC",
                "*Bạn đọc mới được tính theo ngày cấp thẻ đầu tiên; biểu đồ áp dụng khoảng thời gian đã chọn.",
                content);
    }

    private JPanel createInventorySection() {
        JPanel metrics = createMetricGrid(
                new String[] { "Tổng bản sách", "Sẵn sàng cho mượn", "Đang được mượn", "Không lưu thông" },
                new JLabel[] { lblCopyCount, lblAvailableCopyCount, lblCopyOnLoanCount, lblUnavailableCopyCount });
        JPanel charts = new JPanel(new GridLayout(1, 2, 12, 12));
        charts.setOpaque(false);
        charts.add(stockPieHost);
        charts.add(genreChartHost);

        JPanel content = new JPanel(new BorderLayout(0, 10));
        content.setOpaque(false);
        content.add(metrics, BorderLayout.NORTH);
        content.add(charts, BorderLayout.CENTER);
        return createSection("TỒN KHO SÁCH",
                "Tồn kho đếm theo bản sách chưa hủy; phân bố thể loại có thể chồng lặp khi một đầu sách thuộc nhiều thể loại.",
                content);
    }

    private JPanel createSection(String title, String subtitle, JComponent content) {
        JPanel section = new JPanel(new BorderLayout(0, 10));
        section.setBackground(Color.WHITE);
        section.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(225, 229, 234)),
                new EmptyBorder(14, 16, 16, 16)));
        section.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel heading = new JPanel();
        heading.setLayout(new BoxLayout(heading, BoxLayout.Y_AXIS));
        heading.setOpaque(false);
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 17));
        titleLabel.setForeground(GREEN);
        JLabel subtitleLabel = new JLabel(subtitle);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitleLabel.setForeground(new Color(95, 102, 110));
        heading.add(titleLabel);
        heading.add(Box.createVerticalStrut(3));
        heading.add(subtitleLabel);

        section.add(heading, BorderLayout.NORTH);
        section.add(content, BorderLayout.CENTER);
        section.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        return section;
    }

    private JPanel createMetricGrid(String[] titles, JLabel[] values) {
        JPanel grid = new JPanel(new GridLayout(1, titles.length, 12, 12));
        grid.setOpaque(false);
        for (int i = 0; i < titles.length; i++) {
            grid.add(createMetricCard(titles[i], values[i]));
        }
        return grid;
    }

    private JPanel createMetricCard(String title, JLabel value) {
        JPanel card = new JPanel(new MigLayout("insets 12", "[grow, fill]", "[][grow]"));
        card.setBackground(new Color(248, 250, 252));
        card.setBorder(new LineBorder(new Color(230, 234, 239)));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        titleLabel.setForeground(new Color(90, 98, 106));
        value.setFont(new Font("Segoe UI", Font.BOLD, 26));
        value.setForeground(new Color(38, 71, 57));
        value.setText("—");
        card.add(titleLabel, "wrap");
        card.add(value, "growy, bottom");
        card.setPreferredSize(new Dimension(190, 84));
        return card;
    }

    public void loadData() {
        java.util.Date selectedFrom = dateFrom.getDate();
        java.util.Date selectedTo = dateTo.getDate();
        if (selectedFrom == null || selectedTo == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn đầy đủ khoảng thời gian thống kê.",
                    "Thiếu khoảng thời gian", JOptionPane.WARNING_MESSAGE);
            return;
        }

        LocalDate from = LocalDate.ofInstant(
                java.time.Instant.ofEpochMilli(selectedFrom.getTime()), ZoneId.systemDefault());
        LocalDate to = LocalDate.ofInstant(
                java.time.Instant.ofEpochMilli(selectedTo.getTime()), ZoneId.systemDefault());
        if (from.isAfter(to)) {
            JOptionPane.showMessageDialog(this, "Từ ngày không được sau Đến ngày.",
                    "Khoảng thời gian không hợp lệ", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Granularity granularity = (Granularity) granularitySelector.getSelectedItem();
        setLoading(true);
        new SwingWorker<DashboardStatistics, Void>() {
            @Override
            protected DashboardStatistics doInBackground() throws Exception {
                return thongKeBUS.getDashboardStatistics(from, to, granularity);
            }

            @Override
            protected void done() {
                setLoading(false);
                try {
                    applyStatistics(get());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    showLoadError(e);
                } catch (ExecutionException e) {
                    showLoadError(e.getCause() == null ? e : e.getCause());
                }
            }
        }.execute();
    }

    private void applyStatistics(DashboardStatistics statistics) {
        lblTitleCount.setText(formatCount(statistics.activeBookTitles()));
        lblActiveReaderCount.setText(formatCount(statistics.activeReaders()));
        lblOpenTicketCount.setText(formatCount(statistics.openLoanTickets()));
        lblCollectedFines.setText(formatMoney(statistics.collectedFines()));

        lblBorrowedCount.setText(formatCount(statistics.totalBorrowedCopies()));
        lblReturnedCount.setText(formatCount(statistics.totalReturnedCopies()));
        lblOutstandingCount.setText(formatCount(statistics.currentlyBorrowedCopies()));
        lblOverdueCount.setText(formatCount(statistics.overdueCopies()));

        lblReaderCount.setText(formatCount(statistics.totalReaders()));
        lblReaderActiveCount.setText(formatCount(statistics.activeReaders()));
        lblNewReaderCount.setText(formatCount(statistics.newReaders()));
        lblReadersWithLoansCount.setText(formatCount(statistics.readersWithOutstandingLoans()));

        lblCopyCount.setText(formatCount(statistics.totalCopies()));
        lblAvailableCopyCount.setText(formatCount(statistics.availableCopies()));
        lblCopyOnLoanCount.setText(formatCount(statistics.copiesOnLoan()));
        lblUnavailableCopyCount.setText(formatCount(statistics.unavailableCopies()));

        updateLoanTrend(statistics.loanTrend());
        updateReaderTrend(statistics.readerTrend());
        updateTopBooks(statistics.mostBorrowedBooks());
        updateInventoryCharts(statistics);
    }

    private void updateLoanTrend(List<LoanTrendPoint> points) {
        if (points.isEmpty()) {
            showEmpty(loanTrendHost, "Chưa có dữ liệu mượn hoặc trả trong khoảng thời gian này.");
            return;
        }
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (LoanTrendPoint point : points) {
            dataset.addValue(point.borrowedCopies(), "Lượt mượn", point.period());
            dataset.addValue(point.returnedCopies(), "Lượt trả", point.period());
        }
        JFreeChart chart = ChartFactory.createLineChart(
                "Xu hướng mượn - trả", "Thời gian", "Số bản sách",
                dataset, PlotOrientation.VERTICAL, true, true, false);
        styleCategoryChart(chart, true);
        showChart(loanTrendHost, chart);
    }

    private void updateReaderTrend(List<ReaderTrendPoint> points) {
        if (points.isEmpty()) {
            showEmpty(readerTrendHost, "Chưa có dữ liệu ngày cấp thẻ trong khoảng thời gian này.");
            return;
        }
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (ReaderTrendPoint point : points) {
            dataset.addValue(point.readers(), "Bạn đọc mới", point.period());
        }
        JFreeChart chart = ChartFactory.createBarChart(
                "Bạn đọc đăng ký theo thời gian", "Thời gian", "Số bạn đọc",
                dataset, PlotOrientation.VERTICAL, true, true, false);
        styleCategoryChart(chart, false);
        showChart(readerTrendHost, chart);
    }

    private void updateTopBooks(List<BookRanking> rankings) {
        topBooksModel.setRowCount(0);
        topBooksHost.removeAll();
        if (rankings.isEmpty()) {
            JLabel emptyLabel = createEmptyLabel("Chưa có lượt mượn trong khoảng thời gian này.");
            topBooksHost.add(emptyLabel, BorderLayout.CENTER);
        } else {
            int rank = 1;
            for (BookRanking book : rankings) {
                topBooksModel.addRow(new Object[] {
                        rank++, book.bookId(), book.title(), formatCount(book.borrowCount())
                });
            }
            JTable table = new JTable(topBooksModel);
            table.setRowHeight(28);
            table.setFillsViewportHeight(true);
            table.getTableHeader().setReorderingAllowed(false);
            topBooksHost.add(new JScrollPane(table), BorderLayout.CENTER);
        }
        topBooksHost.revalidate();
        topBooksHost.repaint();
    }

    private void updateInventoryCharts(DashboardStatistics statistics) {
        long circulatingCopies = statistics.availableCopies() + statistics.copiesOnLoan();
        if (circulatingCopies == 0) {
            showEmpty(stockPieHost, "Chưa có bản sách sẵn sàng hoặc đang được mượn.");
        } else {
            DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
            dataset.setValue("Sẵn sàng cho mượn", statistics.availableCopies());
            dataset.setValue("Đang được mượn", statistics.copiesOnLoan());
            JFreeChart chart = ChartFactory.createPieChart(
                    "Tỷ lệ trong số bản sách còn lưu thông", dataset, true, true, false);
            chart.setBackgroundPaint(Color.WHITE);
            PiePlot<?> plot = (PiePlot<?>) chart.getPlot();
            plot.setBackgroundPaint(Color.WHITE);
            plot.setSectionPaint("Sẵn sàng cho mượn", new Color(46, 160, 95));
            plot.setSectionPaint("Đang được mượn", new Color(243, 156, 18));
            plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}: {1} ({2})"));
            showChart(stockPieHost, chart);
        }

        if (statistics.inventoryByGenre().isEmpty()) {
            showEmpty(genreChartHost, "Chưa có dữ liệu bản sách theo thể loại.");
            return;
        }
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (GenreInventory genre : statistics.inventoryByGenre()) {
            dataset.addValue(genre.totalCopies(), "Tổng bản", genre.genre());
            dataset.addValue(genre.availableCopies(), "Sẵn sàng", genre.genre());
            dataset.addValue(genre.copiesOnLoan(), "Đang mượn", genre.genre());
        }
        JFreeChart chart = ChartFactory.createBarChart(
                "Số bản sách theo thể loại", "Thể loại", "Số bản sách",
                dataset, PlotOrientation.VERTICAL, true, true, false);
        styleCategoryChart(chart, false);
        showChart(genreChartHost, chart);
    }

    private void styleCategoryChart(JFreeChart chart, boolean lineChart) {
        chart.setBackgroundPaint(Color.WHITE);
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(new Color(224, 224, 224));
        plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.UP_45);
        plot.getRangeAxis().setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        if (lineChart) {
            LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
            renderer.setSeriesShapesVisible(0, true);
            renderer.setSeriesShapesVisible(1, true);
            renderer.setSeriesPaint(0, new Color(33, 150, 243));
            renderer.setSeriesPaint(1, new Color(76, 175, 80));
        }
    }

    private JPanel createChartHost() {
        JPanel host = new JPanel(new BorderLayout());
        host.setBackground(Color.WHITE);
        host.setPreferredSize(new Dimension(560, 300));
        return host;
    }

    private void showChart(JPanel host, JFreeChart chart) {
        host.removeAll();
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setMouseWheelEnabled(true);
        host.add(chartPanel, BorderLayout.CENTER);
        host.revalidate();
        host.repaint();
    }

    private void showEmpty(JPanel host, String message) {
        host.removeAll();
        host.add(createEmptyLabel(message), BorderLayout.CENTER);
        host.revalidate();
        host.repaint();
    }

    private JLabel createEmptyLabel(String message) {
        JLabel label = new JLabel(message, SwingConstants.CENTER);
        label.setForeground(new Color(110, 118, 126));
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return label;
    }

    private JLabel createValueLabel() {
        JLabel label = new JLabel("—");
        label.setFont(new Font("Segoe UI", Font.BOLD, 26));
        label.setForeground(new Color(38, 71, 57));
        return label;
    }

    private String formatCount(long value) {
        return String.format(Locale.forLanguageTag("vi-VN"), "%,d", value);
    }

    private String formatMoney(java.math.BigDecimal value) {
        return String.format(Locale.forLanguageTag("vi-VN"), "%,.0f VNĐ", value);
    }

    private void setLoading(boolean loading) {
        refreshButton.setEnabled(!loading);
        dateFrom.setEnabled(!loading);
        dateTo.setEnabled(!loading);
        granularitySelector.setEnabled(!loading);
    }

    private void showLoadError(Throwable error) {
        setMetricValues("—");
        showEmpty(loanTrendHost, "Không thể tải dữ liệu mượn - trả.");
        showEmpty(readerTrendHost, "Không thể tải dữ liệu bạn đọc.");
        showEmpty(stockPieHost, "Không thể tải dữ liệu tồn kho.");
        showEmpty(genreChartHost, "Không thể tải dữ liệu thể loại.");
        topBooksModel.setRowCount(0);
        topBooksHost.removeAll();
        topBooksHost.add(createEmptyLabel("Không thể tải dữ liệu xếp hạng sách."), BorderLayout.CENTER);
        topBooksHost.revalidate();
        topBooksHost.repaint();
        JOptionPane.showMessageDialog(this,
                "Không thể tải thống kê từ cơ sở dữ liệu: " + error.getMessage(),
                "Lỗi tải Dashboard", JOptionPane.ERROR_MESSAGE);
    }

    private void setMetricValues(String value) {
        lblTitleCount.setText(value);
        lblActiveReaderCount.setText(value);
        lblOpenTicketCount.setText(value);
        lblCollectedFines.setText(value);
        lblBorrowedCount.setText(value);
        lblReturnedCount.setText(value);
        lblOutstandingCount.setText(value);
        lblOverdueCount.setText(value);
        lblReaderCount.setText(value);
        lblReaderActiveCount.setText(value);
        lblNewReaderCount.setText(value);
        lblReadersWithLoansCount.setText(value);
        lblCopyCount.setText(value);
        lblAvailableCopyCount.setText(value);
        lblCopyOnLoanCount.setText(value);
        lblUnavailableCopyCount.setText(value);
    }
}
