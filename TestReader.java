import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

import org.junit.Test;
import junit.framework.TestCase;

/**
 * Test for the class Reader.
 * 
 * @author Sonja
 */
public class TestReader extends TestCase{

	/**
	 * Class to be tested.
	 */
	private Reader reader = new Reader();
	/**
	 * Containers for the graph to be tested.
	 */
	private Graph graph;
	private ArrayList<Integer> vertices;
	private TreeMap<Integer, Integer> currentEdge;
	private Integer currentEndVertex;
	/**
	 * Containers for testing.
	 */
	private UndirectedGraph expectedUndirectedGraph = new UndirectedGraph();
	private DirectedGraph expectedDirectedGraph = new DirectedGraph();
	private boolean success;
	

	/**
	 * Produce the expected graphs.
	 */
	private void produceExpected() {
		/*
		 * produce expected undirected graph
		 */
		expectedUndirectedGraph.addVertex(new Integer(1));
		expectedUndirectedGraph.addVertex(new Integer(2));
		expectedUndirectedGraph.addVertex(new Integer(3));
		expectedUndirectedGraph.addEdge(new Integer(1), new Integer(2));
		expectedUndirectedGraph.addEdge(new Integer(2), new Integer(3), new Integer(5));
		
		/*
		 * produce expected directed graph
		 */
		expectedDirectedGraph.addVertex(new Integer(1));
		expectedDirectedGraph.addVertex(new Integer(2));
		expectedDirectedGraph.addVertex(new Integer(3));
		expectedDirectedGraph.addEdge(new Integer(1), new Integer(2));
		expectedDirectedGraph.addEdge(new Integer(3), new Integer(2), new Integer(5));
	}
	
