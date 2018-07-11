import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.PatternSyntaxException;
import java.io.BufferedReader;

/**
 * 
 * @author Anica
 *
 */

public class ReaderBUILD {

	private static BufferedReader bReader;
	private static int numberOfLeaves;
	private static int numberOfTriples;
	private static ArrayList<Integer> leaves;
	private static ArrayList<Pair<Pair<Integer, Integer>, Integer>> triples;
	private static Pair<Integer, Integer> tripleLeftside;
	private static Pair<Pair<Integer, Integer>, Integer> triple;
	private static StringBuilder sbL = new StringBuilder();
	private static StringBuilder sbR = new StringBuilder();
	
	public ReaderBUILD() {
		leaves = null;
		triples = null;
	}
	
	public static void read(String fileName) throws IOException {
		/*
		 * get necessary variables
		 */
		String[] currentLineSplit;
		bReader = new BufferedReader(new FileReader(fileName));
		String currentLine = bReader.readLine();
		leaves = new ArrayList<>();
		triples = new ArrayList<>();
		sbL = new StringBuilder();
		sbR = new StringBuilder();
		if (currentLine.matches("\\d+")) {
			numberOfLeaves = Integer.parseInt(currentLine);
		}else {
			throw new IOException();
		}
		currentLine = bReader.readLine();
		if (currentLine.matches("\\d+")) {
			numberOfTriples = Integer.parseInt(currentLine);
		}else {
			throw new IOException();
		}
		/*
		 * fill leaves
		 */
		sbL.append("L = {");
		for (int i = 0; i < numberOfLeaves; i++) {
			currentLine = bReader.readLine();
			if (currentLine.matches("\\d+")) {
				leaves.add(Integer.parseInt(currentLine));
				sbL.append(currentLine);
				if(i < numberOfLeaves-1) {
					sbL.append(", ");
				}					
			}else {
				throw new IOException();
			}
		}
		sbL.append("}");
		System.out.println(sbL);
		/*
		 * fill triples
		 */
		sbR.append("R = {");
		for (int i=0; i<numberOfTriples; i++) {
			currentLine = bReader.readLine();
			if (currentLine.matches("\\d+\\s\\d+\\s\\d+")){
				currentLineSplit = currentLine.split("\\s");
				tripleLeftside = new Pair<Integer, Integer>(Integer.valueOf(currentLineSplit[0]), Integer.valueOf(currentLineSplit[1]));
				triple = new Pair<Pair<Integer,Integer>, Integer>(tripleLeftside, Integer.valueOf(currentLineSplit[2]));
				triples.add(triple);
				sbR.append("((");
				sbR.append(currentLineSplit[0]);
				sbR.append(",");
				sbR.append(currentLineSplit[1]);
				sbR.append("),");
				sbR.append(currentLineSplit[2]);
				sbR.append(")");
				if(i < numberOfTriples-1) {
					sbR.append(", ");
				}
				
			}else {
				throw new IOException();
			}
		}
		sbR.append("}");
		System.out.println(sbR);
		System.out.println("number of leaves= " + leaves.size());
		bReader.close();
	}
	
	/**
	 * Returns the leafset.
	 * 
	 * @return leafset
	 */
	public static ArrayList<Integer> getLeafset() {
		System.out.println("huhu");
		return leaves;
	}
	
	/**
	 * Returns the tripleset.
	 * 
	 * @return tripleset
	 */
	public static ArrayList<Pair<Pair<Integer, Integer>, Integer>> getTripleset() {
		return triples;
	}
	
	/**
	 * 
	 * @return leafset as String
	 */
	public static String getLeafsetPrint() {
		return sbL.toString();
	}
	
	/**
	 * 
	 * @return tripleset as String
	 */
	public static String getTriplesetPrint() {
		return sbR.toString();
	}
}
