import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;
import junit.framework.TestCase;

/**
 * 
 * 
 * @author Sonja
 *
 */
public class TestReaderBUILD extends TestCase{

	private ReaderBUILD reader = new ReaderBUILD();
	private boolean success;
	private ArrayList<Integer> leaves;
	private String leavesPrint;
	private ArrayList<Pair<Pair<Integer, Integer>, Integer>> triples;
	private String triplesPrint;
	

	private boolean containsTriple(ArrayList<Pair<Pair<Integer, Integer>, Integer>> tripleset, Integer left, Integer middle, Integer right) {
		Pair<Integer,Integer> currentLeftside;
		for(Pair<Pair<Integer, Integer>, Integer> t : tripleset) {
			if(t.getSecond().intValue() == right) {
				currentLeftside = t.getFirst();
				if(currentLeftside.getFirst().intValue() == left) {
					if(currentLeftside.getSecond().intValue() == middle) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	@Test
	public void testEmptyConstructor() {

		assertNull("Leafset should be null after call to constructor.", reader.getLeafset());
		assertNull("Tripleset should be null after call to constructor.", reader.getTripleset());
	}
	 
	@Test
	public void test() {
		/*
		 * test empty file
		 */	
		try {
			reader.read("C:\\Users\\Sonja\\eclipse-workspace\\SoftwarePraktikum\\src\\Textfiles\\Test\\RemptyFile.txt");
		} catch (IOException e) {
			assertTrue("IOException was thrown.", true);
		}
		/*
		 * test wrong format
		 */
		try {
			reader.read("C:\\Users\\Sonja\\eclipse-workspace\\SoftwarePraktikum\\src\\Textfiles\\Test\\RBwrongFormat.txt");
		} catch (IOException e) {
			assertTrue("IOException was thrown.", true);
		}
		try {
			reader.read("C:\\Users\\Sonja\\eclipse-workspace\\SoftwarePraktikum\\src\\Textfiles\\Test\\Rwrong2.txt");
		} catch (IOException e) {
			assertTrue("IOException was thrown.", true);
		}try {
			reader.read("C:\\Users\\Sonja\\eclipse-workspace\\SoftwarePraktikum\\src\\Textfiles\\Test\\Rwrong3.txt");
		} catch (IOException e) {
			assertTrue("IOException was thrown.", true);
		}
		/*
		 * test empty leafset
		 */
		try {
			reader.read("C:\\Users\\Sonja\\eclipse-workspace\\SoftwarePraktikum\\src\\Textfiles\\Test\\RBemptyLeafset.txt");
		} catch (IOException e) {
			assertFalse("IOException was thrown but not expected.", false);
		}
		leaves = reader.getLeafset();
		success = leaves.size() == 0;
		assertTrue("reader.read(textfile) creates no empty leafset although it was stated empty.", success);
		/*
		 * test empty tripleset
		 */
		try {
			reader.read("C:\\Users\\Sonja\\eclipse-workspace\\SoftwarePraktikum\\src\\Textfiles\\Test\\RBemptyTripleset.txt");
		} catch (IOException e) {
			assertFalse("IOException was thrown but not expected.", false);
		}
		triples= reader.getTripleset();
		success = triples.size() == 0;
		assertTrue("reader.read(textfile) creates no empty tripleset although it was stated empty.", success);
		/*
		 * test right input
		 */
		try {
			reader.read("C:\\Users\\Sonja\\eclipse-workspace\\SoftwarePraktikum\\src\\Textfiles\\Test\\RBright.txt");
		} catch (IOException e) {
			assertFalse("IOException was thrown but not expected.", false);
		}
		/*
		 *  check leaves
		 */
		leaves = reader.getLeafset();
		success = leaves.size() == 4;
		assertTrue("reader.read(textfile) creates the wrong number of leaves.", success);
		success = leaves.contains(new Integer (1));
		assertTrue("reader.read(textfile) saves a wrong leaf.", success);
		success = leaves.contains(new Integer (2));
		assertTrue("reader.read(textfile) saves a wrong leaf.", success);
		success = leaves.contains(new Integer (4));
		assertTrue("reader.read(textfile) saves a wrong leaf.", success);
		success = leaves.contains(new Integer (5));
		assertTrue("reader.read(textfile) saves a wrong leaf.", success);
		leavesPrint = reader.getLeafsetPrint();
		success = leavesPrint.equals("L = {1, 2, 5, 4}");
		assertTrue("reader.read(textfile) prints a wrong leafset.", success);
		/*
		 * 	check triples
		 */
		triples= reader.getTripleset();
		success = triples.size() == 2;
		assertTrue("reader.read(textfile) creates the wrong number of triples.", success);
		success = containsTriple(triples, new Integer(1), new Integer(2),new Integer(5));
		assertTrue("reader.read(textfile) saves a wrong triple.", success);
		success = containsTriple(triples, new Integer(1), new Integer(2),new Integer(4));
		assertTrue("reader.read(textfile) saves a wrong triple.", success);
		triplesPrint = reader.getTriplesetPrint();
		success = triplesPrint.equals("R = {((1,2),5), ((1,2),4)}");
		assertTrue("reader.read(textfile) prints a wrong tripleset.", success);
	}

}

