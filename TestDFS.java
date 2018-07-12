import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;

import org.junit.Test;

import junit.framework.TestCase;

/**
 * @author Anica
 *
 */
public class TestDFS extends TestCase {

	private Graph graph;
	private Graph result;
	private DFS dfs = new DFS();
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
			dfs.execute(graph);
		} catch (NullPointerException e) {
			assertTrue("NullPointerException was thrown.", true);
		}
		
		/*
		 * Test empty graph
		 */
		graph = new UndirectedGraph();
		try {
			dfs.execute(graph);
		} catch (IllegalArgumentException e) {
			assertTrue("IllegalArgumentException.", true);
		}
		
		/*
		 * Test graph without edges (only one vertex)
		 */
		graph.addVertex();
		changes = dfs.execute(graph);
		expectedChanges.add(new VertexOperation("consider", 1));
		expectedChanges.add(new VertexOperation("choose", 1));
		assertTrue("dfs.execute(graph) makes the wrong number of changes", changes.size()==expectedChanges.size());
		
		boolean success;
		
		for(int i=0; i<changes.size(); i++) {
			currentChange = (VertexOperation) changes.get(i);
			currentExpected = (VertexOperation) expectedChanges.get(i);
			
			success = currentChange.getOperationType().equals(currentExpected.getOperationType());
			assertTrue("dfs.execute(graph) decides for the wrong type of an operation.", success);
			success = currentChange.getOperationName().equals(currentExpected.getOperationName());
			assertTrue("dfs.execute(graph) decides for the wrong name of an operation.", success);
			success = currentChange.getVertexName()==currentExpected.getVertexName();
			assertTrue("dfs.execute(graph) decides for the wrong vertex.", success);
		}
		
		/*
		 * Test tree
		 */
		try {
			graph = reader.read("C:\\Users\\Anica\\eclipse-workspace\\Graph_ST_AH\\src\\Textfiles\\Test\\DFSTree.txt");
		} catch (IOException e) {
			// is tested at TestReader
		}
		changes = dfs.execute(graph);
		expectedChanges = new ArrayList<>();
		expectedChanges.add(new VertexOperation("consider", 1));
		expectedChanges.add(new VertexOperation("choose", 1));
		expectedChanges.add(new VertexOperation("consider", 5));
		expectedChanges.add(new VertexOperation("choose", 5));
		expectedChanges.add(new VertexOperation("consider", 8));
		expectedChanges.add(new VertexOperation("choose", 8));
		expectedChanges.add(new VertexOperation("consider", 7));
		expectedChanges.add(new VertexOperation("choose", 7));
		expectedChanges.add(new VertexOperation("consider", 6));
		expectedChanges.add(new VertexOperation("choose", 6));
		expectedChanges.add(new VertexOperation("consider", 2));
		expectedChanges.add(new VertexOperation("choose", 2));
		expectedChanges.add(new VertexOperation("consider", 4));
		expectedChanges.add(new VertexOperation("choose", 4));
		expectedChanges.add(new VertexOperation("consider", 3));
		expectedChanges.add(new VertexOperation("choose", 3));
		assertTrue("dfs.execute(graph) makes the wrong number of changes", changes.size()==expectedChanges.size());
		
		for(int i=0; i<changes.size(); i++) {
			currentChange = (VertexOperation) changes.get(i);
			currentExpected = (VertexOperation) expectedChanges.get(i);
			
			success = currentChange.getOperationType().equals(currentExpected.getOperationType());
			assertTrue("dfs.execute(graph) decides for the wrong type of an operation.", success);
			success = currentChange.getOperationName().equals(currentExpected.getOperationName());
			assertTrue("dfs.execute(graph) decides for the wrong name of an operation.", success);
			success = currentChange.getVertexName()==currentExpected.getVertexName();
			assertTrue("dfs.execute(graph) decides for the wrong vertex.", success);
		}
		
		/*
		 * Test circle
		 */
		try {
			graph = reader.read("C:\\Users\\Anica\\eclipse-workspace\\Graph_ST_AH\\src\\Textfiles\\Test\\DFSCircle.txt");
		} catch (IOException e) {
			// is tested at TestReader
		}
		changes = dfs.execute(graph);
		expectedChanges = new ArrayList<>();
		expectedChanges.add(new VertexOperation("consider", 1));
		expectedChanges.add(new VertexOperation("choose", 1));
		expectedChanges.add(new VertexOperation("consider", 3));
		expectedChanges.add(new VertexOperation("choose", 3));
		expectedChanges.add(new VertexOperation("consider", 2));
		expectedChanges.add(new VertexOperation("choose", 2));
		assertTrue("dfs.execute(graph) makes the wrong number of changes", changes.size()==expectedChanges.size());
		
		for(int i=0; i<changes.size(); i++) {
			currentChange = (VertexOperation) changes.get(i);
			currentExpected = (VertexOperation) expectedChanges.get(i);
			
			success = currentChange.getOperationType().equals(currentExpected.getOperationType());
			assertTrue("dfs.execute(graph) decides for the wrong type of an operation.", success);
			success = currentChange.getOperationName().equals(currentExpected.getOperationName());
			assertTrue("dfs.execute(graph) decides for the wrong name of an operation.", success);
			success = currentChange.getVertexName()==currentExpected.getVertexName();
			assertTrue("dfs.execute(graph) decides for the wrong vertex.", success);
		}
		
		/*
		 * Test graph with 2 components
		 */
		try {
			graph = reader.read("C:\\Users\\Anica\\eclipse-workspace\\Graph_ST_AH\\src\\Textfiles\\Test\\DFS2Components.txt");
		} catch (IOException e) {
			// is tested at TestReader
		}
		changes = dfs.execute(graph);
		// same expected arrylist as before
		assertTrue("dfs.execute(graph) makes the wrong number of changes", changes.size()==expectedChanges.size());
		
		for(int i=0; i<changes.size(); i++) {
			currentChange = (VertexOperation) changes.get(i);
			currentExpected = (VertexOperation) expectedChanges.get(i);
			
			success = currentChange.getOperationType().equals(currentExpected.getOperationType());
			assertTrue("dfs.execute(graph) decides for the wrong type of an operation.", success);
			success = currentChange.getOperationName().equals(currentExpected.getOperationName());
			assertTrue("dfs.execute(graph) decides for the wrong name of an operation.", success);
			success = currentChange.getVertexName()==currentExpected.getVertexName();
			assertTrue("dfs.execute(graph) decides for the wrong vertex.", success);
		}
	}
	
	/**
	 * Tests the method execute(Graph, Integer startvertex)
	 */
	@Test
	public void testExecute2() {
		graph = new UndirectedGraph();
		graph.addVertex();
		try {
			dfs.execute(graph, 2);
		} catch (Exception e) {
			assertTrue("IllegalArgumentException.", true); 
		}
	}
	
	/**
	 * Tests the method getResult(Graph)
	 */
	public void testGetResult() {
		/*
		 * Test graph with one component
		 * (the result should be the same as the input)
		 */
		try {
			graph = reader.read("C:\\Users\\Anica\\eclipse-workspace\\Graph_ST_AH\\src\\Textfiles\\Test\\DFSCircle.txt");
		} catch (IOException e) {
			// is tested at TestReader
		}
		result = dfs.getResult(graph);
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
		
		/*
		 * Test graph with two components (a circle with three vertices and an edge)
		 */
		try {
			graph = reader.read("C:\\Users\\Anica\\eclipse-workspace\\Graph_ST_AH\\src\\Textfiles\\Test\\DFS2Components.txt");
		} catch (IOException e) {
			// is tested at TestReader
		}
		result = dfs.getResult(graph);
		
		success = result.deleteEdge(1, 2);
		assertTrue("The result (dfs.getResult(graph) does not contain the edge {1,2} although it should.", success);
		success = result.deleteEdge(2, 3);
		assertTrue("The result (dfs.getResult(graph) does not contain the edge {2,3} although it should.", success);
		success = result.deleteEdge(1, 3);
		assertTrue("The result (dfs.getResult(graph) does not contain the edge {1,3} although it should.", success);
		success = result.deleteVertex(1);
		assertTrue("The result (dfs.getResult(graph) does not contain the vertex 1 although it should.", success);
		success = result.deleteVertex(2);
		assertTrue("The result (dfs.getResult(graph) does not contain the vertex 2 although it should.", success);
		success = result.deleteVertex(3);
		assertTrue("The result (dfs.getResult(graph) does not contain the vertex 3 although it should.", success);
		assertTrue("The result (dfs.getResult(graph) contains more vertices (and maybe edges) than it should.", result.isEmpty());
	}
	
	/**
	 * Tests the method getVerticesOfConnectedComponent()
	 */
	public void testGetVerticesOfConnectedComponent() {
		/*
		 * Test graph with two components (a circle with three vertices and an edge)
		 */
		try {
			graph = reader.read("C:\\Users\\Anica\\eclipse-workspace\\Graph_ST_AH\\src\\Textfiles\\Test\\DFS2Components.txt");
		} catch (IOException e) {
			// is tested at TestReader
		}
		dfs.execute(graph);
		ArrayList<Integer> verticesConComp = dfs.getVerticesOfConnectedComponent();
		boolean success;		
		assertTrue("The list of vertices of the connected component has a wrong number of elements", verticesConComp.size()==3);
		Integer vertex = 1;
		success = verticesConComp.remove(vertex);
		assertTrue("The list of vertices of the connected component does not contain the vertex 1 although it should.", success);
		vertex = 2;
		success = verticesConComp.remove(vertex);
		assertTrue("The list of vertices of the connected component does not contain the vertex 2 although it should.", success);
		vertex = 3;
		success = verticesConComp.remove(vertex);
		assertTrue("The list of vertices of the connected component does not contain the vertex 3 although it should.", success);
		assertTrue("The list of vertices of the connected component contains elements that it should not contain.", verticesConComp.isEmpty());
	}
}
