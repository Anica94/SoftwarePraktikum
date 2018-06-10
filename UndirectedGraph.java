import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.NoSuchElementException;

/*
 * The graph being implemented is undirected meaning that <v,>) in E does mean that <u,v> in E.
 */
public class UndirectedGraph implements Graph {
	
	/*
	 * Hashmap containing all vertices as keys and their respective incident edges as elements 
	 * organized in a treemap (or red-black-tree) where the endpoints of an edge are the keys 
	 * and the weight of this edge is the respective value.
	 */
	private HashMap<Integer, TreeMap<Integer, Integer>> startpoints;
	
	/*
	 * Saves the highest number a vertex ever had so the default name of a new vertex will be bigger by one.
	 */
	private int highestVertexName;
	
	/*
	 * Produces a new empty undirected graph.
	 */
	public UndirectedGraph() {
		startpoints = new HashMap<Integer, TreeMap<Integer, Integer>>();
		highestVertexName = 0;
	}

	@Override
	public void addVertex() {
		Integer vertexNameDefault = new Integer(highestVertexName+1);
		this.addVertex(vertexNameDefault);

	}

	@Override
	public boolean addVertex(Integer vertexName) {
		if (vertexName == null) {
    		throw new NullPointerException();
    	}
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsVertex(Integer vertexName) {
		if (vertexName == null) {
    		throw new NullPointerException();
    	}
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteVertex(Integer vertexName) {
		if (vertexName == null) {
    		throw new NullPointerException();
    	}
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAdjacentTo(Integer vertexNameStart, Integer vertexNameEnd) {
		if (vertexNameStart == null || vertexNameEnd == null) {
    		throw new NullPointerException();
    	}
		if (!startpoints.containsKey(vertexNameStart) || !startpoints.containsKey(vertexNameEnd) || vertexNameStart.equals(vertexNameEnd)) {
    		throw new IllegalArgumentException();
    	}
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addEdge(Integer vertexNameStart, Integer vertexNameEnd) {
		if (vertexNameStart == null || vertexNameEnd == null) {
    		throw new NullPointerException();
    	}
		if (!startpoints.containsKey(vertexNameStart) || !startpoints.containsKey(vertexNameEnd) || vertexNameStart.equals(vertexNameEnd)) {
    		throw new IllegalArgumentException();
    	}
		
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addEdge(Integer vertexNameStart, Integer vertexNameEnd, Integer edgeWeight) {
		if (vertexNameStart == null || vertexNameEnd == null || edgeWeight == null) {
    		throw new NullPointerException();
    	}
		if (!startpoints.containsKey(vertexNameStart) || !startpoints.containsKey(vertexNameEnd) || vertexNameStart.equals(vertexNameEnd)) {
    		throw new IllegalArgumentException();
    	}
		
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsEdge(Integer vertexNameStart, Integer vertexNameEnd) {
		if (vertexNameStart == null || vertexNameEnd == null) {
    		throw new NullPointerException();
    	}
		if (!startpoints.containsKey(vertexNameStart) || !startpoints.containsKey(vertexNameEnd) || vertexNameStart.equals(vertexNameEnd)) {
    		throw new IllegalArgumentException();
    	}
		
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteEdge(Integer vertexNameStart, Integer vertexNameEnd) {
		if (vertexNameStart == null || vertexNameEnd == null) {
    		throw new NullPointerException();
    	}
		if (!startpoints.containsKey(vertexNameStart) || !startpoints.containsKey(vertexNameEnd) || vertexNameStart.equals(vertexNameEnd)) {
    		throw new IllegalArgumentException();
    	}
		
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Integer getEdgeWeight(Integer vertexNameStart, Integer vertexNameEnd) {
		if (vertexNameStart == null || vertexNameEnd == null) {
    		throw new NullPointerException();
    	}
		if (!startpoints.containsKey(vertexNameStart) || !startpoints.containsKey(vertexNameEnd) || vertexNameStart.equals(vertexNameEnd)) {
    		throw new IllegalArgumentException();
    	}
		if (!isAdjacentTo(vertexNameStart, vertexNameEnd)) {
    		throw new NoSuchElementException();
    	}
			
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setEdgeWeight(Integer vertexNameStart, Integer vertexNameEnd, Integer edgeWeightNew) {
		if (vertexNameStart == null || vertexNameEnd == null || edgeWeightNew == null) {
    		throw new NullPointerException();
    	}
		if (!startpoints.containsKey(vertexNameStart) || !startpoints.containsKey(vertexNameEnd) || vertexNameStart.equals(vertexNameEnd)) {
    		throw new IllegalArgumentException();
    	}
		if (!isAdjacentTo(vertexNameStart, vertexNameEnd)) {
    		throw new NoSuchElementException();
    	}
		
		// TODO Auto-generated method stub

	}

}
