package pkgfinal.project;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.awt.TextRenderer;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;

public class Main implements GLEventListener {
    
    private static GLCanvas glcanvas;
    private static FPSAnimator animator;
    
    private static GameTree gameTree;
    
    private static int responseMessageIndex; // When we are about to print a response message, choose the one with this index. This variable is always generated randomly.
    
    private static final String SUCCESS_MESSAGE_0 = "انت معلم";
    private static final String SUCCESS_MESSAGE_1 = "الحاسوب علم ملك";
    private static final String SUCCESS_MESSAGE_2 = "كبيييير";
    private static final String SUCCESS_MESSAGE_3 = "طخ طخ طخ";
    private static final String SUCCESS_MESSAGE_4 = "اسد اسد";
    
    private static final String FAIL_MESSAGE_0 = "عبيط";
    private static final String FAIL_MESSAGE_1 = "بتدرس قلتلي شو";
    private static final String FAIL_MESSAGE_2 = "وبيعطي بيوخد الله";
    private static final String FAIL_MESSAGE_3 = "خير لعله";
    private static final String FAIL_MESSAGE_4 = "فضحتنا";
    
    private static boolean showNodeText = true; // If showNodeText = true, draw the node's data text, else don't.
    private static boolean drawResponse = false; // If drawRespone, draw the response message, else don't.
    private static boolean drawResponseSuccess = false; // If true, then the messages will be after player's succesful move.
    private static boolean inControl = true; // If true, the player can press left or right, else can't.
    private static boolean countingDown = true; // If true, the timeLeft is decreasing, else freeze.
    
    private static byte backgroundMode = 0; // 0 = Blue background / 1 = Green background / 2 = Red background
    
    
    private static int nodeCount = 0; // Number of nodes created so far.
    
    private static final int TIME = 1000; // One second.
    private static int timeLeft = 20 * TIME; // timeLeft is the timer that keeps counting down. Once it reaches 0, it is game over.
    private static Color timeLeftColor = Color.WHITE; // The color of the timeLeft. If timeLeft is < 5, then make it red.
    
    private static int points = 0;
    // subTreeTranslation X and Y are the translations made by the subtree drawing.
    private static float subTreeTranslationX = 0;
    private static float subTreeTranslationY = 0;
    
    // Alpha is the value of the transperency.
    private static float alphaUpper = 1.0f; // Alpha value of the upper node in the subtree drawing.
    private static float alphaLeft = 1.0f; // Alpha value of the left child in the subtree drawing.
    private static float alphaRight = 1.0f; // Alpha value of the right chiled in the subtree drawing.
    
    // drawingState decides if we are drawing the sub tree or the full tree at the moment.
    private static byte drawingState;
    // drawingState constants
    private static final byte SUB_TREE = 0;
    private static final byte FULL_TREE = 1;
    private static final byte QUESTION = 2;
    
    /*// difficultyState decides which difficulty the game is playing in.
    private static byte difficultyState = 0;
    // difficultyState constants
    private static final byte BEGINNER = 0;
    private static final byte EASY = 1;
    private static final byte MEDIUM = 2;
    private static final byte HARD = 3;*/
    
    // Other constants
    private static final int SCREEN_WIDTH = 600;
    private static final int SCREEN_HEIGHT = 600;
    
    private static final float CHILD_GAP = 0.75f; // Space between the two children in the subtree drawing.
    private static final float NODE_RADIUS = 0.18f; // Radius of the node's circle.
    
    private static final boolean LEFT = true;
    private static final boolean RIGHT = false;
    
    private static int currentNumber; // The current number the player should find the proper place to insert into the tree.
    
    // This method draws the subtree and is called from the display method. The subtree is part of the tree that shows
    // up to 3 nodes: an upper node and its left and right children.
    private void drawSubTree(GL2 gl) {
        
        /*
        * Since display is always called, keep checking if the alpha values are less than 1.0f and always try to increase
        * them slowly. This way you would get nice gradual effect of decreasing transparency. alphaUp, alphaLeft and alphaRight
        * are set to zero during the game, and at that moment, here will the place to fix them and put them back at 1.0f.
        * So if you want to make the upper node appear gradually, simply set alphaUp to zero.
        */
        if (alphaUpper < 1.0f)
            alphaUpper += 0.04f;
        else
            alphaUpper = 1.0f;
        
        if (alphaLeft < 1.0f)
            alphaLeft += 0.04f;
        else
            alphaLeft = 1.0f;
        
        if (alphaRight < 1.0f)
            alphaRight += 0.04f;
        else
            alphaRight = 1.0f;
        
        // gl.glColor4f method takes RGBA parameters, and A stands for alpha.
        
        // Always before calling drawNode, set the color to draw the node's inner circle, and then
        // call drawNode.
        gl.glColor4f(0.2f, 0.8f, 0.2f, alphaUpper); // Green color.
        
        // Draw the current node of the subtree (current node is the upper node drawn).
        drawNode(gl, 0, 0.5f, gameTree.getCurrentData(), alphaUpper);
        
        // Draw left child of the current node only if it exists.
        if(gameTree.hasLeft()) {
            gl.glColor4f(0.2f, 0.8f, 0.2f, alphaLeft); // Green color.
            drawNode(gl, -CHILD_GAP, -0.5f, gameTree.getLeftCurrentData(), alphaLeft);
            drawEdge(gl, 0, 0.5f, -CHILD_GAP, -0.5f, LEFT, alphaLeft);
        }
        
        // Draw right child of the current node only if it exists.
        if (gameTree.hasRight()) {
            gl.glColor4f(0.2f, 0.8f, 0.2f, alphaRight); // Green color.
            drawNode(gl, CHILD_GAP, -0.5f, gameTree.getRightCurrentData(), alphaRight);
            drawEdge(gl, 0, 0.5f, CHILD_GAP, -0.5f, RIGHT, alphaRight);
        }
        
    }
    
