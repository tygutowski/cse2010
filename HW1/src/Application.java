/*
 * Author: Tyler Gutowski, tgutowski2020@fit.edu or tygutowski@gmail.com
 * Course: CSE 2010, Section 01, Fall 2021
 * Project: Proj 01, hw1S12GroupHelp
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Application object made to manage the methods to access the SinglyLinkedList
// No input handled in this class, only output.
public class Application {
	// Create two SinglyLinkedLists to hold both new messages and all messages 
	// (messages contains new messages)
	SinglyLinkedList messages = new SinglyLinkedList();
	SinglyLinkedList new_messages = new SinglyLinkedList();
	// When the application is created, set the current pointer to the head
	Application() {
		messages.setCurrentAsHead();
	}
	// This method is for printing "DisplayConversation" and then also printing the conversation
	// This is only called when calling DisplayConversation in HW1.java
	public void DisplayConvo(String expectedContact) {
		System.out.println("DisplayConversation " + expectedContact);
		DisplayConversation(expectedContact);
	}
	// This method does NOT print "DisplayConversation". This is used for
	// DeleteMessage() and SendMessage()
	public void DisplayConversation(String expectedContact) {
		// Make ArrayList to handle the order of the timestamps
		List<Integer> timeStampList = new ArrayList<>();
		// Set current message pointer to the head
		messages.setCurrentAsHead();
		// Iterate through all to the end
		while(messages.getCurrent() != null) {
			// If the message is sent by the contact, then add to the timestamp to the list
			if(messages.getContact().equals(expectedContact)) {
				int timeStamp = messages.getTimeStamp();
				timeStampList.add(timeStamp);
			}
			// If the message is sent to the contact, then add the timestamp to the array list
			if(messages.getSentTo().equals(expectedContact)) {
				int timeStamp = messages.getTimeStamp();
				timeStampList.add(timeStamp);
			}
			// Go to the next pointer (Should loop through all for the pointer)
			messages.next();
		}
		// Sort the list, to print the lowest value
		Collections.sort(timeStampList);
		// For every expected timestamp (Send by OR sent to the contact)
		for (int expectedTimeStamp : timeStampList) {
			// Go to the head
			messages.setCurrentAsHead();
			// Then find the first instance of the timestamp listed (should only be 1)
			while(messages.getCurrent() != null) {
				// If the timeStamp is the expected timeStamp
				if(expectedTimeStamp == messages.getTimeStamp()) {
					int timeStamp = messages.getTimeStamp();
					String contact = messages.getContact();
					String message = messages.getMessage();
					// Then print the data. Formatting because timeStamp is an input.
					System.out.printf("%06d", timeStamp);
					System.out.println(" " + contact + " " + message);
				}
				// Go to the next message
				messages.next();
			}
		}
	}
	// Called whenever the user sends a message. Prints to allow the user to know when its send,
	// then adds it to the messages LinkedList
	public void SendMessage(String timeStamp, String contact, String message) {
		// Convert timestamp string to int
		int timeStampInt = Integer.parseInt(timeStamp);
		// Print when the player sends a message
		System.out.println("SendMessage " + timeStamp + " " + contact + " " + message);
		// Add a new node to the end of the list
		messages.addLast(timeStampInt, "me", contact, message);
		// Then display the new conversation
		DisplayConversation(contact);
	}
	// Called whenever the user deletes a message. Prints to allow the user to know when its deleted,
	// and also find the first instance of the timeStamp and contact that is wanted to delete; then 
	// delete it.
	public void DeleteMessage(String expectedTimeStamp, String expectedContact) {
		// Print when the player deletes a message
		System.out.println("DeleteMessage " + expectedTimeStamp + " " + expectedContact);
		// convert timestamp string to int
		int timeStampInt = Integer.parseInt(expectedTimeStamp);	
		// set to head and iterate through
		messages.setCurrentAsHead();
		while(messages.getCurrent() != null) {
			// get variables needed
			int timeStamp = messages.getTimeStamp();
			String contact = messages.getContact();
			String sentTo = messages.getSentTo();
			// if the timestamp is what we're looking for
			if (timeStamp == timeStampInt) {
				if(contact.equals(expectedContact)) {
					// and it was sent BY the contact, delete
					messages.deleteNode(messages.getTimeStamp());
				}
				if(sentTo.equals(expectedContact)) {
					// and it was sent FROM the contact, delete
					messages.deleteNode(messages.getTimeStamp());
				}
			}
			// next message
			messages.next();
		}
		// display conversation once done
		DisplayConversation(expectedContact);
	}
	// Closes the App
	public void CloseApp() {
		System.out.println("CloseApp");
	}
	public void ReceiveMessage(String timeStamp, String contact, String message) {
		int timeStampInt = Integer.parseInt(timeStamp);		
		System.out.println("NotifyUser " + contact);
		// two linked lists, one for all messages, and one for new messages (for OpenApp)
		messages.addLast(timeStampInt, contact, "me", message);
		new_messages.addLast(timeStampInt, contact, "me", message);
	}
	// Opens the app. Prints to let the player know who sent them messages while they were offline.
	// Also wipes all new_messages whenever the app is opened.
	public void OpenApp() {
		// instance variables
		System.out.println("OpenApp");
		int newMessagesAlice = 0;
		int newMessagesBob = 0;
		int newMessagesCarol = 0;
		// set pointer to head
		new_messages.setCurrentAsHead();
		// iterate through all pointers
		while(new_messages.getCurrent() != null) {
			// for each pending message, if the contact is whoever, then add to their newmessages
			String contact = new_messages.getContact();
			if(contact.equals("Alice")) {
				newMessagesAlice += 1;
			}
			else if(contact.equals("Bob")) {
				newMessagesBob += 1;
			}
			else if(contact.equals("Carol")) {
				newMessagesCarol += 1;
			}
			new_messages.next();
		}
		// print all new messages
		System.out.println("Alice " + Integer.toString(newMessagesAlice));
		System.out.println("Bob " + Integer.toString(newMessagesBob));
		System.out.println("Carol " + Integer.toString(newMessagesCarol));
		// set head pointer to null to clear list
		new_messages.clear();
	}
}
