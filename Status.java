import java.util.Vector;

/**
 * This is a class that contains a list of possible statuses of the software:
 * "Status", meaning that no other specific status is chosen, "runs Breadth-First Search", 
 * "runs Depth-First Search", "runs Find Connected Components", "runs Topological Sort", 
 * "runs Minimum Spanning Tree", "runs Maximal Matching" and "runs BUILD" for the 
 * respective algorithms, "add vertex", "add edge", "delete vertex", "delete edge" and 
 * "move" for editing the graph with the respective operation, "open file" and "save file" 
 * for opening or saving a file, "Wrong file. See documentation for more information." and 
 * "Wrong input. See documentation for more information." for warnings.
 * 
 * @author Anica
 */

public class Status {
	private Vector<String> status = new Vector<String>();
	private int number;
	
	Status(){
		number = 0;
		
		//0
		status.add("Status");
		//1						
		status.add("runs Breadth-First Search");
		//2
		status.add("runs Depth-First Search");		
		//3
		status.add("runs Topological Sort");		
		//4
		status.add("runs Minimum Spanning Tree");	
		//5
		status.add("runs Maximal Matching");
		//6
		status.add("runs BUILD");
		//7
		status.add("add vertex");
		//8
		status.add("add edge");
		//9
		status.addElement("add edge");
		//10
		status.add("delete vertex");
		//11
		status.add("delete edge");
		//12
		status.add("move");
		//13
		status.add("open file");
		//14
		status.add("save file");
		//15
		status.add("runs Find Connected Components");
		//16
		status.add("Wrong file. See documentation for more information.");
		//17
		status.add("Wrong input. See documentation for more information.");
	}
	
	/**
	 * Getter fot the current status.
	 * @param number number of the current status.
	 * @return current status.
	 */
	public String getStatus(int number) {
		this.number = number;
		return status.elementAt(number);
	}
	
	/**
	 * Getter for the current status number.
	 * @return current status number.
	 */
	public int getStatusNumber() {
		return number;
	}
	
}
