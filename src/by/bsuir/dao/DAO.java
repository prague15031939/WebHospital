package by.bsuir.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import by.bsuir.model.Doctor;
import by.bsuir.model.DoctorSpecialization;
import by.bsuir.model.Patient;
import by.bsuir.model.PatientStatus;
import by.bsuir.model.Prescription;
import by.bsuir.model.UserAccount;
import by.bsuir.model.UserStatus;

public class DAO {
	private String jdbcURL = "jdbc:mysql://localhost:3306/hospital?serverTimezone=Europe/Minsk&useSSL=false";
	private String jdbcUsername = "root";
	private String jdbcPassword = "MySqlPassword09052020";
	
	protected Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword); 
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return connection;
	}
	
	protected UserAccount FillUserAccount(PreparedStatement preparedStatement) {
		UserAccount acc = null;
		try {
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int id = Integer.parseInt(rs.getString("user_id"));
				String name = rs.getString("user_name");
				String hash = rs.getString("user_hash");
				acc = new UserAccount(id, name, hash);
				acc.email = rs.getString("email");
				acc.image = rs.getString("image");
				acc.status = UserStatus.valueOf(rs.getString("status"));
			} 
		}
		catch (Exception e) {}
		return acc;
	}
	
	public UserAccount GetUserAccount(String requestedHash) {		
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `user_account` WHERE `user_hash` = ?");
			preparedStatement.setString(1, requestedHash);
			return FillUserAccount(preparedStatement);
		} 
		catch (Exception e) {}
		return null;
	}
	
	public UserAccount GetUserAccount(int id) {		
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `user_account` WHERE `user_id` = ?");
			preparedStatement.setInt(1, id);
			return FillUserAccount(preparedStatement);
		} 
		catch (Exception e) {}
		return null;
	}
	
	public ArrayList<Patient> GetDoctorsPatients(int doctorID) {
		ArrayList<Patient> patients = new ArrayList<>(); 
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `service` WHERE `doctor_id` = ?");
			preparedStatement.setInt(1, doctorID);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int patientID = Integer.parseInt(rs.getString("patient_id"));
				patients.add(GetPatient(patientID));
			}
		} 
		catch (Exception e) {}
		
		return patients;
	}
	
	public Patient GetPatient(int patientID) {
		Patient pat = null;
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `patient` WHERE `id` = ?");
			preparedStatement.setInt(1, patientID);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int id = Integer.parseInt(rs.getString("id"));
				String name = rs.getString("name");
				Date birthDate = rs.getDate("birth_date");
				String livingPlace = rs.getString("living_place");
				pat = new Patient(id, name, birthDate, livingPlace);
				pat.passportNumber = rs.getString("passport");
				pat.pastIllnesses = rs.getString("past_ill");
				pat.status = PatientStatus.valueOf(rs.getString("status"));
			}
		} 
		catch (Exception e) {}
		
		return pat;
	}
	
	public Doctor GetDoctor(int doctorID) {
		Doctor doc = null;
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `doctor` WHERE `id` = ?");
			preparedStatement.setInt(1, doctorID);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int id = Integer.parseInt(rs.getString("id"));
				String name = rs.getString("name");
				Date birthDate = rs.getDate("birth_date");
				String spec = rs.getString("specialization");
				doc = new Doctor(id, name, birthDate, DoctorSpecialization.valueOf(spec));
			}
		} 
		catch (Exception e) {}
		
		return doc;
	}
	
	public int doPrescription(Prescription prescription, int doctorID, int patientID) {
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO prescription (patient_id, doctor_id, timestamp, diagnosis, procedures, medicines, manipulations) VALUES (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, patientID);
			preparedStatement.setInt(2, doctorID);
			preparedStatement.setObject(3, new java.sql.Timestamp(prescription.timestamp.getTime())); 
			preparedStatement.setString(4, prescription.diagnosis);
			preparedStatement.setString(5, String.join("`", prescription.procedures));
			preparedStatement.setString(6, String.join(";", prescription.medicines));
			preparedStatement.setString(7, String.join("`", prescription.manipulations));
			preparedStatement.executeUpdate();
			
			ResultSet rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {
			    return rs.getInt(1);
			} 
		} 
		catch (Exception e) {}
		
		return -1;
	}
	
	public ArrayList<Prescription> GetPatientsPrescriptions(int patientID) {
		ArrayList<Prescription> list = new ArrayList<>(); 
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `prescription` WHERE `patient_id` = ?");
			preparedStatement.setInt(1, patientID);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {	
				int prescriptionID = Integer.parseInt(rs.getString("id"));
				list.add(GetPrescription(prescriptionID));
			}
		} 
		catch (Exception e) {}
		
		return list;
	}
	
	public Prescription GetPrescription(int prescriptionID) {
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `prescription` WHERE `id` = ?");
			preparedStatement.setInt(1, prescriptionID);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				
				String diagnosis = rs.getString("diagnosis");
				String[] medicines = rs.getString("medicines").split(";");
				String[] procedures = rs.getString("procedures").split("`");
				String[] manipulations = rs.getString("manipulations").split("`");
				Prescription item = new Prescription(diagnosis, medicines, procedures, manipulations);
				item.timestamp = rs.getTimestamp("timestamp");
				item.prescriptionID = Integer.parseInt(rs.getString("id"));
				item.patientID = Integer.parseInt(rs.getString("patient_id"));
				item.doctorID = Integer.parseInt(rs.getString("doctor_id"));
				
				return item;
			}
		} 
		catch (Exception e) {}
		
		return null;
	}
}