    // Draw the white edge between two nodes.
    private void drawEdge(GL2 gl, float cx1, float cy1, float cx2, float cy2, boolean left, float alpha) {
        // cx stands for center x and cy stands for center y.
        // To draw an edge, pass in the center coordinates of the two nodes, but state if you are drawing from left or right.
        // If left = true, then you are drawing from the left of the first node (cx1, cy1) and to the right of the second node (cx2, cy2).
        // If left = false, then you are drawing from the right of the first node (cx1, cy1) and to the left of the second node (cx2, cy2).
        
        float temp = NODE_RADIUS / 1.41421f; // NODE_RADIUS * cos 45
        
        // !left = right.
        
        float newX1 = cx1 + (left ? -temp : temp); // If left, decrease temp to start from the left of the first node.
        float newY1 = cy1 - temp; // Decrease temp to get the center's y of the first node.
        float newX2 = cx2 + (left ? temp : -temp); // If left, increase temp to end to the right of the first node.
        float newY2 = cy2 + temp; // Increase temp to get the center's y of the first node.
        
        gl.glColor4f(1f, 1f, 1f, alpha);
        gl.glBegin(GL2.GL_LINES);
        gl.glVertex2f(newX1, newY1);
        gl.glVertex2f(newX2, newY2);
        gl.glEnd();
        
    }
    
    // Draw the node with a filled circle, border and it's integer value in gameTree.
    private void drawNode(GL2 gl, float cx, float cy, int x, float alpha) {
        
        // Always before calling drawNode, set the color to draw the node's inner circle, and then
        // call drawNode.
        drawCircle(gl, cx, cy, NODE_RADIUS, 600, true);
        // Border of the circle (node).
        gl.glColor4f(1f, 1f, 1f, alpha);
        drawCircle(gl, cx, cy, NODE_RADIUS, 360, false);
        
        if (showNodeText) {
            TextRenderer textRenderer = new TextRenderer(new Font("Arial", Font.BOLD, 16));
            
            textRenderer.beginRendering(400, 320);
            textRenderer.setColor(Color.WHITE);
            textRenderer.setSmoothing(true);
            
            // Draw relatively to the node's center coordinates and number's length to shift a little to the left or right.
            int numLength1 = String.valueOf(x).length();
            textRenderer.draw(String.valueOf(x), (int) (190 + cx * 190 + - numLength1 + 2), (int) (157 + cy * 157));
            
            textRenderer.endRendering();
            textRenderer.flush();
        }
        
    }
    
    private void drawCircle(GL2 gl, float cx, float cy, float r, int num_segments, boolean filled) {
    
        // If filled = true, then draw a filled cricle.
        // Else draw unfilled cricle (a border).
        
        // If !filled, we are simply going to draw a single circular line,
        // so call gl.glBegin from here and call gl.glEnd after the loop only once.
        // If filled, we are going to draw lots of lines to make the filled circle,
        // so don't call gl.glBegin and gl.glEnd outside the loop. We are going to
        // call them inside the loop.
        
        if (!filled) gl.glBegin(GL2.GL_LINE_STRIP);
        
        for(int i = 0; i < num_segments; i++) {
            
            float theta = (float) (2.0f * Math.PI * i / num_segments);
            
            float x = (float) (r * Math.cos(theta));
            float y = (float) (r * Math.sin(theta));
            
            // If filled, draw a line from center to x + cx and y + cy.
            if (filled) {
                gl.glBegin(GL2.GL_LINES);
                gl.glVertex2f(cx, cy);
                gl.glVertex2f(x + cx, y + cy);
                gl.glEnd();
            } else // If !filled, just put a vertex here as we are only putting vertices around the circle's border to draw one line at the end.
                gl.glVertex2f(x + cx, y + cy);
            
        }
        
        if (!filled) gl.glEnd();
        
    }
    
    // Draw the currentNumber as a text on screen.
    private void drawCurrentNumber() {
        
        TextRenderer textRenderer = new TextRenderer(new Font("Arial", Font.BOLD, 16));
        
        textRenderer.beginRendering(400, 320);
        textRenderer.setColor(Color.WHITE);
        textRenderer.setSmoothing(false);
        textRenderer.draw("Current Number: " + String.valueOf(currentNumber), 130, 305);
        
        textRenderer.endRendering();
        textRenderer.flush();
        
    }
    
