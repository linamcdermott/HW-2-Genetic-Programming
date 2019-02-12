package Genetic_Programming.Tree;

import java.util.Random;

public class Tree {
	
    Node root;
    String expression = "";
    private Random random = new Random();
    private static String[] operators = {"+", "-", "/", "*"};
    
    /** Basic node class. The value can be a variable, number, or operator. **/
    private static class Node {
    	
    	public Node left;
    	public Node right;
     // public Node parent;
    	public String value;
    	
    }
	
    /** Basic constructor for a tree. **/
    public Tree () {
    	root = null;
    }
    
	/**
	 * Constructs a random tree of a given depth.
	 * @param depth the depth of the tree to be created.
	 */
	public Tree (int depth) {
		root = new Node();
		//If we're at a leaf node
		if (depth == 1) {
			root.left = null;
			root.right = null;
			double probablity = Math.random();
			//About half of the time, leaf node should be a variable
			if (probablity > 0.5) {
				root.value = "x";
			}
			//Other half of the time, leaf node should be an integer
			else {
				root.value = Integer.toString(random.nextInt(100));
			}
		}
		//If we're at an internal node
		else {
			root.value = operators[random.nextInt(operators.length)];
			root.left = new Tree(depth - 1).root;
			root.right = new Tree(depth - 1).root;
		}
	}
	
	/**
	 * Uses recursive function to clone a tree. Does not change the original tree.
	 */
	public Tree clone() {
		if (root == null) {
			return null;
		}
		Tree clone = new Tree();
		Node nodeCopy = new Node();
		
		String valueCopy = root.value;
		nodeCopy.value = valueCopy;
		clone.root = nodeCopy;
		
		clone.root.left = clone(root.left).root; //Use recursive function to fill in left sub-tree.
		clone.root.right = clone(root.right).root; //Use recursive function to fill in right sub-tree.
		
		return clone;
	}
	
	public Tree clone(Node currentRoot) {
		Tree subtreeClone = new Tree();
		Node nodeCopy = new Node();
		
		if (currentRoot == null) {
			return subtreeClone;
		}
		
		String valueCopy = currentRoot.value;
		nodeCopy.value = valueCopy;
		subtreeClone.root = nodeCopy;
		
		subtreeClone.root.left = clone(currentRoot.left).root; //Use recursive function to fill in left sub-tree.
		subtreeClone.root.right = clone(currentRoot.right).root; //Use recursive function to fill in right sub-tree.
		
		return subtreeClone;
	}
	
	//TODO: implement
	public Tree crossover(Tree t1) {
		//should use a COPY of the original tree (don't mutate the originals)
		return null;
	}
	
	//TODO: implement
	public Tree mutate() {
		//should return a mutated COPY
		return null;
	}
	
	/**
	 * Evaluates the expression represented by the tree for a given value x using a post-order traversal.
	 * @param x the value x for which the expression should be evaluated.
	 */
	public double evaluateTree(int x, Node currentNode) {
		if (currentNode.left == null) {
			if (currentNode.value.equals("x")) {
				return x;
			}
			return Double.parseDouble(currentNode.value);
		}
		else {
			String op =  currentNode.value;
			return evaluateExpression(
					x, 
					op, 
					String.valueOf(evaluateTree(x, currentNode.left)), 
					String.valueOf(evaluateTree(x, currentNode.right)));
		}
	}
	
	/**
	 * Evaluates the expression given by any subtree.
	 * @param x the input x for the function.
	 * @param op the operator.
	 * @param left the left side of the expression.
	 * @param right the right side of the expression.
	 * @return the value calculated by the input expression.
	 */
	public double evaluateExpression(int x, String op, String left, String right) {
		double num1;
		double num2;
		//Check if either of the nodes are "x"; if so, substitute x-value.
		if (left.equals("x")) { num1 = x; }
		else { num1 = Double.parseDouble(left); }
		if (right.equals("x")) { num2 = x; }
		else { num2 = Double.parseDouble(right); }
		
		if (op.equals("/")) {
			//Can't divide by 0.
			if (num2 == 0) {
				return 1;
			}
			return num1 / num2;
		}
		else if (op.equals("*")) {
			return num1 * num2;
		}
		else if (op.equals("+")) {
			return num1 + num2;
		}
		else if (op.equals("-")){
			return num1 - num2;
		}
		else {
			return 0;
		}
	}
	
	/**
	 * Prints the expression represented by the tree using an in-order traversal.
	 * @param currentRoot the root of the subtree currently being printed.
	 */
	public void printExpression(Node currentRoot) {
		if (currentRoot == null) {
			return;
		}
		
		printExpression(currentRoot.left);
		System.out.print(" ");
		System.out.print(currentRoot.value);
		System.out.print(" ");
		printExpression(currentRoot.right);
	}
	
	public void print() {
		print(root, "", true);
	}
	
	private void print(Node currentRoot, String prefix, boolean isTail) {
		System.out.println(prefix + (isTail ? "└── " : "├── ") + currentRoot.value.toString());
        
        if (currentRoot.left != null) {
        	print(currentRoot.left, prefix + (isTail ? "    " : "│   "), false);
        }
        if (currentRoot.right != null) {
        	print(currentRoot.right, prefix + (isTail ?"    " : "│   "), true);
        }
	}
	
}
