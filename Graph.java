/*
 * Interface of a general Graph <tt>G<V, E></tt> contains a set <tt>V</tt> of vertices and a set <tt>E</tt>
 * of edges.  Each edge e=<v, u> in E connects v to u. In this case the vertices are only named by numbers 
 * and the edges are specified by their start- and endpoints.
 */

public interface Graph {
	
	/**
	 * Adds a new vertex with default name (i.e. number) to this graph.
	 */
	void addVertex();
	
	/**
	 * Adds a new vertex with specified name to this graph if not already present. 
	 * If this graph  already contains a vertex with this name, the call
     * leaves this graph unchanged and returns <tt>false</tt>.
     * 
	 * @param vertexName specified name of the vertex to be added to the graph.
	 * 
	 * @return <tt>true</tt> if this graph did not already contain the specified vertex.
	 * 
	 * @throws NullPointerException if the specified name is <code> null</code>.
	 */
	boolean addVertex(int vertexName);
	
	/**
	 * Returns <tt>true</tt> if this graph contains the specified vertex,
	 * i.e. a vertex with this name.
	 * 
	 * @param vertexName name of the vertex whose presence in this graph is to be tested.
	 * 
	 * @return <tt>true</tt> if this graph contains the specified vertex.
	 * 
	 * @throws NullPointerException if the specified name is <code> null</code>.
	 */
	boolean containsVertex(int vertexName);
	
	/**
	 * Deletes the specified vertex from this graph including all its touching
     * edges if present. More formally, if the graph contains a vertex with the name vertexName,
     * the call deletes all edges that are incident to this vertex and then deletes the vertex itself.
     * If no vertex with the specified name is found, the call leaves the graph unchanged.
     * Returns <tt>true</tt> if the graph contained the specified vertex. 
     * (The graph will not contain the specified vertex once the call returns).
     * 
     * @param vertexName name of the vertex to be deleted from this graph, if present. 
     * 
     * @return <code>true</code> if the graph contained the specified vertex;
     * <code>false</code> otherwise.
	 * 
	 * @throws NullPointerException if the specified name is <code> null</code>.
	 */
	boolean deleteVertex(int vertexName);
	
	/**
	 * Returns <tt>true</tt> if both specified vertices are adjacent. More formally, if there exists an
	 * edge (vertexNameStart,vertexNameEnd) in the graph.
	 * 
	 * <p>The start and end vertices must already be contained in this graph.
	 * If they are not found in graph IllegalArgumentException is thrown.
	 * If they are the same IllegalArgumentException is thrown as well.</p>
	 * 
	 * @param vertexNameStart name of the startpoint of an edge to be tested.
	 * @param vertexNameEnd name of the endpoint of an edge to be tested.
	 * 
	 * @return <tt>true</tt> if the specified vertices are adjacent.
	 * 
	 * @throws IllegalArgumentException if start or end vertices are not
     * found in the graph or if start- and endpoint are the same.
     * @throws NullPointerException if any of the specified vertices is <code>
     * null</code>.
	 */
	boolean isAdjacentTo(int vertexNameStart, int vertexNameEnd);
	
	/** Adds a new edge between the vertices vertexNameStart and vertexNameEnd with default weight 1.
	 * If this graph already contains an edge between those vertices, the call leaves this graph unchanged and returns <tt>false</tt>.
     * If the edge was added to the graph, returns <code>true</code>.
	 * 
	 * <p>The start and end vertices must already be contained in this graph.
	 * If they are not found in graph IllegalArgumentException is thrown.
	 * If they are the same IllegalArgumentException is thrown as well.</p>
	 * 
	 * @param vertexNameStart name of the startpoint of the edge to be added.
	 * @param vertexNameEnd name of the endpoint of the edge to be added.
	 * 
	 * @return <tt>true</tt> if this graph did not already contain the specified
     * edge.
     *
     * @throws IllegalArgumentException if start or end vertices are not
     * found in the graph or if start- and endpoint are the same.
     * @throws NullPointerException if any of the specified vertices is <code>
     * null</code>.
	 */
	boolean addEdge(int vertexNameStart, int vertexNameEnd);
	
