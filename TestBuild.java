import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

import org.junit.Test;
import junit.framework.TestCase;

/**
 * 
 * 
 * @author Sonja
 *
 */
public class TestBuild extends TestCase{

	private Build build = new Build();
	private ReaderBUILD reader = new ReaderBUILD();
	private boolean success;
	private ArrayList<Integer> leaves;
	private ArrayList<Pair<Pair<Integer, Integer>, Integer>> triples;
	private Pair<UndirectedGraph, Integer> rootedTree;
	private UndirectedGraph tree;
	private Integer root;
	private ArrayList<Operation> changes;
	private ArrayList<Operation> expectedChanges = new ArrayList<>();
	private UndirectedGraph expectedTree = new UndirectedGraph();
	private ArrayList<Integer> vertices;
	private TreeMap<Integer, Integer> currentEdge;
	private Integer currentEndVertex;
	private VertexOperation currentChangeV;
	private VertexOperation currentExpectedV;
	private EdgeOperation currentChangeE;
	private EdgeOperation currentExpectedE;
	
	
	
	@Test
	public void testEmptyConstructor() {
		success = build.getChanges().isEmpty();
		assertTrue("Changes should be empty after call to constructor", success);
	}
	@Test
	public void test() {
		/*
		 * test of empty or wrong input not necessary since ReaderBUILD will not produce wrong or empty leafset and wrong tripleset
		 */
		/*
		 * test empty tripleset
		 */
		try {
			reader.read("C:\\Users\\Sonja\\eclipse-workspace\\SoftwarePraktikum\\src\\Textfiles\\Test\\BemptyTripleset.txt");
		} catch (IOException e1) {
			// is tested at TestReaderBUILD
		}
		leaves = reader.getLeafset();
		triples = reader.getTripleset();
		rootedTree = build.build(triples, leaves, new Integer(-1));
        tree = rootedTree.getFirst();
        root = rootedTree.getSecond();
        /*
         * 	produce expected changes
         */
        // aho graph
     	expectedChanges.add(new VertexOperation("aho add", new Integer(1)));
        expectedChanges.add(new VertexOperation("aho add", new Integer(2)));
        expectedChanges.add(new VertexOperation("aho add", new Integer(3)));
   		// connected components
 		expectedChanges.add(new VertexOperation("consider", new Integer(1)));
 		expectedChanges.add(new VertexOperation("choose", new Integer(1)));
 		expectedChanges.add(new VertexOperation("consider", new Integer(2)));
 		expectedChanges.add(new VertexOperation("choose", new Integer(2)));
 		expectedChanges.add(new VertexOperation("consider", new Integer(3)));
 		expectedChanges.add(new VertexOperation("choose", new Integer(3)));
  			// recursive call 1
     		// aho graph
     		expectedChanges.add(new VertexOperation("aho add", new Integer(1)));
   	        // connected components
 	        expectedChanges.add(new VertexOperation("consider", new Integer(1)));
 			expectedChanges.add(new VertexOperation("choose", new Integer(1)));
 			// build
 			expectedChanges.add(new VertexOperation("build add", new Integer(1)));
			// recursive call 2
     		// aho graph
			expectedChanges.add(new VertexOperation("aho add", new Integer(2)));
			// connected components
			expectedChanges.add(new VertexOperation("consider", new Integer(2)));
			expectedChanges.add(new VertexOperation("choose", new Integer(2)));
			// build
			expectedChanges.add(new VertexOperation("build add", new Integer(2)));
			// recursive call 3
 			// aho graph
 			expectedChanges.add(new VertexOperation("aho add", new Integer(3)));
 			// connected components
 			expectedChanges.add(new VertexOperation("consider", new Integer(3)));
 			expectedChanges.add(new VertexOperation("choose", new Integer(3)));
 			//build
 			expectedChanges.add(new VertexOperation("build add", new Integer(3)));
 		// build
		expectedChanges.add(new VertexOperation("build add", new Integer(4)));
		expectedChanges.add(new EdgeOperation("build add", new Integer(4), new Integer(1)));
		expectedChanges.add(new EdgeOperation("build add", new Integer(4), new Integer(2)));
		expectedChanges.add(new EdgeOperation("build add", new Integer(4), new Integer(3)));
		/*
         * 	check changes
         */
        changes = build.getChanges();
		success = changes.size()==expectedChanges.size();
		assertTrue("build.build(triples, leaves, new Integer(-1)) makes the wrong number of changes", success);
		for(int i = 0; i < changes.size(); i++) {
			success = changes.get(i).getOperationType().equals(expectedChanges.get(i).getOperationType());
			assertTrue("build.build(triples, leaves, new Integer(-1)) decides for the wrong type of an operation.", success);
			if(changes.get(i).getOperationType().equals("vertex")) {
				currentChangeV = (VertexOperation) changes.get(i);
				currentExpectedV = (VertexOperation) expectedChanges.get(i);
				success = currentChangeV.getOperationName().equals(currentExpectedV.getOperationName());
				assertTrue("build.build(triples, leaves, new Integer(-1)) decides for the wrong name of an operation.", success);
				success = currentChangeV.getVertexName().intValue() == currentExpectedV.getVertexName().intValue();
				assertTrue("build.build(triples, leaves, new Integer(-1)) decides for the wrong vertex.", success);
			}
			else if(changes.get(i).getOperationType().equals("edge")) {
				currentChangeE = (EdgeOperation) changes.get(i);
				currentExpectedE = (EdgeOperation) expectedChanges.get(i);
				success = currentChangeE.getOperationName().equals(currentExpectedE.getOperationName());
				assertTrue("build.build(triples, leaves, new Integer(-1)) decides for the wrong name of an operation.", success);
				success = currentChangeE.getStartVertexName().intValue() == currentExpectedE.getStartVertexName().intValue();
				assertTrue("build.build(triples, leaves, new Integer(-1)) decides for the wrong startpoint.", success);
				success = currentChangeE.getEndVertexName().intValue() == currentExpectedE.getEndVertexName().intValue();
				assertTrue("build.build(triples, leaves, new Integer(-1)) decides for the wrong endpoint.", success);
			}
		}
		/*
		 * test inconsistent tripleset
		 */
		try {
			reader.read("C:\\Users\\Sonja\\eclipse-workspace\\SoftwarePraktikum\\src\\Textfiles\\Test\\Binconsistent.txt");
		} catch (IOException e2) {
			// is tested at TestReaderBUILD
		}
		leaves = reader.getLeafset();
		triples = reader.getTripleset();
		rootedTree = build.build(triples, leaves, new Integer(-1));
        tree = rootedTree.getFirst();
        root = rootedTree.getSecond();
        assertNull("build.build(triples, leaves, new Integer(-1)) did not return null as root for an inconsistent tripleset.", root);
        assertNull("build.build(triples, leaves, new Integer(-1)) did not return null as tree for an inconsistent tripleset.", tree);
        /*
         * 	produce expected changes
         */
        // aho graph
        expectedChanges.add(new VertexOperation("aho add", new Integer(1)));
        expectedChanges.add(new VertexOperation("aho add", new Integer(2)));
        expectedChanges.add(new VertexOperation("aho add", new Integer(3)));
        expectedChanges.add(new EdgeOperation("aho add", new Integer(1), new Integer(2)));
		expectedChanges.add(new EdgeOperation("aho add", new Integer(1), new Integer(3)));
		expectedChanges.add(new EdgeOperation("aho add", new Integer(2), new Integer(3)));
		// connected components
		expectedChanges.add(new VertexOperation("consider", new Integer(1)));
		expectedChanges.add(new VertexOperation("choose", new Integer(1)));
		expectedChanges.add(new VertexOperation("consider", new Integer(3)));
		expectedChanges.add(new VertexOperation("choose", new Integer(3)));
		expectedChanges.add(new VertexOperation("consider", new Integer(2)));
		expectedChanges.add(new VertexOperation("choose", new Integer(2)));
		/*
         * 	check changes
         */
        changes = build.getChanges();
		success = changes.size()==expectedChanges.size();
		assertTrue("build.build(triples, leaves, new Integer(-1)) makes the wrong number of changes", success);
		for(int i = 0; i < changes.size(); i++) {
			success = changes.get(i).getOperationType().equals(expectedChanges.get(i).getOperationType());
			assertTrue("build.build(triples, leaves, new Integer(-1)) decides for the wrong type of an operation.", success);
			if(changes.get(i).getOperationType().equals("vertex")) {
				currentChangeV = (VertexOperation) changes.get(i);
				currentExpectedV = (VertexOperation) expectedChanges.get(i);
				success = currentChangeV.getOperationName().equals(currentExpectedV.getOperationName());
				assertTrue("build.build(triples, leaves, new Integer(-1)) decides for the wrong name of an operation.", success);
				success = currentChangeV.getVertexName().intValue() == currentExpectedV.getVertexName().intValue();
				assertTrue("build.build(triples, leaves, new Integer(-1)) decides for the wrong vertex.", success);
			}
			else if(changes.get(i).getOperationType().equals("edge")) {
				currentChangeE = (EdgeOperation) changes.get(i);
				currentExpectedE = (EdgeOperation) expectedChanges.get(i);
				success = currentChangeE.getOperationName().equals(currentExpectedE.getOperationName());
				assertTrue("build.build(triples, leaves, new Integer(-1)) decides for the wrong name of an operation.", success);
				success = currentChangeE.getStartVertexName().intValue() == currentExpectedE.getStartVertexName().intValue();
				assertTrue("build.build(triples, leaves, new Integer(-1)) decides for the wrong startpoint.", success);
				success = currentChangeE.getEndVertexName().intValue() == currentExpectedE.getEndVertexName().intValue();
				assertTrue("build.build(triples, leaves, new Integer(-1)) decides for the wrong endpoint.", success);
			}
		}
		/*
		 * test consistent tripleset
		 */
		try {
			reader.read("C:\\Users\\Sonja\\eclipse-workspace\\SoftwarePraktikum\\src\\Textfiles\\Test\\BconsistentAllTriples.txt");
		} catch (IOException e3) {
			// is tested at TestReaderBUILD
		}
		leaves = reader.getLeafset();
		triples = reader.getTripleset();
		rootedTree = build.build(triples, leaves, new Integer(-1));
        tree = rootedTree.getFirst();
        root = rootedTree.getSecond();
        success = root.intValue() == 6;
        assertTrue("build.build(triples, leaves, new Integer(-1)) did decide for a wrong root.", success);
        /*
         *  produce expected tree
         */
        expectedTree.addVertex(new Integer(1));
        expectedTree.addVertex(new Integer(2));
        expectedTree.addVertex(new Integer(3));
        expectedTree.addVertex(new Integer(4));
        expectedTree.addVertex(new Integer(5));
        expectedTree.addVertex(new Integer(6));
		expectedTree.addEdge(new Integer(1), new Integer(5));
		expectedTree.addEdge(new Integer(2), new Integer(5));
		expectedTree.addEdge(new Integer(6), new Integer(5));
		expectedTree.addEdge(new Integer(6), new Integer(3));
		expectedTree.addEdge(new Integer(6), new Integer(4));
		/*
		 * 	check vertices
		 */
		vertices = tree.getVertices();
		success = vertices.size() == expectedTree.getVertices().size();
		assertTrue("build.build(triples, leaves, new Integer(-1)) saves the wrong number of vertices.", success);
		for(Integer i : vertices) {
			success = expectedTree.getVertices().contains(i);
			assertTrue("build.build(triples, leaves, new Integer(-1)) saves a wrong vertex.", success);
		}
		/*
		 * 	check edges
		 */
		while(tree.containsEdges()) {
			for(Integer i : vertices) {
				currentEdge = tree.getStartpoints().get(i);
				while(!currentEdge.isEmpty()) {
					currentEndVertex = currentEdge.firstKey();
					success = expectedTree.containsEdge(i, currentEndVertex);
					assertTrue("build.build(triples, leaves, new Integer(-1)) saves a wrong edge.", success);
					expectedTree.deleteEdge(i, currentEndVertex);
					tree.deleteEdge(i, currentEndVertex);
				}
			}
		}
		/*
		 *  produce expected changes
		 */
		// aho graph
		expectedChanges.add(new VertexOperation("aho add", new Integer(1)));
        expectedChanges.add(new VertexOperation("aho add", new Integer(2)));
        expectedChanges.add(new VertexOperation("aho add", new Integer(3)));
        expectedChanges.add(new VertexOperation("aho add", new Integer(4)));
        expectedChanges.add(new EdgeOperation("aho add", new Integer(1), new Integer(2)));
		expectedChanges.add(new EdgeOperation("aho add", new Integer(1), new Integer(2)));
		// connected components
		expectedChanges.add(new VertexOperation("consider", new Integer(1)));
		expectedChanges.add(new VertexOperation("choose", new Integer(1)));
		expectedChanges.add(new VertexOperation("consider", new Integer(2)));
		expectedChanges.add(new VertexOperation("choose", new Integer(2)));
		expectedChanges.add(new VertexOperation("consider", new Integer(3)));
		expectedChanges.add(new VertexOperation("choose", new Integer(3)));
		expectedChanges.add(new VertexOperation("consider", new Integer(4)));
		expectedChanges.add(new VertexOperation("choose", new Integer(4)));
			// recursive call 1
			// aho graph
			expectedChanges.add(new VertexOperation("aho add", new Integer(1)));
	        expectedChanges.add(new VertexOperation("aho add", new Integer(2)));
	        // connected components
	        expectedChanges.add(new VertexOperation("consider", new Integer(1)));
			expectedChanges.add(new VertexOperation("choose", new Integer(1)));
			expectedChanges.add(new VertexOperation("consider", new Integer(2)));
			expectedChanges.add(new VertexOperation("choose", new Integer(2)));
				// recursive call 1.1
				// aho graph
				expectedChanges.add(new VertexOperation("aho add", new Integer(1)));
				// connected components
				expectedChanges.add(new VertexOperation("consider", new Integer(1)));
				expectedChanges.add(new VertexOperation("choose", new Integer(1)));
				// build
				expectedChanges.add(new VertexOperation("build add", new Integer(1)));
				
				// recursive call 1.2
				// aho graph
				expectedChanges.add(new VertexOperation("aho add", new Integer(2)));
				// connected components
				expectedChanges.add(new VertexOperation("consider", new Integer(2)));
				expectedChanges.add(new VertexOperation("choose", new Integer(2)));
				// build
				expectedChanges.add(new VertexOperation("build add", new Integer(2)));
			// build
			expectedChanges.add(new VertexOperation("build add", new Integer(5)));
			expectedChanges.add(new EdgeOperation("build add", new Integer(5), new Integer(1)));
			expectedChanges.add(new EdgeOperation("build add", new Integer(5), new Integer(2)));
			// recursive call 2
			// aho graph
			expectedChanges.add(new VertexOperation("aho add", new Integer(3)));
			// connected components
			expectedChanges.add(new VertexOperation("consider", new Integer(3)));
			expectedChanges.add(new VertexOperation("choose", new Integer(3)));
			//build
			expectedChanges.add(new VertexOperation("build add", new Integer(3)));
			// recursive call 3
			// aho graph
			expectedChanges.add(new VertexOperation("aho add", new Integer(4)));
			// connected components
			expectedChanges.add(new VertexOperation("consider", new Integer(4)));
			expectedChanges.add(new VertexOperation("choose", new Integer(4)));
			// build
			expectedChanges.add(new VertexOperation("build add", new Integer(4)));
		// build
		expectedChanges.add(new VertexOperation("build add", new Integer(6)));
		expectedChanges.add(new EdgeOperation("build add", new Integer(6), new Integer(5)));
		expectedChanges.add(new EdgeOperation("build add", new Integer(6), new Integer(3)));
		expectedChanges.add(new EdgeOperation("build add", new Integer(6), new Integer(4)));
		/*
         * 	check changes
         */
        changes = build.getChanges();
		success = changes.size()==expectedChanges.size();
		assertTrue("build.build(triples, leaves, new Integer(-1)) makes the wrong number of changes", success);
		for(int i = 0; i < changes.size(); i++) {
			success = changes.get(i).getOperationType().equals(expectedChanges.get(i).getOperationType());
			assertTrue("build.build(triples, leaves, new Integer(-1)) decides for the wrong type of an operation.", success);
			if(changes.get(i).getOperationType().equals("vertex")) {
				currentChangeV = (VertexOperation) changes.get(i);
				currentExpectedV = (VertexOperation) expectedChanges.get(i);
				success = currentChangeV.getOperationName().equals(currentExpectedV.getOperationName());
				assertTrue("build.build(triples, leaves, new Integer(-1)) decides for the wrong name of an operation.", success);
				success = currentChangeV.getVertexName().intValue() == currentExpectedV.getVertexName().intValue();
				assertTrue("build.build(triples, leaves, new Integer(-1)) decides for the wrong vertex.", success);
			}
			else if(changes.get(i).getOperationType().equals("edge")) {
				currentChangeE = (EdgeOperation) changes.get(i);
				currentExpectedE = (EdgeOperation) expectedChanges.get(i);
				success = currentChangeE.getOperationName().equals(currentExpectedE.getOperationName());
				assertTrue("build.build(triples, leaves, new Integer(-1)) decides for the wrong name of an operation.", success);
				success = currentChangeE.getStartVertexName().intValue() == currentExpectedE.getStartVertexName().intValue();
				assertTrue("build.build(triples, leaves, new Integer(-1)) decides for the wrong startpoint.", success);
				success = currentChangeE.getEndVertexName().intValue() == currentExpectedE.getEndVertexName().intValue();
				assertTrue("build.build(triples, leaves, new Integer(-1)) decides for the wrong endpoint.", success);
			}
		}
	}
}
