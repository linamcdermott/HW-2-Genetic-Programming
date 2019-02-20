package Genetic_Programming.Tree;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.*;

public class TestTree {

	public static void main(String[] args) throws FileNotFoundException, IOException {
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
		Tree mut3 = cross2.mutate();
		mut3.print();
		
		System.out.println(cross2.calculateFitness());
		
		
		HashMap<Tree, Double> scoredForest = new HashMap<Tree, Double>(100);
		
		// Initialize seed population
		for(int i = 0; i < 100; i++) {
			Tree tree = new Tree(3);
			Double treeFitness = tree.calculateFitness();
			scoredForest.put(tree, treeFitness);
		}
		
		//Map<Tree, Double> scoredForest1 = sortByValue(scoredForest); 
		
	}

}
