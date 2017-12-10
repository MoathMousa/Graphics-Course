/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lines;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Moath
 */
public class Lines {

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
        
        draw_line(image.getRaster(), 300, 100, 100, 400, new int[]{133, 121, 215});
               
    }
    
    public static void draw_line(WritableRaster raster, int x1, int y1, int x2, int y2, int[] color){
        
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);

        int sx = (x1 < x2) ? 1 : -1;
        int sy = (y1 < y2) ? 1 : -1;

        int err = dx - dy;

    while (true) {
        raster.setPixel(x1, y1, new int []{255,0,0});

        if (x1 == x2 && y1 == y2) {
        break;
    }

    int e2 = 2 * err;

    if (e2 > -dy) {
        err = err - dy;
        x1 = x1 + sx;
    }

    if (e2 < dx) {
        err = err + dx;
        y1 = y1 + sy;
       }
    }
    }
}
