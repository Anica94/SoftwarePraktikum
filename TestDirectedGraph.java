import java.util.ArrayList;

import org.junit.Test;
import junit.framework.TestCase;

/**
 * Test for the class DirectedGraph.
 * 
 * @author Anica
 */

public class TestDirectedGraph extends TestCase {	
	
	DirectedGraph graph;
	/**
	 * Tests the empty constructor
	 */
	@Test
	public void testEmptyConstructor() {
		graph = new DirectedGraph();
		assertNotNull("Graph should != null after call to constructor.", graph);
		assertTrue("The graph should be empty.", graph.isEmpty());
		assertEquals("Number of vertices should be 0.", 0, graph.getVertices().size());
		assertEquals("Size of startpoints should be 0.", 0, graph.getStartpoints().size());
		assertEquals("Size of endpoints should be 0.", 0, graph.getEndpoints().size());
		assertTrue("The highest vertex name should be 0.", graph.getHighestVertexName()==0);
		assertFalse("The graph should not contain edges.", graph.containsEdges());
	}
	
	/**
	 * Tests the method getHighestVertexName()
	 */
	@Test
	public void testGetHighestVertexName() {
		graph = new DirectedGraph();
		graph.addVertex();
		assertTrue("The highest vertex name should be 1.", graph.getHighestVertexName()==1);
		graph.addVertex(5);
		assertTrue("The highest vertex name should be 5.", graph.getHighestVertexName()==5);
		graph.addVertex(2);
		assertTrue("The highest vertex name should be 5.", graph.getHighestVertexName()==5);
		graph.deleteVertex(5);
		/*
		 * The highest vertex name should be still 5 (although it usually is not).
		 * It's just there for making sure that no two vertices with the same number are added 
		 * by calling addVertex().
		 */
		assertTrue("The highest vertex name should be 5.", graph.getHighestVertexName()==5);
	}
	
	/**
	 * Tests the method typeOfGraph()
	 */
	@Test
	public void testTypeOfGraph() {
		graph = new DirectedGraph();
		assertEquals("The type of graph should be \"directed\"","directed", graph.typeOfGraph());
	}

	/**
	 * Tests the method addVertex()
	 */
	@Test
	public void testAddVertex() {
		graph = new DirectedGraph();
		Integer vertex = graph.addVertex();
		assertTrue("The added vertex should be 1", vertex==1);
		assertEquals("The number of vertices should be 1", 1, graph.getStartpoints().size());
		assertTrue("Startpoints should contain vertex 1.", graph.getStartpoints().containsKey(1));
		assertTrue("Endpoints should contain vertex 1.", graph.getEndpoints().containsKey(1));
		graph.addVertex(3);
		vertex = graph.addVertex();
		assertTrue("The added vertex should be 4", vertex==4);
	}
	
	/**
	 * Tests the method addVertex(Integer)
	 */
	@Test
	public void testAddVertexWithName() {
		graph = new DirectedGraph();
		boolean success = graph.addVertex(2);
		assertTrue("The vertex should have been added.", success);
		assertEquals("The number of vertices should be 1", 1, graph.getStartpoints().size());
		assertTrue("Startpoints should contain vertex 2.", graph.getStartpoints().containsKey(2));
		assertTrue("Endpoints should contain vertex 2.", graph.getEndpoints().containsKey(2));
		success = graph.addVertex(2);
		assertFalse("The vertex should not have been added because it is already there.", success);
		assertEquals("The number of vertices should be 1", 1, graph.getStartpoints().size());
		assertEquals("The number of vertices should be 1", 1, graph.getEndpoints().size());
	}
	
	/**
	 * Tests the method containsVertex(Integer)
	 */
	@Test
	public void testContainsVertex() {
		graph = new DirectedGraph();
		boolean success = graph.containsVertex(1);
		assertFalse("The graph should not contain vertex 1.", success);
		graph.addVertex();
		success = graph.containsVertex(1);
		assertTrue("The graph should contain vertex 1.", success);
		success = graph.containsVertex(2);
		assertFalse("The graph should not contain vertex 2.", success);
	}
	
	/**
	 * Tests the method deleteVertex(Integer)
	 */
	@Test
	public void testDeleteVertex() {
		graph = new DirectedGraph();
		boolean success = graph.deleteVertex(1);
		assertFalse("It should not be possible to delete vertex 1 because it is not in the graph.", success);
		graph.addVertex();
		success = graph.deleteVertex(1);
		assertTrue("The vertex should have been deleted.", success);
		assertFalse("The deleted vertex should not be contained in the startpoints.", graph.getStartpoints().containsKey(1));
		assertFalse("The deleted vertex should not be contained in the endpoints.", graph.getEndpoints().containsKey(1));
		assertEquals("The number of vertices should be 0.", 0, graph.getStartpoints().size());
		assertEquals("The number of vertices should be 0.", 0, graph.getEndpoints().size());
	}
	
