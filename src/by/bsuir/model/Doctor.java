package by.bsuir.model;

import java.util.Date;

public class Doctor {
	
	public Doctor(int id, String name, Date birthDate, DoctorSpecialization specialization) {
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
		this.specialization = specialization;
	}
	
	public int id;
	public String name;
	public Date birthDate;
	public DoctorSpecialization specialization;
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public Date getBirthDate() {
		return birthDate;
	}
	
	public DoctorSpecialization getSpecialization() {
		return specialization;
	}
}
