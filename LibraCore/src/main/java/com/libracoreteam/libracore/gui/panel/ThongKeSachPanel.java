package com.libracoreteam.libracore.gui.panel;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
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
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.imageio.ImageIO;

public class ThongKeSachPanel extends JPanel {

    private static final String[] LOAI_THONG_KE = {
            "Tổng số sách",
            "Sách đang mượn",
            "Sách nhập kho",
            "Sách hỏng / mất"
    };

    private static final Color[] BAR_COLORS = {
            new Color(0x2196F3), 
            new Color(0x4CAF50), 
            new Color(0xFF9800), 
            new Color(0xF44336),
    };

    private final JDateChooser dateFrom;
    private final JDateChooser dateTo;
    private final JComboBox<String> comboLoai;
    private final JButton btnThongKe;
    private final JButton btnXuatPDF;

    private JFreeChart chart;
    private ChartPanel chartPanel;
    private DefaultCategoryDataset dataset;

    private final DefaultTableModel tblModel;
    private final JTable table;

    private final ThongKeBUS bus = new ThongKeBUS();

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FMT_SHORT = DateTimeFormatter.ofPattern("dd/MM");

    public ThongKeSachPanel() {
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

        comboLoai = new JComboBox<>(LOAI_THONG_KE);
        comboLoai.setPreferredSize(new Dimension(150, 32));

        btnThongKe = new JButton("Thống kê");
        btnThongKe.setPreferredSize(new Dimension(100, 32));
        btnThongKe.setBackground(new Color(0x1565C0));
        btnThongKe.setForeground(Color.WHITE);
        btnThongKe.setFocusPainted(false);
        btnThongKe.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnXuatPDF = new JButton("Xuất PDF");
        btnXuatPDF.setPreferredSize(new Dimension(100, 32));
        btnXuatPDF.setBackground(new Color(0xC62828));
        btnXuatPDF.setForeground(Color.WHITE);
        btnXuatPDF.setFocusPainted(false);
        btnXuatPDF.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        toolbar.add(new JLabel("Từ ngày"));
        toolbar.add(dateFrom);
        toolbar.add(new JLabel("Đến ngày"));
        toolbar.add(dateTo);
        toolbar.add(comboLoai);
        toolbar.add(btnThongKe);
        toolbar.add(btnXuatPDF);

        add(toolbar, BorderLayout.NORTH);

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

        table.getColumnModel().getColumn(0).setPreferredWidth(250);
        table.getColumnModel().getColumn(1).setPreferredWidth(180);
        table.getColumnModel().getColumn(2).setPreferredWidth(80);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);

