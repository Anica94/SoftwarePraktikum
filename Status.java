import java.util.Vector;

public class Status {
	private Vector<String> status = new Vector<String>();
	private int number;
	
	Status(){
		number = 0;
		
		//0
		status.add("Status");
		//1						
		status.add("Runs Breadth-First Search");
		//2
		status.add("Runs Depth-First Search");		
		//3
		status.add("Runs Topological Sort");		
		//4
		status.add("Runs Minimum Spanning Tree");	
		//5
		status.add("Runs Maximal Matching");
		//6
		status.add("Runs BUILD");
		//7
		status.add("Add Vertex");
		//8
		status.add("Add Edge");
		//9
		status.addElement("Add Edge");
		//10
		status.add("Delete");
		//11
		status.add("Move");
		//12
		status.add("Open file");
		//13
		status.add("Save file");
	}
	
	public String getStatus(int number) {
		this.number = number;
		return status.elementAt(number);
	}
	
	public int getStatusNumber() {
		return number;
	}
	
}
