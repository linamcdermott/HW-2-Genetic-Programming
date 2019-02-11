package Genetic_Programming.Tree;

public class Node {
	
	Node left;
	Node right;
	Node parent;
	
	private NodeType nodeType;
	private int value;
	private Operator operator;
	private char variable;
	
	/**public void setLeft(Node child) {
		left = child;
	}
	
	public void setRight(Node child) {
		right = child;
	}**/
	
	
	public Node(int value) {
		left = null;
		right = null;
		
		this.value = value;
		nodeType = NodeType.VALUE;
	}
	
	public Node(char variable) {
		
		left = null;
		right = null;
		this.variable = variable;
		nodeType = NodeType.VARIABLE;
	}
	
	public Node(Operator operator) {
		left = null;
		right = null;
		
		this.operator = operator;
		nodeType = NodeType.OPERATOR;
	}
	
	public String toString() {
		String str = "";
		switch (nodeType) {
		case VALUE:
			return Integer.toString(value);
		case VARIABLE:
			return "x";
		case OPERATOR:
			switch (operator) {
			case ADD:
				return "+";
			case SUBTRACT:
				return "-";
			case DIVIDE:
				return "/";
			case MULTIPLY:
				return "*";
			}
		}
		return str;
		
	}
	
	public void print() {
		print("", true);
	}
	
	private void print(String prefix, boolean isTail) {
		System.out.println(prefix + (isTail ? "└── " : "├── ") + this.toString());
        
        if (this.left != null) {
        	this.left.print(prefix + (isTail ? "    " : "│   "), false);
        }
        if (this.right != null) {
        	this.right.print(prefix + (isTail ?"    " : "│   "), true);
        }
	}
}