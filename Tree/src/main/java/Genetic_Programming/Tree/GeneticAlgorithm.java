package Genetic_Programming.Tree;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Random;

public class GeneticAlgorithm {

	private static Random random = new Random();
	static PriorityQueue<Tree> scoredForest = new PriorityQueue<Tree>();
	
	public static void generatePopulation() throws FileNotFoundException, IOException {
		for (int i = 0; i < 100; i++) {
			int depth = random.nextInt(5);
			Tree current = new Tree(depth);
			current.fitness = current.calculateFitness();
			scoredForest.add(current);
		}
	}
	
	public static Tree runGeneticAlgorithm() throws FileNotFoundException, IOException {
		
		ArrayList<PriorityQueue<Tree>> allForests = new ArrayList<PriorityQueue<Tree>>();
		
		generatePopulation();
		int numIterations = 0;
		boolean done = false;
		
		while (numIterations < 100 || !done) {
			PriorityQueue<Tree> nextForest = new PriorityQueue<Tree>();
			Tree currentFittest;
			Tree x;
			Tree y;
			//need to calculate the generation's tree fitness
//			for (int i = 0; i < scoredForest.size(); i++) {
//				Tree current = population.get(i);
//				current.fitness = current.calculateFitness();
//				scoredForest.add(current);
//			}
			
			double survivalChance = 0.95;
			int reproductionSize = 0;
			for(int reproduction = 0; reproduction < scoredForest.size(); reproduction++) {
				if(reproductionSize > 0.2 * scoredForest.size()) {
					break;
				}
				if(random.nextDouble() <= Math.pow(survivalChance, reproduction)) {
					nextForest.add(scoredForest.poll()); 
					reproductionSize++;
				}
			}
			System.out.println("Next Forest size should be 20: " + nextForest.size());
		}
		return null;
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		runGeneticAlgorithm();
	}
}
