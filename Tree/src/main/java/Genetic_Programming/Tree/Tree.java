package Genetic_Programming.Tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Tree {
	
	Node root;
	String expression = "";
	private Random random = new Random();
	private static String[] operators = { "+", "-", "/", "*" };
	
	// Data structure to store current population and all trees ever existed
	ArrayList<Tree> currentForest = new ArrayList<Tree>(100);
	ArrayList<ArrayList<Tree>> allForests = new ArrayList<ArrayList<Tree>>();

	/** Basic node class. The value can be a variable, number, or operator. **/
	public static class Node {

		public Node left;
		public Node right;
		public Node parent;
		public boolean isLeft;
		public String value;

		public ArrayList<Node> getChildren() {
			ArrayList<Node> childNodes = new ArrayList<>();
			if (this.left != null) {
				childNodes.add(left);
			}
			if (this.right != null) {
				childNodes.add(right);
			}
			return childNodes;
		}
	}
	
	// Return a random node to be mutated or cross over
	public Node randomNode() {
		Random randomNum = new Random();
		int nodePosition = randomNum.nextInt(this.nodesCounter(root)) + 1;
		System.out.println("Node Pos: " + nodePosition);
		
		return BFS(root, nodePosition);
	}
	
	// This is slow, search the tree twice
	private int nodesCounter(Node currentRoot) {
		int counter = 1;
		if (currentRoot.left != null) {
			counter += nodesCounter(currentRoot.left);
		}
		
		if (currentRoot.right != null) {
			counter += nodesCounter(currentRoot.right);
		}
		
		return counter;
	}

	
	// Use Breadth-first Search to look for a node at position n
	// Eventually,change it to private 
	public Node BFS(Node startNode, int goalNode) {
		int nodeCounter = 1;

		if (nodeCounter == goalNode) {
			System.out.println("Goal Node Found: " + startNode.value.toString());
			return startNode;
		}

		Queue<Node> queue = new LinkedList<>();
		ArrayList<Node> explored = new ArrayList<>();
		queue.add(startNode);
		explored.add(startNode);

		while (!queue.isEmpty()) {
			Node current = queue.remove();
			if (goalNode == nodeCounter) {
//				System.out.println("Goal Node Found: " + current.value.toString());
//				System.out.println("NodeCounter:" + nodeCounter);
				return current;
			} else {
				if (current.left != null) {
		            queue.add(current.left);
            	}
				if (current.right != null) {
		            queue.add(current.right);
				}
			}
			explored.add(current);
			nodeCounter++;
		}

		return null;
	}
	
	
	
	// TODO? - YJ
	// An auxiliary function which allows
	// us to remove any child nodes from
	// our list of child nodes.
	public boolean removeChild(Node n) {
		return false;
	}

	// Do we need this? is Tree (int depth) enough?
	/** Basic constructor for a tree. **/
	public Tree() {
		root = null;
	}

	/**
	 * YJ: Full Grow: Constructs a random tree of a given depth.
	 * 
	 * TODO: Decide whether to build the subtree on a node
	 * 
	 * @param depth the depth of the tree to be created.
	 */
	public Tree(int depth) {
		root = new Node();
		// If we're at a leaf node
		if (depth == 1) {
			root.left = null;
			root.right = null;
			double probablity = Math.random();
			// About half of the time, leaf node should be a variable
			
			// TODO?: change this to 0.67? People say 50% variables is too much
			if (probablity > 0.5) {
				root.value = "x";
			}
			// Other half of the time, leaf node should be an integer
			else {
				root.value = Integer.toString(random.nextInt(5)); // YJ: +/-5 for dataset1
			}
		}
		// If we're at an internal node
		// Full tree is okay
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

		clone.root.left = clone(root.left).root; // Use recursive function to fill in left sub-tree.
		clone.root.right = clone(root.right).root; // Use recursive function to fill in right sub-tree.

		return clone;
	}

	private Tree clone(Node currentRoot) {
		Tree subtreeClone = new Tree();
		Node nodeCopy = new Node();

		if (currentRoot == null) {
			return subtreeClone;
		}

		String valueCopy = currentRoot.value;
		nodeCopy.value = valueCopy;
		subtreeClone.root = nodeCopy;

		subtreeClone.root.left = clone(currentRoot.left).root; // Use recursive function to fill in left sub-tree.
		subtreeClone.root.right = clone(currentRoot.right).root; // Use recursive function to fill in right sub-tree.

		return subtreeClone;
	}

	// TODO: implement
	// Want to pick trees with high fitness
	public Tree crossover(Tree t2) {
		// should use a COPY of the original tree (don't mutate the originals)
		Tree t1Copy = this.clone();
		Tree t2Copy = t2.clone();
		
		Node t1Node = this.randomNode();
		Node t2Node = t2.randomNode();
		Node temp = t2Node;
		
		if(t1Node.left)
		return null;
	}

	// TODO: implement
	// Want to pick trees with high fitness
	public Tree mutate(Tree t1) {
		// should return a mutated COPY
		Node t1Node = t1.randomNode();
		
		return null;
	}

	/**
	 * Evaluates the expression represented by the tree for a given value x using a
	 * post-order traversal.
	 * 
	 * @param x the value x for which the expression should be evaluated.
	 */
	public double evaluateTree(int x, Node currentNode) {
		if (currentNode.left == null) {
			if (currentNode.value.equals("x")) {
				return x;
			}
			return Double.parseDouble(currentNode.value);
		} else {
			String op = currentNode.value;
			return evaluateExpression(x, op, String.valueOf(evaluateTree(x, currentNode.left)),
					String.valueOf(evaluateTree(x, currentNode.right)));
		}
	}

	/**
	 * Evaluates the expression given by any subtree.
	 * 
	 * @param x     the input x for the function.
	 * @param op    the operator.
	 * @param left  the left side of the expression.
	 * @param right the right side of the expression.
	 * @return the value calculated by the input expression.
	 */
	private double evaluateExpression(int x, String op, String left, String right) {
		double num1;
		double num2;
		// Check if either of the nodes are "x"; if so, substitute x-value.
		if (left.equals("x")) {
			num1 = x;
		} else {
			num1 = Double.parseDouble(left);
		}
		if (right.equals("x")) {
			num2 = x;
		} else {
			num2 = Double.parseDouble(right);
		}

		if (op.equals("/")) {
			// Can't divide by 0.
			if (num2 == 0) {
				return 1;
			}
			return num1 / num2;
		} else if (op.equals("*")) {
			return num1 * num2;
		} else if (op.equals("+")) {
			return num1 + num2;
		} else if (op.equals("-")) {
			return num1 - num2;
		} else {
			return 0;
		}
	}

	/**
	 * Prints the expression represented by the tree using an in-order traversal.
	 * 
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
			print(currentRoot.right, prefix + (isTail ? "    " : "│   "), true);
		}
	}
	
	// TODO:
	// How to evaluate complexity score
	// 1. how many nodes in a tree
	//
	// how to evaluate final function, do both
	// 1. set limit on the number of generations, and return the function with the best score
	// 2. converge: keep running until a function is good enough

}
