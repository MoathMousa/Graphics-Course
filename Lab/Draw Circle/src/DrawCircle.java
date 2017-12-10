import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;
import javax.swing.JFrame;
public class DrawCircle implements GLEventListener {
    public void display(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
       
       
        
        soildCirlce(gl,(float)0.2, (float)0.4,(float)0.4,360);
        emptyCirlce(gl,(float)0.2, (float)-0.4,(float)0.4,360);
       
    }
   
    public void soildCirlce(GL2 gl, float radius,float xCenter,float yCenter, int alpha){
        gl.glBegin(GL2.GL_LINES);
        for(int i=0;i<alpha;i++){
            gl.glVertex2f(xCenter,yCenter);
            double angle=i*Math.PI/180;
            float x=(float) (radius*Math.cos(angle));
            float y=(float)(radius*Math.sin(angle));
              gl.glVertex2f(xCenter+x,yCenter+y);          
        }
        gl.glEnd(); 
    }
   
    
     public void emptyCirlce(GL2 gl, float radius,float xCenter,float yCenter, int alpha){
        gl.glBegin(GL2.GL_LINE_LOOP);
        for(int i=0;i<alpha;i++){
            //gl.glVertex2f(xCenter,yCenter);
            double angle=i*Math.PI/180;
            float x=(float) (radius*Math.cos(angle));
            float y=(float)(radius*Math.sin(angle));
              gl.glVertex2f(xCenter+x,yCenter+y);
             
        }
        gl.glEnd();
        
        
        
    }
    public void dispose(GLAutoDrawable arg0) {}
    public void init(GLAutoDrawable arg0) {}
    public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {}
    public static void main(String[] args) {     
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        final GLCanvas glcanvas = new GLCanvas(capabilities);
        DrawCircle l = new DrawCircle();
        glcanvas.addGLEventListener(l);
        glcanvas.setSize(400, 400);
           
        final JFrame frame = new JFrame("");
        frame.getContentPane().add(glcanvas);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setVisible(true);
    }
}