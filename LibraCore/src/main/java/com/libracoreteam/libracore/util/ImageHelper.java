package com.libracoreteam.libracore.util;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.*;

public class ImageHelper {

  private static final String IMG_DIR = "images/nhanvien/";

  public static String saveImage(File srcFile, int idNhanVien) {
    try {
      File dir = new File(IMG_DIR);
      if (!dir.exists())
        dir.mkdirs();
      String ext = srcFile.getName().substring(srcFile.getName().lastIndexOf('.'));
      String destName = idNhanVien + "_" + System.currentTimeMillis() + ext;
      Path dest = Paths.get(IMG_DIR + destName);
      Files.copy(srcFile.toPath(), dest, StandardCopyOption.REPLACE_EXISTING);
      return IMG_DIR + destName;
    } catch (IOException e) {
      return null;
    }
  }

  public static ImageIcon loadImage(String path, int w, int h) {
    if (path == null || path.isEmpty())
      return null;
    File f = new File(path);
    if (!f.exists())
      return null;
    ImageIcon icon = new ImageIcon(f.getAbsolutePath());
    Image img = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
    return new ImageIcon(img);
  }
}
