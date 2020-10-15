package by.bsuir.model;

import java.util.Date;

public class Patient {
	
	public Patient(int id, String name, Date birthDate, String livingPlace) {
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
		this.livingPlace = livingPlace;
	}
	
	public int id;
	public String name;
	public String passportNumber;
	public Date birthDate;
	public String livingPlace;
	public String pastIllnesses;
	public PatientStatus status;
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public Date getBirthDate() {
		return birthDate;
	}

}
