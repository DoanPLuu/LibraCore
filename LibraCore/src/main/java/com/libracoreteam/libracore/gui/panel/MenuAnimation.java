package com.libracoreteam.libracore.gui.panel;

import java.awt.Component;
import net.miginfocom.swing.MigLayout;
import javax.swing.Timer;

/**
 * Helper class để animate việc mở/đóng submenu
 * Dùng javax.swing.Timer thay vì TimingFramework (không cần dependency)
 * @author luuis
 */
public class MenuAnimation {
    
    private static final int ANIMATION_DURATION = 300; // milliseconds
    private static final int FRAME_RATE = 60; // frames per second
    
    /**
     * Animate việc hiển thị/ẩn submenu panel
     * @param component Panel chứa submenu items
     * @param item MenuItem chính (để animate arrow)
     * @param layout MigLayout của MenuPanel
     * @param show true = mở, false = đóng
     */
    public static void showMenu(Component component, MenuItem item, MigLayout layout, boolean show) {
        int targetHeight = component.getPreferredSize().height;
        int startHeight = show ? 0 : targetHeight;
        int endHeight = show ? targetHeight : 0;
        
        long startTime = System.currentTimeMillis();
        
        Timer timer = new Timer(1000 / FRAME_RATE, e -> {
            long elapsed = System.currentTimeMillis() - startTime;
            float progress = Math.min(1.0f, (float) elapsed / ANIMATION_DURATION);
            
            // Easing function (ease-in-out) - tạo animation mượt hơn
            float easedProgress = easeInOut(progress);
            
            // Tính height hiện tại
            int currentHeight = (int) (startHeight + (endHeight - startHeight) * easedProgress);
            layout.setComponentConstraints(component, "h " + currentHeight + "!");
            
            // Animate arrow
            item.setArrowAnimation(show ? easedProgress : 1f - easedProgress);
            
            component.revalidate();
            item.repaint();
            
            // Dừng timer khi animation xong
            if (progress >= 1.0f) {
                ((Timer) e.getSource()).stop();
            }
        });
        
        timer.start();
    }
    
    /**
     * Easing function: ease-in-out
     * Tạo animation mượt hơn (bắt đầu chậm, giữa nhanh, cuối chậm)
     */
    private static float easeInOut(float t) {
        return t < 0.5f 
            ? 2 * t * t 
            : 1 - (float) Math.pow(-2 * t + 2, 2) / 2;
    }
}
