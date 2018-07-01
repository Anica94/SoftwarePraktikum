import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.NoSuchElementException;

/**
 * The graph being implemented is undirected meaning that <v,> in E does mean that <u,v> in E. 
 *  
 * @author Sonja
 */
public class UndirectedGraph implements Graph {
	
	/**
	 * Hashmap containing all vertices as keys and their respective incident edges as elements 
	 * organized in a treemap (or red-black-tree) where the endpoints of an edge are the keys 
	 * and the weight of this edge is the respective value. Note all edges are saved twice.
	 */
	protected HashMap<Integer, TreeMap<Integer, Integer>> startpoints;
	
	/**
	 * Saves the highest number a vertex ever had so the default name of a new vertex will be bigger by one.
	 */
	protected Integer highestVertexName;
	
	/**
	 * Produces a new empty undirected graph.
	 */
	public UndirectedGraph() {
		/*
		 * produce hashmap of vertices
		 * produce counter for names
		 */
		startpoints = new HashMap<Integer, TreeMap<Integer, Integer>>();
		highestVertexName = new Integer(0);
	}
	
	/**
	 * Getter for the highest number of a vertex.
	 * 
	 * @return highest number of all vertices.
	 */
	public Integer getHighestVertexName() {
		return highestVertexName;
	}
	
	@Override
	public HashMap<Integer, TreeMap<Integer, Integer>> getStartpoints(){
		return startpoints;
	}

	@Override
	public String typeOfGraph() {
		return "undirected";
	}

	@Override
	public Integer addVertex() {
		/*
		 * produce default name
		 * add vertex
		 * update highest vertex name
		 * return default name
		 */
		Integer vertexNameDefault = highestVertexName+1;
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
		/*
		 * add new vertex with empty adjacent treemap
		 * update highest vertex name
		 */
		startpoints.put(vertexName, new TreeMap<Integer, Integer>());
		if (highestVertexName < vertexName) {
			highestVertexName = vertexName;
		}
		return true;
	}

	@Override
	public boolean containsVertex(Integer vertexName) {
		if (vertexName == null) {
    		throw new NullPointerException();
    	}
		/*
		 * check hashmap for vertex
		 */
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
		if (!startpoints.containsKey(vertexName)) {
			return false;
		}
		/*
		 * iterate through all adjacent vertices of vertexName,
		 * 		in each case remove vertexName from the respective adjacent vertices
		 * remove vertexName
		 */
		TreeMap<Integer, Integer> adjacent = new TreeMap<Integer, Integer>(startpoints.get(vertexName));
		Collection c = adjacent.keySet();
	    Iterator itr = c.iterator();
	    while (itr.hasNext()){
	    	Integer  currentVertex = (Integer) itr.next();
	    	this.deleteEdge(vertexName, currentVertex);
	    }
	    startpoints.remove(vertexName);
		return true;
	}

	@Override
	public ArrayList<Integer> getVertices(){
		/*
		 * produce and return all vertices
		 */ 
		ArrayList<Integer> vertices = new ArrayList<>(startpoints.keySet());
		return vertices;
	}
	
	@Override
	public boolean isAdjacentTo(Integer vertexNameStart, Integer vertexNameEnd) {
		if (vertexNameStart == null || vertexNameEnd == null) {
    		throw new NullPointerException();
    	}
		if (!startpoints.containsKey(vertexNameStart) || !startpoints.containsKey(vertexNameEnd)) {
    		throw new IllegalArgumentException();
    	}
		/*
		 *  check adjacent vertices of vertexNameStart
		 */
		TreeMap<Integer, Integer> adjacent = startpoints.get(vertexNameStart);
		if (adjacent.containsKey(vertexNameEnd))
		{
			return true;
		}
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
		/*
		 * produce default weight
		 * add Edge
		 */
		Integer edgeWeightDefault = new Integer(1);
		return this.addEdge(vertexNameStart, vertexNameEnd, edgeWeightDefault);
	}
	
