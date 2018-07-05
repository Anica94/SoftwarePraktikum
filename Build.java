import java.util.ArrayList;


/**
 * @author Sonja
 *
 */
public class Build {

	/**
	 * Saves the result of the algorithm as a graph.
	 */
	private UndirectedGraph result;
	// #instanz von find conCom

	/**
	 * Produces an algorithm for build with an empty result.
	 */
	public Build() {
		result = null;
	}

	/**
	 * 
	 */
	public Pair<UndirectedGraph, Integer> build(ArrayList<Pair<Pair<Integer, Integer>, Integer>> triples, ArrayList<Integer> leaves) {

		// Aho
		UndirectedGraph ahoGraph;
		ArrayList<ArrayList<Integer>> connectedComponents;
		int numberOfComponents;
		ArrayList<Integer> newLeaves;
		ArrayList<Pair<Pair<Integer, Integer>, Integer>> newTriples;
		
		// tree
		Pair<UndirectedGraph, Integer> rootedTree;
		ArrayList<Pair<UndirectedGraph, Integer>> trees = new ArrayList();
		UndirectedGraph tree = new UndirectedGraph(); // result
		UndirectedGraph currentTree;
		Integer root = new Integer(-1); // result
		Integer currentRoot;
		
		// BUILD
		ahoGraph = this.computeAhoGraph(triples, leaves);
		connectedComponents = this.computeConnectedComponents(ahoGraph); // Instanz
		numberOfComponents = connectedComponents.size();
		if (numberOfComponents == 1 & leaves.size() == 1) {
			root = leaves.get(0);
			tree.addVertex(root);
			rootedTree = new Pair(tree, root);
			return rootedTree;
		}
		else if (numberOfComponents == 1 & leaves.size() > 1) {
			return null;
		}
		else {
			for (int i = 0; i < numberOfComponents; i++) {
				// tripelmenge einschränken durch AhoGraph
				newLeaves = connectedComponents.get(i);
				newTriples = this.computeNewTriples(triples, newLeaves);
				trees.add(build(newTriples, newLeaves));
			}
			if ( !trees.contains(null)) {
				//////// unbedingt überprüfen!!!!!!!!!!!
				// compute root
				for (int i = 0; i < trees.size(); i++) {
					currentRoot = trees.get(i).getSecond();
					if (root <= currentRoot) {
						root = currentRoot+1;
					}
				}
				// attach all trees
				tree.addVertex(root);
				for (int i = 0; i < trees.size(); i++) {
					currentTree = trees.get(i).getFirst();
					tree.getStartpoints().putAll(currentTree.getStartpoints());
					currentRoot = trees.get(i).getSecond();
					tree.addEdge(root, currentRoot);
				}
			rootedTree = new Pair(tree, root);
			}
			else {
				rootedTree = new Pair(null, null);
			}
			return rootedTree;
		}
	}
	
	/**
	 * 
	 */
	private UndirectedGraph computeAhoGraph(ArrayList<Pair<Pair<Integer, Integer>, Integer>> triples, ArrayList<Integer> leaves) {
		UndirectedGraph ahoGraph = new UndirectedGraph();
		Pair<Pair<Integer, Integer>, Integer> currentTriple;
		Pair<Integer, Integer> currentTripleLeftside;
		Integer x;
		Integer y;
		// add vertices
		for (int i = 0; i < leaves.size(); i++) {
			ahoGraph.addVertex(leaves.get(i));
		}
		// add edges
		for(int i = 0; i < triples.size(); i++) {
			currentTriple = triples.get(i); 
			//if(leaves.contains(currentTriple.getSecond())) { // z in leaves
				currentTripleLeftside = currentTriple.getFirst();
				x = currentTripleLeftside.getFirst();
				if(leaves.contains(x)) { // x in leaves
					y = currentTripleLeftside.getSecond();
					if(leaves.contains(y)) { // y in leaves
						ahoGraph.addEdge(x, y);
					}
				}
			//}
		}
		return ahoGraph;
	}
	
	// schreibt Anica
	/**
	 * 
	 */
	private ArrayList<ArrayList<Integer>> computeConnectedComponents(UndirectedGraph ahoGraph) {// liste von liste von allen Knoten in einer Component?
		return null;
	}
	
	/**
	 * 
	 */
	private ArrayList<Pair<Pair<Integer, Integer>, Integer>> computeNewTriples(ArrayList<Pair<Pair<Integer, Integer>, Integer>> triples, ArrayList<Integer> leaves){
		ArrayList<Pair<Pair<Integer, Integer>, Integer>> newTriples = new ArrayList<>();
		Pair<Pair<Integer, Integer>, Integer> currentTriple;
		Pair<Integer, Integer> currentTripleLeftside;
		for(int i = 0; i < triples.size(); i++) {
			currentTriple = triples.get(i);
			if(leaves.contains(currentTriple.getSecond())) { // z in leaves
				currentTripleLeftside = currentTriple.getFirst();
				if(leaves.contains(currentTripleLeftside.getFirst())) { // x in leaves
					if(leaves.contains(currentTripleLeftside.getSecond())) { // y in leaves
						newTriples.add(currentTriple);
					}
				}
			}
		}
		return newTriples;
	}
	
}
