/* 
 * Editor: Tyler Gutowski, tgutowski2020@fit.edu or tygutowski@gmail.com
 * Course: CSE 2010, Section 01, Fall 2021
 * Project: Proj 01, hw1S12GroupHelp
*/
public class SinglyLinkedList implements Cloneable {
  /**
   * Node of a singly linked list, which stores a reference to its
   * element and to the subsequent node in the list (or null if this
   * is the last node).
   */
  private static class Node {
    // custom implementation to store special data types
    private int timeStamp;
    private String contact;
    private String message; // reference to the element stored at this node
    private String sentTo;
    private Node next;      // reference to the subsequent node in the list

    //Creates a node with the given element and next node.
    // e  the element to be stored
    // n  reference to a node that should follow the new node
    public Node(int t, String c, String s, String m, Node n) {
      timeStamp = t;
      contact = c;
      message = m;
      next = n;
      sentTo = s;
    }

    public Node getNext() { return next; }

    public void setNext(Node n) { next = n; }

  private Node head = null;               // head node of the list (or null if empty)

  private int lowestTimeStamp = 0;
  private Node tail = null;               // last node of the list (or null if empty)
  private Node current = null;
  private int size = 0;                      // number of nodes in the list
  public SinglyLinkedList() { }              // constructs an initially empty list

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

  public int getTimeStamp() { return current.timeStamp; }
  public String getContact() { return current.contact; }
  public String getMessage() { return current.message; }
  public String getSentTo() { return current.sentTo; }
  public boolean isEmpty() { return size == 0; }

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

  public Node last() {              // returns (but does not remove) the last element
    if (isEmpty()) return null;
    return tail;
  }

  public void addFirst(int t, String c, String s, String m) {                // adds element e to the front of the list
    head = new Node(t, c, s, m, head);              // create and link a new node
    if (size == 0)
      tail = head;                           // special case: new node becomes tail also
    size++;
  }

  public void addLast(int t, String c, String s, String m) {                 // adds element e to the end of the list
    Node newest = new Node(t, c, s, m, null);    // node will eventually be the tail
    if (isEmpty())
      head = newest;                         // special case: previously empty list
    else
      tail.setNext(newest);                  // new node after existing tail
    tail = newest;                           // new node becomes the tail
    size++;
  }

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
