/*
 * Author: Tyler Gutowski, tgutowski2020@fit.edu or tygutowski@gmail.com
 * Course: CSE 2010, Section 01, Fall 2021
 * Project: Proj 05, hw5S12Individual
 */
// Some help received from Jojo Zhuang on jojozhuang.github.io to understand
// the concepts of skip lists better.

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Create a skip list class which contains nodes. The skip list has a head and tail, and uses the
// FakeRandHeight for the height map in order to quickly traverse over the skiplist. The class
// contains a get, put, remove and output method.
public class Skiplist {
    // Create a head and a tail and initialize a static rng object which will be used to get the
	// height of each index in the list.
	private Node head;
    private Node tail;
    public static FakeRandHeight rng = new FakeRandHeight();
    public Skiplist() {
    	// Constructor. Set the head as the minumum time (jan 1, 00:00)
        head = new Node("head", "010100");
        // Set the teail as the max time (dec 31, 23:59)
        tail = new Node("tail", "123123");
        // the head's right node is the tail
        head.right = tail;
        // the tail's left node is the head.
        tail.left = head;
    }
    // "get"s a node. given the time, the skiplist checks for the value the user is looking for and
    // returns a NODE that matches the time you're looking for.
    public Node get(String time) {
    	// set the current as the head
    	Node current = head;
    	// while the current isnt null (meaning there is a list)
    	while(current != null) {
    		// while there are nodes to the right, and ONLY IF THE NODE TO THE RIGHT IS LARGER THAN THE CURRENT NODE
    		while(current.right != null && current.right.time.compareTo(time) <= 0) {
    			// then go to the node to the right
				current = current.right;
			}
    		// if you happen to hit the node youre looking for, break
			if(current.time.equals(time)) {
				break;
			}
			// if you didnt find your node but reached the end, go down one
			current = current.down;
		}
    	// return current. if no node is found it will
    	// return null.
    	return current;
    }
    public boolean put(String name, String time) {
    	// Make a list of nodes that need to be updated
    	List<Node> update = new ArrayList<>();
    	// Set the current node to the head
    	Node current = head;
    	// While the current node isnt null and the right isn't
    	// null, then the right is the new current.
    	while(current != null) {
    		// while the right's node is less than the given node
			while(current.right != null && current.right.time.compareTo(time) < 0) {
				// right is the new current
				current = current.right;
			}
			// add the current node to the list of nodes that need
			// to be updated
			update.add(current);
			// iterate downwards (because now there are no longer any
			// lesser nodes to the right!
			current = current.down;
		}
    	int level = 0;
    	Node newNode = null;
    	// Make rng object from FakeRandHeight class. This is used
    	// to "randomly" generate a height for each of the items in
    	// the skip list.
    	int tempRNG = rng.get();
    	// For the height of the number returned by rng
    	//System.out.println(tempRNG);
    	int iterator = 0;
    	// if the level is 0 (meaning that there needs to be at least one level
    	// created) or if the iterator is less than the tempRNG (meaning that the 
    	// random height created isnt met yet, keep creating nodes upwards)
    	while(level == 0 || level <= tempRNG) {
    		// If the node is null (meaning its brand new/first iteration)
    		if(newNode == null) {
    			// give it a name and time
    			newNode = new Node(name, time);
    		}
    		else {
    			// otherwise simply create ANOTHER node to place on top of it
    			newNode = new Node(newNode);
    		}
    		Node updateNode;
    		// if the list of nodes that need to be updated is less than (or equal
    		// to) the level, then increase the height of the skiplist by one and
    		// set the new node to the head of the list (head of the list is now
    		// also up one level (top left))
    		if (update.size() <= level) {
    			increaseLevel();
    			updateNode = this.head;
    		}
    		else {
    			// otherwise just set the new node to the current head, in case
    			// the height doesnt need to be increased by another
    			updateNode = update.get(update.size() - level - 1);
    		}
    		// set the pointers of each node to the correct nodes
    		// to insert a node you need to put the new node to the right of the
    		// old node, the new node to the left of the old node
    		newNode.right = updateNode.right;
    		newNode.left = updateNode;
    		// then set the pointers of the old node to the new node.
    		newNode.right.left = newNode;
    		updateNode.right = newNode;
    		// increase the level by 1
    		level += 1;
    	}
    	return true;
    	
    }
    public boolean remove(String time) {
    	// create a list of nodes that need to be updated
    	List<Node> update = new ArrayList<>();
    	// current node is the head
    	Node current = this.head;
    	// while the current node exists (and so does the list)
    	while (current != null) {
    		// while the node to the right exists and is greater than your
    		// current node
    		while (current.right != null && current.right.time.compareTo(time) < 0) {
				// go to the right
    			current = current.right;
			}
    		
    		if (current.right.time.equals(time)) {
    			// if you find the node youre looking for
    			// the updated node is the node youre on
    			Node updateNode = current;
    			Node deleteNode = updateNode.right;
        		// then edit the pointers in order to delete the node
    			// to your right
        		updateNode.right = deleteNode.right;
        		deleteNode.right.left = updateNode;
        		deleteNode.up = null;
        		deleteNode.down = null;
        		// DO NOT BREAK BECAUSE WE NEED TO REMOVE EVERY INDEX OF
        		// THE EVENT
    		}
    		// go down a level
    		current = current.down;
    	}
    	return true;
    }
    // Method to increase the level by one (level given by rng). It creates a new identical tail and head, then
    // adjusts the new head above the current head, the new tail above the current tail. Basically just pushing everything that
    // already exists up one level.
    private void increaseLevel() {
    	// Creates new tail and head nodes to put on top of current tail and head
    	Node higherHead = new Node(head.name, head.time);
        Node higherTail = new Node(tail.name, tail.time);
        // Links the pointers of the new nodes to one another
        higherHead.right = higherTail;
        higherTail.left = higherHead;
        // Links the pointers of the new nodes to the old nodes
        head.up = higherHead;
        higherHead.down = head;
        head = higherHead;
        tail.up = higherTail;
        higherTail.down = tail;
        tail = higherTail;
    }
    // Method for printing the output required in the HW5 pdf. begins by printing the highest layer (which should be empty)
    // then works its way down to the lowest layer, printing each of the events at each layer.
    public void output() {
    	// make an output list to print later
    	List<String> outputList = new ArrayList<String>();
    	// get the height of the skiplist
    	int height = getHeight();
    	// set the current node to the head
	    Node current = this.head;
	    // while the current isnt null (list exists)
	    while(current != null) {
	    	// make a temp string to add to the outputlist later
	    	String tempString = "";
	    	Node first = current;
	    	first = first.right;
	    	// if there is a node to the right, add it to the tempString
	    	if(first.right != null) {
	    		tempString += "(S" + height + ") ";
	    	}
	    	while(first.right != null) {
	    		// for every node to the right, add it to the temp string
	    		tempString += first.time + ":" + first.name + " ";
	    		first = first.right;
	    	}
	    	// then go down once there are no more nodes
	    	current = current.down;
	    	// and decrease the height
    		height -= 1;
    		// then add it to the output list
    		outputList.add(tempString);
	    }
	    outputList.removeAll(Arrays.asList("", null));
	    // remove any empty spaces (in case there were no nodes on the level)
	    // and print the top level node as empty
	    System.out.println("(S" + outputList.size() + ") empty");	    
	    // then for every item in the outputlist, print it.
	    for(String item : outputList) {
	    	System.out.println(item);
	    }
    }
    // Method that returns the height of the skiplist (the highest value + 1).
    public int getHeight() {
    	int height = 0;
    	// start at the top of the head
    	Node current = this.head;
    	// work your way down
    	while(current.down != null && current.right != null)  {
    		current = current.down;
    		// iterating by one at a time.
    		height += 1;
    	}
    	// return the height
    	return height;
    }
}