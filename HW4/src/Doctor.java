/*
 * Author:  Tyler Gutowski, tgutowski2020@fit.edu
 * Course:  CSE 2010, Section 02, Fall 2021
 * Project: hw4S12individual, Hospital
 */

// Doctor object, has a name, a current time and a patient that they
// are currently helping. default patient is set to null (because the
// doctor does not start out by helping someone, but instead needs to
// be assigned to a patient). Public class because we need to access
// and create new doctors whenever a new one "arrives"
public class Doctor {
	private String name = null;
	private int time = 0;
	private Patient patient = null;
	public Doctor(String doctorName, int doctorTime, Patient doctorPatient) {
		name = doctorName;
		time = doctorTime;
		patient = doctorPatient;
	}
	// isBusy determines if the
	// doctor is currently helping
	// a patient. returns false if no
	// patient is currently being helped
	public boolean isBusy() {
		if(patient != null) {
			return true;
		}
		return false;
	}
	// sets the patient given, requires
	// a Patient object
	public void setPatient(Patient doctorPatient) {
		patient = doctorPatient;
	}
	// returns the Patient the doctor
	// is currently helping
	public Patient getPatient() {
		return patient;
	}
	// returns the name of the
	// doctor
	public String getName() {
		return name;
	}
	// returns the current time that
	// the doctor is in.
	public int getTime() {
		return time;
	}
	// sets the current time it is for
	// the doctor. this updates whenever a 
	// patient is finished being helped
	public void setTime(int doctorTime) {
		time = doctorTime;
	}
}
