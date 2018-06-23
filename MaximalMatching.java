import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

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
	public ArrayList<Option> execute(Graph graph) {
		if (graph == null) {
    		throw new NullPointerException();
    	}
		/*
		 * make copy of the graph to work on (contains E')
		 * produce container of variables to use later
		 * produce arraylist of options that will be done by the algorithm
		 */
		Graph copyGraph = graph;
		ArrayList<Integer> copyVertices = copyGraph.getVertices();
		HashMap<Integer, TreeMap<Integer, Integer>> copyStartpoints = copyGraph.getStartpoints();
		Integer currentVertex;
		Integer startVertexName = null;
		Integer endVertexName = null;
		ArrayList<Option> changes = new ArrayList<Option>();
		/*
		 * produce the empty matching (M) as graph without any edges
		 */
		if(copyGraph.typeOfGraph().equals("undirected")) {
			result = new UndirectedGraph();
		}
		else {
			result = new DirectedGraph();
		}
		for(int i = 0; i < copyVertices.size(); i++) {
			currentVertex = copyVertices.get(i);
			result.addVertex(currentVertex);
		}
		/*
		 * do maximal matching
		 * return arraylist of options
		 */
		while (copyGraph.containsEdges()) {
			/*
			 * find random edge <startVertexName, endVertexName> in copyGraph (<u,v> in E'):
			 * for all vertices
			 * 		if there is an adjacent vertex
			 * 			choose the corresponding edge
			 * delete this edge in the copyGraph (update E')
			 * memorize this step as an option 
			 */
			for(int i = 0; i < copyVertices.size(); i++) {
				currentVertex = copyVertices.get(i);
				if (copyStartpoints.containsKey(currentVertex))
				{
					startVertexName = currentVertex;
					endVertexName = copyStartpoints.get(currentVertex).firstKey();
					break;
				}
			}
			copyGraph.deleteEdge(startVertexName, endVertexName);
			changes.add(new EdgeOption("consider", startVertexName, endVertexName));
			/*
			 * add this edge to the matching / result to be (update M)
			 * memorize this step as an option
			 */  			
			result.addEdge(startVertexName, endVertexName);
			changes.add(new EdgeOption("choose", startVertexName, endVertexName));
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