    // Draw timeLeft as a text on screen. This well set color always to timeLeftColor.
    private void drawTimeLeft() {
        
        TextRenderer textRenderer = new TextRenderer(new Font("Arial", Font.BOLD, 16));
        
        textRenderer.beginRendering(400, 320);
        textRenderer.setSmoothing(false);
        textRenderer.setColor(timeLeftColor);
        textRenderer.draw("Time Left: " + String.valueOf(timeLeft / (TIME) + "." + timeLeft % (TIME)), 130, 0);
        
        textRenderer.endRendering();
        textRenderer.flush();
        
    }
        private void drawPointsCredit() {
        
        TextRenderer textRenderer = new TextRenderer(new Font("Arial", Font.BOLD, 16));
        
        textRenderer.beginRendering(400, 320);
        textRenderer.setSmoothing(false);
        textRenderer.setColor(Color.WHITE);
        textRenderer.draw("Points: " + String.valueOf(points), 10, 280);
        
        textRenderer.endRendering();
        textRenderer.flush();
        
    }
    
    
    // Draw the response message, the one that appears after success or failure.
    private void drawResponseMessage() {
        
        if (drawResponse) { // Draw only when drawResponse is true.
            TextRenderer textRenderer = new TextRenderer(new Font("Arial", Font.BOLD, 22));
            
            textRenderer.beginRendering(400, 320);
            textRenderer.setSmoothing(false);
            
            String message = ""; // The message to draw
            
            if (drawResponseSuccess) { // Draw a success message.
                
                textRenderer.setColor(Color.RED); // Red on a green background appears better.
                points = points + 1;
                
                switch (responseMessageIndex) { // responseMessageIndex should be randomly generated before.
                    case 0:
                        message = SUCCESS_MESSAGE_0;
                        break;
                    case 1:
                        message = SUCCESS_MESSAGE_1;
                        break;
                    case 2:
                        message = SUCCESS_MESSAGE_2;
                        break;
                    case 3:
                        message = SUCCESS_MESSAGE_3;
                        break;
                    case 4:
                        message = SUCCESS_MESSAGE_4;
                        break;
                    default:
                        break;
                }
                
            } else { // Draw a failure message.
                
                textRenderer.setColor(Color.GREEN); // Green on a red background appears better.
                points = (points - 2);
                if (points < 0)
                    points = 0;
                
                switch (responseMessageIndex) { // responseMessageIndex should be randomly generated before.
                    case 0:
                        message = FAIL_MESSAGE_0;
                        break;
                    case 1:
                        message = FAIL_MESSAGE_1;
                        break;
                    case 2:
                        message = FAIL_MESSAGE_2;
                        break;
                    case 3:
                        message = FAIL_MESSAGE_3;
                        break;
                    case 4:
                        message = FAIL_MESSAGE_4;
                        break;
                    default:
                        break;
                }
                
            }
            
            int x = message.length() * 6 / 2; // Adjust the x of the message to draw.
            
            textRenderer.draw(message, 170 - x, 100);
            textRenderer.endRendering();
            textRenderer.flush();
            
        }
    }
    
