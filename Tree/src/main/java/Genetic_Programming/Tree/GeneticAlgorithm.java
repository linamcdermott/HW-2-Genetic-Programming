package Genetic_Programming.Tree;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Random;

public class GeneticAlgorithm {

	private static Random random = new Random();
	static PriorityQueue<Tree> currentForest = new PriorityQueue<Tree>();
	
	public static void generatePopulation() throws FileNotFoundException, IOException {
		for (int i = 0; i < 100; i++) {
			Tree current = new Tree(3);
			current.fitness = current.calculateFitness();
			currentForest.add(current);
		}
		System.out.println("First Forest size should be 100: " + currentForest.size());
	}
	
	public static Tree runGeneticAlgorithm() throws FileNotFoundException, IOException {
		
		//ArrayList<PriorityQueue<Tree>> allForests = new ArrayList<PriorityQueue<Tree>>();
		
		generatePopulation();
		int numIterations = 0;
		boolean done = false;
		
		while (numIterations < 5 && !done) {
			PriorityQueue<Tree> nextForest = new PriorityQueue<Tree>();
			Tree x;
			Tree y;
			//need to calculate the generation's tree fitness
//			for (int i = 0; i < currentForest.size(); i++) {
//				Tree current = population.get(i);
//				current.fitness = current.calculateFitness();
//				currentForest.add(current);
//			}
			
			double survivalChance = 0.99;
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
			}
			//System.out.println("Next Forest size should be 100: " + nextForest.size());
			System.out.println("Num children: " + numChildren + "; Num Mutations: " + numMutations);
			numIterations++;
			currentForest = nextForest;
		}
		Tree best = currentForest.poll();
		best.print();
		best.printExpression(best.root);
		System.out.println();
		System.out.println(best.fitness);
		return null;
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		runGeneticAlgorithm();
	}
}
