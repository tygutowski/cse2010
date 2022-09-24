/*
 * Author:  Tyler Gutowski, tgutowski2020@fit.edu
 * Course:  CSE 2010, Section 02, Fall 2021
 * Project: Proj 03, Tree
 */

// Import List and ArrayList in order to organize list of children nodes
import java.util.ArrayList;
import java.util.List;

public class Node<T> { // Node class for tree
    private String name = null; // Name of the node
    @SuppressWarnings("rawtypes")
	private List<Node> children = new ArrayList<>(); // Children of the node in an arrayList
    private Node<?> parent = null; // Parent of the node as a node (not name)

    public Node(String name) {
        this.name = name; // Initialize the node's name
    }
    // Function to add a child node to another node.
    public void addChild(Node<?> child) {
        child.setParent(this); // Set the child of the parent to this node.
        this.children.add(child); // Add this node to the local child ArrayList
    }
    // Get the children of the current node
    @SuppressWarnings("rawtypes")
	public List<Node> getChildren() {
        return children; // Simply return the local children ArrayList
    }
    // Get the name of the node
    public String getName() {
        return name; // Simply return the local name
    }
    // Set the parent of the node. Only used in
    // addChild().
    private void setParent(Node<?> parent) {
        this.parent = parent; // Set the parent variable equal to the parent parameter
    }
    // Get the parent of the node. Used for upwards traversal of the tree.
    public Node<?> getParent() {
        return parent;
    }
}