	/**
	 * Tests the method getVertices()
	 */
	@Test
	public void testGetVertices() {
		graph = new DirectedGraph();
		graph.addVertex();
		graph.addVertex();
		ArrayList<Integer> vertices = graph.getVertices();
		assertEquals("The sice of graph.getVertices() should be the same as the size of graph.getStartpoints()", graph.getStartpoints().size(), vertices.size());
		boolean success = vertices.contains(1);
		assertTrue("graph.getVertices() should contain vertex 1.", success);
		success = vertices.contains(2);
		assertTrue("graph.getVertices() should contain vertex 2.", success);
	}
	
	/**
	 * Tests the method isAdjacent(Integer, Integer)
	 */
	@Test
	public void testIsAdjacentTo() {
		graph = new DirectedGraph();
		graph.addVertex();
		graph.addVertex();
		graph.addVertex();
		graph.addEdge(1, 2);
		boolean success = graph.isAdjacentTo(1, 2);
		assertTrue("Vertex 1 should be adjacent to vertex 2.", success);
		success = graph.isAdjacentTo(2, 1);
		assertFalse("Vertex 2 should not be adjacent to vertex 1.", success);
		success = graph.isAdjacentTo(1, 3);
		assertFalse("Vertex 1 should not be adjacent to vertex 3.", success);
		try {
			success = graph.isAdjacentTo(1, 4);
			fail("expected IllegalArgumentException");
		} catch (Exception e) {
			// ignore, this exception is expected.
		}		
	}
	
	/**
	 * Tests the method addEdge(Integer, Integer)
	 */
	@Test
	public void testAddEdge() {
		graph = new DirectedGraph();
		graph.addVertex();
		try {
			graph.addEdge(1, 2);
			fail("expected IllegalArgumentException");
		} catch (Exception e) {
			// ignore, this exception is expected.
		}
		try {
			graph.addEdge(2, 1);
			fail("expected IllegalArgumentException");
		} catch (Exception e) {
			// ignore, this exception is expected.
		}
		try {
			graph.addEdge(1, 1);
			fail("expected IllegalArgumentException");
		} catch (Exception e) {
			// ignore, this exception is expected.
		}
		graph.addVertex();
		boolean success = graph.addEdge(1, 2);
		assertTrue("The edge should have been added.", success);
		assertTrue("The graph should contain the edge.", graph.containsEdge(1, 2));
		assertFalse("The graph should not contain the edge.", graph.containsEdge(2, 1));
		assertTrue("The edgeweight should be 1.", graph.getEdgeWeight(1, 2)==1);
		success = graph.addEdge(1, 2);
		assertFalse("The edge should not have been added.", success);
		success = graph.addEdge(2, 1);
		assertTrue("The edge should have been added.", success);
	}
	
	/**
	 * Tests the method addEdge(Integer, Integer, Integer)
	 */
	@Test
	public void testAddEdgeWithWeight() {
		graph = new DirectedGraph();
		graph.addVertex();
		graph.addVertex();
		boolean success = graph.addEdge(1, 2, 2);
		assertTrue("The edge should have been added.", success);
		assertTrue("The graph should contain the edge.", graph.containsEdge(1, 2));
		assertTrue("The edgeweight should be 2.", graph.getEdgeWeight(1, 2)==2);
	}
	
	/**
	 * Tests the method containsEdge(Integer, Integer)
	 */
	@Test
	public void testContainsEdge() {
		graph = new DirectedGraph();
		graph.addVertex();
		try {
			graph.containsEdge(1, 2);
			fail("expected IllegalArgumentException");
		} catch (Exception e) {
			// ignore, this exception is expected.
		}
		try {
			graph.containsEdge(2, 1);
			fail("expected IllegalArgumentException");
		} catch (Exception e) {
			// ignore, this exception is expected.
		}
		graph.addVertex();
		boolean success = graph.containsEdge(1, 2);
		assertFalse("The graph should not contain the edge.", success);
		graph.addEdge(1, 2);
		success = graph.containsEdge(1, 2);
		assertTrue("The graph should contain the edge.", success);
		success = graph.containsEdge(2, 1);
		assertFalse("The graph should not contain the edge.", success);
		success = graph.containsEdge(1, 1);
		assertFalse("The graph should not contain the edge.", success);
	}
	
