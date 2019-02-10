package Genetic_Programming.Tree;

import java.util.Random;

public class TreeMaker {
	
	static Random random = new Random();
    private static final int MAX_CONSTANT = 100;
    private static Operator[] operators = {Operator.ADD, Operator.SUBTRACT, Operator.DIVIDE, Operator.MULTIPLY};
    
	public static Tree makeFullTree(int depth) {
		if (depth > 1) {
			Operator op = operators[random.nextInt(operators.length)];
			return new Tree(new Node(op), makeFullTree(depth -1), makeFullTree(depth-1));
		} else {
			double probability = Math.random();
			if (probability > 0.5) {
				return new Tree(new Node(random.nextInt(MAX_CONSTANT)));
			} else {
				return new Tree(new Node('x'));
			}
		}
	}
	
	public static Tree makeRandTree(int depth) {
		if (depth > 1 && random.nextBoolean()) {
			Operator op = operators[random.nextInt(operators.length)];
			return new Tree(new Node(op), makeFullTree(depth -1), makeFullTree(depth-1));
		} else {
			double probability = Math.random();
			if (probability > 0.5) {
				return new Tree(new Node(random.nextInt(MAX_CONSTANT)));
			} else {
				return new Tree(new Node('x'));
			}
		}
	}
}
