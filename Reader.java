import java.io.FileReader;
import java.io.IOException;
import java.util.regex.PatternSyntaxException;
import java.io.BufferedReader;

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
			throw new PatternSyntaxException("Here should be 'd' for directed graph or 'u' for undirected graph.", currentLine, -1);
		}
		typeOfGraph = currentLine;
		currentLine = bReader.readLine();
		if (currentLine.matches("\\d")) {
			numberOfVertices = Integer.parseInt(currentLine);
		}else {
			throw new PatternSyntaxException("Here should be a digit for the number of vertices.", currentLine, -1);
		}
		currentLine = bReader.readLine();
		if (currentLine.matches("\\d")) {
			numberOfEdges = Integer.parseInt(currentLine);
		}else {
			throw new PatternSyntaxException("Here should be a digit for the number of edges.", currentLine, -1);
		}
		if (this.typeOfGraph().equals("d")) {
			graph = new DirectedGraph();
		}
		else if ( this.typeOfGraph().equals("u")) {
			graph = new UndirectedGraph();
		}
		/*
		 * fill graph
		 */
		for (int i = 0; i < numberOfVertices; i++) {
			currentLine = bReader.readLine();
			if (currentLine.matches("\\d")) {
				graph.addVertex(Integer.valueOf(currentLine));
			}else {
				throw new PatternSyntaxException("Here should be some digits for name of a vertex.", currentLine, -1);
			}
		}
		for (int i=0; i<numberOfEdges; i++) {
			currentLine = bReader.readLine();
			if (currentLine.matches("\\d*(\\s\\d*){1,2}")){
				currentLineSplit = currentLine.split("\\s");
				if (currentLineSplit.length == 2) {
					graph.addEdge(Integer.valueOf(currentLineSplit[0]), Integer.valueOf(currentLineSplit[1]));
				} 
				else if (currentLineSplit.length == 3) {
					graph.addEdge(Integer.valueOf(currentLineSplit[0]), Integer.valueOf(currentLineSplit[1]), Integer.valueOf(currentLineSplit[2]));
				} 
				
			}else {
				throw new PatternSyntaxException("Here should be 'some digits space some digits' for an edge with default weight 1 or 'some digits space some digits space some digits' for an edge with specified weight.", currentLine, -1);
			}
		}
		bReader.close();
		return graph;
	}
	
	public String typeOfGraph() {
		if (typeOfGraph.equals(null)) {
			throw new NullPointerException();
		}
		return typeOfGraph;
	}
}