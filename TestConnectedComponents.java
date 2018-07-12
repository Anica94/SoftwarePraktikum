import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import org.junit.Test;

import junit.framework.TestCase;

/**
 * 
 * @author Anica
 *
 */
public class TestConnectedComponents extends TestCase{

	private Graph graph;	
	private Graph result;
	private ConnectedComponents conComp = new ConnectedComponents();
	private Reader reader = new Reader();
	private ArrayList<Operation> changes = new ArrayList<>();
	private ArrayList<Operation> expectedChanges = new ArrayList<>();
	private VertexOperation currentChange, currentExpected;
	
	/**
	 * Tests the method execute(Graph)
	 */
	@Test
	public void testExecute() {
		
		/*
		 * Test null
		 */	
		try {
			conComp.execute(graph);
		} catch (NullPointerException e) {
			assertTrue("NullPointerException was thrown.", true);
		}
		
		/*
		 * Test empty graph
		 */
		graph = new UndirectedGraph();
		try {
			conComp.execute(graph);
		} catch (IllegalArgumentException e) {
			assertTrue("IllegalArgumentException.", true);
		}
		
		/*
		 * Test graph with three components
		 */
		try {
			graph = reader.read("C:\\Users\\Anica\\eclipse-workspace\\Graph_ST_AH\\src\\Textfiles\\Test\\CC.txt");
		} catch (IOException e) {
			// is tested at TestReader
		}
		changes = conComp.execute(graph);
		boolean success;
		expectedChanges = new ArrayList<>();
		expectedChanges.add(new VertexOperation("consider", 1));
		expectedChanges.add(new VertexOperation("choose", 1));
		expectedChanges.add(new VertexOperation("consider", 3));
		expectedChanges.add(new VertexOperation("choose", 3));
		expectedChanges.add(new VertexOperation("consider", 2));
		expectedChanges.add(new VertexOperation("choose", 2));
		expectedChanges.add(new VertexOperation("consider", 4));
		expectedChanges.add(new VertexOperation("choose", 4));
		expectedChanges.add(new VertexOperation("consider", 5));
		expectedChanges.add(new VertexOperation("choose", 5));
		expectedChanges.add(new VertexOperation("consider", 6));
		expectedChanges.add(new VertexOperation("choose", 6));
		assertTrue("conComp.execute(graph) makes the wrong number of changes", changes.size()==expectedChanges.size());
		
		for(int i=0; i<changes.size(); i++) {
			currentChange = (VertexOperation) changes.get(i);
			currentExpected = (VertexOperation) expectedChanges.get(i);
			
			success = currentChange.getOperationType().equals(currentExpected.getOperationType());
			assertTrue("conComp.execute(graph) decides for the wrong type of an operation.", success);
			success = currentChange.getOperationName().equals(currentExpected.getOperationName());
			assertTrue("conComp.execute(graph) decides for the wrong name of an operation.", success);
			success = currentChange.getVertexName()==currentExpected.getVertexName();
			assertTrue("conComp.execute(graph) decides for the wrong vertex.", success);
		}
	}
	
	/**
	 * Tests the method getResult(Graph)
	 */
	@Test
	public void testGetResult() {
		/*
		 * Test graph with three components
		 * (the result graph should be the same as the input graph)
		 */
		try {
			graph = reader.read("C:\\Users\\Anica\\eclipse-workspace\\Graph_ST_AH\\src\\Textfiles\\Test\\CC.txt");
		} catch (IOException e) {
			// is tested at TestReader
		}
		result = conComp.getResult(graph);
		ArrayList<Integer> vertices = graph.getVertices();
		HashMap<Integer, TreeMap<Integer, Integer>> startpoints = graph.getStartpoints();
		Integer currentVerex;
		Integer currentEndpoint;
		boolean success = false;
		
		for(int i=0; i<vertices.size(); i++) {
			success = false;
			currentVerex = vertices.get(i);
			TreeMap<Integer, Integer> startAdjacent =startpoints.get(currentVerex);
			while(!startAdjacent.isEmpty()) {
				currentEndpoint = startAdjacent.firstKey();
				graph.deleteEdge(currentVerex, currentEndpoint);
		    	try {
					success = result.deleteEdge(currentVerex, currentEndpoint);
				} catch (Exception e) {
					// Is tested in TestGraph
				}		    		
		    	assertTrue("There is an edge in the input graph which is not in the result although it should be there.", success);
			}
		    graph.deleteVertex(currentVerex);
		    try {
				success = result.deleteVertex(currentVerex);
			} catch (Exception e) {
				// Is tested in TestGraph
			}
		    assertTrue("There is a vertex in the input graph which is not in the result although it should be there.", success);
		}	
		success = result.isEmpty();
		assertTrue("There are more vertices (and maybe edges) in the result than should be there",success);
	}
	
	/**
	 * Tests the method getConnectedComponents()
	 */
	@Test
	public void testGetConnectedComponents() {
		/*
		 * Test graph with three components
		 */
		try {
			graph = reader.read("C:\\Users\\Anica\\eclipse-workspace\\Graph_ST_AH\\src\\Textfiles\\Test\\CC.txt");
		} catch (IOException e) {
			// is tested at TestReader
		}
		boolean success;
		Integer v;
		conComp.execute(graph);
		ArrayList<ArrayList<Integer>> connectedComponents = conComp.getConnectedComponents();
		ArrayList<Integer> currentCC;
		assertTrue("The ArrayList contains a wrong number of connected components.", connectedComponents.size()==3);
		currentCC = connectedComponents.get(0);
		assertTrue("The first connected component contains a wrong number of vertices.", currentCC.size()==3);
		v = 1;
		success = currentCC.remove(v);
		assertTrue("The first connected component does not contain a vertex that it should contain.", success);
		v = 2;
		success = currentCC.remove(v);
		assertTrue("The first connected component does not contain a vertex that it should contain.", success);
		v = 3;
		success = currentCC.remove(v);
		assertTrue("The first connected component does not contain a vertex that it should contain.", success);
		assertTrue("The first connected component contains a vertex that it should not contain.", currentCC.isEmpty());
		currentCC = connectedComponents.get(1);
		assertTrue("The second connected component contains a wrong number of vertices.", currentCC.size()==2);
		v = 4;
		success = currentCC.remove(v);
		assertTrue("The second connected component does not contain a vertex that it should contain.", success);
		v = 5;
		success = currentCC.remove(v);
		assertTrue("The second connected component does not contain a vertex that it should contain.", success);
		assertTrue("The second connected component contains a vertex that it should not contain.", currentCC.isEmpty());
		currentCC = connectedComponents.get(2);
		assertTrue("The third connected component contains a wrong number of vertices.", currentCC.size()==1);
		v = 6;
		success = currentCC.remove(v);
		assertTrue("The third connected component does not contain a vertex that it should contain.", success);
		assertTrue("The third connected component contains a vertex that it should not contain.", currentCC.isEmpty());
	}
}
