import java.io.FileReader;
import java.io.IOException;
import java.util.regex.PatternSyntaxException;
import java.io.BufferedReader;
/**
 * This class takes care of reading a graph from a text file.
 * 
 * @author Sonja
 *
 */
public class Reader {
	
	private BufferedReader bReader;
	private Graph graph;
	private int numberOfEdges;
	private int numberOfVertices;
	private String typeOfGraph;
	
	Reader(){
		
	}

	public Graph read(String fileName) throws IOException {
		
		/*
		 * get necessary variables
		 */
		String[] currentLineSplit;
		bReader = new BufferedReader(new FileReader(fileName));
		String currentLine = bReader.readLine();
		if (!currentLine.matches("[du]")) {
			throw new IOException();
		}
		if (currentLine.equals("d")) {
			graph = new DirectedGraph();
		}
		else if (currentLine.equals("u")) {
			graph = new UndirectedGraph();
		}
		currentLine = bReader.readLine();
		if (currentLine.matches("\\d+")) {
			numberOfVertices = Integer.parseInt(currentLine);
		}else {
			throw new IOException();
		}
		currentLine = bReader.readLine();
		if (currentLine.matches("\\d+")) {
			numberOfEdges = Integer.parseInt(currentLine);
		}else {
			throw new IOException();
		}
		
		/*
		 * fill graph
		 */
		for (int i = 0; i < numberOfVertices; i++) {
			currentLine = bReader.readLine();
			if (currentLine.matches("\\d+")) {
				graph.addVertex(Integer.valueOf(currentLine));
			}else {
				throw new IOException();
			}
		}
		for (int i=0; i<numberOfEdges; i++) {
			currentLine = bReader.readLine();
			if (currentLine.matches("\\d+(\\s\\d+){1,2}")){
				currentLineSplit = currentLine.split("\\s");
				if (currentLineSplit.length == 2) {
					graph.addEdge(Integer.valueOf(currentLineSplit[0]), Integer.valueOf(currentLineSplit[1]));
				} 
				else if (currentLineSplit.length == 3) {
					graph.addEdge(Integer.valueOf(currentLineSplit[0]), Integer.valueOf(currentLineSplit[1]), Integer.valueOf(currentLineSplit[2]));
				} 
				
			}else {
				throw new IOException();
			}
		}
		bReader.close();
		return graph;
	}
	
}
