package Genetic_Programming.Tree;
/**
 * @author yangzijiang
 *
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class parser{
	
	static HashMap<Double, Double> dataset1 = new HashMap<Double, Double>(); // training
	static HashMap<Double, Double> dataset1Test = new HashMap<Double, Double>(); // testing
	static HashMap<ArrayList<Double>, Double> dataset2 = new HashMap<ArrayList<Double>, Double>();
	static HashMap<ArrayList<Double>, Double> dataset2Test = new HashMap<ArrayList<Double>, Double>();
	// dataset2_rmv_outliers
	static HashMap<ArrayList<Double>, Double> dataset2b = new HashMap<ArrayList<Double>, Double>();
	static HashMap<ArrayList<Double>, Double> dataset2bTest = new HashMap<ArrayList<Double>, Double>();
	// dataset2 rmv_outliers & limited size by 90%
	static HashMap<ArrayList<Double>, Double> dataset2c = new HashMap<ArrayList<Double>, Double>();
	static HashMap<ArrayList<Double>, Double> dataset2cTest = new HashMap<ArrayList<Double>, Double>();
	static HashMap<Double, Double> dataset3 = new HashMap<Double, Double>();
	static HashMap<Double, Double> dataset3Test = new HashMap<Double, Double>();
	
	/* 
	 * Java program to read CVS file using BufferedReader and String split() method 
	 */ 
	public static void csvParser() throws FileNotFoundException, IOException { 
		
		// Parser Dataset1
		BufferedReader br1 = new BufferedReader(new FileReader("dataset1.csv")); 
		String line1 = br1.readLine(); // Reading header, Ignoring 
		
		while ((line1 = br1.readLine()) != null && !line1.isEmpty()) { 
			String[] fields = line1.split(","); 
			Double x1 = Double.valueOf(fields[0]);
			Double y = Double.valueOf(fields[1]);
			// Split dataset1 to training and testing by 80-20 rule
			if(dataset1.size() <= 800) {
				dataset1.put(x1, y);
			}
			else {
				dataset1Test.put(x1, y);
			}
		} 
		
		br1.close();
		
		// Parser Dataset2
		BufferedReader br2 = new BufferedReader(new FileReader("dataset2.csv")); 
		String line2 = br2.readLine(); // Reading header, Ignoring 
		
		while ((line2 = br2.readLine()) != null && !line2.isEmpty()) {
			String[] fields = line2.split(","); 
			ArrayList<Double> data = new ArrayList<Double>(3);
			
			Double x1 = Double.valueOf(fields[0]); 
			Double x2 = Double.valueOf(fields[1]); 
			Double x3 = Double.valueOf(fields[2]);	
			Double y = Double.valueOf(fields[3]);
			data.add(0, x1);
			data.add(1, x2);
			data.add(2, x3);
			// Split dataset2 to training and testing by 80-20 rule
			if(dataset2.size() <= 20000) {
				dataset2.put(data, y);
			}
			else {
				dataset2Test.put(data, y);
			}
		} 
		
		br2.close(); 
		
		// Parser Dataset2_rmv_outliers
		BufferedReader br2b = new BufferedReader(new FileReader("dataset2_rmv_outliers.csv")); 
		String line2b = br2b.readLine(); // Reading header, Ignoring 
		
		while ((line2b = br2b.readLine()) != null && !line2b.isEmpty()) {
			String[] fields = line2b.split(","); 
			ArrayList<Double> data = new ArrayList<Double>(3);
			
			Double x1 = Double.valueOf(fields[0]); 
			Double x2 = Double.valueOf(fields[1]); 
			Double x3 = Double.valueOf(fields[2]);	
			Double y = Double.valueOf(fields[3]);
			data.add(0, x1);
			data.add(1, x2);
			data.add(2, x3);
			// Split dataset2 to training and testing by 80-20 rule
			if(dataset2b.size() <= 20000) {
				dataset2b.put(data, y);
			}
			else {
				dataset2bTest.put(data, y);
			}
		} 
		
		br2b.close(); 
		
		// Parser Dataset2_rmv_outliers & reduced size by 90%
		BufferedReader br2c = new BufferedReader(new FileReader("dataset2_rmv_outliers.csv")); 
		String line2c = br2c.readLine(); // Reading header, Ignoring 
		
		while ((line2c = br2c.readLine()) != null && !line2c.isEmpty()) {
			String[] fields = line2c.split(","); 
			ArrayList<Double> data = new ArrayList<Double>(3);
			
			Double x1 = Double.valueOf(fields[0]); 
			Double x2 = Double.valueOf(fields[1]); 
			Double x3 = Double.valueOf(fields[2]);	
			Double y = Double.valueOf(fields[3]);
			data.add(0, x1);
			data.add(1, x2);
			data.add(2, x3);
			// Split dataset2 to training and testing by 80-20 rule
			if(dataset2c.size() <= 2000) {
				dataset2c.put(data, y);
			}
			else if(dataset2cTest.size() <= 2500){
				dataset2cTest.put(data, y);
			}
		} 
		
		br2c.close(); 
		
		// Parser Dataset3
		BufferedReader br3 = new BufferedReader(new FileReader("dataset3.csv")); 
		String line3 = br3.readLine(); // Reading header, Ignoring 
		
		while ((line3 = br3.readLine()) != null && !line3.isEmpty()) { 
			String[] fields = line3.split(","); 
			Double x1 = Double.valueOf(fields[0]); 
			Double y = Double.valueOf(fields[1]);
			// Split dataset3 to training and testing by 80-20 rule
			if(dataset3.size() <= 7294) {
				dataset3.put(x1, y);
			}
			else {
				dataset3Test.put(x1, y);
			}
		} 
		
		br3.close(); 
	}
	
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		csvParser();
		System.out.println(dataset1.size()); //full size: 1002; training: 800; testing: 202
		System.out.println(dataset2.size()); //full size: 25000; training: 20000; testing: 5000
		System.out.println(dataset3.size()); //full size: 9118; training: 7294; testing: 1824
		
		// ArrayList as key is found in databset2
		ArrayList<Double> data = new ArrayList<Double>(3);
		
		data.add(0, (double) 9609691);
		data.add(1, (double) 2256113);
		data.add(2, (double) 3.2);
		
		System.out.println(data);
		System.out.println(dataset2.get(data));
		System.out.println(dataset2.containsValue(141.56));
		System.out.println(dataset2.containsKey(data));
	}
}