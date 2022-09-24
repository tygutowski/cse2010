/*
 * Author: Tyler Gutowski, tgutowski2020@fit.edu or tygutowski@gmail.com
 * Course: CSE 2010, Section 01, Fall 2021
 * Project: Proj 05, hw5S12Individual
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// class HW5 is used only to collect input from the input file, then
// it simply calls methods from the planner class to do the
// computations. Throws a fileNotFoundException in case there are any
// problems finding the file. Uses an array to store the parameters
public class HW5 {
	public static void main(String[] args) throws FileNotFoundException {
		// Create a new planner object
		Planner planner = new Planner();
		Scanner input = new Scanner(new File("C:\\Users\\tygut\\eclipse-workspace\\HW5\\src\\HW5Input"));
		// while there is still more input to the file
		while(input.hasNextLine()) {
			String line = input.nextLine();
			String[] commands = line.split(" ");
			// command[0] is always the method i am calling,
			// commands[1:] are the parameters of the methods
			
			// use a switch to determine which method to call. simpler than using a
			// bunch of if statements
			switch(commands[0]) {
			case("AddEvent"):
				planner.AddEvent(commands[1], commands[2]);
				break;
			case("CancelEvent"):
				planner.CancelEvent(commands[1]);
				break;
			case("GetEvent"):
				planner.GetEvent(commands[1]);
				break;
			case("GetEventsBetweenTimes"):
				planner.GetEventsBetweenTimes(commands[1], commands[2]);
				break;
			case("GetEventsForOneDay"):
				planner.GetEventsForOneDay(commands[1]);
				break;
			case("GetEventsForTheRestOfTheDay"):
				planner.GetEventsForTheRestOfTheDay(commands[1]);
				break;
			case("GetEventsFromEarlierInTheDay"):
				planner.GetEventsFromEarlierInTheDay(commands[1]);
				break;
			case("PrintSkipList"):
				planner.PrintSkipList();
				break;
			}
		}
	}
}
