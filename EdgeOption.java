
public class EdgeOption implements Option {
	
	private String optionName; // can be for general "add", "delete", "move", and for algs "consider", "choose" 
	private Integer startVertexName;
	private Integer endVertexName;

	/**
	 * @param optionName
	 * @param startVertexName
	 * @param endVertexName
	 */
	public EdgeOption(String optionName, Integer startVertexName, Integer endVertexName) {
		this.optionName = optionName;
		this.startVertexName = startVertexName;
		this.endVertexName = endVertexName;
	}
	
	public Integer getEndVertexName() {
		return endVertexName;
	}

	@Override
	public String getOptionName() {
		return optionName;
	}

	public Integer getStartVertexName() {
		return startVertexName;
	}
}
