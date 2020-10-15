package by.bsuir.model;

import java.util.List;

public class Prescription {
	
	public Prescription(String diagnosis, String medicines, String[] procedures, String[] manipulations) {
		this.diagnosis = diagnosis;
		this.medicines = medicines;
		this.procedures = procedures;
		this.manipulations = manipulations;
	}
	
	public int prescriptionID;
	public int patientID;
	public int doctorID;
	public String diagnosis;
	public String[] procedures;
	public String medicines;
	public String[] manipulations;
}
