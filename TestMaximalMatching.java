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
public class TestMaximalMatching extends TestCase {
	
	private Graph graph;
	private Graph result;
	private MaximalMatching mm = new MaximalMatching();
	private Reader reader = new Reader();
	private ArrayList<Operation> changes = new ArrayList<>();
	private ArrayList<Operation> expectedChanges = new ArrayList<>();
	private EdgeOperation currentChange, currentExpected;

	/**
	 * Tests the method execute(Graph)
	 */
	@Test
	public void testExecute() {
		
		/*
		 * Test null
		 */
		
		try {
			mm.execute(graph);
		} catch (NullPointerException e) {
			assertTrue("NullPointerException was thrown.", true);
		}
		
		/*
		 * Test empty graph
		 */
		graph = new UndirectedGraph();
		try {
			mm.execute(graph);
		} catch (IllegalArgumentException e) {
			assertTrue("IllegalArgumentException.", true);
		}
		
		/*
		 * Test graph without edges
		 */
		try {
			graph = reader.read("C:\\Users\\Anica\\eclipse-workspace\\Graph_ST_AH\\src\\Textfiles\\Test\\MMnoEdges.txt");
		} catch (IOException e) {
			// is tested at TestReader
		}
		changes = mm.execute(graph);
		assertTrue("mm.execute(graph) made changes although no changes should be made.", changes.isEmpty());
		
		/*
		 * Test graph with two components: a star and a circle
		 */
		try {
			graph = reader.read("C:\\Users\\Anica\\eclipse-workspace\\Graph_ST_AH\\src\\Textfiles\\Test\\MMStarCircle.txt");
		} catch (IOException e) {
			// is tested at TestReader
		}
		changes = mm.execute(graph);
		expectedChanges.add(new EdgeOperation("consider", 1, 2));
		expectedChanges.add(new EdgeOperation("choose", 1, 2));
		expectedChanges.add(new EdgeOperation("consider", 3, 4));
		expectedChanges.add(new EdgeOperation("choose", 3, 4));
		expectedChanges.add(new EdgeOperation("consider", 5, 6));
		expectedChanges.add(new EdgeOperation("choose", 5, 6));
		
		boolean success;
		
		assertTrue("mm.execute(graph) makes the wrong number of changes", changes.size()==expectedChanges.size());
		for(int i=0; i<changes.size(); i++) {
			currentChange = (EdgeOperation) changes.get(i);
			currentExpected = (EdgeOperation) expectedChanges.get(i);
			
			success = currentChange.getOperationType().equals(currentExpected.getOperationType());
			assertTrue("mm.execute(graph) decides for the wrong type of an operation.", success);
			success = currentChange.getOperationName().equals(currentExpected.getOperationName());
			assertTrue("mm.execute(graph) decides for the wrong name of an operation.", success);
			success = currentChange.getStartVertexName()==currentExpected.getStartVertexName();
			assertTrue("mm.execute(graph) decides for the wrong startpoint.", success);
			success = currentChange.getEndVertexName()==currentExpected.getEndVertexName();
			assertTrue("mm.execute(graph) decides for the wrong endpoint.", success);
		}
	}
	
	/**
	 * Tests if the result is right.
	 */
	public void testGetResult() {
		/*
		 * Test if the result is really a matching
		 */
		try {
			graph = reader.read("C:\\Users\\Anica\\eclipse-workspace\\Graph_ST_AH\\src\\Textfiles\\Test\\MMStarCircle.txt");
		} catch (IOException e) {
			// is tested at TestReader
		}
		result = mm.getResult(graph);
		ArrayList<Integer> vertices = graph.getVertices();
		ArrayList<Integer> verticesResult = result.getVertices();
		HashMap<Integer, TreeMap<Integer, Integer>> startpoints = graph.getStartpoints();
		HashMap<Integer, TreeMap<Integer, Integer>> startpointsResult = result.getStartpoints();
		Integer currentVerex;
		Integer currentEndpoint;
		boolean success;
		
		for(int i=0; i<verticesResult.size(); i++) {
			currentVerex = verticesResult.get(i);
			success = startpointsResult.get(currentVerex).size()<=1;
			assertTrue("The result (mm.getResult(graph)) is no matching.", success);
		}
		
		/*
		 * Test if the resulting matching is really maximal
		 */
		for(int i=0; i<vertices.size(); i++) {
			currentVerex = vertices.get(i);
			TreeMap<Integer, Integer> startAdjacent =graph.getStartpoints().get(currentVerex);
			Set<Integer> set = startAdjacent.keySet();
			java.util.Iterator<Integer> itr = set.iterator();
		    while (itr.hasNext()){
		    	currentEndpoint = itr.next();
		    	// if success is true, the current edge is not in M but should be
		    	success = (startpointsResult.get(currentVerex).isEmpty() && startpointsResult.get(currentEndpoint).isEmpty());
		    	assertFalse("The result (mm.getResult(graph)) is no maximal matching, there is an edge that should be in M as well.", success);
		    }
		}
	}

}
