import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

/*
 * The graph being implemented is directed meaning that <v,u> in E does not necessarily mean that <u,v> in E.
 * 
 * @author Sonja
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
		/*
		 * produce hashmap of vertices (outgoing edges)
		 * produce hashmap of vertices (ingoing edges)
		 * produce counter for names
		 */
		startpoints = new HashMap<Integer, TreeMap<Integer, Integer>>();
		endpoints = new HashMap<Integer, TreeMap<Integer, Integer>>();
		highestVertexName = 0;
	}

	@Override
	public Integer addVertex() {
		/*
		 * produce default name
		 * add vertex
		 * return default name
		 */
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
		/*
		 * add new vertex with empty adjacent treemap for outgoing
		 * add new vertex with empty adjacent treemap for ingoing
		 */
		startpoints.put(vertexName, new TreeMap<Integer, Integer>());
		endpoints.put(vertexName, new TreeMap<Integer, Integer>());
		return true;
	}

	@Override
	public boolean containsVertex(Integer vertexName) {
		if (vertexName == null) {
    		throw new NullPointerException();
    	}
		/*
		 * check outgoing hashmap for vertex
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
		/* iterate through all adjacent vertices of vertexName for outgoing,
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
	    /* iterate through all adjacent vertices of vertexName for ingoing,
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
