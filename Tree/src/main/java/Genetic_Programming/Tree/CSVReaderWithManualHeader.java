package Genetic_Programming.Tree;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CSVReaderWithManualHeader {
    private static final String SAMPLE_CSV_FILE_PATH = "./dataset1.csv";

    public static void main(String[] args) throws IOException {
        try (
            Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                    .withHeader("x", "f(x)")
                    .withIgnoreHeaderCase()
                    .withTrim());
    		
    		/* Option 2:  Without header
    		CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
            for (CSVRecord csvRecord : csvParser) {
                // Accessing Values by Column Index
                String name = csvRecord.get(0);
                String email = csvRecord.get(1);
            */
        ) {
            for (CSVRecord csvRecord : csvParser) {
                // Accessing values by the names assigned to each column
            	String a = csvRecord.get("x");
            	String b = csvRecord.get("f(x)");
            	
				// Need to convert it to double from string?
				// Or not, as we are only printing them out here
				//		double x = Double.valueOf(a);
				// 		double f = Double.valueOf(b);
		        //      double x = Double.parseDouble(a);
				//      double f = Double.parseDouble(b);
                
                System.out.println("Record No - " + csvRecord.getRecordNumber());
                System.out.println("x : " + a);
                System.out.println("f(x) : " + b);
                System.out.println("\n");
            }
        }
    }
}