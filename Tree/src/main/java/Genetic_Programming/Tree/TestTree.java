package Genetic_Programming.Tree;

public class TestTree {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Tree t = new Tree(3);
		t.printExpression(t.root);
		System.out.println("");
		Tree clone = t.clone();
		System.out.println("Printing clone: ");
		clone.printExpression(clone.root);
		System.out.println("");
		System.out.println(t.evaluateTree(3, t.root));
		
		t.BFS(t.root, 2);
		
		t.print();
	}

}
