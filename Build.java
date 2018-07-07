import java.util.ArrayList;

/**
 * This algorithm is BUILD. It computes a rooted, phylog. tree distinctly leaf-labeled by a given leafset 
 * consistent with all rooted triplets in a given tripleset, if one exists; otherwise null.
 * 
 * @author Sonja
 */
public class Build {

	// TODO #instanz von find conCom
	ConnectedComponents conComps;
	/**
	 * Saves the changes made by BUILD.
	 */
	private ArrayList<Operation> changes;

	/**
	 * Produces an algorithm for BUILD with an empty list of changes.
	 */
	public Build() {
		changes = new ArrayList<Operation>();
	}


	/**
	 * BUILD for a given tripleset and a given leafset. The result is a pair of the resulting tree and its root, if such a tree exists;
	 * otherwise a pair of two null objects.
	 * 
	 * @param triples the tripleset for which a tree shall be computed.
	 * @param leaves the leafset for which a tree shall be computed.
	 * 
	 * @return pair of the resulting tree and its root or of two null objects.
	 * 
	 * @throws NullPointerException if the tripleset or the leafset is <code> null</code>.
	 */
	public Pair<UndirectedGraph, Integer> build(ArrayList<Pair<Pair<Integer, Integer>, Integer>> triples, ArrayList<Integer> leaves) {
		if (triples == null || leaves == null) { // auch wenn die leer? oder dann trotzdem laufenlassen und Ergebnis wird (hoffentlich) null?
    		throw new NullPointerException();
    	}
		
		/*
		 * variables for the Aho graph
		 */
		UndirectedGraph ahoGraph;
		ArrayList<ArrayList<Integer>> connectedComponents;
		int numberOfComponents;
		ArrayList<Integer> newLeaves;
		ArrayList<Pair<Pair<Integer, Integer>, Integer>> newTriples;
		
		/*
		 * variables for the (resulting) tree
		 */
		Pair<UndirectedGraph, Integer> rootedTree;
		ArrayList<Pair<UndirectedGraph, Integer>> trees = new ArrayList();
		UndirectedGraph tree = new UndirectedGraph(); // result
		UndirectedGraph currentTree;
		Integer root = new Integer(-1); // result
		Integer currentRoot;
		
		/*
		 * do BUILD
		 * 	compute Aho graph
		 * 	compute connected components
		 */
		ahoGraph = this.computeAhoGraph(triples, leaves);
		// TODO 
		System.out.println("hihi");
		connectedComponents = this.computeConnectedComponents(ahoGraph); // Instanz
		conComps = new ConnectedComponents();
		changes.addAll(conComps.execute(ahoGraph));// statt null die changes von findConCom
		// TODO END
		numberOfComponents = connectedComponents.size();
		/*
		 * the tripleset is consistent and the result is isomorphic to K_1
		 */
		if (numberOfComponents == 1 & leaves.size() == 1) {
			root = leaves.get(0);
			tree.addVertex(root);
			changes.add(new VertexOperation("build add", root));
			rootedTree = new Pair(tree, root);
			return rootedTree;
		}
		/*
		 * the tripleset is not consistent -> return null
		 */
		else if (numberOfComponents == 1 & leaves.size() > 1) {
			rootedTree = new Pair(null, null);
			return rootedTree;
		}
		else {
			/*
			 * recursive call of BUILD
			 */
			for (int i = 0; i < numberOfComponents; i++) {
				newLeaves = connectedComponents.get(i);
				newTriples = this.computeNewTriples(triples, newLeaves);
				trees.add(build(newTriples, newLeaves));
			}
			/*
			 * the tripleset is consitent
			 */
			if ( !trees.contains(null)) {
				//////// unbedingt Ã¼berprÃ¼fen!!!!!!!!!!!
				/*
				 * compute new root
				 */
				for (int i = 0; i < trees.size(); i++) {
					currentRoot = trees.get(i).getSecond();
					if (root <= currentRoot) {
						root = currentRoot+1;
					}
				}
				/*
				 *  attach all trees (results of the recursive call)
				 */
				tree.addVertex(root);
				changes.add(new VertexOperation("build add", root));
				for (int i = 0; i < trees.size(); i++) {
					currentTree = trees.get(i).getFirst();
					tree.getStartpoints().putAll(currentTree.getStartpoints());
					currentRoot = trees.get(i).getSecond();
					tree.addEdge(root, currentRoot);
					changes.add(new EdgeOperation("build add", root, currentRoot));
				}
			rootedTree = new Pair(tree, root);
			}
			/*
			 * the tripleset is not consistent -> return null
			 */
			else {
				rootedTree = new Pair(null, null);
			}
			return rootedTree;
		}
	}
	
