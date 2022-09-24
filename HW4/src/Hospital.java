/*
 * Author:  Tyler Gutowski, tgutowski2020@fit.edu
 * Course:  CSE 2010, Section 02, Fall 2021
 * Project: hw4S12individual, Hospital
 */

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

// The hospital class is the main method which manages most of the calculations.
// It manages all of the doctors and patients, and has a static queue for the
// patients, and a static list for the doctors which can be accessed from anywhere
// in the class.
public class Hospital {
	public static PriorityQueue<Patient> patientQueue = new PriorityQueue<Patient>();
	public static List<Doctor> doctorList = new ArrayList<Doctor>();
	// When a new patient arrives, tell the console that
	// they have arrived, create new Patient object, and
	// add the patient to the queue. Then, if there
	// are any free doctors, set the doctor to the
	// patient. If there aren't any free doctors, check
	// the ESI of all patients, and swap one out if needed.
	public void patientArrives(int time, String name, int esi) {
		// Print that the patient arrives
		System.out.println("patientArrives " + String.format("%0" + 4 + "d", time) + " " + name + " " + esi);
		// Create a new patient object with the parameters
		Patient patient = new Patient(name, time, esi);
		// Add the patient to the queue
		patientQueue.add(patient);
		// Check all current patients (To see if any are ready to be checked out)
		checkCurrentPatients(time);
		// For all doctors
		for (Doctor doctor : doctorList) {
			// If any are not busy
			if(!doctor.isBusy()) {
				// Start curing the patient
				doctorStartCuringPatient(doctor, patient.getStartTime());
				// Return, because only one doctor needs to be curing a patient
				// at a time, and we don't need to check ESI of the patients.
				return;
			}
		}
		// If all doctors are busy, then check the ESI ratings of
		// all of the patients being cured, in case there is a more
		// severe injury
		checkPatientESI(patient);
	}
	// Method to check all patient ESIs, and automatically swap a doctor
	// to the given patient if a patient has a higher ESI than the patient
	// given.
	public void checkPatientESI(Patient patient) {
		// The higher the ESI, the less dangerous the
		// injury, so set the highest as 5 (1-5)
		int highestESI = 5;
		Doctor tempDoctor = null;
		// For all doctors in the doctorList
		for (Doctor doctor : doctorList) {
			// If the doctor currently has a patient.
			if(doctor.isBusy()) {
				// If the doctor patient has a lower ESI than
				// the highest ESI, then set highest as 
				if(doctor.getPatient().getESI() < highestESI) {
					highestESI = doctor.getPatient().getESI();
					// Set the temp doctor (the doctor we MIGHT want to swap)
					// as the doctor referenced
					tempDoctor = doctor;
				}
			}
		}
		// If the highest ESI is not 1 (It did change)
		if(highestESI != 1) {
			// Add the patient to the queue, because
			// We want to swap, because there is a less
			// important patient that can be swapped out
			patientQueue.add(tempDoctor.getPatient());
			tempDoctor.setPatient(patient);
		}
	}	
	// When a new doctor arrives, create a new Doctor object with
	// the time they arrived and their name. Add them to a doctor
	// list, and begin curing the next patient that needs curing.
	public void doctorArrives(int time, String name) {
		System.out.println("doctorArrives " + String.format("%0" + 4 + "d", time) + " " + name);
		Doctor doctor = new Doctor(name, time, null);
		doctorList.add(doctor);
		if(!patientQueue.isEmpty()) {
			Patient patient = patientQueue.poll();
			doctor.setPatient(patient);
			String timeString = String.format("%0" + 4 + "d",time);
			int hours = Integer.parseInt(timeString.substring(0, 2));
			int minutes = Integer.parseInt(timeString.substring(2,4));
			hours += minutes/60;
			minutes %= 60;
			System.out.println("doctorStartsTreatingPatient " + String.format("%0" + 2 + "d",hours) + String.format("%0" + 2 + "d",minutes) + " " + doctor.getName() + " "  + doctor.getPatient().getName());
		}
		checkCurrentPatients(time);
	}
	// When this method is called, the doctor referenced polls the
	// next patient - the patient with the lowest ESI value - and
	// prints that the doctor has started curing the patient
	public void doctorStartCuringPatient(Doctor doctor, int time) {
		if(!patientQueue.isEmpty()) {
			Patient patient = patientQueue.poll();
			doctor.setPatient(patient);
			String timeString = String.format("%0" + 4 + "d",time);
			int hours = Integer.parseInt(timeString.substring(0, 2));
			int minutes = Integer.parseInt(timeString.substring(2,4));
			hours += minutes/60;
			minutes %= 60;
			System.out.println("doctorStartsTreatingPatient " + String.format("%0" + 2 + "d",hours) + String.format("%0" + 2 + "d",minutes) + " " + doctor.getName() + " " + doctor.getPatient().getName());
		}
		checkCurrentPatients(time);
	}
	// When the doctor finishes curing a patient, set the doctor's time
	// to the highest value between the patient's expected finish time, and
	// the doctor's time + the surgery duration. This is because the doctor
	// only updates his watch when a new doctor arrives or a surgery is finished,
	// so he isn't always sure what time it is.
	public void doctorFinishesCurePatient(Doctor doctor, Patient patient) {
		// Doctor fixes his watch
		doctor.setTime(Math.max(doctor.getPatient().getExpectedFinishTime(), doctor.getPatient().getSurgeryDuration() + doctor.getTime()));
		// Print out to the console
		String timeString = String.format("%0" + 4 + "d",doctor.getTime());
		int hours = Integer.parseInt(timeString.substring(0, 2));
		int minutes = Integer.parseInt(timeString.substring(2,4));
		hours += minutes/60;
		minutes %= 60;
		System.out.println("doctorFinishesTreatmentAndPatientDeparts " + String.format("%0" + 2 + "d",hours) + String.format("%0" + 2 + "d",minutes) + " " + doctor.getName() + " " + doctor.getPatient().getName());
		// Doctor's new patient is null, because he is finished helping a patient
		doctor.setPatient(null);
		// And the doctor starts curing the next patient.
		doctorStartCuringPatient(doctor, doctor.getTime());
	}
	// To initialize the doctor list. Creates two Doctor objects
	// with empty patients and time set to 0 and adds them both 
	// to the doctor list. Set the times to 800 because the hospital
	// opens at 8am
	public void initializeDoctors() {
		// Create two new doctors to start with
		Doctor alice = new Doctor("Alice", 800, null);
		Doctor bob = new Doctor("Bob", 800, null);
		// Add them to the doctor list
		doctorList.add(alice);
		doctorList.add(bob);
	}
	// Method to check the status of all current patients.
	// Will determine if any patients are finished with
	// their treatments. When a patient is finished, it will
	// print to the console that they're finished, then it will
	// remove them from the patientQueue and free up the
	// doctor's current patient.
	public void checkCurrentPatients(int time) {
		// If there are doctors working
		if(!doctorList.isEmpty()) {
			// For all doctors in the doctor list
			for (Doctor doctor : doctorList) {
				// If the doctor is working on a patient
				if(doctor.isBusy()) {
					//System.out.println(doctor.getName());
					// If the patient's expected time has been passed
					if(doctor.getTime() + doctor.getPatient().getSurgeryDuration() <= time) {
						// Then the doctor finishes curing the patient
						doctorFinishesCurePatient(doctor, doctor.getPatient());
					}
				}
			}
		}
	}
	// Once all patients and doctors are finished arriving, 
	// set the time to 2359 (the latest time possible) in order
	// to "speed up" time in order to finish any procedures
	// still in progress. Uses a for loop to iterate through each
	// minute, in order to print the times in order
	public void patientsFinishedArriving(int latestTime) {
		for(int i = latestTime; i <= 2359; i++) {
			if(!doctorList.isEmpty()) {
				// For all doctors in the doctor list
				for (Doctor doctor : doctorList) {
					// If the doctor is working on a patient
					if(doctor.isBusy()) {
						//System.out.println(doctor.getName());
						// If the patient's expected time has been passed
						if((doctor.getPatient().getSurgeryDuration() + doctor.getTime()) == i) {
							// Then the doctor finishes curing the patient
							doctorFinishesCurePatient(doctor, doctor.getPatient());
						}
					}
				}
			}
		}
	}
}
