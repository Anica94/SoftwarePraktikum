import java.util.HashMap;
import java.util.TreeMap;

/*
 * The graph being implemented is directed meaning that <v,u> in E does not necessarily mean that <u,v> in E.
 */
public class DirectedGraph implements Graph {
	
	/*
	 * Hashmap containing all vertices as keys and their respective incident edges as elements 
	 * organized in a treemap (or red-black-tree) where the endpoints of an edge are the keys 
	 * and the weight of this edge is the respective value. 
	 * To be exact, this hashmap maintains all outgoing edes.
	 */
	private HashMap<Integer, TreeMap<Integer, Integer>> startpoints;
	
	/*
	 * Hashmap containing all vertices as keys and their respective incident edges as elements 
	 * organized in a treemap (or red-black-tree) where the startpoints of an edge are the keys 
	 * and the weight of this edge is the respective value.
	 * To be exact, this hashmap maintains all ingoing edges.
	 */
	private HashMap<Integer, TreeMap<Integer, Integer>> endpoints;
	/*
	 * Saves the highest number a vertex ever had so the default name of a new vertex will be bigger by one.
	 */
	private int highestVertexName;
	
	/*
	 * Produces a new empty directed graph.
	 */
	public DirectedGraph() {
		startpoints = new HashMap<Integer, TreeMap<Integer, Integer>>();
		endpoints = new HashMap<Integer, TreeMap<Integer, Integer>>();
		highestVertexName = 0;
	}

	@Override
	public Integer addVertex() {
		Integer vertexNameDefault = new Integer(highestVertexName+1);
		this.addVertex(vertexNameDefault);
		return vertexNameDefault;
	}

	@Override
	public boolean addVertex(Integer vertexName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsVertex(Integer vertexName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteVertex(Integer vertexName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAdjacentTo(Integer vertexNameStart, Integer vertexNameEnd) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addEdge(Integer vertexNameStart, Integer vertexNameEnd) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addEdge(Integer vertexNameStart, Integer vertexNameEnd, Integer edgeWeight) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsEdge(Integer vertexNameStart, Integer vertexNameEnd) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteEdge(Integer vertexNameStart, Integer vertexNameEnd) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Integer getEdgeWeight(Integer vertexNameStart, Integer vertexNameEnd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setEdgeWeight(Integer vertexNameStart, Integer vertexNameEnd, Integer edgeWeightNew) {
		// TODO Auto-generated method stub

	}

}

