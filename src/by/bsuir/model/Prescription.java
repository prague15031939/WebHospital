package by.bsuir.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import by.bsuir.dao.DoctorDAO;

public class Prescription {
	
	public Prescription(String diagnosis, String[] medicines, String[] procedures, String[] manipulations) {
		this.diagnosis = diagnosis;
		this.medicines = medicines;
		this.procedures = procedures;
		this.manipulations = manipulations;
	}
	
	public Prescription() {
		
	}
	
	public int prescriptionID;
	public int patientID;
	public int doctorID;
	public Date timestamp;
	public String diagnosis;
	public String[] procedures;
	public String[] medicines;
	public String[] manipulations;
	
	public int getPrescriptionID() {
		return prescriptionID;
	}
	
	public int getPatientID() {
		return patientID;
	}
	
	public int getDoctorID() {
		return doctorID;
	}
	
	public Date getTimestamp() {
		return timestamp;
	}

	public String getDiagnosis() {
		return diagnosis;
	}
	
	public String[] getProcedures() {
		return procedures;
	}
	
	public String[] getMedicines() {
		return medicines;
	}

	public String[] getManipulations() {
		return manipulations;
	}
	
	public String getStringTime() {
		return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(timestamp);
	}
	
	public String getQuickDoctorInfo() {
		return new DoctorDAO().getDoctor(doctorID).getQuickInfo();
	}
}
