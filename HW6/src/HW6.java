
/*
 * Author: Tyler Gutowski, tgutowski2020@fit.edu or tygutowski@gmail.com
 * Course: CSE 2010, Section 01, Fall 2021
 * Project: Proj 06, hw6S12GroupHelp
 */

// The only thing I changed in grouphelp is the checking if the player's
// input was correct or incorrect. I didn't consider because it wasn't
// a part of the example cases.
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class HW6 {
	// Main runner class. Starts by importing the map from the args[] parameter.
	// Then it creates a list of ghosts to add later, and creates a graph object.
	// Then it prompts the user to input a direction (up, down, left, right) and
	// takes their turn, then takes the turn for each ghost. A ghost is anything
	// that is not '#', '.', ' ' or 'P'. Will only run one iteration so far. If the
	// ghost touches the player in the single turn, it will print that the ghosts
	// are full at the end of the turn. If all of the dots are eaten, it will print
	// that Pacman has won.
	public static void main(String[] args) throws FileNotFoundException {
		// Gets map from args[]
		Scanner params = new Scanner(new File("C:\\Users\\tygut\\eclipse-workspace\\HW6\\src\\HW6Input1.txt"));
		//Scanner params = new Scanner(new File(args[0]));
		String[] data = params.nextLine().split(" ");
		// Ghostlist to add Ghost objects to later.
		ArrayList<Ghost> ghostList = new ArrayList<Ghost>();
		// Graph to organize map
		Graph graph = new Graph();

		// Simple game data such as the number of rows, columns and the current map
		// state
		int rows = Integer.parseInt(data[0]);
		int columns = Integer.parseInt(data[1]);
		char[][] map = new char[rows][];

		// Create pacman object to store data (position and points)
		Pacman pacman = new Pacman(0, 0, 0);

		// create the map from input
		for (int i = 0; i < rows; i++) {
			map[i] = params.nextLine().toCharArray();
		}
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				// If P is on the map
				if (map[i][j] == 'P') {
					// set pacman's Y, X and points
					pacman.setY(i);
					pacman.setX(j);
				}
			}
		}
		// Print the initial graph
		printGraph(rows, columns, map);
		// Prompt user input
		System.out.print("Please enter your move [u(p), d(own), l(elf), or r(ight)]: ");
		@SuppressWarnings("resource")
		boolean inputGood = false;
		String line = "";
		Scanner input = new Scanner(System.in);
		while(inputGood == false) {
		// Make a new scanner for user input
			line = input.nextLine();
			// Set pacman's current position to empty
			map[pacman.getY()][pacman.getX()] = ' ';
			// Find what the user prompted and update pacman's X or Y value accordingly
			if(line.charAt(0) == 'u' || line.charAt(0) == 'd' || line.charAt(0) == 'l' || line.charAt(0) == 'r') {
				inputGood = true;
			}
			else {
				System.out.print("Invalid input. Please enter your move [u(p), d(own), l(elf), or r(ight)]: ");
			}
		}
		if (line.charAt(0) == 'u') {
			pacman.setY(pacman.getY() - 1);
		} else if (line.charAt(0) == 'd') {
			pacman.setY(pacman.getY() + 1);
		} else if (line.charAt(0) == 'l') {
			pacman.setX(pacman.getX() - 1);
		} else if (line.charAt(0) == 'r') {
			pacman.setX(pacman.getX() + 1);
		}
		// If pacman's new point has a dot, increment his point counter
		if (map[pacman.getY()][pacman.getX()] == '.') {
			pacman.incrementPoints();
		}
		// Set his new position to P (indicating that he moved.
		map[pacman.getY()][pacman.getX()] = 'P';
		// Print the graph, since he now moved.
		printGraph(rows, columns, map);
		// Total vertices is equal to the number of rows*columns
		int v = rows * columns;
		// Make an adjacencyList with the number of total vertices
		ArrayList<ArrayList<Integer>> adj = new ArrayList<ArrayList<Integer>>(v);
		for (int i = 0; i < v; i++) {
			// add an integer arraylist to each vertice (to indicate what nodes to where)
			adj.add(new ArrayList<Integer>());
		}
		// for every single node
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				// Iterate through the map, and if the current item is NOT a wall
				if (map[i][j] != '#') {
					// Note: we use (columns * i + j) because that determines what
					// node value we want. columns is the total number of columns.
					// If up is NOT a wall, add an edge pointing upwards.
					if (map[i - 1][j] != '#') {
						graph.addEdge(columns * (i - 1) + j, columns * i + j, adj);
					}
					// If down is NOT a wall, add an edge pointing down.
					if (map[i + 1][j] != '#') {
						graph.addEdge(columns * (i + 1) + j, columns * i + j, adj);
					}
					// If left is NOT a wall, add an edge pointing left.
					if (map[i][j - 1] != '#') {
						graph.addEdge(columns * i + (j - 1), columns * i + j, adj);
					}
					// If right is NOT a wall, add an edge pointing right.
					if (map[i][j + 1] != '#') {
						graph.addEdge(columns * i + (j + 1), columns * i + j, adj);
					}
				}
			}
		}
		// Check the entire map for any nodes that are a ghost, then add the ghost to
		// ghostList.
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (map[i][j] != '#' && map[i][j] != '.' && map[i][j] != 'P' && map[i][j] != ' ') {
					Ghost tempGhost = new Ghost(map[i][j], j, i);
					ghostList.add(tempGhost);
				}
			}
		}
		// Print the number of points
		System.out.println("Points: " + pacman.getPoints());
		// Sort the ghost list using the comparator, which sorts by character
		// (Alphabetical order)
		Collections.sort(ghostList);
		// For every ghost on the map
		for (Ghost ghost : ghostList) {
			// Print their name
			System.out.print("Ghost " + Character.toLowerCase(ghost.getIcon()) + ": ");
			// Get their expected direction, icon and X and Y values
			char dir = graph.printPath(adj, columns * ghost.getY() + ghost.getX(),
					columns * pacman.getY() + pacman.getX(), v, columns);
			int ghost_x = ghost.getX();
			int ghost_y = ghost.getY();
			char icon = ghost.getIcon();
			// If the ghost is on a dot
			if (Character.isUpperCase(icon)) {
				// When they move set the previous tile to a dot
				map[ghost_y][ghost_x] = '.';
			} else {
				// Otherwise set it empty
				map[ghost_y][ghost_x] = ' ';
			}
			// Edit the ghosts's coordinates depending on their new move
			if (dir == 'u') {
				ghost_y -= 1;
			} else if (dir == 'd') {
				ghost_y += 1;
			} else if (dir == 'l') {
				ghost_x -= 1;
			} else if (dir == 'r') {
				ghost_x += 1;
			}
			// If the new position is empty
			if (map[ghost_y][ghost_x] == ' ') {
				// Make them lowercase
				ghost.setIcon(Character.toLowerCase(ghost.getIcon()));
			}
			// If there is a dot in the new position
			else if (map[ghost_y][ghost_x] == '.') {
				// Make them uppercase
				ghost.setIcon(Character.toUpperCase(ghost.getIcon()));
			}
			// Set the ghost's icon to the new icon
			map[ghost_y][ghost_x] = ghost.getIcon();
			// Print the map
			printGraph(rows, columns, map);
			// If the ghost is on pacman, the ghost wins.
			if (ghost_x == pacman.getX() && ghost_y == pacman.getY()) {
				System.out.println("A ghost is not hungry any more!");
			}
		}
		// Delete the ghostList for new iteration.
		ghostList.clear();
	}

	// Method to print the entire graph, along with the axis lines
	// on the left and top of the graph. parameters are the rows,
	// columns, and the map, and it simply iterates through each column and prints
	// each character array (row).
	public static void printGraph(int rows, int columns, char[][] map) {
		System.out.print(" ");
		for (int i = 0; i < columns; i++) {
			System.out.print(i % 10);
		}
		System.out.println();
		for (int i = 0; i < rows; i++) {
			System.out.print(i);
			System.out.println(map[i]);
		}
	}

}