    // Called from the display method.
    // Make four squares to fill the background.
    // Draw blue if normal playing, green if successful move and red if fail.
    private void drawBackground(GL2 gl) {
        
        if (backgroundMode == 0) { // Draw a blue background.
            gl.glBegin(GL2.GL_QUADS);
            gl.glColor3f(0.0f, 0.0f, 0.34f);
            gl.glVertex2f(-1.0f, 1.0f);
            gl.glVertex2f(0.0f, 1.0f);
            gl.glColor3f(0.0f, 0.0f, 0.9f);
            gl.glVertex2f(0.0f, 0.0f);
            gl.glColor3f(0.0f, 0.0f, 0.34f);
            gl.glVertex2f(-1.0f, 0.0f);
            gl.glEnd();
            
            gl.glBegin(GL2.GL_QUADS);
            gl.glColor3f(0.0f, 0.0f, 0.34f);
            gl.glVertex2f(1.0f, 1.0f);
            gl.glVertex2f(0.0f, 1.0f);
            gl.glColor3f(0.0f, 0.0f, 0.9f);
            gl.glVertex2f(0.0f, 0.0f);
            gl.glColor3f(0.0f, 0.0f, 0.34f);
            gl.glVertex2f(1.0f, 0.0f);
            gl.glEnd();
            
            gl.glBegin(GL2.GL_QUADS);
            gl.glColor3f(0.0f, 0.0f, 0.34f);
            gl.glVertex2f(-1.0f, -1.0f);
            gl.glVertex2f(0.0f, -1.0f);
            gl.glColor3f(0.0f, 0.0f, 0.9f);
            gl.glVertex2f(0.0f, 0.0f);
            gl.glColor3f(0.0f, 0.0f, 0.34f);
            gl.glVertex2f(-1.0f, 0.0f);
            gl.glEnd();
            
            gl.glBegin(GL2.GL_QUADS);
            gl.glColor3f(0.0f, 0.0f, 0.34f);
            gl.glVertex2f(1.0f, -1.0f);
            gl.glVertex2f(0.0f, -1.0f);
            gl.glColor3f(0.0f, 0.0f, 0.9f);
            gl.glVertex2f(0.0f, 0.0f);
            gl.glColor3f(0.0f, 0.0f, 0.34f);
            gl.glVertex2f(1.0f, 0.0f);
            gl.glEnd();
            
        } else if (backgroundMode == 1) { // Draw a green background.
            
            gl.glBegin(GL2.GL_QUADS);
            gl.glColor3f(0.0f, 0.34f, 0.0f);
            gl.glVertex2f(-1.0f, 1.0f);
            gl.glVertex2f(0.0f, 1.0f);
            gl.glColor3f(0.0f, 0.9f, 0.0f);
            gl.glVertex2f(0.0f, 0.0f);
            gl.glColor3f(0.0f, 0.34f, 0.0f);
            gl.glVertex2f(-1.0f, 0.0f);
            gl.glEnd();
            
            gl.glBegin(GL2.GL_QUADS);
            gl.glColor3f(0.0f, 0.34f, 0.0f);
            gl.glVertex2f(1.0f, 1.0f);
            gl.glVertex2f(0.0f, 1.0f);
            gl.glColor3f(0.0f, 0.9f, 0.0f);
            gl.glVertex2f(0.0f, 0.0f);
            gl.glColor3f(0.0f, 0.34f, 0.0f);
            gl.glVertex2f(1.0f, 0.0f);
            gl.glEnd();
            
            gl.glBegin(GL2.GL_QUADS);
            gl.glColor3f(0.0f, 0.34f, 0.0f);
            gl.glVertex2f(-1.0f, -1.0f);
            gl.glVertex2f(0.0f, -1.0f);
            gl.glColor3f(0.0f, 0.9f, 0.0f);
            gl.glVertex2f(0.0f, 0.0f);
            gl.glColor3f(0.0f, 0.34f, 0.0f);
            gl.glVertex2f(-1.0f, 0.0f);
            gl.glEnd();
            
            gl.glBegin(GL2.GL_QUADS);
            gl.glColor3f(0.0f, 0.34f, 0.0f);
            gl.glVertex2f(1.0f, -1.0f);
            gl.glVertex2f(0.0f, -1.0f);
            gl.glColor3f(0.0f, 0.9f, 0.0f);
            gl.glVertex2f(0.0f, 0.0f);
            gl.glColor3f(0.0f, 0.34f, 0.0f);
            gl.glVertex2f(1.0f, 0.0f);
            gl.glEnd();
            
        } if (backgroundMode == 2) { // Draw a red background.
            
            gl.glBegin(GL2.GL_QUADS);
            gl.glColor3f(0.34f, 0.0f, 0.0f);
            gl.glVertex2f(-1.0f, 1.0f);
            gl.glVertex2f(0.0f, 1.0f);
            gl.glColor3f(0.9f, 0.0f, 0.0f);
            gl.glVertex2f(0.0f, 0.0f);
            gl.glColor3f(0.34f, 0.0f, 0.0f);
            gl.glVertex2f(-1.0f, 0.0f);
            gl.glEnd();
            
            gl.glBegin(GL2.GL_QUADS);
            gl.glColor3f(0.34f, 0.0f, 0.0f);
            gl.glVertex2f(1.0f, 1.0f);
            gl.glVertex2f(0.0f, 1.0f);
            gl.glColor3f(0.9f, 0.0f, 0.0f);
            gl.glVertex2f(0.0f, 0.0f);
            gl.glColor3f(0.34f, 0.0f, 0.0f);
            gl.glVertex2f(1.0f, 0.0f);
            gl.glEnd();
            
            gl.glBegin(GL2.GL_QUADS);
            gl.glColor3f(0.34f, 0.0f, 0.0f);
            gl.glVertex2f(-1.0f, -1.0f);
            gl.glVertex2f(0.0f, -1.0f);
            gl.glColor3f(0.9f, 0.0f, 0.0f);
            gl.glVertex2f(0.0f, 0.0f);
            gl.glColor3f(0.34f, 0.0f, 0.0f);
            gl.glVertex2f(-1.0f, 0.0f);
            gl.glEnd();
            
            gl.glBegin(GL2.GL_QUADS);
            gl.glColor3f(0.34f, 0.0f, 0.0f);
            gl.glVertex2f(1.0f, -1.0f);
            gl.glVertex2f(0.0f, -1.0f);
            gl.glColor3f(0.9f, 0.0f, 0.0f);
            gl.glVertex2f(0.0f, 0.0f);
            gl.glColor3f(0.34f, 0.0f, 0.0f);
            gl.glVertex2f(1.0f, 0.0f);
            gl.glEnd();
            
        }
        
    }
    
    public void display(GLAutoDrawable drawable) {
        
        final GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        
        // Enable transparency.
        gl.glEnable(GL2.GL_BLEND);
        gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
        
        drawBackground(gl);
        drawCurrentNumber();
        drawTimeLeft();
        drawPointsCredit();
        drawResponseMessage();
        
        gl.glTranslatef(subTreeTranslationX, subTreeTranslationY, 0); // Always translate the sub tree.
        drawSubTree(gl);
        
        gl.glFlush();
        
    }
    
    public void dispose(GLAutoDrawable arg0) {}
    
    public void init(GLAutoDrawable arg0) {}
    
    public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {}
    
