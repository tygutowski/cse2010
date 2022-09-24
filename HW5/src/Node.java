/*
 * Author: Tyler Gutowski, tgutowski2020@fit.edu or tygutowski@gmail.com
 * Course: CSE 2010, Section 01, Fall 2021
 * Project: Proj 05, hw5S12Individual
 */
// Node class, used to hold our data in the skiplist.
class Node{
	// names, times, and pointers to all of the necessary other nodes.
	// i am leaving left and up in order to edit nodes like the tail, because it will
	// simply make it easier for me.
    public String name;
    public String time;
    public Node left;
    public Node right;
    public Node up;
    public Node down;
    
    public Node(String name, String time) {
    	// constructor for specific nodes.
    	this.name = name;
    	this.time = time;
    	this.left = null;
    	this.right = null;
    	this.up = null;
    	this.down = null;
    }
    public Node(Node node) {
    	// In case a node is referenced, it will simply have the same name and time
    	// as the node!
    	this.name = node.name;
    	this.time = node.time;
    	this.left = null;
    	this.right = null;
    	this.up = null;
    	this.down = node;
    }
}