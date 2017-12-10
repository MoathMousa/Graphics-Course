
import javax.swing.JFrame;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

public class LightLab2 implements GLEventListener {

    float roro = -90.0f;
    float xt = 0f;
        private float rtri;


    public static void main(String[] args) {
        GLProfile glProfile = GLProfile.get(GLProfile.GL2);
        GLCapabilities glCapabilities = new GLCapabilities(glProfile);
        GLCanvas glCanvas = new GLCanvas(glCapabilities);
        glCanvas.setSize(1000, 1000);
        LightLab2 driver = new LightLab2();
        glCanvas.addGLEventListener(driver);
        JFrame frame = new JFrame();
        frame.setSize(1000, 1000);
        frame.setVisible(true);
        frame.getContentPane().add(glCanvas);
        final FPSAnimator animator = new FPSAnimator(glCanvas, 300, true);
        animator.start();
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
          drawSphere(0.1f, 50, 50,0.9f,0.9f,0.3f, gl);

        gl.glFlush();

    }

    @Override
    public void dispose(GLAutoDrawable arg0) {
        // TODO Auto-generated method stub

    }

     @Override
    public void init(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

        //gl.glShadeModel(GL2.GL_SMOOTH);
        //LIGHTING         
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT0);
        gl.glEnable(GL2.GL_LIGHT1);
        gl.glEnable(GL2.GL_LIGHT2);
        gl.glEnable(GL2.GL_LIGHT3);

        //LIGHT 0
        float[] diffuseLight0 = {1f, 0f, 0f, 0f}; //light diffuse lighting
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, diffuseLight0, 0);
        float[] light0_position = {0.5f, 0.5f, 20, 1.0f};
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, light0_position, 0);
       
        
        
        float[] diffuseLight1= {0f, 1f, 0f, 0f}; //light diffuse lighting
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_DIFFUSE, diffuseLight1, 0);
        float[] light1_position = {0.5f, 0.5f, 2f, 1.0f};
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, light1_position, 0);
        
        
         float[] diffuseLight2 = {0f, 0f, 1f, 0f}; //light diffuse lighting
        gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_DIFFUSE, diffuseLight2, 0);
        float[] light2_position = {.5f, .5f, 1.5f, 1.0f};
        gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_POSITION, light2_position, 0);
        
