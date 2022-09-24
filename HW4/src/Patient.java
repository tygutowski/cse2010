/*
 * Author:  Tyler Gutowski, tgutowski2020@fit.edu
 * Course:  CSE 2010, Section 02, Fall 2021
 * Project: hw4S12individual, Hospital
 */

// Patient object. Implements comparable in order to
// compare their ESI values (to make a queue). Also has
// a name, a surgery startTime, an ESI (1-5, 1 being most
// dangerous), a surgeryDuration. SurgeryDuration is determined
// by 2^(6-ESI).
public class Patient implements Comparable<Patient> {
	private String name = null;
	private int startTime = 0;
	private int ESI = 0;
	private int surgeryDuration = 0;
	// Patient constructor to set their names, times and ESI values.
	public Patient(String patientName, int patientTime, int patientESI) {
		name = patientName;
		startTime = patientTime;
		// Surgery duration is 2^6-ESI
		surgeryDuration = (int) Math.pow(2, (6-patientESI));
		ESI = patientESI;
	}
	// Returns the name of the patient as
	// a String
	public String getName() {
		return this.name;
	}
	// Overridden class because it is implemented from
	// Comparable. No reason to use heaps in my opinion,
	// because there is no need to use a binary tree. All
	// we need to do is to compare the data from each object,
	// so using the built-in queue that java provides is
	// more than enough.
	@Override
	public int compareTo(Patient o) {
		return (this.ESI - o.ESI);
	}
	// Returns the ESI value of the patient as an integer.
	public int getESI() {
		return ESI;
	}
	// Returns the START time of the surgery. This is used
	// to determine what the expected finish time is.
	public int getStartTime() {
		return startTime;
	}
	// Returns the DURATION of the surgery as an integer. This
	// is used to determine what the expected finish time is
	public int getSurgeryDuration() {
		return surgeryDuration;
	}
	// Returns the expected finish time as an integer. The
	// reason its expected and not final is because the
	// time may be cancelled if a patient with a lower
	// ESI comes in.
	public int getExpectedFinishTime() {
		return startTime + surgeryDuration;
	}
}
