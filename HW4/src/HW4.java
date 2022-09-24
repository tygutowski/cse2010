/*
 * Author:  Tyler Gutowski, tgutowski2020@fit.edu
 * Course:  CSE 2010, Section 02, Fall 2021
 * Project: hw4S12individual, Hospital
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// Main runner class, collects files from the arguments
// then uses the files retrieved and iterates through them.
// Creates a hospital class to run most of the code
// Throws file not found error in case the files specified
// in the arguments dont exist.
public class HW4 {
	// Make a hospital object which manages the doctors and patients
	public static Hospital hospital = new Hospital();
	// Throws exception in case files arent found
	public static void main(String[] args) throws FileNotFoundException {
		// Initialize the doctors (Start with Alice and Bob)
		hospital.initializeDoctors();
		// Set the input scanner
		//Scanner input = new Scanner(new File("C:\\Users\\tygut\\eclipse-workspace\\HW4\\src\\HW4Input"));
		Scanner input = new Scanner(new File(args[0]));
		// While there are new lines
		while(input.hasNextLine()) {
			String line = input.nextLine();
			String[] commands = line.split(" ");
			switch(commands[0]) {
			// If the first command is "patientArrives", call it in the hospital with its parameters
			case("patientArrives"):
				hospital.patientArrives(Integer.parseInt(commands[1]), commands[2], Integer.parseInt(commands[3]));
				break;
			// Do the same for doctorArrives
			case("doctorArrives"):
				hospital.doctorArrives(Integer.parseInt(commands[1]), commands[2]);
				break;
			}
		}
		// After everything is called, speed the time up to 2359 in order to unclog anyone
		// that is currently being treated (time doesnt normally move unless another patient
		// or doctor arrives)
		hospital.patientsFinishedArriving(0);
	}
}
