package clippinglab;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import javax.swing.JFrame;


public class ClippingLab  implements GLEventListener{
    
     private float angle;
     private float trans1=0;
     private float trans2=0;
     

    public void display(GLAutoDrawable drawable) {
            
        final GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        
        double[] clipcoordinates1 = {-0.5,0,0,0};
        double[] clipcoordinates2 = {0.5,0,0,0};
 
        gl.glClipPlane(GL2.GL_CLIP_PLANE0, clipcoordinates2,0);
        gl.glEnable(GL2.GL_CLIP_PLANE0);
        
        //Green Down
        gl.glLoadIdentity();
        gl.glColor3f( 0.419608f, 0.556863f, 0.137255f);
        gl.glBegin(GL2.GL_QUADS);
            gl.glVertex2f(-1.0f, -1.0f);
            gl.glVertex2f(-1.0f, -0.8f);
            gl.glVertex2f(1.0f, -0.8f);
            gl.glVertex2f(1.0f, -1.0f);
        gl.glEnd();
        gl.glFlush();
        
        //White Down
        gl.glLoadIdentity();
        gl.glColor3f(0.839216f, 0.839216f, 0.839216f);
        gl.glBegin(GL2.GL_QUADS);
            gl.glVertex2f(-1.0f, -0.8f);
            gl.glVertex2f(-1.0f, -0.6f);
            gl.glVertex2f(1.0f, -0.6f);
            gl.glVertex2f(1.0f, -0.8f);
        gl.glEnd();
        gl.glFlush();

        //White Up
        gl.glLoadIdentity();
        gl.glColor3f(0.839216f, 0.839216f, 0.839216f);
        gl.glBegin(GL2.GL_QUADS);
            gl.glVertex2f(-1.0f, 0.1f);
            gl.glVertex2f(-1.0f, 0.3f);
            gl.glVertex2f(1.0f, 0.3f);
            gl.glVertex2f(1.0f, 0.1f);
        gl.glEnd();
        gl.glFlush();
        
        //Green Up
        gl.glLoadIdentity();
        gl.glColor3f( 0.419608f, 0.556863f, 0.137255f);
        gl.glBegin(GL2.GL_QUADS);
            gl.glVertex2f(-1.0f, 0.3f);
            gl.glVertex2f(-1.0f, 0.6f);
            gl.glVertex2f(1.0f, 0.6f);
            gl.glVertex2f(1.0f, 0.3f);
        gl.glEnd();
        gl.glFlush();
        
        //Sky
        gl.glLoadIdentity();
        gl.glColor3f( 0.941176f, 0.972549f, 1f);
        gl.glBegin(GL2.GL_QUADS);
            gl.glVertex2f(-1.0f, 0.6f);
            gl.glVertex2f(-1.0f, 1.0f);
            gl.glVertex2f(1.0f, 1.0f);
            gl.glVertex2f(1.0f, 0.6f);
        gl.glEnd();
        gl.glFlush();
        
        //Blocks Down
        gl.glLoadIdentity();
        gl.glColor3f(1.0f,0,0);
        gl.glBegin(GL2.GL_QUADS);
            gl.glVertex2f(-1.0f, -0.65f);
            gl.glVertex2f(-1.0f, -0.6f);
            gl.glVertex2f(-0.7f, -0.6f);
            gl.glVertex2f(-0.7f, -0.65f);
        gl.glEnd();
        
        gl.glLoadIdentity();
        gl.glColor3f(1f,1f,1f);
        gl.glBegin(GL2.GL_QUADS);
            gl.glVertex2f(-0.7f, -0.65f);
            gl.glVertex2f(-0.7f, -0.6f);
            gl.glVertex2f(-0.4f, -0.6f);
            gl.glVertex2f(-0.4f, -0.65f);
        gl.glEnd();
        
//        
        gl.glLoadIdentity();
        gl.glColor3f(1.0f,0,0);
        gl.glBegin(GL2.GL_QUADS);
            gl.glVertex2f(-0.4f, -0.65f);
            gl.glVertex2f(-0.4f, -0.6f);
            gl.glVertex2f(-0.1f, -0.6f);
            gl.glVertex2f(-0.1f, -0.65f);
        gl.glEnd();
        
        gl.glLoadIdentity();
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        gl.glBegin(GL2.GL_QUADS);
           gl.glVertex2f(-0.1f, -0.65f);
            gl.glVertex2f(-0.1f, -0.6f);
            gl.glVertex2f(0.2f, -0.6f);
            gl.glVertex2f(0.2f, -0.65f);
        gl.glEnd();
//           
        gl.glLoadIdentity();
        gl.glColor3f(1.0f,0,0);
        gl.glBegin(GL2.GL_QUADS);
            gl.glVertex2f(0.2f, -0.65f);
            gl.glVertex2f(0.2f, -0.6f);
            gl.glVertex2f(0.5f, -0.6f);
            gl.glVertex2f(0.5f, -0.65f);
        gl.glEnd();
        
        gl.glLoadIdentity();
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        gl.glBegin(GL2.GL_QUADS);
            gl.glVertex2f(0.5f, -0.65f);
            gl.glVertex2f(0.5f, -0.6f);
            gl.glVertex2f(0.8f, -0.6f);
            gl.glVertex2f(0.8f, -0.65f);
        gl.glEnd();
//        
        gl.glLoadIdentity();
        gl.glColor3f(1.0f,0,0);
        gl.glBegin(GL2.GL_QUADS);
            gl.glVertex2f(0.8f, -0.65f);
            gl.glVertex2f(0.8f, -0.6f);
            gl.glVertex2f(1f, -0.6f);
            gl.glVertex2f(1f, -0.65f);
        gl.glEnd();

        
        //Blocks Up
        gl.glLoadIdentity();
        gl.glColor3f(1.0f,0,0);
        gl.glBegin(GL2.GL_QUADS);
            gl.glVertex2f(-1.0f, 0.1f);
            gl.glVertex2f(-1.0f, 0.15f);
            gl.glVertex2f(-0.7f, 0.15f);
            gl.glVertex2f(-0.7f, 0.1f);
        gl.glEnd();
        
        gl.glLoadIdentity();
        gl.glColor3f(1f,1f,1f);
        gl.glBegin(GL2.GL_QUADS);
            gl.glVertex2f(-0.7f, 0.1f);
            gl.glVertex2f(-0.7f, 0.15f);
            gl.glVertex2f(-0.4f, 0.15f);
            gl.glVertex2f(-0.4f, 0.1f);
        gl.glEnd();
        
//        
        gl.glLoadIdentity();
        gl.glColor3f(1.0f,0,0);
        gl.glBegin(GL2.GL_QUADS);
            gl.glVertex2f(-0.4f, 0.1f);
            gl.glVertex2f(-0.4f, 0.15f);
            gl.glVertex2f(-0.1f, 0.15f);
            gl.glVertex2f(-0.1f, 0.1f);
        gl.glEnd();
        
        gl.glLoadIdentity();
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        gl.glBegin(GL2.GL_QUADS);
            gl.glVertex2f(-0.1f, 0.1f);
            gl.glVertex2f(-0.1f, 0.15f);
            gl.glVertex2f(0.2f, 0.15f);
            gl.glVertex2f(0.2f, 0.1f);
        gl.glEnd();
//           
        gl.glLoadIdentity();
        gl.glColor3f(1.0f,0,0);
        gl.glBegin(GL2.GL_QUADS);
            gl.glVertex2f(0.2f, 0.1f);
            gl.glVertex2f(0.2f, 0.15f);
            gl.glVertex2f(0.5f, 0.15f);
            gl.glVertex2f(0.5f, 0.1f);
        gl.glEnd();
        
        gl.glLoadIdentity();
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        gl.glBegin(GL2.GL_QUADS);
            gl.glVertex2f(0.5f, 0.1f);
            gl.glVertex2f(0.5f, 0.15f);
            gl.glVertex2f(0.8f, 0.15f);
            gl.glVertex2f(0.8f, 0.1f);
        gl.glEnd();
//        
        gl.glLoadIdentity();
        gl.glColor3f(1.0f,0,0);
        gl.glBegin(GL2.GL_QUADS);
            gl.glVertex2f(0.8f, 0.1f);
            gl.glVertex2f(0.8f, 0.15f);
            gl.glVertex2f(1f, 0.15f);
            gl.glVertex2f(1f, 0.1f);
        gl.glEnd();
        
        //Street Lines
        
        gl.glLoadIdentity();
        gl.glColor3f(1.0f,1f,1f);
        gl.glBegin(GL2.GL_QUADS);
            gl.glVertex2f(-1.0f, -0.26f);
            gl.glVertex2f(-1.0f, -0.23f);
            gl.glVertex2f(-0.8f, -0.23f);
            gl.glVertex2f(-0.8f, -0.26f);
        gl.glEnd();
        
         gl.glLoadIdentity();
        gl.glColor3f(1.0f,1f,1f);
        gl.glBegin(GL2.GL_QUADS);
            gl.glVertex2f(-0.6f, -0.26f);
            gl.glVertex2f(-0.6f, -0.23f);
            gl.glVertex2f(-0.4f, -0.23f);
            gl.glVertex2f(-0.4f, -0.26f);
        gl.glEnd();
        
        gl.glLoadIdentity();
        gl.glColor3f(1.0f,1f,1f);
        gl.glBegin(GL2.GL_QUADS);
            gl.glVertex2f(-0.2f, -0.26f);
            gl.glVertex2f(-0.2f, -0.23f);
            gl.glVertex2f(0f, -0.23f);
            gl.glVertex2f(0f, -0.26f);
        gl.glEnd();
        
        gl.glLoadIdentity();
        gl.glColor3f(1.0f,1f,1f);
        gl.glBegin(GL2.GL_QUADS);
            gl.glVertex2f(0.2f, -0.26f);
            gl.glVertex2f(0.2f, -0.23f);
            gl.glVertex2f(0.4f, -0.23f);
            gl.glVertex2f(0.4f, -0.26f);
        gl.glEnd();
        
        gl.glLoadIdentity();
        gl.glColor3f(1.0f,1f,1f);
        gl.glBegin(GL2.GL_QUADS);
            gl.glVertex2f(0.6f, -0.26f);
            gl.glVertex2f(0.6f, -0.23f);
            gl.glVertex2f(0.8f, -0.23f);
            gl.glVertex2f(0.8f, -0.26f);
        gl.glEnd();
        
        gl.glLoadIdentity();
        gl.glColor3f(1.0f,1f,1f);
        gl.glBegin(GL2.GL_QUADS);
            gl.glVertex2f(0.98f, -0.26f);
            gl.glVertex2f(0.98f, -0.23f);
            gl.glVertex2f(1f, -0.23f);
            gl.glVertex2f(1f, -0.26f);
        gl.glEnd();
        
        
        soildCirlce(gl,(float)0.15, (float)-0.84,(float)0.84,360);

        //Animation
        
                // Car
        
        
         gl.glLoadIdentity();
         
//         gl.glTranslatef(trans1, 0, 0);

         
        gl.glColor3f(.255f, 0.412f, 1f);
        gl.glBegin(GL2.GL_QUADS);//car
        
        gl.glVertex2f(0.0f, -0.4f);
        gl.glVertex2f(-0.5f, -0.4f);
        gl.glVertex2f(-0.46f, -0.2f);
        gl.glVertex2f(-0.04f, -0.2f);
        
        trans1 += 0.02f;
        
        gl.glEnd();
        
        

        
        gl.glColor3f(.255f, 0.412f, 1f);
        gl.glBegin(GL2.GL_QUADS);//sakef the car
        
        gl.glVertex2f(-0.42f, -0.2f);
        gl.glVertex2f(-0.08f, -0.2f);
        gl.glVertex2f(-0.1f, -0.05f);
        gl.glVertex2f(-0.4f, -0.05f);
        
        gl.glEnd();
        
        //Wheel
       gl.glLoadIdentity();
       gl.glTranslatef(-0.4f,-0.45f, 0);
       gl.glRotatef(angle, 0.0f, 0.0f , 1.0f);
       gl.glTranslatef(0.4f, 0.45f, 0);
        
        soildCirlcWheel(gl, 0.05f, -0.4f, -0.45f, 360);
        
        //Wheel 
       gl.glLoadIdentity();
       gl.glTranslatef(-0.1f,-0.45f, 0);
       gl.glRotatef(angle, 0.0f, 0.0f , 1.0f);
       gl.glTranslatef(0.1f, 0.45f, 0);
        
        
        soildCirlcWheel(gl, 0.05f, -0.1f, -0.45f, 360);

        
        
        gl.glLoadIdentity();
        
        // Stick

        gl.glColor3f( 1f, 0.972549f, 0.862745f);
        gl.glBegin(GL2.GL_QUADS);
            gl.glVertex2f(-0.28f, 0.32f);
            gl.glVertex2f(-0.28f, 0.66f);
            gl.glVertex2f(-0.24f, 0.66f);
            gl.glVertex2f(-0.24f, 0.32f);
        gl.glEnd();
        gl.glFlush();
        
//       gl.glTranslatef(-0.26f,0.65f, 0);
//       gl.glRotatef(angle, 0.0f, 0.0f , 1.0f);
//       gl.glTranslatef(0.26f,- 0.65f, 0);
       
        
        //The mill
        
        gl.glBegin(GL2.GL_TRIANGLES);
        
            gl.glColor3f(1.0f, 0f, 0f);
            gl.glVertex2f(-0.26f, 0.65f);
            gl.glVertex2f(-0.1f, 0.7f);
            gl.glVertex2f(-0.1f, 0.6f);
            
            gl.glColor3f(1.0f, 0f, 0f);
            gl.glVertex2f(-0.26f, 0.65f);
            gl.glVertex2f(-0.31f, 0.8f);
            gl.glVertex2f(-0.21f, 0.8f);
           
            gl.glColor3f(1.0f, 0f, 0f);
            gl.glVertex2f(-0.26f, 0.65f);
            gl.glVertex2f(-0.4f, 0.7f);
            gl.glVertex2f(-0.4f, 0.6f);
            
            
            gl.glColor3f(1.0f, 0f, 0f);
            gl.glVertex2f(-0.26f, 0.65f);
            gl.glVertex2f(-0.21f, 0.5f);
            gl.glVertex2f(-0.31f, 0.5f);
            
        gl.glEnd();        
        gl.glFlush();
     
        angle += 0.05f;
        
      
         //Mill right
        
        gl.glLoadIdentity();
        
        // Stick R

        gl.glColor3f( 1f, 0.972549f, 0.862745f);
        gl.glBegin(GL2.GL_QUADS);
            gl.glVertex2f(0.61f, 0.32f);
            gl.glVertex2f(0.61f, 0.66f);
            gl.glVertex2f(0.65f, 0.66f);
            gl.glVertex2f(0.65f, 0.32f);
        gl.glEnd();
        gl.glFlush();
        
//       gl.glTranslatef(0.63f,0.65f, 0);
//       gl.glRotatef(angle, 0.0f, 0.0f , 1.0f);
//       gl.glTranslatef(-0.63f,- 0.65f, 0);
        
        //The mill R
        
        gl.glBegin(GL2.GL_TRIANGLES);
        
            gl.glColor3f(1.0f, 0f, 0f);
            gl.glVertex2f(0.63f, 0.65f);
            gl.glVertex2f(0.73f, 0.7f);
            gl.glVertex2f(0.73f, 0.6f);
            
            gl.glColor3f(1.0f, 0f, 0f);
            gl.glVertex2f(0.63f, 0.65f);
            gl.glVertex2f(0.58f, 0.75f);
            gl.glVertex2f(0.68f, 0.75f);
           
            gl.glColor3f(1.0f, 0f, 0f);
            gl.glVertex2f(0.63f, 0.65f);
            gl.glVertex2f(0.53f, 0.7f);
            gl.glVertex2f(0.53f, 0.6f);
            
            
            gl.glColor3f(1.0f, 0f, 0f);
            gl.glVertex2f(0.63f, 0.65f);
            gl.glVertex2f(0.68f, 0.55f);
            gl.glVertex2f(0.58f, 0.55f);
            
        gl.glEnd();        
        gl.glFlush();
     
        angle += 1.0f;
        

        }
    
