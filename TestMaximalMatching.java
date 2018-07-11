
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import junit.framework.TestCase;

/**
 * @author Anica
 *
 */
public class TestMaximalMatching extends TestCase {
	
	private Graph graph;
	private MaximalMatching mm = new MaximalMatching();
	private Reader reader = new Reader();
	private ArrayList<Operation> changes = new ArrayList<>();
	private ArrayList<Operation> expectedChanges = new ArrayList<>();
	private EdgeOperation currentChange, currentExpected;

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
			
			/*
			if(currentChange.getOperationType().equals(currentExpected.getOperationType())) {
				if(currentChange.getOperationName().equals(currentExpected.getOperationName())) {
					if(currentChange.getStartVertexName()==currentExpected.getStartVertexName()) {
						if(currentChange.getEndVertexName()==currentExpected.getEndVertexName()) {
							
						}
						else {
							assertFalse("mm.execute(graph) decides for the wrong endpoint.", true);
						}
					}
					else {
						assertFalse("mm.execute(graph) decides for the wrong startpoint.", true);
					}
				}
				else {
					assertFalse("mm.execute(graph) decides for the wrong name of an operation.", true);
				}
			}
			else {
				assertFalse("mm.execute(graph) decides for the wrong type of an operation.", true);
			}
			*/
		}	
		assertTrue("mm.execute(graph) makes the expected changes.", true);
	}
	
	public void testGetResult() {
		
	}

}
