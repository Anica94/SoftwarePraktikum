import java.util.ArrayList;


/**
 * @author Sonja
 *
 */
public class Build implements Algorithm {

	private ArrayList<Integer> leaves;
	private ArrayList<Pair<Pair<Integer, Integer>, Integer>> triples;
	private UndirectedGraph ahoGraph;
	/**
	 * Saves the result of the algorithm as a graph.
	 */
	private Graph result;

	/**
	 * Produces an algorithm for build with an empty result.
	 */
	public Build() {
		result = null;
	}

	// Build braucht leafset und triples, nicht graph :@ also muss der graph null sein bzw ist irrelevant
	@Override
	public ArrayList<Operation> execute(Graph graph) {
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public Graph getResult(Graph graph) {
		// TODO Auto-generated method stub
		return result;
	}
	
	/**
	 * 
	 */
	public UndirectedGraph build(ArrayList<Pair<Pair<Integer, Integer>, Integer>> triples, ArrayList<Integer> leaves) {
		UndirectedGraph tree = null;
		int numberOfComponents;
		ahoGraph = this.computeAhoGraph(triples, leaves);
		numberOfComponents = this.computeConnectedComponents(ahoGraph); // liste von liste von allen Knoten in einer Component?
		ArrayList<Integer> newLeaves = leaves;
		if (numberOfComponents == 1 & leaves.size() == 1) {
			tree = new UndirectedGraph();
			tree.addVertex(); // irgendwo noch result produzieren und wenn null ausgeben, dann aufpassen, dass gesamtgraph nicht zerstört wird
			return tree;
		}
		else if (numberOfComponents == 1 & leaves.size() > 1) {
			return null;
		}
		else {
			for (int i = 0; i < numberOfComponents; i++) {
				// tripelmenge einschränken durch AhoGraph
				ArrayList<Pair<Pair<Integer, Integer>, Integer>> newTriples = this.computeNewTriples(triples, leaves);
				tree = build(newTriples, leaves); // list of trees???
			}
			if ( tree == null) {
				// do nothing
			}
			else {
				// zusammenfügen
			}
		}
	}
	
	/**
	 * 
	 */
	private UndirectedGraph computeAhoGraph(ArrayList<Pair<Pair<Integer, Integer>, Integer>> triples, ArrayList<Integer> leaves) {
		UndirectedGraph ahoGraph = new UndirectedGraph();
		// add vertices
		for (int i = 0; i < leaves.size(); i++) {
			ahoGraph.addVertex(leaves.get(i));
		}
		// add edges
		for(int i = 0; i < triples.size(); i++) {
			Pair<Pair<Integer, Integer>, Integer> currentTriple = triples.get(i); // woanders deklarieren!!!!!!
			Pair<Integer, Integer> currentTripleLeftside = currentTriple.getFirst(); // woanders deklarieren!!!
			Integer x = currentTripleLeftside.getFirst();// woanders deklarieren!!!!!!
			if(leaves.contains(x)) { // x in leaves
				Integer y = currentTripleLeftside.getSecond();// woanders deklarieren!!!!!!
				if(leaves.contains(y)) { // y in leaves
					ahoGraph.addEdge(x, y);
				}
			}
		}
		return ahoGraph;
	}
	
	/**
	 * 
	 */
	private int computeConnectedComponents(UndirectedGraph ahoGraph) {// liste von liste von allen Knoten in einer Component?
		return 1;
	}
	
	/**
	 * 
	 */
	private ArrayList<Pair<Pair<Integer, Integer>, Integer>> computeNewTriples(ArrayList<Pair<Pair<Integer, Integer>, Integer>> triples, ArrayList<Integer> leaves){
		ArrayList<Pair<Pair<Integer, Integer>, Integer>> newTriples = new ArrayList<>();
		for(int i = 0; i < triples.size(); i++) {
			Pair<Pair<Integer, Integer>, Integer> currentTriple = triples.get(i); // woanders deklarieren!!!!!!
			if(leaves.contains(currentTriple.getSecond())) { // z in leaves
				Pair<Integer, Integer> currentTripleLeftside = currentTriple.getFirst(); // woanders deklarieren!!!
				if(leaves.contains(currentTripleLeftside.getFirst())) { // x in leaves
					if(leaves.contains(currentTripleLeftside.getSecond())) { // y in leaves
						newTriples.add(currentTriple);
					}
				}
			}
		}
		return newTriples;
	}
	/**
	 * Sets the leafset.
	 */
	public void setLeafset(ArrayList<Integer> leaves) {
		this.leaves = leaves;
	}
	
	/**
	 * Returns the tripleset.
	 * 
	 * @return tripleset
	 */
	public void setTripleset(ArrayList<Pair<Pair<Integer, Integer>, Integer>> triples) {
		this.triples = triples;
	}
}
