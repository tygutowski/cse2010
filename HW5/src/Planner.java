/*
 * Author: Tyler Gutowski, tgutowski2020@fit.edu or tygutowski@gmail.com
 * Course: CSE 2010, Section 01, Fall 2021
 * Project: Proj 05, hw5S12Individual
 */
// A planner class in order to organize the events. This class
// is used for adding, cancelling, printing and getting events
// based on specific parameters. All methods in here are called
// in HW5.java
public class Planner {
	// Static skiplist so its always on the heap.
	public static Skiplist skiplist = new Skiplist();
	// Add event method is used to add an event to the skiplist.
	// First it prints to add the event (given by parameters), then it
	// checks if there is already an event with the timestamp. If there is
	// it returns an error, otherwise it adds it.
	public void AddEvent(String time, String event) {
		System.out.print("AddEvent " + time + " " + event);
		// Check if there is an existing node
		Node eventNode = skiplist.get(time);
		if(eventNode == null) {
			// If there is not, add the event
			skiplist.put(event, time);
		}
		else {
			// If there is, print error
			System.out.print(" ExistingEventError:" + eventNode.name);
		}
		System.out.println();
	}
	// Cancel event method is used to remove an event from the skiplist.
	// First it prints that we are cancelling an event, then it
	// checks if the event exists. If it does not, it prints an error. If
	// the event does exist, it removes it from the list.
	public void CancelEvent(String time) {
		System.out.print("CancelEvent " + time);
		Node eventNode = skiplist.get(time);
		if(eventNode == null) {
			System.out.print(" NoEventError");
		}
		else {
			System.out.print(" " + eventNode.name);
			skiplist.remove(time);
		}
		System.out.println();
	}
	// Printskiplist simply prints that we are printing the
	// skip list, then calls skiplist.output(). output() is described
	// under Skiplist.java
	public void PrintSkipList() {
		System.out.println("PrintSkipList");
		skiplist.output();
	}
	// GetEvent returns the node of the event given the timestamp.
	// If the event exists, the node's name is printed. If it does not
	// exist, then an error is called.
	public void GetEvent(String time) {
		System.out.print("GetEvent " + time);
		Node eventNode = skiplist.get(time);
		if(eventNode == null) {
			System.out.print(" none");
		}
		else { 
			System.out.print(" " + eventNode.name);
		}
		System.out.println();
	}
	// GetEventsBetweenTimes prints all events between (inclusive) two timestamps.
	// I use a month list, day list, and hour list to iterate through all possible
	// timestamps between the two given timestamps. Then I check whether or not something
	// exists between the two. If it does exist, we print it, otherwise we simply ignore it.
	public void GetEventsBetweenTimes(String startTime, String endTime) {
		System.out.print("GetEventsBetweenTimes " + startTime + " " + endTime);
		String[] monthList = {"01", "02", "03", "04", "05", "06", "07", "08", "09",
			     "10", "11", "12"};
		String[] dayList = {"01", "02", "03", "04", "05", "06", "07", "08", "09",
			     "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
			     "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
		String[] hourList = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09",
			     "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
			     "20", "21", "22", "23"};
		// for every month
		for(int i = 0; i < monthList.length; i++) {
			// for every day
			for(int j = 0; j < dayList.length; j++) {
				// for every hour
				for(int k = 0; k < hourList.length; k++) {
					// if the time is between the starttime
					if(startTime.compareTo(monthList[i] + dayList[j] + hourList[k]) <= 0) {
						// and the endtime
						if(endTime.compareTo(monthList[i] + dayList[j] + hourList[k]) >= 0) {
							// check if there is a node that exists
							Node eventNode = skiplist.get(monthList[i] + dayList[j] + hourList[k]);
							// if it exists and isnt null
							if(eventNode != null) {
								// print it
								System.out.print(" " + eventNode.time + ":" + eventNode.name);
							}
						}
					}
				}
			}
		}
		System.out.println();
	}
	// Prints all events for a single day given the date. We use an hourlist to iterate through all hours
	// of a specific day, and if any of the timestamps match up, we print the time and name of the
	// event that matches up.
	public void GetEventsForOneDay(String date) {
		System.out.print("GetEventsForOneDay " + date + " ");
		String[] hourList = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09",
						     "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
						     "20", "21", "22", "23"};
		// for all hours
		for(String hour : hourList) {
			// check if there is an event at the date + hour
			Node eventNode = skiplist.get(date + hour);
			// if an event exists
			if(eventNode != null) {
				// print it
				System.out.print(eventNode.time + ":" + eventNode.name + " ");
			}
		}
		System.out.println();
	}
	// Prints all events for a single day after the given time. We use an hourlist to iterate through all hours
	// of a specific day, and if any of the timestamps match up, we print the time and name of the
	// event that matches up. This one differentiates from the previous method because instead of starting at 0,
	// we start at the index of the current time and work forward
	public void GetEventsForTheRestOfTheDay(String currentTime) {
		System.out.print("GetEventsForTheRestOfTheDay " + currentTime + " ");
		String[] hourList = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09",
			     "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
			     "20", "21", "22", "23"};
		// get the start hour
		String beginningHour = currentTime.substring(4,6);
		int startingIndex = 0;
		// for all hours from 0 to the index
		for(int i = 0; i < hourList.length; i++) {
			// check if the time is equal to your time
			if(hourList[i].equals(beginningHour)) {
				// if it is, then save and break;
				beginningHour = hourList[i];
				startingIndex = i;
				break;
			}
		}
		// then for all hours starting FROM your time up to 23
		for(int i = startingIndex; i < hourList.length; i++) {
			// check to see if there is an event with that timestamp
			Node eventNode = skiplist.get(currentTime.substring(0,4) + hourList[i]);
			// if it exists
			if(eventNode != null) {
				// print it!
				System.out.print(eventNode.time + ":" + eventNode.name + " ");
			}
		}
		System.out.println();
	}
	// Prints all events for a single day before the date. We use an hourlist to iterate through all hours
	// of a specific day, and if any of the timestamps match up, we print the time and name of the
	// event that matches up. This one differentiates from the last two because it starts at 0 and stops
	// iterating once we hit the time we are looking for.
	public void GetEventsFromEarlierInTheDay(String currentTime) {
		System.out.print("GetEventsFromEarlierInTheDay " + currentTime + " ");
		String[] hourList = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09",
			     "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
			     "20", "21", "22", "23"};
		// get the ending hour you want
		String endHour = currentTime.substring(4,6);
		int endIndex = 0;
		// check which index its at
		for(int i = 0; i <= hourList.length; i++) {
			if(hourList[i].equals(endHour)) {
				endHour = hourList[i];
				endIndex = i;
				break;
			}
		}
		// for all hours from 0 to the end index
		for(int i = 0; i <= endIndex; i++) {
			// check if there is an event with the same timestamp as the one given
			Node eventNode = skiplist.get(currentTime.substring(0,4) + hourList[i]);
			// if event exists
			if(eventNode != null) {
				// print it
				System.out.print(eventNode.time + ":" + eventNode.name + " ");
			}
		}
		System.out.println();
	}
}
