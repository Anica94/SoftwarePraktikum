import java.util.ArrayList;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;

/**
 * This algorithm computes the depth first search (DFS) 
 * for finding a connected component of an undirected graph.
 * 
 * @author Anica
 *
 */
public class DFS implements Algorithm {
	/**
	 * Saves the result of the algorithm as a graph.
	 */
	private Graph result;
	
	/**
	 * Container for the vertices of the connected component containing the startvertex.
	 */
	private ArrayList<Integer> verticesConnectedComponent;
	
	/**
	 * Produces an algorithm for DFS with an empty result.
	 */
	public DFS() {
		result = null;
	}

	/**
	 * Runs DFS with a dafault startvertex (the first vertex in the list of vertices).
	 */
	@Override
	public ArrayList<Operation> execute(Graph graph) {
		if (graph == null) {
    		throw new NullPointerException();
    	}
		if (graph.isEmpty()) {
			throw new IllegalArgumentException();
		}

		ArrayList<Integer> vertices = graph.getVertices();
		Integer startvertex = vertices.get(0);
		
		return execute(graph, startvertex);
	}
	
	/**
	 * Runs the DFS for a specified startvertex.
	 * @param graph
	 * @param startvertex
	 * @return list of operations
	 * 
	 * @throws IllegalArgumentException if the startvertex is not contained in the graph. 
	 */
	public ArrayList<Operation> execute(Graph graph, Integer startvertex) {
		if (graph == null) {
    		throw new NullPointerException();
    	}
		if (graph.isEmpty()) {
			throw new IllegalArgumentException();
		}
		
		if(!graph.getStartpoints().containsKey(startvertex)) {
			throw new IllegalArgumentException();
		}
		
		/*
		 * produce arraylist of operations that will be done by the algorithm
		 * produce containers of variables to use later
		 */	
		ArrayList<Operation> changes = new ArrayList<Operation>();
		Integer v, u;
		verticesConnectedComponent = new ArrayList<>();
		TreeMap<Integer, Integer> startAdjacent;
		Set<Integer> set;
		/*
		 * produce an empty graph that will contain the result
		 */
		if(graph.typeOfGraph().equals("undirected")) {
			result = new UndirectedGraph();
		}
		else {
			result = new DirectedGraph();
		}

		/*
		 * run the dfs:
		 * 	add the startvertex to the stack
		 * 	while stack is not empty
		 * 		pop vertex v from stack
		 * 		if v is not already contained in the list of vertices of the con. comp.
		 * 			add v to the list and to the resulting graph
		 * 			memorize this step as an operation
		 * 			add all adjacent vertices u of v to the stack and edges <v,u> to the resulting graph
		 * 				(if there is an edge <u,v> in the graph)
		 * return the list of operations
		 */
		Stack<Integer> stack = new Stack<>();
		stack.push(startvertex);
		while(!stack.isEmpty()) {
			v = stack.pop();			
			if(!verticesConnectedComponent.contains(v)) {
				verticesConnectedComponent.add(v);
				result.addVertex(v);
				changes.add(new VertexOperation("consider", v));
				changes.add(new VertexOperation("choose", v));
				startAdjacent =graph.getStartpoints().get(v);
				set = startAdjacent.keySet();
				java.util.Iterator<Integer> itr = set.iterator();
			    while (itr.hasNext()){
			    	u = itr.next();
			    	stack.push(u);
			    	if(verticesConnectedComponent.contains(u)) {
			    		result.addEdge(v, u);
			    	}
			    }
			}
		}		
		return changes;
	}

	@Override
	public Graph getResult(Graph graph) {
		this.execute(graph);
		return result;
	}
	
	/**
	 * Returns the result for running DFS with a given startvertex.
	 * @param graph
	 * @param startvertex
	 * @return result
	 */
	public Graph getResult(Graph graph, Integer startvertex) {
		this.execute(graph, startvertex);
		return result;
	}
	
	/**
	 * Getter for the list of vertices of the connected component containing the startvertex.
	 * @return list of vertices of the connected component containing the startvertex.
	 */
	public ArrayList<Integer> getVerticesOfConnectedComponent(){
		return verticesConnectedComponent;
	}
}

