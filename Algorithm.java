import java.util.ArrayList;

/**
 * Interface of a general algorithm. An algorithm can be executed which delivers a list of all options done by this algorithm 
 * where an option is a step of the algorithm that directly concerns the graph on which the algorithm is performed. 
 * Furthermore, it can be asked for the result which is saved as a graph.
 * 
 * @author Sonja
 */
public interface Algorithm {

	/**
	 * Executes an algorithm on the specified graph and returns a list of the options made by this algorithm. 
	 * 
	 * @param graph on which the algorithm shall be executed. 
	 * 
	 * @return list of options made by the algorithm.
	 * 
	 * @throws NullPointerException if the specified graph is <code> null</code>.
	 */
	public ArrayList<Option> execute(Graph graph);
	
	// TODO NULLPOINTER???????????????
	/**
	 * Executes the algorithm and returns a graph that contains the result of the respective algorithm.
	 * 
	 * @param graph for which the result of the algorithm shall be returned.
	 * 
	 * @return graph that contains the result.
	 */
	public Graph getResult(Graph graph);
}
