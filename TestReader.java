import java.io.IOException;

public class TestReader {

	public static void main(String[] args) throws IOException {
		Reader reader = new Reader();
		reader.read("C:\\Users\\Sonja\\eclipse-workspace\\SoftwarePraktikum\\src\\Textfiles\\FileTestDirected.txt");
		System.out.println(""+reader.typeOfGraph());
//		reader.read("C:\\Users\\Sonja\\eclipse-workspace\\SoftwarePraktikum\\src\\Textfiles\\FileTestDirectedFalsch.txt");
//		System.out.println(""+reader.typeOfGraph());
		reader.read("C:\\Users\\Sonja\\eclipse-workspace\\SoftwarePraktikum\\src\\Textfiles\\\\FileTestUndirected.txt");
		System.out.println(""+reader.typeOfGraph());
//		reader.read("C:\\Users\\Sonja\\eclipse-workspace\\SoftwarePraktikum\\src\\Textfiles\\FileTestUndirectedFalsch.txt");
//		System.out.println(""+reader.typeOfGraph());
	}

}
