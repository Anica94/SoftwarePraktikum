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

	private BufferedReader bReader;
	private int numberOfLeaves;
	private int numberOfTriples;
	private ArrayList<Integer> leaves;
	private ArrayList<Pair<Pair<Integer, Integer>, Integer>> triples;
	private Pair<Integer, Integer> tripleLeftside;
	private Pair<Pair<Integer, Integer>, Integer> triple;
	
	public ReaderBUILD() {
		// TODO Auto-generated constructor stub
	}
	
	public void read(String fileName) throws IOException {
		/*
		 * get necessary variables
		 */
		String[] currentLineSplit;
		bReader = new BufferedReader(new FileReader(fileName));
		String currentLine = bReader.readLine();
		leaves = new ArrayList<>();
		triples = new ArrayList<>();
		if (currentLine.matches("\\d")) {
			numberOfLeaves = Integer.parseInt(currentLine);
		}else {
			throw new PatternSyntaxException("Here should be a digit for the number of leaves.", currentLine, -1);
		}
		currentLine = bReader.readLine();
		if (currentLine.matches("\\d")) {
			numberOfTriples = Integer.parseInt(currentLine);
		}else {
			throw new PatternSyntaxException("Here should be a digit for the number of triples.", currentLine, -1);
		}
		/*
		 * fill leaves
		 */
		for (int i = 0; i < numberOfLeaves; i++) {
			currentLine = bReader.readLine();
			if (currentLine.matches("\\d")) {
				leaves.add(Integer.parseInt(currentLine));
			}else {
				throw new PatternSyntaxException("Here should be some digits for name of a leaf.", currentLine, -1);
			}
		}
		/*
		 * fill triples
		 */
		for (int i=0; i<numberOfTriples; i++) {
			currentLine = bReader.readLine();
			if (currentLine.matches("\\d*\\s\\d*\\s\\d*")){
				currentLineSplit = currentLine.split("\\s");
				tripleLeftside = new Pair<Integer, Integer>(Integer.valueOf(currentLineSplit[0]), Integer.valueOf(currentLineSplit[1]));
				triple = new Pair<Pair<Integer,Integer>, Integer>(tripleLeftside, Integer.valueOf(currentLineSplit[2]));
				triples.add(triple);
			}else {
				throw new PatternSyntaxException("Here should be 'some digits space some digits space some digits'.", currentLine, -1);
			}
		}
		bReader.close();
	}
	
	/**
	 * Returns the leafset.
	 * 
	 * @return leafset
	 */
	public ArrayList<Integer> getLeafset() {
		return leaves;
	}
	
	/**
	 * Returns the tripleset.
	 * 
	 * @return tripleset
	 */
	public ArrayList<Pair<Pair<Integer, Integer>, Integer>> getTripleset() {
		return triples;
	}
}
