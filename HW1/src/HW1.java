/*
 * Author: Tyler Gutowski, tgutowski2020@fit.edu or tygutowski@gmail.com
 * Course: CSE 2010, Section 01, Fall 2021
 * Project: Proj 01, hw1S12GroupHelp
 */

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

// HW1 is to detect input from the user to determine what file they want to run
// It also takes the input from the files, then parses it into Strings, then sends the
// information to Application.java to be stored in a SinglyLinkedList
public class HW1 {
	// main class, detects input and parses it into strings to be used
	public static void main(String[] args) throws IOException {
		// File name is argument 0, so make a new scanner with the file name
		Scanner input = new Scanner(new File(args[0]));
		// Create new app object to manage data
		Application app = new Application();
		// Scanner input for every line
		while(input.hasNextLine()) {
			// Get the next line input
			String str = input.nextLine();
			// Split the string to an array
			String[] command = str.split(" ");
			// Set the message to blank
			String message = "";
			// First two items in command[] are parameters, everything else is the third parameter (message)
			for(int i = 3; i < command.length; i++) {
				message += command[i] + " ";
			}
			// switch case to call functions
			switch (command[0]) {
				// If ReceiveMessage is called
				case "ReceiveMessage": 
						app.ReceiveMessage(command[1], command[2], message);
					break;
				// If OpenApp is called
				case "OpenApp": 
					if(command.length == 1) {
						app.OpenApp();
					}
					break;
				// If DisplayConversation is called
				case "DisplayConversation": 
					if(command.length == 2) {
						app.DisplayConvo(command[1]);
					}
					break;
				// If SendMessage is called
				case "SendMessage": 
						app.SendMessage(command[1], command[2], message);
					break;
				// If DeleteMessage is called
				case "DeleteMessage": 
					if(command.length == 3) {
						app.DeleteMessage(command[1], command[2]);
					}
					break;
				// If CloseApp is called
				case "CloseApp": 
					if(command.length == 1) {
						app.CloseApp();
					}
					break;
				// Otherwise ignore
				default:
					break;
					
			}
		}
		// Close input to save memory
		input.close();
	}
}