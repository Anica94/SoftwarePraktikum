import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.NoSuchElementException;

/**
 * The graph being implemented is directed meaning that <v,u> in E does not necessarily mean that <u,v> in E.
 * Now, the hashmap startpoints, inherited from UndirectedGraph, maintains all outgoing edges.
 * 
 * @author Sonja
 */
public class DirectedGraph extends UndirectedGraph implements Graph {
	/**
	 * Hashmap containing all vertices as keys and their respective incident edges as elements 
	 * organized in a treemap (or red-black-tree) where the startpoints of an edge are the keys 
	 * and the weight of this edge is the respective value.
	 * To be exact, this hashmap maintains all ingoing edges.
	 */
	private HashMap<Integer, TreeMap<Integer, Integer>> endpoints;
	
	/**
	 * Produces a new empty directed graph.
	 */
	public DirectedGraph() {
		/*
		 * produce hashmap of vertices (outgoing edges)
		 * produce hashmap of vertices (ingoing edges)
		 * produce counter for names
		 */
		startpoints = new HashMap<Integer, TreeMap<Integer, Integer>>();
		endpoints = new HashMap<Integer, TreeMap<Integer, Integer>>();
		highestVertexName = new Integer(0);
	}
	
	/**
	 * Getter for the hashmap of all vertices and their respective ingoing edges.
	 * 
	 * @return hashmap of all vertices and their respective edges.
	 */
	public HashMap<Integer, TreeMap<Integer, Integer>> getEndpoints(){
		return endpoints;
	}
	
	@Override
	public String typeOfGraph() {
		return "directed";
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
		 * add new vertex with empty adjacent treemap for outgoing
		 * add new vertex with empty adjacent treemap for ingoing
		 * update highest vertex name
		 */
		startpoints.put(vertexName, new TreeMap<Integer, Integer>());
		endpoints.put(vertexName, new TreeMap<Integer, Integer>());
		if (highestVertexName < vertexName) {
			highestVertexName = vertexName;
		}
		return true;
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
		 * iterate through all adjacent vertices of vertexName for outgoing,
		 * 		in each case remove vertexName from the respective adjacent vertices
		 * remove vertexName
		 */
		TreeMap<Integer, Integer> adjacent = startpoints.get(vertexName);
		Collection c = adjacent.values();
	    Iterator itr = c.iterator();
	    while (itr.hasNext()){
	    	Integer  currentVertex = (Integer) itr.next();
	    	this.deleteEdge(vertexName, currentVertex); ////evtl Fehler wegen plötzlich wird "c" verändert (beide Richtungen gelöscht --- abwarten
	    }
	    startpoints.remove(vertexName);
	    /* 
	     * iterate through all adjacent vertices of vertexName for ingoing,
		 * 		in each case remove vertexName from the respective adjacent vertices
		 * remove vertexName
		 */
		adjacent = endpoints.get(vertexName);
		c = adjacent.values();
	    itr = c.iterator();
	    while (itr.hasNext()){
	    	Integer  currentVertex = (Integer) itr.next();
	    	this.deleteEdge(vertexName, currentVertex); ////evtl Fehler wegen plötzlich wird "c" verändert (beide Richtungen gelöscht --- abwarten
	    }
	    endpoints.remove(vertexName);
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
		/*
		 *  check adjacent vertices of vertexNameStart (outgoing)
		 */
		TreeMap<Integer, Integer> adjacent = startpoints.get(vertexNameStart);
		if (adjacent.containsKey(vertexNameEnd))
		{
			return true;
		}
		/*
		 *  check adjacent vertices of vertexNameStart (ingoing)
		 */
		adjacent = endpoints.get(vertexNameStart);
		if (adjacent.containsKey(vertexNameEnd))
		{
			return true;
		}
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
		if (this.isAdjacentTo(vertexNameStart, vertexNameEnd)) {
			return false;
		}
		/*
		 * add endpoint to adjacent of vertexNameStart (outgoing)
		 */
		TreeMap<Integer, Integer> startAdjacent = startpoints.get(vertexNameStart);
		startAdjacent.put(vertexNameEnd, edgeWeight);
		startpoints.put(vertexNameStart, startAdjacent);
		/*
		 * add startpoint to adjacent of vertexNameEnd (ingoing)
		 */
		TreeMap<Integer, Integer> endAdjacent = endpoints.get(vertexNameEnd);
		endAdjacent.put(vertexNameStart, edgeWeight);
		endpoints.put(vertexNameEnd, endAdjacent);
		return true;
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
		 * remove endpoint in adjacent of vertexNameStart (outgoing)
		 */
		TreeMap<Integer, Integer> startAdjacent = startpoints.get(vertexNameStart);
		startAdjacent.remove(vertexNameEnd);
		startpoints.put(vertexNameStart, startAdjacent);
		/*
		 * remove startpoint in adjacent of vertexNameEnd (ingoing)
		 */
		TreeMap<Integer, Integer> endAdjacent = endpoints.get(vertexNameEnd);
		endAdjacent.remove(vertexNameStart);
		endpoints.put(vertexNameEnd, endAdjacent);
		return true;
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
		 * find edge in adjacent of vertexNameStart (outgoing)
		 * change its weight
		 */
		TreeMap<Integer, Integer> startAdjacent = startpoints.get(vertexNameStart);
		startAdjacent.put(vertexNameEnd, edgeWeightNew);
		startpoints.put(vertexNameStart, startAdjacent);
		/*
		 * find edge in adjacent of vertexNameEnd (ingoing)
		 * change its weight
		 */
		TreeMap<Integer, Integer> endAdjacent = endpoints.get(vertexNameEnd);
		endAdjacent.put(vertexNameStart, edgeWeightNew);
		endpoints.put(vertexNameEnd, endAdjacent);
	}
}
