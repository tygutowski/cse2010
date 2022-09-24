/*
 * Author:  Tyler Gutowski, tgutowski2020@fit.edu
 * Course:  CSE 2010, Section 02, Fall 2021
 * Project: Proj 03, Tree
 */

// imports
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class HW3 {
	public static Node<String> rootNode = null;
	public static void main(String[] args) throws FileNotFoundException {
		createTree(args);
	}	
	// Method to create tree, REcursivelyCreateTree is more important
	private static void createTree(String[] args) throws FileNotFoundException {
		Scanner data = new Scanner(new File(args[0]));
		if(data.hasNextLine()) { // If the data has a next line
			String firstLine = data.nextLine(); // Save the line
			String[] firstLineNodes = firstLine.split(" "); // Split up the nodes into a String Array
			rootNode = new Node(firstLineNodes[0]); // Set the root node equal to the first parameter
			List<Node> nodeList = new ArrayList<Node>(); // Make a Node List
			nodeList.add(rootNode); // Add the root to the Node List
			for(int i = 1; i < firstLineNodes.length; i++) { // For all other Nodes besides the root
				Node tempChild = new Node(firstLineNodes[i]); // Make a temporary child
				rootNode.addChild(tempChild); // Add the child to the root
				nodeList.add(tempChild); // Add the child to the list.
			}
			
			RecursivelyCreateTree(rootNode, data, nodeList); // Begin to recursively create the tree
			scanQuery(nodeList, args);
		}
	}
	// Method to recurviely create a tree, since trees are easily done recursively.
	// The concept is that we create the root previously, then start recursing with
	// at the root until we reach a point where there are no children left (leafs)
	// We call this as long as there is still data in the Input file.
	private static void RecursivelyCreateTree(Node parentNode, Scanner data, List<Node> nodeList) {
		if(data.hasNextLine()) { // If the data has a next line
			Node localParent = null; // Set the local parent to null, in order to use later
			String line = data.nextLine(); // Save the line
			String[] lineNodes = line.split(" "); // Split up the data into a String Array
			for(Node node : nodeList) { // For all nodes in the List of Nodes
				if(node.getName().equals(lineNodes[0])) { // If the node's name is equal to the first parameter (parent)
					localParent = node; // The localParent is the node we're looking for!
				} 
			}
			for(int i = 1; i < lineNodes.length; i++) { // For every other parameter (children)
				Node tempChild = new Node(lineNodes[i]); // Create a temporary child
				localParent.addChild(tempChild); // Add the temporary child to the local parent
				nodeList.add(tempChild); // Add the temporary child to the node list
			}
			RecursivelyCreateTree(localParent, data, nodeList);
		}
	}
	private static void scanQuery(List<Node> nodeList, String[] args) throws FileNotFoundException {
		Scanner query = new Scanner(new File(args[1]));
		while(query.hasNextLine()) { // While the query still has things to call
			String nextLine = query.nextLine(); // Next line is new line
			String words[] = nextLine.split(" "); // Split the query words
			// Call command that is the first word, paremeters are the second word:
			switch(words[0]) {
			case "GetNamesByCategory":
				System.out.print("GetNamesByCategory " + words[1] + " ");
				GetNamesByCategory(words[1], nodeList);
				break;
			case "GetNamesByState":
				System.out.print("GetNamesByState " + words[1] + " ");
				GetNamesByState(words[1], nodeList);
				break;
			case "GetNamesByCategoryAndState":
				System.out.print("GetNamesByCategoryAndState " + words[1] + " " + words[2] + " ");
				GetNamesByCategoryAndState(words[1], words[2], nodeList);
				break;
			case "GetNamesWithMultipleStates":
				System.out.print("GetNamesWithMultipleStates ");
				GetNamesWithMultipleStates(nodeList);
				break;
			case "GetNamesWithMultipleCategories":
				System.out.print("GetNamesWithMultipleCategories ");
				GetNamesWithMultipleCategories(nodeList);
				break;
			case "GetCategory":
				System.out.print("GetCategory " + words[1] + " ");
				GetCategory(words[1], nodeList);
				break;
			case "GetState":
				System.out.print("GetState " + words[1] + " ");
				GetState(words[1], nodeList);
				break;
			}
		}
	}
	// Recursive function to find all leaf nodes. Does so by trying to find the list of children. If child list is
	// empty, then we know we are at a leaf.
	private static void GetLeaves(Node<?> localRoot, List<Node> nodeList, List<String> leafList) {
		for(Node node : nodeList) { // for EVERY node in the list
			if(node.equals(localRoot)) { // if the node is the one we're looking for
				List<Node> childList = node.getChildren(); // get the children of the node
				for(Node child : childList) { // for all of the children
					if(child.getChildren().isEmpty()) { // if there aren't any children (we are at a leaf)
						leafList.add(child.getName()); // add the child to the node
					}
					else { // if there are children, call it again (not leaf!)
						GetLeaves(child, nodeList, leafList);
					}
				}
			}
		}
	}
	// Function to get all names given the category. Has no return, only prints
	// the list of names that exist in the given category.
	private static void GetNamesByCategory(String category, List<Node> nodeList) {
		List<String> leafList = new ArrayList();
		for(Node<?> node : nodeList) { // for EVERY SINGLE node in the tree
			if(node.getName().equals(category)) { // check if the name is the category
				GetLeaves(node, nodeList, leafList); // if it is, then recurse to print its leaves
			}
		}
		Collections.sort(leafList); // Sort the leafList
		for(String leaf : leafList) { // For all items in the leafList
			System.out.print(leaf + " "); // Print them all out!
		}
		System.out.println(); // Println to skip new line
	}
	// Function to get all names given the state. Has no return, only prints
	// the list of names that exist in the given state.
	private static void GetNamesByState(String state, List<Node> nodeList) {
		List<String> leafList = new ArrayList();
		for(Node<?> node : nodeList) { // for EVERY SINGLE node in the tree
			if(node.getName().contains(state)) { // check if the name is the category
				GetLeaves(node, nodeList, leafList); // if it is, then recurse to print its leaves
			}
		}
		Collections.sort(leafList);
		for(String leaf : leafList) {
			System.out.print(leaf + " ");
		}
		System.out.println();
	}
	// Function to get all names given the category and state. Has no return, only prints
	// the list of names that exist in the given category and state.
	private static void GetNamesByCategoryAndState(String category, String state, List<Node> nodeList) {
		List<String> leafList = new ArrayList(); // Create an empty leaf list to save all leaf nodes
		for(Node<?> node : nodeList) { // for EVERY SINGLE node in the tree
			if(node.getName().equals(state+"_cat"+category.charAt(category.length()-1))) { // check if the name is the category
				GetLeaves(node, nodeList, leafList); // if it is, then recurse to print its leaves
			}
		}
		Collections.sort(leafList);
		for(String leaf : leafList) {
			System.out.print(leaf + " ");
		}
		System.out.println();
	}
	// Function to get all names that exist in multiple states. Has no return, only
	// prints the list of names.
	private static void GetNamesWithMultipleStates(List<Node> nodeList) {
		ArrayList<String> leafList = new ArrayList<String>(); // make a new arrayList to leaf nodes
		for(Node<?> node : nodeList) { // for EVERY SINGLE node in the tree
			GetLeaves(node, nodeList, leafList); // recurse to print its leaves
			// There should be duplicates (if there are any at all).
			// Check for duplicates, and simply add any duplicates to another list
		}
		Collections.sort(leafList); // Sort the leafList using collections
		//Following code retrieved from: https://stackoverflow.com/questions/50549993/find-duplicate-value-in-array-list-and-print-the-duplicated-value
		Map<String, Integer> counts = new HashMap<String, Integer>();

		for (String str : leafList) { // For every item in the leafList
		    if (counts.containsKey(str)) { // If the map contains the String Key
		        counts.put(str, counts.get(str) + 1); // Add the item after the current item in the map
		    } else { // otherwise
		        counts.put(str, 1); // put it at the beginning
		    }
		}
		List<String> sortedList = new ArrayList(); // Create new arraylist to sort
		for (Map.Entry<String, Integer> entry : counts.entrySet()) { // For every item in the map
			if(entry.getValue()/3 > 1) { // If the occurrence is greater than 1 (duplicate)
				sortedList.add(entry.getKey()); // Add it to the sorted arraylist
			}
		}
		Collections.sort(sortedList); // Sort the sorted arrayList
		for(String item : sortedList) { // For all leaves
			System.out.print(item + " "); // Print them out
		}
		System.out.println(); // Println to skip line at the end
	}
	// Function to get all names with multiple categories. Has no return, only
	// prints the list of names.
	private static void GetNamesWithMultipleCategories(List<Node> nodeList) {
		ArrayList<String> leafList = new ArrayList<String>(); // make a new arrayList to leaf nodes
		for(Node<?> node : nodeList) { // for EVERY SINGLE node in the tree
			GetLeaves(node, nodeList, leafList); // recurse to print its leaves
			// There should be duplicates (if there are any at all).
			// Check for duplicates, and simply add any duplicates to another list
		}
		Collections.sort(leafList); // Sort the leafList using collections
		//Following code retrieved from: https://stackoverflow.com/questions/50549993/find-duplicate-value-in-array-list-and-print-the-duplicated-value
		Map<String, Integer> counts = new HashMap<String, Integer>();

		for (String str : leafList) { // For every item in the leafList
		    if (counts.containsKey(str)) { // If the map contains the String Key
		        counts.put(str, counts.get(str) + 1); // Add the item after the current item in the map
		    } else { // otherwise
		        counts.put(str, 1); // put it at the beginning
		    }
		}
		List<String> sortedList = new ArrayList(); // Create new arraylist to sort
		for (Map.Entry<String, Integer> entry : counts.entrySet()) { // For every item in the map
			if(entry.getValue()/3 > 1) { // If the occurrence is greater than 1 (duplicate)
				sortedList.add(entry.getKey()); // Add it to the sorted arraylist
			}
		}
		Collections.sort(sortedList); // Sort the sorted arrayList
		for(String item : sortedList) { // For all leaves
			System.out.print(item + " "); // Print them out
		}
		System.out.println(); // Println to skip line at the end
	}
	// Function to get the category given a name. Has no return, only prints
	// the category
	private static void GetCategory(String name, List<Node> nodeList) {
		for(Node<?> node : nodeList) { // for EVERY SINGLE node in the tree
			if(node.getName().equals(name)) { // Find the node with the name that we are looking for
				String tempString = node.getParent().getName(); // Find its parent node, which should have category and name
				if(tempString.contains("_cat")) { // If the parent node contains the word "_cat", then
					String categoryIntensity = tempString.split("_cat")[1]; // we know that the number directly after "_cat" indicate the 
					System.out.print("category" + categoryIntensity + " ");// category number, so print the category number and "category"!
				}
			}
		}
		System.out.println(); // Println to skip line at the end
		}
	// Function to get the state name given the hurricane name. Has no return, only
	// prints the name of the state
	private static void GetState(String name, List<Node> nodeList) {
		for(Node<?> node : nodeList) { // for EVERY SINGLE node in the tree
			if(node.getName().equals(name)) { // Find the node with the name that we are looking for
				String tempString = node.getParent().getName(); // Find its parent node, which should have category and name
				if(tempString.contains("_cat")) { // If the parent node contains the word "_cat", then
					String[] stateName = tempString.split("_cat"); // we know that the 2 letters before "_cat" indicate the 
					System.out.print(stateName[0] + " "); // state name, so print the state name!
				}
			}
		}
		System.out.println(); // Println to skip line at the end
	}
}
