import java.util.ArrayList;

/**
 * This algorithm finds the connected components of a given graph.
 * 
 * @author Anica
 */
public class ConnectedComponents implements Algorithm {
	/**
	 * Saves the result of the algorithm as a graph.
	 */
	private Graph result;
	/**
	 * Container for the result of the DFS.
	 */
	private Graph resultDFS;
	
	/**
	 * Container for the algorithm DFS.
	 */
	private DFS dfs = new DFS();
	/**
	 * Container for the connected components.
	 */
	private ArrayList<ArrayList<Integer>> connectedComponents;

	/**
	 * Produces an algorithm for ConnectedComponents with an empty result.
	 */
	public ConnectedComponents() {
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
		 * produce arraylist of operations that will be done by the algorithm
		 * produce containers of variables to use later
		 */	
		ArrayList<Operation> changes = new ArrayList<Operation>();
		connectedComponents = new ArrayList<>();
		ArrayList<Operation> currentChanges = new ArrayList<Operation>();
		ArrayList<Integer> verticesConnectedComponent;
		ArrayList<Integer> vertices = graph.getVertices();
		Integer startvertex;
		
		/*
		 * produce an empty graph that will contain the result
		 * the result will be a graph containing all connected components 
		 * (which is the graph itself)
		 */
		if(graph.typeOfGraph().equals("undirected")) {
			result = new UndirectedGraph();
		}
		else {
			result = new DirectedGraph();
		}
		
		/*
		 * find connected components:
		 * 	while the list of vertices is not empty
		 * 		run DFS for the first element of the list <tt>vertices<tt>
		 * 		add the changes made by the DFS to this changes
		 * 		add the result of DFS to the resulting graph
		 * 		add the list of vertices of the connected component found by 
		 * 			DFS as a new element to the list of connected components
		 * 		remove all vertices of the connected component found by DFS
		 * 			of the list of vertices
		 * 
		 * return the list of operations
		 */
		while(!vertices.isEmpty()) {
			startvertex = vertices.get(0);
			dfs = new DFS();
			currentChanges = dfs.execute(graph, startvertex);
			changes.addAll(currentChanges);
			resultDFS = dfs.getResult(graph, startvertex);
			result.getStartpoints().putAll(resultDFS.getStartpoints());
			verticesConnectedComponent = new ArrayList<>();
			verticesConnectedComponent = dfs.getVerticesOfConnectedComponent();		
			connectedComponents.add(verticesConnectedComponent);
			for(int i=0; i<verticesConnectedComponent.size(); i++) {
				vertices.remove(verticesConnectedComponent.get(i));
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
	 * Getter for the list of connected components (with a list of vertices of each connected component).
	 * @return list of connected components.
	 */
	public ArrayList<ArrayList<Integer>> getConnectedComponents(){
		return connectedComponents;
	}

}
