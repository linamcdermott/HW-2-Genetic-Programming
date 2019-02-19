package Genetic_Programming.Tree;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Stack;
import java.util.*;

public class Tree implements Comparable<Tree>{
	
	//dataset2 max vals
	public static Double maxX1 = 9999629.0;
	public static Double maxX2 = 9999545.0;
	public static Double maxX3 = 10.0;
	public static Double maxfx = 160629674968.55; 
	public static Double maxbfx = 1222361.53; // dataset2 f(x) without outliers

	Node root;
	String expression = "";
	double fitness;
	int complexity;
	private Random random = new Random();
	private static String[] operators = { "+", "-", "/", "*" };
	private static String[] variables = {"x", "y", "z"};

	/** Basic node class. The value can be a variable, number, or operator. **/
	public static class Node {
		public Node left;
		public Node right;
		public String value;
	}
	
	// Return a random node to be mutated or cross over
	public Node randomInternal() {
		Random randomNum = new Random();
		int nodePosition = randomNum.nextInt(this.internalCounter(root)) + 1;
		//System.out.println("Node Pos: " + nodePosition);
		
		return internalDFS(root, nodePosition);
	}
	
	
	// This is slow, search the tree twice
	//Returns number of nodes excluding leaf nodes
	private int internalCounter(Node currentRoot) {
		int counter = 1;
		if (currentRoot.left.left != null) {
			counter += internalCounter(currentRoot.left);
		}
		
		if (currentRoot.right.left != null) {
			counter += internalCounter(currentRoot.right);
		}
		
		return counter;
	}

	// Use Breadth-first Search to look for a node at position n
	// Eventually,change it to private 
	public Node internalDFS(Node startNode, int goalNode) {
		int nodeCounter = 1;

		if (nodeCounter == goalNode) {
			//System.out.println("Goal Node Found: " + startNode.value.toString());
			return startNode;
		}

		Stack<Node> stack = new Stack<>();
		ArrayList<Node> explored = new ArrayList<>();
		stack.add(startNode);
		explored.add(startNode);

		while (!stack.isEmpty()) {
			Node current = stack.pop();
			if (goalNode == nodeCounter) {
				//System.out.println("Goal Node Found: " + current.value.toString());
//				System.out.println("NodeCounter:" + nodeCounter);
				return current;
			} else {
				if (current.left.left != null) {
		            stack.add(current.left);
            	}
				if (current.right.left != null) {
		            stack.add(current.right);
				}
			}
			explored.add(current);
			nodeCounter++;
		}

		return null;
	}
	
	public Node randomNode() {
		Random randomNum = new Random();
		//System.out.println("Node Pos: " + nodePosition);
		int totalNodes = this.nodesCounter(root);
		int nodePosition = randomNum.nextInt(totalNodes) + 1;
		
		return DFS(root, nodePosition);
	}
	
	
	// This is slow, search the tree twice
	//Returns number of nodes excluding leaf nodes
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
	
	public int getComplexity() {
		this.complexity = nodesCounter(root);
		return this.complexity;
	}

	// Use Breadth-first Search to look for a node at position n
	// Eventually,change it to private 
	public Node DFS(Node startNode, int goalNode) {
		int nodeCounter = 1;

		if (nodeCounter == goalNode) {
			//System.out.println("Goal Node Found: " + startNode.value.toString());
			return startNode;
		}

		Stack<Node> stack = new Stack<>();
		ArrayList<Node> explored = new ArrayList<>();
		stack.add(startNode);
		explored.add(startNode);

		while (!stack.isEmpty()) {
			Node current = stack.pop();
			if (goalNode == nodeCounter) {
				return current;
			} else {
				if (current.left != null) {
		            stack.add(current.left);
            	}
				if (current.right != null) {
		            stack.add(current.right);
				}
			}
			explored.add(current);
			nodeCounter++;
		}

		return null;
	}
	
	/** Basic constructor for a tree. **/
	public Tree() {
		root = null;
		fitness = Math.pow(2, 63);
	}

