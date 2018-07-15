import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;
import junit.framework.TestCase;

/**
 * Test for the class ReaderBUILD.
 * 
 * @author Sonja
 */
public class TestReaderBUILD extends TestCase{

	/**
	 * Class to be tested.
	 */
	private ReaderBUILD reader = new ReaderBUILD();
	/**
	 * Containers for the leaf- and tripleset to be tested.
	 */
	private ArrayList<Integer> leaves;
	private String leavesPrint;
	private ArrayList<Pair<Pair<Integer, Integer>, Integer>> triples;
	private String triplesPrint;
	/**
	 * Container for testing.
	 */
	private boolean success;
	
	/**
	 * Returns <code>true</code> a specified triple ((left, middle), right) is contained in a specified tripleset; <code>false</code> otherwise.
	 * 
	 * @param tripleset in which the specified triple is searched.
	 * @param left part of the specified triple for which is searched.
	 * @param middle part of the specified triple for which is searched.
	 * @param right part of the specified triple for which is searched.
	 * 
	 * @return <code>true</code> if the specified tripleset contains the specified triple;
	 * <code>false</code> otherwise.
	 */
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
			reader.read("C:\\Users\\Sonja\\eclipse-workspace\\SoftwarePraktikum\\src\\Textfiles\\Test\\RBemptyFile.txt");
			fail("expected IOException");
		} catch (IOException e) {
			// ignore, this exception is expected
		}
		/*
		 * test wrong format
		 * 	RBwrongFormat.txt: wrong number of leaves
		 * 	RBwrong.txt: triple with not existing leaf
		 * 	RBwrong2.txt: random text
		 * 	RBwrong3.txt: graph text file (edge to a not existing endpoint)
		 */
		try {
			reader.read("C:\\Users\\Sonja\\eclipse-workspace\\SoftwarePraktikum\\src\\Textfiles\\Test\\RBwrongFormat.txt");
			fail("expected IOException");
		} catch (IOException e) {
			// ignore, this exception is expected
		}
		try {
			reader.read("C:\\Users\\Sonja\\eclipse-workspace\\SoftwarePraktikum\\src\\Textfiles\\Test\\RBwrong.txt");
			fail("expected IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			// ignore, this exception is expected
		} catch (IOException e) {
			assertFalse("IOException was thrown but not expected.", true);
		}
		try {
			reader.read("C:\\Users\\Sonja\\eclipse-workspace\\SoftwarePraktikum\\src\\Textfiles\\Test\\RBwrong2.txt");
			fail("expected IOException");
		} catch (IOException e) {
			// ignore, this exception is expected
		}try {
			reader.read("C:\\Users\\Sonja\\eclipse-workspace\\SoftwarePraktikum\\src\\Textfiles\\Test\\RBwrong3.txt");
			fail("expected IOException");
		} catch (IOException e) {
			// ignore, this exception is expected
		}
		/*
		 * test empty leafset
		 */
		try {
			reader.read("C:\\Users\\Sonja\\eclipse-workspace\\SoftwarePraktikum\\src\\Textfiles\\Test\\RBemptyLeafset.txt");
			fail("expected IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			// ignore, this exception is expected
		} catch (IOException e) {
			assertFalse("IOException was thrown but not expected.", true);
		}
		/*
		 * test empty tripleset
		 */
		try {
			reader.read("C:\\Users\\Sonja\\eclipse-workspace\\SoftwarePraktikum\\src\\Textfiles\\Test\\RBemptyTripleset.txt");
		} catch (IOException e) {
			assertFalse("IOException was thrown but not expected.", true);
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
			assertFalse("IOException was thrown but not expected.", true);
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

