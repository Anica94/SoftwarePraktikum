/**
 * The option being implemented symbolizes an effect on a graph concerning an edge.
 * 
 * @author Sonja
 */
public class EdgeOption implements Option {
	
	/**
	 * String containing the name of the option which can be "add", "delete", "move" in general or "consider", "choose" concerning algorithms.
	 */
	private String optionName;
	/**
	 * Integer that encodes the name of the startpoint of the edge in question.
	 */
	private Integer startVertexName;
	/**
	 * Integer that encodes the name of the endpoint of the edge in question.
	 */
	private Integer endVertexName;

	/**
	 * Produces a new type of option for a specified edge.
	 *  
	 * @param optionName type of which the option shall be
	 * @param startVertexName startpoint of the specified edge
	 * @param endVertexName endpoint of the specified edge
	 */
	public EdgeOption(String optionName, Integer startVertexName, Integer endVertexName) {
		this.optionName = optionName;
		this.startVertexName = startVertexName;
		this.endVertexName = endVertexName;
	}
	
	/**
	 * Returns the endpoint of the edge concerned by the option.
	 * 
	 * @return name of the endpoint of the edge in question
	 */
	public Integer getEndVertexName() {
		return endVertexName;
	}

	@Override
	public String getOptionName() {
		return optionName;
	}

	/**
	 * Returns the startpoint of the edge concerned by the option.
	 * 
	 * @return name of the startpoint of the edge in question
	 */
	public Integer getStartVertexName() {
		return startVertexName;
	}
}
