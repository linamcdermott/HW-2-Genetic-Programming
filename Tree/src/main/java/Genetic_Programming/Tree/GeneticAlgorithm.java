package Genetic_Programming.Tree;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Random;

public class GeneticAlgorithm {

	private static Random random = new Random();
	static //static PriorityQueue<Tree> currentForest = new PriorityQueue<Tree>();
	ArrayList<Tree> currentPop = new ArrayList<Tree>();
	
	public static void generatePopulation() throws FileNotFoundException, IOException {
		for (int i = 0; i < 100; i++) {
			Tree current = new Tree(3);
			current.fitness = current.calculateFitness();
			//currentForest.add(current);
			currentPop.add(current);
		}
		//System.out.println("First Forest size should be 100: " + currentForest.size());
	}
	
	public static Tree runGeneticAlgorithm() throws FileNotFoundException, IOException {
		
		//ArrayList<PriorityQueue<Tree>> allForests = new ArrayList<PriorityQueue<Tree>>();
		
		generatePopulation();
		int numIterations = 0;
		boolean done = false;
		Tree bestTree = new Tree();
		
		while (numIterations < 5 && !done) {
			ArrayList<Tree> nextPop = new ArrayList<Tree>();
			Collections.sort(currentPop);
			//Check best tree of the current population and see if it is the best ever
			if (currentPop.get(0).fitness < bestTree.fitness) {
				bestTree = currentPop.get(0);
			}
			//Make next population
			while (nextPop.size() < 100) {
				if (nextPop.size() == 100) {
					break;
				}
				
				//TODO: implement way to get random tree (random weighted selection)
				
				//50% chance of crossover, 30% pass on no mutation, 20% pass on w/ mutation
				Tree x = new Tree(); //This will be changed to a random tree
				double probability = Math.random();
				if (probability <= 0.5) {
					Tree y = new Tree(); //This will be changed to a random tree
					Tree crossover = x.crossover(y);
					crossover.fitness = crossover.calculateFitness();
					nextPop.add(crossover);
				}
				if (probability > 0.5 && probability <= 0.8) {
					nextPop.add(x);
				}
				else {
					Tree m = x.mutate();
					m.fitness = m.calculateFitness();
					nextPop.add(m);
				}
			}
			/**double survivalChance = 0.99;
			int reproductionSize = 0;
			ArrayList<Tree> mostFitted = new ArrayList<Tree>();
			for(int reproduction = 0; reproduction < currentForest.size(); reproduction++) {
				if(reproductionSize > 0.2 * currentForest.size()) {
					break;
				}
				// Change this later
				if(random.nextDouble() <= Math.pow(survivalChance, reproduction)) {
					Tree picked = currentForest.poll();
					nextForest.add(picked); 
					mostFitted.add(picked);
					reproductionSize++;
				}
			}
			
			int numChildren = 0;
			int numMutations = 0;
			while (nextForest.size() < 100) {
				if (nextForest.size() == 100) {
					break;
				}
				x = mostFitted.get(random.nextInt(mostFitted.size()));
				y = mostFitted.get(random.nextInt(mostFitted.size()));
				Tree child = x.crossover(y);
				child.fitness = child.calculateFitness();
				nextForest.add(child);
				numChildren++;
				if (random.nextDouble() < 0.1) {
					Tree mutated = child.mutate();
					mutated.fitness = mutated.calculateFitness();
					nextForest.add(mutated);
					numMutations++;
				}
			}**/
			//System.out.println("Next Forest size should be 100: " + nextForest.size());
			//System.out.println("Num children: " + numChildren + "; Num Mutations: " + numMutations);
			numIterations++;
			currentPop = nextPop;
		}
		return null;
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		//runGeneticAlgorithm();
	}
}
