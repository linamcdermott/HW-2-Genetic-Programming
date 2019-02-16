package Genetic_Programming.Tree;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Random;

public class GeneticAlgorithm {

	private static Random random = new Random();
	//static PriorityQueue<Tree> currentForest = new PriorityQueue<Tree>();
	static ArrayList<Tree> currentPop = new ArrayList<Tree>();
	
	public static void generatePopulation() throws FileNotFoundException, IOException {
		for (int i = 0; i < 100; i++) {
			Tree current = new Tree(3);
			current.fitness = current.calculateFitness();
			//currentForest.add(current);
			currentPop.add(current);
		}
		//System.out.println("First Forest size should be 100: " + currentForest.size());
	}
	
	// Make a sum list for picking trees based on fitness
	private static ArrayList<Double> makeSumList(ArrayList<Tree> currentPop) {
		ArrayList<Double> sumList = new ArrayList<Double>();
		
		sumList.add(0, currentPop.get(0).fitness);
		for (int i = 1; i < currentPop.size(); i++) {
			Double currentElement = sumList.get(i - 1) + currentPop.get(i).fitness;
			sumList.add(i, currentElement);
		}	
		return sumList;
	}
	
	public static Tree getRandomTree() {
		ArrayList<Double> sums = makeSumList(currentPop);
		double min = sums.get(0);
		double max = sums.get(99);
		double randDouble = random.nextDouble() * (max - min);
		int index = 0;
		for (int i = 0; i < sums.size(); i++) {
			if (sums.get(i) >= randDouble) {
				index = i;
				break;
			}
		}
		return currentPop.get(currentPop.size()-index-1);
	}
	
	// Pick trees based on complexity
	private static ArrayList<Tree> complexityFilter(ArrayList<Tree> thisPop){
		ArrayList<Integer> complexities = new ArrayList<Integer>();
		
		for(int i = 0; i < thisPop.size(); i++) {
			complexities.add(thisPop.get(i).getComplexity());
		}
		Collections.sort(complexities);
		
		double median = (double) complexities.get(complexities.size()/2);
		
		System.out.print("median is: " + median + "\n");
		ArrayList<Tree> nextPop = new ArrayList<Tree>();
		
		for(Tree currentTree : thisPop) {
			if(currentTree.complexity <= 1.5 * median) {
				nextPop.add(currentTree);
				if(nextPop.size() >= 100) {
					break;
				}
			}
		}
		System.out.print("Size of nextPop shoulde be 100: " + nextPop.size() + "\n");
		
		return nextPop;
	}
	
	public static Tree runGeneticAlgorithm() throws FileNotFoundException, IOException {
		
		//ArrayList<PriorityQueue<Tree>> allForests = new ArrayList<PriorityQueue<Tree>>();
		
		generatePopulation();
		int numIterations = 0;
		boolean done = false;
		Tree bestTree = new Tree();
		
		while (bestTree.fitness > 1) {
			ArrayList<Tree> thisPop = new ArrayList<Tree>();
			Collections.sort(currentPop);
			//Check best tree of the current population and see if it is the best ever
			if (currentPop.get(0).fitness < bestTree.fitness) {
				bestTree = currentPop.get(0);
				bestTree.print();
				System.out.println();
				System.out.println(bestTree.fitness);
			}
			//Make next population
			while (thisPop.size() < 201) {
				if (thisPop.size() == 201) {
					break;
				}
				
				//TODO: implement way to get random tree (random weighted selection)
				
				//50% chance of crossover, 30% pass on no mutation, 20% pass on w/ mutation
				Tree x = getRandomTree(); //This will be changed to a random tree
				double probability = Math.random();
				if (probability <= 0.5) {
					Tree y = getRandomTree(); //This will be changed to a random tree
					Tree crossover = x.crossover(y);
					crossover.fitness = crossover.calculateFitness();
					thisPop.add(x);
					thisPop.add(crossover);
				}
				if (probability > 0.5 && probability <= 0.8) {
					thisPop.add(x);
				}
				else {
					Tree m = x.mutate();
					m.fitness = m.calculateFitness();
					thisPop.add(m);
				}
			}
			
			ArrayList<Tree> nextPop = complexityFilter(thisPop);
			
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
			
			bestTree.print();
			System.out.println();
			bestTree.printExpression(bestTree.root);
			System.out.println();
			System.out.println(bestTree.fitness);
		}
		bestTree.print();
		System.out.println();
		bestTree.printExpression(bestTree.root);
		System.out.println();
		System.out.println(bestTree.fitness);
		return bestTree;
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		runGeneticAlgorithm();
	}
}
