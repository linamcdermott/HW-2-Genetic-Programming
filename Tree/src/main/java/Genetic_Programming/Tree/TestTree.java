package Genetic_Programming.Tree;

public class TestTree {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Tree t = TreeMaker.makeFullTree(3);
		t.evaluateTree(t.root);
		System.out.println(t.expression);
	}

}
