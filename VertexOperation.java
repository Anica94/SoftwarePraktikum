/**
 * The operation being implemented symbolizes an effect on a graph concerning a vertex.
 * 
 * @author Sonja
 */
public class VertexOperation implements Operation {
	
	/**
	 * String containing the name of the operation which can be "consider", "choose" or "not choose"
	 * and stands for steps made by algorithms.
	 */
	private String operationName;
	/**
	 * Integer that encodes the name of the vertex in question.
	 */
	private Integer vertexName;
	
	/**
	 * Produces a new type of operation for a specified vertex.
	 * 
	 * <p>If the name of the operation differs from "consider", "choose" or "not choose"
	 * IllegalArgumentException is thrown.</p>
	 *  
	 * @param operationName type of which the operation shall be
	 * @param vertexName name of the specified vertex
	 * 
	 * @throws IllegalArgumentException if the operationName is wrong.
	 * @throws NullPointerException if the specified vertices is <code> null</code>.
	 */
	public VertexOperation(String operationName, Integer vertexName) {
		if (vertexName == null) {
    		throw new NullPointerException();
    	}
		if (!(operationName.equals("consider") || operationName.equals("choose") || operationName.equals("not choose"))) {
    		throw new IllegalArgumentException();
    	}
		this.operationName = operationName;
		this.vertexName = vertexName;
	}

	@Override
	public String getOperationName() {
		return operationName;
	}

	@Override
	public String getOperationType() {
		return "vertex";
	}
	/**
	 * Returns the name of the vertex concerned by the operation.
	 * 
	 * @return name of the vertex in question
	 */
	public Integer getVertexName() {
		return vertexName;
	}

}