    public static void main(String[] args) {
        
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        glcanvas = new GLCanvas(capabilities);
        Main l = new Main();
        glcanvas.addGLEventListener(l);
        
        glcanvas.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        
        final JFrame frame = new JFrame("Tree Builder");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(glcanvas);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setVisible(true);
        
        gameTree = new GameTree(false); // gameTree is static. In GameTree's constructor, generate a random number for the root.
        generateNumber(); // Start with a random number.
        
        frame.addKeyListener(new KeyAdapter() {
            
            public void keyPressed(KeyEvent ke) {
                
                if (inControl) {
                    switch (ke.getKeyCode()) {
                        case KeyEvent.VK_LEFT:
                            leftKeyAction();
                            break;
                        case KeyEvent.VK_RIGHT:
                            rightKeyAction();
                            break;
                        default:
                    }
                }
                
            }
        });
        
        animator = new FPSAnimator(glcanvas, 60, true);
        animator.start();
        
        // Create a timer.
        Timer timer = new Timer();
        
        // Make the timer execute run() every 1 millisecond.
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (countingDown) { // If countingDown is false, don't do anything (freeze the timer).
                    if (timeLeft > 0) {
                        timeLeft--; // Keep decrementing every millisecond.
                        if (timeLeft <= 5000) // Timer is about to finish, so write in red.
                            timeLeftColor = Color.RED;
                        else
                            timeLeftColor = Color.WHITE;
                    }
                    else { // timeLeft reached 0.
                        gameOver();
                        
                        timer.cancel();
                        timer.purge();
                    }
                }
            }
            
        }, 0, 1);
        
    }
    
    private static void leftKeyAction() {
        
        inControl = false; // The player can't play now.
        countingDown = false; // Freeze the timer.
        // First check if it is a valid move.
        if (gameTree.isValidMove(currentNumber, GameTree.LEFT)) {
            
            if (gameTree.hasLeft()) { // If there's a left, go left.
                showNodeText = false; // While translating, don't draw the text.
                
                // Several calculations to translate the sub tree.
                float tempX = CHILD_GAP;
                float tempY = 0.5f;
                
                while (tempX > 0) {
                    
                    subTreeTranslationX = CHILD_GAP - tempX;
                    subTreeTranslationY = 1.0f - 2 * tempY;
                    
                    glcanvas.display(); // After each calculation, call display to update the graphics.
                    
                    tempY = (tempY > 0) ? (tempY - 0.02f) : 0;
                    tempX -= 0.03f;
                    
                }
                
                // After finishing the translation, move the current node to the left in gameTree, so that the
                // left child goes in place of the upper node and reset translation variables, and the
                // new sub tree's root becomes this child. This way, we create the effect of going down through the tree.
                gameTree.goLeft();
                
                subTreeTranslationX = 0;
                subTreeTranslationY = 0;
                
                // Make the children's alpha, alphaLeft and alphaRight 0,
                // so that drawSubTree() method gradually increases alphaLeft and alphaRight while drawing nodes.
                alphaLeft = 0.0f;
                alphaRight = 0.0f;
                
                showNodeText = true; // After done translating the subtree, we can draw the text.
                inControl = true; // The player can play now.
                countingDown = true; // Resume the timer.
                
            } else {
                // If there is no left, this means we found the correct place for this number. Prepare for the next round.
                // Insert the number to the tree, and drawSubTree will autmatically draw the new child.
                // Make alphaLeft = 0 to make it appear slowly.
                gameTree.insert(currentNumber);
                alphaLeft = 0.0f;
                
                // We must draw a response.
                responseMessageIndex = new Random().nextInt(5); // First generate a response index.
                drawResponseSuccess = true; // This is a successful move.
                drawResponse = true; // Allow response to draw.
                
                // For the next few lines the following effect occurs:
                // Paint the background blue, then green, then blue, then green... every 200 milliseconds.
                // to make a flashing green effect, indicating player's succesful move.
                backgroundMode = 1; // Background color to green.
                glcanvas.display(); // Redraw everything (to redraw the background).
                
                // Timer after 200 milliseconds, make background blue.
                Timer timer = new Timer();
                
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        backgroundMode = 0;
                        glcanvas.display();
                    }
                    
                }, 200);
                
                // Timer after 400 milliseconds (200 milliseconds after the previous), make background green.
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        backgroundMode = 1;
                        glcanvas.display();
                    }
                    
                }, 400);
                
                // Timer after 600 milliseconds (200 milliseconds after the previous), make background blue.
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        backgroundMode = 0;
                        glcanvas.display();
                    }
                    
                }, 600);
                
                
                // Timer after 800 milliseconds (200 milliseconds after the previous), make background green.
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        backgroundMode = 1;
                        glcanvas.display();
                    }
                    
                }, 800);
                
                // Timer after 1000 milliseconds (200 milliseconds after the previous), make background blue and restore everything.
                // This is the final timer.
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        
                        backgroundMode = 0; // Background to blue.
                        glcanvas.display();
                        
                        inControl = true; // The player can play now.
                        drawResponse = false; // Hide the response.
                        countingDown = true; // Resume the timer.
                        
                        goToRoot(); // Go to root (next round).
                        generateNumber(); // Generate a new number for the next round.
                        
                        // Since this was a successful move, reward the player with time depending on nodeCount.
                        timeLeft += TIME / 20 + TIME * nodeCount++ / 20;
                        
                        //if (nodeCount < 30)
                        //    nodeCount++;
                        
                    }
                    
                }, 1000);
                
            }
            
        } else {
            
            // Failure move, because the player's move is invalid.
            
            // We must draw a response.
            responseMessageIndex = new Random().nextInt(5); // First generate a response index.
            drawResponseSuccess = false; // Failure response.
            drawResponse = true; // Allow response to draw.
            
            backgroundMode = 2; // Background color to red.
            glcanvas.display(); // Redraw everthing to redraw the background.
            
            // For the next few lines the following effect occurs:
            // Paint the background blue, then red, then blue, then red... every 200 milliseconds.
            // to make a flashing red effect, indicating player's failure move.
            Timer timer = new Timer();
            
            // Timer after 200 milliseconds, make background blue.
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    backgroundMode = 0;
                    glcanvas.display();
                }
                
            }, 200);
            
            // Timer after 400 milliseconds (200 milliseconds after the previous), make background red.
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    backgroundMode = 2;
                    glcanvas.display();
                }
                
            }, 400);
            
            // Timer after 400 milliseconds (200 milliseconds after the previous), make background blue.
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    backgroundMode = 0;
                    glcanvas.display();
                }
                
            }, 600);
            
            // Timer after 600 milliseconds (200 milliseconds after the previous), make background green.
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    backgroundMode = 2;
                    glcanvas.display();
                }
                
            }, 800);
            
            // Timer after 1000 milliseconds (200 milliseconds after the previous), make background blue and restore everything.
            // This is the final timer.
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    
                    backgroundMode = 0; // Background to blue.
                    glcanvas.display();
                    
                    inControl = true; // The player can play now.
                    drawResponse = false; // Hide the response.
                    countingDown = true; // Resume the timer.
                    
                    goToRoot(); // Go to root (next round).
                    generateNumber(); // Generate a new number for the next round.
                    
                    // Since this wasn't a successful move, DO NOT reward the player with time.
                    
                }
                
            }, 1000);
            
        }
        
    }
    
    // Exactly the same as leftKeyAction(), only flip directions (left to right).
    private static void rightKeyAction() {
        
        inControl = false; // The player can't play now.
        countingDown = false; // Freeze the timer.
        // First check if it is a valid move.
        if (gameTree.isValidMove(currentNumber, GameTree.RIGHT)) {
            
            if (gameTree.hasRight()) { // If there's a right, go right.
                showNodeText = false; // While translating, don't draw the text.
                
                // Several calculations to translate the sub tree.
                float tempX = -CHILD_GAP;
                float tempY = 0.5f;
                
                while (tempX < 0) {
                    
                    subTreeTranslationX = -tempX - CHILD_GAP;
                    subTreeTranslationY = 1.0f - 2 * tempY;
                    
                    glcanvas.display(); // After each calculation, call display to update the graphics.
                    
                    tempY = (tempY > 0) ? (tempY - 0.02f) : 0;
                    tempX += 0.03f;
                    
                }
                
                // After finishing the translation, move the current node to the right in gameTree, so that the
                // right child goes in place of the upper node and reset translation variables, and the
                // new sub tree's root becomes this child. This way, we create the effect of going down through the tree.
                gameTree.goRight();
                
                subTreeTranslationX = 0;
                subTreeTranslationY = 0;
                
                // Make the children's alpha, alphaLeft and alphaRight 0,
                // so that drawSubTree() method gradually increases alphaLeft and alphaRight while drawing nodes.
                alphaLeft = 0.0f;
                alphaRight = 0.0f;
                
                showNodeText = true; // After done translating the subtree, we can draw the text.
                inControl = true; // The player can play now.
                countingDown = true; // Resume the timer.
                
            } else {
                // If there is no right, this means we found the correct place for this number. Prepare for the next round.
                // Insert the number to the tree, and drawSubTree will autmatically draw the new child.
                // Make alphaRight = 0 to make it appear slowly.
                gameTree.insert(currentNumber);
                alphaRight = 0.0f;
                
                // We must draw a response.
                responseMessageIndex = new Random().nextInt(5); // First generate a response index.
                drawResponseSuccess = true; // This is a successful move.
                drawResponse = true; // Allow response to draw.
                
                // For the next few lines the following effect occurs:
                // Paint the background blue, then green, then blue, then green... every 200 milliseconds.
                // to make a flashing green effect, indicating player's succesful move.
                backgroundMode = 1; // Background color to green.
                glcanvas.display(); // Redraw everything (to redraw the background).
                
                // Timer after 200 milliseconds, make background blue.
                Timer timer = new Timer();
                
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        backgroundMode = 0;
                        glcanvas.display();
                    }
                    
                }, 200);
                
                // Timer after 400 milliseconds (200 milliseconds after the previous), make background green.
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        backgroundMode = 1;
                        glcanvas.display();
                    }
                    
                }, 400);
                
                // Timer after 600 milliseconds (200 milliseconds after the previous), make background blue.
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        backgroundMode = 0;
                        glcanvas.display();
                    }
                    
                }, 600);
                
                
                // Timer after 800 milliseconds (200 milliseconds after the previous), make background green.
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        backgroundMode = 1;
                        glcanvas.display();
                    }
                    
                }, 800);
                
                // Timer after 1000 milliseconds (200 milliseconds after the previous), make background blue and restore everything.
                // This is the final timer.
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        
                        backgroundMode = 0; // Background to blue.
                        glcanvas.display();
                        
                        inControl = true; // The player can play now.
                        drawResponse = false; // Hide the response.
                        countingDown = true; // Resume the timer.
                        
                        goToRoot(); // Go to root (next round).
                        generateNumber(); // Generate a new number for the next round.
                        
                        // Since this was a successful move, reward the player with time depending on nodeCount.
                        timeLeft += TIME / 20 + TIME * nodeCount++ / 20;
                        
                        //if (nodeCount < 30)
                        //    nodeCount++;
                        
                    }
                    
                }, 1000);
                
            }
            
        } else {
            
            // Failure move, because the player's move is invalid.
            
            // We must draw a response.
            responseMessageIndex = new Random().nextInt(5); // First generate a response index.
            drawResponseSuccess = false; // Failure response.
            drawResponse = true; // Allow response to draw.
            
            backgroundMode = 2; // Background color to red.
            glcanvas.display(); // Redraw everthing to redraw the background.
            
            // For the next few lines the following effect occurs:
            // Paint the background blue, then red, then blue, then red... every 200 milliseconds.
            // to make a flashing red effect, indicating player's failure move.
            Timer timer = new Timer();
            
            // Timer after 200 milliseconds, make background blue.
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    backgroundMode = 0;
                    glcanvas.display();
                }
                
            }, 200);
            
            // Timer after 400 milliseconds (200 milliseconds after the previous), make background red.
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    backgroundMode = 2;
                    glcanvas.display();
                }
                
            }, 400);
            
            // Timer after 400 milliseconds (200 milliseconds after the previous), make background blue.
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    backgroundMode = 0;
                    glcanvas.display();
                }
                
            }, 600);
            
            // Timer after 600 milliseconds (200 milliseconds after the previous), make background green.
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    backgroundMode = 2;
                    glcanvas.display();
                }
                
            }, 800);
            
            // Timer after 1000 milliseconds (200 milliseconds after the previous), make background blue and restore everything.
            // This is the final timer.
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    
                    backgroundMode = 0; // Background to blue.
                    glcanvas.display();
                    
                    inControl = true; // The player can play now.
                    drawResponse = false; // Hide the response.
                    countingDown = true; // Resume the timer.
                    
                    goToRoot(); // Go to root (next round).
                    generateNumber(); // Generate a new number for the next round.
                    
                    // Since this wasn't a successful move, DO NOT reward the player with time.
                    
                }
                
            }, 1000);
            
        }
        
    }
    
    private static void gameOver() {
        // DUN DUN DUN.
        inControl = false;
        countingDown = false;
        inControl = false;
        timeLeft = 0;
        
    }
    
    private static void goToRoot() {
        
        gameTree.resetCurrentNode();
        showNodeText = true;
        
    }
    
    private static void generateNumber() {
        Random rand = new Random();
        
        currentNumber = rand.nextInt(101);
        // Ensure no duplicates.
        while (!gameTree.find(currentNumber))
            currentNumber = rand.nextInt(101);
        
        
    }
    
    /*private static void rightKeyAction() {
        
        inControl = false; // The player can't play now.
        // First check if it is a valid move.
        if (gameTree.isValidMove(currentNumber, GameTree.RIGHT)) {
            countingDown = false;
            if (gameTree.hasRight()) { // If there is a right, go right.
                showText = false; // While translating, don't draw the text.
                
                // Several calculations to translate the sub tree.
                float tempX = -CHILD_GAP;
                float tempY = 0.5f;
                
                while (tempX < 0) {
                    
                    subTreeTranslationX = -tempX - CHILD_GAP;
                    subTreeTranslationY = 1.0f - 2 * tempY;
                    
                    glcanvas.display(); // After each calculation, call display to update the graphics.
                    
                    tempY = (tempY > 0) ? (tempY - 0.02f) : 0;
                    tempX += 0.03f;
                    
                }
                
                // After finishing the translation, move the current node to the right in gameTree, so that the
                // right child goes in place of the upper node and reset translation variables, and the
                // new sub tree's root becomes this child. This way, we create the effect of going down through the tree.
                gameTree.goRight();
                
                subTreeTranslationX = 0;
                subTreeTranslationY = 0;
                
                // Make the children's alpha, alphaLeft and alphaRight 0,
                // so that drawSubTree() method gradually increases alphaLeft and alphaRight while drawing nodes.
                alphaLeft = 0.0f;
                alphaRight = 0.0f;
                
                showText = true; // After done translating the subtree, we can draw the text.
                inControl = true; // The player can play now.
                countingDown = true;
            } else {
                // If there is no right, this means we found the correct place for this number. Prepare for the next round.
                // Insert the number to the tree, and drawSubTree will autmatically draw the new child.
                // Make alphaRight = 0 to make it appear slowly.
                gameTree.insert(currentNumber);
                drawResponseSuccess = true;
                alphaRight = 0.0f;
                drawResponse = true;
                backgroundMode = 1;
                glcanvas.display();
                Timer timer = new Timer();
                responseMessageIndex = new Random().nextInt(5);
                
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        backgroundMode = 0;
                        glcanvas.display();
                    }
                    
                }, 200);
                
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        backgroundMode = 1;
                        glcanvas.display();
                    }
                    
                }, 400);
                
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        backgroundMode = 0;
                        glcanvas.display();
                    }
                    
                }, 600);
                
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        backgroundMode = 1;
                        glcanvas.display();
                    }
                    
                }, 800);
                
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        backgroundMode = 0;
                        glcanvas.display();
                        inControl = true; // The player can play now.
                        drawResponse = false;
                        goToRoot(); // The tree is a normal tree, so directly go to root (next round).
                        generateNumber(); // Generate a new number for the next round.
                        timeLeft += TIME + TIME * nodeCount / 30;
                        if (nodeCount < 30)
                            nodeCount++;
                        countingDown = true;
                    }
                    
                }, 1000);
                
            }
            
        } else {
            
            backgroundMode = 2;
            glcanvas.display();
            Timer timer = new Timer();
            countingDown = false;
            drawResponse = false;
            responseMessageIndex = new Random().nextInt(5);
            drawResponseSuccess = false;
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    backgroundMode = 0;
                    glcanvas.display();
                }
                
            }, 200);
            
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    backgroundMode = 2;
                    glcanvas.display();
                }
                
            }, 400);
            
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    backgroundMode = 0;
                    glcanvas.display();
                }
                
            }, 600);
            
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    backgroundMode = 2;
                    glcanvas.display();
                }
                
            }, 800);
            
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    backgroundMode = 0;
                    glcanvas.display();
                    inControl = true; // The player can play now.
                    drawResponse = false;
                    goToRoot(); // The tree is a normal tree, so directly go to root (next round).
                    generateNumber(); // Generate a new number for the next round.
                    countingDown = true;
                    
                }
                
            }, 1000);
            
        }
        
    }*/
    
    /*private static void upKeyAction() {
    
    // First check if it is a valid move.
    // The player can move up as long as the sub tree doesn't need balancing.
    if (!gameTree.needsBalancing()) {
    
    inControl = false; // The player can't play now.
    
    if (gameTree.hasParent()) { // If there is a parent (not root), go up.
    showText = false; // While translating, don't draw the text.
    
    // Several calculations to translate the sub tree.
    float tempY = 0.5f;
    float tempX;
    
    if (gameTree.getParentDirection() == GameTree.PARENT_IS_FROM_LEFT) {
    // If parent of the current node is from left, then translate to right down.
    
    tempX = -CHILD_GAP;
    
    while (tempX < 0) {
    
    subTreeTranslationX = -tempX - CHILD_GAP;
    subTreeTranslationY = 2 * tempY - 1.0f;
    
    glcanvas.display(); // After each calculation, call display to update the graphics.
    
    tempY = (tempY > 0) ? (tempY - 0.02f) : 0;
    tempX += 0.03f;
    
    }
    
    } else if (gameTree.getParentDirection() == GameTree.PARENT_IS_FROM_RIGHT) {
    // If parent of the current node is from right, then translate to left down.
    
    tempX = CHILD_GAP;
    
    while (tempX > 0) {
    
    subTreeTranslationX = CHILD_GAP - tempX;
    subTreeTranslationY = 2 * tempY - 1.0f;
    
    glcanvas.display(); // After each calculation, call display to update the graphics.
    
    tempY = (tempY > 0) ? (tempY - 0.02f) : 0;
    tempX -= 0.03f;
    
    }
    
    }
    
    // After finishing the translation, move the current node up in gameTree, so that the
    // the parent goes in place of the current node and reset translation variables, and the
    // new sub tree's root becomes the parent of this node. This way, we create the effect of going up through the tree.
    gameTree.goUp();
    
    subTreeTranslationX = 0;
    subTreeTranslationY = 0;
    
    // Make the parent's and the other child's alpha 0,
    // so that drawSubTree() method gradually increases alphaUpper and alphaRight or alphaLeft (depending on the parent's direction)
    // while drawing nodes.
    
    alphaUpper = 0.0f;
    
    if (gameTree.getParentDirection() == GameTree.PARENT_IS_FROM_LEFT) // This means this is a right child.
    alphaLeft = 0.0f;
    else if (gameTree.getParentDirection() == GameTree.PARENT_IS_FROM_RIGHT) // This means this is a left child.
    alphaRight = 0.0f;
    
    showText = true; // After done translating the subtree, we can draw the text.
    inControl = true; // The player can play now.
    
    } else {
    // If there is no parent, this means we are currently at the root and no balancing was need throughout
    // the whole tree. Prepare for the next round.
    
    generateNumber();  // Generate a new number for the next round.
    goToRoot(); // Each round the player starts from the root. Calling this is rather useless but it is put here for extra extra carefulness.
    inControl = true; // The player can play now.
    aVLMode = false; // Finished the balance search mode.
    
    }
    
    }
    
    }*/
    
    /*private static void spaceKeyAction() {
    
    // First check if it is a valid move.
    // Pressing space is valid only when the tree balancing takes place at the current node.
    if (gameTree.needsBalancing()) {
    
    inControl = false; // The player can't play now.
    
    Timer timer = new Timer();
    
    timer.schedule(new TimerTask() {
    @Override
    public void run() {
    goToRoot(); // Each round the player starts from the root.
    inControl = true; // The player can play now.
    aVLMode = false; // Finished the balance search mode.
    }
    }, 1000);
    
    }
    
    }*/
    
}