import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.PatternSyntaxException;
import java.io.BufferedReader;

/**
 * This class takes care of reading a leaf- and tripleset from a text file.
 * 
 * @author Anica
 */

public class ReaderBUILD {

	/**
	 * Container for a buffered reader.
	 */
	private static BufferedReader bReader;
	
	/**
	 * Container for the leaf- and tripleset to be read.
	 */
	private static int numberOfLeaves;
	private static int numberOfTriples;
	private static ArrayList<Integer> leaves;
	private static ArrayList<Pair<Pair<Integer, Integer>, Integer>> triples;
	private static Pair<Integer, Integer> tripleLeftside;
	private static Pair<Pair<Integer, Integer>, Integer> triple;
	/**
	 * Container for printing.
	 */
	private static StringBuilder sbL = new StringBuilder();
	private static StringBuilder sbR = new StringBuilder();
	
	/**
	 * Produces an empty ReaderBUILD.
	 */
	public ReaderBUILD() {
		leaves = null;
		triples = null;
	}
	
	/**
	 * Reads a leaf- and tripleset from a text file with the following format:
	 * # of leaves
	 * # of triples
	 * in each line one number as name of a leaf, e.g. leaves '1', '5' and '78':
	 * 1
	 * 5
	 * 78
	 * in each line one triple ((x,y),z) as 'number of x space number of y space number of z', e.g.:
	 * 1 78 5
	 * 
	 * @param fileName name of the file to be read from.
	 * 
	 * @throws IOException if some problems with the text file occur.
	 */
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
		if (currentLine == null) {
			throw new IOException();
		}
		if (currentLine.matches("\\d+")) {
			numberOfLeaves = Integer.parseInt(currentLine);
		}else {
			throw new IOException();
		}
		if (numberOfLeaves == 0) {
			throw new IllegalArgumentException();
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
				leaves.add(Integer.valueOf(currentLine));
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
				if(!leaves.contains(Integer.valueOf(currentLineSplit[0])) || !leaves.contains(Integer.valueOf(currentLineSplit[1])) || !leaves.contains(Integer.valueOf(currentLineSplit[2]))){
					throw new IllegalArgumentException();
				}
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
		bReader.close();
	}
	
	/**
	 * Returns the leafset.
	 * 
	 * @return leafset.
	 */
	public static ArrayList<Integer> getLeafset() {
		return leaves;
	}
	
	/**
	 * Returns the tripleset.
	 * 
	 * @return tripleset.
	 */
	public static ArrayList<Pair<Pair<Integer, Integer>, Integer>> getTripleset() {
		return triples;
	}
	
	/**
	 *  Returns the leafset as string.
	 * 
	 * @return leafset as string.
	 */
	public static String getLeafsetPrint() {
		return sbL.toString();
	}
	
	/**
	 * Returns the tripleset as string.
	 * 
	 * @return tripleset as string
	 */
	public static String getTriplesetPrint() {
		return sbR.toString();
	}
}
