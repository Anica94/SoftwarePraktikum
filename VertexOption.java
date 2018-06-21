
public class VertexOption implements Option {
	
	private String optionName; // can be for general "add", "delete", "move", and for algs "consider", "choose"
	private Integer vertexName;
	
	/**
	 * @param optionName
	 * @param vertexName
	 */
	public VertexOption(String optionName, Integer vertexName) {
		this.optionName = optionName;
		this.vertexName = vertexName;
	}

	@Override
	public String getOptionName() {
		return optionName;
	}

	public Integer getVertexName() {
		return vertexName;
	}
}
