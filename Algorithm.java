import java.util.ArrayList;

/**
 * Interface of a general algorithm. An algorithm can be executed which delivers a list of all operations done by this algorithm 
 * where an operation is a step of the algorithm that directly concerns the graph on which the algorithm is performed. 
 * Furthermore, it can be asked for the result which is saved as a graph.
 * 
 * @author Sonja
 */
public interface Algorithm {

	/**
	 * Executes an algorithm on the specified graph and returns a list of the operations made by this algorithm. 
	 * 
	 * @param graph on which the algorithm shall be executed. 
	 * 
	 * @return list of operations made by the algorithm.
	 * 
	 * @throws NullPointerException if the specified graph is <code> null</code>.
	 */
	public ArrayList<Operation> execute(Graph graph);
	
	/**
	 * Executes the algorithm and returns a graph that contains the result of the respective algorithm.
	 * 
	 * @param graph for which the result of the algorithm shall be returned.
	 * 
	 * @return graph that contains the result.
	 */
	public Graph getResult(Graph graph);
}