	@Test
	public void testRead() {
		/*
		 * test empty file
		 */	
		try {
			graph = reader.read("C:\\Users\\Sonja\\eclipse-workspace\\SoftwarePraktikum\\src\\Textfiles\\Test\\RemptyFile.txt");
			fail("expected IOException");
		} catch (IOException e) {
			// ignore, this exception is expected
		}
		/*
		 * test empty graph
		 */
		try {
			graph = reader.read("C:\\Users\\Sonja\\eclipse-workspace\\SoftwarePraktikum\\src\\Textfiles\\Test\\RemptyGraph.txt");
		} catch (IOException e) {
			assertFalse("reader.read(textfile) throws an exception instead of creating an empty graph.", true);
		}
		assertTrue("reader.read(textfile) creates no empty graph although this was stated in the textfile.", graph.isEmpty());
		/*
		 * test graph without edges
		 */
		try {
			graph = reader.read("C:\\Users\\Sonja\\eclipse-workspace\\SoftwarePraktikum\\src\\Textfiles\\Test\\RnoEdges.txt");
		} catch (IOException e) {
			assertFalse("reader.read(textfile) throws an exception instead of creating a graph without edges.", true);
		}
		assertFalse("reader.read(textfile) creates edges although none were stated in the textfile.", graph.containsEdges());
		/*
		 * test wrong format
		 * 	Rwrong.txt: contains blank line
		 * 	Rwrong2.txt: random text
		 * 	Rwrong3.txt: edge to not existing endpoint
		 */
		try {
			graph = reader.read("C:\\Users\\Sonja\\eclipse-workspace\\SoftwarePraktikum\\src\\Textfiles\\Test\\Rwrong.txt");
			fail("expected IOException");
		} catch (IOException e) {
			// ignore, this exception is expected
		}
		try {
			graph = reader.read("C:\\Users\\Sonja\\eclipse-workspace\\SoftwarePraktikum\\src\\Textfiles\\Test\\Rwrong2.txt");
			fail("expected IOException");
		} catch (IOException e) {
			// ignore, this exception is expected
		}try {
			graph = reader.read("C:\\Users\\Sonja\\eclipse-workspace\\SoftwarePraktikum\\src\\Textfiles\\Test\\Rwrong3.txt");
			fail("expected IOException");
		} catch (IOException e) {
			// ignore, this exception is expected
		}
		this.produceExpected();
		/*
		 * test directed graph
		 */
		try {
			graph = reader.read("C:\\Users\\Sonja\\eclipse-workspace\\SoftwarePraktikum\\src\\Textfiles\\Test\\RDirected.txt");
		} catch (IOException e) {
			assertFalse("reader.read(textfile) throws an exception instead of creating a graph without edges.", true);
		}
		success = graph.typeOfGraph().equals(expectedDirectedGraph.typeOfGraph());
		assertTrue("reader.read(textfile) decides for the wrong type of graph.", success);
		/*
		 * 	check vertices
		 */
		vertices = graph.getVertices();
		success = (vertices.size() == expectedDirectedGraph.getVertices().size());
		assertTrue("reader.read(textfile) saves the wrong number of vertices.", success);
		for(Integer i : vertices) {
			success = expectedDirectedGraph.getVertices().contains(i);
			assertTrue("reader.read(textfile) saves a wrong vertex.", success);
		}
		/*
		 *  check edges
		 */
		while(graph.containsEdges()) {
			for(Integer i : vertices) {
				currentEdge = graph.getStartpoints().get(i);
				while(!currentEdge.isEmpty()) {
					currentEndVertex = currentEdge.firstKey();
					success = expectedDirectedGraph.containsEdge(i, currentEndVertex);
					assertTrue("reader.read(textfile) saves a wrong edge.", success);
					success = expectedDirectedGraph.getStartpoints().get(i).get(currentEndVertex).intValue() == currentEdge.get(currentEndVertex).intValue();
					assertTrue("reader.read(textfile) saves a wrong edge weight.", success);
					expectedDirectedGraph.deleteEdge(i, currentEndVertex);
					graph.deleteEdge(i, currentEndVertex);
				}
			}
			for(Integer i : vertices) {
				currentEdge = ((DirectedGraph) graph).getEndpoints().get(i);
				while(!currentEdge.isEmpty()) {
					currentEndVertex = currentEdge.firstKey();
					success = expectedDirectedGraph.containsEdge(i, currentEndVertex);
					assertTrue("reader.read(textfile) saves a wrong edge.", success);
					success = expectedDirectedGraph.getEndpoints().get(i).get(currentEndVertex).intValue() == currentEdge.get(currentEndVertex).intValue();
					assertTrue("reader.read(textfile) saves a wrong edge weight.", success);
					expectedDirectedGraph.deleteEdge(i, currentEndVertex);
					graph.deleteEdge(i, currentEndVertex);
				}
			}
		}
		/*
		 * test undirected graph
		 */
		try {
			graph = reader.read("C:\\Users\\Sonja\\eclipse-workspace\\SoftwarePraktikum\\src\\Textfiles\\Test\\RUndirected.txt");
		} catch (IOException e) {
			assertFalse("reader.read(textfile) throws an exception instead of creating a graph without edges.", true);
		}
		success = graph.typeOfGraph().equals(expectedUndirectedGraph.typeOfGraph());
		assertTrue("reader.read(textfile) decides for the wrong type of graph.", success);
		/*
		 * 	check vertices
		 */
		vertices = graph.getVertices();
		success = vertices.size() == expectedUndirectedGraph.getVertices().size();
		assertTrue("reader.read(textfile) saves the wrong number of vertices.", success);
		for(Integer i : vertices) {
			success = expectedUndirectedGraph.getVertices().contains(i);
			assertTrue("reader.read(textfile) saves a wrong vertex.", success);
		}
		/*
		 * 	check edges
		 */
		while(graph.containsEdges()) {
			for(Integer i : vertices) {
				currentEdge = graph.getStartpoints().get(i);
				while(!currentEdge.isEmpty()) {
					currentEndVertex = currentEdge.firstKey();
					success = expectedUndirectedGraph.containsEdge(i, currentEndVertex);
					assertTrue("reader.read(textfile) saves a wrong edge.", success);
					success = expectedUndirectedGraph.getStartpoints().get(i).get(currentEndVertex).intValue() == currentEdge.get(currentEndVertex).intValue();
					assertTrue("reader.read(textfile) saves a wrong edge weight.", success);
					expectedUndirectedGraph.deleteEdge(i, currentEndVertex);
					graph.deleteEdge(i, currentEndVertex);
				}
			}
		}		
		
	}

}
