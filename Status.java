import java.util.Vector;

/**
 * 
 * @author Anica
 *
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
	}
	
	public String getStatus(int number) {
		this.number = number;
		return status.elementAt(number);
	}
	
	public int getStatusNumber() {
		return number;
	}
	
}