	/**
	 * Computes the Aho graph (auxiliary graph) for the given triple- and leafset.
	 * 
	 * @param triples for which the Aho graph shall be computed.
	 * @param leaves for which the Aho graph shall be computed.
	 * 
	 * @return (undirected) AhoGraph.
	 * 
	 * @throws NullPointerException if the tripleset or the leafset is <code> null</code>.
	 */
	private UndirectedGraph computeAhoGraph(ArrayList<Pair<Pair<Integer, Integer>, Integer>> triples, ArrayList<Integer> leaves) {
		//if (triples == null || leaves == null) { // auch wenn die leer? oder dann trotzdem laufenlassen und Ergebnis wird (hoffentlich) null?
    	//	throw new NullPointerException();
    	//}
		if(leaves == null) {
			throw new NullPointerException();
		}
		
		UndirectedGraph ahoGraph = new UndirectedGraph();
		Pair<Pair<Integer, Integer>, Integer> currentTriple;
		Pair<Integer, Integer> currentTripleLeftside;
		Integer currentLeave;
		Integer x;
		Integer y;
		/*
		 *  add all given 'leaves' as vertices
		 */
		for (int i = 0; i < leaves.size(); i++) {
			currentLeave = leaves.get(i);
			ahoGraph.addVertex(currentLeave);
			changes.add(new VertexOperation("choose", currentLeave));
		}
		/*
		 *  add edges between the 'leaves' if they belong to the 'leftside' of a given triple
		 */
		for(int i = 0; i < triples.size(); i++) {
			currentTriple = triples.get(i); 
			//if(leaves.contains(currentTriple.getSecond())) { // z in leaves
				currentTripleLeftside = currentTriple.getFirst();
				x = currentTripleLeftside.getFirst();
				if(leaves.contains(x)) { // x in leaves
					y = currentTripleLeftside.getSecond();
					if(leaves.contains(y)) { // y in leaves
						ahoGraph.addEdge(x, y);
						changes.add(new EdgeOperation("choose", x, y));
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
		conComps = new ConnectedComponents();
		conComps.execute(ahoGraph);
		return conComps.getConnectedComponents();
	}
	
	/**
	 * Computes a new tripleset which is the given tripleset restricted by the given leafset.
	 * 
	 * @param triples for which a new tripleset shall be computed.
	 * @param leaves for which a new tripleset shall be computed.
	 * 
	 * @return new tripleset.
	 * 
	 * @throws NullPointerException if the tripleset or the leafset is <code> null</code>.
	 */
	private ArrayList<Pair<Pair<Integer, Integer>, Integer>> computeNewTriples(ArrayList<Pair<Pair<Integer, Integer>, Integer>> triples, ArrayList<Integer> leaves){
		if (triples == null || leaves == null) { // auch wenn die leer? oder dann trotzdem laufenlassen und Ergebnis wird (hoffentlich) null?
    		throw new NullPointerException();
    	}
		/*
		 * check whether all elements of a given triples are in the given leaves
		 * 	return this triple if yes
		 */
		ArrayList<Pair<Pair<Integer, Integer>, Integer>> newTriples = new ArrayList<>();
		Pair<Pair<Integer, Integer>, Integer> currentTriple;
		Pair<Integer, Integer> currentTripleLeftside;
		for(int i = 0; i < triples.size(); i++) {
			currentTriple = triples.get(i);
			/*
			 * check z in leaves
			 */
			if(leaves.contains(currentTriple.getSecond())) {
				/*
				 * check x in leaves
				 */
				currentTripleLeftside = currentTriple.getFirst();
				if(leaves.contains(currentTripleLeftside.getFirst())) {
					/*
					 * check y in leaves
					 */
					if(leaves.contains(currentTripleLeftside.getSecond())) {
						newTriples.add(currentTriple);
					}
				}
			}
		}
		return newTriples;
	}
	
	/**
	 * Getter for the changes made by BUILD.
	 * 
	 * @return changes made by BUILD.
	 */
	public ArrayList<Operation> getChanges(){
		return changes;
	}
}
