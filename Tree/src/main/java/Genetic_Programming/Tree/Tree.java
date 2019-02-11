package Genetic_Programming.Tree;

public class Tree {
	
    Node root;
    String expression = "";
    
	public Tree (Node n) {
		root = n;
	}
	
	public Tree (Node n, Tree left, Tree right) {
		root = n;
		root.left = left.root;
		root.right = right.root;
	}
	
	public void evaluateTree(Node currentRoot) {
		if (currentRoot == null) {
			return;
		}
		evaluateTree(currentRoot.left);
		evaluateTree(currentRoot.right);
		expression += " ";
		expression += currentRoot.toString();
		expression += " ";
	}

	public void printTree(Node currentRoot) {
		currentRoot.print();
	}
}
