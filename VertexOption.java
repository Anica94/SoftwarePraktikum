/**
 * The option being implemented symbolizes an effect on a graph concerning a vertex.
 * 
 * @author Sonja
 */
public class VertexOption implements Option { // TODO besserer Name: Operation ?!!
	
	/**
	 * String containing the name of the option which can be "add", "delete", "move" in general or "consider", "choose" concerning algorithms.
	 */
	private String optionName;
	/**
	 * Integer that encodes the name of the vertex in question.
	 */
	private Integer vertexName;
	
	/**
	 * Produces a new type of option for a specified vertex.
	 *  
	 * @param optionName type of which the option shall be
	 * @param vertexName name of the specified vertex
	 */
	public VertexOption(String optionName, Integer vertexName) {
		this.optionName = optionName;
		this.vertexName = vertexName;
	}

	@Override
	public String getOptionName() {
		return optionName;
	}

	/**
	 * Returns the name of the vertex concerned by the option.
	 * 
	 * @return name of the vertex in question
	 */
	public Integer getVertexName() {
		return vertexName;
	}
}
