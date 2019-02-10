package Genetic_Programming.Tree;

public class Node {
	
	Node left;
	Node right;
	
	private NodeType nodeType;
	private int value;
	private Operator operator;
	
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
}