        JScrollPane tableScroll = new JScrollPane(table);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, chartPanel, tableScroll);
        splitPane.setResizeWeight(0.60);
        splitPane.setDividerSize(6);
        splitPane.setBorder(null);

        add(splitPane, BorderLayout.CENTER);

        btnThongKe.addActionListener(e -> runThongKe());
        btnXuatPDF.addActionListener(e -> exportPDF());

        runThongKe();
    }


    private void exportPDF() {
        if (tblModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this,
                    "Không có dữ liệu để xuất. Vui lòng thống kê trước.",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Lưu file PDF");
        chooser.setFileFilter(new FileNameExtensionFilter("PDF files (*.pdf)", "pdf"));
        chooser.setSelectedFile(new File("ThongKeSach.pdf"));
        if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION)
            return;

        File file = chooser.getSelectedFile();
        if (!file.getName().toLowerCase().endsWith(".pdf")) {
            file = new File(file.getAbsolutePath() + ".pdf");
        }

        try {
            Document doc = new Document(PageSize.A4, 36, 36, 50, 36);
            PdfWriter.getInstance(doc, new FileOutputStream(file));
            doc.open();

            String[] fontPaths = {
                    "C:/Windows/Fonts/arial.ttf",
                    "C:/Windows/Fonts/Arial.ttf",
                    "C:/Windows/Fonts/times.ttf"
            };
            String fontPathBold = "C:/Windows/Fonts/arialbd.ttf";
            String resolvedFont = fontPaths[0];
            for (String fp : fontPaths) {
                if (new File(fp).exists()) {
                    resolvedFont = fp;
                    break;
                }
            }
            if (!new File(resolvedFont).exists()) {
                JOptionPane.showMessageDialog(this,
                        "Không tìm thấy font Arial trên máy. Tiếng Việt có thể bị lỗi.",
                        "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            }
            BaseFont bf = BaseFont.createFont(resolvedFont, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            BaseFont bfBold = new File(fontPathBold).exists()
                    ? BaseFont.createFont(fontPathBold, BaseFont.IDENTITY_H, BaseFont.EMBEDDED)
                    : bf;

            com.itextpdf.text.Font titleFont = new com.itextpdf.text.Font(bfBold, 16, com.itextpdf.text.Font.BOLD,
                    BaseColor.DARK_GRAY);
            com.itextpdf.text.Font subFont = new com.itextpdf.text.Font(bf, 10, com.itextpdf.text.Font.NORMAL,
                    BaseColor.GRAY);
            com.itextpdf.text.Font headerFont = new com.itextpdf.text.Font(bfBold, 10, com.itextpdf.text.Font.BOLD,
                    BaseColor.WHITE);
            com.itextpdf.text.Font cellFont = new com.itextpdf.text.Font(bf, 9, com.itextpdf.text.Font.NORMAL,
                    BaseColor.DARK_GRAY);

            Paragraph title = new Paragraph(
                    (String) comboLoai.getSelectedItem() + " - Báo cáo thống kê", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(4);
            doc.add(title);

            String rangeLabel = "Từ " + (getDateFrom() != null ? getDateFrom().format(FMT) : "?") +
                    " đến " + (getDateTo() != null ? getDateTo().format(FMT) : "?");
            Paragraph sub = new Paragraph(rangeLabel, subFont);
            sub.setAlignment(Element.ALIGN_CENTER);
            sub.setSpacingAfter(12);
            doc.add(sub);

            int chartW = 750, chartH = 350;
            BufferedImage chartImg = chart.createBufferedImage(chartW, chartH);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(chartImg, "png", baos);
            com.itextpdf.text.Image pdfImg = com.itextpdf.text.Image.getInstance(baos.toByteArray());
            float pageWidth = doc.getPageSize().getWidth() - doc.leftMargin() - doc.rightMargin();
            pdfImg.scaleToFit(pageWidth, pageWidth * chartH / chartW);
            pdfImg.setAlignment(Element.ALIGN_CENTER);
            doc.add(pdfImg);

            doc.add(Chunk.NEWLINE);

            int colCount = tblModel.getColumnCount();
            PdfPTable pdfTable = new PdfPTable(colCount);
            pdfTable.setWidthPercentage(100);
            pdfTable.setSpacingBefore(10);

            BaseColor headerBg = new BaseColor(0x15, 0x65, 0xC0);
            for (int c = 0; c < colCount; c++) {
                PdfPCell cell = new PdfPCell(new Phrase(tblModel.getColumnName(c), headerFont));
                cell.setBackgroundColor(headerBg);
                cell.setPadding(6);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorderColor(BaseColor.LIGHT_GRAY);
                pdfTable.addCell(cell);
            }

            for (int r = 0; r < tblModel.getRowCount(); r++) {
                BaseColor rowBg = (r % 2 == 0) ? BaseColor.WHITE : new BaseColor(0xF5, 0xF5, 0xF5);
                for (int c = 0; c < colCount; c++) {
                    Object val = tblModel.getValueAt(r, c);
                    PdfPCell cell = new PdfPCell(new Phrase(val == null ? "" : val.toString(), cellFont));
                    cell.setBackgroundColor(rowBg);
                    cell.setPadding(5);
                    cell.setBorderColor(BaseColor.LIGHT_GRAY);
                    pdfTable.addCell(cell);
                }
            }

            doc.add(pdfTable);

            doc.add(Chunk.NEWLINE);
            com.itextpdf.text.Font footFont = new com.itextpdf.text.Font(bf, 8, com.itextpdf.text.Font.ITALIC,
                    BaseColor.GRAY);
            Paragraph footer = new Paragraph(
                    "Xuất lúc: " + java.time.LocalDateTime.now()
                            .format(DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy")),
                    footFont);
            footer.setAlignment(Element.ALIGN_RIGHT);
            doc.add(footer);

            doc.close();

            int choice = JOptionPane.showConfirmDialog(this,
                    "Xuất PDF thành công!\nBạn có muốn mở file ngay không?",
                    "Thành công", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (choice == JOptionPane.YES_OPTION) {
                Desktop.getDesktop().open(file);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi xuất PDF: " + ex.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }


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


    private void styleChart(JFreeChart c, int colorIdx) {
        c.setBackgroundPaint(Color.WHITE);
        c.setTextAntiAlias(true);
        c.getTitle().setFont(new Font("Segoe UI", Font.BOLD, 14));

        CategoryPlot plot = c.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(new Color(0xE0E0E0));
        plot.setOutlineVisible(false);

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setMaximumBarWidth(0.08);
        renderer.setSeriesPaint(0, BAR_COLORS[colorIdx]);
        renderer.setShadowVisible(false);
        renderer.setDrawBarOutline(false);

        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setVisible(rows() > 0);
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
        domainAxis.setTickLabelFont(new Font("Segoe UI", Font.PLAIN, 10));
        domainAxis.setLabel("Tên sách");

        plot.getRangeAxis().setTickLabelFont(new Font("Segoe UI", Font.PLAIN, 11));
        plot.getRangeAxis().setLabel("Số lượng");
    }

    private int rows() {
        return dataset.getColumnCount();
    }


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