	/** Adds a new edge between the vertices vertexNameStart and vertexNameEnd with specified weight.
	 * If this graph already contains an edge between those vertices, the call leaves this graph unchanged and returns <tt>false</tt>.
     * If the edge was added to the graph, returns <code>true</code>.
	 * 
	 * <p>The start and end vertices must already be contained in this graph.
	 * If they are not found in graph IllegalArgumentException is thrown.
	 * If they are the same IllegalArgumentException is thrown as well.</p>
	 * 
	 * @param vertexNameStart name of the startpoint of the edge to be added.
	 * @param vertexNameEnd name of the endpoint of the edge to be added.
	 * @param edgeWeight weight of the edge to be added.
	 * 
	 * @return <tt>true</tt> if this graph did not already contain the specified
     * edge.
     *
     * @throws IllegalArgumentException if start or end vertices are not
     * found in the graph or if start- and endpoint are the same.
     * @throws NullPointerException if any of the specified vertices is <code>
     * null</code>.
	 */
	boolean addEdge(int vertexNameStart, int vertexNameEnd, int edgeWeight);
	
	/**
	 * Returns <tt>true</tt> if this graph contains the specified edge between the vertices vertexNameStart and vertexNameEnd.
	 * 
	 * <p>The start and end vertices must already be contained in this graph.
	 * If they are not found in graph IllegalArgumentException is thrown.
	 * If they are the same IllegalArgumentException is thrown as well.</p>
	 * 
	 * @param vertexNameStart name of the startpoint of the edge to be tested.
	 * @param vertexNameEnd name of the endpoint of the edge to be tested.
	 * 
	 * @return <tt>true</tt> if this graph contains the specified edge.
	 *
	 * @throws IllegalArgumentException if start or end vertices are not
     * found in the graph or if start- and endpoint are the same.
	 * @throws NullPointerException if any of the specified vertices is <code> null</code>.
	 */
	boolean containsEdge(int vertexNameStart, int vertexNameEnd);
	
	/**
	 * Deletes the edge specified by their start- and endpoint from this graph. All incident vertices
	 * especially the vertices vertexNameStart and vertexNameEnd will be unchanged.
     * If the specified edge is found, the call leaves the graph unchanged.
     * Returns <tt>true</tt> if the graph contained the specified edge. 
     * (The graph will not contain the specified edge once the call returns).
	 * 
	 * <p>The start and end vertices must already be contained in this graph.
	 * If they are not found in graph IllegalArgumentException is thrown.
	 * If they are the same IllegalArgumentException is thrown as well.</p>
	 * 
	 * @param vertexNameStart name of the startpoint of the edge to be deleted.
	 * @param vertexNameEnd name of the endpoint of the edge to be deleted.
	 * 
	 * @return <code>true</code> if the graph contained the specified edge;
     * <code>false</code> otherwise.
     * 
     * @throws IllegalArgumentException if start or end vertices are not
     * found in the graph or if start- and endpoint are the same.
	 * @throws NullPointerException if any of the specified vertices is <code> null</code>.
	 */
	boolean deleteEdge(int vertexNameStart, int vertexNameEnd);
	
	/**
	 * Returns the weight of the edge specified by their start- and endpoint.
	 *
	 * <p> If the edge does not exist NoSuchElementException is thrown.
	 * The start and end vertices must already be contained in this graph.
	 * If they are not found in graph IllegalArgumentException is thrown.
	 * If they are the same IllegalArgumentException is thrown as well.</p>
	 * 
	 * @param vertexNameStart name of the startpoint of the edge whose weight shall be returned.
	 * @param vertexNameEnd name of the endpoint of the edge whose weight shall be returned.
	 * 
	 * @return weight of the specified edge.
	 * 
	 * @throws NoSuchElementException if the specified edge is not found in the graph.
	 * @throws IllegalArgumentException if start or end vertices are not
     * found in the graph or if start- and endpoint are the same.
	 * @throws NullPointerException if any of the specified vertices is <code> null</code>.
	 */
	int getEdgeWeight(int vertexNameStart, int vertexNameEnd);
	
	/**
	
	 * Changes the weight of the edge specified by their start- and endpoint.
	 * 
	 * <p>If the edge does not exist NoSuchElementException is thrown.
	 * The start and end vertices must already be contained in this graph.
	 * If they are not found in graph IllegalArgumentException is thrown.
	 * If they are the same IllegalArgumentException is thrown as well.</p>
	 * 
	 * @param vertexNameStart name of the startpoint of the edge whose weight shall be changed.
	 * @param vertexNameEnd name of the endpoint of the edge whose weight shall be changed.
	 * @param newEdgeWeight weight that the edge shall get.
	 * 
	 * @throws NoSuchElementException if the specified edge is not found in the graph.
	 * @throws IllegalArgumentException if start or end vertices are not
     * found in the graph or if start- and endpoint are the same.
	 * @throws NullPointerException if any of the specified vertices or the new weight is <code> null</code>.
	 */
	void setEdgeWeight(int vertexNameStart, int vertexNameEnd, int newEdgeWeight);
	
}
