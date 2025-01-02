import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String[] fl = sc.nextLine().split(" ");
		String graphType = fl[0];
		String startVertex = fl[1];
		String searchType = fl[2];

		Map<String, ArrayList<String>> neigh = new HashMap<String, ArrayList<String>>();
		String line = "";
		while (sc.hasNextLine()) {
			line = sc.nextLine();
			if (line.isEmpty()) {
				continue;
			}
			String[] pair = { line.split(" ")[0], line.split(" ")[1] };
			if (graphType.equals("d")) {
				if (neigh.get(pair[0]) == null) {
					neigh.put(pair[0], new ArrayList<String>());
					neigh.get(pair[0]).add(pair[1]);
					continue;
				}
				neigh.get(pair[0]).add(pair[1]);
			} else {
				if (neigh.get(pair[0]) == null) {
					neigh.put(pair[0], new ArrayList<String>());
					neigh.get(pair[0]).add(pair[1]);
				} else {
					neigh.get(pair[0]).add(pair[1]);
				}
				if (neigh.get(pair[1]) == null) {
					neigh.put(pair[1], new ArrayList<String>());
					neigh.get(pair[1]).add(pair[0]);
				} else {
					neigh.get(pair[1]).add(pair[0]);
				}
			}
		}
		sc.close();

		ArrayList<String> visited = new ArrayList<String>();
		if (searchType.equals("d")) {
			Stack<String> stack = new Stack<String>();
			stack.push(startVertex);
			while (!stack.isEmpty()) {
				String visiting = stack.pop();
				if (!visited.contains(visiting)) {
					visited.add(visiting);
				}
				List<String> vertexNeigh = neigh.get(visiting);
				if (vertexNeigh != null) {
					Collections.sort(vertexNeigh);
				}
				else {
					continue;
				}
				for (int i = vertexNeigh.size() - 1; i >= 0; --i) {
					if (!visited.contains(vertexNeigh.get(i))) {
						stack.push(vertexNeigh.get(i));
					}
				}
			}
		} else {
			Queue<String> queue = new LinkedList<String>();
			queue.add(startVertex);
			while (!queue.isEmpty()) {
				String visiting = queue.poll();
				if (!visited.contains(visiting)) {
					visited.add(visiting);
				}
				List<String> vertexNeigh = neigh.get(visiting);
				if (vertexNeigh != null) {
					Collections.sort(vertexNeigh);
				}
				else {
					continue;
				}
				for (String vertex : vertexNeigh) {
					if (!visited.contains(vertex)) {
						visited.add(vertex);
						queue.add(vertex);
					}
				}
			}
		}
		for (String vertex : visited) {
			System.out.println(vertex);
		}
	}
}