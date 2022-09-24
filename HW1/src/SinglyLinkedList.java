/* 
 * Editor: Tyler Gutowski, tgutowski2020@fit.edu or tygutowski@gmail.com
 * Course: CSE 2010, Section 01, Fall 2021
 * Project: Proj 01, hw1S12GroupHelp
 * 
 * This file is a modified version of the SinglyLinkedList. It was modified to
 * specifically store messages, along with their timestamps, sender, receiver, and
 * the next Node in the LinkedList.
 * 
 * Copyright 2014, Michael T. Goodrich, Roberto Tamassia, Michael H. Goldwasser
 *
 * Developed for use with the book:
 *
 *    Data Structures and Algorithms in Java, Sixth Edition
 *    Michael T. Goodrich, Roberto Tamassia, and Michael H. Goldwasser
 *    John Wiley & Sons, 2014
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
//package net.datastructures;

/**
 * A basic singly linked list implementation.
 *
 * @author Michael T. Goodrich
 * @author Roberto Tamassia
 * @author Michael H. Goldwasser
 */
public class SinglyLinkedList implements Cloneable {
  //---------------- nested Node class ----------------
  /**
   * Node of a singly linked list, which stores a reference to its
   * element and to the subsequent node in the list (or null if this
   * is the last node).
   */
  private static class Node {
	// custom implementation to store special data types (i could have used an object, but this was simpler
	// BUT IT WAS LESS IMPLEMENTABLE
    /** The element stored at this node */
    private int timeStamp;
    private String contact;
    private String message; // reference to the element stored at this node
    private String sentTo;

    /** A reference to the subsequent node in the list */
    private Node next;         // reference to the subsequent node in the list

    /**
     * Creates a node with the given element and next node.
     *
     * @param e  the element to be stored
     * @param n  reference to a node that should follow the new node
     */
    public Node(int t, String c, String s, String m, Node n) {
      timeStamp = t;
      contact = c;
      message = m;
      next = n;
      sentTo = s;
    }

    // Accessor methods
    /**
     * Returns the element stored at the node.
     * @return the element stored at the node
     */
    /**
     * Returns the node that follows this one (or null if no such node).
     * @return the following node
     */
    public Node getNext() { return next; }

    // Modifier methods
    /**
     * Sets the node's next reference to point to Node n.
     * @param n    the node that should follow this one
     */
    public void setNext(Node n) { next = n; }
  } //----------- end of nested Node class -----------

  // instance variables of the SinglyLinkedList
  /** The head node of the list */
  private Node head = null;               // head node of the list (or null if empty)

  private int lowestTimeStamp = 0;
  /** The last node of the list */
  private Node tail = null;               // last node of the list (or null if empty)

  private Node current = null;
  /** Number of nodes in the list */
  private int size = 0;                      // number of nodes in the list

  /** Constructs an initially empty list. */
  public SinglyLinkedList() { }              // constructs an initially empty list

  // access methods
  /**
   * Returns the number of elements in the linked list.
   * @return number of elements in the linked list
   */
  public int size() { return size; }
  public int getLowestTimeStampValue() {
	  lowestTimeStamp = head.timeStamp;
	  Node walker = head;
	  while(walker != null) {
		  if(walker.timeStamp < lowestTimeStamp) {
			  lowestTimeStamp = walker.timeStamp;
		  }
		  walker = walker.next;
	  }
	  return lowestTimeStamp;
  }
  /**
   * Tests whether the linked list is empty.
   * @return true if the linked list is empty, false otherwise
   */
  public int getTimeStamp() { return current.timeStamp; }
  public String getContact() { return current.contact; }
  public String getMessage() { return current.message; }
  public String getSentTo() { return current.sentTo; }
  public boolean isEmpty() { return size == 0; }
  /**
   * Returns (but does not remove) the first element of the list
   * @return element at the front of the list (or null if empty)
   */
  public Node first() {             // returns (but does not remove) the first element
    if (isEmpty()) return null;
    return head;
  }
  void deleteNode(int key)
  {
      // Store head node
      Node temp = head, prev = null;

      // If head node itself holds the key to be deleted
      if (temp != null && temp.timeStamp == key) {
          head = temp.next; // Changed head
          return;
      }

      // Search for the key to be deleted, keep track of
      // the previous node as we need to change temp.next
      while (temp != null && temp.timeStamp != key) {
          prev = temp;
          temp = temp.next;
      }

      // If key was not present in linked list
      if (temp == null)
          return;

      // Unlink the node from linked list
      prev.next = temp.next;
  }
  /**
   * Returns (but does not remove) the last element of the list.
   * @return element at the end of the list (or null if empty)
   */
  public Node last() {              // returns (but does not remove) the last element
    if (isEmpty()) return null;
    return tail;
  }

  // update methods
  /**
   * Adds an element to the front of the list.
   * @param e  the new element to add
   */

  public void addFirst(int t, String c, String s, String m) {                // adds element e to the front of the list
    head = new Node(t, c, s, m, head);              // create and link a new node
    if (size == 0)
      tail = head;                           // special case: new node becomes tail also
    size++;
  }

  /**
   * Adds an element to the end of the list.
   * @param e  the new element to add
   */
  public void addLast(int t, String c, String s, String m) {                 // adds element e to the end of the list
    Node newest = new Node(t, c, s, m, null);    // node will eventually be the tail
    if (isEmpty())
      head = newest;                         // special case: previously empty list
    else
      tail.setNext(newest);                  // new node after existing tail
    tail = newest;                           // new node becomes the tail
    size++;
  }

  /**
   * Removes and returns the first element of the list.
   * @return the removed element (or null if empty)
   */
  public Node removeFirst() {                   // removes and returns the first element
    if (isEmpty()) return null;              // nothing to remove
    Node answer = head;
    head = head.getNext();                   // will become null if list had only one node
    size--;
    if (size == 0)
      tail = null;                           // special case as list is now empty
    return answer;
  }
  public void setCurrentAsHead() {
	  current = head;
  }
  public void next() {
	  current = current.getNext();
  }
  public Node getCurrent() {
	  if(current == null) { return null; }
	  return current;
  }
  public void deleteCurrent() {
	  System.out.println("DELETE CURRENT NODE");
  }
  public void addAfterCurrent(int t, String c, String s, String m) {
	  Node newest = new Node(t, c, s, m, null);
	  if (isEmpty())
		  head = newest;
	  else
		  current.setNext(newest);
	  current = newest;
	  size++;
  }
  public void clear() {
	  head = null;
  }
  public int getNextMessage(int lowestTimeStamp, String expectedContact) {
	  int previous = 0;
	  int nextMessageTimeStamp = lowestTimeStamp;
	  Node walker = head;
	  while(walker != null) {
		  if (walker.timeStamp > nextMessageTimeStamp) {
		        previous = nextMessageTimeStamp;
		        nextMessageTimeStamp = walker.timeStamp;
		    }
		    else if (walker.timeStamp > previous) {
		        previous = walker.timeStamp;
		    }
		} 
	  return nextMessageTimeStamp;
  }
  public String findTimeStamp(int timeStamp) {
	  Node walker = head;
	  while(walker != null) {
		  if(walker.timeStamp == timeStamp) {
			  return walker.timeStamp + " " + walker.contact + " " + walker.message;
		  }
	  }
	  return null;
  }
}