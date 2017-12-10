package pkgfinal.project;
// GameTree is a specialized binary tree data structure for the game.
// Unneeded methods are removed, such as delete.

// TreeNode currentNode: a pointer to keep track where the player currently is.
// boolean isValidMove(int x, boolean direction): direction is left or right (true or false).
//                                              If left and x < currentNode.data, true else false.
//                                              If right and x > currentNode.data, true else false.
// boolean goLeft(): move currentNode to left.
// boolean goRight(): move currentNode to right.
// void resetCurrentNode(): set currentNode to ROOT.
// The find method returns true if the node exists, else false.
// Insertion is the same as in BST and AVL, but a boolean is included to check if it is a normal or AVL BST.

import java.util.Random;

public class GameTree {
    
    // Constants
    public static final boolean LEFT = true;
    public static final boolean RIGHT = false;
    
    public class TreeNode {
        
        int data;
        
        TreeNode left;
        TreeNode right;
        
        TreeNode(int data) {
            this.data = data;
            
            this.left = null;
            this.right = null;
        }
        
    }
    
    private final TreeNode ROOT;
    
    private TreeNode currentNode;
    
    public GameTree(boolean AVLTree) {
        currentNode = ROOT = new TreeNode(new Random().nextInt(101));
    }
    
    public boolean isValidMove(int x, boolean direction) {
        return (direction == LEFT && x < currentNode.data) || (direction == RIGHT && x > currentNode.data);
    }
    
    public int getCurrentData() {
        return currentNode.data;
    }
    
    public int getLeftCurrentData() {
        return currentNode.left.data;
    }
    
    public int getRightCurrentData() {
        return currentNode.right.data;
    }
    
    public boolean hasLeft() {
        return currentNode.left != null;
    }
    
    public boolean hasRight() {
        return currentNode.right != null;
    }
    
    public void goLeft() {
        currentNode = currentNode.left;
    }
    
    public void goRight() {
        currentNode = currentNode.right;
    }
    
    public void resetCurrentNode() {
        currentNode = ROOT;
    }
    
    public void insert(int x) {
        currentNode = insert(x, currentNode);
    }
    
    private TreeNode insert(int x, TreeNode t) {
        
        if (t == null)
            return new TreeNode(x);
        else if (x > t.data)
            t.right = insert(x, t.right);
        else if (x < t.data)
            t.left = insert(x, t.left);
        
        //t.height = Math.max(getHeight(t.left), getHeight(t.right)) + 1;
        
        return t;
        
    }
    
    public boolean find(int x) {
        
        TreeNode t = ROOT;
        
        while (t != null && t.data != x) {
            
            if (x < t.data)
                t = t.left;
            else if (x > t.data)
                t = t.right;
            
        }
        
        return t == null;
        
    }
    
    public boolean isEmpty() {
        return ROOT == null;
    }
    
}
