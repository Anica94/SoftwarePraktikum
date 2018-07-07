import java.util.ArrayList;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;

/**
 * 
 * @author Anica
 *
 */
public class DFS implements Algorithm {
	/**
	 * Saves the result of the algorithm as a graph.
	 */
	private Graph result;
	
	private ArrayList<Integer> verticesConnectedComponent;
	
	/**
	 * Produces an algorithm for DFS with an empty result.
	 */
	public DFS() {
		result = null;
	}

	@Override
	public ArrayList<Operation> execute(Graph graph) {
		if (graph == null) {
    		throw new NullPointerException();
    	}

		ArrayList<Integer> vertices = graph.getVertices();
		Integer startvertex = vertices.get(0);
		
		return execute(graph, startvertex);
	}
	
	public ArrayList<Operation> execute(Graph graph, Integer startvertex) {
		if (graph == null) {
    		throw new NullPointerException();
    	}
		
		ArrayList<Operation> changes = new ArrayList<Operation>();
		ArrayList<Integer> vertices = graph.getVertices();
		Integer v, u;
		verticesConnectedComponent = new ArrayList<>();
		TreeMap<Integer, Integer> startAdjacent;
		Set<Integer> set;
		if(graph.typeOfGraph().equals("undirected")) {
			result = new UndirectedGraph();
		}
		else {
			result = new DirectedGraph();
		}

		Stack<Integer> stack = new Stack<>();
		stack.push(startvertex);
		while(!stack.isEmpty()) {
			v = stack.pop();
			changes.add(new VertexOperation("consider", v));
			changes.add(new VertexOperation("choose", v));
			if(!verticesConnectedComponent.contains(v)) {
				verticesConnectedComponent.add(v);
				result.addVertex(v);
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
	
	public ArrayList<Integer> getVerticesOfConnectedComponent(){
		return verticesConnectedComponent;
	}

}