        public void soildCirlce(GL2 gl, float radius,float xCenter,float yCenter, int alpha){
        gl.glBegin(GL2.GL_LINES);
        for(int i=0;i<alpha;i++){
            gl.glColor3f(1f, 1f, 0);
            gl.glVertex2f(xCenter,yCenter);
            double angle=i*Math.PI/180;
            float x=(float) (radius*Math.cos(angle));
            float y=(float)(radius*Math.sin(angle));
              gl.glVertex2f(xCenter+x,yCenter+y);          
        }
        gl.glEnd(); 
    }
        
         public void soildCirlcWheel(GL2 gl, float radius,float xCenter,float yCenter, int alpha){
        gl.glBegin(GL2.GL_LINES);
        for(int i=0;i<alpha;i++){
            gl.glColor3f(0.5f, 0.5f, 0.5f);
            gl.glVertex2f(xCenter,yCenter);
            double angle=i*Math.PI/180;
            float x=(float) (radius*Math.cos(angle));
            float y=(float)(radius*Math.sin(angle));
              gl.glVertex2f(xCenter+x,yCenter+y);          
        }
        gl.glEnd();
       }
        
        
    

    public static void main(String[] args) {
        
                
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        final GLCanvas glcanvas = new GLCanvas(capabilities);
        ClippingLab l = new ClippingLab();
        glcanvas.addGLEventListener(l);
        glcanvas.setSize(700, 700);
        
        final JFrame frame = new JFrame("Lab");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(glcanvas);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setVisible(true);
        
        FPSAnimator animator = new FPSAnimator(glcanvas, 1000, true);
        animator.start();

    }

    public void dispose(GLAutoDrawable arg0) {}
    public void init(GLAutoDrawable arg0) {}
    public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {}

}