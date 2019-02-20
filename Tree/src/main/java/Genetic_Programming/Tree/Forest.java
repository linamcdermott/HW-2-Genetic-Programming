package Genetic_Programming.Tree;

import java.util.*;

public class Forest {
	    private Tree tree;
	   	private double fitness;

	    public Forest(Tree tree, double fitness){
	       this.tree = tree;
	       this.fitness = fitness;
	    }
	    
	    public static void main(String[] args) {
		   	// Data structure to store current population and all trees ever existed
		   	ArrayList<Tree> currentForest = new ArrayList<Tree>(100);
			ArrayList<ArrayList<Tree>> allForests = new ArrayList<ArrayList<Tree>>();
			//	    list.sort(Comparator.comparingDouble(Forest::getRating));
		   // getters & setters
	    }
}
