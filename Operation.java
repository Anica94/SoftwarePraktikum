/**
 * Interface of a general operation that can influence or change a graph. 
 * Operations are used to ensure a stepwise drawing.
 * They symbolize a function of an algorithm like "consider", "choose" and "not choose" 
 * which means that a considered vertex or edge has not been chosen.
 * Furthermore, it can be "build add" especially to use in the BUILD algorithm. 
 * 
 * @author Sonja
 */
public interface Operation {
	
	/**
	 * Returns the name of an operation. 
	 * It can be "consider", "choose", "not choose" or "build add". 
	 * 
	 * @return name of the operation.
	 */
	public String getOperationName();
	
	/**
	 * Returns the type of an operation. 
	 * It can be "edge" or "vertex". 
	 * 
	 * @return type of the operation.
	 */
	public String getOperationType();

}
