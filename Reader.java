import java.io.FileReader;
import java.io.IOException;
import java.util.regex.PatternSyntaxException;
import java.io.BufferedReader;

/**
 * This class takes care of reading a graph from a text file.
 * 
 * @author Sonja
 */
public class Reader {
	
	/**
	 * Container for a buffered reader.
	 */
	private BufferedReader bReader;
	/**
	 * Container for the graph to be read.
	 */
	private Graph graph;
	private int numberOfEdges;
	private int numberOfVertices;
	
	/**
	 * Produces a new empty reader.
	 */
	Reader(){
		
	}

	/**
	 * Reads a graph from a text file with the following format:
	 * 'd' or 'u' for directed or undirected graph
	 * # of vertices
	 * # of edges
	 * in each line one number as name of a vertex, e.g. vertices '1', '5' and '78':
	 * 1
	 * 5
	 * 78
	 * in each line one edge as either 'startvertexname space endvertexname' or 'startvertexname space endvertexname space edge weight', e.g.:
	 * 1 5 6
	 * 78 1
	 * 
	 * @param fileName name of the file to be read from.
	 * 
	 * @return graph as specified in the text file.
	 * 
	 * @throws IOException if some problems with the text file occur.
	 */
	public Graph read(String fileName) throws IOException {
		
		/*
		 * get necessary variables
		 */
		String[] currentLineSplit;
		bReader = new BufferedReader(new FileReader(fileName));
		String currentLine = bReader.readLine();
		if (currentLine == null) {
			throw new IOException();
		}
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
		 * 	add vertices
		 * 	add edges
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
					try {
						graph.addEdge(Integer.valueOf(currentLineSplit[0]), Integer.valueOf(currentLineSplit[1]));
					} catch (IllegalArgumentException e) {
						throw new IOException();
					}
				} 
				else if (currentLineSplit.length == 3) {
					try {
						graph.addEdge(Integer.valueOf(currentLineSplit[0]), Integer.valueOf(currentLineSplit[1]), Integer.valueOf(currentLineSplit[2]));
					} catch (IllegalArgumentException e) {
						throw new IOException();
					}
				} 
				
			}else {
				throw new IOException();
			}
		}
		bReader.close();
		return graph;
	}
	
}
