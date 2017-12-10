package ddalines;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class DDALines {

    static JFrame frame;
    static JPanel panel;
    static BufferedImage image;
    
    public static void main(String[] args) {
        
        image = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
        
        panel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, null);
            }
        };
        
        frame = new JFrame();
        frame.setSize(500, 500);
        frame.add(panel);
        frame.setVisible(true);
        
        draw_line_dda(image.getRaster(), 50, 50, 100, 60, new int[]{133, 121, 215});
                
    }
    
    public static void draw_line_dda(WritableRaster raster, int x0, int y0, int x1, int y1, int[] color) {
        
        int dX = x1 - x0;
        int dY = y1 - y0;
        
        double incX;
        double incY;
        double step;
        
        if (Math.abs(dX) > Math.abs(dY))
            step = Math.abs(dX);
        else
            step = Math.abs(dY);
        
        incX = dX / step;
        incY = dY / step;
        
        double x = x0, y = y0;
        
        for (int i = 0; i < step; i++) {
            
            x = x + incX;
            y = y + incY;
            
            raster.setPixel((int) Math.round(x), (int) Math.round(y), color);
        }
    }
    
}