	/**
	 * Tests the method containsEdges()
	 */
	@Test
	public void testContainsEdges() {
		graph = new DirectedGraph();
		boolean success = graph.containsEdges();
		assertFalse("graph.containEdges() should be false.", success);
		graph.addVertex();
		graph.addVertex();
		success = graph.containsEdges();
		assertFalse("graph.containEdges() should be false.", success);
		graph.addEdge(1, 2);
		success = graph.containsEdges();
		assertTrue("graph.containsEdges() should be true.", success);
	}
	
	/**
	 * Tests the method deleteEdge(Integer, Integer)
	 */
	@Test
	public void testDeleteEdge() {
		graph = new DirectedGraph();
		graph.addVertex();
		try {
			graph.deleteEdge(1, 2);
			fail("expected IllegalArgumentException");
		} catch (Exception e) {
			// ignore, this exception is expected.
		}
		try {
			graph.deleteEdge(2, 1);
			fail("expected IllegalArgumentException");
		} catch (Exception e) {
			// ignore, this exception is expected.
		}
		try {
			graph.deleteEdge(1, 1);
			fail("expected IllegalArgumentException");
		} catch (Exception e) {
			// ignore, this exception is expected.
		}
		graph.addVertex();
		boolean success = graph.deleteEdge(1, 2);
		assertFalse("graph.deleteEdge(1,2) should be false.", success);
		graph.addEdge(1, 2);
		success = graph.deleteEdge(2, 1);
		assertFalse("graph.deleteEdge(1,2) should be false", success);
		graph.addEdge(2, 1);
		success = graph.deleteEdge(1, 2);
		assertTrue("graph.deleteEdge(1,2) should be true.", success);
		assertFalse("graph.containsEdge(1,2) should be false.", graph.containsEdge(1, 2));
		assertTrue("graph.containsEdge(2,1) should be true.", graph.containsEdge(2, 1));
	}
	
	/**
	 * Tests the method getEdgeWeight(Integer, Integer)
	 */
	@Test
	public void testGetEdgeWeight() {
		graph = new DirectedGraph();
		graph.addVertex();
		try {
			graph.getEdgeWeight(1, 1);
			fail("expected IllegalArgumentException");
		} catch (Exception e) {
			// ignore, this exception is expected.
		}
		try {
			graph.getEdgeWeight(1, 2);
			fail("expected IllegalArgumentException");
		} catch (Exception e) {
			// ignore, this exception is expected.
		}
		try {
			graph.getEdgeWeight(2, 1);
			fail("expected IllegalArgumentException");
		} catch (Exception e) {
			// ignore, this exception is expected.
		}
		graph.addVertex();
		try {
			graph.getEdgeWeight(1, 2);
			fail("expected IllegalArgumentException");
		} catch (Exception e) {
			// ignore, this exception is expected.
		}
		graph.addEdge(1, 2, 2);
		graph.addEdge(2, 1);
		assertTrue("graph.getEdgeWeight(1,2) should be 2.", graph.getEdgeWeight(1, 2)==2);
		assertTrue("graph.getEdgeWeight(2,1) should be 1.", graph.getEdgeWeight(2, 1)==1);
	}
	
	/**
	 * Tests the method setEdgeWeight(Integer, Integer, Integer)
	 */
	@Test
	public void testSetEdgeWeight() {
		graph = new DirectedGraph();
		graph.addVertex();
		try {
			graph.setEdgeWeight(1, 1, 2);
			fail("expected IllegalArgumentException");
		} catch (Exception e) {
			// ignore, this exception is expected.
		}
		try {
			graph.setEdgeWeight(1, 2, 2);
			fail("expected IllegalArgumentException");
		} catch (Exception e) {
			// ignore, this exception is expected.
		}
		try {
			graph.setEdgeWeight(2, 1, 2);
			fail("expected IllegalArgumentException");
		} catch (Exception e) {
			// ignore, this exception is expected.
		}
		graph.addVertex();
		try {
			graph.setEdgeWeight(1, 2, 2);
			fail("expected IllegalArgumentException");
		} catch (Exception e) {
			// ignore, this exception is expected.
		}
		graph.addEdge(1, 2);
		graph.addEdge(2, 1);
		graph.setEdgeWeight(1, 2, 2);
		assertTrue("The edgeweight of edge (1,2) should be 2.", graph.getEdgeWeight(1, 2)==2);
		assertEquals("The edgeweight of edge (2,1) should be 1.", new Integer(1), graph.getEdgeWeight(2, 1));
	}
	
	/**
	 * Tests the method isEmpty()
	 */
	@Test
	public void testIsEmpty() {
		graph = new DirectedGraph();
		assertTrue("The graph should be empty.", graph.isEmpty());
		graph.addVertex();
		assertFalse("The graph should not be empty.", graph.isEmpty());
		graph.deleteVertex(1);
		assertTrue("The graph should be empty.", graph.isEmpty());
	}
}
