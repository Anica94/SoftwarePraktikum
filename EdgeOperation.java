/**
 * The operation being implemented symbolizes an effect on a graph concerning an edge.
 * 
 * @author Sonja
 */
public class EdgeOperation implements Operation {
	
	/**
	 * String containing the name of the operation which can be "consider", "choose" or "not choose" 
	 * and stands for steps made by algorithms.
	 */
	private String operationName;
	/**
	 * Integer that encodes the name of the startpoint of the edge in question.
	 */
	private Integer startVertexName;
	/**
	 * Integer that encodes the name of the endpoint of the edge in question.
	 */
	private Integer endVertexName;

	/**
	 * Produces a new type of operation for a specified edge.
	 * 
	 * <p>If the name of the operation differs from "consider", "choose" or "not choose"
	 * IllegalArgumentException is thrown.</p>
	 *  
	 * @param operationName type of which the operation shall be
	 * @param startVertexName startpoint of the specified edge
	 * @param endVertexName endpoint of the specified edge
	 * 
	 * @throws IllegalArgumentException if the operationName is wrong.
	 * @throws NullPointerException if any of the specified vertices is <code> null</code>.
	 */
	public EdgeOperation(String operationName, Integer startVertexName, Integer endVertexName) {
		if (startVertexName == null || endVertexName == null) {
    		throw new NullPointerException();
    	}
		if (!(operationName.equals("consider") || operationName.equals("choose") || operationName.equals("not choose"))) {
    		throw new IllegalArgumentException();
    	}
		this.operationName = operationName;
		this.startVertexName = startVertexName;
		this.endVertexName = endVertexName;
	}
	
	/**
	 * Returns the endpoint of the edge concerned by the operation.
	 * 
	 * @return name of the endpoint of the edge in question
	 */
	public Integer getEndVertexName() {
		return endVertexName;
	}

	@Override
	public String getOperationName() {
		return operationName;
	}
	
	@Override
	public String getOperationType() {
		return "edge";
	}
	
	/**
	 * Returns the startpoint of the edge concerned by the operation.
	 * 
	 * @return name of the startpoint of the edge in question
	 */
	public Integer getStartVertexName() {
		return startVertexName;
	}
}
