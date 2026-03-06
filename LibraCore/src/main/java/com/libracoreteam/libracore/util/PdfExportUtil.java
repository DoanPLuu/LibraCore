package com.libracoreteam.libracore.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.Component;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PdfExportUtil {

  private static final String[] FONT_PATHS = {
      "C:/Windows/Fonts/arial.ttf",
      "C:/Windows/Fonts/Arial.ttf",
      "C:/Windows/Fonts/times.ttf"
  };
  private static final String FONT_BOLD = "C:/Windows/Fonts/arialbd.ttf";
  private static final BaseColor HEADER_BG = new BaseColor(0x15, 0x65, 0xC0);

  public static void export(Component parent, String title, String subtitle,
      String[] columns, Object[][] rows, String defaultFileName) {
    JFileChooser chooser = new JFileChooser();
    chooser.setDialogTitle("Lưu file PDF");
    chooser.setFileFilter(new FileNameExtensionFilter("PDF files (*.pdf)", "pdf"));
    chooser.setSelectedFile(new File(defaultFileName.endsWith(".pdf") ? defaultFileName : defaultFileName + ".pdf"));
    if (chooser.showSaveDialog(parent) != JFileChooser.APPROVE_OPTION)
      return;

    File file = chooser.getSelectedFile();
    if (!file.getName().toLowerCase().endsWith(".pdf"))
      file = new File(file.getAbsolutePath() + ".pdf");

    try {
      String fontPath = FONT_PATHS[0];
      for (String fp : FONT_PATHS) {
        if (new File(fp).exists()) {
          fontPath = fp;
          break;
        }
      }
      BaseFont bf = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
      BaseFont bfBold = new File(FONT_BOLD).exists()
          ? BaseFont.createFont(FONT_BOLD, BaseFont.IDENTITY_H, BaseFont.EMBEDDED)
          : bf;

      Font titleFont = new Font(bfBold, 16, Font.BOLD, BaseColor.DARK_GRAY);
      Font subFont = new Font(bf, 10, Font.NORMAL, BaseColor.GRAY);
      Font headerFont = new Font(bfBold, 10, Font.BOLD, BaseColor.WHITE);
      Font cellFont = new Font(bf, 9, Font.NORMAL, BaseColor.DARK_GRAY);
      Font footerFont = new Font(bf, 8, Font.ITALIC, BaseColor.GRAY);

      Document doc = new Document(PageSize.A4, 36, 36, 50, 36);
      PdfWriter.getInstance(doc, new FileOutputStream(file));
      doc.open();

      Paragraph pTitle = new Paragraph(title, titleFont);
      pTitle.setAlignment(Element.ALIGN_CENTER);
      pTitle.setSpacingAfter(4);
      doc.add(pTitle);

      if (subtitle != null && !subtitle.isEmpty()) {
        Paragraph pSub = new Paragraph(subtitle, subFont);
        pSub.setAlignment(Element.ALIGN_CENTER);
        pSub.setSpacingAfter(12);
        doc.add(pSub);
      }

      PdfPTable table = new PdfPTable(columns.length);
      table.setWidthPercentage(100);
      table.setSpacingBefore(10);

      for (String col : columns) {
        PdfPCell cell = new PdfPCell(new Phrase(col, headerFont));
        cell.setBackgroundColor(HEADER_BG);
        cell.setPadding(6);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBorderColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);
      }

      for (int r = 0; r < rows.length; r++) {
        BaseColor rowBg = r % 2 == 0 ? BaseColor.WHITE : new BaseColor(0xF5, 0xF5, 0xF5);
        for (Object val : rows[r]) {
          PdfPCell cell = new PdfPCell(new Phrase(val == null ? "" : val.toString(), cellFont));
          cell.setBackgroundColor(rowBg);
          cell.setPadding(5);
          cell.setBorderColor(BaseColor.LIGHT_GRAY);
          table.addCell(cell);
        }
      }
      doc.add(table);

      doc.add(Chunk.NEWLINE);
      Paragraph footer = new Paragraph(
          "Xuất lúc: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy")),
          footerFont);
      footer.setAlignment(Element.ALIGN_RIGHT);
      doc.add(footer);

      doc.close();

      int choice = JOptionPane.showConfirmDialog(parent,
          "Xuất PDF thành công!\nBạn có muốn mở file ngay không?",
          "Thành công", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
      if (choice == JOptionPane.YES_OPTION)
        Desktop.getDesktop().open(file);

    } catch (Exception ex) {
      JOptionPane.showMessageDialog(parent,
          "Lỗi khi xuất PDF: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
      ex.printStackTrace();
    }
  }

  public static Object[][] fromTableModel(DefaultTableModel model) {
    int rows = model.getRowCount(), cols = model.getColumnCount();
    Object[][] data = new Object[rows][cols];
    for (int r = 0; r < rows; r++)
      for (int c = 0; c < cols; c++)
        data[r][c] = model.getValueAt(r, c);
    return data;
  }
}
