import java.util.ArrayList;

/**
 * @author Anica
 *
 */
public class ConnectedComponents implements Algorithm {
	/**
	 * Saves the result of the algorithm as a graph.
	 */
	private Graph result;
	private Graph resultDFS;
	
	private DFS dfs = new DFS();
	private ArrayList<ArrayList<Integer>> connectedComponents;

	public ConnectedComponents() {
		
		// TODO Auto-generated constructor stub
	}
	
	/* (non-Javadoc)
	 * @see Algorithm#execute(Graph)
	 */
	@Override
	public ArrayList<Operation> execute(Graph graph) {
		connectedComponents = new ArrayList<>();
		if (graph == null) {
    		throw new NullPointerException();
    	}
		if (graph.isEmpty()) {
			throw new IllegalArgumentException();
		}
		
		ArrayList<Operation> changes = new ArrayList<Operation>();
		ArrayList<Operation> currentChanges = new ArrayList<Operation>();
		ArrayList<Integer> verticesConnectedComponent;
		ArrayList<Integer> vertices = graph.getVertices();
		Integer startvertex;
		
		if(graph.typeOfGraph().equals("undirected")) {
			result = new UndirectedGraph();
		}
		else {
			result = new DirectedGraph();
		}
		
		while(!vertices.isEmpty()) {
			startvertex = vertices.get(0);
			dfs = new DFS();
			currentChanges = dfs.execute(graph, startvertex);
			resultDFS = dfs.getResult(graph, startvertex);
			changes.addAll(currentChanges);
			verticesConnectedComponent = new ArrayList<>();
			verticesConnectedComponent = dfs.getVerticesOfConnectedComponent();		
			connectedComponents.add(verticesConnectedComponent);
			result.getStartpoints().putAll(resultDFS.getStartpoints());
			for(int i=0; i<verticesConnectedComponent.size(); i++) {
				vertices.remove(verticesConnectedComponent.get(i));
			}
		}	
		return changes;
	}

	/* (non-Javadoc)
	 * @see Algorithm#getResult(Graph)
	 */
	@Override
	public Graph getResult(Graph graph) {
		this.execute(graph);
		return result;
	}
	
	public ArrayList<ArrayList<Integer>> getConnectedComponents(){
		return connectedComponents;
	}

}
