/**
 * Interface of a general option that can influence or change a graph. 
 * Options are used to ensure a stepwise drawing.
 * There are two types of options: 
 * Those that have a general meaning like "add", "delete" and "move" 
 * and those who symbolize a function of an algorithm like "consider" and "choose".
 * 
 * @author Sonja
 */
public interface Option {
	
	// TODO "READ" OR "SAVE"???????????????????????
	/**
	 * Returns the name of an option. Concerning general functions of this software it can be "add", "delete" or "move".
	 * Regarding algorithms it can be "consider" or "choose". 
	 * 
	 * @return name of the option.
	 */
	public String getOptionName();

}
