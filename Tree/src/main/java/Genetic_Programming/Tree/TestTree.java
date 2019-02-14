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
		
		t.DFS(t.root, 2);
		t.print();
		//System.out.println("Random Node is: " + t.randomNode().value.toString());
		
		Tree t2 = new Tree(3);
		
		t2.print();
		
		Tree cross = t.crossover(t2);
		cross.print();
		
		Tree cross2 = cross.crossover(t2);
		cross2.print();

	}

}
