import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * This algorithm computes a maximal matching for an undirected graph.
 * 
 * @author Sonja, Anica
 */
public class MaximalMatching implements Algorithm {
	/**
	 * Saves the result of the algorithm as a graph.
	 */
	private Graph result;

	/**
	 * Produces an algorithm for maximal matching with an empty result.
	 */
	public MaximalMatching() {
		result = null;
	}


	@Override
	public ArrayList<Operation> execute(Graph graph) {
		if (graph == null) {
    		throw new NullPointerException();
    	}
		if (graph.isEmpty()) {
			throw new IllegalArgumentException();
		}
		/*
		 * make copy of the graph to work on (contains E')
		 * produce containers of variables to use later
		 * produce arraylist of operations that will be done by the algorithm
		 */		
		ArrayList<Integer> vertices = graph.getVertices();
		HashMap<Integer, TreeMap<Integer, Integer>> startpoints = graph.getStartpoints();
		Integer currentVertex;
		Graph copyGraph;
		copyGraph = new UndirectedGraph();
		for(int i = 0; i < vertices.size(); i++) {
			currentVertex = vertices.get(i);
			copyGraph.addVertex(currentVertex);
		}
		for(int i = 0; i < vertices.size(); i++) {
			currentVertex = vertices.get(i);
			TreeMap<Integer, Integer> startAdjacent =graph.getStartpoints().get(currentVertex);
			Set<Integer> set = startAdjacent.keySet();
			java.util.Iterator<Integer> itr = set.iterator();
		    while (itr.hasNext()){
		    	copyGraph.addEdge(currentVertex, itr.next());
		    }
		}
		ArrayList<Integer> copyVertices = copyGraph.getVertices();
		HashMap<Integer, TreeMap<Integer, Integer>> copyStartpoints = copyGraph.getStartpoints();
		Integer startVertexName = null;
		Integer endVertexName = null;
		ArrayList<Operation> changes = new ArrayList<Operation>();
		/*
		 * produce the empty matching (M) as graph without any edges
		 */
		result = new UndirectedGraph();
		for(int i = 0; i < copyVertices.size(); i++) {
			currentVertex = copyVertices.get(i);
			result.addVertex(currentVertex);
		}
		/*
		 * do maximal matching
		 * return arraylist of operations
		 */
		while (copyGraph.containsEdges()) {
			/*
			 * find random edge <startVertexName, endVertexName> in copyGraph (<u,v> in E'):
			 * for all vertices
			 * 		if there is an adjacent vertex
			 * 			choose the corresponding edge
			 * delete this edge in the copyGraph (update E')
			 * memorize this step as an operation 
			 */
			for(int i = 0; i < copyVertices.size(); i++) {
				currentVertex = copyVertices.get(i);
				if (copyStartpoints.containsKey(currentVertex) && !copyStartpoints.get(currentVertex).isEmpty())
				{
					startVertexName = currentVertex;
					endVertexName = copyStartpoints.get(currentVertex).firstKey();
					break;
				}
			}
			copyGraph.deleteEdge(startVertexName, endVertexName);
			changes.add(new EdgeOperation("consider", startVertexName, endVertexName));
			/*
			 * add this edge to the matching / result to be (update M)
			 * memorize this step as an operation
			 */  			
			result.addEdge(startVertexName, endVertexName);
			changes.add(new EdgeOperation("choose", startVertexName, endVertexName));
			/*
			 * delete all edges that are incident:
			 * for all vertices
			 * 		if there is an edge with the start- or the endpoint of the chosen edge
			 * 			delete it
			 */
			for(int i = 0; i < copyVertices.size(); i++) {
				currentVertex = copyVertices.get(i);
				if (copyGraph.containsEdge(startVertexName, currentVertex) || copyGraph.containsEdge(currentVertex, startVertexName)) {
					copyGraph.deleteEdge(startVertexName, currentVertex);
				}
				if (copyGraph.containsEdge(endVertexName, currentVertex) || copyGraph.containsEdge(currentVertex, endVertexName)) {
					copyGraph.deleteEdge(endVertexName, currentVertex);
				}
			}
		}
		return changes;
	}
	
	@Override
	public Graph getResult(Graph graph){
		this.execute(graph);
		return result;
	}

}
