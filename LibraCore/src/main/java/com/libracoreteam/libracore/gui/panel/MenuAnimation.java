package com.libracoreteam.libracore.gui.panel;

import java.awt.Component;
import net.miginfocom.swing.MigLayout;
import javax.swing.Timer;


public class MenuAnimation {
    
    private static final int ANIMATION_DURATION = 300; 
    private static final int FRAME_RATE = 60; 
    
    
    public static void showMenu(Component component, MenuItem item, MigLayout layout, boolean show) {
        int targetHeight = component.getPreferredSize().height;
        int startHeight = show ? 0 : targetHeight;
        int endHeight = show ? targetHeight : 0;
        
        long startTime = System.currentTimeMillis();
        
        Timer timer = new Timer(1000 / FRAME_RATE, e -> {
            long elapsed = System.currentTimeMillis() - startTime;
            float progress = Math.min(1.0f, (float) elapsed / ANIMATION_DURATION);
            
           
            float easedProgress = easeInOut(progress);
            
            
            int currentHeight = (int) (startHeight + (endHeight - startHeight) * easedProgress);
            layout.setComponentConstraints(component, "h " + currentHeight + "!");
            
            
            item.setArrowAnimation(show ? easedProgress : 1f - easedProgress);
            
            component.revalidate();
            item.repaint();
            
           
            if (progress >= 1.0f) {
                ((Timer) e.getSource()).stop();
            }
        });
        
        timer.start();
    }
    
    
    private static float easeInOut(float t) {
        return t < 0.5f 
            ? 2 * t * t 
            : 1 - (float) Math.pow(-2 * t + 2, 2) / 2;
    }
}
