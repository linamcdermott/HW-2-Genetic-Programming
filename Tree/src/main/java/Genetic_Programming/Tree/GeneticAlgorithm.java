package Genetic_Programming.Tree;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GeneticAlgorithm {

	private static Random random = new Random();
	static ArrayList<Tree> currentPop = new ArrayList<Tree>();
	
	public static void generatePopulation() throws FileNotFoundException, IOException {
		for (int i = 0; i < 100; i++) {
			Tree current = new Tree(3);
			current.fitness = current.calculateFitness2(); // ****************** choose the dataset ***************************
			//System.out.println(current.fitness);
			currentPop.add(current);
		}
	}
	
	// Make a sum list for picking trees based on fitness
	private static ArrayList<Double> makeSumList(ArrayList<Tree> currentPop) {
		ArrayList<Double> sumList = new ArrayList<Double>();
		int popSize = currentPop.size();
		
		sumList.add(0, currentPop.get(popSize - 1).fitness);
		for (int i = popSize - 2; i >= 0; i--) {
			Double currentElement = sumList.get(popSize - i - 2) + currentPop.get(i).fitness;
			sumList.add(popSize - i - 1, currentElement);
		}	
		
		return sumList;
	}
	
	public static Tree getRandomTree() {
		ArrayList<Double> sums = makeSumList(currentPop);
		double min = sums.get(0);
		double max = sums.get(99);
		double randDouble = random.nextDouble() * (max - min);
		int index = 0;
		for (int i = 1; i < sums.size(); i++) {
			if (sums.get(i) >= randDouble) {
				index = i - 1;
				break;
			}
		}
		return currentPop.get(index);
	}
	
	// Pick trees based on complexity
	private static ArrayList<Tree> complexityFilter(ArrayList<Tree> thisPop){
		ArrayList<Integer> complexities = new ArrayList<Integer>();
		
		for(int i = 0; i < thisPop.size(); i++) {
			complexities.add(thisPop.get(i).getComplexity());
		}
		Collections.sort(complexities);
		
		double median = (double) complexities.get(complexities.size()/2);
		
		//System.out.print("median is: " + median + "\n");
		ArrayList<Tree> nextPop = new ArrayList<Tree>();
		
		for(Tree currentTree : thisPop) {
			if(currentTree.complexity <= 1.5 * median) {
				nextPop.add(currentTree);
				if(nextPop.size() >= 100) {
					break;
				}
			}
		}
		//System.out.print("Size of nextPop should be 100: " + nextPop.size() + "\n");
		
		return nextPop;
	}
	
	public static Tree runGeneticAlgorithm() throws FileNotFoundException, IOException {
				
		generatePopulation();
		int numIterations = 0;
		Tree bestTree = new Tree();
		
		while (numIterations < 400) { //bestTree.fitness > 0.05
			ArrayList<Tree> thisPop = new ArrayList<Tree>();
			Collections.sort(currentPop);
			//Check best tree of the current population and see if it is the best ever
			if (currentPop.get(0).fitness < bestTree.fitness) {
				bestTree = currentPop.get(0);
				//bestTree.print();
				//System.out.println();
				//System.out.println(bestTree.fitness);
			}
			//Make next population
			while (thisPop.size() < 201) {		
				// Dataset 1: 80% crossover (mutate 20% of the children), 10% pass on no mutation, 10% pass on w/ mutation
				// Dataset 2: 80% crossover (mutate 50% of the children), 5% pass on no mutation, 15% pass on w/ mutation only
				Tree x = getRandomTree(); 
				
				double probability = Math.random();
				if (probability <= 0.8) {
					Tree y = getRandomTree(); 
					Tree crossover = x.crossover(y);
					if (Math.random() < 0.5) {
						crossover = crossover.mutate();
					}
					crossover.fitness = crossover.calculateFitness2(); // *********************************** choose the dataset **********
					thisPop.add(crossover);
				}
				if (probability > 0.8 && probability <= 0.85) {
					thisPop.add(x);
				}
				else {
					Tree m = x.mutate();
					m.fitness = m.calculateFitness2(); // ****************************************** choose the dataset **********
					thisPop.add(m);
				}
			}
			
			ArrayList<Tree> nextPop = complexityFilter(thisPop);
			
			numIterations++;
			currentPop = nextPop;
			
			//bestTree.print();
//			System.out.println();
//			bestTree.printExpression(bestTree.root);
//			System.out.println();
			if(numIterations == 1 || numIterations == 100 || numIterations == 200 || numIterations == 300) {
				bestTree.print();
			}
			System.out.println(numIterations + "th generation fitness : " + bestTree.fitness);
		}
		bestTree.print();
		System.out.println();
		bestTree.printExpression(bestTree.root);
		System.out.println();
		System.out.println(bestTree.fitness);
		System.out.println(numIterations);
		return bestTree;
	}
	
	public static double testGeneticAlgorithm() throws FileNotFoundException, IOException{
		Tree bestTree = runGeneticAlgorithm();
		System.out.println("Testing Genetic Algorithm's fitness: " + bestTree.testFitness2()); // ************* choose the dataset **********
		return bestTree.testFitness2(); 					// ************************************** choose the dataset **********
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		testGeneticAlgorithm();
	}
}
