import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class MaximalMatching implements Algorithm {
	
	private Graph result;

	public MaximalMatching() {
		result = null;
	}

	@Override
	public ArrayList<Option> execute(Graph graph) {
		// MaxMAtching Alg aus Folie
		ArrayList<Option> changes = new ArrayList();
		Graph copyGraph = graph; // E'
		ArrayList<Integer> copyVertices = copyGraph.getVertices();
		Integer currentVertex;
		// produce M as graph without any edges
		for(int i = 0; i < copyVertices.size(); i++) {
			currentVertex = copyVertices.get(i);
			result.addVertex(currentVertex);
		}
		
		while (copyGraph.containsEdges()) {
			Integer startVertexName = null; // wird wegen while Bed aber zu etwas
			Integer endVertexName = null;
			for(int i = 0; i < copyVertices.size(); i++) {
				currentVertex = copyVertices.get(i);
				HashMap<Integer, TreeMap<Integer, Integer>> copyStartpoints = copyGraph.getStartpoints();
				if (copyStartpoints.containsKey(currentVertex)) // also gibt es einen adjazenten Knoten zum current
				{
					startVertexName = currentVertex;
					endVertexName = copyStartpoints.get(currentVertex).firstKey(); // beliebige Kante
					break;
				}
			}
			changes.add(new EdgeOption("consider", startVertexName, endVertexName));
			//füge diese Kante zu M  hinzu  und lösche sie aus copyGraph
			result.addEdge(startVertexName, endVertexName);
			copyGraph.deleteEdge(startVertexName, endVertexName);
			copyGraph.deleteEdge(startVertexName, endVertexName);
			changes.add(new EdgeOption("choose", startVertexName, endVertexName));
			// lösche alle inzidenten Kanten -> für alle Knoten, if containsedge mit u oder v then deleteedge || für directed
			for(int i = 0; i < copyVertices.size(); i++) {
				currentVertex = copyVertices.get(i);
				if (copyGraph.containsEdge(startVertexName, currentVertex) || copyGraph.containsEdge(currentVertex, startVertexName)) {
					copyGraph.deleteEdge(startVertexName, currentVertex);
				}
				if (copyGraph.containsEdge(endVertexName, currentVertex) || copyGraph.containsEdge(currentVertex, endVertexName)) {
					copyGraph.deleteEdge(endVertexName, currentVertex);
				}
			}
		}
		return changes;
	}
	
	@Override
	public Graph getResult(){
		return result;
	}

}
