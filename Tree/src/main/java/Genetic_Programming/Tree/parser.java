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
	
	// HashMap won't work because x-values are not unique.
	static HashMap<Double, Double> dataset1 = new HashMap<Double, Double>();
	static HashMap<ArrayList<Double>, Double> dataset2 = new HashMap<ArrayList<Double>, Double>();
	static HashMap<Double, Double> dataset3 = new HashMap<Double, Double>();

	/* 
	 * Java program to read CVS file using BufferedReader and String split() method 
	 */ 
	public static void csvParser() throws FileNotFoundException, IOException { 
		
		// Parser Dataset1
		BufferedReader br1 = new BufferedReader(new FileReader("dataset1.csv")); 
		String line1 = br1.readLine(); // Reading header, Ignoring 
		
		int i = 0;
		while ((line1 = br1.readLine()) != null && !line1.isEmpty()) { 
			String[] fields = line1.split(","); 
			Double x1 = Double.valueOf(fields[0]);
			Double y = Double.valueOf(fields[1]); 
			dataset1.put(x1, y);
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
			dataset2.put(data, y);
		} 
		
		br2.close(); 
		
		// Parser Dataset3
		BufferedReader br3 = new BufferedReader(new FileReader("dataset3.csv")); 
		String line3 = br3.readLine(); // Reading header, Ignoring 
		
		while ((line3 = br3.readLine()) != null && !line3.isEmpty()) { 
			String[] fields = line3.split(","); 
			Double x1 = Double.valueOf(fields[0]); 
			Double y = Double.valueOf(fields[1]); 
			dataset3.put(x1, y);
		} 
		
		br3.close(); 
	}
	
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		csvParser();
		System.out.println(dataset1.size());
		System.out.println(dataset2.size());
		System.out.println(dataset3.size());
		
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