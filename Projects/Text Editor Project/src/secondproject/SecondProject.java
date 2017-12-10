package secondproject;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SecondProject {
    
    static LinkList[] lC = new LinkList[30];
    static JFrame frame;
    static JPanel panel;
    static BufferedImage image;
    static LinkList fonts[];
    static int x=750,y=100,size=12;
    static boolean IsFisrt =true;
    static boolean nextChar =false;
    static File file=new File("loadF.txt");
       

    public static void main(String[] args)throws Exception{
//        Character x = new Character("0,0,0,0,7168,7168,7168,7168,65535,65535,65535,0,128,448,128,0");
//        x.toString();
        InputLetters(lC);
        image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
        panel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, null);
            }
        };
        
        frame = new JFrame();
        frame.setSize(800, 600);
        frame.add(panel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        load(file); 
//        //take node and the node take character and convert and prepare the array then draw it in the methode that take the charachter array
        drawFont(image.getRaster(),new Node((new Character("0,0,0,7680,16128,24960,57728,65535,65535,0,0,0,0,0,0,0"))));
        drawFont(image.getRaster(),new Node((new Character("0,0,4064,8176,6240,2240,1920,65535,65535,0,0,0,0,0,0,0"))));
        drawFont(image.getRaster(),new Node((new Character("28,28,28,28,28,28,28,65532,65528,0,0,0,0,0,0,0"))));
        drawFont(image.getRaster(),new Node((new Character("96,1888,3840,7168,14336,28672,57376,65504,65472,0,0,0,0,0,0,0"))));
        drawFont(image.getRaster(),new Node((new Character("0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0"))));
        drawFont(image.getRaster(),new Node((new Character("0,0,0,7680,16128,24960,57728,65535,65535,0,0,0,0,0,0,0"))));
        drawFont(image.getRaster(),new Node((new Character("0,6144,15360,26112,59136,58880,64512,57344,61440,30720,7680,4064,992,0,0,0"))));
        drawFont(image.getRaster(),new Node((new Character("0,0,0,0,56064,56064,56064,65535,32767,0,0,0,0,0,0,0"))));
        drawFont(image.getRaster(),new Node((new Character("0,0,0,0,0,0,0,65280,65411,387,771,1022,508,0,0,0"))));

    }
    
    public static void drawFont(WritableRaster raster,Node q){  
        for(int i=0;i<16;i++){//loops for draw font from 8*8 bin array
            for(int j=0;j<16;j++)
                if(q.element.letter[i][j]==1){
                    raster.setPixel(x+j, y+i, new int[]{255, 255, 255});}
        }x-=16; 
    }
    
     public static void load(File file) throws FileNotFoundException{
		
        Scanner input = new Scanner(file);
        String[] split = new String[10];
        Character x = null;
        while(input.hasNextLine())
            split = input.nextLine().split(" ");
        for(int i=0;i<split.length;i++){
            x = new Character(split[i]);
            drawFont(image.getRaster(),new Node((x)));
        }
    }
     
    public static void InputLetters(LinkList[]  l){
//        lC[0] = .addLast(new Character("28,28,28,28,28,28,28,28,28,0,0,0,0,0,0,0"));
    }
          
}