	@Override
	public boolean addEdge(Integer vertexNameStart, Integer vertexNameEnd, Integer edgeWeight) {
		if (vertexNameStart == null || vertexNameEnd == null || edgeWeight == null) {
    		throw new NullPointerException();
    	}
		if (!startpoints.containsKey(vertexNameStart) || !startpoints.containsKey(vertexNameEnd) || vertexNameStart.equals(vertexNameEnd)) {
    		throw new IllegalArgumentException();
    	}
		if (this.isAdjacentTo(vertexNameStart, vertexNameEnd)) {
			return false;
		}
		/*
		 * add endpoint to adjacent of vertexNameStart
		 */
		TreeMap<Integer, Integer> startAdjacent = startpoints.get(vertexNameStart);
		startAdjacent.put(vertexNameEnd, edgeWeight);
		startpoints.put(vertexNameStart, startAdjacent);
		/*
		 * add startpoint to adjacent of vertexNameEnd
		 */
		TreeMap<Integer, Integer> endAdjacent = startpoints.get(vertexNameEnd);
		endAdjacent.put(vertexNameStart, edgeWeight);
		startpoints.put(vertexNameEnd, endAdjacent);
		return true;
	}

	@Override
	public boolean containsEdge(Integer vertexNameStart, Integer vertexNameEnd) {
		if (vertexNameStart == null || vertexNameEnd == null) {
    		throw new NullPointerException();
    	}
		if (!startpoints.containsKey(vertexNameStart) || !startpoints.containsKey(vertexNameEnd)) {
    		throw new IllegalArgumentException();
    	}
		/*
		 * check if start- and endpoint are adjacent
		 */
		if(this.isAdjacentTo(vertexNameStart, vertexNameEnd)) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean containsEdges() {
		/*
		 * consider any vertex 
		 *  	check if there is any vertex adjacent to the current one
		 */
		ArrayList<Integer> vertices = this.getVertices();
		Integer currentVertex;
		for(int i = 0; i < vertices.size(); i++) {
			currentVertex = vertices.get(i);
			if (startpoints.containsKey(currentVertex) && !startpoints.get(currentVertex).isEmpty())
			{
				return true;
			}
		}
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
		if(!this.containsEdge(vertexNameStart, vertexNameEnd)) {
			return false;
		}
		/*
		 * remove endpoint in adjacent of vertexNameStart
		 */
		TreeMap<Integer, Integer> startAdjacent = startpoints.get(vertexNameStart);
		startAdjacent.remove(vertexNameEnd);
		startpoints.put(vertexNameStart, startAdjacent);
		/*
		 * remove startpoint in adjacent of vertexNameEnd
		 */
		TreeMap<Integer, Integer> endAdjacent = startpoints.get(vertexNameEnd);
		endAdjacent.remove(vertexNameStart);
		startpoints.put(vertexNameEnd, endAdjacent);
		return true;
	}

	@Override
	public Integer getEdgeWeight(Integer vertexNameStart, Integer vertexNameEnd) {
		if (vertexNameStart == null || vertexNameEnd == null) {
    		throw new NullPointerException();
    	}
		if (!startpoints.containsKey(vertexNameStart) || !startpoints.containsKey(vertexNameEnd) || vertexNameStart.equals(vertexNameEnd)) {
    		throw new IllegalArgumentException();
    	}
		if (!this.isAdjacentTo(vertexNameStart, vertexNameEnd)) {
    		throw new NoSuchElementException();
    	}
		/*
		 * find edge
		 * return its weight
		 */
		TreeMap<Integer, Integer> adjacent = startpoints.get(vertexNameStart);
		return adjacent.get(vertexNameEnd);
	}

	@Override
	public void setEdgeWeight(Integer vertexNameStart, Integer vertexNameEnd, Integer edgeWeightNew) {
		if (vertexNameStart == null || vertexNameEnd == null || edgeWeightNew == null) {
    		throw new NullPointerException();
    	}
		if (!startpoints.containsKey(vertexNameStart) || !startpoints.containsKey(vertexNameEnd) || vertexNameStart.equals(vertexNameEnd)) {
    		throw new IllegalArgumentException();
    	}
		if (!this.isAdjacentTo(vertexNameStart, vertexNameEnd)) {
    		throw new NoSuchElementException();
    	}
		/*
		 * find edge in adjacent of vertexNameStart
		 * change its weight
		 */
		TreeMap<Integer, Integer> startAdjacent = startpoints.get(vertexNameStart);
		startAdjacent.put(vertexNameEnd, edgeWeightNew);
		startpoints.put(vertexNameStart, startAdjacent);
		/*
		 * find edge in adjacent of vertexNameEnd
		 * change its weight
		 */
		TreeMap<Integer, Integer> endAdjacent = startpoints.get(vertexNameEnd);
		endAdjacent.put(vertexNameStart, edgeWeightNew);
		startpoints.put(vertexNameEnd, endAdjacent);
	}
	
	@Override
	public Graph copyGraph() {
		Graph copyGraph = new UndirectedGraph();
		
		
		
		return copyGraph;
	}

}