	/**
	 * YJ: Full Grow: Constructs a random tree of a given depth.
	 * TODO: Decide whether to build the subtree on a node
	 * 
	 * @param depth the depth of the tree to be created.
	 */
	public Tree(int depth) {
		fitness = Math.pow(2, 63);
		root = new Node();
		// If we're at a leaf node
		if (depth == 1) {
			root.left = null;
			root.right = null;
			double probablity = Math.random();
			// About half of the time, leaf node should be a variable
			
			if (probablity > 0.67) {
				// root.value = "x" //for dataset1
				root.value = variables[random.nextInt(variables.length)]; // ******************choose the dataset ********************
			}
			// Other half of the time, leaf node should be an integer
			else {
				// dataset1 integer.toString(random.nextInt(9) + 1)	// ******************choose the dataset ********************
				root.value = Double.toString(random.nextDouble()); // YJ: exclude 0 & normalized dataset2
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

	@Override
	public int compareTo(Tree t) {
	    if (this.fitness > t.fitness) {
	    	return 1;
	    }
	    if (this.fitness < t.fitness) {
	    	return -1;
	    }
	    return 0;
	  }
	
	/**
	 * Uses recursive function to clone a tree. Does not change the original tree.
	 */
	public Tree clone() {
		if (root == null) {
			return null;
		}
		Tree clone = new Tree();
		clone.fitness = this.fitness;
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
		
		Node t1Node = t1Copy.randomInternal();
		Node t2Node = t2Copy.randomInternal();
		
		if (random.nextBoolean()) {
			t1Node.left = t2Node;
		}
		else {
			t1Node.right = t2Node;
		}
		
		return t1Copy;
	}

	// Want to pick trees with high fitness
	public Tree mutate() {
		// should return a mutated COPY
		Tree copy = this.clone();
		Node randNode = copy.randomNode();
		if (randNode.left != null) {
			randNode.value = operators[random.nextInt(operators.length)];
		}
		else { // Other half of the time, it is an internal node
			double probability = Math.random();
			if (probability > 0.67) {
				randNode.value = variables[random.nextInt(variables.length)];
			}
			else {
				// dataset1 integer.toString(random.nextInt(9) + 1)        // ******************choose the dataset1 ********************
				randNode.value = Double.toString(random.nextDouble()); // YJ: exclude 0 and normalized dataset2
			}
		}
		return copy;
	}
	
	// Training set (80%): Returns the root-mean-squared error
	public double calculateFitness() throws FileNotFoundException, IOException {
		parser.csvParser();
		
		HashMap<Double, Double> dataset = parser.dataset1; // Dataset1
		HashMap<Double, Double> dataset3 = parser.dataset3; //Dataset3
		
		double fitness = 0;
		for (Double key: dataset.keySet()) {
			double treeVal = this.evaluateTree(key, root);
			fitness += Math.pow((dataset.get(key) - treeVal), 2);
		}
		return Math.sqrt(fitness/(dataset.keySet().size()));
	}

	public double calculateFitness2() throws FileNotFoundException, IOException {
		parser.csvParser();
		HashMap<ArrayList<Double>, Double> dataset = parser.dataset2c; //  ****************choose Dataset2 or Dataset2b or dataset2c ****************
		double fitness = 0;
		int count = 0;
		for (ArrayList<Double> key: dataset.keySet()) {
			// Normalize x-values
			double x1 = key.get(0)/maxX1;
			double x2 = key.get(1)/maxX2;
			double x3 = key.get(2)/maxX3;
			double treeVal = this.evaluateTree2(x1, x2, x3, root);
			fitness += Math.pow((dataset.get(key)/maxbfx - treeVal), 2); // *********************** choose maxfx or maxbfx ************ 
			count++;
			//double treeVal = this.evaluateTree(key, root);
			//fitness += Math.pow((dataset.get(key) - treeVal), 2);
		}
		return Math.sqrt(fitness/count);
	}
	
	// Testing set (20%): Returns the root-mean-squared error
	public double testFitness() throws FileNotFoundException, IOException {
		parser.csvParser();
		HashMap<Double, Double> dataset = parser.dataset1Test; // Dataset1 Test
		double fitness = 0;
		for (Double key: dataset.keySet()) {
			double treeVal = this.evaluateTree(key, root);
			fitness += Math.pow((dataset.get(key) - treeVal), 2);
		}
		return Math.sqrt(fitness/(dataset.keySet().size()));
	}
	
	public double testFitness2() throws FileNotFoundException, IOException {
		parser.csvParser();
		HashMap<ArrayList<Double>, Double> dataset = parser.dataset2cTest; // ******************** choose Dataset2Test or Dataset2bTest or dataset2cTest ********* 
		double fitness = 0;
		int count = 0;
		for (ArrayList<Double> key: dataset.keySet()) {
			double x1 = key.get(0)/maxX1;
			double x2 = key.get(1)/maxX2;
			double x3 = key.get(2)/maxX3;
			double treeVal = this.evaluateTree2(x1, x2, x3, root);
			fitness += Math.pow((dataset.get(key)/maxbfx - treeVal), 2);
			count++;
		}
		return Math.sqrt(fitness/count);
	}
	
	/**
	 * Evaluates the expression represented by the tree for a given value x using a
	 * post-order traversal.
	 * 
	 * @param x the value x for which the expression should be evaluated.
	 */
	public double evaluateTree(double x, Node currentNode) {
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
	
	public double evaluateTree2(double x, double y, double z, Node currentNode) {
		if (currentNode.left == null) {
			if (currentNode.value.equals("x")) {
				return x;
			}
			else if (currentNode.value.equals("y")) {
				return y;
			}
			else if (currentNode.value.equals("z")) {
				return z;
			}
			return Double.parseDouble(currentNode.value);
		} else {
			String op = currentNode.value;
			return evaluateExpression2(x, y, z, op, String.valueOf(evaluateTree2(x, y, z, currentNode.left)),
					String.valueOf(evaluateTree2(x, y, z, currentNode.right)));
		}
	}
	
	private double evaluateExpression2(double x, double y, double z, String op, String left, String right) {
		double num1;
		double num2;
		// Check if either of the nodes are "x"; if so, substitute x-value.
		if (left.equals("x")) {
			num1 = x;
		} 
		else if (left.equals("y")) {
			num1 = y;
		}
		else if (left.equals("z")) {
			num1 = z;
		}
		else {
			num1 = Double.parseDouble(left);
		}
		if (right.equals("x")) {
			num2 = x;
		} 
		else if (right.equals("y")) {
			num2 = y;
		}
		else if (right.equals("z")) {
			num2 = z;
		}
		else {
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
	 * Evaluates the expression given by any subtree.
	 * 
	 * @param x     the input x for the function.
	 * @param op    the operator.
	 * @param left  the left side of the expression.
	 * @param right the right side of the expression.
	 * @return the value calculated by the input expression.
	 */
	private double evaluateExpression(double x, String op, String left, String right) {
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
	// how to evaluate final function, do both
	// 1. set limit on the number of generations, and return the function with the best score
	// 2. converge: keep running until a function is good enough

}