/*
        //LIGHT 1
       float[] diffuseLight1 = {0f, 1f, 0f, 0f}; //light diffuse lighting
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_DIFFUSE, diffuseLight1, 0);
        float[] light1_position = {0.5f, 0.5f, 20, 1.0f};
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, light1_position, 0);
      
    /*    //LIGHT 2
        float[] diffuseLight2 = {0f, 0f, 1f, 0f}; //light diffuse lighting
        gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_DIFFUSE, diffuseLight2, 0);
        float[] light2_position = {.5f, .5f, .5f, 1.0f};
        gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_POSITION, light2_position, 0);
/*
        //LIGHT 3
        float[] diffuseLight3 = {0f, 0f, 1f, 0f}; //light diffuse lighting
        gl.glLightfv(GL2.GL_LIGHT3, GL2.GL_DIFFUSE, diffuseLight3, 0);
        float[] light3_position = {1.0f, 1.0f, 1f, 1.0f};
        gl.glLightfv(GL2.GL_LIGHT3, GL2.GL_POSITION, light3_position, 0);
*/
        
          drawSphere(0.1f, 50, 50,-0.9f,-0.9f,0.3f, gl);
          
          drawSphere(0.1f, 50, 50,-0.5f,-0.5f,0.3f, gl);
          
          drawSphere(0.1f, 50, 50,0.4f,0.4f,0.3f, gl);

    }

    void drawSphere(float r, int lats, int longs,float xinc,float yinc,float percent, GL2 gl) {
        int i, j;
        for (i = 0; i <= lats; i++) {
            double lat0 = Math.PI * (-0.5 + (double) (i - 1) / lats);
            double z0 = Math.sin(lat0);
            double zr0 = Math.cos(lat0);

            double lat1 = Math.PI * (-0.5 + (double) i / lats);
            double z1 = Math.sin(lat1);
            double zr1 = Math.cos(lat1);
            gl.glBegin(gl.GL_QUAD_STRIP);
            for (j = 0; j <= longs; j++) {
                double lng = 2 * Math.PI * (double) (j - 1) / longs;
                double x = percent*Math.cos(lng);
                double y = percent*Math.sin(lng);

                gl.glNormal3d(xinc + x * zr0,yinc+ y * zr0, z0);
                gl.glVertex3d(xinc + x  * zr0,yinc + y * zr0 , z0);
                gl.glNormal3d(xinc + x * zr1,yinc+ y * zr1, z1);
                gl.glVertex3d(xinc +x  * zr1,yinc + y * zr1 , z1);
                

            }
            
//            gl.glTranslated(0.6f, 0.6f, 0);

            gl.glEnd();
        }
    }


    @Override
    public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {
        // TODO Auto-generated method stub

    }

    private void drawMawg(GL2 gl, float sy,float cx) {
        gl.glTranslatef(xt-cx, 0, 0);
        xt += 0.0001f;
        if (xt > 0.2) {
            xt = 0;
        }
        float w = -1;
        double k = 0;

        for (; w < 1.4; w += .001) {
            gl.glBegin(GL2.GL_LINES);

            gl.glVertex3f(w, 0, 0.2f);

            k += 0.009;
            gl.glVertex3f(w, sy + ((float) Math.abs(Math.sin(k)) / 10), 0.2f);
            gl.glEnd();

        }
        gl.glLoadIdentity();

    }

    private void drawFish(GL2 gl) {
        roro += 0.5f;
        if (roro > 90) {
            roro = -90;
        }
        System.out.println(roro);

        gl.glRotatef(roro % 360, 0, 0, -1);
        gl.glTranslatef(roro / 100, 0.5f, 0);

        gl.glColor3f(1f, 1f, .9f);

        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex2f(-0.2f, 0f);
        gl.glVertex2f(0f, 0.1f);
        gl.glVertex2f(0.2f, 0f);
        gl.glVertex2f(0f, -0.1f);
        gl.glEnd();
        gl.glBegin(GL2.GL_TRIANGLES);
        gl.glVertex2f(-0.15f, 0f);
        gl.glVertex2f(-0.25f, 0f);
        gl.glVertex2f(-0.35f, 0.1f);
        gl.glBegin(GL2.GL_TRIANGLES);
        gl.glVertex2f(-0.15f, 0f);
        gl.glVertex2f(-0.25f, 0f);
        gl.glVertex2f(-0.35f, -0.1f);
        gl.glEnd();
        gl.glColor3f(0f, 0f, 0f);

        for (int i = 0; i < 720; i++) {

            gl.glBegin(GL2.GL_LINES);
            gl.glVertex2f(0.10f, 0f);
            gl.glVertex2f((float) (Math.cos(i * Math.PI / 360) / 40 + 0.10), (float) Math.sin(i * Math.PI / 360) / 40);
            gl.glEnd();
        }
        gl.glColor3f(.2f, 0.55f, .9f);

        for (int i = 0; i < 720; i++) {

            gl.glBegin(GL2.GL_LINES);
            gl.glVertex2f(0.10f, 0f);
            gl.glVertex2f((float) (Math.cos(i * Math.PI / 360) / 70 + 0.10), (float) Math.sin(i * Math.PI / 360) / 70);
            gl.glEnd();
        }
    }

    private void drawsun(GL2 gl) {
        gl.glRotatef(-(roro + 50), 0, 0, -1);

        gl.glTranslatef(-(roro + 100) / 5000, 0.9f, 0);
        gl.glColor3f(1f, 0.68f, 0.1f);

        for (int i = 0; i < 720; i++) {

            gl.glBegin(GL2.GL_LINES);
            gl.glVertex2f(0.6f, 0f);
            gl.glVertex2f((float) (Math.cos(i * Math.PI / 360) / 20 + 0.6f), (float) Math.sin(i * Math.PI / 360) / 20);
            gl.glEnd();
        }
    }
    public void marwan(GL2 gl){
        
       
        gl.glLoadIdentity();
        gl.glColor3f(0f, 0f, 1f);
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex2f(-0.01f, 0.2f);
        gl.glVertex2f(-0.01f, -0.9f);
        gl.glVertex2f(0.01f, -0.9f);
        gl.glVertex2f(0.01f, 0.2f);
        gl.glEnd();
       
        
       
     
      gl.glRotatef(rtri, 0.0f, 1.1f, 0.0f);
     
        gl.glColor3f(1f,1f, 0f);
        gl.glBegin(GL2.GL_TRIANGLES);
        gl.glVertex2f(-0.01f, 0.2f);
        gl.glVertex2f(-0.2f, 0.6f);
        gl.glVertex2f(-0.4f, 0.6f);
        gl.glEnd();
        
            gl.glColor3f(0f,1f, 1f);
        gl.glBegin(GL2.GL_TRIANGLES);
        gl.glVertex2f(-1.01f, 0.2f);
        gl.glVertex2f(-0.2f, 0.6f);
        gl.glVertex2f(-0.4f, 0.6f);
        gl.glEnd();
        
        rtri+=0.9f;
    }
}
