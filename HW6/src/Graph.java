
/*
 * Author: Tyler Gutowski, tgutowski2020@fit.edu or tygutowski@gmail.com
 * Course: CSE 2010, Section 01, Fall 2021
 * Project: Proj 06, hw6S12GroupHelp
 */
// Some help received from Aakash Hasija and Sahil Vaid from Geeksforgeeks.org 
// to help understand the concept of BFS and pathfinding.
import java.util.ArrayList;
import java.util.LinkedList;

//This class uses BFS to organize an 
public class Graph {

	// Function to create pointers (edges) between two vertices.
	public void addEdge(int vertex1, int vertex2, ArrayList<ArrayList<Integer>> adjacencyList) {
		// Edges should be both ways (Not necessarily for a tree, but to be able
		// to search it makes it easier.
		adjacencyList.get(vertex1).add(vertex2);
		adjacencyList.get(vertex2).add(vertex1);
	}

	// Method that returns a character (first direction) the ghosts will want to
	// move. Then, it prints out every coordinate position from the source to the
	// destination vertex.
	public char printPath(ArrayList<ArrayList<Integer>> adjacencyList, int source, int destination,
			int numberOfVertices, int columns) {
		// Parent array stores the parent of i, and distance array stores the
		// distance from the source.
		int parent[] = new int[numberOfVertices];
		int distance[] = new int[numberOfVertices];
		// Run BFS, and if it returns false, we know that the ghost vertice has
		// no direct path to pacman vertice
		if (BFS(adjacencyList, source, destination, numberOfVertices, parent, distance) == false) {
			return ' ';
		}
		// LinkedList to store path (Stored as vertex numbers)
		LinkedList<Integer> path = new LinkedList<Integer>();
		int branch = destination;
		// Add the branch to the path
		path.add(branch);
		// While the branch has a parent (not equal to -1!)
		while (parent[branch] != -1) {
			// Add its parent to the path
			path.add(parent[branch]);
			branch = parent[branch];
		}
		// Set the return char to empty
		char returnchar = ' ';
		for (int i = path.size() - 1; i > -1; i--) {
			// If its the first iteration of the loop
			if (i == path.size() - 1) {
				// tempNum is equal to the difference between the
				// current path vertex value, and the previous.

				// If the difference is 1 or -1, the path is left or right,
				// respectively. If the difference is the number of columns, then
				// the path is up or down, depending on if the difference is
				// positive or negative.

				// Then print the direction, along with the size of the path
				// queue
				int tempNum = path.get(i) - path.get(i - 1);
				if (tempNum == columns) { // Up
					System.out.print("u " + (path.size() - 1) + " ");
					returnchar = 'u';
				} else if (tempNum == -columns) { // Down
					System.out.print("d " + (path.size() - 1) + " ");
					returnchar = 'd';
				} else if (tempNum == 1) { // Left
					System.out.print("l " + (path.size() - 1) + " ");
					returnchar = 'l';
				} else if (tempNum == -1) { // Right
					System.out.print("r " + (path.size() - 1) + " ");
					returnchar = 'r';
				}
			}
			// Iterate through each vertex and print the coordinates
			// which are retrieved from getting the modulus and integer division
			System.out.print("(" + (int) path.get(i) / columns + "," + path.get(i) % columns + ") ");
		}
		System.out.println();
		// Return the direction that the ghost should move (u,d,l,r)
		return returnchar;
	}

	// BFS algorithm that stores the parent of the vertexes, along with its
	// distance from the source vertex
	private boolean BFS(ArrayList<ArrayList<Integer>> adjacencyList, int sourceIndex, int destination,
			int numberOfVertices, int parent[], int distance[]) {
		// Array to store whether or not each node has been visited yet.
		boolean visited[] = new boolean[numberOfVertices];
		// LinkedList treated as a queue to keep a queue of the last used vertices.
		LinkedList<Integer> queue = new LinkedList<Integer>();
		// Set all vertices initially to false, and all distances to infinity.
		for (int i = 0; i < numberOfVertices; i++) {
			parent[i] = -1; // There is no parent to the source node.
			distance[i] = Integer.MAX_VALUE; // Max value (infinity)
			visited[i] = false;
		}
		// The source is the first item that is visited. Source's distance is 0
		// (obviously)
		visited[sourceIndex] = true;
		distance[sourceIndex] = 0;
		queue.add(sourceIndex);
		// Begins searching at the source, and works its way outwards.
		// While the queue isn't empty
		while (queue.size() != 0) {
			int u = queue.remove();
			for (int i = 0; i < adjacencyList.get(u).size(); i++) {
				// If we havent visited the current index
				if (visited[adjacencyList.get(u).get(i)] == false) {
					// Distance += 1
					distance[adjacencyList.get(u).get(i)] = distance[u] + 1;
					// Parent is the previous node
					parent[adjacencyList.get(u).get(i)] = u;
					// Add it to queue
					queue.add(adjacencyList.get(u).get(i));
					// Now we have visited it!
					visited[adjacencyList.get(u).get(i)] = true;
					// If the vertex is the destination, then return true
					if (adjacencyList.get(u).get(i) == destination) {
						return true;
					}
				}
			}
		}
		// If the destination vertex is never found, then return false
		return false;
	}
}