import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.NoSuchElementException;


/*
 * The graph being implemented is undirected meaning that <v,> in E does mean that <u,v> in E.
 */
public class UndirectedGraph implements Graph {
	
	/*
	 * Hashmap containing all vertices as keys and their respective incident edges as elements 
	 * organized in a treemap (or red-black-tree) where the endpoints of an edge are the keys 
	 * and the weight of this edge is the respective value. Note all edges are saved twice.
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
	public Integer addVertex() {
		Integer vertexNameDefault = new Integer(highestVertexName+1);
		this.addVertex(vertexNameDefault);
		return vertexNameDefault;
	}

	@Override
	public boolean addVertex(Integer vertexName) {
		if (vertexName == null) {
    		throw new NullPointerException();
    	}
		if (startpoints.containsKey(vertexName)) {
			return false;
		}
		startpoints.put(vertexName, new TreeMap<Integer, Integer>());
		return true;
	}

	@Override
	public boolean containsVertex(Integer vertexName) {
		if (vertexName == null) {
    		throw new NullPointerException();
    	}
		if (startpoints.containsKey(vertexName)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteVertex(Integer vertexName) {
		if (vertexName == null) {
    		throw new NullPointerException();
    	}
		if (startpoints.containsKey(vertexName)) {
			return false;
		}
		/* iterate through all adjacent vertices of vertexName, 
		 * 		in each case remove vertexName from the respective adjacent vertices, 
		 * remove vertexName
		 */
		TreeMap<Integer, Integer> adjacent = startpoints.get(vertexName);
		Collection c = adjacent.values();
	    Iterator itr = c.iterator();

	    while (itr.hasNext()){
	    	Integer  currentVertex = (Integer) itr.next();
	    	TreeMap<Integer, Integer> currentAdjacent = startpoints.get(currentVertex);
	    	currentAdjacent.remove(vertexName);
	    	startpoints.put(currentVertex, currentAdjacent);
	    }
	    startpoints.remove(vertexName);
		return true;
	}

	@Override
	public boolean isAdjacentTo(Integer vertexNameStart, Integer vertexNameEnd) {
		if (vertexNameStart == null || vertexNameEnd == null) {
    		throw new NullPointerException();
    	}
		if (!startpoints.containsKey(vertexNameStart) || !startpoints.containsKey(vertexNameEnd) || vertexNameStart.equals(vertexNameEnd)) {
    		throw new IllegalArgumentException();
    	}
		// check adjacent vertices of vertexNameStart
		TreeMap<Integer, Integer> adjacent = startpoints.get(vertexNameStart);
		if (adjacent.containsKey(vertexNameEnd))
		{
			return true;
		}
		return false;
	}
// TODO ///////////
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

